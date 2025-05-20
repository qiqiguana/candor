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
public class RecPmtMsgRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public RecPmtMsgRec_Type() {
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
    private org.sourceforge.ifx.framework.element.RecPmtAddRs _recPmtAddRs;

    /** 
     * Setter for recPmtAddRs
     * @param recPmtAddRs the org.sourceforge.ifx.framework.element.RecPmtAddRs to set
     */
    public void setRecPmtAddRs(org.sourceforge.ifx.framework.element.RecPmtAddRs _recPmtAddRs) {
        this._recPmtAddRs = _recPmtAddRs;
    }

    /**
     * Getter for recPmtAddRs
     * @return a org.sourceforge.ifx.framework.element.RecPmtAddRs
     */
    public org.sourceforge.ifx.framework.element.RecPmtAddRs getRecPmtAddRs() {
        return _recPmtAddRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecPmtModRs _recPmtModRs;

    /** 
     * Setter for recPmtModRs
     * @param recPmtModRs the org.sourceforge.ifx.framework.element.RecPmtModRs to set
     */
    public void setRecPmtModRs(org.sourceforge.ifx.framework.element.RecPmtModRs _recPmtModRs) {
        this._recPmtModRs = _recPmtModRs;
    }

    /**
     * Getter for recPmtModRs
     * @return a org.sourceforge.ifx.framework.element.RecPmtModRs
     */
    public org.sourceforge.ifx.framework.element.RecPmtModRs getRecPmtModRs() {
        return _recPmtModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecPmtCanRs _recPmtCanRs;

    /** 
     * Setter for recPmtCanRs
     * @param recPmtCanRs the org.sourceforge.ifx.framework.element.RecPmtCanRs to set
     */
    public void setRecPmtCanRs(org.sourceforge.ifx.framework.element.RecPmtCanRs _recPmtCanRs) {
        this._recPmtCanRs = _recPmtCanRs;
    }

    /**
     * Getter for recPmtCanRs
     * @return a org.sourceforge.ifx.framework.element.RecPmtCanRs
     */
    public org.sourceforge.ifx.framework.element.RecPmtCanRs getRecPmtCanRs() {
        return _recPmtCanRs;
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
       * RecPmtAddRs
       * RecPmtModRs
       * RecPmtCanRs
       */
    public final String[] ELEMENTS = {
              "CustId"
                 ,"MsgRecDt"
                 ,"RecPmtAddRs"
                 ,"RecPmtModRs"
                 ,"RecPmtCanRs"
          };
}
