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
public class RecPmtMsgRqInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public RecPmtMsgRqInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecPmtAddRq _recPmtAddRq;

    /** 
     * Setter for recPmtAddRq
     * @param recPmtAddRq the org.sourceforge.ifx.framework.element.RecPmtAddRq to set
     */
    public void setRecPmtAddRq(org.sourceforge.ifx.framework.element.RecPmtAddRq _recPmtAddRq) {
        this._recPmtAddRq = _recPmtAddRq;
    }

    /**
     * Getter for recPmtAddRq
     * @return a org.sourceforge.ifx.framework.element.RecPmtAddRq
     */
    public org.sourceforge.ifx.framework.element.RecPmtAddRq getRecPmtAddRq() {
        return _recPmtAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecPmtModRq _recPmtModRq;

    /** 
     * Setter for recPmtModRq
     * @param recPmtModRq the org.sourceforge.ifx.framework.element.RecPmtModRq to set
     */
    public void setRecPmtModRq(org.sourceforge.ifx.framework.element.RecPmtModRq _recPmtModRq) {
        this._recPmtModRq = _recPmtModRq;
    }

    /**
     * Getter for recPmtModRq
     * @return a org.sourceforge.ifx.framework.element.RecPmtModRq
     */
    public org.sourceforge.ifx.framework.element.RecPmtModRq getRecPmtModRq() {
        return _recPmtModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecPmtCanRq _recPmtCanRq;

    /** 
     * Setter for recPmtCanRq
     * @param recPmtCanRq the org.sourceforge.ifx.framework.element.RecPmtCanRq to set
     */
    public void setRecPmtCanRq(org.sourceforge.ifx.framework.element.RecPmtCanRq _recPmtCanRq) {
        this._recPmtCanRq = _recPmtCanRq;
    }

    /**
     * Getter for recPmtCanRq
     * @return a org.sourceforge.ifx.framework.element.RecPmtCanRq
     */
    public org.sourceforge.ifx.framework.element.RecPmtCanRq getRecPmtCanRq() {
        return _recPmtCanRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecPmtInqRq _recPmtInqRq;

    /** 
     * Setter for recPmtInqRq
     * @param recPmtInqRq the org.sourceforge.ifx.framework.element.RecPmtInqRq to set
     */
    public void setRecPmtInqRq(org.sourceforge.ifx.framework.element.RecPmtInqRq _recPmtInqRq) {
        this._recPmtInqRq = _recPmtInqRq;
    }

    /**
     * Getter for recPmtInqRq
     * @return a org.sourceforge.ifx.framework.element.RecPmtInqRq
     */
    public org.sourceforge.ifx.framework.element.RecPmtInqRq getRecPmtInqRq() {
        return _recPmtInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecPmtAudRq _recPmtAudRq;

    /** 
     * Setter for recPmtAudRq
     * @param recPmtAudRq the org.sourceforge.ifx.framework.element.RecPmtAudRq to set
     */
    public void setRecPmtAudRq(org.sourceforge.ifx.framework.element.RecPmtAudRq _recPmtAudRq) {
        this._recPmtAudRq = _recPmtAudRq;
    }

    /**
     * Getter for recPmtAudRq
     * @return a org.sourceforge.ifx.framework.element.RecPmtAudRq
     */
    public org.sourceforge.ifx.framework.element.RecPmtAudRq getRecPmtAudRq() {
        return _recPmtAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RecPmtSyncRq _recPmtSyncRq;

    /** 
     * Setter for recPmtSyncRq
     * @param recPmtSyncRq the org.sourceforge.ifx.framework.element.RecPmtSyncRq to set
     */
    public void setRecPmtSyncRq(org.sourceforge.ifx.framework.element.RecPmtSyncRq _recPmtSyncRq) {
        this._recPmtSyncRq = _recPmtSyncRq;
    }

    /**
     * Getter for recPmtSyncRq
     * @return a org.sourceforge.ifx.framework.element.RecPmtSyncRq
     */
    public org.sourceforge.ifx.framework.element.RecPmtSyncRq getRecPmtSyncRq() {
        return _recPmtSyncRq;
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
       * RecPmtAddRq
       * RecPmtModRq
       * RecPmtCanRq
       * RecPmtInqRq
       * RecPmtAudRq
       * RecPmtSyncRq
       */
    public final String[] ELEMENTS = {
              "RecPmtAddRq"
                 ,"RecPmtModRq"
                 ,"RecPmtCanRq"
                 ,"RecPmtInqRq"
                 ,"RecPmtAudRq"
                 ,"RecPmtSyncRq"
          };
}
