/*
 * $Id: IFXLong.java,v 1.1 2004/02/23 03:36:46 spal Exp $
 * $Source: /cvsroot/ifx-framework/code/src/org/sourceforge/ifx/basetypes/IFXLong.java,v $
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
package org.sourceforge.ifx.basetypes;

/**
 * This class provides a wrapper for the xsd:long datatype.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.1 $
 */
public class IFXLong extends IFXObject implements IBaseType {

    private java.lang.Long _long;

    /** Default constructor. */
    public IFXLong() {;}

    // method implementations for the IBaseType interface

    /**
     * Gets the String representation of the object.
     * @return a string.
     */
    public java.lang.String getString() {
        return this._long.toString();
    }

    /**
     * Sets the object from the String representation.
     * @param s the String to set.
     */
    public void setString(java.lang.String s) {
        this._long = new java.lang.Long(s);
    }

    /**
     * Compares two IFXLong objects for equality.
     * @param obj the object to compare against.
     * @return true or false.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof IFXLong)) return false;
        IFXLong that = (IFXLong) obj;
        return (this.getString().equals(that.getString()));
    }
}
