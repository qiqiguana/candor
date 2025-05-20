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

import java.awt.Color;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JPanel;

import de.paragon.explorer.event.DrawEvent;
import de.paragon.explorer.event.DrawListener;

public class ExplorerDrawingPanel extends JPanel {
	private static final long			serialVersionUID	= -2710229233010861795L;
	private Collection<DrawListener>	drawListeners;

	/**
	 * StandardDrawingPanel constructor comment.
	 */
	public ExplorerDrawingPanel() {
		super();
		this.initialize();
	}

	/**
	 * StandardDrawingPanel constructor comment.
	 * 
	 * @param layout
	 *            java.awt.LayoutManager
	 * @param isDoubleBuffered
	 *            boolean
	 */
	public ExplorerDrawingPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	public void addDrawListener(DrawListener listener) {
		this.getDrawListeners().add(listener);
	}

	private Collection<DrawListener> getDrawListeners() {
		if (this.drawListeners == null) {
			this.setDrawListeners(new Vector<DrawListener>());
		}
		return this.drawListeners;
	}

	private void initialize() {
		this.setBackground(Color.lightGray);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!(this.getDrawListeners().isEmpty())) {
			DrawEvent e = new DrawEvent(this, g);
			Iterator<DrawListener> itr = this.getDrawListeners().iterator();
			while (itr.hasNext()) {
				(itr.next()).draw(e);
			}
		}
	}

	private void setDrawListeners(Collection<DrawListener> newListeners) {
		this.drawListeners = newListeners;
	}
}
