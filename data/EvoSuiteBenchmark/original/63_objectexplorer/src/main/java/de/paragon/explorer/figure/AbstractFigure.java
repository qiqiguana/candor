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

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import de.paragon.explorer.excp.FigureEventException;
import de.paragon.explorer.excp.FigureException;
import de.paragon.explorer.gui.DisplayBox;
import de.paragon.explorer.util.ResourceBundlePurchaser;
import de.paragon.explorer.util.StandardEnumeration;

/**
 * Kommentar: Diese Klasse implementiert Figure und gibt Default-Implementation
 * fuer die grafischen Basisklassen vor Beschreibung der Kasse:
 * 
 * Die Instanzvariablen: parent: normale Hierarchieverknuepfung, die Figur, von
 * der diese Figur Teilfigur ist.
 */
public abstract class AbstractFigure implements Figure {
	private static final String	ERROR_WHILE_DISPATCHING_APPEARED								= "abstractfigure.error_while_dispatching_appeared";
	private static final String	ERROR_WHILE_DISPATCHING_INVALIDATED								= "abstractfigure.error_while_dispatching_invalidated";
	private static final String	LISTENER_DOESNT_KNOW2HANDLE_FIGURE_CHANGE_EVENT					= "abstractfigure.listener_doesnt_know2handle_figure_change_event";
	private static final String	TRYING2ADD_A_FIGURE2A_BASICFIGURE								= "abstractfigure.trying2add_a_figure2a_basicfigure";
	private static final String	TRYING2ADD_PARTS2A_NON_COMPOSITE_FIGURE							= "abstractfigure.trying2add_parts2a_non_composite_figure";
	private static final String	TRYING2CHECK_WHETER_A_FIGURE_IS_PART_OF_A_NON_COMPOSITE_FIGURE	= "abstractfigure.trying2check_wheter_a_figure_is_part_of_a_non_composite_figure";
	private static final String	TRYING2GET_PARTS_OF_A_BASIC_FIGURE								= "abstractfigure.trying2get_parts_of_a_basic_figure";
	private static final String	TRYING2REMOVE_ALL_FROM_A_BASIC_FIGURE							= "abstractfigure.trying2remove_all_from_a_basic_figure";
	private static final String	TRYING2REMOVE_FROM_A_BASIC_FIGURE								= "abstractfigure.trying2remove_from_a_basic_figure";
	private static final String	TRYING2SET_PARTS_OF_A_NON_COMPOSITE_FIGURE						= "abstractfigure.trying2set_parts_of_a_non_composite_figure";
	private FigureChangeManager	changeManager;
	private Figure				parent;

	// gemeinsames Eventhandling von E.Gamma implizit angesprochen
	// durch Implementationsbeispiel in Figure
	//
	// changeManager zur Kapselung der Listenerverwaltung ist
	// hier Idee von C.L., im Observer Pattern aber auch angesprochen
	//
	// fuer Defaultverhalten des Eventhandlings
	protected AbstractFigure() {
		this.changeManager = new FigureChangeManager();
	}

	// Methode folgt aus dem DesignPattern Composite (Verwendung ist Vorschlag
	// von E.Gamma). Exception werfen ist Implementationsempfehlung des
	// DesignPatterns.
	public void add(Figure f) throws FigureException {
		throw new FigureException(ResourceBundlePurchaser.getMessage(AbstractFigure.TRYING2ADD_A_FIGURE2A_BASICFIGURE));
	}

	// Methode folgt aus dem DesignPattern Composite (Verwendung ist Vorschlag
	// von E.Gamma). Exception werfen ist Implementationsempfehlung des
	// DesignPatterns.
	public void addAll(StandardEnumeration figures) throws FigureException {
		throw new FigureException(ResourceBundlePurchaser.getMessage(AbstractFigure.TRYING2ADD_PARTS2A_NON_COMPOSITE_FIGURE));
	}

	// MethodenIdee: E.Gamma, Delegation: C.L.
	public void addChangeListener(FigureChangeListener l) {
		this.getChangeManager().addChangeListener(l);
	}

	// Vorschlag von E.Gamma
	protected abstract void basicMoveBy(int x, int y) throws FigureException;

	// Methode folgt aus dem DesignPattern Composite (Verwendung ist Vorschlag
	// von E.Gamma). Exception werfen ist Implementationsempfehlung des
	// DesignPatterns.
	public boolean containsFigure(Figure f) throws FigureException {
		throw new FigureException(ResourceBundlePurchaser.getMessage(AbstractFigure.TRYING2CHECK_WHETER_A_FIGURE_IS_PART_OF_A_NON_COMPOSITE_FIGURE));
	}

	// identisch zum Beispielcode von E.Gamma
	// (bis auf displayBox->getDisplayBox() und Exceptions)
	//
	// Die Frage, ob eine Figur einen Punkt enthaelt oder nicht,
	// ist durch die dieser bekannten DisplayBox zu beantworten
	// und kann folglich an sie delegiert werden.
	public boolean containsPoint(int x, int y) {
		return this.getDisplayBox().isInside(x, y);
	}

	// Wiederholung der Nennung: 80% C.L. (in Analogie zu getDisplayBox())
	public abstract void draw(Graphics g) throws FigureException;

	// Ausdifferenzierung nach erscheinenden und verschwindenden Figuren: C.L.
	public void figureAppeared(FigureChangeEvent e) throws FigureException {
		throw new FigureEventException(ResourceBundlePurchaser.getMessage(AbstractFigure.LISTENER_DOESNT_KNOW2HANDLE_FIGURE_CHANGE_EVENT));
	}

	// Vorschlag von E.Gamma
	public void figureInvalidated(FigureChangeEvent e) throws FigureException {
		throw new FigureEventException(ResourceBundlePurchaser.getMessage(AbstractFigure.LISTENER_DOESNT_KNOW2HANDLE_FIGURE_CHANGE_EVENT));
	}

	public Rectangle getBounds() throws FigureException {
		return this.getDisplayBox().getRectangle();
	}

	// C.L:
	protected FigureChangeManager getChangeManager() {
		return this.changeManager;
	}

	// von Figure, E.Gamma nennt in Figure (Folie 11) und AbstractFigure (Folie
	// 13) jeweils die abstrakte Methode displayBox();
	// Folie 11 deutet allerdings auf eine Defaultimplementation von
	// displayBox() hin, die basicDisplayBox() aufruft (vermutlich abstrakt).
	// Dies macht eigentlich nur fuer einen echten Zeichenprozess Sinn (im
	// Widerspruch zum Beispielcode von containsPoint()).
	// C.L. realisiert die Version von Folie 13.
	//
	// Implementation durch konkrete Subklassen
	public abstract DisplayBox getDisplayBox();

	// Methode folgt aus dem DesignPattern Composite (Verwendung ist Vorschlag
	// von E.Gamma). Exception werfen ist Implementationsempfehlung des
	// DesignPatterns.
	public StandardEnumeration getFigures() throws FigureException {
		throw new FigureException(ResourceBundlePurchaser.getMessage(AbstractFigure.TRYING2GET_PARTS_OF_A_BASIC_FIGURE));
	}

	public Figure getParent() {
		return this.parent;
	}

	public Dimension getSize() throws FigureException {
		Dimension d = new Dimension();
		Rectangle r = this.getBounds();
		d.width = r.width;
		d.height = r.height;
		return d;
	}

	public void hasChanged() throws FigureEventException {
		try {
			this.getChangeManager().processEvent(new FigureChangeEvent(this, this.getDisplayBox(), PackageConstants.FIGURE_APPEARED));
		}
		catch (Exception ex) {
			throw new FigureEventException(ResourceBundlePurchaser.getMessage(AbstractFigure.ERROR_WHILE_DISPATCHING_APPEARED));
		}
	}

	// identisch zum Beispielcode von E.Gamma
	// (bis auf changed()->hasChanged(), dx -> x, dy -> y und Exceptions)
	// 
	// erlaubt es einen gemeinsamen Notification-Mechanismus vorzuimplementieren
	// (durch willChange() und changed()), delegiert eigentliche Bewegung
	// an Unterklasse
	public void moveBy(int x, int y) throws FigureException {
		this.willChange();
		this.basicMoveBy(x, y);
		this.hasChanged();
	}

	// Methode folgt aus dem DesignPattern Composite (Verwendung ist Vorschlag
	// von E.Gamma). Exception werfen ist Implementationsempfehlung des
	// DesignPatterns.
	public void remove(Figure f) throws FigureException {
		throw new FigureException(ResourceBundlePurchaser.getMessage(AbstractFigure.TRYING2REMOVE_FROM_A_BASIC_FIGURE));
	}

	// Methode folgt aus dem DesignPattern Composite (Verwendung ist Vorschlag
	// von E.Gamma). Exception werfen ist Implementationsempfehlung des
	// DesignPatterns.
	public void removeAll() throws FigureException {
		throw new FigureException(ResourceBundlePurchaser.getMessage(AbstractFigure.TRYING2REMOVE_ALL_FROM_A_BASIC_FIGURE));
	}

	// MethodenIdee E.Gamma, Delegation: C.L.
	public void removeChangeListener(FigureChangeListener l) {
		this.getChangeManager().removeChangeListener(l);
	}

	public void setBounds(int x, int y, int width, int height) throws FigureException {
		Rectangle r = new Rectangle(x, y, width, height);
		this.setBounds(r);
	}

	public void setBounds(Rectangle r) throws FigureException {
		this.willChange();
		Rectangle db = this.getDisplayBox().getRectangle();
		db.x = r.x;
		db.y = r.y;
		db.width = r.width;
		db.height = r.height;
		this.hasChanged();
	}

	// Methode folgt aus dem DesignPattern Composite (Verwendung ist Vorschlag
	// von E.Gamma). Exception werfen ist Implementationsempfehlung des
	// DesignPatterns.
	public void setFigures(StandardEnumeration figures) throws FigureException {
		throw new FigureException(ResourceBundlePurchaser.getMessage(AbstractFigure.TRYING2SET_PARTS_OF_A_NON_COMPOSITE_FIGURE));
	}

	public void setParent(Figure newParent) {
		this.parent = newParent;
	}

	public void setSize(Dimension d) throws FigureException {
		this.willChange();
		Rectangle db = this.getDisplayBox().getRectangle();
		db.width = d.width;
		db.height = d.height;
		this.hasChanged();
	}

	// Vorschlag von E.Gamma
	//
	//
	public void setSize(int width, int height) throws FigureException {
		Dimension d = new Dimension(width, height);
		this.setSize(d);
	}

	public void willChange() throws FigureEventException {
		try {
			this.getChangeManager().processEvent(new FigureChangeEvent(this, (DisplayBox) this.getDisplayBox().clone(), PackageConstants.FIGURE_INVALIDATED));
		}
		catch (Exception ex) {
			throw new FigureEventException(ResourceBundlePurchaser.getMessage(AbstractFigure.ERROR_WHILE_DISPATCHING_INVALIDATED));
		}
	}
}
