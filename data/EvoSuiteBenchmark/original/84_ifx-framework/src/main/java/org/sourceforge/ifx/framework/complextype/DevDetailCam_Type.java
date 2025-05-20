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
public class DevDetailCam_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DevDetailCam_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevCamType _devCamType;

    /** 
     * Setter for devCamType
     * @param devCamType the org.sourceforge.ifx.framework.element.DevCamType to set
     */
    public void setDevCamType(org.sourceforge.ifx.framework.element.DevCamType _devCamType) {
        this._devCamType = _devCamType;
    }

    /**
     * Getter for devCamType
     * @return a org.sourceforge.ifx.framework.element.DevCamType
     */
    public org.sourceforge.ifx.framework.element.DevCamType getDevCamType() {
        return _devCamType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevCamOperation _devCamOperation;

    /** 
     * Setter for devCamOperation
     * @param devCamOperation the org.sourceforge.ifx.framework.element.DevCamOperation to set
     */
    public void setDevCamOperation(org.sourceforge.ifx.framework.element.DevCamOperation _devCamOperation) {
        this._devCamOperation = _devCamOperation;
    }

    /**
     * Getter for devCamOperation
     * @return a org.sourceforge.ifx.framework.element.DevCamOperation
     */
    public org.sourceforge.ifx.framework.element.DevCamOperation getDevCamOperation() {
        return _devCamOperation;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevCamStatusArea _devCamStatusArea;

    /** 
     * Setter for devCamStatusArea
     * @param devCamStatusArea the org.sourceforge.ifx.framework.element.DevCamStatusArea to set
     */
    public void setDevCamStatusArea(org.sourceforge.ifx.framework.element.DevCamStatusArea _devCamStatusArea) {
        this._devCamStatusArea = _devCamStatusArea;
    }

    /**
     * Getter for devCamStatusArea
     * @return a org.sourceforge.ifx.framework.element.DevCamStatusArea
     */
    public org.sourceforge.ifx.framework.element.DevCamStatusArea getDevCamStatusArea() {
        return _devCamStatusArea;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevCamStatusMedia _devCamStatusMedia;

    /** 
     * Setter for devCamStatusMedia
     * @param devCamStatusMedia the org.sourceforge.ifx.framework.element.DevCamStatusMedia to set
     */
    public void setDevCamStatusMedia(org.sourceforge.ifx.framework.element.DevCamStatusMedia _devCamStatusMedia) {
        this._devCamStatusMedia = _devCamStatusMedia;
    }

    /**
     * Getter for devCamStatusMedia
     * @return a org.sourceforge.ifx.framework.element.DevCamStatusMedia
     */
    public org.sourceforge.ifx.framework.element.DevCamStatusMedia getDevCamStatusMedia() {
        return _devCamStatusMedia;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevCamStatusState _devCamStatusState;

    /** 
     * Setter for devCamStatusState
     * @param devCamStatusState the org.sourceforge.ifx.framework.element.DevCamStatusState to set
     */
    public void setDevCamStatusState(org.sourceforge.ifx.framework.element.DevCamStatusState _devCamStatusState) {
        this._devCamStatusState = _devCamStatusState;
    }

    /**
     * Getter for devCamStatusState
     * @return a org.sourceforge.ifx.framework.element.DevCamStatusState
     */
    public org.sourceforge.ifx.framework.element.DevCamStatusState getDevCamStatusState() {
        return _devCamStatusState;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DevCamStatusPictures _devCamStatusPictures;

    /** 
     * Setter for devCamStatusPictures
     * @param devCamStatusPictures the org.sourceforge.ifx.framework.element.DevCamStatusPictures to set
     */
    public void setDevCamStatusPictures(org.sourceforge.ifx.framework.element.DevCamStatusPictures _devCamStatusPictures) {
        this._devCamStatusPictures = _devCamStatusPictures;
    }

    /**
     * Getter for devCamStatusPictures
     * @return a org.sourceforge.ifx.framework.element.DevCamStatusPictures
     */
    public org.sourceforge.ifx.framework.element.DevCamStatusPictures getDevCamStatusPictures() {
        return _devCamStatusPictures;
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
       * DevCamType
       * DevCamOperation
       * DevCamStatusArea
       * DevCamStatusMedia
       * DevCamStatusState
       * DevCamStatusPictures
       */
    public final String[] ELEMENTS = {
              "DevCamType"
                 ,"DevCamOperation"
                 ,"DevCamStatusArea"
                 ,"DevCamStatusMedia"
                 ,"DevCamStatusState"
                 ,"DevCamStatusPictures"
          };
}
