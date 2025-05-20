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

package com.browsersoft.openhre.hl7.impl;

import com.browsersoft.openhre.hl7.api.HL7DefaultConfigurationMaker;
import com.browsersoft.openhre.hl7.api.config.HL7VersionConfigurationMap;
import com.browsersoft.openhre.hl7.api.config.InvalidConfigDataStructureException;
import com.browsersoft.openhre.hl7.api.config.HL7Configuration;
import com.browsersoft.openhre.hl7.impl.config.DOMUtils;
import com.browsersoft.openhre.hl7.impl.config.HL7ConfigurationImpl;
import com.browsersoft.openhre.hl7.impl.config.HL7VersionConfigurationMapImpl;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.io.IOException;

public class HL7DefaultConfigurationMakerImpl implements HL7DefaultConfigurationMaker {

    public HL7VersionConfigurationMap readConfiguration() throws InvalidConfigDataStructureException, IOException, SAXException {

        Node nodeMessages = getResourceConfig("hl7_2_5_messages.xml").getDocumentElement();
        Node nodeSegments = getResourceConfig( "hl7_2_5_segments.xml").getDocumentElement();
        Node nodeDataTypes = getResourceConfig( "hl7_2_5_data_types.xml").getDocumentElement();
        Node nodeTables = getResourceConfig( "hl7_2_5_tables.xml").getDocumentElement();
        Node nodeMessagesT2 = getResourceConfig( "T2_messages.xml").getDocumentElement();
        Node nodeSegmentsT2 = getResourceConfig( "T2_segments.xml").getDocumentElement();
        Node nodeMessagesI2I = getResourceConfig( "i2i_2_4_messages.xml").getDocumentElement();
        Node nodeSegmentsI2I = getResourceConfig( "i2i_2_4_segments.xml").getDocumentElement();
        Node nodeMessagesMed = getResourceConfig( "MED_2_1_messages.xml").getDocumentElement();
        Node nodeSegmentsMed = getResourceConfig( "MED_2_1_segments.xml").getDocumentElement();
        Node nodeMessagesMed22 = getResourceConfig( "MED_2_2_messages.xml").getDocumentElement();
        Node nodeSegmentsMed22 = getResourceConfig( "MED_2_2_segments.xml").getDocumentElement();

        HL7VersionConfigurationMap map = new HL7VersionConfigurationMapImpl();

        HL7Configuration new231Configuration = new HL7ConfigurationImpl();
        new231Configuration.readConfigurations( nodeMessages, nodeSegments, nodeDataTypes, nodeTables);
        new231Configuration.connectConfigurations();
        map.addConfiguration("HL7_2.5", new231Configuration);

        HL7Configuration newT2Configuration = new HL7ConfigurationImpl();
        newT2Configuration.readMessagesConfiguration(nodeMessagesT2);
        newT2Configuration.readSegmentsConfiguration(nodeSegments);
        newT2Configuration.readSegmentsConfiguration(nodeSegmentsT2);
        newT2Configuration.readDataTypesConfiguration(nodeDataTypes);
        newT2Configuration.readTablesConfiguration(nodeTables);
        newT2Configuration.connectConfigurations();
        map.addConfiguration("T2_1.0", newT2Configuration);

        HL7Configuration newI2IConfiguration = new HL7ConfigurationImpl();
        newI2IConfiguration.readMessagesConfiguration(nodeMessagesI2I);
        newI2IConfiguration.readSegmentsConfiguration(nodeSegments);
        newI2IConfiguration.readSegmentsConfiguration(nodeSegmentsI2I);
        newI2IConfiguration.readDataTypesConfiguration(nodeDataTypes);
        newI2IConfiguration.readTablesConfiguration(nodeTables);
        newI2IConfiguration.connectConfigurations();
        map.addConfiguration("I2I_2.4", newI2IConfiguration);

        HL7Configuration newMEDConfiguration = new HL7ConfigurationImpl();
        newMEDConfiguration.readMessagesConfiguration(nodeMessagesMed);
        newMEDConfiguration.readSegmentsConfiguration(nodeSegments);
        newMEDConfiguration.readSegmentsConfiguration(nodeSegmentsMed);
        newMEDConfiguration.readDataTypesConfiguration(nodeDataTypes);
        newMEDConfiguration.readTablesConfiguration(nodeTables);
        newMEDConfiguration.connectConfigurations();
        map.addConfiguration("MED_2.1", newMEDConfiguration);

        HL7Configuration newMED22Configuration = new HL7ConfigurationImpl();
        newMED22Configuration.readMessagesConfiguration(nodeMessagesMed22);
        newMED22Configuration.readSegmentsConfiguration(nodeSegmentsMed22);
        newMED22Configuration.readDataTypesConfiguration(nodeDataTypes);
        newMED22Configuration.readTablesConfiguration(nodeTables);
        newMED22Configuration.connectConfigurations();
        map.addConfiguration("MED_2.2", newMED22Configuration);

        return map;

    }

    private Document getResourceConfig ( String name ) throws IOException, SAXException {
        return DOMUtils.parseInputStream( getClass().getResourceAsStream( "/com/browsersoft/openhre/hl7/config/" + name ));
    }

}