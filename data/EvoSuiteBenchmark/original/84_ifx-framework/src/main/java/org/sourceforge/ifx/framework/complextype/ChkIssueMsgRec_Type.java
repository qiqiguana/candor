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
public class ChkIssueMsgRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public ChkIssueMsgRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustId _custId;

    /** 
     * Setter for custId
     * @param custId the org.sourceforge.ifx.framework.element.CustId to set
     */
    public void setCustId(org.sourceforge.ifx.framework.element.CustId _custId) {
        this._custId = _custId;
    }

    /**
     * Getter for custId
     * @return a org.sourceforge.ifx.framework.element.CustId
     */
    public org.sourceforge.ifx.framework.element.CustId getCustId() {
        return _custId;
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
    private org.sourceforge.ifx.framework.element.ChkIssueAddRs _chkIssueAddRs;

    /** 
     * Setter for chkIssueAddRs
     * @param chkIssueAddRs the org.sourceforge.ifx.framework.element.ChkIssueAddRs to set
     */
    public void setChkIssueAddRs(org.sourceforge.ifx.framework.element.ChkIssueAddRs _chkIssueAddRs) {
        this._chkIssueAddRs = _chkIssueAddRs;
    }

    /**
     * Getter for chkIssueAddRs
     * @return a org.sourceforge.ifx.framework.element.ChkIssueAddRs
     */
    public org.sourceforge.ifx.framework.element.ChkIssueAddRs getChkIssueAddRs() {
        return _chkIssueAddRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkIssueModRs _chkIssueModRs;

    /** 
     * Setter for chkIssueModRs
     * @param chkIssueModRs the org.sourceforge.ifx.framework.element.ChkIssueModRs to set
     */
    public void setChkIssueModRs(org.sourceforge.ifx.framework.element.ChkIssueModRs _chkIssueModRs) {
        this._chkIssueModRs = _chkIssueModRs;
    }

    /**
     * Getter for chkIssueModRs
     * @return a org.sourceforge.ifx.framework.element.ChkIssueModRs
     */
    public org.sourceforge.ifx.framework.element.ChkIssueModRs getChkIssueModRs() {
        return _chkIssueModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkIssueStatusModRs _chkIssueStatusModRs;

    /** 
     * Setter for chkIssueStatusModRs
     * @param chkIssueStatusModRs the org.sourceforge.ifx.framework.element.ChkIssueStatusModRs to set
     */
    public void setChkIssueStatusModRs(org.sourceforge.ifx.framework.element.ChkIssueStatusModRs _chkIssueStatusModRs) {
        this._chkIssueStatusModRs = _chkIssueStatusModRs;
    }

    /**
     * Getter for chkIssueStatusModRs
     * @return a org.sourceforge.ifx.framework.element.ChkIssueStatusModRs
     */
    public org.sourceforge.ifx.framework.element.ChkIssueStatusModRs getChkIssueStatusModRs() {
        return _chkIssueStatusModRs;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkIssueDelRs _chkIssueDelRs;

    /** 
     * Setter for chkIssueDelRs
     * @param chkIssueDelRs the org.sourceforge.ifx.framework.element.ChkIssueDelRs to set
     */
    public void setChkIssueDelRs(org.sourceforge.ifx.framework.element.ChkIssueDelRs _chkIssueDelRs) {
        this._chkIssueDelRs = _chkIssueDelRs;
    }

    /**
     * Getter for chkIssueDelRs
     * @return a org.sourceforge.ifx.framework.element.ChkIssueDelRs
     */
    public org.sourceforge.ifx.framework.element.ChkIssueDelRs getChkIssueDelRs() {
        return _chkIssueDelRs;
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
       * CustId
       * MsgRecDt
       * ChkIssueAddRs
       * ChkIssueModRs
       * ChkIssueStatusModRs
       * ChkIssueDelRs
       */
    public final String[] ELEMENTS = {
              "CustId"
                 ,"MsgRecDt"
                 ,"ChkIssueAddRs"
                 ,"ChkIssueModRs"
                 ,"ChkIssueStatusModRs"
                 ,"ChkIssueDelRs"
          };
}
