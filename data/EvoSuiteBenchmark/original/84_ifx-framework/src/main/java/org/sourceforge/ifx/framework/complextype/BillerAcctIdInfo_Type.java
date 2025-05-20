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
public class BillerAcctIdInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public BillerAcctIdInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AcctFormat _acctFormat;

    /** 
     * Setter for acctFormat
     * @param acctFormat the org.sourceforge.ifx.framework.element.AcctFormat to set
     */
    public void setAcctFormat(org.sourceforge.ifx.framework.element.AcctFormat _acctFormat) {
        this._acctFormat = _acctFormat;
    }

    /**
     * Getter for acctFormat
     * @return a org.sourceforge.ifx.framework.element.AcctFormat
     */
    public org.sourceforge.ifx.framework.element.AcctFormat getAcctFormat() {
        return _acctFormat;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AcctMask[] _acctMask;

    /** 
     * Setter for acctMask
     * @param acctMask the org.sourceforge.ifx.framework.element.AcctMask[] to set
     */
    public void setAcctMask(org.sourceforge.ifx.framework.element.AcctMask[] _acctMask) {
        this._acctMask = _acctMask;
    }

    /**
     * Getter for acctMask
     * @return a org.sourceforge.ifx.framework.element.AcctMask[]
     */
    public org.sourceforge.ifx.framework.element.AcctMask[] getAcctMask() {
        return _acctMask;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AcctHelpMsg[] _acctHelpMsg;

    /** 
     * Setter for acctHelpMsg
     * @param acctHelpMsg the org.sourceforge.ifx.framework.element.AcctHelpMsg[] to set
     */
    public void setAcctHelpMsg(org.sourceforge.ifx.framework.element.AcctHelpMsg[] _acctHelpMsg) {
        this._acctHelpMsg = _acctHelpMsg;
    }

    /**
     * Getter for acctHelpMsg
     * @return a org.sourceforge.ifx.framework.element.AcctHelpMsg[]
     */
    public org.sourceforge.ifx.framework.element.AcctHelpMsg[] getAcctHelpMsg() {
        return _acctHelpMsg;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AcctRestrictMsg[] _acctRestrictMsg;

    /** 
     * Setter for acctRestrictMsg
     * @param acctRestrictMsg the org.sourceforge.ifx.framework.element.AcctRestrictMsg[] to set
     */
    public void setAcctRestrictMsg(org.sourceforge.ifx.framework.element.AcctRestrictMsg[] _acctRestrictMsg) {
        this._acctRestrictMsg = _acctRestrictMsg;
    }

    /**
     * Getter for acctRestrictMsg
     * @return a org.sourceforge.ifx.framework.element.AcctRestrictMsg[]
     */
    public org.sourceforge.ifx.framework.element.AcctRestrictMsg[] getAcctRestrictMsg() {
        return _acctRestrictMsg;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AcctValidateURL[] _acctValidateURL;

    /** 
     * Setter for acctValidateURL
     * @param acctValidateURL the org.sourceforge.ifx.framework.element.AcctValidateURL[] to set
     */
    public void setAcctValidateURL(org.sourceforge.ifx.framework.element.AcctValidateURL[] _acctValidateURL) {
        this._acctValidateURL = _acctValidateURL;
    }

    /**
     * Getter for acctValidateURL
     * @return a org.sourceforge.ifx.framework.element.AcctValidateURL[]
     */
    public org.sourceforge.ifx.framework.element.AcctValidateURL[] getAcctValidateURL() {
        return _acctValidateURL;
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
       * AcctFormat
       * AcctMask
       * AcctHelpMsg
       * AcctRestrictMsg
       * AcctValidateURL
       */
    public final String[] ELEMENTS = {
              "AcctFormat"
                 ,"AcctMask"
                 ,"AcctHelpMsg"
                 ,"AcctRestrictMsg"
                 ,"AcctValidateURL"
          };
}
