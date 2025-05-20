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

import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import de.paragon.explorer.util.LoggerFactory;

public class ObjectCopyDialog extends JDialog {
	private static final long	serialVersionUID	= -4982214378670175390L;
	private static final Logger	logger				= LoggerFactory.make();

	/**
	 * main entrypoint - starts the part when it is run as an application
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		try {
			ObjectCopyDialog aObjectCopyDialog;
			aObjectCopyDialog = new ObjectCopyDialog();
			aObjectCopyDialog.setModal(true);
			aObjectCopyDialog.addWindowListener(new java.awt.event.WindowAdapter() {
				@Override
				public void windowClosing(java.awt.event.WindowEvent e) {
					System.exit(0);
				};
			});
			aObjectCopyDialog.setVisible(true);
			java.awt.Insets insets = aObjectCopyDialog.getInsets();
			aObjectCopyDialog.setSize(aObjectCopyDialog.getWidth() + insets.left + insets.right, aObjectCopyDialog.getHeight() + insets.top + insets.bottom);
			aObjectCopyDialog.setVisible(true);
		}
		catch (Throwable t) {
			ObjectCopyDialog.logger.error("Exception occurred in main() of ObjectCopyDialog", t);
		}
	}

	private JButton		ivjJButton1				= null;
	private JButton		ivjJButton2				= null;
	private JPanel		ivjJFrameContentPane	= null;
	private JLabel		ivjJLabel2				= null;
	private JList		ivjJList1				= null;
	private JScrollPane	ivjJScrollPane1			= null;

	/**
	 * ObjectMoveFrame constructor comment.
	 */
	public ObjectCopyDialog() {
		super();
		this.initialize();
	}

	/**
	 * ObjectMoveFrame constructor comment.
	 * 
	 * @param title
	 *            String
	 */
	public ObjectCopyDialog(java.awt.Frame owner) {
		super(owner);
		this.initialize();
	}

	/**
	 * ObjectMoveFrame constructor comment.
	 * 
	 * @param title
	 *            String
	 */
	public ObjectCopyDialog(java.awt.Frame owner, boolean modal) {
		super(owner, modal);
		this.initialize();
	}

	public void addCancelButtonActionListener(java.awt.event.ActionListener param) {
		this.getJButton2().addActionListener(param);
	}

	public void addListSelectionListener(ListSelectionListener param) {
		this.getJList1().getSelectionModel().addListSelectionListener(param);
	}

	public void addOKButtonActionListener(java.awt.event.ActionListener param) {
		this.getJButton1().addActionListener(param);
	}

	/**
	 * Return the JButton1 property value.
	 * 
	 * @return javax.swing.JButton
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private JButton getJButton1() {
		if (this.ivjJButton1 == null) {
			try {
				this.ivjJButton1 = new javax.swing.JButton();
				this.ivjJButton1.setName("JButton1");
				this.ivjJButton1.setText("OK");
				this.ivjJButton1.setActionCommand("ok");
				this.ivjJButton1.setPreferredSize(new java.awt.Dimension(100, 25));
				// user code begin {1}
				// user code end
			}
			catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				this.handleException(ivjExc);
			}
		}
		return this.ivjJButton1;
	}

	/**
	 * Return the JButton2 property value.
	 * 
	 * @return javax.swing.JButton
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private JButton getJButton2() {
		if (this.ivjJButton2 == null) {
			try {
				this.ivjJButton2 = new javax.swing.JButton();
				this.ivjJButton2.setName("JButton2");
				this.ivjJButton2.setText("Cancel");
				this.ivjJButton2.setActionCommand("cancel");
				this.ivjJButton2.setPreferredSize(new java.awt.Dimension(100, 25));
				// user code begin {1}
				// user code end
			}
			catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				this.handleException(ivjExc);
			}
		}
		return this.ivjJButton2;
	}

	/**
	 * Return the JFrameContentPane property value.
	 * 
	 * @return javax.swing.JPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private JPanel getJFrameContentPane() {
		if (this.ivjJFrameContentPane == null) {
			try {
				this.ivjJFrameContentPane = new javax.swing.JPanel();
				this.ivjJFrameContentPane.setName("JFrameContentPane");
				this.ivjJFrameContentPane.setLayout(new java.awt.GridBagLayout());
				java.awt.GridBagConstraints constraintsJScrollPane1 = new java.awt.GridBagConstraints();
				constraintsJScrollPane1.gridx = 1;
				constraintsJScrollPane1.gridy = 2;
				constraintsJScrollPane1.gridwidth = 2;
				constraintsJScrollPane1.fill = java.awt.GridBagConstraints.BOTH;
				constraintsJScrollPane1.weightx = 1.0;
				constraintsJScrollPane1.weighty = 1.0;
				constraintsJScrollPane1.insets = new java.awt.Insets(0, 0, 0, 0);
				this.getJFrameContentPane().add(this.getJScrollPane1(), constraintsJScrollPane1);
				java.awt.GridBagConstraints constraintsJLabel2 = new java.awt.GridBagConstraints();
				constraintsJLabel2.gridx = 1;
				constraintsJLabel2.gridy = 1;
				constraintsJLabel2.gridwidth = 2;
				constraintsJLabel2.insets = new java.awt.Insets(0, 0, 0, 0);
				this.getJFrameContentPane().add(this.getJLabel2(), constraintsJLabel2);
				java.awt.GridBagConstraints constraintsJButton1 = new java.awt.GridBagConstraints();
				constraintsJButton1.gridx = 1;
				constraintsJButton1.gridy = 3;
				constraintsJButton1.weightx = 1;
				constraintsJButton1.anchor = java.awt.GridBagConstraints.SOUTHEAST;
				constraintsJButton1.insets = new java.awt.Insets(4, 4, 4, 4);
				this.getJFrameContentPane().add(this.getJButton1(), constraintsJButton1);
				java.awt.GridBagConstraints constraintsJButton2 = new java.awt.GridBagConstraints();
				constraintsJButton2.gridx = 2;
				constraintsJButton2.gridy = 3;
				constraintsJButton2.insets = new java.awt.Insets(4, 4, 4, 4);
				this.getJFrameContentPane().add(this.getJButton2(), constraintsJButton2);
				// user code begin {1}
				// user code end
			}
			catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				this.handleException(ivjExc);
			}
		}
		return this.ivjJFrameContentPane;
	}

	/**
	 * Return the JLabel2 property value.
	 * 
	 * @return javax.swing.JLabel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private JLabel getJLabel2() {
		if (this.ivjJLabel2 == null) {
			try {
				this.ivjJLabel2 = new javax.swing.JLabel();
				this.ivjJLabel2.setName("JLabel2");
				this.ivjJLabel2.setFont(new java.awt.Font("dialog", 0, 12));
				this.ivjJLabel2.setText("copy object to:");
				this.ivjJLabel2.setForeground(java.awt.Color.black);
				// user code begin {1}
				// user code end
			}
			catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				this.handleException(ivjExc);
			}
		}
		return this.ivjJLabel2;
	}

	/**
	 * Return the JList1 property value.
	 * 
	 * @return javax.swing.JList
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private JList getJList1() {
		if (this.ivjJList1 == null) {
			try {
				this.ivjJList1 = new javax.swing.JList();
				this.ivjJList1.setName("JList1");
				this.ivjJList1.setBounds(0, 0, 257, 156);
				// user code begin {1}
				// user code end
			}
			catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				this.handleException(ivjExc);
			}
		}
		return this.ivjJList1;
	}

	/**
	 * Return the JScrollPane1 property value.
	 * 
	 * @return javax.swing.JScrollPane
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private JScrollPane getJScrollPane1() {
		if (this.ivjJScrollPane1 == null) {
			try {
				this.ivjJScrollPane1 = new javax.swing.JScrollPane();
				this.ivjJScrollPane1.setName("JScrollPane1");
				this.getJScrollPane1().setViewportView(this.getJList1());
				// user code begin {1}
				// user code end
			}
			catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				this.handleException(ivjExc);
			}
		}
		return this.ivjJScrollPane1;
	}

	public final ListSelectionModel getSelectionModel() {
		return this.getJList1().getSelectionModel();
	}

	/**
	 * Called whenever the part throws an exception.
	 * 
	 * @param exception
	 *            Throwable
	 */
	private void handleException(Throwable exception) {
		/* Uncomment the following lines to print uncaught exceptions to stdout */
		// System.out.println("--------- UNCAUGHT EXCEPTION ---------");
		// exception.printStackTrace(System.out);
	}

	/**
	 * Initialize the class.
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private void initialize() {
		try {
			// user code begin {1}
			// user code end
			this.setName("ObjectMoveFrame");
			this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
			this.setSize(300, 300);
			this.setForeground(java.awt.SystemColor.textHighlightText);
			this.setContentPane(this.getJFrameContentPane());
			// setResizable(false);
		}
		catch (Throwable ivjExc) {
			this.handleException(ivjExc);
		}
		// user code begin {2}
		// user code end
	}

	public void setListData(Vector<?> param) {
		this.getJList1().setListData(param);
	}

	public void setOKButtonEnabled(boolean value) {
		this.getJButton1().setEnabled(value);
	}
}
