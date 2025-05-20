/******************************************************************************
 *
 * Copyright (c) 1998,99 by Mindbright Technology AB, Stockholm, Sweden.
 *                 www.mindbright.se, info@mindbright.se
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
 *
 *****************************************************************************
 * $Author: webjabber $
 * $Date: 2006/06/13 18:43:42 $
 * $Name:  $
 *****************************************************************************/
package mindbright.ssh;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import com.gotoservers.ftp.RemoteFileBrowser;
import com.isnetworks.ssh.FileDisplay;

import mindbright.gui.ProgressBar;
import mindbright.util.AWTConvenience;

public final class SSHSCPGUIThread implements Runnable, SSHSCPIndicator {
	//String curDir, localFile, remoteFile;
	String curDir ="/";
	//String remoteHost;

	//int remotePort;

	// //SSHAuthenticator authenticator;
	// //SSHClientUser mainUser;
	// //SSHInteractor interactor;
	boolean recursive, background, toRemote;

	Frame parent;

	SSHSCPPanel mRefreshDialog;

	//String[] localFileList;

	Dialog copyIndicator;

	ProgressBar progress;

	// //SSHSCP scp;
	Thread copyThread;

	Label srcLbl, dstLbl, sizeLbl, nameLbl, speedLbl;

	Button cancB;

	long startTime;

	long lastTime;

	long totTransSize;

	long fileTransSize;

	long curFileSize;

	long lastSize;

	int fileCnt;
	
	boolean isBinary;

	boolean doneCopying;
	RemoteFileBrowser mRemoteFileBrowser;
	FileDisplay mLocalFileDisplay;
	FileDisplay mRemoteFileDisplay;
	public SSHSCPGUIThread(
	// //String remoteHost, int remotePort,
			// //SSHAuthenticator authenticator,
			// //SSHClientUser mainUser, SSHInteractor interactor,
			//Frame parent, String curDir, String localFile, String remoteFile,
			Frame parent,
			FileDisplay localFileDisplay,
			FileDisplay remoteFileDisplay,
			RemoteFileBrowser remoteFileBrowser,
			
			boolean recursive, 
			boolean background, 
			boolean toRemote,
			boolean isBinary,
			SSHSCPPanel dialog) throws Exception {
		// super(SSH.getThreadGroup(), SSH.createThreadName()); // JH_Mod
		this.mLocalFileDisplay = localFileDisplay;
		this.mRemoteFileDisplay = remoteFileDisplay;
		this.mRemoteFileBrowser = remoteFileBrowser;
		this.isBinary = isBinary;
		this.parent = parent;
		this.recursive = recursive;
		this.background = background;
		this.toRemote = toRemote;

		this.fileCnt = 0;
		this.doneCopying = false;
		this.startTime = 0;
		this.lastTime = 0;
		this.totTransSize = 0;
		this.fileTransSize = 0;
		this.lastSize = 0;
		this.mRefreshDialog = dialog;
		// this.start();
	}

	private void setupDialog() {
		String sourceFile = "Local:";
		String destFile = "Remote:";

		if (!toRemote) {
			String tmp;
			tmp = sourceFile;
			sourceFile = destFile;
			destFile = tmp;
		}

		copyIndicator = new Dialog(parent, "GoToServers - File Transfer", false);

		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints gridc = new GridBagConstraints();
		Label lbl;
		Button b;

		copyIndicator.setLayout(grid);

		gridc.fill = GridBagConstraints.HORIZONTAL;
		gridc.anchor = GridBagConstraints.WEST;
		gridc.gridy = 0;
		gridc.gridwidth = 1;
		gridc.insets = new Insets(4, 4, 4, 4);

		lbl = new Label("Source:");
		grid.setConstraints(lbl, gridc);
		copyIndicator.add(lbl);

		gridc.gridwidth = 4;
		srcLbl = new Label(cutName(sourceFile, 32));
		grid.setConstraints(srcLbl, gridc);
		copyIndicator.add(srcLbl);

		gridc.gridy = 1;
		gridc.gridwidth = 1;

		lbl = new Label("Destination:");
		grid.setConstraints(lbl, gridc);
		copyIndicator.add(lbl);

		gridc.gridwidth = 4;
		dstLbl = new Label(cutName(destFile, 32));
		grid.setConstraints(dstLbl, gridc);
		copyIndicator.add(dstLbl);

		gridc.gridy = 2;

		gridc.gridwidth = 1;
		lbl = new Label("Current:");
		grid.setConstraints(lbl, gridc);
		copyIndicator.add(lbl);

		gridc.gridwidth = 3;
		nameLbl = new Label("connecting...");
		grid.setConstraints(nameLbl, gridc);
		copyIndicator.add(nameLbl);

		gridc.gridwidth = 1;
		sizeLbl = new Label("");
		grid.setConstraints(sizeLbl, gridc);
		copyIndicator.add(sizeLbl);

		gridc.gridy = 3;
		gridc.gridwidth = 3;
		gridc.fill = GridBagConstraints.NONE;
		gridc.anchor = GridBagConstraints.CENTER;
		gridc.insets = new Insets(4, 12, 4, 4);
		progress = new ProgressBar(512, 160, 20);
		grid.setConstraints(progress, gridc);
		copyIndicator.add(progress);

		gridc.gridwidth = GridBagConstraints.REMAINDER;
		gridc.insets = new Insets(4, 4, 4, 4);
		gridc.fill = GridBagConstraints.HORIZONTAL;
		speedLbl = new Label("0.0 kB/sec", Label.CENTER);
		grid.setConstraints(speedLbl, gridc);
		copyIndicator.add(speedLbl);

		gridc.gridy = 4;
		cancB = new Button("Cancel");
		cancB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!doneCopying) {
					if (copyThread != null)
						copyThread.stop();
					if (mRemoteFileBrowser != null)
						mRemoteFileBrowser.abort();
				}
				copyIndicator.setVisible(false);
			}
		});

		gridc.fill = GridBagConstraints.NONE;
		gridc.gridwidth = GridBagConstraints.REMAINDER;
		gridc.anchor = GridBagConstraints.CENTER;
		gridc.ipady = 2;
		gridc.ipadx = 2;
		grid.setConstraints(cancB, gridc);
		copyIndicator.add(cancB);

		AWTConvenience.setBackgroundOfChildren(copyIndicator);

		Dimension d = speedLbl.getSize();
		d.width += d.width * 2;
		speedLbl.setSize(d);
		sizeLbl.setSize(d);

		copyIndicator.setResizable(true);
		copyIndicator.pack();
		AWTConvenience.placeDialog(copyIndicator);
	}

	public void run() {
		setupDialog();
		// copyThread = new Thread(new Runnable() {
		copyThread = new Thread(SSHSCPPanel.getThreadGroup(), new Runnable() { // JH_Mod
					public void run() {

						try {
							if ( toRemote ){
								mRemoteFileBrowser.putFiles(SSHSCPGUIThread.this, 
										mRemoteFileDisplay,
										mLocalFileDisplay,
										isBinary
										);
							}else{
								mRemoteFileBrowser.getFiles(SSHSCPGUIThread.this, 
										mRemoteFileDisplay,
										mLocalFileDisplay,
										isBinary
										);
								
							}
							////scp = new SSHSCP(remoteHost, remotePort,
							////		authenticator, new File(curDir), recursive,
							////		false);
							////scp.setClientUser(mainUser);
							////scp.setInteractor(interactor);
							////scp.setIndicator(SSHSCPGUIThread.this);
							////if (toRemote) {
							////	scp.copyToRemote(localFileList, remoteFile);
							////} else {
							////	scp.copyToLocal(localFileList, remoteFile);
							///}
							copyThread.setPriority(Thread.NORM_PRIORITY);
							Toolkit.getDefaultToolkit().beep();
						} catch (Exception e) {
							//TODO:
							////interactor.alert("SCP Error: " + e.getMessage());
							System.out.println("SCP Error:");
							e.printStackTrace();
						}
						nameLbl.setText("Copied " + fileCnt + " file"
								+ (fileCnt != 1 ? "s" : "") + ".");
						double kSize = (double) totTransSize / 1024;
						sizeLbl.setText(round(kSize) + " kB");
						doneCopying = true;
						cancB.setLabel("Done");
						mRefreshDialog.refresh();

						AWTConvenience
								.setKeyListenerOfChildren(copyIndicator,
										new AWTConvenience.OKCancelAdapter(
												cancB, cancB), null);

					}
				});

		if (background) {
			copyThread.setPriority(Thread.MIN_PRIORITY);
		}

		copyThread.start();

		copyIndicator.setVisible(true);
	}

	public static String[] spaceSplit(String str) {
		if( str==null || str.length()<=0 )return null;
		
		int l = 0, r, cnt = 0;
		String[] list = new String[str.length() / 2];
		boolean lastIsQuoted = false;
		str = str.trim();
		while ((r = str.indexOf(' ', l)) >= 0) {
			if (str.charAt(l) == '"') {
				l += 1;
				r = str.indexOf('"', l);
				if (r == -1)
					return null;
			}
			String name = str.substring(l, r);
			if (name.endsWith(File.separator))
				name = name.substring(0, name.length() - 1);
			list[cnt++] = name;

			l = r;
			do {
				l++;
				if (l == str.length()) {
					lastIsQuoted = true;
					break;
				}
			} while (str.charAt(l) == ' ');
		}

		if (!lastIsQuoted) {
			if (str.charAt(l) == '"') {
				l += 1;
				r = str.indexOf('"', l);
				if (r == -1)
					return null;
			}
			String name = str.substring(l);
			if (name.endsWith(File.separator))
				name = name.substring(0, name.length() - 1);
			list[cnt++] = name;
		}

		for (int i = 0; i < cnt; i++) {
			if (list[i].endsWith("\"")) {
				list[i] = list[i].substring(0, list[i].length() - 1);
			}
		}

		String[] tmp = list;
		list = new String[cnt];
		System.arraycopy(tmp, 0, list, 0, cnt);

		return list;
	}

	public static String[] starExpand(String[] fileList, String curDir) {
		int i, j, n, cnt = 0;
		String[] newList = new String[4096]; // !!! Ouch...
		String[] curDirList = (new File(curDir)).list();
		String path, curFile;

		for (i = 0; i < fileList.length; i++) {
			curFile = fileList[i];
			path = "";
			n = curFile.indexOf('*');
			if (n == -1) {
				cnt = addUnique(newList, curFile, cnt);
				continue;
			}
			String[] dirList;
			File f = new File(curFile);
			if (!f.isAbsolute()) {
				dirList = curDirList;
			} else {
				String dir = f.getParent();
				if (dir == null)
					dir = new String(File.separator); // !!! Ouch...
				dirList = (new File(dir)).list();
				curFile = f.getName();
				path = dir + File.separator;
				n = curFile.indexOf('*');
			}

			String pre = curFile.substring(0, n);
			String post = curFile.substring(n + 1);
			for (j = 0; j < dirList.length; j++) {
				String name = dirList[j];
				if (name.startsWith(pre) && name.endsWith(post)) {
					cnt = addUnique(newList, path + name, cnt);
				}
			}
		}
		String[] tmp = newList;
		newList = new String[cnt];
		System.arraycopy(tmp, 0, newList, 0, cnt);
		return newList;
	}

	static int addUnique(String[] list, String str, int last) {
		int i;
		for (i = 0; i < last; i++)
			if (list[i].equals(str))
				break;
		if (i == last)
			list[last++] = str;
		return last;
	}

	public void connected(String server) {
		nameLbl.setText("...connected");
	}

	public void startFile(String file, long size) {
		double kSize = (double) size / 1024;
		sizeLbl.setText(round(kSize) + " kB");
		nameLbl.setText(file);
		if (toRemote) {
			srcLbl.setText(cutName("localhost:" + file, 32));
		} else {
			dstLbl.setText(cutName("localhost:" + file, 32));
		}
		progress.setMax(size, true);
		if (startTime == 0)
			startTime = System.currentTimeMillis();
		curFileSize = size;
		fileTransSize = 0;
		fileCnt++;
	}

	public void startDir(String file) {
		if (startTime == 0)
			startTime = System.currentTimeMillis();
		if (file.length() > curDir.length())
			file = file.substring(curDir.length());
		if (toRemote) {
			srcLbl.setText(cutName("localhost:" + file, 32));
		} else {
			dstLbl.setText(cutName("localhost:" + file, 32));
		}
	}

	public void endFile() {
		progress.setValue(curFileSize, true);
	}

	public void endDir() {
	}

	public void progress(long size) {
		totTransSize += size;
		fileTransSize += size;
		if ((curFileSize > 0)
				&& ((((totTransSize - lastSize) * 100) / curFileSize) >= 1)) {
			progress.setValue(fileTransSize, !background);
			long now = System.currentTimeMillis();
			long totSec = ((now - startTime) / 1000);
			double rate = (totSec != 0 ? (((double) totTransSize / 1024) / totSec)
					: 0.0);
			totSec = (now - lastTime);
			if (totSec != 0) {
				double rate2 = ((double) (totTransSize - lastSize) / 1024)
						/ totSec;
				rate = (rate + rate2) / 2.0;
			}
			speedLbl.setText("" + round(rate) + " kB/sec");
			lastSize = totTransSize;
			lastTime = now;
		}
	}

	double round(double val) {
		val = val * 10.0;
		val = Math.floor(val);
		val = val / 10.0;
		return val;
	}

	String cutName(String name, int len) {
		if (name.length() > len) {
			len -= 3;
			String pre = name.substring(0, len / 2);
			String suf = name.substring(name.length() - (len / 2));
			name = pre + "..." + suf;
		}
		return name;
	}

}
