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

import com.browsersoft.openhre.hl7.api.config.HL7DataType;
import com.browsersoft.openhre.hl7.api.config.HL7FieldDependingProcessor;
import com.browsersoft.openhre.hl7.api.config.InvalidConfigDataStructureException;
import com.browsersoft.openhre.hl7.api.parse.HL7CheckerState;
import com.browsersoft.openhre.hl7.api.parse.ParserException;
import org.w3c.dom.Node;

public class HL7FieldGivenDependingProcessorImpl implements HL7FieldDependingProcessor {

   private static final String FROM_PARAMETER = "from";

    private String from;



    public void readConfiguration( Node node ) throws InvalidConfigDataStructureException {

        setFrom(DOMUtils.getParameter(node, FROM_PARAMETER));

    }

    public HL7DataType getDataTypeForSituation( HL7CheckerState state ) throws ParserException {

        String value = state.getConfiguration().getPatterns().getValueForPattern(from);

        if ( value.equals("") ) {
            throw new ParserException( from, 28 );
        }

        HL7DataType dataType = state.getConfiguration().getDataTypes().getItem(value);

        if ( dataType == null ) {
            throw new ParserException( value, 9 );
        }

        return dataType;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom( String from ) {
        this.from = from;
    }
}
