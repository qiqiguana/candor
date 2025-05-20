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
public class DevPtrSupply_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DevPtrSupply_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevPtrSupplyLocation _devPtrSupplyLocation;

    /** 
     * Setter for devPtrSupplyLocation
     * @param devPtrSupplyLocation the org.sourceforge.ifx.framework.element.DevPtrSupplyLocation to set
     */
    public void setDevPtrSupplyLocation(org.sourceforge.ifx.framework.element.DevPtrSupplyLocation _devPtrSupplyLocation) {
        this._devPtrSupplyLocation = _devPtrSupplyLocation;
    }

    /**
     * Getter for devPtrSupplyLocation
     * @return a org.sourceforge.ifx.framework.element.DevPtrSupplyLocation
     */
    public org.sourceforge.ifx.framework.element.DevPtrSupplyLocation getDevPtrSupplyLocation() {
        return _devPtrSupplyLocation;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevPtrSupplyLevel _devPtrSupplyLevel;

    /** 
     * Setter for devPtrSupplyLevel
     * @param devPtrSupplyLevel the org.sourceforge.ifx.framework.element.DevPtrSupplyLevel to set
     */
    public void setDevPtrSupplyLevel(org.sourceforge.ifx.framework.element.DevPtrSupplyLevel _devPtrSupplyLevel) {
        this._devPtrSupplyLevel = _devPtrSupplyLevel;
    }

    /**
     * Getter for devPtrSupplyLevel
     * @return a org.sourceforge.ifx.framework.element.DevPtrSupplyLevel
     */
    public org.sourceforge.ifx.framework.element.DevPtrSupplyLevel getDevPtrSupplyLevel() {
        return _devPtrSupplyLevel;
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
       * DevPtrSupplyLocation
       * DevPtrSupplyLevel
       */
    public final String[] ELEMENTS = {
              "DevPtrSupplyLocation"
                 ,"DevPtrSupplyLevel"
          };
}
