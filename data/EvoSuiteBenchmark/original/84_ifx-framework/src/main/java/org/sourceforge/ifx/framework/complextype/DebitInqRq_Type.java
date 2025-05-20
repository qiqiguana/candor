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
public class DebitInqRq_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DebitInqRq_Type() {
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
    private org.sourceforge.ifx.framework.element.DebitId[] _debitId;

    /** 
     * Setter for debitId
     * @param debitId the org.sourceforge.ifx.framework.element.DebitId[] to set
     */
    public void setDebitId(org.sourceforge.ifx.framework.element.DebitId[] _debitId) {
        this._debitId = _debitId;
    }

    /**
     * Getter for debitId
     * @return a org.sourceforge.ifx.framework.element.DebitId[]
     */
    public org.sourceforge.ifx.framework.element.DebitId[] getDebitId() {
        return _debitId;
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

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAcctId[] _depAcctId;

    /** 
     * Setter for depAcctId
     * @param depAcctId the org.sourceforge.ifx.framework.element.DepAcctId[] to set
     */
    public void setDepAcctId(org.sourceforge.ifx.framework.element.DepAcctId[] _depAcctId) {
        this._depAcctId = _depAcctId;
    }

    /**
     * Getter for depAcctId
     * @return a org.sourceforge.ifx.framework.element.DepAcctId[]
     */
    public org.sourceforge.ifx.framework.element.DepAcctId[] getDepAcctId() {
        return _depAcctId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardAcctId[] _cardAcctId;

    /** 
     * Setter for cardAcctId
     * @param cardAcctId the org.sourceforge.ifx.framework.element.CardAcctId[] to set
     */
    public void setCardAcctId(org.sourceforge.ifx.framework.element.CardAcctId[] _cardAcctId) {
        this._cardAcctId = _cardAcctId;
    }

    /**
     * Getter for cardAcctId
     * @return a org.sourceforge.ifx.framework.element.CardAcctId[]
     */
    public org.sourceforge.ifx.framework.element.CardAcctId[] getCardAcctId() {
        return _cardAcctId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ClientChgCode[] _clientChgCode;

    /** 
     * Setter for clientChgCode
     * @param clientChgCode the org.sourceforge.ifx.framework.element.ClientChgCode[] to set
     */
    public void setClientChgCode(org.sourceforge.ifx.framework.element.ClientChgCode[] _clientChgCode) {
        this._clientChgCode = _clientChgCode;
    }

    /**
     * Getter for clientChgCode
     * @return a org.sourceforge.ifx.framework.element.ClientChgCode[]
     */
    public org.sourceforge.ifx.framework.element.ClientChgCode[] getClientChgCode() {
        return _clientChgCode;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SelRangeDt[] _selRangeDt;

    /** 
     * Setter for selRangeDt
     * @param selRangeDt the org.sourceforge.ifx.framework.element.SelRangeDt[] to set
     */
    public void setSelRangeDt(org.sourceforge.ifx.framework.element.SelRangeDt[] _selRangeDt) {
        this._selRangeDt = _selRangeDt;
    }

    /**
     * Getter for selRangeDt
     * @return a org.sourceforge.ifx.framework.element.SelRangeDt[]
     */
    public org.sourceforge.ifx.framework.element.SelRangeDt[] getSelRangeDt() {
        return _selRangeDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SelRangeCurAmt[] _selRangeCurAmt;

    /** 
     * Setter for selRangeCurAmt
     * @param selRangeCurAmt the org.sourceforge.ifx.framework.element.SelRangeCurAmt[] to set
     */
    public void setSelRangeCurAmt(org.sourceforge.ifx.framework.element.SelRangeCurAmt[] _selRangeCurAmt) {
        this._selRangeCurAmt = _selRangeCurAmt;
    }

    /**
     * Getter for selRangeCurAmt
     * @return a org.sourceforge.ifx.framework.element.SelRangeCurAmt[]
     */
    public org.sourceforge.ifx.framework.element.SelRangeCurAmt[] getSelRangeCurAmt() {
        return _selRangeCurAmt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.IncToken[] _incToken;

    /** 
     * Setter for incToken
     * @param incToken the org.sourceforge.ifx.framework.element.IncToken[] to set
     */
    public void setIncToken(org.sourceforge.ifx.framework.element.IncToken[] _incToken) {
        this._incToken = _incToken;
    }

    /**
     * Getter for incToken
     * @return a org.sourceforge.ifx.framework.element.IncToken[]
     */
    public org.sourceforge.ifx.framework.element.IncToken[] getIncToken() {
        return _incToken;
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
       * RecCtrlIn
       * DebitId
       * CSPRefId
       * SPRefId
       * DepAcctId
       * CardAcctId
       * ClientChgCode
       * SelRangeDt
       * SelRangeCurAmt
       * IncToken
       */
    public final String[] ELEMENTS = {
              "RqUID"
                 ,"MsgRqHdr"
                 ,"AsyncRqUID"
                 ,"CustId"
                 ,"RecCtrlIn"
                 ,"DebitId"
                 ,"CSPRefId"
                 ,"SPRefId"
                 ,"DepAcctId"
                 ,"CardAcctId"
                 ,"ClientChgCode"
                 ,"SelRangeDt"
                 ,"SelRangeCurAmt"
                 ,"IncToken"
          };
}
