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

package com.browsersoft.openhre.hl7.api.regular;

import com.browsersoft.openhre.hl7.api.config.HL7Segment;

/**
 * This class represent aneelement in <CODE>ExpressionElementMapper</CODE> 
 */
public interface ExpressionElementMapperItem  {

    /**
     * element is a segment
     */
     public static final int SEGMENT_ITEM          = 0;

    /**
     * element is a additional begin tag ( the reason, why the support additional tag extension has been added to this library
     * is the format of T2 messages - please see into T2 HealthInfo specification )
     */
     public static final int ADDITIONAL_ITEM_BEGIN = 1;

    /**
     * element is a additional end tag
     */
     public static final int ADDITIONAL_ITEM_END   = 2;

    /**
     * element is a end of whole message
     */
    public static final int END_ITEM              = 3;

    /**
     * get the type of elementItem. Possible values are:
     * <CODE>SEGMENT_ITEM</CODE>, <CODE>ADDITIONAL_ITEM_BEGIN</CODE>, <CODE>ADDITIONAL_ITEM_END, END_ITEM</CODE>
     * @return type type
     */

     public int getType();

    /**
     *  set the type of elementItem. Possible values are:
     * <CODE>SEGMENT_ITEM</CODE>, <CODE>ADDITIONAL_ITEM_BEGIN</CODE>, <CODE>ADDITIONAL_ITEM_END, END_ITEM</CODE>
     * @param type type
     */
     public void setType( int type );

    /**
     * get the id of segment or name of additional tag
     * @return  id or name
     */
     public String getID();

    /**
     * set the id of segment or name of additional tag
     * @param id or name
     */
     public void setID( String id );

    /**
     * get configuration for segment ( only in case type == SEGMENT_ITEM )
     * @return configuration for segment
     */
     public HL7Segment getSegment();

    /**
     * set configuration for segment ( only in case type == SEGMENT_ITEM )
     * @param segment segment
     */
     public void setSegment( HL7Segment segment );

}