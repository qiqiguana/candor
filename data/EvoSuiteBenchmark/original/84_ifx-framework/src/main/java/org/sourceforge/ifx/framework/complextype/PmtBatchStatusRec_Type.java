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
public class PmtBatchStatusRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PmtBatchStatusRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtBatchId _pmtBatchId;

    /** 
     * Setter for pmtBatchId
     * @param pmtBatchId the org.sourceforge.ifx.framework.element.PmtBatchId to set
     */
    public void setPmtBatchId(org.sourceforge.ifx.framework.element.PmtBatchId _pmtBatchId) {
        this._pmtBatchId = _pmtBatchId;
    }

    /**
     * Getter for pmtBatchId
     * @return a org.sourceforge.ifx.framework.element.PmtBatchId
     */
    public org.sourceforge.ifx.framework.element.PmtBatchId getPmtBatchId() {
        return _pmtBatchId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtBatchStatus _pmtBatchStatus;

    /** 
     * Setter for pmtBatchStatus
     * @param pmtBatchStatus the org.sourceforge.ifx.framework.element.PmtBatchStatus to set
     */
    public void setPmtBatchStatus(org.sourceforge.ifx.framework.element.PmtBatchStatus _pmtBatchStatus) {
        this._pmtBatchStatus = _pmtBatchStatus;
    }

    /**
     * Getter for pmtBatchStatus
     * @return a org.sourceforge.ifx.framework.element.PmtBatchStatus
     */
    public org.sourceforge.ifx.framework.element.PmtBatchStatus getPmtBatchStatus() {
        return _pmtBatchStatus;
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
       * PmtBatchId
       * PmtBatchStatus
       */
    public final String[] ELEMENTS = {
              "PmtBatchId"
                 ,"PmtBatchStatus"
          };
}
