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
public class DevDetailPIN_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DevDetailPIN_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevPINType _devPINType;

    /** 
     * Setter for devPINType
     * @param devPINType the org.sourceforge.ifx.framework.element.DevPINType to set
     */
    public void setDevPINType(org.sourceforge.ifx.framework.element.DevPINType _devPINType) {
        this._devPINType = _devPINType;
    }

    /**
     * Getter for devPINType
     * @return a org.sourceforge.ifx.framework.element.DevPINType
     */
    public org.sourceforge.ifx.framework.element.DevPINType getDevPINType() {
        return _devPINType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevPINOperation _devPINOperation;

    /** 
     * Setter for devPINOperation
     * @param devPINOperation the org.sourceforge.ifx.framework.element.DevPINOperation to set
     */
    public void setDevPINOperation(org.sourceforge.ifx.framework.element.DevPINOperation _devPINOperation) {
        this._devPINOperation = _devPINOperation;
    }

    /**
     * Getter for devPINOperation
     * @return a org.sourceforge.ifx.framework.element.DevPINOperation
     */
    public org.sourceforge.ifx.framework.element.DevPINOperation getDevPINOperation() {
        return _devPINOperation;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevPINEncStat _devPINEncStat;

    /** 
     * Setter for devPINEncStat
     * @param devPINEncStat the org.sourceforge.ifx.framework.element.DevPINEncStat to set
     */
    public void setDevPINEncStat(org.sourceforge.ifx.framework.element.DevPINEncStat _devPINEncStat) {
        this._devPINEncStat = _devPINEncStat;
    }

    /**
     * Getter for devPINEncStat
     * @return a org.sourceforge.ifx.framework.element.DevPINEncStat
     */
    public org.sourceforge.ifx.framework.element.DevPINEncStat getDevPINEncStat() {
        return _devPINEncStat;
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
       * DevPINType
       * DevPINOperation
       * DevPINEncStat
       */
    public final String[] ELEMENTS = {
              "DevPINType"
                 ,"DevPINOperation"
                 ,"DevPINEncStat"
          };
}
