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
package org.sourceforge.ifx.framework.pain004.complextype;

/**
 * Generated code.
 * 
 * @author org.sourceforge.ifx.tools.CodeGenerator
 */
public class GeneralInformation2
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public GeneralInformation2() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.simpletype.Max35Text _max35Text;

    /** 
     * Setter for max35Text
     * @param max35Text the org.sourceforge.ifx.framework.pain004.simpletype.Max35Text to set
     */
    public void setMax35Text(org.sourceforge.ifx.framework.pain004.simpletype.Max35Text _max35Text) {
        this._max35Text = _max35Text;
    }

    /**
     * Getter for max35Text
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.Max35Text
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.Max35Text getMax35Text() {
        return _max35Text;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.simpletype.ISODateTime _iSODateTime;

    /** 
     * Setter for iSODateTime
     * @param iSODateTime the org.sourceforge.ifx.framework.pain004.simpletype.ISODateTime to set
     */
    public void setISODateTime(org.sourceforge.ifx.framework.pain004.simpletype.ISODateTime _iSODateTime) {
        this._iSODateTime = _iSODateTime;
    }

    /**
     * Getter for iSODateTime
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.ISODateTime
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.ISODateTime getISODateTime() {
        return _iSODateTime;
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


    /**
     * Returns true if objects are equal, false otherwise.
     * @param obj the object to compare with.
     * @return true if equal, false if not.
     */
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /** Element ordering - 
       * Max35Text
       * ISODateTime
       * BranchAndFinancialInstitutionIdentification
       * PartyIdentification1
       */
    public final String[] ELEMENTS = {
              "Max35Text"
                 ,"ISODateTime"
                 ,"BranchAndFinancialInstitutionIdentification"
                 ,"PartyIdentification1"
          };
}
