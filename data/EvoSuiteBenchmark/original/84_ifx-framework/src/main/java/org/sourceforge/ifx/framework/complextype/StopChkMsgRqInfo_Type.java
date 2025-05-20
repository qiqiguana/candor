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
public class StopChkMsgRqInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public StopChkMsgRqInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StopChkAddRq _stopChkAddRq;

    /** 
     * Setter for stopChkAddRq
     * @param stopChkAddRq the org.sourceforge.ifx.framework.element.StopChkAddRq to set
     */
    public void setStopChkAddRq(org.sourceforge.ifx.framework.element.StopChkAddRq _stopChkAddRq) {
        this._stopChkAddRq = _stopChkAddRq;
    }

    /**
     * Getter for stopChkAddRq
     * @return a org.sourceforge.ifx.framework.element.StopChkAddRq
     */
    public org.sourceforge.ifx.framework.element.StopChkAddRq getStopChkAddRq() {
        return _stopChkAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StopChkCanRq _stopChkCanRq;

    /** 
     * Setter for stopChkCanRq
     * @param stopChkCanRq the org.sourceforge.ifx.framework.element.StopChkCanRq to set
     */
    public void setStopChkCanRq(org.sourceforge.ifx.framework.element.StopChkCanRq _stopChkCanRq) {
        this._stopChkCanRq = _stopChkCanRq;
    }

    /**
     * Getter for stopChkCanRq
     * @return a org.sourceforge.ifx.framework.element.StopChkCanRq
     */
    public org.sourceforge.ifx.framework.element.StopChkCanRq getStopChkCanRq() {
        return _stopChkCanRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StopChkInqRq _stopChkInqRq;

    /** 
     * Setter for stopChkInqRq
     * @param stopChkInqRq the org.sourceforge.ifx.framework.element.StopChkInqRq to set
     */
    public void setStopChkInqRq(org.sourceforge.ifx.framework.element.StopChkInqRq _stopChkInqRq) {
        this._stopChkInqRq = _stopChkInqRq;
    }

    /**
     * Getter for stopChkInqRq
     * @return a org.sourceforge.ifx.framework.element.StopChkInqRq
     */
    public org.sourceforge.ifx.framework.element.StopChkInqRq getStopChkInqRq() {
        return _stopChkInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StopChkAudRq _stopChkAudRq;

    /** 
     * Setter for stopChkAudRq
     * @param stopChkAudRq the org.sourceforge.ifx.framework.element.StopChkAudRq to set
     */
    public void setStopChkAudRq(org.sourceforge.ifx.framework.element.StopChkAudRq _stopChkAudRq) {
        this._stopChkAudRq = _stopChkAudRq;
    }

    /**
     * Getter for stopChkAudRq
     * @return a org.sourceforge.ifx.framework.element.StopChkAudRq
     */
    public org.sourceforge.ifx.framework.element.StopChkAudRq getStopChkAudRq() {
        return _stopChkAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StopChkSyncRq _stopChkSyncRq;

    /** 
     * Setter for stopChkSyncRq
     * @param stopChkSyncRq the org.sourceforge.ifx.framework.element.StopChkSyncRq to set
     */
    public void setStopChkSyncRq(org.sourceforge.ifx.framework.element.StopChkSyncRq _stopChkSyncRq) {
        this._stopChkSyncRq = _stopChkSyncRq;
    }

    /**
     * Getter for stopChkSyncRq
     * @return a org.sourceforge.ifx.framework.element.StopChkSyncRq
     */
    public org.sourceforge.ifx.framework.element.StopChkSyncRq getStopChkSyncRq() {
        return _stopChkSyncRq;
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
       * StopChkAddRq
       * StopChkCanRq
       * StopChkInqRq
       * StopChkAudRq
       * StopChkSyncRq
       */
    public final String[] ELEMENTS = {
              "StopChkAddRq"
                 ,"StopChkCanRq"
                 ,"StopChkInqRq"
                 ,"StopChkAudRq"
                 ,"StopChkSyncRq"
          };
}
