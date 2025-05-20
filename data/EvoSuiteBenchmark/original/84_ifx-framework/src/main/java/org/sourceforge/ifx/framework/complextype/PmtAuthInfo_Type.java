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
public class PmtAuthInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PmtAuthInfo_Type() {
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
    private org.sourceforge.ifx.framework.element.CurAmt _curAmt;

    /** 
     * Setter for curAmt
     * @param curAmt the org.sourceforge.ifx.framework.element.CurAmt to set
     */
    public void setCurAmt(org.sourceforge.ifx.framework.element.CurAmt _curAmt) {
        this._curAmt = _curAmt;
    }

    /**
     * Getter for curAmt
     * @return a org.sourceforge.ifx.framework.element.CurAmt
     */
    public org.sourceforge.ifx.framework.element.CurAmt getCurAmt() {
        return _curAmt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Name _name;

    /** 
     * Setter for name
     * @param name the org.sourceforge.ifx.framework.element.Name to set
     */
    public void setName(org.sourceforge.ifx.framework.element.Name _name) {
        this._name = _name;
    }

    /**
     * Getter for name
     * @return a org.sourceforge.ifx.framework.element.Name
     */
    public org.sourceforge.ifx.framework.element.Name getName() {
        return _name;
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
       * CurAmt
       * Name
       */
    public final String[] ELEMENTS = {
              "DepAcctIdFrom"
                 ,"CardAcctIdFrom"
                 ,"CurAmt"
                 ,"Name"
          };
}
