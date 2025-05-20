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

/**
 * Klassenbeschreibung:
 * 
 * Beschreibung der Instanzvariablen: explorerModel: Hier wird das ExplorerModel
 * eingetragen, das das gesamte Modellgebilde repraesentiert. headerModel: Hier
 * wird das ObjectHeaderModel eingetragen, auf das die ConnectionFigure
 * refernziert. attributeModel: Hier wird das AttributeModel eingetragen, von
 * dem die Referenzierung ausgeht.
 * 
 * Das ConnectionModel wird referenziert von: Dem AttributeModel, von dem aus
 * eine Referenz gezeigt wird. den beiden ObjectModels, auf das oder von dem
 * referenziert wird, zusammen mit allen anderen ConnectionModels, die ebenfalls
 * auf diese oder von diesen referenzieren. StandardConnectionFigure:
 * Verknuepfung mit dem Model, damit die StandardConnectionFigure auf einen
 * Inhalt zurueckgreifen kann.
 **/
public class ConnectionModel extends Model {
	private ExplorerModel		explorerModel;
	private ObjectHeaderModel	headerModel;
	private AttributeModel		attributeModel;

	public ConnectionModel() {
		super();
	}

	public AttributeModel getAttributeModel() {
		return this.attributeModel;
	}

	public ExplorerModel getExplorerModel() {
		return this.explorerModel;
	}

	@Override
	public Figure getFigure() {
		return this.getFigureInModel();
	}

	public ObjectHeaderModel getHeaderModel() {
		return this.headerModel;
	}

	public void setAttributeModel(AttributeModel attrModl) {
		this.attributeModel = attrModl;
	}

	public void setExplorerModel(ExplorerModel explModl) {
		this.explorerModel = explModl;
	}

	@Override
	public void setFigure(Figure newFigure) {
		this.setFigureInModel(newFigure);
	}

	public void setHeaderModel(ObjectHeaderModel headModl) {
		this.headerModel = headModl;
	}
}
