package org.heal.module.catalog;

import org.heal.module.metadata.ShortMetadataBean;

import java.io.Serializable;

/**
 * A JavaBean that stores information about a queued metadata record.
 */
public class QueuedRecordBean implements Serializable {
    private String queuedRecordId;
    private ShortMetadataBean shortMetadata = new ShortMetadataBean();
    private String type;
    private String status;
    private String comments;

    public QueuedRecordBean() {
    }

    public String getQueuedRecordId() {
        return queuedRecordId;
    }

    public void setQueuedRecordId(final String queuedRecordId) {
        this.queuedRecordId = queuedRecordId;
    }

    public ShortMetadataBean getShortMetadata() {
        return shortMetadata;
    }

    public void setShortMetadata(final ShortMetadataBean shortMetadata) {
        this.shortMetadata = shortMetadata;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(final String comments) {
        this.comments = comments;
    }
}
