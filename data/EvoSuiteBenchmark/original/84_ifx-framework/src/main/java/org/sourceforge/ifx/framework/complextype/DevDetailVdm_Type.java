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
public class DevDetailVdm_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DevDetailVdm_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevVdmOperation _devVdmOperation;

    /** 
     * Setter for devVdmOperation
     * @param devVdmOperation the org.sourceforge.ifx.framework.element.DevVdmOperation to set
     */
    public void setDevVdmOperation(org.sourceforge.ifx.framework.element.DevVdmOperation _devVdmOperation) {
        this._devVdmOperation = _devVdmOperation;
    }

    /**
     * Getter for devVdmOperation
     * @return a org.sourceforge.ifx.framework.element.DevVdmOperation
     */
    public org.sourceforge.ifx.framework.element.DevVdmOperation getDevVdmOperation() {
        return _devVdmOperation;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevVdmServiceState _devVdmServiceState;

    /** 
     * Setter for devVdmServiceState
     * @param devVdmServiceState the org.sourceforge.ifx.framework.element.DevVdmServiceState to set
     */
    public void setDevVdmServiceState(org.sourceforge.ifx.framework.element.DevVdmServiceState _devVdmServiceState) {
        this._devVdmServiceState = _devVdmServiceState;
    }

    /**
     * Getter for devVdmServiceState
     * @return a org.sourceforge.ifx.framework.element.DevVdmServiceState
     */
    public org.sourceforge.ifx.framework.element.DevVdmServiceState getDevVdmServiceState() {
        return _devVdmServiceState;
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
       * DevVdmOperation
       * DevVdmServiceState
       */
    public final String[] ELEMENTS = {
              "DevVdmOperation"
                 ,"DevVdmServiceState"
          };
}
