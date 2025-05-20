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
public class BankSvcChkSumMsgRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public BankSvcChkSumMsgRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustId _custId;

    /** 
     * Setter for custId
     * @param custId the org.sourceforge.ifx.framework.element.CustId to set
     */
    public void setCustId(org.sourceforge.ifx.framework.element.CustId _custId) {
        this._custId = _custId;
    }

    /**
     * Getter for custId
     * @return a org.sourceforge.ifx.framework.element.CustId
     */
    public org.sourceforge.ifx.framework.element.CustId getCustId() {
        return _custId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MsgRecDt _msgRecDt;

    /** 
     * Setter for msgRecDt
     * @param msgRecDt the org.sourceforge.ifx.framework.element.MsgRecDt to set
     */
    public void setMsgRecDt(org.sourceforge.ifx.framework.element.MsgRecDt _msgRecDt) {
        this._msgRecDt = _msgRecDt;
    }

    /**
     * Getter for msgRecDt
     * @return a org.sourceforge.ifx.framework.element.MsgRecDt
     */
    public org.sourceforge.ifx.framework.element.MsgRecDt getMsgRecDt() {
        return _msgRecDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankSvcChkSumAddRs _bankSvcChkSumAddRs;

    /** 
     * Setter for bankSvcChkSumAddRs
     * @param bankSvcChkSumAddRs the org.sourceforge.ifx.framework.element.BankSvcChkSumAddRs to set
     */
    public void setBankSvcChkSumAddRs(org.sourceforge.ifx.framework.element.BankSvcChkSumAddRs _bankSvcChkSumAddRs) {
        this._bankSvcChkSumAddRs = _bankSvcChkSumAddRs;
    }

    /**
     * Getter for bankSvcChkSumAddRs
     * @return a org.sourceforge.ifx.framework.element.BankSvcChkSumAddRs
     */
    public org.sourceforge.ifx.framework.element.BankSvcChkSumAddRs getBankSvcChkSumAddRs() {
        return _bankSvcChkSumAddRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankSvcChkSumModRs _bankSvcChkSumModRs;

    /** 
     * Setter for bankSvcChkSumModRs
     * @param bankSvcChkSumModRs the org.sourceforge.ifx.framework.element.BankSvcChkSumModRs to set
     */
    public void setBankSvcChkSumModRs(org.sourceforge.ifx.framework.element.BankSvcChkSumModRs _bankSvcChkSumModRs) {
        this._bankSvcChkSumModRs = _bankSvcChkSumModRs;
    }

    /**
     * Getter for bankSvcChkSumModRs
     * @return a org.sourceforge.ifx.framework.element.BankSvcChkSumModRs
     */
    public org.sourceforge.ifx.framework.element.BankSvcChkSumModRs getBankSvcChkSumModRs() {
        return _bankSvcChkSumModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankSvcChkSumStatusModRs _bankSvcChkSumStatusModRs;

    /** 
     * Setter for bankSvcChkSumStatusModRs
     * @param bankSvcChkSumStatusModRs the org.sourceforge.ifx.framework.element.BankSvcChkSumStatusModRs to set
     */
    public void setBankSvcChkSumStatusModRs(org.sourceforge.ifx.framework.element.BankSvcChkSumStatusModRs _bankSvcChkSumStatusModRs) {
        this._bankSvcChkSumStatusModRs = _bankSvcChkSumStatusModRs;
    }

    /**
     * Getter for bankSvcChkSumStatusModRs
     * @return a org.sourceforge.ifx.framework.element.BankSvcChkSumStatusModRs
     */
    public org.sourceforge.ifx.framework.element.BankSvcChkSumStatusModRs getBankSvcChkSumStatusModRs() {
        return _bankSvcChkSumStatusModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankSvcChkSumDelRs _bankSvcChkSumDelRs;

    /** 
     * Setter for bankSvcChkSumDelRs
     * @param bankSvcChkSumDelRs the org.sourceforge.ifx.framework.element.BankSvcChkSumDelRs to set
     */
    public void setBankSvcChkSumDelRs(org.sourceforge.ifx.framework.element.BankSvcChkSumDelRs _bankSvcChkSumDelRs) {
        this._bankSvcChkSumDelRs = _bankSvcChkSumDelRs;
    }

    /**
     * Getter for bankSvcChkSumDelRs
     * @return a org.sourceforge.ifx.framework.element.BankSvcChkSumDelRs
     */
    public org.sourceforge.ifx.framework.element.BankSvcChkSumDelRs getBankSvcChkSumDelRs() {
        return _bankSvcChkSumDelRs;
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
       * CustId
       * MsgRecDt
       * BankSvcChkSumAddRs
       * BankSvcChkSumModRs
       * BankSvcChkSumStatusModRs
       * BankSvcChkSumDelRs
       */
    public final String[] ELEMENTS = {
              "CustId"
                 ,"MsgRecDt"
                 ,"BankSvcChkSumAddRs"
                 ,"BankSvcChkSumModRs"
                 ,"BankSvcChkSumStatusModRs"
                 ,"BankSvcChkSumDelRs"
          };
}
