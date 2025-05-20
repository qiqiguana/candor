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

package nu.staldal.lagoon;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.parsers.*;
import org.xml.sax.*;


/**
 * Small application to check for errors in XML files.
 * Can be invoked as a "compiler" from an editor such as Emacs
 * to automatically point out error locations in the XML file.
 *
 * Accepts either a local file name or an URL on the command line.
 */
public class XMLCheck
{
	public static void main(String[] args) 
		throws ParserConfigurationException, SAXException
	{
		boolean validate = false;
		String input = null;

		for (int i = 0; i<args.length; i++)
		{
			if (args[i].equals("-v"))
				validate = true;
			else
				input = args[i];
		}

		if (input == null)
		{
			System.out.println("Syntax: XMLCheck [-v] <filename or URL>");
			return;
		}

		MyParser parser = new MyParser();

		if (parser.parse(input, validate))
			System.exit(0);
		else
			System.exit(1);
	}
}

class MyParser implements ErrorHandler
{
	private boolean ioError;

	MyParser() {}

	boolean parse(String input, boolean validate) 
		throws ParserConfigurationException, SAXException
	{		
		XMLReader parser = 
			SAXParserFactory.newInstance().newSAXParser().getXMLReader(); 

        if (validate)
        {
    		try {
				parser.setFeature("http://xml.org/sax/features/validation", 
					true);
		    }
    		catch (SAXException e)
	    	{
		    	System.err.println("Unable to turn on validation: " + 
			    	e.getMessage());
    		}
		}

		parser.setErrorHandler(this);

		try {
			InputSource is = new InputSource(input);

			ioError = false;

			parser.parse(is);
		}
		catch (java.io.FileNotFoundException e)
		{
			System.err.println("File not found: " + e.getMessage());
			return false;
		}
		catch (java.io.IOException e)
		{
			System.err.println(e.toString());
			return false;
		}
		catch (SAXException e)
		{
			if (!ioError) System.err.println("Document not well-formed");
			return false;
		}
		return true;
	}

	public void warning(SAXParseException e)
	{
		try {
			String name = (e.getSystemId() == null)
				? null 
				: ((e.getSystemId().startsWith("file:")) 
					? new File(new URI(e.getSystemId())).toString()
					: e.getSystemId());
					
			System.err.println(name + ":" + e.getLineNumber() + ":"
				+ e.getColumnNumber() + ": Warning: " + e.getMessage());
		}
		catch (URISyntaxException ex)
		{
			ex.printStackTrace(System.err);	
		}
	}

	public void error(SAXParseException e)
	{
		try {
			String name = (e.getSystemId() == null)
				? null 
				: ((e.getSystemId().startsWith("file:")) 
					? new File(new URI(e.getSystemId())).toString()
					: e.getSystemId());
	
			System.err.println(name + ":" + e.getLineNumber() + ":"
				+ e.getColumnNumber() + ": Error: " + e.getMessage());
		}
		catch (URISyntaxException ex)
		{
			ex.printStackTrace(System.err);	
		}
	}

	public void fatalError(SAXParseException e)
	{
		try {
			String name = (e.getSystemId() == null)
				? null 
				: ((e.getSystemId().startsWith("file:")) 
					? new File(new URI(e.getSystemId())).toString()
					: e.getSystemId());
	
			if (name == null)
			{
				System.err.println(e.getMessage());
				ioError = true;
			}
			else
			{
				System.err.println(name + ":" + e.getLineNumber() + ":"
					+ e.getColumnNumber() +  ": Fatal: " + e.getMessage());
			}
		}
		catch (URISyntaxException ex)
		{
			ex.printStackTrace(System.err);	
		}
	}
}
