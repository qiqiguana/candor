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
public class PmtBatchStatusInqRq_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PmtBatchStatusInqRq_Type() {
        super();
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
    private org.sourceforge.ifx.framework.element.PayorAcctId _payorAcctId;

    /** 
     * Setter for payorAcctId
     * @param payorAcctId the org.sourceforge.ifx.framework.element.PayorAcctId to set
     */
    public void setPayorAcctId(org.sourceforge.ifx.framework.element.PayorAcctId _payorAcctId) {
        this._payorAcctId = _payorAcctId;
    }

    /**
     * Getter for payorAcctId
     * @return a org.sourceforge.ifx.framework.element.PayorAcctId
     */
    public org.sourceforge.ifx.framework.element.PayorAcctId getPayorAcctId() {
        return _payorAcctId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PayorAcctName _payorAcctName;

    /** 
     * Setter for payorAcctName
     * @param payorAcctName the org.sourceforge.ifx.framework.element.PayorAcctName to set
     */
    public void setPayorAcctName(org.sourceforge.ifx.framework.element.PayorAcctName _payorAcctName) {
        this._payorAcctName = _payorAcctName;
    }

    /**
     * Getter for payorAcctName
     * @return a org.sourceforge.ifx.framework.element.PayorAcctName
     */
    public org.sourceforge.ifx.framework.element.PayorAcctName getPayorAcctName() {
        return _payorAcctName;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtBatchId[] _pmtBatchId;

    /** 
     * Setter for pmtBatchId
     * @param pmtBatchId the org.sourceforge.ifx.framework.element.PmtBatchId[] to set
     */
    public void setPmtBatchId(org.sourceforge.ifx.framework.element.PmtBatchId[] _pmtBatchId) {
        this._pmtBatchId = _pmtBatchId;
    }

    /**
     * Getter for pmtBatchId
     * @return a org.sourceforge.ifx.framework.element.PmtBatchId[]
     */
    public org.sourceforge.ifx.framework.element.PmtBatchId[] getPmtBatchId() {
        return _pmtBatchId;
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
    private org.sourceforge.ifx.framework.element.CustPayeeName[] _custPayeeName;

    /** 
     * Setter for custPayeeName
     * @param custPayeeName the org.sourceforge.ifx.framework.element.CustPayeeName[] to set
     */
    public void setCustPayeeName(org.sourceforge.ifx.framework.element.CustPayeeName[] _custPayeeName) {
        this._custPayeeName = _custPayeeName;
    }

    /**
     * Getter for custPayeeName
     * @return a org.sourceforge.ifx.framework.element.CustPayeeName[]
     */
    public org.sourceforge.ifx.framework.element.CustPayeeName[] getCustPayeeName() {
        return _custPayeeName;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtBatchStatusCode[] _pmtBatchStatusCode;

    /** 
     * Setter for pmtBatchStatusCode
     * @param pmtBatchStatusCode the org.sourceforge.ifx.framework.element.PmtBatchStatusCode[] to set
     */
    public void setPmtBatchStatusCode(org.sourceforge.ifx.framework.element.PmtBatchStatusCode[] _pmtBatchStatusCode) {
        this._pmtBatchStatusCode = _pmtBatchStatusCode;
    }

    /**
     * Getter for pmtBatchStatusCode
     * @return a org.sourceforge.ifx.framework.element.PmtBatchStatusCode[]
     */
    public org.sourceforge.ifx.framework.element.PmtBatchStatusCode[] getPmtBatchStatusCode() {
        return _pmtBatchStatusCode;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtTrnStatusCode[] _pmtTrnStatusCode;

    /** 
     * Setter for pmtTrnStatusCode
     * @param pmtTrnStatusCode the org.sourceforge.ifx.framework.element.PmtTrnStatusCode[] to set
     */
    public void setPmtTrnStatusCode(org.sourceforge.ifx.framework.element.PmtTrnStatusCode[] _pmtTrnStatusCode) {
        this._pmtTrnStatusCode = _pmtTrnStatusCode;
    }

    /**
     * Getter for pmtTrnStatusCode
     * @return a org.sourceforge.ifx.framework.element.PmtTrnStatusCode[]
     */
    public org.sourceforge.ifx.framework.element.PmtTrnStatusCode[] getPmtTrnStatusCode() {
        return _pmtTrnStatusCode;
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


    /**
     * Returns true if objects are equal, false otherwise.
     * @param obj the object to compare with.
     * @return true if equal, false if not.
     */
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /** Element ordering - 
       * RqUID
       * MsgRqHdr
       * AsyncRqUID
       * CustId
       * PayorAcctId
       * PayorAcctName
       * PmtBatchId
       * PmtRefId
       * SvcRqUID
       * PmtRemitRefId
       * ChkNum
       * CustPayeeId
       * CustPayeeName
       * PmtBatchStatusCode
       * PmtTrnStatusCode
       * SelRangeDueDt
       */
    public final String[] ELEMENTS = {
              "RqUID"
                 ,"MsgRqHdr"
                 ,"AsyncRqUID"
                 ,"CustId"
                 ,"PayorAcctId"
                 ,"PayorAcctName"
                 ,"PmtBatchId"
                 ,"PmtRefId"
                 ,"SvcRqUID"
                 ,"PmtRemitRefId"
                 ,"ChkNum"
                 ,"CustPayeeId"
                 ,"CustPayeeName"
                 ,"PmtBatchStatusCode"
                 ,"PmtTrnStatusCode"
                 ,"SelRangeDueDt"
          };
}
