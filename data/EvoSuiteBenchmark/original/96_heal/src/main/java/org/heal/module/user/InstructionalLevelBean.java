package org.heal.module.user;

import java.io.Serializable;

/**
 * @version 1.0
 * @author Jason Varghese
 */
public class InstructionalLevelBean implements Serializable {
    // properties
    private String instructionalLevelId;
    private String userId;
    private String instructionalLevel;

    /**
     * @return The metadataId property value.
     */
    public String getInstructionalLevelId() {
        return instructionalLevelId;
    }

    public void setInstructionalLevelId(String instructionalLevelId) {
        this.instructionalLevelId = instructionalLevelId;
    }

    /**
     * @return The metadataId property value.
     */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }		

    /**
     * @return The targetUserGroupId property value.
     */
    public String getInstructionalLevel() {
        return instructionalLevel;
    }

    public void setInstructionalLevel(String instructionalLevel) {
        this.instructionalLevel = instructionalLevel;
    }

    public String toString() {
        return "InstructionalLevel:"+
                " instructionalLevel: " +getInstructionalLevel()+
								" userId: " +getUserId()+
                " instructionalLevelId: " +getInstructionalLevelId();
    }
}
