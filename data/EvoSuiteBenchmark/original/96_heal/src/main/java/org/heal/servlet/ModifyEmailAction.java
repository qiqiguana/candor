package org.heal.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.heal.module.user.UserRegistryBean;
import org.heal.module.user.UserBean;
import org.heal.module.notice.NotificationServicesBean;
import javax.mail.MessagingException;
import org.heal.util.FileLocator;
import java.sql.SQLException;
import java.util.Vector;
import javax.servlet.RequestDispatcher;

public class ModifyEmailAction implements Action 
{
  public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException 
  {        
    String email = request.getParameter("email");
		String newEmail = request.getParameter("newemail");
    String cfmEmail = request.getParameter("emailConfirm");
    UserBean user = new UserBean();      
    UserRegistryBean userRegistry = (UserRegistryBean)servlet.getServletContext().getAttribute("userRegistry");
    Vector errorMessage = new Vector();   
    if (email == null || email.length() == 0) 
    {
      errorMessage.addElement("Current E-mail address missing");
    } 				
    if (newEmail  == null || newEmail.length() == 0) 
    {
			errorMessage.addElement("New E-mail address missing");
    }
    try 
    { 
      if (userRegistry.userExists(newEmail)) {
				errorMessage.addElement("User " + newEmail + " already exists, please try another email address.");
      }
      else if(userRegistry.userExists(email)== false){
        errorMessage.addElement("Invalid current E-mail address");
      }
    }
    catch (SQLException ex) 
    {    
    }	
    if (cfmEmail == null || cfmEmail.length() == 0) {
			errorMessage.addElement("E-mail confirmation missing");
    }			     
    if(!cfmEmail.equals(newEmail))
    {
      errorMessage.addElement("Email confirmation does not match");      
    }
    if (errorMessage.size() > 0) 
    {
      request.setAttribute("errorMessage",errorMessage);
          
        RequestDispatcher rd;
        rd = request.getRequestDispatcher("/user/modifyemail.jsp");
        rd.forward(request, response); 
		}
    else
    {
        userRegistry.updateEmail(email, newEmail);
        try 
        {
          user = userRegistry.getUserFromEmail(newEmail);
        }
        catch (SQLException ex)
        {
        }
        String userId = user.getUserId();
        
        NotificationServicesBean nsb = (NotificationServicesBean) servlet.getServletContext().getAttribute("notificationServices");
				FileLocator healFileLocator = (FileLocator) servlet.getServletContext().getAttribute("healFileLocator");
				try{
				nsb.sendUsernameValidationEmail(user,healFileLocator.getServerBaseURL()+"/healapp/emailValidation?emailId="+userId+"&email="+user.getEmail());
				}
				catch(MessagingException m)
				{
					m.printStackTrace();
				}
        response.sendRedirect("../user/register_response.jsp?email="+newEmail);
    }
  }
  public boolean actionRequiresLogin() {
			return false;
  }
}