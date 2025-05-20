package org.heal.module.user;

import java.io.*;
import java.util.*;

/**
 * This class contains information about heal users.
 *
 * @author Seth Wright
 * @modify Jason Varghese
 * @version 0.1
 */
public class UserBean implements Serializable {
    // Properties
    private String userId = "";
    private String userName = "";
    private String password = "";
    private boolean minor = true;
    private boolean administrator = false;
    private boolean cataloger = false;
    private boolean approver = false;
    private String firstName = "";
    private String lastName = "";
    private String middleInitial = "";
    private String email = "";
    ArrayList professionalRole = new ArrayList();
    String professionalSpecialty = "";
    ArrayList instructionalLevel = new ArrayList();
    private String phoneNumber = "";
    private String institutionName = "";
    private String address1 = "";
    private String address2 = "";
    private String city = "";
    private String state = "";
    private String zipCode = "";
    private String country = "";
    private boolean mailingList = true;
    private boolean loginModified = false;
    private boolean emailValidated = false;
    private boolean IAMSEMember = false;
	

    /**
     * Returns the bean's data in encoded in XML format.
     */
    public String getXML() {
	return "not implemented";  //XXX implement this later.
    }
    
    /**
     * Parses the XML string and stores the data in the bean.
     */
    public void parseXML(String xmlData) {
	//XXX not implemented.
    }

    /**
     * Returns the UserId property value.
     */
    public String getUserId() {
	return this.userId;
    }

    /**
     * Sets the id property value.
     */
    public void setUserId(String newUserId) {
	this.userId = newUserId;
    }

    /**
     * Returns the userName property value.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the userName property value.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Returns the password property value.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password property value.
     */
    public void setPassword(String password) {
        this.password = password;
    }

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

    /**
     * Returns the firstName property value.
     */
    public String getFirstName() {
	return this.firstName;
    }

    /**
     * Sets the firstName property value.
     */
    public void setFirstName(String newFirstName) {
	this.firstName = newFirstName;
    }

    /**
     * Returns the lastName property value.
     */
    public String getLastName() {
	return this.lastName;
    }

    /**
     * Sets the lastName property value.
     */
    public void setLastName(String newLastName) {
	this.lastName = newLastName;
    }

    /**
     * Returns the middleInitial property value.
     */
    public String getMiddleInitial() {
	return this.middleInitial;
    }

    /**
     * Sets the middleInitial property value.
     */
    public void setMiddleInitial(String newMiddleInitial) {
	this.middleInitial = newMiddleInitial;
    }

    /**
     * Returns the email property value.
     */
    public String getEmail() {
	return this.email;
    }

    /**
     * Sets the email property value.
     */
    public void setEmail(String newEmail) {
	this.email = newEmail;
    }

	/**
	 * Returns a vector of CopyrightBean objects each containing
	 * a CopyrightTextBean for this metadata.
	 */
	public ArrayList getInstructionalLevel() {
		return instructionalLevel;
	}

	/**
	 * Sets the copyright property value.
	 * DO NOT use NULL as a parameter.
	 */
	public void setInstructionalLevel(ArrayList newInstructionalLevel) {
		instructionalLevel = newInstructionalLevel;
	}

	/**
	 * Adds a copyright to the set of copyrights for
	 * this metadata object.
	 */
	public void addInstructionalLevel(InstructionalLevelBean newInstructionalLevel) {
		instructionalLevel.add(newInstructionalLevel);
	}
	
	/**
	 * Returns a vector of CopyrightBean objects each containing
	 * a CopyrightTextBean for this metadata.
	 */
	public ArrayList getProfessionalRole() {
		return professionalRole;
	}

	/**
	 * Sets the copyright property value.
	 * DO NOT use NULL as a parameter.
	 */
	public void setProfessionalRole(ArrayList newProfessionalRole) {
		professionalRole = newProfessionalRole;
	}

	/**
	 * Adds a copyright to the set of copyrights for
	 * this metadata object.
	 */
	public void addProfessionalRole(ProfessionalRoleBean newProfessionalRole) {
		professionalRole.add(newProfessionalRole);
	}	
	

    /**
     * Returns the professionalSpecialty property value.
     */
    public String getProfessionalSpecialty() {
	return this.professionalSpecialty;
    }

    /**
     * Sets the professionalSpecialty property value.
     */
    public void setProfessionalSpecialty(String newProfessionalSpecialty) {
	this.professionalSpecialty = newProfessionalSpecialty;
    }

    /**
     * Returns the phoneNumber property value.
     */
    public String getPhoneNumber() {
	return this.phoneNumber;
    }

    /**
     * Sets the phoneNumber property value.
     */
    public void setPhoneNumber(String newPhoneNumber) {
	this.phoneNumber = newPhoneNumber;
    }

    /**
     * Returns the institutionName property value.
     */
    public String getInstitutionName() {
	return this.institutionName;
    }

    /**
     * Sets the institutionName property value.
     */
    public void setInstitutionName(String newInstitutionName) {
	this.institutionName = newInstitutionName;
    }

    /**
     * Returns the address1 property value.
     */
    public String getAddress1() {
	return this.address1;
    }

    /**
     * Sets the address1 property value.
     */
    public void setAddress1(String newAddress1) {
	this.address1 = newAddress1;
    }

    /**
     * Returns the address2 property value.
     */
    public String getAddress2() {
	return this.address2;
    }

    /**
     * Sets the address2 property value.
     */
    public void setAddress2(String newAddress2) {
	this.address2 = newAddress2;
    }

    /**
     * Returns the city property value.
     */
    public String getCity() {
	return this.city;
    }

    /**
     * Sets the city property value.
     */
    public void setCity(String newCity) {
	this.city = newCity;
    }

    /**
     * Returns the state property value.
     */
    public String getState() {
	return this.state;
    }

    /**
     * Sets the state property value.
     */
    public void setState(String newState) {
	this.state = newState;
    }

    /**
     * Returns the zipCode property value.
     */
    public String getZipCode() {
	return this.zipCode;
    }

    /**
     * Sets the zipCode property value.
     */
    public void setZipCode(String newZipCode) {
	this.zipCode = newZipCode;
    }

    /**
     * Returns the country property value.
     */
    public String getCountry() {
	return this.country;
    }

    /**
     * Sets the country property value.
     */
    public void setCountry(String newCountry) {
	this.country = newCountry;
    }

        /**
     * Returns the mailingList property value.
     */
    public boolean getMailingList() {
	return this.mailingList;
    }

    /**
     * Sets the mailingList property value.
     */
    public void setMailingList(boolean newMailingList) {
	this.mailingList = newMailingList;
    }
		
        /**
     * Returns the isUserNameChanged changed property value.
     */
    public boolean isLoginModified() {
	return this.loginModified;
    }

    /**
     * Sets the userNameChanged changed property value.
     */
    public void setLoginModified(boolean newLoginModified) {
	this.loginModified = newLoginModified;
    }
        /**
     * Returns the isUserNameChanged changed property value.
     */
    public boolean isEmailValidated() {
	return this.emailValidated;
    }

    /**
     * Sets the userNameChanged changed property value.
     */
    public void setEmailValidated(boolean newEmailValidated) {
	this.emailValidated = newEmailValidated;
    }			
		
    public boolean isIAMSEMember() {
	return this.IAMSEMember;
    }

    public void setIAMSEMember(boolean newIAMSEMember) {
	this.IAMSEMember = newIAMSEMember;
    }			
}
