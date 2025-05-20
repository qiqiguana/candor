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
public class DevDetailAlm_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DevDetailAlm_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevAlmType _devAlmType;

    /** 
     * Setter for devAlmType
     * @param devAlmType the org.sourceforge.ifx.framework.element.DevAlmType to set
     */
    public void setDevAlmType(org.sourceforge.ifx.framework.element.DevAlmType _devAlmType) {
        this._devAlmType = _devAlmType;
    }

    /**
     * Getter for devAlmType
     * @return a org.sourceforge.ifx.framework.element.DevAlmType
     */
    public org.sourceforge.ifx.framework.element.DevAlmType getDevAlmType() {
        return _devAlmType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevAlmOperation _devAlmOperation;

    /** 
     * Setter for devAlmOperation
     * @param devAlmOperation the org.sourceforge.ifx.framework.element.DevAlmOperation to set
     */
    public void setDevAlmOperation(org.sourceforge.ifx.framework.element.DevAlmOperation _devAlmOperation) {
        this._devAlmOperation = _devAlmOperation;
    }

    /**
     * Getter for devAlmOperation
     * @return a org.sourceforge.ifx.framework.element.DevAlmOperation
     */
    public org.sourceforge.ifx.framework.element.DevAlmOperation getDevAlmOperation() {
        return _devAlmOperation;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevAlmAlarm _devAlmAlarm;

    /** 
     * Setter for devAlmAlarm
     * @param devAlmAlarm the org.sourceforge.ifx.framework.element.DevAlmAlarm to set
     */
    public void setDevAlmAlarm(org.sourceforge.ifx.framework.element.DevAlmAlarm _devAlmAlarm) {
        this._devAlmAlarm = _devAlmAlarm;
    }

    /**
     * Getter for devAlmAlarm
     * @return a org.sourceforge.ifx.framework.element.DevAlmAlarm
     */
    public org.sourceforge.ifx.framework.element.DevAlmAlarm getDevAlmAlarm() {
        return _devAlmAlarm;
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
       * DevAlmType
       * DevAlmOperation
       * DevAlmAlarm
       */
    public final String[] ELEMENTS = {
              "DevAlmType"
                 ,"DevAlmOperation"
                 ,"DevAlmAlarm"
          };
}
