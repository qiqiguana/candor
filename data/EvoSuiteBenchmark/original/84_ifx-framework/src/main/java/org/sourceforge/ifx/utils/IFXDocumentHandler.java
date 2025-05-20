/*
 * $Id: IFXDocumentHandler.java,v 1.2 2004/02/26 17:50:30 spal Exp $
 * $Source: /cvsroot/ifx-framework/code/src/org/sourceforge/ifx/utils/IFXDocumentHandler.java,v $
 * IFX-Framework - IFX XML to JavaBean application framework.
 * Copyright (C) 2003  The IFX-Framework Team
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.sourceforge.ifx.utils;

import java.io.OutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Iterator;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.sourceforge.ifx.basetypes.IFXObject;

/**
 * The IFXDocumentHandler provides methods for formatting and validating
 * IFX XML Documents.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.2 $
 */
public class IFXDocumentHandler {

    /** System default Line separator string. */
    public static final String DEFAULT_LINE_SEPARATOR = 
        System.getProperty("line.separator");

    /**
     * Builds a JDOM Document object from an IFXObject.
     * @param obj the IFXObject object to format.
     * @param namespacePrefix the namespace prefix to use in the output. A
     * value of null implies no prefix. If the namespaceURI is specified it
     * will be treated as the default namespace.
     * @param namespaceURI the namespace URI to use in the output. If specified
     * an xmlns declaration will be prepended to the output.
     * @return a JDOM Document object.
     * @exception IFXException if there was a problem building the document.
     */
    public static Document build(IFXObject obj, String namespacePrefix, 
            String namespaceURI) throws IFXException {
        IFXEncoder encoder = null;
        if (namespaceURI != null) {
            encoder = new IFXEncoder(namespacePrefix, namespaceURI);
        } else {
            encoder = new IFXEncoder();
        }
        Element element = encoder.encode(obj);
        Document doc = new Document(element);
        return doc;
    }

    /**
     * Writes a Document object to the specified output stream.
     * @param doc a JDOM Document object.
     * @param indentSize the size of the indent in the output. A value of
     * zero implies no indentation.
     * @param lineSeparator the line separator for the output. A value of
     * null implies no line separators and no newlines in the output. To use
     * the system default, use DEFAULT_LINE_SEPARATOR.
     * @param ostream the OutputStream to write the formatted output to.
     * @exception IFXException if there was a problem writing the Document.
     */
    public static void write(Document doc, int indentSize, 
            String lineSeparator, OutputStream ostream) throws IFXException {
        XMLOutputter outputter = new XMLOutputter();
        StringBuffer indent = new StringBuffer();
        for (int i = 0; i < indentSize; i++) {
            indent.append(" ");
        }
        outputter.setIndent(indent.toString());
        if (lineSeparator == null) {
            outputter.setNewlines(false);
        } else {
            outputter.setNewlines(true);
            outputter.setLineSeparator(lineSeparator);
        }
        try {
            outputter.output(doc, ostream);
        } catch (IOException e) {
            throw new IFXException("Error writing Document");
        }
    }

    /**
     * Parses a Document object into an IFXObject. This is a thin wrapper
     * over the IFXDecoder.decode() method.
     * @param doc the JDOM Document object to parse.
     * @exception IFXException if any problems were encountered in parsing.
     */
    public static IFXObject parse(Document doc) throws IFXException {
        Element element = doc.getRootElement();
        IFXDecoder decoder = new IFXDecoder();
        return decoder.decode(element);
    }

    /**
     * Reads an XML stream from the specified InputStream and returns a 
     * JDOM Document object. If validation is requested, then the XML is
     * validated while reading it into a Document.
     * @param istream the InputStream to read from.
     * @param validate XML is validated against schema if true.
     * @param validationProperties a Map of name value pairs specifying
     * the schema and the namespaces to validate against.
     * @exception IFXExveption if there was a problem reading the document.
     */
    public static Document read(InputStream istream, boolean validate, 
            Map validationProperties) throws IFXException {
        SAXBuilder builder = null;
        if (validate) {
            builder = 
                new SAXBuilder("org.apache.xerces.parsers.SAXParser", true);
            builder.setFeature(
                "http://apache.org/xml/features/validation/schema", true);
            if (validationProperties != null) {
                Iterator propIter = validationProperties.keySet().iterator();
                StringBuffer buf = new StringBuffer();
                int i = 0;
                while (propIter.hasNext()) {
                    String key = (String) propIter.next();
                    String value = (String) validationProperties.get(key);
                    if (i > 0) { buf.append(" "); }
                    buf.append(key).append(" ").append(value);
                    i++;
                }
                builder.setProperty("http://apache.org/xml/properties/schema/external-schemaLocation", buf.toString());
            }
        } else {
            builder = new SAXBuilder();
        }
        Document doc = null;
        try {
            doc = builder.build(istream);
        } catch (Exception e) {
            throw new IFXException("Error reading Document", e);
        }
        return doc;
    }
}
