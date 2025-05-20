package org.heal.servlet;

import java.util.ArrayList;
import java.util.Vector;
import org.heal.module.user.ProfessionalRoleBean;
import org.heal.module.user.UserBean;
import org.heal.module.user.UserRegistryBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.net.URLEncoder;

public class AuthenticateAction implements Action {
	private final static String ENCODING = "UTF-8";

	public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        
        request.getSession().removeAttribute("validUser");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String origURL = request.getParameter("origURL");
		boolean usernameIsEmail = false;
		if (userName!=null && userName.indexOf('@')>0)
			usernameIsEmail = true;

		if(userName.length() == 0 || password.length() == 0) {
            String query = "../user/login.jsp?errorMsg=" 
                + URLEncoder.encode("You must enter a Username and Password.", ENCODING);

			if(origURL != null && origURL.length() > 0) {
				query = query + "&origURL=" + URLEncoder.encode(origURL, ENCODING);
			}

			response.sendRedirect(query);
			return;
		}

		UserRegistryBean userRegistry = (UserRegistryBean)servlet.getServletContext().getAttribute("userRegistry");
		UserBean user;
		String query="";
		try {
			if(null == (user = userRegistry.verifyLogin(userName, password, usernameIsEmail))) {
				query = "../user/login.jsp?errorMsg="
					  + URLEncoder.encode("The Username and Password you entered is not valid.", ENCODING);

				if(origURL != null && origURL.length() > 0) {
					query = query + "&origURL=" + URLEncoder.encode(origURL, ENCODING);
				}

				response.sendRedirect(query);
				return;
			} else {
				request.getSession().setAttribute("validUser", user);
				boolean modifiedLogin = user.isLoginModified();
				if (!modifiedLogin){
					query = "../healapp/showRegistrationInfo?type=modify";
					if (origURL !=null && origURL.length()>0)
						query = query + "&origURL=" + URLEncoder.encode(origURL, ENCODING);
					response.sendRedirect(query);
					return;					
				}
				if(!usernameIsEmail) {
                    request.getSession().invalidate();
					query = "../user/login.jsp?errorMsg="
					  + URLEncoder.encode("The username name you entered is invalid. <br> Your username is now your email address.")+ "&origURL=" + URLEncoder.encode(origURL, ENCODING);
                    response.sendRedirect(query);
                    return;								
				}

				boolean emailValidated = user.isEmailValidated();						
				if(!emailValidated) {
                    request.getSession().invalidate();
					query = "../user/login.jsp?errorMsg="
					  + URLEncoder.encode("The email address you entered has not been validated. <br> Please click on the link sent to your email inbox.")+ "&origURL=" + URLEncoder.encode(origURL, ENCODING);
                    response.sendRedirect(query);
                    return;								
				}						

				/*
				  Redirect to the main page or to the original URL, if
				  invoked as a result of a access attempt to a protected
				  page.
				*/
				if(origURL != null && origURL.length() > 0) {
					response.sendRedirect(origURL);
				} else {
					response.sendRedirect("../index.jsp");
				}
            }  
		} catch(SQLException ex) {
			throw new ServletException(ex);
		}
	}

	public boolean actionRequiresLogin() {
		return false;
	}
}
