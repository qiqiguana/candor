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
public class XferStatus_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public XferStatus_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.XferStatusCode _xferStatusCode;

    /** 
     * Setter for xferStatusCode
     * @param xferStatusCode the org.sourceforge.ifx.framework.element.XferStatusCode to set
     */
    public void setXferStatusCode(org.sourceforge.ifx.framework.element.XferStatusCode _xferStatusCode) {
        this._xferStatusCode = _xferStatusCode;
    }

    /**
     * Getter for xferStatusCode
     * @return a org.sourceforge.ifx.framework.element.XferStatusCode
     */
    public org.sourceforge.ifx.framework.element.XferStatusCode getXferStatusCode() {
        return _xferStatusCode;
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
    private org.sourceforge.ifx.framework.element.EffDt _effDt;

    /** 
     * Setter for effDt
     * @param effDt the org.sourceforge.ifx.framework.element.EffDt to set
     */
    public void setEffDt(org.sourceforge.ifx.framework.element.EffDt _effDt) {
        this._effDt = _effDt;
    }

    /**
     * Getter for effDt
     * @return a org.sourceforge.ifx.framework.element.EffDt
     */
    public org.sourceforge.ifx.framework.element.EffDt getEffDt() {
        return _effDt;
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

    // property declaration 
    private org.sourceforge.ifx.framework.element.StatusModBy _statusModBy;

    /** 
     * Setter for statusModBy
     * @param statusModBy the org.sourceforge.ifx.framework.element.StatusModBy to set
     */
    public void setStatusModBy(org.sourceforge.ifx.framework.element.StatusModBy _statusModBy) {
        this._statusModBy = _statusModBy;
    }

    /**
     * Getter for statusModBy
     * @return a org.sourceforge.ifx.framework.element.StatusModBy
     */
    public org.sourceforge.ifx.framework.element.StatusModBy getStatusModBy() {
        return _statusModBy;
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


    /**
     * Returns true if objects are equal, false otherwise.
     * @param obj the object to compare with.
     * @return true if equal, false if not.
     */
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /** Element ordering - 
       * XferStatusCode
       * StatusDesc
       * EffDt
       * ApprovalId
       * StatusModBy
       * Status
       */
    public final String[] ELEMENTS = {
              "XferStatusCode"
                 ,"StatusDesc"
                 ,"EffDt"
                 ,"ApprovalId"
                 ,"StatusModBy"
                 ,"Status"
          };
}
