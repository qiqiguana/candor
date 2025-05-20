/******************************************************************************
 * Modified by Zhong Li, merge headerling to one, removed Chdir
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
 * 
* AWT Panel that represents a file system.  Has buttons for basic
* file administration operations and a list of the files in a
* given directory.
* 
* This code is based on a LayoutManager tutorial on Sun's Java web site.
* http://developer.java.sun.com/developer/onlineTraining/GUI/AWTLayoutMgr/shortcourse.html
*/
package com.isnetworks.ssh;

import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.*;
import mindbright.ssh.*;

/** This class represents a small pane which will list the files present
 *  on a given platform.  This pane was made into its own class to allow
 *  easy reuse as both the local and remote file displays
 *  
 *  This GUI is built up using "lazy instantiation" via method calls
 *  for each part of the component.
 */
public class FileDisplay extends Panel {
	private FileBrowser mBrowser;
	private NumberFormat nf = NumberFormat.getInstance();
	private Button       mChgDirButton;
	private Button       mDeleteButton;
	private Panel        mFileButtonsInnerPanel;
	private Panel        mFileButtonsPanel;
	private Panel        mFileHeaderPanel;
	private FileList     mFileList;
	private Label        mMachineDescriptionLabel;	
	private Label        mSelectStatusLabel;
	private Button       mMkDirButton;
	private Button       mRefreshButton;
	private Button       mRenameButton;
	private TextField    mFileSystemLocationField;
	
	/**
	* Frame to own dialog boxes
	*/
	private Frame mOwnerFrame;
	
	/**
	* Reference to SCP main dialog box to send error messages to
	*/
	private SSHSCPPanel mSCPDialog;

	/** Constructor 
	*  This defines the overall GUI for this component
	*  It's a BorderLayout with a header, a set of buttons & a list
	*/
	public FileDisplay( Frame ownerFrame, String name, SSHSCPPanel scpDialog ) {
		mOwnerFrame = ownerFrame;
		mSCPDialog = scpDialog;
		
		mMachineDescriptionLabel = new Label( name );
		mFileSystemLocationField = new TextField("");
		
		setLayout(new BorderLayout());
		setBackground(Color.lightGray);
		add("North",  getFileHeaderPanel());
		add("Center", getFileList());
		add("South",   getFileButtonsPanel());
	}


	/** The header panel -- contains labels for Remote/Local and the current directory */
	private Panel getFileHeaderPanel() {
		if (mFileHeaderPanel == null) {
			mFileHeaderPanel = new Panel(new BorderLayout());
			mSelectStatusLabel = new Label();
			mFileHeaderPanel.add(mSelectStatusLabel,BorderLayout.SOUTH);
			mFileHeaderPanel.add(mMachineDescriptionLabel,BorderLayout.WEST);
			mFileHeaderPanel.add(mFileSystemLocationField,BorderLayout.CENTER);
			mFileHeaderPanel.add(getChgDirButton(),BorderLayout.EAST);
		}
		return mFileHeaderPanel;
	}


	/** This is merely a wrapper to bind the set of buttons to their
	*   preferred height
	*/
	private Panel getFileButtonsPanel() {
		if (mFileButtonsPanel == null) {
			mFileButtonsPanel = new Panel(new BorderLayout());
			mFileButtonsPanel.add("North", getFileButtonsInnerPanel());
		}
		return mFileButtonsPanel;
	}

	/** The panel containing the buttons for the file list */
	private Panel getFileButtonsInnerPanel() {
		if (mFileButtonsInnerPanel == null) {
			mFileButtonsInnerPanel = new Panel(new GridLayout(1,4));
			//mFileButtonsInnerPanel.add(getChgDirButton());
			mFileButtonsInnerPanel.add(getMkDirButton());
			mFileButtonsInnerPanel.add(getRenameButton());
			mFileButtonsInnerPanel.add(getDeleteButton());
			mFileButtonsInnerPanel.add(getRefreshButton());
		}
		return mFileButtonsInnerPanel;
	}


	//----- Buttons ----- 
	private Button getChgDirButton() {
		if (mChgDirButton == null) {
			mChgDirButton = new Button("ChDir");
			mChgDirButton.addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					String directoryName = mFileSystemLocationField.getText();//SSHMiscDialogs.textfield( "Change directory", "Directory", mOwnerFrame, getFileSystemLocationLabelText() );
					if ( directoryName != null ) {
						try {
							mBrowser.changeDirectory( directoryName );
						}
						catch( SSHException ex ) {
							mSCPDialog.logError( ex );
						}
					}
				}
			} );
		}
		return mChgDirButton;
	}

	private Button getMkDirButton() {
		if (mMkDirButton == null) {
			mMkDirButton = new Button("MkDir");
			mMkDirButton.addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					String directoryName = SSHMiscDialogs.textfield( "Make directory relative to current path", "Directory name", mOwnerFrame );
					if ( directoryName != null ) {
						try {
							mBrowser.makeDirectory( directoryName );
						}
						catch( SSHException ex ) {
							mSCPDialog.logError( ex );
						}
					}
				}
			} );
		}
		return mMkDirButton;
	}

	private Button getRenameButton() {
		if (mRenameButton == null) {
			mRenameButton = new Button("Rename");
			mRenameButton.addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					FileListItem mFileListItem = mFileList.getSelectedFileListItem();
					String newName = SSHMiscDialogs.textfield( "Rename file", "New file name", mOwnerFrame, mFileListItem.getName() );
					if ( newName != null ) {
						try {
							mBrowser.rename( mFileListItem, newName );
						}
						catch( SSHException ex ) {
							mSCPDialog.logError( ex );
						}
					}
				}
			} );
		}
		return mRenameButton;
	}

	private Button getDeleteButton() {
		if (mDeleteButton == null) {
			mDeleteButton = new Button("Delete");
			mDeleteButton.addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					try {
						mBrowser.delete( mFileList.getSelectedFileListItems() );
					}
					catch( SSHException ex ) {
						mSCPDialog.logError( ex );
					}
				}
			} );
		}
		return mDeleteButton;
	}

	private Button getRefreshButton() {
		if (mRefreshButton == null) {
			mRefreshButton = new Button( "Refresh" );
			mRefreshButton.addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					try {
						mBrowser.refresh();
					}
					catch( SSHException ex ) {
						mSCPDialog.logError( ex );
					}
				}
			} );
		}
		return mRefreshButton;
	}


	/** The list of files */
	private FileList getFileList() {
		if (mFileList == null) {
			mFileList = new FileList();
			mFileList.setFont( new Font( "Monospaced", Font.PLAIN, 12 ) );
			mFileList.setMultipleMode( true );
			mFileList.addActionListener( new ActionListener() {
				public void actionPerformed( ActionEvent e ) {
					try {
						mBrowser.fileDoubleClicked( mFileList.getFileListItem( e.getActionCommand() ) );
					}
					catch( SSHException ex ) {
						mSCPDialog.logError( ex );
					}
				}
			} );
			
			mFileList.addItemListener( new ItemListener() {
				public void itemStateChanged( ItemEvent e ) {
					enableButtons();
				}
			} );
			
			mFileList.setBackground(Color.white);
		}
		return mFileList;
	}


	private void enableButtons() {
		int cn = mFileList.getSelectionCount();
		mRenameButton.setEnabled( cn == 1 );
		mDeleteButton.setEnabled( cn>0 );
		String ltext = "";
		if( cn>0 ){
			FileListItem[] items = mFileList.getSelectedFileListItems();
			long total = 0;
			for(int i=0;i<items.length;i++){
				if(!items[i].isDirectory()) 
					total += items[i].getSize();
			}
			ltext = ""+cn+" selected. Total "+nf.format(total)+" Bytes.";
		}
		mSelectStatusLabel.setText(ltext);
	}

	//----- public methods that make the file system label a property -----
	private String currentPath;
	public String getFileSystemLocation() {
		return currentPath;
	}
	//public String getFileSystemLocationLabelText() {
	//	return mFileSystemLocationField.getText();
	//}

	public void setFileSystemLocationLabelText(String arg1) {
	    currentPath = arg1;
		mFileSystemLocationField.setText(arg1);
	}

	public void setFileList( Vector files, String directory ) {
		setFileSystemLocationLabelText( directory );

		mFileList.setListItems( files );
		enableButtons();
	}
	
	public void setFileBrowser( FileBrowser browser ) {
		mBrowser = browser;
	}
	
	public FileListItem getSelectedFile() {
		return mFileList.getSelectedFileListItem();
	}
	
	public FileListItem[] getSelectedFiles() {
		return mFileList.getSelectedFileListItems();
	}
}
