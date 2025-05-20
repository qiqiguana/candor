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


package com.browsersoft.openhre.hl7.impl.parser;

import java.util.HashMap;


public class XMLUtils {

    private static final String HTMLnames [] = {

            /*38*/ "amp",
            /*60*/ "lt",
            /*62*/ "gt",
            /*34*/ "quot",

    };

    private static final int HTMLcodes [] = {

            38, /* "amp" */
            60, /* "lt" */
            62, /* "gt" */
            34, /* "quot" */
    };

    private HashMap entity2codes = new HashMap();
    private HashMap codes2entity = new HashMap();

    public XMLUtils() {
        for ( int i = 0; i < HTMLnames.length; i++ ) {
            entity2codes.put(HTMLnames[i], new Integer(HTMLcodes[i]));
            codes2entity.put(new Integer(HTMLcodes[i]), HTMLnames[i]);
        }
    }

    public String escapeString( String text ) {

        StringBuffer buffer = new StringBuffer();
        String esc = "";
        for ( int i = 0; i < text.length(); i++ ) {
            esc = "";
            char c = text.charAt(i);

            if ( (c > 0xfffd)
                    || ((c < 0x0020) && !((c == 0x0009)
                    || (c == 0x000A) || (c == 0x000D))) ) {
                esc = null;
            } else {
                Integer code = new Integer(c);
                if ( codes2entity.containsKey(code) ) {
                    esc = (String) codes2entity.get(new Integer(c));
                }
            }

            // else escape with numeric char refs
            if ( esc.equals("") ) {
                buffer.append(c);
            } else if ( esc == null ) {
                //nothing  - no character
            } else {
                buffer.append('&');
                buffer.append(esc);
                buffer.append(';');
            }

        }

        return buffer.toString();
    }

}
