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
public class BankAcctFeatLimit_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public BankAcctFeatLimit_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankAcctFeatLimitType _bankAcctFeatLimitType;

    /** 
     * Setter for bankAcctFeatLimitType
     * @param bankAcctFeatLimitType the org.sourceforge.ifx.framework.element.BankAcctFeatLimitType to set
     */
    public void setBankAcctFeatLimitType(org.sourceforge.ifx.framework.element.BankAcctFeatLimitType _bankAcctFeatLimitType) {
        this._bankAcctFeatLimitType = _bankAcctFeatLimitType;
    }

    /**
     * Getter for bankAcctFeatLimitType
     * @return a org.sourceforge.ifx.framework.element.BankAcctFeatLimitType
     */
    public org.sourceforge.ifx.framework.element.BankAcctFeatLimitType getBankAcctFeatLimitType() {
        return _bankAcctFeatLimitType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TrnSrc _trnSrc;

    /** 
     * Setter for trnSrc
     * @param trnSrc the org.sourceforge.ifx.framework.element.TrnSrc to set
     */
    public void setTrnSrc(org.sourceforge.ifx.framework.element.TrnSrc _trnSrc) {
        this._trnSrc = _trnSrc;
    }

    /**
     * Getter for trnSrc
     * @return a org.sourceforge.ifx.framework.element.TrnSrc
     */
    public org.sourceforge.ifx.framework.element.TrnSrc getTrnSrc() {
        return _trnSrc;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CurAmt _curAmt;

    /** 
     * Setter for curAmt
     * @param curAmt the org.sourceforge.ifx.framework.element.CurAmt to set
     */
    public void setCurAmt(org.sourceforge.ifx.framework.element.CurAmt _curAmt) {
        this._curAmt = _curAmt;
    }

    /**
     * Getter for curAmt
     * @return a org.sourceforge.ifx.framework.element.CurAmt
     */
    public org.sourceforge.ifx.framework.element.CurAmt getCurAmt() {
        return _curAmt;
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
       * BankAcctFeatLimitType
       * TrnSrc
       * CurAmt
       */
    public final String[] ELEMENTS = {
              "BankAcctFeatLimitType"
                 ,"TrnSrc"
                 ,"CurAmt"
          };
}
