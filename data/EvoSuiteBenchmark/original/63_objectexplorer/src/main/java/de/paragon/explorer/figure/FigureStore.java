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

// C.L.
/**
 * dient dem Speichern von Figure-Objekten, liefert Inhalt als Instanz einer
 * StandardEnumeration zurueck, die verwendete spezielle Implementation heisst
 * VectorBasedFigureEnumeration,
 * 
 * FigureStore wird als Default-Speichermedium von CompositeFigure benutzt.
 * Gespeichert werden Variablen vom Typ Figure (Interface).
 */
import java.util.Vector;

import de.paragon.explorer.excp.NoSuchFigureException;
import de.paragon.explorer.excp.NotAFigureException;
import de.paragon.explorer.util.ResourceBundlePurchaser;
import de.paragon.explorer.util.StandardEnumeration;
import de.paragon.explorer.util.StandardEnumerator;

public class FigureStore {
	private static final String	NO_SUCH_FIGURE_EXC	= "figurestore.no_such_figure_exc";
	private Vector<Figure>		figures;

	public FigureStore() {
		this.figures = new Vector<Figure>();
	}

	public void add(Figure f) {
		this.figures.addElement(f);
	}

	public StandardEnumeration getFigures() throws NotAFigureException {
		return new StandardEnumerator(this.figures);
	}

	public boolean isInStore(Figure f) {
		for (int i = 0; i < this.figures.size(); i++) {
			if (f == this.figures.elementAt(i)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Entfernt alle Eintraege eines Objektes
	 */
	public void remove(Figure f) throws NoSuchFigureException {
		if (!this.isInStore(f)) {
			throw new NoSuchFigureException(ResourceBundlePurchaser.getMessage(FigureStore.NO_SUCH_FIGURE_EXC));
		}
		while (this.isInStore(f)) {
			this.figures.removeElement(f);
		}
	}
}
