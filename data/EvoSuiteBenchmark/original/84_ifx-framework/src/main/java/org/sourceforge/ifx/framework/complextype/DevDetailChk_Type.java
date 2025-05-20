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
public class DevDetailChk_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DevDetailChk_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevChkType _devChkType;

    /** 
     * Setter for devChkType
     * @param devChkType the org.sourceforge.ifx.framework.element.DevChkType to set
     */
    public void setDevChkType(org.sourceforge.ifx.framework.element.DevChkType _devChkType) {
        this._devChkType = _devChkType;
    }

    /**
     * Getter for devChkType
     * @return a org.sourceforge.ifx.framework.element.DevChkType
     */
    public org.sourceforge.ifx.framework.element.DevChkType getDevChkType() {
        return _devChkType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevChkOperation _devChkOperation;

    /** 
     * Setter for devChkOperation
     * @param devChkOperation the org.sourceforge.ifx.framework.element.DevChkOperation to set
     */
    public void setDevChkOperation(org.sourceforge.ifx.framework.element.DevChkOperation _devChkOperation) {
        this._devChkOperation = _devChkOperation;
    }

    /**
     * Getter for devChkOperation
     * @return a org.sourceforge.ifx.framework.element.DevChkOperation
     */
    public org.sourceforge.ifx.framework.element.DevChkOperation getDevChkOperation() {
        return _devChkOperation;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevChkImageEnabled _devChkImageEnabled;

    /** 
     * Setter for devChkImageEnabled
     * @param devChkImageEnabled the org.sourceforge.ifx.framework.element.DevChkImageEnabled to set
     */
    public void setDevChkImageEnabled(org.sourceforge.ifx.framework.element.DevChkImageEnabled _devChkImageEnabled) {
        this._devChkImageEnabled = _devChkImageEnabled;
    }

    /**
     * Getter for devChkImageEnabled
     * @return a org.sourceforge.ifx.framework.element.DevChkImageEnabled
     */
    public org.sourceforge.ifx.framework.element.DevChkImageEnabled getDevChkImageEnabled() {
        return _devChkImageEnabled;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevChkMedia _devChkMedia;

    /** 
     * Setter for devChkMedia
     * @param devChkMedia the org.sourceforge.ifx.framework.element.DevChkMedia to set
     */
    public void setDevChkMedia(org.sourceforge.ifx.framework.element.DevChkMedia _devChkMedia) {
        this._devChkMedia = _devChkMedia;
    }

    /**
     * Getter for devChkMedia
     * @return a org.sourceforge.ifx.framework.element.DevChkMedia
     */
    public org.sourceforge.ifx.framework.element.DevChkMedia getDevChkMedia() {
        return _devChkMedia;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevChkInk _devChkInk;

    /** 
     * Setter for devChkInk
     * @param devChkInk the org.sourceforge.ifx.framework.element.DevChkInk to set
     */
    public void setDevChkInk(org.sourceforge.ifx.framework.element.DevChkInk _devChkInk) {
        this._devChkInk = _devChkInk;
    }

    /**
     * Getter for devChkInk
     * @return a org.sourceforge.ifx.framework.element.DevChkInk
     */
    public org.sourceforge.ifx.framework.element.DevChkInk getDevChkInk() {
        return _devChkInk;
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
       * DevChkType
       * DevChkOperation
       * DevChkImageEnabled
       * DevChkMedia
       * DevChkInk
       */
    public final String[] ELEMENTS = {
              "DevChkType"
                 ,"DevChkOperation"
                 ,"DevChkImageEnabled"
                 ,"DevChkMedia"
                 ,"DevChkInk"
          };
}
