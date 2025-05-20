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

// Interface von E. Gamma vorgeschlagen (mit FigureInvalidated)
//
//
// Event-Handling durch:
//
// - figureInvalidated() fuer verschwindende Figuren
// - figureAppeared() fuer auftauchende Figuren,
// 
import java.util.EventListener;

import de.paragon.explorer.excp.FigureException;

public interface FigureChangeListener extends EventListener {
	// C.L.
	public abstract void figureAppeared(FigureChangeEvent e) throws FigureException;

	// nach E.Gamma
	public abstract void figureInvalidated(FigureChangeEvent e) throws FigureException;
}
