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
public class Status_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public Status_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.basetypes.IFXString _id;

    /** 
     * Setter for id
     * @param id the org.sourceforge.ifx.basetypes.IFXString to set
     */
    public void setId(org.sourceforge.ifx.basetypes.IFXString _id) {
        this._id = _id;
    }

    /**
     * Getter for id
     * @return a org.sourceforge.ifx.basetypes.IFXString
     */
    public org.sourceforge.ifx.basetypes.IFXString getId() {
        return _id;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StatusCode _statusCode;

    /** 
     * Setter for statusCode
     * @param statusCode the org.sourceforge.ifx.framework.element.StatusCode to set
     */
    public void setStatusCode(org.sourceforge.ifx.framework.element.StatusCode _statusCode) {
        this._statusCode = _statusCode;
    }

    /**
     * Getter for statusCode
     * @return a org.sourceforge.ifx.framework.element.StatusCode
     */
    public org.sourceforge.ifx.framework.element.StatusCode getStatusCode() {
        return _statusCode;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ServerStatusCode _serverStatusCode;

    /** 
     * Setter for serverStatusCode
     * @param serverStatusCode the org.sourceforge.ifx.framework.element.ServerStatusCode to set
     */
    public void setServerStatusCode(org.sourceforge.ifx.framework.element.ServerStatusCode _serverStatusCode) {
        this._serverStatusCode = _serverStatusCode;
    }

    /**
     * Getter for serverStatusCode
     * @return a org.sourceforge.ifx.framework.element.ServerStatusCode
     */
    public org.sourceforge.ifx.framework.element.ServerStatusCode getServerStatusCode() {
        return _serverStatusCode;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Severity _severity;

    /** 
     * Setter for severity
     * @param severity the org.sourceforge.ifx.framework.element.Severity to set
     */
    public void setSeverity(org.sourceforge.ifx.framework.element.Severity _severity) {
        this._severity = _severity;
    }

    /**
     * Getter for severity
     * @return a org.sourceforge.ifx.framework.element.Severity
     */
    public org.sourceforge.ifx.framework.element.Severity getSeverity() {
        return _severity;
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
    private org.sourceforge.ifx.framework.element.AdditionalStatus[] _additionalStatus;

    /** 
     * Setter for additionalStatus
     * @param additionalStatus the org.sourceforge.ifx.framework.element.AdditionalStatus[] to set
     */
    public void setAdditionalStatus(org.sourceforge.ifx.framework.element.AdditionalStatus[] _additionalStatus) {
        this._additionalStatus = _additionalStatus;
    }

    /**
     * Getter for additionalStatus
     * @return a org.sourceforge.ifx.framework.element.AdditionalStatus[]
     */
    public org.sourceforge.ifx.framework.element.AdditionalStatus[] getAdditionalStatus() {
        return _additionalStatus;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AsyncRsInfo[] _asyncRsInfo;

    /** 
     * Setter for asyncRsInfo
     * @param asyncRsInfo the org.sourceforge.ifx.framework.element.AsyncRsInfo[] to set
     */
    public void setAsyncRsInfo(org.sourceforge.ifx.framework.element.AsyncRsInfo[] _asyncRsInfo) {
        this._asyncRsInfo = _asyncRsInfo;
    }

    /**
     * Getter for asyncRsInfo
     * @return a org.sourceforge.ifx.framework.element.AsyncRsInfo[]
     */
    public org.sourceforge.ifx.framework.element.AsyncRsInfo[] getAsyncRsInfo() {
        return _asyncRsInfo;
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
       * Id
       * StatusCode
       * ServerStatusCode
       * Severity
       * StatusDesc
       * AdditionalStatus
       * AsyncRsInfo
       */
    public final String[] ELEMENTS = {
              "Id"
                 ,"StatusCode"
                 ,"ServerStatusCode"
                 ,"Severity"
                 ,"StatusDesc"
                 ,"AdditionalStatus"
                 ,"AsyncRsInfo"
          };
}
