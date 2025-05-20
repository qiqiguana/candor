package net.sf.xbus.bootstrap;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.reflection.ReflectionSupport;
import net.sf.xbus.base.core.reflection.XBUSClassLoader;

/**
 * <code>JavaReceiverBootstrap</code> is used to call the xBus directly from
 * another Java application. It receives a request via a call of its
 * <code>receive</code> method.
 */
public class JavaReceiverBootstrap
{
	/**
	 * Receives a request, processes the message as configured in
	 * <code>standard.conf</code> and returns a response.
	 * 
	 * @param system name of the system as configured in
	 *            <code>standard.conf</code>
	 * @param request the message that shall be processed by the xBus
	 * @return the outcome of processing the request
	 * @throws XException if the request cannot be processed
	 */
	public static Object receive(String system, Object request)
			throws XException
	{
		Object responseObject = null;

		ClassLoader tmpCL = Thread.currentThread().getContextClassLoader();

		Thread.currentThread().setContextClassLoader(
				XBUSClassLoader.getInstance(tmpCL));

		try
		{
			Object obj = ReflectionSupport
					.createObject("net.sf.xbus.technical.java.JavaReceiver");

			Class[] parameterTypes = new Class[]
			{String.class, Object.class};
			Object[] arguments = new Object[]
			{system, request};

			responseObject = ReflectionSupport.callMethod("receive", obj,
					parameterTypes, arguments);
		}
		finally
		{
			Thread.currentThread().setContextClassLoader(tmpCL);
		}

		return responseObject;
	}
}
