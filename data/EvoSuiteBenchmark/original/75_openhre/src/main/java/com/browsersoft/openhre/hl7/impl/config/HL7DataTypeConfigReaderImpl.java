
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

import com.browsersoft.openhre.hl7.api.config.HL7DataTypeConfigReader;
import com.browsersoft.openhre.hl7.api.config.HL7DataTypeMap;
import com.browsersoft.openhre.hl7.api.config.InvalidConfigDataStructureException;
import com.browsersoft.openhre.hl7.api.config.HL7DataType;
import com.browsersoft.openhre.hl7.api.config.HL7DataTypePartList;
import com.browsersoft.openhre.hl7.api.config.HL7DataTypePart;
import com.browsersoft.openhre.hl7.api.config.HL7DataTypePartPrimitive;
import com.browsersoft.openhre.hl7.api.config.HL7DataTypePartSubPart;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class HL7DataTypeConfigReaderImpl implements HL7DataTypeConfigReader {

	public static final String TAG_TYPES =                                           "types";
	public static final String TAG_TYPE =                                            "type";
	public static final String PARAMETER_TAG_TYPE_ID =                               "id";
	public static final String PARAMETER_TAG_TYPE_CONTENT_TYPE =                     "contentType";
	public static final String PARAMETER_TAG_TYPE_CONTENT_TYPE_COMBINATION_VALUE =   "combination";
	public static final String PARAMETER_TAG_TYPE_CONTENT_TYPE_PART_LIST_VALUE =     "partlist";
	public static final String TAG_DESCRIPTION =                                     "description";
	public static final String TAG_PARTS =                                           "parts";
	public static final String TAG_PART =                                            "part";
	public static final String PARAMETER_TAG_PART_READER =                           "reader";
	public static final String PARAMETER_TAG_PART_DESCRIPTION =                      "description";
	public static final String PARAMETER_TAG_PART_SUBTYPE =                          "subtype";
	public static final String PARAMETER_TAG_PART_TABLE =                            "table";

	public void readConfiguration( Node node, HL7DataTypeMap map ) throws InvalidConfigDataStructureException {

		readTypes(node, map);

	}

	private void readTypes( Node node, HL7DataTypeMap newMap ) throws InvalidConfigDataStructureException {

		if ( !node.getNodeName().equals(TAG_TYPES) ) {
			throw new InvalidConfigDataStructureException(node.getNodeName(), 1);
		}

		NodeList childs = node.getChildNodes();

		for ( int i = 0; i < childs.getLength(); i++ ) {

			Node child = childs.item(i);

			if ( child.getNodeType() == Node.ELEMENT_NODE ) {
				if ( child.getNodeName().equals(TAG_TYPE) ) {
					readType(child, newMap);
				}
			}
		}

	}

	private void readType( Node node, HL7DataTypeMap newMap ) throws InvalidConfigDataStructureException {

		if ( !node.getNodeName().equals(TAG_TYPE) ) {
			throw new InvalidConfigDataStructureException(node.getNodeName(), 1);
		}

		HL7DataType newType = new HL7DataTypeImpl();

		String valueOfIDParam = DOMUtils.getParameter(node, PARAMETER_TAG_TYPE_ID);

		if ( valueOfIDParam.equals("") ) {
			throw new InvalidConfigDataStructureException(node.getNodeName(), 2);
		}

		String valueOfContentTypeParam = DOMUtils.getParameter(node, PARAMETER_TAG_TYPE_CONTENT_TYPE);

		if ( valueOfContentTypeParam.equals(PARAMETER_TAG_TYPE_CONTENT_TYPE_PART_LIST_VALUE) || valueOfContentTypeParam.equals("") )
		{
			newType.setContentType(HL7DataType.CONTENT_TYPE_PARTLIST);
		} else if ( valueOfContentTypeParam.equals(PARAMETER_TAG_TYPE_CONTENT_TYPE_COMBINATION_VALUE) ) {
			newType.setContentType(HL7DataType.CONTENT_TYPE_COMBINATION);
		} else {
			throw new InvalidConfigDataStructureException(valueOfContentTypeParam, 5);
		}

		newType.setID(valueOfIDParam);

		NodeList childs = node.getChildNodes();

		boolean partsFinded = false;

		for ( int i = 0; i < childs.getLength(); i++ ) {

			Node child = childs.item(i);

			if ( child.getNodeType() == Node.ELEMENT_NODE ) {

				// tag description is optional
				if ( child.getNodeName().equals(TAG_DESCRIPTION) ) {
					readDescription(child, newType);
				}

				if ( child.getNodeName().equals(TAG_PARTS) ) {
					partsFinded = true;
					readParts(child, newType);
				}
			}
		}

		if ( newType.getContentType() == HL7DataType.CONTENT_TYPE_PARTLIST ) {
			if ( !partsFinded ) {
				throw new InvalidConfigDataStructureException(TAG_PARTS, 3);
			}
		}

		newMap.addItem(newType);

	}

	private void readDescription( Node node, HL7DataType newType ) {

		String valueOfDescription = DOMUtils.getTextContent(node);
		newType.setDescription(valueOfDescription);

	}

	private void readParts( Node node, HL7DataType newType ) throws InvalidConfigDataStructureException {

		NodeList childs = node.getChildNodes();

		boolean onePartFinded = false;

		for ( int i = 0; i < childs.getLength(); i++ ) {

			Node child = childs.item(i);

			if ( child.getNodeType() == Node.ELEMENT_NODE ) {
				// tag description is optional
				if ( child.getNodeName().equals(TAG_PART) ) {
					onePartFinded = true;
					readPart(child, newType.getParts());
				}
			}
		}

		if ( newType.getContentType() == HL7DataType.CONTENT_TYPE_PARTLIST ) {
			if ( !onePartFinded ) {
				throw new InvalidConfigDataStructureException(TAG_PARTS, 3);
			}
		}


	}

	private void readPart( Node node, HL7DataTypePartList partList ) throws InvalidConfigDataStructureException {

        HL7DataTypePart newPart;

        String valueOfReaderParam = DOMUtils.getParameter(node, PARAMETER_TAG_PART_READER);
        if ( !valueOfReaderParam.equals("") ) {
            newPart = newPrimitivePart(node);
        } else {
            newPart = newSubPart(node);
        }

		String valueOfDescriptionParam = DOMUtils.getParameter(node, PARAMETER_TAG_PART_DESCRIPTION);
		newPart.setDescription(valueOfDescriptionParam);

		partList.addItem(newPart);

    }


    private HL7DataTypePartPrimitive newPrimitivePart ( Node node ) throws InvalidConfigDataStructureException {

        HL7DataTypePartPrimitive newPart = new HL7DataTypePartPrimitiveImpl();

		String valueOfReaderParam = DOMUtils.getParameter(node, PARAMETER_TAG_PART_READER);
		newPart.setReader(valueOfReaderParam);

        String valueOfTableParam = DOMUtils.getParameter(node, PARAMETER_TAG_PART_TABLE);
		newPart.setIDTable(valueOfTableParam);

        return newPart;

    }

    private HL7DataTypePartSubPart newSubPart ( Node node ) throws InvalidConfigDataStructureException {

        HL7DataTypePartSubPart newPart = new HL7DataTypePartSubPartImpl();

        String valueOfSubTypeParam = DOMUtils.getParameter(node, PARAMETER_TAG_PART_SUBTYPE);
		newPart.setSubPartID(valueOfSubTypeParam);

        if ( newPart.getSubPartID().equals("") ) {
			throw new InvalidConfigDataStructureException(TAG_PART, 4);
		}

        return newPart;
    }
}
