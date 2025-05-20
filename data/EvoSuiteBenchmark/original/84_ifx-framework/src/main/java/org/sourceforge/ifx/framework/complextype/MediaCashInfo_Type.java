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
public class MediaCashInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public MediaCashInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CashValidity _cashValidity;

    /** 
     * Setter for cashValidity
     * @param cashValidity the org.sourceforge.ifx.framework.element.CashValidity to set
     */
    public void setCashValidity(org.sourceforge.ifx.framework.element.CashValidity _cashValidity) {
        this._cashValidity = _cashValidity;
    }

    /**
     * Getter for cashValidity
     * @return a org.sourceforge.ifx.framework.element.CashValidity
     */
    public org.sourceforge.ifx.framework.element.CashValidity getCashValidity() {
        return _cashValidity;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CashQuality _cashQuality;

    /** 
     * Setter for cashQuality
     * @param cashQuality the org.sourceforge.ifx.framework.element.CashQuality to set
     */
    public void setCashQuality(org.sourceforge.ifx.framework.element.CashQuality _cashQuality) {
        this._cashQuality = _cashQuality;
    }

    /**
     * Getter for cashQuality
     * @return a org.sourceforge.ifx.framework.element.CashQuality
     */
    public org.sourceforge.ifx.framework.element.CashQuality getCashQuality() {
        return _cashQuality;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CashSignature _cashSignature;

    /** 
     * Setter for cashSignature
     * @param cashSignature the org.sourceforge.ifx.framework.element.CashSignature to set
     */
    public void setCashSignature(org.sourceforge.ifx.framework.element.CashSignature _cashSignature) {
        this._cashSignature = _cashSignature;
    }

    /**
     * Getter for cashSignature
     * @return a org.sourceforge.ifx.framework.element.CashSignature
     */
    public org.sourceforge.ifx.framework.element.CashSignature getCashSignature() {
        return _cashSignature;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CashSerialNum _cashSerialNum;

    /** 
     * Setter for cashSerialNum
     * @param cashSerialNum the org.sourceforge.ifx.framework.element.CashSerialNum to set
     */
    public void setCashSerialNum(org.sourceforge.ifx.framework.element.CashSerialNum _cashSerialNum) {
        this._cashSerialNum = _cashSerialNum;
    }

    /**
     * Getter for cashSerialNum
     * @return a org.sourceforge.ifx.framework.element.CashSerialNum
     */
    public org.sourceforge.ifx.framework.element.CashSerialNum getCashSerialNum() {
        return _cashSerialNum;
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
       * CashValidity
       * CashQuality
       * CashSignature
       * CashSerialNum
       */
    public final String[] ELEMENTS = {
              "CashValidity"
                 ,"CashQuality"
                 ,"CashSignature"
                 ,"CashSerialNum"
          };
}
