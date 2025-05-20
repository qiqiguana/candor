/******************************************************************************
 *
 * Copyright (c) 2006 by GoToServers.com
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

package com.gotoservers.ftp;

import mindbright.ssh.SSHMiscDialogs;
import mindbright.ssh.SSHPropertyHandler;
import mindbright.ssh.SSHSCPIndicator;
import mindbright.ssh.SSHSCPPanel;

import org.apache.commons.net.ProtocolCommandEvent;
import org.apache.commons.net.ProtocolCommandListener;

import java.io.*;
import java.util.*;

import com.isnetworks.ssh.*;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class RemoteFileBrowser extends AbstractFileBrowser implements
		ProtocolCommandListener {
	String currentPath;

	FTPClient ftp;

	SSHPropertyHandler mPropertyHandler;

	SSHSCPPanel mErrorLog;

	public RemoteFileBrowser(FileDisplay fileDisplay,
			SSHPropertyHandler propertyHandler, SSHSCPPanel errorLog) {
		super(fileDisplay, propertyHandler);
		mPropertyHandler = propertyHandler;
		mErrorLog = errorLog;

	}

	public void fileDoubleClicked(FileListItem file) throws SSHException {
		try {
			if (file.isDirectory()) {
				ftp.cwd(file.getName());
				refresh();
			}
		} catch (Exception e) {
			throw new SSHException(e.getMessage());
		}
	}
	private String getPWD(){
		try{
			if (ftp.pwd() == 257) {
				String res = ftp.getReplyString();
				if (res != null && res.length() >= 5
						&& res.indexOf("257 ") == 0) {
					res = res.substring(4);
					if (res.charAt(0) == '"') {
						res = res.substring(1);
						int pos = res.indexOf('"');
						if (pos > 0) {
							res = res.substring(0, pos);
						}
					} else {
						int pos = res.indexOf(' ');
						if (pos > 0) {
							res = res.substring(0, pos);
						}
					}
					return res;
				}
			}
		}catch(Exception e){
			mErrorLog.logError(e);
		}
		return null;
	}
	public void refresh() throws SSHException {
		if( !ftp.isConnected() ){
			connect();
			try {
				ftp.changeWorkingDirectory(currentPath);
			} catch (Exception e) {
				throw new SSHException(e.getMessage());
			}
			
		}
		try {
			String pwd = getPWD();
			if( pwd!=null ) currentPath = pwd;
			if (currentPath == null) currentPath = "/";

			// 257 "PATHNAME"

			FTPFile[] files = ftp.listFiles();

			Vector v = new Vector();

			// Add each file and directory in the list
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory() && ".".equals(files[i].getName()))
					continue;
				v.addElement(new FileListItem(files[i].getName(), currentPath,
						files[i].isDirectory(), files[i].getSize(),
						files[i].getTimestamp().getTimeInMillis()
						));
			}

			// Sort the array since File.list() does not define an order for the
			// results
			FileListItem.sort(v);

			// Set list in the GUI
			mFileDisplay.setFileList(v, currentPath);
		} catch (Exception e) {
			throw new SSHException(e.getMessage());
		}

	}

	public void delete(FileListItem[] files) throws SSHException {
		if (files != null
				 && files.length>0 
				 && SSHMiscDialogs.confirm("Deletion Confirmation","Do you want to delete them?",true,mPropertyHandler.getParent())
			){
			for (int i = 0; i < files.length; i++) {
				try {
					if (files[i].isDirectory()) {
						if (!ftp.removeDirectory(files[i].getName())) {
							System.out.println(ftp.getReplyString());
						}
					} else if (!ftp.deleteFile(files[i].getName())) {
						System.out.println(ftp.getReplyString());
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			refresh();
		}

	}
	private void connect()  throws SSHException {
		ftp = new FTPClient();
		ftp.addProtocolCommandListener(this);
		try {
			int reply;
			ftp.connect(mPropertyHandler.getRemoteServer(), mPropertyHandler
					.getRemotePort());

			// After connection attempt, you should check the reply code to
			// verify
			// success.
			reply = ftp.getReplyCode();

			if (!FTPReply.isPositiveCompletion(reply)) {
				throw new SSHException(ftp.getReplyString());
			}
			if (!ftp.login(mPropertyHandler.getRemoteUser(), mPropertyHandler
					.getRemotePassword())) {
				throw new SSHException(ftp.getReplyString());
			}
			// if (binaryTransfer)
			// ftp.setFileType(FTP.BINARY_FILE_TYPE);

			ftp.enterLocalPassiveMode();

		} catch (IOException e) {
			disconnect();
			e.printStackTrace();
			throw new SSHException(e.getMessage());
		} finally {

		}

	}
	public void initialize() throws SSHException {
		connect();
		refresh();
	}

	public void makeDirectory(String directoryName) throws SSHException {
		try {
			if (ftp.makeDirectory(directoryName)) {
				refresh();
			} else {
				System.out.println(ftp.getReplyString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void rename(FileListItem file, String newFileName)
			throws SSHException {
		try {
			if (ftp.rename(file.getName(), newFileName)) {
				refresh();
			} else {
				throw new SSHException(ftp.getReplyString());
			}
		} catch (Exception e) {
			mErrorLog.logError(e);
		}

	}

	public void changeDirectory(String directoryName) throws SSHException {
		try {
			if (ftp.changeWorkingDirectory(directoryName)) {
				refresh();
			} else {
				throw new SSHException(ftp.getReplyString());
			}
		} catch (Exception e) {
			mErrorLog.logError(e);
		}
	}

	public void disconnect() {
		System.out.println("disconnect");
		if (ftp.isConnected()) {
			try {
				ftp.logout();
			} catch (IOException f) {
				// do nothing
			}
			try {
				ftp.disconnect();
			} catch (IOException f) {
				// do nothing
			}
		}

	}

	public void protocolCommandSent(ProtocolCommandEvent event) {
		// TODO Auto-generated method stub
		// System.out.println("Sent Command:" + event.getCommand());
		// System.out.println("Sent Message:" + event.getMessage());
	}

	public void protocolReplyReceived(ProtocolCommandEvent event) {
		// TODO Auto-generated method stub
		System.out.println("Replay Code:" + event.getReplyCode());
		System.out.println("Replay Message:" + event.getMessage());

	}
	private void getAllFiles(SSHSCPIndicator indicator,FileListItem[] rFiles, String curDir ){
		//handle file
		for (int i = 0; i < rFiles.length; i++) {
			FileListItem fi = rFiles[i];
			if (fi.isDirectory()) {
				if( ".".equals(fi.getName()) 
				 || "..".equals(fi.getName()) ) {
					continue;
				}
				String localDir = curDir+fi.getName()+"/";
				File localDirFile = new File(localDir);
				if( localDirFile.exists() ){
					if( !localDirFile.isDirectory() ){
						mErrorLog.logError(new SSHException("Can't create directory:"+localDir) );
						continue;
					}
				}else{
					try{
						if( !localDirFile.mkdir() ){
							throw new SSHException("Can't create directory:"+localDir);
						}
					}catch(Exception e){
						mErrorLog.logError(e );
						continue;
					}
				}
				
				try{
					if (ftp.changeWorkingDirectory(fi.getName())) {
						String pwd = getPWD();
						if( pwd==null)pwd = fi.getAbsolutePath();
						System.err.println("D:"+pwd);
						
						FTPFile[] files = ftp.listFiles();
						if( files!=null && files.length>0){
							FileListItem[] drFiles = new FileListItem[files.length];
							// Add each file and directory in the list
							for (int ri = 0; ri < files.length; ri++) {
								System.err.println("F:"+files[ri].getName());
								drFiles[ri]= new FileListItem(files[ri].getName(), localDir,
										files[ri].isDirectory(), files[ri].getSize(),
										files[ri].getTimestamp().getTimeInMillis()
										);
							}
							getAllFiles(indicator,drFiles,localDir );

						}
						
						
						ftp.changeToParentDirectory();
					} else {
						throw new SSHException(ftp.getReplyString());
					}
				} catch (Exception e) {
					mErrorLog.logError(e);
				}
				// handle latter
			} else {
				InputStream is = null;
				OutputStream os = null;
				try {
					File localFile = new File(curDir + fi.getName());
					is = ftp.retrieveFileStream(fi.getName());
					if( is!=null ){
						os = new FileOutputStream(localFile);
						byte[] buffer = new byte[4096];
						int len;
						long total = 0;
						indicator.startFile(fi.getName(), fi.getSize());
						while ((len = is.read(buffer)) >= 0) {
							if (len > 0) {
								os.write(buffer, 0, len);
								total += len;
								indicator.progress(total);
							}
						}
						indicator.endFile();
					}else{
						mErrorLog.logError(new SSHException(ftp.getReplyString()));
					}
				} catch (Exception e) {
					mErrorLog.logError(e);
					// TODO:
					e.printStackTrace();
				} finally {
					if (is != null) {
						try {
							is.close();
						} catch (Exception e) {
						}
						;
					}
					if (os != null) {
						try {
							os.close();
						} catch (Exception e) {
						}
						;
					}
					try{
						ftp.completePendingCommand();
					}catch(Exception e){
						
					}
				}
			}
			
		}
		
	}
	public void getFiles(SSHSCPIndicator indicator, FileDisplay remoteDisplay,
			FileDisplay localDisplay, boolean isBinary) {
		FileListItem[] rFiles = remoteDisplay.getSelectedFiles();
		if (rFiles != null && rFiles.length > 0) {
			try {
				if (isBinary) {
					ftp.setFileType(FTP.BINARY_FILE_TYPE);
				} else {
					ftp.setFileType(FTP.ASCII_FILE_TYPE);
				}
				String curDir = localDisplay.getFileSystemLocation();//.getFileSystemLocationLabelText();
				if (curDir == null)
					curDir = "/";
				else {
					int len = curDir.length();
					if (curDir.charAt(len - 1) != '/'
							&& curDir.charAt(len - 1) != '\\') {
						curDir += "/";
					}
				}
				getAllFiles(indicator,rFiles, curDir );
			} catch (Exception e) {
				mErrorLog.logError(e);
			}
			try {
				changeDirectory(this.currentPath);
			} catch (Exception e) {
				mErrorLog.logError(e);
			}

		}

	}
	private void putAllFiles(SSHSCPIndicator indicator,FileListItem[] lFiles, String curDir ){
		//handle file
		for (int i = 0; i < lFiles.length; i++) {
			FileListItem fi = lFiles[i];
			if (fi.isDirectory()) {
				if( ".".equals(fi.getName()) 
				 || "..".equals(fi.getName()) ) {
					continue;
				}
				String localDir = curDir+fi.getName()+"/";
				File localDirFile = new File(localDir);
				if( !localDirFile.exists() 
				 || !localDirFile.isDirectory()){
					mErrorLog.logError(new SSHException("Can't list directory:"+localDir ));
					continue;
				}
				try{
					ftp.makeDirectory( fi.getName());
					if (ftp.changeWorkingDirectory(fi.getName())) {
						String pwd = getPWD();
						if( pwd==null){
							mErrorLog.logError(new SSHException("Can't get path :"+fi.getName()) );
							continue;
						}
						System.err.println("D:"+pwd);
						
						File[] files = localDirFile.listFiles();
						if( files!=null && files.length>0){
							FileListItem[] dlFiles = new FileListItem[files.length];
							// Add each file and directory in the list
							for (int li = 0; li < files.length; li++) {
								System.err.println("F:"+files[li].getName());
								dlFiles[li]= new FileListItem(files[li].getName(), localDir,
										files[li].isDirectory(), files[li].length(),
										files[li].lastModified()
										);
							}
							putAllFiles(indicator,dlFiles,localDir );

						}
						
						
						ftp.changeToParentDirectory();
					} else {
						throw new SSHException(ftp.getReplyString());
					}
				} catch (Exception e) {
					mErrorLog.logError(e);
				}
				// handle latter
			} else {
				if( fi.getSize()<=0){
					mErrorLog.logError(new SSHException("Can't copy file directory:"+curDir + fi.getName()));
					continue;
				}
				
				InputStream is = null;
				OutputStream os = null;
				try {
					File localFile = new File(curDir + fi.getName());
					os = ftp.storeFileStream(fi.getName());
					if( os!=null ){
						is = new FileInputStream(localFile);
						byte[] buffer = new byte[4096];
						int len;
						long total = 0;
						long fSize  = fi.getSize();
						indicator.startFile(fi.getName(), fSize);
						while ((len = is.read(buffer)) >= 0) {
							if (len > 0) {
								os.write(buffer, 0, len);
								total += len;
								indicator.progress(len);
							}
						}
						indicator.endFile();
					}else{
						mErrorLog.logError(new SSHException(ftp.getReplyString()));
					}
				} catch (Exception e) {
					mErrorLog.logError(e);
					// TODO:
					e.printStackTrace();
				} finally {
					if (is != null) {
						try {
							is.close();
						} catch (Exception e) {
						}
						;
					}
					if (os != null) {
						try {
							os.close();
						} catch (Exception e) {
						}
						;
					}
					try{
						ftp.completePendingCommand();
					}catch(Exception e){
						
					}
				}
			}
			
		}
		
	}

	public void putFiles(SSHSCPIndicator indicator,FileDisplay remoteDisplay, FileDisplay localDisplay,
			boolean isBinary) {
		FileListItem[] lFiles = localDisplay.getSelectedFiles();
		if (lFiles != null && lFiles.length > 0) {
			try {
				if (isBinary) {
					ftp.setFileType(FTP.BINARY_FILE_TYPE);
				} else {
					ftp.setFileType(FTP.ASCII_FILE_TYPE);
				}
				String curDir = localDisplay.getFileSystemLocation();//.getFileSystemLocationLabelText();
				if (curDir == null)
					curDir = "/";
				else {
					int len = curDir.length();
					if (curDir.charAt(len - 1) != '/'
							&& curDir.charAt(len - 1) != '\\') {
						curDir += "/";
					}
				}
				putAllFiles(indicator,lFiles, curDir );
			} catch (Exception e) {
				mErrorLog.logError(e);
			}
			try {
				changeDirectory(this.currentPath);
			} catch (Exception e) {
				mErrorLog.logError(e);
			}

		}

	}
	public void abort(){
		try{ftp.abort();}catch(Exception e){};
	}

}
