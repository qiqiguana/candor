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
public class PaymentInformation9
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PaymentInformation9() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.simpletype.ISODate _iSODate;

    /** 
     * Setter for iSODate
     * @param iSODate the org.sourceforge.ifx.framework.pain004.simpletype.ISODate to set
     */
    public void setISODate(org.sourceforge.ifx.framework.pain004.simpletype.ISODate _iSODate) {
        this._iSODate = _iSODate;
    }

    /**
     * Getter for iSODate
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.ISODate
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.ISODate getISODate() {
        return _iSODate;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.simpletype.PaymentMethod1Code _paymentMethod1Code;

    /** 
     * Setter for paymentMethod1Code
     * @param paymentMethod1Code the org.sourceforge.ifx.framework.pain004.simpletype.PaymentMethod1Code to set
     */
    public void setPaymentMethod1Code(org.sourceforge.ifx.framework.pain004.simpletype.PaymentMethod1Code _paymentMethod1Code) {
        this._paymentMethod1Code = _paymentMethod1Code;
    }

    /**
     * Getter for paymentMethod1Code
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.PaymentMethod1Code
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.PaymentMethod1Code getPaymentMethod1Code() {
        return _paymentMethod1Code;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.CreditTransferTypeIdentification _creditTransferTypeIdentification;

    /** 
     * Setter for creditTransferTypeIdentification
     * @param creditTransferTypeIdentification the org.sourceforge.ifx.framework.pain004.complextype.CreditTransferTypeIdentification to set
     */
    public void setCreditTransferTypeIdentification(org.sourceforge.ifx.framework.pain004.complextype.CreditTransferTypeIdentification _creditTransferTypeIdentification) {
        this._creditTransferTypeIdentification = _creditTransferTypeIdentification;
    }

    /**
     * Getter for creditTransferTypeIdentification
     * @return a org.sourceforge.ifx.framework.pain004.complextype.CreditTransferTypeIdentification
     */
    public org.sourceforge.ifx.framework.pain004.complextype.CreditTransferTypeIdentification getCreditTransferTypeIdentification() {
        return _creditTransferTypeIdentification;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.PartyIdentification1 _partyIdentification1;

    /** 
     * Setter for partyIdentification1
     * @param partyIdentification1 the org.sourceforge.ifx.framework.pain004.complextype.PartyIdentification1 to set
     */
    public void setPartyIdentification1(org.sourceforge.ifx.framework.pain004.complextype.PartyIdentification1 _partyIdentification1) {
        this._partyIdentification1 = _partyIdentification1;
    }

    /**
     * Getter for partyIdentification1
     * @return a org.sourceforge.ifx.framework.pain004.complextype.PartyIdentification1
     */
    public org.sourceforge.ifx.framework.pain004.complextype.PartyIdentification1 getPartyIdentification1() {
        return _partyIdentification1;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.CashAccount3 _cashAccount3;

    /** 
     * Setter for cashAccount3
     * @param cashAccount3 the org.sourceforge.ifx.framework.pain004.complextype.CashAccount3 to set
     */
    public void setCashAccount3(org.sourceforge.ifx.framework.pain004.complextype.CashAccount3 _cashAccount3) {
        this._cashAccount3 = _cashAccount3;
    }

    /**
     * Getter for cashAccount3
     * @return a org.sourceforge.ifx.framework.pain004.complextype.CashAccount3
     */
    public org.sourceforge.ifx.framework.pain004.complextype.CashAccount3 getCashAccount3() {
        return _cashAccount3;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain002.complextype.PaymentReference1[] _paymentReference1;

    /** 
     * Setter for paymentReference1
     * @param paymentReference1 the org.sourceforge.ifx.framework.pain002.complextype.PaymentReference1[] to set
     */
    public void setPaymentReference1(org.sourceforge.ifx.framework.pain002.complextype.PaymentReference1[] _paymentReference1) {
        this._paymentReference1 = _paymentReference1;
    }

    /**
     * Getter for paymentReference1
     * @return a org.sourceforge.ifx.framework.pain002.complextype.PaymentReference1[]
     */
    public org.sourceforge.ifx.framework.pain002.complextype.PaymentReference1[] getPaymentReference1() {
        return _paymentReference1;
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
       * ISODate
       * PaymentMethod1Code
       * CreditTransferTypeIdentification
       * PartyIdentification1
       * CashAccount3
       * PaymentReference1
       */
    public final String[] ELEMENTS = {
              "ISODate"
                 ,"PaymentMethod1Code"
                 ,"CreditTransferTypeIdentification"
                 ,"PartyIdentification1"
                 ,"CashAccount3"
                 ,"PaymentReference1"
          };
}
