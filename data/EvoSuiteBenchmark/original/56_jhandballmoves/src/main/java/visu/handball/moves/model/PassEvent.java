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

package visu.handball.moves.model;

import visu.handball.moves.model.player.Offender;
import visu.handball.moves.model.player.Player;

public class PassEvent extends MoveEvent {

	private static final long serialVersionUID = -5172891552466311536L;

	private Offender destinationPlayer;

	private boolean goalPass;

	public PassEvent(Player fromPlayer, int nr, int delay) {
		super(fromPlayer, nr, delay);
		this.destinationPlayer = null;
		controlPoint = null;
		destinationPointTemporary = true;
		goalPass = false;
	}

	public void setDestinationPlayer(Offender destinationPlayer, boolean temp) {
		this.destinationPlayer = destinationPlayer;
		destinationPointTemporary = temp;
	}

	public Offender getDestinationPlayer() {
		return destinationPlayer;
	}

	public boolean isGoalPass() {
		return goalPass;
	}

	public void setGoalPass(boolean goalPass) {
		this.goalPass = goalPass;
		if (goalPass) {
			destinationPointTemporary = false;
		}
	}

	@Override
	public String toString() {
		String str = "";
		if (goalPass) {
			str = "Torwurf";
		} else if (destinationPlayer == null) {
			str = "Passweg nicht definiert";
		} else {
			str = "Pass zu Spieler \" " + destinationPlayer + "\"";
		}
		return str;
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

		PassEvent other = (PassEvent) obj;

		if (other.getDestinationPoint() == null
				^ this.getDestinationPoint() == null) {
			return false;
		}

		if (other.getDestinationPoint() == null) {
			// hier ist dann auch this.getDestinationPoint == null
			if (other.getDelay() != this.getDelay()
					|| !other.getPlayer().equals(this.getPlayer())
					|| other.getSequenceNr() != this.getSequenceNr()
					|| other.isGoalPass() != this.isGoalPass()
					|| (other.getDestinationPlayer() == null ^ this
							.getDestinationPlayer() == null)) {
				return false;
			}
		} else if (other.getDestinationX() != this.getDestinationX()
				|| other.getDestinationY() != this.getDestinationY()
				|| other.getDelay() != this.getDelay()
				|| !other.getPlayer().equals(this.getPlayer())
				|| other.getSequenceNr() != this.getSequenceNr()
				|| other.isGoalPass() != this.isGoalPass()
				|| (other.getDestinationPlayer() == null ^ this
						.getDestinationPlayer() == null)) {
			return false;
		}

		if (other.getDestinationPlayer() != null
				&& this.getDestinationPlayer() != null) {
			if (!other.getDestinationPlayer().equals(
					this.getDestinationPlayer())) {
				return false;
			}
		}

		return true;
	}

}
