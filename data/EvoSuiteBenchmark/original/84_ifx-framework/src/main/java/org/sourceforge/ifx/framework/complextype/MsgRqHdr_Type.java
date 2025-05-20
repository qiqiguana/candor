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
public class MsgRqHdr_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public MsgRqHdr_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.EMVRqData _eMVRqData;

    /** 
     * Setter for eMVRqData
     * @param eMVRqData the org.sourceforge.ifx.framework.element.EMVRqData to set
     */
    public void setEMVRqData(org.sourceforge.ifx.framework.element.EMVRqData _eMVRqData) {
        this._eMVRqData = _eMVRqData;
    }

    /**
     * Getter for eMVRqData
     * @return a org.sourceforge.ifx.framework.element.EMVRqData
     */
    public org.sourceforge.ifx.framework.element.EMVRqData getEMVRqData() {
        return _eMVRqData;
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
    private org.sourceforge.ifx.framework.element.ClientTerminalSeqId _clientTerminalSeqId;

    /** 
     * Setter for clientTerminalSeqId
     * @param clientTerminalSeqId the org.sourceforge.ifx.framework.element.ClientTerminalSeqId to set
     */
    public void setClientTerminalSeqId(org.sourceforge.ifx.framework.element.ClientTerminalSeqId _clientTerminalSeqId) {
        this._clientTerminalSeqId = _clientTerminalSeqId;
    }

    /**
     * Getter for clientTerminalSeqId
     * @return a org.sourceforge.ifx.framework.element.ClientTerminalSeqId
     */
    public org.sourceforge.ifx.framework.element.ClientTerminalSeqId getClientTerminalSeqId() {
        return _clientTerminalSeqId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PointOfServiceData _pointOfServiceData;

    /** 
     * Setter for pointOfServiceData
     * @param pointOfServiceData the org.sourceforge.ifx.framework.element.PointOfServiceData to set
     */
    public void setPointOfServiceData(org.sourceforge.ifx.framework.element.PointOfServiceData _pointOfServiceData) {
        this._pointOfServiceData = _pointOfServiceData;
    }

    /**
     * Getter for pointOfServiceData
     * @return a org.sourceforge.ifx.framework.element.PointOfServiceData
     */
    public org.sourceforge.ifx.framework.element.PointOfServiceData getPointOfServiceData() {
        return _pointOfServiceData;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MsgAuthCode _msgAuthCode;

    /** 
     * Setter for msgAuthCode
     * @param msgAuthCode the org.sourceforge.ifx.framework.element.MsgAuthCode to set
     */
    public void setMsgAuthCode(org.sourceforge.ifx.framework.element.MsgAuthCode _msgAuthCode) {
        this._msgAuthCode = _msgAuthCode;
    }

    /**
     * Getter for msgAuthCode
     * @return a org.sourceforge.ifx.framework.element.MsgAuthCode
     */
    public org.sourceforge.ifx.framework.element.MsgAuthCode getMsgAuthCode() {
        return _msgAuthCode;
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
       * EMVRqData
       * NetworkTrnInfo
       * ClientTerminalSeqId
       * PointOfServiceData
       * MsgAuthCode
       */
    public final String[] ELEMENTS = {
              "EMVRqData"
                 ,"NetworkTrnInfo"
                 ,"ClientTerminalSeqId"
                 ,"PointOfServiceData"
                 ,"MsgAuthCode"
          };
}
