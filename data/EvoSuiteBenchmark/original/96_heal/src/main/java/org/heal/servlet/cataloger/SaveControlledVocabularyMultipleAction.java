package org.heal.servlet.cataloger;

import org.heal.module.metadata.TaxonBean;
import org.heal.module.metadata.TaxonPathBean;
import org.heal.servlet.Action;
import org.heal.util.AuthenticationTools;
import org.heal.util.CommonDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Comments here!
 */
public class SaveControlledVocabularyMultipleAction implements Action {
	public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(!AuthenticationTools.isCataloger(request)) {
			// The user does not have the access to view this page
			// so we go no further, and redirect them to an access denied page
			response.sendRedirect("/error/accessDenied.jsp");
			return;
		}

		final String formSource = request.getParameter("source");
		final String formId = request.getParameter("id");
		final String formEntry = request.getParameter("entry");
		final String formMetadataIds = request.getParameter("metadataIds");

		List<String> metadataIds = new ArrayList<String>();
		List<String> errors = new ArrayList<String>();
		if(isEmpty(formSource)) {
            errors.add("source (empty)");
		}
		if(isEmpty(formId) && !"Consumer Health/Patient Education".equals(formSource)) {
			errors.add("id (empty)");
		}
		if("Mesh".equals(formSource) && isEmpty(formEntry)) {
			errors.add("entry (empty)");
		}
		if(isEmpty(formMetadataIds)) {
			errors.add("metadataIds (empty)");
		} else {
			try {
				for(String metadataId : formMetadataIds.split("\\n")) {
					if(0 != metadataId.trim().length() && !"\n".equals(metadataId.trim())) {
						Integer.parseInt(metadataId.trim());
						metadataIds.add(metadataId.trim());
					}
				}
			} catch(NumberFormatException e) {
				errors.add("metadataIds (not numbers)");
			}
		}

		if(0 != errors.size()) {
			request.setAttribute("errors", errors);
			request.setAttribute("source", formSource);
			request.setAttribute("id", formId);
			request.setAttribute("entry", formEntry);
			request.setAttribute("metadataIds", formMetadataIds);
			RequestDispatcher rd;
			rd = request.getRequestDispatcher("/catalog/addControlledVocabularyMultiple.jsp");
			rd.forward(request, response);
			return;
		}

		CommonDAO cd = (CommonDAO)servlet.getServletContext().getAttribute("CommonDAO");
		try {
			for(String metadataId : metadataIds) {
				saveControlledVocab(cd, metadataId, formSource, formEntry, formId);
			}
		} catch(SQLException e) {
			throw new ServletException(e);
		}

		RequestDispatcher rd = request.getRequestDispatcher("/catalog/success.jsp");
		rd.forward(request, response);
	}

	private void saveControlledVocab(CommonDAO cd, String metadataId, String source, String entry, String id)
		  throws SQLException {
		TaxonPathBean tpb = new TaxonPathBean();
		tpb.setMetadataId(metadataId);
		tpb.setSource(source);

		TaxonBean tb = new TaxonBean();
		tb.setEntry(entry);
		tb.setId(id);

		tpb.addTaxon(tb);

		cd.saveTaxonPath(tpb);
	}

	private boolean isEmpty(String test) {
		return (null == test || 0 == test.trim().length());
	}

	public boolean actionRequiresLogin() {
		return true;
	}
}
