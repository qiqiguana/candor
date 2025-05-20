/*
 * $Id: IFXBase64Binary.java,v 1.1 2004/02/23 03:36:46 spal Exp $
 * $Source: /cvsroot/ifx-framework/code/src/org/sourceforge/ifx/basetypes/IFXBase64Binary.java,v $
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
 * Wrapper class to accomodate xsd:base64Binary which translates to byte[] 
 * in Java. Instead, we make our generator translate the xsd:base64Binary
 * to this IFXBase64Binary class which has methods to get and set an array of 
 * bytes.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.1 $
 */
public class IFXBase64Binary extends IFXObject implements IBaseType {
    
    private byte[] bytes;

    /** Default ctor. */
    public IFXBase64Binary() {;}

    // method implementations for IBaseType

    /**
     * Returns the string from the internal representation.
     * @return the string representation.
     */
    public String getString() {
        return new String(bytes);
    }

    /**
     * Sets the internal representation from the string.
     * @param s the string to convert.
     */
    public void setString(String s) {
        bytes = s.getBytes();
    }

    public byte[] getBytes() {
        return bytes;
    }

    /**
     * Compares two IFXBase64Binary objects for equality.
     * @param obj the object to compare against.
     * @return true or false.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof IFXBase64Binary)) return false;
        IFXBase64Binary that = (IFXBase64Binary) obj;
        byte[] thisbytes = this.getString().getBytes();
        byte[] thatbytes = that.getString().getBytes();
        if (thisbytes.length != thatbytes.length) return false;
        for (int i = 0; i < thisbytes.length; i++) {
            if (thisbytes[i] != thatbytes[i]) return false;
        }
        return true;
    }
}
