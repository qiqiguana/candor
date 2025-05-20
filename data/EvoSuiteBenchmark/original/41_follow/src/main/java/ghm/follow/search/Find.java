package ghm.follow.search;

import ghm.follow.FollowApp;
import ghm.follow.gui.FollowAppAction;
import java.awt.Cursor;
import java.awt.event.ActionEvent;

public class Find extends FollowAppAction
{
	public static final String NAME = "find";

	private FindDialog dialog;

	public Find(FollowApp app)
	{
		super(app, FollowApp.getResourceString("action.Find.name"),
				FollowApp.getResourceString("action.Find.mnemonic"),
				FollowApp.getResourceString("action.Find.accelerator"),
				FollowApp.getIcon(Find.class, "action.Find.icon"),
				ActionContext.SINGLE_FILE);
	}

	public void actionPerformed(ActionEvent e)
	{
		getApp().setCursor(Cursor.WAIT_CURSOR);
		if (dialog == null)
		{
			dialog = new FindDialog(this);
			dialog.setLocationRelativeTo(getApp().getFrame());
			dialog.setLocation(100, 100);
			dialog.pack();
		}
		dialog.initFocus();
		dialog.setVisible(true);
		getApp().setCursor(Cursor.DEFAULT_CURSOR);
	}
}