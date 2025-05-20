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
public class CreditMsgRqInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CreditMsgRqInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAddRq _creditAddRq;

    /** 
     * Setter for creditAddRq
     * @param creditAddRq the org.sourceforge.ifx.framework.element.CreditAddRq to set
     */
    public void setCreditAddRq(org.sourceforge.ifx.framework.element.CreditAddRq _creditAddRq) {
        this._creditAddRq = _creditAddRq;
    }

    /**
     * Getter for creditAddRq
     * @return a org.sourceforge.ifx.framework.element.CreditAddRq
     */
    public org.sourceforge.ifx.framework.element.CreditAddRq getCreditAddRq() {
        return _creditAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditModRq _creditModRq;

    /** 
     * Setter for creditModRq
     * @param creditModRq the org.sourceforge.ifx.framework.element.CreditModRq to set
     */
    public void setCreditModRq(org.sourceforge.ifx.framework.element.CreditModRq _creditModRq) {
        this._creditModRq = _creditModRq;
    }

    /**
     * Getter for creditModRq
     * @return a org.sourceforge.ifx.framework.element.CreditModRq
     */
    public org.sourceforge.ifx.framework.element.CreditModRq getCreditModRq() {
        return _creditModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditStatusModRq _creditStatusModRq;

    /** 
     * Setter for creditStatusModRq
     * @param creditStatusModRq the org.sourceforge.ifx.framework.element.CreditStatusModRq to set
     */
    public void setCreditStatusModRq(org.sourceforge.ifx.framework.element.CreditStatusModRq _creditStatusModRq) {
        this._creditStatusModRq = _creditStatusModRq;
    }

    /**
     * Getter for creditStatusModRq
     * @return a org.sourceforge.ifx.framework.element.CreditStatusModRq
     */
    public org.sourceforge.ifx.framework.element.CreditStatusModRq getCreditStatusModRq() {
        return _creditStatusModRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditCanRq _creditCanRq;

    /** 
     * Setter for creditCanRq
     * @param creditCanRq the org.sourceforge.ifx.framework.element.CreditCanRq to set
     */
    public void setCreditCanRq(org.sourceforge.ifx.framework.element.CreditCanRq _creditCanRq) {
        this._creditCanRq = _creditCanRq;
    }

    /**
     * Getter for creditCanRq
     * @return a org.sourceforge.ifx.framework.element.CreditCanRq
     */
    public org.sourceforge.ifx.framework.element.CreditCanRq getCreditCanRq() {
        return _creditCanRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditInqRq _creditInqRq;

    /** 
     * Setter for creditInqRq
     * @param creditInqRq the org.sourceforge.ifx.framework.element.CreditInqRq to set
     */
    public void setCreditInqRq(org.sourceforge.ifx.framework.element.CreditInqRq _creditInqRq) {
        this._creditInqRq = _creditInqRq;
    }

    /**
     * Getter for creditInqRq
     * @return a org.sourceforge.ifx.framework.element.CreditInqRq
     */
    public org.sourceforge.ifx.framework.element.CreditInqRq getCreditInqRq() {
        return _creditInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditAudRq _creditAudRq;

    /** 
     * Setter for creditAudRq
     * @param creditAudRq the org.sourceforge.ifx.framework.element.CreditAudRq to set
     */
    public void setCreditAudRq(org.sourceforge.ifx.framework.element.CreditAudRq _creditAudRq) {
        this._creditAudRq = _creditAudRq;
    }

    /**
     * Getter for creditAudRq
     * @return a org.sourceforge.ifx.framework.element.CreditAudRq
     */
    public org.sourceforge.ifx.framework.element.CreditAudRq getCreditAudRq() {
        return _creditAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CreditSyncRq _creditSyncRq;

    /** 
     * Setter for creditSyncRq
     * @param creditSyncRq the org.sourceforge.ifx.framework.element.CreditSyncRq to set
     */
    public void setCreditSyncRq(org.sourceforge.ifx.framework.element.CreditSyncRq _creditSyncRq) {
        this._creditSyncRq = _creditSyncRq;
    }

    /**
     * Getter for creditSyncRq
     * @return a org.sourceforge.ifx.framework.element.CreditSyncRq
     */
    public org.sourceforge.ifx.framework.element.CreditSyncRq getCreditSyncRq() {
        return _creditSyncRq;
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
       * CreditAddRq
       * CreditModRq
       * CreditStatusModRq
       * CreditCanRq
       * CreditInqRq
       * CreditAudRq
       * CreditSyncRq
       */
    public final String[] ELEMENTS = {
              "CreditAddRq"
                 ,"CreditModRq"
                 ,"CreditStatusModRq"
                 ,"CreditCanRq"
                 ,"CreditInqRq"
                 ,"CreditAudRq"
                 ,"CreditSyncRq"
          };
}
