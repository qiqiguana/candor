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
public class ChksumRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public ChksumRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChksumId _chksumId;

    /** 
     * Setter for chksumId
     * @param chksumId the org.sourceforge.ifx.framework.element.ChksumId to set
     */
    public void setChksumId(org.sourceforge.ifx.framework.element.ChksumId _chksumId) {
        this._chksumId = _chksumId;
    }

    /**
     * Getter for chksumId
     * @return a org.sourceforge.ifx.framework.element.ChksumId
     */
    public org.sourceforge.ifx.framework.element.ChksumId getChksumId() {
        return _chksumId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChksumInfo _chksumInfo;

    /** 
     * Setter for chksumInfo
     * @param chksumInfo the org.sourceforge.ifx.framework.element.ChksumInfo to set
     */
    public void setChksumInfo(org.sourceforge.ifx.framework.element.ChksumInfo _chksumInfo) {
        this._chksumInfo = _chksumInfo;
    }

    /**
     * Getter for chksumInfo
     * @return a org.sourceforge.ifx.framework.element.ChksumInfo
     */
    public org.sourceforge.ifx.framework.element.ChksumInfo getChksumInfo() {
        return _chksumInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ChksumStatus _chksumStatus;

    /** 
     * Setter for chksumStatus
     * @param chksumStatus the org.sourceforge.ifx.framework.element.ChksumStatus to set
     */
    public void setChksumStatus(org.sourceforge.ifx.framework.element.ChksumStatus _chksumStatus) {
        this._chksumStatus = _chksumStatus;
    }

    /**
     * Getter for chksumStatus
     * @return a org.sourceforge.ifx.framework.element.ChksumStatus
     */
    public org.sourceforge.ifx.framework.element.ChksumStatus getChksumStatus() {
        return _chksumStatus;
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
       * ChksumId
       * ChksumInfo
       * ChksumStatus
       */
    public final String[] ELEMENTS = {
              "ChksumId"
                 ,"ChksumInfo"
                 ,"ChksumStatus"
          };
}
