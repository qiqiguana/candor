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
public class RemitMsgRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public RemitMsgRec_Type() {
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
    private org.sourceforge.ifx.framework.element.RemitAddRs _remitAddRs;

    /** 
     * Setter for remitAddRs
     * @param remitAddRs the org.sourceforge.ifx.framework.element.RemitAddRs to set
     */
    public void setRemitAddRs(org.sourceforge.ifx.framework.element.RemitAddRs _remitAddRs) {
        this._remitAddRs = _remitAddRs;
    }

    /**
     * Getter for remitAddRs
     * @return a org.sourceforge.ifx.framework.element.RemitAddRs
     */
    public org.sourceforge.ifx.framework.element.RemitAddRs getRemitAddRs() {
        return _remitAddRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RemitModRs _remitModRs;

    /** 
     * Setter for remitModRs
     * @param remitModRs the org.sourceforge.ifx.framework.element.RemitModRs to set
     */
    public void setRemitModRs(org.sourceforge.ifx.framework.element.RemitModRs _remitModRs) {
        this._remitModRs = _remitModRs;
    }

    /**
     * Getter for remitModRs
     * @return a org.sourceforge.ifx.framework.element.RemitModRs
     */
    public org.sourceforge.ifx.framework.element.RemitModRs getRemitModRs() {
        return _remitModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RemitStatusModRs _remitStatusModRs;

    /** 
     * Setter for remitStatusModRs
     * @param remitStatusModRs the org.sourceforge.ifx.framework.element.RemitStatusModRs to set
     */
    public void setRemitStatusModRs(org.sourceforge.ifx.framework.element.RemitStatusModRs _remitStatusModRs) {
        this._remitStatusModRs = _remitStatusModRs;
    }

    /**
     * Getter for remitStatusModRs
     * @return a org.sourceforge.ifx.framework.element.RemitStatusModRs
     */
    public org.sourceforge.ifx.framework.element.RemitStatusModRs getRemitStatusModRs() {
        return _remitStatusModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RemitDelRs _remitDelRs;

    /** 
     * Setter for remitDelRs
     * @param remitDelRs the org.sourceforge.ifx.framework.element.RemitDelRs to set
     */
    public void setRemitDelRs(org.sourceforge.ifx.framework.element.RemitDelRs _remitDelRs) {
        this._remitDelRs = _remitDelRs;
    }

    /**
     * Getter for remitDelRs
     * @return a org.sourceforge.ifx.framework.element.RemitDelRs
     */
    public org.sourceforge.ifx.framework.element.RemitDelRs getRemitDelRs() {
        return _remitDelRs;
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
       * RemitAddRs
       * RemitModRs
       * RemitStatusModRs
       * RemitDelRs
       */
    public final String[] ELEMENTS = {
              "CustId"
                 ,"MsgRecDt"
                 ,"RemitAddRs"
                 ,"RemitModRs"
                 ,"RemitStatusModRs"
                 ,"RemitDelRs"
          };
}
