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

package com.browsersoft.openhre.hl7.impl.config;

import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.InputStream;


public class DOMUtils {

    public static Document parseFile( String sPath ) throws IOException, SAXException {
        DOMParser parser = new DOMParser();
        parser.parse(sPath);
        return parser.getDocument();
    }

    public static Document parseInputStream( InputStream stream ) throws IOException, SAXException {
        DOMParser parser = new DOMParser();
        parser.parse(new InputSource(stream));
        return parser.getDocument();
    }

    public static String getParameter( Node node, String name ) {

        NamedNodeMap attributes = node.getAttributes();
        Node attribute = attributes.getNamedItem(name);
        if ( attribute != null ) {
            return attribute.getNodeValue().trim();
        } else {
            return "";
        }

    }

    public static String getTextContent( Node node ) {

        NodeList children = node.getChildNodes();

        String ret = "";
        if ( children != null ) {
            for ( int i = 0; i < children.getLength(); i++ ) {
                if ( children.item(i).getNodeType() == Node.TEXT_NODE ) {
                    ret += children.item(i).getNodeValue();
                }
            }
        }
        ret = ret.trim();
        ret = ret.replace('\n', ' ');
        ret = ret.replace('\r', ' ');
        ret = ret.replace('\t', ' ');
        return ret;
    }

}
