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

import com.browsersoft.openhre.hl7.api.config.HL7Configuration;
import com.browsersoft.openhre.hl7.api.parse.HL7Checker;
import com.browsersoft.openhre.hl7.api.parse.HL7SAXEventGenerator;
import com.browsersoft.openhre.hl7.api.parse.HL7SAXEventGeneratorHandler;
import com.browsersoft.openhre.hl7.api.parse.ParserException;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributesImpl;

import java.io.IOException;
import java.io.Reader;

public class HL7SAXEventGeneratorImpl implements HL7SAXEventGenerator {

    private ContentHandler contentHandler;
    private HL7SAXEventGeneratorHandler handler;
    private HL7Checker checker;
    private String idFile;
    AttributesImpl emptyAttr = new AttributesImpl();

    public HL7SAXEventGeneratorImpl() {
        checker = new HL7CheckerImpl();
        checker.setHandler(this);
    }

    public void convert( Reader reader, String messageID, HL7Configuration configuration ) throws IOException, ParserException {
        checker.setConfiguration(configuration);
        checker.readData(reader, messageID);
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
                contentHandler.endElement("urn:hl7-org:v2xml", text, text);
            } else {
                contentHandler.startElement("urn:hl7-org:v2xml", text, text, emptyAttr);
            }
        } catch ( SAXException e ) {
            error(HL7Checker.TYPE_ERROR, 12, e.getMessage(), "", "");
        }
    }

    private void writeText( String text ) {
        try {
            contentHandler.characters( text.toCharArray(), 0, text.length());
        } catch ( SAXException e ) {
            error(HL7Checker.TYPE_ERROR, 12, e.getMessage(), "", "");
        }
    }

    public void error( int type, int code, String message, String segmentID, String fieldID ) {
        if ( handler != null ) {
            handler.error(idFile, type, code, message, segmentID, fieldID);
        }
    }


    public ContentHandler getContentHandler() {
        return contentHandler;
    }

    public void setContentHandler( ContentHandler contentHandler ) {
       this.contentHandler = contentHandler;
    }

    public HL7SAXEventGeneratorHandler getHandler() {
        return handler;
    }

    public void setHandler( HL7SAXEventGeneratorHandler handler ) {
        this.handler = handler;
    }

    public HL7Checker getChecker() {
        return checker;
    }

    public void setChecker( HL7Checker checker ) {
        this.checker = checker;
    }

    public String getIDFile() {
        return idFile;
    }

    public void setIDFile( String idFile ) {
        this.idFile = idFile;
    }

}