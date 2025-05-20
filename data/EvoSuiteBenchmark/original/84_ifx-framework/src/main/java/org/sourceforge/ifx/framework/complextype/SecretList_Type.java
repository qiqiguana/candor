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
public class SecretList_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public SecretList_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SecretId _secretId;

    /** 
     * Setter for secretId
     * @param secretId the org.sourceforge.ifx.framework.element.SecretId to set
     */
    public void setSecretId(org.sourceforge.ifx.framework.element.SecretId _secretId) {
        this._secretId = _secretId;
    }

    /**
     * Getter for secretId
     * @return a org.sourceforge.ifx.framework.element.SecretId
     */
    public org.sourceforge.ifx.framework.element.SecretId getSecretId() {
        return _secretId;
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
    private org.sourceforge.ifx.framework.element.Secret _secret;

    /** 
     * Setter for secret
     * @param secret the org.sourceforge.ifx.framework.element.Secret to set
     */
    public void setSecret(org.sourceforge.ifx.framework.element.Secret _secret) {
        this._secret = _secret;
    }

    /**
     * Getter for secret
     * @return a org.sourceforge.ifx.framework.element.Secret
     */
    public org.sourceforge.ifx.framework.element.Secret getSecret() {
        return _secret;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CryptSecret _cryptSecret;

    /** 
     * Setter for cryptSecret
     * @param cryptSecret the org.sourceforge.ifx.framework.element.CryptSecret to set
     */
    public void setCryptSecret(org.sourceforge.ifx.framework.element.CryptSecret _cryptSecret) {
        this._cryptSecret = _cryptSecret;
    }

    /**
     * Getter for cryptSecret
     * @return a org.sourceforge.ifx.framework.element.CryptSecret
     */
    public org.sourceforge.ifx.framework.element.CryptSecret getCryptSecret() {
        return _cryptSecret;
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
       * SecretId
       * CryptType
       * Secret
       * CryptSecret
       */
    public final String[] ELEMENTS = {
              "SecretId"
                 ,"CryptType"
                 ,"Secret"
                 ,"CryptSecret"
          };
}
