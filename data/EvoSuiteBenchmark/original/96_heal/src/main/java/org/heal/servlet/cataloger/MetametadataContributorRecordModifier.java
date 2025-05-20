package org.heal.servlet.cataloger;

import org.heal.servlet.Action;
import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.MetametadataContributorBean;
import org.heal.util.DateTools;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.RequestDispatcher;
import java.io.IOException;

/**
 * Saves changes to a {@link MetametadataContributorBean}.
 */
public class MetametadataContributorRecordModifier implements Action, MetadataModifier {
    private static final Action NEXT_ACTION = new EditMetadataAction();

    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // We skip the usual authentication-checking stuff since this
        // already should've been done by ModifyMetadataAction
        final CompleteMetadataBean cmb = (CompleteMetadataBean)request.getSession().getAttribute("metadata");
        final String selectedId = request.getParameter("selectedId");

        // This shouldn't be necessary, but it won't hurt to check
        if(null == cmb || null == selectedId) {
            response.sendRedirect("/error/accessDenied.jsp");
            return;
        }

        MetametadataContributorBean mcbToEdit = new MetametadataContributorBean();
        for(Object o : cmb.getMetametadataContributors()) {
            MetametadataContributorBean temp = (MetametadataContributorBean)o;
            if(selectedId.equals(temp.getMetametadataContributorId())) {
                mcbToEdit = temp;
                break;
            }
        }

        request.setAttribute("MetametadataContributorBean", mcbToEdit);
        RequestDispatcher rd = request.getRequestDispatcher("/catalog/editMetametadataContributor.jsp");
        rd.forward(request, response);
    }

    public boolean actionRequiresLogin() {
        return true;
    }

    public void updateMetadata(CompleteMetadataBean cmb, ServletRequest request) {
        final String mmContributorId = request.getParameter("metametadataContributorId");
        final String role = request.getParameter("role");
        final String contributeDateStr = request.getParameter("contributeDate");
        final String contributeDateDescription = request.getParameter("contributeDateDescription");
        final String vCard = request.getParameter("vCard");

        MetametadataContributorBean mcb = null;
        int lowestMetametadataContributorId = 0;
        for(Object o : cmb.getMetametadataContributors()) {
            MetametadataContributorBean temp = (MetametadataContributorBean)o;
            String idStr = temp.getMetametadataContributorId();
            int id = Integer.parseInt(idStr);
            if(idStr.equals(mmContributorId)) {
                mcb = temp;
                break;
            }

            // We must find the lowest metametadata contributor id for cases
            // where this is a new metametadata contributor object, and we must
            // generate a unique id for it (which we define to be
            // negative numbers for metametadata contributors not in the
            // database yet).
            if(id < lowestMetametadataContributorId) {
                lowestMetametadataContributorId = id;
            }
        }
        if(null == mcb) {
            // No existing metametadata contributor was found, so we must
            // create one with a unique id and add it to the CompleteMetadataBean
            mcb = new MetametadataContributorBean();
            mcb.setMetametadataContributorId(String.valueOf(lowestMetametadataContributorId - 1));
            cmb.getMetametadataContributors().add(mcb);
        }

        mcb.setDate(DateTools.parse(contributeDateStr));
        mcb.setDateDescription(contributeDateDescription);
        mcb.setRole(role);
        mcb.setvCard(vCard);
    }

    public Action getNextAction(ServletRequest request) {
        return NEXT_ACTION;
    }
}
