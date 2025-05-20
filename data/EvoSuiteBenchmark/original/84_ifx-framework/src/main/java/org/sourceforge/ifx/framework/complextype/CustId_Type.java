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
public class CustId_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CustId_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SPName _sPName;

    /** 
     * Setter for sPName
     * @param sPName the org.sourceforge.ifx.framework.element.SPName to set
     */
    public void setSPName(org.sourceforge.ifx.framework.element.SPName _sPName) {
        this._sPName = _sPName;
    }

    /**
     * Getter for sPName
     * @return a org.sourceforge.ifx.framework.element.SPName
     */
    public org.sourceforge.ifx.framework.element.SPName getSPName() {
        return _sPName;
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

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardMagData _cardMagData;

    /** 
     * Setter for cardMagData
     * @param cardMagData the org.sourceforge.ifx.framework.element.CardMagData to set
     */
    public void setCardMagData(org.sourceforge.ifx.framework.element.CardMagData _cardMagData) {
        this._cardMagData = _cardMagData;
    }

    /**
     * Getter for cardMagData
     * @return a org.sourceforge.ifx.framework.element.CardMagData
     */
    public org.sourceforge.ifx.framework.element.CardMagData getCardMagData() {
        return _cardMagData;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardLogicalData _cardLogicalData;

    /** 
     * Setter for cardLogicalData
     * @param cardLogicalData the org.sourceforge.ifx.framework.element.CardLogicalData to set
     */
    public void setCardLogicalData(org.sourceforge.ifx.framework.element.CardLogicalData _cardLogicalData) {
        this._cardLogicalData = _cardLogicalData;
    }

    /**
     * Getter for cardLogicalData
     * @return a org.sourceforge.ifx.framework.element.CardLogicalData
     */
    public org.sourceforge.ifx.framework.element.CardLogicalData getCardLogicalData() {
        return _cardLogicalData;
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
       * SPName
       * CustPermId
       * CustLoginId
       * CardMagData
       * CardLogicalData
       */
    public final String[] ELEMENTS = {
              "SPName"
                 ,"CustPermId"
                 ,"CustLoginId"
                 ,"CardMagData"
                 ,"CardLogicalData"
          };
}
