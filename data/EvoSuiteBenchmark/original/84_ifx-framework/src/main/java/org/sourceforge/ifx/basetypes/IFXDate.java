/*
 * $Id: IFXDate.java,v 1.1 2004/02/23 03:36:46 spal Exp $
 * $Source: /cvsroot/ifx-framework/code/src/org/sourceforge/ifx/basetypes/IFXDate.java,v $
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

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.text.ParseException;

/**
 * The IFXDate class provides an abstraction of the xsd:date datatype.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.1 $
 */
public class IFXDate extends IFXObject implements IBaseType {

    private final static SimpleDateFormat sdf = 
        new SimpleDateFormat("yyyy-MM-dd");
    private GregorianCalendar _cal;

    /** Default constructor */
    public IFXDate() {
        this._cal = new GregorianCalendar();
    }

    /**
     * Returns the String representation of this object.
     * @return a String.
     */
    public String getString() {
        return sdf.format(this._cal.getTime());
    }

    /**
     * Sets this object from its string value.
     * @param s the String to set.
     */
    public void setString(String s) {
        try {
            this._cal.setTime(sdf.parse(s));
        } catch (ParseException e) { }
    }

    /**
     * Compares two IFXDate objects for equality.
     * @param obj the object to compare against.
     * @return true or false.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof IFXDate)) return false;
        IFXDate that = (IFXDate) obj;
        return (this.getString().equals(that.getString()));
    }
}
