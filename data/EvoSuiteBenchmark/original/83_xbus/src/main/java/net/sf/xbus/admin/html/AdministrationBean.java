package net.sf.xbus.admin.html;

import net.sf.xbus.admin.Administrator;
import net.sf.xbus.base.core.XException;
import net.sf.xbus.base.core.trace.Trace;

/**
 * Administration for xBus servers. Currently there are the servlet engine and
 * the {@link net.sf.xbus.technical.ReceiverService}.
 */
public class AdministrationBean
{
	private String mRefreshServerReceiver = null;
	private String mTitle = null;
	private String mResult = null;

	/**
	 * Refreshes the configuration.
	 * 
	 * @throws XException if something goes wrong
	 */
	public void restartReceiverService()
	{
		mRefreshServerReceiver = new Administrator().restartReceiverService();
	}

	/**
	 * Returns a string for the JSP, which indicates, wether the configuration
	 * has been refreshed or not.
	 */
	public String getRefreshMQ()
	{
		return mRefreshServerReceiver;
	}

	/**
	 * Returns the content of the tracing.
	 * 
	 * @see net.sf.xbus.base.core.trace.Trace
	 * @throws XException if something goes wrong
	 */
	public String getTrace()
	{
		return Trace.getTrace();
	}

	/**
	 * Returns the status of the ReceiverThreads.
	 */
	public String getReceiverServiceStatus()
	{
		return new Administrator().getReceiverServiceStatus();
	}

	/**
	 * Returns the status of the JVM.
	 */
	public String getJvmStatus()
	{
		return new Administrator().getJVMStatus();
	}

	public String getResult()
	{
		return mResult;
	}
	public void setResult(String result)
	{
		mResult = result;
	}
	public String getTitle()
	{
		return mTitle;
	}
	public void setTitle(String title)
	{
		mTitle = title;
	}
}
