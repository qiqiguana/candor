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

import com.browsersoft.openhre.hl7.api.config.HL7Field;
import com.browsersoft.openhre.hl7.api.config.HL7FieldList;

import java.util.ArrayList;

public class HL7FieldListImpl implements HL7FieldList {

	private ArrayList items;

	public HL7FieldListImpl() {
		items = new ArrayList();
	}

	public int size() {
		return items.size();
	}

	public void addItem( HL7Field item ) {
		items.add(item);
	}

	public HL7Field getItem( int index ) {
		if ( index >= 0 && index < items.size() ) {
			return (HL7Field) items.get(index);
		} else {
			return null;
		}
	}

	public void setItem( int index, HL7Field value ) {
		if ( index >= 0 && index < items.size() ) {
			items.set(index, value);
		}
	}

	public void removeItem( int index ) {
		if ( index >= 0 && index < items.size() ) {
			items.remove(index);
		}
	}

	public void clearAll() {
		items.clear();
	}

	public String toString() {

		StringBuffer buffer = new StringBuffer();

		for ( int i = 0; i < size(); i++ ) {
			buffer.append(getItem(i).toString());
			buffer.append("\n");
		}

		return buffer.toString();

	}
}
