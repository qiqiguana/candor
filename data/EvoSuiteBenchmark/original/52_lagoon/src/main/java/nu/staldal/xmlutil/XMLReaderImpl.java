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


/**
 * Helper class to implement the SAX2 interface XMLReader.
 * Useful to send a SAX2 stream into a TrAX processor.
 */
public abstract class XMLReaderImpl implements XMLReader
{
	protected EntityResolver resolver;
    protected ContentHandler contentHandler;
	protected DTDHandler dtdHandler;
	protected ErrorHandler errorHandler;
    
    public void setEntityResolver(EntityResolver resolver)
	{
		this.resolver = resolver;
	}

    public EntityResolver getEntityResolver()
	{
		return resolver;
	}

    public void setContentHandler(ContentHandler contentHandler)
    {
        this.contentHandler = contentHandler;
    }
    
    public ContentHandler getContentHandler()
    {
        return contentHandler;
    }
	
    public void setDTDHandler(DTDHandler dtdHandler)
	{
		this.dtdHandler = dtdHandler;
	}

    public DTDHandler getDTDHandler()
	{
		return dtdHandler;
	}

    public void setErrorHandler(ErrorHandler errorHandler)
	{
		this.errorHandler = errorHandler;
	}

    public ErrorHandler getErrorHandler()
	{
		return errorHandler;
	}


	public void parse(String systemId)
    	throws java.io.IOException, SAXException
	{
		parse(new InputSource(systemId));
	}
 
	
    public boolean getFeature(String name)
        throws SAXNotRecognizedException, SAXNotSupportedException
	{
		throw new SAXNotRecognizedException(name);
	}

    public void setFeature (String name, boolean value)
		throws SAXNotRecognizedException, SAXNotSupportedException
	{
		throw new SAXNotRecognizedException(name);
	}

    public Object getProperty (String name)
		throws SAXNotRecognizedException, SAXNotSupportedException
	{
		throw new SAXNotRecognizedException(name);
	}
		

    public void setProperty (String name, Object value)
		throws SAXNotRecognizedException, SAXNotSupportedException
	{
		throw new SAXNotRecognizedException(name);
	}
}
