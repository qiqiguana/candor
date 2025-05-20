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
public class ChkOrdMsgRqInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public ChkOrdMsgRqInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkOrdAddRq _chkOrdAddRq;

    /** 
     * Setter for chkOrdAddRq
     * @param chkOrdAddRq the org.sourceforge.ifx.framework.element.ChkOrdAddRq to set
     */
    public void setChkOrdAddRq(org.sourceforge.ifx.framework.element.ChkOrdAddRq _chkOrdAddRq) {
        this._chkOrdAddRq = _chkOrdAddRq;
    }

    /**
     * Getter for chkOrdAddRq
     * @return a org.sourceforge.ifx.framework.element.ChkOrdAddRq
     */
    public org.sourceforge.ifx.framework.element.ChkOrdAddRq getChkOrdAddRq() {
        return _chkOrdAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkOrdModRq _chkOrdModRq;

    /** 
     * Setter for chkOrdModRq
     * @param chkOrdModRq the org.sourceforge.ifx.framework.element.ChkOrdModRq to set
     */
    public void setChkOrdModRq(org.sourceforge.ifx.framework.element.ChkOrdModRq _chkOrdModRq) {
        this._chkOrdModRq = _chkOrdModRq;
    }

    /**
     * Getter for chkOrdModRq
     * @return a org.sourceforge.ifx.framework.element.ChkOrdModRq
     */
    public org.sourceforge.ifx.framework.element.ChkOrdModRq getChkOrdModRq() {
        return _chkOrdModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkOrdCanRq _chkOrdCanRq;

    /** 
     * Setter for chkOrdCanRq
     * @param chkOrdCanRq the org.sourceforge.ifx.framework.element.ChkOrdCanRq to set
     */
    public void setChkOrdCanRq(org.sourceforge.ifx.framework.element.ChkOrdCanRq _chkOrdCanRq) {
        this._chkOrdCanRq = _chkOrdCanRq;
    }

    /**
     * Getter for chkOrdCanRq
     * @return a org.sourceforge.ifx.framework.element.ChkOrdCanRq
     */
    public org.sourceforge.ifx.framework.element.ChkOrdCanRq getChkOrdCanRq() {
        return _chkOrdCanRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkOrdInqRq _chkOrdInqRq;

    /** 
     * Setter for chkOrdInqRq
     * @param chkOrdInqRq the org.sourceforge.ifx.framework.element.ChkOrdInqRq to set
     */
    public void setChkOrdInqRq(org.sourceforge.ifx.framework.element.ChkOrdInqRq _chkOrdInqRq) {
        this._chkOrdInqRq = _chkOrdInqRq;
    }

    /**
     * Getter for chkOrdInqRq
     * @return a org.sourceforge.ifx.framework.element.ChkOrdInqRq
     */
    public org.sourceforge.ifx.framework.element.ChkOrdInqRq getChkOrdInqRq() {
        return _chkOrdInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkOrdSyncRq _chkOrdSyncRq;

    /** 
     * Setter for chkOrdSyncRq
     * @param chkOrdSyncRq the org.sourceforge.ifx.framework.element.ChkOrdSyncRq to set
     */
    public void setChkOrdSyncRq(org.sourceforge.ifx.framework.element.ChkOrdSyncRq _chkOrdSyncRq) {
        this._chkOrdSyncRq = _chkOrdSyncRq;
    }

    /**
     * Getter for chkOrdSyncRq
     * @return a org.sourceforge.ifx.framework.element.ChkOrdSyncRq
     */
    public org.sourceforge.ifx.framework.element.ChkOrdSyncRq getChkOrdSyncRq() {
        return _chkOrdSyncRq;
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
       * ChkOrdAddRq
       * ChkOrdModRq
       * ChkOrdCanRq
       * ChkOrdInqRq
       * ChkOrdSyncRq
       */
    public final String[] ELEMENTS = {
              "ChkOrdAddRq"
                 ,"ChkOrdModRq"
                 ,"ChkOrdCanRq"
                 ,"ChkOrdInqRq"
                 ,"ChkOrdSyncRq"
          };
}
