package org.heal.servlet.tagcloud;

import org.heal.servlet.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// used to bring up the user review form.
public class InputTagsFormAction implements Action {
    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        RequestDispatcher rd;
        String formURL = "/tagcloud/tagform.jsp?" + request.getQueryString();
        rd = request.getRequestDispatcher(formURL);
        rd.forward(request, response);
    }

    // login is required to view this page.
    public boolean actionRequiresLogin() {
        return true;
    }
}
