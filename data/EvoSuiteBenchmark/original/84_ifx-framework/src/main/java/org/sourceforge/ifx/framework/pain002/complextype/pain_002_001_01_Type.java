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
package org.sourceforge.ifx.framework.pain002.complextype;

/**
 * Generated code.
 * 
 * @author org.sourceforge.ifx.tools.CodeGenerator
 */
public class pain_002_001_01_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public pain_002_001_01_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain002.complextype.GeneralInformation1 _generalInformation1;

    /** 
     * Setter for generalInformation1
     * @param generalInformation1 the org.sourceforge.ifx.framework.pain002.complextype.GeneralInformation1 to set
     */
    public void setGeneralInformation1(org.sourceforge.ifx.framework.pain002.complextype.GeneralInformation1 _generalInformation1) {
        this._generalInformation1 = _generalInformation1;
    }

    /**
     * Getter for generalInformation1
     * @return a org.sourceforge.ifx.framework.pain002.complextype.GeneralInformation1
     */
    public org.sourceforge.ifx.framework.pain002.complextype.GeneralInformation1 getGeneralInformation1() {
        return _generalInformation1;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain002.complextype.OriginalGroupReferenceInformation1 _originalGroupReferenceInformation1;

    /** 
     * Setter for originalGroupReferenceInformation1
     * @param originalGroupReferenceInformation1 the org.sourceforge.ifx.framework.pain002.complextype.OriginalGroupReferenceInformation1 to set
     */
    public void setOriginalGroupReferenceInformation1(org.sourceforge.ifx.framework.pain002.complextype.OriginalGroupReferenceInformation1 _originalGroupReferenceInformation1) {
        this._originalGroupReferenceInformation1 = _originalGroupReferenceInformation1;
    }

    /**
     * Getter for originalGroupReferenceInformation1
     * @return a org.sourceforge.ifx.framework.pain002.complextype.OriginalGroupReferenceInformation1
     */
    public org.sourceforge.ifx.framework.pain002.complextype.OriginalGroupReferenceInformation1 getOriginalGroupReferenceInformation1() {
        return _originalGroupReferenceInformation1;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain002.complextype.PaymentInformation9[] _paymentInformation9;

    /** 
     * Setter for paymentInformation9
     * @param paymentInformation9 the org.sourceforge.ifx.framework.pain002.complextype.PaymentInformation9[] to set
     */
    public void setPaymentInformation9(org.sourceforge.ifx.framework.pain002.complextype.PaymentInformation9[] _paymentInformation9) {
        this._paymentInformation9 = _paymentInformation9;
    }

    /**
     * Getter for paymentInformation9
     * @return a org.sourceforge.ifx.framework.pain002.complextype.PaymentInformation9[]
     */
    public org.sourceforge.ifx.framework.pain002.complextype.PaymentInformation9[] getPaymentInformation9() {
        return _paymentInformation9;
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
       * GeneralInformation1
       * OriginalGroupReferenceInformation1
       * PaymentInformation9
       */
    public final String[] ELEMENTS = {
              "GeneralInformation1"
                 ,"OriginalGroupReferenceInformation1"
                 ,"PaymentInformation9"
          };
}
