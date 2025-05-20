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
public class IntRateInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public IntRateInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Rate _rate;

    /** 
     * Setter for rate
     * @param rate the org.sourceforge.ifx.framework.element.Rate to set
     */
    public void setRate(org.sourceforge.ifx.framework.element.Rate _rate) {
        this._rate = _rate;
    }

    /**
     * Getter for rate
     * @return a org.sourceforge.ifx.framework.element.Rate
     */
    public org.sourceforge.ifx.framework.element.Rate getRate() {
        return _rate;
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
    private org.sourceforge.ifx.framework.element.IntAPY _intAPY;

    /** 
     * Setter for intAPY
     * @param intAPY the org.sourceforge.ifx.framework.element.IntAPY to set
     */
    public void setIntAPY(org.sourceforge.ifx.framework.element.IntAPY _intAPY) {
        this._intAPY = _intAPY;
    }

    /**
     * Getter for intAPY
     * @return a org.sourceforge.ifx.framework.element.IntAPY
     */
    public org.sourceforge.ifx.framework.element.IntAPY getIntAPY() {
        return _intAPY;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Term _term;

    /** 
     * Setter for term
     * @param term the org.sourceforge.ifx.framework.element.Term to set
     */
    public void setTerm(org.sourceforge.ifx.framework.element.Term _term) {
        this._term = _term;
    }

    /**
     * Getter for term
     * @return a org.sourceforge.ifx.framework.element.Term
     */
    public org.sourceforge.ifx.framework.element.Term getTerm() {
        return _term;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.LowCurAmt _lowCurAmt;

    /** 
     * Setter for lowCurAmt
     * @param lowCurAmt the org.sourceforge.ifx.framework.element.LowCurAmt to set
     */
    public void setLowCurAmt(org.sourceforge.ifx.framework.element.LowCurAmt _lowCurAmt) {
        this._lowCurAmt = _lowCurAmt;
    }

    /**
     * Getter for lowCurAmt
     * @return a org.sourceforge.ifx.framework.element.LowCurAmt
     */
    public org.sourceforge.ifx.framework.element.LowCurAmt getLowCurAmt() {
        return _lowCurAmt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.HighCurAmt _highCurAmt;

    /** 
     * Setter for highCurAmt
     * @param highCurAmt the org.sourceforge.ifx.framework.element.HighCurAmt to set
     */
    public void setHighCurAmt(org.sourceforge.ifx.framework.element.HighCurAmt _highCurAmt) {
        this._highCurAmt = _highCurAmt;
    }

    /**
     * Getter for highCurAmt
     * @return a org.sourceforge.ifx.framework.element.HighCurAmt
     */
    public org.sourceforge.ifx.framework.element.HighCurAmt getHighCurAmt() {
        return _highCurAmt;
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
       * Rate
       * Desc
       * IntAPY
       * Term
       * LowCurAmt
       * HighCurAmt
       */
    public final String[] ELEMENTS = {
              "Rate"
                 ,"Desc"
                 ,"IntAPY"
                 ,"Term"
                 ,"LowCurAmt"
                 ,"HighCurAmt"
          };
}
