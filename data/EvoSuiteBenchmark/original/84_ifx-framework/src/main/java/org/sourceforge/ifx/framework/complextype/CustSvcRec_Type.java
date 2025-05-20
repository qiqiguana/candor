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
public class CustSvcRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CustSvcRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustSvcId _custSvcId;

    /** 
     * Setter for custSvcId
     * @param custSvcId the org.sourceforge.ifx.framework.element.CustSvcId to set
     */
    public void setCustSvcId(org.sourceforge.ifx.framework.element.CustSvcId _custSvcId) {
        this._custSvcId = _custSvcId;
    }

    /**
     * Getter for custSvcId
     * @return a org.sourceforge.ifx.framework.element.CustSvcId
     */
    public org.sourceforge.ifx.framework.element.CustSvcId getCustSvcId() {
        return _custSvcId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustSvcInfo _custSvcInfo;

    /** 
     * Setter for custSvcInfo
     * @param custSvcInfo the org.sourceforge.ifx.framework.element.CustSvcInfo to set
     */
    public void setCustSvcInfo(org.sourceforge.ifx.framework.element.CustSvcInfo _custSvcInfo) {
        this._custSvcInfo = _custSvcInfo;
    }

    /**
     * Getter for custSvcInfo
     * @return a org.sourceforge.ifx.framework.element.CustSvcInfo
     */
    public org.sourceforge.ifx.framework.element.CustSvcInfo getCustSvcInfo() {
        return _custSvcInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustSvcStatus _custSvcStatus;

    /** 
     * Setter for custSvcStatus
     * @param custSvcStatus the org.sourceforge.ifx.framework.element.CustSvcStatus to set
     */
    public void setCustSvcStatus(org.sourceforge.ifx.framework.element.CustSvcStatus _custSvcStatus) {
        this._custSvcStatus = _custSvcStatus;
    }

    /**
     * Getter for custSvcStatus
     * @return a org.sourceforge.ifx.framework.element.CustSvcStatus
     */
    public org.sourceforge.ifx.framework.element.CustSvcStatus getCustSvcStatus() {
        return _custSvcStatus;
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
       * CustSvcId
       * CustSvcInfo
       * CustSvcStatus
       */
    public final String[] ELEMENTS = {
              "CustSvcId"
                 ,"CustSvcInfo"
                 ,"CustSvcStatus"
          };
}
