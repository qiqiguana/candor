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
public class PmtMsgRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PmtMsgRec_Type() {
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
    private org.sourceforge.ifx.framework.element.PmtAddRs _pmtAddRs;

    /** 
     * Setter for pmtAddRs
     * @param pmtAddRs the org.sourceforge.ifx.framework.element.PmtAddRs to set
     */
    public void setPmtAddRs(org.sourceforge.ifx.framework.element.PmtAddRs _pmtAddRs) {
        this._pmtAddRs = _pmtAddRs;
    }

    /**
     * Getter for pmtAddRs
     * @return a org.sourceforge.ifx.framework.element.PmtAddRs
     */
    public org.sourceforge.ifx.framework.element.PmtAddRs getPmtAddRs() {
        return _pmtAddRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtModRs _pmtModRs;

    /** 
     * Setter for pmtModRs
     * @param pmtModRs the org.sourceforge.ifx.framework.element.PmtModRs to set
     */
    public void setPmtModRs(org.sourceforge.ifx.framework.element.PmtModRs _pmtModRs) {
        this._pmtModRs = _pmtModRs;
    }

    /**
     * Getter for pmtModRs
     * @return a org.sourceforge.ifx.framework.element.PmtModRs
     */
    public org.sourceforge.ifx.framework.element.PmtModRs getPmtModRs() {
        return _pmtModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtStatusModRs _pmtStatusModRs;

    /** 
     * Setter for pmtStatusModRs
     * @param pmtStatusModRs the org.sourceforge.ifx.framework.element.PmtStatusModRs to set
     */
    public void setPmtStatusModRs(org.sourceforge.ifx.framework.element.PmtStatusModRs _pmtStatusModRs) {
        this._pmtStatusModRs = _pmtStatusModRs;
    }

    /**
     * Getter for pmtStatusModRs
     * @return a org.sourceforge.ifx.framework.element.PmtStatusModRs
     */
    public org.sourceforge.ifx.framework.element.PmtStatusModRs getPmtStatusModRs() {
        return _pmtStatusModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtCanRs _pmtCanRs;

    /** 
     * Setter for pmtCanRs
     * @param pmtCanRs the org.sourceforge.ifx.framework.element.PmtCanRs to set
     */
    public void setPmtCanRs(org.sourceforge.ifx.framework.element.PmtCanRs _pmtCanRs) {
        this._pmtCanRs = _pmtCanRs;
    }

    /**
     * Getter for pmtCanRs
     * @return a org.sourceforge.ifx.framework.element.PmtCanRs
     */
    public org.sourceforge.ifx.framework.element.PmtCanRs getPmtCanRs() {
        return _pmtCanRs;
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
       * PmtAddRs
       * PmtModRs
       * PmtStatusModRs
       * PmtCanRs
       */
    public final String[] ELEMENTS = {
              "CustId"
                 ,"MsgRecDt"
                 ,"PmtAddRs"
                 ,"PmtModRs"
                 ,"PmtStatusModRs"
                 ,"PmtCanRs"
          };
}
