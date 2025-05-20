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
package org.sourceforge.ifx.framework.remit.complextype;

/**
 * Generated code.
 * 
 * @author org.sourceforge.ifx.tools.CodeGenerator
 */
public class CompositeContactInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CompositeContactInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ContactInfoType _contactInfoType;

    /** 
     * Setter for contactInfoType
     * @param contactInfoType the org.sourceforge.ifx.framework.element.ContactInfoType to set
     */
    public void setContactInfoType(org.sourceforge.ifx.framework.element.ContactInfoType _contactInfoType) {
        this._contactInfoType = _contactInfoType;
    }

    /**
     * Getter for contactInfoType
     * @return a org.sourceforge.ifx.framework.element.ContactInfoType
     */
    public org.sourceforge.ifx.framework.element.ContactInfoType getContactInfoType() {
        return _contactInfoType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ContactInfo _contactInfo;

    /** 
     * Setter for contactInfo
     * @param contactInfo the org.sourceforge.ifx.framework.element.ContactInfo to set
     */
    public void setContactInfo(org.sourceforge.ifx.framework.element.ContactInfo _contactInfo) {
        this._contactInfo = _contactInfo;
    }

    /**
     * Getter for contactInfo
     * @return a org.sourceforge.ifx.framework.element.ContactInfo
     */
    public org.sourceforge.ifx.framework.element.ContactInfo getContactInfo() {
        return _contactInfo;
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
       * ContactInfoType
       * ContactInfo
       */
    public final String[] ELEMENTS = {
              "ContactInfoType"
                 ,"ContactInfo"
          };
}
