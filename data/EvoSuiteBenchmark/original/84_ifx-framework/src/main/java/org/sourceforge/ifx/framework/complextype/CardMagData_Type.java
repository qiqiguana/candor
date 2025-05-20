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
public class CardMagData_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CardMagData_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MagData1 _magData1;

    /** 
     * Setter for magData1
     * @param magData1 the org.sourceforge.ifx.framework.element.MagData1 to set
     */
    public void setMagData1(org.sourceforge.ifx.framework.element.MagData1 _magData1) {
        this._magData1 = _magData1;
    }

    /**
     * Getter for magData1
     * @return a org.sourceforge.ifx.framework.element.MagData1
     */
    public org.sourceforge.ifx.framework.element.MagData1 getMagData1() {
        return _magData1;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MagData2 _magData2;

    /** 
     * Setter for magData2
     * @param magData2 the org.sourceforge.ifx.framework.element.MagData2 to set
     */
    public void setMagData2(org.sourceforge.ifx.framework.element.MagData2 _magData2) {
        this._magData2 = _magData2;
    }

    /**
     * Getter for magData2
     * @return a org.sourceforge.ifx.framework.element.MagData2
     */
    public org.sourceforge.ifx.framework.element.MagData2 getMagData2() {
        return _magData2;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MagData3 _magData3;

    /** 
     * Setter for magData3
     * @param magData3 the org.sourceforge.ifx.framework.element.MagData3 to set
     */
    public void setMagData3(org.sourceforge.ifx.framework.element.MagData3 _magData3) {
        this._magData3 = _magData3;
    }

    /**
     * Getter for magData3
     * @return a org.sourceforge.ifx.framework.element.MagData3
     */
    public org.sourceforge.ifx.framework.element.MagData3 getMagData3() {
        return _magData3;
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
       * MagData1
       * MagData2
       * MagData3
       */
    public final String[] ELEMENTS = {
              "MagData1"
                 ,"MagData2"
                 ,"MagData3"
          };
}
