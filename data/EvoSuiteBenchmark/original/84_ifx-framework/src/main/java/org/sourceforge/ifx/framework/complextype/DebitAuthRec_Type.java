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
public class DebitAuthRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DebitAuthRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitAuthId _debitAuthId;

    /** 
     * Setter for debitAuthId
     * @param debitAuthId the org.sourceforge.ifx.framework.element.DebitAuthId to set
     */
    public void setDebitAuthId(org.sourceforge.ifx.framework.element.DebitAuthId _debitAuthId) {
        this._debitAuthId = _debitAuthId;
    }

    /**
     * Getter for debitAuthId
     * @return a org.sourceforge.ifx.framework.element.DebitAuthId
     */
    public org.sourceforge.ifx.framework.element.DebitAuthId getDebitAuthId() {
        return _debitAuthId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitAuthInfo _debitAuthInfo;

    /** 
     * Setter for debitAuthInfo
     * @param debitAuthInfo the org.sourceforge.ifx.framework.element.DebitAuthInfo to set
     */
    public void setDebitAuthInfo(org.sourceforge.ifx.framework.element.DebitAuthInfo _debitAuthInfo) {
        this._debitAuthInfo = _debitAuthInfo;
    }

    /**
     * Getter for debitAuthInfo
     * @return a org.sourceforge.ifx.framework.element.DebitAuthInfo
     */
    public org.sourceforge.ifx.framework.element.DebitAuthInfo getDebitAuthInfo() {
        return _debitAuthInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitAuthStatus _debitAuthStatus;

    /** 
     * Setter for debitAuthStatus
     * @param debitAuthStatus the org.sourceforge.ifx.framework.element.DebitAuthStatus to set
     */
    public void setDebitAuthStatus(org.sourceforge.ifx.framework.element.DebitAuthStatus _debitAuthStatus) {
        this._debitAuthStatus = _debitAuthStatus;
    }

    /**
     * Getter for debitAuthStatus
     * @return a org.sourceforge.ifx.framework.element.DebitAuthStatus
     */
    public org.sourceforge.ifx.framework.element.DebitAuthStatus getDebitAuthStatus() {
        return _debitAuthStatus;
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
       * DebitAuthId
       * DebitAuthInfo
       * DebitAuthStatus
       */
    public final String[] ELEMENTS = {
              "DebitAuthId"
                 ,"DebitAuthInfo"
                 ,"DebitAuthStatus"
          };
}
