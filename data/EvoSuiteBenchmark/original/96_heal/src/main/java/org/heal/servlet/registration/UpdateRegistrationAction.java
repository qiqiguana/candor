package org.heal.servlet.registration;


import com.ora.jsp.util.StringFormat;
import java.util.ArrayList;
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
import org.heal.servlet.*;


/**
 * An {@link Action} that is used to update registered users' info.
 */
public class UpdateRegistrationAction implements Action {

    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

        Vector errorMessages = new Vector();
        RequestDispatcher rd;
        String  firstName,lastName,areaOfExpertise,professionalRoleOther,professionalRoleOtherText,
                userName="",institutionName,mailingListS="",city, state, country, email="", emailConfirm="",
                password="", passwordConfirm;
        String instructionalLevel[]=null, professionalRole[]=null;
        UserBean userEntry = new UserBean();
        UserRegistryBean userRegistry = (UserRegistryBean) servlet.getServletContext().getAttribute("userRegistry");
        UserBean user = (UserBean) request.getSession().getAttribute("validUser");

        /* TODO -  Some of these duplicate form retrievel and validation code in multiple registration related files 
         * can be combined into a single tool.  This can handled much nicer if we move to Struts.
         */

        Boolean IAMSEMemberS = false;
        String IAMSEMemberValue = request.getParameter("IAMSEMember");
        if (IAMSEMemberValue != null) {
            IAMSEMemberS = true;
        }

        userName = request.getParameter("email");
        password = request.getParameter("password");
        passwordConfirm = request.getParameter("passwordconfirm");
        emailConfirm = request.getParameter("emailConfirm");

    
        professionalRoleOther = request.getParameter("professionalRoleOther");
		professionalRoleOtherText = request.getParameter("professionalRoleOtherText").trim(); 
        areaOfExpertise = request.getParameter("areaOfExpertise");
        firstName = request.getParameter("firstName").trim();
        lastName = request.getParameter("lastName").trim();
        mailingListS = request.getParameter("isMailingList");
        institutionName = request.getParameter("institutionName").trim();
        city = request.getParameter("city").trim();
        state = request.getParameter("state").trim();
        country = request.getParameter("country").trim();
        String minorS = (request.getParameter("aminor"));
	
        instructionalLevel = request.getParameterValues("instructionalLevel");
        professionalRole = request.getParameterValues("professionalRole");
  
		userName = user.getUserName();
        if (isStringEmpty(userName)) {
            errorMessages.addElement("Unable to determine the username, please logout.");
        }

		//we didn't receive a password setting from the form...
        if (isStringEmpty(password)) {
            //but we did get one for password confirm, so there is a mismatch      
            if (!isStringEmpty(passwordConfirm)) {
                errorMessages.addElement("Password and password confirmation do not match.");
            } 
            else {
                //both were empty, so we set the password to that in the bean
                password = user.getPassword();
            }
        }	
    
        else {
            //password entry in the form wasn't empty
            if (isStringEmpty(passwordConfirm)) {
                errorMessages.addElement("Password confirmation was empty.");
            } 
            else if (!passwordConfirm.equals(password)) {
            errorMessages.addElement("Password and password confirmation do not match.");
            }
        }
				
        if ((professionalRole = request.getParameterValues("professionalRole")) == null || professionalRole.length == 0 || professionalRole[0].length()==0) {
            errorMessages.addElement("Professional Role missing");
        }

        if(professionalRole!=null){
            for (int a=0;a<professionalRole.length;a++){
                if("Other".equals(professionalRole[a])){
                    if (professionalRoleOtherText==null||professionalRoleOtherText.length()==0){
                        errorMessages.addElement("Enter value for Professional Role: Other");
                    }					
                }
            }
		}
    
        if (isStringEmpty(areaOfExpertise)) {
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
	
        if (isStringEmpty(city)) {
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
            userEntry.setFirstName(firstName);
            userEntry.setMiddleInitial(request.getParameter("middleInitial"));
            userEntry.setLastName(lastName);		
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
			
            userEntry.setProfessionalSpecialty(areaOfExpertise);
            userEntry.setEmail(email);
            userEntry.setInstitutionName(institutionName);
            userEntry.setCountry(country);
            userEntry.setState(state);
            userEntry.setCity(city);
            userEntry.setIAMSEMember(IAMSEMemberS);
            //not being used but in DB
            /*
            userEntry.setAddress1(request.getParameter("address1"));
            userEntry.setAddress2(request.getParameter("address2"));
            userEntry.setZipCode(request.getParameter("zipCode"));
            */
      
            request.setAttribute("validUser", userEntry);
            // TODO move to config file instead of hardcoding			    
            rd = request.getRequestDispatcher("/user/update.jsp");
            rd.forward(request, response);
        }
									
        else {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setInstitutionName(institutionName);
            user.setState(state);
            user.setCity(city);
            user.setCountry(country);
            //user.setMiddleInitial(request.getParameter("middleInitial"));        
            //user.setAddress1(request.getParameter("address1"));
            //user.setAddress2(request.getParameter("address2"));
            //user.setZipCode(request.getParameter("zipCode"));        
        
			boolean minor = false;
			boolean mailingList = true;

            if ("yes".equals(minorS))
                minor = true;

            if ("no".equals(mailingListS))
                mailingList = false;
			
            user.setMinor(minor);
            user.setMailingList(mailingList);					
            user.setLoginModified(true);			
            user.setIAMSEMember(IAMSEMemberS);
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
     
            request.getSession().setAttribute("validUser", user);
            String from = request.getParameter("action");
            String origURL = request.getParameter("origURL");
            //<%-- Redirect to the notice page--%>
            // TODO move to config file instead of hardcoding			                
            String redirect = "../index.jsp";
				
            if ("modify".equals(from)){
                if ((origURL!=null) && (origURL.length()>0))
                    redirect = origURL;
            }
            // TODO move to config file instead of hardcoding			    
            response.sendRedirect("../user/update_response.jsp ");
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
