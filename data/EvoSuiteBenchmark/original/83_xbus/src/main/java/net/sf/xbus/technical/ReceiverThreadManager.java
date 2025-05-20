package net.sf.xbus.technical;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.reflection.XBUSClassLoader;
import net.sf.xbus.base.core.trace.Trace;

/**
 * Manages the state of all receivers running in the background. This class
 * implements the Singleton design pattern.
 */
public class ReceiverThreadManager {
	/**
	 * Default for the amount of errors, after them a receiver will be stopped.
	 */
	static protected final int DEFAULT_STOP_AFTER_ERRORS = -1; // times

	private static ReceiverThreadManager mReceiverThreadManager = null;
	private static final Object classLock = ReceiverThreadManager.class;

	/**
	 * Contains all ReceiverThreads that has been started by the
	 * ReceiverService. Key: name of the system Entry: the ReceiverServiceThread
	 * object
	 */
	private SortedMap mAllThreads = Collections
			.synchronizedSortedMap(new TreeMap());
	private SortedSet mStoppedThreads = Collections
			.synchronizedSortedSet(new TreeSet());
	private SortedSet mStoppedHTTPReceivers = Collections
			.synchronizedSortedSet(new TreeSet());
	private boolean mIsServletEngine = false;

	/**
	 * All threads for the systems will run in one ThreadGroup. This makes
	 * administration easier.
	 */
	private ThreadGroup mThreadGroup = new ThreadGroup("ReceiverService");

	/**
	 * The default constructor is private because this is a singleton
	 */
	private ReceiverThreadManager() {
	}

	/**
	 * Returns the one and only object of class
	 * <code>ReceiverThreadManager</code>.
	 * 
	 * @return the one and only object of class
	 *         <code>ReceiverThreadManager</code>
	 */
	static public ReceiverThreadManager getInstance() {
		synchronized (classLock) {
			if (mReceiverThreadManager == null) {
				mReceiverThreadManager = new ReceiverThreadManager();
			}
			return mReceiverThreadManager;
		}
	}

	/**
	 * Starts a ReceiverThread for the given system.
	 * 
	 * @param system
	 *            the name of the system to start the receiver for
	 * @throws XException
	 *             if something goes wrong
	 */
	public void startReceiverThread(String system) throws XException {
		/*
		 * Check if System is configured with ReceiverThread
		 */
		String receiver = Configuration.getInstance().getValueOptional(
				Constants.CHAPTER_SYSTEM, system, "Receiver");
		if ((receiver != null) && (receiver.endsWith("Thread"))) {
			/*
			 * Check if ReceiverThread isn't already running
			 */
			if (getRunningReceiverThreads().contains(system)) {
				Vector params = new Vector(1);
				params.add(system);
				throw new XException(Constants.LOCATION_INTERN,
						Constants.LAYER_TECHNICAL,
						Constants.PACKAGE_TECHNICAL_TECHNICAL, "7", params);
			}

			/*
			 * Start receiver
			 */
			ReceiverThreadBase receiverThreadImpl = ReceiverFactory
					.createReceiverThread(receiver, system);

			Thread receiverThread = new Thread(mThreadGroup,
					receiverThreadImpl, system);
			receiverThread.setDaemon(false);
			receiverThread
					.setContextClassLoader(XBUSClassLoader.getInstance(Thread
							.currentThread().getContextClassLoader()));
			receiverThread.start();

			/*
			 * Put system and thread in list of all systems and remove it from
			 * stoppedThreads
			 */
			mAllThreads.put(system, receiverThreadImpl);
			mStoppedThreads.remove(system);
		} else if (isHTTPReceiver(system)) {
			if (mStoppedHTTPReceivers.contains(system)) {
				mStoppedHTTPReceivers.remove(system);
				Trace.always("Starting HTTPReceiver for " + system);
			}
		} else {
			Vector params = new Vector(1);
			params.add(system);
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_TECHNICAL,
					Constants.PACKAGE_TECHNICAL_TECHNICAL, "6", params);
		}

	}

	/**
	 * Demands the stop of the ReceiverThread for the given <code>system</code>.
	 * 
	 * @param system
	 *            the name of the system to stop the receiver for
	 * @throws XException
	 *             if something goes wrong
	 */
	public void demandStopReceiverThread(String system) throws XException {
		Trace.always("Demanding stop for " + system);

		if (getRunningReceiverThreads().contains(system)) {

			ReceiverThreadBase startedThread = (ReceiverThreadBase) mAllThreads
					.get(system);

			if (startedThread == null) {
				Trace.warn(system + " is not a started receiver");
				return;
			}

			startedThread.interruptThread();
			mStoppedThreads.add(system);
		} else if (getRunningHTTPReceivers().contains(system)) {
			mStoppedHTTPReceivers.add(system);
			Trace.always("HTTPReceiver for " + system + " stopped");
		} else {
			Trace.warn(system + " is not a started receiver");
			return;
		}
	}

	/**
	 * Returns a set with the names of all ReceiverThreads found in the
	 * <code>Configuration</code>.
	 * 
	 * @return the names of all ReceiverThreads found in the
	 *         <code>Configuration</code>
	 */
	public Set getAllReceiverThreads() {
		Set returnSet = new TreeSet();
		returnSet.addAll(mAllThreads.keySet());
		return returnSet;
	}

	/**
	 * Returns a set with the names of all background receivers (ReceiverThreads
	 * and HTTPReceivers) found in the <code>Configuration</code>.
	 * 
	 * @return the names of all background receivers found in the
	 *         <code>Configuration</code>
	 * @throws XException
	 *             if something goes wrong
	 */
	public Set getAllSystems() throws XException {
		Set returnSet = getAllReceiverThreads();
		returnSet.addAll(getAllHTTPReceivers());
		return returnSet;
	}

	/**
	 * Returns a set with the names of all running background receivers
	 * (ReceiverThreads and HTTPReceivers).
	 * 
	 * @return the names of all running background receivers
	 * @throws XException
	 *             if something goes wrong
	 */
	public Set getRunningSystems() throws XException {
		Set retSet = getRunningReceiverThreads();
		retSet.addAll(getRunningHTTPReceivers());
		return retSet;
	}

	/**
	 * Returns a set with the names of all running ReceiverThreads.
	 * 
	 * @return the names of all running ReceiverThreads
	 */
	public Set getRunningReceiverThreads() {
		int threadCount = mThreadGroup.activeCount();
		TreeSet retSet = new TreeSet();

		Thread[] startedThreads = new Thread[threadCount];
		mThreadGroup.enumerate(startedThreads);

		for (int i = 0; i < threadCount; i++) {
			if ((startedThreads[i] != null)
					&& (mAllThreads.containsKey(startedThreads[i].getName()))) {
				retSet.add(startedThreads[i].getName());
			}
		}
		return retSet;
	}

	/**
	 * Returns a set with the names of all running HTTPReceivers.
	 * 
	 * @return the names of all running HTTPReceivers
	 * @throws XException
	 *             if something goes wrong
	 */
	protected Set getRunningHTTPReceivers() throws XException {
		TreeSet returnSet = new TreeSet();

		for (Iterator it = getAllHTTPReceivers().iterator(); it.hasNext();) {
			Object stoppedHTTPReceiver = it.next();
			if (!mStoppedHTTPReceivers.contains(stoppedHTTPReceiver)) {
				returnSet.add(stoppedHTTPReceiver);
			}
		}

		return returnSet;
	}

	/**
	 * Returns a set with the names of all stopped background receivers
	 * (ReceiverThreads and HTTPReceivers).
	 * 
	 * @return the names of all stopped background receivers
	 */
	public Set getStoppedSystems() {
		TreeSet returnSet = new TreeSet();

		returnSet.addAll(mStoppedThreads);
		returnSet.addAll(mStoppedHTTPReceivers);

		return returnSet;
	}

	/**
	 * Indicates whether the ReceiverService is started inside a servlet engine.
	 * 
	 * @param isServletEngine
	 *            true if the ReceiverService is running in a servlet engine
	 */
	protected void setIsServletEngine(boolean isServletEngine) {
		mIsServletEngine = isServletEngine;
	}

	/**
	 * Clears the list of all stopped HTTPReceivers.
	 */
	public void clearStoppedHTTPReceivers() {
		mStoppedHTTPReceivers.clear();
	}

	/**
	 * Returns a list of all HTTPReceivers found in the
	 * <code>Configuration</code>.
	 * 
	 * @return a list of all HTTPReceivers found in the
	 *         <code>Configuration</code>
	 * @throws XException
	 *             if something goes wrong
	 */
	private List getAllHTTPReceivers() throws XException {
		List returnSet = new Vector();
		if (mIsServletEngine) {
			List systems;
			Configuration config;
			try {
				config = Configuration.getInstance();
				systems = config.getSections(Constants.CHAPTER_SYSTEM);
			} catch (XException e) {
				return null;
			}

			String system = null;
			for (Iterator it = systems.iterator(); it.hasNext();) {
				system = (String) it.next();
				if (isHTTPReceiver(system)) {
					returnSet.add(system);
				}
			}
		}
		return returnSet;
	}

	private boolean isHTTPReceiver(String system) throws XException {
		String receiver = Configuration.getInstance().getValueOptional(
				Constants.CHAPTER_SYSTEM, system, "Receiver");
		if ((receiver != null) && (receiver.startsWith("HTTP"))) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Indicates whether a HTTPReceiver is stopped
	 * 
	 * @param system
	 *            the name of the system
	 * @return true if the HTTPReceiver is stopped, false otherwise
	 */
	public boolean isHTTPReceiverStopped(String system) {
		return mStoppedHTTPReceivers.contains(system);
	}

	/**
	 * Returns the amount of errors, after them a receiver will be stopped The
	 * value is read out of the configuration, either a default for the receiver
	 * class or a value specific for the current source. If no value is
	 * specified in the configuration, a hard coded default is used.
	 * 
	 * @return the amount of errors, after them a receiver will be stopped
	 * @param system
	 *            the name of the system
	 * @param receiverClassName
	 *            the name of the receiver class
	 */
	public static int getStopAfterErrors(String system, String receiverClassName) {
		int stopAfterErrors = DEFAULT_STOP_AFTER_ERRORS;
		try {
			Configuration config = Configuration.getInstance();
			stopAfterErrors = config.getValueAsIntOptional("System", system,
					"StopAfterErrors");
			if (stopAfterErrors == 0) {
				stopAfterErrors = config.getValueAsInt("Base",
						receiverClassName, "StopAfterErrors");
			}
		} catch (XException e) {
			Trace.warn("Using default for StopAfterErrors: "
					+ DEFAULT_STOP_AFTER_ERRORS);
		}

		return stopAfterErrors;
	}
}
