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
public class EMVRsData_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public EMVRsData_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AuthRsCode _authRsCode;

    /** 
     * Setter for authRsCode
     * @param authRsCode the org.sourceforge.ifx.framework.element.AuthRsCode to set
     */
    public void setAuthRsCode(org.sourceforge.ifx.framework.element.AuthRsCode _authRsCode) {
        this._authRsCode = _authRsCode;
    }

    /**
     * Getter for authRsCode
     * @return a org.sourceforge.ifx.framework.element.AuthRsCode
     */
    public org.sourceforge.ifx.framework.element.AuthRsCode getAuthRsCode() {
        return _authRsCode;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AuthCode _authCode;

    /** 
     * Setter for authCode
     * @param authCode the org.sourceforge.ifx.framework.element.AuthCode to set
     */
    public void setAuthCode(org.sourceforge.ifx.framework.element.AuthCode _authCode) {
        this._authCode = _authCode;
    }

    /**
     * Getter for authCode
     * @return a org.sourceforge.ifx.framework.element.AuthCode
     */
    public org.sourceforge.ifx.framework.element.AuthCode getAuthCode() {
        return _authCode;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.IssAuthData _issAuthData;

    /** 
     * Setter for issAuthData
     * @param issAuthData the org.sourceforge.ifx.framework.element.IssAuthData to set
     */
    public void setIssAuthData(org.sourceforge.ifx.framework.element.IssAuthData _issAuthData) {
        this._issAuthData = _issAuthData;
    }

    /**
     * Getter for issAuthData
     * @return a org.sourceforge.ifx.framework.element.IssAuthData
     */
    public org.sourceforge.ifx.framework.element.IssAuthData getIssAuthData() {
        return _issAuthData;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.IssScriptData _issScriptData;

    /** 
     * Setter for issScriptData
     * @param issScriptData the org.sourceforge.ifx.framework.element.IssScriptData to set
     */
    public void setIssScriptData(org.sourceforge.ifx.framework.element.IssScriptData _issScriptData) {
        this._issScriptData = _issScriptData;
    }

    /**
     * Getter for issScriptData
     * @return a org.sourceforge.ifx.framework.element.IssScriptData
     */
    public org.sourceforge.ifx.framework.element.IssScriptData getIssScriptData() {
        return _issScriptData;
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
       * AuthRsCode
       * AuthCode
       * IssAuthData
       * IssScriptData
       */
    public final String[] ELEMENTS = {
              "AuthRsCode"
                 ,"AuthCode"
                 ,"IssAuthData"
                 ,"IssScriptData"
          };
}
