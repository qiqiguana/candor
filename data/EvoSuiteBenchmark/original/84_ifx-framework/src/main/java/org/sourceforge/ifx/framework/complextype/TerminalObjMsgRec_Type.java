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
public class TerminalObjMsgRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public TerminalObjMsgRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MsgRecDt _msgRecDt;

    /** 
     * Setter for msgRecDt
     * @param msgRecDt the org.sourceforge.ifx.framework.element.MsgRecDt to set
     */
    public void setMsgRecDt(org.sourceforge.ifx.framework.element.MsgRecDt _msgRecDt) {
        this._msgRecDt = _msgRecDt;
    }

    /**
     * Getter for msgRecDt
     * @return a org.sourceforge.ifx.framework.element.MsgRecDt
     */
    public org.sourceforge.ifx.framework.element.MsgRecDt getMsgRecDt() {
        return _msgRecDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalObjAddRs _terminalObjAddRs;

    /** 
     * Setter for terminalObjAddRs
     * @param terminalObjAddRs the org.sourceforge.ifx.framework.element.TerminalObjAddRs to set
     */
    public void setTerminalObjAddRs(org.sourceforge.ifx.framework.element.TerminalObjAddRs _terminalObjAddRs) {
        this._terminalObjAddRs = _terminalObjAddRs;
    }

    /**
     * Getter for terminalObjAddRs
     * @return a org.sourceforge.ifx.framework.element.TerminalObjAddRs
     */
    public org.sourceforge.ifx.framework.element.TerminalObjAddRs getTerminalObjAddRs() {
        return _terminalObjAddRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalObjModRs _terminalObjModRs;

    /** 
     * Setter for terminalObjModRs
     * @param terminalObjModRs the org.sourceforge.ifx.framework.element.TerminalObjModRs to set
     */
    public void setTerminalObjModRs(org.sourceforge.ifx.framework.element.TerminalObjModRs _terminalObjModRs) {
        this._terminalObjModRs = _terminalObjModRs;
    }

    /**
     * Getter for terminalObjModRs
     * @return a org.sourceforge.ifx.framework.element.TerminalObjModRs
     */
    public org.sourceforge.ifx.framework.element.TerminalObjModRs getTerminalObjModRs() {
        return _terminalObjModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalObjDelRs _terminalObjDelRs;

    /** 
     * Setter for terminalObjDelRs
     * @param terminalObjDelRs the org.sourceforge.ifx.framework.element.TerminalObjDelRs to set
     */
    public void setTerminalObjDelRs(org.sourceforge.ifx.framework.element.TerminalObjDelRs _terminalObjDelRs) {
        this._terminalObjDelRs = _terminalObjDelRs;
    }

    /**
     * Getter for terminalObjDelRs
     * @return a org.sourceforge.ifx.framework.element.TerminalObjDelRs
     */
    public org.sourceforge.ifx.framework.element.TerminalObjDelRs getTerminalObjDelRs() {
        return _terminalObjDelRs;
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
       * MsgRecDt
       * TerminalObjAddRs
       * TerminalObjModRs
       * TerminalObjDelRs
       */
    public final String[] ELEMENTS = {
              "MsgRecDt"
                 ,"TerminalObjAddRs"
                 ,"TerminalObjModRs"
                 ,"TerminalObjDelRs"
          };
}
