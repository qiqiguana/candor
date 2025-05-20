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
public class CashAccount3
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CashAccount3() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.AccountIdentification1Choice _accountIdentification1Choice;

    /** 
     * Setter for accountIdentification1Choice
     * @param accountIdentification1Choice the org.sourceforge.ifx.framework.pain004.complextype.AccountIdentification1Choice to set
     */
    public void setAccountIdentification1Choice(org.sourceforge.ifx.framework.pain004.complextype.AccountIdentification1Choice _accountIdentification1Choice) {
        this._accountIdentification1Choice = _accountIdentification1Choice;
    }

    /**
     * Getter for accountIdentification1Choice
     * @return a org.sourceforge.ifx.framework.pain004.complextype.AccountIdentification1Choice
     */
    public org.sourceforge.ifx.framework.pain004.complextype.AccountIdentification1Choice getAccountIdentification1Choice() {
        return _accountIdentification1Choice;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.simpletype.CashAccountType3Code _cashAccountType3Code;

    /** 
     * Setter for cashAccountType3Code
     * @param cashAccountType3Code the org.sourceforge.ifx.framework.pain004.simpletype.CashAccountType3Code to set
     */
    public void setCashAccountType3Code(org.sourceforge.ifx.framework.pain004.simpletype.CashAccountType3Code _cashAccountType3Code) {
        this._cashAccountType3Code = _cashAccountType3Code;
    }

    /**
     * Getter for cashAccountType3Code
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.CashAccountType3Code
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.CashAccountType3Code getCashAccountType3Code() {
        return _cashAccountType3Code;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.simpletype.CurrencyCode _currencyCode;

    /** 
     * Setter for currencyCode
     * @param currencyCode the org.sourceforge.ifx.framework.pain004.simpletype.CurrencyCode to set
     */
    public void setCurrencyCode(org.sourceforge.ifx.framework.pain004.simpletype.CurrencyCode _currencyCode) {
        this._currencyCode = _currencyCode;
    }

    /**
     * Getter for currencyCode
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.CurrencyCode
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.CurrencyCode getCurrencyCode() {
        return _currencyCode;
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


    /**
     * Returns true if objects are equal, false otherwise.
     * @param obj the object to compare with.
     * @return true if equal, false if not.
     */
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /** Element ordering - 
       * AccountIdentification1Choice
       * CashAccountType3Code
       * CurrencyCode
       * Max70Text
       */
    public final String[] ELEMENTS = {
              "AccountIdentification1Choice"
                 ,"CashAccountType3Code"
                 ,"CurrencyCode"
                 ,"Max70Text"
          };
}
