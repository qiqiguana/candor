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
public class PmtAuthRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PmtAuthRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtAuthId _pmtAuthId;

    /** 
     * Setter for pmtAuthId
     * @param pmtAuthId the org.sourceforge.ifx.framework.element.PmtAuthId to set
     */
    public void setPmtAuthId(org.sourceforge.ifx.framework.element.PmtAuthId _pmtAuthId) {
        this._pmtAuthId = _pmtAuthId;
    }

    /**
     * Getter for pmtAuthId
     * @return a org.sourceforge.ifx.framework.element.PmtAuthId
     */
    public org.sourceforge.ifx.framework.element.PmtAuthId getPmtAuthId() {
        return _pmtAuthId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtAuthInfo _pmtAuthInfo;

    /** 
     * Setter for pmtAuthInfo
     * @param pmtAuthInfo the org.sourceforge.ifx.framework.element.PmtAuthInfo to set
     */
    public void setPmtAuthInfo(org.sourceforge.ifx.framework.element.PmtAuthInfo _pmtAuthInfo) {
        this._pmtAuthInfo = _pmtAuthInfo;
    }

    /**
     * Getter for pmtAuthInfo
     * @return a org.sourceforge.ifx.framework.element.PmtAuthInfo
     */
    public org.sourceforge.ifx.framework.element.PmtAuthInfo getPmtAuthInfo() {
        return _pmtAuthInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtAuthStatus _pmtAuthStatus;

    /** 
     * Setter for pmtAuthStatus
     * @param pmtAuthStatus the org.sourceforge.ifx.framework.element.PmtAuthStatus to set
     */
    public void setPmtAuthStatus(org.sourceforge.ifx.framework.element.PmtAuthStatus _pmtAuthStatus) {
        this._pmtAuthStatus = _pmtAuthStatus;
    }

    /**
     * Getter for pmtAuthStatus
     * @return a org.sourceforge.ifx.framework.element.PmtAuthStatus
     */
    public org.sourceforge.ifx.framework.element.PmtAuthStatus getPmtAuthStatus() {
        return _pmtAuthStatus;
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
       * PmtAuthId
       * PmtAuthInfo
       * PmtAuthStatus
       */
    public final String[] ELEMENTS = {
              "PmtAuthId"
                 ,"PmtAuthInfo"
                 ,"PmtAuthStatus"
          };
}
