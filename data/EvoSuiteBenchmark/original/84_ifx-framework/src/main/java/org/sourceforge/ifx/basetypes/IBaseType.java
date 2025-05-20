/*
 * $Id: IBaseType.java,v 1.3 2004/02/26 17:49:30 spal Exp $
 * $Source: /cvsroot/ifx-framework/code/src/org/sourceforge/ifx/basetypes/IBaseType.java,v $
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
 * The IBaseType interface provides two methods to convert from and to
 * a String. This interface is implemented by objects which model xsd
 * datatypes, since we need a way to convert from the string representation
 * in the XML file to the internal object representation, and vice versa.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.3 $
 */
public interface IBaseType {

    /** 
     * Returns the string representation of the underlying basetype object.
     * @return the string
     */
    public String getString();

    /**
     * Converts the supplied string to its internal representation.
     * @param s the string to convert.
     */
    public void setString(String s);
}
