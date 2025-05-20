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
* Interface which defines the operations a user can perform on either
* the local or remote file systems
*/
package com.isnetworks.ssh;

public interface FileBrowser {

	/**
	* User wants to switch to a directory
	*/
	public void fileDoubleClicked( FileListItem file ) throws SSHException;
	
	/**
	* Refresh the file list
	*/
	public void refresh() throws SSHException;
	
	/**
	* Delete a file or set of files from the file system
	*/
	public void delete( FileListItem[] file ) throws SSHException;

	/**
	* Connect with the file system if needed
	*/
	public void initialize() throws SSHException;
	
	/**
	* Create a new empty directory
	*/
	public void makeDirectory( String directoryName ) throws SSHException;
	
	/**
	* Rename an existing file
	*/
	public void rename( FileListItem file, String newFileName ) throws SSHException;
	
	/**
	* Switch to a specific, user-input directory
	*/
	public void changeDirectory( String directoryName ) throws SSHException;
}