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
public class DevSiuType_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DevSiuType_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevSiuSensors _devSiuSensors;

    /** 
     * Setter for devSiuSensors
     * @param devSiuSensors the org.sourceforge.ifx.framework.element.DevSiuSensors to set
     */
    public void setDevSiuSensors(org.sourceforge.ifx.framework.element.DevSiuSensors _devSiuSensors) {
        this._devSiuSensors = _devSiuSensors;
    }

    /**
     * Getter for devSiuSensors
     * @return a org.sourceforge.ifx.framework.element.DevSiuSensors
     */
    public org.sourceforge.ifx.framework.element.DevSiuSensors getDevSiuSensors() {
        return _devSiuSensors;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevSiuDoors _devSiuDoors;

    /** 
     * Setter for devSiuDoors
     * @param devSiuDoors the org.sourceforge.ifx.framework.element.DevSiuDoors to set
     */
    public void setDevSiuDoors(org.sourceforge.ifx.framework.element.DevSiuDoors _devSiuDoors) {
        this._devSiuDoors = _devSiuDoors;
    }

    /**
     * Getter for devSiuDoors
     * @return a org.sourceforge.ifx.framework.element.DevSiuDoors
     */
    public org.sourceforge.ifx.framework.element.DevSiuDoors getDevSiuDoors() {
        return _devSiuDoors;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevSiuIndicators _devSiuIndicators;

    /** 
     * Setter for devSiuIndicators
     * @param devSiuIndicators the org.sourceforge.ifx.framework.element.DevSiuIndicators to set
     */
    public void setDevSiuIndicators(org.sourceforge.ifx.framework.element.DevSiuIndicators _devSiuIndicators) {
        this._devSiuIndicators = _devSiuIndicators;
    }

    /**
     * Getter for devSiuIndicators
     * @return a org.sourceforge.ifx.framework.element.DevSiuIndicators
     */
    public org.sourceforge.ifx.framework.element.DevSiuIndicators getDevSiuIndicators() {
        return _devSiuIndicators;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevSiuGuidelights _devSiuGuidelights;

    /** 
     * Setter for devSiuGuidelights
     * @param devSiuGuidelights the org.sourceforge.ifx.framework.element.DevSiuGuidelights to set
     */
    public void setDevSiuGuidelights(org.sourceforge.ifx.framework.element.DevSiuGuidelights _devSiuGuidelights) {
        this._devSiuGuidelights = _devSiuGuidelights;
    }

    /**
     * Getter for devSiuGuidelights
     * @return a org.sourceforge.ifx.framework.element.DevSiuGuidelights
     */
    public org.sourceforge.ifx.framework.element.DevSiuGuidelights getDevSiuGuidelights() {
        return _devSiuGuidelights;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevSiuAuxiliary _devSiuAuxiliary;

    /** 
     * Setter for devSiuAuxiliary
     * @param devSiuAuxiliary the org.sourceforge.ifx.framework.element.DevSiuAuxiliary to set
     */
    public void setDevSiuAuxiliary(org.sourceforge.ifx.framework.element.DevSiuAuxiliary _devSiuAuxiliary) {
        this._devSiuAuxiliary = _devSiuAuxiliary;
    }

    /**
     * Getter for devSiuAuxiliary
     * @return a org.sourceforge.ifx.framework.element.DevSiuAuxiliary
     */
    public org.sourceforge.ifx.framework.element.DevSiuAuxiliary getDevSiuAuxiliary() {
        return _devSiuAuxiliary;
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
       * DevSiuSensors
       * DevSiuDoors
       * DevSiuIndicators
       * DevSiuGuidelights
       * DevSiuAuxiliary
       */
    public final String[] ELEMENTS = {
              "DevSiuSensors"
                 ,"DevSiuDoors"
                 ,"DevSiuIndicators"
                 ,"DevSiuGuidelights"
                 ,"DevSiuAuxiliary"
          };
}
