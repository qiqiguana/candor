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
public class ForExRateInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public ForExRateInfo_Type() {
        super();
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
    private org.sourceforge.ifx.framework.element.CurRate _curRate;

    /** 
     * Setter for curRate
     * @param curRate the org.sourceforge.ifx.framework.element.CurRate to set
     */
    public void setCurRate(org.sourceforge.ifx.framework.element.CurRate _curRate) {
        this._curRate = _curRate;
    }

    /**
     * Getter for curRate
     * @return a org.sourceforge.ifx.framework.element.CurRate
     */
    public org.sourceforge.ifx.framework.element.CurRate getCurRate() {
        return _curRate;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CurConvertRule _curConvertRule;

    /** 
     * Setter for curConvertRule
     * @param curConvertRule the org.sourceforge.ifx.framework.element.CurConvertRule to set
     */
    public void setCurConvertRule(org.sourceforge.ifx.framework.element.CurConvertRule _curConvertRule) {
        this._curConvertRule = _curConvertRule;
    }

    /**
     * Getter for curConvertRule
     * @return a org.sourceforge.ifx.framework.element.CurConvertRule
     */
    public org.sourceforge.ifx.framework.element.CurConvertRule getCurConvertRule() {
        return _curConvertRule;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Fee[] _fee;

    /** 
     * Setter for fee
     * @param fee the org.sourceforge.ifx.framework.element.Fee[] to set
     */
    public void setFee(org.sourceforge.ifx.framework.element.Fee[] _fee) {
        this._fee = _fee;
    }

    /**
     * Getter for fee
     * @return a org.sourceforge.ifx.framework.element.Fee[]
     */
    public org.sourceforge.ifx.framework.element.Fee[] getFee() {
        return _fee;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ExpDt[] _expDt;

    /** 
     * Setter for expDt
     * @param expDt the org.sourceforge.ifx.framework.element.ExpDt[] to set
     */
    public void setExpDt(org.sourceforge.ifx.framework.element.ExpDt[] _expDt) {
        this._expDt = _expDt;
    }

    /**
     * Getter for expDt
     * @return a org.sourceforge.ifx.framework.element.ExpDt[]
     */
    public org.sourceforge.ifx.framework.element.ExpDt[] getExpDt() {
        return _expDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CSPRefId[] _cSPRefId;

    /** 
     * Setter for cSPRefId
     * @param cSPRefId the org.sourceforge.ifx.framework.element.CSPRefId[] to set
     */
    public void setCSPRefId(org.sourceforge.ifx.framework.element.CSPRefId[] _cSPRefId) {
        this._cSPRefId = _cSPRefId;
    }

    /**
     * Getter for cSPRefId
     * @return a org.sourceforge.ifx.framework.element.CSPRefId[]
     */
    public org.sourceforge.ifx.framework.element.CSPRefId[] getCSPRefId() {
        return _cSPRefId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SPRefId[] _sPRefId;

    /** 
     * Setter for sPRefId
     * @param sPRefId the org.sourceforge.ifx.framework.element.SPRefId[] to set
     */
    public void setSPRefId(org.sourceforge.ifx.framework.element.SPRefId[] _sPRefId) {
        this._sPRefId = _sPRefId;
    }

    /**
     * Getter for sPRefId
     * @return a org.sourceforge.ifx.framework.element.SPRefId[]
     */
    public org.sourceforge.ifx.framework.element.SPRefId[] getSPRefId() {
        return _sPRefId;
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
       * CurAmt
       * CurRate
       * CurConvertRule
       * Fee
       * ExpDt
       * CSPRefId
       * SPRefId
       */
    public final String[] ELEMENTS = {
              "CurAmt"
                 ,"CurRate"
                 ,"CurConvertRule"
                 ,"Fee"
                 ,"ExpDt"
                 ,"CSPRefId"
                 ,"SPRefId"
          };
}
