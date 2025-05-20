package net.sf.xbus.bootstrap;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.reflection.ReflectionSupport;
import net.sf.xbus.base.core.reflection.XBUSClassLoader;

/**
 * <code>ReceiverServiceBootstrap</code> starts the
 * {@link net.sf.xbus.technical.ReceiverService}, using the
 * {@link net.sf.xbus.base.core.reflection.XBUSClassLoader}.
 */
public class ReceiverServiceBootstrap
{
	private static final String mReceiverServiceClassName = "net.sf.xbus.technical.ReceiverService";

	/**
	 * Starts the {@link net.sf.xbus.technical.ReceiverService} in a way, that
	 * it is stoppable by the
	 * {@link net.sf.xbus.technical.ReceiverServiceAdministrator}
	 * 
	 * @param args not used
	 */
	public static void main(String[] args)
	{
		callReceiverServiceMethod("startWithoutServletEngine", null);
	}

	public static void start(boolean insideServletEngine)
	{
		if (insideServletEngine)
		{
			callReceiverServiceMethod("startInsideServletEngine", Thread
					.currentThread().getContextClassLoader());
		}
		else
		{
			callReceiverServiceMethod("startWithoutServletEngine", Thread
					.currentThread().getContextClassLoader());
		}
	}

	public static Object getInstance()
	{
		return callReceiverServiceMethod("getInstance", null);
	}

	private static Object callReceiverServiceMethod(String methodName,
			ClassLoader parent)
	{
		Thread.currentThread().setContextClassLoader(
				XBUSClassLoader.getInstance(parent));

		Object retObject = null;

		try
		{
			Object obj = ReflectionSupport
					.createObject(mReceiverServiceClassName);
			retObject = ReflectionSupport.callMethod(methodName, obj, null,
					null);
		}
		catch (XException e)
		{
			System.exit(1);
		}

		return retObject;
	}
}
