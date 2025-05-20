/*
 * Copyright (c) 2001-2003, Mikael Ståldal
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

package nu.staldal.lagoon.core;

import java.io.*;
import java.util.*;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.*;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.sax.SAXSource;
import org.xml.sax.*;

import nu.staldal.xmlutil.*;
import nu.staldal.lagoon.util.*;
import nu.staldal.util.Utils;


/**
 *
 */
abstract class EntryWithSource implements SourceManager, SourceManagerProvider
{
    private static final boolean DEBUG = false;

	protected final LagoonProcessor processor;
	protected final Sitemap sitemap;
    protected final File sourceRootDir;

    protected final String sourceURL;

	protected final SAXParserFactory spf;	
	

    /**
     * Constructor.
     *
     * @param processor the LagoonProcessor.
     * @param sitemap  the Sitemap.
     * @param sourceURL  the file to use, may contain wildcard in filename,
	 *                   must absolute or pseudo-absolute, 
	 *					 may be <code>null</code>.
     * @param sourceRootDir  absolute path to the source directory
     */
    public EntryWithSource(LagoonProcessor processor, Sitemap sitemap, 
		String sourceURL, File sourceRootDir)
        throws LagoonException
    {
		this.processor = processor;
		this.sitemap = sitemap;

        this.sourceRootDir = sourceRootDir;
		
		if (!Utils.absoluteURL(sourceURL) 
				&& !Utils.pseudoAbsoluteURL(sourceURL))
		{
        	throw new LagoonException(
				"source must be absolute or pseudo-absolute");
		}

		this.sourceURL = sourceURL;

		try {
			spf = SAXParserFactory.newInstance();
			spf.setNamespaceAware(true);
			spf.setValidating(false);
			spf.setFeature("http://xml.org/sax/features/namespaces", true);
			spf.setFeature("http://xml.org/sax/features/namespace-prefixes", false);
			spf.setFeature("http://xml.org/sax/features/validation", false);
		}
		catch (ParserConfigurationException e)
		{
			throw new Error("Unable to configure XML parser");	
		}
		catch (SAXException e)
		{
			throw new Error("Unable to configure XML parser");	
		}
    }


	// SourceManager implemenation
    
    public String getSourceURL()
        throws FileNotFoundException
	{
        if (sourceURL == null)
            throw new FileNotFoundException("no source file specified");

		return sourceURL;		
	}
	
	
    public InputStream openFile(String url)
        throws FileNotFoundException, IOException
    {
		File file = getFile(url);
		
		if (file == null)
		{
			if (url.startsWith("res:"))
			{
				return getClass().getResourceAsStream(url.substring(4));
			}
			else
			{
				URL theUrl = new URL(url);
				URLConnection uc = theUrl.openConnection();
				return uc.getInputStream();
			}
		}
		else
		{
			return new FileInputStream(file);
		}
    }


    public File getFile(String url)
        throws FileNotFoundException
    {
		if (Utils.absoluteURL(url))
		{
			if (url.startsWith("file:"))
			{
				return new File(url.substring(5));	
			}
			else
			{
				return null;
			}
		}
		else
		{
			return new File(sourceRootDir,
        	    getFileURL(url).substring(1).replace('/', File.separatorChar));
		}
    }

	
    public Source getFileAsJAXPSource(final String url, final Target target)
        throws FileNotFoundException
	{
		File file = getFile(url);	
		
		if (file == null)
		{
			if (Utils.absoluteURL(url) && url.startsWith("part:"))
			{
				final PartEntry pe = sitemap.lookupPart(url.substring(5));
				if (pe == null)
					throw 
						new	FileNotFoundException("Part " + url + " not found");
					
				return new SAXSource(new XMLReaderImpl() {
					public void parse(InputSource is) 
						throws SAXException, IOException
					{
						pe.getXMLProducer().start(contentHandler, target);
					}					
				}, new InputSource());
			}
			else if (Utils.absoluteURL(url) && url.startsWith("res:"))
			{
				return new StreamSource(
					getClass().getResourceAsStream(url.substring(4)));
			}
			else				
			{
				return new StreamSource(getFileURL(url));
			}
		}
		else
			return new StreamSource(file);
	}
	

	public void getFileAsSAX(String url, ContentHandler ch, Target target)
		throws IOException, SAXException
	{
		if (Utils.absoluteURL(url) && url.startsWith("part:"))
		{
			PartEntry pe = sitemap.lookupPart(url.substring(5));
			if (pe == null)
				throw new FileNotFoundException("Part " + url + " not found");

			pe.getXMLProducer().start(ch, target);
			return;				
		}
		
		InputSource is;
		InputStream istream = null;
		
		if (Utils.absoluteURL(url) && url.startsWith("res:"))
		{
			is = new InputSource(
				getClass().getResourceAsStream(url.substring(4)));
		}
		else 
		{
			is = new InputSource(getFileURL(url));
		
			File file = getFile(url);
		
			if (file != null)
			{
				istream = new FileInputStream(file);				
				is.setByteStream(istream);	
			}
		}

		try {
			XMLReader parser = spf.newSAXParser().getXMLReader(); 

			parser.setContentHandler(ch);
			parser.setEntityResolver(new EntityResolver() {
				public InputSource resolveEntity(String publicId,
                				                 String systemId)
                          			throws SAXException,
                                 			IOException
				{
					InputSource is = new InputSource(getFileURL(systemId));
					
					File fil = getFile(systemId);
					
					if (fil != null)
					{
						InputStream istr = new FileInputStream(fil);				
						is.setByteStream(istr);	
					}
					
					return is;
				}
			});

			parser.parse(is);
		}
		catch (ParserConfigurationException e)
		{
			throw new SAXException(e);
		}
		finally
		{
			if (istream != null) istream.close();
		}		
	}
	
	
    public String getFileURL(String url)
        throws FileNotFoundException
    {
		return processor.getFileURLRelativeTo(url, getSourceURL());
	}


    public boolean fileHasBeenUpdated(String url, long when)
        throws FileNotFoundException, IOException, LagoonException
    {
        File file = getFile(url);
		if (file == null)
		{
			if (Utils.absoluteURL(url) && url.startsWith("part:"))
			{
				PartEntry pe = sitemap.lookupPart(url.substring(5));
				if (pe == null)
					throw new FileNotFoundException(
						"Part " + url + " not found");

				return pe.getXMLProducer().hasBeenUpdated(when);
			}
			else if (Utils.absoluteURL(url) && url.startsWith("res:"))
			{
				return false;  // cannot check
			}
			else
				return true;  // cannot check
		}
        long sourceDate = file.lastModified();

        return ((sourceDate > 0) // source exsist
                &&
                // will also build if (when == -1) (i.e. unknown)
                (sourceDate > when));
    }

	
	// SourceManagerProvider implementation
    
	public SourceManager getSourceManager()
	{
		return this;	
	}		
}

