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
public class CustRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CustRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustId _custId;

    /** 
     * Setter for custId
     * @param custId the org.sourceforge.ifx.framework.element.CustId to set
     */
    public void setCustId(org.sourceforge.ifx.framework.element.CustId _custId) {
        this._custId = _custId;
    }

    /**
     * Getter for custId
     * @return a org.sourceforge.ifx.framework.element.CustId
     */
    public org.sourceforge.ifx.framework.element.CustId getCustId() {
        return _custId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustInfo _custInfo;

    /** 
     * Setter for custInfo
     * @param custInfo the org.sourceforge.ifx.framework.element.CustInfo to set
     */
    public void setCustInfo(org.sourceforge.ifx.framework.element.CustInfo _custInfo) {
        this._custInfo = _custInfo;
    }

    /**
     * Getter for custInfo
     * @return a org.sourceforge.ifx.framework.element.CustInfo
     */
    public org.sourceforge.ifx.framework.element.CustInfo getCustInfo() {
        return _custInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustStatus _custStatus;

    /** 
     * Setter for custStatus
     * @param custStatus the org.sourceforge.ifx.framework.element.CustStatus to set
     */
    public void setCustStatus(org.sourceforge.ifx.framework.element.CustStatus _custStatus) {
        this._custStatus = _custStatus;
    }

    /**
     * Getter for custStatus
     * @return a org.sourceforge.ifx.framework.element.CustStatus
     */
    public org.sourceforge.ifx.framework.element.CustStatus getCustStatus() {
        return _custStatus;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.UpDt _upDt;

    /** 
     * Setter for upDt
     * @param upDt the org.sourceforge.ifx.framework.element.UpDt to set
     */
    public void setUpDt(org.sourceforge.ifx.framework.element.UpDt _upDt) {
        this._upDt = _upDt;
    }

    /**
     * Getter for upDt
     * @return a org.sourceforge.ifx.framework.element.UpDt
     */
    public org.sourceforge.ifx.framework.element.UpDt getUpDt() {
        return _upDt;
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
       * CustId
       * CustInfo
       * CustStatus
       * UpDt
       */
    public final String[] ELEMENTS = {
              "CustId"
                 ,"CustInfo"
                 ,"CustStatus"
                 ,"UpDt"
          };
}
