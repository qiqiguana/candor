/*
 * $Id: MarkerInterface.java,v 1.3 2004/05/03 21:42:29 spal Exp $
 * $Source: /cvsroot/ifx-framework/code/src/org/sourceforge/ifx/tools/MarkerInterface.java,v $
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
 * Class to build a marker interface for choice elements.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.3 $
 */
public class MarkerInterface {

    private String packageName = null;
    private String name = null;

    /**
     * Constructs an empty MarkerInterface object.
     */
    public MarkerInterface() {;}

    /**
     * Returns the package name.
     * @return the package name.
     */
    public String getPackage() {
        return packageName;
    }

    /**
     * Sets the package name.
     * @param packageName the package name.
     */
    public void setPackage(String packageName) {
        this.packageName = packageName;
    }

    /**
     * Returns the class name.
     * @return the class name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the class name.
     * @param name the class name.
     */
    public void setName(String name) {
        this.name = name;
    }
}
