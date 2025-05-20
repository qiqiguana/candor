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

import java.util.Vector;

import de.paragon.explorer.figure.ListBoxFigureBuilder;
import de.paragon.explorer.figure.TextBoxFigure;
import de.paragon.explorer.util.ObjectViewManager;
import de.paragon.explorer.util.PropertyManager;
import de.paragon.explorer.util.StandardEnumeration;
import de.paragon.explorer.util.StandardEnumerator;

public final class ObjectModelBuilder {
	private static ObjectModelBuilder	singleton;

	public static ObjectModelBuilder getInstance() {
		return ObjectModelBuilder.getSingleton();
	}

	private static ObjectModelBuilder getSingleton() {
		if (ObjectModelBuilder.singleton == null) {
			ObjectModelBuilder.setSingleton(new ObjectModelBuilder());
		}
		return ObjectModelBuilder.singleton;
	}

	private static void setSingleton(ObjectModelBuilder builder) {
		ObjectModelBuilder.singleton = builder;
	}

	private ObjectModelBuilder() {
		super();
	}

	protected StandardEnumeration asEnumeration(Object[] anArray) {
		Vector<Object> aVector = new Vector<Object>();
		for (int i = 0; i < anArray.length; i++) {
			aVector.addElement(anArray[i]);
		}
		return new StandardEnumerator(aVector);
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass das ExplorerModel, das
	 * ObjectModel, die ExplorerFigure und die ListBoxFigure schon miteinander
	 * verknuepft sind. Zunaechst wird das HeaderModel erzeugt und mit dem
	 * ObjectModel verknuepft. Analog wird fuer jedes Feld des Objekts ein
	 * AttributeModel erzeugt und mit dem ObjectModel verknuepft. Dann wird
	 * allen AttributeModels ihr jeweiliger Wert, also das Objekt, auf das das
	 * jeweilige Feld zeigt, zugewiesen. Fuer alle Teile des ObjectModels (also
	 * die Vereinigung von ObjectHeaderModel und AttributeModels) wird je eine
	 * TextBox- Figure erzeugt und mit dem jeweiligen Model verknuepft. Jede der
	 * soeben erzeugten TextBoxFigures erhaelt den ent- sprechenden
	 * darzustellenden Text. Danach wird der mit dem HeaderModel verknuepften
	 * TextBox- Figure der darzustellende Text zugewiesen, anhand dessen die
	 * Groesse bestimmt wird, und anschliessend wird sie mit derselben
	 * ListBoxFigure verknuepft, die mit der Explorer-Figure verknuepft ist, die
	 * die Graphics kennt, anhand derer die Groesse bestimmt wurde. Dann wird
	 * eine FilledRectangleFigure mit der ListBoxFigure verknuepft. Schliesslich
	 * wird mit allen mit einem AttributeModel verknuepften TextBoxFigures
	 * analog zum HeaderModel verfahren. Zum Schluss wird noch die Groesse der
	 * ListBoxFigure korrigiert.
	 */
	public void buildObjectModel(ObjectModel objModl, Object object) {
		this.setObject(objModl, object);
		this.createModelParts(objModl);
		this.setAttributes(objModl);
		this.getListBoxFigureBuilder().buildListBoxFigure(objModl);
		this.initializeObjectViewManager(objModl);
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass ExplorerModel,
	 * ExplorerFigure, ObjectModel und ListBoxFigure miteinander verknuepft
	 * sind. Diese Methode erzeugt ein HeaderModel und verknuepft dies mit dem
	 * objectModel. Dasselbe geschieht anschliessend fuer alle Felder des
	 * Objects. Fuer jedes Feld wird ein AttributeModel erzeugt und verknuepft.
	 */
	private void createModelParts(ObjectModel objModl) {
		this.createNewHeaderModel(objModl);
		for (int i = 0; i < objModl.getNumberOfAttributes(); i++) {
			if (objModl.isArrayObject()) {
				this.getAttributeModelBuilder().createArrayAttributeModel(objModl);
			}
			else {
				this.getAttributeModelBuilder().createStandardAttributeModel(objModl);
			}
		}
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass das ObjectModel bereits mit
	 * einem Object versehen ist. Erzeugt ein neues HeaderModel und verknuepft
	 * es mit dem ObjectModel.
	 */
	private void createNewHeaderModel(ObjectModel objModl) {
		objModl.setHeaderModel(new ObjectHeaderModel());
		objModl.getHeaderModel().setObjectModel(objModl);
	}

	private void createNewListBoxFigure(ObjectModel objModl) {
		this.getListBoxFigureBuilder().createNewListBoxFigure(objModl);
	}

	private void createNewListBoxFigure(ObjectModel objModl, TextBoxFigure tbf) {
		this.getListBoxFigureBuilder().createNewListBoxFigure(objModl, tbf);
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass bereits das ExplorerModel
	 * mit einer ExplorerFigur verknuepft wurde. Sie erzeugt ein neues
	 * ObjectModel und laesst es mit dem ExplorerModel verknuepfen.
	 * Anschliessend veranlasst sie, dies auch mit einer neu zu erzeugenden
	 * ListBoxFigure zu tun.
	 * 
	 * @param explModl
	 *            ExplorerModel
	 * 
	 * @return ObjectModel
	 */
	protected ObjectModel createNewObjectModel(ExplorerModel explModl) {
		ObjectModel objModl = new ObjectModel();
		explModl.addObjectModel(objModl);
		objModl.setExplorerModel(explModl);
		this.createNewListBoxFigure(objModl);
		return objModl;
	}

	/**
	 * 
	 * @param tbf
	 *            TextBoxFigure
	 * 
	 * @return ObjectModel
	 */
	protected ObjectModel createNewObjectModel(TextBoxFigure tbf) {
		ExplorerModel explModel = ((AttributeModel) tbf.getModel()).getObjectModel().getExplorerModel();
		ObjectModel objModl = new ObjectModel();
		explModel.addObjectModel(objModl);
		objModl.setExplorerModel(explModel);
		this.createNewListBoxFigure(objModl, tbf);
		return objModl;
	}

	private AttributeModelBuilder getAttributeModelBuilder() {
		return AttributeModelBuilder.getInstance();
	}

	private ListBoxFigureBuilder getListBoxFigureBuilder() {
		return ListBoxFigureBuilder.getInstance();
	}

	private void initializeObjectViewManager(ObjectModel objModl) {
		ObjectViewManager objView = objModl.getObjectViewManager();
		objView.setAllAttributesVisible(false);
		objView.setNullAttributesVisible(PropertyManager.getInstance().getHideNullAttribut());
		objView.setStaticAttributesVisible(PropertyManager.getInstance().getHideStaticAttribut());
		objView.setUnexploredAttributesVisible(PropertyManager.getInstance().getHideUnexploredAttribut());
		objView.setAttributesToDisplay(!(objModl.isNullObject()));
	}

	public void setAttributes(ObjectModel objModl) {
		if (objModl.isArrayObject()) {
			this.getAttributeModelBuilder().setArrayAttributeData(objModl);
		}
		else {
			this.getAttributeModelBuilder().setStandardAttributeData(objModl);
		}
	}

	public void setObject(ObjectModel objModl, Object object) {
		objModl.setObject(object);
	}
}
