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
public class PostalAddress1
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PostalAddress1() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.simpletype.AddressType2Code _addressType2Code;

    /** 
     * Setter for addressType2Code
     * @param addressType2Code the org.sourceforge.ifx.framework.pain004.simpletype.AddressType2Code to set
     */
    public void setAddressType2Code(org.sourceforge.ifx.framework.pain004.simpletype.AddressType2Code _addressType2Code) {
        this._addressType2Code = _addressType2Code;
    }

    /**
     * Getter for addressType2Code
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.AddressType2Code
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.AddressType2Code getAddressType2Code() {
        return _addressType2Code;
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
    private org.sourceforge.ifx.framework.pain004.simpletype.Max16Text _max16Text;

    /** 
     * Setter for max16Text
     * @param max16Text the org.sourceforge.ifx.framework.pain004.simpletype.Max16Text to set
     */
    public void setMax16Text(org.sourceforge.ifx.framework.pain004.simpletype.Max16Text _max16Text) {
        this._max16Text = _max16Text;
    }

    /**
     * Getter for max16Text
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.Max16Text
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.Max16Text getMax16Text() {
        return _max16Text;
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
    private org.sourceforge.ifx.framework.pain004.simpletype.CountryCode _countryCode;

    /** 
     * Setter for countryCode
     * @param countryCode the org.sourceforge.ifx.framework.pain004.simpletype.CountryCode to set
     */
    public void setCountryCode(org.sourceforge.ifx.framework.pain004.simpletype.CountryCode _countryCode) {
        this._countryCode = _countryCode;
    }

    /**
     * Getter for countryCode
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.CountryCode
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.CountryCode getCountryCode() {
        return _countryCode;
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
       * AddressType2Code
       * Max70Text
       * Max16Text
       * Max35Text
       * CountryCode
       */
    public final String[] ELEMENTS = {
              "AddressType2Code"
                 ,"Max70Text"
                 ,"Max16Text"
                 ,"Max35Text"
                 ,"CountryCode"
          };
}
