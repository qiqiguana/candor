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
public class BillCounts_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public BillCounts_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillStatusCounts[] _billStatusCounts;

    /** 
     * Setter for billStatusCounts
     * @param billStatusCounts the org.sourceforge.ifx.framework.element.BillStatusCounts[] to set
     */
    public void setBillStatusCounts(org.sourceforge.ifx.framework.element.BillStatusCounts[] _billStatusCounts) {
        this._billStatusCounts = _billStatusCounts;
    }

    /**
     * Getter for billStatusCounts
     * @return a org.sourceforge.ifx.framework.element.BillStatusCounts[]
     */
    public org.sourceforge.ifx.framework.element.BillStatusCounts[] getBillStatusCounts() {
        return _billStatusCounts;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillPmtStatusCounts[] _billPmtStatusCounts;

    /** 
     * Setter for billPmtStatusCounts
     * @param billPmtStatusCounts the org.sourceforge.ifx.framework.element.BillPmtStatusCounts[] to set
     */
    public void setBillPmtStatusCounts(org.sourceforge.ifx.framework.element.BillPmtStatusCounts[] _billPmtStatusCounts) {
        this._billPmtStatusCounts = _billPmtStatusCounts;
    }

    /**
     * Getter for billPmtStatusCounts
     * @return a org.sourceforge.ifx.framework.element.BillPmtStatusCounts[]
     */
    public org.sourceforge.ifx.framework.element.BillPmtStatusCounts[] getBillPmtStatusCounts() {
        return _billPmtStatusCounts;
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
       * BillStatusCounts
       * BillPmtStatusCounts
       */
    public final String[] ELEMENTS = {
              "BillStatusCounts"
                 ,"BillPmtStatusCounts"
          };
}
