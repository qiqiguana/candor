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
public class XferMsgRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public XferMsgRec_Type() {
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
    private org.sourceforge.ifx.framework.element.XferAddRs _xferAddRs;

    /** 
     * Setter for xferAddRs
     * @param xferAddRs the org.sourceforge.ifx.framework.element.XferAddRs to set
     */
    public void setXferAddRs(org.sourceforge.ifx.framework.element.XferAddRs _xferAddRs) {
        this._xferAddRs = _xferAddRs;
    }

    /**
     * Getter for xferAddRs
     * @return a org.sourceforge.ifx.framework.element.XferAddRs
     */
    public org.sourceforge.ifx.framework.element.XferAddRs getXferAddRs() {
        return _xferAddRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.XferModRs _xferModRs;

    /** 
     * Setter for xferModRs
     * @param xferModRs the org.sourceforge.ifx.framework.element.XferModRs to set
     */
    public void setXferModRs(org.sourceforge.ifx.framework.element.XferModRs _xferModRs) {
        this._xferModRs = _xferModRs;
    }

    /**
     * Getter for xferModRs
     * @return a org.sourceforge.ifx.framework.element.XferModRs
     */
    public org.sourceforge.ifx.framework.element.XferModRs getXferModRs() {
        return _xferModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.XferStatusModRs _xferStatusModRs;

    /** 
     * Setter for xferStatusModRs
     * @param xferStatusModRs the org.sourceforge.ifx.framework.element.XferStatusModRs to set
     */
    public void setXferStatusModRs(org.sourceforge.ifx.framework.element.XferStatusModRs _xferStatusModRs) {
        this._xferStatusModRs = _xferStatusModRs;
    }

    /**
     * Getter for xferStatusModRs
     * @return a org.sourceforge.ifx.framework.element.XferStatusModRs
     */
    public org.sourceforge.ifx.framework.element.XferStatusModRs getXferStatusModRs() {
        return _xferStatusModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.XferCanRs _xferCanRs;

    /** 
     * Setter for xferCanRs
     * @param xferCanRs the org.sourceforge.ifx.framework.element.XferCanRs to set
     */
    public void setXferCanRs(org.sourceforge.ifx.framework.element.XferCanRs _xferCanRs) {
        this._xferCanRs = _xferCanRs;
    }

    /**
     * Getter for xferCanRs
     * @return a org.sourceforge.ifx.framework.element.XferCanRs
     */
    public org.sourceforge.ifx.framework.element.XferCanRs getXferCanRs() {
        return _xferCanRs;
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
       * XferAddRs
       * XferModRs
       * XferStatusModRs
       * XferCanRs
       */
    public final String[] ELEMENTS = {
              "CustId"
                 ,"MsgRecDt"
                 ,"XferAddRs"
                 ,"XferModRs"
                 ,"XferStatusModRs"
                 ,"XferCanRs"
          };
}
