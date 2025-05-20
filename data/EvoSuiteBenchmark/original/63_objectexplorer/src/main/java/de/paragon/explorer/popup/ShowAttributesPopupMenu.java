/**
 * [ObjectExplorer4J - Tool zur grafischen Darstellung von Objekten und ihren
 * Referenzen]
 * 
 * Copyright (C) [2009] [PARAGON Systemhaus GmbH]
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, see <http://www.gnu.org/licenses/>.
 **/
package de.paragon.explorer.popup;

import java.awt.CheckboxMenuItem;

public class ShowAttributesPopupMenu extends java.awt.PopupMenu {
	private static final String	EXPLORED			= "explored";
	private static final String	ALL					= "all";
	private static final long	serialVersionUID	= -6388133794153920830L;

	/**
	 * ShowAttributesPopupMenu constructor comment.
	 */
	public ShowAttributesPopupMenu() {
		super();
		this.init();
	}

	/**
	 * ShowAttributesPopupMenu constructor comment.
	 * 
	 * @param label
	 *            String
	 */
	public ShowAttributesPopupMenu(String label) {
		super(label);
		this.init();
	}

	private void init() {
		this.add(new CheckboxMenuItem(ShowAttributesPopupMenu.ALL));
		this.add(new CheckboxMenuItem(ShowAttributesPopupMenu.EXPLORED));
	}
}
