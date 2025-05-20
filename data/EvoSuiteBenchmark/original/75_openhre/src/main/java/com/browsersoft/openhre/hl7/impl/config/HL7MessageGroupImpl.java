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

import com.browsersoft.openhre.hl7.api.config.HL7MessageGroup;
import com.browsersoft.openhre.hl7.api.config.HL7MessageGroupItem;

import java.util.ArrayList;

public class HL7MessageGroupImpl extends HL7MessageGroupItemImpl implements HL7MessageGroup {

	private ArrayList items;
    private String additional = "";

    public HL7MessageGroupImpl() {
		items = new ArrayList();
	}

	public int getType() {
		return HL7MessageGroupItem.ITEM_TYPE_GROUP;
	}

    public String getAdditional() {
        return additional;
    }

    public void setAdditional( String additional ) {
        this.additional = additional;
    }

    public int size() {
		return items.size();
	}

	public void addItem( HL7MessageGroupItem item ) {
		items.add(item);
	}

	public HL7MessageGroupItem getItem( int index ) {
		if ( index >= 0 && index < items.size() ) {
			return (HL7MessageGroupItem) items.get(index);
		} else {
			return null;
		}
	}

	public void setItem( int index, HL7MessageGroupItem value ) {
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


		String ret = "group ";

		ret += " repeatable =\"" +  repeatable + "\"";
		ret += " required =\"" +  required + "\"";

		for ( int i = 0; i < size(); i++ ) {
			ret += "     "  + getItem(i).toString() + "\n";
		}

		return ret;

	}

}
