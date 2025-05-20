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
public class POSSecurity_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public POSSecurity_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PSSNetworkType _pSSNetworkType;

    /** 
     * Setter for pSSNetworkType
     * @param pSSNetworkType the org.sourceforge.ifx.framework.element.PSSNetworkType to set
     */
    public void setPSSNetworkType(org.sourceforge.ifx.framework.element.PSSNetworkType _pSSNetworkType) {
        this._pSSNetworkType = _pSSNetworkType;
    }

    /**
     * Getter for pSSNetworkType
     * @return a org.sourceforge.ifx.framework.element.PSSNetworkType
     */
    public org.sourceforge.ifx.framework.element.PSSNetworkType getPSSNetworkType() {
        return _pSSNetworkType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PSSMsgMAC[] _pSSMsgMAC;

    /** 
     * Setter for pSSMsgMAC
     * @param pSSMsgMAC the org.sourceforge.ifx.framework.element.PSSMsgMAC[] to set
     */
    public void setPSSMsgMAC(org.sourceforge.ifx.framework.element.PSSMsgMAC[] _pSSMsgMAC) {
        this._pSSMsgMAC = _pSSMsgMAC;
    }

    /**
     * Getter for pSSMsgMAC
     * @return a org.sourceforge.ifx.framework.element.PSSMsgMAC[]
     */
    public org.sourceforge.ifx.framework.element.PSSMsgMAC[] getPSSMsgMAC() {
        return _pSSMsgMAC;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PSSMsgEncryption[] _pSSMsgEncryption;

    /** 
     * Setter for pSSMsgEncryption
     * @param pSSMsgEncryption the org.sourceforge.ifx.framework.element.PSSMsgEncryption[] to set
     */
    public void setPSSMsgEncryption(org.sourceforge.ifx.framework.element.PSSMsgEncryption[] _pSSMsgEncryption) {
        this._pSSMsgEncryption = _pSSMsgEncryption;
    }

    /**
     * Getter for pSSMsgEncryption
     * @return a org.sourceforge.ifx.framework.element.PSSMsgEncryption[]
     */
    public org.sourceforge.ifx.framework.element.PSSMsgEncryption[] getPSSMsgEncryption() {
        return _pSSMsgEncryption;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PSSCATSecLevel[] _pSSCATSecLevel;

    /** 
     * Setter for pSSCATSecLevel
     * @param pSSCATSecLevel the org.sourceforge.ifx.framework.element.PSSCATSecLevel[] to set
     */
    public void setPSSCATSecLevel(org.sourceforge.ifx.framework.element.PSSCATSecLevel[] _pSSCATSecLevel) {
        this._pSSCATSecLevel = _pSSCATSecLevel;
    }

    /**
     * Getter for pSSCATSecLevel
     * @return a org.sourceforge.ifx.framework.element.PSSCATSecLevel[]
     */
    public org.sourceforge.ifx.framework.element.PSSCATSecLevel[] getPSSCATSecLevel() {
        return _pSSCATSecLevel;
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
       * PSSNetworkType
       * PSSMsgMAC
       * PSSMsgEncryption
       * PSSCATSecLevel
       */
    public final String[] ELEMENTS = {
              "PSSNetworkType"
                 ,"PSSMsgMAC"
                 ,"PSSMsgEncryption"
                 ,"PSSCATSecLevel"
          };
}
