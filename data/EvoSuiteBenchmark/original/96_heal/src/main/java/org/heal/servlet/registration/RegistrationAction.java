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

/**
 * An {@link Action} that used to register users.
 */

public class RegistrationAction implements Action {

    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

        Vector errorMessages = new Vector();
        RequestDispatcher rd;
        String firstName,lastName,areaOfExpertise,professionalRoleOther,professionalRoleOtherText,
            userName="",institutionName,mailingListS="",city, state, country, emailConfirm="",
            password="", passwordConfirm;
        String instructionalLevel[]=null, professionalRole[]=null;
        UserBean userEntry = new UserBean();

        /* TODO -  Some of duplicate form retrievel and validation code in multiple registration related files 
         * can be combined into a single tool.  This can handled much nicer if we move to Struts.
         */
        Boolean IAMSEMemberS = false;
        String IAMSEMemberValue = request.getParameter("IAMSEMember");
        if (IAMSEMemberValue != null) {
            IAMSEMemberS = true;
        }

        UserRegistryBean userRegistry = (UserRegistryBean) servlet.getServletContext().getAttribute("userRegistry");
        UserBean user = (UserBean) request.getSession().getAttribute("validUser");
        userName = request.getParameter("email").trim();
        password = request.getParameter("password");
        passwordConfirm = request.getParameter("passwordconfirm");
        emailConfirm = request.getParameter("emailConfirm");
        professionalRoleOther = request.getParameter("professionalRoleOther");
        professionalRoleOtherText = request.getParameter("professionalRoleOtherText").trim(); 
        areaOfExpertise = request.getParameter("areaOfExpertise");
        System.out.println("areaofE:"+areaOfExpertise);
        firstName = request.getParameter("firstName").trim();
        lastName = request.getParameter("lastName").trim();
        mailingListS = request.getParameter("isMailingList");
        institutionName = request.getParameter("institutionName").trim();
        city = request.getParameter("city").trim();
        System.out.println("city:"+city);
        state = request.getParameter("state").trim();
        System.out.println("State:"+state);
        country = request.getParameter("country").trim();
        System.out.println("country:"+country);
        instructionalLevel = request.getParameterValues("instructionalLevel");
        professionalRole = request.getParameterValues("professionalRole");
        System.out.println("IAMSE:"+IAMSEMemberS);
    
        user = new UserBean();
        try {
            if (isStringEmpty(userName)){
                errorMessages.addElement("E-mail address missing");
            } 
            else if (userRegistry.userExists(userName)) {
                errorMessages.addElement("User " + userName + " already exists, please try another email address.");
            }
            else{
                user.setUserName(userName);
            }
            if (isStringEmpty(password)){
                errorMessages.addElement("Password missing");
            }
            if (isStringEmpty(passwordConfirm)) {
                errorMessages.addElement("Password confirmation missing");
            }

            if (!passwordConfirm.equals(password)) {
                errorMessages.addElement("Password and password confirmation do not match.");      
            } 
        }
		
        catch (SQLException ex) { 
            ex.printStackTrace();
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

        professionalRoleOther = request.getParameter("professionalRoleOther");
		professionalRoleOtherText = request.getParameter("professionalRoleOtherText").trim();

		if(professionalRole!=null){
            for (int a=0;a<professionalRole.length;a++){
                if("Other".equals(professionalRole[a])){
                    if (professionalRoleOtherText==null||professionalRoleOtherText.length()==0){
                        errorMessages.addElement("Enter value for Professional Role: Other");
                    }					
                }
            }
		}

		if ((areaOfExpertise = request.getParameter("areaOfExpertise")) == null || areaOfExpertise.length() == 0) {
            errorMessages.addElement("Area of Expertise missing");
        }
    
		if (isStringEmpty(firstName)) {
            errorMessages.addElement("First Name missing");
        }
			
        if (isStringEmpty(lastName)) {
            errorMessages.addElement("Last Name missing");
        }
			
        if (isStringEmpty(mailingListS)) {
            errorMessages.addElement("Please indicate if you want to be on the mailing list");
        }					
    
        if (isStringEmpty(institutionName)) {
            errorMessages.addElement("Institution Name missing");
        }	

        if (isStringEmpty(city)){
            errorMessages.addElement("City missing");
        }	
		
        if (isStringEmpty(state)) {
            errorMessages.addElement("State missing");
        }	
			
        if (isStringEmpty(country)) {
            errorMessages.addElement("Country missing");
        }

        if (errorMessages.size() > 0) {
            request.setAttribute("errorMessages", errorMessages);
            userEntry.setProfessionalSpecialty(areaOfExpertise);
            request.setAttribute("validUser", userEntry);
			
			ArrayList ilb = new ArrayList();
			instructionalLevel = request.getParameterValues("instructionalLevel");
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

            // TODO move to config file instead of hardcoding			    
            rd = request.getRequestDispatcher("/user/register.jsp");
            rd.forward(request, response);
            return;
        } 
				
		else{
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setInstitutionName(institutionName);       
            user.setState(state);
            user.setCity(city);
            user.setCountry(country);
            user.setIAMSEMember(IAMSEMemberS);
            
            //user.setMiddleInitial(request.getParameter("middleInitial"));        
            //user.setAddress1(request.getParameter("address1"));
            //user.setAddress2(request.getParameter("address2"));
            //user.setZipCode(request.getParameter("zipCode"));
        
			boolean minor = false;
			boolean mailingList = true;

			String minorS = (request.getParameter("aminor"));
			if ("yes".equals(minorS))
                minor = true;

			if ("no".equals(mailingListS))
                mailingList = false;
			
			user.setMinor(minor);
			user.setMailingList(mailingList);					
			user.setLoginModified(true);			
				
			instructionalLevel = request.getParameterValues("instructionalLevel");
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
            user.setEmail(userName);
			user.setPassword(password);	
			String userId = "";
			String userId2 = "";
			
            try {
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
                // TODO move to config file instead of hardcoding			                
                nsb.sendUsernameValidationEmail(user,healFileLocator.getServerBaseURL()+"/healapp/emailValidation?emailId="+userId+"&email="+user.getEmail());
			}
			catch(MessagingException m){
                m.printStackTrace();
			}
            // TODO move to config file instead of hardcoding			    
            response.sendRedirect("../user/register_response.jsp?email="+userName);
		}
	}
  
    public boolean isStringEmpty(String st){
        if((st==null) || (st.length()==0)){
            return true;
        }
        else 
            return false;
        }
    
    public boolean actionRequiresLogin() {
        return false;
    }
}
