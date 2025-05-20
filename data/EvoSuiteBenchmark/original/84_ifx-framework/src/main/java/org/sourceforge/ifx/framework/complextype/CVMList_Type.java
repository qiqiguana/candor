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
public class CVMList_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CVMList_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AmtX _amtX;

    /** 
     * Setter for amtX
     * @param amtX the org.sourceforge.ifx.framework.element.AmtX to set
     */
    public void setAmtX(org.sourceforge.ifx.framework.element.AmtX _amtX) {
        this._amtX = _amtX;
    }

    /**
     * Getter for amtX
     * @return a org.sourceforge.ifx.framework.element.AmtX
     */
    public org.sourceforge.ifx.framework.element.AmtX getAmtX() {
        return _amtX;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AmtY _amtY;

    /** 
     * Setter for amtY
     * @param amtY the org.sourceforge.ifx.framework.element.AmtY to set
     */
    public void setAmtY(org.sourceforge.ifx.framework.element.AmtY _amtY) {
        this._amtY = _amtY;
    }

    /**
     * Getter for amtY
     * @return a org.sourceforge.ifx.framework.element.AmtY
     */
    public org.sourceforge.ifx.framework.element.AmtY getAmtY() {
        return _amtY;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CVMRule[] _cVMRule;

    /** 
     * Setter for cVMRule
     * @param cVMRule the org.sourceforge.ifx.framework.element.CVMRule[] to set
     */
    public void setCVMRule(org.sourceforge.ifx.framework.element.CVMRule[] _cVMRule) {
        this._cVMRule = _cVMRule;
    }

    /**
     * Getter for cVMRule
     * @return a org.sourceforge.ifx.framework.element.CVMRule[]
     */
    public org.sourceforge.ifx.framework.element.CVMRule[] getCVMRule() {
        return _cVMRule;
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
       * AmtX
       * AmtY
       * CVMRule
       */
    public final String[] ELEMENTS = {
              "AmtX"
                 ,"AmtY"
                 ,"CVMRule"
          };
}
