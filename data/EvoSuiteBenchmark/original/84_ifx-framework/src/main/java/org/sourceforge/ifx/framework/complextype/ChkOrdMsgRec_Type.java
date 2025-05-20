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
public class ChkOrdMsgRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public ChkOrdMsgRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustId[] _custId;

    /** 
     * Setter for custId
     * @param custId the org.sourceforge.ifx.framework.element.CustId[] to set
     */
    public void setCustId(org.sourceforge.ifx.framework.element.CustId[] _custId) {
        this._custId = _custId;
    }

    /**
     * Getter for custId
     * @return a org.sourceforge.ifx.framework.element.CustId[]
     */
    public org.sourceforge.ifx.framework.element.CustId[] getCustId() {
        return _custId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MsgRecDt[] _msgRecDt;

    /** 
     * Setter for msgRecDt
     * @param msgRecDt the org.sourceforge.ifx.framework.element.MsgRecDt[] to set
     */
    public void setMsgRecDt(org.sourceforge.ifx.framework.element.MsgRecDt[] _msgRecDt) {
        this._msgRecDt = _msgRecDt;
    }

    /**
     * Getter for msgRecDt
     * @return a org.sourceforge.ifx.framework.element.MsgRecDt[]
     */
    public org.sourceforge.ifx.framework.element.MsgRecDt[] getMsgRecDt() {
        return _msgRecDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkOrdAddRs[] _chkOrdAddRs;

    /** 
     * Setter for chkOrdAddRs
     * @param chkOrdAddRs the org.sourceforge.ifx.framework.element.ChkOrdAddRs[] to set
     */
    public void setChkOrdAddRs(org.sourceforge.ifx.framework.element.ChkOrdAddRs[] _chkOrdAddRs) {
        this._chkOrdAddRs = _chkOrdAddRs;
    }

    /**
     * Getter for chkOrdAddRs
     * @return a org.sourceforge.ifx.framework.element.ChkOrdAddRs[]
     */
    public org.sourceforge.ifx.framework.element.ChkOrdAddRs[] getChkOrdAddRs() {
        return _chkOrdAddRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkOrdModRs[] _chkOrdModRs;

    /** 
     * Setter for chkOrdModRs
     * @param chkOrdModRs the org.sourceforge.ifx.framework.element.ChkOrdModRs[] to set
     */
    public void setChkOrdModRs(org.sourceforge.ifx.framework.element.ChkOrdModRs[] _chkOrdModRs) {
        this._chkOrdModRs = _chkOrdModRs;
    }

    /**
     * Getter for chkOrdModRs
     * @return a org.sourceforge.ifx.framework.element.ChkOrdModRs[]
     */
    public org.sourceforge.ifx.framework.element.ChkOrdModRs[] getChkOrdModRs() {
        return _chkOrdModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkOrdCanRs[] _chkOrdCanRs;

    /** 
     * Setter for chkOrdCanRs
     * @param chkOrdCanRs the org.sourceforge.ifx.framework.element.ChkOrdCanRs[] to set
     */
    public void setChkOrdCanRs(org.sourceforge.ifx.framework.element.ChkOrdCanRs[] _chkOrdCanRs) {
        this._chkOrdCanRs = _chkOrdCanRs;
    }

    /**
     * Getter for chkOrdCanRs
     * @return a org.sourceforge.ifx.framework.element.ChkOrdCanRs[]
     */
    public org.sourceforge.ifx.framework.element.ChkOrdCanRs[] getChkOrdCanRs() {
        return _chkOrdCanRs;
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
       * ChkOrdAddRs
       * ChkOrdModRs
       * ChkOrdCanRs
       */
    public final String[] ELEMENTS = {
              "CustId"
                 ,"MsgRecDt"
                 ,"ChkOrdAddRs"
                 ,"ChkOrdModRs"
                 ,"ChkOrdCanRs"
          };
}
