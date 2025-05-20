
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

import com.browsersoft.openhre.hl7.api.config.HL7DataTypePartSubPart;
import com.browsersoft.openhre.hl7.api.config.HL7DataType;
import com.browsersoft.openhre.hl7.api.config.HL7DataTypePart;


public class HL7DataTypePartSubPartImpl extends HL7DataTypePartImpl implements HL7DataTypePartSubPart {

    private String subPartID;
    private HL7DataType subType;

    public int getType() {
        return HL7DataTypePart.PART_TYPE_SUBTYPE;
    }

    public String getSubPartID() {
        return subPartID;
    }

    public void setSubPartID( String subPartID ) {
        this.subPartID = subPartID;
    }

    public HL7DataType getSubType() {
        return subType;
    }

    public void setSubType( HL7DataType subType ) {
        this.subType = subType;
    }

    public String toString() {

        String ret = " sub part type ";

        if ( !subPartID.equals("")) {
            ret += "subPartID = \"" + subPartID + "\"";
        }
        
        return ret;

    }

}
