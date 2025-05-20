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

import com.browsersoft.openhre.hl7.api.parse.HL7PrimitiveDataTypeReader;

/**
 * Class represents primitive data part type and match following
 * structure in configuration file hl7_XXX_data_types.xml
 * <pre>
 * &lt;part reader="com.browsersoft.openhre.hl7.impl.data.TSReader"//&gt;
 * </pre>
 * This class method getType() (from HL7DataTypePart) must always return HL7DataTypePart.PART_TYPE_PRIMITIVE;
*/

public interface HL7DataTypePartPrimitive extends HL7DataTypePart {

    /**
     * Returns the class name of reader for this part
     *
     * @return description
     */
    public String getReader();

    /**
     * Sets the class name of reader for this part
     *
     * @param className class name
     */
    public void setReader( String className );

    /**
     * Returns the instance of <CODE>HL7PrimitiveDataTypeReader</CODE>
     *
     * @return instance of reader
     */
    public HL7PrimitiveDataTypeReader getInstanceReader();

    /**
     * Sets the instance of <CODE>HL7PrimitiveDataTypeReader</CODE>
     *
     * @param instanceOfReader instance of reader
     */
    public void setInstanceReader( HL7PrimitiveDataTypeReader instanceOfReader );

    /**
     * Returns the id of table
     *
     * @return table id
     */
    public String getIDTable();

    /**
     * Sets the table id
     *
     * @param idTable table id
     */
    public void setIDTable( String idTable );

    /**
     * Returns the table
     *
     * @return table
     */
    public HL7Table getTable();

    /**
     * Sets the table
     *
     * @param table table
     */
    public void setTable( HL7Table table );

}

