package net.sf.xbus.technical;

import java.lang.management.ManagementFactory;
import java.util.Iterator;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import net.sf.xbus.admin.jmx.Administrator;
import net.sf.xbus.application.PreProcessor;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.TAManager;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.trace.Trace;

/**
 * <code>ReceiverService</code> runs as a background service and waits for
 * incoming messages to process.
 * <p>
 * At startup, <code>ReceiverService</code> searches for systems, which receiver
 * ends with <code>"Thread"</code>. For every found system, a thread is started
 * to process the messages. The used receivers must extend the
 * {@link net.sf.xbus.technical.ReceiverThreadBase}.
 * <p>
 */

public class ReceiverService {
	/**
	 * Reference to the Singleton object
	 */
	static private ReceiverService mReceiverService = null;

	/**
	 * Used for locking during <code>getInstance</code>
	 */
	static private final Object classLock = ReceiverService.class;

	/**
	 * Indicates if the JMX administration shall be started
	 */
	private boolean mStartJMX = false;

	/**
	 * Indicates if the systems for processing incoming messages are already
	 * started
	 */
	private boolean mSystemsStarted = false;

	/**
	 * The Watchdog is a thread that checks every few seconds, if all
	 * ReceiverThreads are still running.
	 */
	private ReceiverServiceWatchdog mWatchdog = null;
	private Thread mWatchdogThread = null;

	/**
	 * The constructor should be private to implement the <code>Singleton</code>
	 * pattern. But the bootstrapping mechanism needs a public constructor.
	 */
	public ReceiverService() {
	}

	/**
	 * Returns the one and only instance of the <code>ReceiverService</code>,
	 * which implements the <b>Singleton </b> pattern. If the object doesn't
	 * exist, it is created.
	 * 
	 * @return the one and only instance of the <code>ReceiverService</code>
	 */
	public static ReceiverService getInstance() {
		synchronized (classLock) {
			if (mReceiverService == null) {
				mReceiverService = new ReceiverService();
			}

			return mReceiverService;
		}
	}

	/**
	 * Starts all threads to receive messages and for administration.
	 * 
	 * @param insideServletEngine
	 *            if true, the ReceiverService is running inside a servlet
	 *            engine
	 */
	public synchronized void start(boolean insideServletEngine)
			throws XException {
		Trace.always("Entering ReceiverService.start");

		/*
		 * The class variable must be set here because an issue with the
		 * classloader. Do not remove!
		 */
		mReceiverService = this;

		/*
		 * Register MBeans
		 */
		try {
			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
			ObjectName name = new ObjectName("xBus:mbean=Administrator");
			Administrator mbean = new Administrator();
			mbs.registerMBean(mbean, name);
		} catch (Exception e) {
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_ADMIN, Constants.PACKAGE_ADMIN_JMX, "0", e);
		}

		startAllSystems();
		ReceiverThreadManager.getInstance().setIsServletEngine(
				insideServletEngine);
		startWatchdog();

		Trace.always("Leaving ReceiverService.start");
	}

	/**
	 * Starts all threads to receive messages and for administration. The
	 * <code>ReceiverService</code> is running inside a servlet engine.
	 */
	public synchronized void startInsideServletEngine() throws XException {
		start(true);
	}

	/**
	 * Starts all threads to receive messages and for administration. The
	 * <code>ReceiverService</code> is not running inside a servlet engine.
	 */
	public synchronized void startWithoutServletEngine() throws XException {
		start(false);
	}

	/**
	 * Stops all threads, including the administration threads.
	 * 
	 * @return true if ReceiverService has been stopped
	 */
	public synchronized boolean stop() throws XException {
		Trace.always("Stopping ReceiverService...");

		stopWatchdog();
		stopAllSystems();
		TAManager.getInstance().close();
		// if (mStartJMX)
		// {
		// AdministratorJMXServer.stop();
		// }

		Trace.always("ReceiverService stopped");
		return true;
	}

	/**
	 * Starts all configured threads to process message for different systems.
	 */
	public synchronized void startAllSystems() throws XException {
		Configuration config = Configuration.getInstance();

		PreProcessor.process(PreProcessor.RECEIVER_SERVICE);

		List sections = config.getSections(Constants.CHAPTER_SYSTEM);
		String system = null;
		String receiver = null;
		for (Iterator it = sections.iterator(); it.hasNext();) {
			system = (String) it.next();
			receiver = config.getValueOptional(Constants.CHAPTER_SYSTEM,
					system, "Receiver");
			if ((receiver != null) && (receiver.endsWith("Thread"))) {
				ReceiverThreadManager.getInstance().startReceiverThread(system);
			}
		}

		mSystemsStarted = true;
	}

	/**
	 * Stops all threads after processing their current message.
	 */
	public synchronized void stopAllSystems() throws XException {
		if (!mSystemsStarted) {
			return;
		}

		ReceiverThreadManager receiverThreadManager = ReceiverThreadManager
				.getInstance();
		/*
		 * Demand to stop all running systems
		 */
		String systemname;
		for (Iterator i = receiverThreadManager.getRunningReceiverThreads()
				.iterator(); i.hasNext();) {
			systemname = (String) i.next();
			receiverThreadManager.demandStopReceiverThread(systemname);
		}

		/*
		 * Wait until all systems have been stopped
		 */
		boolean completed = receiverThreadManager.getRunningReceiverThreads()
				.isEmpty();
		while (!completed) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			completed = receiverThreadManager.getRunningReceiverThreads()
					.isEmpty();
		}

		mSystemsStarted = false;
	}

	public boolean isWatchdogRunning() {
		return ((mWatchdogThread != null) && (mWatchdogThread.isAlive()));
	}

	/**
	 * TODO Kommentierung
	 */
	private void startWatchdog() {
		mWatchdog = new ReceiverServiceWatchdog();
		mWatchdogThread = new Thread(mWatchdog, "ReceiverServiceWatchdog");
		mWatchdogThread.start();
	}

	/**
	 * TODO Kommentierung
	 */
	private void stopWatchdog() {
		mWatchdog.stopWatchdog();
	}

}
