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
package org.sourceforge.ifx.framework.pain001.complextype;

/**
 * Generated code.
 * 
 * @author org.sourceforge.ifx.tools.CodeGenerator
 */
public class FinancialInstitutionIdentification1
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public FinancialInstitutionIdentification1() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.simpletype.BICIdentifier _bICIdentifier;

    /** 
     * Setter for bICIdentifier
     * @param bICIdentifier the org.sourceforge.ifx.framework.pain004.simpletype.BICIdentifier to set
     */
    public void setBICIdentifier(org.sourceforge.ifx.framework.pain004.simpletype.BICIdentifier _bICIdentifier) {
        this._bICIdentifier = _bICIdentifier;
    }

    /**
     * Getter for bICIdentifier
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.BICIdentifier
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.BICIdentifier getBICIdentifier() {
        return _bICIdentifier;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.ClearingSystemMemberIdentificationChoice _clearingSystemMemberIdentificationChoice;

    /** 
     * Setter for clearingSystemMemberIdentificationChoice
     * @param clearingSystemMemberIdentificationChoice the org.sourceforge.ifx.framework.pain004.complextype.ClearingSystemMemberIdentificationChoice to set
     */
    public void setClearingSystemMemberIdentificationChoice(org.sourceforge.ifx.framework.pain004.complextype.ClearingSystemMemberIdentificationChoice _clearingSystemMemberIdentificationChoice) {
        this._clearingSystemMemberIdentificationChoice = _clearingSystemMemberIdentificationChoice;
    }

    /**
     * Getter for clearingSystemMemberIdentificationChoice
     * @return a org.sourceforge.ifx.framework.pain004.complextype.ClearingSystemMemberIdentificationChoice
     */
    public org.sourceforge.ifx.framework.pain004.complextype.ClearingSystemMemberIdentificationChoice getClearingSystemMemberIdentificationChoice() {
        return _clearingSystemMemberIdentificationChoice;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.simpletype.Max70Text _max70Text;

    /** 
     * Setter for max70Text
     * @param max70Text the org.sourceforge.ifx.framework.pain004.simpletype.Max70Text to set
     */
    public void setMax70Text(org.sourceforge.ifx.framework.pain004.simpletype.Max70Text _max70Text) {
        this._max70Text = _max70Text;
    }

    /**
     * Getter for max70Text
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.Max70Text
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.Max70Text getMax70Text() {
        return _max70Text;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.PostalAddress1 _postalAddress1;

    /** 
     * Setter for postalAddress1
     * @param postalAddress1 the org.sourceforge.ifx.framework.pain004.complextype.PostalAddress1 to set
     */
    public void setPostalAddress1(org.sourceforge.ifx.framework.pain004.complextype.PostalAddress1 _postalAddress1) {
        this._postalAddress1 = _postalAddress1;
    }

    /**
     * Getter for postalAddress1
     * @return a org.sourceforge.ifx.framework.pain004.complextype.PostalAddress1
     */
    public org.sourceforge.ifx.framework.pain004.complextype.PostalAddress1 getPostalAddress1() {
        return _postalAddress1;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.GenericIdentification3 _genericIdentification3;

    /** 
     * Setter for genericIdentification3
     * @param genericIdentification3 the org.sourceforge.ifx.framework.pain004.complextype.GenericIdentification3 to set
     */
    public void setGenericIdentification3(org.sourceforge.ifx.framework.pain004.complextype.GenericIdentification3 _genericIdentification3) {
        this._genericIdentification3 = _genericIdentification3;
    }

    /**
     * Getter for genericIdentification3
     * @return a org.sourceforge.ifx.framework.pain004.complextype.GenericIdentification3
     */
    public org.sourceforge.ifx.framework.pain004.complextype.GenericIdentification3 getGenericIdentification3() {
        return _genericIdentification3;
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
       * BICIdentifier
       * ClearingSystemMemberIdentificationChoice
       * Max70Text
       * PostalAddress1
       * GenericIdentification3
       */
    public final String[] ELEMENTS = {
              "BICIdentifier"
                 ,"ClearingSystemMemberIdentificationChoice"
                 ,"Max70Text"
                 ,"PostalAddress1"
                 ,"GenericIdentification3"
          };
}
