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

package visu.handball.moves.model.player;

import java.io.Serializable;


public class Circle implements HighlightableItem, Serializable {
	
	private static final long serialVersionUID = -5372891552466311536L;

	private int current_x;

	private int current_y;

	private int radius;

	private boolean highlighted = false;
	

	public Circle(int current_x, int current_y, int radius) {
		this.current_x = current_x;
		this.current_y = current_y;
		this.radius = radius;
	}

	public int getCurrent_x() {
		return current_x;
	}

	public void setCurrent_x(int current_x) {
		this.current_x = current_x;
	}	
	
	public int getCurrent_y() {
		return current_y;
	}

	public void setCurrent_y(int current_y) {
		this.current_y = current_y;
	}

	public int getRadius() {
		return radius;
	}
	
	public void setHighlight(boolean hightligt) {
		highlighted = hightligt;
	}

	public boolean isHighlighted() {
		return highlighted;
	}
	
	

}
