package net.sf.xbus.bootstrap;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.reflection.ReflectionSupport;
import net.sf.xbus.base.core.reflection.XBUSClassLoader;
import net.sf.xbus.base.core.trace.Trace;

/**
 * <code>ReceiverServiceAdministratorBootstrap</code> stops or restarts the
 * {@link net.sf.xbus.technical.ReceiverService}. <br />
 * <br />
 * The {@link net.sf.xbus.base.core.reflection.XBUSClassLoader} is used to call
 * the {@link net.sf.xbus.technical.ReceiverServiceAdministrator}.
 */
public class ReceiverServiceAdministratorBootstrap
{
	static private final String SHUTDOWN = "SHUTDOWN";
	static private final String RESTART = "RESTART";

	/**
	 * Either stops (args = "SHUTDOWN") or restarts (args = "RESTART") the
	 * {@link net.sf.xbus.technical.ReceiverService}.
	 * 
	 * @param args an array containing exactly one string ("SHUTDOWN" or
	 *            "RESTART")
	 */
	public static void main(String[] args)
	{
		// ensure that a systems was given in args[]
		if (args.length != 1)
		{
			Trace.error("There must be exactly one parameter");
			System.exit(1);
		}

		try
		{
			if ((SHUTDOWN).equals(args[0].toUpperCase()))
			{
				shutdown();
			}
			else if ((RESTART).equals(args[0].toUpperCase()))
			{
				restart();
			}
			else
			{
				Trace.error("Parameter must be " + SHUTDOWN + " or " + RESTART);
				System.exit(1);
			}
		}
		catch (XException e)
		{
			System.exit(1);
		}

	}

	/**
	 * Stops the {@link net.sf.xbus.technical.ReceiverService}.
	 * 
	 * @throws XException if the <code>ReceiverService</code> cannot be
	 *             stopped.
	 */
	protected static void shutdown() throws XException
	{
		callMethod("net.sf.xbus.technical.ReceiverServiceAdministrator",
				"shutdown");
	}

	/**
	 * Restarts the {@link net.sf.xbus.technical.ReceiverService}.
	 * 
	 * @throws XException if the <code>ReceiverService</code> cannot be
	 *             restarted.
	 */
	protected static void restart() throws XException
	{
		callMethod("net.sf.xbus.technical.ReceiverServiceAdministrator",
				"restart");
	}

	/**
	 * Calls a method for the given class, using the
	 * {@link net.sf.xbus.base.core.reflection.XBUSClassLoader}.
	 * 
	 * @param className fully qualified name of the class
	 * @param methodName name of the method to be called
	 * @throws XException if the method cannot be called
	 */
	private static void callMethod(String className, String methodName)
			throws XException
	{
		Thread.currentThread().setContextClassLoader(
				XBUSClassLoader.getInstance(null));

		Object obj = ReflectionSupport.createObject(className);
		ReflectionSupport.callMethod(methodName, obj, null, null);
	}
}
