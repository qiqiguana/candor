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
public class DevSiuAuxiliaryStatus_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DevSiuAuxiliaryStatus_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevSiuVolume _devSiuVolume;

    /** 
     * Setter for devSiuVolume
     * @param devSiuVolume the org.sourceforge.ifx.framework.element.DevSiuVolume to set
     */
    public void setDevSiuVolume(org.sourceforge.ifx.framework.element.DevSiuVolume _devSiuVolume) {
        this._devSiuVolume = _devSiuVolume;
    }

    /**
     * Getter for devSiuVolume
     * @return a org.sourceforge.ifx.framework.element.DevSiuVolume
     */
    public org.sourceforge.ifx.framework.element.DevSiuVolume getDevSiuVolume() {
        return _devSiuVolume;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevSiuUps _devSiuUps;

    /** 
     * Setter for devSiuUps
     * @param devSiuUps the org.sourceforge.ifx.framework.element.DevSiuUps to set
     */
    public void setDevSiuUps(org.sourceforge.ifx.framework.element.DevSiuUps _devSiuUps) {
        this._devSiuUps = _devSiuUps;
    }

    /**
     * Getter for devSiuUps
     * @return a org.sourceforge.ifx.framework.element.DevSiuUps
     */
    public org.sourceforge.ifx.framework.element.DevSiuUps getDevSiuUps() {
        return _devSiuUps;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevSiuRemoteStatus _devSiuRemoteStatus;

    /** 
     * Setter for devSiuRemoteStatus
     * @param devSiuRemoteStatus the org.sourceforge.ifx.framework.element.DevSiuRemoteStatus to set
     */
    public void setDevSiuRemoteStatus(org.sourceforge.ifx.framework.element.DevSiuRemoteStatus _devSiuRemoteStatus) {
        this._devSiuRemoteStatus = _devSiuRemoteStatus;
    }

    /**
     * Getter for devSiuRemoteStatus
     * @return a org.sourceforge.ifx.framework.element.DevSiuRemoteStatus
     */
    public org.sourceforge.ifx.framework.element.DevSiuRemoteStatus getDevSiuRemoteStatus() {
        return _devSiuRemoteStatus;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevSiuAudibleAlarm _devSiuAudibleAlarm;

    /** 
     * Setter for devSiuAudibleAlarm
     * @param devSiuAudibleAlarm the org.sourceforge.ifx.framework.element.DevSiuAudibleAlarm to set
     */
    public void setDevSiuAudibleAlarm(org.sourceforge.ifx.framework.element.DevSiuAudibleAlarm _devSiuAudibleAlarm) {
        this._devSiuAudibleAlarm = _devSiuAudibleAlarm;
    }

    /**
     * Getter for devSiuAudibleAlarm
     * @return a org.sourceforge.ifx.framework.element.DevSiuAudibleAlarm
     */
    public org.sourceforge.ifx.framework.element.DevSiuAudibleAlarm getDevSiuAudibleAlarm() {
        return _devSiuAudibleAlarm;
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
       * DevSiuVolume
       * DevSiuUps
       * DevSiuRemoteStatus
       * DevSiuAudibleAlarm
       */
    public final String[] ELEMENTS = {
              "DevSiuVolume"
                 ,"DevSiuUps"
                 ,"DevSiuRemoteStatus"
                 ,"DevSiuAudibleAlarm"
          };
}
