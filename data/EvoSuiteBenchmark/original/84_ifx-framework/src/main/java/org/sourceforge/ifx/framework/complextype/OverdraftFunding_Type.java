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
public class OverdraftFunding_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public OverdraftFunding_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAcctId _depAcctId;

    /** 
     * Setter for depAcctId
     * @param depAcctId the org.sourceforge.ifx.framework.element.DepAcctId to set
     */
    public void setDepAcctId(org.sourceforge.ifx.framework.element.DepAcctId _depAcctId) {
        this._depAcctId = _depAcctId;
    }

    /**
     * Getter for depAcctId
     * @return a org.sourceforge.ifx.framework.element.DepAcctId
     */
    public org.sourceforge.ifx.framework.element.DepAcctId getDepAcctId() {
        return _depAcctId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.LoanAcctId _loanAcctId;

    /** 
     * Setter for loanAcctId
     * @param loanAcctId the org.sourceforge.ifx.framework.element.LoanAcctId to set
     */
    public void setLoanAcctId(org.sourceforge.ifx.framework.element.LoanAcctId _loanAcctId) {
        this._loanAcctId = _loanAcctId;
    }

    /**
     * Getter for loanAcctId
     * @return a org.sourceforge.ifx.framework.element.LoanAcctId
     */
    public org.sourceforge.ifx.framework.element.LoanAcctId getLoanAcctId() {
        return _loanAcctId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.FundingPriority _fundingPriority;

    /** 
     * Setter for fundingPriority
     * @param fundingPriority the org.sourceforge.ifx.framework.element.FundingPriority to set
     */
    public void setFundingPriority(org.sourceforge.ifx.framework.element.FundingPriority _fundingPriority) {
        this._fundingPriority = _fundingPriority;
    }

    /**
     * Getter for fundingPriority
     * @return a org.sourceforge.ifx.framework.element.FundingPriority
     */
    public org.sourceforge.ifx.framework.element.FundingPriority getFundingPriority() {
        return _fundingPriority;
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
       * DepAcctId
       * LoanAcctId
       * FundingPriority
       */
    public final String[] ELEMENTS = {
              "DepAcctId"
                 ,"LoanAcctId"
                 ,"FundingPriority"
          };
}
