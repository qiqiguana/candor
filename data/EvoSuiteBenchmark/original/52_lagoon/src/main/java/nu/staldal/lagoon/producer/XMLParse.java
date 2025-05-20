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

package nu.staldal.lagoon.producer;

import java.io.*;

import javax.xml.parsers.*;
import org.xml.sax.*;

import nu.staldal.lagoon.core.*;

public class XMLParse extends Parse implements Runnable
{
	private Thread thread;
	private Exception exception;
	private MyInputStream mis;
	private MyOutputStream mos;
	private Target target;
	private SAXParserFactory spf;	
	
    public void init() throws LagoonException
    {
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

		target = null;
    }

    public void start(ContentHandler sax, Target target)
        throws IOException, SAXException
    {
		this.target = target;
        
		XMLReader parser;	
		try {
			parser = spf.newSAXParser().getXMLReader();
		}
		catch (ParserConfigurationException e)
		{
			throw new LagoonException(e.getMessage());
		}		

        parser.setContentHandler(sax);
		parser.setEntityResolver(new EntityResolver() {
			public InputSource resolveEntity(String publicId,
											 String systemId)
								throws SAXException,
										IOException
			{
				InputSource is = new InputSource(
					getSourceMan().getFileURL(systemId));
				
				File fil = getSourceMan().getFile(systemId);
				
				if (fil != null)
				{
					InputStream istr = new FileInputStream(fil);				
					is.setByteStream(istr);	
				}
				
				return is;
			}
		});
		
		

		exception = null;

		mis = new MyInputStream();
		mos = new MyOutputStream(mis);

		thread = new Thread(this);
		thread.start();

		parser.parse(new InputSource(mis));
        mis.close();

		try {
			thread.join(1000);
		}
		catch (InterruptedException e)
		{}

		if (thread.isAlive())
		{
			thread.interrupt();
		}

		this.target = null;

		if (exception != null)
		{
			if (exception instanceof SAXException)
			{
				throw (SAXException)exception;
			}
			else if (exception instanceof IOException)
			{
				throw (IOException)exception;
			}
		}
	}

	/**
	 * Thread run method.
	 */
	public void run()
	{
		try {
			getNext().start(mos, target);
			mos.close();
		}
		catch (SAXException e)
		{
			exception = e;
		}
		// catch (InterruptedIOException e)
		// {
		// }
		catch (IOException e)
		{
			exception = e;
		}
	}

    public boolean hasBeenUpdated(long when)
        throws LagoonException, IOException
    {
        return getNext().hasBeenUpdated(when);
    }

}

class MyInputStream extends InputStream
{
	private int buf;
	private boolean eof;
    private boolean closed;

	MyInputStream()
	{
		eof = false;
        closed = false;
		buf = -1;
	}

	public int avaliable()
	{
		if (eof || (buf == -1))
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}

	public synchronized int read() throws IOException
	{
		if (closed) throw new IOException("Attempt to read from closed stream");
		if (eof) return -1;

		while (buf == -1)
		{
			try {
				wait(1000);
			}
			catch (InterruptedException e)
			{
				throw new InterruptedIOException();
			}
			if (eof) return -1;
		}

		int b = buf;
		buf = -1;
		notify();
		return b;
	}

    public void close()
    {
        closed = true;
    }

	synchronized void deliver(int b) throws IOException
	{
		if (eof) return;
        if (closed) throw new IOException("Attempt to write to broken pipe");

		while (buf != -1)
		{
			try {
				wait(1000);
			}
			catch (InterruptedException e)
			{
				throw new InterruptedIOException();
			}
			if (eof) return;
            if (closed)
                throw new IOException("Attempt to write to broken pipe");
		}

		buf = b;
		notify();
	}

	void eof()
	{
		eof = true;
	}
}

class MyOutputStream extends OutputStream
{
	private MyInputStream sink;
	private boolean eof;

	MyOutputStream(MyInputStream sink)
	{
		this.sink = sink;
		eof = false;
	}

	public void write(int b) throws IOException
	{
		if (eof) throw new IOException("Attempt to write to closed stream");
		sink.deliver(b);
	}

	// public void flush() {}

	public void close()
	{
		if (eof) return;
		// flush();
		eof = true;
		sink.eof();
	}
}
