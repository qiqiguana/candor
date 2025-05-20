package org.heal.servlet.cataloger;

import org.heal.servlet.Action;
import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.ContextURLBean;
import org.heal.module.metadata.RequirementBean;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.Iterator;

/**
 * An {@link Action} which is called to edit a Requirement.  If
 * there isn't an existing Requirement which can be found to edit,
 * we assume that a new Requirement should be edited.
 */
public class EditRequirementAction implements Action {
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

		RequirementBean requirementToEdit = new RequirementBean();
		for(Iterator iter = cmb.getRequirements().iterator(); iter.hasNext();) {
			RequirementBean rb = (RequirementBean)iter.next();
			if(selectedId.equals(rb.getRequirementId())) {
				requirementToEdit = rb;
				break;
			}
		}
		request.setAttribute("RequirementBean", requirementToEdit);

		RequestDispatcher rd = request.getRequestDispatcher("/catalog/editRequirement.jsp");
		rd.forward(request, response);
	}

	public boolean actionRequiresLogin() {
		return true;
	}
}
