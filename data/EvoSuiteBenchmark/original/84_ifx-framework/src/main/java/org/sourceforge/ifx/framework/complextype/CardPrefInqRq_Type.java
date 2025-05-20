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
public class CardPrefInqRq_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CardPrefInqRq_Type() {
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
    private org.sourceforge.ifx.framework.element.CardPrefId[] _cardPrefId;

    /** 
     * Setter for cardPrefId
     * @param cardPrefId the org.sourceforge.ifx.framework.element.CardPrefId[] to set
     */
    public void setCardPrefId(org.sourceforge.ifx.framework.element.CardPrefId[] _cardPrefId) {
        this._cardPrefId = _cardPrefId;
    }

    /**
     * Getter for cardPrefId
     * @return a org.sourceforge.ifx.framework.element.CardPrefId[]
     */
    public org.sourceforge.ifx.framework.element.CardPrefId[] getCardPrefId() {
        return _cardPrefId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardLogicalData[] _cardLogicalData;

    /** 
     * Setter for cardLogicalData
     * @param cardLogicalData the org.sourceforge.ifx.framework.element.CardLogicalData[] to set
     */
    public void setCardLogicalData(org.sourceforge.ifx.framework.element.CardLogicalData[] _cardLogicalData) {
        this._cardLogicalData = _cardLogicalData;
    }

    /**
     * Getter for cardLogicalData
     * @return a org.sourceforge.ifx.framework.element.CardLogicalData[]
     */
    public org.sourceforge.ifx.framework.element.CardLogicalData[] getCardLogicalData() {
        return _cardLogicalData;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardPrefType[] _cardPrefType;

    /** 
     * Setter for cardPrefType
     * @param cardPrefType the org.sourceforge.ifx.framework.element.CardPrefType[] to set
     */
    public void setCardPrefType(org.sourceforge.ifx.framework.element.CardPrefType[] _cardPrefType) {
        this._cardPrefType = _cardPrefType;
    }

    /**
     * Getter for cardPrefType
     * @return a org.sourceforge.ifx.framework.element.CardPrefType[]
     */
    public org.sourceforge.ifx.framework.element.CardPrefType[] getCardPrefType() {
        return _cardPrefType;
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
       * CardPrefId
       * CardLogicalData
       * CardPrefType
       */
    public final String[] ELEMENTS = {
              "RqUID"
                 ,"MsgRqHdr"
                 ,"AsyncRqUID"
                 ,"CustId"
                 ,"CardPrefId"
                 ,"CardLogicalData"
                 ,"CardPrefType"
          };
}
