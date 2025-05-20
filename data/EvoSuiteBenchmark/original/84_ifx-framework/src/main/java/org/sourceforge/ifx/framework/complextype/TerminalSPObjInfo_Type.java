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
public class TerminalSPObjInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public TerminalSPObjInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SPName _sPName;

    /** 
     * Setter for sPName
     * @param sPName the org.sourceforge.ifx.framework.element.SPName to set
     */
    public void setSPName(org.sourceforge.ifx.framework.element.SPName _sPName) {
        this._sPName = _sPName;
    }

    /**
     * Getter for sPName
     * @return a org.sourceforge.ifx.framework.element.SPName
     */
    public org.sourceforge.ifx.framework.element.SPName getSPName() {
        return _sPName;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalId _terminalId;

    /** 
     * Setter for terminalId
     * @param terminalId the org.sourceforge.ifx.framework.element.TerminalId to set
     */
    public void setTerminalId(org.sourceforge.ifx.framework.element.TerminalId _terminalId) {
        this._terminalId = _terminalId;
    }

    /**
     * Getter for terminalId
     * @return a org.sourceforge.ifx.framework.element.TerminalId
     */
    public org.sourceforge.ifx.framework.element.TerminalId getTerminalId() {
        return _terminalId;
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
       * SPName
       * TerminalId
       */
    public final String[] ELEMENTS = {
              "SPName"
                 ,"TerminalId"
          };
}
