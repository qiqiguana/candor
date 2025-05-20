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
public class TerminalSPObjMsgRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public TerminalSPObjMsgRec_Type() {
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
    private org.sourceforge.ifx.framework.element.TerminalSPObjAddRs _terminalSPObjAddRs;

    /** 
     * Setter for terminalSPObjAddRs
     * @param terminalSPObjAddRs the org.sourceforge.ifx.framework.element.TerminalSPObjAddRs to set
     */
    public void setTerminalSPObjAddRs(org.sourceforge.ifx.framework.element.TerminalSPObjAddRs _terminalSPObjAddRs) {
        this._terminalSPObjAddRs = _terminalSPObjAddRs;
    }

    /**
     * Getter for terminalSPObjAddRs
     * @return a org.sourceforge.ifx.framework.element.TerminalSPObjAddRs
     */
    public org.sourceforge.ifx.framework.element.TerminalSPObjAddRs getTerminalSPObjAddRs() {
        return _terminalSPObjAddRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalSPObjModRs _terminalSPObjModRs;

    /** 
     * Setter for terminalSPObjModRs
     * @param terminalSPObjModRs the org.sourceforge.ifx.framework.element.TerminalSPObjModRs to set
     */
    public void setTerminalSPObjModRs(org.sourceforge.ifx.framework.element.TerminalSPObjModRs _terminalSPObjModRs) {
        this._terminalSPObjModRs = _terminalSPObjModRs;
    }

    /**
     * Getter for terminalSPObjModRs
     * @return a org.sourceforge.ifx.framework.element.TerminalSPObjModRs
     */
    public org.sourceforge.ifx.framework.element.TerminalSPObjModRs getTerminalSPObjModRs() {
        return _terminalSPObjModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalSPObjDelRs _terminalSPObjDelRs;

    /** 
     * Setter for terminalSPObjDelRs
     * @param terminalSPObjDelRs the org.sourceforge.ifx.framework.element.TerminalSPObjDelRs to set
     */
    public void setTerminalSPObjDelRs(org.sourceforge.ifx.framework.element.TerminalSPObjDelRs _terminalSPObjDelRs) {
        this._terminalSPObjDelRs = _terminalSPObjDelRs;
    }

    /**
     * Getter for terminalSPObjDelRs
     * @return a org.sourceforge.ifx.framework.element.TerminalSPObjDelRs
     */
    public org.sourceforge.ifx.framework.element.TerminalSPObjDelRs getTerminalSPObjDelRs() {
        return _terminalSPObjDelRs;
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
       * TerminalSPObjAddRs
       * TerminalSPObjModRs
       * TerminalSPObjDelRs
       */
    public final String[] ELEMENTS = {
              "MsgRecDt"
                 ,"TerminalSPObjAddRs"
                 ,"TerminalSPObjModRs"
                 ,"TerminalSPObjDelRs"
          };
}
