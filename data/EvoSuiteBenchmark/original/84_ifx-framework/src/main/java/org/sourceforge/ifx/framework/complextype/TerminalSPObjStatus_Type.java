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
public class TerminalSPObjStatus_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public TerminalSPObjStatus_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TerminalSPObjStatusCode _terminalSPObjStatusCode;

    /** 
     * Setter for terminalSPObjStatusCode
     * @param terminalSPObjStatusCode the org.sourceforge.ifx.framework.element.TerminalSPObjStatusCode to set
     */
    public void setTerminalSPObjStatusCode(org.sourceforge.ifx.framework.element.TerminalSPObjStatusCode _terminalSPObjStatusCode) {
        this._terminalSPObjStatusCode = _terminalSPObjStatusCode;
    }

    /**
     * Getter for terminalSPObjStatusCode
     * @return a org.sourceforge.ifx.framework.element.TerminalSPObjStatusCode
     */
    public org.sourceforge.ifx.framework.element.TerminalSPObjStatusCode getTerminalSPObjStatusCode() {
        return _terminalSPObjStatusCode;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ExpectedAvailDt _expectedAvailDt;

    /** 
     * Setter for expectedAvailDt
     * @param expectedAvailDt the org.sourceforge.ifx.framework.element.ExpectedAvailDt to set
     */
    public void setExpectedAvailDt(org.sourceforge.ifx.framework.element.ExpectedAvailDt _expectedAvailDt) {
        this._expectedAvailDt = _expectedAvailDt;
    }

    /**
     * Getter for expectedAvailDt
     * @return a org.sourceforge.ifx.framework.element.ExpectedAvailDt
     */
    public org.sourceforge.ifx.framework.element.ExpectedAvailDt getExpectedAvailDt() {
        return _expectedAvailDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StatusDesc _statusDesc;

    /** 
     * Setter for statusDesc
     * @param statusDesc the org.sourceforge.ifx.framework.element.StatusDesc to set
     */
    public void setStatusDesc(org.sourceforge.ifx.framework.element.StatusDesc _statusDesc) {
        this._statusDesc = _statusDesc;
    }

    /**
     * Getter for statusDesc
     * @return a org.sourceforge.ifx.framework.element.StatusDesc
     */
    public org.sourceforge.ifx.framework.element.StatusDesc getStatusDesc() {
        return _statusDesc;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.EffDt _effDt;

    /** 
     * Setter for effDt
     * @param effDt the org.sourceforge.ifx.framework.element.EffDt to set
     */
    public void setEffDt(org.sourceforge.ifx.framework.element.EffDt _effDt) {
        this._effDt = _effDt;
    }

    /**
     * Getter for effDt
     * @return a org.sourceforge.ifx.framework.element.EffDt
     */
    public org.sourceforge.ifx.framework.element.EffDt getEffDt() {
        return _effDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StatusModBy _statusModBy;

    /** 
     * Setter for statusModBy
     * @param statusModBy the org.sourceforge.ifx.framework.element.StatusModBy to set
     */
    public void setStatusModBy(org.sourceforge.ifx.framework.element.StatusModBy _statusModBy) {
        this._statusModBy = _statusModBy;
    }

    /**
     * Getter for statusModBy
     * @return a org.sourceforge.ifx.framework.element.StatusModBy
     */
    public org.sourceforge.ifx.framework.element.StatusModBy getStatusModBy() {
        return _statusModBy;
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
       * TerminalSPObjStatusCode
       * ExpectedAvailDt
       * StatusDesc
       * EffDt
       * StatusModBy
       */
    public final String[] ELEMENTS = {
              "TerminalSPObjStatusCode"
                 ,"ExpectedAvailDt"
                 ,"StatusDesc"
                 ,"EffDt"
                 ,"StatusModBy"
          };
}
