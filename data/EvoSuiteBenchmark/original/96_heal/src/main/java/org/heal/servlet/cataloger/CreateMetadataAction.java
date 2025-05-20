package org.heal.servlet.cataloger;

import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.MetadataDAO;
import org.heal.module.metadata.TaxonBean;
import org.heal.module.metadata.TaxonPathBean;
import org.heal.servlet.Action;
import org.heal.util.AuthenticationTools;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * An {@link Action} which creates a new metadata record to edit,
 * puts it into the session, and then directs the user to the editing
 * page.
 */
public class CreateMetadataAction implements Action {
	public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(!AuthenticationTools.isCataloger(request)) {
			// The user does not have the access to view this page
			// so we go no further, and redirect them to an access denied page
			// TODO instead of hardcoding page locations, this should be in config files somewhere
			response.sendRedirect("/error/accessDenied.jsp");
			return;
		}

		final HttpSession session = request.getSession();
		CompleteMetadataBean cmb = (CompleteMetadataBean)session.getAttribute("metadata");
        if(null != cmb) {
			response.sendRedirect("../catalog/concurrentMetadataEditError.jsp");
			return;
		}

		final String metadataId = request.getParameter("basedOnMetadataId");
		if(null == metadataId) {
			cmb = new CompleteMetadataBean();
		} else {
			final MetadataDAO metadataServices = (MetadataDAO)servlet.getServletContext().getAttribute("MetadataDAO");
			try {
				cmb = metadataServices.getCompleteMetadata(metadataId);
			} catch(SQLException e) {
				throw new ServletException(e);
			}
			clearIds(cmb);
			cmb.setLocation(null);
			cmb.setCatalogDate(null);
			cmb.setApproveDate(null);
			if(null != cmb.getThumbnail()) {
				cmb.setThumbnail(null);
			}
			cmb.setFileSize(null);
			cmb.setFileHeight(null);
			cmb.setFileWidth(null);
		}
		cmb.setContributeDate(new Date());
        session.setAttribute("metadata", cmb);

		RequestDispatcher rd = request.getRequestDispatcher("/healapp/editMetadata");
		rd.forward(request, response);
	}

	/**
	 * Since this {@link Action} creates <em>new</em> metadata records, we
	 * must clear the ids from an existing {@link CompleteMetadataBean} that
	 * this new record is based on.
	 *
	 * @param cmb
	 */
	private static void clearIds(CompleteMetadataBean cmb) throws ServletException {
        cmb.setMetadataId(null);
		cmb.setGlobalId(null);

		clearIdsFromList(cmb.getContextURLs());
		clearIdsFromList(cmb.getContributorList());
		clearIdsFromList(cmb.getCopyrightHolders());
		clearIdsFromList(cmb.getCopyrights());
		clearIdsFromList(cmb.getDiseaseDiagnoses());
		clearIdsFromList(cmb.getFormats());
		clearIdsFromList(cmb.getKeywords());
		clearIdsFromList(cmb.getRelations());
		clearIdsFromList(cmb.getRequirements());
		clearIdsFromList(cmb.getTargetUserGroups());

		// Taxons/TaxonPaths need to be handled a bit differently since
		// they have an unusual association going on
		String nextTaxonId = "-1";
		String nextTaxonPathId = "-1";
		for(Iterator iterOne = cmb.getTaxonPaths().iterator(); iterOne.hasNext();) {
            final TaxonPathBean taxonPath = (TaxonPathBean)iterOne.next();
			taxonPath.setMetadataId(null);
			taxonPath.setTaxonPathId(nextTaxonPathId);

			for(Iterator iterTwo = taxonPath.getTaxons().iterator(); iterTwo.hasNext();) {
				final TaxonBean taxon = (TaxonBean)iterTwo.next();
				taxon.setTaxonId(nextTaxonId);
				taxon.setTaxonPathId(null);
				nextTaxonId = String.valueOf(Integer.parseInt(nextTaxonId) - 1);
			}
			nextTaxonPathId = String.valueOf(Integer.parseInt(nextTaxonPathId) - 1);
		}
	}

	private static void clearIdsFromList(List objects) throws ServletException {
		Map ids = new HashMap();
		for(Iterator iter = objects.iterator(); iter.hasNext();) {
			Object ob = iter.next();
			Method[] methods = ob.getClass().getMethods();
			for(int i = 0; i < methods.length; ++i) {
				final String methodName = methods[i].getName();
				Object[] args = new Object[1];

				if("setMetadataId".equals(methodName)) {
					args[0] = null;
				} else if(methodName.matches("^set.+Id")) {
					String id;
                    if(!ids.containsKey(methodName)) {
						id = "-1";
					} else {
						id = String.valueOf(Integer.parseInt((String)ids.get(methodName))-1);
					}
					args[0] = id;
					ids.put(methodName, id);
				} else {
					continue;
				}
				Class[] parameters = methods[i].getParameterTypes();
				if(1 != parameters.length || !parameters[0].equals(String.class)) {
					// Constructs method signature for error message
					StringBuffer argString = new StringBuffer("(");
					for(int j = 0; j < parameters.length; ++j) {
						argString.append(parameters[j].getName());
						if(j < parameters.length-1) {
							argString.append(", ");
						}
					}
					argString.append(")");
					throw new ServletException("Unexpected setXXXId method found: " +methodName
						  +argString.toString());
				}
				try {
					methods[i].invoke(ob, args);
				} catch(Exception e) {
					throw new ServletException(e);
				}
			}
		}
	}

	/**
	 * @return <code>true</code>
	 */
	public boolean actionRequiresLogin() {
		return true;
	}
}
