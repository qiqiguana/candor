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
 * This class represent map collection of pattern and their catching values ( it is using in field OBX.2 )
 */
public interface HL7PatternsForCatchValues {

    /**
	 * Returns size of map
	 *
	 * @return size of map
	 */
    public int size();

    /**
     * does pattern exist?
     * @param pattern
     * @return true of false
     */
    public boolean patternExist( String pattern );

    /**
	 * Returns array of ids in map
	 *
	 * @return array of ids
	 */
    public String[] getPatterns();

    /**
	 * Remove all items from map
	 */
    public void clearAll();

    /**
	 * Add pattern item to map
	 *
	 * @param pattern pattern
	 */
    public void addPattern( String pattern );

    /**
     * Set the value for pattern
     * @param pattern pattern
     * @param value value
     */
    public void setValueForPattern( String pattern, String value );

    /**
     * Set the pattern value
     * @param pattern pattern
     * @return value
     */
    public String getValueForPattern( String pattern );

    /**
     * Clear patterns values
     */
    public void clearPatternValues();

}
