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
public class CustPref_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CustPref_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Language _language;

    /** 
     * Setter for language
     * @param language the org.sourceforge.ifx.framework.element.Language to set
     */
    public void setLanguage(org.sourceforge.ifx.framework.element.Language _language) {
        this._language = _language;
    }

    /**
     * Getter for language
     * @return a org.sourceforge.ifx.framework.element.Language
     */
    public org.sourceforge.ifx.framework.element.Language getLanguage() {
        return _language;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MktgInfo _mktgInfo;

    /** 
     * Setter for mktgInfo
     * @param mktgInfo the org.sourceforge.ifx.framework.element.MktgInfo to set
     */
    public void setMktgInfo(org.sourceforge.ifx.framework.element.MktgInfo _mktgInfo) {
        this._mktgInfo = _mktgInfo;
    }

    /**
     * Getter for mktgInfo
     * @return a org.sourceforge.ifx.framework.element.MktgInfo
     */
    public org.sourceforge.ifx.framework.element.MktgInfo getMktgInfo() {
        return _mktgInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustBankSvcPref _custBankSvcPref;

    /** 
     * Setter for custBankSvcPref
     * @param custBankSvcPref the org.sourceforge.ifx.framework.element.CustBankSvcPref to set
     */
    public void setCustBankSvcPref(org.sourceforge.ifx.framework.element.CustBankSvcPref _custBankSvcPref) {
        this._custBankSvcPref = _custBankSvcPref;
    }

    /**
     * Getter for custBankSvcPref
     * @return a org.sourceforge.ifx.framework.element.CustBankSvcPref
     */
    public org.sourceforge.ifx.framework.element.CustBankSvcPref getCustBankSvcPref() {
        return _custBankSvcPref;
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
       * Language
       * MktgInfo
       * CustBankSvcPref
       */
    public final String[] ELEMENTS = {
              "Language"
                 ,"MktgInfo"
                 ,"CustBankSvcPref"
          };
}
