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
public class CustPayeeRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CustPayeeRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustPayeeId _custPayeeId;

    /** 
     * Setter for custPayeeId
     * @param custPayeeId the org.sourceforge.ifx.framework.element.CustPayeeId to set
     */
    public void setCustPayeeId(org.sourceforge.ifx.framework.element.CustPayeeId _custPayeeId) {
        this._custPayeeId = _custPayeeId;
    }

    /**
     * Getter for custPayeeId
     * @return a org.sourceforge.ifx.framework.element.CustPayeeId
     */
    public org.sourceforge.ifx.framework.element.CustPayeeId getCustPayeeId() {
        return _custPayeeId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustPayeeInfo _custPayeeInfo;

    /** 
     * Setter for custPayeeInfo
     * @param custPayeeInfo the org.sourceforge.ifx.framework.element.CustPayeeInfo to set
     */
    public void setCustPayeeInfo(org.sourceforge.ifx.framework.element.CustPayeeInfo _custPayeeInfo) {
        this._custPayeeInfo = _custPayeeInfo;
    }

    /**
     * Getter for custPayeeInfo
     * @return a org.sourceforge.ifx.framework.element.CustPayeeInfo
     */
    public org.sourceforge.ifx.framework.element.CustPayeeInfo getCustPayeeInfo() {
        return _custPayeeInfo;
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
    private org.sourceforge.ifx.framework.element.DaysToPay _daysToPay;

    /** 
     * Setter for daysToPay
     * @param daysToPay the org.sourceforge.ifx.framework.element.DaysToPay to set
     */
    public void setDaysToPay(org.sourceforge.ifx.framework.element.DaysToPay _daysToPay) {
        this._daysToPay = _daysToPay;
    }

    /**
     * Getter for daysToPay
     * @return a org.sourceforge.ifx.framework.element.DaysToPay
     */
    public org.sourceforge.ifx.framework.element.DaysToPay getDaysToPay() {
        return _daysToPay;
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
       * CustPayeeId
       * CustPayeeInfo
       * BillerContact
       * DaysToPay
       */
    public final String[] ELEMENTS = {
              "CustPayeeId"
                 ,"CustPayeeInfo"
                 ,"BillerContact"
                 ,"DaysToPay"
          };
}
