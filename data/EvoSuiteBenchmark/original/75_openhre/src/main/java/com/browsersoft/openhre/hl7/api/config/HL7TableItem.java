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
 * Class represents one table item and match following
 * structure in configuration file hl7_XXX_tables.xml
 * <pre>
 *    &lt;item value="F" description="Female"/&gt;
 * </pre>
 */

public interface HL7TableItem {

    /**
	 * Returns the item value
	 *
	 * @return value
	 */
	public String getValue();

	/**
	 * Sets the item value
	 *
	 * @param value item value
	 */
	public void setValue( String value );

    /**
	 * Returns the item description
	 *
	 * @return description
	 */
	public String getDescription();

	/**
	 * Sets the item description
	 *
	 * @param description item description
	 */
	public void setDescription( String description );

}
