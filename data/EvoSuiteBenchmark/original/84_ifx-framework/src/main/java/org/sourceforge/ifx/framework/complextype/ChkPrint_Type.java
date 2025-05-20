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
public class ChkPrint_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public ChkPrint_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustName[] _custName;

    /** 
     * Setter for custName
     * @param custName the org.sourceforge.ifx.framework.element.CustName[] to set
     */
    public void setCustName(org.sourceforge.ifx.framework.element.CustName[] _custName) {
        this._custName = _custName;
    }

    /**
     * Getter for custName
     * @return a org.sourceforge.ifx.framework.element.CustName[]
     */
    public org.sourceforge.ifx.framework.element.CustName[] getCustName() {
        return _custName;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PersonName[] _personName;

    /** 
     * Setter for personName
     * @param personName the org.sourceforge.ifx.framework.element.PersonName[] to set
     */
    public void setPersonName(org.sourceforge.ifx.framework.element.PersonName[] _personName) {
        this._personName = _personName;
    }

    /**
     * Getter for personName
     * @return a org.sourceforge.ifx.framework.element.PersonName[]
     */
    public org.sourceforge.ifx.framework.element.PersonName[] getPersonName() {
        return _personName;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PostAddr _postAddr;

    /** 
     * Setter for postAddr
     * @param postAddr the org.sourceforge.ifx.framework.element.PostAddr to set
     */
    public void setPostAddr(org.sourceforge.ifx.framework.element.PostAddr _postAddr) {
        this._postAddr = _postAddr;
    }

    /**
     * Getter for postAddr
     * @return a org.sourceforge.ifx.framework.element.PostAddr
     */
    public org.sourceforge.ifx.framework.element.PostAddr getPostAddr() {
        return _postAddr;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Phone _phone;

    /** 
     * Setter for phone
     * @param phone the org.sourceforge.ifx.framework.element.Phone to set
     */
    public void setPhone(org.sourceforge.ifx.framework.element.Phone _phone) {
        this._phone = _phone;
    }

    /**
     * Getter for phone
     * @return a org.sourceforge.ifx.framework.element.Phone
     */
    public org.sourceforge.ifx.framework.element.Phone getPhone() {
        return _phone;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Desc _desc;

    /** 
     * Setter for desc
     * @param desc the org.sourceforge.ifx.framework.element.Desc to set
     */
    public void setDesc(org.sourceforge.ifx.framework.element.Desc _desc) {
        this._desc = _desc;
    }

    /**
     * Getter for desc
     * @return a org.sourceforge.ifx.framework.element.Desc
     */
    public org.sourceforge.ifx.framework.element.Desc getDesc() {
        return _desc;
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
       * CustName
       * PersonName
       * PostAddr
       * Phone
       * Desc
       */
    public final String[] ELEMENTS = {
              "CustName"
                 ,"PersonName"
                 ,"PostAddr"
                 ,"Phone"
                 ,"Desc"
          };
}
