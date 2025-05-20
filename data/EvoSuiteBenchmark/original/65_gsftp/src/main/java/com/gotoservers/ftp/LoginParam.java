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

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mindbright.ssh.SSHPropertyHandler;

//
// The panel which implements the user authentication scheme
//

public class LoginParam extends Panel implements ActionListener { // Dialog
																	// implements
																	// ActionListener
																	// {

	private Label promptServer;

	private TextField server;

	private Label promptPort;

	private TextField port;

	private Label promptusername;

	private TextField username;

	private Label promptpassword;

	private TextField password;

	private Label error;

	private Button okButton;

	SSHPropertyHandler ph;
	FtpApplet parent;

	//
	// Constructor.
	//

	public LoginParam(FtpApplet parent, SSHPropertyHandler ph) {
		this.parent = parent;
		this.ph = ph;
		promptServer = new Label("FTP Server:", Label.RIGHT);
		server = new TextField(20);
		server.setText(ph.getRemoteServer());

		promptPort = new Label("FTP Port:", Label.RIGHT);
		port = new TextField(3);
		int p = ph.getRemotePort();
		if (p > 0) {
			port.setText(String.valueOf(p));
		} else {
			ph.setRemotePort(21);
			port.setText("21");
		}

		promptusername = new Label("Username:", Label.RIGHT);
		username = new TextField(10);
		username.setText(ph.getRemoteUser());
		promptpassword = new Label("Password:", Label.RIGHT);
		password = new TextField(10);
		password.setText(ph.getRemotePassword());
		password.setForeground(Color.black);
		password.setBackground(Color.white);
		password.setEchoChar('*');
		error = new Label("");
		error.setForeground(Color.red);

		okButton = new Button("Login");
		okButton.addActionListener(this);
		
		// setDefaultButton(new JButton("ok"));
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints();

		// panel.
		setLayout(gridbag);

		gbc.fill = GridBagConstraints.NONE;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(error, gbc);
		add(error);

		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridwidth = 1;
		gridbag.setConstraints(promptServer, gbc);
		add(promptServer);
		gbc.gridwidth = 1;
		gridbag.setConstraints(server, gbc);
		add(server);

		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridwidth = 1;
		gridbag.setConstraints(promptPort, gbc);
		add(promptPort);
		gbc.gridwidth = 1;
		gridbag.setConstraints(port, gbc);
		add(port);

		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridwidth = 1;
		gridbag.setConstraints(promptusername, gbc);
		add(promptusername);
		gbc.gridwidth = 1;
		gridbag.setConstraints(username, gbc);
		add(username);

		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridwidth = 1;
		gridbag.setConstraints(promptpassword, gbc);
		add(promptpassword);
		gbc.gridwidth = 1;
		gridbag.setConstraints(password, gbc);
		add(password);

		//gbc.ipady = 10;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.BOTH;
		//gbc.insets = new Insets(0, 20, 0, 0);
		//gbc.ipadx = 40;
		gridbag.setConstraints(okButton, gbc);
		add(okButton);

		//this.setSize(new Dimension(500, 300));
		setVisible(true);
		validate();
	}

	public Dimension getPreferredSize() {
		return new Dimension(600, 48);

	}

	public void actionPerformed(ActionEvent e) {
		try {
			String temp = server.getText();
			if (temp == null || temp.trim().length() == 0) {
				error.setBackground(Color.yellow);
				error.setText("Please fill FTP Server");
				server.requestFocus();
				this.validate();
				return;
			}
			temp = port.getText();
			int p = 0;
			if (temp == null || temp.trim().length() == 0) {
				error.setBackground(Color.yellow);
				error.setText("Please fill FTP Port");
				port.requestFocus();
				this.validate();
				return;
			} else {
				try {
					p = Integer.parseInt(temp);
				} catch (Exception ex) {
				}
			}
			if (p <= 0) {
				error.setBackground(Color.yellow);
				error.setText("Please enter integer for FTP Port");
				port.requestFocus();
				this.validate();
				return;
			}
			temp = username.getText();
			if (temp == null || temp.trim().length() == 0) {
				error.setBackground(Color.yellow);
				error.setText("Please fill Username");
				username.requestFocus();
				this.validate();
				return;
			}
			temp = password.getText();
			if (temp == null || temp.trim().length() == 0) {
				error.setBackground(Color.yellow);
				error.setText("Please fill Password");
				password.requestFocus();
				this.validate();
				return;
			}
			
			ph.setRemotePassword(password.getText());
			ph.setRemotePort(p);
			ph.setRemoteServer(server.getText());
			ph.setRemoteUser(username.getText());
			parent.login();
			
		} catch (Exception ez) {
		}
	}
}
