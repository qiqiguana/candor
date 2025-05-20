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
 * Class represents part list in one data type and match following
 * structure in configuration file hl7_XXX_data_types.xml
 * <pre>
 * &lt;parts&gt;
 *    &lt;part subtype="IS" description="namespace ID"/&gt;
 *    &lt;part subtype="ST" description="universal ID"/&gt;
 *    &lt;part subtype="ID" description="universal ID type"/&gt;
 * &lt;/parts&gt;
 * </pre>
 */

public interface HL7DataTypePartList {

	/**
	 * Returns size of list
	 *
	 * @return size of list
	 */
	public int size();

	/**
	 * Add new item to list
	 *
	 * @param item new item
	 */
	public void addItem( HL7DataTypePart item );

	/**
	 * Returns part with <CODE>index</CODE>
	 *
	 * @return item part
	 */
	public HL7DataTypePart getItem( int index );

	/**
	 * Sets item with <CODE>index</CODE> to new value
	 *
	 * @param index index
	 * @param value value
	 */
	public void setItem( int index, HL7DataTypePart value );

	/**
	 * Remove item with <CODE>index</CODE>
	 *
	 * @param index index
	 */
	public void removeItem( int index );

	/**
	 * Remove all items in list
	 */
	public void clearAll();

}
