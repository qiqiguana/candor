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
public class CustNameAddr_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CustNameAddr_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.NameAddrType _nameAddrType;

    /** 
     * Setter for nameAddrType
     * @param nameAddrType the org.sourceforge.ifx.framework.element.NameAddrType to set
     */
    public void setNameAddrType(org.sourceforge.ifx.framework.element.NameAddrType _nameAddrType) {
        this._nameAddrType = _nameAddrType;
    }

    /**
     * Getter for nameAddrType
     * @return a org.sourceforge.ifx.framework.element.NameAddrType
     */
    public org.sourceforge.ifx.framework.element.NameAddrType getNameAddrType() {
        return _nameAddrType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.FullName[] _fullName;

    /** 
     * Setter for fullName
     * @param fullName the org.sourceforge.ifx.framework.element.FullName[] to set
     */
    public void setFullName(org.sourceforge.ifx.framework.element.FullName[] _fullName) {
        this._fullName = _fullName;
    }

    /**
     * Getter for fullName
     * @return a org.sourceforge.ifx.framework.element.FullName[]
     */
    public org.sourceforge.ifx.framework.element.FullName[] getFullName() {
        return _fullName;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustName[] _custName;

    /** 
     * Setter for custName
     * @param custName the org.sourceforge.ifx.framework.element.CustName[] to set
     */
    public void setCustName(org.sourceforge.ifx.framework.element.CustName[] _custName) {
        this._custName = _custName;
    }

    /**
     * Getter for custName
     * @return a org.sourceforge.ifx.framework.element.CustName[]
     */
    public org.sourceforge.ifx.framework.element.CustName[] getCustName() {
        return _custName;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PersonName[] _personName;

    /** 
     * Setter for personName
     * @param personName the org.sourceforge.ifx.framework.element.PersonName[] to set
     */
    public void setPersonName(org.sourceforge.ifx.framework.element.PersonName[] _personName) {
        this._personName = _personName;
    }

    /**
     * Getter for personName
     * @return a org.sourceforge.ifx.framework.element.PersonName[]
     */
    public org.sourceforge.ifx.framework.element.PersonName[] getPersonName() {
        return _personName;
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


    /**
     * Returns true if objects are equal, false otherwise.
     * @param obj the object to compare with.
     * @return true if equal, false if not.
     */
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /** Element ordering - 
       * NameAddrType
       * FullName
       * CustName
       * PersonName
       * PostAddr
       * CustContact
       * ContactInfo
       */
    public final String[] ELEMENTS = {
              "NameAddrType"
                 ,"FullName"
                 ,"CustName"
                 ,"PersonName"
                 ,"PostAddr"
                 ,"CustContact"
                 ,"ContactInfo"
          };
}
