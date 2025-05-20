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
public class RemitInstruction_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public RemitInstruction_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RemitRefId _remitRefId;

    /** 
     * Setter for remitRefId
     * @param remitRefId the org.sourceforge.ifx.framework.element.RemitRefId to set
     */
    public void setRemitRefId(org.sourceforge.ifx.framework.element.RemitRefId _remitRefId) {
        this._remitRefId = _remitRefId;
    }

    /**
     * Getter for remitRefId
     * @return a org.sourceforge.ifx.framework.element.RemitRefId
     */
    public org.sourceforge.ifx.framework.element.RemitRefId getRemitRefId() {
        return _remitRefId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PmtRefId _pmtRefId;

    /** 
     * Setter for pmtRefId
     * @param pmtRefId the org.sourceforge.ifx.framework.element.PmtRefId to set
     */
    public void setPmtRefId(org.sourceforge.ifx.framework.element.PmtRefId _pmtRefId) {
        this._pmtRefId = _pmtRefId;
    }

    /**
     * Getter for pmtRefId
     * @return a org.sourceforge.ifx.framework.element.PmtRefId
     */
    public org.sourceforge.ifx.framework.element.PmtRefId getPmtRefId() {
        return _pmtRefId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DeliveryInstruction _deliveryInstruction;

    /** 
     * Setter for deliveryInstruction
     * @param deliveryInstruction the org.sourceforge.ifx.framework.element.DeliveryInstruction to set
     */
    public void setDeliveryInstruction(org.sourceforge.ifx.framework.element.DeliveryInstruction _deliveryInstruction) {
        this._deliveryInstruction = _deliveryInstruction;
    }

    /**
     * Getter for deliveryInstruction
     * @return a org.sourceforge.ifx.framework.element.DeliveryInstruction
     */
    public org.sourceforge.ifx.framework.element.DeliveryInstruction getDeliveryInstruction() {
        return _deliveryInstruction;
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
    private org.sourceforge.ifx.framework.element.ContactInfo _contactInfo;

    /** 
     * Setter for contactInfo
     * @param contactInfo the org.sourceforge.ifx.framework.element.ContactInfo to set
     */
    public void setContactInfo(org.sourceforge.ifx.framework.element.ContactInfo _contactInfo) {
        this._contactInfo = _contactInfo;
    }

    /**
     * Getter for contactInfo
     * @return a org.sourceforge.ifx.framework.element.ContactInfo
     */
    public org.sourceforge.ifx.framework.element.ContactInfo getContactInfo() {
        return _contactInfo;
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
       * RemitRefId
       * PmtRefId
       * DeliveryInstruction
       * DeliveryMethod
       * ContactInfo
       */
    public final String[] ELEMENTS = {
              "RemitRefId"
                 ,"PmtRefId"
                 ,"DeliveryInstruction"
                 ,"DeliveryMethod"
                 ,"ContactInfo"
          };
}
