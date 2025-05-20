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
public class PassbkStatusRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PassbkStatusRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PassbkId _passbkId;

    /** 
     * Setter for passbkId
     * @param passbkId the org.sourceforge.ifx.framework.element.PassbkId to set
     */
    public void setPassbkId(org.sourceforge.ifx.framework.element.PassbkId _passbkId) {
        this._passbkId = _passbkId;
    }

    /**
     * Getter for passbkId
     * @return a org.sourceforge.ifx.framework.element.PassbkId
     */
    public org.sourceforge.ifx.framework.element.PassbkId getPassbkId() {
        return _passbkId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PassbkStatus _passbkStatus;

    /** 
     * Setter for passbkStatus
     * @param passbkStatus the org.sourceforge.ifx.framework.element.PassbkStatus to set
     */
    public void setPassbkStatus(org.sourceforge.ifx.framework.element.PassbkStatus _passbkStatus) {
        this._passbkStatus = _passbkStatus;
    }

    /**
     * Getter for passbkStatus
     * @return a org.sourceforge.ifx.framework.element.PassbkStatus
     */
    public org.sourceforge.ifx.framework.element.PassbkStatus getPassbkStatus() {
        return _passbkStatus;
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
       * PassbkId
       * PassbkStatus
       */
    public final String[] ELEMENTS = {
              "PassbkId"
                 ,"PassbkStatus"
          };
}
