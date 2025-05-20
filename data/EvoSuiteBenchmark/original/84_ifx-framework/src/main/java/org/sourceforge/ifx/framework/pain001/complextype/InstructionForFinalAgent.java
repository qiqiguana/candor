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
public class InstructionForFinalAgent
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public InstructionForFinalAgent() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain001.simpletype.Instruction3Code _instruction3Code;

    /** 
     * Setter for instruction3Code
     * @param instruction3Code the org.sourceforge.ifx.framework.pain001.simpletype.Instruction3Code to set
     */
    public void setInstruction3Code(org.sourceforge.ifx.framework.pain001.simpletype.Instruction3Code _instruction3Code) {
        this._instruction3Code = _instruction3Code;
    }

    /**
     * Getter for instruction3Code
     * @return a org.sourceforge.ifx.framework.pain001.simpletype.Instruction3Code
     */
    public org.sourceforge.ifx.framework.pain001.simpletype.Instruction3Code getInstruction3Code() {
        return _instruction3Code;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.pain001.simpletype.Max140Text _max140Text;

    /** 
     * Setter for max140Text
     * @param max140Text the org.sourceforge.ifx.framework.pain001.simpletype.Max140Text to set
     */
    public void setMax140Text(org.sourceforge.ifx.framework.pain001.simpletype.Max140Text _max140Text) {
        this._max140Text = _max140Text;
    }

    /**
     * Getter for max140Text
     * @return a org.sourceforge.ifx.framework.pain001.simpletype.Max140Text
     */
    public org.sourceforge.ifx.framework.pain001.simpletype.Max140Text getMax140Text() {
        return _max140Text;
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
       * Instruction3Code
       * Max140Text
       */
    public final String[] ELEMENTS = {
              "Instruction3Code"
                 ,"Max140Text"
          };
}
