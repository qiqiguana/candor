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
public class DebitAuthInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DebitAuthInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DebitAuthType _debitAuthType;

    /** 
     * Setter for debitAuthType
     * @param debitAuthType the org.sourceforge.ifx.framework.element.DebitAuthType to set
     */
    public void setDebitAuthType(org.sourceforge.ifx.framework.element.DebitAuthType _debitAuthType) {
        this._debitAuthType = _debitAuthType;
    }

    /**
     * Getter for debitAuthType
     * @return a org.sourceforge.ifx.framework.element.DebitAuthType
     */
    public org.sourceforge.ifx.framework.element.DebitAuthType getDebitAuthType() {
        return _debitAuthType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CompositeCurAmt[] _compositeCurAmt;

    /** 
     * Setter for compositeCurAmt
     * @param compositeCurAmt the org.sourceforge.ifx.framework.element.CompositeCurAmt[] to set
     */
    public void setCompositeCurAmt(org.sourceforge.ifx.framework.element.CompositeCurAmt[] _compositeCurAmt) {
        this._compositeCurAmt = _compositeCurAmt;
    }

    /**
     * Getter for compositeCurAmt
     * @return a org.sourceforge.ifx.framework.element.CompositeCurAmt[]
     */
    public org.sourceforge.ifx.framework.element.CompositeCurAmt[] getCompositeCurAmt() {
        return _compositeCurAmt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAcctId[] _depAcctId;

    /** 
     * Setter for depAcctId
     * @param depAcctId the org.sourceforge.ifx.framework.element.DepAcctId[] to set
     */
    public void setDepAcctId(org.sourceforge.ifx.framework.element.DepAcctId[] _depAcctId) {
        this._depAcctId = _depAcctId;
    }

    /**
     * Getter for depAcctId
     * @return a org.sourceforge.ifx.framework.element.DepAcctId[]
     */
    public org.sourceforge.ifx.framework.element.DepAcctId[] getDepAcctId() {
        return _depAcctId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CardAcctId[] _cardAcctId;

    /** 
     * Setter for cardAcctId
     * @param cardAcctId the org.sourceforge.ifx.framework.element.CardAcctId[] to set
     */
    public void setCardAcctId(org.sourceforge.ifx.framework.element.CardAcctId[] _cardAcctId) {
        this._cardAcctId = _cardAcctId;
    }

    /**
     * Getter for cardAcctId
     * @return a org.sourceforge.ifx.framework.element.CardAcctId[]
     */
    public org.sourceforge.ifx.framework.element.CardAcctId[] getCardAcctId() {
        return _cardAcctId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BaseEnvr[] _baseEnvr;

    /** 
     * Setter for baseEnvr
     * @param baseEnvr the org.sourceforge.ifx.framework.element.BaseEnvr[] to set
     */
    public void setBaseEnvr(org.sourceforge.ifx.framework.element.BaseEnvr[] _baseEnvr) {
        this._baseEnvr = _baseEnvr;
    }

    /**
     * Getter for baseEnvr
     * @return a org.sourceforge.ifx.framework.element.BaseEnvr[]
     */
    public org.sourceforge.ifx.framework.element.BaseEnvr[] getBaseEnvr() {
        return _baseEnvr;
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
       * DebitAuthType
       * CompositeCurAmt
       * DepAcctId
       * CardAcctId
       * BaseEnvr
       */
    public final String[] ELEMENTS = {
              "DebitAuthType"
                 ,"CompositeCurAmt"
                 ,"DepAcctId"
                 ,"CardAcctId"
                 ,"BaseEnvr"
          };
}
