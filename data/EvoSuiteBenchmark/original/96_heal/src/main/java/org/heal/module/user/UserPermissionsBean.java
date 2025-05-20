package org.heal.module.user;

import java.io.*;
import java.util.*;

/**
 * This class contains permissions information about a heal user.
 *
 * @author Seth Wright
 * @version 0.1
 */
public class UserPermissionsBean implements Serializable {
    // Properties
    private boolean minor = false;
    private boolean administrator = false;
    private boolean cataloger = false;
    private boolean approver = false;
    private boolean IAMSEMember = false;

    /**
     * Returns whether or not the user is a minor.
     */
    public boolean isMinor() {
	return minor;
    }

    /**
     * Sets whether or not the user is a minor.
     */
    public void setMinor(boolean newMinor) {
	this.minor = newMinor;
    }

    /**
     * Returns whether or not the user is a administrator.
     */
    public boolean isAdministrator() {
	return administrator;
    }

    /**
     * Sets whether or not the user is a administrator.
     */
    public void setAdministrator(boolean newAdministrator) {
	this.administrator = newAdministrator;
    }

    /**
     * Returns whether or not the user is a cataloger.
     */
    public boolean isCataloger() {
	return cataloger;
    }

    /**
     * Sets whether or not the user is a cataloger.
     */
    public void setCataloger(boolean newCataloger) {
	this.cataloger = newCataloger;
    }

    /**
     * Returns whether or not the user is a approver.
     */
    public boolean isApprover() {
	return approver;
    }

    /**
     * Sets whether or not the user is a approver.
     */
    public void setApprover(boolean newApprover) {
	this.approver = newApprover;
    }
    
    //Returns whether or not the user is a IAMSE Member.
    public boolean isIAMSEMember() {
	return IAMSEMember;
    }

    // Sets whether or not the user is a IAMSE Member.
     public void setIAMSEMember(boolean newIAMSEMember) {
	this.IAMSEMember = newIAMSEMember;
    }
}
