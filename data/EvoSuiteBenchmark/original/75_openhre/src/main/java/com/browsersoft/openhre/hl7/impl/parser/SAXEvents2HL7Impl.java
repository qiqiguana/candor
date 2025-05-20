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

import com.browsersoft.openhre.hl7.api.parse.SAXEvents2HL7;
import com.browsersoft.openhre.hl7.api.parse.ParserException;

import java.io.IOException;
import java.io.Writer;

public class SAXEvents2HL7Impl implements SAXEvents2HL7 {

    private static final int STATE_OUTSIDE =         0;
    private static final int STATE_IN_ROOT =         1;
    private static final int STATE_IN_SEGMENT =      2;
    private static final int STATE_IN_FIELD =        4;
    private static final int STATE_IN_COMPONENT =    5;
    private static final int STATE_IN_SUBCOMPONENT = 6;

    private boolean writeContent = false;

    private int state = 0;


    private int lastNumberField = 0;
    private int lastNumberComponents= 0;
    private int lastNumberSubcomponent = 0;

    //field separator
    private String segmentSeparator = "\n";
    //field separator
    private char fieldSeparator = '|';
    // component separator
    private char componentSeparator = '^';
    // repeatable separator
    private char repeatableSeparator = '~';
    //subcomponent separator
    private char subComponentSeparator = '&';
    //escape character
    private char escapeCharacter = '\\';

    private Writer writer;


    public Writer getWriter() {
        return writer;
    }

    public void setWriter( Writer writer ) {
        this.writer = writer;
    }

    public void processStart() throws IOException {
        state = STATE_OUTSIDE;
        writer.write("MSH");
        writer.write(fieldSeparator);
        writer.write(componentSeparator);
        writer.write(repeatableSeparator);
        writer.write(escapeCharacter);
        writer.write(subComponentSeparator);
    }

    public void processStartTag( String name ) throws IOException,  ParserException {
       switch ( state ) {
            case STATE_OUTSIDE:
                state = STATE_IN_ROOT;
                break;
            case STATE_IN_ROOT:
                if ( name.length() == 3 ) {
                    if ( !name.equals("MSH") ) {
                        writer.append(segmentSeparator);
                        writer.append(name);
                    }
                    state = STATE_IN_SEGMENT;
                    lastNumberField = 0;
                }
                break;
            case STATE_IN_SEGMENT:
                if ( name.equals("MSH.1") || name.equals("MSH.2") ) {
                    writeContent = false;
                    lastNumberField = 2;
                } else {
                    int actualNumber = getNumberOfTag(name);
                    if ( actualNumber == lastNumberField ) {
                        writer.append(repeatableSeparator);
                    } else if ( actualNumber > lastNumberField ) {
                        for ( int i = lastNumberField; i < actualNumber; i++ ) {
                            writer.append(fieldSeparator);
                        }
                    } else {
                        throw new ParserException( name, 40 );
                    }
                    lastNumberField =  actualNumber;
                    lastNumberComponents = 1;
                    lastNumberSubcomponent = 1;
                    writeContent = true;
                }
                state = STATE_IN_FIELD;
                break;
            case STATE_IN_FIELD: {
                int actualNumber = getNumberOfTag(name);
                if ( actualNumber >= lastNumberComponents ) {
                    for ( int i = lastNumberComponents; i < actualNumber; i++ ) {
                        writer.append(componentSeparator);
                    }
                    lastNumberComponents =  actualNumber;
                    lastNumberSubcomponent = 1;
                } else {
                    throw new ParserException( name, 40 );
                }
                state = STATE_IN_COMPONENT;
                break;
            }
            case STATE_IN_COMPONENT: {
                int actualNumber = getNumberOfTag(name);
                if ( actualNumber >= lastNumberSubcomponent ) {
                    for ( int i = lastNumberSubcomponent; i < actualNumber; i++ ) {
                        writer.append(subComponentSeparator);
                    }
                    lastNumberSubcomponent =  actualNumber;
                } else {
                    throw new ParserException( name, 40 );
                }
                state = STATE_IN_SUBCOMPONENT;
                break;
            }
        }

    }

    public void processText( String value ) throws IOException {
        if ( writeContent ) {
            value = value.trim();
            writer.write(value);
        }
    }

    public void processEndTag( String name ) throws IOException {

        switch ( state ) {
            case STATE_IN_SEGMENT:
                state = STATE_IN_ROOT;
                break;
            case STATE_IN_FIELD:
                state = STATE_IN_SEGMENT;
                break;
            case STATE_IN_COMPONENT:
                state = STATE_IN_FIELD;
                break;
            case STATE_IN_SUBCOMPONENT:
                state = STATE_IN_COMPONENT;
                break;
        }
    }

    private int getNumberOfTag( String tagName ) throws ParserException {
        int indexOfDot = tagName.indexOf('.');
        if ( indexOfDot > 0 ) {
            String num = tagName.substring(indexOfDot + 1);
            try {
                int number = Integer.parseInt(num);
                if ( number < 1 ) {
                    throw new ParserException(tagName, 40 );
                }
                return number;
            } catch ( Exception e ) {
                throw new ParserException(tagName, 40 );
            }
        } else {
            throw new ParserException(tagName, 40 );
        }
    }

    public char getFieldSeparator() {
        return fieldSeparator;
    }

    public void setFieldSeparator( char fieldSeparator ) {
        this.fieldSeparator = fieldSeparator;
    }

    public char getComponentSeparator() {
        return componentSeparator;
    }

    public void setComponentSeparator( char componentSeparator ) {
        this.componentSeparator = componentSeparator;
    }

    public char getRepeatableSeparator() {
        return repeatableSeparator;
    }

    public void setRepeatableSeparator( char repeatableSeparator ) {
        this.repeatableSeparator = repeatableSeparator;
    }

    public char getSubComponentSeparator() {
        return subComponentSeparator;
    }

    public void setSubComponentSeparator( char subComponentSeparator ) {
        this.subComponentSeparator = subComponentSeparator;
    }

    public char getEscapeCharacter() {
        return escapeCharacter;
    }

    public void setEscapeCharacter( char escapeCharacter ) {
        this.escapeCharacter = escapeCharacter;
    }

    public String getSegmentSeparator() {
        return segmentSeparator;
    }

    public void setSegmentSeparator( String segmentSeparator ) {
        this.segmentSeparator = segmentSeparator;
    }
}
