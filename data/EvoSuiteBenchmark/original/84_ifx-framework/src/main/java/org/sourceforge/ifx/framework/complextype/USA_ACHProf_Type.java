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
public class USA_ACHProf_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public USA_ACHProf_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DaysWith _daysWith;

    /** 
     * Setter for daysWith
     * @param daysWith the org.sourceforge.ifx.framework.element.DaysWith to set
     */
    public void setDaysWith(org.sourceforge.ifx.framework.element.DaysWith _daysWith) {
        this._daysWith = _daysWith;
    }

    /**
     * Getter for daysWith
     * @return a org.sourceforge.ifx.framework.element.DaysWith
     */
    public org.sourceforge.ifx.framework.element.DaysWith getDaysWith() {
        return _daysWith;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DfltDaysToPay _dfltDaysToPay;

    /** 
     * Setter for dfltDaysToPay
     * @param dfltDaysToPay the org.sourceforge.ifx.framework.element.DfltDaysToPay to set
     */
    public void setDfltDaysToPay(org.sourceforge.ifx.framework.element.DfltDaysToPay _dfltDaysToPay) {
        this._dfltDaysToPay = _dfltDaysToPay;
    }

    /**
     * Getter for dfltDaysToPay
     * @return a org.sourceforge.ifx.framework.element.DfltDaysToPay
     */
    public org.sourceforge.ifx.framework.element.DfltDaysToPay getDfltDaysToPay() {
        return _dfltDaysToPay;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CanWind _canWind;

    /** 
     * Setter for canWind
     * @param canWind the org.sourceforge.ifx.framework.element.CanWind to set
     */
    public void setCanWind(org.sourceforge.ifx.framework.element.CanWind _canWind) {
        this._canWind = _canWind;
    }

    /**
     * Getter for canWind
     * @return a org.sourceforge.ifx.framework.element.CanWind
     */
    public org.sourceforge.ifx.framework.element.CanWind getCanWind() {
        return _canWind;
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
       * DaysWith
       * DfltDaysToPay
       * CanWind
       */
    public final String[] ELEMENTS = {
              "DaysWith"
                 ,"DfltDaysToPay"
                 ,"CanWind"
          };
}
