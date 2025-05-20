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
public class PmtAuthMsgRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PmtAuthMsgRec_Type() {
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
    private org.sourceforge.ifx.framework.element.PmtAuthAddRs _pmtAuthAddRs;

    /** 
     * Setter for pmtAuthAddRs
     * @param pmtAuthAddRs the org.sourceforge.ifx.framework.element.PmtAuthAddRs to set
     */
    public void setPmtAuthAddRs(org.sourceforge.ifx.framework.element.PmtAuthAddRs _pmtAuthAddRs) {
        this._pmtAuthAddRs = _pmtAuthAddRs;
    }

    /**
     * Getter for pmtAuthAddRs
     * @return a org.sourceforge.ifx.framework.element.PmtAuthAddRs
     */
    public org.sourceforge.ifx.framework.element.PmtAuthAddRs getPmtAuthAddRs() {
        return _pmtAuthAddRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtAuthModRs _pmtAuthModRs;

    /** 
     * Setter for pmtAuthModRs
     * @param pmtAuthModRs the org.sourceforge.ifx.framework.element.PmtAuthModRs to set
     */
    public void setPmtAuthModRs(org.sourceforge.ifx.framework.element.PmtAuthModRs _pmtAuthModRs) {
        this._pmtAuthModRs = _pmtAuthModRs;
    }

    /**
     * Getter for pmtAuthModRs
     * @return a org.sourceforge.ifx.framework.element.PmtAuthModRs
     */
    public org.sourceforge.ifx.framework.element.PmtAuthModRs getPmtAuthModRs() {
        return _pmtAuthModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtAuthCanRs _pmtAuthCanRs;

    /** 
     * Setter for pmtAuthCanRs
     * @param pmtAuthCanRs the org.sourceforge.ifx.framework.element.PmtAuthCanRs to set
     */
    public void setPmtAuthCanRs(org.sourceforge.ifx.framework.element.PmtAuthCanRs _pmtAuthCanRs) {
        this._pmtAuthCanRs = _pmtAuthCanRs;
    }

    /**
     * Getter for pmtAuthCanRs
     * @return a org.sourceforge.ifx.framework.element.PmtAuthCanRs
     */
    public org.sourceforge.ifx.framework.element.PmtAuthCanRs getPmtAuthCanRs() {
        return _pmtAuthCanRs;
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
       * PmtAuthAddRs
       * PmtAuthModRs
       * PmtAuthCanRs
       */
    public final String[] ELEMENTS = {
              "CustId"
                 ,"MsgRecDt"
                 ,"PmtAuthAddRs"
                 ,"PmtAuthModRs"
                 ,"PmtAuthCanRs"
          };
}
