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
public class ChksumMsgRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public ChksumMsgRec_Type() {
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
    private org.sourceforge.ifx.framework.element.ChksumAddRs _chksumAddRs;

    /** 
     * Setter for chksumAddRs
     * @param chksumAddRs the org.sourceforge.ifx.framework.element.ChksumAddRs to set
     */
    public void setChksumAddRs(org.sourceforge.ifx.framework.element.ChksumAddRs _chksumAddRs) {
        this._chksumAddRs = _chksumAddRs;
    }

    /**
     * Getter for chksumAddRs
     * @return a org.sourceforge.ifx.framework.element.ChksumAddRs
     */
    public org.sourceforge.ifx.framework.element.ChksumAddRs getChksumAddRs() {
        return _chksumAddRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChksumModRs _chksumModRs;

    /** 
     * Setter for chksumModRs
     * @param chksumModRs the org.sourceforge.ifx.framework.element.ChksumModRs to set
     */
    public void setChksumModRs(org.sourceforge.ifx.framework.element.ChksumModRs _chksumModRs) {
        this._chksumModRs = _chksumModRs;
    }

    /**
     * Getter for chksumModRs
     * @return a org.sourceforge.ifx.framework.element.ChksumModRs
     */
    public org.sourceforge.ifx.framework.element.ChksumModRs getChksumModRs() {
        return _chksumModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChksumStatusModRs _chksumStatusModRs;

    /** 
     * Setter for chksumStatusModRs
     * @param chksumStatusModRs the org.sourceforge.ifx.framework.element.ChksumStatusModRs to set
     */
    public void setChksumStatusModRs(org.sourceforge.ifx.framework.element.ChksumStatusModRs _chksumStatusModRs) {
        this._chksumStatusModRs = _chksumStatusModRs;
    }

    /**
     * Getter for chksumStatusModRs
     * @return a org.sourceforge.ifx.framework.element.ChksumStatusModRs
     */
    public org.sourceforge.ifx.framework.element.ChksumStatusModRs getChksumStatusModRs() {
        return _chksumStatusModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChksumDelRs _chksumDelRs;

    /** 
     * Setter for chksumDelRs
     * @param chksumDelRs the org.sourceforge.ifx.framework.element.ChksumDelRs to set
     */
    public void setChksumDelRs(org.sourceforge.ifx.framework.element.ChksumDelRs _chksumDelRs) {
        this._chksumDelRs = _chksumDelRs;
    }

    /**
     * Getter for chksumDelRs
     * @return a org.sourceforge.ifx.framework.element.ChksumDelRs
     */
    public org.sourceforge.ifx.framework.element.ChksumDelRs getChksumDelRs() {
        return _chksumDelRs;
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
       * ChksumAddRs
       * ChksumModRs
       * ChksumStatusModRs
       * ChksumDelRs
       */
    public final String[] ELEMENTS = {
              "CustId"
                 ,"MsgRecDt"
                 ,"ChksumAddRs"
                 ,"ChksumModRs"
                 ,"ChksumStatusModRs"
                 ,"ChksumDelRs"
          };
}
