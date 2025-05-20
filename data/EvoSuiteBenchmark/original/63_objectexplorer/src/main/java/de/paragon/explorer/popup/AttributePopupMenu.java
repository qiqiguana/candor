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

import java.awt.MenuItem;
import java.awt.PopupMenu;

import de.paragon.explorer.ProjectConstants4ObjectExplorer;
import de.paragon.explorer.model.AttributeModel;

/**
 * Kommentar: Die PopupMenus sind vom StandardDrawingFrame entkoppelt worden.
 * Die das PopupMenu aufrufende Klasse muss lediglich wissen, wieviel Eintraege
 * fuer das PopupMenu vorgesehen sind, alles andere laesst sich notfalls noch
 * aendern.
 */
public class AttributePopupMenu extends PopupMenu {
	private static final long	serialVersionUID	= 1036826928834939565L;

	// private static AttributePopupMenu singleton;
	public AttributePopupMenu() {
		super();
		this.init();
	}

	public synchronized void enable(String name, boolean state) {
		for (int i = 0; i < this.getItemCount(); i++) {
			MenuItem item = this.getItem(i);
			if (name.equals(item.getLabel())) {
				item.setEnabled(state);
				return;
			}
		}
	}

	private void init() {
		this.add(new MenuItem(ProjectConstants4ObjectExplorer.getHIDE_ATTRIBUTE()));
		this.add(new MenuItem(ProjectConstants4ObjectExplorer.getUPDATE_ATTRIBUTES()));
		this.addSeparator();
		this.add(new MenuItem(ProjectConstants4ObjectExplorer.getSHOW_REFERENCE()));
		this.add(new MenuItem(ProjectConstants4ObjectExplorer.getHIDE_REFERENCE()));
	}

	public void initialize(AttributeModel attrModl) {
		this.enable(ProjectConstants4ObjectExplorer.getSHOW_REFERENCE(), !(attrModl.isAttributePrimitive()) && (attrModl.getConnectionModel() == null));
		this.enable(ProjectConstants4ObjectExplorer.getHIDE_REFERENCE(), !(attrModl.getConnectionModel() == null));
	}
}
