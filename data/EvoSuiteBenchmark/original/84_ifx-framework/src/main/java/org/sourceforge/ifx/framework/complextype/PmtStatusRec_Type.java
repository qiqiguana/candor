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
public class PmtStatusRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PmtStatusRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtId _pmtId;

    /** 
     * Setter for pmtId
     * @param pmtId the org.sourceforge.ifx.framework.element.PmtId to set
     */
    public void setPmtId(org.sourceforge.ifx.framework.element.PmtId _pmtId) {
        this._pmtId = _pmtId;
    }

    /**
     * Getter for pmtId
     * @return a org.sourceforge.ifx.framework.element.PmtId
     */
    public org.sourceforge.ifx.framework.element.PmtId getPmtId() {
        return _pmtId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtStatus _pmtStatus;

    /** 
     * Setter for pmtStatus
     * @param pmtStatus the org.sourceforge.ifx.framework.element.PmtStatus to set
     */
    public void setPmtStatus(org.sourceforge.ifx.framework.element.PmtStatus _pmtStatus) {
        this._pmtStatus = _pmtStatus;
    }

    /**
     * Getter for pmtStatus
     * @return a org.sourceforge.ifx.framework.element.PmtStatus
     */
    public org.sourceforge.ifx.framework.element.PmtStatus getPmtStatus() {
        return _pmtStatus;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtRefId _pmtRefId;

    /** 
     * Setter for pmtRefId
     * @param pmtRefId the org.sourceforge.ifx.framework.element.PmtRefId to set
     */
    public void setPmtRefId(org.sourceforge.ifx.framework.element.PmtRefId _pmtRefId) {
        this._pmtRefId = _pmtRefId;
    }

    /**
     * Getter for pmtRefId
     * @return a org.sourceforge.ifx.framework.element.PmtRefId
     */
    public org.sourceforge.ifx.framework.element.PmtRefId getPmtRefId() {
        return _pmtRefId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SvcRqUID _svcRqUID;

    /** 
     * Setter for svcRqUID
     * @param svcRqUID the org.sourceforge.ifx.framework.element.SvcRqUID to set
     */
    public void setSvcRqUID(org.sourceforge.ifx.framework.element.SvcRqUID _svcRqUID) {
        this._svcRqUID = _svcRqUID;
    }

    /**
     * Getter for svcRqUID
     * @return a org.sourceforge.ifx.framework.element.SvcRqUID
     */
    public org.sourceforge.ifx.framework.element.SvcRqUID getSvcRqUID() {
        return _svcRqUID;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.NetworkTrnInfo _networkTrnInfo;

    /** 
     * Setter for networkTrnInfo
     * @param networkTrnInfo the org.sourceforge.ifx.framework.element.NetworkTrnInfo to set
     */
    public void setNetworkTrnInfo(org.sourceforge.ifx.framework.element.NetworkTrnInfo _networkTrnInfo) {
        this._networkTrnInfo = _networkTrnInfo;
    }

    /**
     * Getter for networkTrnInfo
     * @return a org.sourceforge.ifx.framework.element.NetworkTrnInfo
     */
    public org.sourceforge.ifx.framework.element.NetworkTrnInfo getNetworkTrnInfo() {
        return _networkTrnInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CompositeContactInfo _compositeContactInfo;

    /** 
     * Setter for compositeContactInfo
     * @param compositeContactInfo the org.sourceforge.ifx.framework.element.CompositeContactInfo to set
     */
    public void setCompositeContactInfo(org.sourceforge.ifx.framework.element.CompositeContactInfo _compositeContactInfo) {
        this._compositeContactInfo = _compositeContactInfo;
    }

    /**
     * Getter for compositeContactInfo
     * @return a org.sourceforge.ifx.framework.element.CompositeContactInfo
     */
    public org.sourceforge.ifx.framework.element.CompositeContactInfo getCompositeContactInfo() {
        return _compositeContactInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtRemitAck[] _pmtRemitAck;

    /** 
     * Setter for pmtRemitAck
     * @param pmtRemitAck the org.sourceforge.ifx.framework.element.PmtRemitAck[] to set
     */
    public void setPmtRemitAck(org.sourceforge.ifx.framework.element.PmtRemitAck[] _pmtRemitAck) {
        this._pmtRemitAck = _pmtRemitAck;
    }

    /**
     * Getter for pmtRemitAck
     * @return a org.sourceforge.ifx.framework.element.PmtRemitAck[]
     */
    public org.sourceforge.ifx.framework.element.PmtRemitAck[] getPmtRemitAck() {
        return _pmtRemitAck;
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
       * PmtId
       * PmtStatus
       * PmtRefId
       * SvcRqUID
       * NetworkTrnInfo
       * CompositeContactInfo
       * PmtRemitAck
       */
    public final String[] ELEMENTS = {
              "PmtId"
                 ,"PmtStatus"
                 ,"PmtRefId"
                 ,"SvcRqUID"
                 ,"NetworkTrnInfo"
                 ,"CompositeContactInfo"
                 ,"PmtRemitAck"
          };
}
