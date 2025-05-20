package org.heal.servlet.cataloger;

import org.heal.module.metadata.CompleteMetadataBean;
import org.heal.module.metadata.MetametadataIdentifierBean;
import org.heal.servlet.Action;

import javax.servlet.ServletRequest;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Saves changes to a {@link MetametadataIdentifierBean}.
 */
public class MetametadataIdentifierRecordModifier implements Action, MetadataModifier {
    private static final Action NEXT_ACTION = new EditMetadataAction();

    public void updateMetadata(CompleteMetadataBean cmb, ServletRequest request) {
        final String mmIdentifierId = request.getParameter("metametadataIdentifierId");
        final String catalogue = request.getParameter("catalogue");
        final String entry = request.getParameter("entry");
        final String metadataSchema = request.getParameter("metadataSchema");

        MetametadataIdentifierBean mib = null;
        int lowestMetametadataIdentifierId = 0;
        for(Object o : cmb.getMetametadataIdentifiers()) {
            MetametadataIdentifierBean temp = (MetametadataIdentifierBean)o;
            String idStr = temp.getMetametadataIdentifierId();
            int id = Integer.parseInt(idStr);
            if(idStr.equals(mmIdentifierId)) {
                mib = temp;
                break;
            }

            // We must find the lowest metametadata identifier id for cases
            // where this is a new metametadata identifier object, and we must
            // generate a unique id for it (which we define to be
            // negative numbers for metametadata identifiers not in the
            // database yet).
            if(id < lowestMetametadataIdentifierId) {
                lowestMetametadataIdentifierId = id;
            }
        }
        if(null == mib) {
            // No existing metametadata identifier was found, so we must
            // create one with a unique id and add it to the CompleteMetadataBean
            mib = new MetametadataIdentifierBean();
            mib.setMetametadataIdentifierId(String.valueOf(lowestMetametadataIdentifierId - 1));
            cmb.getMetametadataIdentifiers().add(mib);
        }
        mib.setCatalog(catalogue);
        mib.setEntry(entry);
        mib.setMetadataSchema(metadataSchema);
    }

    public Action getNextAction(ServletRequest request) {
        return NEXT_ACTION;
    }

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

        MetametadataIdentifierBean mmIdentifierToEdit = new MetametadataIdentifierBean();
        for(Object o : cmb.getMetametadataIdentifiers()) {
            MetametadataIdentifierBean temp = (MetametadataIdentifierBean)o;
            if(selectedId.equals(temp.getMetametadataIdentifierId())) {
                mmIdentifierToEdit = temp;
                break;
            }
        }
        request.setAttribute("MetametadataIdentifierBean", mmIdentifierToEdit);
        RequestDispatcher rd = request.getRequestDispatcher("/catalog/editMetametadataIdentifier.jsp");
        rd.forward(request, response);
    }

    public boolean actionRequiresLogin() {
        return true;
    }
}
