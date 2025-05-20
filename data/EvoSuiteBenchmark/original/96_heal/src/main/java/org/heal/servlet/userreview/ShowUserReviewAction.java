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
import org.heal.util.AuthenticationTools;

// lists all the reviews (approved and pending) for a particular resource.
public class ShowUserReviewAction implements Action {
    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        // only approvers, admins or catalogers can access this page.
        if(AuthenticationTools.isApprover(request) == false && AuthenticationTools.isAdministrator(request) == false && AuthenticationTools.isCataloger(request) == false) {
            response.sendRedirect("/error/accessDenied.jsp");
            return;
        }

        final String metadataId = request.getParameter("metadataId");

        final UserReviewDAO userReviewRegistry = (UserReviewDAO)servlet.getServletContext().getAttribute("UserReviewDAO");
        ArrayList userReviewArray = new ArrayList();
        int userRatingAVG = 0;
				
        try {
            // get all the user review data.
            userReviewArray = userReviewRegistry.getUserReview(metadataId, "all", "reviewDate");	
            // calculate the average star rating.
            userRatingAVG = userReviewRegistry.getUserRatingAVG(metadataId);	

        } catch(SQLException e) {
            throw new ServletException(e);
        }

        String referrer = request.getHeader("referer");
        request.setAttribute("origURL", referrer);
        request.setAttribute("userReviewArray", userReviewArray);
        request.setAttribute("userRatingAVG", userRatingAVG);

        RequestDispatcher rd;
        rd = request.getRequestDispatcher("/userreview/indivuserreview.jsp");
        rd.forward(request, response);
    }

    // login is required to view this page.
    public boolean actionRequiresLogin() {
        return true;
    }
}
