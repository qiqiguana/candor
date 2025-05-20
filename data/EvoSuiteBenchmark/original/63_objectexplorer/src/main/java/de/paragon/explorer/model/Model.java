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
 * Klassenbeschreibung: Die abstrakte Klasse Model ermoeglicht fuer alle
 * Subklassen:
 * 
 * Instanzvariablen: figure: Die Verknuepfung mit einer Figure, die den Inhaltes
 * eines Models visuell darstellen soll. Konkret bedeutet dies fuer die
 * Explorer-Subklassen: ExplorerModel-ExplorerFigure; die ExplorerFigure stellt
 * den gesamten Inhalt - geschachtelt aehn- lich wie die Hierarchie der Models -
 * dar. ObjectModels-ListBoxFigure; Eine ListBoxFigure stellt ein Object dar,
 * das durch ein Object Model repraesentiert wird. ObjectHeaderModel und
 * AttributeModel-TextBox- Figure; eine TextBoxFigure stellt den Klassen- bzw.
 * Feldnamen in Stringform graphisch dar.
 * ConnectionModel-StandardConnectionFigure; Die StandardConnectionFigure stellt
 * eine Referenz von einem Feld auf ein Object graphisch dar, falls diese
 * Darstellung erwuenscht ist.
 * 
 * object: Das Object, das das jeweilige SubModel repraesentiert.
 * 
 * Ein Objekt dieser Klasse wird referenziert von: ExplorerFigure,
 * ListBoxFigure, TextBoxFigure zur inhaltlichen Darstellung dieser Figuren.
 */
public abstract class Model {
	private Object	object;
	private Figure	figureInModel;

	protected Figure getFigureInModel() {
		return this.figureInModel;
	}

	protected void setFigureInModel(Figure newFigureInModel) {
		this.figureInModel = newFigureInModel;
	}

	public Model() {
		super();
	}

	public abstract Figure getFigure();

	public Object getObject() {
		return this.object;
	}

	public boolean isAttributeModel() {
		return false;
	}

	public boolean isExplorerModel() {
		return false;
	}

	public boolean isObjectHeaderModel() {
		return false;
	}

	public boolean isObjectModel() {
		return false;
	}

	public abstract void setFigure(Figure newFigure);

	public void setObject(Object newObject) {
		this.object = newObject;
	}
}
