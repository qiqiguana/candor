/*
 * Copyright (c) 2001, Mikael Ståldal
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

package nu.staldal.xmlutil;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.Enumeration;


/**
 * An adapter to convert from SAX1 DocumentHandler to SAX2 ContentHandler.
 */
public class ContentHandlerAdapter implements DocumentHandler
{
	private ContentHandler ch;
	private NamespaceSupport sup;


	/**
	 * Constructs an adapter.
	 *
	 * @param ch  the SAX2 ContentHandler to fire events on.
	 */
	public ContentHandlerAdapter(ContentHandler ch)
    {
		this.ch = ch;
		sup = new NamespaceSupport();
    }

	
    // DocumentHandler implementation

    public void setDocumentLocator(Locator locator)
    {
        ch.setDocumentLocator(locator);
    }

    public void startDocument()
        throws SAXException
    {
        ch.startDocument();
    }

    public void endDocument()
        throws SAXException
    {
        ch.endDocument();
    }

	public void startElement(String name, AttributeList atts)
        throws SAXException
    {
		sup.pushContext();
		for (int i = 0; i < atts.getLength(); i++)
		{
			String aName = atts.getName(i);
			if (aName.startsWith("xmlns:"))
			{
				sup.declarePrefix(aName.substring(6), atts.getValue(i));
			}
			else if (aName.equals("xmlns"))
			{
				sup.declarePrefix("", atts.getValue(i));
			}
		}

		String[] parts = new String[3];
		AttributesImpl ai = new AttributesImpl();
		for (int i = 0; i < atts.getLength(); i++)
		{
			String aName = atts.getName(i);
			if (!aName.startsWith("xmlns:") && !aName.equals("xmlns"))
			{
				parts = sup.processName(aName, parts, true);

                ai.addAttribute(parts[0], parts[1], parts[2],
                			   atts.getType(i),
                			   atts.getValue(i));
			}
		}

		for (Enumeration e = sup.getDeclaredPrefixes(); e.hasMoreElements(); )
		{
			String p = (String)e.nextElement();
			ch.startPrefixMapping(p, sup.getURI(p));
		}

		parts = sup.processName(name, parts, false);

		ch.startElement(parts[0], parts[1], parts[2], ai);
	}

	public void endElement(String name)
        throws SAXException
    {
		String[] parts = new String[3];

		parts = sup.processName(name, parts, false);

		ch.endElement(parts[0], parts[1], parts[2]);

		for (Enumeration e = sup.getDeclaredPrefixes(); e.hasMoreElements(); )
		{
			String p = (String)e.nextElement();
			ch.endPrefixMapping(p);
		}

		sup.popContext();
	}

    public void characters(char[] c, int start, int length)
        throws SAXException
    {
        ch.characters(c, start, length);
    }

    public void ignorableWhitespace(char[] c, int start, int length)
        throws SAXException
    {
        ch.ignorableWhitespace(c, start, length);
    }

    public void processingInstruction(String target, String data)
        throws SAXException
    {
        ch.processingInstruction(target, data);
    }

}
