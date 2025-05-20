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

import com.browsersoft.openhre.hl7.api.config.HL7Table;
import com.browsersoft.openhre.hl7.api.config.HL7TableItem;
import com.browsersoft.openhre.hl7.api.config.HL7TableItemMap;
import com.browsersoft.openhre.hl7.api.config.HL7TableMap;
import com.browsersoft.openhre.hl7.api.config.HL7TablesConfigReader;
import com.browsersoft.openhre.hl7.api.config.InvalidConfigDataStructureException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class HL7TablesConfigReaderImpl implements HL7TablesConfigReader {

    public static final String TAG_TABLES =                                      "tables";
    public static final String TAG_TABLE =                                       "table";
    public static final String PARAMETER_TAG_TABLE_ID =                          "id";
    public static final String TAG_DESCRIPTION =                                 "description";
    public static final String TAG_ITEMS =                                       "items";
    public static final String TAG_ITEM =                                        "item";
    public static final String PARAMETER_TAG_ITEM_VALUE =                        "value";
    public static final String PARAMETER_TAG_ITEM_DESCRIPTION =                  "description";

    public void readConfiguration( Node node, HL7TableMap tables ) throws InvalidConfigDataStructureException {

        readTables(node, tables);


    }

    private void readTables( Node node, HL7TableMap newMap ) throws InvalidConfigDataStructureException {

        if ( !node.getNodeName().equals(TAG_TABLES) ) {
            throw new InvalidConfigDataStructureException(node.getNodeName(), 1);
        }

        NodeList childs = node.getChildNodes();

        for ( int i = 0; i < childs.getLength(); i++ ) {

            Node child = childs.item(i);

            if ( child.getNodeType() == Node.ELEMENT_NODE ) {
                if ( child.getNodeName().equals(TAG_TABLE) ) {
                    readTable(child, newMap);
                }
            }
        }

    }

    private void readTable( Node node, HL7TableMap newMap ) throws InvalidConfigDataStructureException {

		if ( !node.getNodeName().equals(TAG_TABLE) ) {
			throw new InvalidConfigDataStructureException(node.getNodeName(), 1);
		}

		HL7Table newTable = new HL7TableImpl();

		String valueOfIDParam = DOMUtils.getParameter(node, PARAMETER_TAG_TABLE_ID);

		if ( valueOfIDParam.equals("") ) {
			throw new InvalidConfigDataStructureException(node.getNodeName(), 2);
		}

		newTable.setID(valueOfIDParam);

		NodeList childs = node.getChildNodes();

		boolean itemsFinded = false;

		for ( int i = 0; i < childs.getLength(); i++ ) {

			Node child = childs.item(i);

			if ( child.getNodeType() == Node.ELEMENT_NODE ) {

				// tag description is optional
				if ( child.getNodeName().equals(TAG_DESCRIPTION) ) {
					readDescription(child, newTable);
				}

				if ( child.getNodeName().equals(TAG_ITEMS) ) {
					itemsFinded = true;
					readItems(child, newTable);
				}
			}
		}

		if ( !itemsFinded ) {
			throw new InvalidConfigDataStructureException(TAG_ITEMS, 3);
		}


		newMap.addItem(newTable);

	}

    private void readDescription( Node node, HL7Table newTable ) {

		String valueOfDescription = DOMUtils.getTextContent(node);
		newTable.setDescription(valueOfDescription);

	}

    private void readItems( Node node, HL7Table newTable ) throws InvalidConfigDataStructureException {

            NodeList childs = node.getChildNodes();

            boolean oneItemFinded = false;

            for ( int i = 0; i < childs.getLength(); i++ ) {

                Node child = childs.item(i);

                if ( child.getNodeType() == Node.ELEMENT_NODE ) {
                    // tag description is optional
                    if ( child.getNodeName().equals(TAG_ITEM) ) {
                        oneItemFinded = true;
                        readItem(child, newTable.getItems());
                    }
                }
            }

            if ( !oneItemFinded ) {
                throw new InvalidConfigDataStructureException(TAG_ITEMS, 3);
            }

    }

    private void readItem( Node node, HL7TableItemMap itemMap ) throws InvalidConfigDataStructureException {

            HL7TableItem newItem = new HL7TableItemImpl();

            String valueOfValueParam = DOMUtils.getParameter(node, PARAMETER_TAG_ITEM_VALUE);

            if ( valueOfValueParam.equals("")) {
			    throw new InvalidConfigDataStructureException(TAG_ITEM, 2);
            }

            newItem.setValue(valueOfValueParam);

            String valueOfDescriptionParam = DOMUtils.getParameter(node, PARAMETER_TAG_ITEM_DESCRIPTION);
            newItem.setDescription(valueOfDescriptionParam);

            itemMap.addItem(newItem);
    }

}
