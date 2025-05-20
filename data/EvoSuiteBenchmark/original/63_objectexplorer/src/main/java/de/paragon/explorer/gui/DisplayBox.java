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

// E.Gamma stellt Beispielcode vor, derFigure.displayBox() einen Rueckgabewert
// gibt,
// der die Methode inside(int,int) versteht.
// Eine Instanz- (eventuell, allerdings unwahrscheinlich, auch Klassen-)
// Variable
// fDisplayBox von RectangleFig versteht die Methode translate(int,int).
// C.L. interpretiert letzteres dahingehend, dass es einen Typ DisplayBox geben
// muss,
// der inside() und translate() versteht. inside() gibt definitiv einen Wert vom
// Typ boolean zurueck. cri ersetzt deshalb inside() durch isInside().
//
// Eine DisplayBox ist ein bestimmter Bildschirmbereich, den eine Figur kennt.
// Der DisplayBox ist das Zeichnen egal, sie interessiert sich nur fuer
// die geometrischen Verhaeltnisse.
//
// Sie verfuegt ueber folgende Faehigkeiten:
// - sie kann sich verschieben
// - sie kann angeben, ob sie einen bestimmten Punkt enthaelt
//
// Figuren muessen diese Faehigkeiten auch haben. Da aber jede Figur ihre
// DisplayBox kennt, kann sie diese Aufgaben an diese delegieren.
// 
// Da DisplayBox von Figure zurueckgegeben wird ist sie dem Framework
// bekannt und sollte aehnlich stabil sein, also auf Implementation verzichten.
//
import java.awt.Rectangle;

/**
 * Eine DisplayBox ist ein bestimmter Bildschirmbereich, den eine Figur kennt.
 * Der DisplayBox ist das Zeichnen egal, sie interessiert sich nur fuer die
 * geometrischen Verhaeltnisse.
 * 
 * Sie verfuegt ueber folgende Faehigkeiten: - sie kann sich verschieben - sie
 * kann angeben, ob sie einen bestimmten Punkt enthaelt
 * 
 * Figuren muessen diese Faehigkeiten auch haben. Da aber jede Figur ihre
 * DisplayBox kennt, kann sie diese Aufgaben an diese delegieren.
 * 
 * Da DisplayBox von Figure zurueckgegeben wird ist sie dem Framework bekannt
 * und sollte aehnlich stabil sein, also auf Implementation verzichten.
 * 
 */
public interface DisplayBox extends Cloneable {
	public abstract Object clone();

	// C.L.
	public abstract Rectangle getRectangle();

	// Methode boolean inside(int,int) fuer eine Klasse des Rueckgabetyps
	// von Figure.displayBox() von E.Gamma vorgeschlagen
	public abstract boolean isInside(int x, int y);

	// Methode translate(int,int) fuer eine Klasse, deren Referenz durch
	// eine Variable namens fDisplayBox gehalten wird,
	// von E.Gamma vorgeschlagen
	public abstract void translate(int x, int y);
}
