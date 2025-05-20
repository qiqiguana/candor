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
public class AsyncRsInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public AsyncRsInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AvailDt _availDt;

    /** 
     * Setter for availDt
     * @param availDt the org.sourceforge.ifx.framework.element.AvailDt to set
     */
    public void setAvailDt(org.sourceforge.ifx.framework.element.AvailDt _availDt) {
        this._availDt = _availDt;
    }

    /**
     * Getter for availDt
     * @return a org.sourceforge.ifx.framework.element.AvailDt
     */
    public org.sourceforge.ifx.framework.element.AvailDt getAvailDt() {
        return _availDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ExpDt _expDt;

    /** 
     * Setter for expDt
     * @param expDt the org.sourceforge.ifx.framework.element.ExpDt to set
     */
    public void setExpDt(org.sourceforge.ifx.framework.element.ExpDt _expDt) {
        this._expDt = _expDt;
    }

    /**
     * Getter for expDt
     * @return a org.sourceforge.ifx.framework.element.ExpDt
     */
    public org.sourceforge.ifx.framework.element.ExpDt getExpDt() {
        return _expDt;
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
       * AvailDt
       * ExpDt
       */
    public final String[] ELEMENTS = {
              "AvailDt"
                 ,"ExpDt"
          };
}
