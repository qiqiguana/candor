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
public class DepAcctStmtMsgRqInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DepAcctStmtMsgRqInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAcctStmtInqRq _depAcctStmtInqRq;

    /** 
     * Setter for depAcctStmtInqRq
     * @param depAcctStmtInqRq the org.sourceforge.ifx.framework.element.DepAcctStmtInqRq to set
     */
    public void setDepAcctStmtInqRq(org.sourceforge.ifx.framework.element.DepAcctStmtInqRq _depAcctStmtInqRq) {
        this._depAcctStmtInqRq = _depAcctStmtInqRq;
    }

    /**
     * Getter for depAcctStmtInqRq
     * @return a org.sourceforge.ifx.framework.element.DepAcctStmtInqRq
     */
    public org.sourceforge.ifx.framework.element.DepAcctStmtInqRq getDepAcctStmtInqRq() {
        return _depAcctStmtInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAcctStmtAdviseRq _depAcctStmtAdviseRq;

    /** 
     * Setter for depAcctStmtAdviseRq
     * @param depAcctStmtAdviseRq the org.sourceforge.ifx.framework.element.DepAcctStmtAdviseRq to set
     */
    public void setDepAcctStmtAdviseRq(org.sourceforge.ifx.framework.element.DepAcctStmtAdviseRq _depAcctStmtAdviseRq) {
        this._depAcctStmtAdviseRq = _depAcctStmtAdviseRq;
    }

    /**
     * Getter for depAcctStmtAdviseRq
     * @return a org.sourceforge.ifx.framework.element.DepAcctStmtAdviseRq
     */
    public org.sourceforge.ifx.framework.element.DepAcctStmtAdviseRq getDepAcctStmtAdviseRq() {
        return _depAcctStmtAdviseRq;
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
       * DepAcctStmtInqRq
       * DepAcctStmtAdviseRq
       */
    public final String[] ELEMENTS = {
              "DepAcctStmtInqRq"
                 ,"DepAcctStmtAdviseRq"
          };
}
