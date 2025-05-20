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

import com.browsersoft.openhre.hl7.api.config.*;
import com.browsersoft.openhre.hl7.api.parse.HL7PrimitiveDataTypeReader;
import com.browsersoft.openhre.hl7.api.regular.MessageTracer;
import com.browsersoft.openhre.hl7.api.regular.InvalidExpressionException;
import com.browsersoft.openhre.hl7.api.regular.ExpressionElementMapperItem;
import com.browsersoft.openhre.hl7.impl.regular.MessageTracerImpl;
import org.w3c.dom.Node;

public class HL7ConfigurationImpl implements HL7Configuration {

    private HL7MessageMap messages;
    private HL7DataTypeMap dataTypes;
    private HL7SegmentMap segments;
    private HL7TableMap tables;
    private HL7PatternsForCatchValues patterns;

    private HL7MessageConfigReader messagesReader;
    private HL7SegmentsConfigReader segmentsReader;
    private HL7DataTypeConfigReader dataTypeReader;
    private HL7TablesConfigReader tablesReader;

    public HL7ConfigurationImpl() {
        patterns = new HL7PatternsForCatchValuesImpl();

        messagesReader = new  HL7MessageConfigReaderImpl();
        segmentsReader = new  HL7SegmentsConfigReaderImpl();
        dataTypeReader = new  HL7DataTypeConfigReaderImpl();
        tablesReader = new  HL7TablesConfigReaderImpl();

        messages = new HL7MessageMapImpl();
        segments = new HL7SegmentMapImpl();
        dataTypes = new HL7DataTypeMapImpl();
        tables = new HL7TableMapImpl();
    }

    public void readConfigurations( Node messagesNode, Node segmentsNode, Node dataTypesNode, Node tablesNode ) throws InvalidConfigDataStructureException {

        readMessagesConfiguration(messagesNode);
        readSegmentsConfiguration(segmentsNode);
        readDataTypesConfiguration(dataTypesNode);
        readTablesConfiguration(tablesNode);

    }

    public void readMessagesConfiguration( Node messagesNode ) throws InvalidConfigDataStructureException {
        messagesReader.readConfiguration(messagesNode, messages);
    }

    public void readSegmentsConfiguration( Node segmentsNode ) throws InvalidConfigDataStructureException {
        segmentsReader.readConfiguration(segmentsNode, segments);
    }

    public void readDataTypesConfiguration( Node dataTypesNode ) throws InvalidConfigDataStructureException {
        dataTypeReader.readConfiguration(dataTypesNode, dataTypes);
    }

    public void readTablesConfiguration( Node tablesNode ) throws InvalidConfigDataStructureException {
        tablesReader.readConfiguration(tablesNode, tables);
    }

    public void connectConfigurations() throws InvalidConfigDataStructureException {

        patterns.clearAll();

        String[] messagesIds = messages.getMessageIds();

        for ( int i = 0 ; i < messagesIds.length ; i++ ) {

            HL7Message message = messages.getItem(messagesIds[i]);

            MessageTracer tracer = new MessageTracerImpl();
            try {
                tracer.buildMatrixForMessage(message);
            } catch ( InvalidExpressionException e ) {
                throw new InvalidConfigDataStructureException( message.getID(), 31);
            }

            for ( int j = 0; j < tracer.getMapper().size(); j++ ) {
                ExpressionElementMapperItem mapperItem = tracer.getMapper().getItem(j);
                if ( mapperItem.getType() == ExpressionElementMapperItem.SEGMENT_ITEM ) {
                    String segmentID = mapperItem.getID();
                    HL7Segment segment = segments.getItem(segmentID);
                    if ( segment == null ) {
                       throw new InvalidConfigDataStructureException( segmentID, 8);
                    }
                    mapperItem.setSegment(segment);
                    mapperItem.setID(segmentID.substring(0,3));
                }
            }


            message.setTracer(tracer);



        }

        String[] segmentsIds = segments.getSegmentsIds();

        for ( int i = 0 ; i < segmentsIds.length ; i++ ) {
            HL7Segment segment = segments.getItem(segmentsIds[i]);
            connectFields(segment.getFields());
        }

        String[] dataTypesIds = dataTypes.getTypeIds();

        for ( int i = 0 ; i < dataTypesIds.length ; i++ ) {
            HL7DataType dataType = dataTypes.getItem(dataTypesIds[i]);

            HL7DataTypePartList parts = dataType.getParts();

            for ( int j = 0 ; j < parts.size(); j++ ) {
                connectDataTypePart(parts.getItem(j));
            }
        }


    }

    private void connectDataTypePart( HL7DataTypePart part ) throws InvalidConfigDataStructureException {

        if ( part.getType() == HL7DataTypePart.PART_TYPE_PRIMITIVE ) {

            HL7DataTypePartPrimitive primitivePart = (HL7DataTypePartPrimitive) part;
            String readerClassName = primitivePart.getReader();
            Object readerObject;
            try {
                readerObject = getInstanceFromClassName(readerClassName);
            } catch ( Exception e ) {
                throw new InvalidConfigDataStructureException( readerClassName, 20 );
            }
            if ( !(readerObject instanceof HL7PrimitiveDataTypeReader) ) {
                throw new InvalidConfigDataStructureException( readerClassName, 21 );
            }
            primitivePart.setInstanceReader((HL7PrimitiveDataTypeReader) readerObject);

        } else {

            HL7DataTypePartSubPart subPart = (HL7DataTypePartSubPart) part;
            String subPartId = subPart.getSubPartID();

            HL7DataType type = dataTypes.getItem(subPartId);

            if ( type == null ) {
                throw new InvalidConfigDataStructureException(subPartId, 9);
            }

            subPart.setSubType(type);

        }

    }

    private Object getInstanceFromClassName ( String className ) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class classForObject = Class.forName(className);
        return classForObject.newInstance();
    }

    private void connectFields ( HL7FieldList fields ) throws InvalidConfigDataStructureException {

        for ( int i = 0 ; i < fields.size() ; i++ ) {

            HL7Field field = fields.getItem(i);

            if ( field.getDependingType() == HL7Field.TYPE_DEPENDING_GIVEN ) {
                HL7FieldGivenDependingProcessorImpl given = (HL7FieldGivenDependingProcessorImpl) field.getDependingProcessor();
                String from = given.getFrom();
                if ( !from.equals("")) {
                    patterns.addPattern(from);
                }
            }

            HL7Table objectTable = tables.getItem(field.getTable());
            if ( objectTable == null ) {
                // tables configuration isn't complete..
            }
            field.setTableObject(objectTable);


        }

    }

    private void connectGroup ( HL7MessageGroup group ) throws InvalidConfigDataStructureException {

        for ( int i = 0 ; i < group.size() ; i++ ) {

            HL7MessageGroupItem item = group.getItem(i);

            if ( item.getType() == HL7MessageGroupItem.ITEM_TYPE_SEGMENT ) {
                connectSegment ( (HL7MessageSegment) item );
            } else if ( item.getType() == HL7MessageGroupItem.ITEM_TYPE_GROUP ) {
                connectGroup((HL7MessageGroup) item);
            }

        }

     }

    private void connectSegment ( HL7MessageSegment segment ) throws InvalidConfigDataStructureException {

        HL7Segment objectSegment = segments.getItem(segment.getID());
        if ( objectSegment == null ) {
            throw new InvalidConfigDataStructureException(segment.getID(), 8);
        }
        segment.setSegment(objectSegment);

    }

    public  HL7PatternsForCatchValues  getPatterns() {
        return patterns;
    }

    public void setPatterns( HL7PatternsForCatchValues patterns ) {
        this.patterns = patterns;
    }

    public HL7MessageMap getMessages() {
        return messages;
    }

    public void setMessages( HL7MessageMap messages ) {
        this.messages = messages;
    }

    public HL7DataTypeMap getDataTypes() {
        return dataTypes;
    }

    public void setDataTypes( HL7DataTypeMap dataTypes ) {
        this.dataTypes = dataTypes;
    }

    public HL7SegmentMap getSegments() {
        return segments;
    }

    public void setSegments( HL7SegmentMap segments ) {
        this.segments = segments;
    }

    public HL7TableMap getTables() {
        return tables;
    }

    public void setTables( HL7TableMap tables ) {
        this.tables = tables;
    }

    public String toString() {
        String ret = messages.toString();
        ret += segments.toString();
        ret += dataTypes.toString();
        ret += tables.toString();
        return ret;
    }

}
