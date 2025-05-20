package org.heal.util;

import org.heal.module.user.UserBean;

import javax.servlet.http.HttpServletRequest;

/**
 * A convenience class which holds some common methods used for authentication.
 */
public final class AuthenticationTools {
	/**
	 * A private constructor to prevent an instance of this class to be created.
	 */
	private AuthenticationTools() { }

	/**
	 * @return <code>true</code> if the user given in the <code>HttpServletRequest</code>
	 *         is a valid cataloger, <code>false</code> otherwise.
	 */
	public static boolean isCataloger(HttpServletRequest request) {
		UserBean user = getUser(request);
        return (null != user && user.isCataloger());
	}

	/**
	 * @return <code>true</code> if the user given in the <code>HttpServletRequest</code>
	 * is a valid approver, <code>false</code> otherwise.
	 */
	public static boolean isApprover(HttpServletRequest request) {
		UserBean user = getUser(request);
		return (null != user && user.isApprover());
	}
  
	/**
	 * @return <code>true</code> if the user given in the <code>HttpServletRequest</code>
	 * is a valid administrator, <code>false</code> otherwise.
	 */
	public static boolean isAdministrator(HttpServletRequest request) {
		UserBean user = getUser(request);
		return (null != user && user.isAdministrator());
	}  
  
	/**
	 * @param request The <code>HttpServletRequest</code> that this method attempts
	 * 		to get the {@link UserBean} from.
	 * @return The {@link UserBean} attribute from the <code>HttpServletRequest</code>
	 * 		or <code>null</code> if none exists.
	 */
	private static UserBean getUser(HttpServletRequest request) {
		return (UserBean)request.getSession().getAttribute("validUser");
	}
}
