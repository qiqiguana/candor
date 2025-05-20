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
public class PresSvcProfInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PresSvcProfInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SvcCore _svcCore;

    /** 
     * Setter for svcCore
     * @param svcCore the org.sourceforge.ifx.framework.element.SvcCore to set
     */
    public void setSvcCore(org.sourceforge.ifx.framework.element.SvcCore _svcCore) {
        this._svcCore = _svcCore;
    }

    /**
     * Getter for svcCore
     * @return a org.sourceforge.ifx.framework.element.SvcCore
     */
    public org.sourceforge.ifx.framework.element.SvcCore getSvcCore() {
        return _svcCore;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MsgSupt[] _msgSupt;

    /** 
     * Setter for msgSupt
     * @param msgSupt the org.sourceforge.ifx.framework.element.MsgSupt[] to set
     */
    public void setMsgSupt(org.sourceforge.ifx.framework.element.MsgSupt[] _msgSupt) {
        this._msgSupt = _msgSupt;
    }

    /**
     * Getter for msgSupt
     * @return a org.sourceforge.ifx.framework.element.MsgSupt[]
     */
    public org.sourceforge.ifx.framework.element.MsgSupt[] getMsgSupt() {
        return _msgSupt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.OptSupt[] _optSupt;

    /** 
     * Setter for optSupt
     * @param optSupt the org.sourceforge.ifx.framework.element.OptSupt[] to set
     */
    public void setOptSupt(org.sourceforge.ifx.framework.element.OptSupt[] _optSupt) {
        this._optSupt = _optSupt;
    }

    /**
     * Getter for optSupt
     * @return a org.sourceforge.ifx.framework.element.OptSupt[]
     */
    public org.sourceforge.ifx.framework.element.OptSupt[] getOptSupt() {
        return _optSupt;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PrcSched[] _prcSched;

    /** 
     * Setter for prcSched
     * @param prcSched the org.sourceforge.ifx.framework.element.PrcSched[] to set
     */
    public void setPrcSched(org.sourceforge.ifx.framework.element.PrcSched[] _prcSched) {
        this._prcSched = _prcSched;
    }

    /**
     * Getter for prcSched
     * @return a org.sourceforge.ifx.framework.element.PrcSched[]
     */
    public org.sourceforge.ifx.framework.element.PrcSched[] getPrcSched() {
        return _prcSched;
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
       * SvcCore
       * MsgSupt
       * OptSupt
       * PrcSched
       */
    public final String[] ELEMENTS = {
              "SvcCore"
                 ,"MsgSupt"
                 ,"OptSupt"
                 ,"PrcSched"
          };
}
