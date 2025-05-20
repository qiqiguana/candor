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
public class MediaAcctAdjInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public MediaAcctAdjInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MediaAcctId _mediaAcctId;

    /** 
     * Setter for mediaAcctId
     * @param mediaAcctId the org.sourceforge.ifx.framework.element.MediaAcctId to set
     */
    public void setMediaAcctId(org.sourceforge.ifx.framework.element.MediaAcctId _mediaAcctId) {
        this._mediaAcctId = _mediaAcctId;
    }

    /**
     * Getter for mediaAcctId
     * @return a org.sourceforge.ifx.framework.element.MediaAcctId
     */
    public org.sourceforge.ifx.framework.element.MediaAcctId getMediaAcctId() {
        return _mediaAcctId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MediaTrnType _mediaTrnType;

    /** 
     * Setter for mediaTrnType
     * @param mediaTrnType the org.sourceforge.ifx.framework.element.MediaTrnType to set
     */
    public void setMediaTrnType(org.sourceforge.ifx.framework.element.MediaTrnType _mediaTrnType) {
        this._mediaTrnType = _mediaTrnType;
    }

    /**
     * Getter for mediaTrnType
     * @return a org.sourceforge.ifx.framework.element.MediaTrnType
     */
    public org.sourceforge.ifx.framework.element.MediaTrnType getMediaTrnType() {
        return _mediaTrnType;
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
    private org.sourceforge.ifx.framework.element.TrnAuthId _trnAuthId;

    /** 
     * Setter for trnAuthId
     * @param trnAuthId the org.sourceforge.ifx.framework.element.TrnAuthId to set
     */
    public void setTrnAuthId(org.sourceforge.ifx.framework.element.TrnAuthId _trnAuthId) {
        this._trnAuthId = _trnAuthId;
    }

    /**
     * Getter for trnAuthId
     * @return a org.sourceforge.ifx.framework.element.TrnAuthId
     */
    public org.sourceforge.ifx.framework.element.TrnAuthId getTrnAuthId() {
        return _trnAuthId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.TrnRqUID _trnRqUID;

    /** 
     * Setter for trnRqUID
     * @param trnRqUID the org.sourceforge.ifx.framework.element.TrnRqUID to set
     */
    public void setTrnRqUID(org.sourceforge.ifx.framework.element.TrnRqUID _trnRqUID) {
        this._trnRqUID = _trnRqUID;
    }

    /**
     * Getter for trnRqUID
     * @return a org.sourceforge.ifx.framework.element.TrnRqUID
     */
    public org.sourceforge.ifx.framework.element.TrnRqUID getTrnRqUID() {
        return _trnRqUID;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RelatedCreditId _relatedCreditId;

    /** 
     * Setter for relatedCreditId
     * @param relatedCreditId the org.sourceforge.ifx.framework.element.RelatedCreditId to set
     */
    public void setRelatedCreditId(org.sourceforge.ifx.framework.element.RelatedCreditId _relatedCreditId) {
        this._relatedCreditId = _relatedCreditId;
    }

    /**
     * Getter for relatedCreditId
     * @return a org.sourceforge.ifx.framework.element.RelatedCreditId
     */
    public org.sourceforge.ifx.framework.element.RelatedCreditId getRelatedCreditId() {
        return _relatedCreditId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MediaItem[] _mediaItem;

    /** 
     * Setter for mediaItem
     * @param mediaItem the org.sourceforge.ifx.framework.element.MediaItem[] to set
     */
    public void setMediaItem(org.sourceforge.ifx.framework.element.MediaItem[] _mediaItem) {
        this._mediaItem = _mediaItem;
    }

    /**
     * Getter for mediaItem
     * @return a org.sourceforge.ifx.framework.element.MediaItem[]
     */
    public org.sourceforge.ifx.framework.element.MediaItem[] getMediaItem() {
        return _mediaItem;
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
       * MediaAcctId
       * MediaTrnType
       * CurAmt
       * TrnAuthId
       * TrnRqUID
       * RelatedCreditId
       * MediaItem
       * BaseEnvr
       */
    public final String[] ELEMENTS = {
              "MediaAcctId"
                 ,"MediaTrnType"
                 ,"CurAmt"
                 ,"TrnAuthId"
                 ,"TrnRqUID"
                 ,"RelatedCreditId"
                 ,"MediaItem"
                 ,"BaseEnvr"
          };
}
