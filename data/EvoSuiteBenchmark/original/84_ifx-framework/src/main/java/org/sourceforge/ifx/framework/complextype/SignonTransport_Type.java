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
public class SignonTransport_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public SignonTransport_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SignonRole _signonRole;

    /** 
     * Setter for signonRole
     * @param signonRole the org.sourceforge.ifx.framework.element.SignonRole to set
     */
    public void setSignonRole(org.sourceforge.ifx.framework.element.SignonRole _signonRole) {
        this._signonRole = _signonRole;
    }

    /**
     * Getter for signonRole
     * @return a org.sourceforge.ifx.framework.element.SignonRole
     */
    public org.sourceforge.ifx.framework.element.SignonRole getSignonRole() {
        return _signonRole;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.CustId _custId;

    /** 
     * Setter for custId
     * @param custId the org.sourceforge.ifx.framework.element.CustId to set
     */
    public void setCustId(org.sourceforge.ifx.framework.element.CustId _custId) {
        this._custId = _custId;
    }

    /**
     * Getter for custId
     * @return a org.sourceforge.ifx.framework.element.CustId
     */
    public org.sourceforge.ifx.framework.element.CustId getCustId() {
        return _custId;
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
       * SignonRole
       * CustId
       * GenSessKey
       */
    public final String[] ELEMENTS = {
              "SignonRole"
                 ,"CustId"
                 ,"GenSessKey"
          };
}
