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
public class PhoneNum_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PhoneNum_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PhoneType _phoneType;

    /** 
     * Setter for phoneType
     * @param phoneType the org.sourceforge.ifx.framework.element.PhoneType to set
     */
    public void setPhoneType(org.sourceforge.ifx.framework.element.PhoneType _phoneType) {
        this._phoneType = _phoneType;
    }

    /**
     * Getter for phoneType
     * @return a org.sourceforge.ifx.framework.element.PhoneType
     */
    public org.sourceforge.ifx.framework.element.PhoneType getPhoneType() {
        return _phoneType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Phone _phone;

    /** 
     * Setter for phone
     * @param phone the org.sourceforge.ifx.framework.element.Phone to set
     */
    public void setPhone(org.sourceforge.ifx.framework.element.Phone _phone) {
        this._phone = _phone;
    }

    /**
     * Getter for phone
     * @return a org.sourceforge.ifx.framework.element.Phone
     */
    public org.sourceforge.ifx.framework.element.Phone getPhone() {
        return _phone;
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
       * PhoneType
       * Phone
       */
    public final String[] ELEMENTS = {
              "PhoneType"
                 ,"Phone"
          };
}
