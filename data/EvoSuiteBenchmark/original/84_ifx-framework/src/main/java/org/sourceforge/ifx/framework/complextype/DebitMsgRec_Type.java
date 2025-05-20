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
public class DebitMsgRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DebitMsgRec_Type() {
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
    private org.sourceforge.ifx.framework.element.DebitAddRs _debitAddRs;

    /** 
     * Setter for debitAddRs
     * @param debitAddRs the org.sourceforge.ifx.framework.element.DebitAddRs to set
     */
    public void setDebitAddRs(org.sourceforge.ifx.framework.element.DebitAddRs _debitAddRs) {
        this._debitAddRs = _debitAddRs;
    }

    /**
     * Getter for debitAddRs
     * @return a org.sourceforge.ifx.framework.element.DebitAddRs
     */
    public org.sourceforge.ifx.framework.element.DebitAddRs getDebitAddRs() {
        return _debitAddRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitModRs _debitModRs;

    /** 
     * Setter for debitModRs
     * @param debitModRs the org.sourceforge.ifx.framework.element.DebitModRs to set
     */
    public void setDebitModRs(org.sourceforge.ifx.framework.element.DebitModRs _debitModRs) {
        this._debitModRs = _debitModRs;
    }

    /**
     * Getter for debitModRs
     * @return a org.sourceforge.ifx.framework.element.DebitModRs
     */
    public org.sourceforge.ifx.framework.element.DebitModRs getDebitModRs() {
        return _debitModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitRevRs _debitRevRs;

    /** 
     * Setter for debitRevRs
     * @param debitRevRs the org.sourceforge.ifx.framework.element.DebitRevRs to set
     */
    public void setDebitRevRs(org.sourceforge.ifx.framework.element.DebitRevRs _debitRevRs) {
        this._debitRevRs = _debitRevRs;
    }

    /**
     * Getter for debitRevRs
     * @return a org.sourceforge.ifx.framework.element.DebitRevRs
     */
    public org.sourceforge.ifx.framework.element.DebitRevRs getDebitRevRs() {
        return _debitRevRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitStatusModRs _debitStatusModRs;

    /** 
     * Setter for debitStatusModRs
     * @param debitStatusModRs the org.sourceforge.ifx.framework.element.DebitStatusModRs to set
     */
    public void setDebitStatusModRs(org.sourceforge.ifx.framework.element.DebitStatusModRs _debitStatusModRs) {
        this._debitStatusModRs = _debitStatusModRs;
    }

    /**
     * Getter for debitStatusModRs
     * @return a org.sourceforge.ifx.framework.element.DebitStatusModRs
     */
    public org.sourceforge.ifx.framework.element.DebitStatusModRs getDebitStatusModRs() {
        return _debitStatusModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitCanRs _debitCanRs;

    /** 
     * Setter for debitCanRs
     * @param debitCanRs the org.sourceforge.ifx.framework.element.DebitCanRs to set
     */
    public void setDebitCanRs(org.sourceforge.ifx.framework.element.DebitCanRs _debitCanRs) {
        this._debitCanRs = _debitCanRs;
    }

    /**
     * Getter for debitCanRs
     * @return a org.sourceforge.ifx.framework.element.DebitCanRs
     */
    public org.sourceforge.ifx.framework.element.DebitCanRs getDebitCanRs() {
        return _debitCanRs;
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
       * DebitAddRs
       * DebitModRs
       * DebitRevRs
       * DebitStatusModRs
       * DebitCanRs
       */
    public final String[] ELEMENTS = {
              "CustId"
                 ,"MsgRecDt"
                 ,"DebitAddRs"
                 ,"DebitModRs"
                 ,"DebitRevRs"
                 ,"DebitStatusModRs"
                 ,"DebitCanRs"
          };
}
