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
public class PmtBatchStatus_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PmtBatchStatus_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.pain_002_001_01 _pain_002_001_01;

    /** 
     * Setter for pain_002_001_01
     * @param pain_002_001_01 the org.sourceforge.ifx.framework.element.pain_002_001_01 to set
     */
    public void setPain_002_001_01(org.sourceforge.ifx.framework.element.pain_002_001_01 _pain_002_001_01) {
        this._pain_002_001_01 = _pain_002_001_01;
    }

    /**
     * Getter for pain_002_001_01
     * @return a org.sourceforge.ifx.framework.element.pain_002_001_01
     */
    public org.sourceforge.ifx.framework.element.pain_002_001_01 getPain_002_001_01() {
        return _pain_002_001_01;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.pain_004_001_01 _pain_004_001_01;

    /** 
     * Setter for pain_004_001_01
     * @param pain_004_001_01 the org.sourceforge.ifx.framework.element.pain_004_001_01 to set
     */
    public void setPain_004_001_01(org.sourceforge.ifx.framework.element.pain_004_001_01 _pain_004_001_01) {
        this._pain_004_001_01 = _pain_004_001_01;
    }

    /**
     * Getter for pain_004_001_01
     * @return a org.sourceforge.ifx.framework.element.pain_004_001_01
     */
    public org.sourceforge.ifx.framework.element.pain_004_001_01 getPain_004_001_01() {
        return _pain_004_001_01;
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
       * Pain_002_001_01
       * Pain_004_001_01
       */
    public final String[] ELEMENTS = {
              "Pain_002_001_01"
                 ,"Pain_004_001_01"
          };
}
