
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

import com.browsersoft.openhre.hl7.api.config.HL7DataType;
import com.browsersoft.openhre.hl7.api.config.HL7DataTypePartList;

public class HL7DataTypeImpl implements HL7DataType {

	private String id;
	private String description;
	private HL7DataTypePartList parts;
	private int contentType;

	public HL7DataTypeImpl() {
		parts = new HL7DataTypePartListImpl();
		description = "";
	}

	public String getID() {
		return id;
	}

	public void setID( String id ) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription( String description ) {
		this.description = description;
	}

	public int getContentType() {
		return contentType;
	}

	public void setContentType( int contentType ) {
		this.contentType = contentType;
	}

	public HL7DataTypePartList getParts() {
		return parts;
	}

	public void setParts( HL7DataTypePartList parts ) {
		this.parts = parts;
	}

	public String toString() {

		String ret = " -----------------------------------------\n";
		ret += " data type id=\"" + id + "\"\n";
		ret += " -----------------------------------------\n";

		if ( !description.equals("") ) {
			ret += " description=\"" + description + "\"\n";
		}

		switch ( contentType ) {
			case CONTENT_TYPE_PARTLIST:
				break;
			case CONTENT_TYPE_COMBINATION:
				ret += " content type=\"combination\"\n";
				break;
		}

		ret += parts.toString() + "\n";

		return ret;


	}

}
