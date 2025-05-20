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

import com.browsersoft.openhre.hl7.api.parse.HL72XML;
import com.browsersoft.openhre.hl7.api.parse.ParserException;
import com.browsersoft.openhre.hl7.api.parse.HL72XMLHandler;
import com.browsersoft.openhre.hl7.api.parse.HL7Checker;
import com.browsersoft.openhre.hl7.api.config.HL7Configuration;

import java.io.Reader;
import java.io.Writer;
import java.io.IOException;

public class HL72XMLImpl implements HL72XML {

    private HL72XMLHandler handler;
    private HL7Checker checker;
    private Writer writer;
    private XMLUtils xmlUtils;

    public HL72XMLImpl() {
        checker = new HL7CheckerImpl();
        checker.setHandler(this);
        xmlUtils = new XMLUtils();
    }


    public void convert( Reader reader, String messageID, Writer writer, HL7Configuration configuration ) throws IOException, ParserException {
        this.writer = writer;
        checker.setConfiguration(configuration);
        checker.readData(reader, messageID);
        writer.flush();
    }

    public void beginAdditionalTag ( String tagName ) {
        writeTag(tagName, false);
    }

    public void endAdditionalTag ( String tagName ) {
        writeTag(tagName, true);
    }

    public void beginSegment( String segmentID ) {
        writeTag(segmentID, false);
    }

    public void endSegment( String segmentID ) {
        writeTag(segmentID, true);
    }

    public void beginField ( String segmentID, int position, boolean empty ) {
        if ( !empty ) {
            writeTag(segmentID + "." + position, false);
        }
    }

    public void endField ( String segmentID, int position, boolean empty ) {
        if ( !empty ) {
            writeTag(segmentID + "." + position, true);
        }
    }

    public void componentValue( String stringValue ) {
           writeText(stringValue);
    }

    public void beginComponent( String dataType, int position, boolean empty ) {
        if ( !empty ) {
            writeTag(dataType + "." + position , false);
        }
    }

    public void endComponent( String dataType, int position, boolean empty ) {
        if ( !empty ) {
            writeTag(dataType + "." + position , true);
        }
    }

    public void subComponent( String dataType, int position, String value ) {
        if ( !value.trim().equals("") ) {
            String tagName = dataType + "." + position;
            writeTag(tagName, false);
            writeText(value);
            writeTag(tagName, true);
        }
    }


    private void writeTag( String text, boolean close ) {
        try {
            if ( close ) {
               writer.write("</" + text + ">\n");
            } else {
               writer.write("<" + text + ">\n");
            }
        } catch ( IOException e ) {
            error(HL7Checker.TYPE_ERROR, 12, e.getMessage(), "", "");
        }
    }

    private void writeText( String text ) {
        try {
            writer.write(xmlUtils.escapeString(text) + "\n");
        } catch ( IOException e ) {
            error(HL7Checker.TYPE_ERROR, 12, e.getMessage(), "", "");
        }
    }

    public void error( int type, int code, String message, String segmentID, String fieldID ) {
        if ( handler != null ) {
            handler.error(type, code, message, segmentID, fieldID);
        }
    }

    public void setHandler( HL72XMLHandler handler ) {
        this.handler = handler;
    }

    public HL72XMLHandler getHandler() {
        return handler;
    }

    public HL7Checker getChecker() {
        return checker;
    }

    public void setChecker( HL7Checker checker ) {
        this.checker = checker;
    }

}
