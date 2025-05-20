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
import com.browsersoft.openhre.hl7.api.config.HL7DataType;
import com.browsersoft.openhre.hl7.api.config.HL7Table;
import com.browsersoft.openhre.hl7.api.config.HL7Configuration;
import com.browsersoft.openhre.hl7.api.config.HL7FieldDependingProcessor;
import com.browsersoft.openhre.hl7.api.parse.HL7CheckerState;
import com.browsersoft.openhre.hl7.api.parse.ParserException;

public class HL7FieldImpl implements HL7Field {

    private String sequential;
    private String reference;
    private String description;
    private String dataTypeID;
    private HL7DataType dataType;
    private HL7FieldDependingProcessor dependingProcessor;

    public HL7FieldDependingProcessor getDependingProcessor() {
        return dependingProcessor;
    }

    public void setDependingProcessor( HL7FieldDependingProcessor dependingProcessor ) {
        this.dependingProcessor = dependingProcessor;
    }

    private int dependingType;
    private int length;
    private int required;
    private boolean repeatable;
    private String table;
    private HL7Table tableObject;

    public HL7FieldImpl() {
        sequential = "";
        reference = "";
        description = "";
        dataTypeID = "";
        table = "";
    }

    public int getDependingType() {
        return dependingType;
    }

    public void setDependingType( int dependingType ) {
       this.dependingType = dependingType;
    }

    public String getSequential() {
        return sequential;
    }

    public void setSequential( String sequential ) {
        this.sequential = sequential;
    }

    public String getReference() {
        return reference;
    }

    public void setReference( String reference ) {
        this.reference = reference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public String getDataTypeID() {
        return dataTypeID;
    }

    public void setDataTypeID( String dataTypeID ) {
        this.dataTypeID = dataTypeID;
    }

    public HL7DataType getDataType() {
        return dataType;
    }

    public void setDataType( HL7DataType dataType ) {
        this.dataType = dataType;
    }



    public int getMaximumLength() {
        return length;
    }

    public void setMaximumLength( int length ) {
        this.length = length;
    }

    public int getRequired() {
        return required;
    }

    public void setRequired( int required ) {
        this.required = required;
    }

    public boolean isRepeatable() {
        return repeatable;
    }

    public void setRepeatable( boolean repeatable ) {
        this.repeatable = repeatable;
    }

    public String getTable() {
        return table;
    }

    public void setTable( String table ) {
        this.table = table;
    }

    public HL7Table getTableObject() {
        return tableObject;
    }

    public void setTableObject( HL7Table tableObject ) {
        this.tableObject = tableObject;
    }

    public HL7DataType getActualDataType( HL7Configuration configuration, HL7CheckerState state ) throws ParserException {

        switch ( dependingType ) {
            case HL7Field.TYPE_DEPENDING_NORMAL:
                return configuration.getDataTypes().getItem(dataTypeID);
            case HL7Field.TYPE_DEPENDING_REPEATABLE:
            case HL7Field.TYPE_DEPENDING_GIVEN:
                return dependingProcessor.getDataTypeForSituation(state);
        }

        return null;
    }


    public String toString() {

        String ret = " field";

        if ( !sequential.equals("") ) {
            ret += " sequential=\"" + sequential + "\"";
        }

        if ( !reference.equals("") ) {
            ret += " reference=\"" + reference + "\"";
        }

        if ( !dataTypeID.equals("") ) {
            ret += " dataTypeID=\"" + dataTypeID + "\"";
        }

        ret += " length=\"" + length + "\"";

        ret += " required=\"";

        switch ( required ) {
            case REQUIRED_R:
                ret += "R";
                break;
            case REQUIRED_O:
                ret += "O";
                break;
            case REQUIRED_C:
                ret += "C";
                break;
            case REQUIRED_X:
                ret += "X";
                break;
            case REQUIRED_B:
                ret += "B";
                break;
        }

        ret += "\"";

        ret += " repeatable=\"" + repeatable + "\"";

        if ( !table.equals("") ) {
            ret += " table=\"" + table + "\"";
        }

        if ( !description.equals("") ) {
            ret += " description=\"" + description + "\"";
        }

        return ret;

    }
}
