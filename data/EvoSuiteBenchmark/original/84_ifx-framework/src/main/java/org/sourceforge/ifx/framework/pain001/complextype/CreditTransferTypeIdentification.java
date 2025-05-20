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
public class CreditTransferTypeIdentification
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CreditTransferTypeIdentification() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.simpletype.CreditTransferType2Code _creditTransferType2Code;

    /** 
     * Setter for creditTransferType2Code
     * @param creditTransferType2Code the org.sourceforge.ifx.framework.pain004.simpletype.CreditTransferType2Code to set
     */
    public void setCreditTransferType2Code(org.sourceforge.ifx.framework.pain004.simpletype.CreditTransferType2Code _creditTransferType2Code) {
        this._creditTransferType2Code = _creditTransferType2Code;
    }

    /**
     * Getter for creditTransferType2Code
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.CreditTransferType2Code
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.CreditTransferType2Code getCreditTransferType2Code() {
        return _creditTransferType2Code;
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
    private org.sourceforge.ifx.framework.pain004.complextype.SettlementPriorityChoice _settlementPriorityChoice;

    /** 
     * Setter for settlementPriorityChoice
     * @param settlementPriorityChoice the org.sourceforge.ifx.framework.pain004.complextype.SettlementPriorityChoice to set
     */
    public void setSettlementPriorityChoice(org.sourceforge.ifx.framework.pain004.complextype.SettlementPriorityChoice _settlementPriorityChoice) {
        this._settlementPriorityChoice = _settlementPriorityChoice;
    }

    /**
     * Getter for settlementPriorityChoice
     * @return a org.sourceforge.ifx.framework.pain004.complextype.SettlementPriorityChoice
     */
    public org.sourceforge.ifx.framework.pain004.complextype.SettlementPriorityChoice getSettlementPriorityChoice() {
        return _settlementPriorityChoice;
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
       * CreditTransferType2Code
       * Max35Text
       * Priority2Code
       * SettlementPriorityChoice
       */
    public final String[] ELEMENTS = {
              "CreditTransferType2Code"
                 ,"Max35Text"
                 ,"Priority2Code"
                 ,"SettlementPriorityChoice"
          };
}
