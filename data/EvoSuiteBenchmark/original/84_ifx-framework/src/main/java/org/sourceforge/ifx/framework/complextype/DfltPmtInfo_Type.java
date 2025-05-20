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
public class DfltPmtInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DfltPmtInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAcctIdFrom _depAcctIdFrom;

    /** 
     * Setter for depAcctIdFrom
     * @param depAcctIdFrom the org.sourceforge.ifx.framework.element.DepAcctIdFrom to set
     */
    public void setDepAcctIdFrom(org.sourceforge.ifx.framework.element.DepAcctIdFrom _depAcctIdFrom) {
        this._depAcctIdFrom = _depAcctIdFrom;
    }

    /**
     * Getter for depAcctIdFrom
     * @return a org.sourceforge.ifx.framework.element.DepAcctIdFrom
     */
    public org.sourceforge.ifx.framework.element.DepAcctIdFrom getDepAcctIdFrom() {
        return _depAcctIdFrom;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardAcctIdFrom _cardAcctIdFrom;

    /** 
     * Setter for cardAcctIdFrom
     * @param cardAcctIdFrom the org.sourceforge.ifx.framework.element.CardAcctIdFrom to set
     */
    public void setCardAcctIdFrom(org.sourceforge.ifx.framework.element.CardAcctIdFrom _cardAcctIdFrom) {
        this._cardAcctIdFrom = _cardAcctIdFrom;
    }

    /**
     * Getter for cardAcctIdFrom
     * @return a org.sourceforge.ifx.framework.element.CardAcctIdFrom
     */
    public org.sourceforge.ifx.framework.element.CardAcctIdFrom getCardAcctIdFrom() {
        return _cardAcctIdFrom;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Category _category;

    /** 
     * Setter for category
     * @param category the org.sourceforge.ifx.framework.element.Category to set
     */
    public void setCategory(org.sourceforge.ifx.framework.element.Category _category) {
        this._category = _category;
    }

    /**
     * Getter for category
     * @return a org.sourceforge.ifx.framework.element.Category
     */
    public org.sourceforge.ifx.framework.element.Category getCategory() {
        return _category;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Memo _memo;

    /** 
     * Setter for memo
     * @param memo the org.sourceforge.ifx.framework.element.Memo to set
     */
    public void setMemo(org.sourceforge.ifx.framework.element.Memo _memo) {
        this._memo = _memo;
    }

    /**
     * Getter for memo
     * @return a org.sourceforge.ifx.framework.element.Memo
     */
    public org.sourceforge.ifx.framework.element.Memo getMemo() {
        return _memo;
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
       * DepAcctIdFrom
       * CardAcctIdFrom
       * Category
       * Memo
       */
    public final String[] ELEMENTS = {
              "DepAcctIdFrom"
                 ,"CardAcctIdFrom"
                 ,"Category"
                 ,"Memo"
          };
}
