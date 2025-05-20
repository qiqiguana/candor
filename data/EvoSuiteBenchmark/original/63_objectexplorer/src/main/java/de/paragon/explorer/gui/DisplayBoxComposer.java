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
package de.paragon.explorer.gui;

import org.apache.log4j.Logger;

import de.paragon.explorer.excp.FigureException;
import de.paragon.explorer.figure.CompositeFigure;
import de.paragon.explorer.figure.Figure;
import de.paragon.explorer.util.LoggerFactory;
import de.paragon.explorer.util.ResourceBundlePurchaser;
import de.paragon.explorer.util.StandardEnumeration;

// C.L.
/**
 * Goal: calculate DisplayBox of a Composed Figure Important Methods: compose:
 * returns enclosing Rectangle of Figure-Parts as DisplayBox Possible Extension:
 * flexible Composition Strategy
 */
public class DisplayBoxComposer {
	private static final Logger	logger					= LoggerFactory.make();
	private static final String	ERROR_IN_THIS_OBJECT	= "displayboxcomposer.error_in_this_object";

	public DisplayBoxComposer() {
		super();
	}

	protected int calcHigh(Figure figure) throws FigureException {
		int y1 = figure.getDisplayBox().getRectangle().y;
		int y2 = y1 + figure.getDisplayBox().getRectangle().height;
		return Math.max(y1, y2);
	}

	protected int calcLeft(Figure figure) throws FigureException {
		int x1 = figure.getDisplayBox().getRectangle().x;
		int x2 = x1 + figure.getDisplayBox().getRectangle().width;
		return Math.min(x1, x2);
	}

	protected int calcLow(Figure figure) throws FigureException {
		int y1 = figure.getDisplayBox().getRectangle().y;
		int y2 = y1 + figure.getDisplayBox().getRectangle().height;
		return Math.min(y1, y2);
	}

	protected int calcRight(Figure figure) throws FigureException {
		int x1 = figure.getDisplayBox().getRectangle().x;
		int x2 = x1 + figure.getDisplayBox().getRectangle().width;
		return Math.max(x1, x2);
	}

	/**
	 * Comment: new DisplayBox = enclosing Rectangle of Parts no parts =>
	 * NullDisplayBox
	 * 
	 * @return DisplayBox
	 */
	public DisplayBox compose(CompositeFigure client) {
		StandardEnumeration figures = null;
		Figure currentFigure;
		try {
			figures = client.getFigures();
		}
		catch (FigureException e) {
			return this.getNewNullDisplayBox();
		}
		if (figures == null) {
			return this.getNewNullDisplayBox();
		}
		int leftBorder = 0;
		int rightBorder = 0;
		int lowBorder = 0;
		int highBorder = 0;
		if (!figures.hasMoreElements()) {
			return this.getNewNullDisplayBox();
		}
		try {
			currentFigure = (Figure) figures.nextElement();
			leftBorder = this.calcLeft(currentFigure);
			rightBorder = this.calcRight(currentFigure);
			lowBorder = this.calcLow(currentFigure);
			highBorder = this.calcHigh(currentFigure);
			while (figures.hasMoreElements()) {
				currentFigure = (Figure) figures.nextElement();
				leftBorder = Math.min(leftBorder, this.calcLeft(currentFigure));
				rightBorder = Math.max(rightBorder, this.calcRight(currentFigure));
				lowBorder = Math.min(lowBorder, this.calcLow(currentFigure));
				highBorder = Math.max(highBorder, this.calcHigh(currentFigure));
			}
		}
		catch (FigureException ex) {
			DisplayBoxComposer.logger.error(ResourceBundlePurchaser.getMessage(DisplayBoxComposer.ERROR_IN_THIS_OBJECT), ex);
			Warning.showWarning(ResourceBundlePurchaser.getMessage(DisplayBoxComposer.ERROR_IN_THIS_OBJECT));
		}
		int x = leftBorder;
		int y = lowBorder;
		int width = rightBorder - x;
		int height = highBorder - y;
		return new StandardRectangularDisplayBox(x, y, width, height);
	}

	private DisplayBox getNewNullDisplayBox() {
		return new NullDisplayBox();
	}
}
