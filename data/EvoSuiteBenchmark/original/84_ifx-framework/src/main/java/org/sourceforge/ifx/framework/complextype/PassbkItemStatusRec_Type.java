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
public class PassbkItemStatusRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PassbkItemStatusRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PassbkItemId _passbkItemId;

    /** 
     * Setter for passbkItemId
     * @param passbkItemId the org.sourceforge.ifx.framework.element.PassbkItemId to set
     */
    public void setPassbkItemId(org.sourceforge.ifx.framework.element.PassbkItemId _passbkItemId) {
        this._passbkItemId = _passbkItemId;
    }

    /**
     * Getter for passbkItemId
     * @return a org.sourceforge.ifx.framework.element.PassbkItemId
     */
    public org.sourceforge.ifx.framework.element.PassbkItemId getPassbkItemId() {
        return _passbkItemId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PassbkItemStatus _passbkItemStatus;

    /** 
     * Setter for passbkItemStatus
     * @param passbkItemStatus the org.sourceforge.ifx.framework.element.PassbkItemStatus to set
     */
    public void setPassbkItemStatus(org.sourceforge.ifx.framework.element.PassbkItemStatus _passbkItemStatus) {
        this._passbkItemStatus = _passbkItemStatus;
    }

    /**
     * Getter for passbkItemStatus
     * @return a org.sourceforge.ifx.framework.element.PassbkItemStatus
     */
    public org.sourceforge.ifx.framework.element.PassbkItemStatus getPassbkItemStatus() {
        return _passbkItemStatus;
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
       * PassbkItemId
       * PassbkItemStatus
       */
    public final String[] ELEMENTS = {
              "PassbkItemId"
                 ,"PassbkItemStatus"
          };
}
