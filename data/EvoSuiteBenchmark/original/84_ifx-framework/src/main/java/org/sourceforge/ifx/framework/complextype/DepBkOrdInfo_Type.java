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
public class DepBkOrdInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DepBkOrdInfo_Type() {
        super();
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
    private org.sourceforge.ifx.framework.element.DepBkStyleId _depBkStyleId;

    /** 
     * Setter for depBkStyleId
     * @param depBkStyleId the org.sourceforge.ifx.framework.element.DepBkStyleId to set
     */
    public void setDepBkStyleId(org.sourceforge.ifx.framework.element.DepBkStyleId _depBkStyleId) {
        this._depBkStyleId = _depBkStyleId;
    }

    /**
     * Getter for depBkStyleId
     * @return a org.sourceforge.ifx.framework.element.DepBkStyleId
     */
    public org.sourceforge.ifx.framework.element.DepBkStyleId getDepBkStyleId() {
        return _depBkStyleId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DeliveryMethod _deliveryMethod;

    /** 
     * Setter for deliveryMethod
     * @param deliveryMethod the org.sourceforge.ifx.framework.element.DeliveryMethod to set
     */
    public void setDeliveryMethod(org.sourceforge.ifx.framework.element.DeliveryMethod _deliveryMethod) {
        this._deliveryMethod = _deliveryMethod;
    }

    /**
     * Getter for deliveryMethod
     * @return a org.sourceforge.ifx.framework.element.DeliveryMethod
     */
    public org.sourceforge.ifx.framework.element.DeliveryMethod getDeliveryMethod() {
        return _deliveryMethod;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BaseEnvr _baseEnvr;

    /** 
     * Setter for baseEnvr
     * @param baseEnvr the org.sourceforge.ifx.framework.element.BaseEnvr to set
     */
    public void setBaseEnvr(org.sourceforge.ifx.framework.element.BaseEnvr _baseEnvr) {
        this._baseEnvr = _baseEnvr;
    }

    /**
     * Getter for baseEnvr
     * @return a org.sourceforge.ifx.framework.element.BaseEnvr
     */
    public org.sourceforge.ifx.framework.element.BaseEnvr getBaseEnvr() {
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
       * DepAcctId
       * Count
       * DepBkStyleId
       * DeliveryMethod
       * BaseEnvr
       */
    public final String[] ELEMENTS = {
              "DepAcctId"
                 ,"Count"
                 ,"DepBkStyleId"
                 ,"DeliveryMethod"
                 ,"BaseEnvr"
          };
}
