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

package visu.handball.moves.model.animation;

import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

import visu.handball.moves.model.PassEvent;

public class PassLineIterator implements PathIterator {

	private static final double SPEED = 15;

	private double[] passCoordsX;

	private double[] passCoordsY;

	private int delay = 0;

	private int index;

	public PassLineIterator(PassEvent event) {
		int startX = event.getPlayer().getCurrent_x();
		int startY = event.getPlayer().getCurrent_y();
		if (event.isGoalPass()) {
			createPassPath(startX, startY, event.getDestinationX(), event
					.getDestinationY());
		} else {
			int endX = event.getDestinationPlayer().getCurrent_x();
			int endY = event.getDestinationPlayer().getCurrent_y();
			createPassPath(startX, startY, endX, endY);
		}

		if (event.getDelay() > 0) {
			delay = Animator.getDelayUnits(event.getDelay());
		}
		index = 0;
	}

	private void createPassPath(int startX, int startY, int endX, int endY) {
		int length = (int) Point2D.distance(startX, startY, endX, endY);
		length = (int) Math.round((length < 20) ? 1 : length / SPEED);
		double xStep = (endX - startX) / ((double) length);
		double yStep = (endY - startY) / (double) length;

		passCoordsX = new double[length];
		passCoordsY = new double[length];

		for (int i = 0; i < length; i++) {
			passCoordsX[i] = startX + (i * xStep);
			passCoordsY[i] = startY + (i * yStep);
		}
	}

	public int getWindingRule() {
		return 0;
	}

	public boolean isDone() {
		return index >= passCoordsX.length;
	}

	public void next() {
		if (delay > 0) {
			delay--;
		} else {
			index++;
		}
	}

	public int currentSegment(float[] coords) {
		coords[0] = new Float(passCoordsX[index]).floatValue();
		coords[1] = new Float(passCoordsY[index]).floatValue();
		return 0;
	}

	public int currentSegment(double[] coords) {
		coords[0] = passCoordsX[index];
		coords[1] = passCoordsY[index];
		return 0;
	}

}
