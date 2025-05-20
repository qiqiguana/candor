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
 * Class represents map of data types and match
 * configuration file hl7_XXX_data_types.xml
 */
public interface HL7DataTypeMap {

	/**
	 * Returns size of map
	 *
	 * @return size of map
	 */
	public int size();

	/**
	 * Returns array of ids in map
	 *
	 * @return array of ids
	 */
	public String[] getTypeIds();

	/**
	 * Returns <CODE>HL7DataType</CODE> data type if exist in map, otherwise return null
	 *
	 * @param type id of type
	 * @return item <CODE>HL7DataType</CODE> or null
	 */
	public HL7DataType getItem( String type );

	/**
	 * Add <CODE>HL7DataType</CODE> item to map
	 *
	 * @param value value
	 */
	public void addItem( HL7DataType value );

	/**
	 * Remove item.
	 *
	 * @param type id of type
	 */
	public void removeItem( String type );

	/**
	 * Remove all items from map
	 */
	public void clearAll();

}
