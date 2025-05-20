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
public class CardAcctId_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CardAcctId_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AcctId _acctId;

    /** 
     * Setter for acctId
     * @param acctId the org.sourceforge.ifx.framework.element.AcctId to set
     */
    public void setAcctId(org.sourceforge.ifx.framework.element.AcctId _acctId) {
        this._acctId = _acctId;
    }

    /**
     * Getter for acctId
     * @return a org.sourceforge.ifx.framework.element.AcctId
     */
    public org.sourceforge.ifx.framework.element.AcctId getAcctId() {
        return _acctId;
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
    private org.sourceforge.ifx.framework.element.AcctType _acctType;

    /** 
     * Setter for acctType
     * @param acctType the org.sourceforge.ifx.framework.element.AcctType to set
     */
    public void setAcctType(org.sourceforge.ifx.framework.element.AcctType _acctType) {
        this._acctType = _acctType;
    }

    /**
     * Getter for acctType
     * @return a org.sourceforge.ifx.framework.element.AcctType
     */
    public org.sourceforge.ifx.framework.element.AcctType getAcctType() {
        return _acctType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CCMotoAcct _cCMotoAcct;

    /** 
     * Setter for cCMotoAcct
     * @param cCMotoAcct the org.sourceforge.ifx.framework.element.CCMotoAcct to set
     */
    public void setCCMotoAcct(org.sourceforge.ifx.framework.element.CCMotoAcct _cCMotoAcct) {
        this._cCMotoAcct = _cCMotoAcct;
    }

    /**
     * Getter for cCMotoAcct
     * @return a org.sourceforge.ifx.framework.element.CCMotoAcct
     */
    public org.sourceforge.ifx.framework.element.CCMotoAcct getCCMotoAcct() {
        return _cCMotoAcct;
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
       * AcctId
       * CardMagData
       * AcctType
       * CCMotoAcct
       */
    public final String[] ELEMENTS = {
              "AcctId"
                 ,"CardMagData"
                 ,"AcctType"
                 ,"CCMotoAcct"
          };
}
