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

import org.apache.log4j.Logger;

import de.paragon.explorer.figure.ExplorerFigure;
import de.paragon.explorer.figure.ExplorerFigureBuilder;
import de.paragon.explorer.figure.ListBoxFigure;
import de.paragon.explorer.figure.StandardConnectionFigure;
import de.paragon.explorer.figure.TextBoxFigure;
import de.paragon.explorer.gui.Warning;
import de.paragon.explorer.model.AttributeModel;
import de.paragon.explorer.model.ConnectionModel;
import de.paragon.explorer.model.ExplorerModel;
import de.paragon.explorer.model.ObjectHeaderModel;
import de.paragon.explorer.model.ObjectModel;

/**
 * Kommentar: Aufgrund der zum Zeitpunkt zu Beginn der Implementierung der
 * ConnectionFigures absehbaren, eingeschraenkten Funktionalitaet der
 * zugehoerigen Builder und Models wurde fuer ConnectionFigure und fuer
 * ConnectionModel zusammen nur ein Builder installiert.
 */
public final class ConnectionBuilder {
	private static final Logger			logger					= LoggerFactory.make();
	private static final String			ERROR_WHILE_REMOVING	= "connectionbuilder.error_while_removing";
	private static ConnectionBuilder	singleton;

	public static ConnectionBuilder getInstance() {
		return ConnectionBuilder.getSingleton();
	}

	private static ConnectionBuilder getSingleton() {
		if (ConnectionBuilder.singleton == null) {
			ConnectionBuilder.setSingleton(new ConnectionBuilder());
		}
		return ConnectionBuilder.singleton;
	}

	private static void setSingleton(ConnectionBuilder builder) {
		ConnectionBuilder.singleton = builder;
	}

	private ConnectionBuilder() {
		super();
	}

	/**
	 * Kommentar: Das ObjectHeaderModel ist das Zielmodell. Das AttributeModel
	 * ist das StartModell. Die beiden Modelle haben also, falls das Attribute
	 * des Objects nicht auf sich selbst refernziert, kein gemeinsames
	 * ObjectModel. Diese Methode erzeugt ConnectionFigure und ConnectionModel,
	 * baut und zeichnet die ConnectionFigure. "add" heisst hier: erzeugen,
	 * bauen und zeichnen.
	 */
	public void addConnection(ObjectHeaderModel headerModel, AttributeModel attrModl) {
		ConnectionModel conModl = this.createNewConnection(headerModel, attrModl);
		StandardConnectionFigure conFig = (StandardConnectionFigure) conModl.getFigure();
		TextBoxFigure headerFig = (TextBoxFigure) headerModel.getFigure();
		TextBoxFigure attrFig = (TextBoxFigure) attrModl.getFigure();
		this.buildConnection(conFig, headerFig, attrFig);
		// this.drawConnectionFigure(conFig);
	}

	/**
	 * Kommentar: Die StandardConnectionFigure ist so konzipiert, dass sie
	 * anhand der beiden verbundenen Figuren alles weiss. Das bedeutet, beim
	 * Zeichnen ermittelt sie selbstaendig anhand der Figuren, wo und wie sie
	 * sich zu zeichnen hat. Daher bedeutet "bauen" lediglich das Zuweisen der
	 * Start- und Zielfigur. Weiter baut diese Methode gleichzeitig die
	 * entsprechenden Models: Das ConnectionModel erhaelt Referenzen auf das
	 * Attribute- und das ObjectHeaderModel, die jeweiligen ObjectModels dieser
	 * Models erhalten Referenzen auf die ConnectionFigure.
	 */
	private void buildConnection(StandardConnectionFigure conFig, TextBoxFigure headerFigure, TextBoxFigure attrFigure) {
		AttributeModel attrModl = (AttributeModel) attrFigure.getModel();
		ObjectHeaderModel headModl = (ObjectHeaderModel) headerFigure.getModel();
		conFig.setStartFigure(attrFigure);
		conFig.setEndFigure(headerFigure);
		(conFig.getModel()).setAttributeModel(attrModl);
		(conFig.getModel()).setHeaderModel(headModl);
		(attrModl.getObjectModel()).addConnectionModel(conFig.getModel());
		(headModl.getObjectModel()).addConnectionModel(conFig.getModel());
	}

	/**
	 * Kommentar: Diese Methode erzeugt ein neues ConnectionModel und eine neue
	 * StandardConnectionFigure. Danach werden alle Verknuepfungen
	 * gemacht:ConnectionModel mit ExplorerModel, die Zuweisung von HeaderModel
	 * und AttributeModel zum ConnectionModel, die Verknuepfung ConnectionModel
	 * mit ConnectionFigure und ConnectionFigure mit ExplorerFigure. Zu Beachten
	 * gilt: addConnectionFigure erledigt hier lediglich nur die Verknuepfung
	 * zwischen den Figuren, im Gegensatz dazu steht ja das add in der Methode
	 * addConnectionFigure fuer erzeugen, bauen und verknuepfen.
	 * 
	 * @param headerModel
	 *            ObjectHeaderModel
	 * @param attrModl
	 *            AttributeModel
	 * 
	 * @return ConnectionModel
	 */
	private ConnectionModel createNewConnection(ObjectHeaderModel headerModel, AttributeModel attrModl) {
		ExplorerFigure explFig = this.getExplorerFigureFor(headerModel);
		ConnectionModel conModl = new ConnectionModel();
		StandardConnectionFigure conFig = new StandardConnectionFigure();
		attrModl.setConnectionModel(conModl);
		conModl.setFigure(conFig);
		conFig.setModel(conModl);
		conModl.setHeaderModel(headerModel);
		conModl.setAttributeModel(attrModl);
		conModl.setExplorerModel((ExplorerModel) explFig.getModel());
		this.getExplorerFigureBuilder().addConnectionFigure(conFig, explFig);
		return conModl;
	}

	private void drawInForeground(StandardConnectionFigure conFig) {
		this.getExplorerFigureBuilder().setInForeground(conFig);
		// this.drawConnectionFigure(conFig);
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass zwei zu verbindende Figuren
	 * mit ihren Models -sprich eine TextBoxFigure und eine ListBoxFigure-
	 * existieren und im Vordergrund gezeichnet sind. Sie soll herausfinden, ob
	 * eine derartige ConnectionFigure mit ihrem Model bereits existiert. Anhand
	 * der ListBoxFigure werden zunaechst alle ConnectionModels ausfindig
	 * gemacht, die zur List-BoxFigure zeigen oder von ihr weg zeigen. Alle
	 * diese werden durchsucht, ob das Attribute-Model des ConnectionModels mit
	 * dem Model der TextBoxFigure und das HeaderModel des Connection-Models mit
	 * dem HeaderModel der ListBoxFigure uebereinstimmt. Dieses wird dann der
	 * Rueckgabevariablen zugewiesen. Wird keines gefunden, wird also null
	 * zurueckgegeben.
	 * 
	 * @param teBoFi
	 *            TextBoxFigure
	 * @param liBoFi
	 *            ListBoxFigure
	 * 
	 * @return StandardConnectionFigure
	 */
	private StandardConnectionFigure getConnectionFigureToShow(TextBoxFigure teBoFi, ListBoxFigure liBoFi) {
		StandardEnumeration enumeration;
		ObjectModel objModl = (ObjectModel) liBoFi.getModel();
		StandardConnectionFigure conFigToShow = null;
		ConnectionModel tempConModl;
		enumeration = objModl.getConnectionModels();
		while (enumeration.hasMoreElements()) {
			tempConModl = (ConnectionModel) enumeration.nextElement();
			if (tempConModl.getAttributeModel() == (AttributeModel) teBoFi.getModel()) {
				if (tempConModl.getHeaderModel() == ((ObjectModel) liBoFi.getModel()).getHeaderModel()) {
					conFigToShow = (StandardConnectionFigure) tempConModl.getFigure();
				}
			}
		}
		return conFigToShow;
	}

	private ExplorerFigureBuilder getExplorerFigureBuilder() {
		return ExplorerFigureBuilder.getInstance();
	}

	private ExplorerFigure getExplorerFigureFor(ObjectHeaderModel headModl) {
		return (ExplorerFigure) headModl.getObjectModel().getExplorerModel().getFigure();
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass das objectModel und die
	 * zugehoerige ListBoxFigure geloescht, das heisst, aus ihren jeweiligen
	 * Containern entfernt sind. Sie entfernt das ConnectionModel vom
	 * AttributeModel, von dem es aus eine Referenz zeigte, und aus den Listen
	 * der beiden Objektmodelle, von dem bzw.zu dem das ConnectionModel zeigte.
	 */
	public void removeConnections(ObjectModel objModl) {
		StandardEnumeration conModls = objModl.getConnectionModels();
		while (conModls.hasMoreElements()) {
			ConnectionModel tempConModl = (ConnectionModel) conModls.nextElement();
			this.removeFromObjectModels(tempConModl);
			this.removeFromAttributeModel(tempConModl);
		}
	}

	/**
	 * Kommentar: Fuer den Fall, dass eine komplette ListBoxFigure geloescht
	 * werden soll, geht diese Methode davon aus, dass das objectModel und die
	 * zugehoerige ListBoxFigure geloescht, das heisst, aus ihren jeweiligen
	 * Containern entfernt sind. Eine Referenz von den beiden ObjectModels, auf
	 * die gezeigt wird und von denen gezeigt wird, exi- stiert ebenfalls nicht
	 * mehr. Fuer den Fall, dass eine Connection geloescht werden soll, geht
	 * diese Methode davon aus, dass eine Referenz von den beiden ObjectModels,
	 * auf die gezeigt wird und von denen gezeigt wird, nicht mehr existiert.
	 * Sie setzt die Referenz des Attributmodells zum Connectionmodell auf null
	 * und entfernt die zugehoerige ConnectionFigure aus dem figureStore der
	 * ExplorerFigure.
	 */
	private void removeFromAttributeModel(ConnectionModel conModl) {
		conModl.getAttributeModel().setConnectionModel(null);
		try {
			if (conModl.getFigure().getParent() != null) {
				conModl.getFigure().getParent().remove(conModl.getFigure());
			}
		}
		catch (Exception ex) {
			ConnectionBuilder.logger.error(ResourceBundlePurchaser.getMessage(ConnectionBuilder.ERROR_WHILE_REMOVING), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ConnectionBuilder.ERROR_WHILE_REMOVING));
		}
	}

	/**
	 * Kommentar: Im Falle, dass diese Methode zum Loeschen einer ListBoxFigure
	 * aufgerufen wird, geht diese Methode davon aus, dass das objectModel und
	 * die zugehoerige ListBoxFigure geloescht, das heisst, aus ihren jeweiligen
	 * Containern entfernt sind. Weiter besteht keine Referenz mehr vom
	 * ExplorerModel und von der ExplorerFigure auf die Connection. Im Falle,
	 * dass diese Methode zum Loeschen einer einzelnen Connection aufgerufen
	 * wird, geht diese Methode davon aus, dass noch alle Beziehungen
	 * existieren. Diese Methode entfernt die Referenzen von den ObjectModels,
	 * von denen die Connection ausgeht oder auf die die Connection zeigt, auf
	 * die Connection.
	 */
	private void removeFromObjectModels(ConnectionModel conModl) {
		StandardConnectionFigure conFig = (StandardConnectionFigure) conModl.getFigure();
		ListBoxFigure liBoFi;
		liBoFi = (ListBoxFigure) conFig.getStartFigure().getParent();
		((ObjectModel) liBoFi.getModel()).removeConnectionModel(conModl);
		liBoFi = (ListBoxFigure) conFig.getEndFigure().getParent();
		((ObjectModel) liBoFi.getModel()).removeConnectionModel(conModl);
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass noch nichts geloescht
	 * worden ist. Sie geht so vor, dass sie zunaechst die Referenzen aus den
	 * ObjectModels loescht, und dann aus dem AttributeModel, von dem die
	 * Connection aus die Referenz zeigte.
	 */
	public void removeSingleConnection(AttributeModel attrModl) {
		if (attrModl.getConnectionModel() != null) {
			this.removeFromObjectModels(attrModl.getConnectionModel());
			this.removeFromAttributeModel(attrModl.getConnectionModel());
		}
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass die TextBox- Figure mit
	 * einem AttributModel verknuepft ist. Weiter geht sie davon aus, dass eine
	 * zu referenzierende ListBoxFigure existiert, und im Vordergrund gezeichnet
	 * worden ist. Die ConnectionFigure muss noch gezeigt werden. Diese Methode
	 * ueberprueft zunaechst, ob eine entsprechende ConnectionFigure ueberhaupt
	 * existiert. Existiert sie, braucht sie lediglich in den Vordergrund
	 * gezeichnet werden. Existiert sie nicht, muss sie neu hinzugefuegt ("add")
	 * werden.
	 */
	public void showConnection(TextBoxFigure teBoFi, ListBoxFigure liBoFi) {
		StandardConnectionFigure conFig = this.getConnectionFigureToShow(teBoFi, liBoFi);
		if (conFig == null) {
			ObjectHeaderModel objHead = ((ObjectModel) liBoFi.getModel()).getHeaderModel();
			this.addConnection(objHead, (AttributeModel) teBoFi.getModel());
		}
		else {
			this.drawInForeground(conFig);
		}
	}
}
