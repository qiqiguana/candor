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
package org.sourceforge.ifx.framework.pain004.complextype;

/**
 * Generated code.
 * 
 * @author org.sourceforge.ifx.tools.CodeGenerator
 */
public class pain_004_001_01_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public pain_004_001_01_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.GeneralInformation2 _generalInformation2;

    /** 
     * Setter for generalInformation2
     * @param generalInformation2 the org.sourceforge.ifx.framework.pain004.complextype.GeneralInformation2 to set
     */
    public void setGeneralInformation2(org.sourceforge.ifx.framework.pain004.complextype.GeneralInformation2 _generalInformation2) {
        this._generalInformation2 = _generalInformation2;
    }

    /**
     * Getter for generalInformation2
     * @return a org.sourceforge.ifx.framework.pain004.complextype.GeneralInformation2
     */
    public org.sourceforge.ifx.framework.pain004.complextype.GeneralInformation2 getGeneralInformation2() {
        return _generalInformation2;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.OriginalGroupReferenceInformation2 _originalGroupReferenceInformation2;

    /** 
     * Setter for originalGroupReferenceInformation2
     * @param originalGroupReferenceInformation2 the org.sourceforge.ifx.framework.pain004.complextype.OriginalGroupReferenceInformation2 to set
     */
    public void setOriginalGroupReferenceInformation2(org.sourceforge.ifx.framework.pain004.complextype.OriginalGroupReferenceInformation2 _originalGroupReferenceInformation2) {
        this._originalGroupReferenceInformation2 = _originalGroupReferenceInformation2;
    }

    /**
     * Getter for originalGroupReferenceInformation2
     * @return a org.sourceforge.ifx.framework.pain004.complextype.OriginalGroupReferenceInformation2
     */
    public org.sourceforge.ifx.framework.pain004.complextype.OriginalGroupReferenceInformation2 getOriginalGroupReferenceInformation2() {
        return _originalGroupReferenceInformation2;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.PaymentInformation12[] _paymentInformation12;

    /** 
     * Setter for paymentInformation12
     * @param paymentInformation12 the org.sourceforge.ifx.framework.pain004.complextype.PaymentInformation12[] to set
     */
    public void setPaymentInformation12(org.sourceforge.ifx.framework.pain004.complextype.PaymentInformation12[] _paymentInformation12) {
        this._paymentInformation12 = _paymentInformation12;
    }

    /**
     * Getter for paymentInformation12
     * @return a org.sourceforge.ifx.framework.pain004.complextype.PaymentInformation12[]
     */
    public org.sourceforge.ifx.framework.pain004.complextype.PaymentInformation12[] getPaymentInformation12() {
        return _paymentInformation12;
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
       * GeneralInformation2
       * OriginalGroupReferenceInformation2
       * PaymentInformation12
       */
    public final String[] ELEMENTS = {
              "GeneralInformation2"
                 ,"OriginalGroupReferenceInformation2"
                 ,"PaymentInformation12"
          };
}
