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
public class BankAcctTrnImgRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public BankAcctTrnImgRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TrnType _trnType;

    /** 
     * Setter for trnType
     * @param trnType the org.sourceforge.ifx.framework.element.TrnType to set
     */
    public void setTrnType(org.sourceforge.ifx.framework.element.TrnType _trnType) {
        this._trnType = _trnType;
    }

    /**
     * Getter for trnType
     * @return a org.sourceforge.ifx.framework.element.TrnType
     */
    public org.sourceforge.ifx.framework.element.TrnType getTrnType() {
        return _trnType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CurAmt _curAmt;

    /** 
     * Setter for curAmt
     * @param curAmt the org.sourceforge.ifx.framework.element.CurAmt to set
     */
    public void setCurAmt(org.sourceforge.ifx.framework.element.CurAmt _curAmt) {
        this._curAmt = _curAmt;
    }

    /**
     * Getter for curAmt
     * @return a org.sourceforge.ifx.framework.element.CurAmt
     */
    public org.sourceforge.ifx.framework.element.CurAmt getCurAmt() {
        return _curAmt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkNum _chkNum;

    /** 
     * Setter for chkNum
     * @param chkNum the org.sourceforge.ifx.framework.element.ChkNum to set
     */
    public void setChkNum(org.sourceforge.ifx.framework.element.ChkNum _chkNum) {
        this._chkNum = _chkNum;
    }

    /**
     * Getter for chkNum
     * @return a org.sourceforge.ifx.framework.element.ChkNum
     */
    public org.sourceforge.ifx.framework.element.ChkNum getChkNum() {
        return _chkNum;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RefInfo _refInfo;

    /** 
     * Setter for refInfo
     * @param refInfo the org.sourceforge.ifx.framework.element.RefInfo to set
     */
    public void setRefInfo(org.sourceforge.ifx.framework.element.RefInfo _refInfo) {
        this._refInfo = _refInfo;
    }

    /**
     * Getter for refInfo
     * @return a org.sourceforge.ifx.framework.element.RefInfo
     */
    public org.sourceforge.ifx.framework.element.RefInfo getRefInfo() {
        return _refInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PrcDt _prcDt;

    /** 
     * Setter for prcDt
     * @param prcDt the org.sourceforge.ifx.framework.element.PrcDt to set
     */
    public void setPrcDt(org.sourceforge.ifx.framework.element.PrcDt _prcDt) {
        this._prcDt = _prcDt;
    }

    /**
     * Getter for prcDt
     * @return a org.sourceforge.ifx.framework.element.PrcDt
     */
    public org.sourceforge.ifx.framework.element.PrcDt getPrcDt() {
        return _prcDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TrnImage _trnImage;

    /** 
     * Setter for trnImage
     * @param trnImage the org.sourceforge.ifx.framework.element.TrnImage to set
     */
    public void setTrnImage(org.sourceforge.ifx.framework.element.TrnImage _trnImage) {
        this._trnImage = _trnImage;
    }

    /**
     * Getter for trnImage
     * @return a org.sourceforge.ifx.framework.element.TrnImage
     */
    public org.sourceforge.ifx.framework.element.TrnImage getTrnImage() {
        return _trnImage;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ImageURL _imageURL;

    /** 
     * Setter for imageURL
     * @param imageURL the org.sourceforge.ifx.framework.element.ImageURL to set
     */
    public void setImageURL(org.sourceforge.ifx.framework.element.ImageURL _imageURL) {
        this._imageURL = _imageURL;
    }

    /**
     * Getter for imageURL
     * @return a org.sourceforge.ifx.framework.element.ImageURL
     */
    public org.sourceforge.ifx.framework.element.ImageURL getImageURL() {
        return _imageURL;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChkImg _chkImg;

    /** 
     * Setter for chkImg
     * @param chkImg the org.sourceforge.ifx.framework.element.ChkImg to set
     */
    public void setChkImg(org.sourceforge.ifx.framework.element.ChkImg _chkImg) {
        this._chkImg = _chkImg;
    }

    /**
     * Getter for chkImg
     * @return a org.sourceforge.ifx.framework.element.ChkImg
     */
    public org.sourceforge.ifx.framework.element.ChkImg getChkImg() {
        return _chkImg;
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
       * TrnType
       * CurAmt
       * ChkNum
       * RefInfo
       * PrcDt
       * TrnImage
       * ImageURL
       * ChkImg
       */
    public final String[] ELEMENTS = {
              "TrnType"
                 ,"CurAmt"
                 ,"ChkNum"
                 ,"RefInfo"
                 ,"PrcDt"
                 ,"TrnImage"
                 ,"ImageURL"
                 ,"ChkImg"
          };
}
