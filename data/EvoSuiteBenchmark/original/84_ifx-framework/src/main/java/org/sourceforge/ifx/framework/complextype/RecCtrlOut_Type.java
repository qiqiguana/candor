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
public class RecCtrlOut_Type
        extends org.sourceforge.ifx.basetypes.IFXObject
{

    /** Default constructor */
    public RecCtrlOut_Type() {
        super();
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.MatchedRec _matchedRec;

    /** 
     * Setter for matchedRec
     * @param matchedRec the org.sourceforge.ifx.framework.element.MatchedRec to set
     */
    public void setMatchedRec(org.sourceforge.ifx.framework.element.MatchedRec _matchedRec) {
        this._matchedRec = _matchedRec;
    }

    /**
     * Getter for matchedRec
     * @return a org.sourceforge.ifx.framework.element.MatchedRec
     */
    public org.sourceforge.ifx.framework.element.MatchedRec getMatchedRec() {
        return _matchedRec;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.SentRec _sentRec;

    /** 
     * Setter for sentRec
     * @param sentRec the org.sourceforge.ifx.framework.element.SentRec to set
     */
    public void setSentRec(org.sourceforge.ifx.framework.element.SentRec _sentRec) {
        this._sentRec = _sentRec;
    }

    /**
     * Getter for sentRec
     * @return a org.sourceforge.ifx.framework.element.SentRec
     */
    public org.sourceforge.ifx.framework.element.SentRec getSentRec() {
        return _sentRec;
    }

    // property declaration 
    private org.sourceforge.ifx.framework.element.Cursor _cursor;

    /** 
     * Setter for cursor
     * @param cursor the org.sourceforge.ifx.framework.element.Cursor to set
     */
    public void setCursor(org.sourceforge.ifx.framework.element.Cursor _cursor) {
        this._cursor = _cursor;
    }

    /**
     * Getter for cursor
     * @return a org.sourceforge.ifx.framework.element.Cursor
     */
    public org.sourceforge.ifx.framework.element.Cursor getCursor() {
        return _cursor;
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
       * MatchedRec
       * SentRec
       * Cursor
       */
    public final String[] ELEMENTS = {
              "MatchedRec"
                 ,"SentRec"
                 ,"Cursor"
          };
}
