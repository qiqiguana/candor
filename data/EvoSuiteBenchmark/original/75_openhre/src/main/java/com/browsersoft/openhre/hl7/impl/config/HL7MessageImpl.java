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
import com.browsersoft.openhre.hl7.api.config.HL7MessageGroup;
import com.browsersoft.openhre.hl7.api.regular.MessageTracer;

public class HL7MessageImpl implements HL7Message {

    private String id;
    private String description;
    private HL7MessageGroup group;
    private MessageTracer tracer;

    public String getID() {
        return id;
    }

    public void setID( String id ) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public HL7MessageGroup getGroup() {
        return group;
    }

    public void setGroup( HL7MessageGroup group ) {
        this.group = group;
    }

    public MessageTracer getTracer() {
        return tracer;
    }


    public void setTracer( MessageTracer tracer ) {
        this.tracer = tracer;
    }

    public String toString() {

        String ret = " -----------------------------------------\n";
        ret += " message id=\"" + id + "\"\n";
        ret += " -----------------------------------------\n";

        if ( !description.equals("") ) {
            ret += " description=\"" + description + "\"\n";
        }

        ret += group.toString() + "\n";


        return ret;


    }


}
