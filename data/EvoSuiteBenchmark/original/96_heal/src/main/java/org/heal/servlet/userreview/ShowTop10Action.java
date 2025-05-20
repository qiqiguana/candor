package org.heal.servlet.userreview;

import org.heal.servlet.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import org.heal.util.FileLocator;


// shows the top 10 most highly rated resources.
public class ShowTop10Action implements Action {
    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        final UserReviewDAO userReviewRegistry = (UserReviewDAO)servlet.getServletContext().getAttribute("UserReviewDAO");
        FileLocator healFileLocator = (FileLocator) servlet.getServletContext().getAttribute("healFileLocator");

        ArrayList userReviewArray = new ArrayList();
				
        try {
            // need to pass the file locator for path to resource thumbnails and images.
            userReviewArray = userReviewRegistry.getTop10(healFileLocator);	

        } catch(SQLException e) {
            throw new ServletException(e);
        }

        String origURL = "../healapp/showMetadata?" + request.getQueryString();
        request.setAttribute("origURL", origURL);
        request.setAttribute("userReviewArray", userReviewArray);

        RequestDispatcher rd;
        rd = request.getRequestDispatcher("/userreview/top10.jsp");
        rd.forward(request, response);
    }

    // login is not required to view this page.
    public boolean actionRequiresLogin() {
        return false;
    }
}
