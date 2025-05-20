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
import java.util.Hashtable;


/**
 * Helper class to implement a FileStorage which stores file modification
 * dates locally in the Lagoon working directory.
 */
public abstract class RemoteFileStorage implements FileStorage
{
    private static final boolean DEBUG = false;
    
    private LagoonContext context;
    private Hashtable lastModTable;

    /**
	 * Open the file to store last update dates locally.
	 * Invoke this from the {@link nu.staldal.lagoon.core.FileStorage#open} method.
	 */
	protected void openDateFile(LagoonContext context)
        throws IOException
    {
        if (DEBUG) System.out.println("RemoteFileStorage.init()");
        
        this.context = context;
		try
		{
			lastModTable =
                (Hashtable)context.getObjectFromRepository(
                    "nu.staldal.lagoon.filestorage.RemoteFileStorage");
            if (lastModTable == null) lastModTable = new Hashtable();
		}
		catch (ClassCastException e)
		{
			throw new IOException("Date file is corrupt");
		}
    }

    /**
	 * Close the file to store last update dates locally.
	 * Invoke this in the {@link nu.staldal.lagoon.core.FileStorage#close} method.
	 */
    protected void closeDateFile()
        throws IOException
    {
		context.putObjectIntoRepository(
            "nu.staldal.lagoon.filestorage.RemoteFileStorage", lastModTable);
    }

	/**
	 * Signals that a file has been created or updated.
	 * Invoke this after successful commitment in the 
	 * {@link nu.staldal.lagoon.core.OutputHandler#commit} method.
	 */
    protected void fileModified(String pathname)
    {
        lastModTable.put(pathname, new Long(System.currentTimeMillis()));
    }

    /**
     * Check if a file exists and when it was last modified.
     *
     * @param pathname  path to the file
     *
     * @return  the time when the file was last modified,
     * or -1 if that information is not avaliable.
     */
    public final long fileLastModified(String pathname)
        // throws java.io.IOException
    {
		Long l = (Long)lastModTable.get(pathname);
		if (l == null)
			return -1;
		else
			return l.longValue();
    }
}
