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
public class PayerInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PayerInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PersonInfo _personInfo;

    /** 
     * Setter for personInfo
     * @param personInfo the org.sourceforge.ifx.framework.element.PersonInfo to set
     */
    public void setPersonInfo(org.sourceforge.ifx.framework.element.PersonInfo _personInfo) {
        this._personInfo = _personInfo;
    }

    /**
     * Getter for personInfo
     * @return a org.sourceforge.ifx.framework.element.PersonInfo
     */
    public org.sourceforge.ifx.framework.element.PersonInfo getPersonInfo() {
        return _personInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.OrgInfo _orgInfo;

    /** 
     * Setter for orgInfo
     * @param orgInfo the org.sourceforge.ifx.framework.element.OrgInfo to set
     */
    public void setOrgInfo(org.sourceforge.ifx.framework.element.OrgInfo _orgInfo) {
        this._orgInfo = _orgInfo;
    }

    /**
     * Getter for orgInfo
     * @return a org.sourceforge.ifx.framework.element.OrgInfo
     */
    public org.sourceforge.ifx.framework.element.OrgInfo getOrgInfo() {
        return _orgInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.OrgId _orgId;

    /** 
     * Setter for orgId
     * @param orgId the org.sourceforge.ifx.framework.element.OrgId to set
     */
    public void setOrgId(org.sourceforge.ifx.framework.element.OrgId _orgId) {
        this._orgId = _orgId;
    }

    /**
     * Getter for orgId
     * @return a org.sourceforge.ifx.framework.element.OrgId
     */
    public org.sourceforge.ifx.framework.element.OrgId getOrgId() {
        return _orgId;
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
       * PersonInfo
       * OrgInfo
       * OrgId
       */
    public final String[] ELEMENTS = {
              "PersonInfo"
                 ,"OrgInfo"
                 ,"OrgId"
          };
}
