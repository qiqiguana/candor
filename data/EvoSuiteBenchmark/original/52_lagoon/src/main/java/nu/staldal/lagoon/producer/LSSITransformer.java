/*
 * Copyright (c) 2003, Mikael Ståldal
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

import java.io.*;
import java.util.*;
import java.text.*;

import org.xml.sax.*;

import nu.staldal.lagoon.core.*;
import nu.staldal.xmlutil.*;
import nu.staldal.util.Utils;


public class LSSITransformer extends Transform
{

    public void init() throws LagoonException, IOException
    {
        // nothing to do
    }

    
    public void start(org.xml.sax.ContentHandler sax, final Target target)
    	throws IOException, SAXException
    {
        sax.startDocument();
        
        Set includedFiles = new HashSet();
        
        getNext().start(new LSSIHandler(getSourceMan(), sax, target, includedFiles), 
            target);
            
        putObjectIntoRepository("includedFiles-"+Utils.encodePath(getSourceMan().getSourceURL()), 
            includedFiles);

        sax.endDocument();
    }


    public boolean hasBeenUpdated(long when)
        throws LagoonException, IOException
    {
        Set includedFiles = (Set)getObjectFromRepository(
            "includedFiles-"+Utils.encodePath(getSourceMan().getSourceURL()));
        
        if (includedFiles == null) return true;
        
        for (Iterator it = includedFiles.iterator(); it.hasNext(); )
        {
            String file = (String)it.next();
            
            if (getSourceMan().fileHasBeenUpdated(file, when))
                return true;
        }
        
        return getNext().hasBeenUpdated(when);
    }
}

    
class LSSIHandler implements ContentHandler
{
    private static final String LSSI_NS = "http://staldal.nu/Lagoon/LSSI";

    private SourceManager sourceMan;
    private ContentHandler sax;
    private Target target;
    private Locator locator;
    private int inDirective;
    private Set includedFiles;    

    LSSIHandler(SourceManager sourceMan, ContentHandler sax, Target target,
        Set includedFiles)
    {
        this.sourceMan = sourceMan;
        this.sax = sax;
        this.target = target;
        this.locator = null;
        this.includedFiles = includedFiles;
        inDirective = 0;        
    }
    

    public void setDocumentLocator(Locator locator)
    {
        this.locator = locator;
        sax.setDocumentLocator(locator);
    }

    public void startDocument()
        throws SAXException
    {
        // ignore
    }

    public void endDocument()
        throws SAXException
    {
        // ignore
    }

    public void startElement(String namespaceURI, String localName,
            String qName, Attributes atts)
        throws SAXException
    {
        if (namespaceURI.equals(LSSI_NS))
        {
            inDirective++;
            
            if (localName.equals("include"))
            {
                String file = atts.getValue("file");
                if (file == null)
                    throw new SAXParseException(
                        "lssi:include missing parameter", locator);

                includedFiles.add(file);
                        
                try {
                    sourceMan.getFileAsSAX(file, 
                        new LSSIHandler(sourceMan, sax, target, includedFiles), 
                        target);                    
                }
                catch (FileNotFoundException e)
                {
                    throw new SAXParseException(e.getMessage(), locator);
                }
                catch (IOException e)
                {
                    throw new SAXException(e);    
                }
            }
            else if (localName.equals("date"))
            {
                String format = atts.getValue("format");                
                if (format == null) format = "yyyy-MM-dd";
                
                DateFormat df = new SimpleDateFormat(format);
                String tz = atts.getValue("tz");
                if (tz != null) df.setTimeZone(TimeZone.getTimeZone(tz));
                
                String theDate = df.format(new Date());
                sax.characters(theDate.toCharArray(), 0, theDate.length());
            }
            else if (localName.equals("lastmod"))
            {
                String format = atts.getValue("format");                
                if (format == null) format = "yyyy-MM-dd";
                
                DateFormat df = new SimpleDateFormat(format);
                String tz = atts.getValue("tz");
                if (tz != null) df.setTimeZone(TimeZone.getTimeZone(tz));
                                
                try {
                    String url = atts.getValue("file");
                    if (url == null) url = sourceMan.getSourceURL();
                    File file = sourceMan.getFile(url);
                    if (file == null)
                    {
                        throw new SAXParseException("No file to check timestamp on", locator);
                    }
                    String theDate = df.format(new Date(file.lastModified()));
                    sax.characters(theDate.toCharArray(), 0, theDate.length());
                }
                catch (FileNotFoundException e)
                {
                    throw new SAXException(e);    
                }
            }
            else if (localName.equals("root"))
            {
                // just ignore
            }
            else
            {
                throw new SAXParseException(
                    "Unknown LSSI element: " + localName, locator);     
            }
        }
        else
        {
            sax.startElement(namespaceURI, localName, qName, atts);
        }
    }

    public void endElement(String namespaceURI, String localName, 
            String qName)
        throws SAXException
    {
        if (namespaceURI.equals(LSSI_NS))
        {
            inDirective--;
        }
        else
        {
            sax.endElement(namespaceURI, localName, qName);
        }
    }

	public void startPrefixMapping(String prefix, String uri)
    	throws SAXException
    {
        sax.startPrefixMapping(prefix, uri);
	}

	public void endPrefixMapping(String prefix)
    	throws SAXException
    {
		sax.endPrefixMapping(prefix);
	}

    public void characters(char ch[], int start, int length)
        throws SAXException
    {
        sax.characters(ch, start, length);
    }

    public void ignorableWhitespace(char ch[], int start, int length)
        throws SAXException
    {
        sax.ignorableWhitespace(ch, start, length);
    }

    public void processingInstruction(String target, String data)
        throws SAXException
    {
        sax.processingInstruction(target, data);
    }

	public void skippedEntity(String name)
        throws SAXException
	{
		sax.skippedEntity(name);
	}

}

