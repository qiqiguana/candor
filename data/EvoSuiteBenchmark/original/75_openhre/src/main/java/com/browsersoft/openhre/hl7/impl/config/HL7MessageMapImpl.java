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

import com.browsersoft.openhre.hl7.api.config.HL7Message;
import com.browsersoft.openhre.hl7.api.config.HL7MessageMap;

import java.util.HashMap;

public class HL7MessageMapImpl implements HL7MessageMap {

	private HashMap map;

	public HL7MessageMapImpl() {
		map = new HashMap();
	}

	public int size() {
		return map.size();
	}

	public String[] getMessageIds() {
		Object[] keysAsObjects = map.keySet().toArray();
		String[] keysAsString = new String[keysAsObjects.length];

		for ( int i = 0; i < keysAsObjects.length; i++ ) {
			keysAsString[i] = (String) keysAsObjects[i];
		}

		return keysAsString;
	}

	public HL7Message getItem( String type ) {
		if ( map.containsKey(type) ) {
			return (HL7Message) map.get(type);
		} else {
			return null;
		}

	}

	public void addItem( HL7Message value ) {
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
		ret += " MESSAGES\n";
		ret += " ******************************************\n";

		String[] keys = getMessageIds();

		for ( int i = 0; i < keys.length; i++ ) {
			ret += getItem(keys[i]).toString();
		}

		ret += " ******************************************\n";

		return ret;


	}
}
