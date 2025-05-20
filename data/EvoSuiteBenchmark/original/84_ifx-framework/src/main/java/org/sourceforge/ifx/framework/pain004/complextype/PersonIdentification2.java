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
public class PersonIdentification2
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PersonIdentification2() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.simpletype.Max35Text _max35Text;

    /** 
     * Setter for max35Text
     * @param max35Text the org.sourceforge.ifx.framework.pain004.simpletype.Max35Text to set
     */
    public void setMax35Text(org.sourceforge.ifx.framework.pain004.simpletype.Max35Text _max35Text) {
        this._max35Text = _max35Text;
    }

    /**
     * Getter for max35Text
     * @return a org.sourceforge.ifx.framework.pain004.simpletype.Max35Text
     */
    public org.sourceforge.ifx.framework.pain004.simpletype.Max35Text getMax35Text() {
        return _max35Text;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain004.complextype.GenericIdentification4 _genericIdentification4;

    /** 
     * Setter for genericIdentification4
     * @param genericIdentification4 the org.sourceforge.ifx.framework.pain004.complextype.GenericIdentification4 to set
     */
    public void setGenericIdentification4(org.sourceforge.ifx.framework.pain004.complextype.GenericIdentification4 _genericIdentification4) {
        this._genericIdentification4 = _genericIdentification4;
    }

    /**
     * Getter for genericIdentification4
     * @return a org.sourceforge.ifx.framework.pain004.complextype.GenericIdentification4
     */
    public org.sourceforge.ifx.framework.pain004.complextype.GenericIdentification4 getGenericIdentification4() {
        return _genericIdentification4;
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
       * Max35Text
       * GenericIdentification4
       */
    public final String[] ELEMENTS = {
              "Max35Text"
                 ,"GenericIdentification4"
          };
}
