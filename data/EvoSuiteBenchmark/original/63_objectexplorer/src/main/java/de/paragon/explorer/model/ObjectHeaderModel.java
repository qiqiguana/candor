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

import de.paragon.explorer.figure.Figure;
import de.paragon.explorer.util.ObjectTitleManager;

/**
 * Klassenbeschreibung:
 * 
 * Instanzvariablen: objectModel: Das Modell fuer das entsprechende Object.
 * 
 * Das ObjectHeaderModel wird referenziert von: Dem ConnectionModel, das eine
 * Referenz zu dem Object, das das ObjectModel dieses Ob- jectHeaderModels
 * referenziert, darstellt. Dem ObjectModel in der normalen Hierarchie-
 * beziehung,
 */
public class ObjectHeaderModel extends ObjectModelPart {
	private ObjectModel	objectModel;

	public ObjectHeaderModel() {
		super();
	}

	@Override
	public String getCompleteTitle() {
		return this.getObjectTitleManager().getCompleteTitle(this.getObjectModel());
	}

	@Override
	public Figure getFigure() {
		return this.getFigureInModel();
	}

	@Override
	public ObjectModel getObjectModel() {
		return this.objectModel;
	}

	private ObjectTitleManager getObjectTitleManager() {
		return ObjectTitleManager.getSingleton();
	}

	public String getTitle() {
		return this.getObjectTitleManager().getTitle(this.getObjectModel());
	}

	@Override
	public boolean isObjectHeaderModel() {
		return true;
	}

	@Override
	public void setFigure(Figure newFigure) {
		this.setFigureInModel(newFigure);
	}

	protected void setObjectModel(ObjectModel model) {
		this.objectModel = model;
	}
}
