/*
 * $Id: NameValuePair.java,v 1.1 2004/05/03 21:41:31 spal Exp $
 * $Source: /cvsroot/ifx-framework/code/src/org/sourceforge/ifx/tools/NameValuePair.java,v $
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

/**
 * Models a name-value pair entry in a Java properties file.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.1 $
 */
public class NameValuePair {

    private String name;
    private String value;

    /**
     * Build a name value pair object with the name and value.
     * @param name the name part of the name-value pair.
     * @param value the value part of the name-value pair.
     */
    public NameValuePair(String name, String value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Return the name part of the name-value pair.
     * @return the name part.
     */
    public String getName() {
        return name;
    }

    /**
     * Return the value part of the name-value pair.
     * @return the value part.
     */
    public String getValue() {
        return value;
    }
}
