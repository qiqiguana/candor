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
public class ChkIssueRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public ChkIssueRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkIssueId _chkIssueId;

    /** 
     * Setter for chkIssueId
     * @param chkIssueId the org.sourceforge.ifx.framework.element.ChkIssueId to set
     */
    public void setChkIssueId(org.sourceforge.ifx.framework.element.ChkIssueId _chkIssueId) {
        this._chkIssueId = _chkIssueId;
    }

    /**
     * Getter for chkIssueId
     * @return a org.sourceforge.ifx.framework.element.ChkIssueId
     */
    public org.sourceforge.ifx.framework.element.ChkIssueId getChkIssueId() {
        return _chkIssueId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkIssueInfo _chkIssueInfo;

    /** 
     * Setter for chkIssueInfo
     * @param chkIssueInfo the org.sourceforge.ifx.framework.element.ChkIssueInfo to set
     */
    public void setChkIssueInfo(org.sourceforge.ifx.framework.element.ChkIssueInfo _chkIssueInfo) {
        this._chkIssueInfo = _chkIssueInfo;
    }

    /**
     * Getter for chkIssueInfo
     * @return a org.sourceforge.ifx.framework.element.ChkIssueInfo
     */
    public org.sourceforge.ifx.framework.element.ChkIssueInfo getChkIssueInfo() {
        return _chkIssueInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkIssueStatus _chkIssueStatus;

    /** 
     * Setter for chkIssueStatus
     * @param chkIssueStatus the org.sourceforge.ifx.framework.element.ChkIssueStatus to set
     */
    public void setChkIssueStatus(org.sourceforge.ifx.framework.element.ChkIssueStatus _chkIssueStatus) {
        this._chkIssueStatus = _chkIssueStatus;
    }

    /**
     * Getter for chkIssueStatus
     * @return a org.sourceforge.ifx.framework.element.ChkIssueStatus
     */
    public org.sourceforge.ifx.framework.element.ChkIssueStatus getChkIssueStatus() {
        return _chkIssueStatus;
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
       * ChkIssueId
       * ChkIssueInfo
       * ChkIssueStatus
       */
    public final String[] ELEMENTS = {
              "ChkIssueId"
                 ,"ChkIssueInfo"
                 ,"ChkIssueStatus"
          };
}
