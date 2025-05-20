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
public class AdditionalStatus_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public AdditionalStatus_Type() {
        super();
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


    /**
     * Returns true if objects are equal, false otherwise.
     * @param obj the object to compare with.
     * @return true if equal, false if not.
     */
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /** Element ordering - 
       * StatusCode
       * ServerStatusCode
       * Severity
       * StatusDesc
       */
    public final String[] ELEMENTS = {
              "StatusCode"
                 ,"ServerStatusCode"
                 ,"Severity"
                 ,"StatusDesc"
          };
}
