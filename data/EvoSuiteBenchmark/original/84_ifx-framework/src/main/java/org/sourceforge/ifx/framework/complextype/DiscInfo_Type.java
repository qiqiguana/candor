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
public class DiscInfo_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public DiscInfo_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.LongText _longText;

    /** 
     * Setter for longText
     * @param longText the org.sourceforge.ifx.framework.element.LongText to set
     */
    public void setLongText(org.sourceforge.ifx.framework.element.LongText _longText) {
        this._longText = _longText;
    }

    /**
     * Getter for longText
     * @return a org.sourceforge.ifx.framework.element.LongText
     */
    public org.sourceforge.ifx.framework.element.LongText getLongText() {
        return _longText;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.DiscURL _discURL;

    /** 
     * Setter for discURL
     * @param discURL the org.sourceforge.ifx.framework.element.DiscURL to set
     */
    public void setDiscURL(org.sourceforge.ifx.framework.element.DiscURL _discURL) {
        this._discURL = _discURL;
    }

    /**
     * Getter for discURL
     * @return a org.sourceforge.ifx.framework.element.DiscURL
     */
    public org.sourceforge.ifx.framework.element.DiscURL getDiscURL() {
        return _discURL;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.AcceptReqd _acceptReqd;

    /** 
     * Setter for acceptReqd
     * @param acceptReqd the org.sourceforge.ifx.framework.element.AcceptReqd to set
     */
    public void setAcceptReqd(org.sourceforge.ifx.framework.element.AcceptReqd _acceptReqd) {
        this._acceptReqd = _acceptReqd;
    }

    /**
     * Getter for acceptReqd
     * @return a org.sourceforge.ifx.framework.element.AcceptReqd
     */
    public org.sourceforge.ifx.framework.element.AcceptReqd getAcceptReqd() {
        return _acceptReqd;
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
       * LongText
       * DiscURL
       * AcceptReqd
       */
    public final String[] ELEMENTS = {
              "LongText"
                 ,"DiscURL"
                 ,"AcceptReqd"
          };
}
