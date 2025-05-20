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
public class ChkIssueId_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public ChkIssueId_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkNum _chkNum;

    /** 
     * Setter for chkNum
     * @param chkNum the org.sourceforge.ifx.framework.element.ChkNum to set
     */
    public void setChkNum(org.sourceforge.ifx.framework.element.ChkNum _chkNum) {
        this._chkNum = _chkNum;
    }

    /**
     * Getter for chkNum
     * @return a org.sourceforge.ifx.framework.element.ChkNum
     */
    public org.sourceforge.ifx.framework.element.ChkNum getChkNum() {
        return _chkNum;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.OrigDt _origDt;

    /** 
     * Setter for origDt
     * @param origDt the org.sourceforge.ifx.framework.element.OrigDt to set
     */
    public void setOrigDt(org.sourceforge.ifx.framework.element.OrigDt _origDt) {
        this._origDt = _origDt;
    }

    /**
     * Getter for origDt
     * @return a org.sourceforge.ifx.framework.element.OrigDt
     */
    public org.sourceforge.ifx.framework.element.OrigDt getOrigDt() {
        return _origDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AcctId _acctId;

    /** 
     * Setter for acctId
     * @param acctId the org.sourceforge.ifx.framework.element.AcctId to set
     */
    public void setAcctId(org.sourceforge.ifx.framework.element.AcctId _acctId) {
        this._acctId = _acctId;
    }

    /**
     * Getter for acctId
     * @return a org.sourceforge.ifx.framework.element.AcctId
     */
    public org.sourceforge.ifx.framework.element.AcctId getAcctId() {
        return _acctId;
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
       * ChkNum
       * OrigDt
       * AcctId
       */
    public final String[] ELEMENTS = {
              "ChkNum"
                 ,"OrigDt"
                 ,"AcctId"
          };
}
