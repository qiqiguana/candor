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
public class SignonRq_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public SignonRq_Type() {
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
    private org.sourceforge.ifx.framework.element.SignonPswd _signonPswd;

    /** 
     * Setter for signonPswd
     * @param signonPswd the org.sourceforge.ifx.framework.element.SignonPswd to set
     */
    public void setSignonPswd(org.sourceforge.ifx.framework.element.SignonPswd _signonPswd) {
        this._signonPswd = _signonPswd;
    }

    /**
     * Getter for signonPswd
     * @return a org.sourceforge.ifx.framework.element.SignonPswd
     */
    public org.sourceforge.ifx.framework.element.SignonPswd getSignonPswd() {
        return _signonPswd;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SignonCert _signonCert;

    /** 
     * Setter for signonCert
     * @param signonCert the org.sourceforge.ifx.framework.element.SignonCert to set
     */
    public void setSignonCert(org.sourceforge.ifx.framework.element.SignonCert _signonCert) {
        this._signonCert = _signonCert;
    }

    /**
     * Getter for signonCert
     * @return a org.sourceforge.ifx.framework.element.SignonCert
     */
    public org.sourceforge.ifx.framework.element.SignonCert getSignonCert() {
        return _signonCert;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SignonTransport _signonTransport;

    /** 
     * Setter for signonTransport
     * @param signonTransport the org.sourceforge.ifx.framework.element.SignonTransport to set
     */
    public void setSignonTransport(org.sourceforge.ifx.framework.element.SignonTransport _signonTransport) {
        this._signonTransport = _signonTransport;
    }

    /**
     * Getter for signonTransport
     * @return a org.sourceforge.ifx.framework.element.SignonTransport
     */
    public org.sourceforge.ifx.framework.element.SignonTransport getSignonTransport() {
        return _signonTransport;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SignonMagPIN _signonMagPIN;

    /** 
     * Setter for signonMagPIN
     * @param signonMagPIN the org.sourceforge.ifx.framework.element.SignonMagPIN to set
     */
    public void setSignonMagPIN(org.sourceforge.ifx.framework.element.SignonMagPIN _signonMagPIN) {
        this._signonMagPIN = _signonMagPIN;
    }

    /**
     * Getter for signonMagPIN
     * @return a org.sourceforge.ifx.framework.element.SignonMagPIN
     */
    public org.sourceforge.ifx.framework.element.SignonMagPIN getSignonMagPIN() {
        return _signonMagPIN;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SignonICC _signonICC;

    /** 
     * Setter for signonICC
     * @param signonICC the org.sourceforge.ifx.framework.element.SignonICC to set
     */
    public void setSignonICC(org.sourceforge.ifx.framework.element.SignonICC _signonICC) {
        this._signonICC = _signonICC;
    }

    /**
     * Getter for signonICC
     * @return a org.sourceforge.ifx.framework.element.SignonICC
     */
    public org.sourceforge.ifx.framework.element.SignonICC getSignonICC() {
        return _signonICC;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SessKey _sessKey;

    /** 
     * Setter for sessKey
     * @param sessKey the org.sourceforge.ifx.framework.element.SessKey to set
     */
    public void setSessKey(org.sourceforge.ifx.framework.element.SessKey _sessKey) {
        this._sessKey = _sessKey;
    }

    /**
     * Getter for sessKey
     * @return a org.sourceforge.ifx.framework.element.SessKey
     */
    public org.sourceforge.ifx.framework.element.SessKey getSessKey() {
        return _sessKey;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SignonOverride _signonOverride;

    /** 
     * Setter for signonOverride
     * @param signonOverride the org.sourceforge.ifx.framework.element.SignonOverride to set
     */
    public void setSignonOverride(org.sourceforge.ifx.framework.element.SignonOverride _signonOverride) {
        this._signonOverride = _signonOverride;
    }

    /**
     * Getter for signonOverride
     * @return a org.sourceforge.ifx.framework.element.SignonOverride
     */
    public org.sourceforge.ifx.framework.element.SignonOverride getSignonOverride() {
        return _signonOverride;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ClientDt _clientDt;

    /** 
     * Setter for clientDt
     * @param clientDt the org.sourceforge.ifx.framework.element.ClientDt to set
     */
    public void setClientDt(org.sourceforge.ifx.framework.element.ClientDt _clientDt) {
        this._clientDt = _clientDt;
    }

    /**
     * Getter for clientDt
     * @return a org.sourceforge.ifx.framework.element.ClientDt
     */
    public org.sourceforge.ifx.framework.element.ClientDt getClientDt() {
        return _clientDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustLangPref _custLangPref;

    /** 
     * Setter for custLangPref
     * @param custLangPref the org.sourceforge.ifx.framework.element.CustLangPref to set
     */
    public void setCustLangPref(org.sourceforge.ifx.framework.element.CustLangPref _custLangPref) {
        this._custLangPref = _custLangPref;
    }

    /**
     * Getter for custLangPref
     * @return a org.sourceforge.ifx.framework.element.CustLangPref
     */
    public org.sourceforge.ifx.framework.element.CustLangPref getCustLangPref() {
        return _custLangPref;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ClientApp _clientApp;

    /** 
     * Setter for clientApp
     * @param clientApp the org.sourceforge.ifx.framework.element.ClientApp to set
     */
    public void setClientApp(org.sourceforge.ifx.framework.element.ClientApp _clientApp) {
        this._clientApp = _clientApp;
    }

    /**
     * Getter for clientApp
     * @return a org.sourceforge.ifx.framework.element.ClientApp
     */
    public org.sourceforge.ifx.framework.element.ClientApp getClientApp() {
        return _clientApp;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ProxyClient _proxyClient;

    /** 
     * Setter for proxyClient
     * @param proxyClient the org.sourceforge.ifx.framework.element.ProxyClient to set
     */
    public void setProxyClient(org.sourceforge.ifx.framework.element.ProxyClient _proxyClient) {
        this._proxyClient = _proxyClient;
    }

    /**
     * Getter for proxyClient
     * @return a org.sourceforge.ifx.framework.element.ProxyClient
     */
    public org.sourceforge.ifx.framework.element.ProxyClient getProxyClient() {
        return _proxyClient;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.EU_Cur _eU_Cur;

    /** 
     * Setter for eU_Cur
     * @param eU_Cur the org.sourceforge.ifx.framework.element.EU_Cur to set
     */
    public void setEU_Cur(org.sourceforge.ifx.framework.element.EU_Cur _eU_Cur) {
        this._eU_Cur = _eU_Cur;
    }

    /**
     * Getter for eU_Cur
     * @return a org.sourceforge.ifx.framework.element.EU_Cur
     */
    public org.sourceforge.ifx.framework.element.EU_Cur getEU_Cur() {
        return _eU_Cur;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SuppressEcho _suppressEcho;

    /** 
     * Setter for suppressEcho
     * @param suppressEcho the org.sourceforge.ifx.framework.element.SuppressEcho to set
     */
    public void setSuppressEcho(org.sourceforge.ifx.framework.element.SuppressEcho _suppressEcho) {
        this._suppressEcho = _suppressEcho;
    }

    /**
     * Getter for suppressEcho
     * @return a org.sourceforge.ifx.framework.element.SuppressEcho
     */
    public org.sourceforge.ifx.framework.element.SuppressEcho getSuppressEcho() {
        return _suppressEcho;
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
       * SignonPswd
       * SignonCert
       * SignonTransport
       * SignonMagPIN
       * SignonICC
       * SessKey
       * SignonOverride
       * ClientDt
       * CustLangPref
       * ClientApp
       * ProxyClient
       * EU_Cur
       * SuppressEcho
       */
    public final String[] ELEMENTS = {
              "Id"
                 ,"SignonPswd"
                 ,"SignonCert"
                 ,"SignonTransport"
                 ,"SignonMagPIN"
                 ,"SignonICC"
                 ,"SessKey"
                 ,"SignonOverride"
                 ,"ClientDt"
                 ,"CustLangPref"
                 ,"ClientApp"
                 ,"ProxyClient"
                 ,"EU_Cur"
                 ,"SuppressEcho"
          };
}
