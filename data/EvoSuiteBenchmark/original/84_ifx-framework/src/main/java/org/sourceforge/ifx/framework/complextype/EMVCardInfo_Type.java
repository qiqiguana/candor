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
public class EMVCardInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public EMVCardInfo_Type() {
        super();
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
    private org.sourceforge.ifx.framework.element.EMVRqData _eMVRqData;

    /** 
     * Setter for eMVRqData
     * @param eMVRqData the org.sourceforge.ifx.framework.element.EMVRqData to set
     */
    public void setEMVRqData(org.sourceforge.ifx.framework.element.EMVRqData _eMVRqData) {
        this._eMVRqData = _eMVRqData;
    }

    /**
     * Getter for eMVRqData
     * @return a org.sourceforge.ifx.framework.element.EMVRqData
     */
    public org.sourceforge.ifx.framework.element.EMVRqData getEMVRqData() {
        return _eMVRqData;
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
       * TrnRqUID
       * EMVRqData
       */
    public final String[] ELEMENTS = {
              "TrnRqUID"
                 ,"EMVRqData"
          };
}
