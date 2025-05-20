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

import com.browsersoft.openhre.hl7.api.config.HL7DataTypePartPrimitive;
import com.browsersoft.openhre.hl7.api.config.HL7Table;
import com.browsersoft.openhre.hl7.api.config.HL7DataTypePart;
import com.browsersoft.openhre.hl7.api.parse.HL7PrimitiveDataTypeReader;

public class HL7DataTypePartPrimitiveImpl extends HL7DataTypePartImpl implements HL7DataTypePartPrimitive {

    private String className = "";
    private HL7PrimitiveDataTypeReader instanceOfReader;
    private String idTable = "";
    private HL7Table table;

    public int getType() {
        return HL7DataTypePart.PART_TYPE_PRIMITIVE;
    }

    public String getReader() {
        return className;
    }

    public void setReader( String className ) {
       this.className = className;
    }

    public HL7PrimitiveDataTypeReader getInstanceReader() {
        return instanceOfReader;
    }

    public void setInstanceReader( HL7PrimitiveDataTypeReader instanceOfReader ) {
        this.instanceOfReader = instanceOfReader;
    }

    public String getIDTable() {
        return idTable;
    }

    public void setIDTable( String idTable ) {
        this.idTable = idTable;
    }

    public HL7Table getTable() {
        return table;
    }

    public void setTable( HL7Table table ) {
        this.table = table;
    }

    public String toString() {

        String ret = " primitive type ";

        if ( !className.equals("")) {
            ret += "className = \"" + className + "\"";
        }

        if ( !idTable.equals("")) {
            ret += "idTable = \"" + idTable + "\"";
        }

        return ret;

    }
}
