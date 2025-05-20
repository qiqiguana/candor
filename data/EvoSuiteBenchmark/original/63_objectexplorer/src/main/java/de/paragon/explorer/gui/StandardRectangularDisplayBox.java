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

// C.L.
import java.awt.Rectangle;

/**
 * Kommentar: implementiert RectangularDisplayBox
 */
public class StandardRectangularDisplayBox implements RectangularDisplayBox {
	private Rectangle	box;

	public StandardRectangularDisplayBox() {
		super();
		this.box = new Rectangle(0, 0, 0, 0);
	}

	public StandardRectangularDisplayBox(int x, int y, int width, int height) {
		super();
		this.box = new Rectangle(x, y, width, height);
	}

	@Override
	public Object clone() {
		try {
			super.clone();
			int x = this.getRectangle().x;
			int y = this.getRectangle().y;
			int width = this.getRectangle().width;
			int height = this.getRectangle().height;
			return new StandardRectangularDisplayBox(x, y, width, height);
		}
		catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	public Rectangle getRectangle() {
		return this.box;
	}

	public boolean isInside(int x, int y) {
		return this.getRectangle().contains(x, y);
	}

	public void translate(int dx, int dy) {
		this.getRectangle().translate(dx, dy);
	}
}
