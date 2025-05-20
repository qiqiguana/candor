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
public class DriversLicense_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DriversLicense_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.LicenseNum _licenseNum;

    /** 
     * Setter for licenseNum
     * @param licenseNum the org.sourceforge.ifx.framework.element.LicenseNum to set
     */
    public void setLicenseNum(org.sourceforge.ifx.framework.element.LicenseNum _licenseNum) {
        this._licenseNum = _licenseNum;
    }

    /**
     * Getter for licenseNum
     * @return a org.sourceforge.ifx.framework.element.LicenseNum
     */
    public org.sourceforge.ifx.framework.element.LicenseNum getLicenseNum() {
        return _licenseNum;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StateProv _stateProv;

    /** 
     * Setter for stateProv
     * @param stateProv the org.sourceforge.ifx.framework.element.StateProv to set
     */
    public void setStateProv(org.sourceforge.ifx.framework.element.StateProv _stateProv) {
        this._stateProv = _stateProv;
    }

    /**
     * Getter for stateProv
     * @return a org.sourceforge.ifx.framework.element.StateProv
     */
    public org.sourceforge.ifx.framework.element.StateProv getStateProv() {
        return _stateProv;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Country _country;

    /** 
     * Setter for country
     * @param country the org.sourceforge.ifx.framework.element.Country to set
     */
    public void setCountry(org.sourceforge.ifx.framework.element.Country _country) {
        this._country = _country;
    }

    /**
     * Getter for country
     * @return a org.sourceforge.ifx.framework.element.Country
     */
    public org.sourceforge.ifx.framework.element.Country getCountry() {
        return _country;
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
       * LicenseNum
       * StateProv
       * Country
       */
    public final String[] ELEMENTS = {
              "LicenseNum"
                 ,"StateProv"
                 ,"Country"
          };
}
