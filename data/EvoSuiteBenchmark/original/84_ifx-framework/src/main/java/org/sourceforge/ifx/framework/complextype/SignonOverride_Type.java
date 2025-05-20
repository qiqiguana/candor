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
public class SignonOverride_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public SignonOverride_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.OverrideType _overrideType;

    /** 
     * Setter for overrideType
     * @param overrideType the org.sourceforge.ifx.framework.element.OverrideType to set
     */
    public void setOverrideType(org.sourceforge.ifx.framework.element.OverrideType _overrideType) {
        this._overrideType = _overrideType;
    }

    /**
     * Getter for overrideType
     * @return a org.sourceforge.ifx.framework.element.OverrideType
     */
    public org.sourceforge.ifx.framework.element.OverrideType getOverrideType() {
        return _overrideType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SignonPswd _signonPswd;

    /** 
     * Setter for signonPswd
     * @param signonPswd the org.sourceforge.ifx.framework.element.SignonPswd to set
     */
    public void setSignonPswd(org.sourceforge.ifx.framework.element.SignonPswd _signonPswd) {
        this._signonPswd = _signonPswd;
    }

    /**
     * Getter for signonPswd
     * @return a org.sourceforge.ifx.framework.element.SignonPswd
     */
    public org.sourceforge.ifx.framework.element.SignonPswd getSignonPswd() {
        return _signonPswd;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SignonCert _signonCert;

    /** 
     * Setter for signonCert
     * @param signonCert the org.sourceforge.ifx.framework.element.SignonCert to set
     */
    public void setSignonCert(org.sourceforge.ifx.framework.element.SignonCert _signonCert) {
        this._signonCert = _signonCert;
    }

    /**
     * Getter for signonCert
     * @return a org.sourceforge.ifx.framework.element.SignonCert
     */
    public org.sourceforge.ifx.framework.element.SignonCert getSignonCert() {
        return _signonCert;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SignonMagPIN _signonMagPIN;

    /** 
     * Setter for signonMagPIN
     * @param signonMagPIN the org.sourceforge.ifx.framework.element.SignonMagPIN to set
     */
    public void setSignonMagPIN(org.sourceforge.ifx.framework.element.SignonMagPIN _signonMagPIN) {
        this._signonMagPIN = _signonMagPIN;
    }

    /**
     * Getter for signonMagPIN
     * @return a org.sourceforge.ifx.framework.element.SignonMagPIN
     */
    public org.sourceforge.ifx.framework.element.SignonMagPIN getSignonMagPIN() {
        return _signonMagPIN;
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
       * OverrideType
       * SignonPswd
       * SignonCert
       * SignonMagPIN
       */
    public final String[] ELEMENTS = {
              "OverrideType"
                 ,"SignonPswd"
                 ,"SignonCert"
                 ,"SignonMagPIN"
          };
}
