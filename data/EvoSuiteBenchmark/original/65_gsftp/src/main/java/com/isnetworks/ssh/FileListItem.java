/******************************************************************************
 *
 * Modified by Zhong Li, added modify time 
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
* Representation of a file on either the local or remote file systems.
* Knows whether it's a directory as well as its name and path.
*/
package com.isnetworks.ssh;

import java.io.*;
import java.util.*;

public class FileListItem {

	/**
	* Name of the file or directory
	*/
	private String mName;

	/**
	* Full path of directory which contains this file
	*/
	private String mParent;
	
	/**
	* Whether this file is a directory
	*/
	private boolean mDirectory;

    private long    mSize;
	
	private long    mLastModify;
	
	/**
	* Construct from a file on the local file system
	*/
	public FileListItem( File file ) {
		this( file.getName(), file.getParent(), file.isDirectory(), file.length(),file.lastModified() );
	}

	public FileListItem( String name, String parent, boolean directory, long size, long lastModify ) {
		mName = name;
		mParent = parent;
		mDirectory = directory;
		mSize = size;
		mLastModify = lastModify;
		
	}
	
	/**
	* Get fully qualified name
	*/
	public String getAbsolutePath() {
		
		return mParent + mName;
	}
	
	/**
	* Get name of file relative to its parent directory
	*/
	public String getName() {
		return mName;
	}
	
	/**
	* Get full path of directory this file lives in
	*/
	public String getParent() {
		return mParent;
	}
	
	public boolean isDirectory() {
		return mDirectory;
	}
    public long getSize() {
        return mSize;
    }
	public long getLastModify() {
		return mLastModify;
	}
	
	/**
	* Used to sort files first by directory/non-directory and then by name
	*/
	private boolean earlierThan( FileListItem fileListing ) {
		// Always put parent directory at the top of the list
		if ( mName.equals( ".." ) ) {
			return true;
		}		
		if ( fileListing.mName.equals( ".." ) ) {
			return false;
		}
		
		if ( isDirectory() && !fileListing.isDirectory() ) {
			return true;
		}
		if ( !isDirectory() && fileListing.isDirectory() ) {
			return false;
		}
		
		return mName.toUpperCase().compareTo( fileListing.mName.toUpperCase() ) < 0;
	}

	/**
	* Simple, inefficient bubble sort for array of FileListItems.
	* Only here because java.util.Arrays class does not exist
	* in Java 1.1 so it wouldn't work in an applet.  Should be
	* acceptable since directories typically contain a relatively
	* small number of files.
	*/
	public static void sort( Vector files ) {
		for( int i = 0; i < files.size(); i++ ) {
			for( int j = i; j < files.size(); j++ ) {
				if ( !((FileListItem)files.elementAt( i )).earlierThan( (FileListItem)files.elementAt( j ) ) ) {
					FileListItem temp = (FileListItem)files.elementAt( j );
					files.setElementAt( (FileListItem)files.elementAt( i ), j );
					files.setElementAt( temp, i );
				}
			}
		}
	}

}