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
package org.sourceforge.ifx.framework.remit.complextype;

/**
 * Generated code.
 * 
 * @author org.sourceforge.ifx.tools.CodeGenerator
 */
public class ContactInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public ContactInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ContactPref _contactPref;

    /** 
     * Setter for contactPref
     * @param contactPref the org.sourceforge.ifx.framework.element.ContactPref to set
     */
    public void setContactPref(org.sourceforge.ifx.framework.element.ContactPref _contactPref) {
        this._contactPref = _contactPref;
    }

    /**
     * Getter for contactPref
     * @return a org.sourceforge.ifx.framework.element.ContactPref
     */
    public org.sourceforge.ifx.framework.element.ContactPref getContactPref() {
        return _contactPref;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PrefTimeStart _prefTimeStart;

    /** 
     * Setter for prefTimeStart
     * @param prefTimeStart the org.sourceforge.ifx.framework.element.PrefTimeStart to set
     */
    public void setPrefTimeStart(org.sourceforge.ifx.framework.element.PrefTimeStart _prefTimeStart) {
        this._prefTimeStart = _prefTimeStart;
    }

    /**
     * Getter for prefTimeStart
     * @return a org.sourceforge.ifx.framework.element.PrefTimeStart
     */
    public org.sourceforge.ifx.framework.element.PrefTimeStart getPrefTimeStart() {
        return _prefTimeStart;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PrefTimeEnd _prefTimeEnd;

    /** 
     * Setter for prefTimeEnd
     * @param prefTimeEnd the org.sourceforge.ifx.framework.element.PrefTimeEnd to set
     */
    public void setPrefTimeEnd(org.sourceforge.ifx.framework.element.PrefTimeEnd _prefTimeEnd) {
        this._prefTimeEnd = _prefTimeEnd;
    }

    /**
     * Getter for prefTimeEnd
     * @return a org.sourceforge.ifx.framework.element.PrefTimeEnd
     */
    public org.sourceforge.ifx.framework.element.PrefTimeEnd getPrefTimeEnd() {
        return _prefTimeEnd;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PhoneNum[] _phoneNum;

    /** 
     * Setter for phoneNum
     * @param phoneNum the org.sourceforge.ifx.framework.element.PhoneNum[] to set
     */
    public void setPhoneNum(org.sourceforge.ifx.framework.element.PhoneNum[] _phoneNum) {
        this._phoneNum = _phoneNum;
    }

    /**
     * Getter for phoneNum
     * @return a org.sourceforge.ifx.framework.element.PhoneNum[]
     */
    public org.sourceforge.ifx.framework.element.PhoneNum[] getPhoneNum() {
        return _phoneNum;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ContactName[] _contactName;

    /** 
     * Setter for contactName
     * @param contactName the org.sourceforge.ifx.framework.element.ContactName[] to set
     */
    public void setContactName(org.sourceforge.ifx.framework.element.ContactName[] _contactName) {
        this._contactName = _contactName;
    }

    /**
     * Getter for contactName
     * @return a org.sourceforge.ifx.framework.element.ContactName[]
     */
    public org.sourceforge.ifx.framework.element.ContactName[] getContactName() {
        return _contactName;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.EmailAddr[] _emailAddr;

    /** 
     * Setter for emailAddr
     * @param emailAddr the org.sourceforge.ifx.framework.element.EmailAddr[] to set
     */
    public void setEmailAddr(org.sourceforge.ifx.framework.element.EmailAddr[] _emailAddr) {
        this._emailAddr = _emailAddr;
    }

    /**
     * Getter for emailAddr
     * @return a org.sourceforge.ifx.framework.element.EmailAddr[]
     */
    public org.sourceforge.ifx.framework.element.EmailAddr[] getEmailAddr() {
        return _emailAddr;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.URL[] _uRL;

    /** 
     * Setter for uRL
     * @param uRL the org.sourceforge.ifx.framework.element.URL[] to set
     */
    public void setURL(org.sourceforge.ifx.framework.element.URL[] _uRL) {
        this._uRL = _uRL;
    }

    /**
     * Getter for uRL
     * @return a org.sourceforge.ifx.framework.element.URL[]
     */
    public org.sourceforge.ifx.framework.element.URL[] getURL() {
        return _uRL;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PostAddr[] _postAddr;

    /** 
     * Setter for postAddr
     * @param postAddr the org.sourceforge.ifx.framework.element.PostAddr[] to set
     */
    public void setPostAddr(org.sourceforge.ifx.framework.element.PostAddr[] _postAddr) {
        this._postAddr = _postAddr;
    }

    /**
     * Getter for postAddr
     * @return a org.sourceforge.ifx.framework.element.PostAddr[]
     */
    public org.sourceforge.ifx.framework.element.PostAddr[] getPostAddr() {
        return _postAddr;
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
       * ContactPref
       * PrefTimeStart
       * PrefTimeEnd
       * PhoneNum
       * ContactName
       * EmailAddr
       * URL
       * PostAddr
       */
    public final String[] ELEMENTS = {
              "ContactPref"
                 ,"PrefTimeStart"
                 ,"PrefTimeEnd"
                 ,"PhoneNum"
                 ,"ContactName"
                 ,"EmailAddr"
                 ,"URL"
                 ,"PostAddr"
          };
}
