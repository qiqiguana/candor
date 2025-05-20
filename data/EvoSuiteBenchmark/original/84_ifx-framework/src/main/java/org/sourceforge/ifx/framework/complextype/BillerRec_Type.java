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
public class BillerRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public BillerRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillerId[] _billerId;

    /** 
     * Setter for billerId
     * @param billerId the org.sourceforge.ifx.framework.element.BillerId[] to set
     */
    public void setBillerId(org.sourceforge.ifx.framework.element.BillerId[] _billerId) {
        this._billerId = _billerId;
    }

    /**
     * Getter for billerId
     * @return a org.sourceforge.ifx.framework.element.BillerId[]
     */
    public org.sourceforge.ifx.framework.element.BillerId[] getBillerId() {
        return _billerId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillerInfo[] _billerInfo;

    /** 
     * Setter for billerInfo
     * @param billerInfo the org.sourceforge.ifx.framework.element.BillerInfo[] to set
     */
    public void setBillerInfo(org.sourceforge.ifx.framework.element.BillerInfo[] _billerInfo) {
        this._billerInfo = _billerInfo;
    }

    /**
     * Getter for billerInfo
     * @return a org.sourceforge.ifx.framework.element.BillerInfo[]
     */
    public org.sourceforge.ifx.framework.element.BillerInfo[] getBillerInfo() {
        return _billerInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillerStatus[] _billerStatus;

    /** 
     * Setter for billerStatus
     * @param billerStatus the org.sourceforge.ifx.framework.element.BillerStatus[] to set
     */
    public void setBillerStatus(org.sourceforge.ifx.framework.element.BillerStatus[] _billerStatus) {
        this._billerStatus = _billerStatus;
    }

    /**
     * Getter for billerStatus
     * @return a org.sourceforge.ifx.framework.element.BillerStatus[]
     */
    public org.sourceforge.ifx.framework.element.BillerStatus[] getBillerStatus() {
        return _billerStatus;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BSPReferTo[] _bSPReferTo;

    /** 
     * Setter for bSPReferTo
     * @param bSPReferTo the org.sourceforge.ifx.framework.element.BSPReferTo[] to set
     */
    public void setBSPReferTo(org.sourceforge.ifx.framework.element.BSPReferTo[] _bSPReferTo) {
        this._bSPReferTo = _bSPReferTo;
    }

    /**
     * Getter for bSPReferTo
     * @return a org.sourceforge.ifx.framework.element.BSPReferTo[]
     */
    public org.sourceforge.ifx.framework.element.BSPReferTo[] getBSPReferTo() {
        return _bSPReferTo;
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
       * BillerId
       * BillerInfo
       * BillerStatus
       * BSPReferTo
       */
    public final String[] ELEMENTS = {
              "BillerId"
                 ,"BillerInfo"
                 ,"BillerStatus"
                 ,"BSPReferTo"
          };
}
