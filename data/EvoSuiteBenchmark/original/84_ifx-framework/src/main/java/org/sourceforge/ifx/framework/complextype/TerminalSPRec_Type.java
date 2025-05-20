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
public class TerminalSPRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public TerminalSPRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalSPObjId _terminalSPObjId;

    /** 
     * Setter for terminalSPObjId
     * @param terminalSPObjId the org.sourceforge.ifx.framework.element.TerminalSPObjId to set
     */
    public void setTerminalSPObjId(org.sourceforge.ifx.framework.element.TerminalSPObjId _terminalSPObjId) {
        this._terminalSPObjId = _terminalSPObjId;
    }

    /**
     * Getter for terminalSPObjId
     * @return a org.sourceforge.ifx.framework.element.TerminalSPObjId
     */
    public org.sourceforge.ifx.framework.element.TerminalSPObjId getTerminalSPObjId() {
        return _terminalSPObjId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalSPObjInfo _terminalSPObjInfo;

    /** 
     * Setter for terminalSPObjInfo
     * @param terminalSPObjInfo the org.sourceforge.ifx.framework.element.TerminalSPObjInfo to set
     */
    public void setTerminalSPObjInfo(org.sourceforge.ifx.framework.element.TerminalSPObjInfo _terminalSPObjInfo) {
        this._terminalSPObjInfo = _terminalSPObjInfo;
    }

    /**
     * Getter for terminalSPObjInfo
     * @return a org.sourceforge.ifx.framework.element.TerminalSPObjInfo
     */
    public org.sourceforge.ifx.framework.element.TerminalSPObjInfo getTerminalSPObjInfo() {
        return _terminalSPObjInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalSPObjStatus _terminalSPObjStatus;

    /** 
     * Setter for terminalSPObjStatus
     * @param terminalSPObjStatus the org.sourceforge.ifx.framework.element.TerminalSPObjStatus to set
     */
    public void setTerminalSPObjStatus(org.sourceforge.ifx.framework.element.TerminalSPObjStatus _terminalSPObjStatus) {
        this._terminalSPObjStatus = _terminalSPObjStatus;
    }

    /**
     * Getter for terminalSPObjStatus
     * @return a org.sourceforge.ifx.framework.element.TerminalSPObjStatus
     */
    public org.sourceforge.ifx.framework.element.TerminalSPObjStatus getTerminalSPObjStatus() {
        return _terminalSPObjStatus;
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
       * TerminalSPObjId
       * TerminalSPObjInfo
       * TerminalSPObjStatus
       */
    public final String[] ELEMENTS = {
              "TerminalSPObjId"
                 ,"TerminalSPObjInfo"
                 ,"TerminalSPObjStatus"
          };
}
