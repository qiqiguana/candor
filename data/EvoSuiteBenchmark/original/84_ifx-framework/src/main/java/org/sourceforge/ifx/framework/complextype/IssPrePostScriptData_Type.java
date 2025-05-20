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
public class IssPrePostScriptData_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public IssPrePostScriptData_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.IssScriptId _issScriptId;

    /** 
     * Setter for issScriptId
     * @param issScriptId the org.sourceforge.ifx.framework.element.IssScriptId to set
     */
    public void setIssScriptId(org.sourceforge.ifx.framework.element.IssScriptId _issScriptId) {
        this._issScriptId = _issScriptId;
    }

    /**
     * Getter for issScriptId
     * @return a org.sourceforge.ifx.framework.element.IssScriptId
     */
    public org.sourceforge.ifx.framework.element.IssScriptId getIssScriptId() {
        return _issScriptId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.IssScriptCmd[] _issScriptCmd;

    /** 
     * Setter for issScriptCmd
     * @param issScriptCmd the org.sourceforge.ifx.framework.element.IssScriptCmd[] to set
     */
    public void setIssScriptCmd(org.sourceforge.ifx.framework.element.IssScriptCmd[] _issScriptCmd) {
        this._issScriptCmd = _issScriptCmd;
    }

    /**
     * Getter for issScriptCmd
     * @return a org.sourceforge.ifx.framework.element.IssScriptCmd[]
     */
    public org.sourceforge.ifx.framework.element.IssScriptCmd[] getIssScriptCmd() {
        return _issScriptCmd;
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
       * IssScriptId
       * IssScriptCmd
       */
    public final String[] ELEMENTS = {
              "IssScriptId"
                 ,"IssScriptCmd"
          };
}
