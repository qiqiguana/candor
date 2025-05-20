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
package org.sourceforge.ifx.framework.pain002.complextype;

/**
 * Generated code.
 * 
 * @author org.sourceforge.ifx.tools.CodeGenerator
 */
public class OriginalTransactionInformation1
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public OriginalTransactionInformation1() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.AmountType1Choice _amountType1Choice;

    /** 
     * Setter for amountType1Choice
     * @param amountType1Choice the org.sourceforge.ifx.framework.pain004.complextype.AmountType1Choice to set
     */
    public void setAmountType1Choice(org.sourceforge.ifx.framework.pain004.complextype.AmountType1Choice _amountType1Choice) {
        this._amountType1Choice = _amountType1Choice;
    }

    /**
     * Getter for amountType1Choice
     * @return a org.sourceforge.ifx.framework.pain004.complextype.AmountType1Choice
     */
    public org.sourceforge.ifx.framework.pain004.complextype.AmountType1Choice getAmountType1Choice() {
        return _amountType1Choice;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.PartyIdentification1 _partyIdentification1;

    /** 
     * Setter for partyIdentification1
     * @param partyIdentification1 the org.sourceforge.ifx.framework.pain004.complextype.PartyIdentification1 to set
     */
    public void setPartyIdentification1(org.sourceforge.ifx.framework.pain004.complextype.PartyIdentification1 _partyIdentification1) {
        this._partyIdentification1 = _partyIdentification1;
    }

    /**
     * Getter for partyIdentification1
     * @return a org.sourceforge.ifx.framework.pain004.complextype.PartyIdentification1
     */
    public org.sourceforge.ifx.framework.pain004.complextype.PartyIdentification1 getPartyIdentification1() {
        return _partyIdentification1;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.CashAccount3 _cashAccount3;

    /** 
     * Setter for cashAccount3
     * @param cashAccount3 the org.sourceforge.ifx.framework.pain004.complextype.CashAccount3 to set
     */
    public void setCashAccount3(org.sourceforge.ifx.framework.pain004.complextype.CashAccount3 _cashAccount3) {
        this._cashAccount3 = _cashAccount3;
    }

    /**
     * Getter for cashAccount3
     * @return a org.sourceforge.ifx.framework.pain004.complextype.CashAccount3
     */
    public org.sourceforge.ifx.framework.pain004.complextype.CashAccount3 getCashAccount3() {
        return _cashAccount3;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.BranchAndFinancialInstitutionIdentification _branchAndFinancialInstitutionIdentification;

    /** 
     * Setter for branchAndFinancialInstitutionIdentification
     * @param branchAndFinancialInstitutionIdentification the org.sourceforge.ifx.framework.pain004.complextype.BranchAndFinancialInstitutionIdentification to set
     */
    public void setBranchAndFinancialInstitutionIdentification(org.sourceforge.ifx.framework.pain004.complextype.BranchAndFinancialInstitutionIdentification _branchAndFinancialInstitutionIdentification) {
        this._branchAndFinancialInstitutionIdentification = _branchAndFinancialInstitutionIdentification;
    }

    /**
     * Getter for branchAndFinancialInstitutionIdentification
     * @return a org.sourceforge.ifx.framework.pain004.complextype.BranchAndFinancialInstitutionIdentification
     */
    public org.sourceforge.ifx.framework.pain004.complextype.BranchAndFinancialInstitutionIdentification getBranchAndFinancialInstitutionIdentification() {
        return _branchAndFinancialInstitutionIdentification;
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
       * AmountType1Choice
       * PartyIdentification1
       * CashAccount3
       * BranchAndFinancialInstitutionIdentification
       */
    public final String[] ELEMENTS = {
              "AmountType1Choice"
                 ,"PartyIdentification1"
                 ,"CashAccount3"
                 ,"BranchAndFinancialInstitutionIdentification"
          };
}
