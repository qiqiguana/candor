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
public class StopChkRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public StopChkRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StopChkInfo _stopChkInfo;

    /** 
     * Setter for stopChkInfo
     * @param stopChkInfo the org.sourceforge.ifx.framework.element.StopChkInfo to set
     */
    public void setStopChkInfo(org.sourceforge.ifx.framework.element.StopChkInfo _stopChkInfo) {
        this._stopChkInfo = _stopChkInfo;
    }

    /**
     * Getter for stopChkInfo
     * @return a org.sourceforge.ifx.framework.element.StopChkInfo
     */
    public org.sourceforge.ifx.framework.element.StopChkInfo getStopChkInfo() {
        return _stopChkInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PostedDt _postedDt;

    /** 
     * Setter for postedDt
     * @param postedDt the org.sourceforge.ifx.framework.element.PostedDt to set
     */
    public void setPostedDt(org.sourceforge.ifx.framework.element.PostedDt _postedDt) {
        this._postedDt = _postedDt;
    }

    /**
     * Getter for postedDt
     * @return a org.sourceforge.ifx.framework.element.PostedDt
     */
    public org.sourceforge.ifx.framework.element.PostedDt getPostedDt() {
        return _postedDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StopChkStatusCode _stopChkStatusCode;

    /** 
     * Setter for stopChkStatusCode
     * @param stopChkStatusCode the org.sourceforge.ifx.framework.element.StopChkStatusCode to set
     */
    public void setStopChkStatusCode(org.sourceforge.ifx.framework.element.StopChkStatusCode _stopChkStatusCode) {
        this._stopChkStatusCode = _stopChkStatusCode;
    }

    /**
     * Getter for stopChkStatusCode
     * @return a org.sourceforge.ifx.framework.element.StopChkStatusCode
     */
    public org.sourceforge.ifx.framework.element.StopChkStatusCode getStopChkStatusCode() {
        return _stopChkStatusCode;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StatusDesc _statusDesc;

    /** 
     * Setter for statusDesc
     * @param statusDesc the org.sourceforge.ifx.framework.element.StatusDesc to set
     */
    public void setStatusDesc(org.sourceforge.ifx.framework.element.StatusDesc _statusDesc) {
        this._statusDesc = _statusDesc;
    }

    /**
     * Getter for statusDesc
     * @return a org.sourceforge.ifx.framework.element.StatusDesc
     */
    public org.sourceforge.ifx.framework.element.StatusDesc getStatusDesc() {
        return _statusDesc;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Status _status;

    /** 
     * Setter for status
     * @param status the org.sourceforge.ifx.framework.element.Status to set
     */
    public void setStatus(org.sourceforge.ifx.framework.element.Status _status) {
        this._status = _status;
    }

    /**
     * Getter for status
     * @return a org.sourceforge.ifx.framework.element.Status
     */
    public org.sourceforge.ifx.framework.element.Status getStatus() {
        return _status;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ApprovalId _approvalId;

    /** 
     * Setter for approvalId
     * @param approvalId the org.sourceforge.ifx.framework.element.ApprovalId to set
     */
    public void setApprovalId(org.sourceforge.ifx.framework.element.ApprovalId _approvalId) {
        this._approvalId = _approvalId;
    }

    /**
     * Getter for approvalId
     * @return a org.sourceforge.ifx.framework.element.ApprovalId
     */
    public org.sourceforge.ifx.framework.element.ApprovalId getApprovalId() {
        return _approvalId;
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
       * StopChkInfo
       * PostedDt
       * StopChkStatusCode
       * StatusDesc
       * Status
       * ApprovalId
       */
    public final String[] ELEMENTS = {
              "StopChkInfo"
                 ,"PostedDt"
                 ,"StopChkStatusCode"
                 ,"StatusDesc"
                 ,"Status"
                 ,"ApprovalId"
          };
}
