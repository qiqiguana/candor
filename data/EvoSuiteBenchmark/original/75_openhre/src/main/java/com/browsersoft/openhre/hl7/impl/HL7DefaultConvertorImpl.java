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

package com.browsersoft.openhre.hl7.impl;

import com.browsersoft.openhre.hl7.api.HL7DefaultConvertor;
import com.browsersoft.openhre.hl7.api.HL7DefaultConvertorHandler;
import com.browsersoft.openhre.hl7.api.MessageTranslator;
import com.browsersoft.openhre.hl7.api.config.HL7Configuration;
import com.browsersoft.openhre.hl7.api.config.HL7VersionConfigurationMap;
import com.browsersoft.openhre.hl7.api.parse.HL72XML;
import com.browsersoft.openhre.hl7.api.parse.HL7Checker;
import com.browsersoft.openhre.hl7.api.parse.ParserException;
import com.browsersoft.openhre.hl7.impl.config.HL7VersionConfigurationMapImpl;
import com.browsersoft.openhre.hl7.impl.parser.HL72XMLImpl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;


public class HL7DefaultConvertorImpl implements HL7DefaultConvertor {

    private HL7VersionConfigurationMap configurations;
    private HL7DefaultConvertorHandler handler;
    private MessageTranslator translator;

    public HL7DefaultConvertorImpl() {
        configurations = new  HL7VersionConfigurationMapImpl();
        translator = new MessageTranslatorImpl();
    }

    public HL7VersionConfigurationMap getConfigurations() {
        return configurations;
    }

    public void setConfigurations( HL7VersionConfigurationMap configurations ) {
        this.configurations = configurations;
    }

    public HL7DefaultConvertorHandler getHandler() {
        return handler;
    }

    public MessageTranslator getMessageTranslator() {
        return translator;
    }

    public void setMessageTranspator( MessageTranslator translator ) {
        this.translator = translator;
    }

    public void setHandler( HL7DefaultConvertorHandler handler ) {
        this.handler = handler;
    }

    public void convert( InputStream input, String inputEncoding, OutputStream output, String outputEncoding, String messageID, String versionOfHL7 ) throws  IOException, ParserException {
       convert( new InputStreamReader ( input, inputEncoding ), new OutputStreamWriter(output, outputEncoding), messageID , versionOfHL7);
    }

    public void convert( Reader input, Writer output, String messageID, String versionOfHL7 ) throws  IOException, ParserException {

        HL72XML hl72xml = new HL72XMLImpl();
        hl72xml.setHandler(this);

        HL7Configuration configuration = configurations.getSuitableConfiguration(versionOfHL7);

        hl72xml.convert( input, messageID, output, configuration);

    }

    public void error( int type, int code, String message, String segmentID, String fieldID ) {

        String error;

        if ( type == HL7Checker.TYPE_ERROR ) {
           error = "ERROR:";
        } else {
           error = "WARNING:";
        }
        error += " " + translator.getLocalizedMessage(code) + " " + message;
        if ( !segmentID.equals("")) {
           error += " in " + segmentID;
        }
        if ( !fieldID.equals("")) {
            error += "/" + fieldID;
        }

        if ( handler != null ) {
            handler.error(type, error);
        }

    }
}
