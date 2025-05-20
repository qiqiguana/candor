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
public class Cheque2
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public Cheque2() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain001.simpletype.ChequeType2Code _chequeType2Code;

    /** 
     * Setter for chequeType2Code
     * @param chequeType2Code the org.sourceforge.ifx.framework.pain001.simpletype.ChequeType2Code to set
     */
    public void setChequeType2Code(org.sourceforge.ifx.framework.pain001.simpletype.ChequeType2Code _chequeType2Code) {
        this._chequeType2Code = _chequeType2Code;
    }

    /**
     * Getter for chequeType2Code
     * @return a org.sourceforge.ifx.framework.pain001.simpletype.ChequeType2Code
     */
    public org.sourceforge.ifx.framework.pain001.simpletype.ChequeType2Code getChequeType2Code() {
        return _chequeType2Code;
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
    private org.sourceforge.ifx.framework.pain001.complextype.NameAndAddress3 _nameAndAddress3;

    /** 
     * Setter for nameAndAddress3
     * @param nameAndAddress3 the org.sourceforge.ifx.framework.pain001.complextype.NameAndAddress3 to set
     */
    public void setNameAndAddress3(org.sourceforge.ifx.framework.pain001.complextype.NameAndAddress3 _nameAndAddress3) {
        this._nameAndAddress3 = _nameAndAddress3;
    }

    /**
     * Getter for nameAndAddress3
     * @return a org.sourceforge.ifx.framework.pain001.complextype.NameAndAddress3
     */
    public org.sourceforge.ifx.framework.pain001.complextype.NameAndAddress3 getNameAndAddress3() {
        return _nameAndAddress3;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain001.simpletype.ChequeDelivery1Code _chequeDelivery1Code;

    /** 
     * Setter for chequeDelivery1Code
     * @param chequeDelivery1Code the org.sourceforge.ifx.framework.pain001.simpletype.ChequeDelivery1Code to set
     */
    public void setChequeDelivery1Code(org.sourceforge.ifx.framework.pain001.simpletype.ChequeDelivery1Code _chequeDelivery1Code) {
        this._chequeDelivery1Code = _chequeDelivery1Code;
    }

    /**
     * Getter for chequeDelivery1Code
     * @return a org.sourceforge.ifx.framework.pain001.simpletype.ChequeDelivery1Code
     */
    public org.sourceforge.ifx.framework.pain001.simpletype.ChequeDelivery1Code getChequeDelivery1Code() {
        return _chequeDelivery1Code;
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


    /**
     * Returns true if objects are equal, false otherwise.
     * @param obj the object to compare with.
     * @return true if equal, false if not.
     */
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /** Element ordering - 
       * ChequeType2Code
       * Max35Text
       * NameAndAddress3
       * ChequeDelivery1Code
       * Priority2Code
       * ISODate
       */
    public final String[] ELEMENTS = {
              "ChequeType2Code"
                 ,"Max35Text"
                 ,"NameAndAddress3"
                 ,"ChequeDelivery1Code"
                 ,"Priority2Code"
                 ,"ISODate"
          };
}
