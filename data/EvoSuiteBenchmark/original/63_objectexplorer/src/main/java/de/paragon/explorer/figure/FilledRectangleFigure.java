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
 * Ein Objekt dieser Klasse wird referenziert von: ListBoxFigure, zusammen mit
 * den TextBoxFigures.
 * 
 */
import java.awt.Graphics;

public class FilledRectangleFigure extends RectangleFigure {
	public FilledRectangleFigure() {
		super();
	}

	public FilledRectangleFigure(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	@Override
	public void draw(Graphics g) {
		int x = this.getDisplayBox().getRectangle().x;
		int y = this.getDisplayBox().getRectangle().y;
		int width = this.getDisplayBox().getRectangle().width;
		int height = this.getDisplayBox().getRectangle().height;
		g.fillRect(x, y, width, height);
	}

	@Override
	public boolean isVisible() {
		return true;
	}
}
