/*
 * IFX-Framework - IFX XML to JavaBean application framework.
 * Copyright (C) 2003  The IFX-Framework Team
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.sourceforge.ifx.framework.complextype;

/**
 * Generated code.
 * 
 * @author org.sourceforge.ifx.tools.CodeGenerator
 */
public class CustInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CustInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PersonInfo _personInfo;

    /** 
     * Setter for personInfo
     * @param personInfo the org.sourceforge.ifx.framework.element.PersonInfo to set
     */
    public void setPersonInfo(org.sourceforge.ifx.framework.element.PersonInfo _personInfo) {
        this._personInfo = _personInfo;
    }

    /**
     * Getter for personInfo
     * @return a org.sourceforge.ifx.framework.element.PersonInfo
     */
    public org.sourceforge.ifx.framework.element.PersonInfo getPersonInfo() {
        return _personInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.OrgInfo _orgInfo;

    /** 
     * Setter for orgInfo
     * @param orgInfo the org.sourceforge.ifx.framework.element.OrgInfo to set
     */
    public void setOrgInfo(org.sourceforge.ifx.framework.element.OrgInfo _orgInfo) {
        this._orgInfo = _orgInfo;
    }

    /**
     * Getter for orgInfo
     * @return a org.sourceforge.ifx.framework.element.OrgInfo
     */
    public org.sourceforge.ifx.framework.element.OrgInfo getOrgInfo() {
        return _orgInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustName _custName;

    /** 
     * Setter for custName
     * @param custName the org.sourceforge.ifx.framework.element.CustName to set
     */
    public void setCustName(org.sourceforge.ifx.framework.element.CustName _custName) {
        this._custName = _custName;
    }

    /**
     * Getter for custName
     * @return a org.sourceforge.ifx.framework.element.CustName
     */
    public org.sourceforge.ifx.framework.element.CustName getCustName() {
        return _custName;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PostAddr _postAddr;

    /** 
     * Setter for postAddr
     * @param postAddr the org.sourceforge.ifx.framework.element.PostAddr to set
     */
    public void setPostAddr(org.sourceforge.ifx.framework.element.PostAddr _postAddr) {
        this._postAddr = _postAddr;
    }

    /**
     * Getter for postAddr
     * @return a org.sourceforge.ifx.framework.element.PostAddr
     */
    public org.sourceforge.ifx.framework.element.PostAddr getPostAddr() {
        return _postAddr;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustContact _custContact;

    /** 
     * Setter for custContact
     * @param custContact the org.sourceforge.ifx.framework.element.CustContact to set
     */
    public void setCustContact(org.sourceforge.ifx.framework.element.CustContact _custContact) {
        this._custContact = _custContact;
    }

    /**
     * Getter for custContact
     * @return a org.sourceforge.ifx.framework.element.CustContact
     */
    public org.sourceforge.ifx.framework.element.CustContact getCustContact() {
        return _custContact;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ContactInfo _contactInfo;

    /** 
     * Setter for contactInfo
     * @param contactInfo the org.sourceforge.ifx.framework.element.ContactInfo to set
     */
    public void setContactInfo(org.sourceforge.ifx.framework.element.ContactInfo _contactInfo) {
        this._contactInfo = _contactInfo;
    }

    /**
     * Getter for contactInfo
     * @return a org.sourceforge.ifx.framework.element.ContactInfo
     */
    public org.sourceforge.ifx.framework.element.ContactInfo getContactInfo() {
        return _contactInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustPref _custPref;

    /** 
     * Setter for custPref
     * @param custPref the org.sourceforge.ifx.framework.element.CustPref to set
     */
    public void setCustPref(org.sourceforge.ifx.framework.element.CustPref _custPref) {
        this._custPref = _custPref;
    }

    /**
     * Getter for custPref
     * @return a org.sourceforge.ifx.framework.element.CustPref
     */
    public org.sourceforge.ifx.framework.element.CustPref getCustPref() {
        return _custPref;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustType _custType;

    /** 
     * Setter for custType
     * @param custType the org.sourceforge.ifx.framework.element.CustType to set
     */
    public void setCustType(org.sourceforge.ifx.framework.element.CustType _custType) {
        this._custType = _custType;
    }

    /**
     * Getter for custType
     * @return a org.sourceforge.ifx.framework.element.CustType
     */
    public org.sourceforge.ifx.framework.element.CustType getCustType() {
        return _custType;
    }


    /**
     * Returns true if objects are equal, false otherwise.
     * @param obj the object to compare with.
     * @return true if equal, false if not.
     */
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /** Element ordering - 
       * PersonInfo
       * OrgInfo
       * CustName
       * PostAddr
       * CustContact
       * ContactInfo
       * CustPref
       * CustType
       */
    public final String[] ELEMENTS = {
              "PersonInfo"
                 ,"OrgInfo"
                 ,"CustName"
                 ,"PostAddr"
                 ,"CustContact"
                 ,"ContactInfo"
                 ,"CustPref"
                 ,"CustType"
          };
}
