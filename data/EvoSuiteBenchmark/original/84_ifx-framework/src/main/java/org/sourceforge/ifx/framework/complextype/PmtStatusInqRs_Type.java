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
public class PmtStatusInqRs_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PmtStatusInqRs_Type() {
        super();
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
    private org.sourceforge.ifx.framework.element.RecCtrlIn _recCtrlIn;

    /** 
     * Setter for recCtrlIn
     * @param recCtrlIn the org.sourceforge.ifx.framework.element.RecCtrlIn to set
     */
    public void setRecCtrlIn(org.sourceforge.ifx.framework.element.RecCtrlIn _recCtrlIn) {
        this._recCtrlIn = _recCtrlIn;
    }

    /**
     * Getter for recCtrlIn
     * @return a org.sourceforge.ifx.framework.element.RecCtrlIn
     */
    public org.sourceforge.ifx.framework.element.RecCtrlIn getRecCtrlIn() {
        return _recCtrlIn;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SettlementMethod[] _settlementMethod;

    /** 
     * Setter for settlementMethod
     * @param settlementMethod the org.sourceforge.ifx.framework.element.SettlementMethod[] to set
     */
    public void setSettlementMethod(org.sourceforge.ifx.framework.element.SettlementMethod[] _settlementMethod) {
        this._settlementMethod = _settlementMethod;
    }

    /**
     * Getter for settlementMethod
     * @return a org.sourceforge.ifx.framework.element.SettlementMethod[]
     */
    public org.sourceforge.ifx.framework.element.SettlementMethod[] getSettlementMethod() {
        return _settlementMethod;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtId[] _pmtId;

    /** 
     * Setter for pmtId
     * @param pmtId the org.sourceforge.ifx.framework.element.PmtId[] to set
     */
    public void setPmtId(org.sourceforge.ifx.framework.element.PmtId[] _pmtId) {
        this._pmtId = _pmtId;
    }

    /**
     * Getter for pmtId
     * @return a org.sourceforge.ifx.framework.element.PmtId[]
     */
    public org.sourceforge.ifx.framework.element.PmtId[] getPmtId() {
        return _pmtId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtRefId[] _pmtRefId;

    /** 
     * Setter for pmtRefId
     * @param pmtRefId the org.sourceforge.ifx.framework.element.PmtRefId[] to set
     */
    public void setPmtRefId(org.sourceforge.ifx.framework.element.PmtRefId[] _pmtRefId) {
        this._pmtRefId = _pmtRefId;
    }

    /**
     * Getter for pmtRefId
     * @return a org.sourceforge.ifx.framework.element.PmtRefId[]
     */
    public org.sourceforge.ifx.framework.element.PmtRefId[] getPmtRefId() {
        return _pmtRefId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SvcRqUID[] _svcRqUID;

    /** 
     * Setter for svcRqUID
     * @param svcRqUID the org.sourceforge.ifx.framework.element.SvcRqUID[] to set
     */
    public void setSvcRqUID(org.sourceforge.ifx.framework.element.SvcRqUID[] _svcRqUID) {
        this._svcRqUID = _svcRqUID;
    }

    /**
     * Getter for svcRqUID
     * @return a org.sourceforge.ifx.framework.element.SvcRqUID[]
     */
    public org.sourceforge.ifx.framework.element.SvcRqUID[] getSvcRqUID() {
        return _svcRqUID;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtRemitRefId[] _pmtRemitRefId;

    /** 
     * Setter for pmtRemitRefId
     * @param pmtRemitRefId the org.sourceforge.ifx.framework.element.PmtRemitRefId[] to set
     */
    public void setPmtRemitRefId(org.sourceforge.ifx.framework.element.PmtRemitRefId[] _pmtRemitRefId) {
        this._pmtRemitRefId = _pmtRemitRefId;
    }

    /**
     * Getter for pmtRemitRefId
     * @return a org.sourceforge.ifx.framework.element.PmtRemitRefId[]
     */
    public org.sourceforge.ifx.framework.element.PmtRemitRefId[] getPmtRemitRefId() {
        return _pmtRemitRefId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAcctIdFrom[] _depAcctIdFrom;

    /** 
     * Setter for depAcctIdFrom
     * @param depAcctIdFrom the org.sourceforge.ifx.framework.element.DepAcctIdFrom[] to set
     */
    public void setDepAcctIdFrom(org.sourceforge.ifx.framework.element.DepAcctIdFrom[] _depAcctIdFrom) {
        this._depAcctIdFrom = _depAcctIdFrom;
    }

    /**
     * Getter for depAcctIdFrom
     * @return a org.sourceforge.ifx.framework.element.DepAcctIdFrom[]
     */
    public org.sourceforge.ifx.framework.element.DepAcctIdFrom[] getDepAcctIdFrom() {
        return _depAcctIdFrom;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardAcctIdFrom[] _cardAcctIdFrom;

    /** 
     * Setter for cardAcctIdFrom
     * @param cardAcctIdFrom the org.sourceforge.ifx.framework.element.CardAcctIdFrom[] to set
     */
    public void setCardAcctIdFrom(org.sourceforge.ifx.framework.element.CardAcctIdFrom[] _cardAcctIdFrom) {
        this._cardAcctIdFrom = _cardAcctIdFrom;
    }

    /**
     * Getter for cardAcctIdFrom
     * @return a org.sourceforge.ifx.framework.element.CardAcctIdFrom[]
     */
    public org.sourceforge.ifx.framework.element.CardAcctIdFrom[] getCardAcctIdFrom() {
        return _cardAcctIdFrom;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Category[] _category;

    /** 
     * Setter for category
     * @param category the org.sourceforge.ifx.framework.element.Category[] to set
     */
    public void setCategory(org.sourceforge.ifx.framework.element.Category[] _category) {
        this._category = _category;
    }

    /**
     * Getter for category
     * @return a org.sourceforge.ifx.framework.element.Category[]
     */
    public org.sourceforge.ifx.framework.element.Category[] getCategory() {
        return _category;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkNum[] _chkNum;

    /** 
     * Setter for chkNum
     * @param chkNum the org.sourceforge.ifx.framework.element.ChkNum[] to set
     */
    public void setChkNum(org.sourceforge.ifx.framework.element.ChkNum[] _chkNum) {
        this._chkNum = _chkNum;
    }

    /**
     * Getter for chkNum
     * @return a org.sourceforge.ifx.framework.element.ChkNum[]
     */
    public org.sourceforge.ifx.framework.element.ChkNum[] getChkNum() {
        return _chkNum;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustPayeeId[] _custPayeeId;

    /** 
     * Setter for custPayeeId
     * @param custPayeeId the org.sourceforge.ifx.framework.element.CustPayeeId[] to set
     */
    public void setCustPayeeId(org.sourceforge.ifx.framework.element.CustPayeeId[] _custPayeeId) {
        this._custPayeeId = _custPayeeId;
    }

    /**
     * Getter for custPayeeId
     * @return a org.sourceforge.ifx.framework.element.CustPayeeId[]
     */
    public org.sourceforge.ifx.framework.element.CustPayeeId[] getCustPayeeId() {
        return _custPayeeId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.FSPayee[] _fSPayee;

    /** 
     * Setter for fSPayee
     * @param fSPayee the org.sourceforge.ifx.framework.element.FSPayee[] to set
     */
    public void setFSPayee(org.sourceforge.ifx.framework.element.FSPayee[] _fSPayee) {
        this._fSPayee = _fSPayee;
    }

    /**
     * Getter for fSPayee
     * @return a org.sourceforge.ifx.framework.element.FSPayee[]
     */
    public org.sourceforge.ifx.framework.element.FSPayee[] getFSPayee() {
        return _fSPayee;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Name[] _name;

    /** 
     * Setter for name
     * @param name the org.sourceforge.ifx.framework.element.Name[] to set
     */
    public void setName(org.sourceforge.ifx.framework.element.Name[] _name) {
        this._name = _name;
    }

    /**
     * Getter for name
     * @return a org.sourceforge.ifx.framework.element.Name[]
     */
    public org.sourceforge.ifx.framework.element.Name[] getName() {
        return _name;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PayerInfo[] _payerInfo;

    /** 
     * Setter for payerInfo
     * @param payerInfo the org.sourceforge.ifx.framework.element.PayerInfo[] to set
     */
    public void setPayerInfo(org.sourceforge.ifx.framework.element.PayerInfo[] _payerInfo) {
        this._payerInfo = _payerInfo;
    }

    /**
     * Getter for payerInfo
     * @return a org.sourceforge.ifx.framework.element.PayerInfo[]
     */
    public org.sourceforge.ifx.framework.element.PayerInfo[] getPayerInfo() {
        return _payerInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtStatusCode[] _pmtStatusCode;

    /** 
     * Setter for pmtStatusCode
     * @param pmtStatusCode the org.sourceforge.ifx.framework.element.PmtStatusCode[] to set
     */
    public void setPmtStatusCode(org.sourceforge.ifx.framework.element.PmtStatusCode[] _pmtStatusCode) {
        this._pmtStatusCode = _pmtStatusCode;
    }

    /**
     * Getter for pmtStatusCode
     * @return a org.sourceforge.ifx.framework.element.PmtStatusCode[]
     */
    public org.sourceforge.ifx.framework.element.PmtStatusCode[] getPmtStatusCode() {
        return _pmtStatusCode;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SelRangeDueDt[] _selRangeDueDt;

    /** 
     * Setter for selRangeDueDt
     * @param selRangeDueDt the org.sourceforge.ifx.framework.element.SelRangeDueDt[] to set
     */
    public void setSelRangeDueDt(org.sourceforge.ifx.framework.element.SelRangeDueDt[] _selRangeDueDt) {
        this._selRangeDueDt = _selRangeDueDt;
    }

    /**
     * Getter for selRangeDueDt
     * @return a org.sourceforge.ifx.framework.element.SelRangeDueDt[]
     */
    public org.sourceforge.ifx.framework.element.SelRangeDueDt[] getSelRangeDueDt() {
        return _selRangeDueDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtStatusRec[] _pmtStatusRec;

    /** 
     * Setter for pmtStatusRec
     * @param pmtStatusRec the org.sourceforge.ifx.framework.element.PmtStatusRec[] to set
     */
    public void setPmtStatusRec(org.sourceforge.ifx.framework.element.PmtStatusRec[] _pmtStatusRec) {
        this._pmtStatusRec = _pmtStatusRec;
    }

    /**
     * Getter for pmtStatusRec
     * @return a org.sourceforge.ifx.framework.element.PmtStatusRec[]
     */
    public org.sourceforge.ifx.framework.element.PmtStatusRec[] getPmtStatusRec() {
        return _pmtStatusRec;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CSPRefId[] _cSPRefId;

    /** 
     * Setter for cSPRefId
     * @param cSPRefId the org.sourceforge.ifx.framework.element.CSPRefId[] to set
     */
    public void setCSPRefId(org.sourceforge.ifx.framework.element.CSPRefId[] _cSPRefId) {
        this._cSPRefId = _cSPRefId;
    }

    /**
     * Getter for cSPRefId
     * @return a org.sourceforge.ifx.framework.element.CSPRefId[]
     */
    public org.sourceforge.ifx.framework.element.CSPRefId[] getCSPRefId() {
        return _cSPRefId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SPRefId[] _sPRefId;

    /** 
     * Setter for sPRefId
     * @param sPRefId the org.sourceforge.ifx.framework.element.SPRefId[] to set
     */
    public void setSPRefId(org.sourceforge.ifx.framework.element.SPRefId[] _sPRefId) {
        this._sPRefId = _sPRefId;
    }

    /**
     * Getter for sPRefId
     * @return a org.sourceforge.ifx.framework.element.SPRefId[]
     */
    public org.sourceforge.ifx.framework.element.SPRefId[] getSPRefId() {
        return _sPRefId;
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
       * Status
       * RqUID
       * MsgRqHdr
       * MsgRsHdr
       * AsyncRqUID
       * CustId
       * RecCtrlIn
       * SettlementMethod
       * PmtId
       * PmtRefId
       * SvcRqUID
       * PmtRemitRefId
       * DepAcctIdFrom
       * CardAcctIdFrom
       * Category
       * ChkNum
       * CustPayeeId
       * FSPayee
       * Name
       * PayerInfo
       * PmtStatusCode
       * SelRangeDueDt
       * PmtStatusRec
       * CSPRefId
       * SPRefId
       */
    public final String[] ELEMENTS = {
              "Status"
                 ,"RqUID"
                 ,"MsgRqHdr"
                 ,"MsgRsHdr"
                 ,"AsyncRqUID"
                 ,"CustId"
                 ,"RecCtrlIn"
                 ,"SettlementMethod"
                 ,"PmtId"
                 ,"PmtRefId"
                 ,"SvcRqUID"
                 ,"PmtRemitRefId"
                 ,"DepAcctIdFrom"
                 ,"CardAcctIdFrom"
                 ,"Category"
                 ,"ChkNum"
                 ,"CustPayeeId"
                 ,"FSPayee"
                 ,"Name"
                 ,"PayerInfo"
                 ,"PmtStatusCode"
                 ,"SelRangeDueDt"
                 ,"PmtStatusRec"
                 ,"CSPRefId"
                 ,"SPRefId"
          };
}
