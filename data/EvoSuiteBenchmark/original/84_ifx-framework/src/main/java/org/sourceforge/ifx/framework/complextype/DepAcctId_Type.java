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
public class DepAcctId_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DepAcctId_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AcctId _acctId;

    /** 
     * Setter for acctId
     * @param acctId the org.sourceforge.ifx.framework.element.AcctId to set
     */
    public void setAcctId(org.sourceforge.ifx.framework.element.AcctId _acctId) {
        this._acctId = _acctId;
    }

    /**
     * Getter for acctId
     * @return a org.sourceforge.ifx.framework.element.AcctId
     */
    public org.sourceforge.ifx.framework.element.AcctId getAcctId() {
        return _acctId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AcctType _acctType;

    /** 
     * Setter for acctType
     * @param acctType the org.sourceforge.ifx.framework.element.AcctType to set
     */
    public void setAcctType(org.sourceforge.ifx.framework.element.AcctType _acctType) {
        this._acctType = _acctType;
    }

    /**
     * Getter for acctType
     * @return a org.sourceforge.ifx.framework.element.AcctType
     */
    public org.sourceforge.ifx.framework.element.AcctType getAcctType() {
        return _acctType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AcctKey _acctKey;

    /** 
     * Setter for acctKey
     * @param acctKey the org.sourceforge.ifx.framework.element.AcctKey to set
     */
    public void setAcctKey(org.sourceforge.ifx.framework.element.AcctKey _acctKey) {
        this._acctKey = _acctKey;
    }

    /**
     * Getter for acctKey
     * @return a org.sourceforge.ifx.framework.element.AcctKey
     */
    public org.sourceforge.ifx.framework.element.AcctKey getAcctKey() {
        return _acctKey;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AcctCur _acctCur;

    /** 
     * Setter for acctCur
     * @param acctCur the org.sourceforge.ifx.framework.element.AcctCur to set
     */
    public void setAcctCur(org.sourceforge.ifx.framework.element.AcctCur _acctCur) {
        this._acctCur = _acctCur;
    }

    /**
     * Getter for acctCur
     * @return a org.sourceforge.ifx.framework.element.AcctCur
     */
    public org.sourceforge.ifx.framework.element.AcctCur getAcctCur() {
        return _acctCur;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankInfo _bankInfo;

    /** 
     * Setter for bankInfo
     * @param bankInfo the org.sourceforge.ifx.framework.element.BankInfo to set
     */
    public void setBankInfo(org.sourceforge.ifx.framework.element.BankInfo _bankInfo) {
        this._bankInfo = _bankInfo;
    }

    /**
     * Getter for bankInfo
     * @return a org.sourceforge.ifx.framework.element.BankInfo
     */
    public org.sourceforge.ifx.framework.element.BankInfo getBankInfo() {
        return _bankInfo;
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
       * AcctId
       * AcctType
       * AcctKey
       * AcctCur
       * BankInfo
       */
    public final String[] ELEMENTS = {
              "AcctId"
                 ,"AcctType"
                 ,"AcctKey"
                 ,"AcctCur"
                 ,"BankInfo"
          };
}
