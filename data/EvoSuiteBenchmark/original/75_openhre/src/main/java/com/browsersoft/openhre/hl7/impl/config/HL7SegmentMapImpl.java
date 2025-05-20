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

import com.browsersoft.openhre.hl7.api.config.HL7Segment;
import com.browsersoft.openhre.hl7.api.config.HL7SegmentMap;

import java.util.HashMap;

public class HL7SegmentMapImpl implements HL7SegmentMap {

	private HashMap map;

	public HL7SegmentMapImpl() {
		map = new HashMap();
	}

	public int size() {
		return map.size();
	}

	public String[] getSegmentsIds() {
		Object[] keysAsObjects = map.keySet().toArray();
		String[] keysAsString = new String[keysAsObjects.length];

		for ( int i = 0; i < keysAsObjects.length; i++ ) {
			keysAsString[i] = (String) keysAsObjects[i];
		}

		return keysAsString;
	}

	public HL7Segment getItem( String type ) {
		if ( map.containsKey(type) ) {
			return (HL7Segment) map.get(type);
		} else {
			return null;
		}

	}

	public void addItem( HL7Segment value ) {
		map.put(value.getID(), value);
	}

	public void removeItem( String type ) {
		if ( map.containsKey(type) ) {
			map.remove(type);
		}
	}

	public void clearAll() {
		map.clear();
	}

	public String toString() {

		String ret = " ******************************************\n";
		ret += " SEGMENTS\n";
		ret += " ******************************************\n";

		String[] keys = getSegmentsIds();

		for ( int i = 0; i < keys.length; i++ ) {
			ret += getItem(keys[i]).toString() + "\n";
		}

		ret += " ******************************************\n";

		return ret;


	}
}
