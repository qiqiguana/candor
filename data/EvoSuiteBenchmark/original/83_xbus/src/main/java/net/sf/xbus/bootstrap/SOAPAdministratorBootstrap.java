package net.sf.xbus.bootstrap;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.reflection.ReflectionSupport;
import net.sf.xbus.base.core.reflection.XBUSClassLoader;
import net.sf.xbus.base.core.trace.Trace;

/**
 * Restarts the xBus by calling a web service.
 */
public class SOAPAdministratorBootstrap
{
	/**
	 * Starts the client program to send a SOAP request to the xBus to restart.
	 * 
	 * @param args host and port of the servlet engine
	 */
	public static void main(String[] args)
	{
		if (args.length != 2)
		{
			System.out
					.println("There must be exactly 2 parameters: host and port");
			System.exit(1);
		}
		try
		{

			String host = args[0];
			Integer port = new Integer(Integer.parseInt(args[1]));

			Thread.currentThread().setContextClassLoader(
					XBUSClassLoader.getInstance(Thread.currentThread()
							.getContextClassLoader()));

			Object obj = ReflectionSupport
					.createObject("net.sf.xbus.admin.soap.SOAPAdministrator");

			Class[] parameterTypes = new Class[]
			{String.class, Integer.class};
			Object[] arguments = new Object[]
			{host, port};

			String message = (String) ReflectionSupport.callMethod("restart",
					obj, parameterTypes, arguments);
			Trace.always(message);
		}
		catch (Exception e)
		{
			Trace.error(e);
			System.exit(1);
		}
	}

	/**
	 * Called by the web service to restart the xBus.
	 * 
	 * @return a message of success or failure
	 */
	public String restart()
	{
		ClassLoader currentCL = Thread.currentThread().getContextClassLoader();

		Thread.currentThread().setContextClassLoader(
				XBUSClassLoader.getInstance(currentCL));

		String retString = null;

		try
		{
			Object obj = ReflectionSupport
					.createObject("net.sf.xbus.admin.Administrator");
			retString = (String) ReflectionSupport.callMethod(
					"restartReceiverService", obj, null, null);
		}
		catch (XException e)
		{
			retString = e.getMessage();
		}

		return retString;
	}
}
