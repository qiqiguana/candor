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
public class MediaTotal_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public MediaTotal_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MediaTotalType _mediaTotalType;

    /** 
     * Setter for mediaTotalType
     * @param mediaTotalType the org.sourceforge.ifx.framework.element.MediaTotalType to set
     */
    public void setMediaTotalType(org.sourceforge.ifx.framework.element.MediaTotalType _mediaTotalType) {
        this._mediaTotalType = _mediaTotalType;
    }

    /**
     * Getter for mediaTotalType
     * @return a org.sourceforge.ifx.framework.element.MediaTotalType
     */
    public org.sourceforge.ifx.framework.element.MediaTotalType getMediaTotalType() {
        return _mediaTotalType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Count[] _count;

    /** 
     * Setter for count
     * @param count the org.sourceforge.ifx.framework.element.Count[] to set
     */
    public void setCount(org.sourceforge.ifx.framework.element.Count[] _count) {
        this._count = _count;
    }

    /**
     * Getter for count
     * @return a org.sourceforge.ifx.framework.element.Count[]
     */
    public org.sourceforge.ifx.framework.element.Count[] getCount() {
        return _count;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TotalCurAmt[] _totalCurAmt;

    /** 
     * Setter for totalCurAmt
     * @param totalCurAmt the org.sourceforge.ifx.framework.element.TotalCurAmt[] to set
     */
    public void setTotalCurAmt(org.sourceforge.ifx.framework.element.TotalCurAmt[] _totalCurAmt) {
        this._totalCurAmt = _totalCurAmt;
    }

    /**
     * Getter for totalCurAmt
     * @return a org.sourceforge.ifx.framework.element.TotalCurAmt[]
     */
    public org.sourceforge.ifx.framework.element.TotalCurAmt[] getTotalCurAmt() {
        return _totalCurAmt;
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
       * MediaTotalType
       * Count
       * TotalCurAmt
       */
    public final String[] ELEMENTS = {
              "MediaTotalType"
                 ,"Count"
                 ,"TotalCurAmt"
          };
}
