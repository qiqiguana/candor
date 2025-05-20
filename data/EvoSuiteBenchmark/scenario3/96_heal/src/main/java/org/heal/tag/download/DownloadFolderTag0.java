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

    /**
     * Parses the attributes so as to initialize the
     * {@link #getDownloadFolderType() download folder type} for child tags
     * to use.
     *
     * @return Always returns <code>TagSupport.EVAL_BODY_INCLUDE</code>
     */
    public int doStartTag();
}
