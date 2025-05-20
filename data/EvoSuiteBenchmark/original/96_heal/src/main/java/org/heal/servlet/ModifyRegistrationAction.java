package org.heal.servlet;

import com.ora.jsp.util.StringFormat;
import java.util.Vector;
import org.heal.module.user.UserBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.heal.module.user.UserRegistryBean;

public class ModifyRegistrationAction implements Action {
    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
						
		String username = request.getParameter("username");
		String action = request.getParameter("action");
		if ("skip".equals(action)){
		
			UserBean user = (UserBean)request.getSession().getAttribute("validUser");
			user.setLoginModified(true);		
			String origURL = request.getParameter("origURL");
			if(origURL != null && origURL.length() > 0) 
			response.sendRedirect(origURL);
			else 
			response.sendRedirect("../index.jsp");
			return;			
		}
		
		if ("reset".equals(action)){
			UserRegistryBean userRegistry = (UserRegistryBean)servlet.getServletContext().getAttribute("userRegistry");
			userRegistry.reset(username);

			response.sendRedirect("../index.jsp");
			return;			
		}		
		
		
		
}

    public boolean actionRequiresLogin() {
        return true;
    }
}