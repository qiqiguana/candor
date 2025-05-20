package org.heal.servlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;
import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.heal.module.notice.NotificationServicesBean;
import org.heal.module.user.UserBean;
import org.heal.module.user.UserRegistryBean;
import org.heal.util.FileLocator;

/**
 * An {@link Action} sends out the password reminder for users.
 * @author Jason Varghese
 */

public class EmailReminderAction implements Action {

    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        
        UserRegistryBean userRegistry = (UserRegistryBean) servlet.getServletContext().getAttribute("userRegistry");
        NotificationServicesBean notificationServices = (NotificationServicesBean) servlet.getServletContext().getAttribute("notificationServices");
        Vector errorMessage = new Vector();
        String email = request.getParameter("email");
        if(email!=null && email.length() >0){
            try{
                UserBean user = userRegistry.getUserFromEmail(email);
                if(user!=null){
                    FileLocator healFileLocator = (FileLocator) servlet.getServletContext().getAttribute("healFileLocator");
                    errorMessage = notificationServices.sendPasswordReminder(user, healFileLocator.getServerBaseURL());
                    if (errorMessage.size()==0)
                        errorMessage.addElement("Password reminder has been sent.");
                    }
                    else{
                        errorMessage.addElement("User email of " +email+"  not found");
                    }
            }
        
            catch (SQLException se){
                System.err.println(se.toString());
            }
            catch (MessagingException m){
                m.printStackTrace();
                errorMessage.addElement("System error: "+ m.toString());		
            }    					
        }
        else{
            errorMessage.addElement("Please Enter an Email Address");
        }
        
        if (errorMessage.size()>0)
            request.setAttribute("errorMessage",errorMessage);        
        RequestDispatcher rd;
        rd = request.getRequestDispatcher("/user/password.jsp");
        rd.forward(request, response);  
    }
        
    public boolean actionRequiresLogin() {
        return false;
    }
}
