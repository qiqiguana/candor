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
public class PmtAckRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PmtAckRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SvcRqUID _svcRqUID;

    /** 
     * Setter for svcRqUID
     * @param svcRqUID the org.sourceforge.ifx.framework.element.SvcRqUID to set
     */
    public void setSvcRqUID(org.sourceforge.ifx.framework.element.SvcRqUID _svcRqUID) {
        this._svcRqUID = _svcRqUID;
    }

    /**
     * Getter for svcRqUID
     * @return a org.sourceforge.ifx.framework.element.SvcRqUID
     */
    public org.sourceforge.ifx.framework.element.SvcRqUID getSvcRqUID() {
        return _svcRqUID;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtAckInfo[] _pmtAckInfo;

    /** 
     * Setter for pmtAckInfo
     * @param pmtAckInfo the org.sourceforge.ifx.framework.element.PmtAckInfo[] to set
     */
    public void setPmtAckInfo(org.sourceforge.ifx.framework.element.PmtAckInfo[] _pmtAckInfo) {
        this._pmtAckInfo = _pmtAckInfo;
    }

    /**
     * Getter for pmtAckInfo
     * @return a org.sourceforge.ifx.framework.element.PmtAckInfo[]
     */
    public org.sourceforge.ifx.framework.element.PmtAckInfo[] getPmtAckInfo() {
        return _pmtAckInfo;
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
       * SvcRqUID
       * PmtAckInfo
       */
    public final String[] ELEMENTS = {
              "SvcRqUID"
                 ,"PmtAckInfo"
          };
}
