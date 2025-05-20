/*
 * Copyright (c) 2001-2004, Mikael Ståldal
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

import java.util.*;

import org.xml.sax.*;

import nu.staldal.xtree.*;


/**
 * Contains the information needed to (re)build a website.
 *
 * Initialized with a sitemap description file.
 * Can then be used to (re)build the website several times.
 */
class Sitemap
{
    // Associations
    private Hashtable entries;
	private Vector entryVector;
    private Hashtable parts;
	private Hashtable outputs;
	private Hashtable projectProperties;

    // Attributes
    private LagoonProcessor processor;
    private java.io.File sourceDir;
	private String siteName;

    // Work attributes
    private String currentTargetName;
    private SourceManagerProvider currentFile;
	private int depth;
	private Producer bottomProducer;
	private Element sitemapTree;
	
	
    /**
     * The constructor
     *
     * @param processor  the processor
     * @param input  XTree representation of the sitemap
     * @param sourceDir  where the source files are
	 */
	public Sitemap(LagoonProcessor processor,
                   Element sitemapTree,
                   java.io.File sourceDir)
        throws LagoonException
	{
        if (!sitemapTree.getLocalName().equals("sitemap"))
		{
			throw new LagoonException("root element must be <sitemap>");	
		}
						
		siteName = sitemapTree.getAttrValueOrNull("name");

        this.processor = processor;
        this.sourceDir = sourceDir;
		this.sitemapTree = sitemapTree;
		
        entries = new Hashtable();
		entryVector = new Vector();
		parts = new Hashtable();
		outputs = new Hashtable();
		projectProperties = new Hashtable();
	}
	
	public void init()
        throws LagoonException, java.io.IOException
	{
        currentFile = null;
	
		for (int i = 0; i<sitemapTree.numberOfChildren(); i++)
		{
			Node node = sitemapTree.getChild(i);
			if (!(node instanceof Element)) continue;
			Element entry = (Element)node;
				
			if (entry.getLocalName().equals("file"))
			{
				currentTargetName = entry.getAttrValueOrNull("target");
				if (currentTargetName == null
						|| currentTargetName.length() < 1
						|| currentTargetName.charAt(0) != '/')
				{
					throw new LagoonException(
						"invalid target specification: " + currentTargetName);
				}
				
				String theSource = entry.getAttrValueOrNull("source");
				if (theSource == null || theSource.length() < 1)
					theSource = currentTargetName;
					
				currentFile = new FileEntry(processor, this, 
											currentTargetName, theSource,
											sourceDir,
											processor.getTempDir());

				String useOutput = entry.getAttrValueOrNull("output");	
				
				if (entry.numberOfChildren() == 0)
				{
					if (useOutput == null)
						entry.addChild(new Element("", "read", 0, 0));
					else
						entry.addChild(new Element("", "source", 0, 0));
				}
											
				depth = 0;
				Object o = handleProducer(entry);
							
				if (o instanceof ByteStreamProducer && (useOutput == null))
				{
					((FileEntry)currentFile).setMyProducer((Producer)o);
				}
				else if (o instanceof XMLStreamProducer && (useOutput != null))
				{
					((FileEntry)currentFile).setMyProducer((Producer)o);

					OutputEntry theOutput = 
						(OutputEntry)outputs.get(useOutput);
					if (theOutput == null)
						throw new LagoonException(
							"Output not found: " + useOutput);
											
					((FileEntry)currentFile).setMyOutput(theOutput);
				}
				else
				{
					throw new LagoonException(
						"Inconsistent producer chain: " + currentTargetName);
				}

	            entries.put(currentTargetName, currentFile);
				entryVector.addElement(currentFile);
			}
			else if (entry.getLocalName().equals("part"))
			{
				currentTargetName = entry.getAttrValueOrNull("name");
				if (currentTargetName == null
						|| currentTargetName.length() < 1)
				{
					throw new LagoonException("part name missing");
				}
				
				currentFile = new PartEntry(processor, this, 
					entry.getAttrValueOrNull("source"), sourceDir);
				
				depth = 0;
				Object o = handleProducer(entry);
				
				if (o instanceof XMLStreamProducer)
				{
					((PartEntry)currentFile).setMyProducer((XMLStreamProducer)o);
				}
				else
				{
					throw new LagoonException(
						"Part must contain a XML stream producer: " 
						+ currentTargetName);
				}
														 				
	            parts.put(currentTargetName, currentFile);
			}
			else if (entry.getLocalName().equals("delete"))
			{
				currentTargetName = entry.getAttrValueOrNull("target");
				if (currentTargetName == null
						|| currentTargetName.length() < 1
						|| currentTargetName.charAt(0) != '/')
				{
					throw new LagoonException(
						"invalid target specification: " + currentTargetName);
				}
				
				DeleteEntry currentEnt = new DeleteEntry(processor, 
														 currentTargetName);
				
	            entries.put(currentTargetName, currentEnt);
				entryVector.addElement(currentEnt);
			}
			else if (entry.getLocalName().equals("output"))
			{
				currentTargetName = entry.getAttrValueOrNull("name");
				if (currentTargetName == null
						|| currentTargetName.length() < 1)
				{
					throw new LagoonException("output name missing");
				}
				
				currentFile = new OutputEntry();
											
				depth = 0;
				Object o = handleProducer(entry);
				
				if (bottomProducer instanceof XMLStreamConsumer)
				{
					((OutputEntry)currentFile).setBottomProducer(
						(XMLStreamConsumer)bottomProducer);
				}
				else
				{
					throw new LagoonException(
						"Output must contain a byte stream producer: " 
						+ currentTargetName);
				}
				
				if (o instanceof ByteStreamProducer)
				{
					((OutputEntry)currentFile).setMyProducer(
						(ByteStreamProducer)o);
				}
				else
				{
					throw new LagoonException(
						"Inconsistent producer chain: " + currentTargetName);
				}

	            outputs.put(currentTargetName, currentFile);
			}
			else if (entry.getLocalName().equals("property"))
			{
				String propName = entry.getAttrValueOrNull("name");
				if (propName == null
						|| propName.length() < 1)
				{
					throw new LagoonException("property name missing");
				}
				
	            String propValue = entry.getTextContentOrNull();
				
				if (propValue != null)
					projectProperties.put(propName, propValue);
			}
			else
			{
				throw new LagoonException(
					"Unknown entry in sitemap: " + entry.getLocalName());	
			}
	        currentTargetName = null;
	        currentFile = null;
		}
		
		sitemapTree = null;
    }


	public void destroy()
        throws java.io.IOException
    {
        for (Enumeration e = getEntries(); e.hasMoreElements(); )
        {
            SitemapEntry ent = (SitemapEntry)e.nextElement();
            ent.destroy();    
        }
    }
    
    
    /**
     * Get the site name. Or <code>null</code> if no name is defined.
     */
    public String getSiteName()
    {
        return siteName;
    }

	
    /**
     * Get an Enumeration of all targets in this sitemap.
     */
    public Enumeration getTargets()
    {
        return entries.keys();
    }


    /**
     * Get an Enumeration of all entries in this sitemap.
     */
    public Enumeration getEntries()
    {
        return entryVector.elements();
    }


    /**
     * Lookup a specific entry in the sitemap.
     *
     * @param target  the target, a pseudo-absolute URL (starting with '/').
     *
     * @returns the entry for the specified target,
     *  or <code>null</code> if not found.
     */
    public SitemapEntry lookupEntry(String target)
    {
        return (SitemapEntry)entries.get(target);
    }


    /**
     * Lookup a specific part in the sitemap.
     *
     * @param name  the name of the part to obtain.
     *
     * @returns the part entry with the specified name,
     *  or <code>null</code> if not found.
     */
    public PartEntry lookupPart(String name)
    {
        return (PartEntry)parts.get(name);
    }


    /**
     * Lookup a specific output entry in the sitemap.
     *
     * @param name  the name of the output to obtain.
     *
     * @returns the output entry with the specified name,
     *  or <code>null</code> if not found.
     */
    OutputEntry lookupOutput(String name)
    {
        return (OutputEntry)outputs.get(name);
    }

	
	/**
	 * Return the value of a project property in the Sitemap.
	 *
	 * @param key  the property name
	 *
	 * @return the property value, or <code>null</code> if the property is 
	 * 	not defined.
	 */
	String getProperty(String key)
	{
		return (String)projectProperties.get(key);	
	}
	
	
	private Object handleProducer(Element parentEl)
		throws LagoonException, java.io.IOException
    {
		Element el = parentEl.getFirstChildElementOrNull();
		if (el == null)
			return parentEl.getTextContentOrNull();
		
		if (el.getLocalName().equals("format") 
				|| el.getLocalName().equals("transform") 
				|| el.getLocalName().equals("source") 
				|| el.getLocalName().equals("read") 
				|| el.getLocalName().equals("parse") 
				|| el.getLocalName().equals("process"))
		{
			String type = el.getAttrValueOrNull("type");
			if (type == null) type = "";

			String prodName = el.getLocalName() + '-' +
								((type.length() == 0) 
								? "(default)" 
								: type);
			Producer prod = processor.createProducer(el.getLocalName(), type);
			if (prod == null)
				throw new LagoonException(
					"Producer " + prodName + " not found");

			prod.setEntryName(currentTargetName);
			prod.setProcessor(processor);
			prod.setSourceManager(currentFile);
			prod.setPosition(depth);

			for (int i = 0; i < el.numberOfAttributes(); i++)
			{
				if ((el.getAttributeNamespaceURI(i).length() > 0)
						|| el.getAttributeLocalName(i).equals("type"))
					continue;

				prod.addParam(
					el.getAttributeLocalName(i), 
					el.getAttributeValue(i));
			}

			depth++;
			Object o = handleProducer(el);
			if (o == null)
			{
				bottomProducer = prod;
			}
			else if (o instanceof String)
			{
				bottomProducer = prod;

				String nameParam = ((String)o).trim();
				if (nameParam.length() > 0)
					prod.addParam("name", nameParam);
			}
			else if (o instanceof Producer)
			{
				try {
					Producer nextProd = (Producer)o;

					if (prod instanceof ByteStreamConsumer)
					{
						((ByteStreamConsumer)prod).
							setNext((ByteStreamProducer)nextProd);
					}
					else if (prod instanceof XMLStreamConsumer)
					{
						((XMLStreamConsumer)prod).
							setNext((XMLStreamProducer)nextProd);
					}
					else
					{
						throw new ClassCastException();
					}
				}
				catch (ClassCastException e)
				{
					throw new LagoonException(prodName 
						+ ": Inconsistent Producer chain");
				}
			}

			try {
				prod.init();
			}
			catch (LagoonException e)
			{
				throw new LagoonException(prodName + ": " + e.getMessage());
			}

			return prod;
		}
		else
		{
			throw new LagoonException(
				"Error in Sitemap, unexpected element: " + el.getLocalName());
		}
    }

}

