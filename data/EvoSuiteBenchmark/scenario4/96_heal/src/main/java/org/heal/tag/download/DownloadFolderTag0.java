package org.heal.tag.download;

import org.heal.module.download.DownloadQueueBean;
import org.heal.module.metadata.ShortMetadataBean;
import org.heal.util.FileLocator;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * A tag which is basically acts as a container for attributes which
 * nested tags will utilize.
 *
 * @version 1.0
 * @author Brad Schaefer (<A HREF="mailto:schaefer@lib.med.utah.edu">schaefer@lib.med.utah.edu</A>)
 * @see org.heal.tag.download.DownloadFolderActionTag
 */
public class DownloadFolderTag extends TagSupport {

    public static final int UNKNOWN = -1;

    public static final int UNAVAILABLE = 0;

    public static final int ADD = 1;

    public static final int REMOVE = 2;

    public static final int REMOTE_IMAGE = 3;

    private DownloadQueueBean downloadQueue = null;

    private ShortMetadataBean shortMetadata = null;

    private int downloadFolderType = UNAVAILABLE;

    public void setDownloadQueue(DownloadQueueBean downloadQueue);

    public void setShortMetadata(ShortMetadataBean shortMetadata);

    /**
     * This is perhaps poorly named, but this will return an
     * integer representing whether or not a metadata record may
     * be added to the download folder, removed from the download folder,
     * or if the download folder is unavailable for the metadata record.
     *
     * @return {@link #UNAVAILABLE DownloadFolderTag.UNAVAILABLE} or
     * {@link #ADD DownloadFolderTag.ADD} or {@link #REMOVE DownloadFolderTag.REMOVE} or
     * {@link #REMOTE_IMAGE DownloadFolderTag.REMOTE_IMAGE}.
     */
    public int getDownloadFolderType();

    /**
     * Parses the attributes so as to initialize the
     * {@link #getDownloadFolderType() download folder type} for child tags
     * to use.
     *
     * @return Always returns <code>TagSupport.EVAL_BODY_INCLUDE</code>
     */
    public int doStartTag();

    /**
     * Resets the internal state of the tag.
     *
     * @return Always returns <code>TagSupport.EVAL_PAGE</code>
     */
    public int doEndTag();

    /**
     * Resets the tag to its initial state.
     */
    private void resetTag();
}
