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
public class BankAcctStmtImgInqRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public BankAcctStmtImgInqRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StartDt _startDt;

    /** 
     * Setter for startDt
     * @param startDt the org.sourceforge.ifx.framework.element.StartDt to set
     */
    public void setStartDt(org.sourceforge.ifx.framework.element.StartDt _startDt) {
        this._startDt = _startDt;
    }

    /**
     * Getter for startDt
     * @return a org.sourceforge.ifx.framework.element.StartDt
     */
    public org.sourceforge.ifx.framework.element.StartDt getStartDt() {
        return _startDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.EndDt _endDt;

    /** 
     * Setter for endDt
     * @param endDt the org.sourceforge.ifx.framework.element.EndDt to set
     */
    public void setEndDt(org.sourceforge.ifx.framework.element.EndDt _endDt) {
        this._endDt = _endDt;
    }

    /**
     * Getter for endDt
     * @return a org.sourceforge.ifx.framework.element.EndDt
     */
    public org.sourceforge.ifx.framework.element.EndDt getEndDt() {
        return _endDt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.StmtImg _stmtImg;

    /** 
     * Setter for stmtImg
     * @param stmtImg the org.sourceforge.ifx.framework.element.StmtImg to set
     */
    public void setStmtImg(org.sourceforge.ifx.framework.element.StmtImg _stmtImg) {
        this._stmtImg = _stmtImg;
    }

    /**
     * Getter for stmtImg
     * @return a org.sourceforge.ifx.framework.element.StmtImg
     */
    public org.sourceforge.ifx.framework.element.StmtImg getStmtImg() {
        return _stmtImg;
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


    /**
     * Returns true if objects are equal, false otherwise.
     * @param obj the object to compare with.
     * @return true if equal, false if not.
     */
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /** Element ordering - 
       * StartDt
       * EndDt
       * StmtImg
       * ImageURL
       */
    public final String[] ELEMENTS = {
              "StartDt"
                 ,"EndDt"
                 ,"StmtImg"
                 ,"ImageURL"
          };
}
