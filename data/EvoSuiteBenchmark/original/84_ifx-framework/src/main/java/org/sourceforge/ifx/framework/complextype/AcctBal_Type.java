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
public class AcctBal_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public AcctBal_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BalType _balType;

    /** 
     * Setter for balType
     * @param balType the org.sourceforge.ifx.framework.element.BalType to set
     */
    public void setBalType(org.sourceforge.ifx.framework.element.BalType _balType) {
        this._balType = _balType;
    }

    /**
     * Getter for balType
     * @return a org.sourceforge.ifx.framework.element.BalType
     */
    public org.sourceforge.ifx.framework.element.BalType getBalType() {
        return _balType;
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
    private org.sourceforge.ifx.framework.element.EffDt _effDt;

    /** 
     * Setter for effDt
     * @param effDt the org.sourceforge.ifx.framework.element.EffDt to set
     */
    public void setEffDt(org.sourceforge.ifx.framework.element.EffDt _effDt) {
        this._effDt = _effDt;
    }

    /**
     * Getter for effDt
     * @return a org.sourceforge.ifx.framework.element.EffDt
     */
    public org.sourceforge.ifx.framework.element.EffDt getEffDt() {
        return _effDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ExpDt _expDt;

    /** 
     * Setter for expDt
     * @param expDt the org.sourceforge.ifx.framework.element.ExpDt to set
     */
    public void setExpDt(org.sourceforge.ifx.framework.element.ExpDt _expDt) {
        this._expDt = _expDt;
    }

    /**
     * Getter for expDt
     * @return a org.sourceforge.ifx.framework.element.ExpDt
     */
    public org.sourceforge.ifx.framework.element.ExpDt getExpDt() {
        return _expDt;
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
       * BalType
       * CurAmt
       * EffDt
       * ExpDt
       * Desc
       */
    public final String[] ELEMENTS = {
              "BalType"
                 ,"CurAmt"
                 ,"EffDt"
                 ,"ExpDt"
                 ,"Desc"
          };
}
