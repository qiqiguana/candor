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
public class PartyIdentification1
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PartyIdentification1() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.simpletype.Max70Text _max70Text;

    /** 
     * Setter for max70Text
     * @param max70Text the org.sourceforge.ifx.framework.pain004.simpletype.Max70Text to set
     */
    public void setMax70Text(org.sourceforge.ifx.framework.pain004.simpletype.Max70Text _max70Text) {
        this._max70Text = _max70Text;
    }

    /**
     * Getter for max70Text
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.Max70Text
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.Max70Text getMax70Text() {
        return _max70Text;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.PostalAddress1 _postalAddress1;

    /** 
     * Setter for postalAddress1
     * @param postalAddress1 the org.sourceforge.ifx.framework.pain004.complextype.PostalAddress1 to set
     */
    public void setPostalAddress1(org.sourceforge.ifx.framework.pain004.complextype.PostalAddress1 _postalAddress1) {
        this._postalAddress1 = _postalAddress1;
    }

    /**
     * Getter for postalAddress1
     * @return a org.sourceforge.ifx.framework.pain004.complextype.PostalAddress1
     */
    public org.sourceforge.ifx.framework.pain004.complextype.PostalAddress1 getPostalAddress1() {
        return _postalAddress1;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.Party1Choice _party1Choice;

    /** 
     * Setter for party1Choice
     * @param party1Choice the org.sourceforge.ifx.framework.pain004.complextype.Party1Choice to set
     */
    public void setParty1Choice(org.sourceforge.ifx.framework.pain004.complextype.Party1Choice _party1Choice) {
        this._party1Choice = _party1Choice;
    }

    /**
     * Getter for party1Choice
     * @return a org.sourceforge.ifx.framework.pain004.complextype.Party1Choice
     */
    public org.sourceforge.ifx.framework.pain004.complextype.Party1Choice getParty1Choice() {
        return _party1Choice;
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
       * Max70Text
       * PostalAddress1
       * Party1Choice
       */
    public final String[] ELEMENTS = {
              "Max70Text"
                 ,"PostalAddress1"
                 ,"Party1Choice"
          };
}
