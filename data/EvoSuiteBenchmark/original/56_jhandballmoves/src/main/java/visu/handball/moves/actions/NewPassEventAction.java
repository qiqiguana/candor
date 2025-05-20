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

package visu.handball.moves.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import visu.handball.moves.Main;
import visu.handball.moves.model.HandballModel;
import visu.handball.moves.model.HandballModelListener;
import visu.handball.moves.model.MoveEvent;
import visu.handball.moves.model.PassEvent;
import visu.handball.moves.resources.Resources;


public class NewPassEventAction extends AbstractAction implements
		HandballModelListener {

	private HandballModel model;

	public NewPassEventAction(HandballModel model) {
		super(Resources.getString("NewPassEventAction.name"), Main.createImageIcon( //$NON-NLS-1$
				"images/ball_small.gif", "Add")); //$NON-NLS-1$ //$NON-NLS-2$
		putValue(AbstractAction.SHORT_DESCRIPTION,
				Resources.getString("NewPassEventAction.description")); //$NON-NLS-1$
		this.model = model;
		model.addListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (model.getMarkedPlayer() == null
				|| (!model.getMarkedPlayer().hasBall() && !model
						.hasPlayerBallInNextSequence(model.getMarkedPlayer())))
			return;

		MoveEvent event = new PassEvent(model.getMarkedPlayer(), model
				.getAcutalSequenceNr() + 1, 0);

		model.addMoveEvent(event);
	}

	public void modelChanged() {
		if (model.getState() == HandballModel.State.EDIT) {
			if (model.getMarkedPlayer() != null
					&& model.getMarkedPlayer().hasBall()
					&& !model.isGoalPassSet()
					&& !model.eventDefinedAfter(model.getAcutalSequenceNr())) {
				// Prüfen ob noch kein PassEreignis für aktuelle Sequenz
				// vorhanden
				setEnabled(!model.passEventDefined());
				return;
			} else if (model.getMarkedPlayer() != null
					&& model.hasPlayerBallInNextSequence(model
							.getMarkedPlayer()) 
					&& !model.isGoalPassSet()
					&& !model.eventDefinedAfter(model.getAcutalSequenceNr())) {
				setEnabled(true);
				return;
			}
		}

		setEnabled(false);
	}
}
