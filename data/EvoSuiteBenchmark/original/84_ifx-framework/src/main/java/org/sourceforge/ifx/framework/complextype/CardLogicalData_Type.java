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
public class CardLogicalData_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CardLogicalData_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardEmbossNum _cardEmbossNum;

    /** 
     * Setter for cardEmbossNum
     * @param cardEmbossNum the org.sourceforge.ifx.framework.element.CardEmbossNum to set
     */
    public void setCardEmbossNum(org.sourceforge.ifx.framework.element.CardEmbossNum _cardEmbossNum) {
        this._cardEmbossNum = _cardEmbossNum;
    }

    /**
     * Getter for cardEmbossNum
     * @return a org.sourceforge.ifx.framework.element.CardEmbossNum
     */
    public org.sourceforge.ifx.framework.element.CardEmbossNum getCardEmbossNum() {
        return _cardEmbossNum;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardSeqNum _cardSeqNum;

    /** 
     * Setter for cardSeqNum
     * @param cardSeqNum the org.sourceforge.ifx.framework.element.CardSeqNum to set
     */
    public void setCardSeqNum(org.sourceforge.ifx.framework.element.CardSeqNum _cardSeqNum) {
        this._cardSeqNum = _cardSeqNum;
    }

    /**
     * Getter for cardSeqNum
     * @return a org.sourceforge.ifx.framework.element.CardSeqNum
     */
    public org.sourceforge.ifx.framework.element.CardSeqNum getCardSeqNum() {
        return _cardSeqNum;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Brand _brand;

    /** 
     * Setter for brand
     * @param brand the org.sourceforge.ifx.framework.element.Brand to set
     */
    public void setBrand(org.sourceforge.ifx.framework.element.Brand _brand) {
        this._brand = _brand;
    }

    /**
     * Getter for brand
     * @return a org.sourceforge.ifx.framework.element.Brand
     */
    public org.sourceforge.ifx.framework.element.Brand getBrand() {
        return _brand;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Technology _technology;

    /** 
     * Setter for technology
     * @param technology the org.sourceforge.ifx.framework.element.Technology to set
     */
    public void setTechnology(org.sourceforge.ifx.framework.element.Technology _technology) {
        this._technology = _technology;
    }

    /**
     * Getter for technology
     * @return a org.sourceforge.ifx.framework.element.Technology
     */
    public org.sourceforge.ifx.framework.element.Technology getTechnology() {
        return _technology;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ExpDt _expDt;

    /** 
     * Setter for expDt
     * @param expDt the org.sourceforge.ifx.framework.element.ExpDt to set
     */
    public void setExpDt(org.sourceforge.ifx.framework.element.ExpDt _expDt) {
        this._expDt = _expDt;
    }

    /**
     * Getter for expDt
     * @return a org.sourceforge.ifx.framework.element.ExpDt
     */
    public org.sourceforge.ifx.framework.element.ExpDt getExpDt() {
        return _expDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardVrfyData _cardVrfyData;

    /** 
     * Setter for cardVrfyData
     * @param cardVrfyData the org.sourceforge.ifx.framework.element.CardVrfyData to set
     */
    public void setCardVrfyData(org.sourceforge.ifx.framework.element.CardVrfyData _cardVrfyData) {
        this._cardVrfyData = _cardVrfyData;
    }

    /**
     * Getter for cardVrfyData
     * @return a org.sourceforge.ifx.framework.element.CardVrfyData
     */
    public org.sourceforge.ifx.framework.element.CardVrfyData getCardVrfyData() {
        return _cardVrfyData;
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

    // property declaration 
    private org.sourceforge.ifx.framework.element.CSPhoneNum _cSPhoneNum;

    /** 
     * Setter for cSPhoneNum
     * @param cSPhoneNum the org.sourceforge.ifx.framework.element.CSPhoneNum to set
     */
    public void setCSPhoneNum(org.sourceforge.ifx.framework.element.CSPhoneNum _cSPhoneNum) {
        this._cSPhoneNum = _cSPhoneNum;
    }

    /**
     * Getter for cSPhoneNum
     * @return a org.sourceforge.ifx.framework.element.CSPhoneNum
     */
    public org.sourceforge.ifx.framework.element.CSPhoneNum getCSPhoneNum() {
        return _cSPhoneNum;
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
       * CardEmbossNum
       * CardSeqNum
       * Brand
       * Technology
       * ExpDt
       * CardVrfyData
       * Name
       * CSPhoneNum
       */
    public final String[] ELEMENTS = {
              "CardEmbossNum"
                 ,"CardSeqNum"
                 ,"Brand"
                 ,"Technology"
                 ,"ExpDt"
                 ,"CardVrfyData"
                 ,"Name"
                 ,"CSPhoneNum"
          };
}
