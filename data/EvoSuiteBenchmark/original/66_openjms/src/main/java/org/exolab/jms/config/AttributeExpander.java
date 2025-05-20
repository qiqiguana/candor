/**
 * Redistribution and use of this software and associated documentation
 * ("Software"), with or without modification, are permitted provided
 * that the following conditions are met:
 *
 * 1. Redistributions of source code must retain copyright
 *    statements and notices.  Redistributions must also contain a
 *    copy of this document.
 *
 * 2. Redistributions in binary form must reproduce the
 *    above copyright notice, this list of conditions and the
 *    following disclaimer in the documentation and/or other
 *    materials provided with the distribution.
 *
 * 3. The name "Exolab" must not be used to endorse or promote
 *    products derived from this Software without prior written
 *    permission of Exoffice Technologies.  For written permission,
 *    please contact info@exolab.org.
 *
 * 4. Products derived from this Software may not be called "Exolab"
 *    nor may "Exolab" appear in their names without prior written
 *    permission of Exoffice Technologies. Exolab is a registered
 *    trademark of Exoffice Technologies.
 *
 * 5. Due credit should be given to the Exolab Project
 *    (http://www.exolab.org/).
 *
 * THIS SOFTWARE IS PROVIDED BY EXOFFICE TECHNOLOGIES AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT
 * NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL
 * EXOFFICE TECHNOLOGIES OR ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * Copyright 2001,2003 (C) Exoffice Technologies Inc. All Rights Reserved.
 *
 * $Id: AttributeExpander.java,v 1.1 2004/11/26 01:50:41 tanderson Exp $
 */
package org.exolab.jms.config;

import java.io.IOException;
import java.io.Reader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exolab.castor.util.Configuration;
import org.exolab.castor.xml.EventProducer;
import org.xml.sax.AttributeList;
import org.xml.sax.DocumentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.AttributeListImpl;


/**
 * This class expands attributes in XML documents as the document is being
 * parsed. It is designed to be used in conjunction with the Castor
 * unmarshalling framework.
 * <p>
 * To be expanded, attribute values must contain text of the form
 * <i>${property.name}</i>, where <i>property.name</i> is a property returned
 * by <code>System.getProperty()</code>.<br>
 * If no property exists, the attribute value remains unchanged.
 *
 * @version     $Revision: 1.1 $ $Date: 2004/11/26 01:50:41 $
 * @author      <a href="mailto:tima@intalio.com">Tim Anderson</a>
 * @see         EventProducer
 * @see         org.exolab.castor.xml.Unmarshaller
 */
public class AttributeExpander implements EventProducer {

    private DocumentHandler _handler = null;
    private Reader _reader = null;

    /**
     * The logger
     */
    private static final Log _log =
        LogFactory.getLog(AttributeExpander.class);


    /**
     * Construct a new instance
     *
     * @param reader the XML file reader
     */
    public AttributeExpander(Reader reader) {
        _reader = reader;
    }

    /**
     * Sets the DocumentHandler to send SAX events to
     */
    public void setDocumentHandler(DocumentHandler handler) {
        _handler = handler;
    }

    /**
     * Signals to start producing events.
     */
    public void start() throws SAXException {
        Parser parser = Configuration.getDefaultParser();
        if (parser == null) {
            throw new SAXException("Unable to create parser");
        }

        DocumentHandler handler = new AttributeInterceptor();
        parser.setDocumentHandler(handler);
        try {
            parser.parse(new InputSource(_reader));
        } catch (IOException exception) {
            throw new SAXException(exception.getMessage(), exception);
        }
    }

    /**
     * Helper class to intercept {@link #startElement} calls, expanding
     * any attributes.
     */
    private class AttributeInterceptor implements DocumentHandler {

        public void setDocumentLocator(Locator locator) {
            _handler.setDocumentLocator(locator);
        }

        public void startDocument() throws SAXException {
            _handler.startDocument();
        }

        public void endDocument() throws SAXException {
            _handler.endDocument();
        }

        public void startElement(String name, AttributeList list)
            throws SAXException {

            AttributeListImpl replaced = new AttributeListImpl();
            for (int i = 0; i < list.getLength(); i++) {
                String value = expand(list.getName(i), list.getValue(i));
                replaced.addAttribute(list.getName(i), list.getType(i), value);
            }
            _handler.startElement(name, replaced);
        }

        public void endElement(String name) throws SAXException {
            _handler.endElement(name);
        }

        public void characters(char[] ch, int start, int length)
            throws SAXException {
            _handler.characters(ch, start, length);
        }

        public void ignorableWhitespace(char[] ch, int start, int length)
            throws SAXException {
            _handler.ignorableWhitespace(ch, start, length);
        }

        public void processingInstruction(String target, String data)
            throws SAXException {
            _handler.processingInstruction(target, data);
        }

        private String expand(String attribute, String value) {
            StringBuffer buffer = new StringBuffer();
            int prev = 0;
            int pos;
            while ((pos = value.indexOf("${", prev)) >= 0) {
                if (pos > 0) {
                    buffer.append(value.substring(prev, pos));
                }
                int index = value.indexOf('}', pos);
                if (index < 0) {
                    // invalid format
                    _log.warn("Cannot expand " + attribute
                        + " - invalid format: " + value);
                    buffer.append("${");
                    prev = pos + 2;
                } else {
                    String name = value.substring(pos + 2, index);
                    String property = System.getProperty(name);
                    if (property != null) {
                        buffer.append(property);
                    } else {
                        // attribute cannot be expanded as the property is
                        // not defined
                        _log.warn("Cannot expand " + attribute
                            + " as property " + name
                            + " is not defined");
                        buffer.append("${");
                        buffer.append(name);
                        buffer.append("}");
                    }
                    prev = index + 1;
                }
            }
            if (prev < value.length()) {
                buffer.append(value.substring(prev));
            }
            String result = buffer.toString();
            return result;
        }

    } //-- AttributeInterceptor

} //-- AttributeExpander
