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
public class DevDetailApp_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DevDetailApp_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevAppOperation[] _devAppOperation;

    /** 
     * Setter for devAppOperation
     * @param devAppOperation the org.sourceforge.ifx.framework.element.DevAppOperation[] to set
     */
    public void setDevAppOperation(org.sourceforge.ifx.framework.element.DevAppOperation[] _devAppOperation) {
        this._devAppOperation = _devAppOperation;
    }

    /**
     * Getter for devAppOperation
     * @return a org.sourceforge.ifx.framework.element.DevAppOperation[]
     */
    public org.sourceforge.ifx.framework.element.DevAppOperation[] getDevAppOperation() {
        return _devAppOperation;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevAppNormalOperation[] _devAppNormalOperation;

    /** 
     * Setter for devAppNormalOperation
     * @param devAppNormalOperation the org.sourceforge.ifx.framework.element.DevAppNormalOperation[] to set
     */
    public void setDevAppNormalOperation(org.sourceforge.ifx.framework.element.DevAppNormalOperation[] _devAppNormalOperation) {
        this._devAppNormalOperation = _devAppNormalOperation;
    }

    /**
     * Getter for devAppNormalOperation
     * @return a org.sourceforge.ifx.framework.element.DevAppNormalOperation[]
     */
    public org.sourceforge.ifx.framework.element.DevAppNormalOperation[] getDevAppNormalOperation() {
        return _devAppNormalOperation;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevAppComponent[] _devAppComponent;

    /** 
     * Setter for devAppComponent
     * @param devAppComponent the org.sourceforge.ifx.framework.element.DevAppComponent[] to set
     */
    public void setDevAppComponent(org.sourceforge.ifx.framework.element.DevAppComponent[] _devAppComponent) {
        this._devAppComponent = _devAppComponent;
    }

    /**
     * Getter for devAppComponent
     * @return a org.sourceforge.ifx.framework.element.DevAppComponent[]
     */
    public org.sourceforge.ifx.framework.element.DevAppComponent[] getDevAppComponent() {
        return _devAppComponent;
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
       * DevAppOperation
       * DevAppNormalOperation
       * DevAppComponent
       */
    public final String[] ELEMENTS = {
              "DevAppOperation"
                 ,"DevAppNormalOperation"
                 ,"DevAppComponent"
          };
}
