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
public class ATMTrnInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public ATMTrnInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ATMOwner _aTMOwner;

    /** 
     * Setter for aTMOwner
     * @param aTMOwner the org.sourceforge.ifx.framework.element.ATMOwner to set
     */
    public void setATMOwner(org.sourceforge.ifx.framework.element.ATMOwner _aTMOwner) {
        this._aTMOwner = _aTMOwner;
    }

    /**
     * Getter for aTMOwner
     * @return a org.sourceforge.ifx.framework.element.ATMOwner
     */
    public org.sourceforge.ifx.framework.element.ATMOwner getATMOwner() {
        return _aTMOwner;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ATMLocation _aTMLocation;

    /** 
     * Setter for aTMLocation
     * @param aTMLocation the org.sourceforge.ifx.framework.element.ATMLocation to set
     */
    public void setATMLocation(org.sourceforge.ifx.framework.element.ATMLocation _aTMLocation) {
        this._aTMLocation = _aTMLocation;
    }

    /**
     * Getter for aTMLocation
     * @return a org.sourceforge.ifx.framework.element.ATMLocation
     */
    public org.sourceforge.ifx.framework.element.ATMLocation getATMLocation() {
        return _aTMLocation;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ATMCityState _aTMCityState;

    /** 
     * Setter for aTMCityState
     * @param aTMCityState the org.sourceforge.ifx.framework.element.ATMCityState to set
     */
    public void setATMCityState(org.sourceforge.ifx.framework.element.ATMCityState _aTMCityState) {
        this._aTMCityState = _aTMCityState;
    }

    /**
     * Getter for aTMCityState
     * @return a org.sourceforge.ifx.framework.element.ATMCityState
     */
    public org.sourceforge.ifx.framework.element.ATMCityState getATMCityState() {
        return _aTMCityState;
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
       * ATMOwner
       * ATMLocation
       * ATMCityState
       */
    public final String[] ELEMENTS = {
              "ATMOwner"
                 ,"ATMLocation"
                 ,"ATMCityState"
          };
}
