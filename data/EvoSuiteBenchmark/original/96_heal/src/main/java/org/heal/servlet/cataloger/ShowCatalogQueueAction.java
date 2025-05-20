package org.heal.servlet.cataloger;

import org.heal.module.catalog.QueueDAO;
import org.heal.servlet.Action;
import org.heal.util.AuthenticationTools;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * An {@link org.heal.servlet.Action} which fetches the information needed to
 * display the catalog queue.
 *
 * @author Brad Schaefer (<A HREF="mailto:schaefer@lib.med.utah.edu">schaefer@lib.med.utah.edu</A>)
 */
public class ShowCatalogQueueAction implements Action {
    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        if(!AuthenticationTools.isCataloger(request)) {
            // The user does not have the access to view this page
            // so we go no further, and redirect them to an access denied page
            // TODO instead of hardcoding page locations, this should be in config files somewhere
            response.sendRedirect("/error/accessDenied.jsp");
            return;
        }

        final QueueDAO queueManager = (QueueDAO)servlet.getServletContext().getAttribute("QueueDAO");

        final List queue = queueManager.getCatalogQueue();
        request.setAttribute("catalogQueue", queue);

        // TODO instead of hardcoding page locations, this should be in config files somewhere
        RequestDispatcher rd = request.getRequestDispatcher("/catalog/viewqueue.jsp");
        rd.forward(request, response);
    }

    /**
     * @return <code>true</code>
     */
    public boolean actionRequiresLogin() {
        return true;
    }
}
