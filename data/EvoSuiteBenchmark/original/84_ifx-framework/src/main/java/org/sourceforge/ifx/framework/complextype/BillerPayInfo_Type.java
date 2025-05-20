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
public class BillerPayInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public BillerPayInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtInst[] _pmtInst;

    /** 
     * Setter for pmtInst
     * @param pmtInst the org.sourceforge.ifx.framework.element.PmtInst[] to set
     */
    public void setPmtInst(org.sourceforge.ifx.framework.element.PmtInst[] _pmtInst) {
        this._pmtInst = _pmtInst;
    }

    /**
     * Getter for pmtInst
     * @return a org.sourceforge.ifx.framework.element.PmtInst[]
     */
    public org.sourceforge.ifx.framework.element.PmtInst[] getPmtInst() {
        return _pmtInst;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DaysToEPost[] _daysToEPost;

    /** 
     * Setter for daysToEPost
     * @param daysToEPost the org.sourceforge.ifx.framework.element.DaysToEPost[] to set
     */
    public void setDaysToEPost(org.sourceforge.ifx.framework.element.DaysToEPost[] _daysToEPost) {
        this._daysToEPost = _daysToEPost;
    }

    /**
     * Getter for daysToEPost
     * @return a org.sourceforge.ifx.framework.element.DaysToEPost[]
     */
    public org.sourceforge.ifx.framework.element.DaysToEPost[] getDaysToEPost() {
        return _daysToEPost;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PrenoteReqd[] _prenoteReqd;

    /** 
     * Setter for prenoteReqd
     * @param prenoteReqd the org.sourceforge.ifx.framework.element.PrenoteReqd[] to set
     */
    public void setPrenoteReqd(org.sourceforge.ifx.framework.element.PrenoteReqd[] _prenoteReqd) {
        this._prenoteReqd = _prenoteReqd;
    }

    /**
     * Getter for prenoteReqd
     * @return a org.sourceforge.ifx.framework.element.PrenoteReqd[]
     */
    public org.sourceforge.ifx.framework.element.PrenoteReqd[] getPrenoteReqd() {
        return _prenoteReqd;
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
       * PmtInst
       * DaysToEPost
       * PrenoteReqd
       */
    public final String[] ELEMENTS = {
              "PmtInst"
                 ,"DaysToEPost"
                 ,"PrenoteReqd"
          };
}
