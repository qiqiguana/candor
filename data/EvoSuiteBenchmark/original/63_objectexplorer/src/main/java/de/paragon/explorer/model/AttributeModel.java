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
import de.paragon.explorer.figure.TextBoxFigure;
import de.paragon.explorer.util.AttributeTitleManager;

/**
 * Klassenbeschreibung: Ein AttributeModel stellt ein Attribute -sprich eine
 * Instanzvariable- dar. Es referenziert auf ein Field und auf ein ObjectModel.
 * Anhand dieser Instanzvariablen kann es das entsprechende Objekt, das die
 * entsprechende Instanzvariable referenziert, ermitteln. Die visuelle
 * Darstellung eines AttributeModels besteht aus zwei Figuren, nicht nur aus
 * einer. Deshalb hat das AttributeModel neben der vererbten Instanzvariable
 * figure eine weitere: ConnectionFigure. Daraus gehen die beiden Bestandteile
 * der Figur hervor: Eine TextBoxFigure und eine ConnectionFigure. Beschreibung
 * der Instanzvariablen: In objectModel wird das ObjectModel gesetzt, zu dem das
 * AttributeModel gehoert. field ist eigentlich selbsterklaerend. In
 * connectionModel wird ein ConnectionModel gespeichert, falls eine Verbindung
 * zum entsprechenden Object gezeigt wird.
 * 
 * Das AttributeModel wird referenziert von: Dem ConnectionModel, das eine
 * Referenz zeigt (falls eines existiert), dem ObjectModel zusammen mit allen
 * anderen Attributmodellen des entsprechenden ObjectModels in der normalen
 * Hierarchieverknuepfung
 **/
public class AttributeModel extends ObjectModelPart {
	private ObjectModel		objectModel;
	private ConnectionModel	connectionModel;
	private int				modifiers;
	private Object			value;
	private Class<?>		type;
	private String			name;

	public AttributeModel() {
		super();
	}

	private AttributeTitleManager getAttributeTitleManager() {
		return AttributeTitleManager.getSingleton();
	}

	@Override
	public String getCompleteTitle() {
		return this.getAttributeTitleManager().getCompleteTitle(this);
	}

	public ConnectionModel getConnectionModel() {
		return this.connectionModel;
	}

	@Override
	public Figure getFigure() {
		return this.getFigureInModel();
	}

	public int getModifiers() {
		return this.modifiers;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public ObjectModel getObjectModel() {
		return this.objectModel;
	}

	public String getTitle() {
		return this.getAttributeTitleManager().getTitle(this);
	}

	public Class<?> getType() {
		return this.type;
	}

	public Object getValue() {
		return this.value;
	}

	@Override
	public boolean isAttributeModel() {
		return true;
	}

	public boolean isAttributePrimitive() {
		if (this.getType().isArray()) {
			return false;
		}
		return this.getType().isPrimitive();
	}

	public boolean isVisible() {
		return ((TextBoxFigure) this.getFigure()).isVisible();
	}

	public void setConnectionModel(ConnectionModel modl) {
		this.connectionModel = modl;
	}

	@Override
	public void setFigure(Figure newFigure) {
		this.setFigureInModel(newFigure);
	}

	public void setModifiers(int newModifiers) {
		this.modifiers = newModifiers;
	}

	public void setName(String newName) {
		this.name = newName;
	}

	public void setObjectModel(ObjectModel model) {
		this.objectModel = model;
	}

	public void setType(Class<?> newType) {
		this.type = newType;
	}

	public void setValue(Object newValue) {
		this.value = newValue;
	}
}
