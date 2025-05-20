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
public class CCAcctTrnRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CCAcctTrnRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankAcctTrnRec _bankAcctTrnRec;

    /** 
     * Setter for bankAcctTrnRec
     * @param bankAcctTrnRec the org.sourceforge.ifx.framework.element.BankAcctTrnRec to set
     */
    public void setBankAcctTrnRec(org.sourceforge.ifx.framework.element.BankAcctTrnRec _bankAcctTrnRec) {
        this._bankAcctTrnRec = _bankAcctTrnRec;
    }

    /**
     * Getter for bankAcctTrnRec
     * @return a org.sourceforge.ifx.framework.element.BankAcctTrnRec
     */
    public org.sourceforge.ifx.framework.element.BankAcctTrnRec getBankAcctTrnRec() {
        return _bankAcctTrnRec;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SalesSlipRefNum _salesSlipRefNum;

    /** 
     * Setter for salesSlipRefNum
     * @param salesSlipRefNum the org.sourceforge.ifx.framework.element.SalesSlipRefNum to set
     */
    public void setSalesSlipRefNum(org.sourceforge.ifx.framework.element.SalesSlipRefNum _salesSlipRefNum) {
        this._salesSlipRefNum = _salesSlipRefNum;
    }

    /**
     * Getter for salesSlipRefNum
     * @return a org.sourceforge.ifx.framework.element.SalesSlipRefNum
     */
    public org.sourceforge.ifx.framework.element.SalesSlipRefNum getSalesSlipRefNum() {
        return _salesSlipRefNum;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Memo _memo;

    /** 
     * Setter for memo
     * @param memo the org.sourceforge.ifx.framework.element.Memo to set
     */
    public void setMemo(org.sourceforge.ifx.framework.element.Memo _memo) {
        this._memo = _memo;
    }

    /**
     * Getter for memo
     * @return a org.sourceforge.ifx.framework.element.Memo
     */
    public org.sourceforge.ifx.framework.element.Memo getMemo() {
        return _memo;
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
       * BankAcctTrnRec
       * SalesSlipRefNum
       * Memo
       */
    public final String[] ELEMENTS = {
              "BankAcctTrnRec"
                 ,"SalesSlipRefNum"
                 ,"Memo"
          };
}
