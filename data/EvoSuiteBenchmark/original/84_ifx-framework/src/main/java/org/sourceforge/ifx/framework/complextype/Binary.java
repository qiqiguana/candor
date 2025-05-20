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
 * The Binary data type is a compound type consisting of threee logical elements.
 * @author org.sourceforge.ifx.tools.CodeGenerator
 */
public class Binary
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public Binary() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ContentType _contentType;

    /** 
     * Setter for contentType
     * @param contentType the org.sourceforge.ifx.framework.element.ContentType to set
     */
    public void setContentType(org.sourceforge.ifx.framework.element.ContentType _contentType) {
        this._contentType = _contentType;
    }

    /**
     * Getter for contentType
     * @return a org.sourceforge.ifx.framework.element.ContentType
     */
    public org.sourceforge.ifx.framework.element.ContentType getContentType() {
        return _contentType;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BinLength _binLength;

    /** 
     * Setter for binLength
     * @param binLength the org.sourceforge.ifx.framework.element.BinLength to set
     */
    public void setBinLength(org.sourceforge.ifx.framework.element.BinLength _binLength) {
        this._binLength = _binLength;
    }

    /**
     * Getter for binLength
     * @return a org.sourceforge.ifx.framework.element.BinLength
     */
    public org.sourceforge.ifx.framework.element.BinLength getBinLength() {
        return _binLength;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.BinData _binData;

    /** 
     * Setter for binData
     * @param binData the org.sourceforge.ifx.framework.element.BinData to set
     */
    public void setBinData(org.sourceforge.ifx.framework.element.BinData _binData) {
        this._binData = _binData;
    }

    /**
     * Getter for binData
     * @return a org.sourceforge.ifx.framework.element.BinData
     */
    public org.sourceforge.ifx.framework.element.BinData getBinData() {
        return _binData;
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
       * ContentType
       * BinLength
       * BinData
       */
    public final String[] ELEMENTS = {
              "ContentType"
                 ,"BinLength"
                 ,"BinData"
          };
}
