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
public class CompRemitStmtRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CompRemitStmtRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CompRemitStmtId _compRemitStmtId;

    /** 
     * Setter for compRemitStmtId
     * @param compRemitStmtId the org.sourceforge.ifx.framework.element.CompRemitStmtId to set
     */
    public void setCompRemitStmtId(org.sourceforge.ifx.framework.element.CompRemitStmtId _compRemitStmtId) {
        this._compRemitStmtId = _compRemitStmtId;
    }

    /**
     * Getter for compRemitStmtId
     * @return a org.sourceforge.ifx.framework.element.CompRemitStmtId
     */
    public org.sourceforge.ifx.framework.element.CompRemitStmtId getCompRemitStmtId() {
        return _compRemitStmtId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CompRemitStmtInfo _compRemitStmtInfo;

    /** 
     * Setter for compRemitStmtInfo
     * @param compRemitStmtInfo the org.sourceforge.ifx.framework.element.CompRemitStmtInfo to set
     */
    public void setCompRemitStmtInfo(org.sourceforge.ifx.framework.element.CompRemitStmtInfo _compRemitStmtInfo) {
        this._compRemitStmtInfo = _compRemitStmtInfo;
    }

    /**
     * Getter for compRemitStmtInfo
     * @return a org.sourceforge.ifx.framework.element.CompRemitStmtInfo
     */
    public org.sourceforge.ifx.framework.element.CompRemitStmtInfo getCompRemitStmtInfo() {
        return _compRemitStmtInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CompRemitStmtStatus _compRemitStmtStatus;

    /** 
     * Setter for compRemitStmtStatus
     * @param compRemitStmtStatus the org.sourceforge.ifx.framework.element.CompRemitStmtStatus to set
     */
    public void setCompRemitStmtStatus(org.sourceforge.ifx.framework.element.CompRemitStmtStatus _compRemitStmtStatus) {
        this._compRemitStmtStatus = _compRemitStmtStatus;
    }

    /**
     * Getter for compRemitStmtStatus
     * @return a org.sourceforge.ifx.framework.element.CompRemitStmtStatus
     */
    public org.sourceforge.ifx.framework.element.CompRemitStmtStatus getCompRemitStmtStatus() {
        return _compRemitStmtStatus;
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
       * CompRemitStmtId
       * CompRemitStmtInfo
       * CompRemitStmtStatus
       */
    public final String[] ELEMENTS = {
              "CompRemitStmtId"
                 ,"CompRemitStmtInfo"
                 ,"CompRemitStmtStatus"
          };
}
