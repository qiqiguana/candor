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
 * Class represents one field in HL7 segment and match following
 * structure in configuration file hl7_XXX_segments.xml
 * <pre>
 * &lt;field sequential="1" maximumLength="80" dataType="CM" required="R" repeatable="Y" table="0357" reference="00024"&gt;
 *    &lt;description&gt;
 *       Error code and location
 *    &lt;/description&gt;
 * &lt;/field&gt;
 * </pre> 
 */

public interface HL7Field {

    /**
    * Data type of field is given by tag dataType
    */
    public static final int TYPE_DEPENDING_NORMAL     = 0;

    /**
    * Data type of field depends an order of repeatable fields
    * Please see into specification of field QRF-5 for more information.
    */
    public static final int TYPE_DEPENDING_REPEATABLE = 1;

    /**
    * Data type of field is given by other value in record
    * Please see into specification of field OBX-5 for more information.
    */
    public static final int TYPE_DEPENDING_GIVEN      = 2;


    /**
	 * The field is required. Use this value is getRequired/setRequired
	 */
	public static final int REQUIRED_R = 0;

	/**
	 * The field is optional. Use this value is getRequired/setRequired
	 */
	public static final int REQUIRED_O = 1;

	/**
	 * The field is conditional on the trigger event or on some other field(s).
	 * Use this value is getRequired/setRequired
	 */
	public static final int REQUIRED_C = 2;

	/**
	 * The field isn't used with this trigger event.
	 * Use this value is getRequired/setRequired
	 */
	public static final int REQUIRED_X = 3;

	/**
	 * The field is left in for backward compatibility with previous versions of HL7.
	 * Use this value is getRequired/setRequired
	 */
	public static final int REQUIRED_B = 4;

	/**
	 * Returns the sequential number
	 *
	 * @return sequential number
	 */
	public String getSequential();

	/**
	 * Sets the sequential number
	 *
	 * @param sequential sequential number
	 */
	public void setSequential( String sequential );

	/**
	 * Returns the reference number
	 *
	 * @return reference number
	 */
	public String getReference();

	/**
	 * Sets the reference number
	 *
	 * @param reference reference number
	 */
	public void setReference( String reference );

	/**
	 * Returns the field description
	 *
	 * @return description
	 */
	public String getDescription();

	/**
	 * Sets the field description
	 *
	 * @param description field description
	 */
	public void setDescription( String description );

	/**
	 * Returns the data type id or id field defines data type binding.
	 * It depends on value of isDataTypeBinding.
	 *
	 * @return data type id
	 */
	public String getDataTypeID();

	/**
	 * Sets the data type id or id field defines data type binding
	 * It depends on value of isDataTypeBinding.
	 *
	 * @param dataTypeID data type id
	 */
	public void setDataTypeID( String dataTypeID );

	/**
	 * Returns the data type object on case that the field data type isn't binding
	 *
	 * @return data type object
	 */
	public HL7DataType getDataType();

	/**
	 * Sets the data type object
	 *
	 * @param dataType data type object
	 */
	public void setDataType( HL7DataType dataType );

    /**
	 * Returns type of data type depending on something
     * possible values are:
	 * <CODE>TYPE_DEPENDING_NORMAL</CODE>
	 * <CODE>TYPE_DEPENDING_REPEATABLE</CODE>
	 * <CODE>TYPE_DEPENDING_GIVEN</CODE>
	 * @return type of depending
	 */
    public int getDependingType();

    /**
	 * Sets the data type depending
	 *
	 * @param dependingType depending type
	 */
    public void setDependingType( int dependingType );

    public HL7FieldDependingProcessor getDependingProcessor();
    public void setDependingProcessor( HL7FieldDependingProcessor dependingProcessor );    

    /**
	 * Returns the length of field
	 *
	 * @return length of field
	 */
	public int getMaximumLength();

	/**
	 * Sets the maximum length of attribute
	 *
	 * @param length maximum length of attribute
	 */
	public void setMaximumLength( int length );

	/**
	 * Returns required code
	 * possible values are:
	 * <CODE>REQUIRED_R</CODE>
	 * <CODE>REQUIRED_O</CODE>
	 * <CODE>REQUIRED_C</CODE>
	 * <CODE>REQUIRED_X</CODE>
	 * <CODE>REQUIRED_B</CODE>
	 *
	 * @return required code
	 */
	public int getRequired();

	/**
	 * Sets the required code
	 *
	 * @param required required code
	 */
	public void setRequired( int required );

	/**
	 * Returns whether the field repeatable
	 *
	 * @return whether the field repeatable
	 */
	public boolean isRepeatable();

	/**
	 * Sets the whether the field repeatable
	 *
	 * @param repeatable whether the field repeatable
	 */
	public void setRepeatable( boolean repeatable );

	/**
	 * Returns the table
	 *
	 * @return table
	 */
	public String getTable();

	/**
	 * Sets the table
	 *
	 * @param table table
	 */
	public void setTable( String table );

    /**
	 * Returns the table object
	 *
	 * @return table object
	 */
	public HL7Table getTableObject();

	/**
	 * Sets the table object
	 *
	 * @param tableObject table object
	 */
	public void setTableObject( HL7Table tableObject );


}
