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
public class CreditAuthRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CreditAuthRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAuthId _creditAuthId;

    /** 
     * Setter for creditAuthId
     * @param creditAuthId the org.sourceforge.ifx.framework.element.CreditAuthId to set
     */
    public void setCreditAuthId(org.sourceforge.ifx.framework.element.CreditAuthId _creditAuthId) {
        this._creditAuthId = _creditAuthId;
    }

    /**
     * Getter for creditAuthId
     * @return a org.sourceforge.ifx.framework.element.CreditAuthId
     */
    public org.sourceforge.ifx.framework.element.CreditAuthId getCreditAuthId() {
        return _creditAuthId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAuthInfo _creditAuthInfo;

    /** 
     * Setter for creditAuthInfo
     * @param creditAuthInfo the org.sourceforge.ifx.framework.element.CreditAuthInfo to set
     */
    public void setCreditAuthInfo(org.sourceforge.ifx.framework.element.CreditAuthInfo _creditAuthInfo) {
        this._creditAuthInfo = _creditAuthInfo;
    }

    /**
     * Getter for creditAuthInfo
     * @return a org.sourceforge.ifx.framework.element.CreditAuthInfo
     */
    public org.sourceforge.ifx.framework.element.CreditAuthInfo getCreditAuthInfo() {
        return _creditAuthInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAuthStatus _creditAuthStatus;

    /** 
     * Setter for creditAuthStatus
     * @param creditAuthStatus the org.sourceforge.ifx.framework.element.CreditAuthStatus to set
     */
    public void setCreditAuthStatus(org.sourceforge.ifx.framework.element.CreditAuthStatus _creditAuthStatus) {
        this._creditAuthStatus = _creditAuthStatus;
    }

    /**
     * Getter for creditAuthStatus
     * @return a org.sourceforge.ifx.framework.element.CreditAuthStatus
     */
    public org.sourceforge.ifx.framework.element.CreditAuthStatus getCreditAuthStatus() {
        return _creditAuthStatus;
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
       * CreditAuthId
       * CreditAuthInfo
       * CreditAuthStatus
       */
    public final String[] ELEMENTS = {
              "CreditAuthId"
                 ,"CreditAuthInfo"
                 ,"CreditAuthStatus"
          };
}
