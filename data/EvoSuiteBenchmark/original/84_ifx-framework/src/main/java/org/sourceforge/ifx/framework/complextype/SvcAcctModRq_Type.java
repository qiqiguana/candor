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
public class SvcAcctModRq_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public SvcAcctModRq_Type() {
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
    private org.sourceforge.ifx.framework.element.SvcAcctId _svcAcctId;

    /** 
     * Setter for svcAcctId
     * @param svcAcctId the org.sourceforge.ifx.framework.element.SvcAcctId to set
     */
    public void setSvcAcctId(org.sourceforge.ifx.framework.element.SvcAcctId _svcAcctId) {
        this._svcAcctId = _svcAcctId;
    }

    /**
     * Getter for svcAcctId
     * @return a org.sourceforge.ifx.framework.element.SvcAcctId
     */
    public org.sourceforge.ifx.framework.element.SvcAcctId getSvcAcctId() {
        return _svcAcctId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SvcAcctInfo _svcAcctInfo;

    /** 
     * Setter for svcAcctInfo
     * @param svcAcctInfo the org.sourceforge.ifx.framework.element.SvcAcctInfo to set
     */
    public void setSvcAcctInfo(org.sourceforge.ifx.framework.element.SvcAcctInfo _svcAcctInfo) {
        this._svcAcctInfo = _svcAcctInfo;
    }

    /**
     * Getter for svcAcctInfo
     * @return a org.sourceforge.ifx.framework.element.SvcAcctInfo
     */
    public org.sourceforge.ifx.framework.element.SvcAcctInfo getSvcAcctInfo() {
        return _svcAcctInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SecretList[] _secretList;

    /** 
     * Setter for secretList
     * @param secretList the org.sourceforge.ifx.framework.element.SecretList[] to set
     */
    public void setSecretList(org.sourceforge.ifx.framework.element.SecretList[] _secretList) {
        this._secretList = _secretList;
    }

    /**
     * Getter for secretList
     * @return a org.sourceforge.ifx.framework.element.SecretList[]
     */
    public org.sourceforge.ifx.framework.element.SecretList[] getSecretList() {
        return _secretList;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CryptType[] _cryptType;

    /** 
     * Setter for cryptType
     * @param cryptType the org.sourceforge.ifx.framework.element.CryptType[] to set
     */
    public void setCryptType(org.sourceforge.ifx.framework.element.CryptType[] _cryptType) {
        this._cryptType = _cryptType;
    }

    /**
     * Getter for cryptType
     * @return a org.sourceforge.ifx.framework.element.CryptType[]
     */
    public org.sourceforge.ifx.framework.element.CryptType[] getCryptType() {
        return _cryptType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SecObjId[] _secObjId;

    /** 
     * Setter for secObjId
     * @param secObjId the org.sourceforge.ifx.framework.element.SecObjId[] to set
     */
    public void setSecObjId(org.sourceforge.ifx.framework.element.SecObjId[] _secObjId) {
        this._secObjId = _secObjId;
    }

    /**
     * Getter for secObjId
     * @return a org.sourceforge.ifx.framework.element.SecObjId[]
     */
    public org.sourceforge.ifx.framework.element.SecObjId[] getSecObjId() {
        return _secObjId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustNameAddr[] _custNameAddr;

    /** 
     * Setter for custNameAddr
     * @param custNameAddr the org.sourceforge.ifx.framework.element.CustNameAddr[] to set
     */
    public void setCustNameAddr(org.sourceforge.ifx.framework.element.CustNameAddr[] _custNameAddr) {
        this._custNameAddr = _custNameAddr;
    }

    /**
     * Getter for custNameAddr
     * @return a org.sourceforge.ifx.framework.element.CustNameAddr[]
     */
    public org.sourceforge.ifx.framework.element.CustNameAddr[] getCustNameAddr() {
        return _custNameAddr;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PersonInfo[] _personInfo;

    /** 
     * Setter for personInfo
     * @param personInfo the org.sourceforge.ifx.framework.element.PersonInfo[] to set
     */
    public void setPersonInfo(org.sourceforge.ifx.framework.element.PersonInfo[] _personInfo) {
        this._personInfo = _personInfo;
    }

    /**
     * Getter for personInfo
     * @return a org.sourceforge.ifx.framework.element.PersonInfo[]
     */
    public org.sourceforge.ifx.framework.element.PersonInfo[] getPersonInfo() {
        return _personInfo;
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
       * SvcAcctId
       * SvcAcctInfo
       * SecretList
       * CryptType
       * SecObjId
       * CustNameAddr
       * PersonInfo
       */
    public final String[] ELEMENTS = {
              "RqUID"
                 ,"MsgRqHdr"
                 ,"AsyncRqUID"
                 ,"CustId"
                 ,"SvcAcctId"
                 ,"SvcAcctInfo"
                 ,"SecretList"
                 ,"CryptType"
                 ,"SecObjId"
                 ,"CustNameAddr"
                 ,"PersonInfo"
          };
}
