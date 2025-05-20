package org.heal.servlet;

import org.heal.module.download.DownloadQueueBean;
import org.heal.module.download.DownloadDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DownloadAction implements Action {
    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        DownloadQueueBean downloadQueue = (DownloadQueueBean)request.getSession().getAttribute("downloadQueue");
        // DownloadServicesBean downloadServices = (DownloadServicesBean) servlet.getServletContext().getAttribute("downloadServices");
        DownloadDAO downloadServices = (DownloadDAO)servlet.getServletContext().getAttribute("downloadDAO");
        if(downloadQueue == null) {
            downloadQueue = new DownloadQueueBean();
            request.getSession().setAttribute("downloadQueue", downloadQueue);
        }
        String format = request.getParameter("packageFormat");
        String url = new String();
        if(format == null) {
            //default format is ZIP
            format = org.heal.module.download.DownloadDAO.ZIPFORMAT;
        }
        try {
            url = downloadServices.createPackage(downloadQueue, format);
        } catch(java.io.IOException ex) {
            ex.printStackTrace();
        } catch(java.sql.SQLException ex) {
            ex.printStackTrace();
        }
        if(url == null) {
            url = "../downloads/viewqueue.jsp";
        }
        response.sendRedirect(url);
    }

    public boolean actionRequiresLogin() {
        return true;
    }
}
