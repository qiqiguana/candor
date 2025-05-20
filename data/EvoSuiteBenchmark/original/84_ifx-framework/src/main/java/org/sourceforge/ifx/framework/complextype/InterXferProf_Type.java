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
package org.sourceforge.ifx.framework.complextype;

/**
 * Generated code.
 * 
 * @author org.sourceforge.ifx.tools.CodeGenerator
 */
public class InterXferProf_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public InterXferProf_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.XferProf _xferProf;

    /** 
     * Setter for xferProf
     * @param xferProf the org.sourceforge.ifx.framework.element.XferProf to set
     */
    public void setXferProf(org.sourceforge.ifx.framework.element.XferProf _xferProf) {
        this._xferProf = _xferProf;
    }

    /**
     * Getter for xferProf
     * @return a org.sourceforge.ifx.framework.element.XferProf
     */
    public org.sourceforge.ifx.framework.element.XferProf getXferProf() {
        return _xferProf;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DomXferFeeCurAmt _domXferFeeCurAmt;

    /** 
     * Setter for domXferFeeCurAmt
     * @param domXferFeeCurAmt the org.sourceforge.ifx.framework.element.DomXferFeeCurAmt to set
     */
    public void setDomXferFeeCurAmt(org.sourceforge.ifx.framework.element.DomXferFeeCurAmt _domXferFeeCurAmt) {
        this._domXferFeeCurAmt = _domXferFeeCurAmt;
    }

    /**
     * Getter for domXferFeeCurAmt
     * @return a org.sourceforge.ifx.framework.element.DomXferFeeCurAmt
     */
    public org.sourceforge.ifx.framework.element.DomXferFeeCurAmt getDomXferFeeCurAmt() {
        return _domXferFeeCurAmt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.IntlXferFeeCurAmt _intlXferFeeCurAmt;

    /** 
     * Setter for intlXferFeeCurAmt
     * @param intlXferFeeCurAmt the org.sourceforge.ifx.framework.element.IntlXferFeeCurAmt to set
     */
    public void setIntlXferFeeCurAmt(org.sourceforge.ifx.framework.element.IntlXferFeeCurAmt _intlXferFeeCurAmt) {
        this._intlXferFeeCurAmt = _intlXferFeeCurAmt;
    }

    /**
     * Getter for intlXferFeeCurAmt
     * @return a org.sourceforge.ifx.framework.element.IntlXferFeeCurAmt
     */
    public org.sourceforge.ifx.framework.element.IntlXferFeeCurAmt getIntlXferFeeCurAmt() {
        return _intlXferFeeCurAmt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.USA_ACHProf _uSA_ACHProf;

    /** 
     * Setter for uSA_ACHProf
     * @param uSA_ACHProf the org.sourceforge.ifx.framework.element.USA_ACHProf to set
     */
    public void setUSA_ACHProf(org.sourceforge.ifx.framework.element.USA_ACHProf _uSA_ACHProf) {
        this._uSA_ACHProf = _uSA_ACHProf;
    }

    /**
     * Getter for uSA_ACHProf
     * @return a org.sourceforge.ifx.framework.element.USA_ACHProf
     */
    public org.sourceforge.ifx.framework.element.USA_ACHProf getUSA_ACHProf() {
        return _uSA_ACHProf;
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
       * XferProf
       * DomXferFeeCurAmt
       * IntlXferFeeCurAmt
       * USA_ACHProf
       */
    public final String[] ELEMENTS = {
              "XferProf"
                 ,"DomXferFeeCurAmt"
                 ,"IntlXferFeeCurAmt"
                 ,"USA_ACHProf"
          };
}
