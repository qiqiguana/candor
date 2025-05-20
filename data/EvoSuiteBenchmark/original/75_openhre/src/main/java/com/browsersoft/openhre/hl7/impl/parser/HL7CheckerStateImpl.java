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
import com.browsersoft.openhre.hl7.api.config.HL7DataTypePart;
import com.browsersoft.openhre.hl7.api.config.HL7DataTypePartSubPart;
import com.browsersoft.openhre.hl7.api.config.HL7Field;
import com.browsersoft.openhre.hl7.api.config.HL7FieldList;
import com.browsersoft.openhre.hl7.api.config.HL7Segment;
import com.browsersoft.openhre.hl7.api.parse.HL7Checker;
import com.browsersoft.openhre.hl7.api.parse.HL7CheckerHandler;
import com.browsersoft.openhre.hl7.api.parse.HL7CheckerState;
import com.browsersoft.openhre.hl7.api.parse.ParserException;
import com.browsersoft.openhre.hl7.api.regular.MessageTracer;

public class HL7CheckerStateImpl implements HL7CheckerState {

    private HL7CheckerHandler handler;

    //segment
    private HL7Segment actualSegment;
    private HL7Configuration configuration;

    //field
    private HL7Field actualField;
    private int actualFieldPosition;
    private int actualFieldRepeatableIndex;
    private int actualFieldLength = 0;

    //part
    private HL7DataType actualDataType;
    private int actualDataTypePartsPossition = 0;
    private HL7DataType actualComponentDataType;
    private int actualDataTypeSubPartsPossition = 0;
    private HL7DataType actualSubComponentDataType;


    private boolean seriousError;
    private MessageTracer messageTracer;

    public boolean isSeriousError() {
        return seriousError;
    }

    public void setSeriousError( boolean seriousError ) {
       this.seriousError = seriousError;
    }

    public MessageTracer getMessageTracer() {
        return messageTracer;
    }

    public void setMessageTracer( MessageTracer messageTracer ) {
        this.messageTracer = messageTracer;
    }

    /* Subcomponents function */
    public void eventBeginSubComponent() {

        if ( actualComponentDataType == null ) {
           return;
        }

        if ( actualDataTypeSubPartsPossition >=  actualComponentDataType.getParts().size() ) {
           generateError( 27, actualComponentDataType.getID());
           setSeriousError(true);
           return;
        }

        HL7DataTypePart part = actualComponentDataType.getParts().getItem(actualDataTypeSubPartsPossition);

        if ( part.getType() == HL7DataTypePart.PART_TYPE_PRIMITIVE ) {
            // only for single subcomponent
            actualSubComponentDataType = actualComponentDataType;
            return;
        }

        String idOfSubpart = ((HL7DataTypePartSubPart) part).getSubPartID();
        actualSubComponentDataType = configuration.getDataTypes().getItem(idOfSubpart);

        if ( !isDataTypePrimitive(actualSubComponentDataType)) {
            generateError( 25, actualSubComponentDataType.getID());
            setSeriousError(true);
            return;
        }

        if ( actualSubComponentDataType == null ) {
           generateError ( 9, idOfSubpart);
            setSeriousError(true);
            return;
        }

        actualDataTypeSubPartsPossition++;

    }

    public void addToActualFieldLength( int length ) {
        actualFieldLength += length;
    }

    /* Component functions */
    public void eventBeginComponent() {

        resetBeginComponent();

        if ( actualDataType == null ) {
            return;
        }

        // CM data type
        if ( actualDataType.getID().equals("CM")) {
            actualComponentDataType = configuration.getDataTypes().getItem("ST");
            return;
        }

        if ( isDataTypePrimitive( actualDataType ) ) {
            //more components in primitive type
            if ( actualDataTypePartsPossition > 0) {
                generateError( 22, actualDataType.getID() );
                setSeriousError(true);
                return;
            }
            actualComponentDataType = actualDataType;

        } else {

            if ( actualDataTypePartsPossition >=  actualDataType.getParts().size() ) {
                generateError( 22, actualDataType.getID() + " " + (actualDataTypePartsPossition + 1) + "th(defined:" + actualDataType.getParts().size() + ")");
                setSeriousError(true);
                return;
            }
            HL7DataTypePart part = actualDataType.getParts().getItem(actualDataTypePartsPossition);
            if ( part.getType() == HL7DataTypePart.PART_TYPE_PRIMITIVE ) {
                //something wrong - Primitive type may be defined only as single subpart
                generateError( 26, actualDataType.getID());
                setSeriousError(true);
                return;
            }
            String idOfSubPart = ((HL7DataTypePartSubPart) part).getSubPartID();

            actualComponentDataType = configuration.getDataTypes().getItem(idOfSubPart);

            if ( actualComponentDataType == null ) {
                generateError ( 9, idOfSubPart);
                setSeriousError(true);
                return;
            }

        }

        actualDataTypePartsPossition++;

    }


    public boolean isDataTypePrimitive( HL7DataType dataType ) {
        if ( dataType.getParts().size() != 1 ) {
            return false;
        } else {
            return dataType.getParts().getItem(0).getType() == HL7DataTypePart.PART_TYPE_PRIMITIVE;
        }
    }

    /* Field functions */
    public void eventBeginField( boolean repeatable ) {
        //System.out.println("eventBeginField:" + repeatable );
        if ( actualSegment == null ) {
            return;
        }

        if ( !repeatable ) {
            //set the actual field
            resetBeginField();
            HL7FieldList fields = actualSegment.getFields();

            if ( actualFieldPosition >= 0 && actualFieldPosition < fields.size() ) {
                actualField = fields.getItem(actualFieldPosition);
                actualFieldPosition++;
            } else {
                generateError ( 16, getNameOfActualField());  // More fields in segment then defined
                actualField = null;
                setSeriousError(true);
                return;
            }

        } else {
            resetBeginRepeatableField();

        }

        findActualDataType();

    }


    private void findActualDataType() {

        if ( actualField == null ) {
            return;
        }

        switch ( actualField.getDependingType() ) {
            case HL7Field.TYPE_DEPENDING_NORMAL:
               String typeID = actualField.getDataTypeID();
               actualDataType = configuration.getDataTypes().getItem(typeID);
               if ( actualDataType == null ) {
                 generateError ( 9, typeID);
                 setSeriousError(true);
                 return;
               }
               break;
            case HL7Field.TYPE_DEPENDING_REPEATABLE:
            case HL7Field.TYPE_DEPENDING_GIVEN:
                try {
                    actualDataType = actualField.getDependingProcessor().getDataTypeForSituation(this);
                } catch ( ParserException e ) {
                    generateError( e.getMessageCode(), e.getPostfix());
                    setSeriousError(true);
                    return;
                }
                break;

        }


    }

    /* Segment functions */

    public void eventBeginSegment( String segmentID ) {

        resetBeginSegment();

        messageTracer.processNextSegment(segmentID);

    }



    public void generateError( int code, String message ) {
        String segmentID = "";
        if ( actualSegment != null ) {
            segmentID = actualSegment.getID();
        }
        String fieldID = "";
        if ( actualField != null ) {
            fieldID = segmentID + "." + actualField.getSequential();
        }
        handler.error( HL7Checker.TYPE_ERROR, code, message, segmentID, fieldID );
    }

    public void generateWarning( int code, String message ) {
        String segmentID = "";
        if ( actualSegment != null ) {
            segmentID = actualSegment.getID();
        }
        String fieldID = "";
        if ( actualField != null ) {
            fieldID = segmentID + "." + actualField.getSequential();
        }
        handler.error( HL7Checker.TYPE_WARNING, code, message, segmentID, fieldID );
    }

    private String getNameOfActualField() {
        if ( actualSegment != null  && actualField != null ) {
            return actualSegment.getID() + "." + actualField.getSequential();
        }  else {
            return "";
        }
    }

    public HL7CheckerHandler getCheckerHandler() {
        return handler;
    }

    public void setCheckerHandler( HL7CheckerHandler handler ) {
        this.handler = handler;
    }

    public void resetBeginComponent() {
        actualSubComponentDataType = null;
        actualDataTypeSubPartsPossition = 0;
    }

    public void resetBeginField() {
        actualFieldRepeatableIndex = 0;
        actualFieldLength = 0;
        actualField = null;
        actualDataType = null;
        actualDataTypePartsPossition = 0;
        resetBeginComponent();
    }

    public void resetBeginRepeatableField() {
        actualFieldRepeatableIndex++;
        actualFieldLength = 0;
        actualDataTypePartsPossition = 0;
        resetBeginComponent();
    }

    public void resetBeginSegment() {
        resetBeginField();
        actualFieldPosition = 0;
        actualSegment = null;
    }

    public HL7Segment getActualSegment() {
        return actualSegment;
    }

    public void setActualSegment( HL7Segment actualSegment ) {
        this.actualSegment = actualSegment;
    }

    public int getActualFieldPosition() {
        return actualFieldPosition;
    }

    public void setActualFieldPosition( int actualFieldPosition ) {
        this.actualFieldPosition = actualFieldPosition;
    }

    public HL7Field getActualField() {
        return actualField;
    }

    public void setActualField( HL7Field actualField ) {
        this.actualField = actualField;
    }

    public int getActualFieldLength() {
        return actualFieldLength;
    }

    public void setActualFieldLength( int actualFieldLength ) {
        this.actualFieldLength = actualFieldLength;
    }


    public int getActualDataTypePartsPossition() {
        return actualDataTypePartsPossition;
    }

    public void setActualDataTypePartsPossition( int actualDataTypePartsPossition ) {
        this.actualDataTypePartsPossition = actualDataTypePartsPossition;
    }

    public int getActualDataTypeSubPartsPossition() {
        return actualDataTypeSubPartsPossition;
    }

    public void setActualDataTypeSubPartsPossition( int actualDataTypeSubPartsPossition ) {
        this.actualDataTypeSubPartsPossition = actualDataTypeSubPartsPossition;
    }

    public int getActualFieldRepeatableIndex() {
        return actualFieldRepeatableIndex;
    }

    public void setActualFieldRepeatableIndex( int actualFieldRepeatableIndex ) {
        this.actualFieldRepeatableIndex = actualFieldRepeatableIndex;
    }

    public HL7DataType getActualDataType() {
        return actualDataType;
    }

    public void setActualDataType( HL7DataType actualDataType ) {
        this.actualDataType = actualDataType;
    }

    public HL7Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration( HL7Configuration configuration ) {
        this.configuration = configuration;
    }

    public HL7DataType getActualComponentDataType() {
        return actualComponentDataType;
    }

    public void setActualComponentDataType( HL7DataType actualComponentDataType ) {
        this.actualComponentDataType = actualComponentDataType;
    }

    public HL7DataType getActualSubComponentDataType() {
        return actualSubComponentDataType;
    }

    public void setActualSubComponentDataType( HL7DataType actualSubComponentDataType ) {
        this.actualSubComponentDataType = actualSubComponentDataType;
    }

    public String toString() {

        String ret = "";

        if ( actualSegment != null ) {
            ret += "ACTUAL_SEGMENT:" + actualSegment.getID()+ "\n";
        }

        if ( actualField != null ) {
            ret += "ACTUAL_FIELD:" + actualSegment.getID() + "." + actualField.getSequential() + "\n";
        }

        ret += "actualFieldPosition:" + actualFieldPosition + "\n";
        ret += "actualFieldRepeatableIndex:" + actualFieldRepeatableIndex + "\n";
        ret += "actualFieldLength:" + actualFieldLength + "\n";

        if ( actualDataType != null ) {
            ret += "actualDataType:" + actualDataType.getID() + "\n";
        }

        ret += "actualDataTypePartsPossition:" + actualDataTypePartsPossition + "\n";

        if ( actualComponentDataType != null ) {
            ret += "actualComponentDataType:" + actualComponentDataType.getID() + "\n";
        }

        ret += "actualDataTypeSubPartsPossition:" + actualDataTypeSubPartsPossition + "\n";

        if ( actualSubComponentDataType != null ) {
            ret += "actualSubComponentDataType:" + actualSubComponentDataType.getID() + "\n";
        }

        return ret;
    }
}
