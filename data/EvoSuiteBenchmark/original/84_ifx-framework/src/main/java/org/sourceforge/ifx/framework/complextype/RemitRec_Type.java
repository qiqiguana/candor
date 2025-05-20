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
public class RemitRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public RemitRec_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RemitId _remitId;

    /** 
     * Setter for remitId
     * @param remitId the org.sourceforge.ifx.framework.element.RemitId to set
     */
    public void setRemitId(org.sourceforge.ifx.framework.element.RemitId _remitId) {
        this._remitId = _remitId;
    }

    /**
     * Getter for remitId
     * @return a org.sourceforge.ifx.framework.element.RemitId
     */
    public org.sourceforge.ifx.framework.element.RemitId getRemitId() {
        return _remitId;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RemitInfo _remitInfo;

    /** 
     * Setter for remitInfo
     * @param remitInfo the org.sourceforge.ifx.framework.element.RemitInfo to set
     */
    public void setRemitInfo(org.sourceforge.ifx.framework.element.RemitInfo _remitInfo) {
        this._remitInfo = _remitInfo;
    }

    /**
     * Getter for remitInfo
     * @return a org.sourceforge.ifx.framework.element.RemitInfo
     */
    public org.sourceforge.ifx.framework.element.RemitInfo getRemitInfo() {
        return _remitInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.RemitStatus _remitStatus;

    /** 
     * Setter for remitStatus
     * @param remitStatus the org.sourceforge.ifx.framework.element.RemitStatus to set
     */
    public void setRemitStatus(org.sourceforge.ifx.framework.element.RemitStatus _remitStatus) {
        this._remitStatus = _remitStatus;
    }

    /**
     * Getter for remitStatus
     * @return a org.sourceforge.ifx.framework.element.RemitStatus
     */
    public org.sourceforge.ifx.framework.element.RemitStatus getRemitStatus() {
        return _remitStatus;
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
       * RemitId
       * RemitInfo
       * RemitStatus
       */
    public final String[] ELEMENTS = {
              "RemitId"
                 ,"RemitInfo"
                 ,"RemitStatus"
          };
}
