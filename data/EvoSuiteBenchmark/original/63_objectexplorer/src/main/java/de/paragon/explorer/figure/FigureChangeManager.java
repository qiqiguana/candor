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

import java.util.Vector;

import org.apache.log4j.Logger;

import de.paragon.explorer.excp.FigureEventException;
import de.paragon.explorer.util.LoggerFactory;
import de.paragon.explorer.util.ResourceBundlePurchaser;

/**
 * Kommentar: verwaltet Listener und dispatched Events
 */
public class FigureChangeManager {
	private static final String				UNKNOWN_TYPE_OF_EVENT			= "figurechangemanager.unknown_type_of_event";
	private static final String				SOME_FIGURE_EXCEPTION_CAUGHT	= "figurechangemanager.some_figure_exception_caught";
	private static final Logger				logger							= LoggerFactory.make();
	private Vector<FigureChangeListener>	listener;

	public FigureChangeManager() {
		this.listener = new Vector<FigureChangeListener>();
	}

	public void addChangeListener(FigureChangeListener l) {
		this.listener.addElement(l);
	}

	protected boolean isInStore(FigureChangeListener l) {
		for (int i = 0; i < this.listener.size(); i++) {
			if (l == this.listener.elementAt(i)) {
				return true;
			}
		}
		return false;
	}

	// Exception-Buendelung fuehrt zu weniger throws-Nachtraegen in
	// Programmierung, erschwert Debugging (nicht dramatisch)
	/** fuehrt ein Dispatching auf die richtigen Listener-Methoden durch */
	public void processEvent(FigureChangeEvent e) throws FigureEventException {
		for (int i = 0; i < this.listener.size(); i++) {
			switch (e.getId()) {
				case PackageConstants.FIGURE_INVALIDATED:
					try {
						(this.listener.elementAt(i)).figureInvalidated(e);
					}
					catch (Exception ex) {
						FigureChangeManager.logger.error(ResourceBundlePurchaser.getMessage(FigureChangeManager.SOME_FIGURE_EXCEPTION_CAUGHT), ex);
						throw new FigureEventException(ResourceBundlePurchaser.getMessage(FigureChangeManager.SOME_FIGURE_EXCEPTION_CAUGHT));
					}
					break;
				case PackageConstants.FIGURE_APPEARED:
					try {
						(this.listener.elementAt(i)).figureAppeared(e);
					}
					catch (Exception ex) {
						FigureChangeManager.logger.error(ResourceBundlePurchaser.getMessage(FigureChangeManager.SOME_FIGURE_EXCEPTION_CAUGHT), ex);
						throw new FigureEventException(ResourceBundlePurchaser.getMessage(FigureChangeManager.SOME_FIGURE_EXCEPTION_CAUGHT));
					}
					break;
				default:
					throw new FigureEventException(ResourceBundlePurchaser.getMessage(FigureChangeManager.UNKNOWN_TYPE_OF_EVENT));
			}
		}
	}

	/**
	 * Entfernt alle Eintraege eines Objektes
	 */
	public void removeChangeListener(FigureChangeListener l) {
		while (this.isInStore(l)) {
			this.listener.removeElement(l);
		}
	}
}
