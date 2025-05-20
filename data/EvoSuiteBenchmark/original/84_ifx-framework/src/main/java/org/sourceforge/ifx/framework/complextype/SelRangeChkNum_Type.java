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
public class SelRangeChkNum_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public SelRangeChkNum_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.LowChkNum _lowChkNum;

    /** 
     * Setter for lowChkNum
     * @param lowChkNum the org.sourceforge.ifx.framework.element.LowChkNum to set
     */
    public void setLowChkNum(org.sourceforge.ifx.framework.element.LowChkNum _lowChkNum) {
        this._lowChkNum = _lowChkNum;
    }

    /**
     * Getter for lowChkNum
     * @return a org.sourceforge.ifx.framework.element.LowChkNum
     */
    public org.sourceforge.ifx.framework.element.LowChkNum getLowChkNum() {
        return _lowChkNum;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.HighChkNum _highChkNum;

    /** 
     * Setter for highChkNum
     * @param highChkNum the org.sourceforge.ifx.framework.element.HighChkNum to set
     */
    public void setHighChkNum(org.sourceforge.ifx.framework.element.HighChkNum _highChkNum) {
        this._highChkNum = _highChkNum;
    }

    /**
     * Getter for highChkNum
     * @return a org.sourceforge.ifx.framework.element.HighChkNum
     */
    public org.sourceforge.ifx.framework.element.HighChkNum getHighChkNum() {
        return _highChkNum;
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
       * LowChkNum
       * HighChkNum
       */
    public final String[] ELEMENTS = {
              "LowChkNum"
                 ,"HighChkNum"
          };
}
