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

import java.io.Serializable;

import visu.handball.moves.model.player.Defender;
import visu.handball.moves.model.player.MovePoint;
import visu.handball.moves.model.player.Offender;
import visu.handball.moves.model.player.Player;

/**
 * @author tommy
 * 
 */
public class MoveEvent implements Comparable<MoveEvent>, Serializable {

	private static final long serialVersionUID = -5372891552466311536L;

	/**
	 * Ereignis ist einem Spieler zugeordnet
	 */
	protected Player player;

	/**
	 * Ereignis besitzt eine Sequenz-Nummer (1. Ereignis hat die Sequenz-Nummer
	 * 0)
	 */
	protected int sequenceNr;

	/**
	 * Ereignis kann Verzögerung besitzen, d.h. gleiche Sequenz-Nummer aber
	 * Verzögerung. Erst nach Ablauf der Verzögerung innerhalb einer
	 * Sequenz-Animation wird dieses Ereignis animiert
	 */
	protected int delay;

	protected MovePoint destinationPoint;

	protected MovePoint controlPoint;

	protected int oldPlayerX;

	protected int oldPlayerY;

	protected boolean destinationPointTemporary = true;

	protected boolean controlPointTemporary = true;

	protected boolean marked = false;

	public MoveEvent(Player player, int nr) {
		this(player, nr, 0);
	}

	public MoveEvent(Player player, int nr, int delay) {
		this.player = player;
		this.sequenceNr = nr;
		this.delay = delay;
		destinationPoint = null;
		controlPoint = null;
		oldPlayerX = player.getCurrent_x();
		oldPlayerY = player.getCurrent_y();
	}

	/**
	 * Liefert die Verzögerung des Ereignisses
	 * 
	 * @return delay
	 */
	public int getDelay() {
		return delay;
	}

	/**
	 * Setzt die Verzögerung des Ereignisses
	 * 
	 * @param delay
	 */
	public void setDelay(int delay) {
		this.delay = delay;
	}

	/**
	 * Liefert den zugeordneten Spieler
	 * 
	 * @return player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Ordnet den uebergebenen Spieler dem Ereignis zu (Es ist fraglich ob, dies
	 * nachträglich noch möglich sein muss)
	 * 
	 * @param player
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Liefert die Sequenz-Nummer, des Ereignisses
	 * 
	 * @return sequence
	 */
	public int getSequenceNr() {
		return sequenceNr;
	}

	/**
	 * Setzt die Sequenz-Nummer auf den übergebenen Wert
	 * 
	 * @param sequenceNr
	 */
	public void setSequenceNr(int sequenceNr) {
		this.sequenceNr = sequenceNr;
	}

	public int compareTo(MoveEvent o) {
		if (sequenceNr < o.sequenceNr)
			return -1;
		else if (sequenceNr > o.sequenceNr)
			return 1;
		else {
			if (delay < o.getDelay()) {
				return -1;
			} else if (delay > o.getDelay()) {
				return 1;
			} else {
				if (player.getPlayerNumber() < o.getPlayer().getPlayerNumber()) {
					return -1;
				} else if (player.getPlayerNumber() > o.getPlayer()
						.getPlayerNumber()) {
					return 1;
				} else if ((player instanceof Offender)
						&& (o.getPlayer() instanceof Defender)) {
					return -1;
				} else if ((player instanceof Defender)
						&& (o.getPlayer() instanceof Offender)) {
					return 1;
				} else {
					if (this instanceof PassEvent && !(o instanceof PassEvent)) {
						return -1;
					} else if (o instanceof PassEvent && !(this instanceof PassEvent)) {
						return 1;
					}
					// gleich, kann dann nicht dem sorted-Set hinzugefügt werden
					return 0;
				}
			}
		}
	}

	public void setPoint(MovePoint point, int x, int y) {
		if (point.equals(controlPoint)) {
			setControlPoint(x, y, false);
		} else if (point.equals(destinationPoint)) {
			setDestinationPoint(x, y, false);
		}
	}

	public void setControlPoint(int x, int y, boolean temp) {
		controlPointTemporary = temp;
		if (controlPoint == null) {
			controlPoint = new MovePoint(x, y);
		} else {
			controlPoint.setCurrent_x(x);
			controlPoint.setCurrent_y(y);
		}
	}

	public void setDestinationPoint(int x, int y, boolean temp) {
		destinationPointTemporary = temp;
		if (destinationPoint == null) {
			destinationPoint = new MovePoint(x, y);
		} else {
			destinationPoint.setCurrent_x(x);
			destinationPoint.setCurrent_y(y);
		}
	}

	@Override
	public String toString() {
		String str = "";
		if (destinationPoint == null) {
			str = "Laufweg nicht definiert";
		} else {
			str = "Laufweg zu Position (" + destinationPoint.getCurrent_x()
					+ "," + destinationPoint.getCurrent_y() + ")";
		}
		return str;
	}

	public int getDestinationX() {
		return destinationPoint.getCurrent_x();
	}

	public int getDestinationY() {
		return destinationPoint.getCurrent_y();
	}

	public MovePoint getControlPoint() {
		return controlPoint;
	}

	public MovePoint getDestinationPoint() {
		return destinationPoint;
	}

	public int getControlPointX() {
		return controlPoint.getCurrent_x();
	}

	public int getControlPointY() {
		return controlPoint.getCurrent_y();
	}

	public boolean isControlPointSet() {
		return !controlPointTemporary;
	}

	public boolean isDestinationPointSet() {
		return !destinationPointTemporary;
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
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

		MoveEvent other = (MoveEvent) obj;
		if (other.getControlPoint() == null ^ this.getControlPoint() == null) {
			return false;
		}

		if (other.getControlPoint() == null) {
			// hier ist dann auch this.getControllPoint == null
			if (other.getDestinationX() != this.getDestinationX()
					|| other.getDestinationY() != this.getDestinationY()
					|| other.getDelay() != this.getDelay()
					|| !other.getPlayer().equals(this.getPlayer())
					|| other.getSequenceNr() != this.getSequenceNr()) {
				return false;
			}
		} else if (other.getControlPointX() != this.getControlPointX()
				|| other.getControlPointY() != this.getControlPointY()
				|| other.getDestinationX() != this.getDestinationX()
				|| other.getDestinationY() != this.getDestinationY()
				|| other.getDelay() != this.getDelay()
				|| !other.getPlayer().equals(this.getPlayer())
				|| other.getSequenceNr() != this.getSequenceNr()) {
			return false;
		}

		return true;
	}
}