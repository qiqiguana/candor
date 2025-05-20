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
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.List;

import visu.handball.moves.model.ColorModel;
import visu.handball.moves.model.HandballModel;
import visu.handball.moves.model.PlayerRemovedListener;
import visu.handball.moves.model.player.Defender;
import visu.handball.moves.model.player.Offender;

public class OffenderDrawer extends AbstractPlayerDrawer implements PlayerRemovedListener{

	private static final int LINE_THICKNESS = 5;

	private BasicStroke stroke;

	private HashMap<Offender, Ellipse2D> offenderMap;

	private HandballModel model;
	
	public OffenderDrawer(HandballModel model, ColorModel colorModel) {
		super(colorModel);
		stroke = new BasicStroke(LINE_THICKNESS);
		offenderMap = new HashMap<Offender, Ellipse2D>();
		this.model = model;
		model.addPlayerRemovedListener(this);
	}

	@Override
	public void drawPlayers(Graphics2D g2d) {
		List<Offender> offenders = model.getOffenders();

		for (Offender offender : offenders) {
			Ellipse2D shape = offenderMap.get(offender);
			int current_x = offender.getCurrent_x();
			int current_y = offender.getCurrent_y();
			
			if (shape == null) {
				shape = new Ellipse2D.Double(current_x - RADIUS,
						current_y - RADIUS, 2 * RADIUS,
						2 * RADIUS);
				offenderMap.put(offender, shape);
			} else {
				shape.setFrameFromCenter(current_x, current_y, current_x - RADIUS,
						current_y - RADIUS);
			}

			g2d.setStroke(stroke);
			if (offender.isHightlighted()) {
				g2d.setColor(hightlightColor);
			} else if (offender.isMarked()) {
				g2d.setColor(markedColor);
			} else {
				g2d.setColor(playerColor);
			}
			g2d.draw(shape);
			drawPlayerNumber(g2d, current_x, current_y, String.valueOf(offender.getPlayerNumber()));
			if (offender.hasBall()) {
				drawBall(g2d, current_x, current_y);
			}
		}
	}

	public void defenderRemoved(Defender defender) {
		//nichts zu erledigen
		
	}

	public void offenderRemoved(Offender offender) {
		offenderMap.remove(offender);
	}
	
	@Override
	protected void updatePlayerColors(ColorModel colorModel) {
		playerColor = colorModel.getAttackersColor();
	}
	

}
