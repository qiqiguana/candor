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
 * Class represents list of table items and match following
 * structure in configuration file hl7_XXX_tables.xml
 * <pre>
 * &lt;items&gt;
 *    &lt;item value="F" description="Female"/&gt;
 *    &lt;item value="M" description="Male"/&gt;
 *    &lt;item value="O" description="Other"/&gt;
 *    &lt;item value="U" description="Unknown"/&gt;
 * &lt;/items&gt;
 * </pre>
 */
public interface HL7TableItemMap {

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
        public String[] getItemsValues();

        /**
         * Returns <CODE>HL7TableItem</CODE> if exist in map, otherwise return null
         *
         * @param id id of segment
         * @return item <CODE>HL7Segment</CODE> or null
         */
        public HL7TableItem getItem( String id );

        /**
         * Add <CODE>HL7TableItem</CODE> item to map
         *
         * @param value value
         */
        public void addItem( HL7TableItem value );

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
