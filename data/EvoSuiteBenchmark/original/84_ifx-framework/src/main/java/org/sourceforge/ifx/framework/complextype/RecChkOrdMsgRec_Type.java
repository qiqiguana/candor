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
public class RecChkOrdMsgRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public RecChkOrdMsgRec_Type() {
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
    private org.sourceforge.ifx.framework.element.RecChkOrdAddRs[] _recChkOrdAddRs;

    /** 
     * Setter for recChkOrdAddRs
     * @param recChkOrdAddRs the org.sourceforge.ifx.framework.element.RecChkOrdAddRs[] to set
     */
    public void setRecChkOrdAddRs(org.sourceforge.ifx.framework.element.RecChkOrdAddRs[] _recChkOrdAddRs) {
        this._recChkOrdAddRs = _recChkOrdAddRs;
    }

    /**
     * Getter for recChkOrdAddRs
     * @return a org.sourceforge.ifx.framework.element.RecChkOrdAddRs[]
     */
    public org.sourceforge.ifx.framework.element.RecChkOrdAddRs[] getRecChkOrdAddRs() {
        return _recChkOrdAddRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecChkOrdModRs[] _recChkOrdModRs;

    /** 
     * Setter for recChkOrdModRs
     * @param recChkOrdModRs the org.sourceforge.ifx.framework.element.RecChkOrdModRs[] to set
     */
    public void setRecChkOrdModRs(org.sourceforge.ifx.framework.element.RecChkOrdModRs[] _recChkOrdModRs) {
        this._recChkOrdModRs = _recChkOrdModRs;
    }

    /**
     * Getter for recChkOrdModRs
     * @return a org.sourceforge.ifx.framework.element.RecChkOrdModRs[]
     */
    public org.sourceforge.ifx.framework.element.RecChkOrdModRs[] getRecChkOrdModRs() {
        return _recChkOrdModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecChkOrdCanRs[] _recChkOrdCanRs;

    /** 
     * Setter for recChkOrdCanRs
     * @param recChkOrdCanRs the org.sourceforge.ifx.framework.element.RecChkOrdCanRs[] to set
     */
    public void setRecChkOrdCanRs(org.sourceforge.ifx.framework.element.RecChkOrdCanRs[] _recChkOrdCanRs) {
        this._recChkOrdCanRs = _recChkOrdCanRs;
    }

    /**
     * Getter for recChkOrdCanRs
     * @return a org.sourceforge.ifx.framework.element.RecChkOrdCanRs[]
     */
    public org.sourceforge.ifx.framework.element.RecChkOrdCanRs[] getRecChkOrdCanRs() {
        return _recChkOrdCanRs;
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
       * RecChkOrdAddRs
       * RecChkOrdModRs
       * RecChkOrdCanRs
       */
    public final String[] ELEMENTS = {
              "CustId"
                 ,"MsgRecDt"
                 ,"RecChkOrdAddRs"
                 ,"RecChkOrdModRs"
                 ,"RecChkOrdCanRs"
          };
}
