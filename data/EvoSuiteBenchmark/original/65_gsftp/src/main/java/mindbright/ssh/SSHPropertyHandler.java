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
 * $Date: 2006/11/06 00:21:55 $
 * $Name:  $
 *****************************************************************************/
package mindbright.ssh;

import java.awt.Frame;



public final class SSHPropertyHandler {//implements SSHClientUser, SSHAuthenticator, ProxyAuthenticator {
	String	remoteServer = "localhost";//"192.168.1.2";
	int		remotePort = 21;
	String 	remoteUser;//"test";
	String  remotePassword;//"123456";
	
	
	String	sshHomeDir;
	Frame   parent; 	
	boolean hasBinay = true;
	public String getSSHHomeDir() {
		if( sshHomeDir==null)
			sshHomeDir = "/";
		return sshHomeDir;
	}
	public void setSSHHomeDir(String homeDir) {
		sshHomeDir = homeDir;
	}
	public String getRemotePassword() {
		return remotePassword;
	}
	public void setRemotePassword(String remotePassword) {
		this.remotePassword = remotePassword;
	}
	public int getRemotePort() {
		return remotePort;
	}
	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}
	public String getRemoteServer() {
		return remoteServer;
	}
	public void setRemoteServer(String remoteServer) {
		this.remoteServer = remoteServer;
	}
	public String getRemoteUser() {
		return remoteUser;
	}
	public void setRemoteUser(String remoteUser) {
		this.remoteUser = remoteUser;
	}
	public String getSshHomeDir() {
		return sshHomeDir;
	}
	public void setSshHomeDir(String sshHomeDir) {
		this.sshHomeDir = sshHomeDir;
	}
	public Frame getParent() {
		return parent;
	}
	public void setParent(Frame parent) {
		this.parent = parent;
	}
	public boolean hasBinay() {
		return hasBinay;
	}
	public void setHasBinay(boolean hasBinay) {
		this.hasBinay = hasBinay;
	}
}
