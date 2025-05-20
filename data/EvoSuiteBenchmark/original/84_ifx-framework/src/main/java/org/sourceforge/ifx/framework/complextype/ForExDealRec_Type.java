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
public class ForExDealRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public ForExDealRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ForExDealId _forExDealId;

    /** 
     * Setter for forExDealId
     * @param forExDealId the org.sourceforge.ifx.framework.element.ForExDealId to set
     */
    public void setForExDealId(org.sourceforge.ifx.framework.element.ForExDealId _forExDealId) {
        this._forExDealId = _forExDealId;
    }

    /**
     * Getter for forExDealId
     * @return a org.sourceforge.ifx.framework.element.ForExDealId
     */
    public org.sourceforge.ifx.framework.element.ForExDealId getForExDealId() {
        return _forExDealId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ForExDealInfo _forExDealInfo;

    /** 
     * Setter for forExDealInfo
     * @param forExDealInfo the org.sourceforge.ifx.framework.element.ForExDealInfo to set
     */
    public void setForExDealInfo(org.sourceforge.ifx.framework.element.ForExDealInfo _forExDealInfo) {
        this._forExDealInfo = _forExDealInfo;
    }

    /**
     * Getter for forExDealInfo
     * @return a org.sourceforge.ifx.framework.element.ForExDealInfo
     */
    public org.sourceforge.ifx.framework.element.ForExDealInfo getForExDealInfo() {
        return _forExDealInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ForExDealStatus _forExDealStatus;

    /** 
     * Setter for forExDealStatus
     * @param forExDealStatus the org.sourceforge.ifx.framework.element.ForExDealStatus to set
     */
    public void setForExDealStatus(org.sourceforge.ifx.framework.element.ForExDealStatus _forExDealStatus) {
        this._forExDealStatus = _forExDealStatus;
    }

    /**
     * Getter for forExDealStatus
     * @return a org.sourceforge.ifx.framework.element.ForExDealStatus
     */
    public org.sourceforge.ifx.framework.element.ForExDealStatus getForExDealStatus() {
        return _forExDealStatus;
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
       * ForExDealId
       * ForExDealInfo
       * ForExDealStatus
       */
    public final String[] ELEMENTS = {
              "ForExDealId"
                 ,"ForExDealInfo"
                 ,"ForExDealStatus"
          };
}
