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
public class DepAcctTrnAdviseRq_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DepAcctTrnAdviseRq_Type() {
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
    private org.sourceforge.ifx.framework.element.DepAcctIdTo _depAcctIdTo;

    /** 
     * Setter for depAcctIdTo
     * @param depAcctIdTo the org.sourceforge.ifx.framework.element.DepAcctIdTo to set
     */
    public void setDepAcctIdTo(org.sourceforge.ifx.framework.element.DepAcctIdTo _depAcctIdTo) {
        this._depAcctIdTo = _depAcctIdTo;
    }

    /**
     * Getter for depAcctIdTo
     * @return a org.sourceforge.ifx.framework.element.DepAcctIdTo
     */
    public org.sourceforge.ifx.framework.element.DepAcctIdTo getDepAcctIdTo() {
        return _depAcctIdTo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardAcctIdTo _cardAcctIdTo;

    /** 
     * Setter for cardAcctIdTo
     * @param cardAcctIdTo the org.sourceforge.ifx.framework.element.CardAcctIdTo to set
     */
    public void setCardAcctIdTo(org.sourceforge.ifx.framework.element.CardAcctIdTo _cardAcctIdTo) {
        this._cardAcctIdTo = _cardAcctIdTo;
    }

    /**
     * Getter for cardAcctIdTo
     * @return a org.sourceforge.ifx.framework.element.CardAcctIdTo
     */
    public org.sourceforge.ifx.framework.element.CardAcctIdTo getCardAcctIdTo() {
        return _cardAcctIdTo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAcctTrnRec _depAcctTrnRec;

    /** 
     * Setter for depAcctTrnRec
     * @param depAcctTrnRec the org.sourceforge.ifx.framework.element.DepAcctTrnRec to set
     */
    public void setDepAcctTrnRec(org.sourceforge.ifx.framework.element.DepAcctTrnRec _depAcctTrnRec) {
        this._depAcctTrnRec = _depAcctTrnRec;
    }

    /**
     * Getter for depAcctTrnRec
     * @return a org.sourceforge.ifx.framework.element.DepAcctTrnRec
     */
    public org.sourceforge.ifx.framework.element.DepAcctTrnRec getDepAcctTrnRec() {
        return _depAcctTrnRec;
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
       * CustId
       * DepAcctIdTo
       * CardAcctIdTo
       * DepAcctTrnRec
       */
    public final String[] ELEMENTS = {
              "RqUID"
                 ,"MsgRqHdr"
                 ,"CustId"
                 ,"DepAcctIdTo"
                 ,"CardAcctIdTo"
                 ,"DepAcctTrnRec"
          };
}
