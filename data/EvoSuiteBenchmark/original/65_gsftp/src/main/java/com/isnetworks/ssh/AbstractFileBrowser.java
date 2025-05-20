/******************************************************************************
 *
 * Copyright (c) 2001 by ISNetworks, Seattle, WA.
 *                       www.isnetworks.com, info@isnetworks.com
 * Based on MindTerm from Mindbright Technology AB, Stockholm, Sweden.
 *                        www.mindbright.se, info@mindbright.se
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *****************************************************************************/

/**
* Abstract superclass for remote and local file browsing.
* Currently just a common place to keep a couple of variables.
*/
package com.isnetworks.ssh;

import mindbright.ssh.*;

public abstract class AbstractFileBrowser implements FileBrowser {

	/**
	* AWT components responsible for GUI representation of file system
	*/
	protected FileDisplay mFileDisplay;

	/**
	* Holds info about server, the current user, and default home
	* directory on local file system
	*/
	protected SSHPropertyHandler mPropertyHandler;
	
	public AbstractFileBrowser( FileDisplay fileDisplay, 
	        SSHPropertyHandler propertyHandler ) {
		mFileDisplay = fileDisplay;
		mPropertyHandler = propertyHandler;
		
		mFileDisplay.setFileBrowser( this );
	}
	public abstract void abort();
	public abstract void putFiles(
	        SSHSCPIndicator indicator,
	        FileDisplay remoteDisplay, 
	        FileDisplay localDisplay,
			boolean isBinary);
	
	public abstract void getFiles(
	        SSHSCPIndicator indicator,
	        FileDisplay remoteDisplay, 
	        FileDisplay localDisplay,
			boolean isBinary);
	public abstract void disconnect();
}

