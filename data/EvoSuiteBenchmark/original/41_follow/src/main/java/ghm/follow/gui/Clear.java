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
import java.io.IOException;

/**
 * Action which clears the text area for the currently followed file.
 * 
 * @author <a href="mailto:greghmerrill@yahoo.com">Greg Merrill</a>
 */
public class Clear extends FollowAppAction
{
	public static final String NAME = "clear";

	public Clear(FollowApp app) throws IOException
	{
		super(app, FollowApp.getResourceString("action.Clear.name"),
				FollowApp.getResourceString("action.Clear.mnemonic"),
				FollowApp.getResourceString("action.Clear.accelerator"),
				FollowApp.getIcon(Clear.class, "action.Clear.icon"),
				ActionContext.SINGLE_FILE);
	}

	public void actionPerformed(ActionEvent e)
	{
		FileFollowingPane fileFollowingPane = getApp().getSelectedFileFollowingPane();
		fileFollowingPane.getTextPane().setText("");
	}
}