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


package com.browsersoft.openhre.hl7.api;

import com.browsersoft.openhre.hl7.api.config.InvalidConfigDataStructureException;
import com.browsersoft.openhre.hl7.api.config.HL7VersionConfigurationMap;
import org.xml.sax.SAXException;

import java.io.IOException;

/**
 * This class serves as a tool for reading whole default configuration for this library
 */
public interface HL7DefaultConfigurationMaker {

    /**
     * Read the default configuration with usage config files ( in this time from files in package om.browsersoft.openhre.hl7.config )
     * @return map of configurations
     * @throws InvalidConfigDataStructureException
     * @throws IOException
     * @throws SAXException
     */
     public HL7VersionConfigurationMap readConfiguration() throws InvalidConfigDataStructureException, IOException, SAXException;

}