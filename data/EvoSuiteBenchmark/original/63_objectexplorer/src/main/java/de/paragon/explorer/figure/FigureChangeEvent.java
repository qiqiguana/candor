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

// Klasse von E. Gamma vorgeschlagen mit Methode invalidatedRectangle(),
// dieses ist allerdings ueber die DisplayBox zugaenglich
//
// Idee der weiteren Differenzierung von C.L. (analog AWT)
//
// Diese Klasse enthaelt Informationen ueber Figurenaenderungen
// - betroffene Figur
// - betroffener Umriss
// - ob Figur aktueller oder alter Umriss (via Event-ID)
//
//
// Der Inhalt eines Events wird bei der Erzeugung durch die Quelle gesetzt,
// daher wird auf set-Methoden verzichtet.
import java.util.EventObject;

import de.paragon.explorer.gui.DisplayBox;

public class FigureChangeEvent extends EventObject {
	private static final long		serialVersionUID	= -6493107030577815492L;
	private transient Figure		figure;
	private transient DisplayBox	displayBox;
	private int						id;

	// Konstruktur (ohne id) von E.Gamma vorgeschlagen
	//
	// id gibt an, welche Art von FigureChangeEvent benutzt wird:
	// entweder ist die Figur verschwunden oder aufgetaucht
	public FigureChangeEvent(Figure source, DisplayBox newDisplayBox, int newId) {
		super(source);
		this.figure = source;
		this.displayBox = newDisplayBox;
		this.id = newId;
	}

	// C.L.
	public DisplayBox getDisplayBox() {
		return this.displayBox;
	}

	// C.L.
	public Figure getFigure() {
		return this.figure;
	}

	// C.L.
	public int getId() {
		return this.id;
	}
}
