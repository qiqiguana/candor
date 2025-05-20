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
import java.awt.geom.Ellipse2D;

import visu.handball.moves.model.ColorModel;
import visu.handball.moves.model.player.Circle;

public abstract class CircleDrawer {

	private Color fillColor;

	private Color outlineColor;
	
	private Color hightlightColor;

	private Ellipse2D shape;

	
	public CircleDrawer(ColorModel colorModel) {
		shape = new Ellipse2D.Double();
		updateColors(colorModel);
	}

	protected void drawCircle(Graphics2D g2d, Circle circle) {
		shape.setFrame(circle.getCurrent_x() - circle.getRadius(), circle
				.getCurrent_y()
				- circle.getRadius(), 2 * circle.getRadius(), 2 * circle
				.getRadius());
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(circle.isHighlighted() ? hightlightColor : fillColor);
		g2d.fill(shape);
		g2d.setColor(outlineColor);
		g2d.draw(shape);
	}

	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	public void setHightlightColor(Color hightlightColor) {
		this.hightlightColor = hightlightColor;
	}

	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
	}
	
	public abstract void updateColors(ColorModel colorModel);
	
	
}
