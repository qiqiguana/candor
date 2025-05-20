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
public class BankSvcChkSum_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public BankSvcChkSum_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankSvcChkSumType _bankSvcChkSumType;

    /** 
     * Setter for bankSvcChkSumType
     * @param bankSvcChkSumType the org.sourceforge.ifx.framework.element.BankSvcChkSumType to set
     */
    public void setBankSvcChkSumType(org.sourceforge.ifx.framework.element.BankSvcChkSumType _bankSvcChkSumType) {
        this._bankSvcChkSumType = _bankSvcChkSumType;
    }

    /**
     * Getter for bankSvcChkSumType
     * @return a org.sourceforge.ifx.framework.element.BankSvcChkSumType
     */
    public org.sourceforge.ifx.framework.element.BankSvcChkSumType getBankSvcChkSumType() {
        return _bankSvcChkSumType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankSvcChkSumValue _bankSvcChkSumValue;

    /** 
     * Setter for bankSvcChkSumValue
     * @param bankSvcChkSumValue the org.sourceforge.ifx.framework.element.BankSvcChkSumValue to set
     */
    public void setBankSvcChkSumValue(org.sourceforge.ifx.framework.element.BankSvcChkSumValue _bankSvcChkSumValue) {
        this._bankSvcChkSumValue = _bankSvcChkSumValue;
    }

    /**
     * Getter for bankSvcChkSumValue
     * @return a org.sourceforge.ifx.framework.element.BankSvcChkSumValue
     */
    public org.sourceforge.ifx.framework.element.BankSvcChkSumValue getBankSvcChkSumValue() {
        return _bankSvcChkSumValue;
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
       * BankSvcChkSumType
       * BankSvcChkSumValue
       */
    public final String[] ELEMENTS = {
              "BankSvcChkSumType"
                 ,"BankSvcChkSumValue"
          };
}
