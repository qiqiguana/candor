/*
 * Created on 31.08.2006
 * Created by Richard Doerfler, Thomas Halm
 * Copyright (C) 2006  Richard Doerfler, Thomas Halm
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package visu.handball.moves.views;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import visu.handball.moves.model.ColorModel;
import visu.handball.moves.model.ColorModelListener;
import visu.handball.moves.model.HandballModel;
import visu.handball.moves.model.HandballModelListener;

/**
 * Klasse zur Darstellung einer Spielfeld-Hälfte
 * 
 * @author Tommy
 * 
 */
public class Field extends JPanel implements HandballModelListener,
		ColorModelListener {

	private HandballModel handballModel;

	private OffenderDrawer offenderDrawer;

	private DefenderDrawer defenderDrawer;

	private FieldDrawer fieldDrawer;

	private EventDrawer eventDrawer;

	private BallDrawer ballDrawer;

	public Field(HandballModel handballModel, ColorModel colorModel) {
		this.handballModel = handballModel;
		handballModel.addListener(this);
		colorModel.addColorModelListener(this);

		fieldDrawer = new FieldDrawer(colorModel);
		offenderDrawer = new OffenderDrawer(handballModel, colorModel);
		defenderDrawer = new DefenderDrawer(handballModel, colorModel);
		eventDrawer = new EventDrawer(handballModel, colorModel);
		ballDrawer = new BallDrawer(colorModel);

		setPreferredSize(new Dimension(FieldDrawer.PANEL_WIDTH,
				FieldDrawer.PANEL_HEIGHT));
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		// Kantenglaettung einschalten
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		fieldDrawer.draw(g2d);
		offenderDrawer.draw(g2d);
		defenderDrawer.draw(g2d);

		if (handballModel.getState() == HandballModel.State.EDIT
				|| handballModel.getState() == HandballModel.State.EDIT_EVENT
				|| handballModel.getState() == HandballModel.State.ANIMATION) {

			eventDrawer
					.setPrintMode(handballModel.getState() == HandballModel.State.ANIMATION);

			eventDrawer.draw(g2d);
		}

		if (!handballModel.isBallSet()) {
			ballDrawer.drawCircle(g2d, handballModel.getBall());
		}
	}

	public boolean insideGoal(int x, int y) {
		return fieldDrawer.insideGoal(x, y);
	}

	public Point getGoalMiddle() {
		return fieldDrawer.getGoalMiddle();
	}

	public boolean insideSixMeter(int x, int y) {
		return fieldDrawer.insideSixMeter(x, y);
	}

	public void modelChanged() {
		repaint();
	}

	public void colorModelChanged(ColorModel colorModel) {
		fieldDrawer.updateColors(colorModel);
		defenderDrawer.updateColors(colorModel);
		offenderDrawer.updateColors(colorModel);
		eventDrawer.updateColors(colorModel);
		ballDrawer.updateColors(colorModel);

		repaint();
	}

}
