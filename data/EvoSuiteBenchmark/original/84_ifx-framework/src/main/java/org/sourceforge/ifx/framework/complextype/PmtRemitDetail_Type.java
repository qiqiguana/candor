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
public class PmtRemitDetail_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PmtRemitDetail_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtRemitRefId _pmtRemitRefId;

    /** 
     * Setter for pmtRemitRefId
     * @param pmtRemitRefId the org.sourceforge.ifx.framework.element.PmtRemitRefId to set
     */
    public void setPmtRemitRefId(org.sourceforge.ifx.framework.element.PmtRemitRefId _pmtRemitRefId) {
        this._pmtRemitRefId = _pmtRemitRefId;
    }

    /**
     * Getter for pmtRemitRefId
     * @return a org.sourceforge.ifx.framework.element.PmtRemitRefId
     */
    public org.sourceforge.ifx.framework.element.PmtRemitRefId getPmtRemitRefId() {
        return _pmtRemitRefId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CurAmt _curAmt;

    /** 
     * Setter for curAmt
     * @param curAmt the org.sourceforge.ifx.framework.element.CurAmt to set
     */
    public void setCurAmt(org.sourceforge.ifx.framework.element.CurAmt _curAmt) {
        this._curAmt = _curAmt;
    }

    /**
     * Getter for curAmt
     * @return a org.sourceforge.ifx.framework.element.CurAmt
     */
    public org.sourceforge.ifx.framework.element.CurAmt getCurAmt() {
        return _curAmt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtRemitStatus _pmtRemitStatus;

    /** 
     * Setter for pmtRemitStatus
     * @param pmtRemitStatus the org.sourceforge.ifx.framework.element.PmtRemitStatus to set
     */
    public void setPmtRemitStatus(org.sourceforge.ifx.framework.element.PmtRemitStatus _pmtRemitStatus) {
        this._pmtRemitStatus = _pmtRemitStatus;
    }

    /**
     * Getter for pmtRemitStatus
     * @return a org.sourceforge.ifx.framework.element.PmtRemitStatus
     */
    public org.sourceforge.ifx.framework.element.PmtRemitStatus getPmtRemitStatus() {
        return _pmtRemitStatus;
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
       * PmtRemitRefId
       * CurAmt
       * PmtRemitStatus
       */
    public final String[] ELEMENTS = {
              "PmtRemitRefId"
                 ,"CurAmt"
                 ,"PmtRemitStatus"
          };
}
