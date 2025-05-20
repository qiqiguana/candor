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
public class StdPayeeInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public StdPayeeInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Name _name;

    /** 
     * Setter for name
     * @param name the org.sourceforge.ifx.framework.element.Name to set
     */
    public void setName(org.sourceforge.ifx.framework.element.Name _name) {
        this._name = _name;
    }

    /**
     * Getter for name
     * @return a org.sourceforge.ifx.framework.element.Name
     */
    public org.sourceforge.ifx.framework.element.Name getName() {
        return _name;
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
    private org.sourceforge.ifx.framework.element.AcctMask[] _acctMask;

    /** 
     * Setter for acctMask
     * @param acctMask the org.sourceforge.ifx.framework.element.AcctMask[] to set
     */
    public void setAcctMask(org.sourceforge.ifx.framework.element.AcctMask[] _acctMask) {
        this._acctMask = _acctMask;
    }

    /**
     * Getter for acctMask
     * @return a org.sourceforge.ifx.framework.element.AcctMask[]
     */
    public org.sourceforge.ifx.framework.element.AcctMask[] getAcctMask() {
        return _acctMask;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.IndustId[] _industId;

    /** 
     * Setter for industId
     * @param industId the org.sourceforge.ifx.framework.element.IndustId[] to set
     */
    public void setIndustId(org.sourceforge.ifx.framework.element.IndustId[] _industId) {
        this._industId = _industId;
    }

    /**
     * Getter for industId
     * @return a org.sourceforge.ifx.framework.element.IndustId[]
     */
    public org.sourceforge.ifx.framework.element.IndustId[] getIndustId() {
        return _industId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DaysToPay[] _daysToPay;

    /** 
     * Setter for daysToPay
     * @param daysToPay the org.sourceforge.ifx.framework.element.DaysToPay[] to set
     */
    public void setDaysToPay(org.sourceforge.ifx.framework.element.DaysToPay[] _daysToPay) {
        this._daysToPay = _daysToPay;
    }

    /**
     * Getter for daysToPay
     * @return a org.sourceforge.ifx.framework.element.DaysToPay[]
     */
    public org.sourceforge.ifx.framework.element.DaysToPay[] getDaysToPay() {
        return _daysToPay;
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
       * Name
       * PostAddr
       * AcctMask
       * IndustId
       * DaysToPay
       */
    public final String[] ELEMENTS = {
              "Name"
                 ,"PostAddr"
                 ,"AcctMask"
                 ,"IndustId"
                 ,"DaysToPay"
          };
}
