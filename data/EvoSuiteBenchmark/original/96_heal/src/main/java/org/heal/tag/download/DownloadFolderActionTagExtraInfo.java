package org.heal.tag.download;

import javax.servlet.jsp.tagext.TagExtraInfo;
import javax.servlet.jsp.tagext.TagData;

/**
 * A <code>TagExtraInfo</code> class for {@link DownloadFolderActionTag} to
 * validate the type attribute of the tag.
 *
 * @version 1.0
 * @author Brad Schaefer (<A HREF="mailto:schaefer@lib.med.utah.edu">schaefer@lib.med.utah.edu</A>)
 * @see DownloadFolderActionTag
 */
public class DownloadFolderActionTagExtraInfo extends TagExtraInfo {
    /**
     * @param tagData
     * @return Returns <code>true</code> only if the tagData has a valid
     *      'type' attribute.
     */
    public boolean isValid(TagData tagData) {
        boolean ret = false;

        Object temp = tagData.getAttribute("type");
        if(temp instanceof String) {
            String type = (String)temp;
            type = type.toLowerCase(); // type constants are known to be lowercase

            if(DownloadFolderActionTag.TYPE_ADD.equals(type) ||
                    DownloadFolderActionTag.TYPE_REMOVE.equals(type) ||
                    DownloadFolderActionTag.TYPE_UNAVAILABLE.equals(type) ||
                    DownloadFolderActionTag.TYPE_REMOTE_IMAGE.equals(type)) {
                ret = true; // this means the tagData is valid!
            }
        }
        return ret;
    }
}
