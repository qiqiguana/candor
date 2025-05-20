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
public class BillMsgRqInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public BillMsgRqInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillInqRq _billInqRq;

    /** 
     * Setter for billInqRq
     * @param billInqRq the org.sourceforge.ifx.framework.element.BillInqRq to set
     */
    public void setBillInqRq(org.sourceforge.ifx.framework.element.BillInqRq _billInqRq) {
        this._billInqRq = _billInqRq;
    }

    /**
     * Getter for billInqRq
     * @return a org.sourceforge.ifx.framework.element.BillInqRq
     */
    public org.sourceforge.ifx.framework.element.BillInqRq getBillInqRq() {
        return _billInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BillStatusModRq _billStatusModRq;

    /** 
     * Setter for billStatusModRq
     * @param billStatusModRq the org.sourceforge.ifx.framework.element.BillStatusModRq to set
     */
    public void setBillStatusModRq(org.sourceforge.ifx.framework.element.BillStatusModRq _billStatusModRq) {
        this._billStatusModRq = _billStatusModRq;
    }

    /**
     * Getter for billStatusModRq
     * @return a org.sourceforge.ifx.framework.element.BillStatusModRq
     */
    public org.sourceforge.ifx.framework.element.BillStatusModRq getBillStatusModRq() {
        return _billStatusModRq;
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
       * BillInqRq
       * BillStatusModRq
       */
    public final String[] ELEMENTS = {
              "BillInqRq"
                 ,"BillStatusModRq"
          };
}
