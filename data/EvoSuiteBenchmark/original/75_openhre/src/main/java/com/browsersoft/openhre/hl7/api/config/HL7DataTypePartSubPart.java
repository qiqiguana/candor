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
 * Class represents subpart and match following
 * structure in configuration file hl7_XXX_data_types.xml
 * <pre>
 * &lt;part subtype="IS" description="namespace ID"/&gt;
 * </pre>
 * This class method getType() (from HL7DataTypePart) must always return HL7DataTypePart.PART_TYPE_SUBTYPE;
*/

public interface HL7DataTypePartSubPart extends HL7DataTypePart  {

    /**
	 * Returns the subtype id
	 *
	 * @return subtype id
	 */
	public String getSubPartID();

	/**
	 * Sets the subtype id
	 *
	 * @param subPartID subtype id
	 */
	public void setSubPartID( String subPartID );

    /**
	 * Returns the subtype
	 *
	 * @return subtype
	 */
	public HL7DataType getSubType();

	/**
	 * Sets the subtype
	 *
	 * @param subType subtype
	 */
	public void setSubType( HL7DataType subType );

}
