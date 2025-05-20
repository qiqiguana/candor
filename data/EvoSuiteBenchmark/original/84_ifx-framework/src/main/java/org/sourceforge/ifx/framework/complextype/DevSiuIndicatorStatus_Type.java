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
public class DevSiuIndicatorStatus_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DevSiuIndicatorStatus_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevSiuOpenClose _devSiuOpenClose;

    /** 
     * Setter for devSiuOpenClose
     * @param devSiuOpenClose the org.sourceforge.ifx.framework.element.DevSiuOpenClose to set
     */
    public void setDevSiuOpenClose(org.sourceforge.ifx.framework.element.DevSiuOpenClose _devSiuOpenClose) {
        this._devSiuOpenClose = _devSiuOpenClose;
    }

    /**
     * Getter for devSiuOpenClose
     * @return a org.sourceforge.ifx.framework.element.DevSiuOpenClose
     */
    public org.sourceforge.ifx.framework.element.DevSiuOpenClose getDevSiuOpenClose() {
        return _devSiuOpenClose;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevSiuFasciaLight _devSiuFasciaLight;

    /** 
     * Setter for devSiuFasciaLight
     * @param devSiuFasciaLight the org.sourceforge.ifx.framework.element.DevSiuFasciaLight to set
     */
    public void setDevSiuFasciaLight(org.sourceforge.ifx.framework.element.DevSiuFasciaLight _devSiuFasciaLight) {
        this._devSiuFasciaLight = _devSiuFasciaLight;
    }

    /**
     * Getter for devSiuFasciaLight
     * @return a org.sourceforge.ifx.framework.element.DevSiuFasciaLight
     */
    public org.sourceforge.ifx.framework.element.DevSiuFasciaLight getDevSiuFasciaLight() {
        return _devSiuFasciaLight;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevSiuAudio _devSiuAudio;

    /** 
     * Setter for devSiuAudio
     * @param devSiuAudio the org.sourceforge.ifx.framework.element.DevSiuAudio to set
     */
    public void setDevSiuAudio(org.sourceforge.ifx.framework.element.DevSiuAudio _devSiuAudio) {
        this._devSiuAudio = _devSiuAudio;
    }

    /**
     * Getter for devSiuAudio
     * @return a org.sourceforge.ifx.framework.element.DevSiuAudio
     */
    public org.sourceforge.ifx.framework.element.DevSiuAudio getDevSiuAudio() {
        return _devSiuAudio;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevSiuHeating _devSiuHeating;

    /** 
     * Setter for devSiuHeating
     * @param devSiuHeating the org.sourceforge.ifx.framework.element.DevSiuHeating to set
     */
    public void setDevSiuHeating(org.sourceforge.ifx.framework.element.DevSiuHeating _devSiuHeating) {
        this._devSiuHeating = _devSiuHeating;
    }

    /**
     * Getter for devSiuHeating
     * @return a org.sourceforge.ifx.framework.element.DevSiuHeating
     */
    public org.sourceforge.ifx.framework.element.DevSiuHeating getDevSiuHeating() {
        return _devSiuHeating;
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
       * DevSiuOpenClose
       * DevSiuFasciaLight
       * DevSiuAudio
       * DevSiuHeating
       */
    public final String[] ELEMENTS = {
              "DevSiuOpenClose"
                 ,"DevSiuFasciaLight"
                 ,"DevSiuAudio"
                 ,"DevSiuHeating"
          };
}
