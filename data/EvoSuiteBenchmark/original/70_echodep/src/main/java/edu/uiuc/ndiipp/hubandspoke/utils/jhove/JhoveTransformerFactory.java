/**
 * JhoveTransformerFactory.java
 * 
 * $Revision: 845 $
 * 
 * $Date: 2009-04-22 18:28:30 +0100 (Wed, 22 Apr 2009) $
 * 
 * Copyright (c) 2009, University Library, University of Illinois at 
 * Urbana-Champaign. All rights reserved. 
 * 
 * Developed by: The Hub and Spoke Project Group 
 *               University of Illinois Urbana-Champaign Library
 *               http://dli.grainger.uiuc.edu/echodep/hands/ 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the 
 * "Software"), to deal with the Software without restriction, including 
 * without limitation the rights to use, copy, modify, merge, publish, 
 * distribute, sublicense, and/or sell copies of the Software, and to 
 * permit persons to whom the Software is furnished to do so, subject to 
 * the following conditions: 
 * 
 * - Redistributions of source code must retain the above copyright notice, 
 * this list of conditions and the following disclaimers. 
 * 
 * - Redistributions in binary form must reproduce the above copyright 
 * notice, this list of conditions and the following disclaimers in the 
 * documentation and/or other materials provided with the distribution. 
 * 
 * - Neither the names of The Hub and Spoke Project Group, University of 
 * Illinois Urbana-Champaign Library, nor the names of its contributors may 
 * be used to endorse or promote products derived from this Software 
 * without specific prior written permission. 
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS 
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE CONTRIBUTORS OR COPYRIGHT HOLDERS BE LIABLE FOR 
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, 
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 * SOFTWARE OR THE USE OR OTHER DEALINGS WITH THE SOFTWARE. 
 */

package edu.uiuc.ndiipp.hubandspoke.utils.jhove;

import org.w3c.dom.*;

import java.util.*;
import javax.xml.xpath.*; 

/**
 * This class takes the path to a file, generates the raw
 * Jhove metadata, and figures out the MIME type in order
 * to return a proper JhoveTransformer for the file.
 * 
 * @author Howard Ding
 *
 */

public class JhoveTransformerFactory {
	
	/**
	 * dispatchTable matches the MIME string reported by Jhove
	 * to a Transformer class that can handle the Jhove metadata
	 * produced from a file of that type.
	 */
	private static Map<String,Class> dispatchTable;
	
	/**
	 * Extracts the MIME type from Jhove metadata
	 * 
	 * @param jhoveInput The raw Jhove metadata
	 * @return String that encodes the MIME type given in the
	 * Jhove metadata
	 * @throws XPathExpressionException
	 */
	protected static String determineMIMEType (Node jhoveInput)
		throws XPathExpressionException 
	{
		XPath xpath = XPathFactory.newInstance().newXPath();
		xpath.setNamespaceContext(JhoveNamespaceContext.getInstance());
		return xpath.evaluate("/jhove:jhove/jhove:repInfo/jhove:mimeType", jhoveInput);
	}
	
	/**
	 * Gets a transformer for the specified file.
	 * 
	 * @param filename Path to the file to be analyzed.
	 * @return A JhoveTransformer suitable for dealing with the file.
	 * @throws Exception
	 */
	
	public static JhoveTransformer getJhoveTransformer (String filename) 
		throws Exception
	{
		JhoveRawGenerator generator = new JhoveRawGenerator();
		Node jhoveInput = generator.processFileToXML(filename);
		String mimetype = determineMIMEType(jhoveInput);
		JhoveTransformer transformer = (JhoveTransformer) (getTransformerClass(mimetype)).newInstance();
		transformer.setRawJhoveData(jhoveInput);
		return transformer;
	}
	
	/**
	 * This has to set up a map between all MIME types
	 * Jhove might return and the class of transformer
	 * that should handle them.
	 * 
	 */
	
	private static void initializeDispatchTable() {
		dispatchTable = new HashMap<String,Class>();
		dispatchTable.put("text/xml",XMLTransformer.class);
		dispatchTable.put("text/html", HTMLTransformer.class);
		dispatchTable.put("text/plain; charset=UTF-8",UTFTransformer.class);
		dispatchTable.put("text/plain; charset=US-ASCII", ASCIITransformer.class);
		dispatchTable.put("image/gif", GIFTransformer.class);
		dispatchTable.put("image/jpeg", JPGTransformer.class);
		dispatchTable.put("image/jp2", JPGTransformer.class);
		dispatchTable.put("image/jpx", JPGTransformer.class);
		dispatchTable.put("image/tiff", TIFFTransformer.class);
		dispatchTable.put("image/tiff-fx", TIFFTransformer.class);
		dispatchTable.put("image/iex", TIFFTransformer.class);
		dispatchTable.put("audio/x-aiff", AIFFTransformer.class);
		dispatchTable.put("audio/x-wave", WAVTransformer.class);
		dispatchTable.put("application/pdf", PDFTransformer.class);
		dispatchTable.put("application/octet-stream", BytestreamTransformer.class);
	}
	
	/**
	 * 
	 * 
	 * This could be cast more generally as a map between 
	 * MIME types and "handlers" in order to expand beyond
	 * Jhove.
	 * 
	 * As is, it tries to match the MIME type exactly first,
	 * and if that fails, tries to match part of it (e.g.
	 * "text/plain; charset=XXXX" will try to match against
	 * text/plain if the whole string fails.  This behavior
	 * could be extended as well.
	 * 
	 * @param mimetype	A MIME type
	 * @return	A JhoveTransformer subclass that can handle the
	 * MIME type
	 * 
	 */
	
	protected static Class getTransformerClass (String mimetype) {
		if (dispatchTable == null) {
			initializeDispatchTable();
		}
		if (dispatchTable.containsKey(mimetype)) {
			return dispatchTable.get(mimetype);
		}
		String mimebase = mimetype.substring(0,mimetype.indexOf(";"));
		if (dispatchTable.containsKey(mimebase)) {
			return dispatchTable.get(mimebase);
		}
		return null;
	}
}