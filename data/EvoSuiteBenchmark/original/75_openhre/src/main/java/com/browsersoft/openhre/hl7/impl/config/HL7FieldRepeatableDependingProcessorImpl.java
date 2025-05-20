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

import com.browsersoft.openhre.hl7.api.config.HL7FieldDependingProcessor;
import com.browsersoft.openhre.hl7.api.config.InvalidConfigDataStructureException;
import com.browsersoft.openhre.hl7.api.config.HL7DataType;
import com.browsersoft.openhre.hl7.api.parse.HL7CheckerState;
import com.browsersoft.openhre.hl7.api.parse.ParserException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

public class HL7FieldRepeatableDependingProcessorImpl implements HL7FieldDependingProcessor {

    private static final String TAG_POSITION =            "position";
    private static final String PARAMETER_TAG_DATA_TYPE = "dataType";

    private ArrayList dataTypes;

    public HL7FieldRepeatableDependingProcessorImpl() {
        dataTypes = new ArrayList();
    }

    public void readConfiguration( Node node ) throws InvalidConfigDataStructureException {

        NodeList childs = node.getChildNodes();

        for ( int i = 0; i < childs.getLength(); i++ ) {

            Node child = childs.item(i);

            if ( child.getNodeType() == Node.ELEMENT_NODE ) {

                if ( child.getNodeName().equals(TAG_POSITION) ) {

                    dataTypes.add(DOMUtils.getParameter( child, PARAMETER_TAG_DATA_TYPE ));

                }
            }
        }

    }

    public HL7DataType getDataTypeForSituation( HL7CheckerState state ) throws ParserException {

        int repeatableIndex = state.getActualFieldRepeatableIndex();

        if ( repeatableIndex >= dataTypes.size()) {
            throw new ParserException( " defined:" + dataTypes.size() + " repeat counter:" + repeatableIndex, 29 );
        }

        String value = (String) dataTypes.get(repeatableIndex);

        HL7DataType dataType = state.getConfiguration().getDataTypes().getItem(value);

        if ( dataType == null ) {
            throw new ParserException( value, 9 );
        }

        return dataType;

    }


}
