package org.heal.tag.download;

import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * A tag nested under a {@link org.heal.tag.download.DownloadFolderTag}
 * which will evaluate the body of this tag iff the parent tag's attributes
 * indicate that it matches the type of download folder action represented
 * by this object.
 *
 * @version 1.0
 * @author Brad Schaefer (<A HREF="mailto:schaefer@lib.med.utah.edu">schaefer@lib.med.utah.edu</A>)
 * @see org.heal.tag.download.DownloadFolderTag
 */
public class DownloadFolderActionTag extends BodyTagSupport {
    private int type = DownloadFolderTag.UNKNOWN;

    public static final String TYPE_ADD = "add";
    public static final String TYPE_REMOVE = "remove";
    public static final String TYPE_UNAVAILABLE = "unavailable";
    public static final String TYPE_REMOTE_IMAGE = "remote image";

    public void setType(String type) {
        if(null != type) {
            type = type.toLowerCase();
        }
        if(TYPE_ADD.equals(type)) {
            this.type = DownloadFolderTag.ADD;
        } else if(TYPE_REMOVE.equals(type)) {
            this.type = DownloadFolderTag.REMOVE;
        } else if(TYPE_UNAVAILABLE.equals(type)) {
            this.type = DownloadFolderTag.UNAVAILABLE;
        } else if(TYPE_REMOTE_IMAGE.equals(type)) {
            this.type = DownloadFolderTag.REMOTE_IMAGE;
        }
    }

    public int doStartTag() {
        int tagAction = SKIP_BODY;

        DownloadFolderTag parent = (DownloadFolderTag)findAncestorWithClass(
                this, DownloadFolderTag.class);

        if(null != parent) {
            if(parent.getDownloadFolderType() == type) {
                tagAction = EVAL_BODY_INCLUDE;
            }
        }

        return tagAction;
    }
}
