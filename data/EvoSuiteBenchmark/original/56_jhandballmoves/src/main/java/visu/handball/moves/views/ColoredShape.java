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
import java.awt.Shape;

public class ColoredShape {

	private Shape shape;

	private Color color;

	private boolean fill;

	private BasicStroke stroke;

	public ColoredShape(Shape shape, Color color, boolean fill) {
		this.shape = shape;
		this.color = color;
		this.fill = fill;
	}

	public ColoredShape(Shape shape, Color color, boolean fill,
			BasicStroke stroke) {
		this.shape = shape;
		this.color = color;
		this.fill = fill;
		this.stroke = stroke;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public boolean isFill() {
		return fill;
	}

	public void setFill(boolean fill) {
		this.fill = fill;
	}

	public BasicStroke getStroke() {
		return stroke;
	}

	public void setStroke(BasicStroke stroke) {
		this.stroke = stroke;
	}
	
	public void drawShape(Graphics2D g2d) {
		g2d.setColor(getColor());
		if (getStroke() != null) {
			g2d.setStroke(getStroke());
		}
		if (isFill()) {
			g2d.fill(getShape());
		}
		g2d.draw(getShape());
	}

}
