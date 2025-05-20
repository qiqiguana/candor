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
public class StmtImage_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public StmtImage_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ImageURL _imageURL;

    /** 
     * Setter for imageURL
     * @param imageURL the org.sourceforge.ifx.framework.element.ImageURL to set
     */
    public void setImageURL(org.sourceforge.ifx.framework.element.ImageURL _imageURL) {
        this._imageURL = _imageURL;
    }

    /**
     * Getter for imageURL
     * @return a org.sourceforge.ifx.framework.element.ImageURL
     */
    public org.sourceforge.ifx.framework.element.ImageURL getImageURL() {
        return _imageURL;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.PrefetchURL[] _prefetchURL;

    /** 
     * Setter for prefetchURL
     * @param prefetchURL the org.sourceforge.ifx.framework.element.PrefetchURL[] to set
     */
    public void setPrefetchURL(org.sourceforge.ifx.framework.element.PrefetchURL[] _prefetchURL) {
        this._prefetchURL = _prefetchURL;
    }

    /**
     * Getter for prefetchURL
     * @return a org.sourceforge.ifx.framework.element.PrefetchURL[]
     */
    public org.sourceforge.ifx.framework.element.PrefetchURL[] getPrefetchURL() {
        return _prefetchURL;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.ExpDt[] _expDt;

    /** 
     * Setter for expDt
     * @param expDt the org.sourceforge.ifx.framework.element.ExpDt[] to set
     */
    public void setExpDt(org.sourceforge.ifx.framework.element.ExpDt[] _expDt) {
        this._expDt = _expDt;
    }

    /**
     * Getter for expDt
     * @return a org.sourceforge.ifx.framework.element.ExpDt[]
     */
    public org.sourceforge.ifx.framework.element.ExpDt[] getExpDt() {
        return _expDt;
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
       * ImageURL
       * PrefetchURL
       * ExpDt
       */
    public final String[] ELEMENTS = {
              "ImageURL"
                 ,"PrefetchURL"
                 ,"ExpDt"
          };
}
