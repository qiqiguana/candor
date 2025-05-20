/*
 * Created on 03.04.2007
 * Created by Thomas Halm
 * Copyright (C) 2007  Richard Doerfler, Thomas Halm
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

import visu.handball.moves.model.ColorModel;

public class BallDrawer extends CircleDrawer {

	public BallDrawer(ColorModel colorModel) {
		super(colorModel);
	}

	@Override
	public void updateColors(ColorModel colorModel) {
		setFillColor(colorModel.getFillBallColor());
		setHightlightColor(colorModel.getHightlightColor());
		setOutlineColor(colorModel.getOutlineBallColor());
	}

}
