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
public class SPRefIdCorrect_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public SPRefIdCorrect_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SPRefId _sPRefId;

    /** 
     * Setter for sPRefId
     * @param sPRefId the org.sourceforge.ifx.framework.element.SPRefId to set
     */
    public void setSPRefId(org.sourceforge.ifx.framework.element.SPRefId _sPRefId) {
        this._sPRefId = _sPRefId;
    }

    /**
     * Getter for sPRefId
     * @return a org.sourceforge.ifx.framework.element.SPRefId
     */
    public org.sourceforge.ifx.framework.element.SPRefId getSPRefId() {
        return _sPRefId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CorrectAction _correctAction;

    /** 
     * Setter for correctAction
     * @param correctAction the org.sourceforge.ifx.framework.element.CorrectAction to set
     */
    public void setCorrectAction(org.sourceforge.ifx.framework.element.CorrectAction _correctAction) {
        this._correctAction = _correctAction;
    }

    /**
     * Getter for correctAction
     * @return a org.sourceforge.ifx.framework.element.CorrectAction
     */
    public org.sourceforge.ifx.framework.element.CorrectAction getCorrectAction() {
        return _correctAction;
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
       * SPRefId
       * CorrectAction
       */
    public final String[] ELEMENTS = {
              "SPRefId"
                 ,"CorrectAction"
          };
}
