/*
 * Copyright (c) 2002-2004, Mikael Ståldal
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
import javax.xml.transform.*;
import javax.xml.transform.sax.*;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.*;
import org.iso_relax.verifier.*; 

import nu.staldal.xmlutil.ContentHandlerFixer;


/**
 * Some utility methods for XTree. All methods in this class are static.
 */
public final class XTreeUtil
{
    /**
     * Private default constructor to prevent instantiation.
     */
    private XTreeUtil() {}
    
	
	/**
	 * Parse an XML document into an XTree.
	 * Uses JAXP to find a parser and JARV to find a validator.
	 * Will not support xml:base.
	 *
	 * @param xmlInput    the input to parse
	 * @param validateDTD validate using DTD
	 * @param schemaType  the type of schema to use, or <code>null</code>
	 *                    for no schema validation
	 * @param schema      the schema to use, or <code>null</code>
	 *                    for no schema validation
	 *
	 * @return an XTree representation of the XML data
	 *
	 * @throws SAXParseException if the XML data is not valid
	 * @throws SAXException if any other error occurs while parsing the XML data
	 * @throws IOException  if there was some I/O error while reading the input.
	 */
	public static Element parseXML(InputSource xmlInput, boolean validateDTD,
								   String schemaType, InputSource schema)
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
			TreeBuilder tb = new TreeBuilder();

			if (schema != null)
			{
				VerifierFactory vf = VerifierFactory.newInstance(schemaType);
					
				Verifier verifier = vf.newVerifier(schema);
				VerifierFilter filter = verifier.getVerifierFilter();
				filter.setParent(xmlReader);
				xmlReader = filter;
			}

			xmlReader.setContentHandler(tb);
			xmlReader.setErrorHandler(tb);
			xmlReader.parse(xmlInput);
			
			if ((schema != null) && !((VerifierFilter)xmlReader).isValid())
			{
				throw new SAXParseException("Invalid XML data", null, null, -1, -1);
			}
		
			return tb.getTree();
		}
		catch (javax.xml.parsers.ParserConfigurationException e)
		{
			throw new Error("XML parser configuration error: " + e.getMessage());	
		}
		catch (VerifierConfigurationException e)
		{
			throw new Error("XML verifier configuration error: " + e.getMessage());	
		}
	}

	
	/**
	 * Parse an XML document into a list of XTree:s, one for each element under the root.
	 * Uses JAXP to find a parser and JARV to find a validator.
	 * Will not support xml:base.
	 *
	 * @param xmlInput    the input to parse
	 * @param validateDTD validate using DTD
	 * @param schemaType  the type of schema to use, or <code>null</code>
	 *                    for no schema validation
	 * @param schema      the schema to use, or <code>null</code>
	 *                    for no schema validation
	 * @param handler     handler to invoke for each element
	 *
	 * @return the root element (without any children)
	 *
	 * @throws SAXParseException if the XML data is not valid
	 * @throws SAXException if any other error occurs while parsing the XML data
	 * @throws IOException  if there was some I/O error while reading the input.
	 */
	public static Element parseXMLSequential(InputSource xmlInput, boolean validateDTD, 
								   String schemaType, InputSource schema,
								   ElementHandler handler)
		throws SAXParseException, SAXException, IOException
	{
		try {
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			parserFactory.setNamespaceAware(true);
			parserFactory.setValidating(validateDTD);
	
			XMLReader xmlReader = parserFactory.newSAXParser().getXMLReader();			
			SequentialTreeBuilder tb = new SequentialTreeBuilder(handler);

			if (schema != null)
			{
				VerifierFactory vf = VerifierFactory.newInstance(schemaType);
					
				Verifier verifier = vf.newVerifier(schema);
				VerifierFilter filter = verifier.getVerifierFilter();
				filter.setParent(xmlReader);
				xmlReader = filter;
			}

			xmlReader.setContentHandler(tb);
			xmlReader.setErrorHandler(tb);
			xmlReader.parse(xmlInput);
			
			if ((schema != null) && !((VerifierFilter)xmlReader).isValid())
			{
				throw new SAXParseException("Invalid XML data", null, null, -1, -1);
			}
		
			return tb.getRootElement();
		}
		catch (javax.xml.parsers.ParserConfigurationException e)
		{
			throw new Error("XML parser configuration error: " + e.getMessage());	
		}
		catch (VerifierConfigurationException e)
		{
			throw new Error("XML verifier configuration error: " + e.getMessage());	
		}
	}

	/**
	 * Serialize an XTree into an OutputStream.
	 *
	 * @param tree      the XTree to serialize
	 * @param os        the OutputStream to write to
	 *
	 * @throws IOException if any error occurs
	 */
	public static void serialize(Node tree, OutputStream os)
        throws IOException
	{
		Properties prop = new Properties();

		prop.setProperty(OutputKeys.METHOD, "xml");
		prop.setProperty(OutputKeys.ENCODING, "utf-8");
		prop.setProperty(OutputKeys.INDENT, "no");

		serialize(tree, os, prop);
	}
	

	/**
	 * Serialize an XTree into an OutputStream.
	 *
	 * @param tree      the XTree to serialize
	 * @param os        the OutputStream to write to
	 * @param prop  	output properties
	 *
	 * @throws IOException if any error occurs
	 */
	public static void serialize(Node tree, OutputStream os, Properties prop)
        throws IOException
	{
		try {
			TransformerFactory tf = TransformerFactory.newInstance();
			if (!(tf.getFeature(SAXTransformerFactory.FEATURE)
					&& tf.getFeature(StreamResult.FEATURE)))
			{
				throw new Error("The transformer factory "
					+ tf.getClass().getName() + " doesn't support SAX");
			}
				
			SAXTransformerFactory tfactory = (SAXTransformerFactory)tf;
			TransformerHandler th = tfactory.newTransformerHandler();
			th.setResult(new StreamResult(os));
			
			Transformer trans = th.getTransformer();
			trans.setOutputProperties(prop);
			
			ContentHandler ch = new ContentHandlerFixer(th, true);
			
			try {
				ch.startDocument();
				tree.toSAX(ch);
				ch.endDocument();
			}
			catch (SAXException e)
			{
				throw new IOException(e.toString());	
			}
		}
		catch (TransformerConfigurationException e)
		{
			throw new Error(e.toString());	
		}
	}

}

