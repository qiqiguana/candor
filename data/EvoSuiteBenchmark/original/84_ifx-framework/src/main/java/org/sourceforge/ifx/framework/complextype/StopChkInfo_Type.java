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
public class StopChkInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public StopChkInfo_Type() {
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
    private org.sourceforge.ifx.framework.element.CurAmt _curAmt;

    /** 
     * Setter for curAmt
     * @param curAmt the org.sourceforge.ifx.framework.element.CurAmt to set
     */
    public void setCurAmt(org.sourceforge.ifx.framework.element.CurAmt _curAmt) {
        this._curAmt = _curAmt;
    }

    /**
     * Getter for curAmt
     * @return a org.sourceforge.ifx.framework.element.CurAmt
     */
    public org.sourceforge.ifx.framework.element.CurAmt getCurAmt() {
        return _curAmt;
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

    // property declaration 
    private org.sourceforge.ifx.framework.element.BaseEnvr _baseEnvr;

    /** 
     * Setter for baseEnvr
     * @param baseEnvr the org.sourceforge.ifx.framework.element.BaseEnvr to set
     */
    public void setBaseEnvr(org.sourceforge.ifx.framework.element.BaseEnvr _baseEnvr) {
        this._baseEnvr = _baseEnvr;
    }

    /**
     * Getter for baseEnvr
     * @return a org.sourceforge.ifx.framework.element.BaseEnvr
     */
    public org.sourceforge.ifx.framework.element.BaseEnvr getBaseEnvr() {
        return _baseEnvr;
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
       * Name
       * CurAmt
       * OrigDt
       * Desc
       * BaseEnvr
       */
    public final String[] ELEMENTS = {
              "ChkNum"
                 ,"Name"
                 ,"CurAmt"
                 ,"OrigDt"
                 ,"Desc"
                 ,"BaseEnvr"
          };
}
