package org.heal.servlet;

import org.heal.module.download.DownloadQueueBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RemoveFromDownloadAction implements Action {


    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {


        DownloadQueueBean downloadQueue = (DownloadQueueBean) request.getSession().getAttribute("downloadQueue");

        boolean removeAll = (request.getParameter("all") != null);
        String[] contentIds = request.getParameterValues("downloadIds");


        if (removeAll) {
            downloadQueue.removeAllFromQueue();
        } else if (contentIds != null) {
            for (int i = 0; i < contentIds.length; i++) {
                downloadQueue.removeFromQueue(contentIds[i]);
            }
        }


        String origURL = request.getParameter("origURL");
        if (origURL == null || origURL.length()<1) {
            origURL = "viewDownloadFolder";
        }
        System.err.println(origURL);
        response.sendRedirect(origURL);
    }

    public boolean actionRequiresLogin() {
        return true;
    }
    
        public boolean isAuthenticatedAction() {
        return false;
    }
}
