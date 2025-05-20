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


package com.browsersoft.openhre.hl7.api.config;

import org.w3c.dom.Node;
import com.browsersoft.openhre.hl7.api.parse.HL7CheckerState;
import com.browsersoft.openhre.hl7.api.parse.ParserException;

/**
 * This class is a parent classes represents not common determining the data type of fields ( for example determining by value in record (OBX.2),
 * determining by repeatable possition in record (QRF-5))
 * It usualy read the tag depends.
 */
public interface HL7FieldDependingProcessor {
    /**
     * read the depends tag
     * @param node
     * @throws InvalidConfigDataStructureException
     */
    public void readConfiguration( Node node ) throws InvalidConfigDataStructureException;

    /**
     * This method is using in converting process and it's called if some registerer field identifier (OBX.2, QRF.5) occured. This method return
     * the matching <CODE>HL7DataType</CODE> according to actual state.
     * @param state conversion state
     * @return matching <CODE>HL7DataType</CODE>
     * @throws ParserException
     */
    public HL7DataType getDataTypeForSituation( HL7CheckerState state ) throws ParserException ;

}
