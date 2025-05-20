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
public class CompRemitStmtInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public CompRemitStmtInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.LockboxDepId _lockboxDepId;

    /** 
     * Setter for lockboxDepId
     * @param lockboxDepId the org.sourceforge.ifx.framework.element.LockboxDepId to set
     */
    public void setLockboxDepId(org.sourceforge.ifx.framework.element.LockboxDepId _lockboxDepId) {
        this._lockboxDepId = _lockboxDepId;
    }

    /**
     * Getter for lockboxDepId
     * @return a org.sourceforge.ifx.framework.element.LockboxDepId
     */
    public org.sourceforge.ifx.framework.element.LockboxDepId getLockboxDepId() {
        return _lockboxDepId;
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
    private org.sourceforge.ifx.framework.element.Count _count;

    /** 
     * Setter for count
     * @param count the org.sourceforge.ifx.framework.element.Count to set
     */
    public void setCount(org.sourceforge.ifx.framework.element.Count _count) {
        this._count = _count;
    }

    /**
     * Getter for count
     * @return a org.sourceforge.ifx.framework.element.Count
     */
    public org.sourceforge.ifx.framework.element.Count getCount() {
        return _count;
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
    private org.sourceforge.ifx.framework.element.BatchInfo[] _batchInfo;

    /** 
     * Setter for batchInfo
     * @param batchInfo the org.sourceforge.ifx.framework.element.BatchInfo[] to set
     */
    public void setBatchInfo(org.sourceforge.ifx.framework.element.BatchInfo[] _batchInfo) {
        this._batchInfo = _batchInfo;
    }

    /**
     * Getter for batchInfo
     * @return a org.sourceforge.ifx.framework.element.BatchInfo[]
     */
    public org.sourceforge.ifx.framework.element.BatchInfo[] getBatchInfo() {
        return _batchInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.LockboxURL[] _lockboxURL;

    /** 
     * Setter for lockboxURL
     * @param lockboxURL the org.sourceforge.ifx.framework.element.LockboxURL[] to set
     */
    public void setLockboxURL(org.sourceforge.ifx.framework.element.LockboxURL[] _lockboxURL) {
        this._lockboxURL = _lockboxURL;
    }

    /**
     * Getter for lockboxURL
     * @return a org.sourceforge.ifx.framework.element.LockboxURL[]
     */
    public org.sourceforge.ifx.framework.element.LockboxURL[] getLockboxURL() {
        return _lockboxURL;
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
       * LockboxDepId
       * DepAcctId
       * CurAmt
       * Count
       * RefInfo
       * PrcDt
       * BatchInfo
       * LockboxURL
       */
    public final String[] ELEMENTS = {
              "LockboxDepId"
                 ,"DepAcctId"
                 ,"CurAmt"
                 ,"Count"
                 ,"RefInfo"
                 ,"PrcDt"
                 ,"BatchInfo"
                 ,"LockboxURL"
          };
}
