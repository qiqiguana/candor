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
public class PaymentReference2
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PaymentReference2() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.PaymentIdentification _paymentIdentification;

    /** 
     * Setter for paymentIdentification
     * @param paymentIdentification the org.sourceforge.ifx.framework.pain004.complextype.PaymentIdentification to set
     */
    public void setPaymentIdentification(org.sourceforge.ifx.framework.pain004.complextype.PaymentIdentification _paymentIdentification) {
        this._paymentIdentification = _paymentIdentification;
    }

    /**
     * Getter for paymentIdentification
     * @return a org.sourceforge.ifx.framework.pain004.complextype.PaymentIdentification
     */
    public org.sourceforge.ifx.framework.pain004.complextype.PaymentIdentification getPaymentIdentification() {
        return _paymentIdentification;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.simpletype.PaymentCancellationStatusCode _paymentCancellationStatusCode;

    /** 
     * Setter for paymentCancellationStatusCode
     * @param paymentCancellationStatusCode the org.sourceforge.ifx.framework.pain004.simpletype.PaymentCancellationStatusCode to set
     */
    public void setPaymentCancellationStatusCode(org.sourceforge.ifx.framework.pain004.simpletype.PaymentCancellationStatusCode _paymentCancellationStatusCode) {
        this._paymentCancellationStatusCode = _paymentCancellationStatusCode;
    }

    /**
     * Getter for paymentCancellationStatusCode
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.PaymentCancellationStatusCode
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.PaymentCancellationStatusCode getPaymentCancellationStatusCode() {
        return _paymentCancellationStatusCode;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.simpletype.Max105Text _max105Text;

    /** 
     * Setter for max105Text
     * @param max105Text the org.sourceforge.ifx.framework.pain004.simpletype.Max105Text to set
     */
    public void setMax105Text(org.sourceforge.ifx.framework.pain004.simpletype.Max105Text _max105Text) {
        this._max105Text = _max105Text;
    }

    /**
     * Getter for max105Text
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.Max105Text
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.Max105Text getMax105Text() {
        return _max105Text;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.OriginalTransactionInformation1 _originalTransactionInformation1;

    /** 
     * Setter for originalTransactionInformation1
     * @param originalTransactionInformation1 the org.sourceforge.ifx.framework.pain004.complextype.OriginalTransactionInformation1 to set
     */
    public void setOriginalTransactionInformation1(org.sourceforge.ifx.framework.pain004.complextype.OriginalTransactionInformation1 _originalTransactionInformation1) {
        this._originalTransactionInformation1 = _originalTransactionInformation1;
    }

    /**
     * Getter for originalTransactionInformation1
     * @return a org.sourceforge.ifx.framework.pain004.complextype.OriginalTransactionInformation1
     */
    public org.sourceforge.ifx.framework.pain004.complextype.OriginalTransactionInformation1 getOriginalTransactionInformation1() {
        return _originalTransactionInformation1;
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
       * PaymentIdentification
       * PaymentCancellationStatusCode
       * Max105Text
       * OriginalTransactionInformation1
       */
    public final String[] ELEMENTS = {
              "PaymentIdentification"
                 ,"PaymentCancellationStatusCode"
                 ,"Max105Text"
                 ,"OriginalTransactionInformation1"
          };
}
