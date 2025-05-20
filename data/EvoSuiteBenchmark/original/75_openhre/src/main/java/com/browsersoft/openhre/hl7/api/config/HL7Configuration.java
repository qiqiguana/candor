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


package com.browsersoft.openhre.hl7.api.config;

import org.w3c.dom.Node;

/**
 * Class represents whole configuration for one specification
 */
public interface HL7Configuration {

    /**
     * read the all configuration en bloc
     * @param messagesNode messages definition root node
     * @param dataTypesNode data types definition root node
     * @param segmentsNode segments definition root node
     * @param tablesNode tables definition root node
     * @throws InvalidConfigDataStructureException
     */
    public void readConfigurations ( Node messagesNode, Node dataTypesNode, Node segmentsNode, Node tablesNode ) throws InvalidConfigDataStructureException;

    /**
     * add additional messages configuration
     * @param messagesNode messages definition root node
     * @throws InvalidConfigDataStructureException
     */
    public void readMessagesConfiguration( Node messagesNode ) throws InvalidConfigDataStructureException;

    /**
     * add additional segments configuration
     * @param segmentsNode segments definition root node
     * @throws InvalidConfigDataStructureException
     */
    public void readSegmentsConfiguration( Node segmentsNode ) throws InvalidConfigDataStructureException;

    /**
     * add additional data types configuration
     * @param dataTypesNode data types definition root node
     * @throws InvalidConfigDataStructureException
     */
    public void readDataTypesConfiguration( Node dataTypesNode ) throws InvalidConfigDataStructureException;

    /**
     * add additional tables configuration
     * @param tablesNode tables definition root node
     * @throws InvalidConfigDataStructureException
     */
    public void readTablesConfiguration( Node tablesNode ) throws InvalidConfigDataStructureException;


    /**
     * if the adding configurations is done, call this method..
     * @throws InvalidConfigDataStructureException
     */
    public void connectConfigurations() throws InvalidConfigDataStructureException;

    public  HL7PatternsForCatchValues  getPatterns();
    public void setPatterns( HL7PatternsForCatchValues patterns );

    public HL7MessageMap getMessages();
	public void setMessages( HL7MessageMap messages );

	public HL7DataTypeMap getDataTypes();
	public void setDataTypes( HL7DataTypeMap dataTypes );

	public HL7SegmentMap getSegments();
	public void setSegments( HL7SegmentMap segments );

    public HL7TableMap getTables();
	public void setTables( HL7TableMap tables );

}
