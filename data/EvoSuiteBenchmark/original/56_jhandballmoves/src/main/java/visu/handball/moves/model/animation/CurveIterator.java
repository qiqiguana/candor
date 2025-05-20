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

import visu.handball.moves.model.MoveEvent;

public class CurveIterator implements PathIterator {

	private static final int SPEED = 10;

	private static final int COUNT = 1000;

	private double[] curveCoordsX;

	private double[] curveCoordsY;

	private int index;

	private int delay;

	public CurveIterator(MoveEvent event) {

		int startX = event.getPlayer().getCurrent_x();
		int startY = event.getPlayer().getCurrent_y();

		int controlX = event.getControlPointX();
		int controlY = event.getControlPointY();

		int endX = event.getDestinationX();
		int endY = event.getDestinationY();

		createCurvePath(startX, startY, controlX, controlY, endX, endY);

		if (event.getDelay() > 0) {
			delay = Animator.getDelayUnits(event.getDelay());
		}

		index = 0;
	}

	private void createCurvePath(int startX, int startY, int controlX,
			int controlY, int endX, int endY) {

		curveCoordsX = new double[COUNT];
		curveCoordsY = new double[COUNT];

		curveCoordsX[0] = startX;
		curveCoordsY[0] = startY;

		for (int i = 1; i < COUNT - 1; i++) {

			double u = i / (COUNT * 1.0);

			double Cx = qb0(u) * startX + qb1(u) * controlX + qb2(u) * endX;

			double Cy = qb0(u) * startY + qb1(u) * controlY + qb2(u) * endY;

			curveCoordsX[i] = Cx;
			curveCoordsY[i] = Cy;
		}
		curveCoordsX[curveCoordsX.length - 1] = endX;
		curveCoordsY[curveCoordsY.length - 1] = endY;

	}

	private double qb0(double u) {
		return (1 - u) * (1 - u);
	}

	private double qb1(double u) {
		return 2 * u * (1 - u);
	}

	private double qb2(double u) {
		return u * u;
	}

	public int currentSegment(float[] coords) {
		coords[0] = new Float(curveCoordsX[index]).floatValue();
		coords[1] = new Float(curveCoordsY[index]).floatValue();
		return 0;
	}

	public int currentSegment(double[] coords) {
		coords[0] = curveCoordsX[index];
		coords[1] = curveCoordsY[index];
		return 0;
	}

	public int getWindingRule() {
		return 0;
	}

	public boolean isDone() {
		return index >= curveCoordsX.length;
	}

	public void next() {
		if (delay > 0) {
			delay--;
		} else {
			int oldIndex = index;
			while ((index < curveCoordsX.length)
					&& (Point2D.distance(curveCoordsX[oldIndex],
							curveCoordsY[oldIndex], curveCoordsX[index],
							curveCoordsY[index]) < SPEED)) {
				index++;
			}
		}
	}

}
