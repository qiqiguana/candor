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
public class ChkAcceptRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public ChkAcceptRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkAcceptId _chkAcceptId;

    /** 
     * Setter for chkAcceptId
     * @param chkAcceptId the org.sourceforge.ifx.framework.element.ChkAcceptId to set
     */
    public void setChkAcceptId(org.sourceforge.ifx.framework.element.ChkAcceptId _chkAcceptId) {
        this._chkAcceptId = _chkAcceptId;
    }

    /**
     * Getter for chkAcceptId
     * @return a org.sourceforge.ifx.framework.element.ChkAcceptId
     */
    public org.sourceforge.ifx.framework.element.ChkAcceptId getChkAcceptId() {
        return _chkAcceptId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkAcceptInfo _chkAcceptInfo;

    /** 
     * Setter for chkAcceptInfo
     * @param chkAcceptInfo the org.sourceforge.ifx.framework.element.ChkAcceptInfo to set
     */
    public void setChkAcceptInfo(org.sourceforge.ifx.framework.element.ChkAcceptInfo _chkAcceptInfo) {
        this._chkAcceptInfo = _chkAcceptInfo;
    }

    /**
     * Getter for chkAcceptInfo
     * @return a org.sourceforge.ifx.framework.element.ChkAcceptInfo
     */
    public org.sourceforge.ifx.framework.element.ChkAcceptInfo getChkAcceptInfo() {
        return _chkAcceptInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkAcceptStatus _chkAcceptStatus;

    /** 
     * Setter for chkAcceptStatus
     * @param chkAcceptStatus the org.sourceforge.ifx.framework.element.ChkAcceptStatus to set
     */
    public void setChkAcceptStatus(org.sourceforge.ifx.framework.element.ChkAcceptStatus _chkAcceptStatus) {
        this._chkAcceptStatus = _chkAcceptStatus;
    }

    /**
     * Getter for chkAcceptStatus
     * @return a org.sourceforge.ifx.framework.element.ChkAcceptStatus
     */
    public org.sourceforge.ifx.framework.element.ChkAcceptStatus getChkAcceptStatus() {
        return _chkAcceptStatus;
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
       * ChkAcceptId
       * ChkAcceptInfo
       * ChkAcceptStatus
       */
    public final String[] ELEMENTS = {
              "ChkAcceptId"
                 ,"ChkAcceptInfo"
                 ,"ChkAcceptStatus"
          };
}
