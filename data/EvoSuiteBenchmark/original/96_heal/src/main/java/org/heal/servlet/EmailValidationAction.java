package org.heal.servlet;

import com.ora.jsp.util.StringFormat;
import java.util.Vector;
import javax.mail.MessagingException;
import org.heal.module.notice.NotificationServicesBean;
import org.heal.module.user.UserBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.heal.module.user.UserRegistryBean;

/**
 * An {@link Action} used to validate a user's email address and activate their account.
 * @author Jason Varghese
 */

public class EmailValidationAction implements Action {
    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        
        String emailId = request.getParameter("emailId");
		String email = request.getParameter("email");
        int id = 0;
        try{
            id = Integer.parseInt(emailId);
		}
		
        catch (NumberFormatException nfe){
            id=0;
		}
    
        String mailingListURL = "http://lib.med.utah.edu/mailman/listinfo/heal-announce";
		if (emailId!=null && email !=null){
            UserRegistryBean userRegistry = (UserRegistryBean)servlet.getServletContext().getAttribute("userRegistry");
            boolean update = false;			
            update = userRegistry.validateEmail(email, id);
            NotificationServicesBean notificationServices = (NotificationServicesBean) servlet.getServletContext().getAttribute("notificationServices");
            try{
                if(update){
                    notificationServices.sendWelcomeMessage(email,mailingListURL);      
                }
            }
            catch (MessagingException mhe){
                mhe.printStackTrace();
            }
            if(update){
                response.sendRedirect("/user/email_validation.jsp"); 
            }  
            else{
            Vector errorMessage = new Vector();
            errorMessage.addElement("Your email address could not be validated.  Please request a password reminder for further account information.  ");
            request.setAttribute("errorMessage" ,errorMessage);
            RequestDispatcher rd;
            rd = request.getRequestDispatcher("/user/password.jsp");
            rd.forward(request, response);        
            }
        }			
    }

    public boolean actionRequiresLogin() {
        return false;
    }
}
