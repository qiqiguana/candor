
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

package com.browsersoft.openhre.hl7.api.config;

import org.w3c.dom.Node;

/**
 * This class is a reader for segments configuration  ( file hl7_XXX_segments.xml )
 */
public interface HL7SegmentsConfigReader {

    /**
     * Read the DOM structure and add segments types to <CODE>HL7SegmentMap</CODE> ( for example PID.... )
     * @param node segments types node
     * @param map to this map will be added the segment types configuration
     * @throws InvalidConfigDataStructureException
     */
    public void readConfiguration( Node node, HL7SegmentMap map ) throws InvalidConfigDataStructureException;

}
