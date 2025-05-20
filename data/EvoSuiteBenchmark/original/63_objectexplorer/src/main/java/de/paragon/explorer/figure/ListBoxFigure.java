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

/**
 * Klassenbeschreibung:
 * 
 * Eine ListBoxFigure zeichnet sich dadurch aus, dass sie beim Zeichnen 3D-Optik
 * annimmt, das heisst, sie erhaelt um ihre DisplayBox oben und links je zwei
 * weisse Linien sowie rechts und unten je eine dunkelgraue und eine schwarze
 * Linie.
 * 
 * Instanzvariablen: model: Das ObjectModel, das fuer den Inhalt dieser
 * ListBoxFigure (u. a. das Objekt) verantwortlich ist.
 * 
 */
import java.awt.Graphics;
import java.awt.Point;

import org.apache.log4j.Logger;

import de.paragon.explorer.gui.Warning;
import de.paragon.explorer.model.Model;
import de.paragon.explorer.util.LoggerFactory;
import de.paragon.explorer.util.ResourceBundlePurchaser;
import de.paragon.explorer.util.StandardEnumeration;

/* new implemented by CHE: */
public class ListBoxFigure extends StandardCompositeFigure {
	private static final Logger	logger				= LoggerFactory.make();
	private static final String	ERROR_WHILE_ASKING	= "listboxfigure.error_while_asking";
	private static final String	ERROR_WHILE_DRAWING	= "listboxfigure.error_while_drawing";
	private Model				model;

	public ListBoxFigure() {
		super();
	}

	@Override
	public void draw(Graphics g) {
		java.awt.Rectangle box = null;
		int startX, startY, endX, endY;
		try {
			box = this.getBounds();
			super.draw(g);
		}
		catch (Exception ex) {
			ListBoxFigure.logger.error(ResourceBundlePurchaser.getMessage(ListBoxFigure.ERROR_WHILE_DRAWING), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ListBoxFigure.ERROR_WHILE_DRAWING));
		}
		// vier fast weisse Linien, je zwei oben und links, zeichnen
		// g.setColor(new Color(240, 240, 240));
		g.setColor(java.awt.Color.black);
		// zunaechst die beiden inneren
		// oben
		// startX = box.x;
		// startY = box.y;
		// endX = box.x + box.width - 1;
		// endY = box.y;
		// g.drawLine(startX, startY, endX, endY);
		// links
		// startX = box.x;
		// startY = box.y;
		// endX = box.x;
		// endY = box.y + box.height - 1;
		// g.drawLine(startX, startY, endX, endY);
		// jetzt die aeusseren
		// oben
		startX = box.x - 1;
		startY = box.y - 1;
		endX = box.x + box.width;
		endY = box.y - 1;
		g.drawLine(startX, startY, endX, endY);
		// links
		startX = box.x - 1;
		startY = box.y - 1;
		endX = box.x - 1;
		endY = box.y + box.height;
		g.drawLine(startX, startY, endX, endY);
		// Je eine graue Linie unten und rechts zeichnen.
		// g.setColor(Color.black);
		// unten
		// startX = box.x;
		// startY = box.y + box.height - 1;
		// endX = box.x + box.width - 1;
		// endY = box.y + box.height - 1;
		// g.drawLine(startX, startY, endX, endY);
		// rechts
		// startX = box.x + box.width - 1;
		// startY = box.y;
		// endX = box.x + box.width - 1;
		// endY = box.y + box.height - 1;
		// g.drawLine(startX, startY, endX, endY);
		// Je eine schwarze Linie unter bzw. rechts von den dunkelgrauen Linien
		// zeichnen.
		// g.setColor(Color.black);
		// unten
		startX = box.x - 1;
		startY = box.y + box.height;
		endX = box.x + box.width;
		endY = box.y + box.height;
		g.drawLine(startX, startY, endX, endY);
		// rechts
		startX = box.x + box.width;
		startY = box.y - 1;
		endX = box.x + box.width;
		endY = box.y + box.height;
		g.drawLine(startX, startY, endX, endY);
	}

	/* new implemented by CHE: */
	public Model getModel() {
		return this.model;
	}

	/**
	 * Kommentar: Diese Methode geht alle TeilFiguren durch und ueberprueft, ob
	 * eine Teilfigur sichtbar ist und den Punkt enthaelt. Da das Ganze so
	 * programmiert ist, dass eine als getroffen vermerkte Figur von einer
	 * weiteren, als getroffen registrierte Figur ueberschrieben werden kann,
	 * kann hier noch ein ueberpruefender Mechanismus eingefuegt werden, da dies
	 * ja eigentlich garnicht moeglich ist.
	 * 
	 * @return Figure
	 */
	public Figure getTextBoxFigureForPoint(Point pt) {
		Figure hitFig = null, tempFig;
		try {
			StandardEnumeration enumeration = this.getFigures();
			while (enumeration.hasMoreElements()) {
				tempFig = (Figure) enumeration.nextElement();
				if (tempFig instanceof TextBoxFigure) {
					if (((TextBoxFigure) tempFig).isVisible()) {
						if (tempFig.containsPoint(pt.x, pt.y)) {
							hitFig = tempFig;
						}
					}
				}
			}
		}
		catch (Exception ex) {
			ListBoxFigure.logger.error(ResourceBundlePurchaser.getMessage(ListBoxFigure.ERROR_WHILE_ASKING), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(ListBoxFigure.ERROR_WHILE_ASKING));
		}
		return hitFig;
	}

	/* new implemented by CHE: */
	public void setModel(Model newModel) {
		this.model = newModel;
	}
}
