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
import java.net.*;

import nu.staldal.lagoon.core.*;
import nu.staldal.ftp.*;


/**
 * A FileStorage which transfers files to a remote site using FTP.
 *
 * <strong>Note:</strong> This class will transmit password in clear text over
 * the network.
 */
public class FTPFileStorage extends RemoteFileStorage
{
	private static final boolean DEBUG = false;

	private FTPClient ftp;
	private String url;
	private String password;


    /**
     * Default constructor.
     */
    public FTPFileStorage()
    {
    }

    public boolean needPassword()
    {
        return true;
    }


	public boolean isReentrant()
    {
        return false;
    }	


    public void open(String url, LagoonContext context, String password)
        throws MalformedURLException, UnknownHostException,
        FTPException, IOException, AuthenticationException
    {
		this.url = url;
		this.password = password;
		
		ftp = new FTPClient(url, password);
		
        openDateFile(context);
    }


    /**
     * Close the file system and release any resources it holds.
     *
     * After this method has been invoked, no other method may be invoked.
     */
    public void close()
    	throws IOException
    {
        closeDateFile();

		ftp.close();
		ftp = null;
    }

	
    /**
     * Create a new file, or overwrite an existing file.
     *
     * @param pathname  path to the file
     */
    public OutputHandler createFile(String pathname)
        throws IOException
    {
		OutputStream os;
		try {
			os = ftp.store(pathname);
		}
		catch (IOException e)
		{
			if (DEBUG) 
				System.out.println("FTP reconnecting: " + e.toString());
			try { ftp.close(); } catch (IOException ignore) {}
			ftp = new FTPClient(url, password);
			os = ftp.store(pathname);
		}
		
		return new FTPOutputHandler(pathname, os);
    }

	
    /**
     * Deletes a file.
     * Does not signal any error if the file doesn't exist.
	 *
     * @param pathname  path to the file
     */
    public void deleteFile(String pathname)
        throws java.io.IOException
    {
		try {
			ftp.deleteFile(pathname);
		}
		catch (IOException e)
		{
			if (DEBUG) 
				System.out.println("FTP reconnecting: " + e.toString());
			try { ftp.close(); } catch (IOException ignore) {}
			ftp = new FTPClient(url, password);
			ftp.deleteFile(pathname);
		}
	}


	class FTPOutputHandler extends OutputHandler
	{
    	private String currentPathname;

		FTPOutputHandler(String currentPathname, OutputStream out)
		{
			super(out);
			this.currentPathname = currentPathname;
		}

		
		public void commit()
			throws java.io.IOException
		{
			out.close();
			fileModified(currentPathname);
		}
		

		public void discard()
			throws java.io.IOException
		{
			try {
				commit();
			}
			catch (FTPException e)
			{
				// ignore exception
			}
	
			ftp.deleteFile(currentPathname);
		}
			
	}	
	
}

