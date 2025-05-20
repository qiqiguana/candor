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
public class PresAcctInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PresAcctInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StdPayeeId _stdPayeeId;

    /** 
     * Setter for stdPayeeId
     * @param stdPayeeId the org.sourceforge.ifx.framework.element.StdPayeeId to set
     */
    public void setStdPayeeId(org.sourceforge.ifx.framework.element.StdPayeeId _stdPayeeId) {
        this._stdPayeeId = _stdPayeeId;
    }

    /**
     * Getter for stdPayeeId
     * @return a org.sourceforge.ifx.framework.element.StdPayeeId
     */
    public org.sourceforge.ifx.framework.element.StdPayeeId getStdPayeeId() {
        return _stdPayeeId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustPayeeId _custPayeeId;

    /** 
     * Setter for custPayeeId
     * @param custPayeeId the org.sourceforge.ifx.framework.element.CustPayeeId to set
     */
    public void setCustPayeeId(org.sourceforge.ifx.framework.element.CustPayeeId _custPayeeId) {
        this._custPayeeId = _custPayeeId;
    }

    /**
     * Getter for custPayeeId
     * @return a org.sourceforge.ifx.framework.element.CustPayeeId
     */
    public org.sourceforge.ifx.framework.element.CustPayeeId getCustPayeeId() {
        return _custPayeeId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SPName _sPName;

    /** 
     * Setter for sPName
     * @param sPName the org.sourceforge.ifx.framework.element.SPName to set
     */
    public void setSPName(org.sourceforge.ifx.framework.element.SPName _sPName) {
        this._sPName = _sPName;
    }

    /**
     * Getter for sPName
     * @return a org.sourceforge.ifx.framework.element.SPName
     */
    public org.sourceforge.ifx.framework.element.SPName getSPName() {
        return _sPName;
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
       * StdPayeeId
       * CustPayeeId
       * SPName
       */
    public final String[] ELEMENTS = {
              "StdPayeeId"
                 ,"CustPayeeId"
                 ,"SPName"
          };
}
