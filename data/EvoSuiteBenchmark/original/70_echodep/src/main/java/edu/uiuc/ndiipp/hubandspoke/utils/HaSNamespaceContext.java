/**
 * HaSNamespaceContext.java
 * 
 * $Revision: 852 $
 * 
 * $Date: 2009-04-27 17:57:17 +0100 (Mon, 27 Apr 2009) $
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

package edu.uiuc.ndiipp.hubandspoke.utils;

import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

/**
 * @author Bill Ingram
 *
 */
public class HaSNamespaceContext implements NamespaceContext {

	/* (non-Javadoc)
	 * @see javax.xml.namespace.NamespaceContext#getNamespaceURI(java.lang.String)
	 */
	@Override
	public String getNamespaceURI(String prefix) {
		if(prefix.equals("mets"))
			return "http://www.loc.gov/METS/";
		else if (prefix.equals("xlink"))
			return "http://www.w3.org/1999/xlink";
		else if (prefix.equals("mods"))
			return "http://www.loc.gov/mods/v3";
		else if (prefix.equals("premis"))
			return "http://www.loc.gov/standards/premis/v1";
		else if (prefix.equals("fedora"))
			return "http://www.fedora.info/definitions/1/0/types/";
		else if (prefix.equals("xml"))
			return "http://www.w3.org/XML/1998/namespace";
		else if (prefix.equals("rel"))
			return "info:fedora/fedora-system:def/relations-external#";
		else 
			return XMLConstants.NULL_NS_URI;
	}

	/* (non-Javadoc)
	 * @see javax.xml.namespace.NamespaceContext#getPrefix(java.lang.String)
	 */
	@Override
	public String getPrefix(String namespaceURI) {
		if(namespaceURI.equals("http://www.loc.gov/METS/"))
			return "mets";
		else if(namespaceURI.equals("http://www.w3.org/1999/xlink"))
			return "xlink";
		else if(namespaceURI.equals("http://www.loc.gov/mods/v3"))
			return "mods";
		else if(namespaceURI.equals("http://www.loc.gov/standards/premis/v1"))
			return "premis";
		else if(namespaceURI.equals("http://www.fedora.info/definitions/1/0/types/"))
			return "fedora";
		else if(namespaceURI.equals("http://www.w3.org/XML/1998/namespace"))
			return "xml";
		else if(namespaceURI.equals("info:fedora/fedora-system:def/relations-external#"))
			return "rel";
		else
			return null;
	}

	/* (non-Javadoc)
	 * @see javax.xml.namespace.NamespaceContext#getPrefixes(java.lang.String)
	 */
	@Override
	public Iterator getPrefixes(String namespaceURI) {
		// TODO Auto-generated method stub
		return null;
	}

}
