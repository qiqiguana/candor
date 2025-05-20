/*
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
package org.sourceforge.ifx.framework.complextype;

/**
 * Generated code.
 * 
 * @author org.sourceforge.ifx.tools.CodeGenerator
 */
public class DevPlatformData_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DevPlatformData_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevPlatformSource _devPlatformSource;

    /** 
     * Setter for devPlatformSource
     * @param devPlatformSource the org.sourceforge.ifx.framework.element.DevPlatformSource to set
     */
    public void setDevPlatformSource(org.sourceforge.ifx.framework.element.DevPlatformSource _devPlatformSource) {
        this._devPlatformSource = _devPlatformSource;
    }

    /**
     * Getter for devPlatformSource
     * @return a org.sourceforge.ifx.framework.element.DevPlatformSource
     */
    public org.sourceforge.ifx.framework.element.DevPlatformSource getDevPlatformSource() {
        return _devPlatformSource;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevPlatformXfs _devPlatformXfs;

    /** 
     * Setter for devPlatformXfs
     * @param devPlatformXfs the org.sourceforge.ifx.framework.element.DevPlatformXfs to set
     */
    public void setDevPlatformXfs(org.sourceforge.ifx.framework.element.DevPlatformXfs _devPlatformXfs) {
        this._devPlatformXfs = _devPlatformXfs;
    }

    /**
     * Getter for devPlatformXfs
     * @return a org.sourceforge.ifx.framework.element.DevPlatformXfs
     */
    public org.sourceforge.ifx.framework.element.DevPlatformXfs getDevPlatformXfs() {
        return _devPlatformXfs;
    }


    /**
     * Returns true if objects are equal, false otherwise.
     * @param obj the object to compare with.
     * @return true if equal, false if not.
     */
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /** Element ordering - 
       * DevPlatformSource
       * DevPlatformXfs
       */
    public final String[] ELEMENTS = {
              "DevPlatformSource"
                 ,"DevPlatformXfs"
          };
}
