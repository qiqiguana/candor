package net.sf.xbus.admin.html;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.xbus.admin.Administrator;
import net.sf.xbus.base.core.Constants;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.trace.Trace;

/**
 * The <code>AdminDispatcherServlet</code> determines the Workflow. This class
 * causes the adjustment of the Models due to a user Requests. Its function
 * exists in the sense of a Controllers in the selection and the co-ordination
 * of the Worker Beans. It is responsible for the fact that all components are
 * initialized.
 */

public class AdminDispatcherServlet
{
	private static final String configurationPage = "/admin/jsp/ConfigurationPage.jsp";
	private static final String mRefreshPage = "/admin/jsp/ConfigurationRefresh.jsp";
	private static final String mReceiverServiceStatusViewPage = "/admin/jsp/ReceiverServiceStatusView.jsp";
	private static final String mReceiverThreadStopPage = "/admin/jsp/ReceiverThreadStop.jsp";
	private static final String mResultPage = "/admin/jsp/Result.jsp";
	private static final String mReceiverThreadStartPage = "/admin/jsp/ReceiverThreadStart.jsp";
	private static final String mResendDeletedMessagePage = "/admin/jsp/DeletedMessageResend.jsp";
	private static final String mJVMStatusViewPage = "/admin/jsp/JVMStatusView.jsp";
	private static final String mTraceViewPage = "/admin/jsp/TraceView.jsp";

	/**
	 * Forwards the HttpRequest to the appropriate jsp.
	 * 
	 * @param url URL from jsp
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @exception XException
	 */

	private void forwardTo(String url, HttpServlet servlet,
			HttpServletRequest request, HttpServletResponse response)
			throws XException
	{
		try
		{
			RequestDispatcher dispatcher = servlet.getServletContext()
					.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		}
		catch (IOException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_ADMIN, Constants.PACKAGE_ADMIN_ADMIN, "0",
					e);
		}
		catch (ServletException e)
		{
			throw new XException(Constants.LOCATION_INTERN,
					Constants.LAYER_ADMIN, Constants.PACKAGE_ADMIN_ADMIN, "0",
					e);
		}

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

	public void doPost(HttpServlet servlet, HttpServletRequest request,
			HttpServletResponse response)
	{

		try
		{

			String command = request.getParameter("parameter");
			HttpSession session = request.getSession(true);

			if (command != null)
			{
				if (command.equals("configuration"))
				{
					// creates configurationBean, sets the Session and forward
					// to the configurationPage.
					ConfigurationBean configurationBean = new ConfigurationBean();
					session.setAttribute("configuration", configurationBean);
					forwardTo(configurationPage, servlet, request, response);
				}
				else if (command.equals("refreshConfiguration"))
				{
					// creates or gets the administrationBean (dependent on this
					// presence
					// in the HttpSession), sets the property and forward to the
					// refreshPage.

					if (session.getAttribute("admin") == null)
					{
						AdministrationBean admin = new AdministrationBean();
						admin.restartReceiverService();
						session.setAttribute("admin", admin);
					}
					else
					{
						AdministrationBean admin = (AdministrationBean) session
								.getAttribute("admin");
						admin.restartReceiverService();
					}

					forwardTo(mRefreshPage, servlet, request, response);
				}
				else if (command.equals("viewReceiverServiceStatus"))
				{
					// creates or gets the administrationBean (dependent on this
					// presence
					// in the HttpSession), sets the property and forward to the
					// refreshPage.
					if (session.getAttribute("admin") == null)
					{
						AdministrationBean admin = new AdministrationBean();
					}
					else
					{
						AdministrationBean admin = (AdministrationBean) session
								.getAttribute("admin");
					}

					forwardTo(mReceiverServiceStatusViewPage, servlet, request,
							response);
				}
				else if (command.equals("stopReceiverThread"))
				{
					// creates or gets the administrationBean (dependent on this
					// presence
					// in the HttpSession), sets the property and forward to the
					// refreshPage.
					if (session.getAttribute("admin") == null)
					{
						AdministrationBean admin = new AdministrationBean();
					}
					else
					{
						AdministrationBean admin = (AdministrationBean) session
								.getAttribute("admin");
					}

					forwardTo(mReceiverThreadStopPage, servlet, request,
							response);
				}
				else if (command.equals("startReceiverThread"))
				{
					// creates or gets the administrationBean (dependent on this
					// presence
					// in the HttpSession), sets the property and forward to the
					// refreshPage.
					if (session.getAttribute("admin") == null)
					{
						AdministrationBean admin = new AdministrationBean();
					}
					else
					{
						AdministrationBean admin = (AdministrationBean) session
								.getAttribute("admin");
					}

					forwardTo(mReceiverThreadStartPage, servlet, request,
							response);
				}
				else if (command.equals("resendDeletedMessage"))
				{
					// creates or gets the administrationBean (dependent on this
					// presence
					// in the HttpSession), sets the property and forward to the
					// refreshPage.
					if (session.getAttribute("admin") == null)
					{
						AdministrationBean admin = new AdministrationBean();
					}
					else
					{
						AdministrationBean admin = (AdministrationBean) session
								.getAttribute("admin");
					}

					forwardTo(mResendDeletedMessagePage, servlet, request,
							response);
				}
				else if (command.equals("viewJVMStatus"))
				{
					// creates or gets the administrationBean (dependent on this
					// presence
					// in the HttpSession), sets the property and forward to the
					// refreshPage.
					if (session.getAttribute("admin") == null)
					{
						AdministrationBean admin = new AdministrationBean();
					}
					else
					{
						AdministrationBean admin = (AdministrationBean) session
								.getAttribute("admin");
					}

					forwardTo(mJVMStatusViewPage, servlet, request, response);
				}
				else if (command.equals("viewTrace"))
				{
					// creates or gets the administrationBean (dependent on this
					// presence
					// in the HttpSession), sets the property and forward to the
					// refreshPage.
					if (session.getAttribute("admin") == null)
					{
						AdministrationBean admin = new AdministrationBean();
					}
					else
					{
						AdministrationBean admin = (AdministrationBean) session
								.getAttribute("admin");
					}

					forwardTo(mTraceViewPage, servlet, request, response);
				}
			}
			else
			{
				String threadName = null;
				if ((threadName = request.getParameter("stopReceiverThread")) != null)
				{
					if (session.getAttribute("admin") == null)
					{
						AdministrationBean admin = new AdministrationBean();
						admin.setTitle("Stop ReceiverThread");
						admin.setResult(new Administrator()
								.demandStopBackgroundReceiver(threadName));
						session.setAttribute("admin", admin);
					}
					else
					{
						AdministrationBean admin = (AdministrationBean) session
								.getAttribute("admin");
						admin.setTitle("Stop ReceiverThread");
						admin.setResult(new Administrator()
								.demandStopBackgroundReceiver(threadName));
					}

					forwardTo(mResultPage, servlet, request, response);
				}
				else if ((threadName = request
						.getParameter("startReceiverThread")) != null)
				{
					if (session.getAttribute("admin") == null)
					{
						AdministrationBean admin = new AdministrationBean();
						admin.setTitle("Start ReceiverThread");
						admin.setResult(new Administrator()
								.startBackgroundReceiver(threadName));
						session.setAttribute("admin", admin);
					}
					else
					{
						AdministrationBean admin = (AdministrationBean) session
								.getAttribute("admin");
						admin.setTitle("Start ReceiverThread");
						admin.setResult(new Administrator()
								.startBackgroundReceiver(threadName));
					}

					forwardTo(mResultPage, servlet, request, response);
				}
				else if ((threadName = request.getParameter("resend")) != null)
				{
					if (session.getAttribute("admin") == null)
					{
						AdministrationBean admin = new AdministrationBean();
						admin.setTitle("Resend Deleted Message");
						admin.setResult(new Administrator()
								.resendDeletedMessage(threadName));
						session.setAttribute("admin", admin);
					}
					else
					{
						AdministrationBean admin = (AdministrationBean) session
								.getAttribute("admin");
						admin.setTitle("Resend Deletet Message");
						admin.setResult(new Administrator()
								.resendDeletedMessage(threadName));
					}

					forwardTo(mResultPage, servlet, request, response);
				}
			}
		}
		catch (Exception ex)
		{
			Trace.error(ex);

			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			// ex.printStackTrace();
		}

	}

}
