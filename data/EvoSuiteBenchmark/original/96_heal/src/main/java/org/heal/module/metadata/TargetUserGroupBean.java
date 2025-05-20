package org.heal.module.metadata;

import java.io.Serializable;

/**
 * @version 1.0
 * @author Brad Schaefer (<A HREF="mailto:schaefer@libscan.med.utah.edu">schaefer@libscan.med.utah.edu</A>) 
 */
public class TargetUserGroupBean implements Serializable {
    // properties
    private String metadataId;
    private String targetUserGroup;

    /**
     * @return The metadataId property value.
     */
    public String getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(String metadataId) {
        this.metadataId = metadataId;
    }

    /**
     * @return The targetUserGroupId property value.
     */
    public String getTargetUserGroup() {
        return targetUserGroup;
    }

    public void setTargetUserGroup(String targetUserGroup) {
        this.targetUserGroup = targetUserGroup;
    }

    public String toString() {
        return "TargetUserGroup:"+
                " targetUserGroup: " +getTargetUserGroup()+
                " metadataId: " +getMetadataId();
    }
}
