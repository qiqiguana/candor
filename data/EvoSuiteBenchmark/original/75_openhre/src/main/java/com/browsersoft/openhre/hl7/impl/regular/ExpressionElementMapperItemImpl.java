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

import com.browsersoft.openhre.hl7.api.regular.ExpressionElementMapperItem;
import com.browsersoft.openhre.hl7.api.config.HL7Segment;


public class ExpressionElementMapperItemImpl implements ExpressionElementMapperItem {

    private int type;
    private String id;
    private HL7Segment segment;

    public int getType() {
        return type;
    }

    public void setType( int type ) {
        this.type = type;
    }

    public String getID() {
        return id;
    }

    public void setID( String id ) {
        this.id = id;
    }

    public HL7Segment getSegment() {
        return segment;
    }

    public void setSegment( HL7Segment segment ) {
        this.segment = segment;
    }

    public String toString() {
        switch ( type ) {
            case ExpressionElementMapperItem.SEGMENT_ITEM:
                return id;
            case ExpressionElementMapperItem.ADDITIONAL_ITEM_BEGIN:
                return "<" + id + ">";
            case ExpressionElementMapperItem.ADDITIONAL_ITEM_END:
                return "</" + id + ">";
            case ExpressionElementMapperItem.END_ITEM:
                return "END_POINT";
        }
        return "";
    }

}

