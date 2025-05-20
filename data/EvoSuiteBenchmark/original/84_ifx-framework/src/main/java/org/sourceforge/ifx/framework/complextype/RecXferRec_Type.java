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
public class RecXferRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public RecXferRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecXferId _recXferId;

    /** 
     * Setter for recXferId
     * @param recXferId the org.sourceforge.ifx.framework.element.RecXferId to set
     */
    public void setRecXferId(org.sourceforge.ifx.framework.element.RecXferId _recXferId) {
        this._recXferId = _recXferId;
    }

    /**
     * Getter for recXferId
     * @return a org.sourceforge.ifx.framework.element.RecXferId
     */
    public org.sourceforge.ifx.framework.element.RecXferId getRecXferId() {
        return _recXferId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.XferInfo _xferInfo;

    /** 
     * Setter for xferInfo
     * @param xferInfo the org.sourceforge.ifx.framework.element.XferInfo to set
     */
    public void setXferInfo(org.sourceforge.ifx.framework.element.XferInfo _xferInfo) {
        this._xferInfo = _xferInfo;
    }

    /**
     * Getter for xferInfo
     * @return a org.sourceforge.ifx.framework.element.XferInfo
     */
    public org.sourceforge.ifx.framework.element.XferInfo getXferInfo() {
        return _xferInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecModelInfo _recModelInfo;

    /** 
     * Setter for recModelInfo
     * @param recModelInfo the org.sourceforge.ifx.framework.element.RecModelInfo to set
     */
    public void setRecModelInfo(org.sourceforge.ifx.framework.element.RecModelInfo _recModelInfo) {
        this._recModelInfo = _recModelInfo;
    }

    /**
     * Getter for recModelInfo
     * @return a org.sourceforge.ifx.framework.element.RecModelInfo
     */
    public org.sourceforge.ifx.framework.element.RecModelInfo getRecModelInfo() {
        return _recModelInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RemainingInsts _remainingInsts;

    /** 
     * Setter for remainingInsts
     * @param remainingInsts the org.sourceforge.ifx.framework.element.RemainingInsts to set
     */
    public void setRemainingInsts(org.sourceforge.ifx.framework.element.RemainingInsts _remainingInsts) {
        this._remainingInsts = _remainingInsts;
    }

    /**
     * Getter for remainingInsts
     * @return a org.sourceforge.ifx.framework.element.RemainingInsts
     */
    public org.sourceforge.ifx.framework.element.RemainingInsts getRemainingInsts() {
        return _remainingInsts;
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
       * RecXferId
       * XferInfo
       * RecModelInfo
       * RemainingInsts
       */
    public final String[] ELEMENTS = {
              "RecXferId"
                 ,"XferInfo"
                 ,"RecModelInfo"
                 ,"RemainingInsts"
          };
}
