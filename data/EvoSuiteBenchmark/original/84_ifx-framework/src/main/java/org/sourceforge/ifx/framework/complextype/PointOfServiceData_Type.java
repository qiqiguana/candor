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
public class PointOfServiceData_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PointOfServiceData_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Environment _environment;

    /** 
     * Setter for environment
     * @param environment the org.sourceforge.ifx.framework.element.Environment to set
     */
    public void setEnvironment(org.sourceforge.ifx.framework.element.Environment _environment) {
        this._environment = _environment;
    }

    /**
     * Getter for environment
     * @return a org.sourceforge.ifx.framework.element.Environment
     */
    public org.sourceforge.ifx.framework.element.Environment getEnvironment() {
        return _environment;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.POSCapabilities _pOSCapabilities;

    /** 
     * Setter for pOSCapabilities
     * @param pOSCapabilities the org.sourceforge.ifx.framework.element.POSCapabilities to set
     */
    public void setPOSCapabilities(org.sourceforge.ifx.framework.element.POSCapabilities _pOSCapabilities) {
        this._pOSCapabilities = _pOSCapabilities;
    }

    /**
     * Getter for pOSCapabilities
     * @return a org.sourceforge.ifx.framework.element.POSCapabilities
     */
    public org.sourceforge.ifx.framework.element.POSCapabilities getPOSCapabilities() {
        return _pOSCapabilities;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.POSSecurity _pOSSecurity;

    /** 
     * Setter for pOSSecurity
     * @param pOSSecurity the org.sourceforge.ifx.framework.element.POSSecurity to set
     */
    public void setPOSSecurity(org.sourceforge.ifx.framework.element.POSSecurity _pOSSecurity) {
        this._pOSSecurity = _pOSSecurity;
    }

    /**
     * Getter for pOSSecurity
     * @return a org.sourceforge.ifx.framework.element.POSSecurity
     */
    public org.sourceforge.ifx.framework.element.POSSecurity getPOSSecurity() {
        return _pOSSecurity;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.POSAgent _pOSAgent;

    /** 
     * Setter for pOSAgent
     * @param pOSAgent the org.sourceforge.ifx.framework.element.POSAgent to set
     */
    public void setPOSAgent(org.sourceforge.ifx.framework.element.POSAgent _pOSAgent) {
        this._pOSAgent = _pOSAgent;
    }

    /**
     * Getter for pOSAgent
     * @return a org.sourceforge.ifx.framework.element.POSAgent
     */
    public org.sourceforge.ifx.framework.element.POSAgent getPOSAgent() {
        return _pOSAgent;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.IdentCharacterData _identCharacterData;

    /** 
     * Setter for identCharacterData
     * @param identCharacterData the org.sourceforge.ifx.framework.element.IdentCharacterData to set
     */
    public void setIdentCharacterData(org.sourceforge.ifx.framework.element.IdentCharacterData _identCharacterData) {
        this._identCharacterData = _identCharacterData;
    }

    /**
     * Getter for identCharacterData
     * @return a org.sourceforge.ifx.framework.element.IdentCharacterData
     */
    public org.sourceforge.ifx.framework.element.IdentCharacterData getIdentCharacterData() {
        return _identCharacterData;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.POSLocation _pOSLocation;

    /** 
     * Setter for pOSLocation
     * @param pOSLocation the org.sourceforge.ifx.framework.element.POSLocation to set
     */
    public void setPOSLocation(org.sourceforge.ifx.framework.element.POSLocation _pOSLocation) {
        this._pOSLocation = _pOSLocation;
    }

    /**
     * Getter for pOSLocation
     * @return a org.sourceforge.ifx.framework.element.POSLocation
     */
    public org.sourceforge.ifx.framework.element.POSLocation getPOSLocation() {
        return _pOSLocation;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PostingSessionId _postingSessionId;

    /** 
     * Setter for postingSessionId
     * @param postingSessionId the org.sourceforge.ifx.framework.element.PostingSessionId to set
     */
    public void setPostingSessionId(org.sourceforge.ifx.framework.element.PostingSessionId _postingSessionId) {
        this._postingSessionId = _postingSessionId;
    }

    /**
     * Getter for postingSessionId
     * @return a org.sourceforge.ifx.framework.element.PostingSessionId
     */
    public org.sourceforge.ifx.framework.element.PostingSessionId getPostingSessionId() {
        return _postingSessionId;
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
       * Environment
       * POSCapabilities
       * POSSecurity
       * POSAgent
       * IdentCharacterData
       * POSLocation
       * PostingSessionId
       */
    public final String[] ELEMENTS = {
              "Environment"
                 ,"POSCapabilities"
                 ,"POSSecurity"
                 ,"POSAgent"
                 ,"IdentCharacterData"
                 ,"POSLocation"
                 ,"PostingSessionId"
          };
}
