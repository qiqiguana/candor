/* 
Copyright (C) 2000-2003 Greg Merrill (greghmerrill@yahoo.com)

This file is part of Follow (http://follow.sf.net).

Follow is free software; you can redistribute it and/or modify
it under the terms of version 2 of the GNU General Public
License as published by the Free Software Foundation.

Follow is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Follow; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package ghm.follow.gui;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 * This class exists as a kludge to get around problems I was having with toolbar items being
 * configured by JToolBar in a way that was not to my liking.
 * 
 * @author <a href="mailto:greghmerrill@yahoo.com">Greg Merrill</a>
 * @author <a href="mailto:carl.hall@gmail.com">Carl Hall</a>
 */
public class ToolBar extends JToolBar
{
	void addFollowAppAction(FollowAppAction a)
	{
		this.add(a);
		JButton toolBarItem = (JButton) this.getComponent(this.getComponentCount() - 1);
		toolBarItem.setText(null);
		toolBarItem.setToolTipText((String) a.getValue(Action.NAME));
	}
}