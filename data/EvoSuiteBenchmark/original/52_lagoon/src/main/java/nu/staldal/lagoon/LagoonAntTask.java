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

package nu.staldal.lagoon;

import java.io.*;
import java.util.Properties;

import org.xml.sax.SAXException;
import org.apache.tools.ant.*;

import nu.staldal.xtree.*;

import nu.staldal.lagoon.core.*;


/**
 * A Ant Task interface to LagoonProcessor
 *
 * @see nu.staldal.lagoon.core.LagoonProcessor
 */
public class LagoonAntTask extends Task
{
	private static boolean DEBUG = false;	
	
    private Properties properties;
	
	private boolean force;
	private File sitemapFile;
	private File sourceDir;
	private String targetURL;
	private String password;
	private File propertyFile;

	
	public LagoonAntTask()
	{
		// nothing to do	
	}


	public void init()
    	throws BuildException
	{
		// nothing to do	
	}

	
	// Attribute setter methods
	
	public void setForce(boolean force)
	{
		this.force = force;
	}
	
	public void setSitemapFile(File sitemapFile)
	{
		this.sitemapFile = sitemapFile;
	}
	
	public void setSourceDir(File sourceDir)
	{
		this.sourceDir = sourceDir;
	}
	
	public void setTargetURL(String targetURL)
	{
		this.targetURL = targetURL;
	}
	
	public void setPassword(String password)
	{
		this.password = password;	
	}
	
	public void setPropertyFile(File propertyFile)
	{
		this.propertyFile = propertyFile;	
	}

		
	public void execute() throws BuildException
	{
        LagoonProcessor processor;
        try {
			if (propertyFile != null)			
			{
				properties = new Properties();
				FileInputStream fis = new FileInputStream(propertyFile);
				properties.load(fis);
				fis.close();
	
				targetURL = getProperty("targetURL");
				sitemapFile = new File(getProperty("sitemapFile"));
				sourceDir = new File(getProperty("sourceDir"));			
                password = properties.getProperty("password");
			}
			else
			{
				if ((targetURL == null) || (sitemapFile == null) || (sourceDir == null))
					throw new BuildException("mandatory attribute missing");
			}
			
			Element sitemapTree;
			try {
				sitemapTree = TreeBuilder.parseXML(
					TreeBuilder.fileToInputSource(sitemapFile), false);
			}
			catch (SAXException e)
			{
				Exception ee = e.getException();
				if (ee == null)
				{
					e.printStackTrace();
					throw new LagoonException(e.getMessage());
				}
				else if (ee instanceof java.io.IOException)
				{
					throw (java.io.IOException)ee;
				}
				else
				{
					ee.printStackTrace();
					throw new LagoonException(ee.getMessage());
				}
			}				

            processor = 
				new LagoonProcessor(
					targetURL, sitemapTree, sourceDir, password,
					new PrintWriter(System.out, true), 
					new PrintWriter(System.err, true));
        }
        catch (AuthenticationMissingException e)
        {
            throw new BuildException("Password is required but not specified");
        }
        catch (AuthenticationException e)
        {
            throw new BuildException("Incorrect password");
        }
        catch (FileNotFoundException e)
        {
            throw new BuildException(e);
        }
        catch (IOException e)
        {
            throw new BuildException(e);
        }
        catch (LagoonException e)
        {
            throw new BuildException(e.getMessage());
        }

        try {
            if (!processor.build(force)) throw new BuildException();
            processor.destroy();
        }
        catch (FileNotFoundException e)
        {
            throw new BuildException(e);
        }
        catch (IOException e)
        {
			if (DEBUG) e.printStackTrace();
            throw new BuildException(e);
        }
	}

    private String getProperty(String name)
        throws LagoonException
    {
        String value = properties.getProperty(name);
        if (value == null)
            throw new LagoonException("Property " + name + " not specified");

        return value.trim();
    }
}

