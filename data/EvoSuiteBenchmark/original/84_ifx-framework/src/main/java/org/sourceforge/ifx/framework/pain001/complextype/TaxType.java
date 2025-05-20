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
public class TaxType
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public TaxType() {
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
    private org.sourceforge.ifx.framework.pain001.simpletype.PercentageRate _percentageRate;

    /** 
     * Setter for percentageRate
     * @param percentageRate the org.sourceforge.ifx.framework.pain001.simpletype.PercentageRate to set
     */
    public void setPercentageRate(org.sourceforge.ifx.framework.pain001.simpletype.PercentageRate _percentageRate) {
        this._percentageRate = _percentageRate;
    }

    /**
     * Getter for percentageRate
     * @return a org.sourceforge.ifx.framework.pain001.simpletype.PercentageRate
     */
    public org.sourceforge.ifx.framework.pain001.simpletype.PercentageRate getPercentageRate() {
        return _percentageRate;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.CurrencyAndAmount _currencyAndAmount;

    /** 
     * Setter for currencyAndAmount
     * @param currencyAndAmount the org.sourceforge.ifx.framework.pain004.complextype.CurrencyAndAmount to set
     */
    public void setCurrencyAndAmount(org.sourceforge.ifx.framework.pain004.complextype.CurrencyAndAmount _currencyAndAmount) {
        this._currencyAndAmount = _currencyAndAmount;
    }

    /**
     * Getter for currencyAndAmount
     * @return a org.sourceforge.ifx.framework.pain004.complextype.CurrencyAndAmount
     */
    public org.sourceforge.ifx.framework.pain004.complextype.CurrencyAndAmount getCurrencyAndAmount() {
        return _currencyAndAmount;
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
       * PercentageRate
       * CurrencyAndAmount
       */
    public final String[] ELEMENTS = {
              "Max35Text"
                 ,"PercentageRate"
                 ,"CurrencyAndAmount"
          };
}
