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
public class DevCimPosition_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DevCimPosition_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevCimPositionLocation _devCimPositionLocation;

    /** 
     * Setter for devCimPositionLocation
     * @param devCimPositionLocation the org.sourceforge.ifx.framework.element.DevCimPositionLocation to set
     */
    public void setDevCimPositionLocation(org.sourceforge.ifx.framework.element.DevCimPositionLocation _devCimPositionLocation) {
        this._devCimPositionLocation = _devCimPositionLocation;
    }

    /**
     * Getter for devCimPositionLocation
     * @return a org.sourceforge.ifx.framework.element.DevCimPositionLocation
     */
    public org.sourceforge.ifx.framework.element.DevCimPositionLocation getDevCimPositionLocation() {
        return _devCimPositionLocation;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevCimShutter _devCimShutter;

    /** 
     * Setter for devCimShutter
     * @param devCimShutter the org.sourceforge.ifx.framework.element.DevCimShutter to set
     */
    public void setDevCimShutter(org.sourceforge.ifx.framework.element.DevCimShutter _devCimShutter) {
        this._devCimShutter = _devCimShutter;
    }

    /**
     * Getter for devCimShutter
     * @return a org.sourceforge.ifx.framework.element.DevCimShutter
     */
    public org.sourceforge.ifx.framework.element.DevCimShutter getDevCimShutter() {
        return _devCimShutter;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevCimPositionStatus _devCimPositionStatus;

    /** 
     * Setter for devCimPositionStatus
     * @param devCimPositionStatus the org.sourceforge.ifx.framework.element.DevCimPositionStatus to set
     */
    public void setDevCimPositionStatus(org.sourceforge.ifx.framework.element.DevCimPositionStatus _devCimPositionStatus) {
        this._devCimPositionStatus = _devCimPositionStatus;
    }

    /**
     * Getter for devCimPositionStatus
     * @return a org.sourceforge.ifx.framework.element.DevCimPositionStatus
     */
    public org.sourceforge.ifx.framework.element.DevCimPositionStatus getDevCimPositionStatus() {
        return _devCimPositionStatus;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevCimTransport _devCimTransport;

    /** 
     * Setter for devCimTransport
     * @param devCimTransport the org.sourceforge.ifx.framework.element.DevCimTransport to set
     */
    public void setDevCimTransport(org.sourceforge.ifx.framework.element.DevCimTransport _devCimTransport) {
        this._devCimTransport = _devCimTransport;
    }

    /**
     * Getter for devCimTransport
     * @return a org.sourceforge.ifx.framework.element.DevCimTransport
     */
    public org.sourceforge.ifx.framework.element.DevCimTransport getDevCimTransport() {
        return _devCimTransport;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevCimTransportItems _devCimTransportItems;

    /** 
     * Setter for devCimTransportItems
     * @param devCimTransportItems the org.sourceforge.ifx.framework.element.DevCimTransportItems to set
     */
    public void setDevCimTransportItems(org.sourceforge.ifx.framework.element.DevCimTransportItems _devCimTransportItems) {
        this._devCimTransportItems = _devCimTransportItems;
    }

    /**
     * Getter for devCimTransportItems
     * @return a org.sourceforge.ifx.framework.element.DevCimTransportItems
     */
    public org.sourceforge.ifx.framework.element.DevCimTransportItems getDevCimTransportItems() {
        return _devCimTransportItems;
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
       * DevCimPositionLocation
       * DevCimShutter
       * DevCimPositionStatus
       * DevCimTransport
       * DevCimTransportItems
       */
    public final String[] ELEMENTS = {
              "DevCimPositionLocation"
                 ,"DevCimShutter"
                 ,"DevCimPositionStatus"
                 ,"DevCimTransport"
                 ,"DevCimTransportItems"
          };
}
