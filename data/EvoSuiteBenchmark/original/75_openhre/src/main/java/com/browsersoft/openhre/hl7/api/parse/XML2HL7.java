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

import org.xml.sax.SAXException;

import java.io.Reader;
import java.io.Writer;
import java.io.IOException;

public interface XML2HL7 {

    public void convert ( Reader reader, Writer writer ) throws SAXException, IOException;

    public SAXEvents2HL7 getSaxEvents2HL7();
    public void setSaxEvents2HL7( SAXEvents2HL7 saxEvents2HL7 );

    public XML2HL7Handler getHandler();
    public void setHandler( XML2HL7Handler handler );

}
