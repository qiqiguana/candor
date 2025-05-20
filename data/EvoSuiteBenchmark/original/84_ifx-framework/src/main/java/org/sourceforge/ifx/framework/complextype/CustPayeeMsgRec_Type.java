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
public class CustPayeeMsgRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CustPayeeMsgRec_Type() {
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
    private org.sourceforge.ifx.framework.element.CustPayeeAddRs _custPayeeAddRs;

    /** 
     * Setter for custPayeeAddRs
     * @param custPayeeAddRs the org.sourceforge.ifx.framework.element.CustPayeeAddRs to set
     */
    public void setCustPayeeAddRs(org.sourceforge.ifx.framework.element.CustPayeeAddRs _custPayeeAddRs) {
        this._custPayeeAddRs = _custPayeeAddRs;
    }

    /**
     * Getter for custPayeeAddRs
     * @return a org.sourceforge.ifx.framework.element.CustPayeeAddRs
     */
    public org.sourceforge.ifx.framework.element.CustPayeeAddRs getCustPayeeAddRs() {
        return _custPayeeAddRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustPayeeModRs _custPayeeModRs;

    /** 
     * Setter for custPayeeModRs
     * @param custPayeeModRs the org.sourceforge.ifx.framework.element.CustPayeeModRs to set
     */
    public void setCustPayeeModRs(org.sourceforge.ifx.framework.element.CustPayeeModRs _custPayeeModRs) {
        this._custPayeeModRs = _custPayeeModRs;
    }

    /**
     * Getter for custPayeeModRs
     * @return a org.sourceforge.ifx.framework.element.CustPayeeModRs
     */
    public org.sourceforge.ifx.framework.element.CustPayeeModRs getCustPayeeModRs() {
        return _custPayeeModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustPayeeTypeModRs _custPayeeTypeModRs;

    /** 
     * Setter for custPayeeTypeModRs
     * @param custPayeeTypeModRs the org.sourceforge.ifx.framework.element.CustPayeeTypeModRs to set
     */
    public void setCustPayeeTypeModRs(org.sourceforge.ifx.framework.element.CustPayeeTypeModRs _custPayeeTypeModRs) {
        this._custPayeeTypeModRs = _custPayeeTypeModRs;
    }

    /**
     * Getter for custPayeeTypeModRs
     * @return a org.sourceforge.ifx.framework.element.CustPayeeTypeModRs
     */
    public org.sourceforge.ifx.framework.element.CustPayeeTypeModRs getCustPayeeTypeModRs() {
        return _custPayeeTypeModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustPayeeDelRs _custPayeeDelRs;

    /** 
     * Setter for custPayeeDelRs
     * @param custPayeeDelRs the org.sourceforge.ifx.framework.element.CustPayeeDelRs to set
     */
    public void setCustPayeeDelRs(org.sourceforge.ifx.framework.element.CustPayeeDelRs _custPayeeDelRs) {
        this._custPayeeDelRs = _custPayeeDelRs;
    }

    /**
     * Getter for custPayeeDelRs
     * @return a org.sourceforge.ifx.framework.element.CustPayeeDelRs
     */
    public org.sourceforge.ifx.framework.element.CustPayeeDelRs getCustPayeeDelRs() {
        return _custPayeeDelRs;
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
       * CustPayeeAddRs
       * CustPayeeModRs
       * CustPayeeTypeModRs
       * CustPayeeDelRs
       */
    public final String[] ELEMENTS = {
              "CustId"
                 ,"MsgRecDt"
                 ,"CustPayeeAddRs"
                 ,"CustPayeeModRs"
                 ,"CustPayeeTypeModRs"
                 ,"CustPayeeDelRs"
          };
}
