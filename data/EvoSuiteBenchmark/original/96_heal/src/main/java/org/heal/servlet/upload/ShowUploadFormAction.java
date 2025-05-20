package org.heal.servlet.upload;

import org.heal.servlet.Action;
import org.heal.util.DateTools;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;


public class ShowUploadFormAction implements Action {
    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String continueSubmission = request.getParameter("continueSubmission");
        Map storedParameters = (Map)request.getSession().getAttribute("requestParameters");
        if("samePub".equals(continueSubmission) && storedParameters != null) {
            SimpleUploadAction.setupAttributes(request, storedParameters);
        } else {
            request.setAttribute("creationDate", DateTools.format(new Date()));
        }
        request.getSession().removeAttribute("requestParameters");
        RequestDispatcher rd = request.getRequestDispatcher("/upload2/uploadform.jsp");
        rd.forward(request, response);
    }

    public boolean actionRequiresLogin() {
        return true;
    }
}

