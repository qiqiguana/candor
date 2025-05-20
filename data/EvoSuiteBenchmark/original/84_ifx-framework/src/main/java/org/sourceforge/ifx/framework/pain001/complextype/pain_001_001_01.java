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
package org.sourceforge.ifx.framework.pain001.complextype;

/**
 * Generated code.
 * 
 * @author org.sourceforge.ifx.tools.CodeGenerator
 */
public class pain_001_001_01
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public pain_001_001_01() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain001.complextype.GroupInformation1 _groupInformation1;

    /** 
     * Setter for groupInformation1
     * @param groupInformation1 the org.sourceforge.ifx.framework.pain001.complextype.GroupInformation1 to set
     */
    public void setGroupInformation1(org.sourceforge.ifx.framework.pain001.complextype.GroupInformation1 _groupInformation1) {
        this._groupInformation1 = _groupInformation1;
    }

    /**
     * Getter for groupInformation1
     * @return a org.sourceforge.ifx.framework.pain001.complextype.GroupInformation1
     */
    public org.sourceforge.ifx.framework.pain001.complextype.GroupInformation1 getGroupInformation1() {
        return _groupInformation1;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain001.complextype.PaymentInformation6[] _paymentInformation6;

    /** 
     * Setter for paymentInformation6
     * @param paymentInformation6 the org.sourceforge.ifx.framework.pain001.complextype.PaymentInformation6[] to set
     */
    public void setPaymentInformation6(org.sourceforge.ifx.framework.pain001.complextype.PaymentInformation6[] _paymentInformation6) {
        this._paymentInformation6 = _paymentInformation6;
    }

    /**
     * Getter for paymentInformation6
     * @return a org.sourceforge.ifx.framework.pain001.complextype.PaymentInformation6[]
     */
    public org.sourceforge.ifx.framework.pain001.complextype.PaymentInformation6[] getPaymentInformation6() {
        return _paymentInformation6;
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
       * GroupInformation1
       * PaymentInformation6
       */
    public final String[] ELEMENTS = {
              "GroupInformation1"
                 ,"PaymentInformation6"
          };
}
