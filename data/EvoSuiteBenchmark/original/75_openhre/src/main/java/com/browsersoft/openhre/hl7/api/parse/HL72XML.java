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

import java.io.Writer;
import java.io.Reader;
import java.io.IOException;

/**
 * This class is one of <CODE>HL7Checker</CODE> client solutions. This class is a convertor from HL7 EDI to HL7 XML.
 */
public interface HL72XML extends HL7CheckerHandler {


    /**
     * Convert HL7 EDI ( reader )  to HL7 XML ( writer ). Use the configuration ( configuration ) and message ID ( messageID )
     * @param reader HL7 EDI Reader
     * @param messageID message id
     * @param writer HL7 XML Writer
     * @param configuration configuration
     * @throws IOException
     * @throws ParserException
     */
    public void convert( Reader reader, String messageID, Writer writer, HL7Configuration configuration ) throws IOException, ParserException;

    public HL72XMLHandler getHandler();
    public void setHandler( HL72XMLHandler handler );

    public HL7Checker getChecker();
    public void setChecker( HL7Checker checker );

}
