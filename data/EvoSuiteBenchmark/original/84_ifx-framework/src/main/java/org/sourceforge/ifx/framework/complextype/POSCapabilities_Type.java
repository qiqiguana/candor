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
public class POSCapabilities_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public POSCapabilities_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.POSAttended _pOSAttended;

    /** 
     * Setter for pOSAttended
     * @param pOSAttended the org.sourceforge.ifx.framework.element.POSAttended to set
     */
    public void setPOSAttended(org.sourceforge.ifx.framework.element.POSAttended _pOSAttended) {
        this._pOSAttended = _pOSAttended;
    }

    /**
     * Getter for pOSAttended
     * @return a org.sourceforge.ifx.framework.element.POSAttended
     */
    public org.sourceforge.ifx.framework.element.POSAttended getPOSAttended() {
        return _pOSAttended;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.POSOperation[] _pOSOperation;

    /** 
     * Setter for pOSOperation
     * @param pOSOperation the org.sourceforge.ifx.framework.element.POSOperation[] to set
     */
    public void setPOSOperation(org.sourceforge.ifx.framework.element.POSOperation[] _pOSOperation) {
        this._pOSOperation = _pOSOperation;
    }

    /**
     * Getter for pOSOperation
     * @return a org.sourceforge.ifx.framework.element.POSOperation[]
     */
    public org.sourceforge.ifx.framework.element.POSOperation[] getPOSOperation() {
        return _pOSOperation;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.POSEntryCapability[] _pOSEntryCapability;

    /** 
     * Setter for pOSEntryCapability
     * @param pOSEntryCapability the org.sourceforge.ifx.framework.element.POSEntryCapability[] to set
     */
    public void setPOSEntryCapability(org.sourceforge.ifx.framework.element.POSEntryCapability[] _pOSEntryCapability) {
        this._pOSEntryCapability = _pOSEntryCapability;
    }

    /**
     * Getter for pOSEntryCapability
     * @return a org.sourceforge.ifx.framework.element.POSEntryCapability[]
     */
    public org.sourceforge.ifx.framework.element.POSEntryCapability[] getPOSEntryCapability() {
        return _pOSEntryCapability;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.POSCaptureCapability[] _pOSCaptureCapability;

    /** 
     * Setter for pOSCaptureCapability
     * @param pOSCaptureCapability the org.sourceforge.ifx.framework.element.POSCaptureCapability[] to set
     */
    public void setPOSCaptureCapability(org.sourceforge.ifx.framework.element.POSCaptureCapability[] _pOSCaptureCapability) {
        this._pOSCaptureCapability = _pOSCaptureCapability;
    }

    /**
     * Getter for pOSCaptureCapability
     * @return a org.sourceforge.ifx.framework.element.POSCaptureCapability[]
     */
    public org.sourceforge.ifx.framework.element.POSCaptureCapability[] getPOSCaptureCapability() {
        return _pOSCaptureCapability;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.POSVerifyCapability[] _pOSVerifyCapability;

    /** 
     * Setter for pOSVerifyCapability
     * @param pOSVerifyCapability the org.sourceforge.ifx.framework.element.POSVerifyCapability[] to set
     */
    public void setPOSVerifyCapability(org.sourceforge.ifx.framework.element.POSVerifyCapability[] _pOSVerifyCapability) {
        this._pOSVerifyCapability = _pOSVerifyCapability;
    }

    /**
     * Getter for pOSVerifyCapability
     * @return a org.sourceforge.ifx.framework.element.POSVerifyCapability[]
     */
    public org.sourceforge.ifx.framework.element.POSVerifyCapability[] getPOSVerifyCapability() {
        return _pOSVerifyCapability;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.POSOutCapabilities[] _pOSOutCapabilities;

    /** 
     * Setter for pOSOutCapabilities
     * @param pOSOutCapabilities the org.sourceforge.ifx.framework.element.POSOutCapabilities[] to set
     */
    public void setPOSOutCapabilities(org.sourceforge.ifx.framework.element.POSOutCapabilities[] _pOSOutCapabilities) {
        this._pOSOutCapabilities = _pOSOutCapabilities;
    }

    /**
     * Getter for pOSOutCapabilities
     * @return a org.sourceforge.ifx.framework.element.POSOutCapabilities[]
     */
    public org.sourceforge.ifx.framework.element.POSOutCapabilities[] getPOSOutCapabilities() {
        return _pOSOutCapabilities;
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
       * POSAttended
       * POSOperation
       * POSEntryCapability
       * POSCaptureCapability
       * POSVerifyCapability
       * POSOutCapabilities
       */
    public final String[] ELEMENTS = {
              "POSAttended"
                 ,"POSOperation"
                 ,"POSEntryCapability"
                 ,"POSCaptureCapability"
                 ,"POSVerifyCapability"
                 ,"POSOutCapabilities"
          };
}
