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
public class Term_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public Term_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Count _count;

    /** 
     * Setter for count
     * @param count the org.sourceforge.ifx.framework.element.Count to set
     */
    public void setCount(org.sourceforge.ifx.framework.element.Count _count) {
        this._count = _count;
    }

    /**
     * Getter for count
     * @return a org.sourceforge.ifx.framework.element.Count
     */
    public org.sourceforge.ifx.framework.element.Count getCount() {
        return _count;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TermUnits _termUnits;

    /** 
     * Setter for termUnits
     * @param termUnits the org.sourceforge.ifx.framework.element.TermUnits to set
     */
    public void setTermUnits(org.sourceforge.ifx.framework.element.TermUnits _termUnits) {
        this._termUnits = _termUnits;
    }

    /**
     * Getter for termUnits
     * @return a org.sourceforge.ifx.framework.element.TermUnits
     */
    public org.sourceforge.ifx.framework.element.TermUnits getTermUnits() {
        return _termUnits;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Desc _desc;

    /** 
     * Setter for desc
     * @param desc the org.sourceforge.ifx.framework.element.Desc to set
     */
    public void setDesc(org.sourceforge.ifx.framework.element.Desc _desc) {
        this._desc = _desc;
    }

    /**
     * Getter for desc
     * @return a org.sourceforge.ifx.framework.element.Desc
     */
    public org.sourceforge.ifx.framework.element.Desc getDesc() {
        return _desc;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DaysCall _daysCall;

    /** 
     * Setter for daysCall
     * @param daysCall the org.sourceforge.ifx.framework.element.DaysCall to set
     */
    public void setDaysCall(org.sourceforge.ifx.framework.element.DaysCall _daysCall) {
        this._daysCall = _daysCall;
    }

    /**
     * Getter for daysCall
     * @return a org.sourceforge.ifx.framework.element.DaysCall
     */
    public org.sourceforge.ifx.framework.element.DaysCall getDaysCall() {
        return _daysCall;
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
       * Count
       * TermUnits
       * Desc
       * DaysCall
       */
    public final String[] ELEMENTS = {
              "Count"
                 ,"TermUnits"
                 ,"Desc"
                 ,"DaysCall"
          };
}
