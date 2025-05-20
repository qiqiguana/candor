/**
 * JhoveNamespaceContext.java
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

import java.util.Iterator;
import java.util.*;
import javax.xml.namespace.NamespaceContext;

/**
 * 
 * Sets up the needed XML namespaces to do XPath queries
 * against raw Jhove output.
 * 
 * @author Howard Ding
 */
public class JhoveNamespaceContext implements NamespaceContext {

	/**
	 * Map from namespace prefix to URI
	 */
	private Map<String,String> namespaceMap;
	
	/**
	 * Map from URI to list of corresponding NamespacePrefixes
	 */
	private Map<String,List<String>> URIMap;
	
	/**
	 * Holds singleton instance.
	 */
	private static JhoveNamespaceContext instance;
	
	/**
	 * Constructor
	 *
	 */
	private JhoveNamespaceContext () {
		super();
		this.initialize();
		instance = this;
	}
	
	/**
	 * Get the singleton instance.
	 * 
	 * @return JhoveNamespaceContext instance
	 */
	public static JhoveNamespaceContext getInstance () {
		if (instance == null) 
			instance = new JhoveNamespaceContext();
		return instance;
	}
	
	/**
	 * Set up the maps between namespaces and URIs.
	 *
	 */
	protected void initialize () {
		namespaceMap = new HashMap<String, String>();
		namespaceMap.put("jhove", "http://hul.harvard.edu/ois/xml/ns/jhove");
		
		//If this were to get more complicated, this
		//can be constructed directly from the namespaceMap
		URIMap = new HashMap<String,List<String>>();		
		List<String> l = new LinkedList<String>();
		l.add("jhove");
		URIMap.put("http://hul.harvard.edu/ois/xml/ns/jhove", l);
	}
	
	/**
	 * Return the URI corresponding to a prefix.
	 * 
	 * @param prefix An XML namespace prefix
	 * @return String representing a URI
	 */
	public String getNamespaceURI(String prefix) {
		return namespaceMap.get(prefix);
	}

	/**
	 * Return a prefix corresponding to a URI
	 * 
	 * @param namespaceURI A URI
	 * @return String A namespace prefix corresponding to the URI
	 */
	public String getPrefix(String namespaceURI) {
		List<String> l = URIMap.get(namespaceURI);
		return (l == null) ? null : l.get(0);
		
	}

	/**
	 * Return an iterator over all prefixes to a URI
	 * 
	 * @param namespaceURI A URI
	 * @return An iterator over XML prefixes corresponding to URI
	 */
	public Iterator getPrefixes(String namespaceURI) {
		List l = URIMap.get(namespaceURI);
		return (l == null) ? null : l.iterator();
	}

}
