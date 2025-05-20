package org.heal.servlet.cataloger;

import org.heal.module.catalog.QueueDAO;
import org.heal.module.catalog.QueuedRecordBean;
import org.heal.module.metadata.MetadataDAO;
import org.heal.module.metadata.ShortMetadataBean;
import org.heal.servlet.Action;
import org.heal.util.AuthenticationTools;
import org.heal.util.FileLocator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.SQLException;

/**
 * An {@link org.heal.servlet.Action Action} which is called in order to edit a metadata item which is in
 * the catalog queue.
 *
 * @author Brad Schaefer (<A HREF="mailto:schaefer@lib.med.utah.edu">schaefer@lib.med.utah.edu</A>)
 * @version 1.0
 */
public class ModifyCatalogQueueEntryAction implements Action {
    public void perform(HttpServlet servlet, HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        final String catalogQueueEntryId = request.getParameter("catalogQueueEntryId");
        final String status = request.getParameter("status");

        if(!AuthenticationTools.isCataloger(request)
                || null == catalogQueueEntryId || null == status) {
            // The user does not have the access to view this page
            // or the Catalog Queue entry id or status is missing,
            // so we go no further, and redirect them to an access denied page
            // TODO instead of hardcoding page locations, this should be in config files somewhere
            response.sendRedirect("/error/accessDenied.jsp");
            return;
        }

        final String metadataId = request.getParameter("metadataId");
        final QueueDAO queueManager =
                (QueueDAO)servlet.getServletContext().getAttribute("QueueDAO");

        try {
            final String statusTest = status.toLowerCase();
            if("waiting".equals(statusTest) || "incomplete".equals(statusTest)) {
                // If the status is 'waiting', or incomplete, we just save the catalog queue entry

// Creates an QueuedRecordBean based on the form data
                final QueuedRecordBean catalogEntry = new QueuedRecordBean();
                catalogEntry.setQueuedRecordId(catalogQueueEntryId);
                catalogEntry.setComments(request.getParameter("comment"));
                catalogEntry.getShortMetadata().setMetadataId(metadataId);
                catalogEntry.setStatus(status);
                catalogEntry.setType(QueueDAO.TYPE_CATALOG);

                queueManager.saveQueuedRecord(catalogEntry);
            } else if("cataloged".equals(statusTest)) {
// If the status is 'cataloged' we mark it as such in the database,
// and copy the file to the content directory

                MetadataDAO metadataServices =
                        (MetadataDAO)servlet.getServletContext().getAttribute("MetadataDAO");
                FileLocator fileLocator = (FileLocator)servlet.getServletContext().getAttribute("healFileLocator");
                ShortMetadataBean metadata = metadataServices.getShortMetadata(metadataId);
                queueManager.markRecordCataloged(metadataId);
                queueManager.dequeue(catalogQueueEntryId);

                String sourceFile = fileLocator.getUploadFilePath(metadata.getLocation());
                String destinationFile = fileLocator.getContentFilePath(metadata.getLocation());
                if(!new File(destinationFile).exists()) {
                    copyFile(sourceFile, destinationFile);
                }
            }
        } catch(SQLException e) {
            // TODO logging?
        }

        // TODO instead of hardcoding page locations, this should be in config files somewhere
        response.sendRedirect("showCatalogQueue");
    }

    /**
     * Copies a file from a sourceLocation to a destinationLocation.
     *
     * @param sourceLocation
     * @param destinationLocation
     */
    private static void copyFile(String sourceLocation, String destinationLocation) {
        FileChannel srcChannel = null;
        FileChannel dstChannel = null;
        try {
            // Create channel on the source
            srcChannel = new FileInputStream(sourceLocation).getChannel();

            // Create channel on the destination
            dstChannel = new FileOutputStream(destinationLocation).getChannel();

            // Copy file contents from source to destination
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            // Close the channels
            if(null != srcChannel) {
                try {
                    srcChannel.close();
                } catch(IOException e) {
                }
            }
            if(null != dstChannel) {
                try {
                    dstChannel.close();
                } catch(IOException e) {
                }
            }
        }
    }

    /**
     * @return <code>true</code>
     */
    public boolean actionRequiresLogin() {
        return true;
    }
}
