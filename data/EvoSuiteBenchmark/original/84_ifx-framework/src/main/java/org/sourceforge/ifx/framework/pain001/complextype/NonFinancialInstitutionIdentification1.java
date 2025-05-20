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
public class NonFinancialInstitutionIdentification1
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public NonFinancialInstitutionIdentification1() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.simpletype.BEIIdentifier _bEIIdentifier;

    /** 
     * Setter for bEIIdentifier
     * @param bEIIdentifier the org.sourceforge.ifx.framework.pain004.simpletype.BEIIdentifier to set
     */
    public void setBEIIdentifier(org.sourceforge.ifx.framework.pain004.simpletype.BEIIdentifier _bEIIdentifier) {
        this._bEIIdentifier = _bEIIdentifier;
    }

    /**
     * Getter for bEIIdentifier
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.BEIIdentifier
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.BEIIdentifier getBEIIdentifier() {
        return _bEIIdentifier;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.simpletype.EANGLNIdentifier _eANGLNIdentifier;

    /** 
     * Setter for eANGLNIdentifier
     * @param eANGLNIdentifier the org.sourceforge.ifx.framework.pain004.simpletype.EANGLNIdentifier to set
     */
    public void setEANGLNIdentifier(org.sourceforge.ifx.framework.pain004.simpletype.EANGLNIdentifier _eANGLNIdentifier) {
        this._eANGLNIdentifier = _eANGLNIdentifier;
    }

    /**
     * Getter for eANGLNIdentifier
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.EANGLNIdentifier
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.EANGLNIdentifier getEANGLNIdentifier() {
        return _eANGLNIdentifier;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.simpletype.CHIPSUniversalIdentifier _cHIPSUniversalIdentifier;

    /** 
     * Setter for cHIPSUniversalIdentifier
     * @param cHIPSUniversalIdentifier the org.sourceforge.ifx.framework.pain004.simpletype.CHIPSUniversalIdentifier to set
     */
    public void setCHIPSUniversalIdentifier(org.sourceforge.ifx.framework.pain004.simpletype.CHIPSUniversalIdentifier _cHIPSUniversalIdentifier) {
        this._cHIPSUniversalIdentifier = _cHIPSUniversalIdentifier;
    }

    /**
     * Getter for cHIPSUniversalIdentifier
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.CHIPSUniversalIdentifier
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.CHIPSUniversalIdentifier getCHIPSUniversalIdentifier() {
        return _cHIPSUniversalIdentifier;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.simpletype.DunsIdentifier _dunsIdentifier;

    /** 
     * Setter for dunsIdentifier
     * @param dunsIdentifier the org.sourceforge.ifx.framework.pain004.simpletype.DunsIdentifier to set
     */
    public void setDunsIdentifier(org.sourceforge.ifx.framework.pain004.simpletype.DunsIdentifier _dunsIdentifier) {
        this._dunsIdentifier = _dunsIdentifier;
    }

    /**
     * Getter for dunsIdentifier
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.DunsIdentifier
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.DunsIdentifier getDunsIdentifier() {
        return _dunsIdentifier;
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
    private org.sourceforge.ifx.framework.pain004.complextype.GenericIdentification3 _genericIdentification3;

    /** 
     * Setter for genericIdentification3
     * @param genericIdentification3 the org.sourceforge.ifx.framework.pain004.complextype.GenericIdentification3 to set
     */
    public void setGenericIdentification3(org.sourceforge.ifx.framework.pain004.complextype.GenericIdentification3 _genericIdentification3) {
        this._genericIdentification3 = _genericIdentification3;
    }

    /**
     * Getter for genericIdentification3
     * @return a org.sourceforge.ifx.framework.pain004.complextype.GenericIdentification3
     */
    public org.sourceforge.ifx.framework.pain004.complextype.GenericIdentification3 getGenericIdentification3() {
        return _genericIdentification3;
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
       * BEIIdentifier
       * EANGLNIdentifier
       * CHIPSUniversalIdentifier
       * DunsIdentifier
       * Max35Text
       * GenericIdentification3
       */
    public final String[] ELEMENTS = {
              "BEIIdentifier"
                 ,"EANGLNIdentifier"
                 ,"CHIPSUniversalIdentifier"
                 ,"DunsIdentifier"
                 ,"Max35Text"
                 ,"GenericIdentification3"
          };
}
