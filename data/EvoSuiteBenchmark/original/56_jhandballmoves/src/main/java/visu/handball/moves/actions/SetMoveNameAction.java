/*
 * Created on 28.05.2007
 * Created by Thomas Halm
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
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import visu.handball.moves.Main;
import visu.handball.moves.model.HandballModel;
import visu.handball.moves.resources.Resources;


public class SetMoveNameAction extends AbstractAction {

	private HandballModel model;
	
	public SetMoveNameAction(HandballModel model) {
		super(Resources.getString("SetMoveNameAction.name"), Main.createImageIcon( //$NON-NLS-1$
				"images/set_name.gif", "SetName")); //$NON-NLS-1$ //$NON-NLS-2$
		putValue(AbstractAction.SHORT_DESCRIPTION,
				Resources.getString("SetMoveNameAction.description")); //$NON-NLS-1$
		this.model = model;
	}

	public void actionPerformed(ActionEvent e) {
		JOptionPane pane = new JOptionPane(Resources.getString("SetMoveNameAction.question.title"),JOptionPane.QUESTION_MESSAGE,JOptionPane.OK_CANCEL_OPTION); //$NON-NLS-1$
		pane.setWantsInput(true);
		pane.setInitialSelectionValue(model.getMoveName());
		JDialog dialog = pane.createDialog(Main.getWindow(), Resources.getString("SetMoveNameAction.question.message")); //$NON-NLS-1$
		dialog.setVisible(true);
		
		int value = ((Integer)pane.getValue()).intValue();
		if (value != JOptionPane.CANCEL_OPTION) {
			model.setMoveName((String) pane.getInputValue());
		}
	}

}
