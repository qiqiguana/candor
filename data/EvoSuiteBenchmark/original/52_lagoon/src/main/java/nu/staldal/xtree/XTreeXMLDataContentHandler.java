/*
 * Copyright (c) 2001-2002, Mikael Ståldal
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

import java.io.*;
import java.awt.datatransfer.DataFlavor;
import javax.activation.*;

import javax.xml.transform.*;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import nu.staldal.xmlutil.ContentHandlerFixer;


/**
 * Java Activation Framework DataContentHandler for XML, 
 * using the XTree representation.
 *
 * @author Mikael Ståldal
 */
public class XTreeXMLDataContentHandler implements DataContentHandler
{
	private static TransformerFactory tf = TransformerFactory.newInstance();

		
    public java.awt.datatransfer.DataFlavor[] getTransferDataFlavors()
	{	
		try {
			return new DataFlavor[] 
				{ new DataFlavor("application/xml"), new DataFlavor("text/xml") };
		}
		catch (ClassNotFoundException e)
		{
			throw new Error("Unable to construct java.awt.datatransfer.DataFlavor");	
		}
	}
	

    public java.lang.Object getTransferData(java.awt.datatransfer.DataFlavor df,
                                            DataSource ds)
    	throws java.io.IOException // ,java.awt.datatransfer.UnsupportedFlavorException               
	{
		try {
			return TreeBuilder.parseXML(new InputSource(ds.getInputStream()), false);
		}
		catch (SAXException e)
		{
			throw new IOException(e.toString());
		}
	}											

	
    public java.lang.Object getContent(DataSource ds)
    	throws java.io.IOException
	{
		try {
			return getTransferData(new DataFlavor("application/xml"), ds);
		}
		catch (ClassNotFoundException e)
		{
			throw new Error("Unable to construct java.awt.datatransfer.DataFlavor");	
		}
	}

							
    public void writeTo(java.lang.Object obj,
                        java.lang.String mimeTypeString,
                        java.io.OutputStream os)
                 throws java.io.IOException
	{
		MimeType mimeType;
		try {
			mimeType = new MimeType(mimeTypeString);
		}
		catch (MimeTypeParseException e)
		{
			throw new IOException(e.toString());	
		}
		String charset = mimeType.getParameter("charset");
		boolean isText = mimeType.getPrimaryType().equals("text"); 
		if (charset == null)
			charset = isText ? "us-ascii" : "utf-8";
		
		try {
			if (!tf.getFeature(SAXTransformerFactory.FEATURE)||
				!tf.getFeature(StreamResult.FEATURE))
			{
				throw new TransformerConfigurationException(
					"JAXP implementation does not support SAX->Stream serialization");	
			}				
			
			TransformerHandler th = ((SAXTransformerFactory)tf).newTransformerHandler();
			th.setResult(new StreamResult(os));
			th.getTransformer().setOutputProperty(OutputKeys.METHOD, "xml");
			th.getTransformer().setOutputProperty(OutputKeys.ENCODING, charset);
			th.getTransformer().setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			th.getTransformer().setOutputProperty(OutputKeys.MEDIA_TYPE, mimeTypeString);
			
			th.startDocument();
			((Node)obj).toSAX(new ContentHandlerFixer(th, true));
			th.endDocument();
		}
		catch (TransformerConfigurationException e)
		{
			throw new Error(e.toString());	
		}
		catch (SAXException e)
		{
			throw new IOException(e.toString());	
		}
	}
}

