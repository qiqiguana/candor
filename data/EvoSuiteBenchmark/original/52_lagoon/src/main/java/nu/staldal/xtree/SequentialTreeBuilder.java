/*
 * Copyright (c) 2002-2003, Mikael Ståldal
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the author nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * ``AS IS'' AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY
 * OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *
 * Note: This is known as "the modified BSD license". It's an approved
 * Open Source and Free Software license, see
 * http://www.opensource.org/licenses/
 * and
 * http://www.gnu.org/philosophy/license-list.html
 */

package nu.staldal.xtree;

import java.util.*;
import java.io.*;
import java.net.URL;

import javax.xml.parsers.*;

import org.xml.sax.*;


/**
 * Build a list of XTree:s from a SAX2 event stream, or by parsing an XML document.
 * <p>
 * Useful to process a large document sequentially, without need to store the whole
 * document in memory at the same time.
 * <p>
 * The SequentialTreeBuilder ensures that the tree will not have two adjacent Text nodes.
 */
public class SequentialTreeBuilder implements ContentHandler, ErrorHandler
{
	public static final boolean DEBUG = false;

    private static final String XML_NS =
    	"http://www.w3.org/XML/1998/namespace";

	private URL baseURI;
    private Locator locator = null;
	private ElementHandler handler;
	private Element rootElement;
	private TreeBuilder subTreeBuilder;
	private Vector nsPrefix = null;
	private Vector nsURI = null;
	private int inSubTree = 0;


	/**
	 * Parse an XML document into a list of XTree:s, one for each element under the root.
	 * Uses JAXP to find a parser.
	 * Will not support xml:base.
	 *
	 * @param xmlInput    the input to parse
	 * @param validateDTD validate using DTD
	 * @param handler     handler to invoke for each element
	 *
	 * @return the root element (without any children)
	 *
	 * @throws SAXParseException if the XML data is not valid
	 * @throws SAXException if any other error occurs while parsing the XML data
	 * @throws IOException  if there was some I/O error while reading the input.
	 */
	public static Element parseXMLSequential(InputSource xmlInput, boolean validateDTD, 
								   ElementHandler handler)
		throws SAXParseException, SAXException, IOException
	{
		try {
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			parserFactory.setNamespaceAware(true);
			parserFactory.setValidating(validateDTD);
			parserFactory.setFeature("http://xml.org/sax/features/namespaces", true);
			parserFactory.setFeature("http://xml.org/sax/features/namespace-prefixes", false);
			parserFactory.setFeature("http://xml.org/sax/features/validation", validateDTD);			
	
			XMLReader xmlReader = parserFactory.newSAXParser().getXMLReader();			
			SequentialTreeBuilder tb = new SequentialTreeBuilder(handler);

			xmlReader.setContentHandler(tb);
			xmlReader.setErrorHandler(tb);
			xmlReader.parse(xmlInput);
					
			return tb.getRootElement();
		}
		catch (javax.xml.parsers.ParserConfigurationException e)
		{
			throw new Error("XML parser configuration error: " + e.getMessage());	
		}
	}


	/**
	 * Constructs a SequentialTreeBuilder, ready to receive SAX events.
	 * Will not support xml:base.
	 *
	 * @param handler   handler to invoke for each element
	 */
	public SequentialTreeBuilder(ElementHandler handler)
	{
		this(handler, null);
	}


	/**
	 * Constructs a SequentialTreeBuilder, ready to receive SAX events.
	 *
	 * @param handler   handler to invoke for each element
	 * @param base      base URL for the document, to support xml:base.
	 */
	public SequentialTreeBuilder(ElementHandler handler, URL base)
	{
		this.handler = handler;
		baseURI = base;
		subTreeBuilder = new TreeBuilder();
	}


	/**
	 * Obtain the root Element
	 *
	 * @throws IllegalStateException  if the SAX events received so far
	 * doesn't constitues a well-formed XML document.
	 */
	public Element getRootElement()
		throws IllegalStateException
	{
		if (rootElement == null)
			throw new IllegalStateException("No root element");

		return rootElement;
	}


    // ContentHandler implementation

    public void setDocumentLocator(Locator locator)
    {
		this.locator = locator;
		subTreeBuilder.setDocumentLocator(locator);
    }

    public void startDocument()
        throws SAXException
    {
		// nothing to do
    }

    public void endDocument()
        throws SAXException
    {
		// nothing to do
    }

    public void startElement(String namespaceURI, String localName,
                             String qName, Attributes atts)
        throws SAXException
    {
        if (DEBUG) System.out.println("startElement("+namespaceURI+','+
			localName+','+qName+')');

		if (rootElement == null)
		{
			rootElement = new Element(namespaceURI, localName, atts.getLength());
			if (locator != null)
			{
				rootElement.setSystemId(locator.getSystemId());
				rootElement.setLine(locator.getLineNumber());
				rootElement.setColumn(locator.getColumnNumber());
			}
			if (baseURI != null) rootElement.setBaseURI(baseURI);
			for (int i = 0; i < atts.getLength(); i++)
			{
				rootElement.addAttribute(atts.getURI(i), atts.getLocalName(i),
					atts.getType(i), atts.getValue(i));
				if (atts.getURI(i).equals(XML_NS) &&
						atts.getLocalName(i).equals("base"))
				{
					try {
						URL url = new URL(rootElement.getBaseURI(), atts.getValue(i));
						rootElement.setBaseURI(url);
					}
					catch (java.net.MalformedURLException e)
					{
						throw new SAXException(e);
					}
	
				}
			}
	     	if (nsPrefix != null)
    	 	{
				rootElement.setNamespaceMappings(nsPrefix, nsURI);
			}
		}
		else
		{
			inSubTree++;
			subTreeBuilder.startElement(namespaceURI, localName, qName, atts);
		}
    }

    public void endElement(String namespaceURI, String localName,
                           String qName)
        throws SAXException
    {
		if (DEBUG) System.out.println("endElement("+namespaceURI+','
            +localName+','+qName+')');
	
		if (inSubTree>0)
		{
			subTreeBuilder.endElement(namespaceURI, localName, qName);
			
			if (inSubTree == 1)
			{
				Element el = subTreeBuilder.getTree();
				el.setParent(rootElement);
				handler.processElement(el);
				subTreeBuilder.reset();
			}
			
			inSubTree--;			
		}
		else
		{
			// nothing to do	
		}		
    }

    public void startPrefixMapping(String prefix, String uri)
        throws SAXException
    {
		if (DEBUG) System.out.println("startPrefixMapping("+
			((prefix.length() == 0) ? "<default>" : prefix)+','+
			uri+')');

		if (rootElement == null)
		{
			if (nsPrefix == null)
			{
				nsPrefix = new Vector();
				nsURI = new Vector();
			}
			nsPrefix.addElement(prefix);
			nsURI.addElement(uri);
		}
		else
		{
			subTreeBuilder.startPrefixMapping(prefix, uri);	
		}
    }

    public void endPrefixMapping(String prefix)
        throws SAXException
    {
		if (DEBUG) System.out.println("endPrefixMapping("+
			((prefix.length() == 0) ? "<default>" : prefix)+')');

        // nothing to do
    }

    public void characters(char ch[], int start, int length)
        throws SAXException
    {
		if (inSubTree>0)
			subTreeBuilder.characters(ch, start, length);
    }

    public void ignorableWhitespace(char ch[], int start, int length)
        throws SAXException
    {
		if (inSubTree>0)
			subTreeBuilder.ignorableWhitespace(ch, start, length);
    }

    public void processingInstruction(String target, String data)
        throws SAXException
    {
		if (inSubTree>0)
			subTreeBuilder.processingInstruction(target, data);
    }

    public void skippedEntity(String name)
        throws SAXException
    {
		if (inSubTree>0)
			subTreeBuilder.skippedEntity(name);
    }


	// ErrorHandler implementation

	public void fatalError(SAXParseException e) throws SAXParseException
	{
		throw e;
	}

	public void error(SAXParseException e) throws SAXParseException
	{
		throw e;
	}

	public void warning(SAXParseException e)
	{
		// do nothing
	}
}

