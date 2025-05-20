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
public class LOCAcctRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public LOCAcctRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.LOCLimit _lOCLimit;

    /** 
     * Setter for lOCLimit
     * @param lOCLimit the org.sourceforge.ifx.framework.element.LOCLimit to set
     */
    public void setLOCLimit(org.sourceforge.ifx.framework.element.LOCLimit _lOCLimit) {
        this._lOCLimit = _lOCLimit;
    }

    /**
     * Getter for lOCLimit
     * @return a org.sourceforge.ifx.framework.element.LOCLimit
     */
    public org.sourceforge.ifx.framework.element.LOCLimit getLOCLimit() {
        return _lOCLimit;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MinPmtCurAmt _minPmtCurAmt;

    /** 
     * Setter for minPmtCurAmt
     * @param minPmtCurAmt the org.sourceforge.ifx.framework.element.MinPmtCurAmt to set
     */
    public void setMinPmtCurAmt(org.sourceforge.ifx.framework.element.MinPmtCurAmt _minPmtCurAmt) {
        this._minPmtCurAmt = _minPmtCurAmt;
    }

    /**
     * Getter for minPmtCurAmt
     * @return a org.sourceforge.ifx.framework.element.MinPmtCurAmt
     */
    public org.sourceforge.ifx.framework.element.MinPmtCurAmt getMinPmtCurAmt() {
        return _minPmtCurAmt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ExpDt _expDt;

    /** 
     * Setter for expDt
     * @param expDt the org.sourceforge.ifx.framework.element.ExpDt to set
     */
    public void setExpDt(org.sourceforge.ifx.framework.element.ExpDt _expDt) {
        this._expDt = _expDt;
    }

    /**
     * Getter for expDt
     * @return a org.sourceforge.ifx.framework.element.ExpDt
     */
    public org.sourceforge.ifx.framework.element.ExpDt getExpDt() {
        return _expDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.LoanInfoCommon _loanInfoCommon;

    /** 
     * Setter for loanInfoCommon
     * @param loanInfoCommon the org.sourceforge.ifx.framework.element.LoanInfoCommon to set
     */
    public void setLoanInfoCommon(org.sourceforge.ifx.framework.element.LoanInfoCommon _loanInfoCommon) {
        this._loanInfoCommon = _loanInfoCommon;
    }

    /**
     * Getter for loanInfoCommon
     * @return a org.sourceforge.ifx.framework.element.LoanInfoCommon
     */
    public org.sourceforge.ifx.framework.element.LoanInfoCommon getLoanInfoCommon() {
        return _loanInfoCommon;
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
       * LOCLimit
       * MinPmtCurAmt
       * ExpDt
       * LoanInfoCommon
       */
    public final String[] ELEMENTS = {
              "LOCLimit"
                 ,"MinPmtCurAmt"
                 ,"ExpDt"
                 ,"LoanInfoCommon"
          };
}
