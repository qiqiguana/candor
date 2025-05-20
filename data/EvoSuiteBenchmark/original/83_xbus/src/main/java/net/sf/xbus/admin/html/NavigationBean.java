package net.sf.xbus.admin.html;

import java.util.Vector;

/**
 * The <code> NavigationBean </code> is critical for the compositions of
 * navigation menu.
 */
public class NavigationBean
{

	private String target;
	private static final String CONFIGURATION = "1";
	private static final String JOURNAL = "2";
	private static final String RECEIVER_SERVICE = "3";
	private static final String TRACE = "4";
	private Vector clickedNavigateVector = new Vector();
	private static final String openTagHref_navigate = "<p><a class=\"navigate\" href=\"NavigationPage.jsp";
	private static final String closeTagHref_config = "?target=1\" target=\"navigate\">&nbsp;&nbsp;Configuration</a></p>";
	private static final String closeTagHref_journal = "?target=2\" target=\"navigate\">&nbsp;&nbsp;Journal</a></p>";
	private static final String closeTagHref_receiverService = "?target=3\" target=\"navigate\">&nbsp;&nbsp;ReceiverService</a></p>";
	private static final String closeTagHref_trace = "?target=4\" target=\"navigate\">&nbsp;&nbsp;Trace</a></p>";
	private static final String openTagHref_dispatcher = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class=\"journNavigate\" href=\"../../admin/servlet/dispatcherServlet";
	private static final String closeTagHref_view = "?parameter=configuration&chapterMenu=leer&sectionMenu=leer\" target=\"haupt\">---> View</a>";
	private static final String closeTagHref_refresh = "?parameter=refreshConfiguration\" target=\"haupt\">---> Refresh</a>";
	private String openTagHref_select = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a class=\"journNavigate\" href=\"SelectPage.jsp\"";
	private static final String closeHref_select = "target=\"haupt\">---> Selection</a><br>";
	private String closeHref_view_ReceiverServiceStatus = "?parameter=viewReceiverServiceStatus\" target=\"haupt\">---> Status</a>";
	private String closeHref_view_StopReceiverThread = "?parameter=stopReceiverThread\" target=\"haupt\">---> Stop Receiver</a>";
	private String closeHref_view_StartReceiverThread = "?parameter=startReceiverThread\" target=\"haupt\">---> Start Receiver</a>";
	private String closeHref_view_ResendDeletedMessage = "?parameter=resendDeletedMessage\" target=\"haupt\">---> Resend Message</a>";
	private String closeHref_view_JVMStatus = "?parameter=viewJVMStatus\" target=\"haupt\">---> JVM</a>";
	private String closeHref_view_trace = "?parameter=viewTrace\" target=\"haupt\">---> View</a>";
	private static final String tagBr = "<br />";

	/**
	 * Allows you to set the property <b><i>target</i></b> and transfers this
	 * new selected command to the <code>checkNavigateVector(String)</code>
	 * for comparing with the presence and stores only open lines from the menu.
	 * 
	 * @param newTarget - String with the selected command.
	 */

	public void setTarget(String newTarget)
	{
		this.target = newTarget;

		if (this.target.equals(CONFIGURATION))
		{
			checkNavigateVector(CONFIGURATION);
		}
		else if (this.target.equals(JOURNAL))
		{
			checkNavigateVector(JOURNAL);
		}
		else if (this.target.equals(RECEIVER_SERVICE))
		{
			checkNavigateVector(RECEIVER_SERVICE);
		}
		else if (this.target.equals(TRACE))
		{
			checkNavigateVector(TRACE);
		}
	}

	/**
	 * Depending on the presence in <code>clickedNavigateVector</code>,
	 * <code>getNavigationAsTableRows()</code> composes and returnes String
	 * with the currently menu to the <code>NavigationPage</code>.
	 * 
	 * @return String - currently menu.
	 */

	public String getNavigationAsTableRows()
	{
		StringBuffer navigateBuffer = new StringBuffer();

		navigateBuffer.append(NavigationBean.openTagHref_navigate);
		navigateBuffer.append(NavigationBean.closeTagHref_config);
		if (this.clickedNavigateVector.contains(CONFIGURATION))
		{
			navigateBuffer.append(NavigationBean.openTagHref_dispatcher);
			navigateBuffer.append(NavigationBean.closeTagHref_view);
			navigateBuffer.append(NavigationBean.tagBr);
			navigateBuffer.append(NavigationBean.openTagHref_dispatcher);
			navigateBuffer.append(NavigationBean.closeTagHref_refresh);
		}

		navigateBuffer.append(NavigationBean.tagBr);
		navigateBuffer.append(NavigationBean.tagBr);

		navigateBuffer.append(NavigationBean.openTagHref_navigate);
		navigateBuffer.append(NavigationBean.closeTagHref_journal);
		if (this.clickedNavigateVector.contains(JOURNAL))
		{
			navigateBuffer.append(this.openTagHref_select);
			navigateBuffer.append(NavigationBean.closeHref_select);
		}

		navigateBuffer.append(NavigationBean.tagBr);
		navigateBuffer.append(NavigationBean.tagBr);

		navigateBuffer.append(NavigationBean.openTagHref_navigate);
		navigateBuffer.append(NavigationBean.closeTagHref_receiverService);
		if (this.clickedNavigateVector.contains(RECEIVER_SERVICE))
		{
			navigateBuffer.append(NavigationBean.openTagHref_dispatcher);
			navigateBuffer.append(this.closeHref_view_ReceiverServiceStatus);
			navigateBuffer.append(NavigationBean.tagBr);
			navigateBuffer.append(NavigationBean.openTagHref_dispatcher);
			navigateBuffer.append(this.closeHref_view_StopReceiverThread);
			navigateBuffer.append(NavigationBean.tagBr);
			navigateBuffer.append(NavigationBean.openTagHref_dispatcher);
			navigateBuffer.append(this.closeHref_view_StartReceiverThread);
			navigateBuffer.append(NavigationBean.tagBr);
			navigateBuffer.append(NavigationBean.openTagHref_dispatcher);
			navigateBuffer.append(this.closeHref_view_ResendDeletedMessage);
			navigateBuffer.append(NavigationBean.tagBr);
			navigateBuffer.append(NavigationBean.openTagHref_dispatcher);
			navigateBuffer.append(this.closeHref_view_JVMStatus);
		}

		navigateBuffer.append(NavigationBean.tagBr);
		navigateBuffer.append(NavigationBean.tagBr);

		navigateBuffer.append(NavigationBean.openTagHref_navigate);
		navigateBuffer.append(NavigationBean.closeTagHref_trace);
		if (this.clickedNavigateVector.contains(TRACE))
		{
			navigateBuffer.append(NavigationBean.openTagHref_dispatcher);
			navigateBuffer.append(this.closeHref_view_trace);

		}

		return navigateBuffer.toString();

	}

	/**
	 * Comparing with the presence, <code>checkNavigateVector(String)</code>
	 * stores only open lines from the menu.
	 * 
	 * @param newTarget - final static variable.
	 */
	private void checkNavigateVector(String newTarget)
	{

		if (this.clickedNavigateVector.contains(newTarget))
		{
			this.clickedNavigateVector.remove(newTarget);
		}
		else
		{
			this.clickedNavigateVector.add(newTarget);
		}
	}

}
