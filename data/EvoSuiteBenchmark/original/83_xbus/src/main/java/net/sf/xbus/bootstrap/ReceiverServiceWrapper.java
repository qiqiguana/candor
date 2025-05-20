package net.sf.xbus.bootstrap;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.reflection.ReflectionSupport;
import net.sf.xbus.base.core.reflection.XBUSClassLoader;
import net.sf.xbus.base.core.trace.Trace;

import org.tanukisoftware.wrapper.WrapperListener;
import org.tanukisoftware.wrapper.WrapperManager;

/**
 * Starts the {@link net.sf.xbus.technical.ReceiverService} as a background
 * service, using the {@link net.sf.xbus.base.core.reflection.XBUSClassLoader}.
 * <p>
 * It works together with the Java Service Wrapper from Silveregg Technologies
 * to start it as a service in Windows NT, Linux or Solaris. This is an
 * open-source packages which can be found at SourceForge.
 */

public class ReceiverServiceWrapper implements WrapperListener
{
	/**
	 * Start the application. If the JVM was launched from the native Wrapper
	 * then the application will wait for the native Wrapper to call the
	 * application's start method. Otherwise the start method will be called
	 * immediately.
	 */
	public static void main(String[] args)
	{
		WrapperManager.start(new ReceiverServiceWrapper(), args);
	}

	/**
	 * Called when the WrapperManager is signaled by the native wrapper code
	 * that it can start its application. The
	 * {@link net.sf.xbus.technical.ReceiverService} will be started.
	 * 
	 * @param arg0 not used
	 * @return NULL if the <code>ReceiverService</code> have been started,
	 *         otherwise 1
	 */
	public Integer start(String[] arg0)
	{
		Thread.currentThread().setContextClassLoader(
				XBUSClassLoader.getInstance(Thread.currentThread()
						.getContextClassLoader()));

		try
		{
			Object obj = ReflectionSupport
					.createObject("net.sf.xbus.technical.ReceiverService");
			ReflectionSupport.callMethod("startWithoutServletEngine", obj,
					null, null);
		}
		catch (XException e)
		{
			return new Integer(1);
		}

		return null;
	}

	/**
	 * Called when the application is shutting down. The
	 * {@link net.sf.xbus.technical.ReceiverService} will be stopped.
	 * 
	 * @param arg0 not used
	 * @return the exit code to actually return to the OS<br>
	 *         (1 - if there are any problems during shutdown, otherwise 0).
	 */
	public int stop(int arg0)
	{
		Trace.always("Stopping ReceiverServiceWrapper...");

		try
		{
			ReceiverServiceAdministratorBootstrap.shutdown();
		}
		catch (XException e)
		{
			return 1;
		}

		Trace.always("ReceiverServiceWrapper stopped");
		return 0;
	}

	/**
	 * Called whenever the native wrapper code traps a system control signal
	 * against the Java process. All signals will be ignored.
	 */
	public void controlEvent(int arg0)
	{
		Trace.info("ReceiverServiceWrapper received controlEvent " + arg0);
	}
}
