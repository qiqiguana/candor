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
public class PmtInst_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PmtInst_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtInstType _pmtInstType;

    /** 
     * Setter for pmtInstType
     * @param pmtInstType the org.sourceforge.ifx.framework.element.PmtInstType to set
     */
    public void setPmtInstType(org.sourceforge.ifx.framework.element.PmtInstType _pmtInstType) {
        this._pmtInstType = _pmtInstType;
    }

    /**
     * Getter for pmtInstType
     * @return a org.sourceforge.ifx.framework.element.PmtInstType
     */
    public org.sourceforge.ifx.framework.element.PmtInstType getPmtInstType() {
        return _pmtInstType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Brand _brand;

    /** 
     * Setter for brand
     * @param brand the org.sourceforge.ifx.framework.element.Brand to set
     */
    public void setBrand(org.sourceforge.ifx.framework.element.Brand _brand) {
        this._brand = _brand;
    }

    /**
     * Getter for brand
     * @return a org.sourceforge.ifx.framework.element.Brand
     */
    public org.sourceforge.ifx.framework.element.Brand getBrand() {
        return _brand;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SettlementInfo[] _settlementInfo;

    /** 
     * Setter for settlementInfo
     * @param settlementInfo the org.sourceforge.ifx.framework.element.SettlementInfo[] to set
     */
    public void setSettlementInfo(org.sourceforge.ifx.framework.element.SettlementInfo[] _settlementInfo) {
        this._settlementInfo = _settlementInfo;
    }

    /**
     * Getter for settlementInfo
     * @return a org.sourceforge.ifx.framework.element.SettlementInfo[]
     */
    public org.sourceforge.ifx.framework.element.SettlementInfo[] getSettlementInfo() {
        return _settlementInfo;
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
       * PmtInstType
       * Brand
       * SettlementInfo
       */
    public final String[] ELEMENTS = {
              "PmtInstType"
                 ,"Brand"
                 ,"SettlementInfo"
          };
}
