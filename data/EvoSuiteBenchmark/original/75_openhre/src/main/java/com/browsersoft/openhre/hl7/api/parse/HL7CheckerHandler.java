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
 * If you use class <CODE>HL7Checker</CODE>, you have to implement this interface and register yourself
 * by method <CODE>setHandler</CODE>.
 */
public interface HL7CheckerHandler {
    /**
     * event - the additinal tag ( the reason, why the support additional tag extension has been added to this library
     * is the format of T2 messages - please see into T2 HealthInfo specification ) begining
     * @param tagName name of tag
     */
    public void beginAdditionalTag ( String tagName );

    /**
     * event - the additinal tag ending
     * @param tagName
     */
    public void endAdditionalTag ( String tagName );

    /**
     * event - segment is begining
     * @param segmentID id of segment
     */
    public void beginSegment ( String segmentID );

    /**
     * event - segment is ending
     * @param segmentID id of segment
     */
    public void endSegment ( String segmentID );

    /**
     * event - field is begining
     * @param segmentID id of segment
     * @param position this number equals the value of parameter sequential in segments/fields/field tag in configuration file
     * @param empty field is empty or not
     */
    public void beginField ( String segmentID, int position, boolean empty );

    /**
     * event - field is ending
     * @param segmentID id of segment
     * @param position this number equals the value of parameter sequential in segments/fields/field tag in configuration file
     * @param empty field is empty or not
     */
    public void endField ( String segmentID, int position, boolean empty );

    /**
     * event - single component value
     * @param stringValue
     */
    public void componentValue ( String stringValue );

    /**
     * event - component is begining
     * @param dataType data type identifier
     * @param position the part number in concrete component data type
     * @param empty component is empty or not
     */
    public void beginComponent ( String dataType, int position, boolean empty );

    /**
     * event - component is ending
     * @param dataType data type identifier
     * @param position the subpart number in concrete component data type
     * @param empty component is empty or not
     */
    public void endComponent ( String dataType, int position, boolean empty );

    /**
     * event - subcomponent
     * @param dataType data type identifier
     * @param position the subpart number in concrete subcomponent data type
     * @param value
     */
    public void subComponent ( String dataType, int position, String value );

    /**
     * event - error or warning
     * @param type either HL7Checker.TYPE_ERROR or HL7Checker.TYPE_WARNING
     * @param code message code ( you have to translating it by <CODE>MessageTranslator</CODE> )
     * @param message pastfix of message
     * @param segmentID id of actual segment
     * @param fieldID id of actual field
     */
    public void error( int type, int code, String message, String segmentID, String fieldID );

}
