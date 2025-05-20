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
public class PresSvcRs_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PresSvcRs_Type() {
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
    private org.sourceforge.ifx.framework.element.Status _status;

    /** 
     * Setter for status
     * @param status the org.sourceforge.ifx.framework.element.Status to set
     */
    public void setStatus(org.sourceforge.ifx.framework.element.Status _status) {
        this._status = _status;
    }

    /**
     * Getter for status
     * @return a org.sourceforge.ifx.framework.element.Status
     */
    public org.sourceforge.ifx.framework.element.Status getStatus() {
        return _status;
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
    private org.sourceforge.ifx.framework.element.MsgRsHdr _msgRsHdr;

    /** 
     * Setter for msgRsHdr
     * @param msgRsHdr the org.sourceforge.ifx.framework.element.MsgRsHdr to set
     */
    public void setMsgRsHdr(org.sourceforge.ifx.framework.element.MsgRsHdr _msgRsHdr) {
        this._msgRsHdr = _msgRsHdr;
    }

    /**
     * Getter for msgRsHdr
     * @return a org.sourceforge.ifx.framework.element.MsgRsHdr
     */
    public org.sourceforge.ifx.framework.element.MsgRsHdr getMsgRsHdr() {
        return _msgRsHdr;
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
    private org.sourceforge.ifx.framework.element.BillerInqRs[] _billerInqRs;

    /** 
     * Setter for billerInqRs
     * @param billerInqRs the org.sourceforge.ifx.framework.element.BillerInqRs[] to set
     */
    public void setBillerInqRs(org.sourceforge.ifx.framework.element.BillerInqRs[] _billerInqRs) {
        this._billerInqRs = _billerInqRs;
    }

    /**
     * Getter for billerInqRs
     * @return a org.sourceforge.ifx.framework.element.BillerInqRs[]
     */
    public org.sourceforge.ifx.framework.element.BillerInqRs[] getBillerInqRs() {
        return _billerInqRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillerRevRs[] _billerRevRs;

    /** 
     * Setter for billerRevRs
     * @param billerRevRs the org.sourceforge.ifx.framework.element.BillerRevRs[] to set
     */
    public void setBillerRevRs(org.sourceforge.ifx.framework.element.BillerRevRs[] _billerRevRs) {
        this._billerRevRs = _billerRevRs;
    }

    /**
     * Getter for billerRevRs
     * @return a org.sourceforge.ifx.framework.element.BillerRevRs[]
     */
    public org.sourceforge.ifx.framework.element.BillerRevRs[] getBillerRevRs() {
        return _billerRevRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillInqRs[] _billInqRs;

    /** 
     * Setter for billInqRs
     * @param billInqRs the org.sourceforge.ifx.framework.element.BillInqRs[] to set
     */
    public void setBillInqRs(org.sourceforge.ifx.framework.element.BillInqRs[] _billInqRs) {
        this._billInqRs = _billInqRs;
    }

    /**
     * Getter for billInqRs
     * @return a org.sourceforge.ifx.framework.element.BillInqRs[]
     */
    public org.sourceforge.ifx.framework.element.BillInqRs[] getBillInqRs() {
        return _billInqRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillRevRs[] _billRevRs;

    /** 
     * Setter for billRevRs
     * @param billRevRs the org.sourceforge.ifx.framework.element.BillRevRs[] to set
     */
    public void setBillRevRs(org.sourceforge.ifx.framework.element.BillRevRs[] _billRevRs) {
        this._billRevRs = _billRevRs;
    }

    /**
     * Getter for billRevRs
     * @return a org.sourceforge.ifx.framework.element.BillRevRs[]
     */
    public org.sourceforge.ifx.framework.element.BillRevRs[] getBillRevRs() {
        return _billRevRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillStatusModRs[] _billStatusModRs;

    /** 
     * Setter for billStatusModRs
     * @param billStatusModRs the org.sourceforge.ifx.framework.element.BillStatusModRs[] to set
     */
    public void setBillStatusModRs(org.sourceforge.ifx.framework.element.BillStatusModRs[] _billStatusModRs) {
        this._billStatusModRs = _billStatusModRs;
    }

    /**
     * Getter for billStatusModRs
     * @return a org.sourceforge.ifx.framework.element.BillStatusModRs[]
     */
    public org.sourceforge.ifx.framework.element.BillStatusModRs[] getBillStatusModRs() {
        return _billStatusModRs;
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
       * Status
       * RqUID
       * MsgRqHdr
       * MsgRsHdr
       * AsyncRqUID
       * SPName
       * BillerInqRs
       * BillerRevRs
       * BillInqRs
       * BillRevRs
       * BillStatusModRs
       */
    public final String[] ELEMENTS = {
              "Id"
                 ,"Status"
                 ,"RqUID"
                 ,"MsgRqHdr"
                 ,"MsgRsHdr"
                 ,"AsyncRqUID"
                 ,"SPName"
                 ,"BillerInqRs"
                 ,"BillerRevRs"
                 ,"BillInqRs"
                 ,"BillRevRs"
                 ,"BillStatusModRs"
          };
}
