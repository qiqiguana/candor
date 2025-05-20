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
public class Fee_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public Fee_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.FeeType _feeType;

    /** 
     * Setter for feeType
     * @param feeType the org.sourceforge.ifx.framework.element.FeeType to set
     */
    public void setFeeType(org.sourceforge.ifx.framework.element.FeeType _feeType) {
        this._feeType = _feeType;
    }

    /**
     * Getter for feeType
     * @return a org.sourceforge.ifx.framework.element.FeeType
     */
    public org.sourceforge.ifx.framework.element.FeeType getFeeType() {
        return _feeType;
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
    private org.sourceforge.ifx.framework.element.Rate _rate;

    /** 
     * Setter for rate
     * @param rate the org.sourceforge.ifx.framework.element.Rate to set
     */
    public void setRate(org.sourceforge.ifx.framework.element.Rate _rate) {
        this._rate = _rate;
    }

    /**
     * Getter for rate
     * @return a org.sourceforge.ifx.framework.element.Rate
     */
    public org.sourceforge.ifx.framework.element.Rate getRate() {
        return _rate;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MinCurAmt _minCurAmt;

    /** 
     * Setter for minCurAmt
     * @param minCurAmt the org.sourceforge.ifx.framework.element.MinCurAmt to set
     */
    public void setMinCurAmt(org.sourceforge.ifx.framework.element.MinCurAmt _minCurAmt) {
        this._minCurAmt = _minCurAmt;
    }

    /**
     * Getter for minCurAmt
     * @return a org.sourceforge.ifx.framework.element.MinCurAmt
     */
    public org.sourceforge.ifx.framework.element.MinCurAmt getMinCurAmt() {
        return _minCurAmt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MaxCurAmt _maxCurAmt;

    /** 
     * Setter for maxCurAmt
     * @param maxCurAmt the org.sourceforge.ifx.framework.element.MaxCurAmt to set
     */
    public void setMaxCurAmt(org.sourceforge.ifx.framework.element.MaxCurAmt _maxCurAmt) {
        this._maxCurAmt = _maxCurAmt;
    }

    /**
     * Getter for maxCurAmt
     * @return a org.sourceforge.ifx.framework.element.MaxCurAmt
     */
    public org.sourceforge.ifx.framework.element.MaxCurAmt getMaxCurAmt() {
        return _maxCurAmt;
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
       * FeeType
       * CurAmt
       * Rate
       * MinCurAmt
       * MaxCurAmt
       */
    public final String[] ELEMENTS = {
              "FeeType"
                 ,"CurAmt"
                 ,"Rate"
                 ,"MinCurAmt"
                 ,"MaxCurAmt"
          };
}
