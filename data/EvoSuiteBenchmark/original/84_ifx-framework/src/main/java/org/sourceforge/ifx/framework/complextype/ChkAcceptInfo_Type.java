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
public class ChkAcceptInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public ChkAcceptInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkAcceptType _chkAcceptType;

    /** 
     * Setter for chkAcceptType
     * @param chkAcceptType the org.sourceforge.ifx.framework.element.ChkAcceptType to set
     */
    public void setChkAcceptType(org.sourceforge.ifx.framework.element.ChkAcceptType _chkAcceptType) {
        this._chkAcceptType = _chkAcceptType;
    }

    /**
     * Getter for chkAcceptType
     * @return a org.sourceforge.ifx.framework.element.ChkAcceptType
     */
    public org.sourceforge.ifx.framework.element.ChkAcceptType getChkAcceptType() {
        return _chkAcceptType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CompositeCurAmt[] _compositeCurAmt;

    /** 
     * Setter for compositeCurAmt
     * @param compositeCurAmt the org.sourceforge.ifx.framework.element.CompositeCurAmt[] to set
     */
    public void setCompositeCurAmt(org.sourceforge.ifx.framework.element.CompositeCurAmt[] _compositeCurAmt) {
        this._compositeCurAmt = _compositeCurAmt;
    }

    /**
     * Getter for compositeCurAmt
     * @return a org.sourceforge.ifx.framework.element.CompositeCurAmt[]
     */
    public org.sourceforge.ifx.framework.element.CompositeCurAmt[] getCompositeCurAmt() {
        return _compositeCurAmt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditId[] _creditId;

    /** 
     * Setter for creditId
     * @param creditId the org.sourceforge.ifx.framework.element.CreditId[] to set
     */
    public void setCreditId(org.sourceforge.ifx.framework.element.CreditId[] _creditId) {
        this._creditId = _creditId;
    }

    /**
     * Getter for creditId
     * @return a org.sourceforge.ifx.framework.element.CreditId[]
     */
    public org.sourceforge.ifx.framework.element.CreditId[] getCreditId() {
        return _creditId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SettleInd[] _settleInd;

    /** 
     * Setter for settleInd
     * @param settleInd the org.sourceforge.ifx.framework.element.SettleInd[] to set
     */
    public void setSettleInd(org.sourceforge.ifx.framework.element.SettleInd[] _settleInd) {
        this._settleInd = _settleInd;
    }

    /**
     * Getter for settleInd
     * @return a org.sourceforge.ifx.framework.element.SettleInd[]
     */
    public org.sourceforge.ifx.framework.element.SettleInd[] getSettleInd() {
        return _settleInd;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TruncatedInd[] _truncatedInd;

    /** 
     * Setter for truncatedInd
     * @param truncatedInd the org.sourceforge.ifx.framework.element.TruncatedInd[] to set
     */
    public void setTruncatedInd(org.sourceforge.ifx.framework.element.TruncatedInd[] _truncatedInd) {
        this._truncatedInd = _truncatedInd;
    }

    /**
     * Getter for truncatedInd
     * @return a org.sourceforge.ifx.framework.element.TruncatedInd[]
     */
    public org.sourceforge.ifx.framework.element.TruncatedInd[] getTruncatedInd() {
        return _truncatedInd;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditMediaChkInfo[] _creditMediaChkInfo;

    /** 
     * Setter for creditMediaChkInfo
     * @param creditMediaChkInfo the org.sourceforge.ifx.framework.element.CreditMediaChkInfo[] to set
     */
    public void setCreditMediaChkInfo(org.sourceforge.ifx.framework.element.CreditMediaChkInfo[] _creditMediaChkInfo) {
        this._creditMediaChkInfo = _creditMediaChkInfo;
    }

    /**
     * Getter for creditMediaChkInfo
     * @return a org.sourceforge.ifx.framework.element.CreditMediaChkInfo[]
     */
    public org.sourceforge.ifx.framework.element.CreditMediaChkInfo[] getCreditMediaChkInfo() {
        return _creditMediaChkInfo;
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
       * ChkAcceptType
       * CompositeCurAmt
       * CreditId
       * SettleInd
       * TruncatedInd
       * CreditMediaChkInfo
       */
    public final String[] ELEMENTS = {
              "ChkAcceptType"
                 ,"CompositeCurAmt"
                 ,"CreditId"
                 ,"SettleInd"
                 ,"TruncatedInd"
                 ,"CreditMediaChkInfo"
          };
}
