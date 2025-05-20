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

import javax.xml.transform.*;
import javax.xml.transform.sax.*;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.*;
import org.xml.sax.helpers.AttributesImpl;

import nu.staldal.xmlutil.*;
import nu.staldal.util.Utils;

public class BuildSitemap
{
    private static final String syntaxMsg =
        "Syntax: nu.staldal.lagoon.BuildSitemap <source_dir> <sitemap_file>";
		
	static File baseDir;

	public static void main(String args[]) throws Exception
	{
        if (args.length < 2)
        {
            System.out.println(syntaxMsg);
            return;
        }

		FileOutputStream fos = new FileOutputStream(args[1]);

		TransformerFactory tf = TransformerFactory.newInstance();
        if (!(tf.getFeature(SAXTransformerFactory.FEATURE)
              	&& tf.getFeature(StreamResult.FEATURE)))
        {
            throw new SAXException("The transformer factory "
                + tf.getClass().getName() + " doesn't support SAX");
        }
            
		SAXTransformerFactory tfactory = (SAXTransformerFactory)tf;
		TransformerHandler th = tfactory.newTransformerHandler();
		th.setResult(new StreamResult(fos));
		
		Transformer trans = th.getTransformer();
		trans.setOutputProperty(OutputKeys.METHOD, "xml");
		trans.setOutputProperty(OutputKeys.ENCODING, "iso-8859-1");
		trans.setOutputProperty(OutputKeys.INDENT, "yes");
		
		ContentHandler ch = new ContentHandlerFixer(th, true);
		
		baseDir = new File(args[0]);		
		
		ch.startDocument();
		AttributesImpl atts = new AttributesImpl();
		atts.addAttribute("","name","","CDATA", Utils.encodePath(args[0]));
		ch.startElement("", "sitemap", "", atts);

		processDirectory(null, ch);

		ch.endElement("", "sitemap", "");
		ch.endDocument();

		fos.close();
	}

	public static void processDirectory(File dir, ContentHandler ch)
		throws SAXException
	{
		File thisDir = (dir == null) ? baseDir : new File(baseDir, dir.getPath());
		String[] dirList = thisDir.list();
		for (int i = 0; i<dirList.length; i++)
		{
			File ent = new File(thisDir, dirList[i]);
			File thisEnt = (dir == null) 
									? new File(dirList[i])
									: new File(dir, dirList[i]);
			if (ent.isDirectory())
			{
				processDirectory(thisEnt, ch);
			}
			else if (ent.isFile())
			{
				AttributesImpl atts = new AttributesImpl();
                String url = "/" + thisEnt.getPath().replace(
                    File.separatorChar, '/');

				atts.addAttribute("","target","","CDATA", url);

				ch.startElement("", "file", "", atts);

                if (url.endsWith(".html") || url.endsWith(".htm"))
                {
    				AttributesImpl formatAtts = new AttributesImpl();

				    formatAtts.addAttribute("","type","","CDATA", "html");

                    ch.startElement("", "format", "", formatAtts);
                    ch.startElement("", "source", "", new AttributesImpl());
	    			ch.endElement("", "source", "");
                    ch.endElement("", "format", "");
                }
                else if (url.endsWith(".xml"))
                {
    				AttributesImpl formatAtts = new AttributesImpl();

				    formatAtts.addAttribute("","type","","CDATA", "xml");

                    ch.startElement("", "format", "", formatAtts);
                    ch.startElement("", "source", "", new AttributesImpl());
	    			ch.endElement("", "source", "");
                    ch.endElement("", "format", "");
                }
                else
                {
                    ch.startElement("", "read", "", new AttributesImpl());
	    			ch.endElement("", "read", "");
                }

				ch.endElement("", "file", "");
			}
		}
	}

}
