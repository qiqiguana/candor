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
public class MediaAcctAdjMsgRqInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public MediaAcctAdjMsgRqInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MediaAcctAdjAddRq _mediaAcctAdjAddRq;

    /** 
     * Setter for mediaAcctAdjAddRq
     * @param mediaAcctAdjAddRq the org.sourceforge.ifx.framework.element.MediaAcctAdjAddRq to set
     */
    public void setMediaAcctAdjAddRq(org.sourceforge.ifx.framework.element.MediaAcctAdjAddRq _mediaAcctAdjAddRq) {
        this._mediaAcctAdjAddRq = _mediaAcctAdjAddRq;
    }

    /**
     * Getter for mediaAcctAdjAddRq
     * @return a org.sourceforge.ifx.framework.element.MediaAcctAdjAddRq
     */
    public org.sourceforge.ifx.framework.element.MediaAcctAdjAddRq getMediaAcctAdjAddRq() {
        return _mediaAcctAdjAddRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MediaAcctAdjInqRq _mediaAcctAdjInqRq;

    /** 
     * Setter for mediaAcctAdjInqRq
     * @param mediaAcctAdjInqRq the org.sourceforge.ifx.framework.element.MediaAcctAdjInqRq to set
     */
    public void setMediaAcctAdjInqRq(org.sourceforge.ifx.framework.element.MediaAcctAdjInqRq _mediaAcctAdjInqRq) {
        this._mediaAcctAdjInqRq = _mediaAcctAdjInqRq;
    }

    /**
     * Getter for mediaAcctAdjInqRq
     * @return a org.sourceforge.ifx.framework.element.MediaAcctAdjInqRq
     */
    public org.sourceforge.ifx.framework.element.MediaAcctAdjInqRq getMediaAcctAdjInqRq() {
        return _mediaAcctAdjInqRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MediaAcctAdjAudRq _mediaAcctAdjAudRq;

    /** 
     * Setter for mediaAcctAdjAudRq
     * @param mediaAcctAdjAudRq the org.sourceforge.ifx.framework.element.MediaAcctAdjAudRq to set
     */
    public void setMediaAcctAdjAudRq(org.sourceforge.ifx.framework.element.MediaAcctAdjAudRq _mediaAcctAdjAudRq) {
        this._mediaAcctAdjAudRq = _mediaAcctAdjAudRq;
    }

    /**
     * Getter for mediaAcctAdjAudRq
     * @return a org.sourceforge.ifx.framework.element.MediaAcctAdjAudRq
     */
    public org.sourceforge.ifx.framework.element.MediaAcctAdjAudRq getMediaAcctAdjAudRq() {
        return _mediaAcctAdjAudRq;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MediaAcctAdjSyncRq _mediaAcctAdjSyncRq;

    /** 
     * Setter for mediaAcctAdjSyncRq
     * @param mediaAcctAdjSyncRq the org.sourceforge.ifx.framework.element.MediaAcctAdjSyncRq to set
     */
    public void setMediaAcctAdjSyncRq(org.sourceforge.ifx.framework.element.MediaAcctAdjSyncRq _mediaAcctAdjSyncRq) {
        this._mediaAcctAdjSyncRq = _mediaAcctAdjSyncRq;
    }

    /**
     * Getter for mediaAcctAdjSyncRq
     * @return a org.sourceforge.ifx.framework.element.MediaAcctAdjSyncRq
     */
    public org.sourceforge.ifx.framework.element.MediaAcctAdjSyncRq getMediaAcctAdjSyncRq() {
        return _mediaAcctAdjSyncRq;
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
       * MediaAcctAdjAddRq
       * MediaAcctAdjInqRq
       * MediaAcctAdjAudRq
       * MediaAcctAdjSyncRq
       */
    public final String[] ELEMENTS = {
              "MediaAcctAdjAddRq"
                 ,"MediaAcctAdjInqRq"
                 ,"MediaAcctAdjAudRq"
                 ,"MediaAcctAdjSyncRq"
          };
}
