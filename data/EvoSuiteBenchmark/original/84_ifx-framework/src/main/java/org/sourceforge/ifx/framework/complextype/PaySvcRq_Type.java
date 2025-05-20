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
public class PaySvcRq_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PaySvcRq_Type() {
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
    private org.sourceforge.ifx.framework.element.StdPayeeInqRq[] _stdPayeeInqRq;

    /** 
     * Setter for stdPayeeInqRq
     * @param stdPayeeInqRq the org.sourceforge.ifx.framework.element.StdPayeeInqRq[] to set
     */
    public void setStdPayeeInqRq(org.sourceforge.ifx.framework.element.StdPayeeInqRq[] _stdPayeeInqRq) {
        this._stdPayeeInqRq = _stdPayeeInqRq;
    }

    /**
     * Getter for stdPayeeInqRq
     * @return a org.sourceforge.ifx.framework.element.StdPayeeInqRq[]
     */
    public org.sourceforge.ifx.framework.element.StdPayeeInqRq[] getStdPayeeInqRq() {
        return _stdPayeeInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StdPayeeRevRq[] _stdPayeeRevRq;

    /** 
     * Setter for stdPayeeRevRq
     * @param stdPayeeRevRq the org.sourceforge.ifx.framework.element.StdPayeeRevRq[] to set
     */
    public void setStdPayeeRevRq(org.sourceforge.ifx.framework.element.StdPayeeRevRq[] _stdPayeeRevRq) {
        this._stdPayeeRevRq = _stdPayeeRevRq;
    }

    /**
     * Getter for stdPayeeRevRq
     * @return a org.sourceforge.ifx.framework.element.StdPayeeRevRq[]
     */
    public org.sourceforge.ifx.framework.element.StdPayeeRevRq[] getStdPayeeRevRq() {
        return _stdPayeeRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustPayeeAddRq[] _custPayeeAddRq;

    /** 
     * Setter for custPayeeAddRq
     * @param custPayeeAddRq the org.sourceforge.ifx.framework.element.CustPayeeAddRq[] to set
     */
    public void setCustPayeeAddRq(org.sourceforge.ifx.framework.element.CustPayeeAddRq[] _custPayeeAddRq) {
        this._custPayeeAddRq = _custPayeeAddRq;
    }

    /**
     * Getter for custPayeeAddRq
     * @return a org.sourceforge.ifx.framework.element.CustPayeeAddRq[]
     */
    public org.sourceforge.ifx.framework.element.CustPayeeAddRq[] getCustPayeeAddRq() {
        return _custPayeeAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustPayeeModRq[] _custPayeeModRq;

    /** 
     * Setter for custPayeeModRq
     * @param custPayeeModRq the org.sourceforge.ifx.framework.element.CustPayeeModRq[] to set
     */
    public void setCustPayeeModRq(org.sourceforge.ifx.framework.element.CustPayeeModRq[] _custPayeeModRq) {
        this._custPayeeModRq = _custPayeeModRq;
    }

    /**
     * Getter for custPayeeModRq
     * @return a org.sourceforge.ifx.framework.element.CustPayeeModRq[]
     */
    public org.sourceforge.ifx.framework.element.CustPayeeModRq[] getCustPayeeModRq() {
        return _custPayeeModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustPayeeTypeModRq[] _custPayeeTypeModRq;

    /** 
     * Setter for custPayeeTypeModRq
     * @param custPayeeTypeModRq the org.sourceforge.ifx.framework.element.CustPayeeTypeModRq[] to set
     */
    public void setCustPayeeTypeModRq(org.sourceforge.ifx.framework.element.CustPayeeTypeModRq[] _custPayeeTypeModRq) {
        this._custPayeeTypeModRq = _custPayeeTypeModRq;
    }

    /**
     * Getter for custPayeeTypeModRq
     * @return a org.sourceforge.ifx.framework.element.CustPayeeTypeModRq[]
     */
    public org.sourceforge.ifx.framework.element.CustPayeeTypeModRq[] getCustPayeeTypeModRq() {
        return _custPayeeTypeModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustPayeeDelRq[] _custPayeeDelRq;

    /** 
     * Setter for custPayeeDelRq
     * @param custPayeeDelRq the org.sourceforge.ifx.framework.element.CustPayeeDelRq[] to set
     */
    public void setCustPayeeDelRq(org.sourceforge.ifx.framework.element.CustPayeeDelRq[] _custPayeeDelRq) {
        this._custPayeeDelRq = _custPayeeDelRq;
    }

    /**
     * Getter for custPayeeDelRq
     * @return a org.sourceforge.ifx.framework.element.CustPayeeDelRq[]
     */
    public org.sourceforge.ifx.framework.element.CustPayeeDelRq[] getCustPayeeDelRq() {
        return _custPayeeDelRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustPayeeInqRq[] _custPayeeInqRq;

    /** 
     * Setter for custPayeeInqRq
     * @param custPayeeInqRq the org.sourceforge.ifx.framework.element.CustPayeeInqRq[] to set
     */
    public void setCustPayeeInqRq(org.sourceforge.ifx.framework.element.CustPayeeInqRq[] _custPayeeInqRq) {
        this._custPayeeInqRq = _custPayeeInqRq;
    }

    /**
     * Getter for custPayeeInqRq
     * @return a org.sourceforge.ifx.framework.element.CustPayeeInqRq[]
     */
    public org.sourceforge.ifx.framework.element.CustPayeeInqRq[] getCustPayeeInqRq() {
        return _custPayeeInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustPayeeAudRq[] _custPayeeAudRq;

    /** 
     * Setter for custPayeeAudRq
     * @param custPayeeAudRq the org.sourceforge.ifx.framework.element.CustPayeeAudRq[] to set
     */
    public void setCustPayeeAudRq(org.sourceforge.ifx.framework.element.CustPayeeAudRq[] _custPayeeAudRq) {
        this._custPayeeAudRq = _custPayeeAudRq;
    }

    /**
     * Getter for custPayeeAudRq
     * @return a org.sourceforge.ifx.framework.element.CustPayeeAudRq[]
     */
    public org.sourceforge.ifx.framework.element.CustPayeeAudRq[] getCustPayeeAudRq() {
        return _custPayeeAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustPayeeSyncRq[] _custPayeeSyncRq;

    /** 
     * Setter for custPayeeSyncRq
     * @param custPayeeSyncRq the org.sourceforge.ifx.framework.element.CustPayeeSyncRq[] to set
     */
    public void setCustPayeeSyncRq(org.sourceforge.ifx.framework.element.CustPayeeSyncRq[] _custPayeeSyncRq) {
        this._custPayeeSyncRq = _custPayeeSyncRq;
    }

    /**
     * Getter for custPayeeSyncRq
     * @return a org.sourceforge.ifx.framework.element.CustPayeeSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.CustPayeeSyncRq[] getCustPayeeSyncRq() {
        return _custPayeeSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtBatchAddRq[] _pmtBatchAddRq;

    /** 
     * Setter for pmtBatchAddRq
     * @param pmtBatchAddRq the org.sourceforge.ifx.framework.element.PmtBatchAddRq[] to set
     */
    public void setPmtBatchAddRq(org.sourceforge.ifx.framework.element.PmtBatchAddRq[] _pmtBatchAddRq) {
        this._pmtBatchAddRq = _pmtBatchAddRq;
    }

    /**
     * Getter for pmtBatchAddRq
     * @return a org.sourceforge.ifx.framework.element.PmtBatchAddRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtBatchAddRq[] getPmtBatchAddRq() {
        return _pmtBatchAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtBatchCanRq[] _pmtBatchCanRq;

    /** 
     * Setter for pmtBatchCanRq
     * @param pmtBatchCanRq the org.sourceforge.ifx.framework.element.PmtBatchCanRq[] to set
     */
    public void setPmtBatchCanRq(org.sourceforge.ifx.framework.element.PmtBatchCanRq[] _pmtBatchCanRq) {
        this._pmtBatchCanRq = _pmtBatchCanRq;
    }

    /**
     * Getter for pmtBatchCanRq
     * @return a org.sourceforge.ifx.framework.element.PmtBatchCanRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtBatchCanRq[] getPmtBatchCanRq() {
        return _pmtBatchCanRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtBatchStatusInqRq[] _pmtBatchStatusInqRq;

    /** 
     * Setter for pmtBatchStatusInqRq
     * @param pmtBatchStatusInqRq the org.sourceforge.ifx.framework.element.PmtBatchStatusInqRq[] to set
     */
    public void setPmtBatchStatusInqRq(org.sourceforge.ifx.framework.element.PmtBatchStatusInqRq[] _pmtBatchStatusInqRq) {
        this._pmtBatchStatusInqRq = _pmtBatchStatusInqRq;
    }

    /**
     * Getter for pmtBatchStatusInqRq
     * @return a org.sourceforge.ifx.framework.element.PmtBatchStatusInqRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtBatchStatusInqRq[] getPmtBatchStatusInqRq() {
        return _pmtBatchStatusInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtBatchStatusAdviseRq[] _pmtBatchStatusAdviseRq;

    /** 
     * Setter for pmtBatchStatusAdviseRq
     * @param pmtBatchStatusAdviseRq the org.sourceforge.ifx.framework.element.PmtBatchStatusAdviseRq[] to set
     */
    public void setPmtBatchStatusAdviseRq(org.sourceforge.ifx.framework.element.PmtBatchStatusAdviseRq[] _pmtBatchStatusAdviseRq) {
        this._pmtBatchStatusAdviseRq = _pmtBatchStatusAdviseRq;
    }

    /**
     * Getter for pmtBatchStatusAdviseRq
     * @return a org.sourceforge.ifx.framework.element.PmtBatchStatusAdviseRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtBatchStatusAdviseRq[] getPmtBatchStatusAdviseRq() {
        return _pmtBatchStatusAdviseRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtAddRq[] _pmtAddRq;

    /** 
     * Setter for pmtAddRq
     * @param pmtAddRq the org.sourceforge.ifx.framework.element.PmtAddRq[] to set
     */
    public void setPmtAddRq(org.sourceforge.ifx.framework.element.PmtAddRq[] _pmtAddRq) {
        this._pmtAddRq = _pmtAddRq;
    }

    /**
     * Getter for pmtAddRq
     * @return a org.sourceforge.ifx.framework.element.PmtAddRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtAddRq[] getPmtAddRq() {
        return _pmtAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtModRq[] _pmtModRq;

    /** 
     * Setter for pmtModRq
     * @param pmtModRq the org.sourceforge.ifx.framework.element.PmtModRq[] to set
     */
    public void setPmtModRq(org.sourceforge.ifx.framework.element.PmtModRq[] _pmtModRq) {
        this._pmtModRq = _pmtModRq;
    }

    /**
     * Getter for pmtModRq
     * @return a org.sourceforge.ifx.framework.element.PmtModRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtModRq[] getPmtModRq() {
        return _pmtModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtStatusModRq[] _pmtStatusModRq;

    /** 
     * Setter for pmtStatusModRq
     * @param pmtStatusModRq the org.sourceforge.ifx.framework.element.PmtStatusModRq[] to set
     */
    public void setPmtStatusModRq(org.sourceforge.ifx.framework.element.PmtStatusModRq[] _pmtStatusModRq) {
        this._pmtStatusModRq = _pmtStatusModRq;
    }

    /**
     * Getter for pmtStatusModRq
     * @return a org.sourceforge.ifx.framework.element.PmtStatusModRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtStatusModRq[] getPmtStatusModRq() {
        return _pmtStatusModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtStatusAdviseRq[] _pmtStatusAdviseRq;

    /** 
     * Setter for pmtStatusAdviseRq
     * @param pmtStatusAdviseRq the org.sourceforge.ifx.framework.element.PmtStatusAdviseRq[] to set
     */
    public void setPmtStatusAdviseRq(org.sourceforge.ifx.framework.element.PmtStatusAdviseRq[] _pmtStatusAdviseRq) {
        this._pmtStatusAdviseRq = _pmtStatusAdviseRq;
    }

    /**
     * Getter for pmtStatusAdviseRq
     * @return a org.sourceforge.ifx.framework.element.PmtStatusAdviseRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtStatusAdviseRq[] getPmtStatusAdviseRq() {
        return _pmtStatusAdviseRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtCanRq[] _pmtCanRq;

    /** 
     * Setter for pmtCanRq
     * @param pmtCanRq the org.sourceforge.ifx.framework.element.PmtCanRq[] to set
     */
    public void setPmtCanRq(org.sourceforge.ifx.framework.element.PmtCanRq[] _pmtCanRq) {
        this._pmtCanRq = _pmtCanRq;
    }

    /**
     * Getter for pmtCanRq
     * @return a org.sourceforge.ifx.framework.element.PmtCanRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtCanRq[] getPmtCanRq() {
        return _pmtCanRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtInqRq[] _pmtInqRq;

    /** 
     * Setter for pmtInqRq
     * @param pmtInqRq the org.sourceforge.ifx.framework.element.PmtInqRq[] to set
     */
    public void setPmtInqRq(org.sourceforge.ifx.framework.element.PmtInqRq[] _pmtInqRq) {
        this._pmtInqRq = _pmtInqRq;
    }

    /**
     * Getter for pmtInqRq
     * @return a org.sourceforge.ifx.framework.element.PmtInqRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtInqRq[] getPmtInqRq() {
        return _pmtInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtRevRq[] _pmtRevRq;

    /** 
     * Setter for pmtRevRq
     * @param pmtRevRq the org.sourceforge.ifx.framework.element.PmtRevRq[] to set
     */
    public void setPmtRevRq(org.sourceforge.ifx.framework.element.PmtRevRq[] _pmtRevRq) {
        this._pmtRevRq = _pmtRevRq;
    }

    /**
     * Getter for pmtRevRq
     * @return a org.sourceforge.ifx.framework.element.PmtRevRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtRevRq[] getPmtRevRq() {
        return _pmtRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtStatusInqRq[] _pmtStatusInqRq;

    /** 
     * Setter for pmtStatusInqRq
     * @param pmtStatusInqRq the org.sourceforge.ifx.framework.element.PmtStatusInqRq[] to set
     */
    public void setPmtStatusInqRq(org.sourceforge.ifx.framework.element.PmtStatusInqRq[] _pmtStatusInqRq) {
        this._pmtStatusInqRq = _pmtStatusInqRq;
    }

    /**
     * Getter for pmtStatusInqRq
     * @return a org.sourceforge.ifx.framework.element.PmtStatusInqRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtStatusInqRq[] getPmtStatusInqRq() {
        return _pmtStatusInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtAudRq[] _pmtAudRq;

    /** 
     * Setter for pmtAudRq
     * @param pmtAudRq the org.sourceforge.ifx.framework.element.PmtAudRq[] to set
     */
    public void setPmtAudRq(org.sourceforge.ifx.framework.element.PmtAudRq[] _pmtAudRq) {
        this._pmtAudRq = _pmtAudRq;
    }

    /**
     * Getter for pmtAudRq
     * @return a org.sourceforge.ifx.framework.element.PmtAudRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtAudRq[] getPmtAudRq() {
        return _pmtAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtSyncRq[] _pmtSyncRq;

    /** 
     * Setter for pmtSyncRq
     * @param pmtSyncRq the org.sourceforge.ifx.framework.element.PmtSyncRq[] to set
     */
    public void setPmtSyncRq(org.sourceforge.ifx.framework.element.PmtSyncRq[] _pmtSyncRq) {
        this._pmtSyncRq = _pmtSyncRq;
    }

    /**
     * Getter for pmtSyncRq
     * @return a org.sourceforge.ifx.framework.element.PmtSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtSyncRq[] getPmtSyncRq() {
        return _pmtSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtAuthAddRq[] _pmtAuthAddRq;

    /** 
     * Setter for pmtAuthAddRq
     * @param pmtAuthAddRq the org.sourceforge.ifx.framework.element.PmtAuthAddRq[] to set
     */
    public void setPmtAuthAddRq(org.sourceforge.ifx.framework.element.PmtAuthAddRq[] _pmtAuthAddRq) {
        this._pmtAuthAddRq = _pmtAuthAddRq;
    }

    /**
     * Getter for pmtAuthAddRq
     * @return a org.sourceforge.ifx.framework.element.PmtAuthAddRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtAuthAddRq[] getPmtAuthAddRq() {
        return _pmtAuthAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtAuthModRq[] _pmtAuthModRq;

    /** 
     * Setter for pmtAuthModRq
     * @param pmtAuthModRq the org.sourceforge.ifx.framework.element.PmtAuthModRq[] to set
     */
    public void setPmtAuthModRq(org.sourceforge.ifx.framework.element.PmtAuthModRq[] _pmtAuthModRq) {
        this._pmtAuthModRq = _pmtAuthModRq;
    }

    /**
     * Getter for pmtAuthModRq
     * @return a org.sourceforge.ifx.framework.element.PmtAuthModRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtAuthModRq[] getPmtAuthModRq() {
        return _pmtAuthModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtAuthCanRq[] _pmtAuthCanRq;

    /** 
     * Setter for pmtAuthCanRq
     * @param pmtAuthCanRq the org.sourceforge.ifx.framework.element.PmtAuthCanRq[] to set
     */
    public void setPmtAuthCanRq(org.sourceforge.ifx.framework.element.PmtAuthCanRq[] _pmtAuthCanRq) {
        this._pmtAuthCanRq = _pmtAuthCanRq;
    }

    /**
     * Getter for pmtAuthCanRq
     * @return a org.sourceforge.ifx.framework.element.PmtAuthCanRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtAuthCanRq[] getPmtAuthCanRq() {
        return _pmtAuthCanRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtAuthInqRq[] _pmtAuthInqRq;

    /** 
     * Setter for pmtAuthInqRq
     * @param pmtAuthInqRq the org.sourceforge.ifx.framework.element.PmtAuthInqRq[] to set
     */
    public void setPmtAuthInqRq(org.sourceforge.ifx.framework.element.PmtAuthInqRq[] _pmtAuthInqRq) {
        this._pmtAuthInqRq = _pmtAuthInqRq;
    }

    /**
     * Getter for pmtAuthInqRq
     * @return a org.sourceforge.ifx.framework.element.PmtAuthInqRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtAuthInqRq[] getPmtAuthInqRq() {
        return _pmtAuthInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtAuthAudRq[] _pmtAuthAudRq;

    /** 
     * Setter for pmtAuthAudRq
     * @param pmtAuthAudRq the org.sourceforge.ifx.framework.element.PmtAuthAudRq[] to set
     */
    public void setPmtAuthAudRq(org.sourceforge.ifx.framework.element.PmtAuthAudRq[] _pmtAuthAudRq) {
        this._pmtAuthAudRq = _pmtAuthAudRq;
    }

    /**
     * Getter for pmtAuthAudRq
     * @return a org.sourceforge.ifx.framework.element.PmtAuthAudRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtAuthAudRq[] getPmtAuthAudRq() {
        return _pmtAuthAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtAuthSyncRq[] _pmtAuthSyncRq;

    /** 
     * Setter for pmtAuthSyncRq
     * @param pmtAuthSyncRq the org.sourceforge.ifx.framework.element.PmtAuthSyncRq[] to set
     */
    public void setPmtAuthSyncRq(org.sourceforge.ifx.framework.element.PmtAuthSyncRq[] _pmtAuthSyncRq) {
        this._pmtAuthSyncRq = _pmtAuthSyncRq;
    }

    /**
     * Getter for pmtAuthSyncRq
     * @return a org.sourceforge.ifx.framework.element.PmtAuthSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtAuthSyncRq[] getPmtAuthSyncRq() {
        return _pmtAuthSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtAuthRevRq[] _pmtAuthRevRq;

    /** 
     * Setter for pmtAuthRevRq
     * @param pmtAuthRevRq the org.sourceforge.ifx.framework.element.PmtAuthRevRq[] to set
     */
    public void setPmtAuthRevRq(org.sourceforge.ifx.framework.element.PmtAuthRevRq[] _pmtAuthRevRq) {
        this._pmtAuthRevRq = _pmtAuthRevRq;
    }

    /**
     * Getter for pmtAuthRevRq
     * @return a org.sourceforge.ifx.framework.element.PmtAuthRevRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtAuthRevRq[] getPmtAuthRevRq() {
        return _pmtAuthRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RemitAddRq[] _remitAddRq;

    /** 
     * Setter for remitAddRq
     * @param remitAddRq the org.sourceforge.ifx.framework.element.RemitAddRq[] to set
     */
    public void setRemitAddRq(org.sourceforge.ifx.framework.element.RemitAddRq[] _remitAddRq) {
        this._remitAddRq = _remitAddRq;
    }

    /**
     * Getter for remitAddRq
     * @return a org.sourceforge.ifx.framework.element.RemitAddRq[]
     */
    public org.sourceforge.ifx.framework.element.RemitAddRq[] getRemitAddRq() {
        return _remitAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RemitModRq[] _remitModRq;

    /** 
     * Setter for remitModRq
     * @param remitModRq the org.sourceforge.ifx.framework.element.RemitModRq[] to set
     */
    public void setRemitModRq(org.sourceforge.ifx.framework.element.RemitModRq[] _remitModRq) {
        this._remitModRq = _remitModRq;
    }

    /**
     * Getter for remitModRq
     * @return a org.sourceforge.ifx.framework.element.RemitModRq[]
     */
    public org.sourceforge.ifx.framework.element.RemitModRq[] getRemitModRq() {
        return _remitModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RemitStatusModRq[] _remitStatusModRq;

    /** 
     * Setter for remitStatusModRq
     * @param remitStatusModRq the org.sourceforge.ifx.framework.element.RemitStatusModRq[] to set
     */
    public void setRemitStatusModRq(org.sourceforge.ifx.framework.element.RemitStatusModRq[] _remitStatusModRq) {
        this._remitStatusModRq = _remitStatusModRq;
    }

    /**
     * Getter for remitStatusModRq
     * @return a org.sourceforge.ifx.framework.element.RemitStatusModRq[]
     */
    public org.sourceforge.ifx.framework.element.RemitStatusModRq[] getRemitStatusModRq() {
        return _remitStatusModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RemitDelRq[] _remitDelRq;

    /** 
     * Setter for remitDelRq
     * @param remitDelRq the org.sourceforge.ifx.framework.element.RemitDelRq[] to set
     */
    public void setRemitDelRq(org.sourceforge.ifx.framework.element.RemitDelRq[] _remitDelRq) {
        this._remitDelRq = _remitDelRq;
    }

    /**
     * Getter for remitDelRq
     * @return a org.sourceforge.ifx.framework.element.RemitDelRq[]
     */
    public org.sourceforge.ifx.framework.element.RemitDelRq[] getRemitDelRq() {
        return _remitDelRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RemitInqRq[] _remitInqRq;

    /** 
     * Setter for remitInqRq
     * @param remitInqRq the org.sourceforge.ifx.framework.element.RemitInqRq[] to set
     */
    public void setRemitInqRq(org.sourceforge.ifx.framework.element.RemitInqRq[] _remitInqRq) {
        this._remitInqRq = _remitInqRq;
    }

    /**
     * Getter for remitInqRq
     * @return a org.sourceforge.ifx.framework.element.RemitInqRq[]
     */
    public org.sourceforge.ifx.framework.element.RemitInqRq[] getRemitInqRq() {
        return _remitInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RemitAudRq[] _remitAudRq;

    /** 
     * Setter for remitAudRq
     * @param remitAudRq the org.sourceforge.ifx.framework.element.RemitAudRq[] to set
     */
    public void setRemitAudRq(org.sourceforge.ifx.framework.element.RemitAudRq[] _remitAudRq) {
        this._remitAudRq = _remitAudRq;
    }

    /**
     * Getter for remitAudRq
     * @return a org.sourceforge.ifx.framework.element.RemitAudRq[]
     */
    public org.sourceforge.ifx.framework.element.RemitAudRq[] getRemitAudRq() {
        return _remitAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RemitSyncRq[] _remitSyncRq;

    /** 
     * Setter for remitSyncRq
     * @param remitSyncRq the org.sourceforge.ifx.framework.element.RemitSyncRq[] to set
     */
    public void setRemitSyncRq(org.sourceforge.ifx.framework.element.RemitSyncRq[] _remitSyncRq) {
        this._remitSyncRq = _remitSyncRq;
    }

    /**
     * Getter for remitSyncRq
     * @return a org.sourceforge.ifx.framework.element.RemitSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.RemitSyncRq[] getRemitSyncRq() {
        return _remitSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RemitRevRq[] _remitRevRq;

    /** 
     * Setter for remitRevRq
     * @param remitRevRq the org.sourceforge.ifx.framework.element.RemitRevRq[] to set
     */
    public void setRemitRevRq(org.sourceforge.ifx.framework.element.RemitRevRq[] _remitRevRq) {
        this._remitRevRq = _remitRevRq;
    }

    /**
     * Getter for remitRevRq
     * @return a org.sourceforge.ifx.framework.element.RemitRevRq[]
     */
    public org.sourceforge.ifx.framework.element.RemitRevRq[] getRemitRevRq() {
        return _remitRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecPmtAddRq[] _recPmtAddRq;

    /** 
     * Setter for recPmtAddRq
     * @param recPmtAddRq the org.sourceforge.ifx.framework.element.RecPmtAddRq[] to set
     */
    public void setRecPmtAddRq(org.sourceforge.ifx.framework.element.RecPmtAddRq[] _recPmtAddRq) {
        this._recPmtAddRq = _recPmtAddRq;
    }

    /**
     * Getter for recPmtAddRq
     * @return a org.sourceforge.ifx.framework.element.RecPmtAddRq[]
     */
    public org.sourceforge.ifx.framework.element.RecPmtAddRq[] getRecPmtAddRq() {
        return _recPmtAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecPmtInstAddRq[] _recPmtInstAddRq;

    /** 
     * Setter for recPmtInstAddRq
     * @param recPmtInstAddRq the org.sourceforge.ifx.framework.element.RecPmtInstAddRq[] to set
     */
    public void setRecPmtInstAddRq(org.sourceforge.ifx.framework.element.RecPmtInstAddRq[] _recPmtInstAddRq) {
        this._recPmtInstAddRq = _recPmtInstAddRq;
    }

    /**
     * Getter for recPmtInstAddRq
     * @return a org.sourceforge.ifx.framework.element.RecPmtInstAddRq[]
     */
    public org.sourceforge.ifx.framework.element.RecPmtInstAddRq[] getRecPmtInstAddRq() {
        return _recPmtInstAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecPmtModRq[] _recPmtModRq;

    /** 
     * Setter for recPmtModRq
     * @param recPmtModRq the org.sourceforge.ifx.framework.element.RecPmtModRq[] to set
     */
    public void setRecPmtModRq(org.sourceforge.ifx.framework.element.RecPmtModRq[] _recPmtModRq) {
        this._recPmtModRq = _recPmtModRq;
    }

    /**
     * Getter for recPmtModRq
     * @return a org.sourceforge.ifx.framework.element.RecPmtModRq[]
     */
    public org.sourceforge.ifx.framework.element.RecPmtModRq[] getRecPmtModRq() {
        return _recPmtModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecPmtCanRq[] _recPmtCanRq;

    /** 
     * Setter for recPmtCanRq
     * @param recPmtCanRq the org.sourceforge.ifx.framework.element.RecPmtCanRq[] to set
     */
    public void setRecPmtCanRq(org.sourceforge.ifx.framework.element.RecPmtCanRq[] _recPmtCanRq) {
        this._recPmtCanRq = _recPmtCanRq;
    }

    /**
     * Getter for recPmtCanRq
     * @return a org.sourceforge.ifx.framework.element.RecPmtCanRq[]
     */
    public org.sourceforge.ifx.framework.element.RecPmtCanRq[] getRecPmtCanRq() {
        return _recPmtCanRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecPmtInqRq[] _recPmtInqRq;

    /** 
     * Setter for recPmtInqRq
     * @param recPmtInqRq the org.sourceforge.ifx.framework.element.RecPmtInqRq[] to set
     */
    public void setRecPmtInqRq(org.sourceforge.ifx.framework.element.RecPmtInqRq[] _recPmtInqRq) {
        this._recPmtInqRq = _recPmtInqRq;
    }

    /**
     * Getter for recPmtInqRq
     * @return a org.sourceforge.ifx.framework.element.RecPmtInqRq[]
     */
    public org.sourceforge.ifx.framework.element.RecPmtInqRq[] getRecPmtInqRq() {
        return _recPmtInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecPmtAudRq[] _recPmtAudRq;

    /** 
     * Setter for recPmtAudRq
     * @param recPmtAudRq the org.sourceforge.ifx.framework.element.RecPmtAudRq[] to set
     */
    public void setRecPmtAudRq(org.sourceforge.ifx.framework.element.RecPmtAudRq[] _recPmtAudRq) {
        this._recPmtAudRq = _recPmtAudRq;
    }

    /**
     * Getter for recPmtAudRq
     * @return a org.sourceforge.ifx.framework.element.RecPmtAudRq[]
     */
    public org.sourceforge.ifx.framework.element.RecPmtAudRq[] getRecPmtAudRq() {
        return _recPmtAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecPmtSyncRq[] _recPmtSyncRq;

    /** 
     * Setter for recPmtSyncRq
     * @param recPmtSyncRq the org.sourceforge.ifx.framework.element.RecPmtSyncRq[] to set
     */
    public void setRecPmtSyncRq(org.sourceforge.ifx.framework.element.RecPmtSyncRq[] _recPmtSyncRq) {
        this._recPmtSyncRq = _recPmtSyncRq;
    }

    /**
     * Getter for recPmtSyncRq
     * @return a org.sourceforge.ifx.framework.element.RecPmtSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.RecPmtSyncRq[] getRecPmtSyncRq() {
        return _recPmtSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecPmtRevRq[] _recPmtRevRq;

    /** 
     * Setter for recPmtRevRq
     * @param recPmtRevRq the org.sourceforge.ifx.framework.element.RecPmtRevRq[] to set
     */
    public void setRecPmtRevRq(org.sourceforge.ifx.framework.element.RecPmtRevRq[] _recPmtRevRq) {
        this._recPmtRevRq = _recPmtRevRq;
    }

    /**
     * Getter for recPmtRevRq
     * @return a org.sourceforge.ifx.framework.element.RecPmtRevRq[]
     */
    public org.sourceforge.ifx.framework.element.RecPmtRevRq[] getRecPmtRevRq() {
        return _recPmtRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtAckInqRq[] _pmtAckInqRq;

    /** 
     * Setter for pmtAckInqRq
     * @param pmtAckInqRq the org.sourceforge.ifx.framework.element.PmtAckInqRq[] to set
     */
    public void setPmtAckInqRq(org.sourceforge.ifx.framework.element.PmtAckInqRq[] _pmtAckInqRq) {
        this._pmtAckInqRq = _pmtAckInqRq;
    }

    /**
     * Getter for pmtAckInqRq
     * @return a org.sourceforge.ifx.framework.element.PmtAckInqRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtAckInqRq[] getPmtAckInqRq() {
        return _pmtAckInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtAckRevRq[] _pmtAckRevRq;

    /** 
     * Setter for pmtAckRevRq
     * @param pmtAckRevRq the org.sourceforge.ifx.framework.element.PmtAckRevRq[] to set
     */
    public void setPmtAckRevRq(org.sourceforge.ifx.framework.element.PmtAckRevRq[] _pmtAckRevRq) {
        this._pmtAckRevRq = _pmtAckRevRq;
    }

    /**
     * Getter for pmtAckRevRq
     * @return a org.sourceforge.ifx.framework.element.PmtAckRevRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtAckRevRq[] getPmtAckRevRq() {
        return _pmtAckRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtAckAdviseRq[] _pmtAckAdviseRq;

    /** 
     * Setter for pmtAckAdviseRq
     * @param pmtAckAdviseRq the org.sourceforge.ifx.framework.element.PmtAckAdviseRq[] to set
     */
    public void setPmtAckAdviseRq(org.sourceforge.ifx.framework.element.PmtAckAdviseRq[] _pmtAckAdviseRq) {
        this._pmtAckAdviseRq = _pmtAckAdviseRq;
    }

    /**
     * Getter for pmtAckAdviseRq
     * @return a org.sourceforge.ifx.framework.element.PmtAckAdviseRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtAckAdviseRq[] getPmtAckAdviseRq() {
        return _pmtAckAdviseRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChksumAddRq[] _chksumAddRq;

    /** 
     * Setter for chksumAddRq
     * @param chksumAddRq the org.sourceforge.ifx.framework.element.ChksumAddRq[] to set
     */
    public void setChksumAddRq(org.sourceforge.ifx.framework.element.ChksumAddRq[] _chksumAddRq) {
        this._chksumAddRq = _chksumAddRq;
    }

    /**
     * Getter for chksumAddRq
     * @return a org.sourceforge.ifx.framework.element.ChksumAddRq[]
     */
    public org.sourceforge.ifx.framework.element.ChksumAddRq[] getChksumAddRq() {
        return _chksumAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChksumModRq[] _chksumModRq;

    /** 
     * Setter for chksumModRq
     * @param chksumModRq the org.sourceforge.ifx.framework.element.ChksumModRq[] to set
     */
    public void setChksumModRq(org.sourceforge.ifx.framework.element.ChksumModRq[] _chksumModRq) {
        this._chksumModRq = _chksumModRq;
    }

    /**
     * Getter for chksumModRq
     * @return a org.sourceforge.ifx.framework.element.ChksumModRq[]
     */
    public org.sourceforge.ifx.framework.element.ChksumModRq[] getChksumModRq() {
        return _chksumModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChksumStatusModRq[] _chksumStatusModRq;

    /** 
     * Setter for chksumStatusModRq
     * @param chksumStatusModRq the org.sourceforge.ifx.framework.element.ChksumStatusModRq[] to set
     */
    public void setChksumStatusModRq(org.sourceforge.ifx.framework.element.ChksumStatusModRq[] _chksumStatusModRq) {
        this._chksumStatusModRq = _chksumStatusModRq;
    }

    /**
     * Getter for chksumStatusModRq
     * @return a org.sourceforge.ifx.framework.element.ChksumStatusModRq[]
     */
    public org.sourceforge.ifx.framework.element.ChksumStatusModRq[] getChksumStatusModRq() {
        return _chksumStatusModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChksumDelRq[] _chksumDelRq;

    /** 
     * Setter for chksumDelRq
     * @param chksumDelRq the org.sourceforge.ifx.framework.element.ChksumDelRq[] to set
     */
    public void setChksumDelRq(org.sourceforge.ifx.framework.element.ChksumDelRq[] _chksumDelRq) {
        this._chksumDelRq = _chksumDelRq;
    }

    /**
     * Getter for chksumDelRq
     * @return a org.sourceforge.ifx.framework.element.ChksumDelRq[]
     */
    public org.sourceforge.ifx.framework.element.ChksumDelRq[] getChksumDelRq() {
        return _chksumDelRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChksumInqRq[] _chksumInqRq;

    /** 
     * Setter for chksumInqRq
     * @param chksumInqRq the org.sourceforge.ifx.framework.element.ChksumInqRq[] to set
     */
    public void setChksumInqRq(org.sourceforge.ifx.framework.element.ChksumInqRq[] _chksumInqRq) {
        this._chksumInqRq = _chksumInqRq;
    }

    /**
     * Getter for chksumInqRq
     * @return a org.sourceforge.ifx.framework.element.ChksumInqRq[]
     */
    public org.sourceforge.ifx.framework.element.ChksumInqRq[] getChksumInqRq() {
        return _chksumInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChksumAudRq[] _chksumAudRq;

    /** 
     * Setter for chksumAudRq
     * @param chksumAudRq the org.sourceforge.ifx.framework.element.ChksumAudRq[] to set
     */
    public void setChksumAudRq(org.sourceforge.ifx.framework.element.ChksumAudRq[] _chksumAudRq) {
        this._chksumAudRq = _chksumAudRq;
    }

    /**
     * Getter for chksumAudRq
     * @return a org.sourceforge.ifx.framework.element.ChksumAudRq[]
     */
    public org.sourceforge.ifx.framework.element.ChksumAudRq[] getChksumAudRq() {
        return _chksumAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChksumSyncRq[] _chksumSyncRq;

    /** 
     * Setter for chksumSyncRq
     * @param chksumSyncRq the org.sourceforge.ifx.framework.element.ChksumSyncRq[] to set
     */
    public void setChksumSyncRq(org.sourceforge.ifx.framework.element.ChksumSyncRq[] _chksumSyncRq) {
        this._chksumSyncRq = _chksumSyncRq;
    }

    /**
     * Getter for chksumSyncRq
     * @return a org.sourceforge.ifx.framework.element.ChksumSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.ChksumSyncRq[] getChksumSyncRq() {
        return _chksumSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChksumAdviseRq[] _chksumAdviseRq;

    /** 
     * Setter for chksumAdviseRq
     * @param chksumAdviseRq the org.sourceforge.ifx.framework.element.ChksumAdviseRq[] to set
     */
    public void setChksumAdviseRq(org.sourceforge.ifx.framework.element.ChksumAdviseRq[] _chksumAdviseRq) {
        this._chksumAdviseRq = _chksumAdviseRq;
    }

    /**
     * Getter for chksumAdviseRq
     * @return a org.sourceforge.ifx.framework.element.ChksumAdviseRq[]
     */
    public org.sourceforge.ifx.framework.element.ChksumAdviseRq[] getChksumAdviseRq() {
        return _chksumAdviseRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CompRemitStmtAddRq[] _compRemitStmtAddRq;

    /** 
     * Setter for compRemitStmtAddRq
     * @param compRemitStmtAddRq the org.sourceforge.ifx.framework.element.CompRemitStmtAddRq[] to set
     */
    public void setCompRemitStmtAddRq(org.sourceforge.ifx.framework.element.CompRemitStmtAddRq[] _compRemitStmtAddRq) {
        this._compRemitStmtAddRq = _compRemitStmtAddRq;
    }

    /**
     * Getter for compRemitStmtAddRq
     * @return a org.sourceforge.ifx.framework.element.CompRemitStmtAddRq[]
     */
    public org.sourceforge.ifx.framework.element.CompRemitStmtAddRq[] getCompRemitStmtAddRq() {
        return _compRemitStmtAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CompRemitStmtInqRq[] _compRemitStmtInqRq;

    /** 
     * Setter for compRemitStmtInqRq
     * @param compRemitStmtInqRq the org.sourceforge.ifx.framework.element.CompRemitStmtInqRq[] to set
     */
    public void setCompRemitStmtInqRq(org.sourceforge.ifx.framework.element.CompRemitStmtInqRq[] _compRemitStmtInqRq) {
        this._compRemitStmtInqRq = _compRemitStmtInqRq;
    }

    /**
     * Getter for compRemitStmtInqRq
     * @return a org.sourceforge.ifx.framework.element.CompRemitStmtInqRq[]
     */
    public org.sourceforge.ifx.framework.element.CompRemitStmtInqRq[] getCompRemitStmtInqRq() {
        return _compRemitStmtInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CompRemitStmtAudRq[] _compRemitStmtAudRq;

    /** 
     * Setter for compRemitStmtAudRq
     * @param compRemitStmtAudRq the org.sourceforge.ifx.framework.element.CompRemitStmtAudRq[] to set
     */
    public void setCompRemitStmtAudRq(org.sourceforge.ifx.framework.element.CompRemitStmtAudRq[] _compRemitStmtAudRq) {
        this._compRemitStmtAudRq = _compRemitStmtAudRq;
    }

    /**
     * Getter for compRemitStmtAudRq
     * @return a org.sourceforge.ifx.framework.element.CompRemitStmtAudRq[]
     */
    public org.sourceforge.ifx.framework.element.CompRemitStmtAudRq[] getCompRemitStmtAudRq() {
        return _compRemitStmtAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CompRemitStmtSyncRq[] _compRemitStmtSyncRq;

    /** 
     * Setter for compRemitStmtSyncRq
     * @param compRemitStmtSyncRq the org.sourceforge.ifx.framework.element.CompRemitStmtSyncRq[] to set
     */
    public void setCompRemitStmtSyncRq(org.sourceforge.ifx.framework.element.CompRemitStmtSyncRq[] _compRemitStmtSyncRq) {
        this._compRemitStmtSyncRq = _compRemitStmtSyncRq;
    }

    /**
     * Getter for compRemitStmtSyncRq
     * @return a org.sourceforge.ifx.framework.element.CompRemitStmtSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.CompRemitStmtSyncRq[] getCompRemitStmtSyncRq() {
        return _compRemitStmtSyncRq;
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
       * StdPayeeInqRq
       * StdPayeeRevRq
       * CustPayeeAddRq
       * CustPayeeModRq
       * CustPayeeTypeModRq
       * CustPayeeDelRq
       * CustPayeeInqRq
       * CustPayeeAudRq
       * CustPayeeSyncRq
       * PmtBatchAddRq
       * PmtBatchCanRq
       * PmtBatchStatusInqRq
       * PmtBatchStatusAdviseRq
       * PmtAddRq
       * PmtModRq
       * PmtStatusModRq
       * PmtStatusAdviseRq
       * PmtCanRq
       * PmtInqRq
       * PmtRevRq
       * PmtStatusInqRq
       * PmtAudRq
       * PmtSyncRq
       * PmtAuthAddRq
       * PmtAuthModRq
       * PmtAuthCanRq
       * PmtAuthInqRq
       * PmtAuthAudRq
       * PmtAuthSyncRq
       * PmtAuthRevRq
       * RemitAddRq
       * RemitModRq
       * RemitStatusModRq
       * RemitDelRq
       * RemitInqRq
       * RemitAudRq
       * RemitSyncRq
       * RemitRevRq
       * RecPmtAddRq
       * RecPmtInstAddRq
       * RecPmtModRq
       * RecPmtCanRq
       * RecPmtInqRq
       * RecPmtAudRq
       * RecPmtSyncRq
       * RecPmtRevRq
       * PmtAckInqRq
       * PmtAckRevRq
       * PmtAckAdviseRq
       * ChksumAddRq
       * ChksumModRq
       * ChksumStatusModRq
       * ChksumDelRq
       * ChksumInqRq
       * ChksumAudRq
       * ChksumSyncRq
       * ChksumAdviseRq
       * CompRemitStmtAddRq
       * CompRemitStmtInqRq
       * CompRemitStmtAudRq
       * CompRemitStmtSyncRq
       */
    public final String[] ELEMENTS = {
              "Id"
                 ,"RqUID"
                 ,"MsgRqHdr"
                 ,"AsyncRqUID"
                 ,"SPName"
                 ,"StdPayeeInqRq"
                 ,"StdPayeeRevRq"
                 ,"CustPayeeAddRq"
                 ,"CustPayeeModRq"
                 ,"CustPayeeTypeModRq"
                 ,"CustPayeeDelRq"
                 ,"CustPayeeInqRq"
                 ,"CustPayeeAudRq"
                 ,"CustPayeeSyncRq"
                 ,"PmtBatchAddRq"
                 ,"PmtBatchCanRq"
                 ,"PmtBatchStatusInqRq"
                 ,"PmtBatchStatusAdviseRq"
                 ,"PmtAddRq"
                 ,"PmtModRq"
                 ,"PmtStatusModRq"
                 ,"PmtStatusAdviseRq"
                 ,"PmtCanRq"
                 ,"PmtInqRq"
                 ,"PmtRevRq"
                 ,"PmtStatusInqRq"
                 ,"PmtAudRq"
                 ,"PmtSyncRq"
                 ,"PmtAuthAddRq"
                 ,"PmtAuthModRq"
                 ,"PmtAuthCanRq"
                 ,"PmtAuthInqRq"
                 ,"PmtAuthAudRq"
                 ,"PmtAuthSyncRq"
                 ,"PmtAuthRevRq"
                 ,"RemitAddRq"
                 ,"RemitModRq"
                 ,"RemitStatusModRq"
                 ,"RemitDelRq"
                 ,"RemitInqRq"
                 ,"RemitAudRq"
                 ,"RemitSyncRq"
                 ,"RemitRevRq"
                 ,"RecPmtAddRq"
                 ,"RecPmtInstAddRq"
                 ,"RecPmtModRq"
                 ,"RecPmtCanRq"
                 ,"RecPmtInqRq"
                 ,"RecPmtAudRq"
                 ,"RecPmtSyncRq"
                 ,"RecPmtRevRq"
                 ,"PmtAckInqRq"
                 ,"PmtAckRevRq"
                 ,"PmtAckAdviseRq"
                 ,"ChksumAddRq"
                 ,"ChksumModRq"
                 ,"ChksumStatusModRq"
                 ,"ChksumDelRq"
                 ,"ChksumInqRq"
                 ,"ChksumAudRq"
                 ,"ChksumSyncRq"
                 ,"ChksumAdviseRq"
                 ,"CompRemitStmtAddRq"
                 ,"CompRemitStmtInqRq"
                 ,"CompRemitStmtAudRq"
                 ,"CompRemitStmtSyncRq"
          };
}
