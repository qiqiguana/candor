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
public class SvcAcctRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public SvcAcctRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SvcAcctId _svcAcctId;

    /** 
     * Setter for svcAcctId
     * @param svcAcctId the org.sourceforge.ifx.framework.element.SvcAcctId to set
     */
    public void setSvcAcctId(org.sourceforge.ifx.framework.element.SvcAcctId _svcAcctId) {
        this._svcAcctId = _svcAcctId;
    }

    /**
     * Getter for svcAcctId
     * @return a org.sourceforge.ifx.framework.element.SvcAcctId
     */
    public org.sourceforge.ifx.framework.element.SvcAcctId getSvcAcctId() {
        return _svcAcctId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SvcAcctInfo _svcAcctInfo;

    /** 
     * Setter for svcAcctInfo
     * @param svcAcctInfo the org.sourceforge.ifx.framework.element.SvcAcctInfo to set
     */
    public void setSvcAcctInfo(org.sourceforge.ifx.framework.element.SvcAcctInfo _svcAcctInfo) {
        this._svcAcctInfo = _svcAcctInfo;
    }

    /**
     * Getter for svcAcctInfo
     * @return a org.sourceforge.ifx.framework.element.SvcAcctInfo
     */
    public org.sourceforge.ifx.framework.element.SvcAcctInfo getSvcAcctInfo() {
        return _svcAcctInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SvcAcctStatus _svcAcctStatus;

    /** 
     * Setter for svcAcctStatus
     * @param svcAcctStatus the org.sourceforge.ifx.framework.element.SvcAcctStatus to set
     */
    public void setSvcAcctStatus(org.sourceforge.ifx.framework.element.SvcAcctStatus _svcAcctStatus) {
        this._svcAcctStatus = _svcAcctStatus;
    }

    /**
     * Getter for svcAcctStatus
     * @return a org.sourceforge.ifx.framework.element.SvcAcctStatus
     */
    public org.sourceforge.ifx.framework.element.SvcAcctStatus getSvcAcctStatus() {
        return _svcAcctStatus;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.EffDt _effDt;

    /** 
     * Setter for effDt
     * @param effDt the org.sourceforge.ifx.framework.element.EffDt to set
     */
    public void setEffDt(org.sourceforge.ifx.framework.element.EffDt _effDt) {
        this._effDt = _effDt;
    }

    /**
     * Getter for effDt
     * @return a org.sourceforge.ifx.framework.element.EffDt
     */
    public org.sourceforge.ifx.framework.element.EffDt getEffDt() {
        return _effDt;
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
       * SvcAcctId
       * SvcAcctInfo
       * SvcAcctStatus
       * EffDt
       */
    public final String[] ELEMENTS = {
              "SvcAcctId"
                 ,"SvcAcctInfo"
                 ,"SvcAcctStatus"
                 ,"EffDt"
          };
}
