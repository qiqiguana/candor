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
public class CardPrefMsgRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CardPrefMsgRec_Type() {
        super();
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
    private org.sourceforge.ifx.framework.element.MsgRecDt _msgRecDt;

    /** 
     * Setter for msgRecDt
     * @param msgRecDt the org.sourceforge.ifx.framework.element.MsgRecDt to set
     */
    public void setMsgRecDt(org.sourceforge.ifx.framework.element.MsgRecDt _msgRecDt) {
        this._msgRecDt = _msgRecDt;
    }

    /**
     * Getter for msgRecDt
     * @return a org.sourceforge.ifx.framework.element.MsgRecDt
     */
    public org.sourceforge.ifx.framework.element.MsgRecDt getMsgRecDt() {
        return _msgRecDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardPrefAddRs _cardPrefAddRs;

    /** 
     * Setter for cardPrefAddRs
     * @param cardPrefAddRs the org.sourceforge.ifx.framework.element.CardPrefAddRs to set
     */
    public void setCardPrefAddRs(org.sourceforge.ifx.framework.element.CardPrefAddRs _cardPrefAddRs) {
        this._cardPrefAddRs = _cardPrefAddRs;
    }

    /**
     * Getter for cardPrefAddRs
     * @return a org.sourceforge.ifx.framework.element.CardPrefAddRs
     */
    public org.sourceforge.ifx.framework.element.CardPrefAddRs getCardPrefAddRs() {
        return _cardPrefAddRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardPrefModRs _cardPrefModRs;

    /** 
     * Setter for cardPrefModRs
     * @param cardPrefModRs the org.sourceforge.ifx.framework.element.CardPrefModRs to set
     */
    public void setCardPrefModRs(org.sourceforge.ifx.framework.element.CardPrefModRs _cardPrefModRs) {
        this._cardPrefModRs = _cardPrefModRs;
    }

    /**
     * Getter for cardPrefModRs
     * @return a org.sourceforge.ifx.framework.element.CardPrefModRs
     */
    public org.sourceforge.ifx.framework.element.CardPrefModRs getCardPrefModRs() {
        return _cardPrefModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardPrefDelRs _cardPrefDelRs;

    /** 
     * Setter for cardPrefDelRs
     * @param cardPrefDelRs the org.sourceforge.ifx.framework.element.CardPrefDelRs to set
     */
    public void setCardPrefDelRs(org.sourceforge.ifx.framework.element.CardPrefDelRs _cardPrefDelRs) {
        this._cardPrefDelRs = _cardPrefDelRs;
    }

    /**
     * Getter for cardPrefDelRs
     * @return a org.sourceforge.ifx.framework.element.CardPrefDelRs
     */
    public org.sourceforge.ifx.framework.element.CardPrefDelRs getCardPrefDelRs() {
        return _cardPrefDelRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardPrefRevRs _cardPrefRevRs;

    /** 
     * Setter for cardPrefRevRs
     * @param cardPrefRevRs the org.sourceforge.ifx.framework.element.CardPrefRevRs to set
     */
    public void setCardPrefRevRs(org.sourceforge.ifx.framework.element.CardPrefRevRs _cardPrefRevRs) {
        this._cardPrefRevRs = _cardPrefRevRs;
    }

    /**
     * Getter for cardPrefRevRs
     * @return a org.sourceforge.ifx.framework.element.CardPrefRevRs
     */
    public org.sourceforge.ifx.framework.element.CardPrefRevRs getCardPrefRevRs() {
        return _cardPrefRevRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardPrefStatusModRs _cardPrefStatusModRs;

    /** 
     * Setter for cardPrefStatusModRs
     * @param cardPrefStatusModRs the org.sourceforge.ifx.framework.element.CardPrefStatusModRs to set
     */
    public void setCardPrefStatusModRs(org.sourceforge.ifx.framework.element.CardPrefStatusModRs _cardPrefStatusModRs) {
        this._cardPrefStatusModRs = _cardPrefStatusModRs;
    }

    /**
     * Getter for cardPrefStatusModRs
     * @return a org.sourceforge.ifx.framework.element.CardPrefStatusModRs
     */
    public org.sourceforge.ifx.framework.element.CardPrefStatusModRs getCardPrefStatusModRs() {
        return _cardPrefStatusModRs;
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
       * CustId
       * MsgRecDt
       * CardPrefAddRs
       * CardPrefModRs
       * CardPrefDelRs
       * CardPrefRevRs
       * CardPrefStatusModRs
       */
    public final String[] ELEMENTS = {
              "CustId"
                 ,"MsgRecDt"
                 ,"CardPrefAddRs"
                 ,"CardPrefModRs"
                 ,"CardPrefDelRs"
                 ,"CardPrefRevRs"
                 ,"CardPrefStatusModRs"
          };
}
