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
public class StructuredRemittanceInformation2
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public StructuredRemittanceInformation2() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain001.simpletype.DocumentType1Code _documentType1Code;

    /** 
     * Setter for documentType1Code
     * @param documentType1Code the org.sourceforge.ifx.framework.pain001.simpletype.DocumentType1Code to set
     */
    public void setDocumentType1Code(org.sourceforge.ifx.framework.pain001.simpletype.DocumentType1Code _documentType1Code) {
        this._documentType1Code = _documentType1Code;
    }

    /**
     * Getter for documentType1Code
     * @return a org.sourceforge.ifx.framework.pain001.simpletype.DocumentType1Code
     */
    public org.sourceforge.ifx.framework.pain001.simpletype.DocumentType1Code getDocumentType1Code() {
        return _documentType1Code;
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
    private org.sourceforge.ifx.framework.pain001.complextype.ReferredDocumentAmount1Choice[] _referredDocumentAmount1Choice;

    /** 
     * Setter for referredDocumentAmount1Choice
     * @param referredDocumentAmount1Choice the org.sourceforge.ifx.framework.pain001.complextype.ReferredDocumentAmount1Choice[] to set
     */
    public void setReferredDocumentAmount1Choice(org.sourceforge.ifx.framework.pain001.complextype.ReferredDocumentAmount1Choice[] _referredDocumentAmount1Choice) {
        this._referredDocumentAmount1Choice = _referredDocumentAmount1Choice;
    }

    /**
     * Getter for referredDocumentAmount1Choice
     * @return a org.sourceforge.ifx.framework.pain001.complextype.ReferredDocumentAmount1Choice[]
     */
    public org.sourceforge.ifx.framework.pain001.complextype.ReferredDocumentAmount1Choice[] getReferredDocumentAmount1Choice() {
        return _referredDocumentAmount1Choice;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.simpletype.Max35Text[] _max35Text;

    /** 
     * Setter for max35Text
     * @param max35Text the org.sourceforge.ifx.framework.pain004.simpletype.Max35Text[] to set
     */
    public void setMax35Text(org.sourceforge.ifx.framework.pain004.simpletype.Max35Text[] _max35Text) {
        this._max35Text = _max35Text;
    }

    /**
     * Getter for max35Text
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.Max35Text[]
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.Max35Text[] getMax35Text() {
        return _max35Text;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.PartyIdentification1[] _partyIdentification1;

    /** 
     * Setter for partyIdentification1
     * @param partyIdentification1 the org.sourceforge.ifx.framework.pain004.complextype.PartyIdentification1[] to set
     */
    public void setPartyIdentification1(org.sourceforge.ifx.framework.pain004.complextype.PartyIdentification1[] _partyIdentification1) {
        this._partyIdentification1 = _partyIdentification1;
    }

    /**
     * Getter for partyIdentification1
     * @return a org.sourceforge.ifx.framework.pain004.complextype.PartyIdentification1[]
     */
    public org.sourceforge.ifx.framework.pain004.complextype.PartyIdentification1[] getPartyIdentification1() {
        return _partyIdentification1;
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
       * DocumentType1Code
       * ISODate
       * ReferredDocumentAmount1Choice
       * Max35Text
       * PartyIdentification1
       */
    public final String[] ELEMENTS = {
              "DocumentType1Code"
                 ,"ISODate"
                 ,"ReferredDocumentAmount1Choice"
                 ,"Max35Text"
                 ,"PartyIdentification1"
          };
}
