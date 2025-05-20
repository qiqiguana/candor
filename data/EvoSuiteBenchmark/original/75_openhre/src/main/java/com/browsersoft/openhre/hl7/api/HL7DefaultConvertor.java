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


package com.browsersoft.openhre.hl7.api;

import com.browsersoft.openhre.hl7.api.config.HL7VersionConfigurationMap;
import com.browsersoft.openhre.hl7.api.parse.HL72XMLHandler;
import com.browsersoft.openhre.hl7.api.parse.ParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 * This class is a convertor from HL7 EDI to HL7 XML with simplified interface of HL72XML.
 */
public interface HL7DefaultConvertor extends HL72XMLHandler {

    public HL7VersionConfigurationMap getConfigurations();
    public void setConfigurations( HL7VersionConfigurationMap configurations );

    /**
     * Convert HL7 EDI ( input, inputEncoding)  to HL7 XML ( output, outputEncoding ). Use the configuration for message ( messageID ) in version ( versionOfHL7 )
     * @param input HL7 EDI input stream
     * @param inputEncoding HL7 EDI encoding
     * @param output HL7 XML output stream
     * @param outputEncoding HL7 XML encoding
     * @param messageID id of message ( for example - VXX, VXR...)
     * @param versionOfHL7 version of configuration ( for example HL7_2.3.1, RSP_Z01... )
     * @throws IOException
     * @throws ParserException
     */
    public void convert ( InputStream input, String inputEncoding, OutputStream output, String outputEncoding, String messageID, String versionOfHL7  ) throws IOException, ParserException;

    /**
     * The equivalent second method with Reader and Writer
     * @param input HL7 EDI Reader
     * @param output HL7 XML Writer
     * @param messageID id of message ( for example - VXX, VXR...)
     * @param versionOfHL7 version of configuration ( for example HL7_2.3.1, RSP_Z01... )
     * @throws IOException
     * @throws ParserException
     */
    public void convert( Reader input, Writer output, String messageID, String versionOfHL7 ) throws  IOException, ParserException;

    public HL7DefaultConvertorHandler getHandler();
    public void setHandler ( HL7DefaultConvertorHandler handler );

    public MessageTranslator getMessageTranslator();
    public void setMessageTranspator( MessageTranslator translator );


}
