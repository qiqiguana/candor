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
public class BillSummSubAmt_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public BillSummSubAmt_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillSummAmtId _billSummAmtId;

    /** 
     * Setter for billSummAmtId
     * @param billSummAmtId the org.sourceforge.ifx.framework.element.BillSummAmtId to set
     */
    public void setBillSummAmtId(org.sourceforge.ifx.framework.element.BillSummAmtId _billSummAmtId) {
        this._billSummAmtId = _billSummAmtId;
    }

    /**
     * Getter for billSummAmtId
     * @return a org.sourceforge.ifx.framework.element.BillSummAmtId
     */
    public org.sourceforge.ifx.framework.element.BillSummAmtId getBillSummAmtId() {
        return _billSummAmtId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ShortDesc _shortDesc;

    /** 
     * Setter for shortDesc
     * @param shortDesc the org.sourceforge.ifx.framework.element.ShortDesc to set
     */
    public void setShortDesc(org.sourceforge.ifx.framework.element.ShortDesc _shortDesc) {
        this._shortDesc = _shortDesc;
    }

    /**
     * Getter for shortDesc
     * @return a org.sourceforge.ifx.framework.element.ShortDesc
     */
    public org.sourceforge.ifx.framework.element.ShortDesc getShortDesc() {
        return _shortDesc;
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
    private org.sourceforge.ifx.framework.element.CurAmt _curAmt;

    /** 
     * Setter for curAmt
     * @param curAmt the org.sourceforge.ifx.framework.element.CurAmt to set
     */
    public void setCurAmt(org.sourceforge.ifx.framework.element.CurAmt _curAmt) {
        this._curAmt = _curAmt;
    }

    /**
     * Getter for curAmt
     * @return a org.sourceforge.ifx.framework.element.CurAmt
     */
    public org.sourceforge.ifx.framework.element.CurAmt getCurAmt() {
        return _curAmt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AllocateAllowed _allocateAllowed;

    /** 
     * Setter for allocateAllowed
     * @param allocateAllowed the org.sourceforge.ifx.framework.element.AllocateAllowed to set
     */
    public void setAllocateAllowed(org.sourceforge.ifx.framework.element.AllocateAllowed _allocateAllowed) {
        this._allocateAllowed = _allocateAllowed;
    }

    /**
     * Getter for allocateAllowed
     * @return a org.sourceforge.ifx.framework.element.AllocateAllowed
     */
    public org.sourceforge.ifx.framework.element.AllocateAllowed getAllocateAllowed() {
        return _allocateAllowed;
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
       * BillSummAmtId
       * ShortDesc
       * Desc
       * Memo
       * CurAmt
       * AllocateAllowed
       */
    public final String[] ELEMENTS = {
              "BillSummAmtId"
                 ,"ShortDesc"
                 ,"Desc"
                 ,"Memo"
                 ,"CurAmt"
                 ,"AllocateAllowed"
          };
}
