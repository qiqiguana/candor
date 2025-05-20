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
public class DevDetailTtu_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DevDetailTtu_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevTtuType _devTtuType;

    /** 
     * Setter for devTtuType
     * @param devTtuType the org.sourceforge.ifx.framework.element.DevTtuType to set
     */
    public void setDevTtuType(org.sourceforge.ifx.framework.element.DevTtuType _devTtuType) {
        this._devTtuType = _devTtuType;
    }

    /**
     * Getter for devTtuType
     * @return a org.sourceforge.ifx.framework.element.DevTtuType
     */
    public org.sourceforge.ifx.framework.element.DevTtuType getDevTtuType() {
        return _devTtuType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevTtuOperation _devTtuOperation;

    /** 
     * Setter for devTtuOperation
     * @param devTtuOperation the org.sourceforge.ifx.framework.element.DevTtuOperation to set
     */
    public void setDevTtuOperation(org.sourceforge.ifx.framework.element.DevTtuOperation _devTtuOperation) {
        this._devTtuOperation = _devTtuOperation;
    }

    /**
     * Getter for devTtuOperation
     * @return a org.sourceforge.ifx.framework.element.DevTtuOperation
     */
    public org.sourceforge.ifx.framework.element.DevTtuOperation getDevTtuOperation() {
        return _devTtuOperation;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevTtuKbd _devTtuKbd;

    /** 
     * Setter for devTtuKbd
     * @param devTtuKbd the org.sourceforge.ifx.framework.element.DevTtuKbd to set
     */
    public void setDevTtuKbd(org.sourceforge.ifx.framework.element.DevTtuKbd _devTtuKbd) {
        this._devTtuKbd = _devTtuKbd;
    }

    /**
     * Getter for devTtuKbd
     * @return a org.sourceforge.ifx.framework.element.DevTtuKbd
     */
    public org.sourceforge.ifx.framework.element.DevTtuKbd getDevTtuKbd() {
        return _devTtuKbd;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevTtuKbdLock _devTtuKbdLock;

    /** 
     * Setter for devTtuKbdLock
     * @param devTtuKbdLock the org.sourceforge.ifx.framework.element.DevTtuKbdLock to set
     */
    public void setDevTtuKbdLock(org.sourceforge.ifx.framework.element.DevTtuKbdLock _devTtuKbdLock) {
        this._devTtuKbdLock = _devTtuKbdLock;
    }

    /**
     * Getter for devTtuKbdLock
     * @return a org.sourceforge.ifx.framework.element.DevTtuKbdLock
     */
    public org.sourceforge.ifx.framework.element.DevTtuKbdLock getDevTtuKbdLock() {
        return _devTtuKbdLock;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevTtuLed[] _devTtuLed;

    /** 
     * Setter for devTtuLed
     * @param devTtuLed the org.sourceforge.ifx.framework.element.DevTtuLed[] to set
     */
    public void setDevTtuLed(org.sourceforge.ifx.framework.element.DevTtuLed[] _devTtuLed) {
        this._devTtuLed = _devTtuLed;
    }

    /**
     * Getter for devTtuLed
     * @return a org.sourceforge.ifx.framework.element.DevTtuLed[]
     */
    public org.sourceforge.ifx.framework.element.DevTtuLed[] getDevTtuLed() {
        return _devTtuLed;
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
       * DevTtuType
       * DevTtuOperation
       * DevTtuKbd
       * DevTtuKbdLock
       * DevTtuLed
       */
    public final String[] ELEMENTS = {
              "DevTtuType"
                 ,"DevTtuOperation"
                 ,"DevTtuKbd"
                 ,"DevTtuKbdLock"
                 ,"DevTtuLed"
          };
}
