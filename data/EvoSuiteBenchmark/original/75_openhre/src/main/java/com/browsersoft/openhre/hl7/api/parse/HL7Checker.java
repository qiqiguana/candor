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


package com.browsersoft.openhre.hl7.api.parse;

import com.browsersoft.openhre.hl7.api.config.HL7Configuration;
import com.browsersoft.openhre.hl7.api.regular.MessageTracerHandler;

import java.io.IOException;
import java.io.Reader;

/**
 * This class is a layer between <CODE>HL7Parser</CODE> and some HL7CheckerClient ( in this situation it is HL72XML ).
 * It accept the events from <CODE>HL7ParserHandler</CODE> interface and produce events to some <CODE>HL7CheckerHandler</CODE>
 * It check the right order of fields and subfields and their values. It also check the order of segments width use <CODE>MessageTracer</CODE>
 */
public interface HL7Checker extends HL7ParserHandler, MessageTracerHandler {

    /**
     * Event is an error
     */
    public static final int TYPE_ERROR   = 0;

    /**
     * Event is an warning
     */
    public static final int TYPE_WARNING = 1;

    /**
     * read the data from Reader according to configuration for message with id messageID
     * @param source HL7 EDI reader
     * @param messageID message id ( VXX, VXR.. )
     * @throws IOException
     * @throws ParserException
     */
    public void readData( Reader source, String messageID ) throws IOException, ParserException;

    public HL7Configuration getConfiguration();
    public void setConfiguration( HL7Configuration configuration );

    public HL7Parser getParser();
    public void setParser( HL7Parser parser );

    public HL7CheckerState getState();
    public void setState( HL7CheckerState state );

    public HL7CheckerHandler getHandler();
    public void setHandler( HL7CheckerHandler handler );

}
