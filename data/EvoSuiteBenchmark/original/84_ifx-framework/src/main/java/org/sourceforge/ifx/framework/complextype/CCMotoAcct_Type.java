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
public class CCMotoAcct_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CCMotoAcct_Type() {
        super();
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
    private org.sourceforge.ifx.framework.element.PostAddr _postAddr;

    /** 
     * Setter for postAddr
     * @param postAddr the org.sourceforge.ifx.framework.element.PostAddr to set
     */
    public void setPostAddr(org.sourceforge.ifx.framework.element.PostAddr _postAddr) {
        this._postAddr = _postAddr;
    }

    /**
     * Getter for postAddr
     * @return a org.sourceforge.ifx.framework.element.PostAddr
     */
    public org.sourceforge.ifx.framework.element.PostAddr getPostAddr() {
        return _postAddr;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PostalCode _postalCode;

    /** 
     * Setter for postalCode
     * @param postalCode the org.sourceforge.ifx.framework.element.PostalCode to set
     */
    public void setPostalCode(org.sourceforge.ifx.framework.element.PostalCode _postalCode) {
        this._postalCode = _postalCode;
    }

    /**
     * Getter for postalCode
     * @return a org.sourceforge.ifx.framework.element.PostalCode
     */
    public org.sourceforge.ifx.framework.element.PostalCode getPostalCode() {
        return _postalCode;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Phone _phone;

    /** 
     * Setter for phone
     * @param phone the org.sourceforge.ifx.framework.element.Phone to set
     */
    public void setPhone(org.sourceforge.ifx.framework.element.Phone _phone) {
        this._phone = _phone;
    }

    /**
     * Getter for phone
     * @return a org.sourceforge.ifx.framework.element.Phone
     */
    public org.sourceforge.ifx.framework.element.Phone getPhone() {
        return _phone;
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


    /**
     * Returns true if objects are equal, false otherwise.
     * @param obj the object to compare with.
     * @return true if equal, false if not.
     */
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /** Element ordering - 
       * ExpDt
       * CardVrfyData
       * Name
       * PostAddr
       * PostalCode
       * Phone
       * Brand
       */
    public final String[] ELEMENTS = {
              "ExpDt"
                 ,"CardVrfyData"
                 ,"Name"
                 ,"PostAddr"
                 ,"PostalCode"
                 ,"Phone"
                 ,"Brand"
          };
}
