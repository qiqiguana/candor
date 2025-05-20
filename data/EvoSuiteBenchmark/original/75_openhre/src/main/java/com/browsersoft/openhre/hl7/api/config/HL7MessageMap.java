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
 * Class represents map of messages and match
 * configuration file hl7_XXX_messages.xml
 */
public interface HL7MessageMap {

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
	public String[] getMessageIds();

	/**
	 * Returns <CODE>HL7Message</CODE> data type if exist in map, otherwise return null
	 *
	 * @param type id of type
	 * @return item <CODE>HL7Message</CODE> or null
	 */
	public HL7Message getItem( String type );

	/**
	 * Add <CODE>HL7Message</CODE> item to map
	 *
	 * @param value value
	 */
	public void addItem( HL7Message value );

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
