
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

import com.browsersoft.openhre.hl7.api.config.HL7Field;
import com.browsersoft.openhre.hl7.api.config.HL7FieldList;
import com.browsersoft.openhre.hl7.api.config.HL7Segment;
import com.browsersoft.openhre.hl7.api.config.HL7SegmentMap;
import com.browsersoft.openhre.hl7.api.config.HL7SegmentsConfigReader;
import com.browsersoft.openhre.hl7.api.config.InvalidConfigDataStructureException;
import com.browsersoft.openhre.hl7.api.config.HL7FieldDependingProcessor;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class HL7SegmentsConfigReaderImpl implements HL7SegmentsConfigReader {

	public static final String TAG_SEGMENTS =                                    "segments";
	public static final String TAG_SEGMENT =                                     "segment";
	public static final String PARAMETER_TAG_SEGMENT_ID =                        "id";
	public static final String TAG_DESCRIPTION =                                 "description";
	public static final String TAG_FIELDS =                                      "fields";
	public static final String TAG_FIELD =                                       "field";
	public static final String PARAMETER_TAG_FIELD_SEQUENTIAL =                  "sequential";
	public static final String PARAMETER_TAG_FIELD_MAXIMUM_LENGTH =              "maximumLength";
	public static final String PARAMETER_TAG_FIELD_DATA_TYPE =                   "dataType";
	public static final String PARAMETER_TAG_FIELD_REQUIRED =                    "required";
	public static final String PARAMETER_TAG_FIELD_REPEATABLE =                  "repeatable";
	public static final String PARAMETER_TAG_FIELD_REPEATABLE_VALUE_YES =        "Y";
	public static final String PARAMETER_TAG_FIELD_REPEATABLE_VALUE_NO =         "N";
	public static final String PARAMETER_TAG_FIELD_REFERENCE =                   "reference";
	public static final String PARAMETER_TAG_FIELD_TABLE =                       "table";
	public static final String PARAMETER_TAG_FIELD_DATA_TYPE_DEPENDS =           "dependingType";
    public static final String PARAMETER_TAG_FIELD_DATA_TYPE_DEPENDS_REPEATABLE ="repeatable";
    public static final String PARAMETER_TAG_FIELD_DATA_TYPE_DEPENDS_GIVEN =     "given";
    public static final String TAG_DEPENDS =                                     "depends";
    public static final String PARAMETER_TAG_FIELD_DATA_TYPE_BINDING_VALUE_YES = "Y";
	public static final String PARAMETER_TAG_FIELD_DATA_TYPE_BINDING_VALUE_NO =  "N";
    public static final String PARAMETER_TAG_FIELD_REQUIRED_R_VALUE =            "R";
    public static final String PARAMETER_TAG_FIELD_REQUIRED_O_VALUE =            "O";
    public static final String PARAMETER_TAG_FIELD_REQUIRED_C_VALUE =            "C";
    public static final String PARAMETER_TAG_FIELD_REQUIRED_X_VALUE =            "X";
    public static final String PARAMETER_TAG_FIELD_REQUIRED_B_VALUE =            "B";

    public void readConfiguration( Node node, HL7SegmentMap map ) throws InvalidConfigDataStructureException {
		readSegments(node, map);
	}


	private void readSegments( Node node, HL7SegmentMap newMap ) throws InvalidConfigDataStructureException {

		if ( !node.getNodeName().equals(TAG_SEGMENTS) ) {
			throw new InvalidConfigDataStructureException(node.getNodeName(), 1);
		}

		NodeList childs = node.getChildNodes();

		for ( int i = 0; i < childs.getLength(); i++ ) {

			Node child = childs.item(i);

			if ( child.getNodeType() == Node.ELEMENT_NODE ) {
				if ( child.getNodeName().equals(TAG_SEGMENT) ) {
					readSegment(child, newMap);
				}
			}
		}

	}

	private void readSegment( Node node, HL7SegmentMap newMap ) throws InvalidConfigDataStructureException {

		if ( !node.getNodeName().equals(TAG_SEGMENT) ) {
			throw new InvalidConfigDataStructureException(node.getNodeName(), 1);
		}

		HL7Segment newSegment = new HL7SegmentImpl();

		String valueOfIDParam = DOMUtils.getParameter(node, PARAMETER_TAG_SEGMENT_ID);

		if ( valueOfIDParam.equals("") ) {
			throw new InvalidConfigDataStructureException(node.getNodeName(), 2);
		}

		newSegment.setID(valueOfIDParam);

		NodeList childs = node.getChildNodes();

		boolean fieldsFinded = false;

		for ( int i = 0; i < childs.getLength(); i++ ) {

			Node child = childs.item(i);

			if ( child.getNodeType() == Node.ELEMENT_NODE ) {

				// tag description is optional
				if ( child.getNodeName().equals(TAG_DESCRIPTION) ) {
					readDescription(child, newSegment);
				}

				if ( child.getNodeName().equals(TAG_FIELDS) ) {
					fieldsFinded = true;
					readFields(child, newSegment);
				}
			}
		}

		if ( !fieldsFinded ) {
			throw new InvalidConfigDataStructureException(TAG_FIELDS, 3);
		}


		newMap.addItem(newSegment);

	}

	private void readDescription( Node node, HL7Segment newType ) {

		String valueOfDescription = DOMUtils.getTextContent(node);
		newType.setDescription(valueOfDescription);

	}

	private void readFields( Node node, HL7Segment newSegment ) throws InvalidConfigDataStructureException {

		NodeList childs = node.getChildNodes();

		boolean oneFieldFinded = false;

		for ( int i = 0; i < childs.getLength(); i++ ) {

			Node child = childs.item(i);

			if ( child.getNodeType() == Node.ELEMENT_NODE ) {
				// tag description is optional
				if ( child.getNodeName().equals(TAG_FIELD) ) {
					oneFieldFinded = true;
					readField(child, newSegment.getFields());
				}
			}
		}

		if ( !oneFieldFinded ) {
			throw new InvalidConfigDataStructureException(TAG_FIELDS, 3);
		}


	}

	private void readField( Node node, HL7FieldList fieldList ) throws InvalidConfigDataStructureException {

		HL7Field newField = new HL7FieldImpl();

		String valueOfSequentialParam = DOMUtils.getParameter(node, PARAMETER_TAG_FIELD_SEQUENTIAL);
		newField.setSequential(valueOfSequentialParam);

		String valueOfLengthParam = DOMUtils.getParameter(node, PARAMETER_TAG_FIELD_MAXIMUM_LENGTH);
		int number = readNumber(valueOfLengthParam);
		newField.setMaximumLength(number);

		String valueOfDataTypeParam = DOMUtils.getParameter(node, PARAMETER_TAG_FIELD_DATA_TYPE);
		newField.setDataTypeID(valueOfDataTypeParam);

		String valueOfRequiredParam = DOMUtils.getParameter(node, PARAMETER_TAG_FIELD_REQUIRED);
        if ( valueOfRequiredParam.equals(PARAMETER_TAG_FIELD_REQUIRED_R_VALUE)) {
            newField.setRequired(HL7Field.REQUIRED_R);
        } else if ( valueOfRequiredParam.equals(PARAMETER_TAG_FIELD_REQUIRED_O_VALUE)) {
            newField.setRequired(HL7Field.REQUIRED_O);
        } else if ( valueOfRequiredParam.equals(PARAMETER_TAG_FIELD_REQUIRED_C_VALUE)) {
            newField.setRequired(HL7Field.REQUIRED_C);
        } else if ( valueOfRequiredParam.equals(PARAMETER_TAG_FIELD_REQUIRED_X_VALUE)) {
            newField.setRequired(HL7Field.REQUIRED_X);
        } else if ( valueOfRequiredParam.equals(PARAMETER_TAG_FIELD_REQUIRED_B_VALUE)) {
            newField.setRequired(HL7Field.REQUIRED_B);
        } else {
            throw new InvalidConfigDataStructureException(valueOfRequiredParam, 5);
        }

        String valueOfReferenceParam = DOMUtils.getParameter(node, PARAMETER_TAG_FIELD_REFERENCE);
		newField.setReference(valueOfReferenceParam);

		String valueOfTableParam = DOMUtils.getParameter(node, PARAMETER_TAG_FIELD_TABLE);
		newField.setReference(valueOfTableParam);

		String valueOfDataTypeDependingParam = DOMUtils.getParameter(node, PARAMETER_TAG_FIELD_DATA_TYPE_DEPENDS );
		if ( valueOfDataTypeDependingParam.equals("") ) {
			newField.setDependingType( HL7Field.TYPE_DEPENDING_NORMAL );
		} else if ( valueOfDataTypeDependingParam.equals(PARAMETER_TAG_FIELD_DATA_TYPE_DEPENDS_REPEATABLE) ) {
			newField.setDependingType( HL7Field.TYPE_DEPENDING_REPEATABLE );
            HL7FieldDependingProcessor processor = processDepends( node, HL7Field.TYPE_DEPENDING_REPEATABLE );
            newField.setDependingProcessor(processor);
        } else if ( valueOfDataTypeDependingParam.equals(PARAMETER_TAG_FIELD_DATA_TYPE_DEPENDS_GIVEN) ) {
			HL7FieldDependingProcessor processor = processDepends( node, HL7Field.TYPE_DEPENDING_GIVEN );
            newField.setDependingType( HL7Field.TYPE_DEPENDING_GIVEN );
            newField.setDependingProcessor(processor);
        } else {
			throw new InvalidConfigDataStructureException(valueOfDataTypeDependingParam, 5);
		}

		String valueOfRepeatableParam = DOMUtils.getParameter(node, PARAMETER_TAG_FIELD_REPEATABLE);
		if ( valueOfRepeatableParam.equals(PARAMETER_TAG_FIELD_REPEATABLE_VALUE_NO) || valueOfRepeatableParam.equals("") )
		{
			newField.setRepeatable(false);
		} else if ( valueOfRepeatableParam.equals(PARAMETER_TAG_FIELD_REPEATABLE_VALUE_YES) ) {
			newField.setRepeatable(true);
		} else {
			throw new InvalidConfigDataStructureException(valueOfRepeatableParam, 5);
		}

		NodeList childs = node.getChildNodes();

		for ( int i = 0; i < childs.getLength(); i++ ) {

			Node child = childs.item(i);

			if ( child.getNodeType() == Node.ELEMENT_NODE ) {
				// tag description is optional
				if ( child.getNodeName().equals(TAG_DESCRIPTION) ) {
					readDescription(child, newField);
				}
			}
		}

		fieldList.addItem(newField);
	}

    private HL7FieldDependingProcessor processDepends( Node node, int type ) throws InvalidConfigDataStructureException {

        NodeList childs = node.getChildNodes();

		for ( int i = 0; i < childs.getLength(); i++ ) {

			Node child = childs.item(i);

			if ( child.getNodeType() == Node.ELEMENT_NODE ) {
				// tag description is optional
				if ( child.getNodeName().equals(TAG_DEPENDS) ) {

                    if ( type == HL7Field.TYPE_DEPENDING_REPEATABLE ) {
                        HL7FieldDependingProcessor processor = new HL7FieldRepeatableDependingProcessorImpl();
                        processor.readConfiguration(child);
                        return processor;
                    } else if  ( type == HL7Field.TYPE_DEPENDING_GIVEN ) {
                        HL7FieldDependingProcessor processor = new HL7FieldGivenDependingProcessorImpl();
                        processor.readConfiguration(child);
                        return processor;                            
                    }
				}
			}
		}
       return null;
    }


    private void readDescription( Node node, HL7Field newField ) {

		String valueOfDescription = DOMUtils.getTextContent(node);
		newField.setDescription(valueOfDescription);

	}

	private int readNumber( String intValue ) throws InvalidConfigDataStructureException {

		try {
			int number = Integer.parseInt(intValue);
			if ( number <= 0 || number > 65536 ) {
				throw new InvalidConfigDataStructureException(intValue, 7);
			}
			return number;
		} catch ( NumberFormatException e ) {
			throw new InvalidConfigDataStructureException(intValue, 6);
		}

	}
}
