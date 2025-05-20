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

import com.browsersoft.openhre.hl7.api.config.HL7PatternsForCatchValues;

import java.util.HashMap;

public class HL7PatternsForCatchValuesImpl implements HL7PatternsForCatchValues {

    private HashMap map;

    public HL7PatternsForCatchValuesImpl() {
        map = new HashMap();
    }

    public int size() {
        return map.size();
    }

    public boolean patternExist( String pattern ) {
        return map.containsKey(pattern);
    }

    public String[] getPatterns() {
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

    public void addPattern( String pattern ) {
       map.put(pattern, "");
    }

    public void setValueForPattern( String pattern, String value ) {
        if ( map.containsKey(pattern)) {
           //map.remove(pattern);
           map.put(pattern, value);
        }
    }

    public String getValueForPattern( String pattern ) {
        if ( map.containsKey(pattern)) {
            return (String) map.get(pattern);
        } else {
            return "";
        }
    }

    public void clearPatternValues() {
        String[] patterns = getPatterns();
        for ( int i = 0 ; i < patterns.length ; i++ ) {
           setValueForPattern( patterns[i], "");
        }
    }
}
