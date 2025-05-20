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
public class DepAppInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DepAppInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepApplicant[] _depApplicant;

    /** 
     * Setter for depApplicant
     * @param depApplicant the org.sourceforge.ifx.framework.element.DepApplicant[] to set
     */
    public void setDepApplicant(org.sourceforge.ifx.framework.element.DepApplicant[] _depApplicant) {
        this._depApplicant = _depApplicant;
    }

    /**
     * Getter for depApplicant
     * @return a org.sourceforge.ifx.framework.element.DepApplicant[]
     */
    public org.sourceforge.ifx.framework.element.DepApplicant[] getDepApplicant() {
        return _depApplicant;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAppAcctId[] _depAppAcctId;

    /** 
     * Setter for depAppAcctId
     * @param depAppAcctId the org.sourceforge.ifx.framework.element.DepAppAcctId[] to set
     */
    public void setDepAppAcctId(org.sourceforge.ifx.framework.element.DepAppAcctId[] _depAppAcctId) {
        this._depAppAcctId = _depAppAcctId;
    }

    /**
     * Getter for depAppAcctId
     * @return a org.sourceforge.ifx.framework.element.DepAppAcctId[]
     */
    public org.sourceforge.ifx.framework.element.DepAppAcctId[] getDepAppAcctId() {
        return _depAppAcctId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankAcctInfo[] _bankAcctInfo;

    /** 
     * Setter for bankAcctInfo
     * @param bankAcctInfo the org.sourceforge.ifx.framework.element.BankAcctInfo[] to set
     */
    public void setBankAcctInfo(org.sourceforge.ifx.framework.element.BankAcctInfo[] _bankAcctInfo) {
        this._bankAcctInfo = _bankAcctInfo;
    }

    /**
     * Getter for bankAcctInfo
     * @return a org.sourceforge.ifx.framework.element.BankAcctInfo[]
     */
    public org.sourceforge.ifx.framework.element.BankAcctInfo[] getBankAcctInfo() {
        return _bankAcctInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardType[] _cardType;

    /** 
     * Setter for cardType
     * @param cardType the org.sourceforge.ifx.framework.element.CardType[] to set
     */
    public void setCardType(org.sourceforge.ifx.framework.element.CardType[] _cardType) {
        this._cardType = _cardType;
    }

    /**
     * Getter for cardType
     * @return a org.sourceforge.ifx.framework.element.CardType[]
     */
    public org.sourceforge.ifx.framework.element.CardType[] getCardType() {
        return _cardType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.OverdraftFunding[] _overdraftFunding;

    /** 
     * Setter for overdraftFunding
     * @param overdraftFunding the org.sourceforge.ifx.framework.element.OverdraftFunding[] to set
     */
    public void setOverdraftFunding(org.sourceforge.ifx.framework.element.OverdraftFunding[] _overdraftFunding) {
        this._overdraftFunding = _overdraftFunding;
    }

    /**
     * Getter for overdraftFunding
     * @return a org.sourceforge.ifx.framework.element.OverdraftFunding[]
     */
    public org.sourceforge.ifx.framework.element.OverdraftFunding[] getOverdraftFunding() {
        return _overdraftFunding;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreatedDt[] _createdDt;

    /** 
     * Setter for createdDt
     * @param createdDt the org.sourceforge.ifx.framework.element.CreatedDt[] to set
     */
    public void setCreatedDt(org.sourceforge.ifx.framework.element.CreatedDt[] _createdDt) {
        this._createdDt = _createdDt;
    }

    /**
     * Getter for createdDt
     * @return a org.sourceforge.ifx.framework.element.CreatedDt[]
     */
    public org.sourceforge.ifx.framework.element.CreatedDt[] getCreatedDt() {
        return _createdDt;
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
       * DepApplicant
       * DepAppAcctId
       * BankAcctInfo
       * CardType
       * OverdraftFunding
       * CreatedDt
       */
    public final String[] ELEMENTS = {
              "DepApplicant"
                 ,"DepAppAcctId"
                 ,"BankAcctInfo"
                 ,"CardType"
                 ,"OverdraftFunding"
                 ,"CreatedDt"
          };
}
