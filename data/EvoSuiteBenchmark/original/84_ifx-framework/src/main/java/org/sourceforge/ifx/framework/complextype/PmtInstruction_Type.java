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
public class PmtInstruction_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PmtInstruction_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Country _country;

    /** 
     * Setter for country
     * @param country the org.sourceforge.ifx.framework.element.Country to set
     */
    public void setCountry(org.sourceforge.ifx.framework.element.Country _country) {
        this._country = _country;
    }

    /**
     * Getter for country
     * @return a org.sourceforge.ifx.framework.element.Country
     */
    public org.sourceforge.ifx.framework.element.Country getCountry() {
        return _country;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtFormat _pmtFormat;

    /** 
     * Setter for pmtFormat
     * @param pmtFormat the org.sourceforge.ifx.framework.element.PmtFormat to set
     */
    public void setPmtFormat(org.sourceforge.ifx.framework.element.PmtFormat _pmtFormat) {
        this._pmtFormat = _pmtFormat;
    }

    /**
     * Getter for pmtFormat
     * @return a org.sourceforge.ifx.framework.element.PmtFormat
     */
    public org.sourceforge.ifx.framework.element.PmtFormat getPmtFormat() {
        return _pmtFormat;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RefInfo[] _refInfo;

    /** 
     * Setter for refInfo
     * @param refInfo the org.sourceforge.ifx.framework.element.RefInfo[] to set
     */
    public void setRefInfo(org.sourceforge.ifx.framework.element.RefInfo[] _refInfo) {
        this._refInfo = _refInfo;
    }

    /**
     * Getter for refInfo
     * @return a org.sourceforge.ifx.framework.element.RefInfo[]
     */
    public org.sourceforge.ifx.framework.element.RefInfo[] getRefInfo() {
        return _refInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.IntermediaryDepAcct[] _intermediaryDepAcct;

    /** 
     * Setter for intermediaryDepAcct
     * @param intermediaryDepAcct the org.sourceforge.ifx.framework.element.IntermediaryDepAcct[] to set
     */
    public void setIntermediaryDepAcct(org.sourceforge.ifx.framework.element.IntermediaryDepAcct[] _intermediaryDepAcct) {
        this._intermediaryDepAcct = _intermediaryDepAcct;
    }

    /**
     * Getter for intermediaryDepAcct
     * @return a org.sourceforge.ifx.framework.element.IntermediaryDepAcct[]
     */
    public org.sourceforge.ifx.framework.element.IntermediaryDepAcct[] getIntermediaryDepAcct() {
        return _intermediaryDepAcct;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.FeeChargeAlloc[] _feeChargeAlloc;

    /** 
     * Setter for feeChargeAlloc
     * @param feeChargeAlloc the org.sourceforge.ifx.framework.element.FeeChargeAlloc[] to set
     */
    public void setFeeChargeAlloc(org.sourceforge.ifx.framework.element.FeeChargeAlloc[] _feeChargeAlloc) {
        this._feeChargeAlloc = _feeChargeAlloc;
    }

    /**
     * Getter for feeChargeAlloc
     * @return a org.sourceforge.ifx.framework.element.FeeChargeAlloc[]
     */
    public org.sourceforge.ifx.framework.element.FeeChargeAlloc[] getFeeChargeAlloc() {
        return _feeChargeAlloc;
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
       * Country
       * PmtFormat
       * RefInfo
       * IntermediaryDepAcct
       * FeeChargeAlloc
       */
    public final String[] ELEMENTS = {
              "Country"
                 ,"PmtFormat"
                 ,"RefInfo"
                 ,"IntermediaryDepAcct"
                 ,"FeeChargeAlloc"
          };
}
