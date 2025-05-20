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
public class PmtEnclRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PmtEnclRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtEnclId _pmtEnclId;

    /** 
     * Setter for pmtEnclId
     * @param pmtEnclId the org.sourceforge.ifx.framework.element.PmtEnclId to set
     */
    public void setPmtEnclId(org.sourceforge.ifx.framework.element.PmtEnclId _pmtEnclId) {
        this._pmtEnclId = _pmtEnclId;
    }

    /**
     * Getter for pmtEnclId
     * @return a org.sourceforge.ifx.framework.element.PmtEnclId
     */
    public org.sourceforge.ifx.framework.element.PmtEnclId getPmtEnclId() {
        return _pmtEnclId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtEnclInfo _pmtEnclInfo;

    /** 
     * Setter for pmtEnclInfo
     * @param pmtEnclInfo the org.sourceforge.ifx.framework.element.PmtEnclInfo to set
     */
    public void setPmtEnclInfo(org.sourceforge.ifx.framework.element.PmtEnclInfo _pmtEnclInfo) {
        this._pmtEnclInfo = _pmtEnclInfo;
    }

    /**
     * Getter for pmtEnclInfo
     * @return a org.sourceforge.ifx.framework.element.PmtEnclInfo
     */
    public org.sourceforge.ifx.framework.element.PmtEnclInfo getPmtEnclInfo() {
        return _pmtEnclInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtEnclStatus _pmtEnclStatus;

    /** 
     * Setter for pmtEnclStatus
     * @param pmtEnclStatus the org.sourceforge.ifx.framework.element.PmtEnclStatus to set
     */
    public void setPmtEnclStatus(org.sourceforge.ifx.framework.element.PmtEnclStatus _pmtEnclStatus) {
        this._pmtEnclStatus = _pmtEnclStatus;
    }

    /**
     * Getter for pmtEnclStatus
     * @return a org.sourceforge.ifx.framework.element.PmtEnclStatus
     */
    public org.sourceforge.ifx.framework.element.PmtEnclStatus getPmtEnclStatus() {
        return _pmtEnclStatus;
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
       * PmtEnclId
       * PmtEnclInfo
       * PmtEnclStatus
       */
    public final String[] ELEMENTS = {
              "PmtEnclId"
                 ,"PmtEnclInfo"
                 ,"PmtEnclStatus"
          };
}
