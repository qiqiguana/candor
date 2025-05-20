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
public class PmtLegalRptData_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PmtLegalRptData_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.LegalRptCode _legalRptCode;

    /** 
     * Setter for legalRptCode
     * @param legalRptCode the org.sourceforge.ifx.framework.element.LegalRptCode to set
     */
    public void setLegalRptCode(org.sourceforge.ifx.framework.element.LegalRptCode _legalRptCode) {
        this._legalRptCode = _legalRptCode;
    }

    /**
     * Getter for legalRptCode
     * @return a org.sourceforge.ifx.framework.element.LegalRptCode
     */
    public org.sourceforge.ifx.framework.element.LegalRptCode getLegalRptCode() {
        return _legalRptCode;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SupplRptCode _supplRptCode;

    /** 
     * Setter for supplRptCode
     * @param supplRptCode the org.sourceforge.ifx.framework.element.SupplRptCode to set
     */
    public void setSupplRptCode(org.sourceforge.ifx.framework.element.SupplRptCode _supplRptCode) {
        this._supplRptCode = _supplRptCode;
    }

    /**
     * Getter for supplRptCode
     * @return a org.sourceforge.ifx.framework.element.SupplRptCode
     */
    public org.sourceforge.ifx.framework.element.SupplRptCode getSupplRptCode() {
        return _supplRptCode;
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
    private org.sourceforge.ifx.framework.element.Memo _memo;

    /** 
     * Setter for memo
     * @param memo the org.sourceforge.ifx.framework.element.Memo to set
     */
    public void setMemo(org.sourceforge.ifx.framework.element.Memo _memo) {
        this._memo = _memo;
    }

    /**
     * Getter for memo
     * @return a org.sourceforge.ifx.framework.element.Memo
     */
    public org.sourceforge.ifx.framework.element.Memo getMemo() {
        return _memo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SupplyingCountry _supplyingCountry;

    /** 
     * Setter for supplyingCountry
     * @param supplyingCountry the org.sourceforge.ifx.framework.element.SupplyingCountry to set
     */
    public void setSupplyingCountry(org.sourceforge.ifx.framework.element.SupplyingCountry _supplyingCountry) {
        this._supplyingCountry = _supplyingCountry;
    }

    /**
     * Getter for supplyingCountry
     * @return a org.sourceforge.ifx.framework.element.SupplyingCountry
     */
    public org.sourceforge.ifx.framework.element.SupplyingCountry getSupplyingCountry() {
        return _supplyingCountry;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CurAmt _curAmt;

    /** 
     * Setter for curAmt
     * @param curAmt the org.sourceforge.ifx.framework.element.CurAmt to set
     */
    public void setCurAmt(org.sourceforge.ifx.framework.element.CurAmt _curAmt) {
        this._curAmt = _curAmt;
    }

    /**
     * Getter for curAmt
     * @return a org.sourceforge.ifx.framework.element.CurAmt
     */
    public org.sourceforge.ifx.framework.element.CurAmt getCurAmt() {
        return _curAmt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ImportDt _importDt;

    /** 
     * Setter for importDt
     * @param importDt the org.sourceforge.ifx.framework.element.ImportDt to set
     */
    public void setImportDt(org.sourceforge.ifx.framework.element.ImportDt _importDt) {
        this._importDt = _importDt;
    }

    /**
     * Getter for importDt
     * @return a org.sourceforge.ifx.framework.element.ImportDt
     */
    public org.sourceforge.ifx.framework.element.ImportDt getImportDt() {
        return _importDt;
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
       * LegalRptCode
       * SupplRptCode
       * Desc
       * Memo
       * SupplyingCountry
       * CurAmt
       * ImportDt
       */
    public final String[] ELEMENTS = {
              "LegalRptCode"
                 ,"SupplRptCode"
                 ,"Desc"
                 ,"Memo"
                 ,"SupplyingCountry"
                 ,"CurAmt"
                 ,"ImportDt"
          };
}
