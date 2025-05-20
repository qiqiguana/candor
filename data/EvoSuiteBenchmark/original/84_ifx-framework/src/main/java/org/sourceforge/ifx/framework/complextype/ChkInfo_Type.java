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
public class ChkInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public ChkInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkNum _chkNum;

    /** 
     * Setter for chkNum
     * @param chkNum the org.sourceforge.ifx.framework.element.ChkNum to set
     */
    public void setChkNum(org.sourceforge.ifx.framework.element.ChkNum _chkNum) {
        this._chkNum = _chkNum;
    }

    /**
     * Getter for chkNum
     * @return a org.sourceforge.ifx.framework.element.ChkNum
     */
    public org.sourceforge.ifx.framework.element.ChkNum getChkNum() {
        return _chkNum;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.OrigDt _origDt;

    /** 
     * Setter for origDt
     * @param origDt the org.sourceforge.ifx.framework.element.OrigDt to set
     */
    public void setOrigDt(org.sourceforge.ifx.framework.element.OrigDt _origDt) {
        this._origDt = _origDt;
    }

    /**
     * Getter for origDt
     * @return a org.sourceforge.ifx.framework.element.OrigDt
     */
    public org.sourceforge.ifx.framework.element.OrigDt getOrigDt() {
        return _origDt;
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
    private org.sourceforge.ifx.framework.element.OrgPhone _orgPhone;

    /** 
     * Setter for orgPhone
     * @param orgPhone the org.sourceforge.ifx.framework.element.OrgPhone to set
     */
    public void setOrgPhone(org.sourceforge.ifx.framework.element.OrgPhone _orgPhone) {
        this._orgPhone = _orgPhone;
    }

    /**
     * Getter for orgPhone
     * @return a org.sourceforge.ifx.framework.element.OrgPhone
     */
    public org.sourceforge.ifx.framework.element.OrgPhone getOrgPhone() {
        return _orgPhone;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Memo _memo;

    /** 
     * Setter for memo
     * @param memo the org.sourceforge.ifx.framework.element.Memo to set
     */
    public void setMemo(org.sourceforge.ifx.framework.element.Memo _memo) {
        this._memo = _memo;
    }

    /**
     * Getter for memo
     * @return a org.sourceforge.ifx.framework.element.Memo
     */
    public org.sourceforge.ifx.framework.element.Memo getMemo() {
        return _memo;
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
       * ChkNum
       * OrigDt
       * Name
       * PostAddr
       * OrgPhone
       * Memo
       */
    public final String[] ELEMENTS = {
              "ChkNum"
                 ,"OrigDt"
                 ,"Name"
                 ,"PostAddr"
                 ,"OrgPhone"
                 ,"Memo"
          };
}
