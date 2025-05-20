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
public class RecXferMsgRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public RecXferMsgRec_Type() {
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
    private org.sourceforge.ifx.framework.element.RecXferAddRs _recXferAddRs;

    /** 
     * Setter for recXferAddRs
     * @param recXferAddRs the org.sourceforge.ifx.framework.element.RecXferAddRs to set
     */
    public void setRecXferAddRs(org.sourceforge.ifx.framework.element.RecXferAddRs _recXferAddRs) {
        this._recXferAddRs = _recXferAddRs;
    }

    /**
     * Getter for recXferAddRs
     * @return a org.sourceforge.ifx.framework.element.RecXferAddRs
     */
    public org.sourceforge.ifx.framework.element.RecXferAddRs getRecXferAddRs() {
        return _recXferAddRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecXferModRs _recXferModRs;

    /** 
     * Setter for recXferModRs
     * @param recXferModRs the org.sourceforge.ifx.framework.element.RecXferModRs to set
     */
    public void setRecXferModRs(org.sourceforge.ifx.framework.element.RecXferModRs _recXferModRs) {
        this._recXferModRs = _recXferModRs;
    }

    /**
     * Getter for recXferModRs
     * @return a org.sourceforge.ifx.framework.element.RecXferModRs
     */
    public org.sourceforge.ifx.framework.element.RecXferModRs getRecXferModRs() {
        return _recXferModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecXferCanRs _recXferCanRs;

    /** 
     * Setter for recXferCanRs
     * @param recXferCanRs the org.sourceforge.ifx.framework.element.RecXferCanRs to set
     */
    public void setRecXferCanRs(org.sourceforge.ifx.framework.element.RecXferCanRs _recXferCanRs) {
        this._recXferCanRs = _recXferCanRs;
    }

    /**
     * Getter for recXferCanRs
     * @return a org.sourceforge.ifx.framework.element.RecXferCanRs
     */
    public org.sourceforge.ifx.framework.element.RecXferCanRs getRecXferCanRs() {
        return _recXferCanRs;
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
       * RecXferAddRs
       * RecXferModRs
       * RecXferCanRs
       */
    public final String[] ELEMENTS = {
              "CustId"
                 ,"MsgRecDt"
                 ,"RecXferAddRs"
                 ,"RecXferModRs"
                 ,"RecXferCanRs"
          };
}
