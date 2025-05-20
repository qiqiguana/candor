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
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;

import visu.handball.moves.model.ColorModel;
import visu.handball.moves.model.player.Ball;

public abstract class AbstractPlayerDrawer {
	
	public static final int RADIUS = 12;

	protected Color markedColor;
	
	protected Color playerColor;
	
	protected Color hightlightColor;

	protected BallDrawer ballDrawer;
	
	public AbstractPlayerDrawer(ColorModel colorModel) {
		ballDrawer = new BallDrawer(colorModel);
		updateColors(colorModel);
	}
	
	/**
	 * Kreuze auf gleiche Größe wie der Angreifer-Kreis Pythagoras a²+b²=c²; 
	 * c=RADIUS; a=b ==> 2a²=c²
	 */
	public static final float RADIUS_OFFSET = (float) (Math.sqrt(RADIUS
			* RADIUS) / Math.sqrt(2));

	public void draw(Graphics2D g2d) {
		drawPlayers(g2d);
	}

	abstract protected void drawPlayers(Graphics2D g2d);

	protected void drawPlayerNumber(Graphics2D g2d, int x, int y,
			String playerNumber) {
		AffineTransform transform = new AffineTransform();
		transform.translate(x + RADIUS + 3, y + RADIUS + 3);

		TextLayout number = new TextLayout(playerNumber, new Font("Helvetica",
				Font.PLAIN, 15), new FontRenderContext(null, true, false));

		Shape shape = number.getOutline(transform);
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(Color.WHITE);
		g2d.fill(shape);
		g2d.draw(shape);
	}

	protected void drawBall(Graphics2D g2d, int x, int y) {
		Ball ball = new Ball(x, y);
		ballDrawer.drawCircle(g2d, ball);
	}
	
	public void updateColors(ColorModel colorModel) {
		this.markedColor = colorModel.getSelectedPlayerColor();
		this.hightlightColor = colorModel.getHightlightColor();
		ballDrawer.updateColors(colorModel);
		updatePlayerColors(colorModel);
	}
	
	abstract protected void updatePlayerColors(ColorModel colorModel);

}
