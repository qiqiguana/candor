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
public class DevCdmPosition_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DevCdmPosition_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevCdmPositionLocation _devCdmPositionLocation;

    /** 
     * Setter for devCdmPositionLocation
     * @param devCdmPositionLocation the org.sourceforge.ifx.framework.element.DevCdmPositionLocation to set
     */
    public void setDevCdmPositionLocation(org.sourceforge.ifx.framework.element.DevCdmPositionLocation _devCdmPositionLocation) {
        this._devCdmPositionLocation = _devCdmPositionLocation;
    }

    /**
     * Getter for devCdmPositionLocation
     * @return a org.sourceforge.ifx.framework.element.DevCdmPositionLocation
     */
    public org.sourceforge.ifx.framework.element.DevCdmPositionLocation getDevCdmPositionLocation() {
        return _devCdmPositionLocation;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevCdmShutter _devCdmShutter;

    /** 
     * Setter for devCdmShutter
     * @param devCdmShutter the org.sourceforge.ifx.framework.element.DevCdmShutter to set
     */
    public void setDevCdmShutter(org.sourceforge.ifx.framework.element.DevCdmShutter _devCdmShutter) {
        this._devCdmShutter = _devCdmShutter;
    }

    /**
     * Getter for devCdmShutter
     * @return a org.sourceforge.ifx.framework.element.DevCdmShutter
     */
    public org.sourceforge.ifx.framework.element.DevCdmShutter getDevCdmShutter() {
        return _devCdmShutter;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevCdmPositionStatus _devCdmPositionStatus;

    /** 
     * Setter for devCdmPositionStatus
     * @param devCdmPositionStatus the org.sourceforge.ifx.framework.element.DevCdmPositionStatus to set
     */
    public void setDevCdmPositionStatus(org.sourceforge.ifx.framework.element.DevCdmPositionStatus _devCdmPositionStatus) {
        this._devCdmPositionStatus = _devCdmPositionStatus;
    }

    /**
     * Getter for devCdmPositionStatus
     * @return a org.sourceforge.ifx.framework.element.DevCdmPositionStatus
     */
    public org.sourceforge.ifx.framework.element.DevCdmPositionStatus getDevCdmPositionStatus() {
        return _devCdmPositionStatus;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevCdmTransport _devCdmTransport;

    /** 
     * Setter for devCdmTransport
     * @param devCdmTransport the org.sourceforge.ifx.framework.element.DevCdmTransport to set
     */
    public void setDevCdmTransport(org.sourceforge.ifx.framework.element.DevCdmTransport _devCdmTransport) {
        this._devCdmTransport = _devCdmTransport;
    }

    /**
     * Getter for devCdmTransport
     * @return a org.sourceforge.ifx.framework.element.DevCdmTransport
     */
    public org.sourceforge.ifx.framework.element.DevCdmTransport getDevCdmTransport() {
        return _devCdmTransport;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevCdmTransportItems _devCdmTransportItems;

    /** 
     * Setter for devCdmTransportItems
     * @param devCdmTransportItems the org.sourceforge.ifx.framework.element.DevCdmTransportItems to set
     */
    public void setDevCdmTransportItems(org.sourceforge.ifx.framework.element.DevCdmTransportItems _devCdmTransportItems) {
        this._devCdmTransportItems = _devCdmTransportItems;
    }

    /**
     * Getter for devCdmTransportItems
     * @return a org.sourceforge.ifx.framework.element.DevCdmTransportItems
     */
    public org.sourceforge.ifx.framework.element.DevCdmTransportItems getDevCdmTransportItems() {
        return _devCdmTransportItems;
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
       * DevCdmPositionLocation
       * DevCdmShutter
       * DevCdmPositionStatus
       * DevCdmTransport
       * DevCdmTransportItems
       */
    public final String[] ELEMENTS = {
              "DevCdmPositionLocation"
                 ,"DevCdmShutter"
                 ,"DevCdmPositionStatus"
                 ,"DevCdmTransport"
                 ,"DevCdmTransportItems"
          };
}
