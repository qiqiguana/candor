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
import javax.swing.JOptionPane;

import visu.handball.moves.Main;
import visu.handball.moves.model.HandballModel;
import visu.handball.moves.model.HandballModelListener;


public class DeleteEventAction extends AbstractAction implements
		HandballModelListener {

	private HandballModel model;

	public DeleteEventAction(HandballModel model) {
		super("Ausgewähltes Event löschen", Main.createImageIcon(
				"images/delete_event.gif", "Delete"));
		putValue(AbstractAction.SHORT_DESCRIPTION,
				"Löscht das ausgewählte Event und alle folgenden Events");
		this.model = model;
		model.addListener(this);
	}

	public void actionPerformed(ActionEvent e) {

		int returncode = JOptionPane
				.showConfirmDialog(
						Main.getWindow(),
						"Wollen Sie wirklich dieses Event und alle folgenden Events löschen?",
						"Frage:", JOptionPane.YES_NO_OPTION);
		switch (returncode) {
		case JOptionPane.YES_OPTION:
			// Benutzer will löschen
			model.removeEventsAfter();
			break;
		case JOptionPane.NO_OPTION:
		case JOptionPane.CLOSED_OPTION:
			// Benutzer will nicht löschen
			return;
		}

	}

	public void modelChanged() {
		if (model.getState() == HandballModel.State.EDIT) {
			if (model.getActualMoveEvent() != null) {
				// Ist ein Event ausgew�hlt?
				setEnabled(true);
				return;
			}
		}
		setEnabled(false);
	}
}
