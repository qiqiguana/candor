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

import com.browsersoft.openhre.hl7.api.parse.XML2HL7;
import com.browsersoft.openhre.hl7.api.parse.SAXEvents2HL7;
import com.browsersoft.openhre.hl7.api.parse.XML2HL7Handler;
import com.browsersoft.openhre.hl7.api.parse.ParserException;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.Reader;
import java.io.Writer;
import java.io.IOException;

public class XML2HL7Impl implements XML2HL7, ContentHandler, ErrorHandler {

    private SAXEvents2HL7 saxEvents2HL7;
    private XML2HL7Handler handler;


    public void convert( Reader reader, Writer writer ) throws SAXException, IOException {

        saxEvents2HL7 = new SAXEvents2HL7Impl();
        saxEvents2HL7.setWriter(writer);
        saxEvents2HL7.processStart();

        // create parser
        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");

        // setting parser and run
        parser.setContentHandler(this);
        parser.setErrorHandler(this);
        parser.parse(new InputSource(reader));


    }



    public void startElement( String uri, String localName, String qName, Attributes atts ) throws SAXException {

        try {
            //System.out.println(localName);
            saxEvents2HL7.processStartTag(localName);
        } catch ( ParserException e ) {
           if ( handler != null ) {
               handler.error( e.getPostfix() );
            }
        } catch ( IOException e ) {
            if ( handler != null ) {
               handler.error( e.getMessage() );
            }
        } /*catch ( Exception e ) {
            if ( handler != null ) {
               handler.error( e.getMessage() );
            }
        }      */

    }

    public void endElement( String uri, String localName, String qName ) throws SAXException {

        try {
            saxEvents2HL7.processEndTag(localName);
        } catch ( IOException e ) {
            if ( handler != null ) {
               handler.error( e.getMessage() );
            }
        }

    }

    public void characters( char ch[], int start, int length ) throws SAXException {

        try {
            saxEvents2HL7.processText( String.valueOf( ch, start, length ));
        } catch ( IOException e ) {
            if ( handler != null ) {
               handler.error( e.getMessage() );
            }
        }

    }


    public void warning( SAXParseException exception ) throws SAXException {
        if ( handler != null ) {
           handler.error( exception.getMessage() );
        }
    }

    public void error( SAXParseException exception ) throws SAXException {
        if ( handler != null ) {
            handler.error( exception.getMessage() );
        }
    }

    public void fatalError( SAXParseException exception ) throws SAXException {
        if ( handler != null ) {
            handler.error( exception.getMessage() );
        }
    }

    public SAXEvents2HL7 getSaxEvents2HL7() {
        return saxEvents2HL7;
    }

    public void setSaxEvents2HL7( SAXEvents2HL7 saxEvents2HL7 ) {
        this.saxEvents2HL7 = saxEvents2HL7;
    }

    public XML2HL7Handler getHandler() {
        return handler;
    }

    public void setHandler( XML2HL7Handler handler ) {
        this.handler = handler;
    }

    // ignore

    public void ignorableWhitespace( char ch[], int start, int length ) throws SAXException {}

    public void processingInstruction( String target, String data ) throws SAXException {}

    public void skippedEntity( String name ) throws SAXException {}

    public void setDocumentLocator( Locator locator ) { }

    public void startDocument() throws SAXException {}

    public void endDocument() throws SAXException {}

    public void startPrefixMapping( String prefix, String uri ) throws SAXException {}

    public void endPrefixMapping( String prefix ) throws SAXException {}

}
