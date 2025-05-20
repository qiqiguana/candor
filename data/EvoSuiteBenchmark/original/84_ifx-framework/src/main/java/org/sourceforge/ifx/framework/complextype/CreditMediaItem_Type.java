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
public class CreditMediaItem_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CreditMediaItem_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditMediaItemId _creditMediaItemId;

    /** 
     * Setter for creditMediaItemId
     * @param creditMediaItemId the org.sourceforge.ifx.framework.element.CreditMediaItemId to set
     */
    public void setCreditMediaItemId(org.sourceforge.ifx.framework.element.CreditMediaItemId _creditMediaItemId) {
        this._creditMediaItemId = _creditMediaItemId;
    }

    /**
     * Getter for creditMediaItemId
     * @return a org.sourceforge.ifx.framework.element.CreditMediaItemId
     */
    public org.sourceforge.ifx.framework.element.CreditMediaItemId getCreditMediaItemId() {
        return _creditMediaItemId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditMediaCashInfo _creditMediaCashInfo;

    /** 
     * Setter for creditMediaCashInfo
     * @param creditMediaCashInfo the org.sourceforge.ifx.framework.element.CreditMediaCashInfo to set
     */
    public void setCreditMediaCashInfo(org.sourceforge.ifx.framework.element.CreditMediaCashInfo _creditMediaCashInfo) {
        this._creditMediaCashInfo = _creditMediaCashInfo;
    }

    /**
     * Getter for creditMediaCashInfo
     * @return a org.sourceforge.ifx.framework.element.CreditMediaCashInfo
     */
    public org.sourceforge.ifx.framework.element.CreditMediaCashInfo getCreditMediaCashInfo() {
        return _creditMediaCashInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditMediaChkInfo _creditMediaChkInfo;

    /** 
     * Setter for creditMediaChkInfo
     * @param creditMediaChkInfo the org.sourceforge.ifx.framework.element.CreditMediaChkInfo to set
     */
    public void setCreditMediaChkInfo(org.sourceforge.ifx.framework.element.CreditMediaChkInfo _creditMediaChkInfo) {
        this._creditMediaChkInfo = _creditMediaChkInfo;
    }

    /**
     * Getter for creditMediaChkInfo
     * @return a org.sourceforge.ifx.framework.element.CreditMediaChkInfo
     */
    public org.sourceforge.ifx.framework.element.CreditMediaChkInfo getCreditMediaChkInfo() {
        return _creditMediaChkInfo;
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
       * CreditMediaItemId
       * CreditMediaCashInfo
       * CreditMediaChkInfo
       */
    public final String[] ELEMENTS = {
              "CreditMediaItemId"
                 ,"CreditMediaCashInfo"
                 ,"CreditMediaChkInfo"
          };
}
