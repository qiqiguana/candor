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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.util.List;

import visu.handball.moves.model.ColorModel;
import visu.handball.moves.model.HandballModel;
import visu.handball.moves.model.MoveEvent;
import visu.handball.moves.model.PassEvent;
import visu.handball.moves.model.player.MovePoint;
import visu.handball.moves.model.player.Player;

public class EventDrawer {

	/**
	 * Strokes die zum Zeichnen verwendet werden
	 */
	private final static BasicStroke NORMAL_STROKE = new BasicStroke(2);

	private final static BasicStroke HIGHLIGHT_STROKE = new BasicStroke(3);

	private final static float[] DELAYED_DASHS = { 3.0f };

	private final static BasicStroke HIGHLIGHT_DELAYED_STROKE = new BasicStroke(
			3f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f,
			DELAYED_DASHS, 0.0f);

	private final static BasicStroke NORMAL_DELAYED_STROKE = new BasicStroke(
			2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f,
			DELAYED_DASHS, 0.0f);

	/**
	 * Farben die zum Zeichnen verwendet wird
	 */
	private Color moveColor;

	private Color passColor;

	private HandballModel model;

	private EventPointDrawer movePointDrawer;

	private boolean printMode = false;

	public EventDrawer(HandballModel model, ColorModel colorModel, boolean printMode) {
		this.model = model;
		this.printMode = printMode;
		this.movePointDrawer = new EventPointDrawer(colorModel);
		updateColors(colorModel);
	}

	public EventDrawer(HandballModel model, ColorModel colorModel) {
		this(model, colorModel, false);
	}

	public void draw(Graphics2D g2d) {
		List<MoveEvent> events = model.getActualMoveEvents();

		for (MoveEvent event : events) {
			if (event instanceof PassEvent) {
				drawPassEvent(g2d, (PassEvent) event);
			} else {
				drawMoveEvent(g2d, event);
			}
		}
	}

	private void drawPassEvent(Graphics2D g2d, PassEvent event) {
		GeneralPath passLine = createPassLine(event);
		GeneralPath arrowPath = createPassArrowPath(event);

		if (passLine != null) {
			g2d.setColor(passColor);
			if (event.isMarked()) {
				g2d.setStroke(HIGHLIGHT_STROKE);
			} else {
				g2d.setStroke(NORMAL_STROKE);
			}
			g2d.draw(passLine);
			if (arrowPath != null) {
				g2d.draw(arrowPath);
			}
		}
	}

	private GeneralPath createPassArrowPath(PassEvent event) {
		if (event.getPlayer() != null) {
			int startX = event.getPlayer().getCurrent_x();
			int startY = event.getPlayer().getCurrent_y();

			int endX = Integer.MIN_VALUE;
			int endY = Integer.MIN_VALUE;
			if (event.getDestinationPlayer() != null) {
				endX = event.getDestinationPlayer().getCurrent_x();
				endY = event.getDestinationPlayer().getCurrent_y();
			} else if (event.getDestinationPoint() != null) {
				endX = event.getDestinationX();
				endY = event.getDestinationY();
			}

			return createArrow(endX, endY, startX, startY);
		}

		return null;
	}

	private GeneralPath createPassLine(PassEvent event) {
		if (event.getPlayer() != null) {
			int startX = event.getPlayer().getCurrent_x();
			int startY = event.getPlayer().getCurrent_y();
			int endX = Integer.MIN_VALUE;
			int endY = Integer.MIN_VALUE;
			if (event.getDestinationPlayer() != null) {
				endX = event.getDestinationPlayer().getCurrent_x();
				endY = event.getDestinationPlayer().getCurrent_y();
			} else if (event.getDestinationPoint() != null) {
				endX = event.getDestinationPoint().getCurrent_x();
				endY = event.getDestinationPoint().getCurrent_y();
			}
			if (endX > Integer.MIN_VALUE && endY > Integer.MIN_VALUE) {
				GeneralPath passLine = new GeneralPath();
				passLine.moveTo(startX, startY);
				passLine.lineTo(endX, endY);
				return passLine;
			}
		}
		return null;
	}

	private void drawMoveEvent(Graphics2D g2d, MoveEvent event) {
		GeneralPath curve = createCurvePath(event);
		GeneralPath arrowPath = createArrowPath(event);

		if (curve != null) {
			g2d.setColor(moveColor);
			if (event.isMarked() && !printMode) {
				if (event.getDelay() > 0) {
					g2d.setStroke(HIGHLIGHT_DELAYED_STROKE);
				} else {
					g2d.setStroke(HIGHLIGHT_STROKE);
				}
			} else if (event.getDelay() > 0) {
				g2d.setStroke(NORMAL_DELAYED_STROKE);
			} else {
				g2d.setStroke(NORMAL_STROKE);
			}

			g2d.draw(curve);

			if (arrowPath != null) {
				g2d.draw(arrowPath);
			}

			if (event.isMarked() && !printMode) {
				if (event.isDestinationPointSet()) {
					movePointDrawer
							.drawCircle(g2d, event.getDestinationPoint());
				}
				if (event.isControlPointSet()) {
					movePointDrawer.drawCircle(g2d, event.getControlPoint());
				}
			}

		}

	}

	private GeneralPath createCurvePath(MoveEvent event) {
		GeneralPath path = null;
		// Testen ob Start- und Zielpunkt vorhanden
		if (event.getPlayer() != null && event.getDestinationPoint() != null) {
			Player player = event.getPlayer();
			MovePoint destinationPoint = event.getDestinationPoint();

			int startX = player.getCurrent_x();
			int startY = player.getCurrent_y();

			// Falls Kontrollpunkt vorhanden ==> QuadCurve
			if (event.getControlPoint() != null) {
				MovePoint controlPoint = event.getControlPoint();
				path = new GeneralPath();
				path.moveTo(startX, startY);
				path.quadTo(controlPoint.getCurrent_x(), controlPoint
						.getCurrent_y(), destinationPoint.getCurrent_x(),
						destinationPoint.getCurrent_y());

			} else {
				path = new GeneralPath();
				path.moveTo(startX, startY);
				path.lineTo(destinationPoint.getCurrent_x(), destinationPoint
						.getCurrent_y());
			}
		}

		return path;
	}

	private GeneralPath createArrow(int destX, int destY, int controlX,
			int controlY) {
		GeneralPath arrow = new GeneralPath();
		float arrSize = 15;
		float adjSize = (float) (arrSize / Math.sqrt(2));

		float ex = destX - controlX;
		float ey = destY - controlY;
		float abs_e = (float) Math.sqrt(ex * ex + ey * ey);
		ex /= abs_e;
		ey /= abs_e;

		arrow.moveTo(destX, destY);
		arrow.lineTo(destX + (ey - ex) * adjSize, destY - (ex + ey) * adjSize);
		arrow.moveTo(destX, destY);
		arrow.lineTo(destX - (ey + ex) * adjSize, destY + (ex - ey) * adjSize);

		return arrow;
	}

	private GeneralPath createArrowPath(MoveEvent event) {
		if (event.getPlayer() != null && event.getDestinationPoint() != null) {
			int destinationX = event.getDestinationX();
			int destinationY = event.getDestinationY();
			int controlX;
			int controlY;
			if (event.getControlPoint() != null) {
				controlX = event.getControlPointX();
				controlY = event.getControlPointY();
				//wenn Kontroll- und Zielpunkt gleich sind,
				//muss der Ausgangspunkt verwendet werden
				if (controlX == destinationX && controlY == destinationY) {
					controlX = event.getPlayer().getCurrent_x();
					controlY = event.getPlayer().getCurrent_y();
				}
			} else {
				controlX = event.getPlayer().getCurrent_x();
				controlY = event.getPlayer().getCurrent_y();
			}

			return createArrow(destinationX, destinationY, controlX, controlY);
		}

		return null;
	}

	public void updateColors(ColorModel colorModel) {
		moveColor = colorModel.getMoveColor();
		passColor = colorModel.getPassColor();
		movePointDrawer.updateColors(colorModel);
	}

	public void setPrintMode(boolean printMode) {
		this.printMode = printMode;
	}

}
