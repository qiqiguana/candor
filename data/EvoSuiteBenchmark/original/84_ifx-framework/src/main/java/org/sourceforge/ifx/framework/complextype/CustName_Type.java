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
public class CustName_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CustName_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.LastName[] _lastName;

    /** 
     * Setter for lastName
     * @param lastName the org.sourceforge.ifx.framework.element.LastName[] to set
     */
    public void setLastName(org.sourceforge.ifx.framework.element.LastName[] _lastName) {
        this._lastName = _lastName;
    }

    /**
     * Getter for lastName
     * @return a org.sourceforge.ifx.framework.element.LastName[]
     */
    public org.sourceforge.ifx.framework.element.LastName[] getLastName() {
        return _lastName;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.FirstName[] _firstName;

    /** 
     * Setter for firstName
     * @param firstName the org.sourceforge.ifx.framework.element.FirstName[] to set
     */
    public void setFirstName(org.sourceforge.ifx.framework.element.FirstName[] _firstName) {
        this._firstName = _firstName;
    }

    /**
     * Getter for firstName
     * @return a org.sourceforge.ifx.framework.element.FirstName[]
     */
    public org.sourceforge.ifx.framework.element.FirstName[] getFirstName() {
        return _firstName;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MiddleName[] _middleName;

    /** 
     * Setter for middleName
     * @param middleName the org.sourceforge.ifx.framework.element.MiddleName[] to set
     */
    public void setMiddleName(org.sourceforge.ifx.framework.element.MiddleName[] _middleName) {
        this._middleName = _middleName;
    }

    /**
     * Getter for middleName
     * @return a org.sourceforge.ifx.framework.element.MiddleName[]
     */
    public org.sourceforge.ifx.framework.element.MiddleName[] getMiddleName() {
        return _middleName;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TitlePrefix[] _titlePrefix;

    /** 
     * Setter for titlePrefix
     * @param titlePrefix the org.sourceforge.ifx.framework.element.TitlePrefix[] to set
     */
    public void setTitlePrefix(org.sourceforge.ifx.framework.element.TitlePrefix[] _titlePrefix) {
        this._titlePrefix = _titlePrefix;
    }

    /**
     * Getter for titlePrefix
     * @return a org.sourceforge.ifx.framework.element.TitlePrefix[]
     */
    public org.sourceforge.ifx.framework.element.TitlePrefix[] getTitlePrefix() {
        return _titlePrefix;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.NameSuffix[] _nameSuffix;

    /** 
     * Setter for nameSuffix
     * @param nameSuffix the org.sourceforge.ifx.framework.element.NameSuffix[] to set
     */
    public void setNameSuffix(org.sourceforge.ifx.framework.element.NameSuffix[] _nameSuffix) {
        this._nameSuffix = _nameSuffix;
    }

    /**
     * Getter for nameSuffix
     * @return a org.sourceforge.ifx.framework.element.NameSuffix[]
     */
    public org.sourceforge.ifx.framework.element.NameSuffix[] getNameSuffix() {
        return _nameSuffix;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Nickname[] _nickname;

    /** 
     * Setter for nickname
     * @param nickname the org.sourceforge.ifx.framework.element.Nickname[] to set
     */
    public void setNickname(org.sourceforge.ifx.framework.element.Nickname[] _nickname) {
        this._nickname = _nickname;
    }

    /**
     * Getter for nickname
     * @return a org.sourceforge.ifx.framework.element.Nickname[]
     */
    public org.sourceforge.ifx.framework.element.Nickname[] getNickname() {
        return _nickname;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.LegalName[] _legalName;

    /** 
     * Setter for legalName
     * @param legalName the org.sourceforge.ifx.framework.element.LegalName[] to set
     */
    public void setLegalName(org.sourceforge.ifx.framework.element.LegalName[] _legalName) {
        this._legalName = _legalName;
    }

    /**
     * Getter for legalName
     * @return a org.sourceforge.ifx.framework.element.LegalName[]
     */
    public org.sourceforge.ifx.framework.element.LegalName[] getLegalName() {
        return _legalName;
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
       * LastName
       * FirstName
       * MiddleName
       * TitlePrefix
       * NameSuffix
       * Nickname
       * LegalName
       */
    public final String[] ELEMENTS = {
              "LastName"
                 ,"FirstName"
                 ,"MiddleName"
                 ,"TitlePrefix"
                 ,"NameSuffix"
                 ,"Nickname"
                 ,"LegalName"
          };
}
