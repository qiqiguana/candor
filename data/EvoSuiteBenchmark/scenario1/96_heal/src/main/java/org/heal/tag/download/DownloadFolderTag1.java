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

    public int doEndTag() {
        // In case the servlet container re-uses this object
        resetTag();
        return EVAL_PAGE;
    }
}
