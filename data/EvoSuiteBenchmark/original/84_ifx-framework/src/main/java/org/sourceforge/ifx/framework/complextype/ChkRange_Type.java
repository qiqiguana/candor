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
public class ChkRange_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public ChkRange_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkNumStart _chkNumStart;

    /** 
     * Setter for chkNumStart
     * @param chkNumStart the org.sourceforge.ifx.framework.element.ChkNumStart to set
     */
    public void setChkNumStart(org.sourceforge.ifx.framework.element.ChkNumStart _chkNumStart) {
        this._chkNumStart = _chkNumStart;
    }

    /**
     * Getter for chkNumStart
     * @return a org.sourceforge.ifx.framework.element.ChkNumStart
     */
    public org.sourceforge.ifx.framework.element.ChkNumStart getChkNumStart() {
        return _chkNumStart;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkNumEnd _chkNumEnd;

    /** 
     * Setter for chkNumEnd
     * @param chkNumEnd the org.sourceforge.ifx.framework.element.ChkNumEnd to set
     */
    public void setChkNumEnd(org.sourceforge.ifx.framework.element.ChkNumEnd _chkNumEnd) {
        this._chkNumEnd = _chkNumEnd;
    }

    /**
     * Getter for chkNumEnd
     * @return a org.sourceforge.ifx.framework.element.ChkNumEnd
     */
    public org.sourceforge.ifx.framework.element.ChkNumEnd getChkNumEnd() {
        return _chkNumEnd;
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
       * ChkNumStart
       * ChkNumEnd
       */
    public final String[] ELEMENTS = {
              "ChkNumStart"
                 ,"ChkNumEnd"
          };
}
