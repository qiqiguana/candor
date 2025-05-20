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
public class CardPrefRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CardPrefRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardPrefId _cardPrefId;

    /** 
     * Setter for cardPrefId
     * @param cardPrefId the org.sourceforge.ifx.framework.element.CardPrefId to set
     */
    public void setCardPrefId(org.sourceforge.ifx.framework.element.CardPrefId _cardPrefId) {
        this._cardPrefId = _cardPrefId;
    }

    /**
     * Getter for cardPrefId
     * @return a org.sourceforge.ifx.framework.element.CardPrefId
     */
    public org.sourceforge.ifx.framework.element.CardPrefId getCardPrefId() {
        return _cardPrefId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardPrefInfo _cardPrefInfo;

    /** 
     * Setter for cardPrefInfo
     * @param cardPrefInfo the org.sourceforge.ifx.framework.element.CardPrefInfo to set
     */
    public void setCardPrefInfo(org.sourceforge.ifx.framework.element.CardPrefInfo _cardPrefInfo) {
        this._cardPrefInfo = _cardPrefInfo;
    }

    /**
     * Getter for cardPrefInfo
     * @return a org.sourceforge.ifx.framework.element.CardPrefInfo
     */
    public org.sourceforge.ifx.framework.element.CardPrefInfo getCardPrefInfo() {
        return _cardPrefInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardPrefStatus _cardPrefStatus;

    /** 
     * Setter for cardPrefStatus
     * @param cardPrefStatus the org.sourceforge.ifx.framework.element.CardPrefStatus to set
     */
    public void setCardPrefStatus(org.sourceforge.ifx.framework.element.CardPrefStatus _cardPrefStatus) {
        this._cardPrefStatus = _cardPrefStatus;
    }

    /**
     * Getter for cardPrefStatus
     * @return a org.sourceforge.ifx.framework.element.CardPrefStatus
     */
    public org.sourceforge.ifx.framework.element.CardPrefStatus getCardPrefStatus() {
        return _cardPrefStatus;
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
       * CardPrefId
       * CardPrefInfo
       * CardPrefStatus
       */
    public final String[] ELEMENTS = {
              "CardPrefId"
                 ,"CardPrefInfo"
                 ,"CardPrefStatus"
          };
}
