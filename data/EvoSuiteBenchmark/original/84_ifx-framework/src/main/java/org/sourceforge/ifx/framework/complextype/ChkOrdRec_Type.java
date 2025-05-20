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
public class ChkOrdRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public ChkOrdRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkOrdId _chkOrdId;

    /** 
     * Setter for chkOrdId
     * @param chkOrdId the org.sourceforge.ifx.framework.element.ChkOrdId to set
     */
    public void setChkOrdId(org.sourceforge.ifx.framework.element.ChkOrdId _chkOrdId) {
        this._chkOrdId = _chkOrdId;
    }

    /**
     * Getter for chkOrdId
     * @return a org.sourceforge.ifx.framework.element.ChkOrdId
     */
    public org.sourceforge.ifx.framework.element.ChkOrdId getChkOrdId() {
        return _chkOrdId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkOrdInfo _chkOrdInfo;

    /** 
     * Setter for chkOrdInfo
     * @param chkOrdInfo the org.sourceforge.ifx.framework.element.ChkOrdInfo to set
     */
    public void setChkOrdInfo(org.sourceforge.ifx.framework.element.ChkOrdInfo _chkOrdInfo) {
        this._chkOrdInfo = _chkOrdInfo;
    }

    /**
     * Getter for chkOrdInfo
     * @return a org.sourceforge.ifx.framework.element.ChkOrdInfo
     */
    public org.sourceforge.ifx.framework.element.ChkOrdInfo getChkOrdInfo() {
        return _chkOrdInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkOrdStatus _chkOrdStatus;

    /** 
     * Setter for chkOrdStatus
     * @param chkOrdStatus the org.sourceforge.ifx.framework.element.ChkOrdStatus to set
     */
    public void setChkOrdStatus(org.sourceforge.ifx.framework.element.ChkOrdStatus _chkOrdStatus) {
        this._chkOrdStatus = _chkOrdStatus;
    }

    /**
     * Getter for chkOrdStatus
     * @return a org.sourceforge.ifx.framework.element.ChkOrdStatus
     */
    public org.sourceforge.ifx.framework.element.ChkOrdStatus getChkOrdStatus() {
        return _chkOrdStatus;
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
       * ChkOrdId
       * ChkOrdInfo
       * ChkOrdStatus
       */
    public final String[] ELEMENTS = {
              "ChkOrdId"
                 ,"ChkOrdInfo"
                 ,"ChkOrdStatus"
          };
}
