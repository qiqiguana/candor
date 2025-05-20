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
public class BillInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public BillInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillType _billType;

    /** 
     * Setter for billType
     * @param billType the org.sourceforge.ifx.framework.element.BillType to set
     */
    public void setBillType(org.sourceforge.ifx.framework.element.BillType _billType) {
        this._billType = _billType;
    }

    /**
     * Getter for billType
     * @return a org.sourceforge.ifx.framework.element.BillType
     */
    public org.sourceforge.ifx.framework.element.BillType getBillType() {
        return _billType;
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
    private org.sourceforge.ifx.framework.element.PresAcctId _presAcctId;

    /** 
     * Setter for presAcctId
     * @param presAcctId the org.sourceforge.ifx.framework.element.PresAcctId to set
     */
    public void setPresAcctId(org.sourceforge.ifx.framework.element.PresAcctId _presAcctId) {
        this._presAcctId = _presAcctId;
    }

    /**
     * Getter for presAcctId
     * @return a org.sourceforge.ifx.framework.element.PresAcctId
     */
    public org.sourceforge.ifx.framework.element.PresAcctId getPresAcctId() {
        return _presAcctId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Memo _memo;

    /** 
     * Setter for memo
     * @param memo the org.sourceforge.ifx.framework.element.Memo to set
     */
    public void setMemo(org.sourceforge.ifx.framework.element.Memo _memo) {
        this._memo = _memo;
    }

    /**
     * Getter for memo
     * @return a org.sourceforge.ifx.framework.element.Memo
     */
    public org.sourceforge.ifx.framework.element.Memo getMemo() {
        return _memo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillSummAmt[] _billSummAmt;

    /** 
     * Setter for billSummAmt
     * @param billSummAmt the org.sourceforge.ifx.framework.element.BillSummAmt[] to set
     */
    public void setBillSummAmt(org.sourceforge.ifx.framework.element.BillSummAmt[] _billSummAmt) {
        this._billSummAmt = _billSummAmt;
    }

    /**
     * Getter for billSummAmt
     * @return a org.sourceforge.ifx.framework.element.BillSummAmt[]
     */
    public org.sourceforge.ifx.framework.element.BillSummAmt[] getBillSummAmt() {
        return _billSummAmt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DueDt[] _dueDt;

    /** 
     * Setter for dueDt
     * @param dueDt the org.sourceforge.ifx.framework.element.DueDt[] to set
     */
    public void setDueDt(org.sourceforge.ifx.framework.element.DueDt[] _dueDt) {
        this._dueDt = _dueDt;
    }

    /**
     * Getter for dueDt
     * @return a org.sourceforge.ifx.framework.element.DueDt[]
     */
    public org.sourceforge.ifx.framework.element.DueDt[] getDueDt() {
        return _dueDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillDt[] _billDt;

    /** 
     * Setter for billDt
     * @param billDt the org.sourceforge.ifx.framework.element.BillDt[] to set
     */
    public void setBillDt(org.sourceforge.ifx.framework.element.BillDt[] _billDt) {
        this._billDt = _billDt;
    }

    /**
     * Getter for billDt
     * @return a org.sourceforge.ifx.framework.element.BillDt[]
     */
    public org.sourceforge.ifx.framework.element.BillDt[] getBillDt() {
        return _billDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.OpenDt[] _openDt;

    /** 
     * Setter for openDt
     * @param openDt the org.sourceforge.ifx.framework.element.OpenDt[] to set
     */
    public void setOpenDt(org.sourceforge.ifx.framework.element.OpenDt[] _openDt) {
        this._openDt = _openDt;
    }

    /**
     * Getter for openDt
     * @return a org.sourceforge.ifx.framework.element.OpenDt[]
     */
    public org.sourceforge.ifx.framework.element.OpenDt[] getOpenDt() {
        return _openDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CloseDt[] _closeDt;

    /** 
     * Setter for closeDt
     * @param closeDt the org.sourceforge.ifx.framework.element.CloseDt[] to set
     */
    public void setCloseDt(org.sourceforge.ifx.framework.element.CloseDt[] _closeDt) {
        this._closeDt = _closeDt;
    }

    /**
     * Getter for closeDt
     * @return a org.sourceforge.ifx.framework.element.CloseDt[]
     */
    public org.sourceforge.ifx.framework.element.CloseDt[] getCloseDt() {
        return _closeDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtInst[] _pmtInst;

    /** 
     * Setter for pmtInst
     * @param pmtInst the org.sourceforge.ifx.framework.element.PmtInst[] to set
     */
    public void setPmtInst(org.sourceforge.ifx.framework.element.PmtInst[] _pmtInst) {
        this._pmtInst = _pmtInst;
    }

    /**
     * Getter for pmtInst
     * @return a org.sourceforge.ifx.framework.element.PmtInst[]
     */
    public org.sourceforge.ifx.framework.element.PmtInst[] getPmtInst() {
        return _pmtInst;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.NotifyReqd[] _notifyReqd;

    /** 
     * Setter for notifyReqd
     * @param notifyReqd the org.sourceforge.ifx.framework.element.NotifyReqd[] to set
     */
    public void setNotifyReqd(org.sourceforge.ifx.framework.element.NotifyReqd[] _notifyReqd) {
        this._notifyReqd = _notifyReqd;
    }

    /**
     * Getter for notifyReqd
     * @return a org.sourceforge.ifx.framework.element.NotifyReqd[]
     */
    public org.sourceforge.ifx.framework.element.NotifyReqd[] getNotifyReqd() {
        return _notifyReqd;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ViewDtlPref[] _viewDtlPref;

    /** 
     * Setter for viewDtlPref
     * @param viewDtlPref the org.sourceforge.ifx.framework.element.ViewDtlPref[] to set
     */
    public void setViewDtlPref(org.sourceforge.ifx.framework.element.ViewDtlPref[] _viewDtlPref) {
        this._viewDtlPref = _viewDtlPref;
    }

    /**
     * Getter for viewDtlPref
     * @return a org.sourceforge.ifx.framework.element.ViewDtlPref[]
     */
    public org.sourceforge.ifx.framework.element.ViewDtlPref[] getViewDtlPref() {
        return _viewDtlPref;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StmtImage[] _stmtImage;

    /** 
     * Setter for stmtImage
     * @param stmtImage the org.sourceforge.ifx.framework.element.StmtImage[] to set
     */
    public void setStmtImage(org.sourceforge.ifx.framework.element.StmtImage[] _stmtImage) {
        this._stmtImage = _stmtImage;
    }

    /**
     * Getter for stmtImage
     * @return a org.sourceforge.ifx.framework.element.StmtImage[]
     */
    public org.sourceforge.ifx.framework.element.StmtImage[] getStmtImage() {
        return _stmtImage;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillRefInfo[] _billRefInfo;

    /** 
     * Setter for billRefInfo
     * @param billRefInfo the org.sourceforge.ifx.framework.element.BillRefInfo[] to set
     */
    public void setBillRefInfo(org.sourceforge.ifx.framework.element.BillRefInfo[] _billRefInfo) {
        this._billRefInfo = _billRefInfo;
    }

    /**
     * Getter for billRefInfo
     * @return a org.sourceforge.ifx.framework.element.BillRefInfo[]
     */
    public org.sourceforge.ifx.framework.element.BillRefInfo[] getBillRefInfo() {
        return _billRefInfo;
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
       * BillType
       * CustId
       * PresAcctId
       * Memo
       * BillSummAmt
       * DueDt
       * BillDt
       * OpenDt
       * CloseDt
       * PmtInst
       * NotifyReqd
       * ViewDtlPref
       * StmtImage
       * BillRefInfo
       */
    public final String[] ELEMENTS = {
              "BillType"
                 ,"CustId"
                 ,"PresAcctId"
                 ,"Memo"
                 ,"BillSummAmt"
                 ,"DueDt"
                 ,"BillDt"
                 ,"OpenDt"
                 ,"CloseDt"
                 ,"PmtInst"
                 ,"NotifyReqd"
                 ,"ViewDtlPref"
                 ,"StmtImage"
                 ,"BillRefInfo"
          };
}
