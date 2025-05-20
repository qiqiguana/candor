/**
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

import javax.swing.ImageIcon;

/**
 * Pauses/unpauses the autoscrolling of files that are open in the Follow application.
 * 
 * @author <a href="mailto:carl.hall@gmail.com">Carl Hall</a>
 */
public class Pause extends FollowAppAction
{
	public static final String NAME = "pause";

	public Pause(FollowApp app)
	{
		// false is passed into getIcon(..) because a file follower never
		// starts in a paused state
		super(app, FollowApp.getResourceString("action.Pause.name"),
				FollowApp.getResourceString("action.Pause.mnemonic"),
				FollowApp.getResourceString("action.Pause.accelerator"),
				FollowApp.getIcon(Pause.class, getIconName(false)),
				ActionContext.SINGLE_FILE);
	}

	/**
	 * Handles actions performed relating to pause or playing a log's following
	 * 
	 * @param e
	 */
	public void actionPerformed(ActionEvent e)
	{
		playPausePane(getApp().getSelectedFileFollowingPane());
	}

	/**
	 * Plays or pauses a pane depending on it's current state. If following, it pauses. If not
	 * following, it plays.
	 * 
	 * @param pane
	 */
	public void playPausePane(FileFollowingPane pane)
	{
		if (pane.isFollowing())
		{
			if (pane.isFollowingPaused())
			{
				pane.unpauseFollowing();
			}
			else
			{
				pane.pauseFollowing();
			}
			setIconByState(pane.isFollowingPaused());
		}
	}

	/**
	 * Sets the icon of this action based on the provided pause state.
	 * 
	 * @param paused
	 */
	public void setIconByState(boolean paused)
	{
		// get the icon to be set
		ImageIcon icon = FollowApp.getIcon(Pause.class, Pause.getIconName(paused));

		// set the icon in the action. when updating here, the icon is changed
		// whether the event is caused by menu, button click or key combo
		setIcon(icon);
	}

	private static String getIconName(boolean paused)
	{
		String image = (paused ? "action.Pause.offIcon" : "action.Pause.onIcon");
		return image;
	}
}