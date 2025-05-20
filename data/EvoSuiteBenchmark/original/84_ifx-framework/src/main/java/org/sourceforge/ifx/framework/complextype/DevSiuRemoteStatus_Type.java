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
public class DevSiuRemoteStatus_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DevSiuRemoteStatus_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevSiuGreenLed _devSiuGreenLed;

    /** 
     * Setter for devSiuGreenLed
     * @param devSiuGreenLed the org.sourceforge.ifx.framework.element.DevSiuGreenLed to set
     */
    public void setDevSiuGreenLed(org.sourceforge.ifx.framework.element.DevSiuGreenLed _devSiuGreenLed) {
        this._devSiuGreenLed = _devSiuGreenLed;
    }

    /**
     * Getter for devSiuGreenLed
     * @return a org.sourceforge.ifx.framework.element.DevSiuGreenLed
     */
    public org.sourceforge.ifx.framework.element.DevSiuGreenLed getDevSiuGreenLed() {
        return _devSiuGreenLed;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevSiuRedLed _devSiuRedLed;

    /** 
     * Setter for devSiuRedLed
     * @param devSiuRedLed the org.sourceforge.ifx.framework.element.DevSiuRedLed to set
     */
    public void setDevSiuRedLed(org.sourceforge.ifx.framework.element.DevSiuRedLed _devSiuRedLed) {
        this._devSiuRedLed = _devSiuRedLed;
    }

    /**
     * Getter for devSiuRedLed
     * @return a org.sourceforge.ifx.framework.element.DevSiuRedLed
     */
    public org.sourceforge.ifx.framework.element.DevSiuRedLed getDevSiuRedLed() {
        return _devSiuRedLed;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevSiuAmberLed _devSiuAmberLed;

    /** 
     * Setter for devSiuAmberLed
     * @param devSiuAmberLed the org.sourceforge.ifx.framework.element.DevSiuAmberLed to set
     */
    public void setDevSiuAmberLed(org.sourceforge.ifx.framework.element.DevSiuAmberLed _devSiuAmberLed) {
        this._devSiuAmberLed = _devSiuAmberLed;
    }

    /**
     * Getter for devSiuAmberLed
     * @return a org.sourceforge.ifx.framework.element.DevSiuAmberLed
     */
    public org.sourceforge.ifx.framework.element.DevSiuAmberLed getDevSiuAmberLed() {
        return _devSiuAmberLed;
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
       * DevSiuGreenLed
       * DevSiuRedLed
       * DevSiuAmberLed
       */
    public final String[] ELEMENTS = {
              "DevSiuGreenLed"
                 ,"DevSiuRedLed"
                 ,"DevSiuAmberLed"
          };
}
