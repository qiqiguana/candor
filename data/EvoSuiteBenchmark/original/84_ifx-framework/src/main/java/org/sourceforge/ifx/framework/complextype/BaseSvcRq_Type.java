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
public class BaseSvcRq_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public BaseSvcRq_Type() {
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
    private org.sourceforge.ifx.framework.element.SvcProfInqRq[] _svcProfInqRq;

    /** 
     * Setter for svcProfInqRq
     * @param svcProfInqRq the org.sourceforge.ifx.framework.element.SvcProfInqRq[] to set
     */
    public void setSvcProfInqRq(org.sourceforge.ifx.framework.element.SvcProfInqRq[] _svcProfInqRq) {
        this._svcProfInqRq = _svcProfInqRq;
    }

    /**
     * Getter for svcProfInqRq
     * @return a org.sourceforge.ifx.framework.element.SvcProfInqRq[]
     */
    public org.sourceforge.ifx.framework.element.SvcProfInqRq[] getSvcProfInqRq() {
        return _svcProfInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.HolInqRq[] _holInqRq;

    /** 
     * Setter for holInqRq
     * @param holInqRq the org.sourceforge.ifx.framework.element.HolInqRq[] to set
     */
    public void setHolInqRq(org.sourceforge.ifx.framework.element.HolInqRq[] _holInqRq) {
        this._holInqRq = _holInqRq;
    }

    /**
     * Getter for holInqRq
     * @return a org.sourceforge.ifx.framework.element.HolInqRq[]
     */
    public org.sourceforge.ifx.framework.element.HolInqRq[] getHolInqRq() {
        return _holInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustAddRq[] _custAddRq;

    /** 
     * Setter for custAddRq
     * @param custAddRq the org.sourceforge.ifx.framework.element.CustAddRq[] to set
     */
    public void setCustAddRq(org.sourceforge.ifx.framework.element.CustAddRq[] _custAddRq) {
        this._custAddRq = _custAddRq;
    }

    /**
     * Getter for custAddRq
     * @return a org.sourceforge.ifx.framework.element.CustAddRq[]
     */
    public org.sourceforge.ifx.framework.element.CustAddRq[] getCustAddRq() {
        return _custAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustModRq[] _custModRq;

    /** 
     * Setter for custModRq
     * @param custModRq the org.sourceforge.ifx.framework.element.CustModRq[] to set
     */
    public void setCustModRq(org.sourceforge.ifx.framework.element.CustModRq[] _custModRq) {
        this._custModRq = _custModRq;
    }

    /**
     * Getter for custModRq
     * @return a org.sourceforge.ifx.framework.element.CustModRq[]
     */
    public org.sourceforge.ifx.framework.element.CustModRq[] getCustModRq() {
        return _custModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustPswdModRq[] _custPswdModRq;

    /** 
     * Setter for custPswdModRq
     * @param custPswdModRq the org.sourceforge.ifx.framework.element.CustPswdModRq[] to set
     */
    public void setCustPswdModRq(org.sourceforge.ifx.framework.element.CustPswdModRq[] _custPswdModRq) {
        this._custPswdModRq = _custPswdModRq;
    }

    /**
     * Getter for custPswdModRq
     * @return a org.sourceforge.ifx.framework.element.CustPswdModRq[]
     */
    public org.sourceforge.ifx.framework.element.CustPswdModRq[] getCustPswdModRq() {
        return _custPswdModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustAuthModRq[] _custAuthModRq;

    /** 
     * Setter for custAuthModRq
     * @param custAuthModRq the org.sourceforge.ifx.framework.element.CustAuthModRq[] to set
     */
    public void setCustAuthModRq(org.sourceforge.ifx.framework.element.CustAuthModRq[] _custAuthModRq) {
        this._custAuthModRq = _custAuthModRq;
    }

    /**
     * Getter for custAuthModRq
     * @return a org.sourceforge.ifx.framework.element.CustAuthModRq[]
     */
    public org.sourceforge.ifx.framework.element.CustAuthModRq[] getCustAuthModRq() {
        return _custAuthModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustAuthRevRq[] _custAuthRevRq;

    /** 
     * Setter for custAuthRevRq
     * @param custAuthRevRq the org.sourceforge.ifx.framework.element.CustAuthRevRq[] to set
     */
    public void setCustAuthRevRq(org.sourceforge.ifx.framework.element.CustAuthRevRq[] _custAuthRevRq) {
        this._custAuthRevRq = _custAuthRevRq;
    }

    /**
     * Getter for custAuthRevRq
     * @return a org.sourceforge.ifx.framework.element.CustAuthRevRq[]
     */
    public org.sourceforge.ifx.framework.element.CustAuthRevRq[] getCustAuthRevRq() {
        return _custAuthRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustStatusModRq[] _custStatusModRq;

    /** 
     * Setter for custStatusModRq
     * @param custStatusModRq the org.sourceforge.ifx.framework.element.CustStatusModRq[] to set
     */
    public void setCustStatusModRq(org.sourceforge.ifx.framework.element.CustStatusModRq[] _custStatusModRq) {
        this._custStatusModRq = _custStatusModRq;
    }

    /**
     * Getter for custStatusModRq
     * @return a org.sourceforge.ifx.framework.element.CustStatusModRq[]
     */
    public org.sourceforge.ifx.framework.element.CustStatusModRq[] getCustStatusModRq() {
        return _custStatusModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustDelRq[] _custDelRq;

    /** 
     * Setter for custDelRq
     * @param custDelRq the org.sourceforge.ifx.framework.element.CustDelRq[] to set
     */
    public void setCustDelRq(org.sourceforge.ifx.framework.element.CustDelRq[] _custDelRq) {
        this._custDelRq = _custDelRq;
    }

    /**
     * Getter for custDelRq
     * @return a org.sourceforge.ifx.framework.element.CustDelRq[]
     */
    public org.sourceforge.ifx.framework.element.CustDelRq[] getCustDelRq() {
        return _custDelRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustInqRq[] _custInqRq;

    /** 
     * Setter for custInqRq
     * @param custInqRq the org.sourceforge.ifx.framework.element.CustInqRq[] to set
     */
    public void setCustInqRq(org.sourceforge.ifx.framework.element.CustInqRq[] _custInqRq) {
        this._custInqRq = _custInqRq;
    }

    /**
     * Getter for custInqRq
     * @return a org.sourceforge.ifx.framework.element.CustInqRq[]
     */
    public org.sourceforge.ifx.framework.element.CustInqRq[] getCustInqRq() {
        return _custInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustIdInqRq[] _custIdInqRq;

    /** 
     * Setter for custIdInqRq
     * @param custIdInqRq the org.sourceforge.ifx.framework.element.CustIdInqRq[] to set
     */
    public void setCustIdInqRq(org.sourceforge.ifx.framework.element.CustIdInqRq[] _custIdInqRq) {
        this._custIdInqRq = _custIdInqRq;
    }

    /**
     * Getter for custIdInqRq
     * @return a org.sourceforge.ifx.framework.element.CustIdInqRq[]
     */
    public org.sourceforge.ifx.framework.element.CustIdInqRq[] getCustIdInqRq() {
        return _custIdInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustAudRq[] _custAudRq;

    /** 
     * Setter for custAudRq
     * @param custAudRq the org.sourceforge.ifx.framework.element.CustAudRq[] to set
     */
    public void setCustAudRq(org.sourceforge.ifx.framework.element.CustAudRq[] _custAudRq) {
        this._custAudRq = _custAudRq;
    }

    /**
     * Getter for custAudRq
     * @return a org.sourceforge.ifx.framework.element.CustAudRq[]
     */
    public org.sourceforge.ifx.framework.element.CustAudRq[] getCustAudRq() {
        return _custAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustSyncRq[] _custSyncRq;

    /** 
     * Setter for custSyncRq
     * @param custSyncRq the org.sourceforge.ifx.framework.element.CustSyncRq[] to set
     */
    public void setCustSyncRq(org.sourceforge.ifx.framework.element.CustSyncRq[] _custSyncRq) {
        this._custSyncRq = _custSyncRq;
    }

    /**
     * Getter for custSyncRq
     * @return a org.sourceforge.ifx.framework.element.CustSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.CustSyncRq[] getCustSyncRq() {
        return _custSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustSvcAddRq[] _custSvcAddRq;

    /** 
     * Setter for custSvcAddRq
     * @param custSvcAddRq the org.sourceforge.ifx.framework.element.CustSvcAddRq[] to set
     */
    public void setCustSvcAddRq(org.sourceforge.ifx.framework.element.CustSvcAddRq[] _custSvcAddRq) {
        this._custSvcAddRq = _custSvcAddRq;
    }

    /**
     * Getter for custSvcAddRq
     * @return a org.sourceforge.ifx.framework.element.CustSvcAddRq[]
     */
    public org.sourceforge.ifx.framework.element.CustSvcAddRq[] getCustSvcAddRq() {
        return _custSvcAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustSvcModRq[] _custSvcModRq;

    /** 
     * Setter for custSvcModRq
     * @param custSvcModRq the org.sourceforge.ifx.framework.element.CustSvcModRq[] to set
     */
    public void setCustSvcModRq(org.sourceforge.ifx.framework.element.CustSvcModRq[] _custSvcModRq) {
        this._custSvcModRq = _custSvcModRq;
    }

    /**
     * Getter for custSvcModRq
     * @return a org.sourceforge.ifx.framework.element.CustSvcModRq[]
     */
    public org.sourceforge.ifx.framework.element.CustSvcModRq[] getCustSvcModRq() {
        return _custSvcModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustSvcStatusModRq[] _custSvcStatusModRq;

    /** 
     * Setter for custSvcStatusModRq
     * @param custSvcStatusModRq the org.sourceforge.ifx.framework.element.CustSvcStatusModRq[] to set
     */
    public void setCustSvcStatusModRq(org.sourceforge.ifx.framework.element.CustSvcStatusModRq[] _custSvcStatusModRq) {
        this._custSvcStatusModRq = _custSvcStatusModRq;
    }

    /**
     * Getter for custSvcStatusModRq
     * @return a org.sourceforge.ifx.framework.element.CustSvcStatusModRq[]
     */
    public org.sourceforge.ifx.framework.element.CustSvcStatusModRq[] getCustSvcStatusModRq() {
        return _custSvcStatusModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustSvcDelRq[] _custSvcDelRq;

    /** 
     * Setter for custSvcDelRq
     * @param custSvcDelRq the org.sourceforge.ifx.framework.element.CustSvcDelRq[] to set
     */
    public void setCustSvcDelRq(org.sourceforge.ifx.framework.element.CustSvcDelRq[] _custSvcDelRq) {
        this._custSvcDelRq = _custSvcDelRq;
    }

    /**
     * Getter for custSvcDelRq
     * @return a org.sourceforge.ifx.framework.element.CustSvcDelRq[]
     */
    public org.sourceforge.ifx.framework.element.CustSvcDelRq[] getCustSvcDelRq() {
        return _custSvcDelRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustSvcAudRq[] _custSvcAudRq;

    /** 
     * Setter for custSvcAudRq
     * @param custSvcAudRq the org.sourceforge.ifx.framework.element.CustSvcAudRq[] to set
     */
    public void setCustSvcAudRq(org.sourceforge.ifx.framework.element.CustSvcAudRq[] _custSvcAudRq) {
        this._custSvcAudRq = _custSvcAudRq;
    }

    /**
     * Getter for custSvcAudRq
     * @return a org.sourceforge.ifx.framework.element.CustSvcAudRq[]
     */
    public org.sourceforge.ifx.framework.element.CustSvcAudRq[] getCustSvcAudRq() {
        return _custSvcAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustSvcSyncRq[] _custSvcSyncRq;

    /** 
     * Setter for custSvcSyncRq
     * @param custSvcSyncRq the org.sourceforge.ifx.framework.element.CustSvcSyncRq[] to set
     */
    public void setCustSvcSyncRq(org.sourceforge.ifx.framework.element.CustSvcSyncRq[] _custSvcSyncRq) {
        this._custSvcSyncRq = _custSvcSyncRq;
    }

    /**
     * Getter for custSvcSyncRq
     * @return a org.sourceforge.ifx.framework.element.CustSvcSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.CustSvcSyncRq[] getCustSvcSyncRq() {
        return _custSvcSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SvcAcctAddRq[] _svcAcctAddRq;

    /** 
     * Setter for svcAcctAddRq
     * @param svcAcctAddRq the org.sourceforge.ifx.framework.element.SvcAcctAddRq[] to set
     */
    public void setSvcAcctAddRq(org.sourceforge.ifx.framework.element.SvcAcctAddRq[] _svcAcctAddRq) {
        this._svcAcctAddRq = _svcAcctAddRq;
    }

    /**
     * Getter for svcAcctAddRq
     * @return a org.sourceforge.ifx.framework.element.SvcAcctAddRq[]
     */
    public org.sourceforge.ifx.framework.element.SvcAcctAddRq[] getSvcAcctAddRq() {
        return _svcAcctAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SvcAcctModRq[] _svcAcctModRq;

    /** 
     * Setter for svcAcctModRq
     * @param svcAcctModRq the org.sourceforge.ifx.framework.element.SvcAcctModRq[] to set
     */
    public void setSvcAcctModRq(org.sourceforge.ifx.framework.element.SvcAcctModRq[] _svcAcctModRq) {
        this._svcAcctModRq = _svcAcctModRq;
    }

    /**
     * Getter for svcAcctModRq
     * @return a org.sourceforge.ifx.framework.element.SvcAcctModRq[]
     */
    public org.sourceforge.ifx.framework.element.SvcAcctModRq[] getSvcAcctModRq() {
        return _svcAcctModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SvcAcctStatusModRq[] _svcAcctStatusModRq;

    /** 
     * Setter for svcAcctStatusModRq
     * @param svcAcctStatusModRq the org.sourceforge.ifx.framework.element.SvcAcctStatusModRq[] to set
     */
    public void setSvcAcctStatusModRq(org.sourceforge.ifx.framework.element.SvcAcctStatusModRq[] _svcAcctStatusModRq) {
        this._svcAcctStatusModRq = _svcAcctStatusModRq;
    }

    /**
     * Getter for svcAcctStatusModRq
     * @return a org.sourceforge.ifx.framework.element.SvcAcctStatusModRq[]
     */
    public org.sourceforge.ifx.framework.element.SvcAcctStatusModRq[] getSvcAcctStatusModRq() {
        return _svcAcctStatusModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SvcAcctIdModRq[] _svcAcctIdModRq;

    /** 
     * Setter for svcAcctIdModRq
     * @param svcAcctIdModRq the org.sourceforge.ifx.framework.element.SvcAcctIdModRq[] to set
     */
    public void setSvcAcctIdModRq(org.sourceforge.ifx.framework.element.SvcAcctIdModRq[] _svcAcctIdModRq) {
        this._svcAcctIdModRq = _svcAcctIdModRq;
    }

    /**
     * Getter for svcAcctIdModRq
     * @return a org.sourceforge.ifx.framework.element.SvcAcctIdModRq[]
     */
    public org.sourceforge.ifx.framework.element.SvcAcctIdModRq[] getSvcAcctIdModRq() {
        return _svcAcctIdModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SvcAcctDelRq[] _svcAcctDelRq;

    /** 
     * Setter for svcAcctDelRq
     * @param svcAcctDelRq the org.sourceforge.ifx.framework.element.SvcAcctDelRq[] to set
     */
    public void setSvcAcctDelRq(org.sourceforge.ifx.framework.element.SvcAcctDelRq[] _svcAcctDelRq) {
        this._svcAcctDelRq = _svcAcctDelRq;
    }

    /**
     * Getter for svcAcctDelRq
     * @return a org.sourceforge.ifx.framework.element.SvcAcctDelRq[]
     */
    public org.sourceforge.ifx.framework.element.SvcAcctDelRq[] getSvcAcctDelRq() {
        return _svcAcctDelRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SvcAcctInqRq[] _svcAcctInqRq;

    /** 
     * Setter for svcAcctInqRq
     * @param svcAcctInqRq the org.sourceforge.ifx.framework.element.SvcAcctInqRq[] to set
     */
    public void setSvcAcctInqRq(org.sourceforge.ifx.framework.element.SvcAcctInqRq[] _svcAcctInqRq) {
        this._svcAcctInqRq = _svcAcctInqRq;
    }

    /**
     * Getter for svcAcctInqRq
     * @return a org.sourceforge.ifx.framework.element.SvcAcctInqRq[]
     */
    public org.sourceforge.ifx.framework.element.SvcAcctInqRq[] getSvcAcctInqRq() {
        return _svcAcctInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SvcAcctAudRq[] _svcAcctAudRq;

    /** 
     * Setter for svcAcctAudRq
     * @param svcAcctAudRq the org.sourceforge.ifx.framework.element.SvcAcctAudRq[] to set
     */
    public void setSvcAcctAudRq(org.sourceforge.ifx.framework.element.SvcAcctAudRq[] _svcAcctAudRq) {
        this._svcAcctAudRq = _svcAcctAudRq;
    }

    /**
     * Getter for svcAcctAudRq
     * @return a org.sourceforge.ifx.framework.element.SvcAcctAudRq[]
     */
    public org.sourceforge.ifx.framework.element.SvcAcctAudRq[] getSvcAcctAudRq() {
        return _svcAcctAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SvcAcctSyncRq[] _svcAcctSyncRq;

    /** 
     * Setter for svcAcctSyncRq
     * @param svcAcctSyncRq the org.sourceforge.ifx.framework.element.SvcAcctSyncRq[] to set
     */
    public void setSvcAcctSyncRq(org.sourceforge.ifx.framework.element.SvcAcctSyncRq[] _svcAcctSyncRq) {
        this._svcAcctSyncRq = _svcAcctSyncRq;
    }

    /**
     * Getter for svcAcctSyncRq
     * @return a org.sourceforge.ifx.framework.element.SvcAcctSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.SvcAcctSyncRq[] getSvcAcctSyncRq() {
        return _svcAcctSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SvcAcctRevRq[] _svcAcctRevRq;

    /** 
     * Setter for svcAcctRevRq
     * @param svcAcctRevRq the org.sourceforge.ifx.framework.element.SvcAcctRevRq[] to set
     */
    public void setSvcAcctRevRq(org.sourceforge.ifx.framework.element.SvcAcctRevRq[] _svcAcctRevRq) {
        this._svcAcctRevRq = _svcAcctRevRq;
    }

    /**
     * Getter for svcAcctRevRq
     * @return a org.sourceforge.ifx.framework.element.SvcAcctRevRq[]
     */
    public org.sourceforge.ifx.framework.element.SvcAcctRevRq[] getSvcAcctRevRq() {
        return _svcAcctRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DiscInqRq[] _discInqRq;

    /** 
     * Setter for discInqRq
     * @param discInqRq the org.sourceforge.ifx.framework.element.DiscInqRq[] to set
     */
    public void setDiscInqRq(org.sourceforge.ifx.framework.element.DiscInqRq[] _discInqRq) {
        this._discInqRq = _discInqRq;
    }

    /**
     * Getter for discInqRq
     * @return a org.sourceforge.ifx.framework.element.DiscInqRq[]
     */
    public org.sourceforge.ifx.framework.element.DiscInqRq[] getDiscInqRq() {
        return _discInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustDiscStatusModRq[] _custDiscStatusModRq;

    /** 
     * Setter for custDiscStatusModRq
     * @param custDiscStatusModRq the org.sourceforge.ifx.framework.element.CustDiscStatusModRq[] to set
     */
    public void setCustDiscStatusModRq(org.sourceforge.ifx.framework.element.CustDiscStatusModRq[] _custDiscStatusModRq) {
        this._custDiscStatusModRq = _custDiscStatusModRq;
    }

    /**
     * Getter for custDiscStatusModRq
     * @return a org.sourceforge.ifx.framework.element.CustDiscStatusModRq[]
     */
    public org.sourceforge.ifx.framework.element.CustDiscStatusModRq[] getCustDiscStatusModRq() {
        return _custDiscStatusModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustDiscInqRq[] _custDiscInqRq;

    /** 
     * Setter for custDiscInqRq
     * @param custDiscInqRq the org.sourceforge.ifx.framework.element.CustDiscInqRq[] to set
     */
    public void setCustDiscInqRq(org.sourceforge.ifx.framework.element.CustDiscInqRq[] _custDiscInqRq) {
        this._custDiscInqRq = _custDiscInqRq;
    }

    /**
     * Getter for custDiscInqRq
     * @return a org.sourceforge.ifx.framework.element.CustDiscInqRq[]
     */
    public org.sourceforge.ifx.framework.element.CustDiscInqRq[] getCustDiscInqRq() {
        return _custDiscInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SecObjAddRq[] _secObjAddRq;

    /** 
     * Setter for secObjAddRq
     * @param secObjAddRq the org.sourceforge.ifx.framework.element.SecObjAddRq[] to set
     */
    public void setSecObjAddRq(org.sourceforge.ifx.framework.element.SecObjAddRq[] _secObjAddRq) {
        this._secObjAddRq = _secObjAddRq;
    }

    /**
     * Getter for secObjAddRq
     * @return a org.sourceforge.ifx.framework.element.SecObjAddRq[]
     */
    public org.sourceforge.ifx.framework.element.SecObjAddRq[] getSecObjAddRq() {
        return _secObjAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SecObjModRq[] _secObjModRq;

    /** 
     * Setter for secObjModRq
     * @param secObjModRq the org.sourceforge.ifx.framework.element.SecObjModRq[] to set
     */
    public void setSecObjModRq(org.sourceforge.ifx.framework.element.SecObjModRq[] _secObjModRq) {
        this._secObjModRq = _secObjModRq;
    }

    /**
     * Getter for secObjModRq
     * @return a org.sourceforge.ifx.framework.element.SecObjModRq[]
     */
    public org.sourceforge.ifx.framework.element.SecObjModRq[] getSecObjModRq() {
        return _secObjModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SecObjInqRq[] _secObjInqRq;

    /** 
     * Setter for secObjInqRq
     * @param secObjInqRq the org.sourceforge.ifx.framework.element.SecObjInqRq[] to set
     */
    public void setSecObjInqRq(org.sourceforge.ifx.framework.element.SecObjInqRq[] _secObjInqRq) {
        this._secObjInqRq = _secObjInqRq;
    }

    /**
     * Getter for secObjInqRq
     * @return a org.sourceforge.ifx.framework.element.SecObjInqRq[]
     */
    public org.sourceforge.ifx.framework.element.SecObjInqRq[] getSecObjInqRq() {
        return _secObjInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SecObjDelRq[] _secObjDelRq;

    /** 
     * Setter for secObjDelRq
     * @param secObjDelRq the org.sourceforge.ifx.framework.element.SecObjDelRq[] to set
     */
    public void setSecObjDelRq(org.sourceforge.ifx.framework.element.SecObjDelRq[] _secObjDelRq) {
        this._secObjDelRq = _secObjDelRq;
    }

    /**
     * Getter for secObjDelRq
     * @return a org.sourceforge.ifx.framework.element.SecObjDelRq[]
     */
    public org.sourceforge.ifx.framework.element.SecObjDelRq[] getSecObjDelRq() {
        return _secObjDelRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SecObjAdviseRq[] _secObjAdviseRq;

    /** 
     * Setter for secObjAdviseRq
     * @param secObjAdviseRq the org.sourceforge.ifx.framework.element.SecObjAdviseRq[] to set
     */
    public void setSecObjAdviseRq(org.sourceforge.ifx.framework.element.SecObjAdviseRq[] _secObjAdviseRq) {
        this._secObjAdviseRq = _secObjAdviseRq;
    }

    /**
     * Getter for secObjAdviseRq
     * @return a org.sourceforge.ifx.framework.element.SecObjAdviseRq[]
     */
    public org.sourceforge.ifx.framework.element.SecObjAdviseRq[] getSecObjAdviseRq() {
        return _secObjAdviseRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SecObjSyncRq[] _secObjSyncRq;

    /** 
     * Setter for secObjSyncRq
     * @param secObjSyncRq the org.sourceforge.ifx.framework.element.SecObjSyncRq[] to set
     */
    public void setSecObjSyncRq(org.sourceforge.ifx.framework.element.SecObjSyncRq[] _secObjSyncRq) {
        this._secObjSyncRq = _secObjSyncRq;
    }

    /**
     * Getter for secObjSyncRq
     * @return a org.sourceforge.ifx.framework.element.SecObjSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.SecObjSyncRq[] getSecObjSyncRq() {
        return _secObjSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PartyAcctRelAddRq[] _partyAcctRelAddRq;

    /** 
     * Setter for partyAcctRelAddRq
     * @param partyAcctRelAddRq the org.sourceforge.ifx.framework.element.PartyAcctRelAddRq[] to set
     */
    public void setPartyAcctRelAddRq(org.sourceforge.ifx.framework.element.PartyAcctRelAddRq[] _partyAcctRelAddRq) {
        this._partyAcctRelAddRq = _partyAcctRelAddRq;
    }

    /**
     * Getter for partyAcctRelAddRq
     * @return a org.sourceforge.ifx.framework.element.PartyAcctRelAddRq[]
     */
    public org.sourceforge.ifx.framework.element.PartyAcctRelAddRq[] getPartyAcctRelAddRq() {
        return _partyAcctRelAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PartyAcctRelDelRq[] _partyAcctRelDelRq;

    /** 
     * Setter for partyAcctRelDelRq
     * @param partyAcctRelDelRq the org.sourceforge.ifx.framework.element.PartyAcctRelDelRq[] to set
     */
    public void setPartyAcctRelDelRq(org.sourceforge.ifx.framework.element.PartyAcctRelDelRq[] _partyAcctRelDelRq) {
        this._partyAcctRelDelRq = _partyAcctRelDelRq;
    }

    /**
     * Getter for partyAcctRelDelRq
     * @return a org.sourceforge.ifx.framework.element.PartyAcctRelDelRq[]
     */
    public org.sourceforge.ifx.framework.element.PartyAcctRelDelRq[] getPartyAcctRelDelRq() {
        return _partyAcctRelDelRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PartyAcctRelInqRq[] _partyAcctRelInqRq;

    /** 
     * Setter for partyAcctRelInqRq
     * @param partyAcctRelInqRq the org.sourceforge.ifx.framework.element.PartyAcctRelInqRq[] to set
     */
    public void setPartyAcctRelInqRq(org.sourceforge.ifx.framework.element.PartyAcctRelInqRq[] _partyAcctRelInqRq) {
        this._partyAcctRelInqRq = _partyAcctRelInqRq;
    }

    /**
     * Getter for partyAcctRelInqRq
     * @return a org.sourceforge.ifx.framework.element.PartyAcctRelInqRq[]
     */
    public org.sourceforge.ifx.framework.element.PartyAcctRelInqRq[] getPartyAcctRelInqRq() {
        return _partyAcctRelInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PartyAcctRelModRq[] _partyAcctRelModRq;

    /** 
     * Setter for partyAcctRelModRq
     * @param partyAcctRelModRq the org.sourceforge.ifx.framework.element.PartyAcctRelModRq[] to set
     */
    public void setPartyAcctRelModRq(org.sourceforge.ifx.framework.element.PartyAcctRelModRq[] _partyAcctRelModRq) {
        this._partyAcctRelModRq = _partyAcctRelModRq;
    }

    /**
     * Getter for partyAcctRelModRq
     * @return a org.sourceforge.ifx.framework.element.PartyAcctRelModRq[]
     */
    public org.sourceforge.ifx.framework.element.PartyAcctRelModRq[] getPartyAcctRelModRq() {
        return _partyAcctRelModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.EMVCardAdviseRq[] _eMVCardAdviseRq;

    /** 
     * Setter for eMVCardAdviseRq
     * @param eMVCardAdviseRq the org.sourceforge.ifx.framework.element.EMVCardAdviseRq[] to set
     */
    public void setEMVCardAdviseRq(org.sourceforge.ifx.framework.element.EMVCardAdviseRq[] _eMVCardAdviseRq) {
        this._eMVCardAdviseRq = _eMVCardAdviseRq;
    }

    /**
     * Getter for eMVCardAdviseRq
     * @return a org.sourceforge.ifx.framework.element.EMVCardAdviseRq[]
     */
    public org.sourceforge.ifx.framework.element.EMVCardAdviseRq[] getEMVCardAdviseRq() {
        return _eMVCardAdviseRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardPrefAddRq[] _cardPrefAddRq;

    /** 
     * Setter for cardPrefAddRq
     * @param cardPrefAddRq the org.sourceforge.ifx.framework.element.CardPrefAddRq[] to set
     */
    public void setCardPrefAddRq(org.sourceforge.ifx.framework.element.CardPrefAddRq[] _cardPrefAddRq) {
        this._cardPrefAddRq = _cardPrefAddRq;
    }

    /**
     * Getter for cardPrefAddRq
     * @return a org.sourceforge.ifx.framework.element.CardPrefAddRq[]
     */
    public org.sourceforge.ifx.framework.element.CardPrefAddRq[] getCardPrefAddRq() {
        return _cardPrefAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardPrefModRq[] _cardPrefModRq;

    /** 
     * Setter for cardPrefModRq
     * @param cardPrefModRq the org.sourceforge.ifx.framework.element.CardPrefModRq[] to set
     */
    public void setCardPrefModRq(org.sourceforge.ifx.framework.element.CardPrefModRq[] _cardPrefModRq) {
        this._cardPrefModRq = _cardPrefModRq;
    }

    /**
     * Getter for cardPrefModRq
     * @return a org.sourceforge.ifx.framework.element.CardPrefModRq[]
     */
    public org.sourceforge.ifx.framework.element.CardPrefModRq[] getCardPrefModRq() {
        return _cardPrefModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardPrefInqRq[] _cardPrefInqRq;

    /** 
     * Setter for cardPrefInqRq
     * @param cardPrefInqRq the org.sourceforge.ifx.framework.element.CardPrefInqRq[] to set
     */
    public void setCardPrefInqRq(org.sourceforge.ifx.framework.element.CardPrefInqRq[] _cardPrefInqRq) {
        this._cardPrefInqRq = _cardPrefInqRq;
    }

    /**
     * Getter for cardPrefInqRq
     * @return a org.sourceforge.ifx.framework.element.CardPrefInqRq[]
     */
    public org.sourceforge.ifx.framework.element.CardPrefInqRq[] getCardPrefInqRq() {
        return _cardPrefInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardPrefDelRq[] _cardPrefDelRq;

    /** 
     * Setter for cardPrefDelRq
     * @param cardPrefDelRq the org.sourceforge.ifx.framework.element.CardPrefDelRq[] to set
     */
    public void setCardPrefDelRq(org.sourceforge.ifx.framework.element.CardPrefDelRq[] _cardPrefDelRq) {
        this._cardPrefDelRq = _cardPrefDelRq;
    }

    /**
     * Getter for cardPrefDelRq
     * @return a org.sourceforge.ifx.framework.element.CardPrefDelRq[]
     */
    public org.sourceforge.ifx.framework.element.CardPrefDelRq[] getCardPrefDelRq() {
        return _cardPrefDelRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardPrefAudRq[] _cardPrefAudRq;

    /** 
     * Setter for cardPrefAudRq
     * @param cardPrefAudRq the org.sourceforge.ifx.framework.element.CardPrefAudRq[] to set
     */
    public void setCardPrefAudRq(org.sourceforge.ifx.framework.element.CardPrefAudRq[] _cardPrefAudRq) {
        this._cardPrefAudRq = _cardPrefAudRq;
    }

    /**
     * Getter for cardPrefAudRq
     * @return a org.sourceforge.ifx.framework.element.CardPrefAudRq[]
     */
    public org.sourceforge.ifx.framework.element.CardPrefAudRq[] getCardPrefAudRq() {
        return _cardPrefAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardPrefSyncRq[] _cardPrefSyncRq;

    /** 
     * Setter for cardPrefSyncRq
     * @param cardPrefSyncRq the org.sourceforge.ifx.framework.element.CardPrefSyncRq[] to set
     */
    public void setCardPrefSyncRq(org.sourceforge.ifx.framework.element.CardPrefSyncRq[] _cardPrefSyncRq) {
        this._cardPrefSyncRq = _cardPrefSyncRq;
    }

    /**
     * Getter for cardPrefSyncRq
     * @return a org.sourceforge.ifx.framework.element.CardPrefSyncRq[]
     */
    public org.sourceforge.ifx.framework.element.CardPrefSyncRq[] getCardPrefSyncRq() {
        return _cardPrefSyncRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardPrefRevRq[] _cardPrefRevRq;

    /** 
     * Setter for cardPrefRevRq
     * @param cardPrefRevRq the org.sourceforge.ifx.framework.element.CardPrefRevRq[] to set
     */
    public void setCardPrefRevRq(org.sourceforge.ifx.framework.element.CardPrefRevRq[] _cardPrefRevRq) {
        this._cardPrefRevRq = _cardPrefRevRq;
    }

    /**
     * Getter for cardPrefRevRq
     * @return a org.sourceforge.ifx.framework.element.CardPrefRevRq[]
     */
    public org.sourceforge.ifx.framework.element.CardPrefRevRq[] getCardPrefRevRq() {
        return _cardPrefRevRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardPrefStatusModRq[] _cardPrefStatusModRq;

    /** 
     * Setter for cardPrefStatusModRq
     * @param cardPrefStatusModRq the org.sourceforge.ifx.framework.element.CardPrefStatusModRq[] to set
     */
    public void setCardPrefStatusModRq(org.sourceforge.ifx.framework.element.CardPrefStatusModRq[] _cardPrefStatusModRq) {
        this._cardPrefStatusModRq = _cardPrefStatusModRq;
    }

    /**
     * Getter for cardPrefStatusModRq
     * @return a org.sourceforge.ifx.framework.element.CardPrefStatusModRq[]
     */
    public org.sourceforge.ifx.framework.element.CardPrefStatusModRq[] getCardPrefStatusModRq() {
        return _cardPrefStatusModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PostingSessionAddRq[] _postingSessionAddRq;

    /** 
     * Setter for postingSessionAddRq
     * @param postingSessionAddRq the org.sourceforge.ifx.framework.element.PostingSessionAddRq[] to set
     */
    public void setPostingSessionAddRq(org.sourceforge.ifx.framework.element.PostingSessionAddRq[] _postingSessionAddRq) {
        this._postingSessionAddRq = _postingSessionAddRq;
    }

    /**
     * Getter for postingSessionAddRq
     * @return a org.sourceforge.ifx.framework.element.PostingSessionAddRq[]
     */
    public org.sourceforge.ifx.framework.element.PostingSessionAddRq[] getPostingSessionAddRq() {
        return _postingSessionAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PostingSessionModRq[] _postingSessionModRq;

    /** 
     * Setter for postingSessionModRq
     * @param postingSessionModRq the org.sourceforge.ifx.framework.element.PostingSessionModRq[] to set
     */
    public void setPostingSessionModRq(org.sourceforge.ifx.framework.element.PostingSessionModRq[] _postingSessionModRq) {
        this._postingSessionModRq = _postingSessionModRq;
    }

    /**
     * Getter for postingSessionModRq
     * @return a org.sourceforge.ifx.framework.element.PostingSessionModRq[]
     */
    public org.sourceforge.ifx.framework.element.PostingSessionModRq[] getPostingSessionModRq() {
        return _postingSessionModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PostingSessionInqRq[] _postingSessionInqRq;

    /** 
     * Setter for postingSessionInqRq
     * @param postingSessionInqRq the org.sourceforge.ifx.framework.element.PostingSessionInqRq[] to set
     */
    public void setPostingSessionInqRq(org.sourceforge.ifx.framework.element.PostingSessionInqRq[] _postingSessionInqRq) {
        this._postingSessionInqRq = _postingSessionInqRq;
    }

    /**
     * Getter for postingSessionInqRq
     * @return a org.sourceforge.ifx.framework.element.PostingSessionInqRq[]
     */
    public org.sourceforge.ifx.framework.element.PostingSessionInqRq[] getPostingSessionInqRq() {
        return _postingSessionInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PostingSessionAudRq[] _postingSessionAudRq;

    /** 
     * Setter for postingSessionAudRq
     * @param postingSessionAudRq the org.sourceforge.ifx.framework.element.PostingSessionAudRq[] to set
     */
    public void setPostingSessionAudRq(org.sourceforge.ifx.framework.element.PostingSessionAudRq[] _postingSessionAudRq) {
        this._postingSessionAudRq = _postingSessionAudRq;
    }

    /**
     * Getter for postingSessionAudRq
     * @return a org.sourceforge.ifx.framework.element.PostingSessionAudRq[]
     */
    public org.sourceforge.ifx.framework.element.PostingSessionAudRq[] getPostingSessionAudRq() {
        return _postingSessionAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PostingSessionStatusModRq[] _postingSessionStatusModRq;

    /** 
     * Setter for postingSessionStatusModRq
     * @param postingSessionStatusModRq the org.sourceforge.ifx.framework.element.PostingSessionStatusModRq[] to set
     */
    public void setPostingSessionStatusModRq(org.sourceforge.ifx.framework.element.PostingSessionStatusModRq[] _postingSessionStatusModRq) {
        this._postingSessionStatusModRq = _postingSessionStatusModRq;
    }

    /**
     * Getter for postingSessionStatusModRq
     * @return a org.sourceforge.ifx.framework.element.PostingSessionStatusModRq[]
     */
    public org.sourceforge.ifx.framework.element.PostingSessionStatusModRq[] getPostingSessionStatusModRq() {
        return _postingSessionStatusModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SessionTotalsInqRq[] _sessionTotalsInqRq;

    /** 
     * Setter for sessionTotalsInqRq
     * @param sessionTotalsInqRq the org.sourceforge.ifx.framework.element.SessionTotalsInqRq[] to set
     */
    public void setSessionTotalsInqRq(org.sourceforge.ifx.framework.element.SessionTotalsInqRq[] _sessionTotalsInqRq) {
        this._sessionTotalsInqRq = _sessionTotalsInqRq;
    }

    /**
     * Getter for sessionTotalsInqRq
     * @return a org.sourceforge.ifx.framework.element.SessionTotalsInqRq[]
     */
    public org.sourceforge.ifx.framework.element.SessionTotalsInqRq[] getSessionTotalsInqRq() {
        return _sessionTotalsInqRq;
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
       * SvcProfInqRq
       * HolInqRq
       * CustAddRq
       * CustModRq
       * CustPswdModRq
       * CustAuthModRq
       * CustAuthRevRq
       * CustStatusModRq
       * CustDelRq
       * CustInqRq
       * CustIdInqRq
       * CustAudRq
       * CustSyncRq
       * CustSvcAddRq
       * CustSvcModRq
       * CustSvcStatusModRq
       * CustSvcDelRq
       * CustSvcAudRq
       * CustSvcSyncRq
       * SvcAcctAddRq
       * SvcAcctModRq
       * SvcAcctStatusModRq
       * SvcAcctIdModRq
       * SvcAcctDelRq
       * SvcAcctInqRq
       * SvcAcctAudRq
       * SvcAcctSyncRq
       * SvcAcctRevRq
       * DiscInqRq
       * CustDiscStatusModRq
       * CustDiscInqRq
       * SecObjAddRq
       * SecObjModRq
       * SecObjInqRq
       * SecObjDelRq
       * SecObjAdviseRq
       * SecObjSyncRq
       * PartyAcctRelAddRq
       * PartyAcctRelDelRq
       * PartyAcctRelInqRq
       * PartyAcctRelModRq
       * EMVCardAdviseRq
       * CardPrefAddRq
       * CardPrefModRq
       * CardPrefInqRq
       * CardPrefDelRq
       * CardPrefAudRq
       * CardPrefSyncRq
       * CardPrefRevRq
       * CardPrefStatusModRq
       * PostingSessionAddRq
       * PostingSessionModRq
       * PostingSessionInqRq
       * PostingSessionAudRq
       * PostingSessionStatusModRq
       * SessionTotalsInqRq
       */
    public final String[] ELEMENTS = {
              "Id"
                 ,"RqUID"
                 ,"AsyncRqUID"
                 ,"SPName"
                 ,"SvcProfInqRq"
                 ,"HolInqRq"
                 ,"CustAddRq"
                 ,"CustModRq"
                 ,"CustPswdModRq"
                 ,"CustAuthModRq"
                 ,"CustAuthRevRq"
                 ,"CustStatusModRq"
                 ,"CustDelRq"
                 ,"CustInqRq"
                 ,"CustIdInqRq"
                 ,"CustAudRq"
                 ,"CustSyncRq"
                 ,"CustSvcAddRq"
                 ,"CustSvcModRq"
                 ,"CustSvcStatusModRq"
                 ,"CustSvcDelRq"
                 ,"CustSvcAudRq"
                 ,"CustSvcSyncRq"
                 ,"SvcAcctAddRq"
                 ,"SvcAcctModRq"
                 ,"SvcAcctStatusModRq"
                 ,"SvcAcctIdModRq"
                 ,"SvcAcctDelRq"
                 ,"SvcAcctInqRq"
                 ,"SvcAcctAudRq"
                 ,"SvcAcctSyncRq"
                 ,"SvcAcctRevRq"
                 ,"DiscInqRq"
                 ,"CustDiscStatusModRq"
                 ,"CustDiscInqRq"
                 ,"SecObjAddRq"
                 ,"SecObjModRq"
                 ,"SecObjInqRq"
                 ,"SecObjDelRq"
                 ,"SecObjAdviseRq"
                 ,"SecObjSyncRq"
                 ,"PartyAcctRelAddRq"
                 ,"PartyAcctRelDelRq"
                 ,"PartyAcctRelInqRq"
                 ,"PartyAcctRelModRq"
                 ,"EMVCardAdviseRq"
                 ,"CardPrefAddRq"
                 ,"CardPrefModRq"
                 ,"CardPrefInqRq"
                 ,"CardPrefDelRq"
                 ,"CardPrefAudRq"
                 ,"CardPrefSyncRq"
                 ,"CardPrefRevRq"
                 ,"CardPrefStatusModRq"
                 ,"PostingSessionAddRq"
                 ,"PostingSessionModRq"
                 ,"PostingSessionInqRq"
                 ,"PostingSessionAudRq"
                 ,"PostingSessionStatusModRq"
                 ,"SessionTotalsInqRq"
          };
}
