package org.heal.module.metadata;

import java.io.*;
import java.util.*;

/**
 * This class contains information about a copyrightHolder.
 *
 * @author Seth Wright
 * @version 0.1
 */
public class CopyrightHolderBean implements Serializable {
    // Properties
    private String copyrightHolderId;
    private String LastName;
    private String FirstName;
    private String Title;
    private String Organization;
    private String Phone;
    private String Email;
    private String metadataId;
    private String vCard;

    /**
     * Returns the copyrightHolderId property value.
     */
    public String getCopyrightHolderId() {
	return this.copyrightHolderId;
    }

    /**
     * Sets the copyrightHolderId property value.
     */
    public void setCopyrightHolderId(String newCopyrightHolderId) {
	this.copyrightHolderId = newCopyrightHolderId;
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


    public String toString() {
	return "CopyrightHolder: ID:"+copyrightHolderId+
	    " metadataId:"+metadataId+" vCard:"+vCard;
    }
}





