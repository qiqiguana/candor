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
import com.browsersoft.openhre.hl7.api.config.HL7DataType;
import com.browsersoft.openhre.hl7.api.config.HL7DataTypePartPrimitive;
import com.browsersoft.openhre.hl7.api.config.HL7Field;
import com.browsersoft.openhre.hl7.api.config.HL7Message;
import com.browsersoft.openhre.hl7.api.config.HL7Segment;
import com.browsersoft.openhre.hl7.api.parse.HL7Checker;
import com.browsersoft.openhre.hl7.api.parse.HL7CheckerHandler;
import com.browsersoft.openhre.hl7.api.parse.HL7CheckerState;
import com.browsersoft.openhre.hl7.api.parse.HL7Parser;
import com.browsersoft.openhre.hl7.api.parse.ParserException;

import java.io.IOException;
import java.io.Reader;


public class HL7CheckerImpl implements HL7Checker {

    private HL7Parser parser;
    private HL7CheckerHandler handler;
    private HL7Configuration configuration;

    private HL7CheckerState state;

    public HL7CheckerImpl() {
        parser = new HL7ParserImpl(300);
        parser.setHandler(this);
        state = new HL7CheckerStateImpl();
    }

    public void readData( Reader source, String messageID ) throws IOException, ParserException {

        state.setConfiguration(configuration);
        state.setCheckerHandler(handler);
        state.getConfiguration().getPatterns().clearPatternValues();
        HL7Message actualMessage = configuration.getMessages().getItem(messageID);

        if ( actualMessage == null ) {
            throw new ParserException(messageID, 13);
        }

        state.setMessageTracer(actualMessage.getTracer());
        state.getMessageTracer().setHandler(this);
        state.getMessageTracer().reset();

        parser.readData(source);
    }

    public void messageTracerSegmentEvent( HL7Segment segment ) {
        state.setActualSegment(segment);
    }

    public void messageTracerErrorEvent( String segment ) {
        state.setSeriousError(true);
        state.generateError(14, segment);
    }

    public void messageTracerAdditionalTagBeginEvent( String tagName ) {
        handler.beginAdditionalTag(tagName);
    }

    public void messageTracerAdditionalTagEndEvent( String tagName ) {
        handler.endAdditionalTag(tagName);
    }


    public void beginSegment( String segmentID ) {
        if ( !state.isSeriousError() ) {

            state.eventBeginSegment(segmentID);

            if ( state.isSeriousError() ) return;

            if ( state.getActualSegment() != null ) {
                handler.beginSegment(getNameOfActualSegment());
            }
        }

    }


    public void endSegment() {
        if ( !state.isSeriousError() ) {
            if ( state.getActualSegment() != null ) {
                handler.endSegment(getNameOfActualSegment());
            }
        }
    }

    public void beginField() {
        if ( !state.isSeriousError() ) {

            state.eventBeginField(false);
            if ( state.isSeriousError() ) return;

            if ( state.getActualField() != null ) {
                generateBeginField(false);
            }
        }
    }

    public void endField() {
        if ( !state.isSeriousError() ) {
            processEndField(false);
        }
    }


    public void beginFieldRepeate() {
        if ( !state.isSeriousError() ) {

            state.eventBeginField(true);
            if ( state.isSeriousError() ) return;

            if ( state.getActualField() != null ) {
                generateBeginField(false);
            }
        }
    }

    public void emptyRepeateField() {
        if ( !state.isSeriousError() ) {
            state.eventBeginField(true);

            if ( state.isSeriousError() ) return;

            if ( state.getActualField() != null ) {
                generateBeginField(true);
                controlEmptyRequired();
                processEndField(true);
            }
        }
    }

    public void emptyField() {
        if ( !state.isSeriousError() ) {
            state.eventBeginField(false);

            if ( state.isSeriousError() ) return;

            if ( state.getActualField() != null ) {
                generateBeginField(true);
                controlEmptyRequired();
                processEndField(true);
            }
        }
    }

    private void processEndField( boolean empty ) {
        if ( state.getActualField() != null ) {
            if ( state.getActualFieldLength() > state.getActualField().getMaximumLength() ) {
                state.generateWarning(19, getNameOfActualField() + " length " + state.getActualFieldLength() +
                        " maximum length: " + state.getActualField().getMaximumLength());                   //Field is longer than maximum lengh
            }
            generateEndField(empty);
        }
    }


    public void controlEmptyRequired() {
        switch ( state.getActualField().getRequired() ) {
            case HL7Field.REQUIRED_R:
                state.generateWarning(18, getNameOfActualField());  //Field is required
                break;

        }
    }

    public void beginComponent() {
        if ( !state.isSeriousError() ) {
            state.eventBeginComponent();
            if ( state.isSeriousError() ) return;
            if ( state.getActualComponentDataType() == null ) {
                return;
            }

            generateBeginComponent(false);
        }
    }

    public void endComponent() {
        if ( !state.isSeriousError() ) {
            generateEndComponent(false);
        }
    }

    private void updatePatterns( String value ) {

        if ( state.getActualSegment() != null && state.getActualField() != null ) {
            String pattern = state.getActualSegment().getID() + "." + state.getActualField().getSequential();
            if ( configuration.getPatterns().patternExist(pattern) ) {
                configuration.getPatterns().setValueForPattern(pattern, value.trim());
            }
        }
    }

    public void component( String value ) {

        if ( !state.isSeriousError() ) {

            updatePatterns(value);

            if ( state.getActualDataType() == null ) {
                return;
            }
            state.eventBeginComponent();
            if ( state.isSeriousError() ) return;

            if ( state.getActualDataType() == null ) {
                return;
            }

            boolean isValueEmpty = isValueEmpty(value);

            if ( state.isDataTypePrimitive(state.getActualDataType()) ) {
                processPrimitiveValue(state.getActualDataType(), value);
                state.addToActualFieldLength(value.length());
                //System.out.println( getNameOfActualField() + " length: "+ value + "(" + value.length() + ")" );
                if ( !isValueEmpty ) {
                    handler.componentValue(value);
                }
                return;
            }

            HL7DataType dataType = state.getActualComponentDataType();

            if ( dataType == null ) {
                return;
            }

            if ( state.isDataTypePrimitive(dataType) ) {
                generateBeginComponent(isValueEmpty);
                processPrimitiveValue(dataType, value);
                state.addToActualFieldLength(value.length());
                handler.componentValue(value);
                generateEndComponent(isValueEmpty);

            } else {

                if ( !isValueEmpty ) {
                    generateBeginComponent(false);
                    subComponent(value);
                    generateEndComponent(false);
                } else {
                    generateBeginComponent(true);
                    generateEndComponent(true);
                }
            }
        }
    }

    public void subComponent( String value ) {
        if ( !state.isSeriousError() ) {
            if ( !isValueEmpty(value) ) {
                state.eventBeginSubComponent();
                if ( state.isSeriousError() ) return;
                processPrimitiveValue(state.getActualSubComponentDataType(), value);
                state.addToActualFieldLength(value.length());
                generateSubComponent(value);
            }
        }

    }

    private boolean isValueEmpty( String value ) {
         return value.equals("");
    }

    public void endOfRecord() {
        if ( !state.isSeriousError() ) {
            state.getMessageTracer().processEnd();
        }
    }


    private void processPrimitiveValue( HL7DataType dataType, String value ) {
        if ( dataType != null ) {
            HL7DataTypePartPrimitive primitivePart = (HL7DataTypePartPrimitive) dataType.getParts().getItem(0);
            try {
                primitivePart.getInstanceReader().readValue(value);
            } catch ( ParserException e ) {
                state.generateWarning(e.getMessageCode(), e.getPostfix());
            }
        }
    }

    /*
    * *****************************************************
    * UTILITIES
    * *****************************************************
    */

    private void generateBeginField( boolean empty ) {
        handler.beginField(getNameOfActualSegment(), Integer.parseInt(state.getActualField().getSequential()), empty);
    }

    private void generateEndField( boolean empty ) {
        handler.endField(getNameOfActualSegment(), Integer.parseInt(state.getActualField().getSequential()), empty);

    }

    private void generateBeginComponent( boolean empty ) {
        //System.out.println("generateBeginComponent:" + state.getActualDataType().getID());
        handler.beginComponent(state.getActualDataType().getID(), state.getActualDataTypePartsPossition(), empty);
    }

    private void generateEndComponent( boolean empty ) {
        handler.endComponent(state.getActualDataType().getID(), state.getActualDataTypePartsPossition(), empty);
    }

    private void generateSubComponent( String value ) {
        handler.subComponent(state.getActualComponentDataType().getID(), state.getActualDataTypeSubPartsPossition(), value);
    }

    private String getNameOfActualSegment() {
        return state.getActualSegment().getID().substring(0, 3);
    }

    private String getNameOfActualField() {
        return state.getActualSegment().getID().substring(0, 3) + "." + state.getActualField().getSequential();
    }


    public HL7Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration( HL7Configuration configuration ) {
        this.configuration = configuration;
    }

    public HL7Parser getParser() {
        return parser;
    }

    public void setParser( HL7Parser parser ) {
        this.parser = parser;
    }

    public HL7CheckerHandler getHandler() {
        return handler;
    }

    public void setHandler( HL7CheckerHandler handler ) {
        this.handler = handler;
    }

    public HL7CheckerState getState() {
        return state;
    }

    public void setState( HL7CheckerState state ) {
        this.state = state;
    }

}
