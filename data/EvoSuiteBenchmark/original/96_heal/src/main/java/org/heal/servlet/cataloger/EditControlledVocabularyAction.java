package org.heal.servlet.cataloger;

import org.heal.servlet.Action;
import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.TaxonBean;
import org.heal.module.metadata.TaxonPathBean;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.Iterator;

/**
 * An {@link Action} which is called to edit a Controlled Vocabulary item.  If
 * there isn't an existing Controlled Vocabulary item which can be found to edit,
 * we assume that a new Controlled Vocabulary entry should be edited.
 */
public class EditControlledVocabularyAction implements Action {
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

		TaxonBean taxonToEdit = new TaxonBean();
		TaxonPathBean taxonPathToEdit = new TaxonPathBean();

		if(null != selectedId && 0 < selectedId.length()) {
			for(Iterator iterOne = cmb.getTaxonPaths().iterator(); iterOne.hasNext();) {
				final TaxonPathBean tempTaxonPath = (TaxonPathBean)iterOne.next();
				for(Iterator iterTwo = tempTaxonPath.getTaxons().iterator(); iterTwo.hasNext();) {
					final TaxonBean tempTaxon = (TaxonBean)iterTwo.next();
					if(tempTaxon.getTaxonId().equals(selectedId)) {
						taxonToEdit = tempTaxon;
						taxonPathToEdit = tempTaxonPath;
					}
				}
			}
		}

		request.setAttribute("TaxonBean", taxonToEdit);
		request.setAttribute("TaxonPathBean", taxonPathToEdit);
		RequestDispatcher rd = request.getRequestDispatcher("/catalog/editControlledVocabulary.jsp");
		rd.forward(request, response);
	}

	/**
	 * @return <code>true</code>
	 */
	public boolean actionRequiresLogin() {
		return true;
	}
}
