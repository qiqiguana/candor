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


package com.browsersoft.openhre.hl7.impl.config;

import com.browsersoft.openhre.hl7.api.config.HL7MessageSegment;
import com.browsersoft.openhre.hl7.api.config.HL7MessageGroupItem;
import com.browsersoft.openhre.hl7.api.config.HL7Segment;


public class HL7MessageSegmentImpl extends HL7MessageGroupItemImpl implements HL7MessageSegment {

	private String id;
    private HL7Segment segment;

	public int getType() {
		return HL7MessageGroupItem.ITEM_TYPE_SEGMENT;
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

		String ret = "segment id=\"" + id + "\"";

		ret += " repeatable =\"" +  repeatable + "\"";
		ret += " required =\"" +  required + "\"";

		return ret;

	}
}
