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
public class RecCtrlIn_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public RecCtrlIn_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MaxRec _maxRec;

    /** 
     * Setter for maxRec
     * @param maxRec the org.sourceforge.ifx.framework.element.MaxRec to set
     */
    public void setMaxRec(org.sourceforge.ifx.framework.element.MaxRec _maxRec) {
        this._maxRec = _maxRec;
    }

    /**
     * Getter for maxRec
     * @return a org.sourceforge.ifx.framework.element.MaxRec
     */
    public org.sourceforge.ifx.framework.element.MaxRec getMaxRec() {
        return _maxRec;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Cursor _cursor;

    /** 
     * Setter for cursor
     * @param cursor the org.sourceforge.ifx.framework.element.Cursor to set
     */
    public void setCursor(org.sourceforge.ifx.framework.element.Cursor _cursor) {
        this._cursor = _cursor;
    }

    /**
     * Getter for cursor
     * @return a org.sourceforge.ifx.framework.element.Cursor
     */
    public org.sourceforge.ifx.framework.element.Cursor getCursor() {
        return _cursor;
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
       * MaxRec
       * Cursor
       */
    public final String[] ELEMENTS = {
              "MaxRec"
                 ,"Cursor"
          };
}
