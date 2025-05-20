
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

import com.browsersoft.openhre.hl7.api.config.HL7Message;
import com.browsersoft.openhre.hl7.api.config.HL7MessageConfigReader;
import com.browsersoft.openhre.hl7.api.config.HL7MessageMap;
import com.browsersoft.openhre.hl7.api.config.InvalidConfigDataStructureException;
import com.browsersoft.openhre.hl7.api.config.HL7MessageGroup;
import com.browsersoft.openhre.hl7.api.config.HL7MessageGroupItem;
import com.browsersoft.openhre.hl7.api.config.HL7MessageSegment;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class HL7MessageConfigReaderImpl implements HL7MessageConfigReader {

	public static final String TAG_MESSAGES =                             "messages";
	public static final String TAG_MESSAGE =                              "message";
	public static final String PARAMETER_ID =                             "id";
    public static final String PARAMETER_ADDITIONAL =                     "additional";
    public static final String TAG_DESCRIPTION =                          "description";
	public static final String TAG_GROUP =                                "group";
	public static final String PARAMETER_REQUIRED =                       "required";
	public static final String PARAMETER_REPEATABLE =                     "repeatable";
	public static final String TAG_SEGMENT =                              "segment";
	public static final String PARAMETER_YES =                            "Y";
	public static final String PARAMETER_NO =                             "N";


	public void readConfiguration( Node node , HL7MessageMap map ) throws InvalidConfigDataStructureException {

		readMessages(node, map);

	}


	private void readMessages( Node node, HL7MessageMap newMap ) throws InvalidConfigDataStructureException {

		if ( !node.getNodeName().equals(TAG_MESSAGES) ) {
			throw new InvalidConfigDataStructureException(node.getNodeName(), 1);
		}

		NodeList childs = node.getChildNodes();

		for ( int i = 0; i < childs.getLength(); i++ ) {

			Node child = childs.item(i);

			if ( child.getNodeType() == Node.ELEMENT_NODE ) {
				if ( child.getNodeName().equals(TAG_MESSAGE) ) {
					readMessage(child, newMap);
				}
			}
		}

	}

	private void readMessage( Node node, HL7MessageMap newMap ) throws InvalidConfigDataStructureException {

		if ( !node.getNodeName().equals(TAG_MESSAGE) ) {
			throw new InvalidConfigDataStructureException(node.getNodeName(), 1);
		}

		HL7Message newMessage = new HL7MessageImpl();

		String valueOfIDParam = DOMUtils.getParameter(node, PARAMETER_ID);

		if ( valueOfIDParam.equals("") ) {
			throw new InvalidConfigDataStructureException(node.getNodeName(), 2);
		}

		newMessage.setID(valueOfIDParam);

		NodeList childs = node.getChildNodes();

		boolean groupFinded = false;

		for ( int i = 0; i < childs.getLength(); i++ ) {

			Node child = childs.item(i);

			if ( child.getNodeType() == Node.ELEMENT_NODE ) {

				// tag description is optional
				if ( child.getNodeName().equals(TAG_DESCRIPTION) ) {
					readDescription(child, newMessage);
				}

				if ( child.getNodeName().equals(TAG_GROUP) ) {
					groupFinded = true;
					newMessage.setGroup(readGroup(child));
				}
			}
		}

			if ( !groupFinded ) {
				throw new InvalidConfigDataStructureException(TAG_GROUP, 3);
			}


		newMap.addItem(newMessage);

	}




	private void readDescription( Node node, HL7Message newType ) {

		String valueOfDescription = DOMUtils.getTextContent(node);
		newType.setDescription(valueOfDescription);

	}

	private HL7MessageGroup readGroup( Node node ) throws InvalidConfigDataStructureException {

		 HL7MessageGroup newGroup = new HL7MessageGroupImpl();

		 NodeList childs = node.getChildNodes();

		 for ( int i = 0; i < childs.getLength(); i++ ) {

			Node child = childs.item(i);

			if ( child.getNodeType() == Node.ELEMENT_NODE ) {
				if ( child.getNodeName().equals(TAG_SEGMENT) || child.getNodeName().equals(TAG_GROUP) ) {
					newGroup.addItem(readGroupItem(child));
				}
			}
		 }

		 return newGroup;

	}


	private HL7MessageGroupItem readGroupItem ( Node node ) throws InvalidConfigDataStructureException {

		if ( node.getNodeName().equals(TAG_SEGMENT) ) {

			HL7MessageSegment segment = new HL7MessageSegmentImpl();

			String valueOfIDParam = DOMUtils.getParameter(node, PARAMETER_ID);
			if ( valueOfIDParam.equals("") ) {
				throw new InvalidConfigDataStructureException(node.getNodeName(), 2);
			}
			segment.setID(valueOfIDParam);

			fillGroupItem( node, segment );

			return segment;

		} else if ( node.getNodeName().equals(TAG_GROUP) ) {

			HL7MessageGroup group = readGroup( node );
            String valueOfAdditionalParam = DOMUtils.getParameter(node, PARAMETER_ADDITIONAL);
			group.setAdditional(valueOfAdditionalParam);

            fillGroupItem(node, group);

			return group;
		} else {
			throw new InvalidConfigDataStructureException(node.getNodeName(), 1);
		}

	}


	private void fillGroupItem ( Node node, HL7MessageGroupItem item ) throws InvalidConfigDataStructureException {

		String valueOfRequiredParam = DOMUtils.getParameter(node, PARAMETER_REQUIRED);

		if ( valueOfRequiredParam.equals(PARAMETER_YES) || valueOfRequiredParam.equals("") ) {
			item.setRequired(true);
		} else if ( valueOfRequiredParam.equals(PARAMETER_NO) ) {
			item.setRequired(false);
		} else {
			throw new InvalidConfigDataStructureException(valueOfRequiredParam, 5);
		}

		String valueOfRepeatableParam = DOMUtils.getParameter(node, PARAMETER_REPEATABLE);

		if ( valueOfRepeatableParam.equals(PARAMETER_NO) || valueOfRepeatableParam.equals("") ) {
			item.setRepeatable(false);
		} else if ( valueOfRepeatableParam.equals(PARAMETER_YES) ) {
			item.setRepeatable(true);
		} else {
			throw new InvalidConfigDataStructureException(valueOfRepeatableParam, 5);
		}


	}




}
