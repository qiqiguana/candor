package org.heal.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class LogoutAction implements Action {


    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        request.getSession().invalidate();
        response.sendRedirect("../user/logout_response.jsp");
    }

    public boolean actionRequiresLogin() {
        return false;
    }
}