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
public class MsgRsHdr_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public MsgRsHdr_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.EMVRsData _eMVRsData;

    /** 
     * Setter for eMVRsData
     * @param eMVRsData the org.sourceforge.ifx.framework.element.EMVRsData to set
     */
    public void setEMVRsData(org.sourceforge.ifx.framework.element.EMVRsData _eMVRsData) {
        this._eMVRsData = _eMVRsData;
    }

    /**
     * Getter for eMVRsData
     * @return a org.sourceforge.ifx.framework.element.EMVRsData
     */
    public org.sourceforge.ifx.framework.element.EMVRsData getEMVRsData() {
        return _eMVRsData;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ServerTerminalSeqId _serverTerminalSeqId;

    /** 
     * Setter for serverTerminalSeqId
     * @param serverTerminalSeqId the org.sourceforge.ifx.framework.element.ServerTerminalSeqId to set
     */
    public void setServerTerminalSeqId(org.sourceforge.ifx.framework.element.ServerTerminalSeqId _serverTerminalSeqId) {
        this._serverTerminalSeqId = _serverTerminalSeqId;
    }

    /**
     * Getter for serverTerminalSeqId
     * @return a org.sourceforge.ifx.framework.element.ServerTerminalSeqId
     */
    public org.sourceforge.ifx.framework.element.ServerTerminalSeqId getServerTerminalSeqId() {
        return _serverTerminalSeqId;
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
       * EMVRsData
       * ServerTerminalSeqId
       * MsgAuthCode
       */
    public final String[] ELEMENTS = {
              "EMVRsData"
                 ,"ServerTerminalSeqId"
                 ,"MsgAuthCode"
          };
}
