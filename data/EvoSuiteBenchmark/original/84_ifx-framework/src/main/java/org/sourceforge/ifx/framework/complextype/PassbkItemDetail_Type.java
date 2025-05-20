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
public class PassbkItemDetail_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PassbkItemDetail_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PostedDt _postedDt;

    /** 
     * Setter for postedDt
     * @param postedDt the org.sourceforge.ifx.framework.element.PostedDt to set
     */
    public void setPostedDt(org.sourceforge.ifx.framework.element.PostedDt _postedDt) {
        this._postedDt = _postedDt;
    }

    /**
     * Getter for postedDt
     * @return a org.sourceforge.ifx.framework.element.PostedDt
     */
    public org.sourceforge.ifx.framework.element.PostedDt getPostedDt() {
        return _postedDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TrnType _trnType;

    /** 
     * Setter for trnType
     * @param trnType the org.sourceforge.ifx.framework.element.TrnType to set
     */
    public void setTrnType(org.sourceforge.ifx.framework.element.TrnType _trnType) {
        this._trnType = _trnType;
    }

    /**
     * Getter for trnType
     * @return a org.sourceforge.ifx.framework.element.TrnType
     */
    public org.sourceforge.ifx.framework.element.TrnType getTrnType() {
        return _trnType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CompositeCurAmt[] _compositeCurAmt;

    /** 
     * Setter for compositeCurAmt
     * @param compositeCurAmt the org.sourceforge.ifx.framework.element.CompositeCurAmt[] to set
     */
    public void setCompositeCurAmt(org.sourceforge.ifx.framework.element.CompositeCurAmt[] _compositeCurAmt) {
        this._compositeCurAmt = _compositeCurAmt;
    }

    /**
     * Getter for compositeCurAmt
     * @return a org.sourceforge.ifx.framework.element.CompositeCurAmt[]
     */
    public org.sourceforge.ifx.framework.element.CompositeCurAmt[] getCompositeCurAmt() {
        return _compositeCurAmt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BalAmt[] _balAmt;

    /** 
     * Setter for balAmt
     * @param balAmt the org.sourceforge.ifx.framework.element.BalAmt[] to set
     */
    public void setBalAmt(org.sourceforge.ifx.framework.element.BalAmt[] _balAmt) {
        this._balAmt = _balAmt;
    }

    /**
     * Getter for balAmt
     * @return a org.sourceforge.ifx.framework.element.BalAmt[]
     */
    public org.sourceforge.ifx.framework.element.BalAmt[] getBalAmt() {
        return _balAmt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Memo[] _memo;

    /** 
     * Setter for memo
     * @param memo the org.sourceforge.ifx.framework.element.Memo[] to set
     */
    public void setMemo(org.sourceforge.ifx.framework.element.Memo[] _memo) {
        this._memo = _memo;
    }

    /**
     * Getter for memo
     * @return a org.sourceforge.ifx.framework.element.Memo[]
     */
    public org.sourceforge.ifx.framework.element.Memo[] getMemo() {
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
       * PostedDt
       * TrnType
       * CompositeCurAmt
       * BalAmt
       * Memo
       */
    public final String[] ELEMENTS = {
              "PostedDt"
                 ,"TrnType"
                 ,"CompositeCurAmt"
                 ,"BalAmt"
                 ,"Memo"
          };
}
