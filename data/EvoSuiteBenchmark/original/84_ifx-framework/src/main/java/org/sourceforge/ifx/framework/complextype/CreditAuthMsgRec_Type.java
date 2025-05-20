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
public class CreditAuthMsgRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CreditAuthMsgRec_Type() {
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
    private org.sourceforge.ifx.framework.element.CreditAuthAddRs _creditAuthAddRs;

    /** 
     * Setter for creditAuthAddRs
     * @param creditAuthAddRs the org.sourceforge.ifx.framework.element.CreditAuthAddRs to set
     */
    public void setCreditAuthAddRs(org.sourceforge.ifx.framework.element.CreditAuthAddRs _creditAuthAddRs) {
        this._creditAuthAddRs = _creditAuthAddRs;
    }

    /**
     * Getter for creditAuthAddRs
     * @return a org.sourceforge.ifx.framework.element.CreditAuthAddRs
     */
    public org.sourceforge.ifx.framework.element.CreditAuthAddRs getCreditAuthAddRs() {
        return _creditAuthAddRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAuthModRs _creditAuthModRs;

    /** 
     * Setter for creditAuthModRs
     * @param creditAuthModRs the org.sourceforge.ifx.framework.element.CreditAuthModRs to set
     */
    public void setCreditAuthModRs(org.sourceforge.ifx.framework.element.CreditAuthModRs _creditAuthModRs) {
        this._creditAuthModRs = _creditAuthModRs;
    }

    /**
     * Getter for creditAuthModRs
     * @return a org.sourceforge.ifx.framework.element.CreditAuthModRs
     */
    public org.sourceforge.ifx.framework.element.CreditAuthModRs getCreditAuthModRs() {
        return _creditAuthModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAuthCanRs _creditAuthCanRs;

    /** 
     * Setter for creditAuthCanRs
     * @param creditAuthCanRs the org.sourceforge.ifx.framework.element.CreditAuthCanRs to set
     */
    public void setCreditAuthCanRs(org.sourceforge.ifx.framework.element.CreditAuthCanRs _creditAuthCanRs) {
        this._creditAuthCanRs = _creditAuthCanRs;
    }

    /**
     * Getter for creditAuthCanRs
     * @return a org.sourceforge.ifx.framework.element.CreditAuthCanRs
     */
    public org.sourceforge.ifx.framework.element.CreditAuthCanRs getCreditAuthCanRs() {
        return _creditAuthCanRs;
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
       * CreditAuthAddRs
       * CreditAuthModRs
       * CreditAuthCanRs
       */
    public final String[] ELEMENTS = {
              "CustId"
                 ,"MsgRecDt"
                 ,"CreditAuthAddRs"
                 ,"CreditAuthModRs"
                 ,"CreditAuthCanRs"
          };
}
