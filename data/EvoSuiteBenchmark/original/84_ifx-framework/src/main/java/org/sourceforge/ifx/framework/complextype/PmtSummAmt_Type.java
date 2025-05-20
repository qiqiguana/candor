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
public class PmtSummAmt_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PmtSummAmt_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillSummAmtId _billSummAmtId;

    /** 
     * Setter for billSummAmtId
     * @param billSummAmtId the org.sourceforge.ifx.framework.element.BillSummAmtId to set
     */
    public void setBillSummAmtId(org.sourceforge.ifx.framework.element.BillSummAmtId _billSummAmtId) {
        this._billSummAmtId = _billSummAmtId;
    }

    /**
     * Getter for billSummAmtId
     * @return a org.sourceforge.ifx.framework.element.BillSummAmtId
     */
    public org.sourceforge.ifx.framework.element.BillSummAmtId getBillSummAmtId() {
        return _billSummAmtId;
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


    /**
     * Returns true if objects are equal, false otherwise.
     * @param obj the object to compare with.
     * @return true if equal, false if not.
     */
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /** Element ordering - 
       * BillSummAmtId
       * CurAmt
       */
    public final String[] ELEMENTS = {
              "BillSummAmtId"
                 ,"CurAmt"
          };
}
