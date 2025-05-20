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
public class OrgInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public OrgInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.IndustId _industId;

    /** 
     * Setter for industId
     * @param industId the org.sourceforge.ifx.framework.element.IndustId to set
     */
    public void setIndustId(org.sourceforge.ifx.framework.element.IndustId _industId) {
        this._industId = _industId;
    }

    /**
     * Getter for industId
     * @return a org.sourceforge.ifx.framework.element.IndustId
     */
    public org.sourceforge.ifx.framework.element.IndustId getIndustId() {
        return _industId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Name _name;

    /** 
     * Setter for name
     * @param name the org.sourceforge.ifx.framework.element.Name to set
     */
    public void setName(org.sourceforge.ifx.framework.element.Name _name) {
        this._name = _name;
    }

    /**
     * Getter for name
     * @return a org.sourceforge.ifx.framework.element.Name
     */
    public org.sourceforge.ifx.framework.element.Name getName() {
        return _name;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.LegalName _legalName;

    /** 
     * Setter for legalName
     * @param legalName the org.sourceforge.ifx.framework.element.LegalName to set
     */
    public void setLegalName(org.sourceforge.ifx.framework.element.LegalName _legalName) {
        this._legalName = _legalName;
    }

    /**
     * Getter for legalName
     * @return a org.sourceforge.ifx.framework.element.LegalName
     */
    public org.sourceforge.ifx.framework.element.LegalName getLegalName() {
        return _legalName;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CompositeContactInfo[] _compositeContactInfo;

    /** 
     * Setter for compositeContactInfo
     * @param compositeContactInfo the org.sourceforge.ifx.framework.element.CompositeContactInfo[] to set
     */
    public void setCompositeContactInfo(org.sourceforge.ifx.framework.element.CompositeContactInfo[] _compositeContactInfo) {
        this._compositeContactInfo = _compositeContactInfo;
    }

    /**
     * Getter for compositeContactInfo
     * @return a org.sourceforge.ifx.framework.element.CompositeContactInfo[]
     */
    public org.sourceforge.ifx.framework.element.CompositeContactInfo[] getCompositeContactInfo() {
        return _compositeContactInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TINInfo[] _tINInfo;

    /** 
     * Setter for tINInfo
     * @param tINInfo the org.sourceforge.ifx.framework.element.TINInfo[] to set
     */
    public void setTINInfo(org.sourceforge.ifx.framework.element.TINInfo[] _tINInfo) {
        this._tINInfo = _tINInfo;
    }

    /**
     * Getter for tINInfo
     * @return a org.sourceforge.ifx.framework.element.TINInfo[]
     */
    public org.sourceforge.ifx.framework.element.TINInfo[] getTINInfo() {
        return _tINInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.EstablishDt[] _establishDt;

    /** 
     * Setter for establishDt
     * @param establishDt the org.sourceforge.ifx.framework.element.EstablishDt[] to set
     */
    public void setEstablishDt(org.sourceforge.ifx.framework.element.EstablishDt[] _establishDt) {
        this._establishDt = _establishDt;
    }

    /**
     * Getter for establishDt
     * @return a org.sourceforge.ifx.framework.element.EstablishDt[]
     */
    public org.sourceforge.ifx.framework.element.EstablishDt[] getEstablishDt() {
        return _establishDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.NumEmployees[] _numEmployees;

    /** 
     * Setter for numEmployees
     * @param numEmployees the org.sourceforge.ifx.framework.element.NumEmployees[] to set
     */
    public void setNumEmployees(org.sourceforge.ifx.framework.element.NumEmployees[] _numEmployees) {
        this._numEmployees = _numEmployees;
    }

    /**
     * Getter for numEmployees
     * @return a org.sourceforge.ifx.framework.element.NumEmployees[]
     */
    public org.sourceforge.ifx.framework.element.NumEmployees[] getNumEmployees() {
        return _numEmployees;
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
       * IndustId
       * Name
       * LegalName
       * CompositeContactInfo
       * TINInfo
       * EstablishDt
       * NumEmployees
       */
    public final String[] ELEMENTS = {
              "IndustId"
                 ,"Name"
                 ,"LegalName"
                 ,"CompositeContactInfo"
                 ,"TINInfo"
                 ,"EstablishDt"
                 ,"NumEmployees"
          };
}
