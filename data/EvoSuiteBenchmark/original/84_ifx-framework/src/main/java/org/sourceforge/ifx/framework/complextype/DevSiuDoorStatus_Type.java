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
public class DevSiuDoorStatus_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DevSiuDoorStatus_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevSiuCabinet _devSiuCabinet;

    /** 
     * Setter for devSiuCabinet
     * @param devSiuCabinet the org.sourceforge.ifx.framework.element.DevSiuCabinet to set
     */
    public void setDevSiuCabinet(org.sourceforge.ifx.framework.element.DevSiuCabinet _devSiuCabinet) {
        this._devSiuCabinet = _devSiuCabinet;
    }

    /**
     * Getter for devSiuCabinet
     * @return a org.sourceforge.ifx.framework.element.DevSiuCabinet
     */
    public org.sourceforge.ifx.framework.element.DevSiuCabinet getDevSiuCabinet() {
        return _devSiuCabinet;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevSiuSafe _devSiuSafe;

    /** 
     * Setter for devSiuSafe
     * @param devSiuSafe the org.sourceforge.ifx.framework.element.DevSiuSafe to set
     */
    public void setDevSiuSafe(org.sourceforge.ifx.framework.element.DevSiuSafe _devSiuSafe) {
        this._devSiuSafe = _devSiuSafe;
    }

    /**
     * Getter for devSiuSafe
     * @return a org.sourceforge.ifx.framework.element.DevSiuSafe
     */
    public org.sourceforge.ifx.framework.element.DevSiuSafe getDevSiuSafe() {
        return _devSiuSafe;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevSiuVandalShield _devSiuVandalShield;

    /** 
     * Setter for devSiuVandalShield
     * @param devSiuVandalShield the org.sourceforge.ifx.framework.element.DevSiuVandalShield to set
     */
    public void setDevSiuVandalShield(org.sourceforge.ifx.framework.element.DevSiuVandalShield _devSiuVandalShield) {
        this._devSiuVandalShield = _devSiuVandalShield;
    }

    /**
     * Getter for devSiuVandalShield
     * @return a org.sourceforge.ifx.framework.element.DevSiuVandalShield
     */
    public org.sourceforge.ifx.framework.element.DevSiuVandalShield getDevSiuVandalShield() {
        return _devSiuVandalShield;
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
       * DevSiuCabinet
       * DevSiuSafe
       * DevSiuVandalShield
       */
    public final String[] ELEMENTS = {
              "DevSiuCabinet"
                 ,"DevSiuSafe"
                 ,"DevSiuVandalShield"
          };
}
