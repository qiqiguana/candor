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
public class Party1Choice
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public Party1Choice() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.NonFinancialInstitutionIdentification1 _nonFinancialInstitutionIdentification1;

    /** 
     * Setter for nonFinancialInstitutionIdentification1
     * @param nonFinancialInstitutionIdentification1 the org.sourceforge.ifx.framework.pain004.complextype.NonFinancialInstitutionIdentification1 to set
     */
    public void setNonFinancialInstitutionIdentification1(org.sourceforge.ifx.framework.pain004.complextype.NonFinancialInstitutionIdentification1 _nonFinancialInstitutionIdentification1) {
        this._nonFinancialInstitutionIdentification1 = _nonFinancialInstitutionIdentification1;
    }

    /**
     * Getter for nonFinancialInstitutionIdentification1
     * @return a org.sourceforge.ifx.framework.pain004.complextype.NonFinancialInstitutionIdentification1
     */
    public org.sourceforge.ifx.framework.pain004.complextype.NonFinancialInstitutionIdentification1 getNonFinancialInstitutionIdentification1() {
        return _nonFinancialInstitutionIdentification1;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.PersonIdentification2 _personIdentification2;

    /** 
     * Setter for personIdentification2
     * @param personIdentification2 the org.sourceforge.ifx.framework.pain004.complextype.PersonIdentification2 to set
     */
    public void setPersonIdentification2(org.sourceforge.ifx.framework.pain004.complextype.PersonIdentification2 _personIdentification2) {
        this._personIdentification2 = _personIdentification2;
    }

    /**
     * Getter for personIdentification2
     * @return a org.sourceforge.ifx.framework.pain004.complextype.PersonIdentification2
     */
    public org.sourceforge.ifx.framework.pain004.complextype.PersonIdentification2 getPersonIdentification2() {
        return _personIdentification2;
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
       * NonFinancialInstitutionIdentification1
       * PersonIdentification2
       */
    public final String[] ELEMENTS = {
              "NonFinancialInstitutionIdentification1"
                 ,"PersonIdentification2"
          };
}
