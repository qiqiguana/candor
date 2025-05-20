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
public class SignonICC_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public SignonICC_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AppPan _appPan;

    /** 
     * Setter for appPan
     * @param appPan the org.sourceforge.ifx.framework.element.AppPan to set
     */
    public void setAppPan(org.sourceforge.ifx.framework.element.AppPan _appPan) {
        this._appPan = _appPan;
    }

    /**
     * Getter for appPan
     * @return a org.sourceforge.ifx.framework.element.AppPan
     */
    public org.sourceforge.ifx.framework.element.AppPan getAppPan() {
        return _appPan;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AppPanSeq _appPanSeq;

    /** 
     * Setter for appPanSeq
     * @param appPanSeq the org.sourceforge.ifx.framework.element.AppPanSeq to set
     */
    public void setAppPanSeq(org.sourceforge.ifx.framework.element.AppPanSeq _appPanSeq) {
        this._appPanSeq = _appPanSeq;
    }

    /**
     * Getter for appPanSeq
     * @return a org.sourceforge.ifx.framework.element.AppPanSeq
     */
    public org.sourceforge.ifx.framework.element.AppPanSeq getAppPanSeq() {
        return _appPanSeq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AppId _appId;

    /** 
     * Setter for appId
     * @param appId the org.sourceforge.ifx.framework.element.AppId to set
     */
    public void setAppId(org.sourceforge.ifx.framework.element.AppId _appId) {
        this._appId = _appId;
    }

    /**
     * Getter for appId
     * @return a org.sourceforge.ifx.framework.element.AppId
     */
    public org.sourceforge.ifx.framework.element.AppId getAppId() {
        return _appId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PINBlock _pINBlock;

    /** 
     * Setter for pINBlock
     * @param pINBlock the org.sourceforge.ifx.framework.element.PINBlock to set
     */
    public void setPINBlock(org.sourceforge.ifx.framework.element.PINBlock _pINBlock) {
        this._pINBlock = _pINBlock;
    }

    /**
     * Getter for pINBlock
     * @return a org.sourceforge.ifx.framework.element.PINBlock
     */
    public org.sourceforge.ifx.framework.element.PINBlock getPINBlock() {
        return _pINBlock;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.GenSessKey _genSessKey;

    /** 
     * Setter for genSessKey
     * @param genSessKey the org.sourceforge.ifx.framework.element.GenSessKey to set
     */
    public void setGenSessKey(org.sourceforge.ifx.framework.element.GenSessKey _genSessKey) {
        this._genSessKey = _genSessKey;
    }

    /**
     * Getter for genSessKey
     * @return a org.sourceforge.ifx.framework.element.GenSessKey
     */
    public org.sourceforge.ifx.framework.element.GenSessKey getGenSessKey() {
        return _genSessKey;
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
       * AppPan
       * AppPanSeq
       * AppId
       * PINBlock
       * GenSessKey
       */
    public final String[] ELEMENTS = {
              "AppPan"
                 ,"AppPanSeq"
                 ,"AppId"
                 ,"PINBlock"
                 ,"GenSessKey"
          };
}
