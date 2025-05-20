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
 * Map of configurations. The keys are names of configuration ( for example - "T2_1.0" or "HL7_2.3.1" )
 */
public interface HL7VersionConfigurationMap {
    /**
     * return size of map
     * @return size of map
     */
    public int size();

    /**
     * Returns array of ids in map
     *
     * @return array of ids
     */
    public String[] getConfigurationsKeys();

    /**
     * clear all configurations
     */
    public void clearAll();

    /**
     * add to configuration to the map
     * @param version identifier of version
     * @param configuration configuration
     */
    public void addConfiguration( String version, HL7Configuration configuration );

    /**
     * get the configuration for my version identifier.
     * @param version identifier
     * @return configuration
     */
    public HL7Configuration getSuitableConfiguration( String version );

}
