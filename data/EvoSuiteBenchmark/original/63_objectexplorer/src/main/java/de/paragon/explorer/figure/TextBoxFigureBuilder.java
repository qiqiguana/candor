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

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;

import org.apache.log4j.Logger;

import de.paragon.explorer.gui.Warning;
import de.paragon.explorer.model.AttributeModel;
import de.paragon.explorer.model.ObjectHeaderModel;
import de.paragon.explorer.model.ObjectModel;
import de.paragon.explorer.model.ObjectModelPart;
import de.paragon.explorer.util.LoggerFactory;
import de.paragon.explorer.util.ResourceBundlePurchaser;
import de.paragon.explorer.util.StandardEnumeration;

public class TextBoxFigureBuilder {
	private static final Logger			logger								= LoggerFactory.make();
	private static final String			ERROR_WHILE_BUILDING_TEXTBOXFIGURE	= "textboxfigurebuilder.error_while_building_textboxfigure";
	private static final int			NUMBER_6							= 6;
	private static final int			NUMBER_3							= 3;
	private static TextBoxFigureBuilder	singleton;

	public static TextBoxFigureBuilder getInstance() {
		return TextBoxFigureBuilder.getSingleton();
	}

	private static TextBoxFigureBuilder getSingleton() {
		if (TextBoxFigureBuilder.singleton == null) {
			TextBoxFigureBuilder.setSingleton(new TextBoxFigureBuilder());
		}
		return TextBoxFigureBuilder.singleton;
	}

	private static void setSingleton(TextBoxFigureBuilder builder) {
		TextBoxFigureBuilder.singleton = builder;
	}

	public TextBoxFigureBuilder() {
		super();
	}

	// StandardEnumeration asEnumeration(Vector<?> aVector) {
	// return new StandardEnumerator(aVector);
	// }
	/**
	 * Kommentar: Diese Methode geht davon aus, dass die Verknuepfungen zwischen
	 * ListBoxFigure, ObjectModel, ObjectHeaderModel und der dazugehoerigen
	 * TextBoxFigure bereits existieren. Sie setzt den entsprechenden Text und
	 * bestimmt die Groesse der TextBoxFigure. Anschliessend verknuepft sie
	 * ListBoxFigure mit der TextBoxFigure.
	 */
	private void buildFigureForHeader(ObjectModel objModl) {
		ObjectHeaderModel headModl = objModl.getHeaderModel();
		TextBoxFigure figure = (TextBoxFigure) headModl.getFigure();
		figure.setText(headModl.getTitle());
		figure.setFont(this.getFont(figure));
		figure.setVisible();
		figure.setBackground(objModl.getColorManager().getBackground(figure));
		figure.setForeground(objModl.getColorManager().getForeground(figure));
		java.awt.Rectangle box = this.computeDisplayBox(figure);
		figure.getDisplayBox().getRectangle().setBounds(box);
		try {
			objModl.getFigure().add(headModl.getFigure());
		}
		catch (Exception ex) {
			TextBoxFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(TextBoxFigureBuilder.ERROR_WHILE_BUILDING_TEXTBOXFIGURE), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(TextBoxFigureBuilder.ERROR_WHILE_BUILDING_TEXTBOXFIGURE));
		}
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass die Verknuepfungen zwischen
	 * ListBoxFigure, ObjectModel, den AttributeModels und den dazugehoerigen
	 * TextBoxFigures bereits existieren. Sie setzt den entsprechenden Text und
	 * bestimmt die Groesse der TextBoxFigure. Anschliessend verknuepft sie
	 * ListBoxFigure mit der TextBoxFigure.
	 */
	private void buildFiguresForAttributes(ObjectModel objModl) {
		StandardEnumeration attrModls = objModl.getAttributeModels();
		while (attrModls.hasMoreElements()) {
			AttributeModel attrModl = (AttributeModel) attrModls.nextElement();
			TextBoxFigure figure = (TextBoxFigure) attrModl.getFigure();
			figure.setText(attrModl.getTitle());
			figure.setBackground(objModl.getColorManager().getBackground(figure));
			figure.setForeground(objModl.getColorManager().getForeground(figure));
			figure.setFont(this.getFont(figure));
			try {
				objModl.getFigure().add(attrModl.getFigure());
			}
			catch (Exception ex) {
				TextBoxFigureBuilder.logger.error(ResourceBundlePurchaser.getMessage(TextBoxFigureBuilder.ERROR_WHILE_BUILDING_TEXTBOXFIGURE), ex);
				Warning.showWarning(ResourceBundlePurchaser.getMessage(TextBoxFigureBuilder.ERROR_WHILE_BUILDING_TEXTBOXFIGURE));
			}
		}
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass die TextBoxFigures mit den
	 * jeweiligen Models verknuepft sind. Sie weist jeder TextBoxFigure einen
	 * String zu, bestimmt mit Hilfe der Graphics, die aus der ExplorerFigure
	 * abgefragt werden, die Groesse der TextBoxFigure und verknuepft sie
	 * anschliessend mit der ListBoxFigure. So ist sichergestellt, dass fuer
	 * Bauen und Zeichnen dieselben Graphics verwendet werden. Zwischen der
	 * Verknuepfung von HeaderFigure und den Attribute- Figures wird noch ein
	 * FilledRectangleFigure verknuepft.
	 */
	private void buildTextBoxFigures(ObjectModel objModl) {
		this.buildFigureForHeader(objModl);
		this.getListBoxFigureBuilder().buildHeaderUnderline(objModl);
		this.buildFiguresForAttributes(objModl);
	}

	public Rectangle computeDisplayBox(TextBoxFigure figure) {
		int x, y, width, height;
		java.awt.FontMetrics fm = this.getFontMetrics(figure);
		String text = figure.getText();
		x = 0;
		y = 0;
		height = fm.getLeading() + fm.getMaxAscent() + fm.getMaxDescent() + TextBoxFigureBuilder.NUMBER_3;
		width = fm.stringWidth(text) + TextBoxFigureBuilder.NUMBER_6;
		return new java.awt.Rectangle(x, y, width, height);
	}

	/**
	 * Kommentar: Diese Methode geht davon aus, dass bereits fuer das
	 * ObjectModel eine passende Anzahl entsprechender Teilmodelle erzeugt
	 * wurde. Sie erzeugt fuer jede dieser Models eine TextBoxFigure und
	 * verknuepft sie mit den Teilmodellen. Danach wird jeder TextBoxFigure ein
	 * String zugewiesen. Anschliessend wird von jeder TextBoxFigur die Groesse
	 * bestimmt und in der gleichen Routine mit der ListBoxFigure verknuepft,
	 * von der die Graphics abgefragt wurden. Beim Hinzufuegen einer neuen Figur
	 * wird ja automatisch die Groesse der ListBoxFigure neu berechnet.
	 */
	protected void createTextBoxFigures(ObjectModel objModl) {
		TextBoxFigure teBoFi = new TextBoxFigure();
		StandardEnumeration attrModls = objModl.getAttributeModels();
		objModl.getHeaderModel().setFigure(teBoFi);
		teBoFi.setModel(objModl.getHeaderModel());
		while (attrModls.hasMoreElements()) {
			AttributeModel tempModl = (AttributeModel) attrModls.nextElement();
			teBoFi = new TextBoxFigure();
			teBoFi.setModel(tempModl);
			tempModl.setFigure(teBoFi);
		}
		this.buildTextBoxFigures(objModl);
	}

	private java.awt.Graphics getGraphics(TextBoxFigure teBoFi) {
		ObjectModel objModl = ((ObjectModelPart) teBoFi.getModel()).getObjectModel();
		ExplorerFigure explFig = (ExplorerFigure) objModl.getExplorerModel().getFigure();
		return explFig.getFrame().getGraphics();
	}

	private Font getFont(TextBoxFigure teBoFi) {
		Font f = null;
		ObjectModel objModl = ((ObjectModelPart) teBoFi.getModel()).getObjectModel();
		ExplorerFigure explFig = (ExplorerFigure) objModl.getExplorerModel().getFigure();
		if (explFig.getFrame() != null) {
			f = explFig.getFrame().getGraphics().getFont();
		}
		else {
			f = explFig.getPanel().getFont();
		}
		return f;
	}

	private FontMetrics getFontMetrics(TextBoxFigure teBoFi) {
		FontMetrics fm = null;
		ObjectModel objModl = ((ObjectModelPart) teBoFi.getModel()).getObjectModel();
		ExplorerFigure explFig = (ExplorerFigure) objModl.getExplorerModel().getFigure();
		if (explFig.getFrame() != null) {
			fm = explFig.getFrame().getGraphics().getFontMetrics();
		}
		else {
			fm = explFig.getPanel().getFontMetrics(explFig.getPanel().getFont());
		}
		return fm;
	}

	private ListBoxFigureBuilder getListBoxFigureBuilder() {
		return ListBoxFigureBuilder.getInstance();
	}

	public void updateAttributes(ObjectModel objModl) {
		StandardEnumeration attrModls = objModl.getAttributeModels();
		while (attrModls.hasMoreElements()) {
			AttributeModel attrModl = (AttributeModel) attrModls.nextElement();
			TextBoxFigure figure = (TextBoxFigure) attrModl.getFigure();
			if (objModl.getObjectViewManager().shouldBeVisible(attrModl)) {
				figure.setVisible();
			}
			figure.setText(attrModl.getTitle());
			figure.setBackground(objModl.getColorManager().getBackground(figure));
			figure.setForeground(objModl.getColorManager().getForeground(figure));
			figure.setFont(this.getGraphics(figure).getFont());
		}
	}

	public void updateHeader(ObjectModel objModl) {
		de.paragon.explorer.model.ObjectHeaderModel headModl = objModl.getHeaderModel();
		TextBoxFigure figure = (TextBoxFigure) headModl.getFigure();
		figure.setText(headModl.getTitle());
		figure.setFont(this.getGraphics(figure).getFont());
		figure.setVisible();
		figure.setBackground(objModl.getColorManager().getBackground(figure));
		figure.setForeground(objModl.getColorManager().getForeground(figure));
		java.awt.Rectangle box = this.computeDisplayBox(figure);
		figure.getDisplayBox().getRectangle().setBounds(box);
	}
}
