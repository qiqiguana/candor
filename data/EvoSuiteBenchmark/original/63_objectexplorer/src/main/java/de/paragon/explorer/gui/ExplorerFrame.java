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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import de.paragon.explorer.Explorer;
import de.paragon.explorer.ProjectConstants4ObjectExplorer;
import de.paragon.explorer.event.GlassPaneEventConverter;
import de.paragon.explorer.event.RefreshListenerIfc;
import de.paragon.explorer.util.StandardEnumeration;
import de.paragon.explorer.util.StandardEnumerator;

public class ExplorerFrame extends JFrame {
	/**
	 * 
	 */
	private static final String	IMAGES_PGLOGO_BALLS_JPG	= "/images/PGLogoBalls.jpg";
	private static final long	serialVersionUID		= 4788729847368691601L;
	private StandardEnumeration	refreshListeners;
	private JScrollPane			scrollPane;
	// private JScrollPane textAreaScrollPane;
	private JTextField			statusTextfield;
	// private JTextArea modificationTextArea;
	private JMenu				windowMenu;
	private JMenuItem			menuItemObjectView;
	private JMenuItem			menuItemUpdateObjects;
	private JMenuItem			menuItemSave;
	private JMenuItem			menuItemAbout;

	/**
	 * Test2Frame constructor comment.
	 */
	public ExplorerFrame() {
		super();
		this.initialize();
	}

	public ExplorerFrame(boolean visible) {
		super();
		this.initialize();
	}

	public void addRefreshListener(RefreshListenerIfc l) {
		this.getRefreshListeners().addElement(l);
	}

	/**
	 * @return Returns the menuItemAbout.
	 */
	public JMenuItem getMenuItemAbout() {
		if (this.menuItemAbout == null) {
			this.menuItemAbout = new JMenuItem("About");
		}
		return this.menuItemAbout;
	}

	public JMenuItem getMenuItemSave() {
		if (this.menuItemSave == null) {
			this.menuItemSave = new JMenuItem("Save");
		}
		return this.menuItemSave;
	}

	/**
	 * @return Returns the menuItemObjectView.
	 */
	public JMenuItem getMenuItemObjectView() {
		if (this.menuItemObjectView == null) {
			this.menuItemObjectView = new JMenuItem("Object View");
		}
		return this.menuItemObjectView;
	}

	/**
	 * @return Returns the menuItemUpdateObjects.
	 */
	public JMenuItem getMenuItemUpdateObjects() {
		if (this.menuItemUpdateObjects == null) {
			this.menuItemUpdateObjects = new JMenuItem(ProjectConstants4ObjectExplorer.getUPDATE_OBJECTS());
		}
		return this.menuItemUpdateObjects;
	}

	private StandardEnumeration getRefreshListeners() {
		if (this.refreshListeners == null) {
			this.setRefreshListeners(new StandardEnumerator());
		}
		return this.refreshListeners;
	}

	public JScrollPane getScrollPane() {
		if (this.scrollPane == null) {
			JScrollPane newScrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			newScrollPane.getHorizontalScrollBar().setUnitIncrement(10);
			newScrollPane.getVerticalScrollBar().setUnitIncrement(10);
			this.setScrollPane(newScrollPane);
		}
		return this.scrollPane;
	}

	public JTextField getStatusTextfield() {
		if (this.statusTextfield == null) {
			this.setTextfield(new JTextField());
		}
		return this.statusTextfield;
	}

	public JMenu getWindowMenu() {
		return this.windowMenu;
	}

	private void initialize() {
		// MenuBar
		this.initializeMenuBar();
		// initilize ScrollPane
		this.getScrollPane().setPreferredSize(new Dimension(800, 600));
		this.getScrollPane().setBackground(java.awt.Color.lightGray);
		this.getScrollPane().setOpaque(true);
		// initialize Textfield
		this.getStatusTextfield().setEnabled(false);
		this.getStatusTextfield().setBackground(Color.lightGray);
		this.getStatusTextfield().setDisabledTextColor(java.awt.Color.black);
		this.getStatusTextfield().setPreferredSize(new Dimension(120, 20));
		this.setIconImage(this.getToolkit().createImage(this.getClass().getResource(ExplorerFrame.IMAGES_PGLOGO_BALLS_JPG)));
		// Initialitze Frame
		this.getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints c1 = new GridBagConstraints();
		c1.weightx = 1;
		c1.weighty = 1;
		c1.gridy = 0;
		c1.gridx = 0;
		c1.fill = java.awt.GridBagConstraints.BOTH;
		c1.weightx = 1.0D;
		c1.weighty = 1.0D;
		GridBagConstraints c2 = new GridBagConstraints();
		c2.weightx = 1;
		c2.weighty = 0;
		c2.gridy = 1;
		c2.gridx = 0;
		c2.fill = java.awt.GridBagConstraints.HORIZONTAL;
		GridBagConstraints c3 = new GridBagConstraints();
		c3.weightx = 1;
		c3.weighty = 0;
		c3.gridy = 2;
		c3.gridx = 0;
		c3.fill = java.awt.GridBagConstraints.HORIZONTAL;
		this.getContentPane().add(this.getScrollPane(), c1);
		this.getContentPane().add(this.getStatusTextfield(), c2);
		this.getGlassPane().addMouseListener(new GlassPaneEventConverter());
		this.pack();
		if (!Explorer.isEmbedded()) {
			this.setVisible(true);
		}
	}

	private void initializeMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu window = new JMenu("Window");
		window.add(this.getMenuItemObjectView());
		window.add(this.getMenuItemUpdateObjects());
		// window.getItem(0).setEnabled(true);
		this.setWindowMenu(window);
		menuBar.add(window);
		JMenu help = new JMenu("Help");
		help.add(this.getMenuItemSave());
		help.addSeparator();
		help.add(this.getMenuItemAbout());
		menuBar.add(help);
		this.setJMenuBar(menuBar);
	}

	private void setRefreshListeners(de.paragon.explorer.util.StandardEnumeration newRefreshListeners) {
		this.refreshListeners = newRefreshListeners;
	}

	private void setScrollPane(JScrollPane newScrollPane) {
		this.scrollPane = newScrollPane;
	}

	private void setTextfield(JTextField newStatusTextfield) {
		this.statusTextfield = newStatusTextfield;
	}

	private void setWindowMenu(javax.swing.JMenu newWindowMenu) {
		this.windowMenu = newWindowMenu;
	}

	/**
	 * Returns a String that represents the value of this object.
	 * 
	 * @return a string representation of the receiver
	 */
	@Override
	public String toString() {
		// Insert code to print the receiver here.
		// This implementation forwards the message to super. You may replace or
		// supplement this.
		return super.toString();
	}
}
