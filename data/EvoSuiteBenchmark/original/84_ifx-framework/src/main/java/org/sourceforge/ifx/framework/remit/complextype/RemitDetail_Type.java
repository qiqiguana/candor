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
package org.sourceforge.ifx.framework.remit.complextype;

/**
 * Generated code.
 * 
 * @author org.sourceforge.ifx.tools.CodeGenerator
 */
public class RemitDetail_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public RemitDetail_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.InvoiceReceiver _invoiceReceiver;

    /** 
     * Setter for invoiceReceiver
     * @param invoiceReceiver the org.sourceforge.ifx.framework.element.InvoiceReceiver to set
     */
    public void setInvoiceReceiver(org.sourceforge.ifx.framework.element.InvoiceReceiver _invoiceReceiver) {
        this._invoiceReceiver = _invoiceReceiver;
    }

    /**
     * Getter for invoiceReceiver
     * @return a org.sourceforge.ifx.framework.element.InvoiceReceiver
     */
    public org.sourceforge.ifx.framework.element.InvoiceReceiver getInvoiceReceiver() {
        return _invoiceReceiver;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.InvoiceSender _invoiceSender;

    /** 
     * Setter for invoiceSender
     * @param invoiceSender the org.sourceforge.ifx.framework.element.InvoiceSender to set
     */
    public void setInvoiceSender(org.sourceforge.ifx.framework.element.InvoiceSender _invoiceSender) {
        this._invoiceSender = _invoiceSender;
    }

    /**
     * Getter for invoiceSender
     * @return a org.sourceforge.ifx.framework.element.InvoiceSender
     */
    public org.sourceforge.ifx.framework.element.InvoiceSender getInvoiceSender() {
        return _invoiceSender;
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
    private org.sourceforge.ifx.framework.element.RefInfo[] _refInfo;

    /** 
     * Setter for refInfo
     * @param refInfo the org.sourceforge.ifx.framework.element.RefInfo[] to set
     */
    public void setRefInfo(org.sourceforge.ifx.framework.element.RefInfo[] _refInfo) {
        this._refInfo = _refInfo;
    }

    /**
     * Getter for refInfo
     * @return a org.sourceforge.ifx.framework.element.RefInfo[]
     */
    public org.sourceforge.ifx.framework.element.RefInfo[] getRefInfo() {
        return _refInfo;
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

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillId[] _billId;

    /** 
     * Setter for billId
     * @param billId the org.sourceforge.ifx.framework.element.BillId[] to set
     */
    public void setBillId(org.sourceforge.ifx.framework.element.BillId[] _billId) {
        this._billId = _billId;
    }

    /**
     * Getter for billId
     * @return a org.sourceforge.ifx.framework.element.BillId[]
     */
    public org.sourceforge.ifx.framework.element.BillId[] getBillId() {
        return _billId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Memo[] _memo;

    /** 
     * Setter for memo
     * @param memo the org.sourceforge.ifx.framework.element.Memo[] to set
     */
    public void setMemo(org.sourceforge.ifx.framework.element.Memo[] _memo) {
        this._memo = _memo;
    }

    /**
     * Getter for memo
     * @return a org.sourceforge.ifx.framework.element.Memo[]
     */
    public org.sourceforge.ifx.framework.element.Memo[] getMemo() {
        return _memo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillingAcct[] _billingAcct;

    /** 
     * Setter for billingAcct
     * @param billingAcct the org.sourceforge.ifx.framework.element.BillingAcct[] to set
     */
    public void setBillingAcct(org.sourceforge.ifx.framework.element.BillingAcct[] _billingAcct) {
        this._billingAcct = _billingAcct;
    }

    /**
     * Getter for billingAcct
     * @return a org.sourceforge.ifx.framework.element.BillingAcct[]
     */
    public org.sourceforge.ifx.framework.element.BillingAcct[] getBillingAcct() {
        return _billingAcct;
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


    /**
     * Returns true if objects are equal, false otherwise.
     * @param obj the object to compare with.
     * @return true if equal, false if not.
     */
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /** Element ordering - 
       * InvoiceReceiver
       * InvoiceSender
       * CurAmt
       * PmtSummAmt
       * RefInfo
       * BillRefInfo
       * BillId
       * Memo
       * BillingAcct
       * InvoiceInfo
       */
    public final String[] ELEMENTS = {
              "InvoiceReceiver"
                 ,"InvoiceSender"
                 ,"CurAmt"
                 ,"PmtSummAmt"
                 ,"RefInfo"
                 ,"BillRefInfo"
                 ,"BillId"
                 ,"Memo"
                 ,"BillingAcct"
                 ,"InvoiceInfo"
          };
}
