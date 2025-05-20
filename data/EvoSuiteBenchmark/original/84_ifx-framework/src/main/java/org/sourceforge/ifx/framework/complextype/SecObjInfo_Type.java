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
public class SecObjInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public SecObjInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SecObjName _secObjName;

    /** 
     * Setter for secObjName
     * @param secObjName the org.sourceforge.ifx.framework.element.SecObjName to set
     */
    public void setSecObjName(org.sourceforge.ifx.framework.element.SecObjName _secObjName) {
        this._secObjName = _secObjName;
    }

    /**
     * Getter for secObjName
     * @return a org.sourceforge.ifx.framework.element.SecObjName
     */
    public org.sourceforge.ifx.framework.element.SecObjName getSecObjName() {
        return _secObjName;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SecObjType _secObjType;

    /** 
     * Setter for secObjType
     * @param secObjType the org.sourceforge.ifx.framework.element.SecObjType to set
     */
    public void setSecObjType(org.sourceforge.ifx.framework.element.SecObjType _secObjType) {
        this._secObjType = _secObjType;
    }

    /**
     * Getter for secObjType
     * @return a org.sourceforge.ifx.framework.element.SecObjType
     */
    public org.sourceforge.ifx.framework.element.SecObjType getSecObjType() {
        return _secObjType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SecObjPurpose _secObjPurpose;

    /** 
     * Setter for secObjPurpose
     * @param secObjPurpose the org.sourceforge.ifx.framework.element.SecObjPurpose to set
     */
    public void setSecObjPurpose(org.sourceforge.ifx.framework.element.SecObjPurpose _secObjPurpose) {
        this._secObjPurpose = _secObjPurpose;
    }

    /**
     * Getter for secObjPurpose
     * @return a org.sourceforge.ifx.framework.element.SecObjPurpose
     */
    public org.sourceforge.ifx.framework.element.SecObjPurpose getSecObjPurpose() {
        return _secObjPurpose;
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
    private org.sourceforge.ifx.framework.element.SecEncryptId _secEncryptId;

    /** 
     * Setter for secEncryptId
     * @param secEncryptId the org.sourceforge.ifx.framework.element.SecEncryptId to set
     */
    public void setSecEncryptId(org.sourceforge.ifx.framework.element.SecEncryptId _secEncryptId) {
        this._secEncryptId = _secEncryptId;
    }

    /**
     * Getter for secEncryptId
     * @return a org.sourceforge.ifx.framework.element.SecEncryptId
     */
    public org.sourceforge.ifx.framework.element.SecEncryptId getSecEncryptId() {
        return _secEncryptId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SecSignId _secSignId;

    /** 
     * Setter for secSignId
     * @param secSignId the org.sourceforge.ifx.framework.element.SecSignId to set
     */
    public void setSecSignId(org.sourceforge.ifx.framework.element.SecSignId _secSignId) {
        this._secSignId = _secSignId;
    }

    /**
     * Getter for secSignId
     * @return a org.sourceforge.ifx.framework.element.SecSignId
     */
    public org.sourceforge.ifx.framework.element.SecSignId getSecSignId() {
        return _secSignId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SecObjValue _secObjValue;

    /** 
     * Setter for secObjValue
     * @param secObjValue the org.sourceforge.ifx.framework.element.SecObjValue to set
     */
    public void setSecObjValue(org.sourceforge.ifx.framework.element.SecObjValue _secObjValue) {
        this._secObjValue = _secObjValue;
    }

    /**
     * Getter for secObjValue
     * @return a org.sourceforge.ifx.framework.element.SecObjValue
     */
    public org.sourceforge.ifx.framework.element.SecObjValue getSecObjValue() {
        return _secObjValue;
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
       * SecObjName
       * SecObjType
       * SecObjPurpose
       * CryptType
       * SecEncryptId
       * SecSignId
       * SecObjValue
       */
    public final String[] ELEMENTS = {
              "SecObjName"
                 ,"SecObjType"
                 ,"SecObjPurpose"
                 ,"CryptType"
                 ,"SecEncryptId"
                 ,"SecSignId"
                 ,"SecObjValue"
          };
}
