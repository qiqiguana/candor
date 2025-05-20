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

import java.awt.geom.PathIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import visu.handball.moves.model.HandballModel;
import visu.handball.moves.model.MoveEvent;
import visu.handball.moves.model.PassEvent;

public class AnimationModel {

	private HandballModel model;

	/**
	 * Events einer Sequenznummer
	 */
	private List<MoveEvent> events;

	private boolean onlyOneSequence;

	private HashMap<MoveEvent, PathIterator> pathIters;

	private PassEvent sequenzPassEvent;

	public AnimationModel(MoveEvent startEvent, HandballModel model,
			boolean onlyOneSequence) {
		this.model = model;
		events = new ArrayList<MoveEvent>();
		pathIters = new HashMap<MoveEvent, PathIterator>();
		initEventList(startEvent);
		this.onlyOneSequence = onlyOneSequence;
	}

	private void initEventList(MoveEvent startEvent) {
		events.clear();
		pathIters.clear();
		int sequenz = startEvent.getSequenceNr();
		sequenzPassEvent = null;
		for (MoveEvent event : model.getEvents()) {
			if (event.getSequenceNr() == sequenz) {
				events.add(event);

				if (event instanceof PassEvent) {
					// Initialisierung bei PassEvent
					if (events.size() == 1
							&& events.get(0) instanceof PassEvent) {
						// Pass animieren
						sequenzPassEvent = (PassEvent) event;

						if (sequenzPassEvent.getDestinationPlayer() != null
								|| sequenzPassEvent.isGoalPass()) {
							pathIters.put(event, new PassLineIterator(
									sequenzPassEvent));
						}
					}
				} else {
					pathIters.put(event, new CurveIterator(event));
				}
			}

			if (event.getSequenceNr() > sequenz) {
				break;
			}
		}

	}

	public boolean nextStep() {
		boolean allDone = true;
		for (MoveEvent event : events) {
			if (!pathIters.get(event).isDone()) {
				allDone = false;
				double[] corrs = new double[2];
				pathIters.get(event).currentSegment(corrs);
				if (event instanceof PassEvent) {
					model.setBallOwnerSilent(null);
					model.moveBallTo((int) Math.round(corrs[0]), (int) Math
							.round(corrs[1]));
				} else {
					event.getPlayer().setCurrent_x((int) Math.round(corrs[0]));
					event.getPlayer().setCurrent_y((int) Math.round(corrs[1]));
				}
				pathIters.get(event).next();
			}
		}

		if (allDone && sequenzPassEvent != null) {
			if (!sequenzPassEvent.isGoalPass()) {
				model.setBallOwner(sequenzPassEvent.getDestinationPlayer());
			} else {
				model.setBallOwner(null);
				model.moveBallTo(sequenzPassEvent.getDestinationX(),
						sequenzPassEvent.getDestinationY());
			}
		}

		if (allDone && !onlyOneSequence) {
			// wenn noch weiteren Sequenzen vorhanden sind diese abspielen
			int index = model.getEvents()
					.indexOf(events.get(events.size() - 1)) + 1;
			if (index < model.getEvents().size()) {
				MoveEvent event = model.getEvents().get(index);
				initEventList(event);
				allDone = false;
			}
		}

		// liefer true wenn alles zu Ende
		return allDone;
	}

	public boolean isOnlyOneSequence() {
		return onlyOneSequence;
	}

}
