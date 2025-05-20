package org.heal.servlet.admin;
import java.io.IOException;
import java.util.Vector;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.heal.servlet.*;
import org.heal.util.AuthenticationTools;
import org.heal.util.CommonDAO;

public class LinkCheckerAction implements Action {
	public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {
    	if(!AuthenticationTools.isAdministrator(request)) {
			// The user does not have the access to view this page
			// so we go no further, and redirect them to an access denied page
			// TODO instead of hardcoding page locations, this should be in config files somewhere
			response.sendRedirect("/error/accessDenied.jsp");
			return;
		}

    }

    public boolean actionRequiresLogin() {
      return false;
	}
}
