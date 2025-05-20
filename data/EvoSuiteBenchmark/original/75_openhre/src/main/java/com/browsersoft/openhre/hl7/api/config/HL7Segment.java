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
 * Class represents one segment and match following
 * structure in configuration file hl7_XXX_segments.xml
 * <pre>
 * &lt;segment id="ERR"&gt;
 *    &lt;description&gt;
 *       Error
 *    &lt;/description&gt;
 *    &lt;fields&gt;
 *       &lt;field sequential="1" maximumLength="80" dataType="CM" required="R" repeatable="Y" table="0357" reference="00024"&gt;
 *          &lt;description&gt;
 *              Error code and location
 *          &lt;/description&gt;
 *       &lt;/field&gt;
 *    &lt;/fields&gt;
 * &lt;/segment&gt;
 * </pre>
 */

public interface HL7Segment {

	/**
	 * Returns the type id ( for example MSH, PID .... )
	 *
	 * @return id
	 */
	public String getID();

	/**
	 * Sets the type id  ( for example MSH, PID .... )
	 *
	 * @param id type id
	 */
	public void setID( String id );

	/**
	 * Returns the type description
	 *
	 * @return description
	 */
	public String getDescription();

	/**
	 * Sets the type description
	 *
	 * @param description type description
	 */
	public void setDescription( String description );

	/**
	 * Returns the list of fields
	 *
	 * @return description
	 */
	public HL7FieldList getFields();

	/**
	 * Sets the list of fields
	 *
	 * @param fields list of fields
	 */
	public void setFields( HL7FieldList fields );
}
