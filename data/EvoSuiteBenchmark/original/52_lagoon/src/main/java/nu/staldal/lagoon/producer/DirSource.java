/*
 * Copyright (c) 2001-2005, Mikael Ståldal
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
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.xml.sax.*;
import org.xml.sax.helpers.AttributesImpl;

import nu.staldal.lagoon.core.*;
import nu.staldal.lagoon.util.Wildcard;

public class DirSource extends Source
{
    private String pattern = null;

	private Hashtable dirlist = null;
	
    public void init() throws IOException
    {
        pattern = getParam("pattern");
		
        dirlist = (Hashtable)getObjectFromRepository("dirlist");
    }

    public void start(ContentHandler sax, Target target)
        throws IOException, SAXException
    {
        File dir = getSourceMan().getFile(getSourceMan().getSourceURL());
        if (!dir.isDirectory())
            throw new IOException("The source is not a directory");

        String[] files = dir.list();

        dirlist = new Hashtable();
        for (int i = 0; i<files.length; i++)
        {
            dirlist.put(files[i], "washere");
        }
        putObjectIntoRepository("dirlist", dirlist);

        sax.startDocument();
        sax.startElement("", "dirlist", "", new AttributesImpl());

        for (int i = 0; i < files.length; i++)
        {
            if ((pattern != null)
                    && (Wildcard.matchWildcard(pattern, files[i]) == null))
                continue;
        
            File file = new File(dir, files[i]);

            AttributesImpl atts = new AttributesImpl();
            atts.addAttribute("", "filename", "", "CDATA", files[i]);
            atts.addAttribute("", "url", "", "CDATA",
                getSourceMan().getFileURL(files[i]) 
				+ (file.isDirectory() ? "/" : ""));
				
			long timestamp = file.lastModified();
			SimpleDateFormat dateFormat =
     			new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat timeFormat =
     			new SimpleDateFormat("HH:mm:ss");
			Date date = new Date(timestamp);
			
	        atts.addAttribute("", "timestamp", "", "CDATA",
				String.valueOf(timestamp));
	        atts.addAttribute("", "date", "", "CDATA",
				dateFormat.format(date));
	        atts.addAttribute("", "time", "", "CDATA",
				timeFormat.format(date));
											
            if (file.isFile())
            {
	            atts.addAttribute("", "size", "", "CDATA",
					String.valueOf(file.length()));
                sax.startElement("", "file", "", atts);
                sax.endElement("", "file", "");
            }
            else if (file.isDirectory())
            {
                sax.startElement("", "directory", "", atts);
                sax.endElement("", "directory", "");
            }
            else
                ; // do nothing
        }

        sax.endElement("", "dirlist", "");
        sax.endDocument();
	}

    public boolean hasBeenUpdated(long when) throws LagoonException, IOException
    {
        if (getSourceMan().fileHasBeenUpdated(getSourceMan().getSourceURL() ,when))
            return true;

        File dir = getSourceMan().getFile(getSourceMan().getSourceURL());
        
		if (dirlist == null) return true;

        String[] files = dir.list();
        for (int i = 0; i<files.length; i++)
        {
            if (dirlist.put(files[i], "ishere") == null)
            {
                return true; // file added
            }
        }

        for (Enumeration en = dirlist.keys(); en.hasMoreElements(); )
        {
            Object o = en.nextElement();
            if (dirlist.get(o) != "ishere")
            {
                return true; // file removed
            }
        }

        return false;
    }

}

