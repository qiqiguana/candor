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
package org.sourceforge.ifx.framework.remit.complextype;

/**
 * Generated code.
 * 
 * @author org.sourceforge.ifx.tools.CodeGenerator
 */
public class CurrencyAmount
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CurrencyAmount() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Amt _amt;

    /** 
     * Setter for amt
     * @param amt the org.sourceforge.ifx.framework.element.Amt to set
     */
    public void setAmt(org.sourceforge.ifx.framework.element.Amt _amt) {
        this._amt = _amt;
    }

    /**
     * Getter for amt
     * @return a org.sourceforge.ifx.framework.element.Amt
     */
    public org.sourceforge.ifx.framework.element.Amt getAmt() {
        return _amt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CurCode _curCode;

    /** 
     * Setter for curCode
     * @param curCode the org.sourceforge.ifx.framework.element.CurCode to set
     */
    public void setCurCode(org.sourceforge.ifx.framework.element.CurCode _curCode) {
        this._curCode = _curCode;
    }

    /**
     * Getter for curCode
     * @return a org.sourceforge.ifx.framework.element.CurCode
     */
    public org.sourceforge.ifx.framework.element.CurCode getCurCode() {
        return _curCode;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CurRate _curRate;

    /** 
     * Setter for curRate
     * @param curRate the org.sourceforge.ifx.framework.element.CurRate to set
     */
    public void setCurRate(org.sourceforge.ifx.framework.element.CurRate _curRate) {
        this._curRate = _curRate;
    }

    /**
     * Getter for curRate
     * @return a org.sourceforge.ifx.framework.element.CurRate
     */
    public org.sourceforge.ifx.framework.element.CurRate getCurRate() {
        return _curRate;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CurConvertRule _curConvertRule;

    /** 
     * Setter for curConvertRule
     * @param curConvertRule the org.sourceforge.ifx.framework.element.CurConvertRule to set
     */
    public void setCurConvertRule(org.sourceforge.ifx.framework.element.CurConvertRule _curConvertRule) {
        this._curConvertRule = _curConvertRule;
    }

    /**
     * Getter for curConvertRule
     * @return a org.sourceforge.ifx.framework.element.CurConvertRule
     */
    public org.sourceforge.ifx.framework.element.CurConvertRule getCurConvertRule() {
        return _curConvertRule;
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
       * Amt
       * CurCode
       * CurRate
       * CurConvertRule
       */
    public final String[] ELEMENTS = {
              "Amt"
                 ,"CurCode"
                 ,"CurRate"
                 ,"CurConvertRule"
          };
}
