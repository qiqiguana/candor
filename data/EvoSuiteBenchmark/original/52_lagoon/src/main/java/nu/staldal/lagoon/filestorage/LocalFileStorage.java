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

package nu.staldal.lagoon.filestorage;

import java.io.*;

import nu.staldal.lagoon.core.*;


/**
 * A FileStorage using the local file system.
 */
public class LocalFileStorage implements FileStorage
{
    private File root;

    /**
     * Default constructor.
     */
    public LocalFileStorage()
    {
        root = null;
    }

    public boolean needPassword()
    {
        return false;
    }

    public boolean isReentrant()
    {
        return true;
    }	

    public void open(String loc, LagoonContext context, String password)
        throws java.io.IOException
    {
        root = new File(loc);
        if (root.exists())
        {
            if (!root.isDirectory())
                throw new IOException(
                    "Location exists but is not a directory");
        }
        else
        {
            if (!root.mkdir())
                throw new IOException(
                    "Location didn't exist and couldn't be created");
        }
    }


    /**
     * Close the file system and release any resources it holds.
     *
     * After this method has been invoked, no other method may be invoked.
     */
    public void close()
        // throws java.io.IOException;
    {
        root = null;
    }

    /**
     * Check if a file exists and when it was last modified.
     *
     * @param path  path to the file
     *
     * @return  the time when the file was last modified,
     * or -1 if that information is not avaliable,
     * or 0 if the file doesn't exists.
     */
    public long fileLastModified(String path)
        // throws java.io.IOException
    {
        File file = root;
        int oldPos = 1;
        int pos;
        while (true)
        {
            pos = path.indexOf('/', oldPos);
            if (pos < 0) break;
            String comp = path.substring(oldPos, pos);
            file = new File(file, comp);
            if (!file.exists()) return 0;
            oldPos = pos + 1;
        }
        file = new File(file, path.substring(oldPos));

        return file.lastModified();
    }

    /**
     * Create a new file, or overwrite an existing file.
     */
    public OutputHandler createFile(String path)
        throws java.io.IOException
    {
        File file = root;
        int oldPos = 1;
        int pos;
        while (true)
        {
            pos = path.indexOf('/', oldPos);
            if (pos < 0) break;
            String comp = path.substring(oldPos, pos);
            file = new File(file, comp);
            if (!file.exists())
                if (!file.mkdir())
                    throw new IOException("Unable to create directory: "
                                          + file);
            oldPos = pos + 1;
        }
        File currentFile = new File(file, path.substring(oldPos));

		return new LocalOutputHandler(currentFile, 
									  new FileOutputStream(currentFile));
	}


    /**
     * Deletes a file.
     * Does not signal any error if the file doesn't exist.
	 *
     * @param path  path to the file
     */
    public void deleteFile(String path)
        throws java.io.IOException
    {
        File file = root;
        int oldPos = 1;
        int pos;
        while (true)
        {
            pos = path.indexOf('/', oldPos);
            if (pos < 0) break;
            String comp = path.substring(oldPos, pos);
            file = new File(file, comp);
            if (!file.exists()) return; // file doesn't exist
            oldPos = pos + 1;
        }
        file = new File(file, path.substring(oldPos));

        if (!file.exists()) return;
        if (file.delete())
        {
			return;
		}
		else
		{
			throw new IOException("Unable to delete file: " + file);
		}
	}
	
	
	static class LocalOutputHandler extends OutputHandler
	{
		private File currentFile;
		
		LocalOutputHandler(File currentFile, OutputStream out)
		{
			super(out);
			this.currentFile = currentFile;
		}
		
		public void commit()
			throws java.io.IOException
		{
			out.close();
		}

		public void discard()
			throws java.io.IOException
		{
			out.close();
			if (!currentFile.exists()) return;
			if (currentFile.delete())
			{
				return;
			}
			else
			{
				throw new IOException("Unable to delete file: " + currentFile);
			}
		}
	}
}
