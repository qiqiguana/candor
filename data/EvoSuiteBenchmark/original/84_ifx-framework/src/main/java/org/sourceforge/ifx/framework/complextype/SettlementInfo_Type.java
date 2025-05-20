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
public class SettlementInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public SettlementInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SettlementMethod _settlementMethod;

    /** 
     * Setter for settlementMethod
     * @param settlementMethod the org.sourceforge.ifx.framework.element.SettlementMethod to set
     */
    public void setSettlementMethod(org.sourceforge.ifx.framework.element.SettlementMethod _settlementMethod) {
        this._settlementMethod = _settlementMethod;
    }

    /**
     * Getter for settlementMethod
     * @return a org.sourceforge.ifx.framework.element.SettlementMethod
     */
    public org.sourceforge.ifx.framework.element.SettlementMethod getSettlementMethod() {
        return _settlementMethod;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAcctId _depAcctId;

    /** 
     * Setter for depAcctId
     * @param depAcctId the org.sourceforge.ifx.framework.element.DepAcctId to set
     */
    public void setDepAcctId(org.sourceforge.ifx.framework.element.DepAcctId _depAcctId) {
        this._depAcctId = _depAcctId;
    }

    /**
     * Getter for depAcctId
     * @return a org.sourceforge.ifx.framework.element.DepAcctId
     */
    public org.sourceforge.ifx.framework.element.DepAcctId getDepAcctId() {
        return _depAcctId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SettlementId _settlementId;

    /** 
     * Setter for settlementId
     * @param settlementId the org.sourceforge.ifx.framework.element.SettlementId to set
     */
    public void setSettlementId(org.sourceforge.ifx.framework.element.SettlementId _settlementId) {
        this._settlementId = _settlementId;
    }

    /**
     * Getter for settlementId
     * @return a org.sourceforge.ifx.framework.element.SettlementId
     */
    public org.sourceforge.ifx.framework.element.SettlementId getSettlementId() {
        return _settlementId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.OrgContact _orgContact;

    /** 
     * Setter for orgContact
     * @param orgContact the org.sourceforge.ifx.framework.element.OrgContact to set
     */
    public void setOrgContact(org.sourceforge.ifx.framework.element.OrgContact _orgContact) {
        this._orgContact = _orgContact;
    }

    /**
     * Getter for orgContact
     * @return a org.sourceforge.ifx.framework.element.OrgContact
     */
    public org.sourceforge.ifx.framework.element.OrgContact getOrgContact() {
        return _orgContact;
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
    private org.sourceforge.ifx.framework.element.Memo _memo;

    /** 
     * Setter for memo
     * @param memo the org.sourceforge.ifx.framework.element.Memo to set
     */
    public void setMemo(org.sourceforge.ifx.framework.element.Memo _memo) {
        this._memo = _memo;
    }

    /**
     * Getter for memo
     * @return a org.sourceforge.ifx.framework.element.Memo
     */
    public org.sourceforge.ifx.framework.element.Memo getMemo() {
        return _memo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtInstruction[] _pmtInstruction;

    /** 
     * Setter for pmtInstruction
     * @param pmtInstruction the org.sourceforge.ifx.framework.element.PmtInstruction[] to set
     */
    public void setPmtInstruction(org.sourceforge.ifx.framework.element.PmtInstruction[] _pmtInstruction) {
        this._pmtInstruction = _pmtInstruction;
    }

    /**
     * Getter for pmtInstruction
     * @return a org.sourceforge.ifx.framework.element.PmtInstruction[]
     */
    public org.sourceforge.ifx.framework.element.PmtInstruction[] getPmtInstruction() {
        return _pmtInstruction;
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
       * SettlementMethod
       * DepAcctId
       * SettlementId
       * OrgContact
       * ContactInfo
       * Memo
       * PmtInstruction
       */
    public final String[] ELEMENTS = {
              "SettlementMethod"
                 ,"DepAcctId"
                 ,"SettlementId"
                 ,"OrgContact"
                 ,"ContactInfo"
                 ,"Memo"
                 ,"PmtInstruction"
          };
}
