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
* Browser to handle file manipulation on the local machine
*/
package com.isnetworks.ssh;

import java.io.*;
import java.util.*;
import mindbright.ssh.*;


public class LocalFileBrowser extends AbstractFileBrowser {
	
	private File mCurrentDirectory;

	public LocalFileBrowser( FileDisplay fileDisplay, SSHPropertyHandler propertyHandler ) {
		super( fileDisplay, propertyHandler );
	}
	
	/**
	* Jump to the default SSH home directory
	*/
	public void initialize() throws SSHException {
		changeDirectory( mPropertyHandler.getSSHHomeDir() );
	}
	
	public void refresh() throws SSHException {	

		Vector v = new Vector();
		if( mCurrentDirectory!=null ){
			// Add link to parent directory if we're not already at the root
			//if ( mCurrentDirectory.getParent() != null ) {
			v.addElement( new FileListItem( "..", null, true, -1,mCurrentDirectory.lastModified() ) );
			//}
			String[] fileNames = mCurrentDirectory.list();
			// Add each file and directory in the list
			for( int i = 0; i < fileNames.length; i++ ) {
				v.addElement( new FileListItem( new File( mCurrentDirectory, fileNames[ i ] ) ) );
			}
		    
		}else{
		    File[] files = File.listRoots();
		    for(int i=0;i<files.length;i++){
		        if( files[i].canRead() ){
					try {
                        v.addElement( new FileListItem(
                                files[i].getCanonicalPath(),
                                null,
                                files[i].isDirectory(),
                                files[i].length(),
                                files[i].lastModified()) );
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
		        }
			}
		}
		
			

		// Sort the array since File.list() does not define an order for the results
		FileListItem.sort( v );
		
		// Set list in the GUI
		try {
		    String path = null;
		    if(mCurrentDirectory!=null)
		        path = mCurrentDirectory.getCanonicalPath();
			mFileDisplay.setFileList( v, path );
		}
		catch( IOException e ) {
			throw new SSHException( "Unable to refresh file list" );
		}
	}

	public void makeDirectory( String directoryName ) throws SSHException {
		File newDirectory = new File( mCurrentDirectory, directoryName );
		if ( !newDirectory.mkdirs() ) {
			throw new SSHException( "Unable to make directory: " + newDirectory.getAbsolutePath() );
		}
		refresh();
	}

	public void delete( FileListItem[] fileListItem ) throws SSHException {
		if (fileListItem != null
				 && fileListItem.length>0 
				 && SSHMiscDialogs.confirm("Deletion Confirmation","Do you want to delete them?",true,mPropertyHandler.getParent())
					) {
			
			for( int i = 0; i < fileListItem.length; i++ ) {
				File deleteFile = new File( fileListItem[ i ].getParent(), fileListItem[ i ].getName() );
				if ( !deleteFile.delete() ) {
					throw new SSHException( "Unable to delete " + fileListItem[ i ].getAbsolutePath() + " - may not have permission or directory may not be empty" );
				}
			}
			
			refresh();
		}
	}
	
	public void changeDirectory( String directoryName ) throws SSHException {
	    if( directoryName!=null && directoryName.trim().length()>0 ){
			File newDirectory = new File( directoryName );
			if ( !newDirectory.exists() ) {
				throw new SSHException( "Directory " + directoryName + " does not exist or you do not have permission to access it." );		
			}
			if( newDirectory.isFile() ) {
				throw new SSHException( directoryName + " a file, not a directory." );
			}
			// This is the right way to do it, but it doesn't work under Netscape
			// if the directory has a space in its name.  Nice work, Netscape!
//			if( !newDirectory.isDirectory() ) {
//				throw new SSHException( directoryName + " is not a directory." );
//			}
			mCurrentDirectory = newDirectory;
	        
	    }else{
	        mCurrentDirectory = null;
	    }
		refresh();
	}
	
	public void rename( FileListItem oldFileListItem, String newName ) throws SSHException {
		File oldFile = new File( oldFileListItem.getParent(), oldFileListItem.getName() );
		File newFile = new File( mCurrentDirectory, newName );
		
		if ( !oldFile.renameTo( newFile ) ) {
			throw new SSHException( "Unable to rename file " + oldFileListItem.getAbsolutePath() + " to " + oldFileListItem.getParent() + newName );
		}
		
		refresh();
	}

	public void fileDoubleClicked( FileListItem fileListItem ) throws SSHException {

		if ( fileListItem.isDirectory() ) {


			File newDirectory = null;

			if ( fileListItem.getName().equals( ".." ) ) {
			    if( mCurrentDirectory!=null && mCurrentDirectory.getParent()!=null ){
				    System.out.println("up"+mCurrentDirectory+":"+mCurrentDirectory.getParent() );
			        newDirectory = new File( mCurrentDirectory.getParent() );
			    }else{
				    System.out.println("up"+mCurrentDirectory);
			        
			    }
			}
			else {
				newDirectory = new File( fileListItem.getParent(), fileListItem.getName() );
			}
			
			if ( newDirectory!=null && (!newDirectory.exists() || !newDirectory.isDirectory() )) {
				throw new SSHException( "Unable to open directory: " + newDirectory.getAbsolutePath() );
			}
			
			mCurrentDirectory = newDirectory;
			
			refresh();
		}else{
		    System.out.println("not directory");
		}
	}

    /* (non-Javadoc)
     * @see com.isnetworks.ssh.AbstractFileBrowser#abort()
     */
    public void abort() {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see com.isnetworks.ssh.AbstractFileBrowser#putFiles(mindbright.ssh.SSHSCPIndicator, com.isnetworks.ssh.FileDisplay, com.isnetworks.ssh.FileDisplay, boolean)
     */
    public void putFiles(SSHSCPIndicator indicator, FileDisplay remoteDisplay, FileDisplay localDisplay, boolean isBinary) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see com.isnetworks.ssh.AbstractFileBrowser#getFiles(mindbright.ssh.SSHSCPIndicator, com.isnetworks.ssh.FileDisplay, com.isnetworks.ssh.FileDisplay, boolean)
     */
    public void getFiles(SSHSCPIndicator indicator, FileDisplay remoteDisplay, FileDisplay localDisplay, boolean isBinary) {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see com.isnetworks.ssh.AbstractFileBrowser#disconnect()
     */
    public void disconnect() {
        // TODO Auto-generated method stub
        
    }	
}