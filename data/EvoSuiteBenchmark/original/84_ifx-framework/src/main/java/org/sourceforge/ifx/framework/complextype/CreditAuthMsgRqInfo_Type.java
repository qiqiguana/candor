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
public class CreditAuthMsgRqInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CreditAuthMsgRqInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAuthAddRq _creditAuthAddRq;

    /** 
     * Setter for creditAuthAddRq
     * @param creditAuthAddRq the org.sourceforge.ifx.framework.element.CreditAuthAddRq to set
     */
    public void setCreditAuthAddRq(org.sourceforge.ifx.framework.element.CreditAuthAddRq _creditAuthAddRq) {
        this._creditAuthAddRq = _creditAuthAddRq;
    }

    /**
     * Getter for creditAuthAddRq
     * @return a org.sourceforge.ifx.framework.element.CreditAuthAddRq
     */
    public org.sourceforge.ifx.framework.element.CreditAuthAddRq getCreditAuthAddRq() {
        return _creditAuthAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAuthModRq _creditAuthModRq;

    /** 
     * Setter for creditAuthModRq
     * @param creditAuthModRq the org.sourceforge.ifx.framework.element.CreditAuthModRq to set
     */
    public void setCreditAuthModRq(org.sourceforge.ifx.framework.element.CreditAuthModRq _creditAuthModRq) {
        this._creditAuthModRq = _creditAuthModRq;
    }

    /**
     * Getter for creditAuthModRq
     * @return a org.sourceforge.ifx.framework.element.CreditAuthModRq
     */
    public org.sourceforge.ifx.framework.element.CreditAuthModRq getCreditAuthModRq() {
        return _creditAuthModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAuthCanRq _creditAuthCanRq;

    /** 
     * Setter for creditAuthCanRq
     * @param creditAuthCanRq the org.sourceforge.ifx.framework.element.CreditAuthCanRq to set
     */
    public void setCreditAuthCanRq(org.sourceforge.ifx.framework.element.CreditAuthCanRq _creditAuthCanRq) {
        this._creditAuthCanRq = _creditAuthCanRq;
    }

    /**
     * Getter for creditAuthCanRq
     * @return a org.sourceforge.ifx.framework.element.CreditAuthCanRq
     */
    public org.sourceforge.ifx.framework.element.CreditAuthCanRq getCreditAuthCanRq() {
        return _creditAuthCanRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAuthInqRq _creditAuthInqRq;

    /** 
     * Setter for creditAuthInqRq
     * @param creditAuthInqRq the org.sourceforge.ifx.framework.element.CreditAuthInqRq to set
     */
    public void setCreditAuthInqRq(org.sourceforge.ifx.framework.element.CreditAuthInqRq _creditAuthInqRq) {
        this._creditAuthInqRq = _creditAuthInqRq;
    }

    /**
     * Getter for creditAuthInqRq
     * @return a org.sourceforge.ifx.framework.element.CreditAuthInqRq
     */
    public org.sourceforge.ifx.framework.element.CreditAuthInqRq getCreditAuthInqRq() {
        return _creditAuthInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAuthAudRq _creditAuthAudRq;

    /** 
     * Setter for creditAuthAudRq
     * @param creditAuthAudRq the org.sourceforge.ifx.framework.element.CreditAuthAudRq to set
     */
    public void setCreditAuthAudRq(org.sourceforge.ifx.framework.element.CreditAuthAudRq _creditAuthAudRq) {
        this._creditAuthAudRq = _creditAuthAudRq;
    }

    /**
     * Getter for creditAuthAudRq
     * @return a org.sourceforge.ifx.framework.element.CreditAuthAudRq
     */
    public org.sourceforge.ifx.framework.element.CreditAuthAudRq getCreditAuthAudRq() {
        return _creditAuthAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAuthSyncRq _creditAuthSyncRq;

    /** 
     * Setter for creditAuthSyncRq
     * @param creditAuthSyncRq the org.sourceforge.ifx.framework.element.CreditAuthSyncRq to set
     */
    public void setCreditAuthSyncRq(org.sourceforge.ifx.framework.element.CreditAuthSyncRq _creditAuthSyncRq) {
        this._creditAuthSyncRq = _creditAuthSyncRq;
    }

    /**
     * Getter for creditAuthSyncRq
     * @return a org.sourceforge.ifx.framework.element.CreditAuthSyncRq
     */
    public org.sourceforge.ifx.framework.element.CreditAuthSyncRq getCreditAuthSyncRq() {
        return _creditAuthSyncRq;
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
       * CreditAuthAddRq
       * CreditAuthModRq
       * CreditAuthCanRq
       * CreditAuthInqRq
       * CreditAuthAudRq
       * CreditAuthSyncRq
       */
    public final String[] ELEMENTS = {
              "CreditAuthAddRq"
                 ,"CreditAuthModRq"
                 ,"CreditAuthCanRq"
                 ,"CreditAuthInqRq"
                 ,"CreditAuthAudRq"
                 ,"CreditAuthSyncRq"
          };
}
