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
public class LoanAcctRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public LoanAcctRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Term _term;

    /** 
     * Setter for term
     * @param term the org.sourceforge.ifx.framework.element.Term to set
     */
    public void setTerm(org.sourceforge.ifx.framework.element.Term _term) {
        this._term = _term;
    }

    /**
     * Getter for term
     * @return a org.sourceforge.ifx.framework.element.Term
     */
    public org.sourceforge.ifx.framework.element.Term getTerm() {
        return _term;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MatDt _matDt;

    /** 
     * Setter for matDt
     * @param matDt the org.sourceforge.ifx.framework.element.MatDt to set
     */
    public void setMatDt(org.sourceforge.ifx.framework.element.MatDt _matDt) {
        this._matDt = _matDt;
    }

    /**
     * Getter for matDt
     * @return a org.sourceforge.ifx.framework.element.MatDt
     */
    public org.sourceforge.ifx.framework.element.MatDt getMatDt() {
        return _matDt;
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
       * Term
       * MatDt
       * LoanInfoCommon
       */
    public final String[] ELEMENTS = {
              "Term"
                 ,"MatDt"
                 ,"LoanInfoCommon"
          };
}
