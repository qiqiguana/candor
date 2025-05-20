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

package nu.staldal.lagoon.producer;

import nu.staldal.lagoon.core.*;
import nu.staldal.lagoon.util.*;

import java.util.*;
import java.io.*;

import org.xml.sax.*;

public class BasicSplit extends Transform implements ContentHandler, Runnable
{
    private static final boolean DEBUG = false;

    private ContentHandler sax;
    private Target target;
    private Thread myThread;
    private Thread mainThread;
    private boolean inPart;
    private String myNS;
    private String myElement;
    private String outputname;
    private Exception myException;
    private Hashtable prefixDict;

    private static void sleepUntilInterrupted()
    {
        try {
            while (true)
                Thread.sleep(1000*60); // Sleep one minute
        }
        catch (InterruptedException e) {}
    }

    public void init() throws LagoonException
    {
        this.myThread = null;

        this.myNS = getParam("namespace");
        if (this.myNS == null)
        	throw new LagoonException("parameter \'namespace\' must be set");
        this.myElement = getParam("element");
        if (this.myElement == null)
        	throw new LagoonException("parameter \'element\' must be set");
        this.outputname = getParam("outputname");
        if (this.outputname == null)
        	throw new LagoonException("parameter \'outputname\' must be set");

        this.myException = null;
        this.sax = null;
		this.target = null;

        if (DEBUG) System.out.println("BasicSplit: namespace=\'" + myNS
            + "\'  element=\'"+myElement+ "\'  outputname=\'"+outputname+"\'");
    }

    public void start(ContentHandler sax, Target target)
        throws IOException, SAXException
    {
        this.sax = sax;
		this.target = target;
        mainThread = Thread.currentThread();

        if (myThread == null)
        {
            inPart = false;
            prefixDict = new Hashtable();

            sax.startDocument();
            // Dummy document
            sax.endDocument();

            myThread = new Thread(this);
            myThread.start();
        }
        else
        {
            myThread.interrupt();
        }

        sleepUntilInterrupted();

		this.target = null;
        this.sax = null;

        if (myException != null)
        {
			if (myException instanceof SAXException)
				throw (SAXException)myException;
			else
				throw new SAXException(myException);
		}
	}

    public boolean hasBeenUpdated(long when)
        throws LagoonException, IOException
    {
        return getNext().hasBeenUpdated(when);
    }

    /**
     * Thread run method.
     */
    public void run()
    {
        try {
            getNext().start(this, target);
        }
        catch (Exception e)
        {
            myException = e;
            mainThread.interrupt();
        }
        finally
        {
            myThread = null;
        }
    }

    // SAX ContentHandler implementation

    public void setDocumentLocator(Locator locator)
    {
        // nothing to do
    }

    public void startDocument()
    {
    }

    public void endDocument()
    {
        mainThread.interrupt();
    }

    public void startElement(String namespaceURI, String localName,
        String qName, Attributes atts)
        throws SAXException
    {
        if (inPart)
            sax.startElement(namespaceURI, localName, qName, atts);
        else if (namespaceURI.equals(myNS) && localName.equals(myElement))
        {
            ((FileTarget)target).newTarget(
				instantiateAtts(outputname, atts), false);
            mainThread.interrupt();
            sleepUntilInterrupted();
            sax.startDocument();
            for (Enumeration e = prefixDict.keys(); e.hasMoreElements(); )
            {
				String prefix = (String)e.nextElement();
				String uri = (String)prefixDict.get(prefix);
				sax.startPrefixMapping(prefix, uri);
			}
            sax.startElement(namespaceURI, localName, qName, atts);
            inPart = true;
        }
    }

    public void endElement(String namespaceURI, String localName, String qName)
        throws SAXException
    {
        if (inPart) sax.endElement(namespaceURI, localName, qName);
        if (namespaceURI.equals(myNS) && localName.equals(myElement))
        {
            inPart = false;
            for (Enumeration e = prefixDict.keys(); e.hasMoreElements(); )
            {
				String prefix = (String)e.nextElement();
				sax.endPrefixMapping(prefix);
			}
            sax.endDocument();
        }
    }

	public void startPrefixMapping(String prefix, String uri)
    	throws SAXException
    {
		if (inPart)
			sax.startPrefixMapping(prefix, uri);
		else
		{
			prefixDict.put(prefix, uri);
		}
	}

	public void endPrefixMapping(String prefix)
    	throws SAXException
    {
		if (inPart)
			sax.endPrefixMapping(prefix);
		{
			prefixDict.remove(prefix);
		}
	}

    public void characters(char ch[], int start, int length)
        throws SAXException
    {
        if (inPart) sax.characters(ch, start, length);
    }

    public void ignorableWhitespace(char ch[], int start, int length)
        throws SAXException
    {
        if (inPart) sax.ignorableWhitespace(ch, start, length);
    }

    public void processingInstruction(String target, String data)
        throws SAXException
    {
        if (inPart) sax.processingInstruction(target, data);
    }

	public void skippedEntity(String name)
        throws SAXException
	{
		if (inPart) sax.skippedEntity(name);
	}


   /**
     * Instantiate a filename template to a filename.
     *
     * @param template   the filename template
     * @param atts      the Attributes
     *
     * @return  the filename
     */
    private static String instantiateAtts(String template,
        final Attributes atts)
        throws SAXException
    {
        try {
            return TemplateProcessor.processTemplate('[', ']', '\'', '\"',
                template,
                new ExpressionEvaluator() {
                    public String eval(String expr) throws SAXException
                    {
                        String value = atts.getValue("", expr);
                        if (value == null) throw new SAXException(
                            "Attribute not found: " + expr);
                        else
                            return value;
                    }
                });
        }
        catch (TemplateException e)
        {
            Exception ee = e.getException();
            if (ee != null)
                throw (SAXException)ee;
            else
                throw new SAXException(
                    "Illegal attribute template: " + e.getMessage());
        }
    }
}
