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
public class FeeChargeAlloc_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public FeeChargeAlloc_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChargeRegulation _chargeRegulation;

    /** 
     * Setter for chargeRegulation
     * @param chargeRegulation the org.sourceforge.ifx.framework.element.ChargeRegulation to set
     */
    public void setChargeRegulation(org.sourceforge.ifx.framework.element.ChargeRegulation _chargeRegulation) {
        this._chargeRegulation = _chargeRegulation;
    }

    /**
     * Getter for chargeRegulation
     * @return a org.sourceforge.ifx.framework.element.ChargeRegulation
     */
    public org.sourceforge.ifx.framework.element.ChargeRegulation getChargeRegulation() {
        return _chargeRegulation;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Fee _fee;

    /** 
     * Setter for fee
     * @param fee the org.sourceforge.ifx.framework.element.Fee to set
     */
    public void setFee(org.sourceforge.ifx.framework.element.Fee _fee) {
        this._fee = _fee;
    }

    /**
     * Getter for fee
     * @return a org.sourceforge.ifx.framework.element.Fee
     */
    public org.sourceforge.ifx.framework.element.Fee getFee() {
        return _fee;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAcctId _depAcctId;

    /** 
     * Setter for depAcctId
     * @param depAcctId the org.sourceforge.ifx.framework.element.DepAcctId to set
     */
    public void setDepAcctId(org.sourceforge.ifx.framework.element.DepAcctId _depAcctId) {
        this._depAcctId = _depAcctId;
    }

    /**
     * Getter for depAcctId
     * @return a org.sourceforge.ifx.framework.element.DepAcctId
     */
    public org.sourceforge.ifx.framework.element.DepAcctId getDepAcctId() {
        return _depAcctId;
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
       * ChargeRegulation
       * Fee
       * DepAcctId
       */
    public final String[] ELEMENTS = {
              "ChargeRegulation"
                 ,"Fee"
                 ,"DepAcctId"
          };
}
