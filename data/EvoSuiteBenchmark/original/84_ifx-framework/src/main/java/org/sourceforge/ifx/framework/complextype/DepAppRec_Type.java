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
public class DepAppRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DepAppRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAppId _depAppId;

    /** 
     * Setter for depAppId
     * @param depAppId the org.sourceforge.ifx.framework.element.DepAppId to set
     */
    public void setDepAppId(org.sourceforge.ifx.framework.element.DepAppId _depAppId) {
        this._depAppId = _depAppId;
    }

    /**
     * Getter for depAppId
     * @return a org.sourceforge.ifx.framework.element.DepAppId
     */
    public org.sourceforge.ifx.framework.element.DepAppId getDepAppId() {
        return _depAppId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAppInfo _depAppInfo;

    /** 
     * Setter for depAppInfo
     * @param depAppInfo the org.sourceforge.ifx.framework.element.DepAppInfo to set
     */
    public void setDepAppInfo(org.sourceforge.ifx.framework.element.DepAppInfo _depAppInfo) {
        this._depAppInfo = _depAppInfo;
    }

    /**
     * Getter for depAppInfo
     * @return a org.sourceforge.ifx.framework.element.DepAppInfo
     */
    public org.sourceforge.ifx.framework.element.DepAppInfo getDepAppInfo() {
        return _depAppInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAppStatus _depAppStatus;

    /** 
     * Setter for depAppStatus
     * @param depAppStatus the org.sourceforge.ifx.framework.element.DepAppStatus to set
     */
    public void setDepAppStatus(org.sourceforge.ifx.framework.element.DepAppStatus _depAppStatus) {
        this._depAppStatus = _depAppStatus;
    }

    /**
     * Getter for depAppStatus
     * @return a org.sourceforge.ifx.framework.element.DepAppStatus
     */
    public org.sourceforge.ifx.framework.element.DepAppStatus getDepAppStatus() {
        return _depAppStatus;
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
       * DepAppId
       * DepAppInfo
       * DepAppStatus
       */
    public final String[] ELEMENTS = {
              "DepAppId"
                 ,"DepAppInfo"
                 ,"DepAppStatus"
          };
}
