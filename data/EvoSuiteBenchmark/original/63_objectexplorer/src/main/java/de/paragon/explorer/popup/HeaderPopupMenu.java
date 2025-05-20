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
import java.awt.MenuItem;
import java.awt.PopupMenu;

import de.paragon.explorer.ProjectConstants4ObjectExplorer;
import de.paragon.explorer.util.ObjectViewManager;
import de.paragon.explorer.util.PropertyManager;

/**
 * Kommentar: Die PopupMenus sind vom StandardDrawingFrame entkoppelt worden.
 * Die das PopupMenu aufrufende Klasse muss lediglich wissen, wieviel Eintraege
 * fuer das PopupMenu vorgesehen sind, alles andere laesst sich notfalls noch
 * aendern.
 */
public class HeaderPopupMenu extends PopupMenu {
	private static final long	serialVersionUID	= -3014981243662669326L;

	public HeaderPopupMenu() {
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
		this.add(new MenuItem(ProjectConstants4ObjectExplorer.getHIDE_OBJECT()));
		this.add(new MenuItem(ProjectConstants4ObjectExplorer.getHIDE_OBJECT_WITH_DEPENDENCIES()));
		this.add(new MenuItem(ProjectConstants4ObjectExplorer.getEXPLORE_OBJECT()));
		this.add(new MenuItem(ProjectConstants4ObjectExplorer.getCOPY_OBJECT()));
		this.add(new MenuItem(ProjectConstants4ObjectExplorer.getUPDATE_OBJECT()));
		this.addSeparator();
		this.add(new CheckboxMenuItem(ProjectConstants4ObjectExplorer.getSHOW_ATTRIBUTES()));
		this.add(new CheckboxMenuItem(ProjectConstants4ObjectExplorer.getHIDE_NULL_ATTRIBUTES(), PropertyManager.getInstance().getHideNullAttribut()));
		this.add(new CheckboxMenuItem(ProjectConstants4ObjectExplorer.getHIDE_STATIC_ATTRIBUTES(), PropertyManager.getInstance().getHideStaticAttribut()));
		this.add(new CheckboxMenuItem(ProjectConstants4ObjectExplorer.getHIDE_UNEXPLORED_ATTRIBUTES(), PropertyManager.getInstance().getHideUnexploredAttribut()));
		this.addSeparator();
		// this.add(new
		// CheckboxMenuItem(ProjectConstants4ObjectExplorer.getREMOVE_DEPENDENCIES()));
		// this.addSeparator();
		this.add(new MenuItem(ProjectConstants4ObjectExplorer.getOBJECT_VIEW()));
	}

	public void initialize(ObjectViewManager objView) {
		this.enable(ProjectConstants4ObjectExplorer.getSHOW_ATTRIBUTES(), objView.hasAttributesToDisplay());
		if (objView.hasAttributesToDisplay()) {
			this.setState(ProjectConstants4ObjectExplorer.getSHOW_ATTRIBUTES(), objView.isAllAttributesVisible());
		}
		else {
			this.setState(ProjectConstants4ObjectExplorer.getSHOW_ATTRIBUTES(), false);
		}
		if (objView.isAllAttributesVisible()) {
			this.enable(ProjectConstants4ObjectExplorer.getHIDE_NULL_ATTRIBUTES(), true);
			this.enable(ProjectConstants4ObjectExplorer.getHIDE_STATIC_ATTRIBUTES(), true);
			this.enable(ProjectConstants4ObjectExplorer.getHIDE_UNEXPLORED_ATTRIBUTES(), true);
		}
		else {
			this.enable(ProjectConstants4ObjectExplorer.getHIDE_NULL_ATTRIBUTES(), false);
			this.enable(ProjectConstants4ObjectExplorer.getHIDE_STATIC_ATTRIBUTES(), false);
			this.enable(ProjectConstants4ObjectExplorer.getHIDE_UNEXPLORED_ATTRIBUTES(), false);
		}
		this.setState(ProjectConstants4ObjectExplorer.getHIDE_NULL_ATTRIBUTES(), !(objView.isNullAttributesVisible()));
		this.setState(ProjectConstants4ObjectExplorer.getHIDE_STATIC_ATTRIBUTES(), !(objView.isStaticAttributesVisible()));
		this.setState(ProjectConstants4ObjectExplorer.getHIDE_UNEXPLORED_ATTRIBUTES(), !(objView.isUnexploredAttributesVisible()));
	}

	public synchronized void setState(String name, boolean state) {
		for (int i = 0; i < this.getItemCount(); i++) {
			MenuItem item = this.getItem(i);
			if (name.equals(item.getLabel())) {
				((CheckboxMenuItem) item).setState(state);
				return;
			}
		}
	}
}
