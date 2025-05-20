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

import com.browsersoft.openhre.hl7.api.parse.HL7Parser;
import com.browsersoft.openhre.hl7.api.parse.HL7ParserHandler;
import com.browsersoft.openhre.hl7.api.parse.ParserException;

import java.io.IOException;
import java.io.Reader;

public class HL7ParserImpl implements HL7Parser {

    private HL7ParserHandler handler;

    private int initBufferSize;

    private char[] textBuffer;
    private char[] mshHeaderBuffer = new char[8];
    private char[] segmentHeaderBuffer = new char[3];

    private int actualPossitionInTextBuffer;

    public HL7ParserImpl( int initBufferSize ) {
         this.initBufferSize = initBufferSize;
         textBuffer = new char[initBufferSize];
    }

    //segments separator - \r is HL7 standard, \n is NL unix endings
    private static final char CR = '\r';

    //field separator
    private char FS;
    // component separator
    private char CS;
    //subcomponent separator
    private char SS;
    // repeatable separator
    private char RS;
    //escape character
    private char EC;

    private boolean fieldOpened = false;


    public void readData( Reader source ) throws IOException, ParserException {

        actualPossitionInTextBuffer  = -1;

        if ( source.read(mshHeaderBuffer) == (-1) ) {
            throw new ParserException("", 10);
        }

        //check the "MSH"
        if ( mshHeaderBuffer[0] != 'M' || mshHeaderBuffer[1] != 'S' || mshHeaderBuffer[2] != 'H' ) {
            throw new ParserException("MSH", 10);
        }

        // set the characters
        FS = mshHeaderBuffer[3];  // |
        CS = mshHeaderBuffer[4];  // ^
        RS = mshHeaderBuffer[5];  // ~
        EC = mshHeaderBuffer[6];  // \
        SS = mshHeaderBuffer[7];  // &

        handler.beginSegment("MSH");
        handler.beginField();
        handler.component(String.valueOf(mshHeaderBuffer, 4, 4));
        handler.endField();

        char begin = (char) source.read();
        if ( begin != FS ) {
            throw new ParserException("", 11);
        }

        char previous = ' ';
        int now;

        while ((now = source.read()) != (-1)) {

            if ( previous != '\\' && ( now == FS || now == CS || now == RS || now == SS || now == CR ) ) {

                doOneCharacter(begin, (char) now,  getBufferValue());
                actualPossitionInTextBuffer = -1;
                begin = (char) now;

            } else {

                addCharacter((char) now);

            }

            if ( now == CR ) {
                if ( source.read(segmentHeaderBuffer) == (-1) ) {
                    return;
                }
                handler.beginSegment(new String(segmentHeaderBuffer));

                begin = (char) source.read();
                if ( begin != FS ) {
                    throw new ParserException("", 11);
                }
                continue;
            }


            previous = (char) now;

        }

        doOneCharacter( begin, CR,  getBufferValue());
        handler.endOfRecord();
    }

    private String getBufferValue() {
       if ( actualPossitionInTextBuffer == -1 ) {
           return "";
       }
       String out = new String(textBuffer, 0, actualPossitionInTextBuffer + 1).trim();
       return out;
    }

    private void addCharacter( char ch ) {
        actualPossitionInTextBuffer++;
        if ( actualPossitionInTextBuffer >= textBuffer.length ) {
            char[] newTextBuffer = new char[textBuffer.length + initBufferSize];
            for ( int i = 0; i < textBuffer.length ; i++ ) {
                newTextBuffer[i] = textBuffer[i];
            }
            textBuffer = newTextBuffer;
        }
        textBuffer[actualPossitionInTextBuffer] = ch;

    }

    private void doOneCharacter( char b, char e, String value ) {

        if ( b == FS && e == CS ) {
            handler.beginField();
            handler.component(value);
        } else if ( b == FS && e == FS ) {
            if ( value.equals("")) {
                handler.emptyField();
            } else {
                handler.beginField();
                handler.component(value);
                handler.endField();
            }
        } else if ( b == FS && e == CR ) {
            if ( !value.equals("")) {
                handler.beginField();
                handler.component(value);
                handler.endField();
            }
            handler.endSegment();
        } else if ( b == FS && e == RS ) {
            if ( value.equals("")) {
                handler.emptyField();
            } else {
                handler.beginField();
                handler.component(value);
                handler.endField();
            }
        } else if ( b == FS && e == SS ) {
            handler.beginField();
            handler.beginComponent();
            handler.subComponent(value);
        } else if ( b == CS && e == FS ) {
            handler.component(value);
            handler.endField();
        } else if ( b == CS && e == CR ) {
            handler.component(value);
            handler.endField();
            handler.endSegment();
        } else if ( b == CS && e == RS ) {
            handler.component(value);
            handler.endField();
        } else if ( b == CS && e == SS ) {
            handler.beginComponent();
            handler.subComponent(value);
        } else if ( b == CS && e == CS ) {
            handler.component(value);
        } else if ( b == RS && e == FS ) {
            if ( value.equals("")) {
                handler.emptyRepeateField();
            } else {
                handler.beginFieldRepeate();
                handler.component(value);
                handler.endField();
            }
        } else if ( b == RS && e == CR ) {
            if ( !value.equals("")) {
                handler.beginFieldRepeate();
                handler.component(value);
                handler.endField();
            }
            handler.endSegment();
        } else if ( b == RS && e == SS ) {
            handler.beginFieldRepeate();
            handler.beginComponent();
            handler.subComponent(value);
        } else if ( b == RS && e == RS ) {
            if ( value.equals("")) {
                handler.emptyRepeateField();
            } else {
                handler.beginFieldRepeate();
                handler.component(value);
                handler.endField();
            }
        } else if ( b == RS && e == CS ) {
            handler.beginFieldRepeate();
            handler.component(value);
        } else if ( b == SS && e == CS ) {
            handler.subComponent(value);
            handler.endComponent();
        } else if ( b == SS && e == FS ) {
            handler.subComponent(value);
            handler.endComponent();
            handler.endField();
        } else if ( b == SS && e == CR ) {
            handler.subComponent(value);
            handler.endComponent();
            handler.endField();
            handler.endSegment();
        } else if ( b == SS && e == RS ) {
            handler.subComponent(value);
            handler.endComponent();
            handler.endField();
        } else if ( b == SS && e == SS ) {
            handler.subComponent(value);
        }

    }

    public HL7ParserHandler getHandler() {
        return handler;
    }

    public void setHandler( HL7ParserHandler handler ) {
        this.handler = handler;
    }

}
