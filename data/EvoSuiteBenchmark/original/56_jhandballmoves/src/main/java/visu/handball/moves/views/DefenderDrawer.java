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
import java.awt.geom.GeneralPath;
import java.util.HashMap;
import java.util.List;

import visu.handball.moves.model.ColorModel;
import visu.handball.moves.model.HandballModel;
import visu.handball.moves.model.PlayerRemovedListener;
import visu.handball.moves.model.player.Defender;
import visu.handball.moves.model.player.Offender;

public class DefenderDrawer extends AbstractPlayerDrawer implements
		PlayerRemovedListener {

	private static final int LINE_THICKNESS = 5;
	
	private BasicStroke stroke;

	private HashMap<Defender, GeneralPath> DefenderMap;

	private HandballModel model;

	public DefenderDrawer(HandballModel model, ColorModel colorModel) {
		super(colorModel);
		stroke = new BasicStroke(LINE_THICKNESS);
		DefenderMap = new HashMap<Defender, GeneralPath>();
		this.model = model;
		model.addPlayerRemovedListener(this);
	}

	@Override
	public void drawPlayers(Graphics2D g2d) {
		List<Defender> defenders = model.getDefenders();

		for (Defender defender : defenders) {
			GeneralPath shape = DefenderMap.get(defender);
			int current_x = defender.getCurrent_x();
			int current_y = defender.getCurrent_y();

			if (shape == null) {
				shape = new GeneralPath();
			} else {
				shape.reset();
			}
			shape.moveTo(current_x - RADIUS_OFFSET, current_y - RADIUS_OFFSET);
			shape.lineTo(current_x + RADIUS_OFFSET, current_y + RADIUS_OFFSET);
			shape.moveTo(current_x - RADIUS_OFFSET, current_y + RADIUS_OFFSET);
			shape.lineTo(current_x + RADIUS_OFFSET, current_y - RADIUS_OFFSET);

			g2d.setStroke(stroke);
			if (defender.isHightlighted()) {
				g2d.setColor(hightlightColor);
			} else if (defender.isMarked()) {
				g2d.setColor(markedColor);
			} else {
				g2d.setColor(playerColor);
			}
			g2d.draw(shape);
			drawPlayerNumber(g2d, current_x, current_y, String.valueOf(defender
					.getPlayerNumber()));
		}
	}

	public void offenderRemoved(Offender offender) {
		// nichts zu erledigen

	}

	public void defenderRemoved(Defender defender) {
		DefenderMap.remove(defender);
	}
	
	@Override
	protected void updatePlayerColors(ColorModel colorModel) {
		playerColor = colorModel.getDefendersColor();
	}

}
