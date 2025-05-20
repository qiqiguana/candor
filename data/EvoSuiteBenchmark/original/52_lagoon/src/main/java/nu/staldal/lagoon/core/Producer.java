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

package nu.staldal.lagoon.core;

import java.io.*;
import java.util.*;

import nu.staldal.util.Utils;


/**
 * A Producer is one step in the pipeline process to build a
 * file in a website.
 */
public abstract class Producer implements ProducerInterface
{
    /**
     * Parameters to this producer.
     */
    private Hashtable params = new Hashtable();

    private String entryName;
	private SourceManagerProvider sourceMan = null;
    private LagoonProcessor processor = null;
    private int position = 0;

    
    public void destroy()
        throws java.io.IOException        
    {
        // empty default implementation 
    }
    

    public void beforeBuild()
        throws java.io.IOException        
    {
        // empty default implementation 
    }
    

    public void afterBuild()
        throws java.io.IOException        
    {
        // empty default implementation 
    }

    
    void doDestroy()
        throws java.io.IOException        
    {
        destroy();
    }
    

    void doBeforeBuild()
        throws java.io.IOException        
    {
        beforeBuild();
    }


    void doAfterBuild()
        throws java.io.IOException        
    {
        afterBuild();
    }
    
    
	/**
	 * Set the name of the sitemap entry this producer is associated with.
	 * Used during initialization.
	 */
	void setEntryName(String entryName)
	{
        this.entryName = entryName;
	}

	
	/**
	 * Get the name of the sitemap entry this producer is associated with.
	 */
	public String getEntryName()
	{
        return entryName;
	}

	
	/**
	 * Set the SourceManagerProvider this producer is associated with.
	 * Used during initialization.
	 */
	void setSourceManager(SourceManagerProvider sourceMan)
	{
        this.sourceMan = sourceMan;
	}

	/**
	 * Set the position of this producer in the pipeline.
	 * Used during initialization.
	 */
	void setPosition(int pos)
	{
        this.position = pos;
	}


	/**
	 * Get the position of this producer in the pipeline.
	 */
	public int getPosition()
	{
        return position;
	}


	/**
	 * Set the LagoonProcessor this producer is associated with.
	 * Used during initialization.
	 */
	void setProcessor(LagoonProcessor processor)
	{
        this.processor = processor;
	}


	/**
	 * Get the SourceManager this producer is associated with.
	 */
	public SourceManager getSourceMan()
	{
        if (sourceMan == null)
            throw new RuntimeException("No SourceManager avaliable");
		
		SourceManager sm = sourceMan.getSourceManager(); 
        
		if (sm == null)
            throw new RuntimeException("No SourceManager avaliable");
        
		return sm;
	}

	
	/**
	 * Get the LagoonContext this producer is associated with.
	 */
	public LagoonContext getContext()
	{
		return processor;
	}

	
    /**
     * Add a parameter to this producer.
     * Used during initialization.
     *
     * @param name  the name of the paramter
     * @param value  the value of the parameter
     */
    void addParam(String name, String value)
    {
        params.put(name, value);
    }

	
    /**
     * Get a parameter.
     *
     * @param name  the name of the parameter to get
     * @return  the value of the requested parameter,
     *           or null if the parameter doesn't exitst
     */
    public String getParam(String name)
    {
        return (String)params.get(name);
    }

	
    /**
     * Get an Enumeration of all parameter names.
     */
    public Enumeration getParamNames()
    {
        return params.keys();
    }


    private String makeKey(String key)
    {
        return getClass().getName() + "." + position + "." + key;
    }

	
    /**
     * Read from a file in the repository.
     * Read from the returned InputStream and close() it.
     *
     * @param key  the key to locate the file
     *
     * @return an InputStream to read the file from, or <code>null</code>
     * if the file wasn't found.
     */
    public InputStream readFileFromRepository(String key)
    {
        return processor.readFileFromRepository(
            Utils.encodePath(entryName),
            makeKey(key));
    }

    /**
     * Store a file in the repository.
     * Write to the returned OutputStream and close() it.
     *
     * @param key  the key to locate the file
     *
     * @return  an OutputStream to write to the file, or <code>null</code>
	 *          if the repository is unavailable.
     */
    public OutputStream storeFileInRepository(String key)
        throws IOException
    {
        return processor.storeFileInRepository(
            Utils.encodePath(entryName),
            makeKey(key));
    }


    /**
     * Get an object from the repository.
     *
     * @param key  the key to locate the object
     *
     * @return  the object, or <code>null</code> if not found
     */
    public Object getObjectFromRepository(String key)
        throws java.io.IOException
    {
        return processor.getObjectFromRepository(
            Utils.encodePath(entryName),
            makeKey(key));
    }


    /**
     * Store an object into the repository.
     *
     * @param key  the key to locate the object
     * @param obj  the object to store, must be Serializable
	 *
	 * @return <code>true</code> if successful, 
	 *		   <code>false</code> if the repository is unavailable. 
     */
    public boolean putObjectIntoRepository(String key, Object obj)
        throws java.io.IOException
    {
        return processor.putObjectIntoRepository(
            Utils.encodePath(entryName),
            makeKey(key),
            obj);
    }
}
