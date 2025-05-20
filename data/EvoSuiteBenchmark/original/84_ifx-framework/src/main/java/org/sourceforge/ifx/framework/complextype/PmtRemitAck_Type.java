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
public class PmtRemitAck_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PmtRemitAck_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TotalFeeCharge _totalFeeCharge;

    /** 
     * Setter for totalFeeCharge
     * @param totalFeeCharge the org.sourceforge.ifx.framework.element.TotalFeeCharge to set
     */
    public void setTotalFeeCharge(org.sourceforge.ifx.framework.element.TotalFeeCharge _totalFeeCharge) {
        this._totalFeeCharge = _totalFeeCharge;
    }

    /**
     * Getter for totalFeeCharge
     * @return a org.sourceforge.ifx.framework.element.TotalFeeCharge
     */
    public org.sourceforge.ifx.framework.element.TotalFeeCharge getTotalFeeCharge() {
        return _totalFeeCharge;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CompositePmtRemitInfo[] _compositePmtRemitInfo;

    /** 
     * Setter for compositePmtRemitInfo
     * @param compositePmtRemitInfo the org.sourceforge.ifx.framework.element.CompositePmtRemitInfo[] to set
     */
    public void setCompositePmtRemitInfo(org.sourceforge.ifx.framework.element.CompositePmtRemitInfo[] _compositePmtRemitInfo) {
        this._compositePmtRemitInfo = _compositePmtRemitInfo;
    }

    /**
     * Getter for compositePmtRemitInfo
     * @return a org.sourceforge.ifx.framework.element.CompositePmtRemitInfo[]
     */
    public org.sourceforge.ifx.framework.element.CompositePmtRemitInfo[] getCompositePmtRemitInfo() {
        return _compositePmtRemitInfo;
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
       * TotalFeeCharge
       * CompositePmtRemitInfo
       */
    public final String[] ELEMENTS = {
              "TotalFeeCharge"
                 ,"CompositePmtRemitInfo"
          };
}
