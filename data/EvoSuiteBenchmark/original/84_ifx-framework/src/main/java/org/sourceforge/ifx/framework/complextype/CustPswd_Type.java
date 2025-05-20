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
public class CustPswd_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CustPswd_Type() {
        super();
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
    private org.sourceforge.ifx.framework.element.Pswd _pswd;

    /** 
     * Setter for pswd
     * @param pswd the org.sourceforge.ifx.framework.element.Pswd to set
     */
    public void setPswd(org.sourceforge.ifx.framework.element.Pswd _pswd) {
        this._pswd = _pswd;
    }

    /**
     * Getter for pswd
     * @return a org.sourceforge.ifx.framework.element.Pswd
     */
    public org.sourceforge.ifx.framework.element.Pswd getPswd() {
        return _pswd;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CryptPswd _cryptPswd;

    /** 
     * Setter for cryptPswd
     * @param cryptPswd the org.sourceforge.ifx.framework.element.CryptPswd to set
     */
    public void setCryptPswd(org.sourceforge.ifx.framework.element.CryptPswd _cryptPswd) {
        this._cryptPswd = _cryptPswd;
    }

    /**
     * Getter for cryptPswd
     * @return a org.sourceforge.ifx.framework.element.CryptPswd
     */
    public org.sourceforge.ifx.framework.element.CryptPswd getCryptPswd() {
        return _cryptPswd;
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
       * CryptType
       * SecObjId
       * Pswd
       * CryptPswd
       */
    public final String[] ELEMENTS = {
              "CryptType"
                 ,"SecObjId"
                 ,"Pswd"
                 ,"CryptPswd"
          };
}
