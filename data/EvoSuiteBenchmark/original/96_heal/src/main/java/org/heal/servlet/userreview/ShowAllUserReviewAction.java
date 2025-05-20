package org.heal.servlet.userreview;

import org.heal.servlet.*;
import org.heal.util.AuthenticationTools;
import org.heal.util.FileLocator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

// user review manager, lists all user reviews that need approval.
public class ShowAllUserReviewAction implements Action {
    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // only approvers, admins or catalogers can access this page.
        if(AuthenticationTools.isApprover(request) == false && AuthenticationTools.isAdministrator(request) == false && AuthenticationTools.isCataloger(request) == false) {
            response.sendRedirect("/error/accessDenied.jsp");
            return;
        }
        
        final UserReviewDAO userReviewRegistry = (UserReviewDAO)servlet.getServletContext().getAttribute("UserReviewDAO");
        FileLocator healFileLocator = (FileLocator) servlet.getServletContext().getAttribute("healFileLocator");

        ArrayList userReviewArray = new ArrayList();
				
        try {
            // need to pass the file locator for path to resource thumbnails and images.
            userReviewArray = userReviewRegistry.getAllUserReview(healFileLocator);	

        } catch(SQLException e) {
            throw new ServletException(e);
        }

        String origURL = "../healapp/showMetadata?" + request.getQueryString();
        request.setAttribute("origURL", origURL);
        request.setAttribute("userReviewArray", userReviewArray);

        RequestDispatcher rd;
        rd = request.getRequestDispatcher("/userreview/userreviewmanager.jsp");
        rd.forward(request, response);
    }
    
    // login is required to view this page.
    public boolean actionRequiresLogin() {
        return true;
    }
}
