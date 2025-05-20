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
public class BillRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public BillRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillId _billId;

    /** 
     * Setter for billId
     * @param billId the org.sourceforge.ifx.framework.element.BillId to set
     */
    public void setBillId(org.sourceforge.ifx.framework.element.BillId _billId) {
        this._billId = _billId;
    }

    /**
     * Getter for billId
     * @return a org.sourceforge.ifx.framework.element.BillId
     */
    public org.sourceforge.ifx.framework.element.BillId getBillId() {
        return _billId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillInfo _billInfo;

    /** 
     * Setter for billInfo
     * @param billInfo the org.sourceforge.ifx.framework.element.BillInfo to set
     */
    public void setBillInfo(org.sourceforge.ifx.framework.element.BillInfo _billInfo) {
        this._billInfo = _billInfo;
    }

    /**
     * Getter for billInfo
     * @return a org.sourceforge.ifx.framework.element.BillInfo
     */
    public org.sourceforge.ifx.framework.element.BillInfo getBillInfo() {
        return _billInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillStatus _billStatus;

    /** 
     * Setter for billStatus
     * @param billStatus the org.sourceforge.ifx.framework.element.BillStatus to set
     */
    public void setBillStatus(org.sourceforge.ifx.framework.element.BillStatus _billStatus) {
        this._billStatus = _billStatus;
    }

    /**
     * Getter for billStatus
     * @return a org.sourceforge.ifx.framework.element.BillStatus
     */
    public org.sourceforge.ifx.framework.element.BillStatus getBillStatus() {
        return _billStatus;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillPmtStatus _billPmtStatus;

    /** 
     * Setter for billPmtStatus
     * @param billPmtStatus the org.sourceforge.ifx.framework.element.BillPmtStatus to set
     */
    public void setBillPmtStatus(org.sourceforge.ifx.framework.element.BillPmtStatus _billPmtStatus) {
        this._billPmtStatus = _billPmtStatus;
    }

    /**
     * Getter for billPmtStatus
     * @return a org.sourceforge.ifx.framework.element.BillPmtStatus
     */
    public org.sourceforge.ifx.framework.element.BillPmtStatus getBillPmtStatus() {
        return _billPmtStatus;
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
       * BillId
       * BillInfo
       * BillStatus
       * BillPmtStatus
       */
    public final String[] ELEMENTS = {
              "BillId"
                 ,"BillInfo"
                 ,"BillStatus"
                 ,"BillPmtStatus"
          };
}
