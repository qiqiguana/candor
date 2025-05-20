package org.heal.servlet.tagcloud;

import org.heal.servlet.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

// shows the top 10 most highly rated resources.
public class ShowTopTagsAction implements Action {
    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        final TagCloudDAO tagCloudRegistry = (TagCloudDAO) servlet.getServletContext().getAttribute("TagCloudDAO");

        ArrayList tagArray = new ArrayList();
				
        try {
            tagArray = tagCloudRegistry.getTopTags();	

        } catch(SQLException e) {
            throw new ServletException(e);
        }

        String origURL = "../healapp/showMetadata?" + request.getQueryString();
        request.setAttribute("origURL", origURL);
        request.setAttribute("tagArray", tagArray);

        RequestDispatcher rd;
        rd = request.getRequestDispatcher("/tagcloud/toptags.jsp");
        rd.forward(request, response);
    }

    // login is not required to view this page.
    public boolean actionRequiresLogin() {
        return false;
    }
}
