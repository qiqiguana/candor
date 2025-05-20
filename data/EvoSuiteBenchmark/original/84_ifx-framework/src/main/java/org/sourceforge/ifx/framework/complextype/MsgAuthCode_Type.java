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
public class MsgAuthCode_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public MsgAuthCode_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MacValue _macValue;

    /** 
     * Setter for macValue
     * @param macValue the org.sourceforge.ifx.framework.element.MacValue to set
     */
    public void setMacValue(org.sourceforge.ifx.framework.element.MacValue _macValue) {
        this._macValue = _macValue;
    }

    /**
     * Getter for macValue
     * @return a org.sourceforge.ifx.framework.element.MacValue
     */
    public org.sourceforge.ifx.framework.element.MacValue getMacValue() {
        return _macValue;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MacVariant _macVariant;

    /** 
     * Setter for macVariant
     * @param macVariant the org.sourceforge.ifx.framework.element.MacVariant to set
     */
    public void setMacVariant(org.sourceforge.ifx.framework.element.MacVariant _macVariant) {
        this._macVariant = _macVariant;
    }

    /**
     * Getter for macVariant
     * @return a org.sourceforge.ifx.framework.element.MacVariant
     */
    public org.sourceforge.ifx.framework.element.MacVariant getMacVariant() {
        return _macVariant;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SecObjId _secObjId;

    /** 
     * Setter for secObjId
     * @param secObjId the org.sourceforge.ifx.framework.element.SecObjId to set
     */
    public void setSecObjId(org.sourceforge.ifx.framework.element.SecObjId _secObjId) {
        this._secObjId = _secObjId;
    }

    /**
     * Getter for secObjId
     * @return a org.sourceforge.ifx.framework.element.SecObjId
     */
    public org.sourceforge.ifx.framework.element.SecObjId getSecObjId() {
        return _secObjId;
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
       * MacValue
       * MacVariant
       * SecObjId
       */
    public final String[] ELEMENTS = {
              "MacValue"
                 ,"MacVariant"
                 ,"SecObjId"
          };
}
