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
public class IndustId_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public IndustId_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Org _org;

    /** 
     * Setter for org
     * @param org the org.sourceforge.ifx.framework.element.Org to set
     */
    public void setOrg(org.sourceforge.ifx.framework.element.Org _org) {
        this._org = _org;
    }

    /**
     * Getter for org
     * @return a org.sourceforge.ifx.framework.element.Org
     */
    public org.sourceforge.ifx.framework.element.Org getOrg() {
        return _org;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.IndustNum _industNum;

    /** 
     * Setter for industNum
     * @param industNum the org.sourceforge.ifx.framework.element.IndustNum to set
     */
    public void setIndustNum(org.sourceforge.ifx.framework.element.IndustNum _industNum) {
        this._industNum = _industNum;
    }

    /**
     * Getter for industNum
     * @return a org.sourceforge.ifx.framework.element.IndustNum
     */
    public org.sourceforge.ifx.framework.element.IndustNum getIndustNum() {
        return _industNum;
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
       * Org
       * IndustNum
       */
    public final String[] ELEMENTS = {
              "Org"
                 ,"IndustNum"
          };
}
