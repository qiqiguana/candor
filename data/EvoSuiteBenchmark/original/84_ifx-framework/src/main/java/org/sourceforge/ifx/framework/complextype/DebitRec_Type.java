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
public class DebitRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DebitRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitId _debitId;

    /** 
     * Setter for debitId
     * @param debitId the org.sourceforge.ifx.framework.element.DebitId to set
     */
    public void setDebitId(org.sourceforge.ifx.framework.element.DebitId _debitId) {
        this._debitId = _debitId;
    }

    /**
     * Getter for debitId
     * @return a org.sourceforge.ifx.framework.element.DebitId
     */
    public org.sourceforge.ifx.framework.element.DebitId getDebitId() {
        return _debitId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitInfo _debitInfo;

    /** 
     * Setter for debitInfo
     * @param debitInfo the org.sourceforge.ifx.framework.element.DebitInfo to set
     */
    public void setDebitInfo(org.sourceforge.ifx.framework.element.DebitInfo _debitInfo) {
        this._debitInfo = _debitInfo;
    }

    /**
     * Getter for debitInfo
     * @return a org.sourceforge.ifx.framework.element.DebitInfo
     */
    public org.sourceforge.ifx.framework.element.DebitInfo getDebitInfo() {
        return _debitInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitStatus _debitStatus;

    /** 
     * Setter for debitStatus
     * @param debitStatus the org.sourceforge.ifx.framework.element.DebitStatus to set
     */
    public void setDebitStatus(org.sourceforge.ifx.framework.element.DebitStatus _debitStatus) {
        this._debitStatus = _debitStatus;
    }

    /**
     * Getter for debitStatus
     * @return a org.sourceforge.ifx.framework.element.DebitStatus
     */
    public org.sourceforge.ifx.framework.element.DebitStatus getDebitStatus() {
        return _debitStatus;
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
       * DebitId
       * DebitInfo
       * DebitStatus
       */
    public final String[] ELEMENTS = {
              "DebitId"
                 ,"DebitInfo"
                 ,"DebitStatus"
          };
}
