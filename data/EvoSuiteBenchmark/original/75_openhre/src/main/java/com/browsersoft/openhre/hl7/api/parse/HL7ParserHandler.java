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
 * If you use class <CODE>HL7Parser</CODE>, you have to implement this interface and register
 * yourself by method <CODE>setHandler</CODE>
 */
public interface HL7ParserHandler {

    /**
     * event - segment is begining
     * @param segmentID
     */
    public void beginSegment ( String segmentID );

    /**
     * event - segment is ending
     */
    public void endSegment ();

    /**
     * event - field is begining
     */
    public void beginField ();

    /**
     * event - empty field
     */
    public void emptyField ();

    /**
     * event - field is ending
     */
    public void endField ();

    /**
     * event - repeate field is begining
     */
    public void beginFieldRepeate ();

    /**
     * event - empty repeatable field
     */
    public void emptyRepeateField ();

    /**
     * event - component is starting.
     * If the component has no subcomponents ( no character & )
     * one event eventComponent and one eventSubComponent will be called
     */
    public void beginComponent ();


    /**
     * event - component is ending
     */
    public void endComponent ();

    /**
     * event - component with no subcomponent is starting
     * If the component has no subcomponents ( no character & ) one event eventComponent and ane
     * eventSubComponent will be called
     * @param value string value
     */
    public void component ( String value );

    /**
     * event - subcomponent
     * @param value string value
     */
    public void subComponent ( String value );

     /**
     * event - end of record
     */
    public void endOfRecord();

}
