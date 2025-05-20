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
public class RootSvcRq_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public RootSvcRq_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.basetypes.IFXString _id;

    /** 
     * Setter for id
     * @param id the org.sourceforge.ifx.basetypes.IFXString to set
     */
    public void setId(org.sourceforge.ifx.basetypes.IFXString _id) {
        this._id = _id;
    }

    /**
     * Getter for id
     * @return a org.sourceforge.ifx.basetypes.IFXString
     */
    public org.sourceforge.ifx.basetypes.IFXString getId() {
        return _id;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RqUID _rqUID;

    /** 
     * Setter for rqUID
     * @param rqUID the org.sourceforge.ifx.framework.element.RqUID to set
     */
    public void setRqUID(org.sourceforge.ifx.framework.element.RqUID _rqUID) {
        this._rqUID = _rqUID;
    }

    /**
     * Getter for rqUID
     * @return a org.sourceforge.ifx.framework.element.RqUID
     */
    public org.sourceforge.ifx.framework.element.RqUID getRqUID() {
        return _rqUID;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MsgRqHdr _msgRqHdr;

    /** 
     * Setter for msgRqHdr
     * @param msgRqHdr the org.sourceforge.ifx.framework.element.MsgRqHdr to set
     */
    public void setMsgRqHdr(org.sourceforge.ifx.framework.element.MsgRqHdr _msgRqHdr) {
        this._msgRqHdr = _msgRqHdr;
    }

    /**
     * Getter for msgRqHdr
     * @return a org.sourceforge.ifx.framework.element.MsgRqHdr
     */
    public org.sourceforge.ifx.framework.element.MsgRqHdr getMsgRqHdr() {
        return _msgRqHdr;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AsyncRqUID _asyncRqUID;

    /** 
     * Setter for asyncRqUID
     * @param asyncRqUID the org.sourceforge.ifx.framework.element.AsyncRqUID to set
     */
    public void setAsyncRqUID(org.sourceforge.ifx.framework.element.AsyncRqUID _asyncRqUID) {
        this._asyncRqUID = _asyncRqUID;
    }

    /**
     * Getter for asyncRqUID
     * @return a org.sourceforge.ifx.framework.element.AsyncRqUID
     */
    public org.sourceforge.ifx.framework.element.AsyncRqUID getAsyncRqUID() {
        return _asyncRqUID;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SPName _sPName;

    /** 
     * Setter for sPName
     * @param sPName the org.sourceforge.ifx.framework.element.SPName to set
     */
    public void setSPName(org.sourceforge.ifx.framework.element.SPName _sPName) {
        this._sPName = _sPName;
    }

    /**
     * Getter for sPName
     * @return a org.sourceforge.ifx.framework.element.SPName
     */
    public org.sourceforge.ifx.framework.element.SPName getSPName() {
        return _sPName;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalObjAddRq[] _terminalObjAddRq;

    /** 
     * Setter for terminalObjAddRq
     * @param terminalObjAddRq the org.sourceforge.ifx.framework.element.TerminalObjAddRq[] to set
     */
    public void setTerminalObjAddRq(org.sourceforge.ifx.framework.element.TerminalObjAddRq[] _terminalObjAddRq) {
        this._terminalObjAddRq = _terminalObjAddRq;
    }

    /**
     * Getter for terminalObjAddRq
     * @return a org.sourceforge.ifx.framework.element.TerminalObjAddRq[]
     */
    public org.sourceforge.ifx.framework.element.TerminalObjAddRq[] getTerminalObjAddRq() {
        return _terminalObjAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalObjModRq[] _terminalObjModRq;

    /** 
     * Setter for terminalObjModRq
     * @param terminalObjModRq the org.sourceforge.ifx.framework.element.TerminalObjModRq[] to set
     */
    public void setTerminalObjModRq(org.sourceforge.ifx.framework.element.TerminalObjModRq[] _terminalObjModRq) {
        this._terminalObjModRq = _terminalObjModRq;
    }

    /**
     * Getter for terminalObjModRq
     * @return a org.sourceforge.ifx.framework.element.TerminalObjModRq[]
     */
    public org.sourceforge.ifx.framework.element.TerminalObjModRq[] getTerminalObjModRq() {
        return _terminalObjModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalObjInqRq[] _terminalObjInqRq;

    /** 
     * Setter for terminalObjInqRq
     * @param terminalObjInqRq the org.sourceforge.ifx.framework.element.TerminalObjInqRq[] to set
     */
    public void setTerminalObjInqRq(org.sourceforge.ifx.framework.element.TerminalObjInqRq[] _terminalObjInqRq) {
        this._terminalObjInqRq = _terminalObjInqRq;
    }

    /**
     * Getter for terminalObjInqRq
     * @return a org.sourceforge.ifx.framework.element.TerminalObjInqRq[]
     */
    public org.sourceforge.ifx.framework.element.TerminalObjInqRq[] getTerminalObjInqRq() {
        return _terminalObjInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalObjStatusModRq[] _terminalObjStatusModRq;

    /** 
     * Setter for terminalObjStatusModRq
     * @param terminalObjStatusModRq the org.sourceforge.ifx.framework.element.TerminalObjStatusModRq[] to set
     */
    public void setTerminalObjStatusModRq(org.sourceforge.ifx.framework.element.TerminalObjStatusModRq[] _terminalObjStatusModRq) {
        this._terminalObjStatusModRq = _terminalObjStatusModRq;
    }

    /**
     * Getter for terminalObjStatusModRq
     * @return a org.sourceforge.ifx.framework.element.TerminalObjStatusModRq[]
     */
    public org.sourceforge.ifx.framework.element.TerminalObjStatusModRq[] getTerminalObjStatusModRq() {
        return _terminalObjStatusModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalObjAudRq[] _terminalObjAudRq;

    /** 
     * Setter for terminalObjAudRq
     * @param terminalObjAudRq the org.sourceforge.ifx.framework.element.TerminalObjAudRq[] to set
     */
    public void setTerminalObjAudRq(org.sourceforge.ifx.framework.element.TerminalObjAudRq[] _terminalObjAudRq) {
        this._terminalObjAudRq = _terminalObjAudRq;
    }

    /**
     * Getter for terminalObjAudRq
     * @return a org.sourceforge.ifx.framework.element.TerminalObjAudRq[]
     */
    public org.sourceforge.ifx.framework.element.TerminalObjAudRq[] getTerminalObjAudRq() {
        return _terminalObjAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalObjAdviseRq[] _terminalObjAdviseRq;

    /** 
     * Setter for terminalObjAdviseRq
     * @param terminalObjAdviseRq the org.sourceforge.ifx.framework.element.TerminalObjAdviseRq[] to set
     */
    public void setTerminalObjAdviseRq(org.sourceforge.ifx.framework.element.TerminalObjAdviseRq[] _terminalObjAdviseRq) {
        this._terminalObjAdviseRq = _terminalObjAdviseRq;
    }

    /**
     * Getter for terminalObjAdviseRq
     * @return a org.sourceforge.ifx.framework.element.TerminalObjAdviseRq[]
     */
    public org.sourceforge.ifx.framework.element.TerminalObjAdviseRq[] getTerminalObjAdviseRq() {
        return _terminalObjAdviseRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalObjDelRq[] _terminalObjDelRq;

    /** 
     * Setter for terminalObjDelRq
     * @param terminalObjDelRq the org.sourceforge.ifx.framework.element.TerminalObjDelRq[] to set
     */
    public void setTerminalObjDelRq(org.sourceforge.ifx.framework.element.TerminalObjDelRq[] _terminalObjDelRq) {
        this._terminalObjDelRq = _terminalObjDelRq;
    }

    /**
     * Getter for terminalObjDelRq
     * @return a org.sourceforge.ifx.framework.element.TerminalObjDelRq[]
     */
    public org.sourceforge.ifx.framework.element.TerminalObjDelRq[] getTerminalObjDelRq() {
        return _terminalObjDelRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalSPObjAddRq[] _terminalSPObjAddRq;

    /** 
     * Setter for terminalSPObjAddRq
     * @param terminalSPObjAddRq the org.sourceforge.ifx.framework.element.TerminalSPObjAddRq[] to set
     */
    public void setTerminalSPObjAddRq(org.sourceforge.ifx.framework.element.TerminalSPObjAddRq[] _terminalSPObjAddRq) {
        this._terminalSPObjAddRq = _terminalSPObjAddRq;
    }

    /**
     * Getter for terminalSPObjAddRq
     * @return a org.sourceforge.ifx.framework.element.TerminalSPObjAddRq[]
     */
    public org.sourceforge.ifx.framework.element.TerminalSPObjAddRq[] getTerminalSPObjAddRq() {
        return _terminalSPObjAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalSPObjModRq[] _terminalSPObjModRq;

    /** 
     * Setter for terminalSPObjModRq
     * @param terminalSPObjModRq the org.sourceforge.ifx.framework.element.TerminalSPObjModRq[] to set
     */
    public void setTerminalSPObjModRq(org.sourceforge.ifx.framework.element.TerminalSPObjModRq[] _terminalSPObjModRq) {
        this._terminalSPObjModRq = _terminalSPObjModRq;
    }

    /**
     * Getter for terminalSPObjModRq
     * @return a org.sourceforge.ifx.framework.element.TerminalSPObjModRq[]
     */
    public org.sourceforge.ifx.framework.element.TerminalSPObjModRq[] getTerminalSPObjModRq() {
        return _terminalSPObjModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalSPObjInqRq[] _terminalSPObjInqRq;

    /** 
     * Setter for terminalSPObjInqRq
     * @param terminalSPObjInqRq the org.sourceforge.ifx.framework.element.TerminalSPObjInqRq[] to set
     */
    public void setTerminalSPObjInqRq(org.sourceforge.ifx.framework.element.TerminalSPObjInqRq[] _terminalSPObjInqRq) {
        this._terminalSPObjInqRq = _terminalSPObjInqRq;
    }

    /**
     * Getter for terminalSPObjInqRq
     * @return a org.sourceforge.ifx.framework.element.TerminalSPObjInqRq[]
     */
    public org.sourceforge.ifx.framework.element.TerminalSPObjInqRq[] getTerminalSPObjInqRq() {
        return _terminalSPObjInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalSPObjAudRq[] _terminalSPObjAudRq;

    /** 
     * Setter for terminalSPObjAudRq
     * @param terminalSPObjAudRq the org.sourceforge.ifx.framework.element.TerminalSPObjAudRq[] to set
     */
    public void setTerminalSPObjAudRq(org.sourceforge.ifx.framework.element.TerminalSPObjAudRq[] _terminalSPObjAudRq) {
        this._terminalSPObjAudRq = _terminalSPObjAudRq;
    }

    /**
     * Getter for terminalSPObjAudRq
     * @return a org.sourceforge.ifx.framework.element.TerminalSPObjAudRq[]
     */
    public org.sourceforge.ifx.framework.element.TerminalSPObjAudRq[] getTerminalSPObjAudRq() {
        return _terminalSPObjAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalSPObjAdviseRq[] _terminalSPObjAdviseRq;

    /** 
     * Setter for terminalSPObjAdviseRq
     * @param terminalSPObjAdviseRq the org.sourceforge.ifx.framework.element.TerminalSPObjAdviseRq[] to set
     */
    public void setTerminalSPObjAdviseRq(org.sourceforge.ifx.framework.element.TerminalSPObjAdviseRq[] _terminalSPObjAdviseRq) {
        this._terminalSPObjAdviseRq = _terminalSPObjAdviseRq;
    }

    /**
     * Getter for terminalSPObjAdviseRq
     * @return a org.sourceforge.ifx.framework.element.TerminalSPObjAdviseRq[]
     */
    public org.sourceforge.ifx.framework.element.TerminalSPObjAdviseRq[] getTerminalSPObjAdviseRq() {
        return _terminalSPObjAdviseRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalSPObjDelRq[] _terminalSPObjDelRq;

    /** 
     * Setter for terminalSPObjDelRq
     * @param terminalSPObjDelRq the org.sourceforge.ifx.framework.element.TerminalSPObjDelRq[] to set
     */
    public void setTerminalSPObjDelRq(org.sourceforge.ifx.framework.element.TerminalSPObjDelRq[] _terminalSPObjDelRq) {
        this._terminalSPObjDelRq = _terminalSPObjDelRq;
    }

    /**
     * Getter for terminalSPObjDelRq
     * @return a org.sourceforge.ifx.framework.element.TerminalSPObjDelRq[]
     */
    public org.sourceforge.ifx.framework.element.TerminalSPObjDelRq[] getTerminalSPObjDelRq() {
        return _terminalSPObjDelRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevInqRq[] _devInqRq;

    /** 
     * Setter for devInqRq
     * @param devInqRq the org.sourceforge.ifx.framework.element.DevInqRq[] to set
     */
    public void setDevInqRq(org.sourceforge.ifx.framework.element.DevInqRq[] _devInqRq) {
        this._devInqRq = _devInqRq;
    }

    /**
     * Getter for devInqRq
     * @return a org.sourceforge.ifx.framework.element.DevInqRq[]
     */
    public org.sourceforge.ifx.framework.element.DevInqRq[] getDevInqRq() {
        return _devInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevAdviseRq[] _devAdviseRq;

    /** 
     * Setter for devAdviseRq
     * @param devAdviseRq the org.sourceforge.ifx.framework.element.DevAdviseRq[] to set
     */
    public void setDevAdviseRq(org.sourceforge.ifx.framework.element.DevAdviseRq[] _devAdviseRq) {
        this._devAdviseRq = _devAdviseRq;
    }

    /**
     * Getter for devAdviseRq
     * @return a org.sourceforge.ifx.framework.element.DevAdviseRq[]
     */
    public org.sourceforge.ifx.framework.element.DevAdviseRq[] getDevAdviseRq() {
        return _devAdviseRq;
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
       * Id
       * RqUID
       * MsgRqHdr
       * AsyncRqUID
       * SPName
       * TerminalObjAddRq
       * TerminalObjModRq
       * TerminalObjInqRq
       * TerminalObjStatusModRq
       * TerminalObjAudRq
       * TerminalObjAdviseRq
       * TerminalObjDelRq
       * TerminalSPObjAddRq
       * TerminalSPObjModRq
       * TerminalSPObjInqRq
       * TerminalSPObjAudRq
       * TerminalSPObjAdviseRq
       * TerminalSPObjDelRq
       * DevInqRq
       * DevAdviseRq
       */
    public final String[] ELEMENTS = {
              "Id"
                 ,"RqUID"
                 ,"MsgRqHdr"
                 ,"AsyncRqUID"
                 ,"SPName"
                 ,"TerminalObjAddRq"
                 ,"TerminalObjModRq"
                 ,"TerminalObjInqRq"
                 ,"TerminalObjStatusModRq"
                 ,"TerminalObjAudRq"
                 ,"TerminalObjAdviseRq"
                 ,"TerminalObjDelRq"
                 ,"TerminalSPObjAddRq"
                 ,"TerminalSPObjModRq"
                 ,"TerminalSPObjInqRq"
                 ,"TerminalSPObjAudRq"
                 ,"TerminalSPObjAdviseRq"
                 ,"TerminalSPObjDelRq"
                 ,"DevInqRq"
                 ,"DevAdviseRq"
          };
}
