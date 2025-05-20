/*
 * $Id: IFXBoolean.java,v 1.1 2005/12/28 09:46:40 spal Exp $
 * $Source: /cvsroot/ifx-framework/code/src/org/sourceforge/ifx/basetypes/IFXBoolean.java,v $
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
 * The IFXBoolean class models the xsd:boolean datatype.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.1 $
 */
public class IFXBoolean extends IFXObject implements IBaseType {

    private boolean _bool;

    /** default constructor */
    public IFXBoolean() {;}

    /**
     * Returns the object as a String.
     * @return a String object.
     */
    public String getString() {
        return new Boolean(_bool).toString();
    }

    /**
     * Sets the object from a String.
     * @param s a String to set.
     */
    public void setString(String s) {
        this._bool = new Boolean(s).booleanValue();
    }

    /**
     * Compares two IFXBoolean objects for equality.
     * @param obj the object to compare against.
     * @return true or false.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof IFXBoolean)) return false;
        IFXBoolean that = (IFXBoolean) obj;
        return (this.getString().equals(that.getString()));
    }
}
