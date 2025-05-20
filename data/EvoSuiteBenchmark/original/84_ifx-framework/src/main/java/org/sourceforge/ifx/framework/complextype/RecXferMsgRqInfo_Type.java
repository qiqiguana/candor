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
public class RecXferMsgRqInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public RecXferMsgRqInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecXferAddRq _recXferAddRq;

    /** 
     * Setter for recXferAddRq
     * @param recXferAddRq the org.sourceforge.ifx.framework.element.RecXferAddRq to set
     */
    public void setRecXferAddRq(org.sourceforge.ifx.framework.element.RecXferAddRq _recXferAddRq) {
        this._recXferAddRq = _recXferAddRq;
    }

    /**
     * Getter for recXferAddRq
     * @return a org.sourceforge.ifx.framework.element.RecXferAddRq
     */
    public org.sourceforge.ifx.framework.element.RecXferAddRq getRecXferAddRq() {
        return _recXferAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecXferModRq _recXferModRq;

    /** 
     * Setter for recXferModRq
     * @param recXferModRq the org.sourceforge.ifx.framework.element.RecXferModRq to set
     */
    public void setRecXferModRq(org.sourceforge.ifx.framework.element.RecXferModRq _recXferModRq) {
        this._recXferModRq = _recXferModRq;
    }

    /**
     * Getter for recXferModRq
     * @return a org.sourceforge.ifx.framework.element.RecXferModRq
     */
    public org.sourceforge.ifx.framework.element.RecXferModRq getRecXferModRq() {
        return _recXferModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecXferCanRq _recXferCanRq;

    /** 
     * Setter for recXferCanRq
     * @param recXferCanRq the org.sourceforge.ifx.framework.element.RecXferCanRq to set
     */
    public void setRecXferCanRq(org.sourceforge.ifx.framework.element.RecXferCanRq _recXferCanRq) {
        this._recXferCanRq = _recXferCanRq;
    }

    /**
     * Getter for recXferCanRq
     * @return a org.sourceforge.ifx.framework.element.RecXferCanRq
     */
    public org.sourceforge.ifx.framework.element.RecXferCanRq getRecXferCanRq() {
        return _recXferCanRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecXferInqRq _recXferInqRq;

    /** 
     * Setter for recXferInqRq
     * @param recXferInqRq the org.sourceforge.ifx.framework.element.RecXferInqRq to set
     */
    public void setRecXferInqRq(org.sourceforge.ifx.framework.element.RecXferInqRq _recXferInqRq) {
        this._recXferInqRq = _recXferInqRq;
    }

    /**
     * Getter for recXferInqRq
     * @return a org.sourceforge.ifx.framework.element.RecXferInqRq
     */
    public org.sourceforge.ifx.framework.element.RecXferInqRq getRecXferInqRq() {
        return _recXferInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecXferAudRq _recXferAudRq;

    /** 
     * Setter for recXferAudRq
     * @param recXferAudRq the org.sourceforge.ifx.framework.element.RecXferAudRq to set
     */
    public void setRecXferAudRq(org.sourceforge.ifx.framework.element.RecXferAudRq _recXferAudRq) {
        this._recXferAudRq = _recXferAudRq;
    }

    /**
     * Getter for recXferAudRq
     * @return a org.sourceforge.ifx.framework.element.RecXferAudRq
     */
    public org.sourceforge.ifx.framework.element.RecXferAudRq getRecXferAudRq() {
        return _recXferAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecXferSyncRq _recXferSyncRq;

    /** 
     * Setter for recXferSyncRq
     * @param recXferSyncRq the org.sourceforge.ifx.framework.element.RecXferSyncRq to set
     */
    public void setRecXferSyncRq(org.sourceforge.ifx.framework.element.RecXferSyncRq _recXferSyncRq) {
        this._recXferSyncRq = _recXferSyncRq;
    }

    /**
     * Getter for recXferSyncRq
     * @return a org.sourceforge.ifx.framework.element.RecXferSyncRq
     */
    public org.sourceforge.ifx.framework.element.RecXferSyncRq getRecXferSyncRq() {
        return _recXferSyncRq;
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
       * RecXferAddRq
       * RecXferModRq
       * RecXferCanRq
       * RecXferInqRq
       * RecXferAudRq
       * RecXferSyncRq
       */
    public final String[] ELEMENTS = {
              "RecXferAddRq"
                 ,"RecXferModRq"
                 ,"RecXferCanRq"
                 ,"RecXferInqRq"
                 ,"RecXferAudRq"
                 ,"RecXferSyncRq"
          };
}
