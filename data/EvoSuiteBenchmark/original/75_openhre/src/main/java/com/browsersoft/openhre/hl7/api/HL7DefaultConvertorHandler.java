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

/**
 * If you use class <CODE>HL7DefaultConvertor</CODE>, you have to implement this interface and register yourself by method <CODE>setHandler</CODE>
 */
public interface HL7DefaultConvertorHandler {

    /**
     * error or warning event from converting process
     * @param type either HL7Checker.TYPE_ERROR or HL7Checker.TYPE_WARNING 
     * @param message description of error
     */
    public void error( int type, String message );

}
