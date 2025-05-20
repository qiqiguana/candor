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
 * Class represents one data type and match following
 * structure in configuration file hl7_XXX_data_types.xml
 * <pre>
 * &lt;type id="HD"&gt;
 *    &lt;description&gt;
 *         hierarchic designator
 *    &lt;/description&gt;
 *    &lt;parts&gt;
 *        &lt;part subtype="IS" description="namespace ID"/&gt;
 *        &lt;part subtype="ST" description="universal ID"/&gt;
 *        &lt;part subtype="ID" description="universal ID type"/&gt;
 *    &lt;/parts&gt;
 * &lt;/type&gt;
 * </pre>
 */

public interface HL7DataType {

	/**
	 * Data type is defined by part list ( tag <parts>   )
	 */
	public static final int CONTENT_TYPE_PARTLIST = 0;

	/**
	 * Data type is a non defined combination of other data types
	 */
	public static final int CONTENT_TYPE_COMBINATION = 1;

	/**
	 * Returns the type id ( for example ST, DT, NM.... )
	 *
	 * @return id
	 */
	public String getID();

	/**
	 * Sets the type id ( for example ST, DT, NM.... )
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
	 * Returns type of content
	 * possible values are:
	 * <CODE>CONTENT_TYPE_PARTLIST</CODE>
	 * <CODE>CONTENT_TYPE_COMBINATION</CODE>
	 *
	 * @return type of content
	 */
	public int getContentType();

	/**
	 * Sets the type of content
	 * possible values are:
	 * <CODE>CONTENT_TYPE_PARTLIST</CODE>
	 * <CODE>CONTENT_TYPE_COMBINATION</CODE>
	 *
	 * @param contentType type of content
	 */
	public void setContentType( int contentType );

	/**
	 * Returns list of parts
	 *
	 * @return list of parts
	 */
	public HL7DataTypePartList getParts();

	/**
	 * Sets list of parts
	 *
	 * @param parts list of parts
	 */
	public void setParts( HL7DataTypePartList parts );


}
