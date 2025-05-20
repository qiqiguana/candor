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
public class IssScriptResultData_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public IssScriptResultData_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.IssScriptResult _issScriptResult;

    /** 
     * Setter for issScriptResult
     * @param issScriptResult the org.sourceforge.ifx.framework.element.IssScriptResult to set
     */
    public void setIssScriptResult(org.sourceforge.ifx.framework.element.IssScriptResult _issScriptResult) {
        this._issScriptResult = _issScriptResult;
    }

    /**
     * Getter for issScriptResult
     * @return a org.sourceforge.ifx.framework.element.IssScriptResult
     */
    public org.sourceforge.ifx.framework.element.IssScriptResult getIssScriptResult() {
        return _issScriptResult;
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


    /**
     * Returns true if objects are equal, false otherwise.
     * @param obj the object to compare with.
     * @return true if equal, false if not.
     */
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /** Element ordering - 
       * IssScriptResult
       * IssScriptId
       */
    public final String[] ELEMENTS = {
              "IssScriptResult"
                 ,"IssScriptId"
          };
}
