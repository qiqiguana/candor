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
public class CCAcctRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CCAcctRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DueDt _dueDt;

    /** 
     * Setter for dueDt
     * @param dueDt the org.sourceforge.ifx.framework.element.DueDt to set
     */
    public void setDueDt(org.sourceforge.ifx.framework.element.DueDt _dueDt) {
        this._dueDt = _dueDt;
    }

    /**
     * Getter for dueDt
     * @return a org.sourceforge.ifx.framework.element.DueDt
     */
    public org.sourceforge.ifx.framework.element.DueDt getDueDt() {
        return _dueDt;
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
    private org.sourceforge.ifx.framework.element.LastPmtDt _lastPmtDt;

    /** 
     * Setter for lastPmtDt
     * @param lastPmtDt the org.sourceforge.ifx.framework.element.LastPmtDt to set
     */
    public void setLastPmtDt(org.sourceforge.ifx.framework.element.LastPmtDt _lastPmtDt) {
        this._lastPmtDt = _lastPmtDt;
    }

    /**
     * Getter for lastPmtDt
     * @return a org.sourceforge.ifx.framework.element.LastPmtDt
     */
    public org.sourceforge.ifx.framework.element.LastPmtDt getLastPmtDt() {
        return _lastPmtDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.LastPmtCurAmt _lastPmtCurAmt;

    /** 
     * Setter for lastPmtCurAmt
     * @param lastPmtCurAmt the org.sourceforge.ifx.framework.element.LastPmtCurAmt to set
     */
    public void setLastPmtCurAmt(org.sourceforge.ifx.framework.element.LastPmtCurAmt _lastPmtCurAmt) {
        this._lastPmtCurAmt = _lastPmtCurAmt;
    }

    /**
     * Getter for lastPmtCurAmt
     * @return a org.sourceforge.ifx.framework.element.LastPmtCurAmt
     */
    public org.sourceforge.ifx.framework.element.LastPmtCurAmt getLastPmtCurAmt() {
        return _lastPmtCurAmt;
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
       * DueDt
       * ExpDt
       * LastPmtDt
       * LastPmtCurAmt
       */
    public final String[] ELEMENTS = {
              "DueDt"
                 ,"ExpDt"
                 ,"LastPmtDt"
                 ,"LastPmtCurAmt"
          };
}
