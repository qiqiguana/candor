/*
 * Created on 02.10.2006
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
import java.awt.Point;
import java.awt.Stroke;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import visu.handball.moves.model.ColorModel;

public class FieldDrawer{

	/*
	 * absolute Groesse des Panels
	 */
	public static final int PANEL_WIDTH = 500;

	public static final int PANEL_HEIGHT = 550;

	/*
	 * Maße einer Handball-Spielfeld-Haelfte (relativ)
	 */
	private static final int FIELD_WIDTH = 20;

	private static final int GOAL_WIDTH = 3;

	private static final int SIX_METER = 6;

	private static final int NINE_METER = 9;

	private static final int SPACE = 40;

	private static final int LINE_THICKNESS = 10;

	private List<ColoredShape> fieldLinesShapes;

	private ColoredShape background;
	
	private Rectangle2D goal;

	private Point2D linkerPfosten;

	private Point2D rechterPfosten;

	private double sixMeterPixel;
	
	public FieldDrawer(ColorModel colorModel) {
		createFieldShapes(colorModel);
	}

	private void createFieldShapes(ColorModel colorModel) {
		Color fieldColor = colorModel.getFieldColor();
		Color lineColor = colorModel.getLineColor();
		
		fieldLinesShapes = new ArrayList<ColoredShape>();

		// Spielfeld-Hintergrund
		Rectangle2D bg = new Rectangle2D.Double(0, 0, PANEL_WIDTH,
				PANEL_HEIGHT);
		background = new ColoredShape(bg, fieldColor, true);

		// Spielfeld-Linien
		BasicStroke lineStroke = new BasicStroke(LINE_THICKNESS);

		int fieldWidth = PANEL_WIDTH - 2 * SPACE;

		Rectangle2D aussen = new Rectangle2D.Double(SPACE, SPACE, fieldWidth,
				fieldWidth);
		fieldLinesShapes.add(new ColoredShape(aussen, lineColor, false, lineStroke));

		Line2D aussenLinie = new Line2D.Double(SPACE, fieldWidth + SPACE,
				SPACE, PANEL_HEIGHT);
		fieldLinesShapes.add(new ColoredShape(aussenLinie, lineColor, false,
				lineStroke));
		aussenLinie = new Line2D.Double(PANEL_WIDTH - SPACE,
				fieldWidth + SPACE, PANEL_WIDTH - SPACE, PANEL_HEIGHT);
		fieldLinesShapes.add(new ColoredShape(aussenLinie, lineColor, false,
				lineStroke));

		int x_center = (PANEL_WIDTH) / 2;
		// Relative Torbreite berechnen
		float relation = (float) GOAL_WIDTH / FIELD_WIDTH;
		int goalWidth = (int) ((fieldWidth) * relation);
		int x_goalStart = x_center - goalWidth / 2;

		goal = new Rectangle2D.Double(x_goalStart, 10, goalWidth, 30);
		rechterPfosten = new Point2D.Double(x_goalStart, SPACE);
		linkerPfosten = new Point2D.Double(x_goalStart + goalWidth, SPACE);
		fieldLinesShapes.add(new ColoredShape(goal, lineColor, false, lineStroke));

		// 6-Meter zeichnen
		relation = (float) SIX_METER / FIELD_WIDTH;
		int radius = (int) ((fieldWidth) * relation);
		sixMeterPixel = radius;
		Arc2D viertel = new Arc2D.Double(x_goalStart - radius, -radius + SPACE,
				2 * radius, 2 * radius, 180, 90, Arc2D.OPEN);
		fieldLinesShapes
				.add(new ColoredShape(viertel, lineColor, false, lineStroke));
		Line2D line = new Line2D.Double(x_goalStart, SPACE + radius,
				x_goalStart + goalWidth, SPACE + radius);
		fieldLinesShapes.add(new ColoredShape(line, lineColor, false, lineStroke));

		viertel = new Arc2D.Double(x_goalStart + goalWidth - radius, -radius
				+ SPACE, 2 * radius, 2 * radius, 270, 90, Arc2D.OPEN);
		fieldLinesShapes
				.add(new ColoredShape(viertel, lineColor, false, lineStroke));

		// 9-Meter zeichen
		float dash[] = { 10.0f };
		BasicStroke dashed = new BasicStroke(3.0f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f);

		relation = (float) NINE_METER / FIELD_WIDTH;
		radius = (int) ((fieldWidth) * relation);
		// Neun-Meter wird keine 90 Grad gezeichent
		double diff = Math.acos((fieldWidth / 2 - goalWidth / 2)
				/ (float) radius);
		double degress = Math.toDegrees(diff);
		viertel = new Arc2D.Double(x_goalStart - radius, -radius + SPACE,
				2 * radius, 2 * radius, 180 + degress, 90 - degress, Arc2D.OPEN);
		fieldLinesShapes.add(new ColoredShape(viertel, lineColor, false, dashed));

		line = new Line2D.Double(x_goalStart, SPACE + radius, x_goalStart
				+ goalWidth, SPACE + radius);
		fieldLinesShapes.add(new ColoredShape(line, lineColor, false, dashed));

		viertel = new Arc2D.Double(x_goalStart + goalWidth - radius, -radius
				+ SPACE, 2 * radius, 2 * radius, 270, 90 - degress, Arc2D.OPEN);
		fieldLinesShapes.add(new ColoredShape(viertel, lineColor, false, dashed));
	}

	public void draw(Graphics2D g2d) {
		Color tempColor = g2d.getColor();
		Stroke tempStroke = g2d.getStroke();

		background.drawShape(g2d);
		
		for (ColoredShape shape : fieldLinesShapes) {
			shape.drawShape(g2d);
		}

		g2d.setColor(tempColor);
		g2d.setStroke(tempStroke);
	}
	
	public void updateColors(ColorModel colorModel) {
		background.setColor(colorModel.getFieldColor());
		for (ColoredShape shape : fieldLinesShapes) {
			shape.setColor(colorModel.getLineColor());
		}
	}
	
	
	public boolean insideGoal(int x, int y) {
		return goal.contains(x, y);
	}

	public Point getGoalMiddle() {
		return new Point((int) goal.getCenterX(), (int) goal.getCenterY());
	}

	public boolean insideSixMeter(int x, int y) {
		if (linkerPfosten.distance(x, y) < sixMeterPixel
				|| rechterPfosten.distance(x, y) < sixMeterPixel) {
			return true;
		} else {
			return false;
		}
	}
}
