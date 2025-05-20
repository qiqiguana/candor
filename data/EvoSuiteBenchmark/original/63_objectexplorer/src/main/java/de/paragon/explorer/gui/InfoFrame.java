/**
 * [ObjectExplorer4J - Tool zur grafischen Darstellung von Objekten und ihren
 * Referenzen]
 * 
 * Copyright (C) [2009] [PARAGON Systemhaus GmbH]
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, see <http://www.gnu.org/licenses/>.
 **/
package de.paragon.explorer.gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JTextField;

public class InfoFrame extends JFrame {
	private static final long	serialVersionUID	= 258324965674745663L;
	private JTextField			textfield;

	/**
	 * InfoFrame constructor comment.
	 */
	public InfoFrame() {
		super();
		this.initialize();
	}

	/**
	 * InfoFrame constructor comment.
	 * 
	 * @param title
	 *            String
	 */
	public InfoFrame(String title) {
		super(title);
	}

	public JTextField getTextfield() {
		return this.textfield;
	}

	private void initialize() {
		this.textfield = new JTextField();
		Container contentPane = this.getContentPane();
		contentPane.add(this.textfield, BorderLayout.CENTER);
		this.pack();
		this.setVisible(true);
	}
}
