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
public class RecPmtInstAddRq_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public RecPmtInstAddRq_Type() {
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
    private org.sourceforge.ifx.framework.element.RecPmtId _recPmtId;

    /** 
     * Setter for recPmtId
     * @param recPmtId the org.sourceforge.ifx.framework.element.RecPmtId to set
     */
    public void setRecPmtId(org.sourceforge.ifx.framework.element.RecPmtId _recPmtId) {
        this._recPmtId = _recPmtId;
    }

    /**
     * Getter for recPmtId
     * @return a org.sourceforge.ifx.framework.element.RecPmtId
     */
    public org.sourceforge.ifx.framework.element.RecPmtId getRecPmtId() {
        return _recPmtId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DueDt _dueDt;

    /** 
     * Setter for dueDt
     * @param dueDt the org.sourceforge.ifx.framework.element.DueDt to set
     */
    public void setDueDt(org.sourceforge.ifx.framework.element.DueDt _dueDt) {
        this._dueDt = _dueDt;
    }

    /**
     * Getter for dueDt
     * @return a org.sourceforge.ifx.framework.element.DueDt
     */
    public org.sourceforge.ifx.framework.element.DueDt getDueDt() {
        return _dueDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CurAmt _curAmt;

    /** 
     * Setter for curAmt
     * @param curAmt the org.sourceforge.ifx.framework.element.CurAmt to set
     */
    public void setCurAmt(org.sourceforge.ifx.framework.element.CurAmt _curAmt) {
        this._curAmt = _curAmt;
    }

    /**
     * Getter for curAmt
     * @return a org.sourceforge.ifx.framework.element.CurAmt
     */
    public org.sourceforge.ifx.framework.element.CurAmt getCurAmt() {
        return _curAmt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RemitInstruction _remitInstruction;

    /** 
     * Setter for remitInstruction
     * @param remitInstruction the org.sourceforge.ifx.framework.element.RemitInstruction to set
     */
    public void setRemitInstruction(org.sourceforge.ifx.framework.element.RemitInstruction _remitInstruction) {
        this._remitInstruction = _remitInstruction;
    }

    /**
     * Getter for remitInstruction
     * @return a org.sourceforge.ifx.framework.element.RemitInstruction
     */
    public org.sourceforge.ifx.framework.element.RemitInstruction getRemitInstruction() {
        return _remitInstruction;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RemitDetail[] _remitDetail;

    /** 
     * Setter for remitDetail
     * @param remitDetail the org.sourceforge.ifx.framework.element.RemitDetail[] to set
     */
    public void setRemitDetail(org.sourceforge.ifx.framework.element.RemitDetail[] _remitDetail) {
        this._remitDetail = _remitDetail;
    }

    /**
     * Getter for remitDetail
     * @return a org.sourceforge.ifx.framework.element.RemitDetail[]
     */
    public org.sourceforge.ifx.framework.element.RemitDetail[] getRemitDetail() {
        return _remitDetail;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtLegalRpt[] _pmtLegalRpt;

    /** 
     * Setter for pmtLegalRpt
     * @param pmtLegalRpt the org.sourceforge.ifx.framework.element.PmtLegalRpt[] to set
     */
    public void setPmtLegalRpt(org.sourceforge.ifx.framework.element.PmtLegalRpt[] _pmtLegalRpt) {
        this._pmtLegalRpt = _pmtLegalRpt;
    }

    /**
     * Getter for pmtLegalRpt
     * @return a org.sourceforge.ifx.framework.element.PmtLegalRpt[]
     */
    public org.sourceforge.ifx.framework.element.PmtLegalRpt[] getPmtLegalRpt() {
        return _pmtLegalRpt;
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
       * RecPmtId
       * DueDt
       * CurAmt
       * RemitInstruction
       * RemitDetail
       * PmtLegalRpt
       */
    public final String[] ELEMENTS = {
              "RqUID"
                 ,"MsgRqHdr"
                 ,"AsyncRqUID"
                 ,"CustId"
                 ,"RecPmtId"
                 ,"DueDt"
                 ,"CurAmt"
                 ,"RemitInstruction"
                 ,"RemitDetail"
                 ,"PmtLegalRpt"
          };
}
