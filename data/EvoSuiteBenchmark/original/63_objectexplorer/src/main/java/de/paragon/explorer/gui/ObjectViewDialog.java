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

import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.Logger;

import de.paragon.explorer.ProjectConstants4ObjectExplorer;
import de.paragon.explorer.util.LoggerFactory;

public class ObjectViewDialog extends JDialog {
	private static final Logger	logger				= LoggerFactory.make();
	private static final long	serialVersionUID	= 1608238919704602028L;
	private static String		CLOSE				= ProjectConstants4ObjectExplorer.getOBJECT_VIEW_DIALOG_CLOSE();
	private static String		GO_TO				= ProjectConstants4ObjectExplorer.getOBJECT_VIEW_DIALOG_GO_TO();
	private static String		EXPLORE				= ProjectConstants4ObjectExplorer.getOBJECT_VIEW_DIALOG_EXPLORE();
	private static String		ADD					= ProjectConstants4ObjectExplorer.getOBJECT_VIEW_DIALOG_ADD();
	private static String		REMOVE				= ProjectConstants4ObjectExplorer.getOBJECT_VIEW_DIALOG_REMOVE();

	/**
	 * main entrypoint - starts the part when it is run as an application
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		try {
			ObjectViewDialog aObjectViewDialog;
			aObjectViewDialog = new ObjectViewDialog();
			aObjectViewDialog.setModal(true);
			aObjectViewDialog.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				};
			});
			// aObjectViewDialog.show();
			java.awt.Insets insets = aObjectViewDialog.getInsets();
			aObjectViewDialog.setSize(aObjectViewDialog.getWidth() + insets.left + insets.right, aObjectViewDialog.getHeight() + insets.top + insets.bottom);
			aObjectViewDialog.setVisible(true);
		}
		catch (Throwable t) {
			ObjectViewDialog.logger.error("Exception occurred in main() of ObjectViewDialog", t);
		}
	}

	private JPanel		ivjJDialogContentPane		= null;
	private JButton		ivjAddButton				= null;
	private JButton		ivjRemoveButton				= null;
	private JPanel		ivjPanelList1				= null;
	private JPanel		ivjPanelList2				= null;
	private JPanel		ivjPanelButtonLists			= null;
	private JPanel		ivjPanelAttributes			= null;
	// private JPanel ivjPanelOKCancel = null;
	private JList		ivjInvisibleList			= null;
	private JList		ivjVisibleList				= null;
	private JScrollPane	ivjScrollPaneInvisibleList	= null;
	private JScrollPane	ivjScrollPaneVisibleList	= null;
	private JTextField	ivjInfoText					= null;
	private JPanel		ivjPanelInfo				= null;
	private JButton		ivjCloseButton				= null;
	private JButton		ivjExploreButton			= null;
	private JButton		ivjGoToButton				= null;
	private JPanel		ivjPanelExploreGoTo			= null;

	/**
	 * ObjectViewDialog constructor comment.
	 */
	public ObjectViewDialog() {
		super();
		this.initialize();
	}

	/**
	 * ObjectViewDialog constructor comment.
	 * 
	 * @param owner
	 *            java.awt.Frame
	 * @param modal
	 *            boolean
	 */
	public ObjectViewDialog(java.awt.Frame owner, boolean modal) {
		super(owner, modal);
		this.initialize();
	}

	public void addButtonActionListener(java.awt.event.ActionListener listener) {
		this.getGoToButton().addActionListener(listener);
		this.getCloseButton().addActionListener(listener);
		this.getExploreButton().addActionListener(listener);
		// this.getDefaultsButton().addActionListener(listener);
		this.getAddButton().addActionListener(listener);
		this.getRemoveButton().addActionListener(listener);
	}

	public void addItemListener(java.awt.event.ItemListener listener) {
		// this.getCheckBoxShowValue().addItemListener(listener);
	}

	@Override
	public void addKeyListener(KeyListener listener) {
		KeyListener[] regsiterdListener = this.getInvisibleList().getKeyListeners();
		if (regsiterdListener[0] != null) {
			this.getInvisibleList().removeKeyListener(regsiterdListener[0]);
		}
		this.getInvisibleList().addKeyListener(listener);
		regsiterdListener = this.getVisibleList().getKeyListeners();
		if (regsiterdListener[0] != null) {
			this.getVisibleList().removeKeyListener(regsiterdListener[0]);
		}
		this.getVisibleList().addKeyListener(listener);
	}

	public void addListSelectionListener(ListSelectionListener param) {
		this.getInvisibleList().getSelectionModel().addListSelectionListener(param);
		this.getVisibleList().getSelectionModel().addListSelectionListener(param);
	}

	/**
	 * Return the JButton1 property value.
	 * 
	 * @return javax.swing.JButton
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public JButton getAddButton() {
		if (this.ivjAddButton == null) {
			try {
				this.ivjAddButton = new javax.swing.JButton();
				this.ivjAddButton.setName("AddButton");
				this.ivjAddButton.setText(">");
				this.ivjAddButton.setMaximumSize(new java.awt.Dimension(41, 25));
				this.ivjAddButton.setActionCommand(ObjectViewDialog.ADD);
				this.ivjAddButton.setPreferredSize(new java.awt.Dimension(41, 25));
				this.ivjAddButton.setMinimumSize(new java.awt.Dimension(41, 25));
				// user code begin {1}
				// user code end
			}
			catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				this.handleException(ivjExc);
			}
		}
		return this.ivjAddButton;
	}

	/**
	 * Return the JButton2 property value.
	 * 
	 * @return javax.swing.JButton
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private JButton getCloseButton() {
		if (this.ivjCloseButton == null) {
			try {
				this.ivjCloseButton = new javax.swing.JButton();
				this.ivjCloseButton.setName("CloseButton");
				this.ivjCloseButton.setText(ObjectViewDialog.CLOSE);
				this.ivjCloseButton.setMaximumSize(new java.awt.Dimension(85, 25));
				this.ivjCloseButton.setActionCommand(ObjectViewDialog.CLOSE);
				this.ivjCloseButton.setPreferredSize(new java.awt.Dimension(85, 25));
				this.ivjCloseButton.setFont(new java.awt.Font("dialog", 0, 12));
				this.ivjCloseButton.setMinimumSize(new java.awt.Dimension(85, 25));
				// user code begin {1}
				// user code end
			}
			catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				this.handleException(ivjExc);
			}
		}
		return this.ivjCloseButton;
	}

	/**
	 * Return the JButton4 property value.
	 * 
	 * @return javax.swing.JButton
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private JButton getExploreButton() {
		if (this.ivjExploreButton == null) {
			try {
				this.ivjExploreButton = new javax.swing.JButton();
				this.ivjExploreButton.setName("ExploreButton");
				this.ivjExploreButton.setText(ObjectViewDialog.EXPLORE);
				this.ivjExploreButton.setMaximumSize(new java.awt.Dimension(85, 25));
				this.ivjExploreButton.setActionCommand(ObjectViewDialog.EXPLORE);
				this.ivjExploreButton.setPreferredSize(new java.awt.Dimension(85, 25));
				this.ivjExploreButton.setFont(new java.awt.Font("dialog", 0, 12));
				this.ivjExploreButton.setMinimumSize(new java.awt.Dimension(85, 25));
				// user code begin {1}
				// user code end
			}
			catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				this.handleException(ivjExc);
			}
		}
		return this.ivjExploreButton;
	}

	/**
	 * Return the JButton1 property value.
	 * 
	 * @return javax.swing.JButton
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public JButton getGoToButton() {
		if (this.ivjGoToButton == null) {
			try {
				this.ivjGoToButton = new javax.swing.JButton();
				this.ivjGoToButton.setName("GoToButton");
				this.ivjGoToButton.setText(ObjectViewDialog.GO_TO);
				this.ivjGoToButton.setMaximumSize(new java.awt.Dimension(85, 25));
				this.ivjGoToButton.setActionCommand(ObjectViewDialog.GO_TO);
				this.ivjGoToButton.setPreferredSize(new java.awt.Dimension(85, 25));
				this.ivjGoToButton.setFont(new java.awt.Font("dialog", 0, 12));
				this.ivjGoToButton.setMinimumSize(new java.awt.Dimension(85, 25));
				this.ivjGoToButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
				// user code begin {1}
				// user code end
			}
			catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				this.handleException(ivjExc);
			}
		}
		return this.ivjGoToButton;
	}

	/**
	 * Return the InfoText property value.
	 * 
	 * @return javax.swing.JTextField
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public JTextField getInfoText() {
		if (this.ivjInfoText == null) {
			try {
				this.ivjInfoText = new javax.swing.JTextField();
				this.ivjInfoText.setName("InfoText");
				this.ivjInfoText.setAutoscrolls(true);
				this.ivjInfoText.setBackground(java.awt.Color.white);
				this.ivjInfoText.setDisabledTextColor(java.awt.Color.black);
				this.ivjInfoText.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
				this.ivjInfoText.setEditable(false);
				this.ivjInfoText.setHorizontalAlignment(SwingConstants.LEFT);
				// user code begin {1}
				// user code end
			}
			catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				this.handleException(ivjExc);
			}
		}
		return this.ivjInfoText;
	}

	/**
	 * Return the JList1 property value.
	 * 
	 * @return javax.swing.JList
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public JList getInvisibleList() {
		if (this.ivjInvisibleList == null) {
			try {
				this.ivjInvisibleList = new javax.swing.JList();
				this.ivjInvisibleList.setName("InvisibleList");
				this.ivjInvisibleList.setAutoscrolls(false);
				this.ivjInvisibleList.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
				this.ivjInvisibleList.setComponentOrientation(java.awt.ComponentOrientation.UNKNOWN);
				this.ivjInvisibleList.setBounds(0, 0, 85, 308);
				// user code begin {1}
				// user code end
			}
			catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				this.handleException(ivjExc);
			}
		}
		return this.ivjInvisibleList;
	}

	/**
	 * Return the JDialogContentPane property value.
	 * 
	 * @return javax.swing.JPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private JPanel getJDialogContentPane() {
		if (this.ivjJDialogContentPane == null) {
			try {
				this.ivjJDialogContentPane = new javax.swing.JPanel();
				this.ivjJDialogContentPane.setName("JDialogContentPane");
				this.ivjJDialogContentPane.setLayout(new java.awt.GridBagLayout());
				java.awt.GridBagConstraints constraintsPanelAttributes = new java.awt.GridBagConstraints();
				constraintsPanelAttributes.gridx = 0;
				constraintsPanelAttributes.gridy = 0;
				constraintsPanelAttributes.fill = java.awt.GridBagConstraints.BOTH;
				constraintsPanelAttributes.weightx = 1.0;
				constraintsPanelAttributes.weighty = 1.0;
				constraintsPanelAttributes.insets = new java.awt.Insets(4, 4, 4, 4);
				this.getJDialogContentPane().add(this.getPanelAttributes(), constraintsPanelAttributes);
				java.awt.GridBagConstraints constraintsPanelOKCancel = new java.awt.GridBagConstraints();
				constraintsPanelOKCancel.gridx = 0;
				constraintsPanelOKCancel.gridy = 1;
				constraintsPanelOKCancel.fill = java.awt.GridBagConstraints.HORIZONTAL;
				constraintsPanelOKCancel.weightx = 1.0;
				constraintsPanelOKCancel.weighty = 0.0;
				constraintsPanelOKCancel.insets = new java.awt.Insets(4, 4, 4, 4);
				// getJDialogContentPane().add(getPanelOKCancel(),
				// constraintsPanelOKCancel);
				// user code begin {1}
				// user code end
			}
			catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				this.handleException(ivjExc);
			}
		}
		return this.ivjJDialogContentPane;
	}

	/**
	 * Return the JPanel2 property value.
	 * 
	 * @return javax.swing.JPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private JPanel getPanelAttributes() {
		if (this.ivjPanelAttributes == null) {
			try {
				this.ivjPanelAttributes = new javax.swing.JPanel();
				this.ivjPanelAttributes.setName("PanelAttributes");
				this.ivjPanelAttributes.setLayout(new java.awt.GridBagLayout());
				java.awt.GridBagConstraints constraintsPanelButtonLists = new java.awt.GridBagConstraints();
				constraintsPanelButtonLists.gridx = 1;
				constraintsPanelButtonLists.gridy = 0;
				constraintsPanelButtonLists.fill = java.awt.GridBagConstraints.BOTH;
				constraintsPanelButtonLists.weightx = 0.1;
				constraintsPanelButtonLists.weighty = 1.0;
				constraintsPanelButtonLists.insets = new java.awt.Insets(4, 4, 4, 4);
				this.getPanelAttributes().add(this.getPanelButtonLists(), constraintsPanelButtonLists);
				java.awt.GridBagConstraints constraintsPanelList1 = new java.awt.GridBagConstraints();
				constraintsPanelList1.gridx = 0;
				constraintsPanelList1.gridy = 0;
				constraintsPanelList1.fill = java.awt.GridBagConstraints.BOTH;
				constraintsPanelList1.weightx = 1.0;
				constraintsPanelList1.weighty = 1.0;
				constraintsPanelList1.insets = new java.awt.Insets(4, 4, 4, 4);
				this.getPanelAttributes().add(this.getPanelList1(), constraintsPanelList1);
				java.awt.GridBagConstraints constraintsPanelList2 = new java.awt.GridBagConstraints();
				constraintsPanelList2.gridx = 2;
				constraintsPanelList2.gridy = 0;
				constraintsPanelList2.fill = java.awt.GridBagConstraints.BOTH;
				constraintsPanelList2.weightx = 1.0;
				constraintsPanelList2.weighty = 1.0;
				constraintsPanelList2.insets = new java.awt.Insets(4, 4, 4, 4);
				this.getPanelAttributes().add(this.getPanelList2(), constraintsPanelList2);
				java.awt.GridBagConstraints constraintsPanelInfo = new java.awt.GridBagConstraints();
				constraintsPanelInfo.gridx = 0;
				constraintsPanelInfo.gridy = 2;
				constraintsPanelInfo.gridwidth = 0;
				constraintsPanelInfo.fill = java.awt.GridBagConstraints.HORIZONTAL;
				constraintsPanelInfo.weightx = 1.0;
				constraintsPanelInfo.insets = new java.awt.Insets(4, 4, 4, 4);
				this.getPanelAttributes().add(this.getPanelInfo(), constraintsPanelInfo);
				java.awt.GridBagConstraints constraintsPanelExploreGoTo = new java.awt.GridBagConstraints();
				constraintsPanelExploreGoTo.gridx = 0;
				constraintsPanelExploreGoTo.gridy = 3;
				constraintsPanelExploreGoTo.gridwidth = 0;
				constraintsPanelExploreGoTo.fill = java.awt.GridBagConstraints.HORIZONTAL;
				constraintsPanelExploreGoTo.weightx = 1.0;
				constraintsPanelExploreGoTo.insets = new java.awt.Insets(4, 4, 4, 4);
				this.getPanelAttributes().add(this.getPanelExploreGoTo(), constraintsPanelExploreGoTo);
				// user code begin {1}
				javax.swing.border.TitledBorder titledBorder = new javax.swing.border.TitledBorder(new javax.swing.border.EtchedBorder(), "Attributes");
				this.ivjPanelAttributes.setBorder(titledBorder);
				// user code end
			}
			catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				this.handleException(ivjExc);
			}
		}
		return this.ivjPanelAttributes;
	}

	/**
	 * Return the JPanel5 property value.
	 * 
	 * @return javax.swing.JPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private JPanel getPanelButtonLists() {
		if (this.ivjPanelButtonLists == null) {
			try {
				this.ivjPanelButtonLists = new javax.swing.JPanel();
				this.ivjPanelButtonLists.setName("PanelButtonLists");
				this.ivjPanelButtonLists.setLayout(new java.awt.GridBagLayout());
				this.ivjPanelButtonLists.setComponentOrientation(java.awt.ComponentOrientation.UNKNOWN);
				java.awt.GridBagConstraints constraintsAddButton = new java.awt.GridBagConstraints();
				constraintsAddButton.gridx = 0;
				constraintsAddButton.gridy = 0;
				constraintsAddButton.insets = new java.awt.Insets(4, 4, 4, 4);
				this.getPanelButtonLists().add(this.getAddButton(), constraintsAddButton);
				java.awt.GridBagConstraints constraintsRemoveButton = new java.awt.GridBagConstraints();
				constraintsRemoveButton.gridx = 0;
				constraintsRemoveButton.gridy = 1;
				constraintsRemoveButton.insets = new java.awt.Insets(4, 4, 4, 4);
				this.getPanelButtonLists().add(this.getRemoveButton(), constraintsRemoveButton);
				// user code begin {1}
				// user code end
			}
			catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				this.handleException(ivjExc);
			}
		}
		return this.ivjPanelButtonLists;
	}

	/**
	 * Return the JPanel3 property value.
	 * 
	 * @return javax.swing.JPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private JPanel getPanelExploreGoTo() {
		if (this.ivjPanelExploreGoTo == null) {
			try {
				this.ivjPanelExploreGoTo = new javax.swing.JPanel();
				this.ivjPanelExploreGoTo.setName("PanelExploreGoTo");
				this.ivjPanelExploreGoTo.setLayout(new java.awt.GridBagLayout());
				java.awt.GridBagConstraints constraintsExploreButton = new java.awt.GridBagConstraints();
				constraintsExploreButton.gridx = 0;
				constraintsExploreButton.gridy = 0;
				constraintsExploreButton.anchor = java.awt.GridBagConstraints.SOUTHEAST;
				constraintsExploreButton.weightx = 1.0;
				constraintsExploreButton.weighty = 1.0;
				constraintsExploreButton.insets = new java.awt.Insets(4, 4, 4, 4);
				this.getPanelExploreGoTo().add(this.getExploreButton(), constraintsExploreButton);
				java.awt.GridBagConstraints constraintsGoToButton = new java.awt.GridBagConstraints();
				constraintsGoToButton.gridx = 1;
				constraintsGoToButton.gridy = 0;
				constraintsGoToButton.insets = new java.awt.Insets(4, 4, 4, 4);
				java.awt.GridBagConstraints constraintsCloseButton = new java.awt.GridBagConstraints();
				constraintsCloseButton.gridx = 2;
				constraintsGoToButton.gridy = 0;
				constraintsCloseButton.insets = new java.awt.Insets(4, 4, 4, 4);
				this.getPanelExploreGoTo().add(this.getGoToButton(), constraintsGoToButton);
				this.getPanelExploreGoTo().add(this.getCloseButton(), constraintsCloseButton);
				// user code begin {1}
				// user code end
			}
			catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				this.handleException(ivjExc);
			}
		}
		return this.ivjPanelExploreGoTo;
	}

	/**
	 * Return the PanelInfo property value.
	 * 
	 * @return javax.swing.JPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private JPanel getPanelInfo() {
		if (this.ivjPanelInfo == null) {
			try {
				this.ivjPanelInfo = new javax.swing.JPanel();
				this.ivjPanelInfo.setName("PanelInfo");
				this.ivjPanelInfo.setLayout(new java.awt.GridBagLayout());
				java.awt.GridBagConstraints constraintsInfoText = new java.awt.GridBagConstraints();
				constraintsInfoText.gridx = 0;
				constraintsInfoText.gridy = 0;
				constraintsInfoText.fill = java.awt.GridBagConstraints.HORIZONTAL;
				constraintsInfoText.weightx = 1.0;
				constraintsInfoText.insets = new java.awt.Insets(4, 4, 4, 4);
				this.getPanelInfo().add(this.getInfoText(), constraintsInfoText);
				// user code begin {1}
				// user code end
			}
			catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				this.handleException(ivjExc);
			}
		}
		return this.ivjPanelInfo;
	}

	/**
	 * Return the JPanel6 property value.
	 * 
	 * @return javax.swing.JPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private JPanel getPanelList1() {
		if (this.ivjPanelList1 == null) {
			try {
				this.ivjPanelList1 = new javax.swing.JPanel();
				this.ivjPanelList1.setName("PanelList1");
				this.ivjPanelList1.setLayout(new java.awt.GridBagLayout());
				java.awt.GridBagConstraints constraintsScrollPaneInvisibleList = new java.awt.GridBagConstraints();
				constraintsScrollPaneInvisibleList.gridx = -1;
				constraintsScrollPaneInvisibleList.gridy = -1;
				constraintsScrollPaneInvisibleList.fill = java.awt.GridBagConstraints.BOTH;
				constraintsScrollPaneInvisibleList.weightx = 1.0;
				constraintsScrollPaneInvisibleList.weighty = 1.0;
				constraintsScrollPaneInvisibleList.insets = new java.awt.Insets(4, 4, 4, 4);
				this.getPanelList1().add(this.getScrollPaneInvisibleList(), constraintsScrollPaneInvisibleList);
				// user code begin {1}
				javax.swing.border.TitledBorder titledBorder = new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0), "Invisible");
				this.ivjPanelList1.setBorder(titledBorder);
				// user code end
			}
			catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				this.handleException(ivjExc);
			}
		}
		return this.ivjPanelList1;
	}

	/**
	 * Return the PanelList2 property value.
	 * 
	 * @return javax.swing.JPanel
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private JPanel getPanelList2() {
		if (this.ivjPanelList2 == null) {
			try {
				this.ivjPanelList2 = new javax.swing.JPanel();
				this.ivjPanelList2.setName("PanelList2");
				this.ivjPanelList2.setLayout(new java.awt.GridBagLayout());
				java.awt.GridBagConstraints constraintsScrollPaneVisibleList = new java.awt.GridBagConstraints();
				constraintsScrollPaneVisibleList.gridx = -1;
				constraintsScrollPaneVisibleList.gridy = -1;
				constraintsScrollPaneVisibleList.fill = java.awt.GridBagConstraints.BOTH;
				constraintsScrollPaneVisibleList.weightx = 1.0;
				constraintsScrollPaneVisibleList.weighty = 1.0;
				constraintsScrollPaneVisibleList.insets = new java.awt.Insets(4, 4, 4, 4);
				this.getPanelList2().add(this.getScrollPaneVisibleList(), constraintsScrollPaneVisibleList);
				// user code begin {1}
				javax.swing.border.TitledBorder titledBorder = new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0), "Visible");
				this.ivjPanelList2.setBorder(titledBorder);
				// user code end
			}
			catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				this.handleException(ivjExc);
			}
		}
		return this.ivjPanelList2;
	}

	/**
	 * Return the JButton2 property value.
	 * 
	 * @return javax.swing.JButton
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public JButton getRemoveButton() {
		if (this.ivjRemoveButton == null) {
			try {
				this.ivjRemoveButton = new javax.swing.JButton();
				this.ivjRemoveButton.setName("RemoveButton");
				this.ivjRemoveButton.setText("<");
				this.ivjRemoveButton.setActionCommand(ObjectViewDialog.REMOVE);
				// user code begin {1}
				// user code end
			}
			catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				this.handleException(ivjExc);
			}
		}
		return this.ivjRemoveButton;
	}

	/**
	 * Return the ScrollPaneInvisibleList property value.
	 * 
	 * @return javax.swing.JScrollPane
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private JScrollPane getScrollPaneInvisibleList() {
		if (this.ivjScrollPaneInvisibleList == null) {
			try {
				this.ivjScrollPaneInvisibleList = new javax.swing.JScrollPane();
				this.ivjScrollPaneInvisibleList.setName("ScrollPaneInvisibleList");
				this.ivjScrollPaneInvisibleList.setAutoscrolls(false);
				this.ivjScrollPaneInvisibleList.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
				this.ivjScrollPaneInvisibleList.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				this.getScrollPaneInvisibleList().setViewportView(this.getInvisibleList());
				// user code begin {1}
				// user code end
			}
			catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				this.handleException(ivjExc);
			}
		}
		return this.ivjScrollPaneInvisibleList;
	}

	/**
	 * Return the ScrollPaneVisibleList property value.
	 * 
	 * @return javax.swing.JScrollPane
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	private JScrollPane getScrollPaneVisibleList() {
		if (this.ivjScrollPaneVisibleList == null) {
			try {
				this.ivjScrollPaneVisibleList = new javax.swing.JScrollPane();
				this.ivjScrollPaneVisibleList.setName("ScrollPaneVisibleList");
				this.getScrollPaneVisibleList().setViewportView(this.getVisibleList());
				// user code begin {1}
				// user code end
			}
			catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				this.handleException(ivjExc);
			}
		}
		return this.ivjScrollPaneVisibleList;
	}

	/**
	 * Return the JList2 property value.
	 * 
	 * @return javax.swing.JList
	 */
	/* WARNING: THIS METHOD WILL BE REGENERATED. */
	public JList getVisibleList() {
		if (this.ivjVisibleList == null) {
			try {
				this.ivjVisibleList = new javax.swing.JList();
				this.ivjVisibleList.setName("VisibleList");
				this.ivjVisibleList.setAutoscrolls(false);
				this.ivjVisibleList.setFixedCellWidth(-1);
				this.ivjVisibleList.setBounds(0, 0, 23, 315);
				// ivjVisibleList.setFocusCycleRoot(true);
				// user code begin {1}
				// user code end
			}
			catch (Throwable ivjExc) {
				// user code begin {2}
				// user code end
				this.handleException(ivjExc);
			}
		}
		return this.ivjVisibleList;
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
			this.setName("ObjectViewDialog");
			this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
			this.setSize(600, 800);
			this.setResizable(true);
			this.setContentPane(this.getJDialogContentPane());
		}
		catch (Throwable ivjExc) {
			this.handleException(ivjExc);
		}
		// user code begin {2}
		// user code end
	}

	public void setAddButtonEnabled(boolean value) {
		this.getAddButton().setEnabled(value);
	}

	public void setExploreButtonEnabled(boolean value) {
		this.getExploreButton().setEnabled(value);
	}

	public void setGoToButtonEnabled(boolean value) {
		this.getGoToButton().setEnabled(value);
	}

	public void setRemoveButtonEnabled(boolean value) {
		this.getRemoveButton().setEnabled(value);
	}
}
