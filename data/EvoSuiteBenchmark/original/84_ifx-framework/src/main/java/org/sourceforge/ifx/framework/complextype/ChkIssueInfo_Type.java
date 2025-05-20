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
public class ChkIssueInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public ChkIssueInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DepAcctId _depAcctId;

    /** 
     * Setter for depAcctId
     * @param depAcctId the org.sourceforge.ifx.framework.element.DepAcctId to set
     */
    public void setDepAcctId(org.sourceforge.ifx.framework.element.DepAcctId _depAcctId) {
        this._depAcctId = _depAcctId;
    }

    /**
     * Getter for depAcctId
     * @return a org.sourceforge.ifx.framework.element.DepAcctId
     */
    public org.sourceforge.ifx.framework.element.DepAcctId getDepAcctId() {
        return _depAcctId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.EffDt _effDt;

    /** 
     * Setter for effDt
     * @param effDt the org.sourceforge.ifx.framework.element.EffDt to set
     */
    public void setEffDt(org.sourceforge.ifx.framework.element.EffDt _effDt) {
        this._effDt = _effDt;
    }

    /**
     * Getter for effDt
     * @return a org.sourceforge.ifx.framework.element.EffDt
     */
    public org.sourceforge.ifx.framework.element.EffDt getEffDt() {
        return _effDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RefInfo[] _refInfo;

    /** 
     * Setter for refInfo
     * @param refInfo the org.sourceforge.ifx.framework.element.RefInfo[] to set
     */
    public void setRefInfo(org.sourceforge.ifx.framework.element.RefInfo[] _refInfo) {
        this._refInfo = _refInfo;
    }

    /**
     * Getter for refInfo
     * @return a org.sourceforge.ifx.framework.element.RefInfo[]
     */
    public org.sourceforge.ifx.framework.element.RefInfo[] getRefInfo() {
        return _refInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkInfo[] _chkInfo;

    /** 
     * Setter for chkInfo
     * @param chkInfo the org.sourceforge.ifx.framework.element.ChkInfo[] to set
     */
    public void setChkInfo(org.sourceforge.ifx.framework.element.ChkInfo[] _chkInfo) {
        this._chkInfo = _chkInfo;
    }

    /**
     * Getter for chkInfo
     * @return a org.sourceforge.ifx.framework.element.ChkInfo[]
     */
    public org.sourceforge.ifx.framework.element.ChkInfo[] getChkInfo() {
        return _chkInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CurAmt[] _curAmt;

    /** 
     * Setter for curAmt
     * @param curAmt the org.sourceforge.ifx.framework.element.CurAmt[] to set
     */
    public void setCurAmt(org.sourceforge.ifx.framework.element.CurAmt[] _curAmt) {
        this._curAmt = _curAmt;
    }

    /**
     * Getter for curAmt
     * @return a org.sourceforge.ifx.framework.element.CurAmt[]
     */
    public org.sourceforge.ifx.framework.element.CurAmt[] getCurAmt() {
        return _curAmt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DueDt[] _dueDt;

    /** 
     * Setter for dueDt
     * @param dueDt the org.sourceforge.ifx.framework.element.DueDt[] to set
     */
    public void setDueDt(org.sourceforge.ifx.framework.element.DueDt[] _dueDt) {
        this._dueDt = _dueDt;
    }

    /**
     * Getter for dueDt
     * @return a org.sourceforge.ifx.framework.element.DueDt[]
     */
    public org.sourceforge.ifx.framework.element.DueDt[] getDueDt() {
        return _dueDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PaidDt[] _paidDt;

    /** 
     * Setter for paidDt
     * @param paidDt the org.sourceforge.ifx.framework.element.PaidDt[] to set
     */
    public void setPaidDt(org.sourceforge.ifx.framework.element.PaidDt[] _paidDt) {
        this._paidDt = _paidDt;
    }

    /**
     * Getter for paidDt
     * @return a org.sourceforge.ifx.framework.element.PaidDt[]
     */
    public org.sourceforge.ifx.framework.element.PaidDt[] getPaidDt() {
        return _paidDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ImageURL[] _imageURL;

    /** 
     * Setter for imageURL
     * @param imageURL the org.sourceforge.ifx.framework.element.ImageURL[] to set
     */
    public void setImageURL(org.sourceforge.ifx.framework.element.ImageURL[] _imageURL) {
        this._imageURL = _imageURL;
    }

    /**
     * Getter for imageURL
     * @return a org.sourceforge.ifx.framework.element.ImageURL[]
     */
    public org.sourceforge.ifx.framework.element.ImageURL[] getImageURL() {
        return _imageURL;
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
       * DepAcctId
       * EffDt
       * RefInfo
       * ChkInfo
       * CurAmt
       * DueDt
       * PaidDt
       * ImageURL
       */
    public final String[] ELEMENTS = {
              "DepAcctId"
                 ,"EffDt"
                 ,"RefInfo"
                 ,"ChkInfo"
                 ,"CurAmt"
                 ,"DueDt"
                 ,"PaidDt"
                 ,"ImageURL"
          };
}
