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
public class BranchAndFinancialInstitutionIdentification
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public BranchAndFinancialInstitutionIdentification() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.FinancialInstitutionIdentification1 _financialInstitutionIdentification1;

    /** 
     * Setter for financialInstitutionIdentification1
     * @param financialInstitutionIdentification1 the org.sourceforge.ifx.framework.pain004.complextype.FinancialInstitutionIdentification1 to set
     */
    public void setFinancialInstitutionIdentification1(org.sourceforge.ifx.framework.pain004.complextype.FinancialInstitutionIdentification1 _financialInstitutionIdentification1) {
        this._financialInstitutionIdentification1 = _financialInstitutionIdentification1;
    }

    /**
     * Getter for financialInstitutionIdentification1
     * @return a org.sourceforge.ifx.framework.pain004.complextype.FinancialInstitutionIdentification1
     */
    public org.sourceforge.ifx.framework.pain004.complextype.FinancialInstitutionIdentification1 getFinancialInstitutionIdentification1() {
        return _financialInstitutionIdentification1;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.BranchData _branchData;

    /** 
     * Setter for branchData
     * @param branchData the org.sourceforge.ifx.framework.pain004.complextype.BranchData to set
     */
    public void setBranchData(org.sourceforge.ifx.framework.pain004.complextype.BranchData _branchData) {
        this._branchData = _branchData;
    }

    /**
     * Getter for branchData
     * @return a org.sourceforge.ifx.framework.pain004.complextype.BranchData
     */
    public org.sourceforge.ifx.framework.pain004.complextype.BranchData getBranchData() {
        return _branchData;
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
       * FinancialInstitutionIdentification1
       * BranchData
       */
    public final String[] ELEMENTS = {
              "FinancialInstitutionIdentification1"
                 ,"BranchData"
          };
}
