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
public class PmtEnclMsgRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PmtEnclMsgRec_Type() {
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
    private org.sourceforge.ifx.framework.element.PmtEnclAddRs _pmtEnclAddRs;

    /** 
     * Setter for pmtEnclAddRs
     * @param pmtEnclAddRs the org.sourceforge.ifx.framework.element.PmtEnclAddRs to set
     */
    public void setPmtEnclAddRs(org.sourceforge.ifx.framework.element.PmtEnclAddRs _pmtEnclAddRs) {
        this._pmtEnclAddRs = _pmtEnclAddRs;
    }

    /**
     * Getter for pmtEnclAddRs
     * @return a org.sourceforge.ifx.framework.element.PmtEnclAddRs
     */
    public org.sourceforge.ifx.framework.element.PmtEnclAddRs getPmtEnclAddRs() {
        return _pmtEnclAddRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtEnclModRs _pmtEnclModRs;

    /** 
     * Setter for pmtEnclModRs
     * @param pmtEnclModRs the org.sourceforge.ifx.framework.element.PmtEnclModRs to set
     */
    public void setPmtEnclModRs(org.sourceforge.ifx.framework.element.PmtEnclModRs _pmtEnclModRs) {
        this._pmtEnclModRs = _pmtEnclModRs;
    }

    /**
     * Getter for pmtEnclModRs
     * @return a org.sourceforge.ifx.framework.element.PmtEnclModRs
     */
    public org.sourceforge.ifx.framework.element.PmtEnclModRs getPmtEnclModRs() {
        return _pmtEnclModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtEnclCanRs _pmtEnclCanRs;

    /** 
     * Setter for pmtEnclCanRs
     * @param pmtEnclCanRs the org.sourceforge.ifx.framework.element.PmtEnclCanRs to set
     */
    public void setPmtEnclCanRs(org.sourceforge.ifx.framework.element.PmtEnclCanRs _pmtEnclCanRs) {
        this._pmtEnclCanRs = _pmtEnclCanRs;
    }

    /**
     * Getter for pmtEnclCanRs
     * @return a org.sourceforge.ifx.framework.element.PmtEnclCanRs
     */
    public org.sourceforge.ifx.framework.element.PmtEnclCanRs getPmtEnclCanRs() {
        return _pmtEnclCanRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtEnclRevRs _pmtEnclRevRs;

    /** 
     * Setter for pmtEnclRevRs
     * @param pmtEnclRevRs the org.sourceforge.ifx.framework.element.PmtEnclRevRs to set
     */
    public void setPmtEnclRevRs(org.sourceforge.ifx.framework.element.PmtEnclRevRs _pmtEnclRevRs) {
        this._pmtEnclRevRs = _pmtEnclRevRs;
    }

    /**
     * Getter for pmtEnclRevRs
     * @return a org.sourceforge.ifx.framework.element.PmtEnclRevRs
     */
    public org.sourceforge.ifx.framework.element.PmtEnclRevRs getPmtEnclRevRs() {
        return _pmtEnclRevRs;
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
       * PmtEnclAddRs
       * PmtEnclModRs
       * PmtEnclCanRs
       * PmtEnclRevRs
       */
    public final String[] ELEMENTS = {
              "CustId"
                 ,"MsgRecDt"
                 ,"PmtEnclAddRs"
                 ,"PmtEnclModRs"
                 ,"PmtEnclCanRs"
                 ,"PmtEnclRevRs"
          };
}
