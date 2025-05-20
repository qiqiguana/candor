/*
 * Copyright (C) 2000-2003 Greg Merrill (greghmerrill@yahoo.com)
 * 
 * This file is part of Follow (http://follow.sf.net).
 * 
 * Follow is free software; you can redistribute it and/or modify it under the
 * terms of version 2 of the GNU General Public License as published by the Free
 * Software Foundation.
 * 
 * Follow is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * Follow; if not, write to the Free Software Foundation, Inc., 59 Temple Place,
 * Suite 330, Boston, MA 02111-1307 USA
 */

package ghm.follow.config;

import ghm.follow.font.FontSelectionPanel;
import ghm.follow.font.InvalidFontException;
import ghm.follow.gui.FileFollowingPane;
import ghm.follow.FollowApp;
import ghm.follow.gui.FollowAppAction;
import ghm.follow.gui.WhatIsThis;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 * Action which brings up a dialog allowing one to configure the Follow application.
 * 
 * @author <a href="mailto:greghmerrill@yahoo.com">Greg Merrill</a>
 */
public class Configure extends FollowAppAction
{
	public static final String NAME = "configure";

	public Configure(FollowApp app)
	{
		super(app, FollowApp.getResourceString("action.Configure.name"),
				FollowApp.getResourceString("action.Configure.mnemonic"),
				FollowApp.getResourceString("action.Configure.accelerator"),
				FollowApp.getIcon(Configure.class, "action.Configure.icon"),
				ActionContext.APP);
	}

	public void actionPerformed(ActionEvent e)
	{
		getApp().setCursor(Cursor.WAIT_CURSOR);
		if (dialog == null)
		{
			dialog = new CfgDialog();
		}
		dialog.bufferSize.setText(String.valueOf(getApp().getAttributes().getBufferSize()));
		dialog.latency.setText(String.valueOf(getApp().getAttributes().getLatency()));
		dialog.tabPlacement.setSelectedItem(new TabPlacementValue(getApp().getAttributes()
				.getTabPlacement()));
		dialog.confirmDelete.setValue(getApp().getAttributes().confirmDelete());
		dialog.confirmDeleteAll.setValue(getApp().getAttributes().confirmDeleteAll());
		dialog.autoScroll.setValue(getApp().getAttributes().autoScroll());
		dialog.editor.setText(String.valueOf(getApp().getAttributes().getEditor()));
		dialog.tabSize.setText(String.valueOf(getApp().getAttributes().getTabSize()));
		dialog.fontSelectionPanel.setSelectedFont(getApp().getAttributes().getFont());
		dialog.recentFilesMax.setText(String
				.valueOf(getApp().getAttributes().getRecentFilesMax()));
		// Quasi-kludge to get around font repainting issue
		dialog.setLocationRelativeTo(getApp().getFrame());
		dialog.setLocation(30, 30);
		// No need to set font; this is taken care of during CfgDialog
		// construction
		dialog.pack();
		dialog.setVisible(true);
		getApp().setCursor(Cursor.DEFAULT_CURSOR);
	}

	private CfgDialog dialog;

	class CfgDialog extends JDialog
	{
		protected JRootPane createRootPane()
		{
			KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
			JRootPane rootPane = new JRootPane();
			rootPane.registerKeyboardAction(new ActionListener()
			{
				public void actionPerformed(ActionEvent actionEvent)
				{
					dialog.close.doClick();
				}
			}, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
			return rootPane;
		}

		CfgDialog()
		{
			super(Configure.this.getApp().getFrame(), FollowApp.getResourceString("dialog.Configure.title"),
					true);
			JComponent contentPane = (JComponent) getContentPane();
			contentPane.setBorder(BorderFactory.createEmptyBorder(12, 12, 11, 11));

			JPanel configPanel = new JPanel(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.anchor = GridBagConstraints.WEST;
			gbc.ipadx = 4;

			// buffer size
			gbc.gridy = 0;
			configPanel
					.add(new JLabel(FollowApp.getResourceString("dialog.Configure.bufferSize.label")), gbc);
			bufferSize = new JTextField();
			bufferSize.setHorizontalAlignment(JTextField.RIGHT);
			gbc.gridx = 1;
			gbc.weightx = 1;
			gbc.ipadx = 0;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			configPanel.add(bufferSize, gbc);
			JButton bufferSizeInfo = new WhatIsThis(getApp(),
					FollowApp.getResourceString("WhatIsThis.bufferSize.title"),
					FollowApp.getResourceString("WhatIsThis.bufferSize.text"));
			gbc.gridx = 2;
			gbc.weightx = 0;
			gbc.fill = GridBagConstraints.NONE;
			configPanel.add(bufferSizeInfo, gbc);

			// latency
			gbc.gridx = 0;
			gbc.gridy++;
			gbc.ipadx = 4;
			configPanel.add(new JLabel(FollowApp.getResourceString("dialog.Configure.latency.label")), gbc);
			latency = new JTextField();
			latency.setHorizontalAlignment(JTextField.RIGHT);
			gbc.gridx = 1;
			gbc.weightx = 1;
			gbc.ipadx = 0;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			configPanel.add(latency, gbc);
			JButton latencyInfo = new WhatIsThis(getApp(),
					FollowApp.getResourceString("WhatIsThis.latency.title"),
					FollowApp.getResourceString("WhatIsThis.latency.text"));
			gbc.gridx = 2;
			gbc.weightx = 0;
			gbc.fill = GridBagConstraints.NONE;
			configPanel.add(latencyInfo, gbc);

			// tab placement
			gbc.gridx = 0;
			gbc.gridy++;
			gbc.ipadx = 4;
			configPanel.add(new JLabel(FollowApp.getResourceString("dialog.Configure.tabPlacement.label")),
					gbc);
			tabPlacement = new JComboBox(ALL_TAB_PLACEMENT_VALUES);
			gbc.gridx = 1;
			gbc.weightx = 1;
			gbc.ipadx = 0;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			configPanel.add(tabPlacement, gbc);
			JButton tabPlacementInfo = new WhatIsThis(getApp(),
					FollowApp.getResourceString("WhatIsThis.tabPlacement.title"),
					FollowApp.getResourceString("WhatIsThis.tabPlacement.text"));
			gbc.gridx = 2;
			gbc.weightx = 0;
			gbc.fill = GridBagConstraints.NONE;
			configPanel.add(tabPlacementInfo, gbc);

			// confirm delete
			gbc.gridx = 0;
			gbc.gridy++;
			gbc.ipadx = 4;
			configPanel.add(new JLabel(FollowApp.getResourceString("dialog.Configure.confirmDelete.label")),
					gbc);
			confirmDelete = new BooleanComboBox(
					FollowApp.getResourceString("dialog.Configure.confirmDelete.yes.displayValue"),
					FollowApp.getResourceString("dialog.Configure.confirmDelete.no.displayValue"));
			gbc.gridx = 1;
			gbc.weightx = 1;
			gbc.ipadx = 0;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			configPanel.add(confirmDelete, gbc);
			JButton confirmDeleteInfo = new WhatIsThis(getApp(),
					FollowApp.getResourceString("WhatIsThis.confirmDelete.title"),
					FollowApp.getResourceString("WhatIsThis.confirmDelete.text"));
			gbc.gridx = 2;
			gbc.weightx = 0;
			gbc.fill = GridBagConstraints.NONE;
			configPanel.add(confirmDeleteInfo, gbc);

			// confirm delete all
			gbc.gridx = 0;
			gbc.gridy++;
			gbc.ipadx = 4;
			configPanel.add(
					new JLabel(FollowApp.getResourceString("dialog.Configure.confirmDeleteAll.label")), gbc);
			confirmDeleteAll = new BooleanComboBox(
					FollowApp.getResourceString("dialog.Configure.confirmDeleteAll.yes.displayValue"),
					FollowApp.getResourceString("dialog.Configure.confirmDeleteAll.no.displayValue"));
			gbc.gridx = 1;
			gbc.weightx = 1;
			gbc.ipadx = 0;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			configPanel.add(confirmDeleteAll, gbc);
			JButton confirmDeleteAllInfo = new WhatIsThis(getApp(),
					FollowApp.getResourceString("WhatIsThis.confirmDeleteAll.title"),
					FollowApp.getResourceString("WhatIsThis.confirmDeleteAll.text"));
			gbc.gridx = 2;
			gbc.weightx = 0;
			gbc.fill = GridBagConstraints.NONE;
			configPanel.add(confirmDeleteAllInfo, gbc);

			// autoscroll
			gbc.gridx = 0;
			gbc.gridy++;
			gbc.ipadx = 4;
			configPanel
					.add(new JLabel(FollowApp.getResourceString("dialog.Configure.autoScroll.label")), gbc);
			autoScroll = new BooleanComboBox(
					FollowApp.getResourceString("dialog.Configure.autoScroll.yes.displayValue"),
					FollowApp.getResourceString("dialog.Configure.autoScroll.no.displayValue"));
			gbc.gridx = 1;
			gbc.weightx = 1;
			gbc.ipadx = 0;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			configPanel.add(autoScroll, gbc);
			JButton autoScrollInfo = new WhatIsThis(getApp(),
					FollowApp.getResourceString("WhatIsThis.autoScroll.title"),
					FollowApp.getResourceString("WhatIsThis.autoScroll.text"));
			gbc.gridx = 2;
			gbc.weightx = 0;
			gbc.fill = GridBagConstraints.NONE;
			configPanel.add(autoScrollInfo, gbc);

			// external editor
			gbc.gridx = 0;
			gbc.gridy++;
			gbc.ipadx = 4;
			configPanel.add(new JLabel(FollowApp.getResourceString("dialog.Configure.editor.label")), gbc);
			editor = new JTextField();
			editor.setHorizontalAlignment(JTextField.LEFT);
			gbc.gridx = 1;
			gbc.weightx = 1;
			gbc.ipadx = 0;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			configPanel.add(editor, gbc);
			JButton editorInfo = new WhatIsThis(getApp(),
					FollowApp.getResourceString("WhatIsThis.editor.title"),
					FollowApp.getResourceString("WhatIsThis.editor.text"));
			gbc.gridx = 2;
			gbc.weightx = 0;
			gbc.fill = GridBagConstraints.NONE;
			configPanel.add(editorInfo, gbc);

			// tabSize
			gbc.gridx = 0;
			gbc.gridy++;
			gbc.ipadx = 4;
			configPanel.add(new JLabel(FollowApp.getResourceString("dialog.Configure.tabSize.label")), gbc);
			tabSize = new JTextField();
			tabSize.setHorizontalAlignment(JTextField.RIGHT);
			gbc.gridx = 1;
			gbc.weightx = 1;
			gbc.ipadx = 0;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			configPanel.add(tabSize, gbc);
			JButton tabSizeInfo = new WhatIsThis(getApp(),
					FollowApp.getResourceString("WhatIsThis.tabSize.title"),
					FollowApp.getResourceString("WhatIsThis.tabSize.text"));
			gbc.gridx = 2;
			gbc.weightx = 0;
			gbc.fill = GridBagConstraints.NONE;
			configPanel.add(tabSizeInfo, gbc);

			// recentFilesMax
			gbc.gridx = 0;
			gbc.gridy++;
			gbc.ipadx = 4;
			configPanel.add(new JLabel(FollowApp.getResourceString("dialog.Configure.recentFilesMax.label")),
					gbc);
			recentFilesMax = new JTextField();
			recentFilesMax.setHorizontalAlignment(JTextField.RIGHT);
			gbc.gridx = 1;
			gbc.weightx = 1;
			gbc.ipadx = 0;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			configPanel.add(recentFilesMax, gbc);
			JButton recentFilesMaxInfo = new WhatIsThis(getApp(),
					FollowApp.getResourceString("WhatIsThis.recentFilesMax.title"),
					FollowApp.getResourceString("WhatIsThis.recentFilesMax.text"));
			gbc.gridx = 2;
			gbc.weightx = 0;
			gbc.fill = GridBagConstraints.NONE;
			configPanel.add(recentFilesMaxInfo, gbc);

			// font selection
			fontSelectionPanel = new CfgFontSelectionPanel();
			// Must change border to top=0 because of default top in titled
			// border
			fontSelectionPanel.setBorder(BorderFactory.createEmptyBorder(0, 12, 11, 11));
			JPanel fontPanelHolder = new JPanel(new BorderLayout());
			fontPanelHolder.add(fontSelectionPanel, BorderLayout.CENTER);
			fontPanelHolder.setBorder(BorderFactory
					.createTitledBorder(FollowApp.getResourceString("dialog.Configure.font.label")));
			gbc.gridx = 0;
			gbc.gridy++;
			gbc.gridwidth = 3;
			gbc.fill = GridBagConstraints.BOTH;
			configPanel.add(fontPanelHolder, gbc);

			contentPane.add(configPanel, BorderLayout.CENTER);

			// Save button
			save = new JButton(FollowApp.getResourceString("dialog.Configure.save.label"));
			save.setMnemonic(FollowApp.getResourceString("dialog.Configure.save.mnemonic").charAt(0));
			save.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					// Validate fields
					StringBuffer invalidFieldsMessage = new StringBuffer();
					if (!isPositiveInteger(bufferSize.getText()))
					{
						invalidFieldsMessage
								.append(FollowApp.getResourceString("dialog.Configure.bufferSizeInvalid.text"));
						invalidFieldsMessage.append(FollowApp.MESSAGE_LINE_SEPARATOR);
						invalidFieldsMessage.append(FollowApp.MESSAGE_LINE_SEPARATOR);
					}
					if (!isPositiveInteger(latency.getText()))
					{
						invalidFieldsMessage
								.append(FollowApp.getResourceString("dialog.Configure.latencyInvalid.text"));
						invalidFieldsMessage.append(FollowApp.MESSAGE_LINE_SEPARATOR);
						invalidFieldsMessage.append(FollowApp.MESSAGE_LINE_SEPARATOR);
					}
					if (!isPositiveInteger(recentFilesMax.getText()))
					{
						invalidFieldsMessage
								.append(FollowApp.getResourceString("dialog.Configure.recentFilesMaxInvalid.text"));
						invalidFieldsMessage.append(FollowApp.MESSAGE_LINE_SEPARATOR);
						invalidFieldsMessage.append(FollowApp.MESSAGE_LINE_SEPARATOR);
					}
					try
					{
						fontSelectionPanel.getSelectedFont();
					}
					catch (InvalidFontException ife)
					{
						invalidFieldsMessage
								.append(FollowApp.getResourceString("dialog.Configure.fontInvalid.text"));
						invalidFieldsMessage.append(FollowApp.MESSAGE_LINE_SEPARATOR);
						invalidFieldsMessage.append(FollowApp.MESSAGE_LINE_SEPARATOR);
					}

					if (invalidFieldsMessage.length() > 0)
					{
						JOptionPane.showMessageDialog(getApp().getFrame(), invalidFieldsMessage
								.toString(),
								FollowApp.getResourceString("dialog.Configure.invalidFieldsDialog.title"),
								JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						getApp().getAttributes().setBufferSize(bufferSize.getText());
						getApp().getAttributes().setLatency(latency.getText());
						getApp().getAttributes().setTabPlacement(
								((TabPlacementValue) tabPlacement.getSelectedItem()).value);
						getApp().getAttributes().setConfirmDelete(confirmDelete.getValue());
						getApp().getAttributes().setConfirmDeleteAll(confirmDeleteAll.getValue());
						getApp().getAttributes().setAutoScroll(autoScroll.getValue());
						getApp().getAttributes().setEditor(editor.getText());
						getApp().getAttributes().setTabSize(tabSize.getText());
						getApp().getAttributes().setRecentFilesMax(recentFilesMax.getText());
//						getApp().refreshRecentFilesMenu();
						Font selectedFont;
						try
						{
							selectedFont = fontSelectionPanel.getSelectedFont();
						}
						catch (InvalidFontException ife)
						{
							// This shouldn't happen if the error catching at
							// the beginning
							// of actionPerformed() worked correctly
							throw new RuntimeException(
									"Programmatic error; supposedly impossible scenario has occurred.");
						}
						getApp().getAttributes().setFont(selectedFont);
						for (FileFollowingPane pane : getApp().getFileToFollowingPaneMap().values())
						{
							pane.getFileFollower().setBufferSize(
									getApp().getAttributes().getBufferSize());
							pane.getFileFollower()
									.setLatency(getApp().getAttributes().getLatency());
							pane.getTextPane().setFont(selectedFont);
							pane.setAutoPositionCaret(getApp().getAttributes().autoScroll());
							pane.getTextPane().setTabSize(getApp().getAttributes().getTabSize());
							getApp().getTabbedPane().invalidate();
							getApp().getTabbedPane().repaint();
						}
						getApp().getTabbedPane().setTabPlacement(
								getApp().getAttributes().getTabPlacement());
						getApp().getTabbedPane().invalidate();
					}
				}
			});

			// Restore Defaults button
			restoreDefaults = new JButton(
					FollowApp.getResourceString("dialog.Configure.restoreDefaults.label"));
			restoreDefaults.setMnemonic(FollowApp.getResourceString(
					"dialog.Configure.restoreDefaults.mnemonic").charAt(0));
			restoreDefaults.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					try
					{
						bufferSize.setText(String.valueOf(getApp().getAttributes()
								.getDefaultAttributes().getBufferSize()));
						latency.setText(String.valueOf(getApp().getAttributes()
								.getDefaultAttributes().getLatency()));
						tabPlacement.setSelectedItem(new TabPlacementValue(getApp()
								.getAttributes().getDefaultAttributes().getTabPlacement()));
						confirmDelete.setValue(getApp().getAttributes().getDefaultAttributes()
								.confirmDelete());
						confirmDeleteAll.setValue(getApp().getAttributes().getDefaultAttributes()
								.confirmDeleteAll());
						autoScroll.setValue(getApp().getAttributes().getDefaultAttributes()
								.autoScroll());
						editor.setText(String.valueOf(getApp().getAttributes()
								.getDefaultAttributes().getEditor()));
						fontSelectionPanel.setSelectedFont(getApp().getAttributes()
								.getDefaultAttributes().getFont());
						recentFilesMax.setText(String.valueOf(getApp().getAttributes()
								.getDefaultAttributes().getRecentFilesMax()));
					}
					catch (IOException ioe)
					{
						JOptionPane.showMessageDialog(getApp().getFrame(),
								FollowApp.getResourceString("dialog.Configure.cantRestoreDefaults.text"),
								FollowApp.getResourceString("dialog.Configure.cantRestoreDefaults.title"),
								JOptionPane.ERROR_MESSAGE);
					}
				}
			});

			// Close button
			close = new JButton(FollowApp.getResourceString("dialog.Configure.close.label"));
			close.setMnemonic(FollowApp.getResourceString("dialog.Configure.close.mnemonic").charAt(0));
			close.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					dispose();
				}
			});

			JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			buttonPanel.add(save);
			buttonPanel.add(restoreDefaults);
			buttonPanel.add(close);
			contentPane.add(buttonPanel, BorderLayout.SOUTH);
		}

		private boolean isPositiveInteger(String value)
		{
			try
			{
				int intValue = Integer.parseInt(value);
				if (intValue < 1)
				{
					return false;
				}
				return true;
			}
			catch (NumberFormatException nfe)
			{
				return false;
			}
		}

		JTextField bufferSize;
		JTextField latency;
		JComboBox tabPlacement;
		BooleanComboBox confirmDelete;
		BooleanComboBox confirmDeleteAll;
		BooleanComboBox autoScroll;
		JTextField editor;
		JTextField tabSize;
		JTextField recentFilesMax;
		CfgFontSelectionPanel fontSelectionPanel;
		JButton save;
		JButton restoreDefaults;
		JButton close;
	}

	private class TabPlacementValue
	{
		public TabPlacementValue(int value)
		{
			this.value = value;
			switch (value)
			{
				case JTabbedPane.TOP:
					displayValue = FollowApp.getResourceString("dialog.Configure.tabPlacement.Top.displayValue");
					break;
				case JTabbedPane.BOTTOM:
					displayValue = FollowApp.getResourceString("dialog.Configure.tabPlacement.Bottom.displayValue");
					break;
				case JTabbedPane.LEFT:
					displayValue = FollowApp.getResourceString("dialog.Configure.tabPlacement.Left.displayValue");
					break;
				case JTabbedPane.RIGHT:
					displayValue = FollowApp.getResourceString("dialog.Configure.tabPlacement.Right.displayValue");
					break;
				default:
					throw new IllegalArgumentException(
							"int value must be one of the tab placement values from JTabbedPane");
			}
		}

		public int value;

		public String displayValue;

		public String toString()
		{
			return displayValue;
		}

		public boolean equals(Object o)
		{
			if (o != null && o.getClass() == getClass())
			{
				return value == ((TabPlacementValue) o).value;
			}
			return false;
		}
	}

	private class CfgFontSelectionPanel extends FontSelectionPanel
	{
		CfgFontSelectionPanel()
		{
			super(Configure.this.getApp().getAttributes().getFont(),
					getStyleDisplayValues(), new int[] { 8, 9, 10, 12,
							14 });
			this.fontFamilyList.setVisibleRowCount(5);
		}
	}

	private static String[] getStyleDisplayValues()
	{
		return new String[] { FollowApp.getResourceString("dialog.Configure.font.plain.displayValue"),
				FollowApp.getResourceString("dialog.Configure.font.bold.displayValue"),
				FollowApp.getResourceString("dialog.Configure.font.italic.displayValue"),
				FollowApp.getResourceString("dialog.Configure.font.boldItalic.displayValue") };
	}

	private TabPlacementValue TOP = new TabPlacementValue(JTabbedPane.TOP);

	private TabPlacementValue BOTTOM = new TabPlacementValue(JTabbedPane.BOTTOM);

	private TabPlacementValue LEFT = new TabPlacementValue(JTabbedPane.LEFT);

	private TabPlacementValue RIGHT = new TabPlacementValue(JTabbedPane.RIGHT);

	private TabPlacementValue[] ALL_TAB_PLACEMENT_VALUES = new TabPlacementValue[] { TOP, BOTTOM,
			LEFT, RIGHT };

	static class BooleanComboBox extends JComboBox
	{
		BooleanComboBox(String trueDisplayValue, String falseDisplayValue)
		{
			super(new String[] { trueDisplayValue, falseDisplayValue });
		}

		public void setValue(boolean value)
		{
			if (value == true)
			{
				this.setSelectedIndex(0);
			}
			else
			{
				this.setSelectedIndex(1);
			}
		}

		public boolean getValue()
		{
			return (this.getSelectedIndex() == 0);
		}
	}
}