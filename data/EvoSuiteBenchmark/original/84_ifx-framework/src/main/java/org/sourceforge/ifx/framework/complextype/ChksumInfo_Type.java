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
public class ChksumInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public ChksumInfo_Type() {
        super();
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
    private org.sourceforge.ifx.framework.element.MsgType _msgType;

    /** 
     * Setter for msgType
     * @param msgType the org.sourceforge.ifx.framework.element.MsgType to set
     */
    public void setMsgType(org.sourceforge.ifx.framework.element.MsgType _msgType) {
        this._msgType = _msgType;
    }

    /**
     * Getter for msgType
     * @return a org.sourceforge.ifx.framework.element.MsgType
     */
    public org.sourceforge.ifx.framework.element.MsgType getMsgType() {
        return _msgType;
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
    private org.sourceforge.ifx.framework.element.Chksum[] _chksum;

    /** 
     * Setter for chksum
     * @param chksum the org.sourceforge.ifx.framework.element.Chksum[] to set
     */
    public void setChksum(org.sourceforge.ifx.framework.element.Chksum[] _chksum) {
        this._chksum = _chksum;
    }

    /**
     * Getter for chksum
     * @return a org.sourceforge.ifx.framework.element.Chksum[]
     */
    public org.sourceforge.ifx.framework.element.Chksum[] getChksum() {
        return _chksum;
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
       * RefInfo
       * MsgType
       * Count
       * Chksum
       */
    public final String[] ELEMENTS = {
              "RefInfo"
                 ,"MsgType"
                 ,"Count"
                 ,"Chksum"
          };
}
