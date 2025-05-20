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
public class AccountIdentification1Choice
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public AccountIdentification1Choice() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.simpletype.IBANIdentifier _iBANIdentifier;

    /** 
     * Setter for iBANIdentifier
     * @param iBANIdentifier the org.sourceforge.ifx.framework.pain004.simpletype.IBANIdentifier to set
     */
    public void setIBANIdentifier(org.sourceforge.ifx.framework.pain004.simpletype.IBANIdentifier _iBANIdentifier) {
        this._iBANIdentifier = _iBANIdentifier;
    }

    /**
     * Getter for iBANIdentifier
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.IBANIdentifier
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.IBANIdentifier getIBANIdentifier() {
        return _iBANIdentifier;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.simpletype.BBANIdentifier _bBANIdentifier;

    /** 
     * Setter for bBANIdentifier
     * @param bBANIdentifier the org.sourceforge.ifx.framework.pain004.simpletype.BBANIdentifier to set
     */
    public void setBBANIdentifier(org.sourceforge.ifx.framework.pain004.simpletype.BBANIdentifier _bBANIdentifier) {
        this._bBANIdentifier = _bBANIdentifier;
    }

    /**
     * Getter for bBANIdentifier
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.BBANIdentifier
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.BBANIdentifier getBBANIdentifier() {
        return _bBANIdentifier;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.simpletype.UPICIdentifier _uPICIdentifier;

    /** 
     * Setter for uPICIdentifier
     * @param uPICIdentifier the org.sourceforge.ifx.framework.pain004.simpletype.UPICIdentifier to set
     */
    public void setUPICIdentifier(org.sourceforge.ifx.framework.pain004.simpletype.UPICIdentifier _uPICIdentifier) {
        this._uPICIdentifier = _uPICIdentifier;
    }

    /**
     * Getter for uPICIdentifier
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.UPICIdentifier
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.UPICIdentifier getUPICIdentifier() {
        return _uPICIdentifier;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.SimpleIdentificationInformation _simpleIdentificationInformation;

    /** 
     * Setter for simpleIdentificationInformation
     * @param simpleIdentificationInformation the org.sourceforge.ifx.framework.pain004.complextype.SimpleIdentificationInformation to set
     */
    public void setSimpleIdentificationInformation(org.sourceforge.ifx.framework.pain004.complextype.SimpleIdentificationInformation _simpleIdentificationInformation) {
        this._simpleIdentificationInformation = _simpleIdentificationInformation;
    }

    /**
     * Getter for simpleIdentificationInformation
     * @return a org.sourceforge.ifx.framework.pain004.complextype.SimpleIdentificationInformation
     */
    public org.sourceforge.ifx.framework.pain004.complextype.SimpleIdentificationInformation getSimpleIdentificationInformation() {
        return _simpleIdentificationInformation;
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
       * IBANIdentifier
       * BBANIdentifier
       * UPICIdentifier
       * SimpleIdentificationInformation
       */
    public final String[] ELEMENTS = {
              "IBANIdentifier"
                 ,"BBANIdentifier"
                 ,"UPICIdentifier"
                 ,"SimpleIdentificationInformation"
          };
}
