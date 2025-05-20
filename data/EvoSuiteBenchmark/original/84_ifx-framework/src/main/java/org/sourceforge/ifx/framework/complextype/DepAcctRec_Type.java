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
public class DepAcctRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DepAcctRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StopCount _stopCount;

    /** 
     * Setter for stopCount
     * @param stopCount the org.sourceforge.ifx.framework.element.StopCount to set
     */
    public void setStopCount(org.sourceforge.ifx.framework.element.StopCount _stopCount) {
        this._stopCount = _stopCount;
    }

    /**
     * Getter for stopCount
     * @return a org.sourceforge.ifx.framework.element.StopCount
     */
    public org.sourceforge.ifx.framework.element.StopCount getStopCount() {
        return _stopCount;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.HoldCount _holdCount;

    /** 
     * Setter for holdCount
     * @param holdCount the org.sourceforge.ifx.framework.element.HoldCount to set
     */
    public void setHoldCount(org.sourceforge.ifx.framework.element.HoldCount _holdCount) {
        this._holdCount = _holdCount;
    }

    /**
     * Getter for holdCount
     * @return a org.sourceforge.ifx.framework.element.HoldCount
     */
    public org.sourceforge.ifx.framework.element.HoldCount getHoldCount() {
        return _holdCount;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.LastDepDt _lastDepDt;

    /** 
     * Setter for lastDepDt
     * @param lastDepDt the org.sourceforge.ifx.framework.element.LastDepDt to set
     */
    public void setLastDepDt(org.sourceforge.ifx.framework.element.LastDepDt _lastDepDt) {
        this._lastDepDt = _lastDepDt;
    }

    /**
     * Getter for lastDepDt
     * @return a org.sourceforge.ifx.framework.element.LastDepDt
     */
    public org.sourceforge.ifx.framework.element.LastDepDt getLastDepDt() {
        return _lastDepDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.LastDepCurAmt _lastDepCurAmt;

    /** 
     * Setter for lastDepCurAmt
     * @param lastDepCurAmt the org.sourceforge.ifx.framework.element.LastDepCurAmt to set
     */
    public void setLastDepCurAmt(org.sourceforge.ifx.framework.element.LastDepCurAmt _lastDepCurAmt) {
        this._lastDepCurAmt = _lastDepCurAmt;
    }

    /**
     * Getter for lastDepCurAmt
     * @return a org.sourceforge.ifx.framework.element.LastDepCurAmt
     */
    public org.sourceforge.ifx.framework.element.LastDepCurAmt getLastDepCurAmt() {
        return _lastDepCurAmt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepMatureDt _depMatureDt;

    /** 
     * Setter for depMatureDt
     * @param depMatureDt the org.sourceforge.ifx.framework.element.DepMatureDt to set
     */
    public void setDepMatureDt(org.sourceforge.ifx.framework.element.DepMatureDt _depMatureDt) {
        this._depMatureDt = _depMatureDt;
    }

    /**
     * Getter for depMatureDt
     * @return a org.sourceforge.ifx.framework.element.DepMatureDt
     */
    public org.sourceforge.ifx.framework.element.DepMatureDt getDepMatureDt() {
        return _depMatureDt;
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
       * StopCount
       * HoldCount
       * LastDepDt
       * LastDepCurAmt
       * DepMatureDt
       */
    public final String[] ELEMENTS = {
              "StopCount"
                 ,"HoldCount"
                 ,"LastDepDt"
                 ,"LastDepCurAmt"
                 ,"DepMatureDt"
          };
}
