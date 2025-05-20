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
public class ForExRateRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public ForExRateRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ForExRateId _forExRateId;

    /** 
     * Setter for forExRateId
     * @param forExRateId the org.sourceforge.ifx.framework.element.ForExRateId to set
     */
    public void setForExRateId(org.sourceforge.ifx.framework.element.ForExRateId _forExRateId) {
        this._forExRateId = _forExRateId;
    }

    /**
     * Getter for forExRateId
     * @return a org.sourceforge.ifx.framework.element.ForExRateId
     */
    public org.sourceforge.ifx.framework.element.ForExRateId getForExRateId() {
        return _forExRateId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ForExRateInfo _forExRateInfo;

    /** 
     * Setter for forExRateInfo
     * @param forExRateInfo the org.sourceforge.ifx.framework.element.ForExRateInfo to set
     */
    public void setForExRateInfo(org.sourceforge.ifx.framework.element.ForExRateInfo _forExRateInfo) {
        this._forExRateInfo = _forExRateInfo;
    }

    /**
     * Getter for forExRateInfo
     * @return a org.sourceforge.ifx.framework.element.ForExRateInfo
     */
    public org.sourceforge.ifx.framework.element.ForExRateInfo getForExRateInfo() {
        return _forExRateInfo;
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
       * ForExRateId
       * ForExRateInfo
       */
    public final String[] ELEMENTS = {
              "ForExRateId"
                 ,"ForExRateInfo"
          };
}
