package org.heal.servlet.cataloger;

import org.heal.servlet.Action;
import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.ContextURLBean;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.Iterator;

/**
 * An {@link Action} which is called to edit a Context URL.  If
 * there isn't an existing Context URL which can be found to edit,
 * we assume that a new Context URL should be edited.
 */
public class EditContextURLAction implements Action {
	public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// We skip the usual authentication-checking stuff since this
		// already should've been done by ModifyMetadataAction
		final CompleteMetadataBean cmb = (CompleteMetadataBean)request.getSession().getAttribute("metadata");
		final String selectedId = request.getParameter("selectedId");

		// This shouldn't be necessary, but it won't hurt to check
		if(null == cmb || null == selectedId) {
			response.sendRedirect("/error/accessDenied.jsp");
			return;
		}

		ContextURLBean cubToEdit = new ContextURLBean();
		for(Iterator iter = cmb.getContextURLs().iterator(); iter.hasNext();) {
			ContextURLBean testCub = (ContextURLBean)iter.next();
			if(testCub.getContextURLId().equals(selectedId)) {
				cubToEdit = testCub;
				break;
			}
		}
		request.setAttribute("ContextURLBean", cubToEdit);
		RequestDispatcher rd = request.getRequestDispatcher("/catalog/editContextURL.jsp");
		rd.forward(request, response);
	}

	/**
	 * @return <code>true</code>
	 */
	public boolean actionRequiresLogin() {
		return true;
	}
}
