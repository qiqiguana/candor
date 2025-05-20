package org.heal.servlet.userreview;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import org.heal.servlet.*;
import org.heal.util.AuthenticationTools;

// approves user reviews
public class ApproveUserReviewAction implements Action {

    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
	
        // only approvers, admins or catalogers can access this page.
        if(AuthenticationTools.isApprover(request) == false && AuthenticationTools.isAdministrator(request) == false && AuthenticationTools.isCataloger(request) == false) {
            response.sendRedirect("/error/accessDenied.jsp");
            return;
        }

        String metaDataId;

        UserReviewBean userReviewEntry = null;
        UserReviewDAO userReviewRegistry = (UserReviewDAO)servlet.getServletContext().getAttribute("UserReviewDAO");

        metaDataId = request.getParameter("metadataId");
        String[] approvedArray = request.getParameterValues("approved");
      
        try {
            // to approve a list of reviews for a resource, all existing approved reviews are set to NOT approved...
            userReviewRegistry.clearUserReviewApproval(metaDataId);
  
            // ...then new approved reviews are updated using this loop.
            if (approvedArray != null && approvedArray.length !=0){

                for (int i=0; i<approvedArray.length; i++)
                {
                    userReviewEntry = new UserReviewBean();
                    userReviewEntry.setApproved(true);
                    userReviewEntry.setReviewId(Integer.parseInt(approvedArray[i]));
                    userReviewRegistry.approveUserReview(userReviewEntry);
                }
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
     	response.sendRedirect("../userreview/userreviewapproval_response.jsp?metadataId="+metaDataId);
    } 

    // login is required to view this page.
    public boolean actionRequiresLogin() {
        return true;
    }
}

