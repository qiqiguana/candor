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
public class TerminalObjAdviseRq_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public TerminalObjAdviseRq_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RqUID _rqUID;

    /** 
     * Setter for rqUID
     * @param rqUID the org.sourceforge.ifx.framework.element.RqUID to set
     */
    public void setRqUID(org.sourceforge.ifx.framework.element.RqUID _rqUID) {
        this._rqUID = _rqUID;
    }

    /**
     * Getter for rqUID
     * @return a org.sourceforge.ifx.framework.element.RqUID
     */
    public org.sourceforge.ifx.framework.element.RqUID getRqUID() {
        return _rqUID;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MsgRqHdr _msgRqHdr;

    /** 
     * Setter for msgRqHdr
     * @param msgRqHdr the org.sourceforge.ifx.framework.element.MsgRqHdr to set
     */
    public void setMsgRqHdr(org.sourceforge.ifx.framework.element.MsgRqHdr _msgRqHdr) {
        this._msgRqHdr = _msgRqHdr;
    }

    /**
     * Getter for msgRqHdr
     * @return a org.sourceforge.ifx.framework.element.MsgRqHdr
     */
    public org.sourceforge.ifx.framework.element.MsgRqHdr getMsgRqHdr() {
        return _msgRqHdr;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalObjRec _terminalObjRec;

    /** 
     * Setter for terminalObjRec
     * @param terminalObjRec the org.sourceforge.ifx.framework.element.TerminalObjRec to set
     */
    public void setTerminalObjRec(org.sourceforge.ifx.framework.element.TerminalObjRec _terminalObjRec) {
        this._terminalObjRec = _terminalObjRec;
    }

    /**
     * Getter for terminalObjRec
     * @return a org.sourceforge.ifx.framework.element.TerminalObjRec
     */
    public org.sourceforge.ifx.framework.element.TerminalObjRec getTerminalObjRec() {
        return _terminalObjRec;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalObjId _terminalObjId;

    /** 
     * Setter for terminalObjId
     * @param terminalObjId the org.sourceforge.ifx.framework.element.TerminalObjId to set
     */
    public void setTerminalObjId(org.sourceforge.ifx.framework.element.TerminalObjId _terminalObjId) {
        this._terminalObjId = _terminalObjId;
    }

    /**
     * Getter for terminalObjId
     * @return a org.sourceforge.ifx.framework.element.TerminalObjId
     */
    public org.sourceforge.ifx.framework.element.TerminalObjId getTerminalObjId() {
        return _terminalObjId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalObjStatus _terminalObjStatus;

    /** 
     * Setter for terminalObjStatus
     * @param terminalObjStatus the org.sourceforge.ifx.framework.element.TerminalObjStatus to set
     */
    public void setTerminalObjStatus(org.sourceforge.ifx.framework.element.TerminalObjStatus _terminalObjStatus) {
        this._terminalObjStatus = _terminalObjStatus;
    }

    /**
     * Getter for terminalObjStatus
     * @return a org.sourceforge.ifx.framework.element.TerminalObjStatus
     */
    public org.sourceforge.ifx.framework.element.TerminalObjStatus getTerminalObjStatus() {
        return _terminalObjStatus;
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
       * RqUID
       * MsgRqHdr
       * TerminalObjRec
       * TerminalObjId
       * TerminalObjStatus
       */
    public final String[] ELEMENTS = {
              "RqUID"
                 ,"MsgRqHdr"
                 ,"TerminalObjRec"
                 ,"TerminalObjId"
                 ,"TerminalObjStatus"
          };
}
