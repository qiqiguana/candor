package org.heal.servlet;


import com.ora.jsp.util.StringFormat;
import java.util.ArrayList;
import java.util.Iterator;
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
import java.io.*;
import java.sql.SQLException;
import java.util.Vector;
import org.heal.util.FileLocator;


public class RegistrationAction implements Action {


	public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

		String action = request.getParameter("action");
    Vector errorMessages = new Vector();
    UserRegistryBean userRegistry = (UserRegistryBean) servlet.getServletContext().getAttribute("userRegistry");
    RequestDispatcher rd;
		UserBean user = (UserBean) request.getSession().getAttribute("validUser");
    String firstName,lastName,middleInitial, professionalTitle,professionalSpecialty,areaOfExpertise,professionalRoleOther,professionalRoleOtherText,
			userName="",phoneNumber,email,aminor,institutionName,mailingListS="",
      address1,address2, postalCode, city, state, country, emailConfirm="",
      password="", passwordConfirm;
		String instructionalLevel[]=null, professionalRole[]=null;
		UserBean userEntry = new UserBean();
	

		if ("create".equals(action)) {
			user = new UserBean();
			try {
				if ((userName = request.getParameter("email")) == null ||
						userName.length() == 0) {
            errorMessages.addElement("E-mail address missing");
        } 
				else if (userRegistry.userExists(userName)) {
					errorMessages.addElement("User " + userName + " already exists, please try another email address.");
        }
				user.setUserName(userName);

				if ((password = request.getParameter("password")) == null || password.length() == 0) {
					errorMessages.addElement("Password missing");
        }
				if ((passwordConfirm = request.getParameter("passwordconfirm")) == null || passwordConfirm.length() == 0) {
					errorMessages.addElement("Password confirmation missing");
				}
			} 
			catch (SQLException ex) { 
			// do something
			}	    
		}
	
		if (action.equals("update") || action.equals("modify")) {
			userName = user.getUserName();
      if (userName == null || userName.length() == 0) {
					errorMessages.addElement("Unable to determine the username, please logout.");
      }
      password = request.getParameter("password");
      passwordConfirm = request.getParameter("passwordconfirm");
			
      if (password == null || password.length() == 0) {
				//we didn't receive a password setting from the form...
				if (passwordConfirm != null && passwordConfirm.length() > 0) {
					//but we did get one for password confirm, so there is a mismatch
					errorMessages.addElement("Password and password confirmation do not match.");
				} 
				else {
				//both were empty, so we set the password to that in the bean
				//if (action.equals("create"))
					password = user.getPassword();
				}
      } 
			else {
				//password entry in the form wasn't empty
        if (passwordConfirm == null || passwordConfirm.length() == 0) {
					errorMessages.addElement("Password confirmation was empty.");
        } 
				else if (!passwordConfirm.equals(password)) {
					errorMessages.addElement("Password and password confirmation do not match.");
        }
      }
		}
				
    emailConfirm = request.getParameter("emailConfirm");
    
    
		if(userName!=null && userName.length()!=0)
		{
		if ((!action.equals("update"))&&(!StringFormat.isValidEmailAddr(request.getParameter("email")))) {
			errorMessages.addElement("Invalid E-mail address");
    }
    
    if(!emailConfirm.equals(request.getParameter("email")))
    {
			errorMessages.addElement("Email confirmation does not match");
      
    }
    
		}
		

    if ((professionalRole = request.getParameterValues("professionalRole")) == null || professionalRole.length == 0 || professionalRole[0].length()==0) {
			errorMessages.addElement("Professional Role missing");
    }

    professionalRoleOther = request.getParameter("professionalRoleOther");
		professionalRoleOtherText = request.getParameter("professionalRoleOtherText").trim();

		if(professionalRole!=null)
		{
		for (int a=0;a<professionalRole.length;a++)
		{
		if("Other".equals(professionalRole[a]))
			{
				if (professionalRoleOtherText==null||professionalRoleOtherText.length()==0){
					errorMessages.addElement("Enter value for Professional Role: Other");
				}					
			}
		}
		}


		if ((areaOfExpertise = request.getParameter("areaOfExpertise")) == null || areaOfExpertise.length() == 0) {
			errorMessages.addElement("Area of Expertise missing");
    }
    if (action.equals("update") || action.equals("create")) {
		
			if ((firstName = request.getParameter("firstName").trim()) == null || firstName.length() == 0) {
				errorMessages.addElement("First Name missing");
      }
			
      if ((lastName = request.getParameter("lastName").trim()) == null || lastName.length() == 0) {
				errorMessages.addElement("Last Name missing");
			}
			
	    if ((mailingListS = request.getParameter("isMailingList")) == null || mailingListS.length() == 0) {
				errorMessages.addElement("Please indicate if you want to be on the mailing list");
      }					


	    if ((institutionName = request.getParameter("institutionName").trim()) == null || institutionName.length() == 0) {
				errorMessages.addElement("Institution Name missing");
      }	

			
	    if ((city = request.getParameter("city").trim()) == null || city.length() == 0) {
				errorMessages.addElement("City missing");
      }	
			
	    if ((state = request.getParameter("state").trim()) == null || state.length() == 0) {
				errorMessages.addElement("State missing");
      }	
			
			if ((country = request.getParameter("country").trim()) == null || country.length() == 0) {
				errorMessages.addElement("Country missing");
      }
		}

		if ((action.equals("create")) && (errorMessages.size() > 0)) {
			request.setAttribute("errorMessages", errorMessages);
      userEntry.setProfessionalSpecialty(areaOfExpertise);
      request.setAttribute("validUser", userEntry);
			
			ArrayList ilb = new ArrayList();
			instructionalLevel = request.getParameterValues("instructionalLevel");
			if ((instructionalLevel  != null && instructionalLevel.length > 0)) {
				for (int a=0;a<instructionalLevel.length;a++)
				{
					if(instructionalLevel[a].length()>0){
						//InstructionalLevelBean ilb = new InstructionalLevelBean();
						ilb.add(instructionalLevel[a]);
						//userEntry.addInstructionalLevel(ilb);
					}
				}
			}

			ArrayList prb = new ArrayList();
			if ((professionalRole  != null && professionalRole.length > 0)) {			
				for (int a=0;a<professionalRole.length;a++)
				{  
					if(professionalRole[a].length()>0){				
						//ProfessionalRoleBean prb = new ProfessionalRoleBean();

							prb.add(professionalRole[a]);
						
						//userEntry.addProfessionalRole(prb);
					}
				}
			}
			request.setAttribute("professionalRoleOtherText",professionalRoleOtherText);			
			request.setAttribute("prb",prb);
			request.setAttribute("ilb",ilb);

			
      rd = request.getRequestDispatcher("/user/register.jsp");
      rd.forward(request, response);
      return;
    } 
		else if ((action.equals("update")) && (errorMessages.size() > 0)) {
			request.setAttribute("errorMessages", errorMessages);
      //UserBean userEntry = new UserBean();
      userEntry.setFirstName(request.getParameter("firstName"));
      userEntry.setMiddleInitial(request.getParameter("middleInitial"));
      userEntry.setLastName(request.getParameter("lastName"));
			
			ArrayList ilb = new ArrayList();
			instructionalLevel = request.getParameterValues("instructionalLevel");
			if ((instructionalLevel  != null && instructionalLevel.length > 0)) {
				for (int a=0;a<instructionalLevel.length;a++)
				{
					if(instructionalLevel[a].length()>0){
						//InstructionalLevelBean ilb = new InstructionalLevelBean();
						ilb.add(instructionalLevel[a]);
						//userEntry.addInstructionalLevel(ilb);
					}
				}
			}

			ArrayList prb = new ArrayList();
			if ((professionalRole  != null && professionalRole.length > 0)) {			
				for (int a=0;a<professionalRole.length;a++)
				{  
					if(professionalRole[a].length()>0){				
						//ProfessionalRoleBean prb = new ProfessionalRoleBean();

							prb.add(professionalRole[a]);
						
						//userEntry.addProfessionalRole(prb);
					}
				}
			}
			request.setAttribute("professionalRoleOtherText",professionalRoleOtherText);
			request.setAttribute("prb",prb);
			request.setAttribute("ilb",ilb);
			
      userEntry.setProfessionalSpecialty(areaOfExpertise);
      userEntry.setEmail(request.getParameter("email"));
      userEntry.setInstitutionName(request.getParameter("institutionName"));
      userEntry.setAddress1(request.getParameter("address1"));
      userEntry.setAddress2(request.getParameter("address2"));
      userEntry.setState(request.getParameter("state"));
      userEntry.setCity(request.getParameter("city"));
      userEntry.setZipCode(request.getParameter("zipCode"));
      userEntry.setCountry(request.getParameter("country"));
			
      request.setAttribute("validUser", userEntry);
      rd = request.getRequestDispatcher("/user/update.jsp");
      rd.forward(request, response);
      }
				
		else if ((action.equals("modify")) && (errorMessages.size() > 0)) {
			request.setAttribute("errorMessages", errorMessages);
      //UserBean userEntry = new UserBean();
      userEntry.setFirstName(request.getParameter("firstName"));
      userEntry.setMiddleInitial(request.getParameter("middleInitial"));
      userEntry.setLastName(request.getParameter("lastName"));
			//System.err.println("hereee");

			ArrayList ilb = new ArrayList();
			instructionalLevel = request.getParameterValues("instructionalLevel");
			if ((instructionalLevel  != null && instructionalLevel.length > 0)) {
				for (int a=0;a<instructionalLevel.length;a++)
				{
					if(instructionalLevel[a].length()>0){
						//InstructionalLevelBean ilb = new InstructionalLevelBean();
						ilb.add(instructionalLevel[a]);
						//userEntry.addInstructionalLevel(ilb);
					}
				}
			}

			ArrayList prb = new ArrayList();
			if ((professionalRole  != null && professionalRole.length > 0)) {			
				for (int a=0;a<professionalRole.length;a++)
				{  
					if(professionalRole[a].length()>0){				
						//ProfessionalRoleBean prb = new ProfessionalRoleBean();
						
							prb.add(professionalRole[a]);
						
						//userEntry.addProfessionalRole(prb);
					}
				}
			}
			request.setAttribute("professionalRoleOtherText",professionalRoleOtherText);			
			request.setAttribute("prb",prb);
			request.setAttribute("ilb",ilb);
      userEntry.setProfessionalSpecialty(areaOfExpertise);
      userEntry.setEmail(request.getParameter("email"));
			//userEntry.setEmail(user.getEmail());
      userEntry.setInstitutionName(request.getParameter("institutionName"));
      userEntry.setAddress1(request.getParameter("address1"));
      userEntry.setAddress2(request.getParameter("address2"));
      userEntry.setState(request.getParameter("state"));
      userEntry.setCity(request.getParameter("city"));
      userEntry.setZipCode(request.getParameter("zipCode"));
      userEntry.setCountry(request.getParameter("country"));
				
      request.setAttribute("validUser", userEntry);
			rd = request.getRequestDispatcher("/user/modifyLogin.jsp");
      rd.forward(request, response);
    }
							
		else {
			if (action.equals("update") || action.equals("create")){
				user.setFirstName(request.getParameter("firstName").trim());
        user.setMiddleInitial(request.getParameter("middleInitial"));
        user.setLastName(request.getParameter("lastName").trim());
        user.setInstitutionName(request.getParameter("institutionName").trim());
        user.setAddress1(request.getParameter("address1"));
        user.setAddress2(request.getParameter("address2"));
        user.setState(request.getParameter("state").trim());
        user.setCity(request.getParameter("city").trim());
        user.setZipCode(request.getParameter("zipCode"));
        //user.setLoginModified(true);
        user.setCountry(request.getParameter("country").trim());
				//System.err.println("status="+user.isEmailValidated());
				boolean minor = false;
				boolean mailingList = true;

				
				String minorS = (request.getParameter("aminor"));
				if ("yes".equals(minorS))
				minor = true;

				if ("no".equals(mailingListS))
					mailingList = false;
			
				user.setMinor(minor);
				user.setMailingList(mailingList);					
				}
				user.setLoginModified(true);			
				
				instructionalLevel = request.getParameterValues("instructionalLevel");
				user.setInstructionalLevel(new ArrayList());
				if ((instructionalLevel  != null && instructionalLevel.length > 0)) {
				
					for (int a=0;a<instructionalLevel.length;a++)
					{
						if(instructionalLevel[a].length()>0){
							InstructionalLevelBean ilb = new InstructionalLevelBean();
							ilb.setInstructionalLevel(instructionalLevel[a]);
							user.addInstructionalLevel(ilb);
						}
					}
				}
				
				if ((professionalRole  != null && professionalRole.length > 0)) {
					user.setProfessionalRole(new ArrayList());
					for (int a=0;a<professionalRole.length;a++)
					{
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
			if(action.equals("create"))
				user.setEmail(request.getParameter("email"));
				
      user.setPassword(password);	
		

						

			String userId = "";
			String userId2 = "";
			try {
				userId2 = userRegistry.saveRegistration(user);
      } 
			catch (SQLException e) {
				System.err.println(e.toString());
      }
			
			userId = user.getUserId();
			if(userId==null || userId.length()==0)
			{
				userId=userId2;
			}
			
			
			if (action.equals("modify") || action.equals("create")){	
				NotificationServicesBean nsb = (NotificationServicesBean) servlet.getServletContext().getAttribute("notificationServices");
				FileLocator healFileLocator = (FileLocator) servlet.getServletContext().getAttribute("healFileLocator");
				try{
				nsb.sendUsernameValidationEmail(user,healFileLocator.getServerBaseURL()+"/healapp/emailValidation?emailId="+userId+"&email="+user.getEmail());
				}
				catch(MessagingException m)
				{
					m.printStackTrace();
				}
			}			
			
			
            
			request.getSession().setAttribute("validUser", user);

      String from = request.getParameter("action");
			String origURL = request.getParameter("origURL");
			//<%-- Redirect to the notice page--%>
			String redirect = "../index.jsp";
				
			if ("modify".equals(from)){
				if ((origURL!=null) && (origURL.length()>0))
					redirect = origURL;
			}
				
			if ("modify".equals(from)) {
				response.sendRedirect(redirect);
      if ("update".equals(from)) 
				response.sendRedirect("../user/update_response.jsp ");
      } 
			else {
				response.sendRedirect("../user/register_response.jsp");
      }
		}
	}
	public boolean actionRequiresLogin() {
			return false;
  }
		//response.sendRedirect(redirectURL);
}