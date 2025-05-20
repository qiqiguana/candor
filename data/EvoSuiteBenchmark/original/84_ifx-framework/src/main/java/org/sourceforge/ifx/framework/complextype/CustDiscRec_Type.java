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
public class CustDiscRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CustDiscRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustId _custId;

    /** 
     * Setter for custId
     * @param custId the org.sourceforge.ifx.framework.element.CustId to set
     */
    public void setCustId(org.sourceforge.ifx.framework.element.CustId _custId) {
        this._custId = _custId;
    }

    /**
     * Getter for custId
     * @return a org.sourceforge.ifx.framework.element.CustId
     */
    public org.sourceforge.ifx.framework.element.CustId getCustId() {
        return _custId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DiscId _discId;

    /** 
     * Setter for discId
     * @param discId the org.sourceforge.ifx.framework.element.DiscId to set
     */
    public void setDiscId(org.sourceforge.ifx.framework.element.DiscId _discId) {
        this._discId = _discId;
    }

    /**
     * Getter for discId
     * @return a org.sourceforge.ifx.framework.element.DiscId
     */
    public org.sourceforge.ifx.framework.element.DiscId getDiscId() {
        return _discId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DiscInfo _discInfo;

    /** 
     * Setter for discInfo
     * @param discInfo the org.sourceforge.ifx.framework.element.DiscInfo to set
     */
    public void setDiscInfo(org.sourceforge.ifx.framework.element.DiscInfo _discInfo) {
        this._discInfo = _discInfo;
    }

    /**
     * Getter for discInfo
     * @return a org.sourceforge.ifx.framework.element.DiscInfo
     */
    public org.sourceforge.ifx.framework.element.DiscInfo getDiscInfo() {
        return _discInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustDiscStatus _custDiscStatus;

    /** 
     * Setter for custDiscStatus
     * @param custDiscStatus the org.sourceforge.ifx.framework.element.CustDiscStatus to set
     */
    public void setCustDiscStatus(org.sourceforge.ifx.framework.element.CustDiscStatus _custDiscStatus) {
        this._custDiscStatus = _custDiscStatus;
    }

    /**
     * Getter for custDiscStatus
     * @return a org.sourceforge.ifx.framework.element.CustDiscStatus
     */
    public org.sourceforge.ifx.framework.element.CustDiscStatus getCustDiscStatus() {
        return _custDiscStatus;
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
       * CustId
       * DiscId
       * DiscInfo
       * CustDiscStatus
       */
    public final String[] ELEMENTS = {
              "CustId"
                 ,"DiscId"
                 ,"DiscInfo"
                 ,"CustDiscStatus"
          };
}
