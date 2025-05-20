package org.heal.tag.download;

import org.heal.module.download.DownloadQueueBean;
import org.heal.module.metadata.ShortMetadataBean;
import org.heal.util.FileLocator;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * This class contains basic information about heal metadata.
 *
 * @author Seth Wright
 * @author Brad Schaefer (<A HREF="mailto:schaefer@lib.med.utah.edu">schaefer@lib.med.utah.edu</A>)
 * @see MetadataBean
 * @see CompleteMetadataBean
 */
public class ShortMetadataBean implements Serializable {

    /**
     * Returns the format property value.
     */
    public String getFormat();

    /**
     * Returns the location property value.
     */
    public String getLocation();

    /**
     * Returns the metadataId property value.
     */
    public String getMetadataId();
}

/**
 * Stores a list of contentIds and a package file location and url.
 *
 * @author Seth Wright
 * @version 0.1
 */
public class DownloadQueueBean implements Serializable {

    /**
     * Returns true if the specified metadataId is already in the queue.
     * Otherwise, it returns false.
     */
    public boolean isQueuedAlready(String contentId);
}

/**
 * A tag which is basically acts as a container for attributes which
 * nested tags will utilize.
 *
 * @version 1.0
 * @author Brad Schaefer (<A HREF="mailto:schaefer@lib.med.utah.edu">schaefer@lib.med.utah.edu</A>)
 * @see org.heal.tag.download.DownloadFolderActionTag
 */
public class DownloadFolderTag extends TagSupport {

    /**
     * Parses the attributes so as to initialize the
     * {@link #getDownloadFolderType() download folder type} for child tags
     * to use.
     *
     * @return Always returns <code>TagSupport.EVAL_BODY_INCLUDE</code>
     */
    public int doStartTag() {
        FileLocator locator = (FileLocator) pageContext.getServletContext().getAttribute("healFileLocator");
        if ("web page".equals(shortMetadata.getFormat().toLowerCase()) || !shortMetadata.getLocation().toLowerCase().startsWith(locator.getServerBaseURL())) {
            if ("image".equals(shortMetadata.getFormat().toLowerCase())) {
                downloadFolderType = REMOTE_IMAGE;
            } else {
                downloadFolderType = UNAVAILABLE;
            }
        } else if (downloadQueue.isQueuedAlready(shortMetadata.getMetadataId())) {
            downloadFolderType = REMOVE;
        } else {
            downloadFolderType = ADD;
        }
        return TagSupport.EVAL_BODY_INCLUDE;
    }
}

/**
 * This class translates URLs to file system paths and vice versa.
 *
 * @author Seth Wright
 * @version 0.1
 */
public class FileLocator implements Serializable {

    /**
     * Returns the server base URL property setting.
     * This is the base url on the server where all content
     * (uploaded, cataloged, approved - all content in the system).
     * This baseurl + the upload directory should point to the
     * upload directory in the webserver.
     */
    public String getServerBaseURL();
}
