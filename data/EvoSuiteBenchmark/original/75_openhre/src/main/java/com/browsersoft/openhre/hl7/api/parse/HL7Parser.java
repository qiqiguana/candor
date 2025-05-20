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

import java.io.IOException;
import java.io.Reader;

public interface HL7Parser {

    /**
     * read the HL7 EDI format
     * @param source HL7 EDI reader
     * @throws IOException
     * @throws ParserException
     */
    public void readData( Reader source ) throws IOException, ParserException;

    public HL7ParserHandler getHandler();
    public void setHandler( HL7ParserHandler handler );



}
