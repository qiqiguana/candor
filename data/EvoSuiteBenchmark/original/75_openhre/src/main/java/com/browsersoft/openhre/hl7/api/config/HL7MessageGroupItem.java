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
 * Class is parent for classes represents group item in group and match following
 * structure in configuration file hl7_XXX_messages.xml
 * <pre>
 *   &lt;group&gt;
 *     &lt;segment id="MSH"/&gt;
 *     &lt;segment id="MSA"/&gt;
 *     &lt;segment id="ERR" required="N"/&gt;
 *   &lt;/group&gt;
 * </pre>
 * if the <CODE>getType()</CODE> method return ITEM_TYPE_GROUP
 * or
 * <pre>
 *    &lt;segment id="MSH"/&gt;
 * </pre>
 * if the <CODE>getType()</CODE> method return ITEM_TYPE_SEGMENT
 */

public interface HL7MessageGroupItem {
	/**
	 * Item is a group. Use this value in getType()
	 */
	public static final int ITEM_TYPE_GROUP = 0;

	/**
	 * Item is a segment. Use this value in getType()
	 */
	public static final int ITEM_TYPE_SEGMENT = 1;


	/**
	 * Get the type of item. First you have to check this property and next
	 * cast the HL7MessageGroupItem to <CODE>HL7MessageGroup</CODE>  if the value
	 * is ITEM_TYPE_GROUP or  <CODE>HL7MessageSegment</CODE> if the value is ITEM_TYPE_SEGMENT
	 */
	public int getType();

	/**
	 * Returns whether the segment repeatable
	 *
	 * @return whether the segment repeatable
	 */
	public boolean isRepeatable();

	/**
	 * Sets the whether the segment repeatable
	 *
	 * @param repeatable whether the segment repeatable
	 */
	public void setRepeatable( boolean repeatable );

	/**
	 * Returns whether the segment required
	 *
	 * @return whether the segment required
	 */
	public boolean isRequired();

	/**
	 * Sets the whether the segment required
	 *
	 * @param required whether the segment required
	 */
	public void setRequired( boolean required );

}
