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

import de.paragon.explorer.figure.ExplorerFigure;
import de.paragon.explorer.figure.ExplorerFigureBuilder;
import de.paragon.explorer.figure.ListBoxFigure;
import de.paragon.explorer.figure.TextBoxFigure;
import de.paragon.explorer.gui.ExplorerDrawingPanel;
import de.paragon.explorer.util.ExplorerManager;

public final class ExplorerModelBuilder {
	private static ExplorerModelBuilder	singleton;

	public static ExplorerModelBuilder getInstance() {
		return ExplorerModelBuilder.getSingleton();
	}

	private static ExplorerModelBuilder getSingleton() {
		if (ExplorerModelBuilder.singleton == null) {
			ExplorerModelBuilder.setSingleton(new ExplorerModelBuilder());
		}
		return ExplorerModelBuilder.singleton;
	}

	private static void setSingleton(ExplorerModelBuilder builder) {
		ExplorerModelBuilder.singleton = builder;
	}

	private ExplorerModelBuilder() {
		super();
	}

	/**
	 * Kommentar: Als erstes wird ein neues ExplorerModel erzeugt. Danach wird
	 * eine ExplorerFigure erzeugt. Diese wird dann mit dem ExplorerModel
	 * verknuepft. Generell gilt: Der Builder, der eine Figur oder ein Model
	 * frisch erzeugt hat, ist fuer die Verknuepfung der unmittelbar zuvor
	 * erzeugten Figur bzw. dem unmittelbar zuvor erzeugten Model zustaendig.
	 * Nach der Verknuepfung von ExplorerModel und ExplorerFigure wird ein neues
	 * ObjectModel erzeugt. Anschliessend werden ObjectModel und ExplorerModel
	 * verknuepft. Dann wird eine neue ListBoxFigure erzeugt. Diese wird dann
	 * mit dem ObjectModel verknuepft. Schliesslich schliesst sich der Kreis mit
	 * der Verknuepfung von ListBoxFigure und Explorer-Figure. Diese wird
	 * vorgenommen von dem ExplorerFigureBuilder. Anschliessend wird das
	 * ObjectModel weiter ausgebaut. Eine Beschreibung hierzu siehe im
	 * ObjectModelBuilder in der Methode buildObjectModel(ObjectModel objModl,
	 * Object object)! Ganz zum Schluss wird die fertige ListBoxFigure in der
	 * ExplorerFigure plaziert, das heisst, ihre Position innerhalb der
	 * ExplorerFigure wird festgelegt. Danach wird sie gezeichnet und ein neuer
	 * EventConverter wird erzeugt, bei dem die ExplorerFigure eingetragen wird.
	 */
	public ExplorerDrawingPanel add1stModel(Object object) {
		ExplorerModel explModl = this.createNewExplorerModelWithFigure();
		if (object != null) {
			ObjectModel objModl = this.createNewObjectModel(explModl);
			this.getObjectModelBuilder().buildObjectModel(objModl, object);
			this.getExplorerFigureBuilder().initialize1stList((ListBoxFigure) objModl.getFigure());
			explModl.setDrawingFrameEventConverter(this.createNewEventConverter((ExplorerFigure) explModl.getFigure()));
			((ExplorerFigure) explModl.getFigure()).repaint();
			this.getExplorerManager().addExplorerModel(explModl);
		}
		return ((ExplorerFigure) explModl.getFigure()).getPanel();
	}

	/**
	 * Kommentar: Als erstes wird ein neues ExplorerModel erzeugt. Danach wird
	 * eine ExplorerFigure erzeugt. Diese wird dann mit dem ExplorerModel
	 * verknuepft. Generell gilt: Der Builder, der eine Figur oder ein Model
	 * frisch erzeugt hat, ist fuer die Verknuepfung der unmittelbar zuvor
	 * erzeugten Figur bzw. dem unmittelbar zuvor erzeugten Model zustaendig.
	 * Nach der Verknuepfung von ExplorerModel und ExplorerFigure wird ein neues
	 * ObjectModel erzeugt. Anschliessend werden ObjectModel und ExplorerModel
	 * verknuepft. Dann wird eine neue ListBoxFigure erzeugt. Diese wird dann
	 * mit dem ObjectModel verknuepft. Schliesslich schliesst sich der Kreis mit
	 * der Verknuepfung von ListBoxFigure und Explorer-Figure. Diese wird
	 * vorgenommen von dem ExplorerFigureBuilder. Anschliessend wird das
	 * ObjectModel weiter ausgebaut. Eine Be- schreibung hierzu siehe im
	 * ObjectModelBuilder in der Methode buildObjectModel(ObjectModel objModl,
	 * Object object)! Ganz zum Schluss wird die fertige ListBoxFigure in der
	 * ExplorerFigure plaziert, das heisst, ihre Position innerhalb der
	 * ExplorerFigure wird festgelegt. Danach wird sie gezeichnet und ein neuer
	 * EventConverter wird erzeugt, bei dem die ExplorerFigure eingetragen wird.
	 */
	public void addModel(ExplorerModel explModl, Object object) {
		ObjectModel objModl = this.createNewObjectModel(explModl);
		this.getObjectModelBuilder().buildObjectModel(objModl, object);
		this.getExplorerFigureBuilder().initializeCopyList((ListBoxFigure) objModl.getFigure());
		this.getExplorerFigureBuilder().draw((ListBoxFigure) objModl.getFigure());
	}

	private void addNewConnectedObjectModelFor(TextBoxFigure tbf) {
		AttributeModel attrModl = (AttributeModel) tbf.getModel();
		// ObjectModel objModl =
		// this.createNewObjectModel(attrModl.getObjectModel().getExplorerModel()
		// );
		ObjectModel objModl = this.createNewObjectModel(tbf);
		this.getObjectModelBuilder().buildObjectModel(objModl, attrModl.getValue());
		this.getExplorerFigureBuilder().initializeListFor(objModl, attrModl);
		this.getExplorerFigureBuilder().draw((ListBoxFigure) objModl.getFigure());
		this.getConnectionBuilder().addConnection(objModl.getHeaderModel(), attrModl);
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass lediglich die fertig
	 * erstellte ListBoxFigure noch innerhalb der Explorer- Figure positioniert
	 * werden muss.
	 */
	private void connect(ExplorerModel explModl, ExplorerFigure explFig) {
		explModl.setFigure(explFig);
		explFig.setModel(explModl);
	}

	private de.paragon.explorer.event.ExplorerFrameEventConverter createNewEventConverter(ExplorerFigure explFig) {
		return de.paragon.explorer.Explorer.getEventConverter(explFig);
	}

	/**
	 * Kommentar: Diese Methode erzeugt ein neues ExplorerModel, eine neue
	 * ExplorerFigure und verknuepft diese beiden.
	 */
	public ExplorerModel createNewExplorerModelWithFigure() {
		ExplorerModel explModl;
		ExplorerFigure explFig;
		explModl = new ExplorerModel();
		explFig = this.getExplorerFigureBuilder().createNewExplorerFigure();
		this.connect(explModl, explFig);
		return explModl;
	}

	private ObjectModel createNewObjectModel(ExplorerModel explModl) {
		return this.getObjectModelBuilder().createNewObjectModel(explModl);
	}

	private ObjectModel createNewObjectModel(TextBoxFigure tbf) {
		return this.getObjectModelBuilder().createNewObjectModel(tbf);
	}

	private de.paragon.explorer.util.ConnectionBuilder getConnectionBuilder() {
		return de.paragon.explorer.util.ConnectionBuilder.getInstance();
	}

	private ExplorerFigureBuilder getExplorerFigureBuilder() {
		return ExplorerFigureBuilder.getInstance();
	}

	public ExplorerManager getExplorerManager() {
		return ExplorerManager.INSTANCE;
	}

	private ObjectModelBuilder getObjectModelBuilder() {
		return ObjectModelBuilder.getInstance();
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass existieren: 1.
	 * ExplorerFigure verknuepft mit einem ExplorerModel; 2. Mindestens eine
	 * fertige ListBoxFigure. Diese Methode macht: Sie ist dann aufzurufen, wenn
	 * weder eine zu referenzierende ListBoxFigure noch die dazugehoerige
	 * ConnectionFigure da ist. Sie erzeugt ein neues ObjectModel incl.
	 * ListBoxFigure; baut dieses ObjectModel aus und fuegt sie abhaengig von
	 * dem AttributeModel, das es referenziert, in die ExplorerFigure ein.
	 * Anschliessend fuegt sie die verbindende ConnectionFigure ein. "add" steht
	 * hierbei fuer create, build und draw.
	 */
	public void handleAttributeModel(TextBoxFigure tbf) {
		if (!(((AttributeModel) tbf.getModel()).isAttributePrimitive())) {
			this.addNewConnectedObjectModelFor(tbf);
		}
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass noch alle Verknuepfungen
	 * existieren, also noch nicht geloescht worden ist. Zunaechst wird das
	 * Objektmodell mit der zugehoerigen ListBoxFigure von der ExplorerFigure
	 * entfernt. Ein Objektmodell zu loeschen bedeutet, es aus der Liste
	 * objectModels des ExplorerModels zu entfernen. Dann geschieht Analoges mit
	 * den Connections, die auf das Modell zeigen oder vom Modell wegzeigen.
	 */
	public void removeFromExplorer(ObjectModel objModl) {
		objModl.getExplorerModel().removeObjectModel(objModl);
		this.getExplorerFigureBuilder().removeFromExplorer((ListBoxFigure) objModl.getFigure());
		this.getConnectionBuilder().removeConnections(objModl);
	}
}
