package org.heal.servlet.cataloger;

import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.MetadataDAO;
import org.heal.module.metadata.TargetUserGroupBean;
import org.heal.module.metadata.ContributorBean;
import org.heal.module.metadata.CopyrightHolderBean;
import org.heal.servlet.Action;
import org.heal.util.AuthenticationTools;
import org.heal.util.VCardBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * An {@link org.heal.servlet.Action Action} used to edit metadata
 * records.
 *
 * @author Brad Schaefer (<A HREF="mailto:schaefer@lib.med.utah.edu">schaefer@lib.med.utah.edu</A>)
 * @version 1.0
 */
public class EditMetadataAction implements Action {
    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        if(!AuthenticationTools.isCataloger(request)) {
			// The user does not have the access to view this page
			// so we go no further, and redirect them to an access denied page
            // TODO instead of hardcoding page locations, this should be in config files somewhere
            response.sendRedirect("/error/accessDenied.jsp");
            return;
        }

		final String metadataId = request.getParameter("metadataId");
		CompleteMetadataBean cmb = (CompleteMetadataBean)request.getSession().getAttribute("metadata");

		if(null == cmb) {
			if(null != metadataId) {
				// If there's no CompleteMetadataBean in the session, we try to retrieve one from
				// the database based on the metadataId parameter
				final MetadataDAO metadataServices = (MetadataDAO) servlet.getServletContext().getAttribute("MetadataDAO");

				try {
					cmb = metadataServices.getCompleteMetadata(metadataId);
					request.getSession().setAttribute("metadata", cmb);
				} catch (SQLException e) {
					throw new ServletException(e);
					// TODO Logging?
				}
			} else {
				request.setAttribute("reason", "No metadata record available to edit.");
				RequestDispatcher rd = request.getRequestDispatcher("/error/accessDenied.jsp");
				rd.forward(request, response);
				return;
			}
		} else {
			if(null != metadataId) {
				response.sendRedirect("../catalog/concurrentMetadataEditError.jsp");
				return;
			}
		}

		if(null != cmb) {
			// We must add target user groups to the request as a Map so that
			// the JSP EL can easily determine whether checkboxes should be checked
			Map targetUserGroups = new HashMap();
			for(Iterator iter = cmb.getTargetUserGroups().iterator(); iter.hasNext();) {
				TargetUserGroupBean tug = (TargetUserGroupBean)iter.next();
				targetUserGroups.put(tug.getTargetUserGroup().toLowerCase(), new Object());
			}
			request.setAttribute("targetUserGroups", targetUserGroups);

			// We add names for contributors so that they can be
			// printed out nicely in the jsp
			VCardBean vCard = new VCardBean();
			List contributors = cmb.getContributorList();
			for(Iterator iter = contributors.iterator(); iter.hasNext();) {
				ContributorBean cb = (ContributorBean)iter.next();
				vCard.setVCard(cb.getVCard());
				if(vCard.isValidVCard()) {
					if(null != vCard.getFormattedName()) {
						cb.setTitle(vCard.getFormattedName());
					} else {
						cb.setTitle(vCard.getFirstName() + " " +vCard.getLastName());
					}
				} else {
					cb.setTitle(cb.getVCard());
				}
			}

			// We add names for copyright holders so that they can be
			// printed out nicely in the jsp
			for(Iterator iter = cmb.getCopyrightHolders().iterator(); iter.hasNext();) {
				CopyrightHolderBean chb = (CopyrightHolderBean)iter.next();
				vCard.setVCard(chb.getVCard());
				if(vCard.isValidVCard()) {
					if(null != vCard.getFormattedName()) {
						chb.setTitle(vCard.getFormattedName());
					} else {
						chb.setTitle(vCard.getFirstName()+ " " +chb.getLastName());
					}
				} else {
					chb.setTitle(chb.getVCard());
				}
			}
		}

        // TODO instead of hardcoding page locations, this should be in config files somewhere
        RequestDispatcher rd = request.getRequestDispatcher("/catalog/editMetadata.jsp");
        rd.forward(request, response);
    }

    /**
     * @return <code>true</code>
     */
    public boolean actionRequiresLogin() {
        return true;
    }
}
