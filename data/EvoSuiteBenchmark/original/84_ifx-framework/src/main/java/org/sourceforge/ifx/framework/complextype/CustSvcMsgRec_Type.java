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
public class CustSvcMsgRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CustSvcMsgRec_Type() {
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
    private org.sourceforge.ifx.framework.element.CustSvcAddRs _custSvcAddRs;

    /** 
     * Setter for custSvcAddRs
     * @param custSvcAddRs the org.sourceforge.ifx.framework.element.CustSvcAddRs to set
     */
    public void setCustSvcAddRs(org.sourceforge.ifx.framework.element.CustSvcAddRs _custSvcAddRs) {
        this._custSvcAddRs = _custSvcAddRs;
    }

    /**
     * Getter for custSvcAddRs
     * @return a org.sourceforge.ifx.framework.element.CustSvcAddRs
     */
    public org.sourceforge.ifx.framework.element.CustSvcAddRs getCustSvcAddRs() {
        return _custSvcAddRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustSvcModRs _custSvcModRs;

    /** 
     * Setter for custSvcModRs
     * @param custSvcModRs the org.sourceforge.ifx.framework.element.CustSvcModRs to set
     */
    public void setCustSvcModRs(org.sourceforge.ifx.framework.element.CustSvcModRs _custSvcModRs) {
        this._custSvcModRs = _custSvcModRs;
    }

    /**
     * Getter for custSvcModRs
     * @return a org.sourceforge.ifx.framework.element.CustSvcModRs
     */
    public org.sourceforge.ifx.framework.element.CustSvcModRs getCustSvcModRs() {
        return _custSvcModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustSvcStatusModRs _custSvcStatusModRs;

    /** 
     * Setter for custSvcStatusModRs
     * @param custSvcStatusModRs the org.sourceforge.ifx.framework.element.CustSvcStatusModRs to set
     */
    public void setCustSvcStatusModRs(org.sourceforge.ifx.framework.element.CustSvcStatusModRs _custSvcStatusModRs) {
        this._custSvcStatusModRs = _custSvcStatusModRs;
    }

    /**
     * Getter for custSvcStatusModRs
     * @return a org.sourceforge.ifx.framework.element.CustSvcStatusModRs
     */
    public org.sourceforge.ifx.framework.element.CustSvcStatusModRs getCustSvcStatusModRs() {
        return _custSvcStatusModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustSvcDelRs _custSvcDelRs;

    /** 
     * Setter for custSvcDelRs
     * @param custSvcDelRs the org.sourceforge.ifx.framework.element.CustSvcDelRs to set
     */
    public void setCustSvcDelRs(org.sourceforge.ifx.framework.element.CustSvcDelRs _custSvcDelRs) {
        this._custSvcDelRs = _custSvcDelRs;
    }

    /**
     * Getter for custSvcDelRs
     * @return a org.sourceforge.ifx.framework.element.CustSvcDelRs
     */
    public org.sourceforge.ifx.framework.element.CustSvcDelRs getCustSvcDelRs() {
        return _custSvcDelRs;
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
       * CustSvcAddRs
       * CustSvcModRs
       * CustSvcStatusModRs
       * CustSvcDelRs
       */
    public final String[] ELEMENTS = {
              "CustId"
                 ,"MsgRecDt"
                 ,"CustSvcAddRs"
                 ,"CustSvcModRs"
                 ,"CustSvcStatusModRs"
                 ,"CustSvcDelRs"
          };
}
