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
package de.paragon.explorer.model;

import de.paragon.explorer.event.ExplorerFrameEventConverter;
import de.paragon.explorer.figure.Figure;
import de.paragon.explorer.util.ExplorerColorManager;
import de.paragon.explorer.util.ExplorerManager;
import de.paragon.explorer.util.StandardEnumeration;
import de.paragon.explorer.util.StandardEnumerator;

/**
 * Klassenbeschreibung: Das ExplorerModel repraesentiert das gesamte
 * Zusammenspiel der Models. Beschreibung der Instanzvariablen:
 * listOfAllExplorerd - Eine Liste aller ObjectModels, die ein Object
 * darstellen.
 * 
 * 
 * Das ExplorerModel wird referenziert von: ConnectionModel in der normalen
 * Hierarchieverknuepfung, ObjectModel in der normalen Hierarchieverknuepfung
 * 
 */
public class ExplorerModel extends Model {
	private StandardEnumeration			listOfAllExplored;
	// "noetig damit Explorermanager nicht aufgeraeumt wird, da er sonst nicht referenziert wuerde")
	@SuppressWarnings(value = { "unused" })
	private ExplorerManager				explorerManager;
	private String						name;
	private ExplorerColorManager		colorManager;
	private ExplorerFrameEventConverter	drawingFrameEventConverter;

	public ExplorerModel() {
		super();
	}

	public void addObjectModel(ObjectModel objModl) {
		this.getObjectModels().addElement(objModl);
	}

	public ExplorerColorManager getColorManager() {
		if (this.colorManager == null) {
			this.setColorManager(new ExplorerColorManager());
		}
		return this.colorManager;
	}

	public ExplorerFrameEventConverter getDrawingFrameEventConverter() {
		return this.drawingFrameEventConverter;
	}

	@Override
	public Figure getFigure() {
		return this.getFigureInModel();
	}

	public String getName() {
		return this.name;
	}

	public StandardEnumeration getObjectModels() {
		if (this.listOfAllExplored == null) {
			this.listOfAllExplored = new StandardEnumerator();
		}
		return this.listOfAllExplored;
	}

	@Override
	public boolean isExplorerModel() {
		return true;
	}

	public void removeObjectModel(ObjectModel objModl) {
		this.getObjectModels().removeElement(objModl);
	}

	public void setColorManager(ExplorerColorManager newColorManager) {
		this.colorManager = newColorManager;
	}

	public void setDrawingFrameEventConverter(ExplorerFrameEventConverter newDrawingFrameEventConverter) {
		this.drawingFrameEventConverter = newDrawingFrameEventConverter;
	}

	@edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "URF_UNREAD_FIELD", justification = "noetig damit Explorermanager nicht aufgeraeumt wird, da er sonst nicht referenziert wuerde")
	public void setExplorerManager(ExplorerManager newExplorerManager) {
		this.explorerManager = newExplorerManager;
	}

	@Override
	public void setFigure(Figure newFigure) {
		this.setFigureInModel(newFigure);
	}

	public void setName(String newName) {
		this.name = newName;
	}
}
