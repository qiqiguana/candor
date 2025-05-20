package org.heal.servlet;

import org.heal.module.download.DownloadQueueBean;
import org.heal.module.download.DownloadDAO;
import org.heal.module.metadata.MetadataDAO;
import org.heal.module.metadata.ShortMetadataBean;
import org.heal.module.metadata.ThumbnailBean;
import org.heal.util.FileLocator;
import org.heal.util.InterfaceUtilitiesBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.Iterator;

public class ViewDownloadFolderAction implements Action {
    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        DownloadQueueBean downloadQueue = (DownloadQueueBean)request.getSession().getAttribute("downloadQueue");
        if(downloadQueue == null) {
            downloadQueue = new DownloadQueueBean();
            request.getSession().setAttribute("downloadQueue", downloadQueue);
        }
        MetadataDAO metadataServices = (MetadataDAO)servlet.getServletContext().getAttribute("MetadataDAO");
        //MetadataServicesBean metadataServices = (MetadataServicesBean) servlet.getServletContext().getAttribute("metadataServices");
        InterfaceUtilitiesBean interfaceUtilities = (InterfaceUtilitiesBean)servlet.getServletContext().getAttribute("interfaceUtilities");
        FileLocator healFileLocator = (FileLocator)servlet.getServletContext().getAttribute("healFileLocator");
        Collection contentIds;
        Iterator idIterator = null;
        String metadataId = null;
        contentIds = downloadQueue.getContentIds();
        idIterator = contentIds.iterator();
        ShortMetadataBean[] currentMeta = null;
        int i = 0;
        //ShortMetadataBean currentMeta;
        if(contentIds.size() > 0)
            currentMeta = new ShortMetadataBean[contentIds.size()];
        while(idIterator.hasNext()) {
            metadataId = (String)idIterator.next();
            //System.err.println(metadataId);
            try {
                currentMeta[i] = metadataServices.getShortMetadata(metadataId);
                //System.err.println("n" + currentMeta[i].getMetadataId());
                String format = currentMeta[i].getFormat();
                if(format == null) {
                    format = "unknown";
                    currentMeta[i].setFormat(format);
                }
                ThumbnailBean thumbnail = interfaceUtilities.getThumbnail(currentMeta[i].getThumbnail(), format, "../");
                currentMeta[i].setThumbnail(thumbnail);
                currentMeta[i].setLocation(healFileLocator.getContentURL(currentMeta[i].getLocation()));
                currentMeta[i].setDescription(interfaceUtilities.getAbbreviatedString(currentMeta[i].getDescription(), 100));
                currentMeta[i].setFileSize(NumberFormat.getInstance().format(Long.parseLong(currentMeta[i].getFileSize())));
            } catch(SQLException e) {
                System.err.println(e);
            }
            i++;
        }
        String origURL = "../healapp/viewDownloadFolder";//+"?"+request.getQueryString();
        request.setAttribute("origURL", origURL);
        request.setAttribute("currentMeta", currentMeta);
        String forwardURL = "/download/viewqueue.jsp";
        RequestDispatcher rd;
        rd = request.getRequestDispatcher(forwardURL);
        rd.forward(request, response);
        return;
    }

    public boolean actionRequiresLogin() {
        return true;
    }
}
