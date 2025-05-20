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
public class PmtEnclPayeeInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PmtEnclPayeeInfo_Type() {
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
    private org.sourceforge.ifx.framework.element.PmtEnclPayee _pmtEnclPayee;

    /** 
     * Setter for pmtEnclPayee
     * @param pmtEnclPayee the org.sourceforge.ifx.framework.element.PmtEnclPayee to set
     */
    public void setPmtEnclPayee(org.sourceforge.ifx.framework.element.PmtEnclPayee _pmtEnclPayee) {
        this._pmtEnclPayee = _pmtEnclPayee;
    }

    /**
     * Getter for pmtEnclPayee
     * @return a org.sourceforge.ifx.framework.element.PmtEnclPayee
     */
    public org.sourceforge.ifx.framework.element.PmtEnclPayee getPmtEnclPayee() {
        return _pmtEnclPayee;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.IndustId _industId;

    /** 
     * Setter for industId
     * @param industId the org.sourceforge.ifx.framework.element.IndustId to set
     */
    public void setIndustId(org.sourceforge.ifx.framework.element.IndustId _industId) {
        this._industId = _industId;
    }

    /**
     * Getter for industId
     * @return a org.sourceforge.ifx.framework.element.IndustId
     */
    public org.sourceforge.ifx.framework.element.IndustId getIndustId() {
        return _industId;
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
       * PmtEnclPayee
       * IndustId
       */
    public final String[] ELEMENTS = {
              "StdPayeeId"
                 ,"PmtEnclPayee"
                 ,"IndustId"
          };
}
