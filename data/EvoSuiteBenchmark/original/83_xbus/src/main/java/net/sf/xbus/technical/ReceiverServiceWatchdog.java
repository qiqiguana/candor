package net.sf.xbus.technical;

import java.util.Iterator;
import java.util.Set;

import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.config.Configuration;
import net.sf.xbus.base.core.trace.Trace;
import net.sf.xbus.base.notifyerror.NotifyError;
import net.sf.xbus.base.xbussystem.XBUSSystem;

/**
 * The <code>ReceiverServiceWatchdog</code> checks in a configurable interval,
 * whether all started {@link net.sf.xbus.technical.ReceiverThreadBase}s are
 * still running. If a thread is not running anymore and it has not been stopped
 * on demand, it is started again.
 */
public class ReceiverServiceWatchdog implements Runnable
{
	private static final int INTERVAL_DEFAULT = 5;
	private boolean mRunning = true;

	/**
	 * Checks in a configurable interval, whether all started
	 * {@link net.sf.xbus.technical.ReceiverThreadBase}s are still running. If
	 * a thread is not running anymore and it has not been stopped on demand, it
	 * is started again.
	 */
	public void run()
	{
		/*
		 * Read the interval time between the checks out of the configuration.
		 * If not specified, a default is used.
		 */
		int intervalTime = 0;
		try
		{
			intervalTime = Configuration.getInstance().getValueAsIntOptional(
					Constants.CHAPTER_BASE, "Watchdog", "Interval");
		}
		catch (Exception e)
		{
			// Explicitly do nothing, default will be set later.
		}

		if (intervalTime == 0)
		{
			Trace.warn("Watchdog running with default interval "
					+ INTERVAL_DEFAULT + " seconds");
			intervalTime = INTERVAL_DEFAULT;
		}

		while (mRunning)
		{
			/*
			 * Check whether all started threads are still running. If a thread
			 * is not running anymore, throw it out of the list of started
			 * systems and start a new instance of this thread.
			 */
			ReceiverThreadManager manager = ReceiverThreadManager.getInstance();
			Set runningThreads = manager.getRunningReceiverThreads();
			Set allThreads = manager.getAllReceiverThreads();
			Set stoppedThreads = manager.getStoppedSystems();

			String system = null;
			for (Iterator it1 = allThreads.iterator(); it1.hasNext();)
			{
				system = (String) it1.next();
				if ((!runningThreads.contains(system))
						&& (!stoppedThreads.contains(system)))
				{
					Trace.always("ReceiverThread " + system
							+ " will be restarted");
					try
					{
						manager.startReceiverThread(system);
					}
					catch (XException e)
					{
						Trace.always("Error wile restarting ReceiverThread "
								+ system);
						try
						{
							NotifyError.notifyError(null,
									new XBUSSystem(system), e.getMessage(),
									null, null);
						}
						catch (XException e1)
						{
							// do nothing
						}
					}
				}
			}

			try
			{
				Thread.sleep(intervalTime * 1000);
			}
			catch (InterruptedException e)
			{
				// explicitly do nothing
			}
		}

	}

	/**
	 * Tells the watchdog that it should stop.
	 */
	public void stopWatchdog()
	{
		mRunning = false;
	}
}
