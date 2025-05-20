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
public class EnrollProf_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public EnrollProf_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.WebEnrollURL _webEnrollURL;

    /** 
     * Setter for webEnrollURL
     * @param webEnrollURL the org.sourceforge.ifx.framework.element.WebEnrollURL to set
     */
    public void setWebEnrollURL(org.sourceforge.ifx.framework.element.WebEnrollURL _webEnrollURL) {
        this._webEnrollURL = _webEnrollURL;
    }

    /**
     * Getter for webEnrollURL
     * @return a org.sourceforge.ifx.framework.element.WebEnrollURL
     */
    public org.sourceforge.ifx.framework.element.WebEnrollURL getWebEnrollURL() {
        return _webEnrollURL;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.EnrollDesc _enrollDesc;

    /** 
     * Setter for enrollDesc
     * @param enrollDesc the org.sourceforge.ifx.framework.element.EnrollDesc to set
     */
    public void setEnrollDesc(org.sourceforge.ifx.framework.element.EnrollDesc _enrollDesc) {
        this._enrollDesc = _enrollDesc;
    }

    /**
     * Getter for enrollDesc
     * @return a org.sourceforge.ifx.framework.element.EnrollDesc
     */
    public org.sourceforge.ifx.framework.element.EnrollDesc getEnrollDesc() {
        return _enrollDesc;
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
    private org.sourceforge.ifx.framework.element.SecretPrompt[] _secretPrompt;

    /** 
     * Setter for secretPrompt
     * @param secretPrompt the org.sourceforge.ifx.framework.element.SecretPrompt[] to set
     */
    public void setSecretPrompt(org.sourceforge.ifx.framework.element.SecretPrompt[] _secretPrompt) {
        this._secretPrompt = _secretPrompt;
    }

    /**
     * Getter for secretPrompt
     * @return a org.sourceforge.ifx.framework.element.SecretPrompt[]
     */
    public org.sourceforge.ifx.framework.element.SecretPrompt[] getSecretPrompt() {
        return _secretPrompt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustNameReqd[] _custNameReqd;

    /** 
     * Setter for custNameReqd
     * @param custNameReqd the org.sourceforge.ifx.framework.element.CustNameReqd[] to set
     */
    public void setCustNameReqd(org.sourceforge.ifx.framework.element.CustNameReqd[] _custNameReqd) {
        this._custNameReqd = _custNameReqd;
    }

    /**
     * Getter for custNameReqd
     * @return a org.sourceforge.ifx.framework.element.CustNameReqd[]
     */
    public org.sourceforge.ifx.framework.element.CustNameReqd[] getCustNameReqd() {
        return _custNameReqd;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PostAddrReqd[] _postAddrReqd;

    /** 
     * Setter for postAddrReqd
     * @param postAddrReqd the org.sourceforge.ifx.framework.element.PostAddrReqd[] to set
     */
    public void setPostAddrReqd(org.sourceforge.ifx.framework.element.PostAddrReqd[] _postAddrReqd) {
        this._postAddrReqd = _postAddrReqd;
    }

    /**
     * Getter for postAddrReqd
     * @return a org.sourceforge.ifx.framework.element.PostAddrReqd[]
     */
    public org.sourceforge.ifx.framework.element.PostAddrReqd[] getPostAddrReqd() {
        return _postAddrReqd;
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
       * WebEnrollURL
       * EnrollDesc
       * CryptType
       * SecretPrompt
       * CustNameReqd
       * PostAddrReqd
       */
    public final String[] ELEMENTS = {
              "WebEnrollURL"
                 ,"EnrollDesc"
                 ,"CryptType"
                 ,"SecretPrompt"
                 ,"CustNameReqd"
                 ,"PostAddrReqd"
          };
}
