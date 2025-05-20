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

// C.L. (was Einsatz an dieser Stelle angeht,
// Null Object ist DesignPattern, als Patlet von E.Gamma im Vortrag
// genannt)
//
import java.awt.Rectangle;

public class NullDisplayBox implements DisplayBox {
	public NullDisplayBox() {
		super();
	}

	@Override
	public Object clone() {
		try {
			super.clone();
			return new NullDisplayBox();
		}
		catch (CloneNotSupportedException ex) {
			throw new InternalError();
		}
	}

	public Rectangle getRectangle() {
		return new Rectangle(0, 0, 0, 0);
	}

	public boolean isInside(int x, int y) {
		return false;
	}

	public void translate(int x, int y) {
	}
}
