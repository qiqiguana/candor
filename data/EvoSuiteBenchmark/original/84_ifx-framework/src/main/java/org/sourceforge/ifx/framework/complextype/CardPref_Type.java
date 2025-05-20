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
public class CardPref_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CardPref_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardPrefMisc _cardPrefMisc;

    /** 
     * Setter for cardPrefMisc
     * @param cardPrefMisc the org.sourceforge.ifx.framework.element.CardPrefMisc to set
     */
    public void setCardPrefMisc(org.sourceforge.ifx.framework.element.CardPrefMisc _cardPrefMisc) {
        this._cardPrefMisc = _cardPrefMisc;
    }

    /**
     * Getter for cardPrefMisc
     * @return a org.sourceforge.ifx.framework.element.CardPrefMisc
     */
    public org.sourceforge.ifx.framework.element.CardPrefMisc getCardPrefMisc() {
        return _cardPrefMisc;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardPrefWithdrawal _cardPrefWithdrawal;

    /** 
     * Setter for cardPrefWithdrawal
     * @param cardPrefWithdrawal the org.sourceforge.ifx.framework.element.CardPrefWithdrawal to set
     */
    public void setCardPrefWithdrawal(org.sourceforge.ifx.framework.element.CardPrefWithdrawal _cardPrefWithdrawal) {
        this._cardPrefWithdrawal = _cardPrefWithdrawal;
    }

    /**
     * Getter for cardPrefWithdrawal
     * @return a org.sourceforge.ifx.framework.element.CardPrefWithdrawal
     */
    public org.sourceforge.ifx.framework.element.CardPrefWithdrawal getCardPrefWithdrawal() {
        return _cardPrefWithdrawal;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardPrefDeposit _cardPrefDeposit;

    /** 
     * Setter for cardPrefDeposit
     * @param cardPrefDeposit the org.sourceforge.ifx.framework.element.CardPrefDeposit to set
     */
    public void setCardPrefDeposit(org.sourceforge.ifx.framework.element.CardPrefDeposit _cardPrefDeposit) {
        this._cardPrefDeposit = _cardPrefDeposit;
    }

    /**
     * Getter for cardPrefDeposit
     * @return a org.sourceforge.ifx.framework.element.CardPrefDeposit
     */
    public org.sourceforge.ifx.framework.element.CardPrefDeposit getCardPrefDeposit() {
        return _cardPrefDeposit;
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
       * CardPrefMisc
       * CardPrefWithdrawal
       * CardPrefDeposit
       */
    public final String[] ELEMENTS = {
              "CardPrefMisc"
                 ,"CardPrefWithdrawal"
                 ,"CardPrefDeposit"
          };
}
