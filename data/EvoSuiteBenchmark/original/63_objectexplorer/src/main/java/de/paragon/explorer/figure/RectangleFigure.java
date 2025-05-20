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

import java.awt.Graphics;

import de.paragon.explorer.gui.DisplayBox;
import de.paragon.explorer.gui.StandardRectangularDisplayBox;

public class RectangleFigure extends AbstractFigure {
	private DisplayBox	displayBox;

	// C.L.
	public RectangleFigure() {
		super();
		this.displayBox = new StandardRectangularDisplayBox(0, 0, 0, 0);
	}

	// C.L.
	public RectangleFigure(int x, int y, int width, int height) {
		super();
		this.displayBox = new StandardRectangularDisplayBox(x, y, width, height);
	}

	@Override
	protected void basicMoveBy(int x, int y) {
		this.getDisplayBox().translate(x, y);
	}

	// E.Gamma schlaegt dies Methode (ohne naehere Angaben zum Code) vor.
	//
	// drawRect(int x, int y, int width, int height) von java.awt.Graphics wird
	// nicht benutzt,
	// da fuer negatives width oder height nichts gezeichnet wird. Eine
	// Koordinatentransformation ist moeglich,
	// aber da drawLine(int x1, int y1, int x2, int y2) das stabilere Konzept zu
	// sein scheint,
	// werden hier vier Linien gezeichnet.
	//
	// Die -1 kommt vom Pixelzaehlen, java.awt.Rectangle verfuegt ueber keine
	// get- und set-Methoden
	// um auf die Variablen einzeln zugreifen zu koennen, dafuer sind diese
	// oeffentlich.
	// 
	@Override
	public void draw(Graphics g) {
		// int x = this.getDisplayBox().getRectangle().x;
		// int y = this.getDisplayBox().getRectangle().y;
		// int width = this.getDisplayBox().getRectangle().width;
		// int height = this.getDisplayBox().getRectangle().height;
		// g.drawLine(x,y, x+width-1,y);
		// g.drawLine(x+width-1,y, x+width-1,y+height-1);
		// g.drawLine(x+width-1,y+height-1, x,y+height-1);
		// g.drawLine(x,y+height-1, x,y);
	}

	@Override
	public DisplayBox getDisplayBox() {
		return this.displayBox;
	}

	public boolean isVisible() {
		return true;
	}
}
