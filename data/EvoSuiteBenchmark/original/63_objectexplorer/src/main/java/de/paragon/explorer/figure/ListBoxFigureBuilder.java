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

import java.lang.reflect.Modifier;
import java.util.Enumeration;

import org.apache.log4j.Logger;

import de.paragon.explorer.gui.Warning;
import de.paragon.explorer.model.AttributeModel;
import de.paragon.explorer.model.NullObject;
import de.paragon.explorer.model.ObjectHeaderModel;
import de.paragon.explorer.model.ObjectModel;
import de.paragon.explorer.model.ObjectModelPart;
import de.paragon.explorer.util.LoggerFactory;
import de.paragon.explorer.util.ResourceBundlePurchaser;
import de.paragon.explorer.util.StandardEnumeration;

public final class ListBoxFigureBuilder {
	private static final String			ERROR_WHILE_UPDATING_FIGURES					= "listboxfigurebuilder.error_while_updating_figures";
	private static final String			ERROR_WHILE_GETTING_FIGURES						= "listboxfigurebuilder.error_while_getting_figures";
	private static final String			ERROR_WHILE_SETTING_ALL_ATTRIBUTES_VISIBLE		= "listboxfigurebuilder.error_while_setting_all_attributes_visible";
	private static final String			ERROR_WHILE_SETTING_ALL_ATTRIBUTES_UNVISIBLE	= "listboxfigurebuilder.error_while_setting_all_attributes_unvisible";
	private static final String			ERROR_WHILE_INIT_MODELL							= "listboxfigurebuilder.error_while_init_modell";
	private static final String			ERROR_WHILE_BUILDING_MODELL						= "listboxfigurebuilder.error_while_building_modell";
	private static final String			ERROR_WHILE_BUILDING_LISTBOXFIGURE				= "listboxfigurebuilder.error_while_building_listboxfigure";
	private static final Logger			logger											= LoggerFactory.make();
	private static ListBoxFigureBuilder	singleton;

	public static ListBoxFigureBuilder getInstance() {
		return ListBoxFigureBuilder.getSingleton();
	}

	private static ListBoxFigureBuilder getSingleton() {
		if (ListBoxFigureBuilder.singleton == null) {
			ListBoxFigureBuilder.setSingleton(new ListBoxFigureBuilder());
		}
		return ListBoxFigureBuilder.singleton;
	}

	private static void setSingleton(ListBoxFigureBuilder builder) {
		ListBoxFigureBuilder.singleton = builder;
	}

	private ListBoxFigureBuilder() {
		super();
	}

	protected void addTextBoxFigure(TextBoxFigure teBoFi) {
		try {
			((ObjectModelPart) teBoFi.getModel()).getObjectModel().getFigure().add(teBoFi);
		}
		catch (Exception ex) {
			ListBoxFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_BUILDING_LISTBOXFIGURE), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_BUILDING_LISTBOXFIGURE));
		}
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass die TextBoxFigure, die mit
	 * dem HeaderModel verknuepft ist, fertig ist. Anhand dessen bestimmt sie
	 * die Groesse der FilledRectangleFigure, die als Unterstrich fuer die
	 * Header-TextBoxFigure dient, und fuegt sie der ListBoxFigure hinzu.
	 */
	protected void buildHeaderUnderline(ObjectModel objModl) {
		TextBoxFigure teBoFig = (TextBoxFigure) objModl.getHeaderModel().getFigure();
		int width = this.getFigureWidth(teBoFig);
		// int height = this.getFigureHeight(teBoFig) / 5;
		int height = 1;
		int y = 0, x = 0;
		try {
			objModl.getFigure().add(new FilledRectangleFigure(x, y, width, height));
		}
		catch (Exception ex) {
			ListBoxFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_BUILDING_MODELL), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_BUILDING_MODELL));
		}
	}

	/**
	 * Kommentar: Zunaechst wird fuer jedes TeilModel eine TextBoxFigure erzeugt
	 * und mit ihrem jeweiligen Model (Header- bzw. AttributeModel) verknuepft.
	 * Diese Methode geht davon aus, dass fuer das ObjectModel bereits
	 * HeaderModels und AttributeModels in dem Object angepasster Anzahl
	 * existieren. Dann wird jeder TextBoxFigure ein entsprechender String
	 * zugewiesen. Anschliessend wird zunaechst die soeben erzeugte
	 * TextBoxFigure fuer das HeaderModel gebaut, was die Bestimmung der Groesse
	 * bedeutet, und mit der ListBoxFigure, die auch die ExplorerFigure kennt,
	 * die die Graphics kennt, verknuepft. Danach wird eine neue
	 * FilledRectangleFigure erzeugt und mit der ListBoxFigure verknuepft.
	 * Danach wird jede der mit einem AttributModel verknuepften TextBoxFiguren
	 * gebaut und mit der ListBoxFigure verknuepft. Danach wird die
	 * ListBoxFigure fertiggebaut.
	 */
	public void buildListBoxFigure(ObjectModel objModl) {
		ListBoxFigure liBoFi = (ListBoxFigure) objModl.getFigure();
		this.getTextBoxFigureBuilder().createTextBoxFigures(objModl);
		this.initializeListBoxFigure(liBoFi);
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass alle Verknuepfungen bereits
	 * existieren und die Teilfiguren -jede fuer sich- gebaut sind. Sie
	 * ermittelt die Weite der ListBoxFigure. Dabei geht sie so vor: Falls die
	 * Figur "visible" ist, geht sie in die Berechnung mit ein. Am Ende ist die
	 * Weite der ListBoxFigure das Maximum aller Weiten der sichtbaren
	 * TextBoxFiguren.
	 * 
	 * @return int
	 */
	private int computeListBoxFigureWidth(ListBoxFigure list) {
		/* damn the class Rectangle for having no get method for the width! */
		ObjectHeaderModel headModl = ((ObjectModel) list.getModel()).getHeaderModel();
		StandardEnumeration attrModls = ((ObjectModel) list.getModel()).getAttributeModels();
		AttributeModel tempModel;
		int x = this.getFigureWidth(headModl.getFigure());
		while (attrModls.hasMoreElements()) {
			tempModel = (AttributeModel) attrModls.nextElement();
			if (((TextBoxFigure) tempModel.getFigure()).isVisible()) {
				x = Math.max(this.getFigureWidth(tempModel.getFigure()), x);
			}
		}
		return x;
	}

	public void createNewListBoxFigure(ObjectModel objModl) {
		ExplorerFieldListBoxFigure exFiLiBoFi = new ExplorerFieldListBoxFigure();
		objModl.setFigure(exFiLiBoFi);
		exFiLiBoFi.setModel(objModl);
		this.getExplorerFigureBuilder().addListBoxFigure(exFiLiBoFi);
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass das ObjectModel bereits mit
	 * einem ExplorerModel verknuepft ist. Das ExplorerModel seinerseits ist
	 * bereits mit einer ExplorerFigure verknuepft. Diese Methode erzeugt eine
	 * neue ListBoxFigure und verknuepft sie mit dem ObjectModel. Anschliessend
	 * laesst sie die neue ListBoxFigure mit der ExplorerFigure verknuepfen, um
	 * den Kreis zu schliessen.
	 */
	public void createNewListBoxFigure(ObjectModel objModl, TextBoxFigure tbf) {
		ExplorerFieldListBoxFigure exFiLiBoFi = new ExplorerFieldListBoxFigure();
		if (tbf.getParent() instanceof ExplorerFieldListBoxFigure) {
			((ExplorerFieldListBoxFigure) tbf.getParent()).addChild(exFiLiBoFi);
		}
		objModl.setFigure(exFiLiBoFi);
		exFiLiBoFi.setModel(objModl);
		this.getExplorerFigureBuilder().addListBoxFigure(exFiLiBoFi);
	}

	private ExplorerFigureBuilder getExplorerFigureBuilder() {
		return ExplorerFigureBuilder.getInstance();
	}

	private int getFigureHeight(Figure figure) {
		int tempHeight = 0;
		try {
			tempHeight = figure.getDisplayBox().getRectangle().height;
		}
		catch (Exception ex) {
			ListBoxFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_BUILDING_MODELL), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_BUILDING_MODELL));
		}
		return tempHeight;
	}

	private int getFigureWidth(Figure figure) {
		int tempWidth = 0;
		try {
			tempWidth = figure.getDisplayBox().getRectangle().width;
		}
		catch (Exception ex) {
			ListBoxFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_BUILDING_MODELL), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_BUILDING_MODELL));
		}
		return tempWidth;
	}

	private TextBoxFigureBuilder getTextBoxFigureBuilder() {
		return TextBoxFigureBuilder.getInstance();
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass saemtliche Verknuepfungen
	 * zwischen Models und Figures existieren, und dass die Text- BoxFigures
	 * jede fuer sich selbst gebaut sind. Diese Methode bestimmt die
	 * DisplayBoxen der Teilfiguren neu. Das heisst, sie ermittelt und setzt die
	 * Position der Teilfigur innerhalb der ListBoxFigure, und sie ermittelt die
	 * Weite der ListBoxFigure und setzt diese in allen Teilfiguren und der
	 * ListBoxFigure selbst. Bei der Emitt- lung der Weite wird die Angabe
	 * "visible" beruecksichtigt. Anschliessend positioniert sie die
	 * ListBoxFigure in der ExplorerFigure, wieder abhaengig von "visible".
	 */
	public void initializeListBoxFigure(ListBoxFigure liBoFi) {
		this.setListBoxFigureWidths(liBoFi, this.computeListBoxFigureWidth(liBoFi));
		StandardEnumeration figParts;
		try {
			figParts = liBoFi.getFigures();
		}
		catch (Exception ex) {
			ListBoxFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_INIT_MODELL), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_INIT_MODELL));
			figParts = null;
		}
		this.setFigureHeight(liBoFi, 0);
		if (figParts != null) {
			while (figParts.hasMoreElements()) {
				RectangleFigure tempFig = (RectangleFigure) figParts.nextElement();
				if (tempFig.isVisible()) {
					try {
						tempFig.moveBy(0, this.getFigureHeight(liBoFi));
					}
					catch (Exception ex) {
						ListBoxFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_INIT_MODELL), ex);
						Warning.showWarning(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_INIT_MODELL));
					}
					this.setFigureHeight(liBoFi, this.getFigureHeight(liBoFi) + this.getFigureHeight(tempFig));
				}
			}
		}
	}

	/**
	 * Kommentar: Diese Methode geht so vor: Zunaechst werden alle
	 * AttributeModels, die in der ListBoxFigure enthalten sind, auf unvisible
	 * gesetzt. Dann wird die Weite von allen Teilen der ListBoxFigure auf 0
	 * (null) gesetzt, und die Orte werden gleich dem Ort der ListBoxFigure
	 * gesetzt. Mit allen TeilFiguren, die TextBoxFigure sind, geschieht dann
	 * folgendes: Die DisplayBox wird erneut berechnet. Die Be- rechnung der
	 * DisplayBox hat zur Folge, dass der Ort der Figure wieder auf 0,0 gesetzt
	 * wird. Also wird die TextBoxFigure wieder um die Position der
	 * ListBoxFigure verschoben.
	 */
	public void setAllAttributesUnvisible(ListBoxFigure liBoFi) {
		ObjectModel objModl;
		StandardEnumeration parts;
		TextBoxFigure teBoFi;
		AttributeModel attrModl;
		RectangleFigure liBoFiPart;
		objModl = (ObjectModel) liBoFi.getModel();
		objModl.getObjectViewManager().setAllAttributesVisible(false);
		// objModl.getObjectViewManager().setNullAttributesVisible(false);
		// objModl.getObjectViewManager().setStaticAttributesVisible(false);
		// objModl.getObjectViewManager().setUnexploredAttributesVisible(false);
		parts = objModl.getAttributeModels();
		try {
			// Hier werden alle Attribute unsichtbar gesetzt.
			while (parts.hasMoreElements()) {
				attrModl = (AttributeModel) parts.nextElement();
				((TextBoxFigure) attrModl.getFigure()).setUnvisible();
			}
			// Hier werden die Weiten aller Teilfiguren auf 0 gesetzt und die
			// Positionen gleich der Position der ListBoxFigure.
			// Hierzu bekommt parts eine neue Zuweisung.
			parts = liBoFi.getFigures();
			while (parts.hasMoreElements()) {
				liBoFiPart = (RectangleFigure) parts.nextElement();
				liBoFiPart.getBounds().width = 0;
				liBoFiPart.getBounds().x = liBoFi.getBounds().x;
				liBoFiPart.getBounds().y = liBoFi.getBounds().y;
			}
			// Hier werden die DisplayBoxen aller TextBoxFigures neu berechnet.
			// Beachte, dass die Berechnung bewirkt, dass die Position auf 0,0
			// gesetzt wird. Daher werden die Figuren anschliessend um die Posi-
			// tion der ListBoxFigure verschoben.
			// Da in der folgenden NeuBerechnung die FilledRectangularBox nicht
			// mitberuecksichtigt wird, war es oben noetig, die Position aller
			// TeilFiguren auf die der ListBoxFigure zu setzten.
			// Es wird mit der TextBoxFigure des HeaderModels begonnen.
			teBoFi = (TextBoxFigure) ((ObjectModel) liBoFi.getModel()).getHeaderModel().getFigure();
			teBoFi.setBounds(this.getTextBoxFigureBuilder().computeDisplayBox(teBoFi));
			teBoFi.moveBy(liBoFi.getBounds().x, liBoFi.getBounds().y);
			// Jetzt kommen die TextBoxFigures der AttributeModels dran.
			parts = ((ObjectModel) liBoFi.getModel()).getAttributeModels();
			while (parts.hasMoreElements()) {
				teBoFi = (TextBoxFigure) ((AttributeModel) parts.nextElement()).getFigure();
				teBoFi.setBounds(this.getTextBoxFigureBuilder().computeDisplayBox(teBoFi));
				teBoFi.moveBy(liBoFi.getBounds().x, liBoFi.getBounds().y);
			}
		}
		catch (Exception ex) {
			ListBoxFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_SETTING_ALL_ATTRIBUTES_UNVISIBLE), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_SETTING_ALL_ATTRIBUTES_UNVISIBLE));
		}
	}

	/**
	 * Kommentar: Diese Methode geht so vor: Zunaechst werden alle
	 * AttributeModels, die in der ListBoxFigure enthalten sind, auf visible
	 * gesetzt. Dann wird die Weite von allen Teilen der ListBoxFigure auf 0
	 * (null) gesetzt, und die Orte werden gleich dem Ort der ListBoxFigure
	 * gesetzt. Mit allen TeilFiguren, die TextBoxFigure sind, geschieht dann
	 * folgendes: Die DisplayBox wird erneut berechnet. Die Berechnung der
	 * DisplayBox hat zur Folge, dass der Ort der Figure wieder auf 0,0 gesetzt
	 * wird. Also wird die TextBoxFigure wieder um die Position der
	 * ListBoxFigure verschoben.
	 */
	public void setAllAttributesVisible(ListBoxFigure liBoFi) {
		ObjectModel objModl;
		StandardEnumeration parts;
		TextBoxFigure teBoFi;
		AttributeModel attrModl;
		RectangleFigure liBoFiPart;
		objModl = (ObjectModel) liBoFi.getModel();
		objModl.getObjectViewManager().setAllAttributesVisible(true);
		// objModl.getObjectViewManager().setNullAttributesVisible(true);
		// objModl.getObjectViewManager().setStaticAttributesVisible(true);
		// objModl.getObjectViewManager().setUnexploredAttributesVisible(true);
		parts = objModl.getAttributeModels();
		try {
			// Hier werden alle Attribute sichtbar gesetzt.
			while (parts.hasMoreElements()) {
				attrModl = (AttributeModel) parts.nextElement();
				if (objModl.getObjectViewManager().shouldBeVisible(attrModl)) {
					((TextBoxFigure) attrModl.getFigure()).setVisible();
				}
			}
			// Hier werden die Weiten aller Teilfiguren auf 0 gesetzt und die
			// Positionen gleich der Position der ListBoxFigure.
			// Hierzu bekommt parts eine neue Zuweisung.
			parts = liBoFi.getFigures();
			while (parts.hasMoreElements()) {
				liBoFiPart = (RectangleFigure) parts.nextElement();
				liBoFiPart.getBounds().width = 0;
				liBoFiPart.getBounds().x = liBoFi.getBounds().x;
				liBoFiPart.getBounds().y = liBoFi.getBounds().y;
			}
			// Hier werden die DisplayBoxen aller TextBoxFigures neu berechnet.
			// Beachte, dass die Berechnung bewirkt, dass die Position auf 0,0
			// gesetzt wird. Daher werden die Figuren anschliessend um die Posi-
			// tion der ListBoxFigure verschoben.
			// Da in der folgenden NeuBerechnung die FilledRectangularBox nicht
			// mitberuecksichtigt wird, war es oben noetig, die Position aller
			// TeilFiguren auf die der ListBoxFigure zu setzten.
			// Es wird mit der TextBoxFigure des HeaderModels begonnen.
			teBoFi = (TextBoxFigure) ((ObjectModel) liBoFi.getModel()).getHeaderModel().getFigure();
			teBoFi.setBounds(this.getTextBoxFigureBuilder().computeDisplayBox(teBoFi));
			teBoFi.moveBy(liBoFi.getBounds().x, liBoFi.getBounds().y);
			// Jetzt kommen die TextBoxFigures der AttributeModels dran.
			parts = ((ObjectModel) liBoFi.getModel()).getAttributeModels();
			while (parts.hasMoreElements()) {
				teBoFi = (TextBoxFigure) ((AttributeModel) parts.nextElement()).getFigure();
				teBoFi.setBounds(this.getTextBoxFigureBuilder().computeDisplayBox(teBoFi));
				teBoFi.moveBy(liBoFi.getBounds().x, liBoFi.getBounds().y);
			}
		}
		catch (Exception ex) {
			ListBoxFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_SETTING_ALL_ATTRIBUTES_VISIBLE), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_SETTING_ALL_ATTRIBUTES_VISIBLE));
		}
	}

	/**
	 * Kommentar: Diese Methode geht so vor: Zunaechst werden alle
	 * AttributeModels, die in der ListBoxFigure enthalten sind und die kein
	 * ConnectionModel haben, auf unvisible gesetzt. Dann wird die Weite von
	 * allen Teilen der ListBoxFigure auf 0 (null) gesetzt, und die Orte werden
	 * gleich dem Ort der ListBoxFigure gesetzt. Mit allen TeilFiguren, die
	 * TextBoxFigure sind, geschieht dann folgendes: Die DisplayBox wird erneut
	 * berechnet. Die Be- rechnung der DisplayBox hat zur Folge, dass der Ort
	 * der Figure wieder auf 0,0 gesetzt wird. Also wird die TextBoxFigure
	 * wieder um die Position der ListBoxFigure verschoben.
	 */
	public void setAllUnexploredAttributesUnvisible(ListBoxFigure liBoFi) {
		ObjectModel objModl;
		StandardEnumeration parts;
		TextBoxFigure teBoFi;
		AttributeModel attrModl;
		RectangleFigure liBoFiPart;
		objModl = (ObjectModel) liBoFi.getModel();
		objModl.getObjectViewManager().setUnexploredAttributesVisible(false);
		parts = objModl.getAttributeModels();
		try {
			// Hier werden alle Attribute, die nicht explored sind, unsichtbar
			// gesetzt.
			while (parts.hasMoreElements()) {
				attrModl = (AttributeModel) parts.nextElement();
				if (attrModl.getConnectionModel() == null) {
					((TextBoxFigure) attrModl.getFigure()).setUnvisible();
				}
			}
			// Hier werden die Weiten aller Teilfiguren auf 0 gesetzt und die
			// Positionen gleich der Position der ListBoxFigure.
			// Hierzu bekommt parts eine neue Zuweisung.
			parts = liBoFi.getFigures();
			while (parts.hasMoreElements()) {
				liBoFiPart = (RectangleFigure) parts.nextElement();
				liBoFiPart.getBounds().width = 0;
				liBoFiPart.getBounds().x = liBoFi.getBounds().x;
				liBoFiPart.getBounds().y = liBoFi.getBounds().y;
			}
			// Hier werden die DisplayBoxen aller TextBoxFigures neu berechnet.
			// Beachte, dass die Berechnung bewirkt, dass die Position auf 0,0
			// gesetzt wird. Daher werden die Figuren anschliessend um die Posi-
			// tion der ListBoxFigure verschoben.
			// Da in der folgenden NeuBerechnung die FilledRectangularBox nicht
			// mitberuecksichtigt wird, war es oben noetig, die Position aller
			// TeilFiguren auf die der ListBoxFigure zu setzten.
			// Es wird mit der TextBoxFigure des HeaderModels begonnen.
			teBoFi = (TextBoxFigure) ((ObjectModel) liBoFi.getModel()).getHeaderModel().getFigure();
			teBoFi.setBounds(this.getTextBoxFigureBuilder().computeDisplayBox(teBoFi));
			teBoFi.moveBy(liBoFi.getBounds().x, liBoFi.getBounds().y);
			// Jetzt kommen die TextBoxFigures der AttributeModels dran.
			parts = ((ObjectModel) liBoFi.getModel()).getAttributeModels();
			while (parts.hasMoreElements()) {
				teBoFi = (TextBoxFigure) ((AttributeModel) parts.nextElement()).getFigure();
				teBoFi.setBounds(this.getTextBoxFigureBuilder().computeDisplayBox(teBoFi));
				teBoFi.moveBy(liBoFi.getBounds().x, liBoFi.getBounds().y);
			}
		}
		catch (Exception ex) {
			ListBoxFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_SETTING_ALL_ATTRIBUTES_UNVISIBLE), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_SETTING_ALL_ATTRIBUTES_UNVISIBLE));
		}
	}

	/**
	 * Kommentar: Diese Methode geht so vor: Zunaechst werden alle
	 * AttributeModels, die in der ListBoxFigure enthalten sind und die kein
	 * ConnectionModel haben, auf unvisible gesetzt. Dann wird die Weite von
	 * allen Teilen der ListBoxFigure auf 0 (null) gesetzt, und die Orte werden
	 * gleich dem Ort der ListBoxFigure gesetzt. Mit allen TeilFiguren, die
	 * TextBoxFigure sind, geschieht dann folgendes: Die DisplayBox wird erneut
	 * berechnet. Die Be- rechnung der DisplayBox hat zur Folge, dass der Ort
	 * der Figure wieder auf 0,0 gesetzt wird. Also wird die TextBoxFigure
	 * wieder um die Position der ListBoxFigure verschoben.
	 */
	public void setAllUnexploredAttributesVisible(ListBoxFigure liBoFi) {
		ObjectModel objModl;
		de.paragon.explorer.util.StandardEnumeration parts;
		TextBoxFigure teBoFi;
		AttributeModel attrModl;
		RectangleFigure liBoFiPart;
		objModl = (ObjectModel) liBoFi.getModel();
		objModl.getObjectViewManager().setUnexploredAttributesVisible(true);
		parts = objModl.getAttributeModels();
		try {
			// Hier werden alle Attribute, die nicht explored sind, unsichtbar
			// gesetzt.
			while (parts.hasMoreElements()) {
				attrModl = (AttributeModel) parts.nextElement();
				if (objModl.getObjectViewManager().shouldBeVisible(attrModl)) {
					((TextBoxFigure) attrModl.getFigure()).setVisible();
				}
			}
			// Hier werden die Weiten aller Teilfiguren auf 0 gesetzt und die
			// Positionen gleich der Position der ListBoxFigure.
			// Hierzu bekommt parts eine neue Zuweisung.
			parts = liBoFi.getFigures();
			while (parts.hasMoreElements()) {
				liBoFiPart = (RectangleFigure) parts.nextElement();
				liBoFiPart.getBounds().width = 0;
				liBoFiPart.getBounds().x = liBoFi.getBounds().x;
				liBoFiPart.getBounds().y = liBoFi.getBounds().y;
			}
			// Hier werden die DisplayBoxen aller TextBoxFigures neu berechnet.
			// Beachte, dass die Berechnung bewirkt, dass die Position auf 0,0
			// gesetzt wird. Daher werden die Figuren anschliessend um die Posi-
			// tion der ListBoxFigure verschoben.
			// Da in der folgenden NeuBerechnung die FilledRectangularBox nicht
			// mitberuecksichtigt wird, war es oben noetig, die Position aller
			// TeilFiguren auf die der ListBoxFigure zu setzten.
			// Es wird mit der TextBoxFigure des HeaderModels begonnen.
			teBoFi = (TextBoxFigure) ((ObjectModel) liBoFi.getModel()).getHeaderModel().getFigure();
			teBoFi.setBounds(this.getTextBoxFigureBuilder().computeDisplayBox(teBoFi));
			teBoFi.moveBy(liBoFi.getBounds().x, liBoFi.getBounds().y);
			// Jetzt kommen die TextBoxFigures der AttributeModels dran.
			parts = ((ObjectModel) liBoFi.getModel()).getAttributeModels();
			while (parts.hasMoreElements()) {
				teBoFi = (TextBoxFigure) ((AttributeModel) parts.nextElement()).getFigure();
				teBoFi.setBounds(this.getTextBoxFigureBuilder().computeDisplayBox(teBoFi));
				teBoFi.moveBy(liBoFi.getBounds().x, liBoFi.getBounds().y);
			}
		}
		catch (Exception ex) {
			ListBoxFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_SETTING_ALL_ATTRIBUTES_VISIBLE), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_SETTING_ALL_ATTRIBUTES_VISIBLE));
		}
	}

	private void setFigureHeight(Figure figure, int y) {
		try {
			figure.getDisplayBox().getRectangle().height = y;
		}
		catch (Exception ex) {
			ListBoxFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_BUILDING_MODELL), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_BUILDING_MODELL));
		}
	}

	private void setFigureWidth(Figure figure, int x) {
		try {
			figure.getDisplayBox().getRectangle().width = x;
		}
		catch (Exception ex) {
			ListBoxFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_BUILDING_MODELL), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_BUILDING_MODELL));
		}
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass alle Verknuepfungen bereits
	 * existieren und die Teilfiguren -jede fuer sich- gebaut sind. Sie weist
	 * jeder Teilfigur und der ListBoxFigure die Breite zu. Zu Beachten gilt
	 * dabei, dass dies auch fuer die "unvisible" TextBoxFigures geschieht,
	 * damit die Connections richtig posi- tioniert sind.
	 */
	private void setListBoxFigureWidths(ListBoxFigure list, int width) {
		/* damn the class Rectangle for having no get method for the width! */
		StandardEnumeration figParts;
		try {
			figParts = list.getFigures();
		}
		catch (Exception ex) {
			ListBoxFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_GETTING_FIGURES), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_GETTING_FIGURES));
			figParts = null;
		}
		this.setFigureWidth(list, width);
		if (figParts != null) {
			while (figParts.hasMoreElements()) {
				this.setFigureWidth((Figure) figParts.nextElement(), width);
			}
		}
	}

	/**
	 * Kommentar: Diese Methode geht so vor: Zunaechst werden alle
	 * AttributeModels, die in der ListBoxFigure enthalten sind, auf unvisible
	 * gesetzt. Dann wird die Weite von allen Teilen der ListBoxFigure auf 0
	 * (null) gesetzt, und die Orte werden gleich dem Ort der ListBoxFigure
	 * gesetzt. Mit allen TeilFiguren, die TextBoxFigure sind, geschieht dann
	 * folgendes: Die DisplayBox wird erneut berechnet. Die Be- rechnung der
	 * DisplayBox hat zur Folge, dass der Ort der Figure wieder auf 0,0 gesetzt
	 * wird. Also wird die TextBoxFigure wieder um die Position der
	 * ListBoxFigure verschoben.
	 */
	public void setNullAttributesUnvisible(ListBoxFigure liBoFi) {
		ObjectModel objModl;
		StandardEnumeration parts;
		TextBoxFigure teBoFi;
		AttributeModel attrModl;
		RectangleFigure liBoFiPart;
		objModl = (ObjectModel) liBoFi.getModel();
		objModl.getObjectViewManager().setNullAttributesVisible(false);
		parts = objModl.getAttributeModels();
		try {
			// Hier werden die Attribute unsichtbar gesetzt, deren Wert gleich
			// null ist.
			while (parts.hasMoreElements()) {
				attrModl = (AttributeModel) parts.nextElement();
				// if (attrModl.getValue() == null) {
				if (NullObject.isNullObject(attrModl.getValue())) {
					((TextBoxFigure) attrModl.getFigure()).setUnvisible();
					// this.getConnectionBuilder().removeSingleConnection(attrModl);
				}
			}
			// Hier werden die Weiten aller Teilfiguren auf 0 gesetzt und die
			// Positionen gleich der Position der ListBoxFigure.
			// Hierzu bekommt parts eine neue Zuweisung.
			parts = liBoFi.getFigures();
			while (parts.hasMoreElements()) {
				liBoFiPart = (RectangleFigure) parts.nextElement();
				liBoFiPart.getBounds().width = 0;
				liBoFiPart.getBounds().x = liBoFi.getBounds().x;
				liBoFiPart.getBounds().y = liBoFi.getBounds().y;
			}
			// Hier werden die DisplayBoxen aller TextBoxFigures neu berechnet.
			// Beachte, dass die Berechnung bewirkt, dass die Position auf 0,0
			// gesetzt wird. Daher werden die Figuren anschliessend um die Posi-
			// tion der ListBoxFigure verschoben.
			// Da in der folgenden NeuBerechnung die FilledRectangularBox nicht
			// mitberuecksichtigt wird, war es oben noetig, die Position aller
			// TeilFiguren auf die der ListBoxFigure zu setzten.
			// Es wird mit der TextBoxFigure des HeaderModels begonnen.
			teBoFi = (TextBoxFigure) ((ObjectModel) liBoFi.getModel()).getHeaderModel().getFigure();
			teBoFi.setBounds(this.getTextBoxFigureBuilder().computeDisplayBox(teBoFi));
			teBoFi.moveBy(liBoFi.getBounds().x, liBoFi.getBounds().y);
			// Jetzt kommen die TextBoxFigures der AttributeModels dran.
			parts = ((ObjectModel) liBoFi.getModel()).getAttributeModels();
			while (parts.hasMoreElements()) {
				teBoFi = (TextBoxFigure) ((AttributeModel) parts.nextElement()).getFigure();
				teBoFi.setBounds(this.getTextBoxFigureBuilder().computeDisplayBox(teBoFi));
				teBoFi.moveBy(liBoFi.getBounds().x, liBoFi.getBounds().y);
			}
		}
		catch (Exception ex) {
			ListBoxFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_SETTING_ALL_ATTRIBUTES_UNVISIBLE), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_SETTING_ALL_ATTRIBUTES_UNVISIBLE));
		}
	}

	/**
	 * Kommentar: Diese Methode geht so vor: Zunaechst werden alle
	 * AttributeModels, die in der ListBoxFigure enthalten sind, auf unvisible
	 * gesetzt. Dann wird die Weite von allen Teilen der ListBoxFigure auf 0
	 * (null) gesetzt, und die Orte werden gleich dem Ort der ListBoxFigure
	 * gesetzt. Mit allen TeilFiguren, die TextBoxFigure sind, geschieht dann
	 * folgendes: Die DisplayBox wird erneut berechnet. Die Be- rechnung der
	 * DisplayBox hat zur Folge, dass der Ort der Figure wieder auf 0,0 gesetzt
	 * wird. Also wird die TextBoxFigure wieder um die Position der
	 * ListBoxFigure verschoben.
	 */
	public void setNullAttributesVisible(ListBoxFigure liBoFi) {
		ObjectModel objModl;
		Enumeration<?> parts;
		TextBoxFigure teBoFi;
		AttributeModel attrModl;
		RectangleFigure liBoFiPart;
		objModl = (ObjectModel) liBoFi.getModel();
		objModl.getObjectViewManager().setNullAttributesVisible(true);
		parts = objModl.getAttributeModels().getVector().elements();
		try {
			// Hier werden die Attribute unsichtbar gesetzt, deren Wert gleich
			// null ist.
			while (parts.hasMoreElements()) {
				attrModl = (AttributeModel) parts.nextElement();
				// if (attrModl.getValue() == null) {
				if (objModl.getObjectViewManager().shouldBeVisible(attrModl)) {
					((TextBoxFigure) attrModl.getFigure()).setVisible();
				}
			}
			// Hier werden die Weiten aller Teilfiguren auf 0 gesetzt und die
			// Positionen gleich der Position der ListBoxFigure.
			// Hierzu bekommt parts eine neue Zuweisung.
			parts = liBoFi.getFigures();
			while (parts.hasMoreElements()) {
				liBoFiPart = (RectangleFigure) parts.nextElement();
				liBoFiPart.getBounds().width = 0;
				liBoFiPart.getBounds().x = liBoFi.getBounds().x;
				liBoFiPart.getBounds().y = liBoFi.getBounds().y;
			}
			// Hier werden die DisplayBoxen aller TextBoxFigures neu berechnet.
			// Beachte, dass die Berechnung bewirkt, dass die Position auf 0,0
			// gesetzt wird. Daher werden die Figuren anschliessend um die Posi-
			// tion der ListBoxFigure verschoben.
			// Da in der folgenden NeuBerechnung die FilledRectangularBox nicht
			// mitberuecksichtigt wird, war es oben noetig, die Position aller
			// TeilFiguren auf die der ListBoxFigure zu setzten.
			// Es wird mit der TextBoxFigure des HeaderModels begonnen.
			teBoFi = (TextBoxFigure) ((ObjectModel) liBoFi.getModel()).getHeaderModel().getFigure();
			teBoFi.setBounds(this.getTextBoxFigureBuilder().computeDisplayBox(teBoFi));
			teBoFi.moveBy(liBoFi.getBounds().x, liBoFi.getBounds().y);
			// Jetzt kommen die TextBoxFigures der AttributeModels dran.
			parts = ((ObjectModel) liBoFi.getModel()).getAttributeModels();
			while (parts.hasMoreElements()) {
				teBoFi = (TextBoxFigure) ((AttributeModel) parts.nextElement()).getFigure();
				teBoFi.setBounds(this.getTextBoxFigureBuilder().computeDisplayBox(teBoFi));
				teBoFi.moveBy(liBoFi.getBounds().x, liBoFi.getBounds().y);
			}
		}
		catch (Exception ex) {
			ListBoxFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_SETTING_ALL_ATTRIBUTES_VISIBLE), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_SETTING_ALL_ATTRIBUTES_VISIBLE));
		}
	}

	/**
	 * Kommentar: Diese Methode geht so vor: Zunaechst wird die TextBoxFigure,
	 * auf unvisible gesetzt. Dann wird die Weite von allen Teilen der
	 * ListBoxFigure auf 0 (null) gesetzt, und die Orte werden gleich dem Ort
	 * der ListBoxFigure gesetzt. Mit allen TeilFiguren, die TextBoxFigure sind,
	 * geschieht dann folgendes: Die DisplayBox wird erneut berechnet. Die Be-
	 * rechnung der DisplayBox hat zur Folge, dass der Ort der Figure wieder auf
	 * 0,0 gesetzt wird. Also wird die TextBoxFigure wieder um die Position der
	 * ListBoxFigure verschoben.
	 */
	public void setSingleAttributeUnvisible(TextBoxFigure texBoFi) {
		ListBoxFigure liBoFi = (ListBoxFigure) texBoFi.getParent();
		de.paragon.explorer.util.StandardEnumeration parts;
		TextBoxFigure teBoFi;
		// AttributeModel attrModl;
		RectangleFigure liBoFiPart;
		try {
			// Hier wird das Attribut unsichtbar gesetzt.
			texBoFi.setUnvisible();
			// this.getConnectionBuilder().removeSingleConnection((AttributeModel)
			// texBoFi.getModel());
			// Hier werden die Weiten aller Teilfiguren auf 0 gesetzt und die
			// Positionen gleich der Position der ListBoxFigure.
			// Hierzu bekommt parts eine neue Zuweisung.
			parts = liBoFi.getFigures();
			while (parts.hasMoreElements()) {
				liBoFiPart = (RectangleFigure) parts.nextElement();
				liBoFiPart.getBounds().width = 0;
				liBoFiPart.getBounds().x = liBoFi.getBounds().x;
				liBoFiPart.getBounds().y = liBoFi.getBounds().y;
			}
			// Hier werden die DisplayBoxen aller TextBoxFigures neu berechnet.
			// Beachte, dass die Berechnung bewirkt, dass die Position auf 0,0
			// gesetzt wird. Daher werden die Figuren anschliessend um die Posi-
			// tion der ListBoxFigure verschoben.
			// Da in der folgenden NeuBerechnung die FilledRectangularBox nicht
			// mitberuecksichtigt wird, war es oben noetig, die Position aller
			// TeilFiguren auf die der ListBoxFigure zu setzten.
			// Es wird mit der TextBoxFigure des HeaderModels begonnen.
			teBoFi = (TextBoxFigure) ((ObjectModel) liBoFi.getModel()).getHeaderModel().getFigure();
			teBoFi.setBounds(this.getTextBoxFigureBuilder().computeDisplayBox(teBoFi));
			teBoFi.moveBy(liBoFi.getBounds().x, liBoFi.getBounds().y);
			// Jetzt kommen die TextBoxFigures der AttributeModels dran.
			parts = ((ObjectModel) liBoFi.getModel()).getAttributeModels();
			while (parts.hasMoreElements()) {
				teBoFi = (TextBoxFigure) ((AttributeModel) parts.nextElement()).getFigure();
				teBoFi.setBounds(this.getTextBoxFigureBuilder().computeDisplayBox(teBoFi));
				teBoFi.moveBy(liBoFi.getBounds().x, liBoFi.getBounds().y);
			}
		}
		catch (Exception ex) {
			ListBoxFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_SETTING_ALL_ATTRIBUTES_UNVISIBLE), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_SETTING_ALL_ATTRIBUTES_UNVISIBLE));
		}
	}

	/**
	 * Kommentar: Diese Methode geht so vor: Zunaechst werden alle
	 * AttributeModels, die in der ListBoxFigure enthalten sind, auf visible
	 * gesetzt. Dann wird die Weite von allen Teilen der ListBoxFigure auf 0
	 * (null) gesetzt, und die Orte werden gleich dem Ort der ListBoxFigure
	 * gesetzt. Mit allen TeilFiguren, die TextBoxFigure sind, geschieht dann
	 * folgendes: Die DisplayBox wird erneut berechnet. Die Be- rechnung der
	 * DisplayBox hat zur Folge, dass der Ort der Figure wieder auf 0,0 gesetzt
	 * wird. Also wird die TextBoxFigure wieder um die Position der
	 * ListBoxFigure verschoben.
	 */
	public void setSingleAttributeVisible(TextBoxFigure texBoFi) {
		ListBoxFigure liBoFi = (ListBoxFigure) texBoFi.getParent();
		StandardEnumeration parts;
		TextBoxFigure teBoFi;
		// AttributeModel attrModl;
		RectangleFigure liBoFiPart;
		try {
			texBoFi.setVisible();
			// Hier werden die Weiten aller Teilfiguren auf 0 gesetzt und die
			// Positionen gleich der Position der ListBoxFigure.
			// Hierzu bekommt parts eine neue Zuweisung.
			parts = liBoFi.getFigures();
			while (parts.hasMoreElements()) {
				liBoFiPart = (RectangleFigure) parts.nextElement();
				liBoFiPart.getBounds().width = 0;
				liBoFiPart.getBounds().x = liBoFi.getBounds().x;
				liBoFiPart.getBounds().y = liBoFi.getBounds().y;
			}
			// Hier werden die DisplayBoxen aller TextBoxFigures neu berechnet.
			// Beachte, dass die Berechnung bewirkt, dass die Position auf 0,0
			// gesetzt wird. Daher werden die Figuren anschliessend um die Posi-
			// tion der ListBoxFigure verschoben.
			// Da in der folgenden NeuBerechnung die FilledRectangularBox nicht
			// mitberuecksichtigt wird, war es oben noetig, die Position aller
			// TeilFiguren auf die der ListBoxFigure zu setzten.
			// Es wird mit der TextBoxFigure des HeaderModels begonnen.
			teBoFi = (TextBoxFigure) ((ObjectModel) liBoFi.getModel()).getHeaderModel().getFigure();
			teBoFi.setBounds(this.getTextBoxFigureBuilder().computeDisplayBox(teBoFi));
			teBoFi.moveBy(liBoFi.getBounds().x, liBoFi.getBounds().y);
			// Jetzt kommen die TextBoxFigures der AttributeModels dran.
			parts = ((ObjectModel) liBoFi.getModel()).getAttributeModels();
			while (parts.hasMoreElements()) {
				teBoFi = (TextBoxFigure) ((AttributeModel) parts.nextElement()).getFigure();
				teBoFi.setBounds(this.getTextBoxFigureBuilder().computeDisplayBox(teBoFi));
				teBoFi.moveBy(liBoFi.getBounds().x, liBoFi.getBounds().y);
			}
		}
		catch (Exception ex) {
			ListBoxFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_SETTING_ALL_ATTRIBUTES_VISIBLE), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_SETTING_ALL_ATTRIBUTES_VISIBLE));
		}
	}

	/**
	 * Kommentar: Diese Methode geht so vor: Zunaechst werden alle
	 * AttributeModels, die in der ListBoxFigure enthalten sind, auf unvisible
	 * gesetzt. Dann wird die Weite von allen Teilen der ListBoxFigure auf 0
	 * (null) gesetzt, und die Orte werden gleich dem Ort der ListBoxFigure
	 * gesetzt. Mit allen TeilFiguren, die TextBoxFigure sind, geschieht dann
	 * folgendes: Die DisplayBox wird erneut berechnet. Die Be- rechnung der
	 * DisplayBox hat zur Folge, dass der Ort der Figure wieder auf 0,0 gesetzt
	 * wird. Also wird die TextBoxFigure wieder um die Position der
	 * ListBoxFigure verschoben.
	 */
	public void setStaticAttributesUnvisible(ListBoxFigure liBoFi) {
		ObjectModel objModl;
		de.paragon.explorer.util.StandardEnumeration parts;
		TextBoxFigure teBoFi;
		AttributeModel attrModl;
		RectangleFigure liBoFiPart;
		objModl = (ObjectModel) liBoFi.getModel();
		objModl.getObjectViewManager().setStaticAttributesVisible(false);
		parts = objModl.getAttributeModels();
		try {
			// Hier werden die Attribute unsichtbar gesetzt, deren Wert gleich
			// null ist.
			while (parts.hasMoreElements()) {
				attrModl = (AttributeModel) parts.nextElement();
				if (Modifier.isStatic(attrModl.getModifiers())) {
					((TextBoxFigure) attrModl.getFigure()).setUnvisible();
					// this.getConnectionBuilder().removeSingleConnection(attrModl);
				}
			}
			// Hier werden die Weiten aller Teilfiguren auf 0 gesetzt und die
			// Positionen gleich der Position der ListBoxFigure.
			// Hierzu bekommt parts eine neue Zuweisung.
			parts = liBoFi.getFigures();
			while (parts.hasMoreElements()) {
				liBoFiPart = (RectangleFigure) parts.nextElement();
				liBoFiPart.getBounds().width = 0;
				liBoFiPart.getBounds().x = liBoFi.getBounds().x;
				liBoFiPart.getBounds().y = liBoFi.getBounds().y;
			}
			// Hier werden die DisplayBoxen aller TextBoxFigures neu berechnet.
			// Beachte, dass die Berechnung bewirkt, dass die Position auf 0,0
			// gesetzt wird. Daher werden die Figuren anschliessend um die Posi-
			// tion der ListBoxFigure verschoben.
			// Da in der folgenden NeuBerechnung die FilledRectangularBox nicht
			// mitberuecksichtigt wird, war es oben noetig, die Position aller
			// TeilFiguren auf die der ListBoxFigure zu setzten.
			// Es wird mit der TextBoxFigure des HeaderModels begonnen.
			teBoFi = (TextBoxFigure) ((ObjectModel) liBoFi.getModel()).getHeaderModel().getFigure();
			teBoFi.setBounds(this.getTextBoxFigureBuilder().computeDisplayBox(teBoFi));
			teBoFi.moveBy(liBoFi.getBounds().x, liBoFi.getBounds().y);
			// Jetzt kommen die TextBoxFigures der AttributeModels dran.
			parts = ((ObjectModel) liBoFi.getModel()).getAttributeModels();
			while (parts.hasMoreElements()) {
				teBoFi = (TextBoxFigure) ((AttributeModel) parts.nextElement()).getFigure();
				teBoFi.setBounds(this.getTextBoxFigureBuilder().computeDisplayBox(teBoFi));
				teBoFi.moveBy(liBoFi.getBounds().x, liBoFi.getBounds().y);
			}
		}
		catch (Exception ex) {
			ListBoxFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_SETTING_ALL_ATTRIBUTES_UNVISIBLE), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_SETTING_ALL_ATTRIBUTES_UNVISIBLE));
		}
	}

	/**
	 * Kommentar: Diese Methode geht so vor: Zunaechst werden alle
	 * AttributeModels, die in der ListBoxFigure enthalten sind, auf unvisible
	 * gesetzt. Dann wird die Weite von allen Teilen der ListBoxFigure auf 0
	 * (null) gesetzt, und die Orte werden gleich dem Ort der ListBoxFigure
	 * gesetzt. Mit allen TeilFiguren, die TextBoxFigure sind, geschieht dann
	 * folgendes: Die DisplayBox wird erneut berechnet. Die Be- rechnung der
	 * DisplayBox hat zur Folge, dass der Ort der Figure wieder auf 0,0 gesetzt
	 * wird. Also wird die TextBoxFigure wieder um die Position der
	 * ListBoxFigure verschoben.
	 */
	public void setStaticAttributesVisible(ListBoxFigure liBoFi) {
		ObjectModel objModl;
		de.paragon.explorer.util.StandardEnumeration parts;
		TextBoxFigure teBoFi;
		AttributeModel attrModl;
		RectangleFigure liBoFiPart;
		objModl = (ObjectModel) liBoFi.getModel();
		objModl.getObjectViewManager().setStaticAttributesVisible(true);
		parts = objModl.getAttributeModels();
		try {
			// Hier werden die Attribute unsichtbar gesetzt, deren Wert gleich
			// null ist.
			while (parts.hasMoreElements()) {
				attrModl = (AttributeModel) parts.nextElement();
				if (objModl.getObjectViewManager().shouldBeVisible(attrModl)) {
					((TextBoxFigure) attrModl.getFigure()).setVisible();
				}
			}
			// Hier werden die Weiten aller Teilfiguren auf 0 gesetzt und die
			// Positionen gleich der Position der ListBoxFigure.
			// Hierzu bekommt parts eine neue Zuweisung.
			parts = liBoFi.getFigures();
			while (parts.hasMoreElements()) {
				liBoFiPart = (RectangleFigure) parts.nextElement();
				liBoFiPart.getBounds().width = 0;
				liBoFiPart.getBounds().x = liBoFi.getBounds().x;
				liBoFiPart.getBounds().y = liBoFi.getBounds().y;
			}
			// Hier werden die DisplayBoxen aller TextBoxFigures neu berechnet.
			// Beachte, dass die Berechnung bewirkt, dass die Position auf 0,0
			// gesetzt wird. Daher werden die Figuren anschliessend um die Posi-
			// tion der ListBoxFigure verschoben.
			// Da in der folgenden NeuBerechnung die FilledRectangularBox nicht
			// mitberuecksichtigt wird, war es oben noetig, die Position aller
			// TeilFiguren auf die der ListBoxFigure zu setzten.
			// Es wird mit der TextBoxFigure des HeaderModels begonnen.
			teBoFi = (TextBoxFigure) ((ObjectModel) liBoFi.getModel()).getHeaderModel().getFigure();
			teBoFi.setBounds(this.getTextBoxFigureBuilder().computeDisplayBox(teBoFi));
			teBoFi.moveBy(liBoFi.getBounds().x, liBoFi.getBounds().y);
			// Jetzt kommen die TextBoxFigures der AttributeModels dran.
			parts = ((ObjectModel) liBoFi.getModel()).getAttributeModels();
			while (parts.hasMoreElements()) {
				teBoFi = (TextBoxFigure) ((AttributeModel) parts.nextElement()).getFigure();
				teBoFi.setBounds(this.getTextBoxFigureBuilder().computeDisplayBox(teBoFi));
				teBoFi.moveBy(liBoFi.getBounds().x, liBoFi.getBounds().y);
			}
		}
		catch (Exception ex) {
			ListBoxFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_SETTING_ALL_ATTRIBUTES_VISIBLE), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_SETTING_ALL_ATTRIBUTES_VISIBLE));
		}
	}

	public void updateObject(ListBoxFigure liBoFi) {
		StandardEnumeration parts;
		TextBoxFigure teBoFi;
		RectangleFigure liBoFiPart;
		try {
			// Hier werden die Weiten aller Teilfiguren auf 0 gesetzt und die
			// Positionen gleich der Position der ListBoxFigure.
			// Hierzu bekommt parts eine neue Zuweisung.
			this.getTextBoxFigureBuilder().updateHeader((ObjectModel) liBoFi.getModel());
			this.getTextBoxFigureBuilder().updateAttributes((ObjectModel) liBoFi.getModel());
			parts = liBoFi.getFigures();
			while (parts.hasMoreElements()) {
				liBoFiPart = (RectangleFigure) parts.nextElement();
				liBoFiPart.getBounds().width = 0;
				liBoFiPart.getBounds().x = liBoFi.getBounds().x;
				liBoFiPart.getBounds().y = liBoFi.getBounds().y;
			}
			// Hier werden die DisplayBoxen aller TextBoxFigures neu berechnet.
			// Beachte, dass die Berechnung bewirkt, dass die Position auf 0,0
			// gesetzt wird. Daher werden die Figuren anschliessend um die Posi-
			// tion der ListBoxFigure verschoben.
			// Da in der folgenden NeuBerechnung die FilledRectangularBox nicht
			// mitberuecksichtigt wird, war es oben noetig, die Position aller
			// TeilFiguren auf die der ListBoxFigure zu setzten.
			// Es wird mit der TextBoxFigure des HeaderModels begonnen.
			teBoFi = (TextBoxFigure) ((ObjectModel) liBoFi.getModel()).getHeaderModel().getFigure();
			teBoFi.setBounds(this.getTextBoxFigureBuilder().computeDisplayBox(teBoFi));
			teBoFi.moveBy(liBoFi.getBounds().x, liBoFi.getBounds().y);
			// Jetzt kommen die TextBoxFigures der AttributeModels dran.
			parts = ((ObjectModel) liBoFi.getModel()).getAttributeModels();
			while (parts.hasMoreElements()) {
				teBoFi = (TextBoxFigure) ((AttributeModel) parts.nextElement()).getFigure();
				teBoFi.setBounds(this.getTextBoxFigureBuilder().computeDisplayBox(teBoFi));
				teBoFi.moveBy(liBoFi.getBounds().x, liBoFi.getBounds().y);
			}
		}
		catch (Exception ex) {
			ListBoxFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_UPDATING_FIGURES), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ListBoxFigureBuilder.ERROR_WHILE_UPDATING_FIGURES));
		}
	}
}
