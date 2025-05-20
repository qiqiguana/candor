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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class ColorRectangleFigure extends RectangleFigure {
	private java.awt.Color	background;

	public ColorRectangleFigure() {
		super();
	}

	public ColorRectangleFigure(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void draw(Graphics g) {
		Rectangle rect = this.getDisplayBox().getRectangle();
		Color currentColor = g.getColor();
		g.setColor(this.getBackground());
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
		g.setColor(currentColor);
		super.draw(g);
	}

	public Color getBackground() {
		return this.background;
	}

	public void setBackground(java.awt.Color newBackground) {
		this.background = newBackground;
	}
}
