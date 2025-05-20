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
 * If you use class <CODE>MessageTracer</CODE>, you have to implement this interface and register
 * yourself by method <CODE>setHandler</CODE>
 */
public interface MessageTracerHandler {
    /**
     * event - it was possible to go throught the graph to the segment  ( this segment may be in this possition, everthing is fine )
     * @param segment segment configuration
     */
    public void messageTracerSegmentEvent( HL7Segment segment );

    /**
     * event - it was impossible to go throught the graph to the segment ( this segment may not be in this possition, it is wrong )
     * @param segment segment configuration
     */
    public void messageTracerErrorEvent( String segment );

    /**
     * event - the trace from old to the new segment leads throught open group with additional tag with name <CODE>tagName</CODE>
     * @param tagName name of tag
     */
    public void messageTracerAdditionalTagBeginEvent( String tagName );

    /**
     * event - the trace from old to the new segment leads throught close group with additional tag with name <CODE>tagName</CODE>
     * @param tagName name of tag
     */
    public void messageTracerAdditionalTagEndEvent( String tagName );

}