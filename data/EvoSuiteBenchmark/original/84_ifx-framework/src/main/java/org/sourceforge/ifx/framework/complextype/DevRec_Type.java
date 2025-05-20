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
public class DevRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DevRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevId _devId;

    /** 
     * Setter for devId
     * @param devId the org.sourceforge.ifx.framework.element.DevId to set
     */
    public void setDevId(org.sourceforge.ifx.framework.element.DevId _devId) {
        this._devId = _devId;
    }

    /**
     * Getter for devId
     * @return a org.sourceforge.ifx.framework.element.DevId
     */
    public org.sourceforge.ifx.framework.element.DevId getDevId() {
        return _devId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevInfo _devInfo;

    /** 
     * Setter for devInfo
     * @param devInfo the org.sourceforge.ifx.framework.element.DevInfo to set
     */
    public void setDevInfo(org.sourceforge.ifx.framework.element.DevInfo _devInfo) {
        this._devInfo = _devInfo;
    }

    /**
     * Getter for devInfo
     * @return a org.sourceforge.ifx.framework.element.DevInfo
     */
    public org.sourceforge.ifx.framework.element.DevInfo getDevInfo() {
        return _devInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevStatus _devStatus;

    /** 
     * Setter for devStatus
     * @param devStatus the org.sourceforge.ifx.framework.element.DevStatus to set
     */
    public void setDevStatus(org.sourceforge.ifx.framework.element.DevStatus _devStatus) {
        this._devStatus = _devStatus;
    }

    /**
     * Getter for devStatus
     * @return a org.sourceforge.ifx.framework.element.DevStatus
     */
    public org.sourceforge.ifx.framework.element.DevStatus getDevStatus() {
        return _devStatus;
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
       * DevId
       * DevInfo
       * DevStatus
       */
    public final String[] ELEMENTS = {
              "DevId"
                 ,"DevInfo"
                 ,"DevStatus"
          };
}
