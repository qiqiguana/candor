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

import de.paragon.explorer.excp.FigureEventException;
import de.paragon.explorer.excp.FigureException;
import de.paragon.explorer.gui.DisplayBox;
import de.paragon.explorer.gui.DisplayBoxComposer;
import de.paragon.explorer.gui.NullDisplayBox;
import de.paragon.explorer.util.StandardEnumeration;

//
// Layout: add- und remove-Operationen aendern Layout nicht: Positionen sind und
// bleiben in
// der Verantwortlichkeit der Teilfiguren(DisplayBox). Dies ist analog zum
// NullLayout von AWT.
/**
 * Klassenbeschreibung: Die neue DisplayBox wird als kleinstes alle DisplayBoxes
 * der Teilfiguren einschliessendes Rechteck bestimmt. Sie wird bei Bedarf
 * (sprich getDisplayBox()-Anfragen) berechnet, sofern sie als moeglicherweise
 * ungueltig markiert ist (flag: displayBoxIsValid).
 */
public class StandardCompositeFigure extends CompositeFigure {
	private DisplayBox			displayBox;
	private boolean				displayBoxIsValid;
	private DisplayBoxComposer	displayBoxComposer;

	protected StandardCompositeFigure() {
		super();
		this.setDisplayBox(new NullDisplayBox());
		this.setDisplayBoxIsValid(true);
		this.displayBoxComposer = new DisplayBoxComposer();
	}

	// kein Layout beim hinzufuegen (d.h. Hinzugefuegte Figur wird nicht neu
	// positioniert)
	@Override
	public void add(Figure figure) throws FigureEventException {
		this.setDisplayBoxIsValid(false);
		super.add(figure);
	}

	@Override
	public void addAll(StandardEnumeration f) throws FigureException {
		this.setDisplayBoxIsValid(false);
		super.addAll(f);
	}

	@Override
	public void basicMoveBy(int x, int y) throws FigureException {
		super.basicMoveBy(x, y);
		this.recalculateDisplayBox();
	}

	@Override
	public DisplayBox getDisplayBox() {
		if (!this.displayBoxIsValid) {
			this.recalculateDisplayBox();
		}
		return this.displayBox;
	}

	protected void recalculateDisplayBox() {
		this.displayBox = this.displayBoxComposer.compose(this);
		this.setDisplayBoxIsValid(true);
	}

	@Override
	public void remove(Figure figure) throws FigureException {
		this.setDisplayBoxIsValid(false);
		super.remove(figure);
	}

	@Override
	public void removeAll() throws FigureException {
		this.setDisplayBoxIsValid(false);
		super.removeAll();
		this.setDisplayBox(new NullDisplayBox());
		this.setDisplayBoxIsValid(true);
	}

	public void setDisplayBox(DisplayBox db) {
		this.displayBox = db;
	}

	protected void setDisplayBoxIsValid(boolean isValid) {
		this.displayBoxIsValid = isValid;
	}

	@Override
	public void setFigures(StandardEnumeration f) throws FigureException {
		this.setDisplayBoxIsValid(false);
		super.setFigures(f);
	}
}
