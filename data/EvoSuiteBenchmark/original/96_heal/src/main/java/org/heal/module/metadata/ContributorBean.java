package org.heal.module.metadata;

import java.io.*;
import java.util.*;

/**
 * This class contains information about a contributor.
 *
 * @author Seth Wright
 * @version 0.1
 */
public class ContributorBean implements Serializable, Comparable {
    // Properties
    private String contributorId = null;
    private String LastName;
    private String FirstName;
    private String Title;
    private String Organization;
    private String Phone;
    private String Email;
    private boolean CopyrightHolder;
    private String metadataId = null;
    private String role = null;
    private String vCard = null;
    private Date date = null;
    private String dateDescription = null;
    private String version = null;
    private String status = null;

    /**
     * Returns the contributorId property value.
     */
    public String getContributorId() {
        return this.contributorId;
    }

    /**
     * Sets the contributorId property value.
     */
    public void setContributorId(String newContributorId) {
        this.contributorId = newContributorId;
    }

    /**
     * Returns the metadataId property value.
     */
    public String getMetadataId() {
        return this.metadataId;
    }

    /**
     * Sets the metadataId property value.
     */
    public void setMetadataId(String newMetadataId) {
        this.metadataId = newMetadataId;
    }

    /**
     * Returns the role property value.
     */
    public String getRole() {
        return this.role;
    }

    /**
     * Sets the role property value.
     */
    public void setRole(String newRole) {
        this.role = newRole;
    }

    /**
     * Returns the vCard property value.
     */
    public String getVCard() {
        return this.vCard;
    }

    /**
     * Sets the vCard property value.
     */
    public void setVCard(String newVCard) {
        this.vCard = newVCard;
    }

    /**
     * Sets the LastName property value.
     */
    public void setLastName(String newLastName) {
        this.LastName = newLastName;
    }

    /**
     * Gets the LastName property value.
     */
    public String getLastName() {
        return this.LastName;
    }

    /**
     * Sets the FirstName property value.
     */
    public void setFirstName(String newFirstName) {
        this.FirstName = newFirstName;
    }

    /**
     * Gets the FirstName property value.
     */
    public String getFirstName() {
        return this.FirstName;
    }

    /**
     * Sets the Title property value.
     */
    public void setTitle(String newTitle ) {
        this.Title = newTitle ;
    }

    /**
     * Gets the Title property value.
     */
    public String getTitle() {
        return this.Title;
    }

    /**
     * Sets the Organization property value.
     */
    public void setOrganization(String newOrganization) {
        this.Organization  = newOrganization;
    }

    /**
     * Gets the Organization property value.
     */
    public String getOrganization () {
        return this.Organization;
    }

    /**
     * Sets the Phoneproperty value.
     */
    public void setPhone(String newPhone) {
        this.Phone  = newPhone;
    }

    /**
     * Gets the Phoneproperty value.
     */
    public String getPhone() {
        return this.Phone;
    }

    /**
     * Sets the Emailvalue.
     */
    public void setEmail(String newEmail) {
        this.Email  = newEmail;
    }

    /**
     * Gets the Emailvalue.
     */
    public String getEmail() {
        return this.Email;
    }

    /**
     * Set the CopyrightHolder value.
     */
    public void setCopyrightHolderFlag(boolean flag) {
        this.CopyrightHolder=flag;
    }

    /**
     * Gets the CopyrightHolder flag.
     */
    public boolean getCopyrightHolderFlag() {
        return this.CopyrightHolder;
    }







    /**
     * compare the metadataId, role, and vCard of the contributors.
     */
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj instanceof ContributorBean) {
            ContributorBean tb = (ContributorBean) obj;
            if (metadataId.equals(tb.metadataId) &&
                    role.equals(tb.role) &&
                    vCard.equals(tb.vCard)) {
                equal = true;
            }
        }
        return equal;
    }

    /**
     * Compares two ContributorBeans.  The comparison is done upon
     * first the metadataId, then if those are equal, the role, and finally
     * if those are equal, the vCard.
     *
     * Note: We don't check the type of the object because if we detect that
     * the types don't match, there is no valid/logical return value we
     * can give to indicate this.  Therefore we rely upon a
     * ClassCastException to be thrown.
     * 
     */
    public int compareTo(Object obj)
            throws ClassCastException {
        ContributorBean tb = (ContributorBean) obj;
        int result;
        result = metadataId.compareTo(tb.metadataId);
        if (result == 0) {
            result = role.compareTo(tb.role);
            if (result == 0) {
                result = vCard.compareTo(tb.vCard);
            }
        }
        return result;
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
    
    public String getVersion() {
        return version;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        return "ContributorBean{" +
                "contributorId='" + contributorId + "'" +
                ", vCard='" + vCard + "'" +
                ", date=" + date +
                ", dateDescription='" + dateDescription + "'" +
                ", metadataId='" + metadataId + "'" +
                ", role='" + role + "'" +
                ", version='" + version + "'" +
                ", status='" + status + "'" +
                "}";
    }
}
