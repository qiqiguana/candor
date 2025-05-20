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
public class PostingSessionRec_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public PostingSessionRec_Type() {
        super();
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

    // property declaration 
    private org.sourceforge.ifx.framework.element.PostingSessionInfo _postingSessionInfo;

    /** 
     * Setter for postingSessionInfo
     * @param postingSessionInfo the org.sourceforge.ifx.framework.element.PostingSessionInfo to set
     */
    public void setPostingSessionInfo(org.sourceforge.ifx.framework.element.PostingSessionInfo _postingSessionInfo) {
        this._postingSessionInfo = _postingSessionInfo;
    }

    /**
     * Getter for postingSessionInfo
     * @return a org.sourceforge.ifx.framework.element.PostingSessionInfo
     */
    public org.sourceforge.ifx.framework.element.PostingSessionInfo getPostingSessionInfo() {
        return _postingSessionInfo;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PostingSessionStatus _postingSessionStatus;

    /** 
     * Setter for postingSessionStatus
     * @param postingSessionStatus the org.sourceforge.ifx.framework.element.PostingSessionStatus to set
     */
    public void setPostingSessionStatus(org.sourceforge.ifx.framework.element.PostingSessionStatus _postingSessionStatus) {
        this._postingSessionStatus = _postingSessionStatus;
    }

    /**
     * Getter for postingSessionStatus
     * @return a org.sourceforge.ifx.framework.element.PostingSessionStatus
     */
    public org.sourceforge.ifx.framework.element.PostingSessionStatus getPostingSessionStatus() {
        return _postingSessionStatus;
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
       * PostingSessionId
       * PostingSessionInfo
       * PostingSessionStatus
       */
    public final String[] ELEMENTS = {
              "PostingSessionId"
                 ,"PostingSessionInfo"
                 ,"PostingSessionStatus"
          };
}
