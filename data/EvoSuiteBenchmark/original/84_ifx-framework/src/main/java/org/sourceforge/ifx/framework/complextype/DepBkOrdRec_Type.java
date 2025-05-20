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
public class DepBkOrdRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DepBkOrdRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepBkOrdId _depBkOrdId;

    /** 
     * Setter for depBkOrdId
     * @param depBkOrdId the org.sourceforge.ifx.framework.element.DepBkOrdId to set
     */
    public void setDepBkOrdId(org.sourceforge.ifx.framework.element.DepBkOrdId _depBkOrdId) {
        this._depBkOrdId = _depBkOrdId;
    }

    /**
     * Getter for depBkOrdId
     * @return a org.sourceforge.ifx.framework.element.DepBkOrdId
     */
    public org.sourceforge.ifx.framework.element.DepBkOrdId getDepBkOrdId() {
        return _depBkOrdId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepBkOrdInfo _depBkOrdInfo;

    /** 
     * Setter for depBkOrdInfo
     * @param depBkOrdInfo the org.sourceforge.ifx.framework.element.DepBkOrdInfo to set
     */
    public void setDepBkOrdInfo(org.sourceforge.ifx.framework.element.DepBkOrdInfo _depBkOrdInfo) {
        this._depBkOrdInfo = _depBkOrdInfo;
    }

    /**
     * Getter for depBkOrdInfo
     * @return a org.sourceforge.ifx.framework.element.DepBkOrdInfo
     */
    public org.sourceforge.ifx.framework.element.DepBkOrdInfo getDepBkOrdInfo() {
        return _depBkOrdInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepBkOrdStatus _depBkOrdStatus;

    /** 
     * Setter for depBkOrdStatus
     * @param depBkOrdStatus the org.sourceforge.ifx.framework.element.DepBkOrdStatus to set
     */
    public void setDepBkOrdStatus(org.sourceforge.ifx.framework.element.DepBkOrdStatus _depBkOrdStatus) {
        this._depBkOrdStatus = _depBkOrdStatus;
    }

    /**
     * Getter for depBkOrdStatus
     * @return a org.sourceforge.ifx.framework.element.DepBkOrdStatus
     */
    public org.sourceforge.ifx.framework.element.DepBkOrdStatus getDepBkOrdStatus() {
        return _depBkOrdStatus;
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
       * DepBkOrdId
       * DepBkOrdInfo
       * DepBkOrdStatus
       */
    public final String[] ELEMENTS = {
              "DepBkOrdId"
                 ,"DepBkOrdInfo"
                 ,"DepBkOrdStatus"
          };
}
