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

import java.util.Vector;

import org.apache.log4j.Logger;

import de.paragon.explorer.gui.Warning;
import de.paragon.explorer.model.AttributeModel;
import de.paragon.explorer.model.ObjectModel;
import de.paragon.explorer.util.LoggerFactory;
import de.paragon.explorer.util.ResourceBundlePurchaser;
import de.paragon.explorer.util.StandardEnumeration;
import de.paragon.explorer.util.StandardEnumerator;

public class ExplorerFigureBuilder {
	private static final int				NUMBER_15														= 15;
	private static final int				NUMBER_25														= 25;
	private static final int				NUMBER_24														= 24;
	private static final int				NUMBER_5														= 5;
	private static final int				NUMBER_50														= 50;
	private static final int				NUMBER_20														= 20;
	private static final String				EXPLORERFIGUREBUILDER_ERROR_WHILE_BUILDING_EXPLORERMODEL		= "explorerfigurebuilder.error_while_building_explorermodel";
	private static final String				EXPLORERFIGUREBUILDER_ERROR_WHILE_BUILDING_MODEL				= "explorerfigurebuilder.error_while_building_model";
	private static final String				EXPLORERFIGUREBUILDER_ERROR_WHILE_COPYING_FIGURES				= "explorerfigurebuilder.error_while_copying_figures";
	private static final String				EXPLORERFIGUREBUILDER_ERROR_WHILE_DRAWING						= "explorerfigurebuilder.error_while_drawing";
	private static final String				EXPLORERFIGUREBUILDER_ERROR_WHILE_INITIALIZING_1ST_LIST			= "explorerfigurebuilder.error_while_initializing_1st_list";
	private static final String				EXPLORERFIGUREBUILDER_ERROR_WHILE_REMOVING						= "explorerfigurebuilder.error_while_removing";
	private static final String				EXPLORERFIGUREBUILDER_ERROR_WHILE_SETTING_FIGURE_IN_FOREGROUND	= "explorerfigurebuilder.error_while_setting_figure_in_foreground";
	private static final String				EXPLORERFIGUREBUILDER_ERROR_WHILE_SETTING_LBF_BEHIND			= "explorerfigurebuilder.error_while_setting_lbf_behind";
	private static final Logger				logger															= LoggerFactory.make();
	private static ExplorerFigureBuilder	singleton;

	public static ExplorerFigureBuilder getInstance() {
		return ExplorerFigureBuilder.getSingleton();
	}

	private static ExplorerFigureBuilder getSingleton() {
		if (ExplorerFigureBuilder.singleton == null) {
			ExplorerFigureBuilder.setSingleton(new ExplorerFigureBuilder());
		}
		return ExplorerFigureBuilder.singleton;
	}

	private static void setSingleton(ExplorerFigureBuilder builder) {
		ExplorerFigureBuilder.singleton = builder;
	}

	/**
	 * Kommentar: Diese Methode verknuepft die ListBoxFigure mit der
	 * ExplorerFigure. Im Gegensatz zur Methode addListBoxFigure benoetigt diese
	 * Methode hier die ExplorerFigure, da die StandardConnectionFigure nicht
	 * mit einem Model verknuepft ist, ueber das auf die ExplorerFigure
	 * zurueckgeschlossen werden koennte. Ansonsten macht diese Methode das
	 * gleiche. Das Verknuepfen von ListBox- bzw. StandardConnectionFigure mit
	 * der Explorer- Figure geschieht in zwei verschiedenen Methoden in Hinblick
	 * auf eventuelle, spaetere Unterscheidung zwischen beiden.
	 */
	public void addConnectionFigure(StandardConnectionFigure conFig, ExplorerFigure explFig) {
		try {
			explFig.add(conFig);
		}
		catch (Exception ex) {
			ExplorerFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_BUILDING_EXPLORERMODEL), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_BUILDING_EXPLORERMODEL));
		}
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass die Verknuepfungen zwischen
	 * ListBoxFigure, ObjectModel und ExplorerModel bereits existieren. Sie
	 * verknuepft die ListBoxFigure mit der ExplorerFigure.
	 */
	public void addListBoxFigure(ListBoxFigure liBoFi) {
		ObjectModel objModl = (ObjectModel) liBoFi.getModel();
		try {
			objModl.getExplorerModel().getFigure().add(liBoFi);
		}
		catch (Exception ex) {
			ExplorerFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_BUILDING_EXPLORERMODEL), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_BUILDING_EXPLORERMODEL));
		}
	}

	public ExplorerFigure createNewExplorerFigure() {
		return new ExplorerFigure();
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass die uebergebene
	 * ListBoxFigure nicht die erste ist. Soll heissen, dass anhand der
	 * ListBoxFigure die Graphics ermittelt werden koennen.
	 */
	public void draw(ListBoxFigure liBoFi) {
		((ExplorerFigure) liBoFi.getParent()).repaint();
	}

	/**
	 * Kommentar: Der Methodenname dieser Methode drueckt es schon aus:
	 * drawInForeground bedeutet, eine Figure zuerst in den Verdergrund zu
	 * setzen und dann zu zeichnen. Ein vorheriges Loeschen ist nicht noetig, da
	 * es nichts gibt, das vorher verdeckt war und nachher zu sehen sein wird.
	 */
	public void drawInForeground(ListBoxFigure liBoFi) {
		this.setInForeground(liBoFi);
		this.draw(liBoFi);
	}

	private int getFigureHeight(Figure figure) {
		int tempHeight = 0;
		try {
			tempHeight = figure.getDisplayBox().getRectangle().height;
		}
		catch (Exception ex) {
			ExplorerFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_BUILDING_MODEL), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_BUILDING_MODEL));
		}
		return tempHeight;
	}

	/**
	 * Kommentar: Eine Kopie der Aufzaehlung muss aus folgenden Grund extra
	 * erstellt werden: Der Operator new StandardEnumerator(Vector v) erzeugt
	 * einen neuen StandardEnumerator, der auf den selben, uebergebenen Vector v
	 * zeigt. Ist dies der Vector aus dem FigureStore, so werden neue und alte
	 * StandardEnumeration nicht unabhaengig voneinander bearbeitet.
	 * 
	 * @return StandardEnumeration
	 */
	private StandardEnumeration getFiguresOf(ExplorerFigure explFig) {
		StandardEnumeration returnEnum = new StandardEnumerator();
		StandardEnumeration tempEnum = null;
		try {
			tempEnum = explFig.getFigures();
		}
		catch (Exception ex) {
			ExplorerFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_COPYING_FIGURES), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_COPYING_FIGURES));
		}
		if (tempEnum != null) {
			while (tempEnum.hasMoreElements()) {
				returnEnum.addElement(tempEnum.nextElement());
			}
		}
		return returnEnum;
	}

	private int getFigureWidth(Figure figure) {
		int tempWidth = 0;
		try {
			tempWidth = figure.getDisplayBox().getRectangle().width;
		}
		catch (Exception ex) {
			ExplorerFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_BUILDING_MODEL), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_BUILDING_MODEL));
		}
		return tempWidth;
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass die ListBoxFigure schon in
	 * allen Variablen definiert ist und nur noch in Position und Groesse in die
	 * ExplorerFigure einge- baut werden muss.
	 */
	public void initialize1stList(ListBoxFigure liBoFi) {
		ExplorerFigure explFig = (ExplorerFigure) liBoFi.getParent();
		try {
			liBoFi.moveBy(ExplorerFigureBuilder.NUMBER_20, ExplorerFigureBuilder.NUMBER_50);
		}
		catch (Exception ex) {
			ExplorerFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_INITIALIZING_1ST_LIST), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_INITIALIZING_1ST_LIST));
		}
		this.setFigureWidth(explFig, ExplorerFigureBuilder.NUMBER_20 + this.getFigureWidth(liBoFi));
		this.setFigureHeight(explFig, ExplorerFigureBuilder.NUMBER_50 + this.getFigureHeight(liBoFi));
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass die ListBoxFigure schon in
	 * allen Variablen definiert ist und nur noch in Position und Groesse in die
	 * ExplorerFigure einge- baut werden muss.
	 */
	public void initializeCopyList(ListBoxFigure liBoFi) {
		ExplorerFigure explFig;
		java.awt.Point p;
		int x;
		int y;
		explFig = (ExplorerFigure) liBoFi.getParent();
		p = javax.swing.SwingUtilities.convertPoint(explFig.getFrame(), ExplorerFigureBuilder.NUMBER_5, ExplorerFigureBuilder.NUMBER_24, explFig.getPanel());
		x = (int) p.getX() + ExplorerFigureBuilder.NUMBER_20;
		y = (int) p.getY() + ExplorerFigureBuilder.NUMBER_20;
		try {
			liBoFi.moveBy(x, y);
		}
		catch (Exception ex) {
			ExplorerFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_INITIALIZING_1ST_LIST), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_INITIALIZING_1ST_LIST));
		}
		this.setFigureWidth(explFig, x + this.getFigureWidth(liBoFi));
		this.setFigureHeight(explFig, y + this.getFigureHeight(liBoFi));
	}

	public void initializeListFor(ObjectModel objModl, AttributeModel attrModl) {
		ListBoxFigure liBoFi = (ListBoxFigure) objModl.getFigure();
		TextBoxFigure teBoFi = (TextBoxFigure) attrModl.getFigure();
		try {
			int x = teBoFi.getBounds().x + teBoFi.getBounds().width + ExplorerFigureBuilder.NUMBER_15;
			int y = teBoFi.getBounds().y;
			if (!teBoFi.isVisible()) {
				ObjectModel myObjModl = (ObjectModel) ((ListBoxFigure) teBoFi.getParent()).getModel();
				Vector<?> attrModls = myObjModl.getAttributeModels().getVector();
				for (int i = 0; i < attrModls.size(); i++) {
					AttributeModel myAttrModl = (AttributeModel) attrModls.elementAt(i);
					if (myAttrModl.getConnectionModel() != null) {
						y = y + ExplorerFigureBuilder.NUMBER_25;
					}
				}
			}
			liBoFi.moveBy(x, y);
		}
		catch (Exception ex) {
			ExplorerFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_INITIALIZING_1ST_LIST), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_INITIALIZING_1ST_LIST));
		}
	}

	/**
	 * Kommentar: Das Verschieben funktioniert so: Anhand der ListBoxFigure
	 * bestimmt diese Methode die zugehoerige ExplorerFigure. Diese wird
	 * geloescht. Dann werden die zugehoerigen Daten der ListBoxFigure
	 * verschoben. Danach wird die gesamte ExplorerFigure wieder gezeichnet.
	 */
	public void moveFigureBy(ListBoxFigure liBoFi, int x, int y) {
		// ExplorerFigure explFig = (ExplorerFigure) liBoFi.getParent();
		// explFig.clear();
		try {
			liBoFi.moveBy(x, y);
			// explFig.draw();
		}
		catch (Exception ex) {
			ExplorerFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_DRAWING), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_DRAWING));
		}
	}

	/**
	 * Kommentar: Zunaechst wird die betreffende Figur aus der Liste aller
	 * Figuren entfernt. Dann wird eine Kopie der Liste der uebrigen Figuren
	 * erstellt. Die betreffende Figur wird der Liste aller Figuren wieder
	 * hinzugefuegt, danach die uebri- gen. Auf diese Weise wird die betreffende
	 * Figur zuerst gezeichnet und dann von allen anderen uebermalt. Eine Kopie
	 * der Aufzaehlung muss aus folgenden Grund extra erstellt werden: Der
	 * Operator new StandardEnumerator(Vector v) erzeugt einen neuen
	 * StandardEnumerator, der auf den selben, uebergebenen Vector v zeigt. Ist
	 * dies der Vector aus dem FigureStore, so werden neue und alte
	 * StandardEnumeration nicht unabhaengig voneinander bearbeitet.
	 */
	public void placeBehind(ListBoxFigure liBoFi) {
		ExplorerFigure explFig = (ExplorerFigure) liBoFi.getParent();
		StandardEnumeration enumeration;
		try {
			explFig.remove(liBoFi);
			enumeration = this.getFiguresOf(explFig);
			explFig.removeAll();
			explFig.add(liBoFi);
			explFig.addAll(enumeration);
		}
		catch (Exception ex) {
			ExplorerFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_SETTING_LBF_BEHIND), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_SETTING_LBF_BEHIND));
		}
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass das zugehoerige ObjectModel
	 * bereits geloescht worden ist. Das Loeschen einer ListBoxFigure besteht
	 * darin, sie aus der Liste figureStore der ExplorerFigure zu entfernen.
	 */
	public void removeFromExplorer(ListBoxFigure liBoFi) {
		try {
			if (liBoFi.getParent() != null) {
				liBoFi.getParent().remove(liBoFi);
			}
		}
		catch (Exception ex) {
			ExplorerFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_REMOVING), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_REMOVING));
		}
	}

	private void setFigureHeight(Figure figure, int y) {
		try {
			figure.getDisplayBox().getRectangle().height = y;
		}
		catch (Exception ex) {
			ExplorerFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_BUILDING_MODEL), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_BUILDING_MODEL));
		}
	}

	private void setFigureWidth(Figure figure, int x) {
		try {
			figure.getDisplayBox().getRectangle().width = x;
		}
		catch (Exception ex) {
			ExplorerFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_BUILDING_MODEL), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_BUILDING_MODEL));
		}
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass die uebergebene Figure
	 * entweder eine ListBoxFigure oder eine ConnectionFigure ist. Diese Methode
	 * entfernt die entsprechende List- BoxFigure aus der Menge aller
	 * ListBoxFigures und setzt sie ans Ende dieser Liste. Es wird nicht
	 * gezeichnet!
	 */
	public void setInForeground(Figure figure) {
		ExplorerFigure explFig = (ExplorerFigure) figure.getParent();
		try {
			explFig.getFigures().removeElement(figure);
			explFig.add(figure);
		}
		catch (Exception ex) {
			ExplorerFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_SETTING_FIGURE_IN_FOREGROUND), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ExplorerFigureBuilder.EXPLORERFIGUREBUILDER_ERROR_WHILE_SETTING_FIGURE_IN_FOREGROUND));
		}
	}

	public void update(ExplorerFigure explFig) {
		explFig.repaint();
	}
}
