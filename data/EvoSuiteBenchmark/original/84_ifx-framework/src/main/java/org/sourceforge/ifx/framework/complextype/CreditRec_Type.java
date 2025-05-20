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
public class CreditRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CreditRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditId _creditId;

    /** 
     * Setter for creditId
     * @param creditId the org.sourceforge.ifx.framework.element.CreditId to set
     */
    public void setCreditId(org.sourceforge.ifx.framework.element.CreditId _creditId) {
        this._creditId = _creditId;
    }

    /**
     * Getter for creditId
     * @return a org.sourceforge.ifx.framework.element.CreditId
     */
    public org.sourceforge.ifx.framework.element.CreditId getCreditId() {
        return _creditId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditInfo _creditInfo;

    /** 
     * Setter for creditInfo
     * @param creditInfo the org.sourceforge.ifx.framework.element.CreditInfo to set
     */
    public void setCreditInfo(org.sourceforge.ifx.framework.element.CreditInfo _creditInfo) {
        this._creditInfo = _creditInfo;
    }

    /**
     * Getter for creditInfo
     * @return a org.sourceforge.ifx.framework.element.CreditInfo
     */
    public org.sourceforge.ifx.framework.element.CreditInfo getCreditInfo() {
        return _creditInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditStatus _creditStatus;

    /** 
     * Setter for creditStatus
     * @param creditStatus the org.sourceforge.ifx.framework.element.CreditStatus to set
     */
    public void setCreditStatus(org.sourceforge.ifx.framework.element.CreditStatus _creditStatus) {
        this._creditStatus = _creditStatus;
    }

    /**
     * Getter for creditStatus
     * @return a org.sourceforge.ifx.framework.element.CreditStatus
     */
    public org.sourceforge.ifx.framework.element.CreditStatus getCreditStatus() {
        return _creditStatus;
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
       * CreditId
       * CreditInfo
       * CreditStatus
       */
    public final String[] ELEMENTS = {
              "CreditId"
                 ,"CreditInfo"
                 ,"CreditStatus"
          };
}
