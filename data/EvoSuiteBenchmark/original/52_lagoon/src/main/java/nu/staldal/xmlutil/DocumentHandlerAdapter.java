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

import java.util.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;


/**
 * An adapter to convert from SAX2 ContentHandler to SAX1 DocumentHandler. 
 */
public class DocumentHandlerAdapter implements ContentHandler
{
	private static final boolean DEBUG = false;

    private DocumentHandler dh;
    private NamespaceSupport nsSup;
    private boolean contextPushed;


	/**
	 * Constructs an adapter.
	 *
	 * @param dh  the SAX1 DocumentHandler to fire events on.
	 */
    public DocumentHandlerAdapter(DocumentHandler dh)
    {
        this.dh = dh;
        nsSup = new NamespaceSupport();
        contextPushed = false;
    }


    // ContentHandler implementation

    public void setDocumentLocator(Locator locator)
    {
        dh.setDocumentLocator(locator);
    }

    public void startDocument()
        throws SAXException
    {
		if (DEBUG) System.out.println("startDocument");
        dh.startDocument();
    }

    public void endDocument()
        throws SAXException
    {
		if (DEBUG) System.out.println("endDocument");
        dh.endDocument();
    }

    public void startElement(String namespaceURI, String localName,
                             String qname, Attributes atts)
        throws SAXException
    {
		if (DEBUG) System.out.println("startElement("+namespaceURI+
            ','+localName+','+qname+')');

        if (!contextPushed)
        {
            nsSup.pushContext();
        }
        contextPushed = false;

        String name;
        if (qname != null && qname.length() > 0)
            name = qname;
        else
        {
            String prefix = nsSup.getPrefix(namespaceURI);
            if (prefix == null)
            {
				if ((namespaceURI == null) || (namespaceURI.length() < 1))
				{
					prefix = "";
				}
				else
				{
					String defaultURI = nsSup.getURI("");
	                if ((defaultURI != null) && defaultURI.equals(namespaceURI))
	                    prefix = ""; // default namespace
	                else
	                    throw new Error("no prefix for \'" + namespaceURI +
	                    	'\'');
			 	}
            }
            name = ((prefix.length() == 0) ? "" : (prefix + ':')) + localName;
        }

        AttributeListImpl al = new AttributeListImpl();
        for (int i = 0; i < atts.getLength(); i++)
        {
            String aname = atts.getQName(i);
            if ((aname == null) || (aname.length() == 0))
            {
                String uri = atts.getURI(i);
                String alocalName = atts.getLocalName(i);
                if (uri.length() == 0)
                {
                    aname = alocalName;
                }
                else
                {
                    String prefix = nsSup.getPrefix(uri);
                    if (prefix == null)
                        throw new Error("no attribute prefix for \'"
                            + uri + '\'');
                    aname = prefix + ':' + alocalName;
                }
            }
            al.addAttribute(aname, atts.getType(i), atts.getValue(i));
        }

        for (Enumeration e = nsSup.getDeclaredPrefixes(); e.hasMoreElements(); )
        {
            String prefix = (String)e.nextElement();
            String uri = nsSup.getURI(prefix);
            if (prefix.length() == 0)
            {
                al.addAttribute("xmlns", "CDATA", uri);
            }
            else
            {
                al.addAttribute("xmlns:"+prefix, "CDATA", uri);
            }
        }

        dh.startElement(name, al);
    }

    public void endElement(String namespaceURI, String localName,
                           String qname)
        throws SAXException
    {
		if (DEBUG) System.out.println("endElement("+namespaceURI+','+
            localName+','+qname+')');

        String name;
        if (qname != null && qname.length() > 0)
            name = qname;
        else
        {
            String prefix = nsSup.getPrefix(namespaceURI);
            if (prefix == null)
            {
				if ((namespaceURI == null) || (namespaceURI.length() < 1))
				{
					prefix = "";
				}
				else
				{
					String defaultURI = nsSup.getURI("");
	                if ((defaultURI != null) && defaultURI.equals(namespaceURI))
	                    prefix = ""; // default namespace
	                else
	                    throw new Error("no prefix for \'" + namespaceURI +
	                    	'\'');
			 	}
            }
            name = ((prefix.length() == 0) ? "" : (prefix + ':')) + localName;
        }

        dh.endElement(name);

        nsSup.popContext();
    }

    public void startPrefixMapping(String prefix, String uri)
        throws SAXException
    {
		if (DEBUG) System.out.println("startPrefixMapping("+
			((prefix.length() == 0) ? "<default>" : prefix) +','+uri+')');

        if (!contextPushed)
        {
            nsSup.pushContext();
            contextPushed = true;
        }

        nsSup.declarePrefix(prefix,uri);
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
        dh.characters(ch, start, length);
    }

    public void ignorableWhitespace(char ch[], int start, int length)
        throws SAXException
    {
        dh.ignorableWhitespace(ch, start, length);
    }

    public void processingInstruction(String target, String data)
        throws SAXException
    {
		if (DEBUG) System.out.println("processingInstruction("+target+','+
			data+')');

        dh.processingInstruction(target, data);
    }

    public void skippedEntity(String name)
        throws SAXException
    {
		if (DEBUG) System.out.println("skippedEntity("+name+')');

        // nothing to do
    }

}
