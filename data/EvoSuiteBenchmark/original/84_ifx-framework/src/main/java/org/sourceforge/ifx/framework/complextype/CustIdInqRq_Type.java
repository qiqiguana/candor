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
public class CustIdInqRq_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CustIdInqRq_Type() {
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
    private org.sourceforge.ifx.framework.element.CustPermId _custPermId;

    /** 
     * Setter for custPermId
     * @param custPermId the org.sourceforge.ifx.framework.element.CustPermId to set
     */
    public void setCustPermId(org.sourceforge.ifx.framework.element.CustPermId _custPermId) {
        this._custPermId = _custPermId;
    }

    /**
     * Getter for custPermId
     * @return a org.sourceforge.ifx.framework.element.CustPermId
     */
    public org.sourceforge.ifx.framework.element.CustPermId getCustPermId() {
        return _custPermId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustLoginId _custLoginId;

    /** 
     * Setter for custLoginId
     * @param custLoginId the org.sourceforge.ifx.framework.element.CustLoginId to set
     */
    public void setCustLoginId(org.sourceforge.ifx.framework.element.CustLoginId _custLoginId) {
        this._custLoginId = _custLoginId;
    }

    /**
     * Getter for custLoginId
     * @return a org.sourceforge.ifx.framework.element.CustLoginId
     */
    public org.sourceforge.ifx.framework.element.CustLoginId getCustLoginId() {
        return _custLoginId;
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
       * CustPermId
       * CustLoginId
       */
    public final String[] ELEMENTS = {
              "RqUID"
                 ,"MsgRqHdr"
                 ,"AsyncRqUID"
                 ,"CustPermId"
                 ,"CustLoginId"
          };
}
