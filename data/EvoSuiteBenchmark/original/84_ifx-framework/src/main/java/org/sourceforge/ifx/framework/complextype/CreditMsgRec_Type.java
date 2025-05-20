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
public class CreditMsgRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CreditMsgRec_Type() {
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
    private org.sourceforge.ifx.framework.element.CreditAddRs _creditAddRs;

    /** 
     * Setter for creditAddRs
     * @param creditAddRs the org.sourceforge.ifx.framework.element.CreditAddRs to set
     */
    public void setCreditAddRs(org.sourceforge.ifx.framework.element.CreditAddRs _creditAddRs) {
        this._creditAddRs = _creditAddRs;
    }

    /**
     * Getter for creditAddRs
     * @return a org.sourceforge.ifx.framework.element.CreditAddRs
     */
    public org.sourceforge.ifx.framework.element.CreditAddRs getCreditAddRs() {
        return _creditAddRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditModRs _creditModRs;

    /** 
     * Setter for creditModRs
     * @param creditModRs the org.sourceforge.ifx.framework.element.CreditModRs to set
     */
    public void setCreditModRs(org.sourceforge.ifx.framework.element.CreditModRs _creditModRs) {
        this._creditModRs = _creditModRs;
    }

    /**
     * Getter for creditModRs
     * @return a org.sourceforge.ifx.framework.element.CreditModRs
     */
    public org.sourceforge.ifx.framework.element.CreditModRs getCreditModRs() {
        return _creditModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditRevRs _creditRevRs;

    /** 
     * Setter for creditRevRs
     * @param creditRevRs the org.sourceforge.ifx.framework.element.CreditRevRs to set
     */
    public void setCreditRevRs(org.sourceforge.ifx.framework.element.CreditRevRs _creditRevRs) {
        this._creditRevRs = _creditRevRs;
    }

    /**
     * Getter for creditRevRs
     * @return a org.sourceforge.ifx.framework.element.CreditRevRs
     */
    public org.sourceforge.ifx.framework.element.CreditRevRs getCreditRevRs() {
        return _creditRevRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditStatusModRs _creditStatusModRs;

    /** 
     * Setter for creditStatusModRs
     * @param creditStatusModRs the org.sourceforge.ifx.framework.element.CreditStatusModRs to set
     */
    public void setCreditStatusModRs(org.sourceforge.ifx.framework.element.CreditStatusModRs _creditStatusModRs) {
        this._creditStatusModRs = _creditStatusModRs;
    }

    /**
     * Getter for creditStatusModRs
     * @return a org.sourceforge.ifx.framework.element.CreditStatusModRs
     */
    public org.sourceforge.ifx.framework.element.CreditStatusModRs getCreditStatusModRs() {
        return _creditStatusModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditCanRs _creditCanRs;

    /** 
     * Setter for creditCanRs
     * @param creditCanRs the org.sourceforge.ifx.framework.element.CreditCanRs to set
     */
    public void setCreditCanRs(org.sourceforge.ifx.framework.element.CreditCanRs _creditCanRs) {
        this._creditCanRs = _creditCanRs;
    }

    /**
     * Getter for creditCanRs
     * @return a org.sourceforge.ifx.framework.element.CreditCanRs
     */
    public org.sourceforge.ifx.framework.element.CreditCanRs getCreditCanRs() {
        return _creditCanRs;
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
       * CreditAddRs
       * CreditModRs
       * CreditRevRs
       * CreditStatusModRs
       * CreditCanRs
       */
    public final String[] ELEMENTS = {
              "CustId"
                 ,"MsgRecDt"
                 ,"CreditAddRs"
                 ,"CreditModRs"
                 ,"CreditRevRs"
                 ,"CreditStatusModRs"
                 ,"CreditCanRs"
          };
}
