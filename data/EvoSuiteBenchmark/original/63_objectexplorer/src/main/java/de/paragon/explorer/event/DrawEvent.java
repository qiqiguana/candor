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
package de.paragon.explorer.event;

import java.awt.Graphics;
import java.util.EventObject;

public class DrawEvent extends EventObject {
	private static final long	serialVersionUID	= -2665739080776594479L;
	private transient Graphics	graphics;

	/**
	 * RepaintEvent constructor comment.
	 * 
	 * @param source
	 *            Object
	 */
	public DrawEvent(Object source) {
		super(source);
	}

	/**
	 * RepaintEvent constructor comment.
	 * 
	 * @param source
	 *            Object
	 */
	public DrawEvent(Object source, Graphics newGraphics) {
		super(source);
		this.setGraphics(newGraphics);
	}

	public Graphics getGraphics() {
		return this.graphics;
	}

	private void setGraphics(Graphics newGraphics) {
		this.graphics = newGraphics;
	}
}
