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
import java.util.*;

import org.xml.sax.*;

import nu.staldal.lsp.*;
import nu.staldal.lsp.compiler.*;
import nu.staldal.lagoon.core.*;
import nu.staldal.util.Utils;


public class LSPTransformer extends Transform
{
	private static final boolean DEBUG = false;

    private LSPCompiler compiler;
    private LSPPage theCompiledPage;
    private HashMap params;
	private String pageName;
	
    public void init()
        throws LagoonException, IOException
    {
        compiler = new LSPCompiler();
		
		pageName = Utils.encodePathAsIdentifier(getEntryName())+"_"+getPosition();

		theCompiledPage = loadLSPPage();
        if (DEBUG)
            if (theCompiledPage == null)
                System.out.println("No compiled page found");

        params = new HashMap();
       	for (Enumeration e = getParamNames(); e.hasMoreElements(); )
       	{
			String paramName = (String)e.nextElement();
		    params.put(paramName, getParam(paramName));
		}
    }

	private LSPPage loadLSPPage()
		throws LagoonException
	{
        try {
			Class theCompiledPageClass = 
				getContext().loadClassFromRepository("_LSP_"+pageName);
			LSPPage thePage = (LSPPage)theCompiledPageClass.newInstance();
			return thePage;
		}
		catch (ClassNotFoundException e)
		{
			return null;	
		}
		catch (InstantiationException e)
		{
			throw new LagoonException(e.getMessage());
		}
		catch (IllegalAccessException e)
		{
			throw new LagoonException(e.getMessage());
		}
	}
		
	private boolean sourceUpdated(long when)
        throws LagoonException, IOException
	{
		if (DEBUG) System.out.println("Checking compile dynamic"); 
        if (theCompiledPage.isCompileDynamic()) return true;

		if (DEBUG) System.out.println("Checking next"); 
        if (getNext().hasBeenUpdated(when))
        {
        	return true;
		}

		if (DEBUG) System.out.println("Checking imported files");
		String[] importedFiles = theCompiledPage.getCompileDependentFiles(); 
		for (int i = 0; i<importedFiles.length; i++)
		{
			String f = importedFiles[i];
			if (DEBUG) System.out.println("Checking imported file: " + f); 
			if (getSourceMan().fileHasBeenUpdated(f, when))
			{
				return true;
			}
		}

		return false;
	}


    private void readSource(XMLStreamProducer next, final Target target)
        throws SAXException, IOException
    {
		if (DEBUG) System.out.println("LSP compile");

        OutputStream out = null;
		try {
			ContentHandler ch = compiler.startCompile(
				pageName,
				new URLResolver() {
					public void resolve(String url, ContentHandler ch) 
						throws IOException, SAXException
					{
						getSourceMan().getFileAsSAX(url, ch, target);	
					}
				});
			next.start(ch, target);
			out = getContext().storeClassInRepository("_LSP_"+pageName);
			compiler.finishCompile(out);
			out.close();
		}
		catch (SAXException e)
		{
			if (out != null)
			{
				out.close();
				getContext().deleteClassInRepository("_LSP_"+pageName);
			}
			throw e;
		}
		getContext().reloadClasses();
		theCompiledPage = loadLSPPage();
		if (theCompiledPage == null)
		{
			throw new LagoonException("Unable to load compiled page");	
		}
    }


    public void start(ContentHandler ch, final Target target)
        throws IOException, SAXException
    {
        if (target instanceof FileTarget 
				&& ((FileTarget)target).isWildcard())
        	throw new LagoonException("Cannot use with wildcard pattern");

        if ((theCompiledPage == null)
                || sourceUpdated(theCompiledPage.getTimeCompiled()))
        {
            readSource(getNext(), target);
		}

        ch.startDocument();

        theCompiledPage.execute(ch, params, getContext());

        ch.endDocument();
    }


    public boolean hasBeenUpdated(long when)
        throws LagoonException, IOException
    {
        return true;    // always rebuild (but not always recompile)
    }

}

