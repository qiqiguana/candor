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

/**
 * Figure typisiert Figuren und verlangt ihnen gewisse Faehigkeiten ab, - sich
 * zeichnen koennen - ihre DisplayBox zurueckgeben zu koennen (=> sie auch zu
 * kennen) - sich verschieben zu koennen - feststellen koennen, ob sie einen
 * bestimmten Punkt enthalten (wichtig z.B. fuer MouseEvent-Handling)
 * 
 * Dieses Interface dient der Stabilitaet der Schnittstelle zu einem Framework
 * Die Default-Implementation ist an die abstrakte Klasse AbstractFigure
 * delegiert.
 * 
 * Um dem Composite Pattern fuer Figure/AbstractFigure und CompositeFigure zu
 * folgen, enthaelt diese Schnittstelle auch alle Methoden, die der Behandlung
 * zusammengesetzter Figuren dienen. => es implementiert auch das
 * FigureChangeListener-Interface
 * 
 * Figure kennt ihren Parent (falls Teil einer CompositeFigure und sich der
 * Parent registriert hat. Figure wird referenziert von: Model - das Model, das
 * auf den Inhalt der visuellen Darstellung der Figure referenziert.
 * AbstractFigure: (in parent) Eine Figur, die Teilfigur von dieser Figur ist.
 * (normale Hie- rarchieverknuepfung) CompositeFigure: (in figureStore) zusammen
 * mit allen anderen Figuren, die Teilfigur dieser Figur sind.
 * 
 */
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;

import de.paragon.explorer.excp.FigureException;
import de.paragon.explorer.gui.DisplayBox;
import de.paragon.explorer.util.StandardEnumeration;

public interface Figure extends FigureChangeListener {
	// folgt aus Anwendung des CompositePatterns auf
	// Figure/AbstractFigure-CompositeFigure
	public abstract void add(Figure figure) throws FigureException;

	// folgt aus Anwendung des CompositePatterns auf
	// Figure/AbstractFigure-CompositeFigure
	public void addAll(StandardEnumeration figures) throws FigureException;

	// als konkrete Methode von E.Gamma vorgeschlagen
	public abstract void addChangeListener(FigureChangeListener l);

	// folgt aus Anwendung des CompositePatterns auf
	// Figure/AbstractFigure-CompositeFigure
	public abstract boolean containsFigure(Figure figure) throws FigureException;

	// direkt von E.Gamma vorgeschlagen
	public abstract boolean containsPoint(int x, int y);

	// direkt von E.Gamma vorgeschlagen (einschliesslich Argument)
	public abstract void draw(Graphics g) throws FigureException;

	public abstract void figureAppeared(FigureChangeEvent e) throws FigureException;

	// folgt aus CompositePattern, da CompositeFigure EventListener sein soll,
	// und Figure das gleiche Interface haben soll.
	public abstract void figureInvalidated(FigureChangeEvent e) throws FigureException;

	public Rectangle getBounds() throws FigureException;

	// E.Gamma schlaegt eine Methode displayBox() vor, Folie 11 verraet, dass
	// das
	// zurueckgegebene Objekt die Methode inside(int, int) versteht.
	public abstract DisplayBox getDisplayBox() throws FigureException;

	// folgt aus Anwendung des CompositePatterns auf
	// Figure/AbstractFigure-CompositeFigure
	public abstract StandardEnumeration getFigures() throws FigureException;

	public abstract Figure getParent();

	public Dimension getSize() throws FigureException;

	// konkrete Klasse changed() einschliesslich Beispielcode
	// von E.Gamma vorgeschlagen
	public abstract void hasChanged() throws FigureException;

	// direkt von E.Gamma vorgeschlagen
	public abstract void moveBy(int x, int y) throws FigureException;

	// folgt aus Anwendung des CompositePatterns auf
	// Figure/AbstractFigure-CompositeFigure
	public abstract void remove(Figure figure) throws FigureException;

	// folgt aus Anwendung des CompositePatterns auf
	// Figure/AbstractFigure-CompositeFigure
	public abstract void removeAll() throws FigureException;

	// ergaenzende Operation zur von E.Gamma vorgeschlagenen addChangeListener()
	public abstract void removeChangeListener(FigureChangeListener l);

	public void setBounds(int x, int y, int width, int height) throws FigureException;

	public void setBounds(Rectangle r) throws FigureException;

	// folgt aus Anwendung des CompositePatterns auf
	// Figure/AbstractFigure-CompositeFigure
	public abstract void setFigures(StandardEnumeration figures) throws FigureException;

	public void setParent(Figure parent);

	public void setSize(Dimension d) throws FigureException;

	// konkrete Methode von E.Gamma vorgeschlagen
	public void setSize(int width, int height) throws FigureException;

	public abstract void willChange() throws FigureException;
}
