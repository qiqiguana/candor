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

/**
 * Class represents segments in message and match following
 * structure in configuration file hl7_XXX_messages.xml
 * <pre>
 *    &lt;segment id="ERR" required="N"/&gt;
 * </pre>
 */

public interface HL7MessageSegment extends HL7MessageGroupItem {

	/**
	 * Returns the segment id ( for example MSH, QRD.... )
	 *
	 * @return segment id
	 */
	public String getID();

	/**
	 * Sets the segment id ( for example MSH, QRD.... )
	 *
	 * @param id segment id
	 */
	public void setID( String id );

	/**
	 * Returns the <CODE>HL7Segment</CODE>
	 *
	 * @return segment
	 */
	public HL7Segment getSegment();

	/**
	 * Set the <CODE>HL7Segment</CODE>
	 *
	 * @param segment segment
	 */
	public void setSegment( HL7Segment segment );
}
