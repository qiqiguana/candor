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
public class PmtAuthMsgRqInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PmtAuthMsgRqInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtAuthAddRq _pmtAuthAddRq;

    /** 
     * Setter for pmtAuthAddRq
     * @param pmtAuthAddRq the org.sourceforge.ifx.framework.element.PmtAuthAddRq to set
     */
    public void setPmtAuthAddRq(org.sourceforge.ifx.framework.element.PmtAuthAddRq _pmtAuthAddRq) {
        this._pmtAuthAddRq = _pmtAuthAddRq;
    }

    /**
     * Getter for pmtAuthAddRq
     * @return a org.sourceforge.ifx.framework.element.PmtAuthAddRq
     */
    public org.sourceforge.ifx.framework.element.PmtAuthAddRq getPmtAuthAddRq() {
        return _pmtAuthAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtAuthModRq _pmtAuthModRq;

    /** 
     * Setter for pmtAuthModRq
     * @param pmtAuthModRq the org.sourceforge.ifx.framework.element.PmtAuthModRq to set
     */
    public void setPmtAuthModRq(org.sourceforge.ifx.framework.element.PmtAuthModRq _pmtAuthModRq) {
        this._pmtAuthModRq = _pmtAuthModRq;
    }

    /**
     * Getter for pmtAuthModRq
     * @return a org.sourceforge.ifx.framework.element.PmtAuthModRq
     */
    public org.sourceforge.ifx.framework.element.PmtAuthModRq getPmtAuthModRq() {
        return _pmtAuthModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtAuthCanRq _pmtAuthCanRq;

    /** 
     * Setter for pmtAuthCanRq
     * @param pmtAuthCanRq the org.sourceforge.ifx.framework.element.PmtAuthCanRq to set
     */
    public void setPmtAuthCanRq(org.sourceforge.ifx.framework.element.PmtAuthCanRq _pmtAuthCanRq) {
        this._pmtAuthCanRq = _pmtAuthCanRq;
    }

    /**
     * Getter for pmtAuthCanRq
     * @return a org.sourceforge.ifx.framework.element.PmtAuthCanRq
     */
    public org.sourceforge.ifx.framework.element.PmtAuthCanRq getPmtAuthCanRq() {
        return _pmtAuthCanRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtAuthInqRq _pmtAuthInqRq;

    /** 
     * Setter for pmtAuthInqRq
     * @param pmtAuthInqRq the org.sourceforge.ifx.framework.element.PmtAuthInqRq to set
     */
    public void setPmtAuthInqRq(org.sourceforge.ifx.framework.element.PmtAuthInqRq _pmtAuthInqRq) {
        this._pmtAuthInqRq = _pmtAuthInqRq;
    }

    /**
     * Getter for pmtAuthInqRq
     * @return a org.sourceforge.ifx.framework.element.PmtAuthInqRq
     */
    public org.sourceforge.ifx.framework.element.PmtAuthInqRq getPmtAuthInqRq() {
        return _pmtAuthInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtAuthAudRq _pmtAuthAudRq;

    /** 
     * Setter for pmtAuthAudRq
     * @param pmtAuthAudRq the org.sourceforge.ifx.framework.element.PmtAuthAudRq to set
     */
    public void setPmtAuthAudRq(org.sourceforge.ifx.framework.element.PmtAuthAudRq _pmtAuthAudRq) {
        this._pmtAuthAudRq = _pmtAuthAudRq;
    }

    /**
     * Getter for pmtAuthAudRq
     * @return a org.sourceforge.ifx.framework.element.PmtAuthAudRq
     */
    public org.sourceforge.ifx.framework.element.PmtAuthAudRq getPmtAuthAudRq() {
        return _pmtAuthAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtAuthSyncRq _pmtAuthSyncRq;

    /** 
     * Setter for pmtAuthSyncRq
     * @param pmtAuthSyncRq the org.sourceforge.ifx.framework.element.PmtAuthSyncRq to set
     */
    public void setPmtAuthSyncRq(org.sourceforge.ifx.framework.element.PmtAuthSyncRq _pmtAuthSyncRq) {
        this._pmtAuthSyncRq = _pmtAuthSyncRq;
    }

    /**
     * Getter for pmtAuthSyncRq
     * @return a org.sourceforge.ifx.framework.element.PmtAuthSyncRq
     */
    public org.sourceforge.ifx.framework.element.PmtAuthSyncRq getPmtAuthSyncRq() {
        return _pmtAuthSyncRq;
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
       * PmtAuthAddRq
       * PmtAuthModRq
       * PmtAuthCanRq
       * PmtAuthInqRq
       * PmtAuthAudRq
       * PmtAuthSyncRq
       */
    public final String[] ELEMENTS = {
              "PmtAuthAddRq"
                 ,"PmtAuthModRq"
                 ,"PmtAuthCanRq"
                 ,"PmtAuthInqRq"
                 ,"PmtAuthAudRq"
                 ,"PmtAuthSyncRq"
          };
}
