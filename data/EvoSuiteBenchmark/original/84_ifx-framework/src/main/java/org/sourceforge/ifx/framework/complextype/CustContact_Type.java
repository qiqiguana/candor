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
public class CustContact_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CustContact_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustContactPref _custContactPref;

    /** 
     * Setter for custContactPref
     * @param custContactPref the org.sourceforge.ifx.framework.element.CustContactPref to set
     */
    public void setCustContactPref(org.sourceforge.ifx.framework.element.CustContactPref _custContactPref) {
        this._custContactPref = _custContactPref;
    }

    /**
     * Getter for custContactPref
     * @return a org.sourceforge.ifx.framework.element.CustContactPref
     */
    public org.sourceforge.ifx.framework.element.CustContactPref getCustContactPref() {
        return _custContactPref;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PrefTimeStart _prefTimeStart;

    /** 
     * Setter for prefTimeStart
     * @param prefTimeStart the org.sourceforge.ifx.framework.element.PrefTimeStart to set
     */
    public void setPrefTimeStart(org.sourceforge.ifx.framework.element.PrefTimeStart _prefTimeStart) {
        this._prefTimeStart = _prefTimeStart;
    }

    /**
     * Getter for prefTimeStart
     * @return a org.sourceforge.ifx.framework.element.PrefTimeStart
     */
    public org.sourceforge.ifx.framework.element.PrefTimeStart getPrefTimeStart() {
        return _prefTimeStart;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PrefTimeEnd _prefTimeEnd;

    /** 
     * Setter for prefTimeEnd
     * @param prefTimeEnd the org.sourceforge.ifx.framework.element.PrefTimeEnd to set
     */
    public void setPrefTimeEnd(org.sourceforge.ifx.framework.element.PrefTimeEnd _prefTimeEnd) {
        this._prefTimeEnd = _prefTimeEnd;
    }

    /**
     * Getter for prefTimeEnd
     * @return a org.sourceforge.ifx.framework.element.PrefTimeEnd
     */
    public org.sourceforge.ifx.framework.element.PrefTimeEnd getPrefTimeEnd() {
        return _prefTimeEnd;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DayPhone _dayPhone;

    /** 
     * Setter for dayPhone
     * @param dayPhone the org.sourceforge.ifx.framework.element.DayPhone to set
     */
    public void setDayPhone(org.sourceforge.ifx.framework.element.DayPhone _dayPhone) {
        this._dayPhone = _dayPhone;
    }

    /**
     * Getter for dayPhone
     * @return a org.sourceforge.ifx.framework.element.DayPhone
     */
    public org.sourceforge.ifx.framework.element.DayPhone getDayPhone() {
        return _dayPhone;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.EvePhone _evePhone;

    /** 
     * Setter for evePhone
     * @param evePhone the org.sourceforge.ifx.framework.element.EvePhone to set
     */
    public void setEvePhone(org.sourceforge.ifx.framework.element.EvePhone _evePhone) {
        this._evePhone = _evePhone;
    }

    /**
     * Getter for evePhone
     * @return a org.sourceforge.ifx.framework.element.EvePhone
     */
    public org.sourceforge.ifx.framework.element.EvePhone getEvePhone() {
        return _evePhone;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DayFax _dayFax;

    /** 
     * Setter for dayFax
     * @param dayFax the org.sourceforge.ifx.framework.element.DayFax to set
     */
    public void setDayFax(org.sourceforge.ifx.framework.element.DayFax _dayFax) {
        this._dayFax = _dayFax;
    }

    /**
     * Getter for dayFax
     * @return a org.sourceforge.ifx.framework.element.DayFax
     */
    public org.sourceforge.ifx.framework.element.DayFax getDayFax() {
        return _dayFax;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.EveFax _eveFax;

    /** 
     * Setter for eveFax
     * @param eveFax the org.sourceforge.ifx.framework.element.EveFax to set
     */
    public void setEveFax(org.sourceforge.ifx.framework.element.EveFax _eveFax) {
        this._eveFax = _eveFax;
    }

    /**
     * Getter for eveFax
     * @return a org.sourceforge.ifx.framework.element.EveFax
     */
    public org.sourceforge.ifx.framework.element.EveFax getEveFax() {
        return _eveFax;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.EmailAddr _emailAddr;

    /** 
     * Setter for emailAddr
     * @param emailAddr the org.sourceforge.ifx.framework.element.EmailAddr to set
     */
    public void setEmailAddr(org.sourceforge.ifx.framework.element.EmailAddr _emailAddr) {
        this._emailAddr = _emailAddr;
    }

    /**
     * Getter for emailAddr
     * @return a org.sourceforge.ifx.framework.element.EmailAddr
     */
    public org.sourceforge.ifx.framework.element.EmailAddr getEmailAddr() {
        return _emailAddr;
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
       * CustContactPref
       * PrefTimeStart
       * PrefTimeEnd
       * DayPhone
       * EvePhone
       * DayFax
       * EveFax
       * EmailAddr
       */
    public final String[] ELEMENTS = {
              "CustContactPref"
                 ,"PrefTimeStart"
                 ,"PrefTimeEnd"
                 ,"DayPhone"
                 ,"EvePhone"
                 ,"DayFax"
                 ,"EveFax"
                 ,"EmailAddr"
          };
}
