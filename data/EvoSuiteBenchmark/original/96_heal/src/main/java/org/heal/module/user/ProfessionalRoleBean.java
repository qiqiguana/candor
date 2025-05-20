package org.heal.module.user;

import java.io.Serializable;

/**
 * @version 1.0
 * @author Jason Varghese
 */
public class ProfessionalRoleBean implements Serializable {
    // properties
    private String professionalRoleId;
    private String userId;
    private String professionalRole;

    /**
     * @return The metadataId property value.
     */
    public String getProfessionalRoleId() {
        return professionalRoleId;
    }

    public void setProfessionalRoleId(String instructionalLevelId) {
        this.professionalRoleId = instructionalLevelId;
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
    public String getProfessionalRole() {
        return professionalRole;
    }

    public void setProfessionalRole(String professionalRole) {
        this.professionalRole = professionalRole;
    }

    public String toString() {
        return "ProfessionalRole:"+
                " professionalRole: " +getProfessionalRole()+
								" userId: " +getUserId()+
                " professionalRoleId: " +getProfessionalRoleId();
    }
}
