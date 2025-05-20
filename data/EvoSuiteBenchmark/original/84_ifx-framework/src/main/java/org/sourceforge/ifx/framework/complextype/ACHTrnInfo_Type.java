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
public class ACHTrnInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public ACHTrnInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.OriginatorName _originatorName;

    /** 
     * Setter for originatorName
     * @param originatorName the org.sourceforge.ifx.framework.element.OriginatorName to set
     */
    public void setOriginatorName(org.sourceforge.ifx.framework.element.OriginatorName _originatorName) {
        this._originatorName = _originatorName;
    }

    /**
     * Getter for originatorName
     * @return a org.sourceforge.ifx.framework.element.OriginatorName
     */
    public org.sourceforge.ifx.framework.element.OriginatorName getOriginatorName() {
        return _originatorName;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.USA_RTN _uSA_RTN;

    /** 
     * Setter for uSA_RTN
     * @param uSA_RTN the org.sourceforge.ifx.framework.element.USA_RTN to set
     */
    public void setUSA_RTN(org.sourceforge.ifx.framework.element.USA_RTN _uSA_RTN) {
        this._uSA_RTN = _uSA_RTN;
    }

    /**
     * Getter for uSA_RTN
     * @return a org.sourceforge.ifx.framework.element.USA_RTN
     */
    public org.sourceforge.ifx.framework.element.USA_RTN getUSA_RTN() {
        return _uSA_RTN;
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
       * OriginatorName
       * USA_RTN
       */
    public final String[] ELEMENTS = {
              "OriginatorName"
                 ,"USA_RTN"
          };
}
