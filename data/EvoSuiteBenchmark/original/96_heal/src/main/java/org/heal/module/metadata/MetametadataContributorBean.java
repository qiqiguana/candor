package org.heal.module.metadata;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a Metametadata Contributor.
 */
public class MetametadataContributorBean implements Serializable {
    private String metadataId;
    private String metametadataContributorId;
    private String role;
    private Date date;
    private String dateDescription;
    private String vCard;

    public MetametadataContributorBean() { }

    public String getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(String metadataId) {
        this.metadataId = metadataId;
    }

    public String getMetametadataContributorId() {
        return metametadataContributorId;
    }

    public void setMetametadataContributorId(String metametadataContributorId) {
        this.metametadataContributorId = metametadataContributorId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateDescription() {
        return dateDescription;
    }

    public void setDateDescription(String dateDescription) {
        this.dateDescription = dateDescription;
    }

    public String getvCard() {
        return vCard;
    }

    public void setvCard(String vCard) {
        this.vCard = vCard;
    }

    public String toString() {
        return "MetametadataContributorBean{" +
                "metadataId='" + metadataId + "'" +
                ", metametadataContributorId='" + metametadataContributorId + "'" +
                ", role='" + role + "'" +
                ", date=" + (date != null ? date.toString() : date) +
                ", dateDescription='" + dateDescription + "'" +
                ", vCard=" + vCard.toString() +
                "}";
    }
}
