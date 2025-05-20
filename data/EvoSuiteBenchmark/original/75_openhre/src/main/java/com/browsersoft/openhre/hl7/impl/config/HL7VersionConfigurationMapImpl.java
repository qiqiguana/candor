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


package com.browsersoft.openhre.hl7.impl.config;

import com.browsersoft.openhre.hl7.api.config.HL7Configuration;
import com.browsersoft.openhre.hl7.api.config.HL7VersionConfigurationMap;

import java.util.HashMap;

public class HL7VersionConfigurationMapImpl implements HL7VersionConfigurationMap {

    private HashMap map;

    public HL7VersionConfigurationMapImpl() {
        map = new HashMap();
    }

    public int size() {
        return map.size();
    }

    public String[] getConfigurationsKeys() {
        Object[] keysAsObjects = map.keySet().toArray();
        String[] keysAsString = new String[keysAsObjects.length];

        for ( int i = 0; i < keysAsObjects.length; i++ ) {
            keysAsString[i] = (String) keysAsObjects[i];
        }

        return keysAsString;
    }


    public void clearAll() {
        map.clear();
    }

    public void addConfiguration( String version, HL7Configuration configuration ) {
        map.put(version, configuration);
    }

    public HL7Configuration getSuitableConfiguration( String version ) {
        if ( map.containsKey(version) ) {
            return (HL7Configuration) map.get(version);
        } else {
            return null;
        }
    }
}
