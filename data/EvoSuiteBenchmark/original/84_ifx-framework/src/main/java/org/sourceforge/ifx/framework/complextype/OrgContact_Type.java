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
public class OrgContact_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public OrgContact_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ContactType _contactType;

    /** 
     * Setter for contactType
     * @param contactType the org.sourceforge.ifx.framework.element.ContactType to set
     */
    public void setContactType(org.sourceforge.ifx.framework.element.ContactType _contactType) {
        this._contactType = _contactType;
    }

    /**
     * Getter for contactType
     * @return a org.sourceforge.ifx.framework.element.ContactType
     */
    public org.sourceforge.ifx.framework.element.ContactType getContactType() {
        return _contactType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Desc _desc;

    /** 
     * Setter for desc
     * @param desc the org.sourceforge.ifx.framework.element.Desc to set
     */
    public void setDesc(org.sourceforge.ifx.framework.element.Desc _desc) {
        this._desc = _desc;
    }

    /**
     * Getter for desc
     * @return a org.sourceforge.ifx.framework.element.Desc
     */
    public org.sourceforge.ifx.framework.element.Desc getDesc() {
        return _desc;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Phone _phone;

    /** 
     * Setter for phone
     * @param phone the org.sourceforge.ifx.framework.element.Phone to set
     */
    public void setPhone(org.sourceforge.ifx.framework.element.Phone _phone) {
        this._phone = _phone;
    }

    /**
     * Getter for phone
     * @return a org.sourceforge.ifx.framework.element.Phone
     */
    public org.sourceforge.ifx.framework.element.Phone getPhone() {
        return _phone;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Fax _fax;

    /** 
     * Setter for fax
     * @param fax the org.sourceforge.ifx.framework.element.Fax to set
     */
    public void setFax(org.sourceforge.ifx.framework.element.Fax _fax) {
        this._fax = _fax;
    }

    /**
     * Getter for fax
     * @return a org.sourceforge.ifx.framework.element.Fax
     */
    public org.sourceforge.ifx.framework.element.Fax getFax() {
        return _fax;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.EmailAddr _emailAddr;

    /** 
     * Setter for emailAddr
     * @param emailAddr the org.sourceforge.ifx.framework.element.EmailAddr to set
     */
    public void setEmailAddr(org.sourceforge.ifx.framework.element.EmailAddr _emailAddr) {
        this._emailAddr = _emailAddr;
    }

    /**
     * Getter for emailAddr
     * @return a org.sourceforge.ifx.framework.element.EmailAddr
     */
    public org.sourceforge.ifx.framework.element.EmailAddr getEmailAddr() {
        return _emailAddr;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.URL _uRL;

    /** 
     * Setter for uRL
     * @param uRL the org.sourceforge.ifx.framework.element.URL to set
     */
    public void setURL(org.sourceforge.ifx.framework.element.URL _uRL) {
        this._uRL = _uRL;
    }

    /**
     * Getter for uRL
     * @return a org.sourceforge.ifx.framework.element.URL
     */
    public org.sourceforge.ifx.framework.element.URL getURL() {
        return _uRL;
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
       * ContactType
       * Desc
       * Phone
       * Fax
       * EmailAddr
       * URL
       */
    public final String[] ELEMENTS = {
              "ContactType"
                 ,"Desc"
                 ,"Phone"
                 ,"Fax"
                 ,"EmailAddr"
                 ,"URL"
          };
}
