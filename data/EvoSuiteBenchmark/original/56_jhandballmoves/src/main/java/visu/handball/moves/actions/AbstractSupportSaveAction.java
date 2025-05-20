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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import visu.handball.moves.Main;
import visu.handball.moves.model.HandballModel;
import visu.handball.moves.resources.Resources;

public abstract class AbstractSupportSaveAction extends AbstractAction {

	protected HandballModel model;

	public AbstractSupportSaveAction(HandballModel model, String string,
			ImageIcon icon, String shortDescription) {
		super(string, icon);
		putValue(AbstractAction.SHORT_DESCRIPTION, shortDescription);
		this.model = model;
	}

	public void actionPerformed(ActionEvent e) {
		if (!model.isSaved()) {

			int returncode = JOptionPane
					.showConfirmDialog(
							Main.getWindow(),
							Resources.getString("AbstractSupportSaveAction.question.notSaved.message"), //$NON-NLS-1$
							Resources.getString("AbstractSupportSaveAction.question.notSaved.title"), JOptionPane.YES_NO_CANCEL_OPTION); //$NON-NLS-1$
			switch (returncode) {
			case JOptionPane.YES_OPTION:
				// Speichern anstossen
				Main.getSaveAction().actionPerformed(null);
				break;
			case JOptionPane.NO_OPTION:
				// Benutzer will nicht speichern. Daher nix zu tun und Aktion
				// ausfuehren.
				break;
			case JOptionPane.CLOSED_OPTION:
			case JOptionPane.CANCEL_OPTION:
				// Ohne Aktion zurueck
				return;
			}

		}
		performAction();
	}

	abstract void performAction();
}