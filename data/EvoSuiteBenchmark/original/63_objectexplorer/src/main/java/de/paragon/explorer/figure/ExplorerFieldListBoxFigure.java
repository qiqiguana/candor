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
package de.paragon.explorer.figure;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import de.paragon.explorer.event.ExplorerPopupActionConverter;

public class ExplorerFieldListBoxFigure extends ListBoxFigure {
	private List<ExplorerFieldListBoxFigure>	children;

	public ExplorerFieldListBoxFigure() {
		super();
	}

	public void addChild(ExplorerFieldListBoxFigure child) {
		this.getChildren().add(child);
	}

	public List<ExplorerFieldListBoxFigure> getAllChildren() {
		List<ExplorerFieldListBoxFigure> allChildFigures = new Vector<ExplorerFieldListBoxFigure>();
		Iterator<ExplorerFieldListBoxFigure> it = this.getChildren().iterator();
		while (it.hasNext()) {
			ExplorerFieldListBoxFigure child = it.next();
			allChildFigures.add(child);
			List<ExplorerFieldListBoxFigure> allChildAdaptorChildren = child.getAllChildren();
			allChildFigures.addAll(allChildAdaptorChildren);
		}
		return allChildFigures;
	}

	/**
	 * @return Returns the children.
	 */
	public List<ExplorerFieldListBoxFigure> getChildren() {
		if (this.children == null) {
			this.children = new Vector<ExplorerFieldListBoxFigure>();
		}
		return this.children;
	}

	public void removeAllChildren(ExplorerPopupActionConverter converter) {
		if (!this.getChildren().isEmpty()) {
			Iterator<ExplorerFieldListBoxFigure> it = this.getChildren().iterator();
			while (it.hasNext()) {
				ExplorerFieldListBoxFigure child = it.next();
				child.removeAllChildren(converter);
				converter.removeChildListBoxFigure(child);
			}
			this.setChildren(null);
		}
	}

	public void removeChild(ExplorerFieldListBoxFigure child) {
		this.getChildren().remove(child);
	}

	/**
	 * @param children
	 *            The children to set.
	 */
	public void setChildren(List<ExplorerFieldListBoxFigure> newChildren) {
		this.children = newChildren;
	}
}
