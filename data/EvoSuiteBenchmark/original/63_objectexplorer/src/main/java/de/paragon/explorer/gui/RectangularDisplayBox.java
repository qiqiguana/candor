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
//
/**
 * Diese Klasse dient zunaechst nur der Typisierung, Sie zeigt an, dass die
 * zurueckgegebene Box mit der durch die DisplayBox beschriebene Menge von
 * Bildschirmpunkten identisch ist.
 */
public interface RectangularDisplayBox extends DisplayBox {
	// @Override --> in JDK 1.5 noch nicht definiert für ein Interface
	public abstract Object clone();
}
