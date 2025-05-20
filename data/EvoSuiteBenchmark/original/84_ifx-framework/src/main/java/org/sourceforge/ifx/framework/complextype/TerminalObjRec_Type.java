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
public class TerminalObjRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public TerminalObjRec_Type() {
        super();
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
    private org.sourceforge.ifx.framework.element.TerminalObjInfo _terminalObjInfo;

    /** 
     * Setter for terminalObjInfo
     * @param terminalObjInfo the org.sourceforge.ifx.framework.element.TerminalObjInfo to set
     */
    public void setTerminalObjInfo(org.sourceforge.ifx.framework.element.TerminalObjInfo _terminalObjInfo) {
        this._terminalObjInfo = _terminalObjInfo;
    }

    /**
     * Getter for terminalObjInfo
     * @return a org.sourceforge.ifx.framework.element.TerminalObjInfo
     */
    public org.sourceforge.ifx.framework.element.TerminalObjInfo getTerminalObjInfo() {
        return _terminalObjInfo;
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
       * TerminalObjId
       * TerminalObjInfo
       * TerminalObjStatus
       */
    public final String[] ELEMENTS = {
              "TerminalObjId"
                 ,"TerminalObjInfo"
                 ,"TerminalObjStatus"
          };
}
