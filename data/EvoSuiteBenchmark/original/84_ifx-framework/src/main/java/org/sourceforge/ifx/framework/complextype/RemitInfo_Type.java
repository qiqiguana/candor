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
 * Made RemitDetail optional to resolve non-deterministic parsing error.
 * @author org.sourceforge.ifx.tools.CodeGenerator
 */
public class RemitInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public RemitInfo_Type() {
        super();
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
    private org.sourceforge.ifx.framework.element.SettlementInfo _settlementInfo;

    /** 
     * Setter for settlementInfo
     * @param settlementInfo the org.sourceforge.ifx.framework.element.SettlementInfo to set
     */
    public void setSettlementInfo(org.sourceforge.ifx.framework.element.SettlementInfo _settlementInfo) {
        this._settlementInfo = _settlementInfo;
    }

    /**
     * Getter for settlementInfo
     * @return a org.sourceforge.ifx.framework.element.SettlementInfo
     */
    public org.sourceforge.ifx.framework.element.SettlementInfo getSettlementInfo() {
        return _settlementInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CounterpartyInfo _counterpartyInfo;

    /** 
     * Setter for counterpartyInfo
     * @param counterpartyInfo the org.sourceforge.ifx.framework.element.CounterpartyInfo to set
     */
    public void setCounterpartyInfo(org.sourceforge.ifx.framework.element.CounterpartyInfo _counterpartyInfo) {
        this._counterpartyInfo = _counterpartyInfo;
    }

    /**
     * Getter for counterpartyInfo
     * @return a org.sourceforge.ifx.framework.element.CounterpartyInfo
     */
    public org.sourceforge.ifx.framework.element.CounterpartyInfo getCounterpartyInfo() {
        return _counterpartyInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkInfo _chkInfo;

    /** 
     * Setter for chkInfo
     * @param chkInfo the org.sourceforge.ifx.framework.element.ChkInfo to set
     */
    public void setChkInfo(org.sourceforge.ifx.framework.element.ChkInfo _chkInfo) {
        this._chkInfo = _chkInfo;
    }

    /**
     * Getter for chkInfo
     * @return a org.sourceforge.ifx.framework.element.ChkInfo
     */
    public org.sourceforge.ifx.framework.element.ChkInfo getChkInfo() {
        return _chkInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkURL _chkURL;

    /** 
     * Setter for chkURL
     * @param chkURL the org.sourceforge.ifx.framework.element.ChkURL to set
     */
    public void setChkURL(org.sourceforge.ifx.framework.element.ChkURL _chkURL) {
        this._chkURL = _chkURL;
    }

    /**
     * Getter for chkURL
     * @return a org.sourceforge.ifx.framework.element.ChkURL
     */
    public org.sourceforge.ifx.framework.element.ChkURL getChkURL() {
        return _chkURL;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustPayeeId _custPayeeId;

    /** 
     * Setter for custPayeeId
     * @param custPayeeId the org.sourceforge.ifx.framework.element.CustPayeeId to set
     */
    public void setCustPayeeId(org.sourceforge.ifx.framework.element.CustPayeeId _custPayeeId) {
        this._custPayeeId = _custPayeeId;
    }

    /**
     * Getter for custPayeeId
     * @return a org.sourceforge.ifx.framework.element.CustPayeeId
     */
    public org.sourceforge.ifx.framework.element.CustPayeeId getCustPayeeId() {
        return _custPayeeId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustPayeeInfo _custPayeeInfo;

    /** 
     * Setter for custPayeeInfo
     * @param custPayeeInfo the org.sourceforge.ifx.framework.element.CustPayeeInfo to set
     */
    public void setCustPayeeInfo(org.sourceforge.ifx.framework.element.CustPayeeInfo _custPayeeInfo) {
        this._custPayeeInfo = _custPayeeInfo;
    }

    /**
     * Getter for custPayeeInfo
     * @return a org.sourceforge.ifx.framework.element.CustPayeeInfo
     */
    public org.sourceforge.ifx.framework.element.CustPayeeInfo getCustPayeeInfo() {
        return _custPayeeInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillRefInfo _billRefInfo;

    /** 
     * Setter for billRefInfo
     * @param billRefInfo the org.sourceforge.ifx.framework.element.BillRefInfo to set
     */
    public void setBillRefInfo(org.sourceforge.ifx.framework.element.BillRefInfo _billRefInfo) {
        this._billRefInfo = _billRefInfo;
    }

    /**
     * Getter for billRefInfo
     * @return a org.sourceforge.ifx.framework.element.BillRefInfo
     */
    public org.sourceforge.ifx.framework.element.BillRefInfo getBillRefInfo() {
        return _billRefInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillId _billId;

    /** 
     * Setter for billId
     * @param billId the org.sourceforge.ifx.framework.element.BillId to set
     */
    public void setBillId(org.sourceforge.ifx.framework.element.BillId _billId) {
        this._billId = _billId;
    }

    /**
     * Getter for billId
     * @return a org.sourceforge.ifx.framework.element.BillId
     */
    public org.sourceforge.ifx.framework.element.BillId getBillId() {
        return _billId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtId _pmtId;

    /** 
     * Setter for pmtId
     * @param pmtId the org.sourceforge.ifx.framework.element.PmtId to set
     */
    public void setPmtId(org.sourceforge.ifx.framework.element.PmtId _pmtId) {
        this._pmtId = _pmtId;
    }

    /**
     * Getter for pmtId
     * @return a org.sourceforge.ifx.framework.element.PmtId
     */
    public org.sourceforge.ifx.framework.element.PmtId getPmtId() {
        return _pmtId;
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
    private org.sourceforge.ifx.framework.element.BillingAcct _billingAcct;

    /** 
     * Setter for billingAcct
     * @param billingAcct the org.sourceforge.ifx.framework.element.BillingAcct to set
     */
    public void setBillingAcct(org.sourceforge.ifx.framework.element.BillingAcct _billingAcct) {
        this._billingAcct = _billingAcct;
    }

    /**
     * Getter for billingAcct
     * @return a org.sourceforge.ifx.framework.element.BillingAcct
     */
    public org.sourceforge.ifx.framework.element.BillingAcct getBillingAcct() {
        return _billingAcct;
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
    private org.sourceforge.ifx.framework.element.PmtSummAmt[] _pmtSummAmt;

    /** 
     * Setter for pmtSummAmt
     * @param pmtSummAmt the org.sourceforge.ifx.framework.element.PmtSummAmt[] to set
     */
    public void setPmtSummAmt(org.sourceforge.ifx.framework.element.PmtSummAmt[] _pmtSummAmt) {
        this._pmtSummAmt = _pmtSummAmt;
    }

    /**
     * Getter for pmtSummAmt
     * @return a org.sourceforge.ifx.framework.element.PmtSummAmt[]
     */
    public org.sourceforge.ifx.framework.element.PmtSummAmt[] getPmtSummAmt() {
        return _pmtSummAmt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.InvoiceInfo[] _invoiceInfo;

    /** 
     * Setter for invoiceInfo
     * @param invoiceInfo the org.sourceforge.ifx.framework.element.InvoiceInfo[] to set
     */
    public void setInvoiceInfo(org.sourceforge.ifx.framework.element.InvoiceInfo[] _invoiceInfo) {
        this._invoiceInfo = _invoiceInfo;
    }

    /**
     * Getter for invoiceInfo
     * @return a org.sourceforge.ifx.framework.element.InvoiceInfo[]
     */
    public org.sourceforge.ifx.framework.element.InvoiceInfo[] getInvoiceInfo() {
        return _invoiceInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtLegalRpt _pmtLegalRpt;

    /** 
     * Setter for pmtLegalRpt
     * @param pmtLegalRpt the org.sourceforge.ifx.framework.element.PmtLegalRpt to set
     */
    public void setPmtLegalRpt(org.sourceforge.ifx.framework.element.PmtLegalRpt _pmtLegalRpt) {
        this._pmtLegalRpt = _pmtLegalRpt;
    }

    /**
     * Getter for pmtLegalRpt
     * @return a org.sourceforge.ifx.framework.element.PmtLegalRpt
     */
    public org.sourceforge.ifx.framework.element.PmtLegalRpt getPmtLegalRpt() {
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
       * RemitInstruction
       * SettlementInfo
       * CounterpartyInfo
       * ChkInfo
       * ChkURL
       * CustPayeeId
       * CustPayeeInfo
       * BillRefInfo
       * BillId
       * PmtId
       * Memo
       * BillingAcct
       * CurAmt
       * PmtSummAmt
       * InvoiceInfo
       * PmtLegalRpt
       */
    public final String[] ELEMENTS = {
              "RemitInstruction"
                 ,"SettlementInfo"
                 ,"CounterpartyInfo"
                 ,"ChkInfo"
                 ,"ChkURL"
                 ,"CustPayeeId"
                 ,"CustPayeeInfo"
                 ,"BillRefInfo"
                 ,"BillId"
                 ,"PmtId"
                 ,"Memo"
                 ,"BillingAcct"
                 ,"CurAmt"
                 ,"PmtSummAmt"
                 ,"InvoiceInfo"
                 ,"PmtLegalRpt"
          };
}
