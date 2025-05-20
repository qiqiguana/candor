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
public class BankSvcChkSumAddRq_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public BankSvcChkSumAddRq_Type() {
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
    private org.sourceforge.ifx.framework.element.SvcRqUID _svcRqUID;

    /** 
     * Setter for svcRqUID
     * @param svcRqUID the org.sourceforge.ifx.framework.element.SvcRqUID to set
     */
    public void setSvcRqUID(org.sourceforge.ifx.framework.element.SvcRqUID _svcRqUID) {
        this._svcRqUID = _svcRqUID;
    }

    /**
     * Getter for svcRqUID
     * @return a org.sourceforge.ifx.framework.element.SvcRqUID
     */
    public org.sourceforge.ifx.framework.element.SvcRqUID getSvcRqUID() {
        return _svcRqUID;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankSvcChkSumId _bankSvcChkSumId;

    /** 
     * Setter for bankSvcChkSumId
     * @param bankSvcChkSumId the org.sourceforge.ifx.framework.element.BankSvcChkSumId to set
     */
    public void setBankSvcChkSumId(org.sourceforge.ifx.framework.element.BankSvcChkSumId _bankSvcChkSumId) {
        this._bankSvcChkSumId = _bankSvcChkSumId;
    }

    /**
     * Getter for bankSvcChkSumId
     * @return a org.sourceforge.ifx.framework.element.BankSvcChkSumId
     */
    public org.sourceforge.ifx.framework.element.BankSvcChkSumId getBankSvcChkSumId() {
        return _bankSvcChkSumId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankSvcChkSumInfo _bankSvcChkSumInfo;

    /** 
     * Setter for bankSvcChkSumInfo
     * @param bankSvcChkSumInfo the org.sourceforge.ifx.framework.element.BankSvcChkSumInfo to set
     */
    public void setBankSvcChkSumInfo(org.sourceforge.ifx.framework.element.BankSvcChkSumInfo _bankSvcChkSumInfo) {
        this._bankSvcChkSumInfo = _bankSvcChkSumInfo;
    }

    /**
     * Getter for bankSvcChkSumInfo
     * @return a org.sourceforge.ifx.framework.element.BankSvcChkSumInfo
     */
    public org.sourceforge.ifx.framework.element.BankSvcChkSumInfo getBankSvcChkSumInfo() {
        return _bankSvcChkSumInfo;
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
       * SvcRqUID
       * BankSvcChkSumId
       * BankSvcChkSumInfo
       */
    public final String[] ELEMENTS = {
              "RqUID"
                 ,"MsgRqHdr"
                 ,"AsyncRqUID"
                 ,"SvcRqUID"
                 ,"BankSvcChkSumId"
                 ,"BankSvcChkSumInfo"
          };
}
