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
public class CustMsgRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CustMsgRec_Type() {
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
    private org.sourceforge.ifx.framework.element.CustAddRs _custAddRs;

    /** 
     * Setter for custAddRs
     * @param custAddRs the org.sourceforge.ifx.framework.element.CustAddRs to set
     */
    public void setCustAddRs(org.sourceforge.ifx.framework.element.CustAddRs _custAddRs) {
        this._custAddRs = _custAddRs;
    }

    /**
     * Getter for custAddRs
     * @return a org.sourceforge.ifx.framework.element.CustAddRs
     */
    public org.sourceforge.ifx.framework.element.CustAddRs getCustAddRs() {
        return _custAddRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustModRs _custModRs;

    /** 
     * Setter for custModRs
     * @param custModRs the org.sourceforge.ifx.framework.element.CustModRs to set
     */
    public void setCustModRs(org.sourceforge.ifx.framework.element.CustModRs _custModRs) {
        this._custModRs = _custModRs;
    }

    /**
     * Getter for custModRs
     * @return a org.sourceforge.ifx.framework.element.CustModRs
     */
    public org.sourceforge.ifx.framework.element.CustModRs getCustModRs() {
        return _custModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustStatusModRs _custStatusModRs;

    /** 
     * Setter for custStatusModRs
     * @param custStatusModRs the org.sourceforge.ifx.framework.element.CustStatusModRs to set
     */
    public void setCustStatusModRs(org.sourceforge.ifx.framework.element.CustStatusModRs _custStatusModRs) {
        this._custStatusModRs = _custStatusModRs;
    }

    /**
     * Getter for custStatusModRs
     * @return a org.sourceforge.ifx.framework.element.CustStatusModRs
     */
    public org.sourceforge.ifx.framework.element.CustStatusModRs getCustStatusModRs() {
        return _custStatusModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustDelRs _custDelRs;

    /** 
     * Setter for custDelRs
     * @param custDelRs the org.sourceforge.ifx.framework.element.CustDelRs to set
     */
    public void setCustDelRs(org.sourceforge.ifx.framework.element.CustDelRs _custDelRs) {
        this._custDelRs = _custDelRs;
    }

    /**
     * Getter for custDelRs
     * @return a org.sourceforge.ifx.framework.element.CustDelRs
     */
    public org.sourceforge.ifx.framework.element.CustDelRs getCustDelRs() {
        return _custDelRs;
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
       * CustAddRs
       * CustModRs
       * CustStatusModRs
       * CustDelRs
       */
    public final String[] ELEMENTS = {
              "CustId"
                 ,"MsgRecDt"
                 ,"CustAddRs"
                 ,"CustModRs"
                 ,"CustStatusModRs"
                 ,"CustDelRs"
          };
}
