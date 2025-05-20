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

import java.awt.Event;

/**
 * Das RefreshEvent dient lediglich dazu, vom StandardDrawingFrame an den
 * DrawingFrameEventConverter weiterzureichen, dass die Zeichnung erneuert
 * werden muss. Zum Zeitpunkt der Erstimplementation war dies der Fall, wenn:
 * 
 * -"paint" aufgerufen wurde, also, wenn sich ein anderes Fenster so vor dem
 * StandardDrawingFrame schiebt, dass ein Teil des Fensters wieder sichtbar
 * wird,
 * 
 * -das WindowEvent "window activated" aufgerufen wird, also wieder angeklickt
 * wurde,
 * 
 * -das ComponentEvent "moved" aufgerufen wird, sich also das Fenster verschoben
 * hat, oder
 * 
 * -das ComponentEvent "resized" aufgerufen wird, das Fenster also in seiner
 * Groesse veraendert wurde.
 * 
 * Das StandardDrawingFrame traegt sich selbst als ComponentListener und als
 * WindowListener ein (das WindowEvent "closed" muss sowieso verwendet werden),
 * und erzeugt in den obigen Methoden ein RefreshEvent und wendet die Methode
 * processEvent(RefreshEvent e) an.
 */
public class RefreshEvent extends java.awt.AWTEvent {
	private static final long	serialVersionUID	= -4810775091387525011L;
	public static final int		REFRESH_MOVED		= 1601;
	public static final int		REFRESH_RESIZED		= 1602;
	public static final int		REFRESH_ACTIVATED	= 1603;
	public static final int		REFRESH_PAINT		= 1604;

	/**
	 * RefreshEvent constructor comment.
	 * 
	 * @param event
	 *            java.awt.Event
	 */
	public RefreshEvent(Event event) {
		super(event);
	}

	/**
	 * RefreshEvent constructor comment.
	 * 
	 * @param source
	 *            Object
	 * @param id
	 *            int
	 */
	public RefreshEvent(Object source, int id) {
		super(source, id);
	}
}
