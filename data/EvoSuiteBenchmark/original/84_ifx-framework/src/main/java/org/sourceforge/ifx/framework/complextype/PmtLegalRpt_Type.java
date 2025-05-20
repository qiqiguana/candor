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
public class PmtLegalRpt_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PmtLegalRpt_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.OrgInfo _orgInfo;

    /** 
     * Setter for orgInfo
     * @param orgInfo the org.sourceforge.ifx.framework.element.OrgInfo to set
     */
    public void setOrgInfo(org.sourceforge.ifx.framework.element.OrgInfo _orgInfo) {
        this._orgInfo = _orgInfo;
    }

    /**
     * Getter for orgInfo
     * @return a org.sourceforge.ifx.framework.element.OrgInfo
     */
    public org.sourceforge.ifx.framework.element.OrgInfo getOrgInfo() {
        return _orgInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.OrgId _orgId;

    /** 
     * Setter for orgId
     * @param orgId the org.sourceforge.ifx.framework.element.OrgId to set
     */
    public void setOrgId(org.sourceforge.ifx.framework.element.OrgId _orgId) {
        this._orgId = _orgId;
    }

    /**
     * Getter for orgId
     * @return a org.sourceforge.ifx.framework.element.OrgId
     */
    public org.sourceforge.ifx.framework.element.OrgId getOrgId() {
        return _orgId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PayeeCountry _payeeCountry;

    /** 
     * Setter for payeeCountry
     * @param payeeCountry the org.sourceforge.ifx.framework.element.PayeeCountry to set
     */
    public void setPayeeCountry(org.sourceforge.ifx.framework.element.PayeeCountry _payeeCountry) {
        this._payeeCountry = _payeeCountry;
    }

    /**
     * Getter for payeeCountry
     * @return a org.sourceforge.ifx.framework.element.PayeeCountry
     */
    public org.sourceforge.ifx.framework.element.PayeeCountry getPayeeCountry() {
        return _payeeCountry;
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
    private org.sourceforge.ifx.framework.element.PrcDt _prcDt;

    /** 
     * Setter for prcDt
     * @param prcDt the org.sourceforge.ifx.framework.element.PrcDt to set
     */
    public void setPrcDt(org.sourceforge.ifx.framework.element.PrcDt _prcDt) {
        this._prcDt = _prcDt;
    }

    /**
     * Getter for prcDt
     * @return a org.sourceforge.ifx.framework.element.PrcDt
     */
    public org.sourceforge.ifx.framework.element.PrcDt getPrcDt() {
        return _prcDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtLegalRptData[] _pmtLegalRptData;

    /** 
     * Setter for pmtLegalRptData
     * @param pmtLegalRptData the org.sourceforge.ifx.framework.element.PmtLegalRptData[] to set
     */
    public void setPmtLegalRptData(org.sourceforge.ifx.framework.element.PmtLegalRptData[] _pmtLegalRptData) {
        this._pmtLegalRptData = _pmtLegalRptData;
    }

    /**
     * Getter for pmtLegalRptData
     * @return a org.sourceforge.ifx.framework.element.PmtLegalRptData[]
     */
    public org.sourceforge.ifx.framework.element.PmtLegalRptData[] getPmtLegalRptData() {
        return _pmtLegalRptData;
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
       * OrgInfo
       * OrgId
       * PayeeCountry
       * CurAmt
       * PrcDt
       * PmtLegalRptData
       */
    public final String[] ELEMENTS = {
              "OrgInfo"
                 ,"OrgId"
                 ,"PayeeCountry"
                 ,"CurAmt"
                 ,"PrcDt"
                 ,"PmtLegalRptData"
          };
}
