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
public class PmtRemitChksum_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PmtRemitChksum_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AckType _ackType;

    /** 
     * Setter for ackType
     * @param ackType the org.sourceforge.ifx.framework.element.AckType to set
     */
    public void setAckType(org.sourceforge.ifx.framework.element.AckType _ackType) {
        this._ackType = _ackType;
    }

    /**
     * Getter for ackType
     * @return a org.sourceforge.ifx.framework.element.AckType
     */
    public org.sourceforge.ifx.framework.element.AckType getAckType() {
        return _ackType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Count _count;

    /** 
     * Setter for count
     * @param count the org.sourceforge.ifx.framework.element.Count to set
     */
    public void setCount(org.sourceforge.ifx.framework.element.Count _count) {
        this._count = _count;
    }

    /**
     * Getter for count
     * @return a org.sourceforge.ifx.framework.element.Count
     */
    public org.sourceforge.ifx.framework.element.Count getCount() {
        return _count;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TotalCurAmt _totalCurAmt;

    /** 
     * Setter for totalCurAmt
     * @param totalCurAmt the org.sourceforge.ifx.framework.element.TotalCurAmt to set
     */
    public void setTotalCurAmt(org.sourceforge.ifx.framework.element.TotalCurAmt _totalCurAmt) {
        this._totalCurAmt = _totalCurAmt;
    }

    /**
     * Getter for totalCurAmt
     * @return a org.sourceforge.ifx.framework.element.TotalCurAmt
     */
    public org.sourceforge.ifx.framework.element.TotalCurAmt getTotalCurAmt() {
        return _totalCurAmt;
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
    private org.sourceforge.ifx.framework.element.CompositeContactInfo _compositeContactInfo;

    /** 
     * Setter for compositeContactInfo
     * @param compositeContactInfo the org.sourceforge.ifx.framework.element.CompositeContactInfo to set
     */
    public void setCompositeContactInfo(org.sourceforge.ifx.framework.element.CompositeContactInfo _compositeContactInfo) {
        this._compositeContactInfo = _compositeContactInfo;
    }

    /**
     * Getter for compositeContactInfo
     * @return a org.sourceforge.ifx.framework.element.CompositeContactInfo
     */
    public org.sourceforge.ifx.framework.element.CompositeContactInfo getCompositeContactInfo() {
        return _compositeContactInfo;
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
       * AckType
       * Count
       * TotalCurAmt
       * Memo
       * CompositeContactInfo
       */
    public final String[] ELEMENTS = {
              "AckType"
                 ,"Count"
                 ,"TotalCurAmt"
                 ,"Memo"
                 ,"CompositeContactInfo"
          };
}
