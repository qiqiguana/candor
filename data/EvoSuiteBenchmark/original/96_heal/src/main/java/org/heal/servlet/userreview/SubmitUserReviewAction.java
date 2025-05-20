package org.heal.servlet.userreview;

import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import org.heal.servlet.*;
import org.heal.module.notice.NotificationServicesBean;
import org.heal.util.FileLocator;

// submits a user review
public class SubmitUserReviewAction implements Action {

    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

            String metaDataId;
            String userId;
            String comments;
            int userRating = 0;
            boolean result = false;
            
            UserReviewBean userReviewEntry = new UserReviewBean();
            UserReviewDAO userReviewRegistry = (UserReviewDAO) servlet.getServletContext().getAttribute("UserReviewDAO");
            NotificationServicesBean notificationServices = (NotificationServicesBean) servlet.getServletContext().getAttribute("notificationServices");
            FileLocator healFileLocator = (FileLocator) servlet.getServletContext().getAttribute("healFileLocator");
            String managerurl = healFileLocator.getServerBaseURL();
        
            // gets all the review data from the form.
            metaDataId = request.getParameter("metadataId");
            userId = request.getParameter("userId");
            userRating = Integer.parseInt(request.getParameter("userRating"));
            comments = request.getParameter("comments").trim();

            Boolean anonymous = false;
            String anonValue = request.getParameter("anonymous");
            if (anonValue != null) {
                anonymous = true;
            }

            // puts all the reivew data into the user review bean.
            userReviewEntry.setMetaDataId(metaDataId);
            userReviewEntry.setUserId(userId);
            userReviewEntry.setUserRating(userRating);
            userReviewEntry.setComments(comments);
            userReviewEntry.setAnonymous(anonymous);

            try {
                // saves the bean into database.
                userReviewRegistry.saveUserReview(userReviewEntry);
            }

            catch (SQLException e) {
                e.printStackTrace();
            }

            // sends email notice to Sharon Dennis whenever a resource has been reviewed.
            try {
                InternetAddress approver = new InternetAddress("sdennis@lib.med.utah.edu");
                InternetAddress[] to = {approver};
                String text = "HEAL resource with metadata ID #" + metaDataId + " has been reviewed." + System.getProperty("line.separator") + "Please go here to approve user reviews: " + managerurl + "/userreview/manager.html";
                String subject = "HEAL Resource Reviewed";

                result = notificationServices.sendEmail(to, text, subject);
                System.out.println("email result: "+result);

            } 
            catch (AddressException ex) {
                System.err.println("Address Ex: " + ex.toString());
            }
            catch (MessagingException ex) {
                System.err.println("Messaging Ex: " + ex.toString());
            }

            response.sendRedirect("../userreview/userreview_response.jsp?metadataId=" + metaDataId);
    } 

    // login is required to view this page.
    public boolean actionRequiresLogin() {
        return true;
    }
}

