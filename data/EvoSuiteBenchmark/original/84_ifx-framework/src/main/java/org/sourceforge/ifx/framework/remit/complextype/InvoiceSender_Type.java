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
package org.sourceforge.ifx.framework.remit.complextype;

/**
 * Generated code.
 * 
 * @author org.sourceforge.ifx.tools.CodeGenerator
 */
public class InvoiceSender_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public InvoiceSender_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.OrgId _orgId;

    /** 
     * Setter for orgId
     * @param orgId the org.sourceforge.ifx.framework.element.OrgId to set
     */
    public void setOrgId(org.sourceforge.ifx.framework.element.OrgId _orgId) {
        this._orgId = _orgId;
    }

    /**
     * Getter for orgId
     * @return a org.sourceforge.ifx.framework.element.OrgId
     */
    public org.sourceforge.ifx.framework.element.OrgId getOrgId() {
        return _orgId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.OrgInfo _orgInfo;

    /** 
     * Setter for orgInfo
     * @param orgInfo the org.sourceforge.ifx.framework.element.OrgInfo to set
     */
    public void setOrgInfo(org.sourceforge.ifx.framework.element.OrgInfo _orgInfo) {
        this._orgInfo = _orgInfo;
    }

    /**
     * Getter for orgInfo
     * @return a org.sourceforge.ifx.framework.element.OrgInfo
     */
    public org.sourceforge.ifx.framework.element.OrgInfo getOrgInfo() {
        return _orgInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PostAddr _postAddr;

    /** 
     * Setter for postAddr
     * @param postAddr the org.sourceforge.ifx.framework.element.PostAddr to set
     */
    public void setPostAddr(org.sourceforge.ifx.framework.element.PostAddr _postAddr) {
        this._postAddr = _postAddr;
    }

    /**
     * Getter for postAddr
     * @return a org.sourceforge.ifx.framework.element.PostAddr
     */
    public org.sourceforge.ifx.framework.element.PostAddr getPostAddr() {
        return _postAddr;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AcctPayAcct _acctPayAcct;

    /** 
     * Setter for acctPayAcct
     * @param acctPayAcct the org.sourceforge.ifx.framework.element.AcctPayAcct to set
     */
    public void setAcctPayAcct(org.sourceforge.ifx.framework.element.AcctPayAcct _acctPayAcct) {
        this._acctPayAcct = _acctPayAcct;
    }

    /**
     * Getter for acctPayAcct
     * @return a org.sourceforge.ifx.framework.element.AcctPayAcct
     */
    public org.sourceforge.ifx.framework.element.AcctPayAcct getAcctPayAcct() {
        return _acctPayAcct;
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
       * OrgId
       * OrgInfo
       * PostAddr
       * AcctPayAcct
       */
    public final String[] ELEMENTS = {
              "OrgId"
                 ,"OrgInfo"
                 ,"PostAddr"
                 ,"AcctPayAcct"
          };
}
