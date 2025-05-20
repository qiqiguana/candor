package net.sf.xbus.bootstrap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.reflection.ReflectionSupport;
import net.sf.xbus.base.core.reflection.XBUSClassLoader;

/**
 * <code>HTTPReceiverBootstrap</code> starts the
 * {@link net.sf.xbus.technical.http.HTTPReceiver}, using the
 * {@link net.sf.xbus.base.core.reflection.XBUSClassLoader}.
 */
public class HTTPReceiverBootstrap extends HttpServlet
{
	/**
	 * Handles the HTTP-request. In case of an error while processing the
	 * messages, it returns a
	 * <code>HttpServletResponse.SC_INTERNAL_SERVER_ERROR</code>.
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res)
	{
		ClassLoader currentCL = Thread.currentThread().getContextClassLoader();

		Thread.currentThread().setContextClassLoader(
				XBUSClassLoader.getInstance(currentCL));

		try
		{
			Object obj = ReflectionSupport
					.createObject("net.sf.xbus.technical.http.HTTPReceiver");
			Class[] parameterTypes = new Class[]
			{HttpServletRequest.class, HttpServletResponse.class};
			Object[] arguments = new Object[]
			{req, res};
			ReflectionSupport.callMethod("doPost", obj, parameterTypes,
					arguments);
		}
		catch (XException e)
		{
			// has already been processed
		}
	}
	public void doGet(HttpServletRequest req, HttpServletResponse res)
	{
		doPost(req, res);
	}
}
