
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
 * Class represents map of segments and match
 * configuration file hl7_XXX_segments.xml
 */
public interface HL7SegmentMap {

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
	public String[] getSegmentsIds();

	/**
	 * Returns <CODE>HL7Segment</CODE> if exist in map, otherwise return null
	 *
	 * @param id id of segment
	 * @return item <CODE>HL7Segment</CODE> or null
	 */
	public HL7Segment getItem( String id );

	/**
	 * Add <CODE>HL7Segment</CODE> item to map
	 *
	 * @param value value
	 */
	public void addItem( HL7Segment value );

	/**
	 * Remove item.
	 *
	 * @param id id of segment
	 */
	public void removeItem( String id );

	/**
	 * Remove all items from map
	 */
	public void clearAll();

}
