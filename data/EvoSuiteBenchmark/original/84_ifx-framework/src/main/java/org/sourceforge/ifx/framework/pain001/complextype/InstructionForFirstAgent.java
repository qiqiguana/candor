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
public class InstructionForFirstAgent
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public InstructionForFirstAgent() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain001.simpletype.RemittanceLocationMethod1Code _remittanceLocationMethod1Code;

    /** 
     * Setter for remittanceLocationMethod1Code
     * @param remittanceLocationMethod1Code the org.sourceforge.ifx.framework.pain001.simpletype.RemittanceLocationMethod1Code to set
     */
    public void setRemittanceLocationMethod1Code(org.sourceforge.ifx.framework.pain001.simpletype.RemittanceLocationMethod1Code _remittanceLocationMethod1Code) {
        this._remittanceLocationMethod1Code = _remittanceLocationMethod1Code;
    }

    /**
     * Getter for remittanceLocationMethod1Code
     * @return a org.sourceforge.ifx.framework.pain001.simpletype.RemittanceLocationMethod1Code
     */
    public org.sourceforge.ifx.framework.pain001.simpletype.RemittanceLocationMethod1Code getRemittanceLocationMethod1Code() {
        return _remittanceLocationMethod1Code;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain001.simpletype.Max128Text _max128Text;

    /** 
     * Setter for max128Text
     * @param max128Text the org.sourceforge.ifx.framework.pain001.simpletype.Max128Text to set
     */
    public void setMax128Text(org.sourceforge.ifx.framework.pain001.simpletype.Max128Text _max128Text) {
        this._max128Text = _max128Text;
    }

    /**
     * Getter for max128Text
     * @return a org.sourceforge.ifx.framework.pain001.simpletype.Max128Text
     */
    public org.sourceforge.ifx.framework.pain001.simpletype.Max128Text getMax128Text() {
        return _max128Text;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain001.complextype.NameAndAddress3 _nameAndAddress3;

    /** 
     * Setter for nameAndAddress3
     * @param nameAndAddress3 the org.sourceforge.ifx.framework.pain001.complextype.NameAndAddress3 to set
     */
    public void setNameAndAddress3(org.sourceforge.ifx.framework.pain001.complextype.NameAndAddress3 _nameAndAddress3) {
        this._nameAndAddress3 = _nameAndAddress3;
    }

    /**
     * Getter for nameAndAddress3
     * @return a org.sourceforge.ifx.framework.pain001.complextype.NameAndAddress3
     */
    public org.sourceforge.ifx.framework.pain001.complextype.NameAndAddress3 getNameAndAddress3() {
        return _nameAndAddress3;
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
    private org.sourceforge.ifx.framework.pain001.simpletype.Max140Text _max140Text;

    /** 
     * Setter for max140Text
     * @param max140Text the org.sourceforge.ifx.framework.pain001.simpletype.Max140Text to set
     */
    public void setMax140Text(org.sourceforge.ifx.framework.pain001.simpletype.Max140Text _max140Text) {
        this._max140Text = _max140Text;
    }

    /**
     * Getter for max140Text
     * @return a org.sourceforge.ifx.framework.pain001.simpletype.Max140Text
     */
    public org.sourceforge.ifx.framework.pain001.simpletype.Max140Text getMax140Text() {
        return _max140Text;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain001.complextype.TaxInformation1 _taxInformation1;

    /** 
     * Setter for taxInformation1
     * @param taxInformation1 the org.sourceforge.ifx.framework.pain001.complextype.TaxInformation1 to set
     */
    public void setTaxInformation1(org.sourceforge.ifx.framework.pain001.complextype.TaxInformation1 _taxInformation1) {
        this._taxInformation1 = _taxInformation1;
    }

    /**
     * Getter for taxInformation1
     * @return a org.sourceforge.ifx.framework.pain001.complextype.TaxInformation1
     */
    public org.sourceforge.ifx.framework.pain001.complextype.TaxInformation1 getTaxInformation1() {
        return _taxInformation1;
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
       * RemittanceLocationMethod1Code
       * Max128Text
       * NameAndAddress3
       * Max35Text
       * Max140Text
       * TaxInformation1
       */
    public final String[] ELEMENTS = {
              "RemittanceLocationMethod1Code"
                 ,"Max128Text"
                 ,"NameAndAddress3"
                 ,"Max35Text"
                 ,"Max140Text"
                 ,"TaxInformation1"
          };
}
