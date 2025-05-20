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

package ghm.follow.gui;

import ghm.follow.FollowApp;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author <a href="mailto:greghmerrill@yahoo.com">Greg Merrill</a>
 */
public class DisableableConfirm extends JDialog
{

	public DisableableConfirm(Frame parent, String title, String message, String confirmButtonText,
			String doNotConfirmButtonText, String disableText)
	{
		super(parent, title, true);

		// messagePanel will contain the string contents of the message
		JPanel messagePanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		StringTokenizer stknzr = new StringTokenizer(message, "\n\r");
		gbc.gridy = 0;
		while (stknzr.hasMoreTokens())
		{
			messagePanel.add(new JLabel(stknzr.nextToken()), gbc);
			gbc.gridy++;
		}

		// controlPanel will contain the confirm/doNotConfirm buttons and the
		// disable checkbox
		JPanel controlPanel = new JPanel(new GridBagLayout());
		gbc = new GridBagConstraints();

		JPanel buttonPanel = new JPanel();
		JButton confirmButton = new JButton(confirmButtonText);
		confirmButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				confirmed = true;
				DisableableConfirm.this.dispose();
			}
		});
		buttonPanel.add(confirmButton);
		JButton doNotConfirmButton = new JButton(doNotConfirmButtonText);
		doNotConfirmButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				confirmed = false;
				DisableableConfirm.this.dispose();
			}
		});
		buttonPanel.add(doNotConfirmButton);
		controlPanel.add(buttonPanel, gbc);

		disabledCheckBox = new JCheckBox(disableText);
		gbc.gridy = 1;
		controlPanel.add(disabledCheckBox, gbc);

		JPanel contentPane = new JPanel(new BorderLayout(0, 10));
		contentPane.setBorder(BorderFactory.createEmptyBorder(12, 12, 11, 11));
		contentPane.add(messagePanel, BorderLayout.CENTER);
		contentPane.add(controlPanel, BorderLayout.SOUTH);
		this.setContentPane(contentPane);
		FollowApp.centerWindowInScreen(this);
	}

	private JCheckBox disabledCheckBox;
	private boolean confirmed;

	boolean markedDisabled()
	{
		return disabledCheckBox.isSelected();
	}

	boolean markedConfirmed()
	{
		return confirmed;
	}

}
