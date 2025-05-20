package net.sf.xbus.bootstrap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.reflection.ReflectionSupport;
import net.sf.xbus.base.core.reflection.XBUSClassLoader;

/**
 * The <code>AdminDispatcherServlet</code> determines the Workflow. This class
 * causes the adjustment of the Models due to a user Requests. Its function
 * exists in the sense of a Controllers in the selection and the co-ordination
 * of the Worker Beans. It is responsible for the fact that all components are
 * initialized.
 */
public class AdminDispatcherServlet extends HttpServlet
{

	/**
	 * Forwards the HttpRequest to the appropriate jsp.
	 * 
	 * @param url URL from jsp
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @exception XException
	 */

	@SuppressWarnings("unused")
	private void forwardTo(String url, HttpServletRequest request,
			HttpServletResponse response)
	{
		ClassLoader currentCL = Thread.currentThread().getContextClassLoader();

		Thread.currentThread().setContextClassLoader(
				XBUSClassLoader.getInstance(currentCL));

		try
		{
			Object obj = ReflectionSupport
					.createObject("net.sf.xbus.admin.html.AdminDispatcherServlet");
			Class[] parameterTypes = new Class[]
			{String.class, HttpServlet.class, HttpServletRequest.class,
					HttpServletResponse.class};
			Object[] arguments = new Object[]
			{url, this, request, response};
			ReflectionSupport.callMethod("forwardTo", obj, parameterTypes,
					arguments);
		}
		catch (XException e)
		{
			// has already been processed
		}
	}

	/**
	 * <code>doGet</code> is called in response to an HTTP GET request. For
	 * supporting both POST and GET protocol from the same servlet,
	 * <code>doGet</code> method calls <code>doPost()</code> method which
	 * will be supported.
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @see #doPost(HttpServletRequest, HttpServletResponse)
	 */

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{

		doPost(request, response);

	}

	/**
	 * <code>doPost</code> is called in response to an HTTP POST request.
	 * Depending on the parameter of the HttpServletRequest sets
	 * <code>doPost()</code> the necessary data in form of the
	 * <code>ConfigurationBean</code> or <code>AdministrationBean</code> to
	 * the HttpSesson and resends the HttpRequest by means of the
	 * <code>forwardTo()</code> method to the appropriate jsp.
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 */

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		ClassLoader currentCL = Thread.currentThread().getContextClassLoader();

		Thread.currentThread().setContextClassLoader(
				XBUSClassLoader.getInstance(currentCL));

		try
		{
			Object obj = ReflectionSupport
					.createObject("net.sf.xbus.admin.html.AdminDispatcherServlet");
			Class[] parameterTypes = new Class[]
			{HttpServlet.class, HttpServletRequest.class,
					HttpServletResponse.class};
			Object[] arguments = new Object[]
			{this, request, response};
			ReflectionSupport.callMethod("doPost", obj, parameterTypes,
					arguments);
		}
		catch (XException e)
		{
			// has already been processed
		}
	}

}
