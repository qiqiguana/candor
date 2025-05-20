// Copyright (C) 2000 Greg Merrill (greghmerrill@yahoo.com)
// Distributed under the terms of the GNU General Public License (version 2)
// For details on the GNU GPL, please visit http://www.gnu.org/copyleft/gpl.html
// To find out more about this and other free software by Greg Merrill,
// please visit http://gregmerrill.imagineis.com

package ghm.follow.gui;

import ghm.follow.FollowApp;

import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * Action which restarts the currently selected followed file.
 * 
 * @author <a href="mailto:greghmerrill@yahoo.com">Greg Merrill</a>
 */
public class Reset extends FollowAppAction
{
	public static final String NAME = "reset";

	public Reset(FollowApp app) throws IOException
	{
		super(app, FollowApp.getResourceString("action.Reset.name"),
				FollowApp.getResourceString("action.Reset.mnemonic"),
				FollowApp.getResourceString("action.Reset.accelerator"),
				FollowApp.getIcon(Reset.class, "action.Reset.icon"),
				ActionContext.SINGLE_FILE);
	}

	public void actionPerformed(ActionEvent e)
	{
		FileFollowingPane fileFollowingPane = getApp().getSelectedFileFollowingPane();
		if (fileFollowingPane != null)
		{
			fileFollowingPane.restartFollowing();
		}
	}
}