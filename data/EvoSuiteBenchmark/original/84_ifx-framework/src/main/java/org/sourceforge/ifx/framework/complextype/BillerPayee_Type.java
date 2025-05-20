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
public class BillerPayee_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public BillerPayee_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillerId _billerId;

    /** 
     * Setter for billerId
     * @param billerId the org.sourceforge.ifx.framework.element.BillerId to set
     */
    public void setBillerId(org.sourceforge.ifx.framework.element.BillerId _billerId) {
        this._billerId = _billerId;
    }

    /**
     * Getter for billerId
     * @return a org.sourceforge.ifx.framework.element.BillerId
     */
    public org.sourceforge.ifx.framework.element.BillerId getBillerId() {
        return _billerId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillerContact _billerContact;

    /** 
     * Setter for billerContact
     * @param billerContact the org.sourceforge.ifx.framework.element.BillerContact to set
     */
    public void setBillerContact(org.sourceforge.ifx.framework.element.BillerContact _billerContact) {
        this._billerContact = _billerContact;
    }

    /**
     * Getter for billerContact
     * @return a org.sourceforge.ifx.framework.element.BillerContact
     */
    public org.sourceforge.ifx.framework.element.BillerContact getBillerContact() {
        return _billerContact;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.HistRetentionDays _histRetentionDays;

    /** 
     * Setter for histRetentionDays
     * @param histRetentionDays the org.sourceforge.ifx.framework.element.HistRetentionDays to set
     */
    public void setHistRetentionDays(org.sourceforge.ifx.framework.element.HistRetentionDays _histRetentionDays) {
        this._histRetentionDays = _histRetentionDays;
    }

    /**
     * Getter for histRetentionDays
     * @return a org.sourceforge.ifx.framework.element.HistRetentionDays
     */
    public org.sourceforge.ifx.framework.element.HistRetentionDays getHistRetentionDays() {
        return _histRetentionDays;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillerPayInfo _billerPayInfo;

    /** 
     * Setter for billerPayInfo
     * @param billerPayInfo the org.sourceforge.ifx.framework.element.BillerPayInfo to set
     */
    public void setBillerPayInfo(org.sourceforge.ifx.framework.element.BillerPayInfo _billerPayInfo) {
        this._billerPayInfo = _billerPayInfo;
    }

    /**
     * Getter for billerPayInfo
     * @return a org.sourceforge.ifx.framework.element.BillerPayInfo
     */
    public org.sourceforge.ifx.framework.element.BillerPayInfo getBillerPayInfo() {
        return _billerPayInfo;
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
       * BillerId
       * BillerContact
       * HistRetentionDays
       * BillerPayInfo
       */
    public final String[] ELEMENTS = {
              "BillerId"
                 ,"BillerContact"
                 ,"HistRetentionDays"
                 ,"BillerPayInfo"
          };
}
