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

import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

/**
 * Action which displays information about the Follow application.
 * 
 * @author <a href="mailto:greghmerrill@yahoo.com">Greg Merrill</a>
 */
public class About extends FollowAppAction
{
	public static final String NAME = "about";

	public About(FollowApp app)
	{
		super(app, FollowApp.getResourceString("action.About.name"),
				FollowApp.getResourceString("action.About.mnemonic"),
				FollowApp.getResourceString("action.About.accelerator"),
				FollowApp.getIcon(About.class, "action.About.icon"),
				ActionContext.APP);
	}

	public void actionPerformed(ActionEvent e)
	{
		JOptionPane.showMessageDialog(getApp().getFrame(),
				FollowApp.getResourceString("dialog.About.text"),
				FollowApp.getResourceString("dialog.About.title"), JOptionPane.INFORMATION_MESSAGE);
	}
}