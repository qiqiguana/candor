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
public class BankSvcChkSumRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public BankSvcChkSumRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankSvcChkSumId _bankSvcChkSumId;

    /** 
     * Setter for bankSvcChkSumId
     * @param bankSvcChkSumId the org.sourceforge.ifx.framework.element.BankSvcChkSumId to set
     */
    public void setBankSvcChkSumId(org.sourceforge.ifx.framework.element.BankSvcChkSumId _bankSvcChkSumId) {
        this._bankSvcChkSumId = _bankSvcChkSumId;
    }

    /**
     * Getter for bankSvcChkSumId
     * @return a org.sourceforge.ifx.framework.element.BankSvcChkSumId
     */
    public org.sourceforge.ifx.framework.element.BankSvcChkSumId getBankSvcChkSumId() {
        return _bankSvcChkSumId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankSvcChkSumInfo _bankSvcChkSumInfo;

    /** 
     * Setter for bankSvcChkSumInfo
     * @param bankSvcChkSumInfo the org.sourceforge.ifx.framework.element.BankSvcChkSumInfo to set
     */
    public void setBankSvcChkSumInfo(org.sourceforge.ifx.framework.element.BankSvcChkSumInfo _bankSvcChkSumInfo) {
        this._bankSvcChkSumInfo = _bankSvcChkSumInfo;
    }

    /**
     * Getter for bankSvcChkSumInfo
     * @return a org.sourceforge.ifx.framework.element.BankSvcChkSumInfo
     */
    public org.sourceforge.ifx.framework.element.BankSvcChkSumInfo getBankSvcChkSumInfo() {
        return _bankSvcChkSumInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BankSvcChkSumStatus _bankSvcChkSumStatus;

    /** 
     * Setter for bankSvcChkSumStatus
     * @param bankSvcChkSumStatus the org.sourceforge.ifx.framework.element.BankSvcChkSumStatus to set
     */
    public void setBankSvcChkSumStatus(org.sourceforge.ifx.framework.element.BankSvcChkSumStatus _bankSvcChkSumStatus) {
        this._bankSvcChkSumStatus = _bankSvcChkSumStatus;
    }

    /**
     * Getter for bankSvcChkSumStatus
     * @return a org.sourceforge.ifx.framework.element.BankSvcChkSumStatus
     */
    public org.sourceforge.ifx.framework.element.BankSvcChkSumStatus getBankSvcChkSumStatus() {
        return _bankSvcChkSumStatus;
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
       * BankSvcChkSumId
       * BankSvcChkSumInfo
       * BankSvcChkSumStatus
       */
    public final String[] ELEMENTS = {
              "BankSvcChkSumId"
                 ,"BankSvcChkSumInfo"
                 ,"BankSvcChkSumStatus"
          };
}
