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

import de.paragon.explorer.excp.FigureEventException;
import de.paragon.explorer.excp.FigureException;
import de.paragon.explorer.gui.DisplayBox;
import de.paragon.explorer.util.ResourceBundlePurchaser;
import de.paragon.explorer.util.StandardEnumeration;

/**
 * Kommentar: CompositeFigure ist die abstrakte Basisklasse aller
 * zusammengesetzten Figuren. Sie enthaelt Default-Implementationen von
 * Figurenmanagement-Funktionen, sowie von draw() und moveBy().
 * 
 * Die Verwaltung der Figuren erfolgt in einem Figurestore, das Ein- und
 * Austragen als FigureChangeListener bei den Figuren erfolgt in dieser Klasse.
 * Im figureStore werden die Teilfiguren als Typ Figure (Interface) gehalten.
 */
public abstract class CompositeFigure extends AbstractFigure {
	private static final String	SETTING_BOUNDS	= "compositefigure.setting_bounds";
	private FigureStore			figureStore;

	// C.L.
	protected CompositeFigure() {
		this.figureStore = new FigureStore();
	}

	// E.Gamma: konkrete Implementation soll erfolgen
	// C.L.: und zwar durch Delegation an FigureStore
	// ausserdem traegt sich die CompositeFigure bei Figure als
	// FigureChangeListener ein, damit durch die Figure ausgeloeste
	// Events (evtl. ueber eine Kette ineinandergeschachtelter
	// CompositeFigure-Objekte) beiDrawingView landen.
	@Override
	public void add(Figure figure) throws FigureEventException {
		this.figureStore.add(figure);
		// figure.addChangeListener(this);
		figure.setParent(this);
		// this.hasChanged();
		/*
		 * Kommentar von CHE: Soweit ich das ermittelt habe, fuehrt hasChanged
		 * zu keiner Aktion
		 */
	}

	// E.Gamma: konkrete Implementation soll erfolgen
	// C.L.: benutzt this.add()
	@Override
	public void addAll(StandardEnumeration f) throws FigureException {
		while (f.hasMoreElements()) {
			this.add((Figure) f.nextElement());
		}
	}

	// 80% C.L.; analoge Implementation zu draw()
	@Override
	public void basicMoveBy(int x, int y) throws FigureException {
		de.paragon.explorer.util.StandardEnumeration f = this.getFigures();
		while (f.hasMoreElements()) {
			((Figure) f.nextElement()).moveBy(x, y);
		}
	}

	// 90% C.L.
	// E.Gamma fordert findFigure fuer das Interface Drawing
	@Override
	public boolean containsFigure(Figure figure) {
		return this.figureStore.isInStore(figure);
	}

	// identisch zum Beispielcode von E.Gamma
	// (bis auf figure->getFigures() und Exceptions)
	@Override
	public void draw(Graphics g) throws FigureException {
		de.paragon.explorer.util.StandardEnumeration f = this.getFigures();
		while (f.hasMoreElements()) {
			((Figure) (f.nextElement())).draw(g);
		}
	}

	// Differenzierung: C.L.
	// Idee des Mechnismus: E.Gamma (vgl. figureInvalidated)
	//
	// gibt Event an die eigenen Listener weiter
	// belaesst Source beim Child
	@Override
	public void figureAppeared(FigureChangeEvent e) throws FigureEventException {
		this.getChangeManager().processEvent(e);
	}

	// E.Gamma: die Teilfiguren (Child-Objekte) reichen Events an die
	// entsprechende CompositeFigure weiter
	// bis die CompositeFigure, die die Drawing ist, die DrawingView haelt,
	// diese benachrichtigt.
	//
	// leaf->CompositeFigure->CompositeFigure->...->CompositeFigure(=StandardDrawingView.drawing)->DrawingView
	//
	// diese Methode benachrichtigt die Listener einer CompositeFigure duch
	// Weiterreichen des
	// von einem Child empfangenen Events.
	//
	@Override
	public void figureInvalidated(FigureChangeEvent e) throws FigureEventException {
		this.getChangeManager().processEvent(e);
	}

	// E.Gamma schlaegt vor, dass CompositeFigure kein Layout definiert,
	// getDisplayBox() (E.Gamma DisplayBox()) also nicht implementiert wird.
	@Override
	public abstract DisplayBox getDisplayBox();

	// E.Gamma: konkrete Implementation soll erfolgen (von figures())
	// C.L.: und zwar durch Delegation an FigureStore
	@Override
	public StandardEnumeration getFigures() throws FigureException {
		return this.figureStore.getFigures();
	}

	// E.Gamma: konkrete Implementation soll erfolgen
	// C.L.: und zwar durch Delegation an FigureStore
	@Override
	public void remove(Figure figure) throws FigureException {
		this.willChange();
		figure.removeChangeListener(this);
		figure.setParent(null);
		this.figureStore.remove(figure);
	}

	// C.L.
	//
	// holt sich zunaechst die Figuren, um sich als Listener austragen zu
	// koennen
	@Override
	public void removeAll() throws FigureException {
		de.paragon.explorer.util.StandardEnumeration f = this.figureStore.getFigures();
		Figure current = null;
		while (f.hasMoreElements()) {
			current = (Figure) f.nextElement();
			this.remove(current);
		}
		this.figureStore = new FigureStore();
	}

	@Override
	public void setBounds(java.awt.Rectangle r) throws FigureException {
		throw new FigureException(ResourceBundlePurchaser.getMessage(CompositeFigure.SETTING_BOUNDS));
	}

	// C.L.
	@Override
	public void setFigures(StandardEnumeration figures) throws FigureException {
		this.removeAll();
		this.addAll(figures);
	}
}
