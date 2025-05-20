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
public class SelRangeDt_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public SelRangeDt_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StartDt _startDt;

    /** 
     * Setter for startDt
     * @param startDt the org.sourceforge.ifx.framework.element.StartDt to set
     */
    public void setStartDt(org.sourceforge.ifx.framework.element.StartDt _startDt) {
        this._startDt = _startDt;
    }

    /**
     * Getter for startDt
     * @return a org.sourceforge.ifx.framework.element.StartDt
     */
    public org.sourceforge.ifx.framework.element.StartDt getStartDt() {
        return _startDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.EndDt _endDt;

    /** 
     * Setter for endDt
     * @param endDt the org.sourceforge.ifx.framework.element.EndDt to set
     */
    public void setEndDt(org.sourceforge.ifx.framework.element.EndDt _endDt) {
        this._endDt = _endDt;
    }

    /**
     * Getter for endDt
     * @return a org.sourceforge.ifx.framework.element.EndDt
     */
    public org.sourceforge.ifx.framework.element.EndDt getEndDt() {
        return _endDt;
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
       * StartDt
       * EndDt
       */
    public final String[] ELEMENTS = {
              "StartDt"
                 ,"EndDt"
          };
}
