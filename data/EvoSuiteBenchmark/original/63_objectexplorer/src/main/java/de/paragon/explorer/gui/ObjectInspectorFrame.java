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
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class ObjectInspectorFrame extends JFrame implements ListSelectionListener {
	private static final long	serialVersionUID	= -3077152612306894110L;

	public static void main(String[] args) {
	}

	private JList	list;
	private JLabel	label;

	/**
	 * ObjectInspectorFrame constructor comment.
	 */
	public ObjectInspectorFrame() {
		super();
		// Create the list and put it in a scroll pane
		this.list = new JList();
		this.list.setSelectedIndex(0);
		this.list.addListSelectionListener(this);
		JScrollPane listScrollPane = new JScrollPane(this.list);
		Container contentPane = this.getContentPane();
		contentPane.add(listScrollPane, BorderLayout.CENTER);
	}

	/**
	 * ObjectInspectorFrame constructor comment.
	 * 
	 * @param title
	 *            String
	 */
	public ObjectInspectorFrame(String title) {
		super(title);
	}

	/**
	 * ObjectInspectorFrame constructor comment.
	 */
	public ObjectInspectorFrame(Vector<?> v1) {
		super();
		// Create the label
		this.label = new JLabel("Attribute");
		// Create the list and put it in a scroll pane
		this.list = new JList();
		this.list.setListData(v1);
		this.list.setSelectedIndex(0);
		this.list.addListSelectionListener(this);
		JScrollPane listScrollPane = new JScrollPane(this.list);
		Container contentPane = this.getContentPane();
		contentPane.add(listScrollPane, BorderLayout.CENTER);
		contentPane.add(this.label, BorderLayout.NORTH);
		this.pack();
		this.setVisible(true);
	}

	public void valueChanged(ListSelectionEvent e) {
		// if (!e.getValueIsAdjusting()) {
		// if (this.list.getSelectedIndex() == -1) {
		// // No selection, disable fire button.
		// // fireButton.setEnabled(false);
		// // employeeName.setText("");
		// }
		// else {
		// // Selection, update text field.
		// // fireButton.setEnabled(true);
		// // String name = list.getSelectedValue().toString();
		// // employeeName.setText(name);
		// }
		// }
	}
}
