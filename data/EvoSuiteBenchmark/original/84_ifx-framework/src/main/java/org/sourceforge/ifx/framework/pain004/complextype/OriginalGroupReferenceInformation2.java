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
public class OriginalGroupReferenceInformation2
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public OriginalGroupReferenceInformation2() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.simpletype.Max35Text _max35Text;

    /** 
     * Setter for max35Text
     * @param max35Text the org.sourceforge.ifx.framework.pain004.simpletype.Max35Text to set
     */
    public void setMax35Text(org.sourceforge.ifx.framework.pain004.simpletype.Max35Text _max35Text) {
        this._max35Text = _max35Text;
    }

    /**
     * Getter for max35Text
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.Max35Text
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.Max35Text getMax35Text() {
        return _max35Text;
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


    /**
     * Returns true if objects are equal, false otherwise.
     * @param obj the object to compare with.
     * @return true if equal, false if not.
     */
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /** Element ordering - 
       * Max35Text
       * PaymentCancellationStatusCode
       * Max105Text
       */
    public final String[] ELEMENTS = {
              "Max35Text"
                 ,"PaymentCancellationStatusCode"
                 ,"Max105Text"
          };
}
