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
public class BillPmtStatus_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public BillPmtStatus_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtId _pmtId;

    /** 
     * Setter for pmtId
     * @param pmtId the org.sourceforge.ifx.framework.element.PmtId to set
     */
    public void setPmtId(org.sourceforge.ifx.framework.element.PmtId _pmtId) {
        this._pmtId = _pmtId;
    }

    /**
     * Getter for pmtId
     * @return a org.sourceforge.ifx.framework.element.PmtId
     */
    public org.sourceforge.ifx.framework.element.PmtId getPmtId() {
        return _pmtId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillPmtStatusCode _billPmtStatusCode;

    /** 
     * Setter for billPmtStatusCode
     * @param billPmtStatusCode the org.sourceforge.ifx.framework.element.BillPmtStatusCode to set
     */
    public void setBillPmtStatusCode(org.sourceforge.ifx.framework.element.BillPmtStatusCode _billPmtStatusCode) {
        this._billPmtStatusCode = _billPmtStatusCode;
    }

    /**
     * Getter for billPmtStatusCode
     * @return a org.sourceforge.ifx.framework.element.BillPmtStatusCode
     */
    public org.sourceforge.ifx.framework.element.BillPmtStatusCode getBillPmtStatusCode() {
        return _billPmtStatusCode;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.EffDt _effDt;

    /** 
     * Setter for effDt
     * @param effDt the org.sourceforge.ifx.framework.element.EffDt to set
     */
    public void setEffDt(org.sourceforge.ifx.framework.element.EffDt _effDt) {
        this._effDt = _effDt;
    }

    /**
     * Getter for effDt
     * @return a org.sourceforge.ifx.framework.element.EffDt
     */
    public org.sourceforge.ifx.framework.element.EffDt getEffDt() {
        return _effDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StatusModBy _statusModBy;

    /** 
     * Setter for statusModBy
     * @param statusModBy the org.sourceforge.ifx.framework.element.StatusModBy to set
     */
    public void setStatusModBy(org.sourceforge.ifx.framework.element.StatusModBy _statusModBy) {
        this._statusModBy = _statusModBy;
    }

    /**
     * Getter for statusModBy
     * @return a org.sourceforge.ifx.framework.element.StatusModBy
     */
    public org.sourceforge.ifx.framework.element.StatusModBy getStatusModBy() {
        return _statusModBy;
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


    /**
     * Returns true if objects are equal, false otherwise.
     * @param obj the object to compare with.
     * @return true if equal, false if not.
     */
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /** Element ordering - 
       * PmtId
       * BillPmtStatusCode
       * EffDt
       * StatusModBy
       * Memo
       */
    public final String[] ELEMENTS = {
              "PmtId"
                 ,"BillPmtStatusCode"
                 ,"EffDt"
                 ,"StatusModBy"
                 ,"Memo"
          };
}
