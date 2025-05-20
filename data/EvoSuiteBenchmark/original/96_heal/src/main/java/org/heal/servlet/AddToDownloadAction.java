package org.heal.servlet;

import org.heal.module.download.DownloadQueueBean;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AddToDownloadAction implements Action {

private final static String VIEW_QUEUE_URL = "viewqueue.jsp";

    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        DownloadQueueBean downloadQueue = (DownloadQueueBean) request.getSession().getAttribute("downloadQueue");
        String[] downloadIds = request.getParameterValues("downloadIds");
        if (downloadIds != null && downloadIds.length > 0) {
            for (int i = 0; i < downloadIds.length; i++) {
                downloadQueue.addToQueue(downloadIds[i]);
            }
        }
        String origURL = request.getParameter("origURL");
            if (origURL == null) {
                origURL = VIEW_QUEUE_URL;
            }
        response.sendRedirect(origURL);
        return;
    }

    public boolean actionRequiresLogin() {
        return true;
    }
}
