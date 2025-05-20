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
public class BankSvcRq_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public BankSvcRq_Type() {
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
    private org.sourceforge.ifx.framework.element.BalInqRq[] _balInqRq;

    /** 
     * Setter for balInqRq
     * @param balInqRq the org.sourceforge.ifx.framework.element.BalInqRq[] to set
     */
    public void setBalInqRq(org.sourceforge.ifx.framework.element.BalInqRq[] _balInqRq) {
        this._balInqRq = _balInqRq;
    }

    /**
     * Getter for balInqRq
     * @return a org.sourceforge.ifx.framework.element.BalInqRq[]
     */
    public org.sourceforge.ifx.framework.element.BalInqRq[] getBalInqRq() {
        return _balInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BalRevRq[] _balRevRq;

    /** 
     * Setter for balRevRq
     * @param balRevRq the org.sourceforge.ifx.framework.element.BalRevRq[] to set
     */
    public void setBalRevRq(org.sourceforge.ifx.framework.element.BalRevRq[] _balRevRq) {
        this._balRevRq = _balRevRq;
    }

    /**
     * Getter for balRevRq
     * @return a org.sourceforge.ifx.framework.element.BalRevRq[]
     */
    public org.sourceforge.ifx.framework.element.BalRevRq[] getBalRevRq() {
        return _balRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AcctInqRq[] _acctInqRq;

    /** 
     * Setter for acctInqRq
     * @param acctInqRq the org.sourceforge.ifx.framework.element.AcctInqRq[] to set
     */
    public void setAcctInqRq(org.sourceforge.ifx.framework.element.AcctInqRq[] _acctInqRq) {
        this._acctInqRq = _acctInqRq;
    }

    /**
     * Getter for acctInqRq
     * @return a org.sourceforge.ifx.framework.element.AcctInqRq[]
     */
    public org.sourceforge.ifx.framework.element.AcctInqRq[] getAcctInqRq() {
        return _acctInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AcctRevRq[] _acctRevRq;

    /** 
     * Setter for acctRevRq
     * @param acctRevRq the org.sourceforge.ifx.framework.element.AcctRevRq[] to set
     */
    public void setAcctRevRq(org.sourceforge.ifx.framework.element.AcctRevRq[] _acctRevRq) {
        this._acctRevRq = _acctRevRq;
    }

    /**
     * Getter for acctRevRq
     * @return a org.sourceforge.ifx.framework.element.AcctRevRq[]
     */
    public org.sourceforge.ifx.framework.element.AcctRevRq[] getAcctRevRq() {
        return _acctRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAcctStmtInqRq[] _depAcctStmtInqRq;

    /** 
     * Setter for depAcctStmtInqRq
     * @param depAcctStmtInqRq the org.sourceforge.ifx.framework.element.DepAcctStmtInqRq[] to set
     */
    public void setDepAcctStmtInqRq(org.sourceforge.ifx.framework.element.DepAcctStmtInqRq[] _depAcctStmtInqRq) {
        this._depAcctStmtInqRq = _depAcctStmtInqRq;
    }

    /**
     * Getter for depAcctStmtInqRq
     * @return a org.sourceforge.ifx.framework.element.DepAcctStmtInqRq[]
     */
    public org.sourceforge.ifx.framework.element.DepAcctStmtInqRq[] getDepAcctStmtInqRq() {
        return _depAcctStmtInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAcctStmtRevRq[] _depAcctStmtRevRq;

    /** 
     * Setter for depAcctStmtRevRq
     * @param depAcctStmtRevRq the org.sourceforge.ifx.framework.element.DepAcctStmtRevRq[] to set
     */
    public void setDepAcctStmtRevRq(org.sourceforge.ifx.framework.element.DepAcctStmtRevRq[] _depAcctStmtRevRq) {
        this._depAcctStmtRevRq = _depAcctStmtRevRq;
    }

    /**
     * Getter for depAcctStmtRevRq
     * @return a org.sourceforge.ifx.framework.element.DepAcctStmtRevRq[]
     */
    public org.sourceforge.ifx.framework.element.DepAcctStmtRevRq[] getDepAcctStmtRevRq() {
        return _depAcctStmtRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CCAcctStmtInqRq[] _cCAcctStmtInqRq;

    /** 
     * Setter for cCAcctStmtInqRq
     * @param cCAcctStmtInqRq the org.sourceforge.ifx.framework.element.CCAcctStmtInqRq[] to set
     */
    public void setCCAcctStmtInqRq(org.sourceforge.ifx.framework.element.CCAcctStmtInqRq[] _cCAcctStmtInqRq) {
        this._cCAcctStmtInqRq = _cCAcctStmtInqRq;
    }

    /**
     * Getter for cCAcctStmtInqRq
     * @return a org.sourceforge.ifx.framework.element.CCAcctStmtInqRq[]
     */
    public org.sourceforge.ifx.framework.element.CCAcctStmtInqRq[] getCCAcctStmtInqRq() {
        return _cCAcctStmtInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CCAcctStmtRevRq[] _cCAcctStmtRevRq;

    /** 
     * Setter for cCAcctStmtRevRq
     * @param cCAcctStmtRevRq the org.sourceforge.ifx.framework.element.CCAcctStmtRevRq[] to set
     */
    public void setCCAcctStmtRevRq(org.sourceforge.ifx.framework.element.CCAcctStmtRevRq[] _cCAcctStmtRevRq) {
        this._cCAcctStmtRevRq = _cCAcctStmtRevRq;
    }

    /**
     * Getter for cCAcctStmtRevRq
     * @return a org.sourceforge.ifx.framework.element.CCAcctStmtRevRq[]
     */
    public org.sourceforge.ifx.framework.element.CCAcctStmtRevRq[] getCCAcctStmtRevRq() {
        return _cCAcctStmtRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAcctTrnInqRq[] _depAcctTrnInqRq;

    /** 
     * Setter for depAcctTrnInqRq
     * @param depAcctTrnInqRq the org.sourceforge.ifx.framework.element.DepAcctTrnInqRq[] to set
     */
    public void setDepAcctTrnInqRq(org.sourceforge.ifx.framework.element.DepAcctTrnInqRq[] _depAcctTrnInqRq) {
        this._depAcctTrnInqRq = _depAcctTrnInqRq;
    }

    /**
     * Getter for depAcctTrnInqRq
     * @return a org.sourceforge.ifx.framework.element.DepAcctTrnInqRq[]
     */
    public org.sourceforge.ifx.framework.element.DepAcctTrnInqRq[] getDepAcctTrnInqRq() {
        return _depAcctTrnInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAcctTrnRevRq[] _depAcctTrnRevRq;

    /** 
     * Setter for depAcctTrnRevRq
     * @param depAcctTrnRevRq the org.sourceforge.ifx.framework.element.DepAcctTrnRevRq[] to set
     */
    public void setDepAcctTrnRevRq(org.sourceforge.ifx.framework.element.DepAcctTrnRevRq[] _depAcctTrnRevRq) {
        this._depAcctTrnRevRq = _depAcctTrnRevRq;
    }

    /**
     * Getter for depAcctTrnRevRq
     * @return a org.sourceforge.ifx.framework.element.DepAcctTrnRevRq[]
     */
    public org.sourceforge.ifx.framework.element.DepAcctTrnRevRq[] getDepAcctTrnRevRq() {
        return _depAcctTrnRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAcctTrnAdviseRq[] _depAcctTrnAdviseRq;

    /** 
     * Setter for depAcctTrnAdviseRq
     * @param depAcctTrnAdviseRq the org.sourceforge.ifx.framework.element.DepAcctTrnAdviseRq[] to set
     */
    public void setDepAcctTrnAdviseRq(org.sourceforge.ifx.framework.element.DepAcctTrnAdviseRq[] _depAcctTrnAdviseRq) {
        this._depAcctTrnAdviseRq = _depAcctTrnAdviseRq;
    }

    /**
     * Getter for depAcctTrnAdviseRq
     * @return a org.sourceforge.ifx.framework.element.DepAcctTrnAdviseRq[]
     */
    public org.sourceforge.ifx.framework.element.DepAcctTrnAdviseRq[] getDepAcctTrnAdviseRq() {
        return _depAcctTrnAdviseRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CCAcctTrnInqRq[] _cCAcctTrnInqRq;

    /** 
     * Setter for cCAcctTrnInqRq
     * @param cCAcctTrnInqRq the org.sourceforge.ifx.framework.element.CCAcctTrnInqRq[] to set
     */
    public void setCCAcctTrnInqRq(org.sourceforge.ifx.framework.element.CCAcctTrnInqRq[] _cCAcctTrnInqRq) {
        this._cCAcctTrnInqRq = _cCAcctTrnInqRq;
    }

    /**
     * Getter for cCAcctTrnInqRq
     * @return a org.sourceforge.ifx.framework.element.CCAcctTrnInqRq[]
     */
    public org.sourceforge.ifx.framework.element.CCAcctTrnInqRq[] getCCAcctTrnInqRq() {
        return _cCAcctTrnInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CCAcctTrnRevRq[] _cCAcctTrnRevRq;

    /** 
     * Setter for cCAcctTrnRevRq
     * @param cCAcctTrnRevRq the org.sourceforge.ifx.framework.element.CCAcctTrnRevRq[] to set
     */
    public void setCCAcctTrnRevRq(org.sourceforge.ifx.framework.element.CCAcctTrnRevRq[] _cCAcctTrnRevRq) {
        this._cCAcctTrnRevRq = _cCAcctTrnRevRq;
    }

    /**
     * Getter for cCAcctTrnRevRq
     * @return a org.sourceforge.ifx.framework.element.CCAcctTrnRevRq[]
     */
    public org.sourceforge.ifx.framework.element.CCAcctTrnRevRq[] getCCAcctTrnRevRq() {
        return _cCAcctTrnRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankAcctTrnImgInqRq[] _bankAcctTrnImgInqRq;

    /** 
     * Setter for bankAcctTrnImgInqRq
     * @param bankAcctTrnImgInqRq the org.sourceforge.ifx.framework.element.BankAcctTrnImgInqRq[] to set
     */
    public void setBankAcctTrnImgInqRq(org.sourceforge.ifx.framework.element.BankAcctTrnImgInqRq[] _bankAcctTrnImgInqRq) {
        this._bankAcctTrnImgInqRq = _bankAcctTrnImgInqRq;
    }

    /**
     * Getter for bankAcctTrnImgInqRq
     * @return a org.sourceforge.ifx.framework.element.BankAcctTrnImgInqRq[]
     */
    public org.sourceforge.ifx.framework.element.BankAcctTrnImgInqRq[] getBankAcctTrnImgInqRq() {
        return _bankAcctTrnImgInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankAcctTrnImgRevRq[] _bankAcctTrnImgRevRq;

    /** 
     * Setter for bankAcctTrnImgRevRq
     * @param bankAcctTrnImgRevRq the org.sourceforge.ifx.framework.element.BankAcctTrnImgRevRq[] to set
     */
    public void setBankAcctTrnImgRevRq(org.sourceforge.ifx.framework.element.BankAcctTrnImgRevRq[] _bankAcctTrnImgRevRq) {
        this._bankAcctTrnImgRevRq = _bankAcctTrnImgRevRq;
    }

    /**
     * Getter for bankAcctTrnImgRevRq
     * @return a org.sourceforge.ifx.framework.element.BankAcctTrnImgRevRq[]
     */
    public org.sourceforge.ifx.framework.element.BankAcctTrnImgRevRq[] getBankAcctTrnImgRevRq() {
        return _bankAcctTrnImgRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.IntRateInqRq[] _intRateInqRq;

    /** 
     * Setter for intRateInqRq
     * @param intRateInqRq the org.sourceforge.ifx.framework.element.IntRateInqRq[] to set
     */
    public void setIntRateInqRq(org.sourceforge.ifx.framework.element.IntRateInqRq[] _intRateInqRq) {
        this._intRateInqRq = _intRateInqRq;
    }

    /**
     * Getter for intRateInqRq
     * @return a org.sourceforge.ifx.framework.element.IntRateInqRq[]
     */
    public org.sourceforge.ifx.framework.element.IntRateInqRq[] getIntRateInqRq() {
        return _intRateInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.IntRateRevRq[] _intRateRevRq;

    /** 
     * Setter for intRateRevRq
     * @param intRateRevRq the org.sourceforge.ifx.framework.element.IntRateRevRq[] to set
     */
    public void setIntRateRevRq(org.sourceforge.ifx.framework.element.IntRateRevRq[] _intRateRevRq) {
        this._intRateRevRq = _intRateRevRq;
    }

    /**
     * Getter for intRateRevRq
     * @return a org.sourceforge.ifx.framework.element.IntRateRevRq[]
     */
    public org.sourceforge.ifx.framework.element.IntRateRevRq[] getIntRateRevRq() {
        return _intRateRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankAcctTaxInqRq[] _bankAcctTaxInqRq;

    /** 
     * Setter for bankAcctTaxInqRq
     * @param bankAcctTaxInqRq the org.sourceforge.ifx.framework.element.BankAcctTaxInqRq[] to set
     */
    public void setBankAcctTaxInqRq(org.sourceforge.ifx.framework.element.BankAcctTaxInqRq[] _bankAcctTaxInqRq) {
        this._bankAcctTaxInqRq = _bankAcctTaxInqRq;
    }

    /**
     * Getter for bankAcctTaxInqRq
     * @return a org.sourceforge.ifx.framework.element.BankAcctTaxInqRq[]
     */
    public org.sourceforge.ifx.framework.element.BankAcctTaxInqRq[] getBankAcctTaxInqRq() {
        return _bankAcctTaxInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ForExRateInqRq[] _forExRateInqRq;

    /** 
     * Setter for forExRateInqRq
     * @param forExRateInqRq the org.sourceforge.ifx.framework.element.ForExRateInqRq[] to set
     */
    public void setForExRateInqRq(org.sourceforge.ifx.framework.element.ForExRateInqRq[] _forExRateInqRq) {
        this._forExRateInqRq = _forExRateInqRq;
    }

    /**
     * Getter for forExRateInqRq
     * @return a org.sourceforge.ifx.framework.element.ForExRateInqRq[]
     */
    public org.sourceforge.ifx.framework.element.ForExRateInqRq[] getForExRateInqRq() {
        return _forExRateInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ForExRateRevRq[] _forExRateRevRq;

    /** 
     * Setter for forExRateRevRq
     * @param forExRateRevRq the org.sourceforge.ifx.framework.element.ForExRateRevRq[] to set
     */
    public void setForExRateRevRq(org.sourceforge.ifx.framework.element.ForExRateRevRq[] _forExRateRevRq) {
        this._forExRateRevRq = _forExRateRevRq;
    }

    /**
     * Getter for forExRateRevRq
     * @return a org.sourceforge.ifx.framework.element.ForExRateRevRq[]
     */
    public org.sourceforge.ifx.framework.element.ForExRateRevRq[] getForExRateRevRq() {
        return _forExRateRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ForExDealAddRq[] _forExDealAddRq;

    /** 
     * Setter for forExDealAddRq
     * @param forExDealAddRq the org.sourceforge.ifx.framework.element.ForExDealAddRq[] to set
     */
    public void setForExDealAddRq(org.sourceforge.ifx.framework.element.ForExDealAddRq[] _forExDealAddRq) {
        this._forExDealAddRq = _forExDealAddRq;
    }

    /**
     * Getter for forExDealAddRq
     * @return a org.sourceforge.ifx.framework.element.ForExDealAddRq[]
     */
    public org.sourceforge.ifx.framework.element.ForExDealAddRq[] getForExDealAddRq() {
        return _forExDealAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ForExDealModRq[] _forExDealModRq;

    /** 
     * Setter for forExDealModRq
     * @param forExDealModRq the org.sourceforge.ifx.framework.element.ForExDealModRq[] to set
     */
    public void setForExDealModRq(org.sourceforge.ifx.framework.element.ForExDealModRq[] _forExDealModRq) {
        this._forExDealModRq = _forExDealModRq;
    }

    /**
     * Getter for forExDealModRq
     * @return a org.sourceforge.ifx.framework.element.ForExDealModRq[]
     */
    public org.sourceforge.ifx.framework.element.ForExDealModRq[] getForExDealModRq() {
        return _forExDealModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ForExDealInqRq[] _forExDealInqRq;

    /** 
     * Setter for forExDealInqRq
     * @param forExDealInqRq the org.sourceforge.ifx.framework.element.ForExDealInqRq[] to set
     */
    public void setForExDealInqRq(org.sourceforge.ifx.framework.element.ForExDealInqRq[] _forExDealInqRq) {
        this._forExDealInqRq = _forExDealInqRq;
    }

    /**
     * Getter for forExDealInqRq
     * @return a org.sourceforge.ifx.framework.element.ForExDealInqRq[]
     */
    public org.sourceforge.ifx.framework.element.ForExDealInqRq[] getForExDealInqRq() {
        return _forExDealInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ForExDealCanRq[] _forExDealCanRq;

    /** 
     * Setter for forExDealCanRq
     * @param forExDealCanRq the org.sourceforge.ifx.framework.element.ForExDealCanRq[] to set
     */
    public void setForExDealCanRq(org.sourceforge.ifx.framework.element.ForExDealCanRq[] _forExDealCanRq) {
        this._forExDealCanRq = _forExDealCanRq;
    }

    /**
     * Getter for forExDealCanRq
     * @return a org.sourceforge.ifx.framework.element.ForExDealCanRq[]
     */
    public org.sourceforge.ifx.framework.element.ForExDealCanRq[] getForExDealCanRq() {
        return _forExDealCanRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ForExDealAudRq[] _forExDealAudRq;

    /** 
     * Setter for forExDealAudRq
     * @param forExDealAudRq the org.sourceforge.ifx.framework.element.ForExDealAudRq[] to set
     */
    public void setForExDealAudRq(org.sourceforge.ifx.framework.element.ForExDealAudRq[] _forExDealAudRq) {
        this._forExDealAudRq = _forExDealAudRq;
    }

    /**
     * Getter for forExDealAudRq
     * @return a org.sourceforge.ifx.framework.element.ForExDealAudRq[]
     */
    public org.sourceforge.ifx.framework.element.ForExDealAudRq[] getForExDealAudRq() {
        return _forExDealAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ForExDealSyncRq[] _forExDealSyncRq;

    /** 
     * Setter for forExDealSyncRq
     * @param forExDealSyncRq the org.sourceforge.ifx.framework.element.ForExDealSyncRq[] to set
     */
    public void setForExDealSyncRq(org.sourceforge.ifx.framework.element.ForExDealSyncRq[] _forExDealSyncRq) {
        this._forExDealSyncRq = _forExDealSyncRq;
    }

    /**
     * Getter for forExDealSyncRq
     * @return a org.sourceforge.ifx.framework.element.ForExDealSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.ForExDealSyncRq[] getForExDealSyncRq() {
        return _forExDealSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ForExDealRevRq[] _forExDealRevRq;

    /** 
     * Setter for forExDealRevRq
     * @param forExDealRevRq the org.sourceforge.ifx.framework.element.ForExDealRevRq[] to set
     */
    public void setForExDealRevRq(org.sourceforge.ifx.framework.element.ForExDealRevRq[] _forExDealRevRq) {
        this._forExDealRevRq = _forExDealRevRq;
    }

    /**
     * Getter for forExDealRevRq
     * @return a org.sourceforge.ifx.framework.element.ForExDealRevRq[]
     */
    public org.sourceforge.ifx.framework.element.ForExDealRevRq[] getForExDealRevRq() {
        return _forExDealRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ForExDealStatusModRq[] _forExDealStatusModRq;

    /** 
     * Setter for forExDealStatusModRq
     * @param forExDealStatusModRq the org.sourceforge.ifx.framework.element.ForExDealStatusModRq[] to set
     */
    public void setForExDealStatusModRq(org.sourceforge.ifx.framework.element.ForExDealStatusModRq[] _forExDealStatusModRq) {
        this._forExDealStatusModRq = _forExDealStatusModRq;
    }

    /**
     * Getter for forExDealStatusModRq
     * @return a org.sourceforge.ifx.framework.element.ForExDealStatusModRq[]
     */
    public org.sourceforge.ifx.framework.element.ForExDealStatusModRq[] getForExDealStatusModRq() {
        return _forExDealStatusModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ForExDealStatusInqRq[] _forExDealStatusInqRq;

    /** 
     * Setter for forExDealStatusInqRq
     * @param forExDealStatusInqRq the org.sourceforge.ifx.framework.element.ForExDealStatusInqRq[] to set
     */
    public void setForExDealStatusInqRq(org.sourceforge.ifx.framework.element.ForExDealStatusInqRq[] _forExDealStatusInqRq) {
        this._forExDealStatusInqRq = _forExDealStatusInqRq;
    }

    /**
     * Getter for forExDealStatusInqRq
     * @return a org.sourceforge.ifx.framework.element.ForExDealStatusInqRq[]
     */
    public org.sourceforge.ifx.framework.element.ForExDealStatusInqRq[] getForExDealStatusInqRq() {
        return _forExDealStatusInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ForExDealAdviseRq[] _forExDealAdviseRq;

    /** 
     * Setter for forExDealAdviseRq
     * @param forExDealAdviseRq the org.sourceforge.ifx.framework.element.ForExDealAdviseRq[] to set
     */
    public void setForExDealAdviseRq(org.sourceforge.ifx.framework.element.ForExDealAdviseRq[] _forExDealAdviseRq) {
        this._forExDealAdviseRq = _forExDealAdviseRq;
    }

    /**
     * Getter for forExDealAdviseRq
     * @return a org.sourceforge.ifx.framework.element.ForExDealAdviseRq[]
     */
    public org.sourceforge.ifx.framework.element.ForExDealAdviseRq[] getForExDealAdviseRq() {
        return _forExDealAdviseRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StopChkAddRq[] _stopChkAddRq;

    /** 
     * Setter for stopChkAddRq
     * @param stopChkAddRq the org.sourceforge.ifx.framework.element.StopChkAddRq[] to set
     */
    public void setStopChkAddRq(org.sourceforge.ifx.framework.element.StopChkAddRq[] _stopChkAddRq) {
        this._stopChkAddRq = _stopChkAddRq;
    }

    /**
     * Getter for stopChkAddRq
     * @return a org.sourceforge.ifx.framework.element.StopChkAddRq[]
     */
    public org.sourceforge.ifx.framework.element.StopChkAddRq[] getStopChkAddRq() {
        return _stopChkAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StopChkAdviseRq[] _stopChkAdviseRq;

    /** 
     * Setter for stopChkAdviseRq
     * @param stopChkAdviseRq the org.sourceforge.ifx.framework.element.StopChkAdviseRq[] to set
     */
    public void setStopChkAdviseRq(org.sourceforge.ifx.framework.element.StopChkAdviseRq[] _stopChkAdviseRq) {
        this._stopChkAdviseRq = _stopChkAdviseRq;
    }

    /**
     * Getter for stopChkAdviseRq
     * @return a org.sourceforge.ifx.framework.element.StopChkAdviseRq[]
     */
    public org.sourceforge.ifx.framework.element.StopChkAdviseRq[] getStopChkAdviseRq() {
        return _stopChkAdviseRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StopChkCanRq[] _stopChkCanRq;

    /** 
     * Setter for stopChkCanRq
     * @param stopChkCanRq the org.sourceforge.ifx.framework.element.StopChkCanRq[] to set
     */
    public void setStopChkCanRq(org.sourceforge.ifx.framework.element.StopChkCanRq[] _stopChkCanRq) {
        this._stopChkCanRq = _stopChkCanRq;
    }

    /**
     * Getter for stopChkCanRq
     * @return a org.sourceforge.ifx.framework.element.StopChkCanRq[]
     */
    public org.sourceforge.ifx.framework.element.StopChkCanRq[] getStopChkCanRq() {
        return _stopChkCanRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StopChkInqRq[] _stopChkInqRq;

    /** 
     * Setter for stopChkInqRq
     * @param stopChkInqRq the org.sourceforge.ifx.framework.element.StopChkInqRq[] to set
     */
    public void setStopChkInqRq(org.sourceforge.ifx.framework.element.StopChkInqRq[] _stopChkInqRq) {
        this._stopChkInqRq = _stopChkInqRq;
    }

    /**
     * Getter for stopChkInqRq
     * @return a org.sourceforge.ifx.framework.element.StopChkInqRq[]
     */
    public org.sourceforge.ifx.framework.element.StopChkInqRq[] getStopChkInqRq() {
        return _stopChkInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StopChkAudRq[] _stopChkAudRq;

    /** 
     * Setter for stopChkAudRq
     * @param stopChkAudRq the org.sourceforge.ifx.framework.element.StopChkAudRq[] to set
     */
    public void setStopChkAudRq(org.sourceforge.ifx.framework.element.StopChkAudRq[] _stopChkAudRq) {
        this._stopChkAudRq = _stopChkAudRq;
    }

    /**
     * Getter for stopChkAudRq
     * @return a org.sourceforge.ifx.framework.element.StopChkAudRq[]
     */
    public org.sourceforge.ifx.framework.element.StopChkAudRq[] getStopChkAudRq() {
        return _stopChkAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StopChkSyncRq[] _stopChkSyncRq;

    /** 
     * Setter for stopChkSyncRq
     * @param stopChkSyncRq the org.sourceforge.ifx.framework.element.StopChkSyncRq[] to set
     */
    public void setStopChkSyncRq(org.sourceforge.ifx.framework.element.StopChkSyncRq[] _stopChkSyncRq) {
        this._stopChkSyncRq = _stopChkSyncRq;
    }

    /**
     * Getter for stopChkSyncRq
     * @return a org.sourceforge.ifx.framework.element.StopChkSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.StopChkSyncRq[] getStopChkSyncRq() {
        return _stopChkSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StopChkRevRq[] _stopChkRevRq;

    /** 
     * Setter for stopChkRevRq
     * @param stopChkRevRq the org.sourceforge.ifx.framework.element.StopChkRevRq[] to set
     */
    public void setStopChkRevRq(org.sourceforge.ifx.framework.element.StopChkRevRq[] _stopChkRevRq) {
        this._stopChkRevRq = _stopChkRevRq;
    }

    /**
     * Getter for stopChkRevRq
     * @return a org.sourceforge.ifx.framework.element.StopChkRevRq[]
     */
    public org.sourceforge.ifx.framework.element.StopChkRevRq[] getStopChkRevRq() {
        return _stopChkRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.XferAddRq[] _xferAddRq;

    /** 
     * Setter for xferAddRq
     * @param xferAddRq the org.sourceforge.ifx.framework.element.XferAddRq[] to set
     */
    public void setXferAddRq(org.sourceforge.ifx.framework.element.XferAddRq[] _xferAddRq) {
        this._xferAddRq = _xferAddRq;
    }

    /**
     * Getter for xferAddRq
     * @return a org.sourceforge.ifx.framework.element.XferAddRq[]
     */
    public org.sourceforge.ifx.framework.element.XferAddRq[] getXferAddRq() {
        return _xferAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.XferAdviseRq[] _xferAdviseRq;

    /** 
     * Setter for xferAdviseRq
     * @param xferAdviseRq the org.sourceforge.ifx.framework.element.XferAdviseRq[] to set
     */
    public void setXferAdviseRq(org.sourceforge.ifx.framework.element.XferAdviseRq[] _xferAdviseRq) {
        this._xferAdviseRq = _xferAdviseRq;
    }

    /**
     * Getter for xferAdviseRq
     * @return a org.sourceforge.ifx.framework.element.XferAdviseRq[]
     */
    public org.sourceforge.ifx.framework.element.XferAdviseRq[] getXferAdviseRq() {
        return _xferAdviseRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.XferModRq[] _xferModRq;

    /** 
     * Setter for xferModRq
     * @param xferModRq the org.sourceforge.ifx.framework.element.XferModRq[] to set
     */
    public void setXferModRq(org.sourceforge.ifx.framework.element.XferModRq[] _xferModRq) {
        this._xferModRq = _xferModRq;
    }

    /**
     * Getter for xferModRq
     * @return a org.sourceforge.ifx.framework.element.XferModRq[]
     */
    public org.sourceforge.ifx.framework.element.XferModRq[] getXferModRq() {
        return _xferModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.XferStatusModRq[] _xferStatusModRq;

    /** 
     * Setter for xferStatusModRq
     * @param xferStatusModRq the org.sourceforge.ifx.framework.element.XferStatusModRq[] to set
     */
    public void setXferStatusModRq(org.sourceforge.ifx.framework.element.XferStatusModRq[] _xferStatusModRq) {
        this._xferStatusModRq = _xferStatusModRq;
    }

    /**
     * Getter for xferStatusModRq
     * @return a org.sourceforge.ifx.framework.element.XferStatusModRq[]
     */
    public org.sourceforge.ifx.framework.element.XferStatusModRq[] getXferStatusModRq() {
        return _xferStatusModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.XferCanRq[] _xferCanRq;

    /** 
     * Setter for xferCanRq
     * @param xferCanRq the org.sourceforge.ifx.framework.element.XferCanRq[] to set
     */
    public void setXferCanRq(org.sourceforge.ifx.framework.element.XferCanRq[] _xferCanRq) {
        this._xferCanRq = _xferCanRq;
    }

    /**
     * Getter for xferCanRq
     * @return a org.sourceforge.ifx.framework.element.XferCanRq[]
     */
    public org.sourceforge.ifx.framework.element.XferCanRq[] getXferCanRq() {
        return _xferCanRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.XferInqRq[] _xferInqRq;

    /** 
     * Setter for xferInqRq
     * @param xferInqRq the org.sourceforge.ifx.framework.element.XferInqRq[] to set
     */
    public void setXferInqRq(org.sourceforge.ifx.framework.element.XferInqRq[] _xferInqRq) {
        this._xferInqRq = _xferInqRq;
    }

    /**
     * Getter for xferInqRq
     * @return a org.sourceforge.ifx.framework.element.XferInqRq[]
     */
    public org.sourceforge.ifx.framework.element.XferInqRq[] getXferInqRq() {
        return _xferInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.XferAudRq[] _xferAudRq;

    /** 
     * Setter for xferAudRq
     * @param xferAudRq the org.sourceforge.ifx.framework.element.XferAudRq[] to set
     */
    public void setXferAudRq(org.sourceforge.ifx.framework.element.XferAudRq[] _xferAudRq) {
        this._xferAudRq = _xferAudRq;
    }

    /**
     * Getter for xferAudRq
     * @return a org.sourceforge.ifx.framework.element.XferAudRq[]
     */
    public org.sourceforge.ifx.framework.element.XferAudRq[] getXferAudRq() {
        return _xferAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.XferSyncRq[] _xferSyncRq;

    /** 
     * Setter for xferSyncRq
     * @param xferSyncRq the org.sourceforge.ifx.framework.element.XferSyncRq[] to set
     */
    public void setXferSyncRq(org.sourceforge.ifx.framework.element.XferSyncRq[] _xferSyncRq) {
        this._xferSyncRq = _xferSyncRq;
    }

    /**
     * Getter for xferSyncRq
     * @return a org.sourceforge.ifx.framework.element.XferSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.XferSyncRq[] getXferSyncRq() {
        return _xferSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.XferRevRq[] _xferRevRq;

    /** 
     * Setter for xferRevRq
     * @param xferRevRq the org.sourceforge.ifx.framework.element.XferRevRq[] to set
     */
    public void setXferRevRq(org.sourceforge.ifx.framework.element.XferRevRq[] _xferRevRq) {
        this._xferRevRq = _xferRevRq;
    }

    /**
     * Getter for xferRevRq
     * @return a org.sourceforge.ifx.framework.element.XferRevRq[]
     */
    public org.sourceforge.ifx.framework.element.XferRevRq[] getXferRevRq() {
        return _xferRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecXferAddRq[] _recXferAddRq;

    /** 
     * Setter for recXferAddRq
     * @param recXferAddRq the org.sourceforge.ifx.framework.element.RecXferAddRq[] to set
     */
    public void setRecXferAddRq(org.sourceforge.ifx.framework.element.RecXferAddRq[] _recXferAddRq) {
        this._recXferAddRq = _recXferAddRq;
    }

    /**
     * Getter for recXferAddRq
     * @return a org.sourceforge.ifx.framework.element.RecXferAddRq[]
     */
    public org.sourceforge.ifx.framework.element.RecXferAddRq[] getRecXferAddRq() {
        return _recXferAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecXferModRq[] _recXferModRq;

    /** 
     * Setter for recXferModRq
     * @param recXferModRq the org.sourceforge.ifx.framework.element.RecXferModRq[] to set
     */
    public void setRecXferModRq(org.sourceforge.ifx.framework.element.RecXferModRq[] _recXferModRq) {
        this._recXferModRq = _recXferModRq;
    }

    /**
     * Getter for recXferModRq
     * @return a org.sourceforge.ifx.framework.element.RecXferModRq[]
     */
    public org.sourceforge.ifx.framework.element.RecXferModRq[] getRecXferModRq() {
        return _recXferModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecXferCanRq[] _recXferCanRq;

    /** 
     * Setter for recXferCanRq
     * @param recXferCanRq the org.sourceforge.ifx.framework.element.RecXferCanRq[] to set
     */
    public void setRecXferCanRq(org.sourceforge.ifx.framework.element.RecXferCanRq[] _recXferCanRq) {
        this._recXferCanRq = _recXferCanRq;
    }

    /**
     * Getter for recXferCanRq
     * @return a org.sourceforge.ifx.framework.element.RecXferCanRq[]
     */
    public org.sourceforge.ifx.framework.element.RecXferCanRq[] getRecXferCanRq() {
        return _recXferCanRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecXferInqRq[] _recXferInqRq;

    /** 
     * Setter for recXferInqRq
     * @param recXferInqRq the org.sourceforge.ifx.framework.element.RecXferInqRq[] to set
     */
    public void setRecXferInqRq(org.sourceforge.ifx.framework.element.RecXferInqRq[] _recXferInqRq) {
        this._recXferInqRq = _recXferInqRq;
    }

    /**
     * Getter for recXferInqRq
     * @return a org.sourceforge.ifx.framework.element.RecXferInqRq[]
     */
    public org.sourceforge.ifx.framework.element.RecXferInqRq[] getRecXferInqRq() {
        return _recXferInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecXferAudRq[] _recXferAudRq;

    /** 
     * Setter for recXferAudRq
     * @param recXferAudRq the org.sourceforge.ifx.framework.element.RecXferAudRq[] to set
     */
    public void setRecXferAudRq(org.sourceforge.ifx.framework.element.RecXferAudRq[] _recXferAudRq) {
        this._recXferAudRq = _recXferAudRq;
    }

    /**
     * Getter for recXferAudRq
     * @return a org.sourceforge.ifx.framework.element.RecXferAudRq[]
     */
    public org.sourceforge.ifx.framework.element.RecXferAudRq[] getRecXferAudRq() {
        return _recXferAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecXferSyncRq[] _recXferSyncRq;

    /** 
     * Setter for recXferSyncRq
     * @param recXferSyncRq the org.sourceforge.ifx.framework.element.RecXferSyncRq[] to set
     */
    public void setRecXferSyncRq(org.sourceforge.ifx.framework.element.RecXferSyncRq[] _recXferSyncRq) {
        this._recXferSyncRq = _recXferSyncRq;
    }

    /**
     * Getter for recXferSyncRq
     * @return a org.sourceforge.ifx.framework.element.RecXferSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.RecXferSyncRq[] getRecXferSyncRq() {
        return _recXferSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecXferRevRq[] _recXferRevRq;

    /** 
     * Setter for recXferRevRq
     * @param recXferRevRq the org.sourceforge.ifx.framework.element.RecXferRevRq[] to set
     */
    public void setRecXferRevRq(org.sourceforge.ifx.framework.element.RecXferRevRq[] _recXferRevRq) {
        this._recXferRevRq = _recXferRevRq;
    }

    /**
     * Getter for recXferRevRq
     * @return a org.sourceforge.ifx.framework.element.RecXferRevRq[]
     */
    public org.sourceforge.ifx.framework.element.RecXferRevRq[] getRecXferRevRq() {
        return _recXferRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkOrdAddRq[] _chkOrdAddRq;

    /** 
     * Setter for chkOrdAddRq
     * @param chkOrdAddRq the org.sourceforge.ifx.framework.element.ChkOrdAddRq[] to set
     */
    public void setChkOrdAddRq(org.sourceforge.ifx.framework.element.ChkOrdAddRq[] _chkOrdAddRq) {
        this._chkOrdAddRq = _chkOrdAddRq;
    }

    /**
     * Getter for chkOrdAddRq
     * @return a org.sourceforge.ifx.framework.element.ChkOrdAddRq[]
     */
    public org.sourceforge.ifx.framework.element.ChkOrdAddRq[] getChkOrdAddRq() {
        return _chkOrdAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkOrdAdviseRq[] _chkOrdAdviseRq;

    /** 
     * Setter for chkOrdAdviseRq
     * @param chkOrdAdviseRq the org.sourceforge.ifx.framework.element.ChkOrdAdviseRq[] to set
     */
    public void setChkOrdAdviseRq(org.sourceforge.ifx.framework.element.ChkOrdAdviseRq[] _chkOrdAdviseRq) {
        this._chkOrdAdviseRq = _chkOrdAdviseRq;
    }

    /**
     * Getter for chkOrdAdviseRq
     * @return a org.sourceforge.ifx.framework.element.ChkOrdAdviseRq[]
     */
    public org.sourceforge.ifx.framework.element.ChkOrdAdviseRq[] getChkOrdAdviseRq() {
        return _chkOrdAdviseRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkOrdModRq[] _chkOrdModRq;

    /** 
     * Setter for chkOrdModRq
     * @param chkOrdModRq the org.sourceforge.ifx.framework.element.ChkOrdModRq[] to set
     */
    public void setChkOrdModRq(org.sourceforge.ifx.framework.element.ChkOrdModRq[] _chkOrdModRq) {
        this._chkOrdModRq = _chkOrdModRq;
    }

    /**
     * Getter for chkOrdModRq
     * @return a org.sourceforge.ifx.framework.element.ChkOrdModRq[]
     */
    public org.sourceforge.ifx.framework.element.ChkOrdModRq[] getChkOrdModRq() {
        return _chkOrdModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkOrdCanRq[] _chkOrdCanRq;

    /** 
     * Setter for chkOrdCanRq
     * @param chkOrdCanRq the org.sourceforge.ifx.framework.element.ChkOrdCanRq[] to set
     */
    public void setChkOrdCanRq(org.sourceforge.ifx.framework.element.ChkOrdCanRq[] _chkOrdCanRq) {
        this._chkOrdCanRq = _chkOrdCanRq;
    }

    /**
     * Getter for chkOrdCanRq
     * @return a org.sourceforge.ifx.framework.element.ChkOrdCanRq[]
     */
    public org.sourceforge.ifx.framework.element.ChkOrdCanRq[] getChkOrdCanRq() {
        return _chkOrdCanRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkOrdInqRq[] _chkOrdInqRq;

    /** 
     * Setter for chkOrdInqRq
     * @param chkOrdInqRq the org.sourceforge.ifx.framework.element.ChkOrdInqRq[] to set
     */
    public void setChkOrdInqRq(org.sourceforge.ifx.framework.element.ChkOrdInqRq[] _chkOrdInqRq) {
        this._chkOrdInqRq = _chkOrdInqRq;
    }

    /**
     * Getter for chkOrdInqRq
     * @return a org.sourceforge.ifx.framework.element.ChkOrdInqRq[]
     */
    public org.sourceforge.ifx.framework.element.ChkOrdInqRq[] getChkOrdInqRq() {
        return _chkOrdInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkOrdAudRq[] _chkOrdAudRq;

    /** 
     * Setter for chkOrdAudRq
     * @param chkOrdAudRq the org.sourceforge.ifx.framework.element.ChkOrdAudRq[] to set
     */
    public void setChkOrdAudRq(org.sourceforge.ifx.framework.element.ChkOrdAudRq[] _chkOrdAudRq) {
        this._chkOrdAudRq = _chkOrdAudRq;
    }

    /**
     * Getter for chkOrdAudRq
     * @return a org.sourceforge.ifx.framework.element.ChkOrdAudRq[]
     */
    public org.sourceforge.ifx.framework.element.ChkOrdAudRq[] getChkOrdAudRq() {
        return _chkOrdAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkOrdSyncRq[] _chkOrdSyncRq;

    /** 
     * Setter for chkOrdSyncRq
     * @param chkOrdSyncRq the org.sourceforge.ifx.framework.element.ChkOrdSyncRq[] to set
     */
    public void setChkOrdSyncRq(org.sourceforge.ifx.framework.element.ChkOrdSyncRq[] _chkOrdSyncRq) {
        this._chkOrdSyncRq = _chkOrdSyncRq;
    }

    /**
     * Getter for chkOrdSyncRq
     * @return a org.sourceforge.ifx.framework.element.ChkOrdSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.ChkOrdSyncRq[] getChkOrdSyncRq() {
        return _chkOrdSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkOrdRevRq[] _chkOrdRevRq;

    /** 
     * Setter for chkOrdRevRq
     * @param chkOrdRevRq the org.sourceforge.ifx.framework.element.ChkOrdRevRq[] to set
     */
    public void setChkOrdRevRq(org.sourceforge.ifx.framework.element.ChkOrdRevRq[] _chkOrdRevRq) {
        this._chkOrdRevRq = _chkOrdRevRq;
    }

    /**
     * Getter for chkOrdRevRq
     * @return a org.sourceforge.ifx.framework.element.ChkOrdRevRq[]
     */
    public org.sourceforge.ifx.framework.element.ChkOrdRevRq[] getChkOrdRevRq() {
        return _chkOrdRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecChkOrdAddRq[] _recChkOrdAddRq;

    /** 
     * Setter for recChkOrdAddRq
     * @param recChkOrdAddRq the org.sourceforge.ifx.framework.element.RecChkOrdAddRq[] to set
     */
    public void setRecChkOrdAddRq(org.sourceforge.ifx.framework.element.RecChkOrdAddRq[] _recChkOrdAddRq) {
        this._recChkOrdAddRq = _recChkOrdAddRq;
    }

    /**
     * Getter for recChkOrdAddRq
     * @return a org.sourceforge.ifx.framework.element.RecChkOrdAddRq[]
     */
    public org.sourceforge.ifx.framework.element.RecChkOrdAddRq[] getRecChkOrdAddRq() {
        return _recChkOrdAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecChkOrdModRq[] _recChkOrdModRq;

    /** 
     * Setter for recChkOrdModRq
     * @param recChkOrdModRq the org.sourceforge.ifx.framework.element.RecChkOrdModRq[] to set
     */
    public void setRecChkOrdModRq(org.sourceforge.ifx.framework.element.RecChkOrdModRq[] _recChkOrdModRq) {
        this._recChkOrdModRq = _recChkOrdModRq;
    }

    /**
     * Getter for recChkOrdModRq
     * @return a org.sourceforge.ifx.framework.element.RecChkOrdModRq[]
     */
    public org.sourceforge.ifx.framework.element.RecChkOrdModRq[] getRecChkOrdModRq() {
        return _recChkOrdModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecChkOrdCanRq[] _recChkOrdCanRq;

    /** 
     * Setter for recChkOrdCanRq
     * @param recChkOrdCanRq the org.sourceforge.ifx.framework.element.RecChkOrdCanRq[] to set
     */
    public void setRecChkOrdCanRq(org.sourceforge.ifx.framework.element.RecChkOrdCanRq[] _recChkOrdCanRq) {
        this._recChkOrdCanRq = _recChkOrdCanRq;
    }

    /**
     * Getter for recChkOrdCanRq
     * @return a org.sourceforge.ifx.framework.element.RecChkOrdCanRq[]
     */
    public org.sourceforge.ifx.framework.element.RecChkOrdCanRq[] getRecChkOrdCanRq() {
        return _recChkOrdCanRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecChkOrdInqRq[] _recChkOrdInqRq;

    /** 
     * Setter for recChkOrdInqRq
     * @param recChkOrdInqRq the org.sourceforge.ifx.framework.element.RecChkOrdInqRq[] to set
     */
    public void setRecChkOrdInqRq(org.sourceforge.ifx.framework.element.RecChkOrdInqRq[] _recChkOrdInqRq) {
        this._recChkOrdInqRq = _recChkOrdInqRq;
    }

    /**
     * Getter for recChkOrdInqRq
     * @return a org.sourceforge.ifx.framework.element.RecChkOrdInqRq[]
     */
    public org.sourceforge.ifx.framework.element.RecChkOrdInqRq[] getRecChkOrdInqRq() {
        return _recChkOrdInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecChkOrdAudRq[] _recChkOrdAudRq;

    /** 
     * Setter for recChkOrdAudRq
     * @param recChkOrdAudRq the org.sourceforge.ifx.framework.element.RecChkOrdAudRq[] to set
     */
    public void setRecChkOrdAudRq(org.sourceforge.ifx.framework.element.RecChkOrdAudRq[] _recChkOrdAudRq) {
        this._recChkOrdAudRq = _recChkOrdAudRq;
    }

    /**
     * Getter for recChkOrdAudRq
     * @return a org.sourceforge.ifx.framework.element.RecChkOrdAudRq[]
     */
    public org.sourceforge.ifx.framework.element.RecChkOrdAudRq[] getRecChkOrdAudRq() {
        return _recChkOrdAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecChkOrdSyncRq[] _recChkOrdSyncRq;

    /** 
     * Setter for recChkOrdSyncRq
     * @param recChkOrdSyncRq the org.sourceforge.ifx.framework.element.RecChkOrdSyncRq[] to set
     */
    public void setRecChkOrdSyncRq(org.sourceforge.ifx.framework.element.RecChkOrdSyncRq[] _recChkOrdSyncRq) {
        this._recChkOrdSyncRq = _recChkOrdSyncRq;
    }

    /**
     * Getter for recChkOrdSyncRq
     * @return a org.sourceforge.ifx.framework.element.RecChkOrdSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.RecChkOrdSyncRq[] getRecChkOrdSyncRq() {
        return _recChkOrdSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecChkOrdInstAddRq[] _recChkOrdInstAddRq;

    /** 
     * Setter for recChkOrdInstAddRq
     * @param recChkOrdInstAddRq the org.sourceforge.ifx.framework.element.RecChkOrdInstAddRq[] to set
     */
    public void setRecChkOrdInstAddRq(org.sourceforge.ifx.framework.element.RecChkOrdInstAddRq[] _recChkOrdInstAddRq) {
        this._recChkOrdInstAddRq = _recChkOrdInstAddRq;
    }

    /**
     * Getter for recChkOrdInstAddRq
     * @return a org.sourceforge.ifx.framework.element.RecChkOrdInstAddRq[]
     */
    public org.sourceforge.ifx.framework.element.RecChkOrdInstAddRq[] getRecChkOrdInstAddRq() {
        return _recChkOrdInstAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepBkOrdAddRq[] _depBkOrdAddRq;

    /** 
     * Setter for depBkOrdAddRq
     * @param depBkOrdAddRq the org.sourceforge.ifx.framework.element.DepBkOrdAddRq[] to set
     */
    public void setDepBkOrdAddRq(org.sourceforge.ifx.framework.element.DepBkOrdAddRq[] _depBkOrdAddRq) {
        this._depBkOrdAddRq = _depBkOrdAddRq;
    }

    /**
     * Getter for depBkOrdAddRq
     * @return a org.sourceforge.ifx.framework.element.DepBkOrdAddRq[]
     */
    public org.sourceforge.ifx.framework.element.DepBkOrdAddRq[] getDepBkOrdAddRq() {
        return _depBkOrdAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepBkOrdAdviseRq[] _depBkOrdAdviseRq;

    /** 
     * Setter for depBkOrdAdviseRq
     * @param depBkOrdAdviseRq the org.sourceforge.ifx.framework.element.DepBkOrdAdviseRq[] to set
     */
    public void setDepBkOrdAdviseRq(org.sourceforge.ifx.framework.element.DepBkOrdAdviseRq[] _depBkOrdAdviseRq) {
        this._depBkOrdAdviseRq = _depBkOrdAdviseRq;
    }

    /**
     * Getter for depBkOrdAdviseRq
     * @return a org.sourceforge.ifx.framework.element.DepBkOrdAdviseRq[]
     */
    public org.sourceforge.ifx.framework.element.DepBkOrdAdviseRq[] getDepBkOrdAdviseRq() {
        return _depBkOrdAdviseRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepBkOrdRevRq[] _depBkOrdRevRq;

    /** 
     * Setter for depBkOrdRevRq
     * @param depBkOrdRevRq the org.sourceforge.ifx.framework.element.DepBkOrdRevRq[] to set
     */
    public void setDepBkOrdRevRq(org.sourceforge.ifx.framework.element.DepBkOrdRevRq[] _depBkOrdRevRq) {
        this._depBkOrdRevRq = _depBkOrdRevRq;
    }

    /**
     * Getter for depBkOrdRevRq
     * @return a org.sourceforge.ifx.framework.element.DepBkOrdRevRq[]
     */
    public org.sourceforge.ifx.framework.element.DepBkOrdRevRq[] getDepBkOrdRevRq() {
        return _depBkOrdRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitAuthAddRq[] _debitAuthAddRq;

    /** 
     * Setter for debitAuthAddRq
     * @param debitAuthAddRq the org.sourceforge.ifx.framework.element.DebitAuthAddRq[] to set
     */
    public void setDebitAuthAddRq(org.sourceforge.ifx.framework.element.DebitAuthAddRq[] _debitAuthAddRq) {
        this._debitAuthAddRq = _debitAuthAddRq;
    }

    /**
     * Getter for debitAuthAddRq
     * @return a org.sourceforge.ifx.framework.element.DebitAuthAddRq[]
     */
    public org.sourceforge.ifx.framework.element.DebitAuthAddRq[] getDebitAuthAddRq() {
        return _debitAuthAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitAuthModRq[] _debitAuthModRq;

    /** 
     * Setter for debitAuthModRq
     * @param debitAuthModRq the org.sourceforge.ifx.framework.element.DebitAuthModRq[] to set
     */
    public void setDebitAuthModRq(org.sourceforge.ifx.framework.element.DebitAuthModRq[] _debitAuthModRq) {
        this._debitAuthModRq = _debitAuthModRq;
    }

    /**
     * Getter for debitAuthModRq
     * @return a org.sourceforge.ifx.framework.element.DebitAuthModRq[]
     */
    public org.sourceforge.ifx.framework.element.DebitAuthModRq[] getDebitAuthModRq() {
        return _debitAuthModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitAuthCanRq[] _debitAuthCanRq;

    /** 
     * Setter for debitAuthCanRq
     * @param debitAuthCanRq the org.sourceforge.ifx.framework.element.DebitAuthCanRq[] to set
     */
    public void setDebitAuthCanRq(org.sourceforge.ifx.framework.element.DebitAuthCanRq[] _debitAuthCanRq) {
        this._debitAuthCanRq = _debitAuthCanRq;
    }

    /**
     * Getter for debitAuthCanRq
     * @return a org.sourceforge.ifx.framework.element.DebitAuthCanRq[]
     */
    public org.sourceforge.ifx.framework.element.DebitAuthCanRq[] getDebitAuthCanRq() {
        return _debitAuthCanRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitAuthInqRq[] _debitAuthInqRq;

    /** 
     * Setter for debitAuthInqRq
     * @param debitAuthInqRq the org.sourceforge.ifx.framework.element.DebitAuthInqRq[] to set
     */
    public void setDebitAuthInqRq(org.sourceforge.ifx.framework.element.DebitAuthInqRq[] _debitAuthInqRq) {
        this._debitAuthInqRq = _debitAuthInqRq;
    }

    /**
     * Getter for debitAuthInqRq
     * @return a org.sourceforge.ifx.framework.element.DebitAuthInqRq[]
     */
    public org.sourceforge.ifx.framework.element.DebitAuthInqRq[] getDebitAuthInqRq() {
        return _debitAuthInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitAuthAudRq[] _debitAuthAudRq;

    /** 
     * Setter for debitAuthAudRq
     * @param debitAuthAudRq the org.sourceforge.ifx.framework.element.DebitAuthAudRq[] to set
     */
    public void setDebitAuthAudRq(org.sourceforge.ifx.framework.element.DebitAuthAudRq[] _debitAuthAudRq) {
        this._debitAuthAudRq = _debitAuthAudRq;
    }

    /**
     * Getter for debitAuthAudRq
     * @return a org.sourceforge.ifx.framework.element.DebitAuthAudRq[]
     */
    public org.sourceforge.ifx.framework.element.DebitAuthAudRq[] getDebitAuthAudRq() {
        return _debitAuthAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitAuthSyncRq[] _debitAuthSyncRq;

    /** 
     * Setter for debitAuthSyncRq
     * @param debitAuthSyncRq the org.sourceforge.ifx.framework.element.DebitAuthSyncRq[] to set
     */
    public void setDebitAuthSyncRq(org.sourceforge.ifx.framework.element.DebitAuthSyncRq[] _debitAuthSyncRq) {
        this._debitAuthSyncRq = _debitAuthSyncRq;
    }

    /**
     * Getter for debitAuthSyncRq
     * @return a org.sourceforge.ifx.framework.element.DebitAuthSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.DebitAuthSyncRq[] getDebitAuthSyncRq() {
        return _debitAuthSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitAuthStatusModRq[] _debitAuthStatusModRq;

    /** 
     * Setter for debitAuthStatusModRq
     * @param debitAuthStatusModRq the org.sourceforge.ifx.framework.element.DebitAuthStatusModRq[] to set
     */
    public void setDebitAuthStatusModRq(org.sourceforge.ifx.framework.element.DebitAuthStatusModRq[] _debitAuthStatusModRq) {
        this._debitAuthStatusModRq = _debitAuthStatusModRq;
    }

    /**
     * Getter for debitAuthStatusModRq
     * @return a org.sourceforge.ifx.framework.element.DebitAuthStatusModRq[]
     */
    public org.sourceforge.ifx.framework.element.DebitAuthStatusModRq[] getDebitAuthStatusModRq() {
        return _debitAuthStatusModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitAuthAdviseRq[] _debitAuthAdviseRq;

    /** 
     * Setter for debitAuthAdviseRq
     * @param debitAuthAdviseRq the org.sourceforge.ifx.framework.element.DebitAuthAdviseRq[] to set
     */
    public void setDebitAuthAdviseRq(org.sourceforge.ifx.framework.element.DebitAuthAdviseRq[] _debitAuthAdviseRq) {
        this._debitAuthAdviseRq = _debitAuthAdviseRq;
    }

    /**
     * Getter for debitAuthAdviseRq
     * @return a org.sourceforge.ifx.framework.element.DebitAuthAdviseRq[]
     */
    public org.sourceforge.ifx.framework.element.DebitAuthAdviseRq[] getDebitAuthAdviseRq() {
        return _debitAuthAdviseRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitAuthRevRq[] _debitAuthRevRq;

    /** 
     * Setter for debitAuthRevRq
     * @param debitAuthRevRq the org.sourceforge.ifx.framework.element.DebitAuthRevRq[] to set
     */
    public void setDebitAuthRevRq(org.sourceforge.ifx.framework.element.DebitAuthRevRq[] _debitAuthRevRq) {
        this._debitAuthRevRq = _debitAuthRevRq;
    }

    /**
     * Getter for debitAuthRevRq
     * @return a org.sourceforge.ifx.framework.element.DebitAuthRevRq[]
     */
    public org.sourceforge.ifx.framework.element.DebitAuthRevRq[] getDebitAuthRevRq() {
        return _debitAuthRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitAddRq[] _debitAddRq;

    /** 
     * Setter for debitAddRq
     * @param debitAddRq the org.sourceforge.ifx.framework.element.DebitAddRq[] to set
     */
    public void setDebitAddRq(org.sourceforge.ifx.framework.element.DebitAddRq[] _debitAddRq) {
        this._debitAddRq = _debitAddRq;
    }

    /**
     * Getter for debitAddRq
     * @return a org.sourceforge.ifx.framework.element.DebitAddRq[]
     */
    public org.sourceforge.ifx.framework.element.DebitAddRq[] getDebitAddRq() {
        return _debitAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitModRq[] _debitModRq;

    /** 
     * Setter for debitModRq
     * @param debitModRq the org.sourceforge.ifx.framework.element.DebitModRq[] to set
     */
    public void setDebitModRq(org.sourceforge.ifx.framework.element.DebitModRq[] _debitModRq) {
        this._debitModRq = _debitModRq;
    }

    /**
     * Getter for debitModRq
     * @return a org.sourceforge.ifx.framework.element.DebitModRq[]
     */
    public org.sourceforge.ifx.framework.element.DebitModRq[] getDebitModRq() {
        return _debitModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitCanRq[] _debitCanRq;

    /** 
     * Setter for debitCanRq
     * @param debitCanRq the org.sourceforge.ifx.framework.element.DebitCanRq[] to set
     */
    public void setDebitCanRq(org.sourceforge.ifx.framework.element.DebitCanRq[] _debitCanRq) {
        this._debitCanRq = _debitCanRq;
    }

    /**
     * Getter for debitCanRq
     * @return a org.sourceforge.ifx.framework.element.DebitCanRq[]
     */
    public org.sourceforge.ifx.framework.element.DebitCanRq[] getDebitCanRq() {
        return _debitCanRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitInqRq[] _debitInqRq;

    /** 
     * Setter for debitInqRq
     * @param debitInqRq the org.sourceforge.ifx.framework.element.DebitInqRq[] to set
     */
    public void setDebitInqRq(org.sourceforge.ifx.framework.element.DebitInqRq[] _debitInqRq) {
        this._debitInqRq = _debitInqRq;
    }

    /**
     * Getter for debitInqRq
     * @return a org.sourceforge.ifx.framework.element.DebitInqRq[]
     */
    public org.sourceforge.ifx.framework.element.DebitInqRq[] getDebitInqRq() {
        return _debitInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitAudRq[] _debitAudRq;

    /** 
     * Setter for debitAudRq
     * @param debitAudRq the org.sourceforge.ifx.framework.element.DebitAudRq[] to set
     */
    public void setDebitAudRq(org.sourceforge.ifx.framework.element.DebitAudRq[] _debitAudRq) {
        this._debitAudRq = _debitAudRq;
    }

    /**
     * Getter for debitAudRq
     * @return a org.sourceforge.ifx.framework.element.DebitAudRq[]
     */
    public org.sourceforge.ifx.framework.element.DebitAudRq[] getDebitAudRq() {
        return _debitAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitSyncRq[] _debitSyncRq;

    /** 
     * Setter for debitSyncRq
     * @param debitSyncRq the org.sourceforge.ifx.framework.element.DebitSyncRq[] to set
     */
    public void setDebitSyncRq(org.sourceforge.ifx.framework.element.DebitSyncRq[] _debitSyncRq) {
        this._debitSyncRq = _debitSyncRq;
    }

    /**
     * Getter for debitSyncRq
     * @return a org.sourceforge.ifx.framework.element.DebitSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.DebitSyncRq[] getDebitSyncRq() {
        return _debitSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitStatusModRq[] _debitStatusModRq;

    /** 
     * Setter for debitStatusModRq
     * @param debitStatusModRq the org.sourceforge.ifx.framework.element.DebitStatusModRq[] to set
     */
    public void setDebitStatusModRq(org.sourceforge.ifx.framework.element.DebitStatusModRq[] _debitStatusModRq) {
        this._debitStatusModRq = _debitStatusModRq;
    }

    /**
     * Getter for debitStatusModRq
     * @return a org.sourceforge.ifx.framework.element.DebitStatusModRq[]
     */
    public org.sourceforge.ifx.framework.element.DebitStatusModRq[] getDebitStatusModRq() {
        return _debitStatusModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitAdviseRq[] _debitAdviseRq;

    /** 
     * Setter for debitAdviseRq
     * @param debitAdviseRq the org.sourceforge.ifx.framework.element.DebitAdviseRq[] to set
     */
    public void setDebitAdviseRq(org.sourceforge.ifx.framework.element.DebitAdviseRq[] _debitAdviseRq) {
        this._debitAdviseRq = _debitAdviseRq;
    }

    /**
     * Getter for debitAdviseRq
     * @return a org.sourceforge.ifx.framework.element.DebitAdviseRq[]
     */
    public org.sourceforge.ifx.framework.element.DebitAdviseRq[] getDebitAdviseRq() {
        return _debitAdviseRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitRevRq[] _debitRevRq;

    /** 
     * Setter for debitRevRq
     * @param debitRevRq the org.sourceforge.ifx.framework.element.DebitRevRq[] to set
     */
    public void setDebitRevRq(org.sourceforge.ifx.framework.element.DebitRevRq[] _debitRevRq) {
        this._debitRevRq = _debitRevRq;
    }

    /**
     * Getter for debitRevRq
     * @return a org.sourceforge.ifx.framework.element.DebitRevRq[]
     */
    public org.sourceforge.ifx.framework.element.DebitRevRq[] getDebitRevRq() {
        return _debitRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAuthAddRq[] _creditAuthAddRq;

    /** 
     * Setter for creditAuthAddRq
     * @param creditAuthAddRq the org.sourceforge.ifx.framework.element.CreditAuthAddRq[] to set
     */
    public void setCreditAuthAddRq(org.sourceforge.ifx.framework.element.CreditAuthAddRq[] _creditAuthAddRq) {
        this._creditAuthAddRq = _creditAuthAddRq;
    }

    /**
     * Getter for creditAuthAddRq
     * @return a org.sourceforge.ifx.framework.element.CreditAuthAddRq[]
     */
    public org.sourceforge.ifx.framework.element.CreditAuthAddRq[] getCreditAuthAddRq() {
        return _creditAuthAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAuthAdviseRq[] _creditAuthAdviseRq;

    /** 
     * Setter for creditAuthAdviseRq
     * @param creditAuthAdviseRq the org.sourceforge.ifx.framework.element.CreditAuthAdviseRq[] to set
     */
    public void setCreditAuthAdviseRq(org.sourceforge.ifx.framework.element.CreditAuthAdviseRq[] _creditAuthAdviseRq) {
        this._creditAuthAdviseRq = _creditAuthAdviseRq;
    }

    /**
     * Getter for creditAuthAdviseRq
     * @return a org.sourceforge.ifx.framework.element.CreditAuthAdviseRq[]
     */
    public org.sourceforge.ifx.framework.element.CreditAuthAdviseRq[] getCreditAuthAdviseRq() {
        return _creditAuthAdviseRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAuthModRq[] _creditAuthModRq;

    /** 
     * Setter for creditAuthModRq
     * @param creditAuthModRq the org.sourceforge.ifx.framework.element.CreditAuthModRq[] to set
     */
    public void setCreditAuthModRq(org.sourceforge.ifx.framework.element.CreditAuthModRq[] _creditAuthModRq) {
        this._creditAuthModRq = _creditAuthModRq;
    }

    /**
     * Getter for creditAuthModRq
     * @return a org.sourceforge.ifx.framework.element.CreditAuthModRq[]
     */
    public org.sourceforge.ifx.framework.element.CreditAuthModRq[] getCreditAuthModRq() {
        return _creditAuthModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAuthCanRq[] _creditAuthCanRq;

    /** 
     * Setter for creditAuthCanRq
     * @param creditAuthCanRq the org.sourceforge.ifx.framework.element.CreditAuthCanRq[] to set
     */
    public void setCreditAuthCanRq(org.sourceforge.ifx.framework.element.CreditAuthCanRq[] _creditAuthCanRq) {
        this._creditAuthCanRq = _creditAuthCanRq;
    }

    /**
     * Getter for creditAuthCanRq
     * @return a org.sourceforge.ifx.framework.element.CreditAuthCanRq[]
     */
    public org.sourceforge.ifx.framework.element.CreditAuthCanRq[] getCreditAuthCanRq() {
        return _creditAuthCanRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAuthInqRq[] _creditAuthInqRq;

    /** 
     * Setter for creditAuthInqRq
     * @param creditAuthInqRq the org.sourceforge.ifx.framework.element.CreditAuthInqRq[] to set
     */
    public void setCreditAuthInqRq(org.sourceforge.ifx.framework.element.CreditAuthInqRq[] _creditAuthInqRq) {
        this._creditAuthInqRq = _creditAuthInqRq;
    }

    /**
     * Getter for creditAuthInqRq
     * @return a org.sourceforge.ifx.framework.element.CreditAuthInqRq[]
     */
    public org.sourceforge.ifx.framework.element.CreditAuthInqRq[] getCreditAuthInqRq() {
        return _creditAuthInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAuthAudRq[] _creditAuthAudRq;

    /** 
     * Setter for creditAuthAudRq
     * @param creditAuthAudRq the org.sourceforge.ifx.framework.element.CreditAuthAudRq[] to set
     */
    public void setCreditAuthAudRq(org.sourceforge.ifx.framework.element.CreditAuthAudRq[] _creditAuthAudRq) {
        this._creditAuthAudRq = _creditAuthAudRq;
    }

    /**
     * Getter for creditAuthAudRq
     * @return a org.sourceforge.ifx.framework.element.CreditAuthAudRq[]
     */
    public org.sourceforge.ifx.framework.element.CreditAuthAudRq[] getCreditAuthAudRq() {
        return _creditAuthAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAuthSyncRq[] _creditAuthSyncRq;

    /** 
     * Setter for creditAuthSyncRq
     * @param creditAuthSyncRq the org.sourceforge.ifx.framework.element.CreditAuthSyncRq[] to set
     */
    public void setCreditAuthSyncRq(org.sourceforge.ifx.framework.element.CreditAuthSyncRq[] _creditAuthSyncRq) {
        this._creditAuthSyncRq = _creditAuthSyncRq;
    }

    /**
     * Getter for creditAuthSyncRq
     * @return a org.sourceforge.ifx.framework.element.CreditAuthSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.CreditAuthSyncRq[] getCreditAuthSyncRq() {
        return _creditAuthSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAuthRevRq[] _creditAuthRevRq;

    /** 
     * Setter for creditAuthRevRq
     * @param creditAuthRevRq the org.sourceforge.ifx.framework.element.CreditAuthRevRq[] to set
     */
    public void setCreditAuthRevRq(org.sourceforge.ifx.framework.element.CreditAuthRevRq[] _creditAuthRevRq) {
        this._creditAuthRevRq = _creditAuthRevRq;
    }

    /**
     * Getter for creditAuthRevRq
     * @return a org.sourceforge.ifx.framework.element.CreditAuthRevRq[]
     */
    public org.sourceforge.ifx.framework.element.CreditAuthRevRq[] getCreditAuthRevRq() {
        return _creditAuthRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAuthStatusModRq[] _creditAuthStatusModRq;

    /** 
     * Setter for creditAuthStatusModRq
     * @param creditAuthStatusModRq the org.sourceforge.ifx.framework.element.CreditAuthStatusModRq[] to set
     */
    public void setCreditAuthStatusModRq(org.sourceforge.ifx.framework.element.CreditAuthStatusModRq[] _creditAuthStatusModRq) {
        this._creditAuthStatusModRq = _creditAuthStatusModRq;
    }

    /**
     * Getter for creditAuthStatusModRq
     * @return a org.sourceforge.ifx.framework.element.CreditAuthStatusModRq[]
     */
    public org.sourceforge.ifx.framework.element.CreditAuthStatusModRq[] getCreditAuthStatusModRq() {
        return _creditAuthStatusModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAddRq[] _creditAddRq;

    /** 
     * Setter for creditAddRq
     * @param creditAddRq the org.sourceforge.ifx.framework.element.CreditAddRq[] to set
     */
    public void setCreditAddRq(org.sourceforge.ifx.framework.element.CreditAddRq[] _creditAddRq) {
        this._creditAddRq = _creditAddRq;
    }

    /**
     * Getter for creditAddRq
     * @return a org.sourceforge.ifx.framework.element.CreditAddRq[]
     */
    public org.sourceforge.ifx.framework.element.CreditAddRq[] getCreditAddRq() {
        return _creditAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditModRq[] _creditModRq;

    /** 
     * Setter for creditModRq
     * @param creditModRq the org.sourceforge.ifx.framework.element.CreditModRq[] to set
     */
    public void setCreditModRq(org.sourceforge.ifx.framework.element.CreditModRq[] _creditModRq) {
        this._creditModRq = _creditModRq;
    }

    /**
     * Getter for creditModRq
     * @return a org.sourceforge.ifx.framework.element.CreditModRq[]
     */
    public org.sourceforge.ifx.framework.element.CreditModRq[] getCreditModRq() {
        return _creditModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditCanRq[] _creditCanRq;

    /** 
     * Setter for creditCanRq
     * @param creditCanRq the org.sourceforge.ifx.framework.element.CreditCanRq[] to set
     */
    public void setCreditCanRq(org.sourceforge.ifx.framework.element.CreditCanRq[] _creditCanRq) {
        this._creditCanRq = _creditCanRq;
    }

    /**
     * Getter for creditCanRq
     * @return a org.sourceforge.ifx.framework.element.CreditCanRq[]
     */
    public org.sourceforge.ifx.framework.element.CreditCanRq[] getCreditCanRq() {
        return _creditCanRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditInqRq[] _creditInqRq;

    /** 
     * Setter for creditInqRq
     * @param creditInqRq the org.sourceforge.ifx.framework.element.CreditInqRq[] to set
     */
    public void setCreditInqRq(org.sourceforge.ifx.framework.element.CreditInqRq[] _creditInqRq) {
        this._creditInqRq = _creditInqRq;
    }

    /**
     * Getter for creditInqRq
     * @return a org.sourceforge.ifx.framework.element.CreditInqRq[]
     */
    public org.sourceforge.ifx.framework.element.CreditInqRq[] getCreditInqRq() {
        return _creditInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAudRq[] _creditAudRq;

    /** 
     * Setter for creditAudRq
     * @param creditAudRq the org.sourceforge.ifx.framework.element.CreditAudRq[] to set
     */
    public void setCreditAudRq(org.sourceforge.ifx.framework.element.CreditAudRq[] _creditAudRq) {
        this._creditAudRq = _creditAudRq;
    }

    /**
     * Getter for creditAudRq
     * @return a org.sourceforge.ifx.framework.element.CreditAudRq[]
     */
    public org.sourceforge.ifx.framework.element.CreditAudRq[] getCreditAudRq() {
        return _creditAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditSyncRq[] _creditSyncRq;

    /** 
     * Setter for creditSyncRq
     * @param creditSyncRq the org.sourceforge.ifx.framework.element.CreditSyncRq[] to set
     */
    public void setCreditSyncRq(org.sourceforge.ifx.framework.element.CreditSyncRq[] _creditSyncRq) {
        this._creditSyncRq = _creditSyncRq;
    }

    /**
     * Getter for creditSyncRq
     * @return a org.sourceforge.ifx.framework.element.CreditSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.CreditSyncRq[] getCreditSyncRq() {
        return _creditSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAdviseRq[] _creditAdviseRq;

    /** 
     * Setter for creditAdviseRq
     * @param creditAdviseRq the org.sourceforge.ifx.framework.element.CreditAdviseRq[] to set
     */
    public void setCreditAdviseRq(org.sourceforge.ifx.framework.element.CreditAdviseRq[] _creditAdviseRq) {
        this._creditAdviseRq = _creditAdviseRq;
    }

    /**
     * Getter for creditAdviseRq
     * @return a org.sourceforge.ifx.framework.element.CreditAdviseRq[]
     */
    public org.sourceforge.ifx.framework.element.CreditAdviseRq[] getCreditAdviseRq() {
        return _creditAdviseRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditStatusModRq[] _creditStatusModRq;

    /** 
     * Setter for creditStatusModRq
     * @param creditStatusModRq the org.sourceforge.ifx.framework.element.CreditStatusModRq[] to set
     */
    public void setCreditStatusModRq(org.sourceforge.ifx.framework.element.CreditStatusModRq[] _creditStatusModRq) {
        this._creditStatusModRq = _creditStatusModRq;
    }

    /**
     * Getter for creditStatusModRq
     * @return a org.sourceforge.ifx.framework.element.CreditStatusModRq[]
     */
    public org.sourceforge.ifx.framework.element.CreditStatusModRq[] getCreditStatusModRq() {
        return _creditStatusModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditRevRq[] _creditRevRq;

    /** 
     * Setter for creditRevRq
     * @param creditRevRq the org.sourceforge.ifx.framework.element.CreditRevRq[] to set
     */
    public void setCreditRevRq(org.sourceforge.ifx.framework.element.CreditRevRq[] _creditRevRq) {
        this._creditRevRq = _creditRevRq;
    }

    /**
     * Getter for creditRevRq
     * @return a org.sourceforge.ifx.framework.element.CreditRevRq[]
     */
    public org.sourceforge.ifx.framework.element.CreditRevRq[] getCreditRevRq() {
        return _creditRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAppAddRq[] _depAppAddRq;

    /** 
     * Setter for depAppAddRq
     * @param depAppAddRq the org.sourceforge.ifx.framework.element.DepAppAddRq[] to set
     */
    public void setDepAppAddRq(org.sourceforge.ifx.framework.element.DepAppAddRq[] _depAppAddRq) {
        this._depAppAddRq = _depAppAddRq;
    }

    /**
     * Getter for depAppAddRq
     * @return a org.sourceforge.ifx.framework.element.DepAppAddRq[]
     */
    public org.sourceforge.ifx.framework.element.DepAppAddRq[] getDepAppAddRq() {
        return _depAppAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAppModRq[] _depAppModRq;

    /** 
     * Setter for depAppModRq
     * @param depAppModRq the org.sourceforge.ifx.framework.element.DepAppModRq[] to set
     */
    public void setDepAppModRq(org.sourceforge.ifx.framework.element.DepAppModRq[] _depAppModRq) {
        this._depAppModRq = _depAppModRq;
    }

    /**
     * Getter for depAppModRq
     * @return a org.sourceforge.ifx.framework.element.DepAppModRq[]
     */
    public org.sourceforge.ifx.framework.element.DepAppModRq[] getDepAppModRq() {
        return _depAppModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAppCanRq[] _depAppCanRq;

    /** 
     * Setter for depAppCanRq
     * @param depAppCanRq the org.sourceforge.ifx.framework.element.DepAppCanRq[] to set
     */
    public void setDepAppCanRq(org.sourceforge.ifx.framework.element.DepAppCanRq[] _depAppCanRq) {
        this._depAppCanRq = _depAppCanRq;
    }

    /**
     * Getter for depAppCanRq
     * @return a org.sourceforge.ifx.framework.element.DepAppCanRq[]
     */
    public org.sourceforge.ifx.framework.element.DepAppCanRq[] getDepAppCanRq() {
        return _depAppCanRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAppInqRq[] _depAppInqRq;

    /** 
     * Setter for depAppInqRq
     * @param depAppInqRq the org.sourceforge.ifx.framework.element.DepAppInqRq[] to set
     */
    public void setDepAppInqRq(org.sourceforge.ifx.framework.element.DepAppInqRq[] _depAppInqRq) {
        this._depAppInqRq = _depAppInqRq;
    }

    /**
     * Getter for depAppInqRq
     * @return a org.sourceforge.ifx.framework.element.DepAppInqRq[]
     */
    public org.sourceforge.ifx.framework.element.DepAppInqRq[] getDepAppInqRq() {
        return _depAppInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAppAudRq[] _depAppAudRq;

    /** 
     * Setter for depAppAudRq
     * @param depAppAudRq the org.sourceforge.ifx.framework.element.DepAppAudRq[] to set
     */
    public void setDepAppAudRq(org.sourceforge.ifx.framework.element.DepAppAudRq[] _depAppAudRq) {
        this._depAppAudRq = _depAppAudRq;
    }

    /**
     * Getter for depAppAudRq
     * @return a org.sourceforge.ifx.framework.element.DepAppAudRq[]
     */
    public org.sourceforge.ifx.framework.element.DepAppAudRq[] getDepAppAudRq() {
        return _depAppAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAppSyncRq[] _depAppSyncRq;

    /** 
     * Setter for depAppSyncRq
     * @param depAppSyncRq the org.sourceforge.ifx.framework.element.DepAppSyncRq[] to set
     */
    public void setDepAppSyncRq(org.sourceforge.ifx.framework.element.DepAppSyncRq[] _depAppSyncRq) {
        this._depAppSyncRq = _depAppSyncRq;
    }

    /**
     * Getter for depAppSyncRq
     * @return a org.sourceforge.ifx.framework.element.DepAppSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.DepAppSyncRq[] getDepAppSyncRq() {
        return _depAppSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAcctStmtAdviseRq[] _depAcctStmtAdviseRq;

    /** 
     * Setter for depAcctStmtAdviseRq
     * @param depAcctStmtAdviseRq the org.sourceforge.ifx.framework.element.DepAcctStmtAdviseRq[] to set
     */
    public void setDepAcctStmtAdviseRq(org.sourceforge.ifx.framework.element.DepAcctStmtAdviseRq[] _depAcctStmtAdviseRq) {
        this._depAcctStmtAdviseRq = _depAcctStmtAdviseRq;
    }

    /**
     * Getter for depAcctStmtAdviseRq
     * @return a org.sourceforge.ifx.framework.element.DepAcctStmtAdviseRq[]
     */
    public org.sourceforge.ifx.framework.element.DepAcctStmtAdviseRq[] getDepAcctStmtAdviseRq() {
        return _depAcctStmtAdviseRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankAcctStmtImgInqRq[] _bankAcctStmtImgInqRq;

    /** 
     * Setter for bankAcctStmtImgInqRq
     * @param bankAcctStmtImgInqRq the org.sourceforge.ifx.framework.element.BankAcctStmtImgInqRq[] to set
     */
    public void setBankAcctStmtImgInqRq(org.sourceforge.ifx.framework.element.BankAcctStmtImgInqRq[] _bankAcctStmtImgInqRq) {
        this._bankAcctStmtImgInqRq = _bankAcctStmtImgInqRq;
    }

    /**
     * Getter for bankAcctStmtImgInqRq
     * @return a org.sourceforge.ifx.framework.element.BankAcctStmtImgInqRq[]
     */
    public org.sourceforge.ifx.framework.element.BankAcctStmtImgInqRq[] getBankAcctStmtImgInqRq() {
        return _bankAcctStmtImgInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankAcctStmtImgRevRq[] _bankAcctStmtImgRevRq;

    /** 
     * Setter for bankAcctStmtImgRevRq
     * @param bankAcctStmtImgRevRq the org.sourceforge.ifx.framework.element.BankAcctStmtImgRevRq[] to set
     */
    public void setBankAcctStmtImgRevRq(org.sourceforge.ifx.framework.element.BankAcctStmtImgRevRq[] _bankAcctStmtImgRevRq) {
        this._bankAcctStmtImgRevRq = _bankAcctStmtImgRevRq;
    }

    /**
     * Getter for bankAcctStmtImgRevRq
     * @return a org.sourceforge.ifx.framework.element.BankAcctStmtImgRevRq[]
     */
    public org.sourceforge.ifx.framework.element.BankAcctStmtImgRevRq[] getBankAcctStmtImgRevRq() {
        return _bankAcctStmtImgRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtEnclAddRq[] _pmtEnclAddRq;

    /** 
     * Setter for pmtEnclAddRq
     * @param pmtEnclAddRq the org.sourceforge.ifx.framework.element.PmtEnclAddRq[] to set
     */
    public void setPmtEnclAddRq(org.sourceforge.ifx.framework.element.PmtEnclAddRq[] _pmtEnclAddRq) {
        this._pmtEnclAddRq = _pmtEnclAddRq;
    }

    /**
     * Getter for pmtEnclAddRq
     * @return a org.sourceforge.ifx.framework.element.PmtEnclAddRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtEnclAddRq[] getPmtEnclAddRq() {
        return _pmtEnclAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtEnclAdviseRq[] _pmtEnclAdviseRq;

    /** 
     * Setter for pmtEnclAdviseRq
     * @param pmtEnclAdviseRq the org.sourceforge.ifx.framework.element.PmtEnclAdviseRq[] to set
     */
    public void setPmtEnclAdviseRq(org.sourceforge.ifx.framework.element.PmtEnclAdviseRq[] _pmtEnclAdviseRq) {
        this._pmtEnclAdviseRq = _pmtEnclAdviseRq;
    }

    /**
     * Getter for pmtEnclAdviseRq
     * @return a org.sourceforge.ifx.framework.element.PmtEnclAdviseRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtEnclAdviseRq[] getPmtEnclAdviseRq() {
        return _pmtEnclAdviseRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtEnclModRq[] _pmtEnclModRq;

    /** 
     * Setter for pmtEnclModRq
     * @param pmtEnclModRq the org.sourceforge.ifx.framework.element.PmtEnclModRq[] to set
     */
    public void setPmtEnclModRq(org.sourceforge.ifx.framework.element.PmtEnclModRq[] _pmtEnclModRq) {
        this._pmtEnclModRq = _pmtEnclModRq;
    }

    /**
     * Getter for pmtEnclModRq
     * @return a org.sourceforge.ifx.framework.element.PmtEnclModRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtEnclModRq[] getPmtEnclModRq() {
        return _pmtEnclModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtEnclInqRq[] _pmtEnclInqRq;

    /** 
     * Setter for pmtEnclInqRq
     * @param pmtEnclInqRq the org.sourceforge.ifx.framework.element.PmtEnclInqRq[] to set
     */
    public void setPmtEnclInqRq(org.sourceforge.ifx.framework.element.PmtEnclInqRq[] _pmtEnclInqRq) {
        this._pmtEnclInqRq = _pmtEnclInqRq;
    }

    /**
     * Getter for pmtEnclInqRq
     * @return a org.sourceforge.ifx.framework.element.PmtEnclInqRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtEnclInqRq[] getPmtEnclInqRq() {
        return _pmtEnclInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtEnclCanRq[] _pmtEnclCanRq;

    /** 
     * Setter for pmtEnclCanRq
     * @param pmtEnclCanRq the org.sourceforge.ifx.framework.element.PmtEnclCanRq[] to set
     */
    public void setPmtEnclCanRq(org.sourceforge.ifx.framework.element.PmtEnclCanRq[] _pmtEnclCanRq) {
        this._pmtEnclCanRq = _pmtEnclCanRq;
    }

    /**
     * Getter for pmtEnclCanRq
     * @return a org.sourceforge.ifx.framework.element.PmtEnclCanRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtEnclCanRq[] getPmtEnclCanRq() {
        return _pmtEnclCanRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtEnclAudRq[] _pmtEnclAudRq;

    /** 
     * Setter for pmtEnclAudRq
     * @param pmtEnclAudRq the org.sourceforge.ifx.framework.element.PmtEnclAudRq[] to set
     */
    public void setPmtEnclAudRq(org.sourceforge.ifx.framework.element.PmtEnclAudRq[] _pmtEnclAudRq) {
        this._pmtEnclAudRq = _pmtEnclAudRq;
    }

    /**
     * Getter for pmtEnclAudRq
     * @return a org.sourceforge.ifx.framework.element.PmtEnclAudRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtEnclAudRq[] getPmtEnclAudRq() {
        return _pmtEnclAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtEnclRevRq[] _pmtEnclRevRq;

    /** 
     * Setter for pmtEnclRevRq
     * @param pmtEnclRevRq the org.sourceforge.ifx.framework.element.PmtEnclRevRq[] to set
     */
    public void setPmtEnclRevRq(org.sourceforge.ifx.framework.element.PmtEnclRevRq[] _pmtEnclRevRq) {
        this._pmtEnclRevRq = _pmtEnclRevRq;
    }

    /**
     * Getter for pmtEnclRevRq
     * @return a org.sourceforge.ifx.framework.element.PmtEnclRevRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtEnclRevRq[] getPmtEnclRevRq() {
        return _pmtEnclRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtEnclStatusModRq[] _pmtEnclStatusModRq;

    /** 
     * Setter for pmtEnclStatusModRq
     * @param pmtEnclStatusModRq the org.sourceforge.ifx.framework.element.PmtEnclStatusModRq[] to set
     */
    public void setPmtEnclStatusModRq(org.sourceforge.ifx.framework.element.PmtEnclStatusModRq[] _pmtEnclStatusModRq) {
        this._pmtEnclStatusModRq = _pmtEnclStatusModRq;
    }

    /**
     * Getter for pmtEnclStatusModRq
     * @return a org.sourceforge.ifx.framework.element.PmtEnclStatusModRq[]
     */
    public org.sourceforge.ifx.framework.element.PmtEnclStatusModRq[] getPmtEnclStatusModRq() {
        return _pmtEnclStatusModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkIssueAddRq[] _chkIssueAddRq;

    /** 
     * Setter for chkIssueAddRq
     * @param chkIssueAddRq the org.sourceforge.ifx.framework.element.ChkIssueAddRq[] to set
     */
    public void setChkIssueAddRq(org.sourceforge.ifx.framework.element.ChkIssueAddRq[] _chkIssueAddRq) {
        this._chkIssueAddRq = _chkIssueAddRq;
    }

    /**
     * Getter for chkIssueAddRq
     * @return a org.sourceforge.ifx.framework.element.ChkIssueAddRq[]
     */
    public org.sourceforge.ifx.framework.element.ChkIssueAddRq[] getChkIssueAddRq() {
        return _chkIssueAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkIssueAdviseRq[] _chkIssueAdviseRq;

    /** 
     * Setter for chkIssueAdviseRq
     * @param chkIssueAdviseRq the org.sourceforge.ifx.framework.element.ChkIssueAdviseRq[] to set
     */
    public void setChkIssueAdviseRq(org.sourceforge.ifx.framework.element.ChkIssueAdviseRq[] _chkIssueAdviseRq) {
        this._chkIssueAdviseRq = _chkIssueAdviseRq;
    }

    /**
     * Getter for chkIssueAdviseRq
     * @return a org.sourceforge.ifx.framework.element.ChkIssueAdviseRq[]
     */
    public org.sourceforge.ifx.framework.element.ChkIssueAdviseRq[] getChkIssueAdviseRq() {
        return _chkIssueAdviseRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkIssueModRq[] _chkIssueModRq;

    /** 
     * Setter for chkIssueModRq
     * @param chkIssueModRq the org.sourceforge.ifx.framework.element.ChkIssueModRq[] to set
     */
    public void setChkIssueModRq(org.sourceforge.ifx.framework.element.ChkIssueModRq[] _chkIssueModRq) {
        this._chkIssueModRq = _chkIssueModRq;
    }

    /**
     * Getter for chkIssueModRq
     * @return a org.sourceforge.ifx.framework.element.ChkIssueModRq[]
     */
    public org.sourceforge.ifx.framework.element.ChkIssueModRq[] getChkIssueModRq() {
        return _chkIssueModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkIssueStatusModRq[] _chkIssueStatusModRq;

    /** 
     * Setter for chkIssueStatusModRq
     * @param chkIssueStatusModRq the org.sourceforge.ifx.framework.element.ChkIssueStatusModRq[] to set
     */
    public void setChkIssueStatusModRq(org.sourceforge.ifx.framework.element.ChkIssueStatusModRq[] _chkIssueStatusModRq) {
        this._chkIssueStatusModRq = _chkIssueStatusModRq;
    }

    /**
     * Getter for chkIssueStatusModRq
     * @return a org.sourceforge.ifx.framework.element.ChkIssueStatusModRq[]
     */
    public org.sourceforge.ifx.framework.element.ChkIssueStatusModRq[] getChkIssueStatusModRq() {
        return _chkIssueStatusModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkIssueDelRq[] _chkIssueDelRq;

    /** 
     * Setter for chkIssueDelRq
     * @param chkIssueDelRq the org.sourceforge.ifx.framework.element.ChkIssueDelRq[] to set
     */
    public void setChkIssueDelRq(org.sourceforge.ifx.framework.element.ChkIssueDelRq[] _chkIssueDelRq) {
        this._chkIssueDelRq = _chkIssueDelRq;
    }

    /**
     * Getter for chkIssueDelRq
     * @return a org.sourceforge.ifx.framework.element.ChkIssueDelRq[]
     */
    public org.sourceforge.ifx.framework.element.ChkIssueDelRq[] getChkIssueDelRq() {
        return _chkIssueDelRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkIssueInqRq[] _chkIssueInqRq;

    /** 
     * Setter for chkIssueInqRq
     * @param chkIssueInqRq the org.sourceforge.ifx.framework.element.ChkIssueInqRq[] to set
     */
    public void setChkIssueInqRq(org.sourceforge.ifx.framework.element.ChkIssueInqRq[] _chkIssueInqRq) {
        this._chkIssueInqRq = _chkIssueInqRq;
    }

    /**
     * Getter for chkIssueInqRq
     * @return a org.sourceforge.ifx.framework.element.ChkIssueInqRq[]
     */
    public org.sourceforge.ifx.framework.element.ChkIssueInqRq[] getChkIssueInqRq() {
        return _chkIssueInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkIssueAudRq[] _chkIssueAudRq;

    /** 
     * Setter for chkIssueAudRq
     * @param chkIssueAudRq the org.sourceforge.ifx.framework.element.ChkIssueAudRq[] to set
     */
    public void setChkIssueAudRq(org.sourceforge.ifx.framework.element.ChkIssueAudRq[] _chkIssueAudRq) {
        this._chkIssueAudRq = _chkIssueAudRq;
    }

    /**
     * Getter for chkIssueAudRq
     * @return a org.sourceforge.ifx.framework.element.ChkIssueAudRq[]
     */
    public org.sourceforge.ifx.framework.element.ChkIssueAudRq[] getChkIssueAudRq() {
        return _chkIssueAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkIssueSyncRq[] _chkIssueSyncRq;

    /** 
     * Setter for chkIssueSyncRq
     * @param chkIssueSyncRq the org.sourceforge.ifx.framework.element.ChkIssueSyncRq[] to set
     */
    public void setChkIssueSyncRq(org.sourceforge.ifx.framework.element.ChkIssueSyncRq[] _chkIssueSyncRq) {
        this._chkIssueSyncRq = _chkIssueSyncRq;
    }

    /**
     * Getter for chkIssueSyncRq
     * @return a org.sourceforge.ifx.framework.element.ChkIssueSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.ChkIssueSyncRq[] getChkIssueSyncRq() {
        return _chkIssueSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankSvcChkSumAddRq[] _bankSvcChkSumAddRq;

    /** 
     * Setter for bankSvcChkSumAddRq
     * @param bankSvcChkSumAddRq the org.sourceforge.ifx.framework.element.BankSvcChkSumAddRq[] to set
     */
    public void setBankSvcChkSumAddRq(org.sourceforge.ifx.framework.element.BankSvcChkSumAddRq[] _bankSvcChkSumAddRq) {
        this._bankSvcChkSumAddRq = _bankSvcChkSumAddRq;
    }

    /**
     * Getter for bankSvcChkSumAddRq
     * @return a org.sourceforge.ifx.framework.element.BankSvcChkSumAddRq[]
     */
    public org.sourceforge.ifx.framework.element.BankSvcChkSumAddRq[] getBankSvcChkSumAddRq() {
        return _bankSvcChkSumAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankSvcChkSumModRq[] _bankSvcChkSumModRq;

    /** 
     * Setter for bankSvcChkSumModRq
     * @param bankSvcChkSumModRq the org.sourceforge.ifx.framework.element.BankSvcChkSumModRq[] to set
     */
    public void setBankSvcChkSumModRq(org.sourceforge.ifx.framework.element.BankSvcChkSumModRq[] _bankSvcChkSumModRq) {
        this._bankSvcChkSumModRq = _bankSvcChkSumModRq;
    }

    /**
     * Getter for bankSvcChkSumModRq
     * @return a org.sourceforge.ifx.framework.element.BankSvcChkSumModRq[]
     */
    public org.sourceforge.ifx.framework.element.BankSvcChkSumModRq[] getBankSvcChkSumModRq() {
        return _bankSvcChkSumModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankSvcChkSumStatusModRq[] _bankSvcChkSumStatusModRq;

    /** 
     * Setter for bankSvcChkSumStatusModRq
     * @param bankSvcChkSumStatusModRq the org.sourceforge.ifx.framework.element.BankSvcChkSumStatusModRq[] to set
     */
    public void setBankSvcChkSumStatusModRq(org.sourceforge.ifx.framework.element.BankSvcChkSumStatusModRq[] _bankSvcChkSumStatusModRq) {
        this._bankSvcChkSumStatusModRq = _bankSvcChkSumStatusModRq;
    }

    /**
     * Getter for bankSvcChkSumStatusModRq
     * @return a org.sourceforge.ifx.framework.element.BankSvcChkSumStatusModRq[]
     */
    public org.sourceforge.ifx.framework.element.BankSvcChkSumStatusModRq[] getBankSvcChkSumStatusModRq() {
        return _bankSvcChkSumStatusModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankSvcChkSumDelRq[] _bankSvcChkSumDelRq;

    /** 
     * Setter for bankSvcChkSumDelRq
     * @param bankSvcChkSumDelRq the org.sourceforge.ifx.framework.element.BankSvcChkSumDelRq[] to set
     */
    public void setBankSvcChkSumDelRq(org.sourceforge.ifx.framework.element.BankSvcChkSumDelRq[] _bankSvcChkSumDelRq) {
        this._bankSvcChkSumDelRq = _bankSvcChkSumDelRq;
    }

    /**
     * Getter for bankSvcChkSumDelRq
     * @return a org.sourceforge.ifx.framework.element.BankSvcChkSumDelRq[]
     */
    public org.sourceforge.ifx.framework.element.BankSvcChkSumDelRq[] getBankSvcChkSumDelRq() {
        return _bankSvcChkSumDelRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankSvcChkSumInqRq[] _bankSvcChkSumInqRq;

    /** 
     * Setter for bankSvcChkSumInqRq
     * @param bankSvcChkSumInqRq the org.sourceforge.ifx.framework.element.BankSvcChkSumInqRq[] to set
     */
    public void setBankSvcChkSumInqRq(org.sourceforge.ifx.framework.element.BankSvcChkSumInqRq[] _bankSvcChkSumInqRq) {
        this._bankSvcChkSumInqRq = _bankSvcChkSumInqRq;
    }

    /**
     * Getter for bankSvcChkSumInqRq
     * @return a org.sourceforge.ifx.framework.element.BankSvcChkSumInqRq[]
     */
    public org.sourceforge.ifx.framework.element.BankSvcChkSumInqRq[] getBankSvcChkSumInqRq() {
        return _bankSvcChkSumInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankSvcChkSumAudRq[] _bankSvcChkSumAudRq;

    /** 
     * Setter for bankSvcChkSumAudRq
     * @param bankSvcChkSumAudRq the org.sourceforge.ifx.framework.element.BankSvcChkSumAudRq[] to set
     */
    public void setBankSvcChkSumAudRq(org.sourceforge.ifx.framework.element.BankSvcChkSumAudRq[] _bankSvcChkSumAudRq) {
        this._bankSvcChkSumAudRq = _bankSvcChkSumAudRq;
    }

    /**
     * Getter for bankSvcChkSumAudRq
     * @return a org.sourceforge.ifx.framework.element.BankSvcChkSumAudRq[]
     */
    public org.sourceforge.ifx.framework.element.BankSvcChkSumAudRq[] getBankSvcChkSumAudRq() {
        return _bankSvcChkSumAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankSvcChkSumSyncRq[] _bankSvcChkSumSyncRq;

    /** 
     * Setter for bankSvcChkSumSyncRq
     * @param bankSvcChkSumSyncRq the org.sourceforge.ifx.framework.element.BankSvcChkSumSyncRq[] to set
     */
    public void setBankSvcChkSumSyncRq(org.sourceforge.ifx.framework.element.BankSvcChkSumSyncRq[] _bankSvcChkSumSyncRq) {
        this._bankSvcChkSumSyncRq = _bankSvcChkSumSyncRq;
    }

    /**
     * Getter for bankSvcChkSumSyncRq
     * @return a org.sourceforge.ifx.framework.element.BankSvcChkSumSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.BankSvcChkSumSyncRq[] getBankSvcChkSumSyncRq() {
        return _bankSvcChkSumSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkAcceptAddRq[] _chkAcceptAddRq;

    /** 
     * Setter for chkAcceptAddRq
     * @param chkAcceptAddRq the org.sourceforge.ifx.framework.element.ChkAcceptAddRq[] to set
     */
    public void setChkAcceptAddRq(org.sourceforge.ifx.framework.element.ChkAcceptAddRq[] _chkAcceptAddRq) {
        this._chkAcceptAddRq = _chkAcceptAddRq;
    }

    /**
     * Getter for chkAcceptAddRq
     * @return a org.sourceforge.ifx.framework.element.ChkAcceptAddRq[]
     */
    public org.sourceforge.ifx.framework.element.ChkAcceptAddRq[] getChkAcceptAddRq() {
        return _chkAcceptAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkAcceptModRq[] _chkAcceptModRq;

    /** 
     * Setter for chkAcceptModRq
     * @param chkAcceptModRq the org.sourceforge.ifx.framework.element.ChkAcceptModRq[] to set
     */
    public void setChkAcceptModRq(org.sourceforge.ifx.framework.element.ChkAcceptModRq[] _chkAcceptModRq) {
        this._chkAcceptModRq = _chkAcceptModRq;
    }

    /**
     * Getter for chkAcceptModRq
     * @return a org.sourceforge.ifx.framework.element.ChkAcceptModRq[]
     */
    public org.sourceforge.ifx.framework.element.ChkAcceptModRq[] getChkAcceptModRq() {
        return _chkAcceptModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkAcceptInqRq[] _chkAcceptInqRq;

    /** 
     * Setter for chkAcceptInqRq
     * @param chkAcceptInqRq the org.sourceforge.ifx.framework.element.ChkAcceptInqRq[] to set
     */
    public void setChkAcceptInqRq(org.sourceforge.ifx.framework.element.ChkAcceptInqRq[] _chkAcceptInqRq) {
        this._chkAcceptInqRq = _chkAcceptInqRq;
    }

    /**
     * Getter for chkAcceptInqRq
     * @return a org.sourceforge.ifx.framework.element.ChkAcceptInqRq[]
     */
    public org.sourceforge.ifx.framework.element.ChkAcceptInqRq[] getChkAcceptInqRq() {
        return _chkAcceptInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkAcceptCanRq[] _chkAcceptCanRq;

    /** 
     * Setter for chkAcceptCanRq
     * @param chkAcceptCanRq the org.sourceforge.ifx.framework.element.ChkAcceptCanRq[] to set
     */
    public void setChkAcceptCanRq(org.sourceforge.ifx.framework.element.ChkAcceptCanRq[] _chkAcceptCanRq) {
        this._chkAcceptCanRq = _chkAcceptCanRq;
    }

    /**
     * Getter for chkAcceptCanRq
     * @return a org.sourceforge.ifx.framework.element.ChkAcceptCanRq[]
     */
    public org.sourceforge.ifx.framework.element.ChkAcceptCanRq[] getChkAcceptCanRq() {
        return _chkAcceptCanRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkAcceptAudRq[] _chkAcceptAudRq;

    /** 
     * Setter for chkAcceptAudRq
     * @param chkAcceptAudRq the org.sourceforge.ifx.framework.element.ChkAcceptAudRq[] to set
     */
    public void setChkAcceptAudRq(org.sourceforge.ifx.framework.element.ChkAcceptAudRq[] _chkAcceptAudRq) {
        this._chkAcceptAudRq = _chkAcceptAudRq;
    }

    /**
     * Getter for chkAcceptAudRq
     * @return a org.sourceforge.ifx.framework.element.ChkAcceptAudRq[]
     */
    public org.sourceforge.ifx.framework.element.ChkAcceptAudRq[] getChkAcceptAudRq() {
        return _chkAcceptAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkAcceptSyncRq[] _chkAcceptSyncRq;

    /** 
     * Setter for chkAcceptSyncRq
     * @param chkAcceptSyncRq the org.sourceforge.ifx.framework.element.ChkAcceptSyncRq[] to set
     */
    public void setChkAcceptSyncRq(org.sourceforge.ifx.framework.element.ChkAcceptSyncRq[] _chkAcceptSyncRq) {
        this._chkAcceptSyncRq = _chkAcceptSyncRq;
    }

    /**
     * Getter for chkAcceptSyncRq
     * @return a org.sourceforge.ifx.framework.element.ChkAcceptSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.ChkAcceptSyncRq[] getChkAcceptSyncRq() {
        return _chkAcceptSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkAcceptRevRq[] _chkAcceptRevRq;

    /** 
     * Setter for chkAcceptRevRq
     * @param chkAcceptRevRq the org.sourceforge.ifx.framework.element.ChkAcceptRevRq[] to set
     */
    public void setChkAcceptRevRq(org.sourceforge.ifx.framework.element.ChkAcceptRevRq[] _chkAcceptRevRq) {
        this._chkAcceptRevRq = _chkAcceptRevRq;
    }

    /**
     * Getter for chkAcceptRevRq
     * @return a org.sourceforge.ifx.framework.element.ChkAcceptRevRq[]
     */
    public org.sourceforge.ifx.framework.element.ChkAcceptRevRq[] getChkAcceptRevRq() {
        return _chkAcceptRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkAcceptStatusModRq[] _chkAcceptStatusModRq;

    /** 
     * Setter for chkAcceptStatusModRq
     * @param chkAcceptStatusModRq the org.sourceforge.ifx.framework.element.ChkAcceptStatusModRq[] to set
     */
    public void setChkAcceptStatusModRq(org.sourceforge.ifx.framework.element.ChkAcceptStatusModRq[] _chkAcceptStatusModRq) {
        this._chkAcceptStatusModRq = _chkAcceptStatusModRq;
    }

    /**
     * Getter for chkAcceptStatusModRq
     * @return a org.sourceforge.ifx.framework.element.ChkAcceptStatusModRq[]
     */
    public org.sourceforge.ifx.framework.element.ChkAcceptStatusModRq[] getChkAcceptStatusModRq() {
        return _chkAcceptStatusModRq;
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
       * AsyncRqUID
       * SPName
       * BalInqRq
       * BalRevRq
       * AcctInqRq
       * AcctRevRq
       * DepAcctStmtInqRq
       * DepAcctStmtRevRq
       * CCAcctStmtInqRq
       * CCAcctStmtRevRq
       * DepAcctTrnInqRq
       * DepAcctTrnRevRq
       * DepAcctTrnAdviseRq
       * CCAcctTrnInqRq
       * CCAcctTrnRevRq
       * BankAcctTrnImgInqRq
       * BankAcctTrnImgRevRq
       * IntRateInqRq
       * IntRateRevRq
       * BankAcctTaxInqRq
       * ForExRateInqRq
       * ForExRateRevRq
       * ForExDealAddRq
       * ForExDealModRq
       * ForExDealInqRq
       * ForExDealCanRq
       * ForExDealAudRq
       * ForExDealSyncRq
       * ForExDealRevRq
       * ForExDealStatusModRq
       * ForExDealStatusInqRq
       * ForExDealAdviseRq
       * StopChkAddRq
       * StopChkAdviseRq
       * StopChkCanRq
       * StopChkInqRq
       * StopChkAudRq
       * StopChkSyncRq
       * StopChkRevRq
       * XferAddRq
       * XferAdviseRq
       * XferModRq
       * XferStatusModRq
       * XferCanRq
       * XferInqRq
       * XferAudRq
       * XferSyncRq
       * XferRevRq
       * RecXferAddRq
       * RecXferModRq
       * RecXferCanRq
       * RecXferInqRq
       * RecXferAudRq
       * RecXferSyncRq
       * RecXferRevRq
       * ChkOrdAddRq
       * ChkOrdAdviseRq
       * ChkOrdModRq
       * ChkOrdCanRq
       * ChkOrdInqRq
       * ChkOrdAudRq
       * ChkOrdSyncRq
       * ChkOrdRevRq
       * RecChkOrdAddRq
       * RecChkOrdModRq
       * RecChkOrdCanRq
       * RecChkOrdInqRq
       * RecChkOrdAudRq
       * RecChkOrdSyncRq
       * RecChkOrdInstAddRq
       * DepBkOrdAddRq
       * DepBkOrdAdviseRq
       * DepBkOrdRevRq
       * DebitAuthAddRq
       * DebitAuthModRq
       * DebitAuthCanRq
       * DebitAuthInqRq
       * DebitAuthAudRq
       * DebitAuthSyncRq
       * DebitAuthStatusModRq
       * DebitAuthAdviseRq
       * DebitAuthRevRq
       * DebitAddRq
       * DebitModRq
       * DebitCanRq
       * DebitInqRq
       * DebitAudRq
       * DebitSyncRq
       * DebitStatusModRq
       * DebitAdviseRq
       * DebitRevRq
       * CreditAuthAddRq
       * CreditAuthAdviseRq
       * CreditAuthModRq
       * CreditAuthCanRq
       * CreditAuthInqRq
       * CreditAuthAudRq
       * CreditAuthSyncRq
       * CreditAuthRevRq
       * CreditAuthStatusModRq
       * CreditAddRq
       * CreditModRq
       * CreditCanRq
       * CreditInqRq
       * CreditAudRq
       * CreditSyncRq
       * CreditAdviseRq
       * CreditStatusModRq
       * CreditRevRq
       * DepAppAddRq
       * DepAppModRq
       * DepAppCanRq
       * DepAppInqRq
       * DepAppAudRq
       * DepAppSyncRq
       * DepAcctStmtAdviseRq
       * BankAcctStmtImgInqRq
       * BankAcctStmtImgRevRq
       * PmtEnclAddRq
       * PmtEnclAdviseRq
       * PmtEnclModRq
       * PmtEnclInqRq
       * PmtEnclCanRq
       * PmtEnclAudRq
       * PmtEnclRevRq
       * PmtEnclStatusModRq
       * ChkIssueAddRq
       * ChkIssueAdviseRq
       * ChkIssueModRq
       * ChkIssueStatusModRq
       * ChkIssueDelRq
       * ChkIssueInqRq
       * ChkIssueAudRq
       * ChkIssueSyncRq
       * BankSvcChkSumAddRq
       * BankSvcChkSumModRq
       * BankSvcChkSumStatusModRq
       * BankSvcChkSumDelRq
       * BankSvcChkSumInqRq
       * BankSvcChkSumAudRq
       * BankSvcChkSumSyncRq
       * ChkAcceptAddRq
       * ChkAcceptModRq
       * ChkAcceptInqRq
       * ChkAcceptCanRq
       * ChkAcceptAudRq
       * ChkAcceptSyncRq
       * ChkAcceptRevRq
       * ChkAcceptStatusModRq
       */
    public final String[] ELEMENTS = {
              "Id"
                 ,"RqUID"
                 ,"AsyncRqUID"
                 ,"SPName"
                 ,"BalInqRq"
                 ,"BalRevRq"
                 ,"AcctInqRq"
                 ,"AcctRevRq"
                 ,"DepAcctStmtInqRq"
                 ,"DepAcctStmtRevRq"
                 ,"CCAcctStmtInqRq"
                 ,"CCAcctStmtRevRq"
                 ,"DepAcctTrnInqRq"
                 ,"DepAcctTrnRevRq"
                 ,"DepAcctTrnAdviseRq"
                 ,"CCAcctTrnInqRq"
                 ,"CCAcctTrnRevRq"
                 ,"BankAcctTrnImgInqRq"
                 ,"BankAcctTrnImgRevRq"
                 ,"IntRateInqRq"
                 ,"IntRateRevRq"
                 ,"BankAcctTaxInqRq"
                 ,"ForExRateInqRq"
                 ,"ForExRateRevRq"
                 ,"ForExDealAddRq"
                 ,"ForExDealModRq"
                 ,"ForExDealInqRq"
                 ,"ForExDealCanRq"
                 ,"ForExDealAudRq"
                 ,"ForExDealSyncRq"
                 ,"ForExDealRevRq"
                 ,"ForExDealStatusModRq"
                 ,"ForExDealStatusInqRq"
                 ,"ForExDealAdviseRq"
                 ,"StopChkAddRq"
                 ,"StopChkAdviseRq"
                 ,"StopChkCanRq"
                 ,"StopChkInqRq"
                 ,"StopChkAudRq"
                 ,"StopChkSyncRq"
                 ,"StopChkRevRq"
                 ,"XferAddRq"
                 ,"XferAdviseRq"
                 ,"XferModRq"
                 ,"XferStatusModRq"
                 ,"XferCanRq"
                 ,"XferInqRq"
                 ,"XferAudRq"
                 ,"XferSyncRq"
                 ,"XferRevRq"
                 ,"RecXferAddRq"
                 ,"RecXferModRq"
                 ,"RecXferCanRq"
                 ,"RecXferInqRq"
                 ,"RecXferAudRq"
                 ,"RecXferSyncRq"
                 ,"RecXferRevRq"
                 ,"ChkOrdAddRq"
                 ,"ChkOrdAdviseRq"
                 ,"ChkOrdModRq"
                 ,"ChkOrdCanRq"
                 ,"ChkOrdInqRq"
                 ,"ChkOrdAudRq"
                 ,"ChkOrdSyncRq"
                 ,"ChkOrdRevRq"
                 ,"RecChkOrdAddRq"
                 ,"RecChkOrdModRq"
                 ,"RecChkOrdCanRq"
                 ,"RecChkOrdInqRq"
                 ,"RecChkOrdAudRq"
                 ,"RecChkOrdSyncRq"
                 ,"RecChkOrdInstAddRq"
                 ,"DepBkOrdAddRq"
                 ,"DepBkOrdAdviseRq"
                 ,"DepBkOrdRevRq"
                 ,"DebitAuthAddRq"
                 ,"DebitAuthModRq"
                 ,"DebitAuthCanRq"
                 ,"DebitAuthInqRq"
                 ,"DebitAuthAudRq"
                 ,"DebitAuthSyncRq"
                 ,"DebitAuthStatusModRq"
                 ,"DebitAuthAdviseRq"
                 ,"DebitAuthRevRq"
                 ,"DebitAddRq"
                 ,"DebitModRq"
                 ,"DebitCanRq"
                 ,"DebitInqRq"
                 ,"DebitAudRq"
                 ,"DebitSyncRq"
                 ,"DebitStatusModRq"
                 ,"DebitAdviseRq"
                 ,"DebitRevRq"
                 ,"CreditAuthAddRq"
                 ,"CreditAuthAdviseRq"
                 ,"CreditAuthModRq"
                 ,"CreditAuthCanRq"
                 ,"CreditAuthInqRq"
                 ,"CreditAuthAudRq"
                 ,"CreditAuthSyncRq"
                 ,"CreditAuthRevRq"
                 ,"CreditAuthStatusModRq"
                 ,"CreditAddRq"
                 ,"CreditModRq"
                 ,"CreditCanRq"
                 ,"CreditInqRq"
                 ,"CreditAudRq"
                 ,"CreditSyncRq"
                 ,"CreditAdviseRq"
                 ,"CreditStatusModRq"
                 ,"CreditRevRq"
                 ,"DepAppAddRq"
                 ,"DepAppModRq"
                 ,"DepAppCanRq"
                 ,"DepAppInqRq"
                 ,"DepAppAudRq"
                 ,"DepAppSyncRq"
                 ,"DepAcctStmtAdviseRq"
                 ,"BankAcctStmtImgInqRq"
                 ,"BankAcctStmtImgRevRq"
                 ,"PmtEnclAddRq"
                 ,"PmtEnclAdviseRq"
                 ,"PmtEnclModRq"
                 ,"PmtEnclInqRq"
                 ,"PmtEnclCanRq"
                 ,"PmtEnclAudRq"
                 ,"PmtEnclRevRq"
                 ,"PmtEnclStatusModRq"
                 ,"ChkIssueAddRq"
                 ,"ChkIssueAdviseRq"
                 ,"ChkIssueModRq"
                 ,"ChkIssueStatusModRq"
                 ,"ChkIssueDelRq"
                 ,"ChkIssueInqRq"
                 ,"ChkIssueAudRq"
                 ,"ChkIssueSyncRq"
                 ,"BankSvcChkSumAddRq"
                 ,"BankSvcChkSumModRq"
                 ,"BankSvcChkSumStatusModRq"
                 ,"BankSvcChkSumDelRq"
                 ,"BankSvcChkSumInqRq"
                 ,"BankSvcChkSumAudRq"
                 ,"BankSvcChkSumSyncRq"
                 ,"ChkAcceptAddRq"
                 ,"ChkAcceptModRq"
                 ,"ChkAcceptInqRq"
                 ,"ChkAcceptCanRq"
                 ,"ChkAcceptAudRq"
                 ,"ChkAcceptSyncRq"
                 ,"ChkAcceptRevRq"
                 ,"ChkAcceptStatusModRq"
          };
}
