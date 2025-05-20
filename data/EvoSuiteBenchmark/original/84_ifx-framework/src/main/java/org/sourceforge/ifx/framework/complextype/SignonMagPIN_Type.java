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
public class SignonMagPIN_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public SignonMagPIN_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SignonRole _signonRole;

    /** 
     * Setter for signonRole
     * @param signonRole the org.sourceforge.ifx.framework.element.SignonRole to set
     */
    public void setSignonRole(org.sourceforge.ifx.framework.element.SignonRole _signonRole) {
        this._signonRole = _signonRole;
    }

    /**
     * Getter for signonRole
     * @return a org.sourceforge.ifx.framework.element.SignonRole
     */
    public org.sourceforge.ifx.framework.element.SignonRole getSignonRole() {
        return _signonRole;
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
    private org.sourceforge.ifx.framework.element.CryptType _cryptType;

    /** 
     * Setter for cryptType
     * @param cryptType the org.sourceforge.ifx.framework.element.CryptType to set
     */
    public void setCryptType(org.sourceforge.ifx.framework.element.CryptType _cryptType) {
        this._cryptType = _cryptType;
    }

    /**
     * Getter for cryptType
     * @return a org.sourceforge.ifx.framework.element.CryptType
     */
    public org.sourceforge.ifx.framework.element.CryptType getCryptType() {
        return _cryptType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PINBlock _pINBlock;

    /** 
     * Setter for pINBlock
     * @param pINBlock the org.sourceforge.ifx.framework.element.PINBlock to set
     */
    public void setPINBlock(org.sourceforge.ifx.framework.element.PINBlock _pINBlock) {
        this._pINBlock = _pINBlock;
    }

    /**
     * Getter for pINBlock
     * @return a org.sourceforge.ifx.framework.element.PINBlock
     */
    public org.sourceforge.ifx.framework.element.PINBlock getPINBlock() {
        return _pINBlock;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SecObjId _secObjId;

    /** 
     * Setter for secObjId
     * @param secObjId the org.sourceforge.ifx.framework.element.SecObjId to set
     */
    public void setSecObjId(org.sourceforge.ifx.framework.element.SecObjId _secObjId) {
        this._secObjId = _secObjId;
    }

    /**
     * Getter for secObjId
     * @return a org.sourceforge.ifx.framework.element.SecObjId
     */
    public org.sourceforge.ifx.framework.element.SecObjId getSecObjId() {
        return _secObjId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.GenSessKey _genSessKey;

    /** 
     * Setter for genSessKey
     * @param genSessKey the org.sourceforge.ifx.framework.element.GenSessKey to set
     */
    public void setGenSessKey(org.sourceforge.ifx.framework.element.GenSessKey _genSessKey) {
        this._genSessKey = _genSessKey;
    }

    /**
     * Getter for genSessKey
     * @return a org.sourceforge.ifx.framework.element.GenSessKey
     */
    public org.sourceforge.ifx.framework.element.GenSessKey getGenSessKey() {
        return _genSessKey;
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
       * SignonRole
       * CardMagData
       * CryptType
       * PINBlock
       * SecObjId
       * GenSessKey
       */
    public final String[] ELEMENTS = {
              "SignonRole"
                 ,"CardMagData"
                 ,"CryptType"
                 ,"PINBlock"
                 ,"SecObjId"
                 ,"GenSessKey"
          };
}
