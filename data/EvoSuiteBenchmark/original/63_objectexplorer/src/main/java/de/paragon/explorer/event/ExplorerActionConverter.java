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
package de.paragon.explorer.event;

import de.paragon.explorer.figure.ExplorerFigure;
import de.paragon.explorer.figure.ExplorerFigureBuilder;
import de.paragon.explorer.figure.ListBoxFigure;
import de.paragon.explorer.figure.ListBoxFigureBuilder;
import de.paragon.explorer.figure.TextBoxFigure;
import de.paragon.explorer.model.AttributeModel;
import de.paragon.explorer.model.ExplorerModelBuilder;
import de.paragon.explorer.model.ObjectHeaderModel;
import de.paragon.explorer.model.ObjectModel;
import de.paragon.explorer.util.ConnectionBuilder;
import de.paragon.explorer.util.ObjectViewManager;
import de.paragon.explorer.util.PropertyManager;
import de.paragon.explorer.util.StandardEnumeration;

/**
 * Kommentar: Diese Klasse empfaengt Explorer-Spezifische Aktionen,die sie vom
 * EventConverter erhalten hat. Aufgabe ist es, diese Aktionen an die Builder
 * weiterzuleiten.
 */
public class ExplorerActionConverter {
	private static ExplorerActionConverter	singleton;

	public static ExplorerActionConverter getInstance() {
		return ExplorerActionConverter.getSingleton();
	}

	private static ExplorerActionConverter getSingleton() {
		if (ExplorerActionConverter.singleton == null) {
			ExplorerActionConverter.setSingleton(new ExplorerActionConverter());
		}
		return ExplorerActionConverter.singleton;
	}

	private static void setSingleton(ExplorerActionConverter builder) {
		ExplorerActionConverter.singleton = builder;
	}

	public ExplorerActionConverter() {
		super();
	}

	/**
	 * draw bedeutet, dass nur die im Argument angebene Figur gezeichnet wird.
	 * draw wird immer dann aufgerufen, wenn sich die Stelle der zu zeich-
	 * nenden Figure nicht geaendert hat.
	 */
	public void draw(ListBoxFigure liBoFi) {
		this.getExplorerFigureBuilder().draw(liBoFi);
	}

	private ConnectionBuilder getConnectionBuilder() {
		return ConnectionBuilder.getInstance();
	}

	private ExplorerFigureBuilder getExplorerFigureBuilder() {
		return ExplorerFigureBuilder.getInstance();
	}

	private ExplorerModelBuilder getExplorerModelBuilder() {
		return ExplorerModelBuilder.getInstance();
	}

	private ListBoxFigureBuilder getListBoxFigureBuilder() {
		return ListBoxFigureBuilder.getInstance();
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass die ueberge- bene
	 * TextBoxFigure mit einem AttributeModel verknuepft ist. Anhand der
	 * TextBoxFigure werden zunaechst alle existierenden ListBoxFigures
	 * ausfindig gemacht. Alle diese werden durchsucht nach dem Object. Dieses
	 * wird dann der Rueckgabevariablen zugewiesen. Wird keines gefunden, wird
	 * also null zurueck- gegeben.
	 */
	private ListBoxFigure getListBoxFigureToShow(TextBoxFigure teBoFi) {
		StandardEnumeration enumeration;
		AttributeModel attrModl = (AttributeModel) teBoFi.getModel();
		ListBoxFigure liBoFiToShow = null;
		ObjectModel tempObjModl;
		enumeration = attrModl.getObjectModel().getExplorerModel().getObjectModels();
		while (enumeration.hasMoreElements()) {
			tempObjModl = (ObjectModel) enumeration.nextElement();
			if (tempObjModl.getObject() == attrModl.getValue()) {
				liBoFiToShow = (ListBoxFigure) tempObjModl.getFigure();
			}
		}
		return liBoFiToShow;
	}

	/**
	 * Kommentar: Eine Figure wird bewegt, wenn -wie bei normalen Fenstern- die
	 * linke Maustaste gedrueckt gehalten wird und gleichzeitig verschoben wird.
	 * Ist dies geschehen, so gibt der DrawingFrameEventConverter an den
	 * ExplorerActionConverter dies weiter. Das weitere Bewegen erfolgt durch
	 * den Explorer- FigureBuilder.
	 */
	public void moveFigureBy(ListBoxFigure liBoFi, int x, int y) {
		this.getExplorerFigureBuilder().moveFigureBy(liBoFi, x, y);
	}

	/**
	 * Diese Methode loescht eine einzelne Connection. Zunaechst werden die
	 * Referenzen auf die Connection von dem AttributeModell und von der Ex-
	 * plorerFigure entfernt. Dann werden die Referenzen von den beiden Object-
	 * modellen, auf die bzw. von denen eine Referenz dargestellt wird,
	 * geloescht.
	 */
	private void removeConnection(TextBoxFigure teBoFi) {
		this.getConnectionBuilder().removeSingleConnection((AttributeModel) teBoFi.getModel());
		ListBoxFigure liBoFi = (ListBoxFigure) teBoFi.getParent();
		this.getExplorerFigureBuilder().update((ExplorerFigure) liBoFi.getParent());
	}

	public void setInForeground(ListBoxFigure liBoFi) {
		this.getExplorerFigureBuilder().setInForeground(liBoFi);
	}

	/**
	 * Kommentar: Diese Methode geht so vor: Die Methode setAllAttributesVisible
	 * macht folgendes: Zunaechst werden alle AttributeModels auf visible
	 * gesetzt. Dann wird die Weite von allen Teilen der ListBoxFigure auf 0
	 * (null) gesetzt, und die Orte werden gleich dem Ort der ListBoxFigure
	 * gesetzt. Mit allen TeilFiguren, die TextBoxFigure sind, geschieht dann
	 * folgendes: Die DisplayBox wird erneut berechnet. Die Be- rechnung der
	 * DisplayBox hat zur Folge, dass der Ort der Figure wieder auf 0,0 gesetzt
	 * wird. Also wird die TextBoxFigure wieder um die Position der
	 * ListBoxFigure verschoben.
	 * 
	 * Nach der Methode setAllAttributesUnvisible sind alle Voraussetzungen
	 * geschaffen, um initializeListBoxFigure anzuwenden. Diese Methode setzt
	 * alle Weiten auf ein einheit- liches Mass, naemlich das Maximum aller
	 * sicht- baren Figuren. Anschliessend werden alle Figuren je nach ihrer
	 * Erscheinung (Stelle in der Liste und (Sichtbarkeit) richtig po-
	 * sitioniert.
	 * 
	 * Schliesslich wird noch upgedated (oh Gott!), d.h. geloescht und neu
	 * gezeichnet.
	 */
	// private void setSingleAttributeVisibleOf(TextBoxFigure teBoFi) {
	// ListBoxFigure liBoFi = (ListBoxFigure) teBoFi.getParent();
	// this.getListBoxFigureBuilder().setSingleAttributeVisible(teBoFi);
	// this.getListBoxFigureBuilder().initializeListBoxFigure(liBoFi);
	// this.getExplorerFigureBuilder().update((ExplorerFigure)
	// liBoFi.getParent());
	// }
	// private void showAllReferences(ListBoxFigure liBoFi) {
	// AttributeModel attrModl = null;
	// this.draw(liBoFi);
	// StandardEnumeration attrModls = ((ObjectModel)
	// liBoFi.getModel()).getAttributeModels();
	// while (attrModls.hasMoreElements()) {
	// attrModl = ((de.paragon.explorer.model.AttributeModel)
	// attrModls.nextElement());
	// if (!(attrModl.isAttributePrimitive())) {
	// this.showReferenceFor((TextBoxFigure) attrModl.getFigure());
	// this.setSingleAttributeVisibleOf((TextBoxFigure) attrModl.getFigure());
	// }
	// }
	// }
	// private void showAllReferencesRec(ListBoxFigure liBoFi) {
	// AttributeModel attrModl = null;
	// this.draw(liBoFi);
	// de.paragon.explorer.util.StandardEnumeration attrModls = ((ObjectModel)
	// liBoFi.getModel()).getAttributeModels();
	// while (attrModls.hasMoreElements()) {
	// attrModl = ((de.paragon.explorer.model.AttributeModel)
	// attrModls.nextElement());
	// if (attrModl.getConnectionModel() == null) {
	// this.showReferenceFor((TextBoxFigure) attrModl.getFigure());
	// }
	// }
	// attrModls = ((ObjectModel) liBoFi.getModel()).getAttributeModels();
	// while (attrModls.hasMoreElements()) {
	// attrModl = ((de.paragon.explorer.model.AttributeModel)
	// attrModls.nextElement());
	// if (!attrModl.isAttributePrimitive()) {
	// this.showAllReferencesRec((ListBoxFigure)
	// attrModl.getConnectionModel().getHeaderModel().getObjectModel().getFigure());
	// }
	// }
	// }
	/**
	 * Kommentar: Zunaechst wird die zur TextBoxFigure gehoerige ListBoxFigure
	 * neu gezeichnet, sodass sie (zunaechst) im Vordergrund erscheint. Darueber
	 * kommen nur noch die beiden unten beschriebenen Figuren. Anschliessend
	 * wird die zu zeigende ListBoxFigure gesucht, nach vorn gesetzt und
	 * gezeichnet. Analog dazu wird der verbindende Pfeil gesucht, nach vorn
	 * gesetzt und gezeichnet.
	 */
	public void showInForeground(TextBoxFigure teBoFi) {
		this.draw((ListBoxFigure) teBoFi.getParent());
		this.showReferenceFor(teBoFi);
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass die uebergebene
	 * TextBoxFigure eine mit AttributeModel ist. Beim Zeigen einer Referenz ist
	 * die Aktion abhaengig von den bereits existierenden Figuren: 1. Die
	 * ListBoxFigure fuer das entsprechende Objekt existiert schon; 2. Die
	 * entsprechende ListBoxFigure existiert noch nicht. Im ersten Fall wird die
	 * Figur nach vorn gesetzt. Danach muessen zwei weitere Moeglichkeiten
	 * unterschieden werden: 1.a. Der Referenzpfeil existiert auch schon; 1.b.
	 * Der Pfeil existiert nicht. Im Fall a. wird der Pfeil nach vorn gesetzt
	 * und gezeichnet. Im Fall b. wird der Pfeil hinzugefuegt und anschliessend
	 * nur die Figur und der Pfeil gezeichnet. Im zweiten Fall, falls die Figur
	 * nicht da ist, wird die Figur, anschliessend der Pfeil gesetzt. Dann
	 * werden nur die Figur und der Pfeil gezeichnet. "show" soll bedeuten, dass
	 * immer zuerst geguckt wird, ob die entsprechende Figure bereits existiert.
	 * Davon ist abhaengig, ob sie gezeichnet wird.
	 */
	private void showReferenceFor(TextBoxFigure teBoFi) {
		ListBoxFigure liBoFiToShow = this.getListBoxFigureToShow(teBoFi);
		// Liefert mir die darzustellende ListBoxFigure zurueck
		if (liBoFiToShow == null) {
			// this.getExplorerModelBuilder().handleAttributeModel((de.paragon.explorer.model.AttributeModel)teBoFi.getModel());
			this.getExplorerModelBuilder().handleAttributeModel(teBoFi);
		}
		else {
			this.getExplorerFigureBuilder().drawInForeground(liBoFiToShow);
			this.getConnectionBuilder().showConnection(teBoFi, liBoFiToShow);
		}
	}

	public void updateAllAttributesOf(ListBoxFigure liBoFi) {
		// de.paragon.explorer.model.AttributeModel attrModl = null;
		StandardEnumeration attrModls = ((ObjectModel) liBoFi.getModel()).getAttributeModels();
		while (attrModls.hasMoreElements()) {
			this.updateAttribute((AttributeModel) attrModls.nextElement());
		}
		this.updateObject(liBoFi);
	}

	public void updateAttribute(AttributeModel attrModl) {
		if (!attrModl.isAttributePrimitive()) {
			if (attrModl.getConnectionModel() != null) {
				this.removeConnection((TextBoxFigure) attrModl.getFigure());
				this.showReferenceFor((TextBoxFigure) attrModl.getFigure());
			}
		}
	}

	public void updateObject(ListBoxFigure liBoFi) {
		this.getListBoxFigureBuilder().updateObject(liBoFi);
		this.getListBoxFigureBuilder().initializeListBoxFigure(liBoFi);
		this.getExplorerFigureBuilder().update((ExplorerFigure) liBoFi.getParent());
	}

	public void saveAttribute(ObjectHeaderModel newModel) {
		//
		if (newModel != null) {
			ObjectModel objectModel = newModel.getObjectModel();
			if (objectModel != null) {
				ObjectViewManager objectViewManager = objectModel.getObjectViewManager();
				if (objectViewManager != null) {
					boolean newValueHideNull = objectViewManager.isNullAttributesVisible();
					boolean newValueHideStatic = objectViewManager.isStaticAttributesVisible();
					boolean newValueHideUnexplored = objectViewManager.isUnexploredAttributesVisible();
					PropertyManager.getInstance().setHideAttributes(newValueHideNull, newValueHideStatic, newValueHideUnexplored);
				}
			}
		}
	}
}
