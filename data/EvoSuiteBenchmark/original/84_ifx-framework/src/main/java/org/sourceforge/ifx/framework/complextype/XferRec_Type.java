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
public class XferRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public XferRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.XferId _xferId;

    /** 
     * Setter for xferId
     * @param xferId the org.sourceforge.ifx.framework.element.XferId to set
     */
    public void setXferId(org.sourceforge.ifx.framework.element.XferId _xferId) {
        this._xferId = _xferId;
    }

    /**
     * Getter for xferId
     * @return a org.sourceforge.ifx.framework.element.XferId
     */
    public org.sourceforge.ifx.framework.element.XferId getXferId() {
        return _xferId;
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
    private org.sourceforge.ifx.framework.element.RecXferMod _recXferMod;

    /** 
     * Setter for recXferMod
     * @param recXferMod the org.sourceforge.ifx.framework.element.RecXferMod to set
     */
    public void setRecXferMod(org.sourceforge.ifx.framework.element.RecXferMod _recXferMod) {
        this._recXferMod = _recXferMod;
    }

    /**
     * Getter for recXferMod
     * @return a org.sourceforge.ifx.framework.element.RecXferMod
     */
    public org.sourceforge.ifx.framework.element.RecXferMod getRecXferMod() {
        return _recXferMod;
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
    private org.sourceforge.ifx.framework.element.XferStatus _xferStatus;

    /** 
     * Setter for xferStatus
     * @param xferStatus the org.sourceforge.ifx.framework.element.XferStatus to set
     */
    public void setXferStatus(org.sourceforge.ifx.framework.element.XferStatus _xferStatus) {
        this._xferStatus = _xferStatus;
    }

    /**
     * Getter for xferStatus
     * @return a org.sourceforge.ifx.framework.element.XferStatus
     */
    public org.sourceforge.ifx.framework.element.XferStatus getXferStatus() {
        return _xferStatus;
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
       * XferId
       * RecXferId
       * RecXferMod
       * XferInfo
       * XferStatus
       */
    public final String[] ELEMENTS = {
              "XferId"
                 ,"RecXferId"
                 ,"RecXferMod"
                 ,"XferInfo"
                 ,"XferStatus"
          };
}
