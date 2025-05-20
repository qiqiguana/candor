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
public class PrcSched_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PrcSched_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PrcDaysOff[] _prcDaysOff;

    /** 
     * Setter for prcDaysOff
     * @param prcDaysOff the org.sourceforge.ifx.framework.element.PrcDaysOff[] to set
     */
    public void setPrcDaysOff(org.sourceforge.ifx.framework.element.PrcDaysOff[] _prcDaysOff) {
        this._prcDaysOff = _prcDaysOff;
    }

    /**
     * Getter for prcDaysOff
     * @return a org.sourceforge.ifx.framework.element.PrcDaysOff[]
     */
    public org.sourceforge.ifx.framework.element.PrcDaysOff[] getPrcDaysOff() {
        return _prcDaysOff;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CutoffTm[] _cutoffTm;

    /** 
     * Setter for cutoffTm
     * @param cutoffTm the org.sourceforge.ifx.framework.element.CutoffTm[] to set
     */
    public void setCutoffTm(org.sourceforge.ifx.framework.element.CutoffTm[] _cutoffTm) {
        this._cutoffTm = _cutoffTm;
    }

    /**
     * Getter for cutoffTm
     * @return a org.sourceforge.ifx.framework.element.CutoffTm[]
     */
    public org.sourceforge.ifx.framework.element.CutoffTm[] getCutoffTm() {
        return _cutoffTm;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PrcDtAdj[] _prcDtAdj;

    /** 
     * Setter for prcDtAdj
     * @param prcDtAdj the org.sourceforge.ifx.framework.element.PrcDtAdj[] to set
     */
    public void setPrcDtAdj(org.sourceforge.ifx.framework.element.PrcDtAdj[] _prcDtAdj) {
        this._prcDtAdj = _prcDtAdj;
    }

    /**
     * Getter for prcDtAdj
     * @return a org.sourceforge.ifx.framework.element.PrcDtAdj[]
     */
    public org.sourceforge.ifx.framework.element.PrcDtAdj[] getPrcDtAdj() {
        return _prcDtAdj;
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
       * PrcDaysOff
       * CutoffTm
       * PrcDtAdj
       */
    public final String[] ELEMENTS = {
              "PrcDaysOff"
                 ,"CutoffTm"
                 ,"PrcDtAdj"
          };
}
