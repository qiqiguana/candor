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
 * Class represents one part in part list in one data type and match following
 * structure in configuration file hl7_XXX_data_types.xml
 * <pre>
 * &lt;part subtype="IS" description="namespace ID"/&gt;
 * </pre>
 * for composed part or
 * <pre>
 * &lt;part reader="com.browsersoft.openhre.hl7.impl.data.TSReader"//&gt;
 * </pre>
 * for simple part
 */

public interface HL7DataTypePart {

    public static final int  PART_TYPE_PRIMITIVE = 0;
    public static final int  PART_TYPE_SUBTYPE   = 1;

    /**
	 * Returns the part description
	 *
	 * @return description
	 */
	public String getDescription();

	/**
	 * Sets the part description
	 *
	 * @param description part description
	 */
	public void setDescription( String description );

    /**
	 * Returns the type of part
	 *
	 * @return description
	 */
    public int getType();

}
