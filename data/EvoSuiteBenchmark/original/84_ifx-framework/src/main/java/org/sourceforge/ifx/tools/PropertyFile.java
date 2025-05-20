/*
 * $Id: PropertyFile.java,v 1.1 2004/05/03 21:41:31 spal Exp $
 * $Source: /cvsroot/ifx-framework/code/src/org/sourceforge/ifx/tools/PropertyFile.java,v $
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
package org.sourceforge.ifx.tools;

import java.util.List;
import java.util.ArrayList;

/**
 * Models a Java properties file to collect information about the element
 * and bean mappings.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.1 $
 */
public class PropertyFile {

    private List propertyList = new ArrayList();

    /**
     * Default constructor.
     */
    public PropertyFile() {;}

    /**
     * Add a property to the propertyfile list.
     * @param name the name part of the property name-value pair.
     * @param value teh value part of the property name-value pair.
     */
    public void addProperty(String name, String value) {
        NameValuePair nvp = new NameValuePair(name, value);
        propertyList.add(nvp);
    }

    /**
     * Return a list of property name value pairs.
     * @return a list of property name-value pairs.
     */
    public List getPropertyList() {
        return propertyList;
    }
}
