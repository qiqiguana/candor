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
public class PresAcctRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PresAcctRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PresAcctId _presAcctId;

    /** 
     * Setter for presAcctId
     * @param presAcctId the org.sourceforge.ifx.framework.element.PresAcctId to set
     */
    public void setPresAcctId(org.sourceforge.ifx.framework.element.PresAcctId _presAcctId) {
        this._presAcctId = _presAcctId;
    }

    /**
     * Getter for presAcctId
     * @return a org.sourceforge.ifx.framework.element.PresAcctId
     */
    public org.sourceforge.ifx.framework.element.PresAcctId getPresAcctId() {
        return _presAcctId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PresAcctInfo _presAcctInfo;

    /** 
     * Setter for presAcctInfo
     * @param presAcctInfo the org.sourceforge.ifx.framework.element.PresAcctInfo to set
     */
    public void setPresAcctInfo(org.sourceforge.ifx.framework.element.PresAcctInfo _presAcctInfo) {
        this._presAcctInfo = _presAcctInfo;
    }

    /**
     * Getter for presAcctInfo
     * @return a org.sourceforge.ifx.framework.element.PresAcctInfo
     */
    public org.sourceforge.ifx.framework.element.PresAcctInfo getPresAcctInfo() {
        return _presAcctInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PresAcctStatus _presAcctStatus;

    /** 
     * Setter for presAcctStatus
     * @param presAcctStatus the org.sourceforge.ifx.framework.element.PresAcctStatus to set
     */
    public void setPresAcctStatus(org.sourceforge.ifx.framework.element.PresAcctStatus _presAcctStatus) {
        this._presAcctStatus = _presAcctStatus;
    }

    /**
     * Getter for presAcctStatus
     * @return a org.sourceforge.ifx.framework.element.PresAcctStatus
     */
    public org.sourceforge.ifx.framework.element.PresAcctStatus getPresAcctStatus() {
        return _presAcctStatus;
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
       * PresAcctId
       * PresAcctInfo
       * PresAcctStatus
       */
    public final String[] ELEMENTS = {
              "PresAcctId"
                 ,"PresAcctInfo"
                 ,"PresAcctStatus"
          };
}
