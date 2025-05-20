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
public class SettlementPriorityChoice
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public SettlementPriorityChoice() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.simpletype.Priority2Code _priority2Code;

    /** 
     * Setter for priority2Code
     * @param priority2Code the org.sourceforge.ifx.framework.pain004.simpletype.Priority2Code to set
     */
    public void setPriority2Code(org.sourceforge.ifx.framework.pain004.simpletype.Priority2Code _priority2Code) {
        this._priority2Code = _priority2Code;
    }

    /**
     * Getter for priority2Code
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.Priority2Code
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.Priority2Code getPriority2Code() {
        return _priority2Code;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.PaymentSchemeChoice _paymentSchemeChoice;

    /** 
     * Setter for paymentSchemeChoice
     * @param paymentSchemeChoice the org.sourceforge.ifx.framework.pain004.complextype.PaymentSchemeChoice to set
     */
    public void setPaymentSchemeChoice(org.sourceforge.ifx.framework.pain004.complextype.PaymentSchemeChoice _paymentSchemeChoice) {
        this._paymentSchemeChoice = _paymentSchemeChoice;
    }

    /**
     * Getter for paymentSchemeChoice
     * @return a org.sourceforge.ifx.framework.pain004.complextype.PaymentSchemeChoice
     */
    public org.sourceforge.ifx.framework.pain004.complextype.PaymentSchemeChoice getPaymentSchemeChoice() {
        return _paymentSchemeChoice;
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
       * Priority2Code
       * PaymentSchemeChoice
       */
    public final String[] ELEMENTS = {
              "Priority2Code"
                 ,"PaymentSchemeChoice"
          };
}
