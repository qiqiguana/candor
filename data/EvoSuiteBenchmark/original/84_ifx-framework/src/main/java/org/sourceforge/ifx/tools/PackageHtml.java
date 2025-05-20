/*
 * $Id: PackageHtml.java,v 1.1 2004/05/03 21:41:31 spal Exp $
 * $Source: /cvsroot/ifx-framework/code/src/org/sourceforge/ifx/tools/PackageHtml.java,v $
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
 * Models a package.html file. This is needed to pass in data to the 
 * velocity template for each package in the generated code.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.1 $
 */
public class PackageHtml {
    
    private String packageName;

    /**
     * Returns the name of the package this should be in.
     * @return the package name for this package.
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * Sets the package name of this package.
     * @param packageName the packageName to set.
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
