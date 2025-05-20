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
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.event.HyperlinkListener;

import de.paragon.explorer.util.ResourceBundlePurchaser;

public class AboutDialog extends JDialog {
	private static final String	BR									= "<br>";
	private static final long	serialVersionUID					= 8806810423040225078L;
	private JPanel				jContentPane						= null;
	private JPanel				jPanel								= null;
	private JEditorPane			jEditorPane							= null;
	private JButton				jButton								= null;
	private String				versionNumber						= ResourceBundlePurchaser.getMessage("aboutdialog.version");
	private static final String	ABOUT_PARAKLET_OBJECT_EXPLORER4J	= "About PARAKLET ObjectExplorer4J";

	public AboutDialog() {
		super();
		this.initialize();
	}

	public AboutDialog(Dialog owner) {
		super(owner);
		this.initialize();
	}

	public AboutDialog(Dialog owner, boolean modal) {
		super(owner, modal);
		this.initialize();
	}

	public AboutDialog(Frame owner) {
		super(owner);
		this.initialize();
	}

	public AboutDialog(Frame owner, boolean modal) {
		super(owner, modal);
		this.initialize();
	}

	private void initialize() {
		this.setTitle(AboutDialog.ABOUT_PARAKLET_OBJECT_EXPLORER4J);
		this.setContentPane(this.getJContentPane());
		this.setSize(610, 235);
	}

	private javax.swing.JPanel getJContentPane() {
		if (this.jContentPane == null) {
			this.jContentPane = new JPanel();
			this.jContentPane.setLayout(new BorderLayout());
			this.jContentPane.add(this.getJPanel(), BorderLayout.SOUTH);
			this.jContentPane.add(this.getJEditorPane(), BorderLayout.CENTER);
		}
		return this.jContentPane;
	}

	private javax.swing.JPanel getJPanel() {
		if (this.jPanel == null) {
			this.jPanel = new JPanel();
			this.jPanel.setLayout(new java.awt.BorderLayout());
			this.jPanel.add(this.getJButton(), BorderLayout.EAST);
			this.jPanel.setPreferredSize(new Dimension(10, 25));
			this.jPanel.setBackground(SystemColor.activeCaptionBorder);
		}
		return this.jPanel;
	}

	private JEditorPane getJEditorPane() {
		if (this.jEditorPane == null) {
			this.jEditorPane = new JEditorPane();
			this.jEditorPane.setContentType("text/html");
			this.jEditorPane.setText(this.getText4AboutDialog());
			this.jEditorPane.setEditable(false);
			this.jEditorPane.setBackground(SystemColor.control);
		}
		return this.jEditorPane;
	}

	private String getText4AboutDialog() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		StringBuilder sb = new StringBuilder();
		sb.append("<html>");
		sb.append("<body color=\"#000000\">");
		sb.append("<table border=\"0\">");
		sb.append("<tr>");
		sb.append("<td>");
		sb.append("<img src=\"file:\\");
		sb.append(new File("src/images/infoSplash.jpg").getAbsolutePath());
		sb.append("\"></img>");
		sb.append("</td>");
		sb.append("<td>");
		sb.append("PARAKLET ObjectExplorer4J");
		sb.append(AboutDialog.BR);
		sb.append("Version: ");
		sb.append(this.getVersionNumber());
		sb.append(AboutDialog.BR);
		sb.append(AboutDialog.BR);
		sb.append("(c) PARAGON Systemhaus GmbH 2003 - ");
		sb.append(year);
		sb.append(AboutDialog.BR);
		sb.append(AboutDialog.BR);
		sb.append("Visit: ");
		sb.append("<a href=\"http://www.paragon-systemhaus.de\">http://www.paragon-systemhaus.de</a>");
		sb.append(AboutDialog.BR);
		sb.append("Mail: ");
		sb.append("<a href=\"mailto:info@paragon-systemhaus.de\">info@paragon-systemhaus.de</a>");
		sb.append("</td>");
		sb.append("</tr>");
		sb.append("</table>");
		sb.append("</body>");
		sb.append("</html>");
		return sb.toString();
	}

	public void addActionListener(ActionListener anActionListener) {
		this.getJButton().addActionListener(anActionListener);
		this.getJEditorPane().addHyperlinkListener((HyperlinkListener) anActionListener);
	}

	private JButton getJButton() {
		if (this.jButton == null) {
			this.jButton = new JButton();
			this.jButton.setText("OK");
			this.jButton.setPreferredSize(new Dimension(100, 25));
		}
		return this.jButton;
	}

	private String getVersionNumber() {
		return this.versionNumber;
	}
}
