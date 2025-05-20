/*
 * $Id: IFXDateTime.java,v 1.1 2004/02/23 03:36:46 spal Exp $
 * $Source: /cvsroot/ifx-framework/code/src/org/sourceforge/ifx/basetypes/IFXDateTime.java,v $
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
 * The IFXDateTime class provides an abstraction of the xsd:dateTime datatype.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.1 $
 */
public class IFXDateTime extends IFXObject implements IBaseType {

    // can be yyyy-MM-dd(THH:mm:ss(.SSSSSS(-ZZ:ZZ)))
    private final static SimpleDateFormat[] sdf = {
        new SimpleDateFormat("yyyy-MM-dd"),
        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"),
        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"),
        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS-ZZ:ZZ")
    };
    private GregorianCalendar _cal;

    public IFXDateTime() {
        this._cal = new GregorianCalendar();
    }

    // implementation of IBaseType methods

    /**
     * Returns the String representation of this object.
     * @return a String.
     */
    public String getString() {
        return sdf[3].format(this._cal.getTime());
    }

    /**
     * Sets the object value from a String.
     * @param s a String
     */
    public void setString(String s) {
        String[] tokens = s.split("[-T:.]");
        try {
            switch (tokens.length) {
                case 3: // yyyy-MM-dd
                    this._cal.setTime(sdf[0].parse(s));
                    break;
                case 6: // yyyy-MM-ddTHH:mm:ss
                    this._cal.setTime(sdf[1].parse(s));
                    break;
                case 7: // yyyy-MM-ddTHH:mm:ss.SSSSSS
                    this._cal.setTime(sdf[2].parse(s));
                    break;
                case 9: // yyyy-MM-ddTHH:mm:ss.SSSSSS-ZZ:ZZ
                    this._cal.setTime(sdf[3].parse(s));
                    break;
                default:
            }
        } catch (ParseException e) {}
    }

    /**
     * Compares two IFXDateTime objects for equality.
     * @param obj the object to compare against.
     * @return true or false.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof IFXDateTime)) return false;
        IFXDateTime that = (IFXDateTime) obj;
        return (this.getString().equals(that.getString()));
    }
}
