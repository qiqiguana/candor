package org.heal.servlet.search;

import java.io.*;
import org.heal.servlet.Action;
import javax.servlet.*;
import javax.servlet.http.*;

/**
 * @author Grace
 * @version 0.1
 */

public class EditAdvSearchAction implements Action {
    public EditAdvSearchAction() {
    }

    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String redirectURL = null;
        redirectURL = request.getParameter("pam");
        response.sendRedirect(redirectURL);
    }

    public boolean actionRequiresLogin() {
        return false;
    }

}