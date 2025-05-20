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

import de.paragon.explorer.excp.FigureException;
import de.paragon.explorer.gui.DisplayBox;

public class InformationFigure extends AbstractFigure {
	/**
	 * InformationFigure constructor comment.
	 */
	protected InformationFigure() {
		super();
	}

	/**
	 * basicMoveBy method comment.
	 */
	@Override
	protected void basicMoveBy(int x, int y) throws FigureException {
	}

	/**
	 * draw method comment.
	 */
	@Override
	public void draw(Graphics g) throws FigureException {
	}

	/**
	 * getDisplayBox method comment.
	 */
	@Override
	public DisplayBox getDisplayBox() {
		return null;
	}
}
