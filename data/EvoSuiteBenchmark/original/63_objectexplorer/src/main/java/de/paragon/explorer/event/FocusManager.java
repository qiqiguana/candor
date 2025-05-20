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

import java.awt.Graphics;
import java.awt.Point;

import javax.swing.SwingUtilities;

import de.paragon.explorer.figure.ExplorerFigure;
import de.paragon.explorer.figure.TextBoxFigure;
import de.paragon.explorer.model.ExplorerModel;
import de.paragon.explorer.model.ObjectModelPart;
import de.paragon.explorer.util.ExplorerColorManager;

/**
 * Diese Klasse ist verantwortlich fuer alle Aufgaben die in Zusammenhang mit
 * dem Focus stehen.
 * 
 */
public class FocusManager {
	private TextBoxFigure	focusObject;
	private ExplorerFigure	explorerFigure;

	// private Graphics graphics;
	/**
	 * FocusManager constructor comment.
	 */
	public FocusManager() {
		super();
	}

	public FocusManager(ExplorerFigure param) {
		this.setExplorerFigure(param);
	}

	public void clearFocus() {
		this.resetFocus();
	}

	private ExplorerColorManager getColorManager() {
		return ((ExplorerModel) this.getExplorerFigure().getModel()).getColorManager();
	}

	private ExplorerFigure getExplorerFigure() {
		return this.explorerFigure;
	}

	private TextBoxFigure getFocusObject() {
		return this.focusObject;
	}

	private Graphics getGraphics() {
		Point p = SwingUtilities.convertPoint(this.getExplorerFigure().getPanel(), 0, 0, this.getExplorerFigure().getFrame());
		Graphics g = this.getExplorerFigure().getFrame().getGraphics();
		g.translate((int) p.getX(), (int) p.getY());
		return g;
	}

	public void handleFocus(TextBoxFigure tebofi, boolean mousePressed) {
		if (tebofi != null) {
			this.resetFocus();
			this.setFocus(tebofi);
			// if (!mousePressed) {
			// }
			tebofi.draw(this.getGraphics());
		}
	}

	private void resetFocus() {
		if (this.getFocusObject() != null) {
			this.getFocusObject().setBackground(this.getColorManager().getBackground(this.getFocusObject()));
			this.getFocusObject().setForeground(this.getColorManager().getForeground(this.getFocusObject()));
			this.getFocusObject().draw(this.getGraphics());
			this.setFocusObject(null);
		}
		this.getExplorerFigure().getFrame().getStatusTextfield().setText("");
	}

	private void setExplorerFigure(ExplorerFigure newExplorerFigure) {
		this.explorerFigure = newExplorerFigure;
	}

	private void setFocus(TextBoxFigure tebofi) {
		this.setFocusObject(tebofi);
		tebofi.setBackground(this.getColorManager().getFocusBackground());
		tebofi.setForeground(this.getColorManager().getFocusForeground());
		this.getExplorerFigure().getFrame().getStatusTextfield().setText(((ObjectModelPart) tebofi.getModel()).getCompleteTitle());
	}

	private void setFocusObject(TextBoxFigure newFocusObject) {
		this.focusObject = newFocusObject;
	}
}
