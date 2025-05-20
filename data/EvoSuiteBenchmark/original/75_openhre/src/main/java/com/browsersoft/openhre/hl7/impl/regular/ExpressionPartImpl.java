/*
 *   ====================================================================
 *                 Open Source Health Records Exchange
 *   ====================================================================
 *
 *   Copyright (C) 2006 Browsersoft Inc.
 *
 *   This program is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License, version 2,
 *   as published by the Free Software Foundation.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   The GNU General Public License is available at
 *   http://www.fsf.org/licensing/licenses/gpl.html
 *
 *   Email: info@openhre.org
 *   Web:   http://www.openhre.org
 */

package com.browsersoft.openhre.hl7.impl.regular;

import com.browsersoft.openhre.hl7.api.regular.ExpressionPart;

public class ExpressionPartImpl implements ExpressionPart {

    private int type;
    private int elementID;

    public int getType() {
        return type;
    }

    public void setType( int type ) {
       this.type = type;
    }

    public int getElementID() {
        return elementID;
    }

    public void setElementID( int elementID ) {
       this.elementID = elementID;
    }

    public String toString() {
        switch (type) {
            case ExpressionPart.CLOSE_OPTIONAL:
                return "CLOSE_OPTIONAL";
            case ExpressionPart.CLOSE_REPEATABLE:
                return "CLOSE_REPEATABLE";
            case ExpressionPart.ELEMENT:
                return "ELEMENT " + elementID;
            case ExpressionPart.OPEN_OPTIONAL:
                return "OPEN_OPTIONAL";
            case ExpressionPart.OPEN_REPEATABLE:
                return "OPEN_REPEATABLE";
        }
        return "";
    }

}