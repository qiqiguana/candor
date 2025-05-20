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

public abstract class Player implements HighlightableItem, Serializable {

	private static final long serialVersionUID = -5372891552466311536L;

	private int start_x;

	private int start_y;

	private int current_x;

	private int current_y;

	private int playerNumber;

	private boolean hasBall;

	protected boolean hightlighted;

	protected boolean marked;

	public Player(int start_x, int start_y, int playerNumber) {
		this.start_x = start_x;
		this.start_y = start_y;
		this.current_x = start_x;
		this.current_y = start_y;
		this.playerNumber = playerNumber;
		this.hasBall = false;
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

	public void setCurrentPosition(int x, int y) {
		this.current_x = x;
		this.current_y = y;
	}

	public boolean hasBall() {
		return hasBall;
	}

	public void setHasBall(boolean hasBall) {
		this.hasBall = hasBall;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}

	public int getStart_x() {
		return start_x;
	}

	public int getStart_y() {
		return start_y;
	}

	public void setStart_x(int start_x) {
		this.start_x = start_x;
		this.current_x = start_x;
	}

	public void setStart_y(int start_y) {
		this.start_y = start_y;
		this.current_y = start_y;
	}

	public void setStartPosition(int x, int y) {
		setStart_x(x);
		setStart_y(y);
	}

	public void resetPosition() {
		current_x = start_x;
		current_y = start_y;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	public void setHighlight(boolean hightligt) {
		this.hightlighted = hightligt;
	}

	public boolean isHightlighted() {
		return hightlighted;
	}

	public boolean isMarked() {
		return marked;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (obj.getClass() != getClass())
			return false;

		Player other = (Player) obj;

		if (other.isMarked() != this.isMarked()
				|| other.hasBall != this.hasBall()
				|| other.getStart_x() != this.getStart_x()
				|| other.getStart_y() != this.getStart_y()
				|| other.getPlayerNumber() != this.getPlayerNumber()) {
			return false;
		}

		return true;
	}

}
