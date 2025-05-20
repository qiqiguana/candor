package org.heal.servlet.cataloger;

import org.heal.servlet.Action;
import org.heal.util.AuthenticationTools;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;

/**
 * A simple {@link Action} which clears the session variable used
 * for editing metadata.
 */
public class CancelEditMetadataAction implements Action {
	public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(!AuthenticationTools.isCataloger(request)) {
			// The user does not have the access to view this page
			// so we go no further, and redirect them to an access denied page
			// TODO instead of hardcoding page locations, this should be in config files somewhere
			response.sendRedirect("/error/accessDenied.jsp");
			return;
		}

		HttpSession session = request.getSession(false);
		if(null != session) {
			session.removeAttribute("metadata");
		}

		RequestDispatcher rd = request.getRequestDispatcher("/catalog/editCancelled.jsp");
		rd.forward(request, response);
	}

	/**
	 * @return <code>false</code>
	 */
	public boolean actionRequiresLogin() {
		return false;
	}
}
