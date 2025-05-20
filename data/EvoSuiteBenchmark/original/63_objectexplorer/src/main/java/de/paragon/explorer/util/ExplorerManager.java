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
package de.paragon.explorer.util;

import java.util.Collection;
import java.util.Vector;

import de.paragon.explorer.figure.ExplorerFigure;
import de.paragon.explorer.model.ExplorerModel;

/**
 * Diese Klasse verwaltet die exitierenden ExplorerFigures
 * 
 * Singleton Umsetzung nach Item 3 in Effective Java - Second Edition
 */
public enum ExplorerManager {
	INSTANCE;
	private static final String			PARAKLET_OBJECT_EXPLORER4J	= "PARAKLET ObjectExplorer4J - ";
	private Collection<ExplorerModel>	existingExplorerModels;
	private int							lastUsedNumber;

	/**
	 * ExplorerManager constructor comment.
	 */
	private ExplorerManager() {
	}

	public void addExplorerModel(ExplorerModel explModl) {
		int actualNumber;
		this.getExistingExplorerModels().add(explModl);
		// noetig damit Explorermanager nicht aufgeraeumt wird, da er sonst
		// nicht referenziert wuerde
		explModl.setExplorerManager(this);
		actualNumber = this.getLastUsedNumber() + 1;
		explModl.setName(ExplorerManager.PARAKLET_OBJECT_EXPLORER4J + Integer.valueOf(actualNumber).toString());
		this.setLastUsedNumber(actualNumber);
		if (((ExplorerFigure) explModl.getFigure()).getFrame() != null) {
			((ExplorerFigure) explModl.getFigure()).getFrame().setTitle(explModl.getName());
		}
	}

	public Collection<ExplorerModel> getExistingExplorerModels() {
		if (this.existingExplorerModels == null) {
			this.setExistingExplorerModels(new Vector<ExplorerModel>());
		}
		return this.existingExplorerModels;
	}

	private int getLastUsedNumber() {
		return this.lastUsedNumber;
	}

	private void setExistingExplorerModels(Collection<ExplorerModel> newExistingExplorerModels) {
		this.existingExplorerModels = newExistingExplorerModels;
	}

	private void setLastUsedNumber(int newLastUsedNumber) {
		this.lastUsedNumber = newLastUsedNumber;
	}
}
