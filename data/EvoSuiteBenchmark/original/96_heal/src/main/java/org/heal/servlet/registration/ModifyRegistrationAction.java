package org.heal.servlet.registration;


import com.ora.jsp.util.StringFormat;
import java.util.ArrayList;
import javax.mail.MessagingException;
import org.heal.module.notice.NotificationServicesBean;
import org.heal.module.user.InstructionalLevelBean;
import org.heal.module.user.ProfessionalRoleBean;
import org.heal.module.user.UserBean;
import org.heal.module.user.UserRegistryBean;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;
import org.heal.util.FileLocator;
import org.heal.servlet.*;

public class ModifyRegistrationAction implements Action {

/**
 * An {@link Action} used to modify registration info of users (to new format).
 */
	public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

    Vector errorMessages = new Vector();
    RequestDispatcher rd;
    String areaOfExpertise,professionalRoleOther,professionalRoleOtherText,userName="",
        emailConfirm="",email="";
		String instructionalLevel[]=null, professionalRole[]=null;
		UserBean userEntry = new UserBean();
    
    UserRegistryBean userRegistry = (UserRegistryBean) servlet.getServletContext().getAttribute("userRegistry");
	UserBean user = (UserBean) request.getSession().getAttribute("validUser");

    /* TODO - SOme of these duplicate form retrievel and validation code in multiple registration related files
     * can be combined into a single tool.  This can handled much nicer if we move to Struts.
     */ 
     
    email = request.getParameter("email");
    emailConfirm = request.getParameter("emailConfirm");
    professionalRoleOther = request.getParameter("professionalRoleOther");
	professionalRoleOtherText = request.getParameter("professionalRoleOtherText").trim(); 
    areaOfExpertise = request.getParameter("areaOfExpertise");
		
    instructionalLevel = request.getParameterValues("instructionalLevel");
    professionalRole = request.getParameterValues("professionalRole");

	userName = user.getUserName();
    if (isStringEmpty(userName)) {
        errorMessages.addElement("Unable to determine the username, please logout.");
    }

	if (!StringFormat.isValidEmailAddr(request.getParameter("email"))) {
        errorMessages.addElement("Invalid E-mail address");
    }
    
    if(!emailConfirm.equals(request.getParameter("email"))){
        errorMessages.addElement("Email confirmation does not match");
    }
		
    if ((professionalRole = request.getParameterValues("professionalRole")) == null || professionalRole.length == 0 || professionalRole[0].length()==0) {
        errorMessages.addElement("Professional Role missing");
    }

	if(professionalRole!=null){
        for (int a=0;a<professionalRole.length;a++){
            if("Other".equals(professionalRole[a])){
                if (isStringEmpty(professionalRoleOtherText)){
                    errorMessages.addElement("Enter value for Professional Role: Other");
                }     					
            }
        }
	}
    
	if (isStringEmpty(areaOfExpertise)) {
        errorMessages.addElement("Area of Expertise missing");
    }
    
    if ((errorMessages.size() > 0)) {
        request.setAttribute("errorMessages", errorMessages);

    ArrayList ilb = new ArrayList();
        if ((instructionalLevel  != null && instructionalLevel.length > 0)) {
            for (int a=0;a<instructionalLevel.length;a++){
                if(instructionalLevel[a].length()>0){
                    ilb.add(instructionalLevel[a]);
				}
			}
		}

	ArrayList prb = new ArrayList();
        if ((professionalRole  != null && professionalRole.length > 0)) {			
            for (int a=0;a<professionalRole.length;a++){  
                if(professionalRole[a].length()>0){										
                    prb.add(professionalRole[a]);						
				}
			}
		}
      
	request.setAttribute("professionalRoleOtherText",professionalRoleOtherText);			
	request.setAttribute("prb",prb);
	request.setAttribute("ilb",ilb);
    userEntry.setProfessionalSpecialty(areaOfExpertise);
    userEntry.setEmail(email);
    request.setAttribute("validUser", userEntry);
      
    // TODO links should be moved to config file rather than hardcoded
	rd = request.getRequestDispatcher("/user/modifyLogin.jsp");
    rd.forward(request, response);
    }
							
	else{
        user.setLoginModified(true);			
        user.setInstructionalLevel(new ArrayList());
		if ((instructionalLevel  != null && instructionalLevel.length > 0)) {
            for (int a=0;a<instructionalLevel.length;a++){
                if(instructionalLevel[a].length()>0){
                    InstructionalLevelBean ilb = new InstructionalLevelBean();
                    ilb.setInstructionalLevel(instructionalLevel[a]);
					user.addInstructionalLevel(ilb);
				}
			}
		}
				
        if ((professionalRole  != null && professionalRole.length > 0)) {
            user.setProfessionalRole(new ArrayList());
            for (int a=0;a<professionalRole.length;a++){
                if(professionalRole[a].length()>0){				
                    ProfessionalRoleBean prb = new ProfessionalRoleBean();
                        if ("Other".equalsIgnoreCase(professionalRole[a]))
                            prb.setProfessionalRole(professionalRoleOtherText);
						else							
                        prb.setProfessionalRole(professionalRole[a]);
                    user.addProfessionalRole(prb);
                }
            }
        }
				
        user.setProfessionalSpecialty(areaOfExpertise);
		String userId = "";
		String userId2 = "";
		try{
            userId2 = userRegistry.saveRegistration(user);
        } 
		catch (SQLException e) {
            e.printStackTrace();
        }
			
        userId = user.getUserId();
		if(userId==null || userId.length()==0){
            userId=userId2;
		}
			
		NotificationServicesBean nsb = (NotificationServicesBean) servlet.getServletContext().getAttribute("notificationServices");
		FileLocator healFileLocator = (FileLocator) servlet.getServletContext().getAttribute("healFileLocator");
		try{
            // TODO links should be moved to cofnig file rather than hardcoded
            nsb.sendUsernameValidationEmail(user,healFileLocator.getServerBaseURL()+"/healapp/emailValidation?emailId="+userId+"&email="+user.getEmail());
		}
		
        catch(MessagingException m){
            m.printStackTrace();
		}
			  
		request.getSession().setAttribute("validUser", user);
		String origURL = request.getParameter("origURL");
      
		//<%-- Redirect to the notice page--%>
        // TODO links should be moved to config file rather than hardcoded	
    	String redirect = "../index.jsp";
				
		if ((origURL!=null) && (origURL.length()>0))
            redirect = origURL;
        
		response.sendRedirect(redirect);
        }
    }
  
    public boolean isStringEmpty(String st){
        if(st==null || st.length()==0)
            return true;
        else return false;
    }
	
    public boolean actionRequiresLogin() {
        return false;
    }
  
}
