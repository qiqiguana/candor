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

/**
 * If you use class <CODE>HL7SAXEventGenerator</CODE>, you have to implement this interface and register
 * yourself by method <CODE>setHandler</CODE>
 */
public interface HL7SAXEventGeneratorHandler {

    /**
     * event - error or warning
     * @param idFile file id
     * @param type either HL7Checker.TYPE_ERROR or HL7Checker.TYPE_WARNING
     * @param code message code
     * @param message message postfix
     * @param segmentID actual segment id
     * @param fieldID actual field id
     */
    public void error( String idFile, int type, int code, String message, String segmentID, String fieldID );

}
