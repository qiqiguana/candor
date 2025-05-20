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

import javax.swing.Icon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * This class exists as a kludge to get around problems I was having with menu items being
 * configured by JMenu in a way that was not to my liking.
 * 
 * @author <a href="mailto:greghmerrill@yahoo.com">Greg Merrill</a>
 */
public class Menu extends JMenu
{

	public Menu(String name, String mnemonic)
	{
		super(name);
		setMnemonic(mnemonic.charAt(0));
	}

	public Menu(String name, String mnemonic, Icon icon)
	{
		super(name);
		setMnemonic(mnemonic.charAt(0));
		setIcon(icon);
	}

	public void addFollowAppAction(FollowAppAction a)
	{
		this.add(a);
		JMenuItem menuItem = this.getItem(this.getItemCount() - 1);
		menuItem.setMnemonic(a.getMnemonic());
		menuItem.setAccelerator(a.getAccelerator());
	}

}
