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

/**
 * Action which closes the currently followed file.
 * 
 * @author <a href="mailto:greghmerrill@yahoo.com">Greg Merrill</a>
 */
public class Close extends FollowAppAction
{
	public static final String NAME = "close";

	public Close(FollowApp app)
	{
		super(app, FollowApp.getResourceString("action.Close.name"),
				FollowApp.getResourceString("action.Close.mnemonic"),
				FollowApp.getResourceString("action.Close.accelerator"),
				ActionContext.SINGLE_FILE);
	}

	public void actionPerformed(ActionEvent e)
	{
		getApp().closeFile();
	}
}
