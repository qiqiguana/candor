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

package visu.handball.moves.model.animation;

import visu.handball.moves.model.HandballModel;

public class Animator extends Thread {

	/**
	 * Wie oft soll der Animator die Animation pro Sekunde aktualisieren?
	 */
	public final static int SPEED = 20;
	
	/**
	 * Liefert die Anzahl an Animator-Steps bis das Delay abgelaufen ist
	 * @param delay
	 * 
	 * @return animatorSteps
	 */
	public static int getDelayUnits(int delay) {
		//quasi halbe sekunde
		return delay * SPEED /2 ;
	}

	private HandballModel model;

	private boolean pause;

	public Animator(HandballModel model) {
		this.model = model;
		start();
	}

	@Override
	public void run() {
		while (model.getState() == HandballModel.State.ANIMATION_RUNNING
				&& !isPause()) {

			if (model.nextAnimationStep()) break;

			try {
				sleep(1000/SPEED);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public synchronized boolean isPause() {
		while (pause) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public synchronized void setPause(boolean pause) {
		this.pause = pause;
		notify();
	}

}
