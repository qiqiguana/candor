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
public class BillerContact_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public BillerContact_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.LegalName _legalName;

    /** 
     * Setter for legalName
     * @param legalName the org.sourceforge.ifx.framework.element.LegalName to set
     */
    public void setLegalName(org.sourceforge.ifx.framework.element.LegalName _legalName) {
        this._legalName = _legalName;
    }

    /**
     * Getter for legalName
     * @return a org.sourceforge.ifx.framework.element.LegalName
     */
    public org.sourceforge.ifx.framework.element.LegalName getLegalName() {
        return _legalName;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Name _name;

    /** 
     * Setter for name
     * @param name the org.sourceforge.ifx.framework.element.Name to set
     */
    public void setName(org.sourceforge.ifx.framework.element.Name _name) {
        this._name = _name;
    }

    /**
     * Getter for name
     * @return a org.sourceforge.ifx.framework.element.Name
     */
    public org.sourceforge.ifx.framework.element.Name getName() {
        return _name;
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
    private org.sourceforge.ifx.framework.element.BillRetAddr _billRetAddr;

    /** 
     * Setter for billRetAddr
     * @param billRetAddr the org.sourceforge.ifx.framework.element.BillRetAddr to set
     */
    public void setBillRetAddr(org.sourceforge.ifx.framework.element.BillRetAddr _billRetAddr) {
        this._billRetAddr = _billRetAddr;
    }

    /**
     * Getter for billRetAddr
     * @return a org.sourceforge.ifx.framework.element.BillRetAddr
     */
    public org.sourceforge.ifx.framework.element.BillRetAddr getBillRetAddr() {
        return _billRetAddr;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RemitName _remitName;

    /** 
     * Setter for remitName
     * @param remitName the org.sourceforge.ifx.framework.element.RemitName to set
     */
    public void setRemitName(org.sourceforge.ifx.framework.element.RemitName _remitName) {
        this._remitName = _remitName;
    }

    /**
     * Getter for remitName
     * @return a org.sourceforge.ifx.framework.element.RemitName
     */
    public org.sourceforge.ifx.framework.element.RemitName getRemitName() {
        return _remitName;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RemitAddr _remitAddr;

    /** 
     * Setter for remitAddr
     * @param remitAddr the org.sourceforge.ifx.framework.element.RemitAddr to set
     */
    public void setRemitAddr(org.sourceforge.ifx.framework.element.RemitAddr _remitAddr) {
        this._remitAddr = _remitAddr;
    }

    /**
     * Getter for remitAddr
     * @return a org.sourceforge.ifx.framework.element.RemitAddr
     */
    public org.sourceforge.ifx.framework.element.RemitAddr getRemitAddr() {
        return _remitAddr;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.OrgContact[] _orgContact;

    /** 
     * Setter for orgContact
     * @param orgContact the org.sourceforge.ifx.framework.element.OrgContact[] to set
     */
    public void setOrgContact(org.sourceforge.ifx.framework.element.OrgContact[] _orgContact) {
        this._orgContact = _orgContact;
    }

    /**
     * Getter for orgContact
     * @return a org.sourceforge.ifx.framework.element.OrgContact[]
     */
    public org.sourceforge.ifx.framework.element.OrgContact[] getOrgContact() {
        return _orgContact;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ContactInfo[] _contactInfo;

    /** 
     * Setter for contactInfo
     * @param contactInfo the org.sourceforge.ifx.framework.element.ContactInfo[] to set
     */
    public void setContactInfo(org.sourceforge.ifx.framework.element.ContactInfo[] _contactInfo) {
        this._contactInfo = _contactInfo;
    }

    /**
     * Getter for contactInfo
     * @return a org.sourceforge.ifx.framework.element.ContactInfo[]
     */
    public org.sourceforge.ifx.framework.element.ContactInfo[] getContactInfo() {
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
       * LegalName
       * Name
       * PostAddr
       * BillRetAddr
       * RemitName
       * RemitAddr
       * OrgContact
       * ContactInfo
       */
    public final String[] ELEMENTS = {
              "LegalName"
                 ,"Name"
                 ,"PostAddr"
                 ,"BillRetAddr"
                 ,"RemitName"
                 ,"RemitAddr"
                 ,"OrgContact"
                 ,"ContactInfo"
          };
}
