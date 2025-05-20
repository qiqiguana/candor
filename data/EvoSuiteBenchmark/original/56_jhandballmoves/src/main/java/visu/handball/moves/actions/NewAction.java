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

import visu.handball.moves.Main;
import visu.handball.moves.model.HandballModel;
import visu.handball.moves.resources.Resources;


public class NewAction extends AbstractSupportSaveAction {

	public NewAction(HandballModel model) {
		super(model, Resources.getString("NewAction.name"), Main.createImageIcon("images/new.gif", //$NON-NLS-1$ //$NON-NLS-2$
				"NEU"), Resources.getString("NewAction.description")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	void performAction() {
		model.initModel();
		Main.getSetNameAction().actionPerformed(new ActionEvent(this,ActionEvent.ACTION_FIRST,"setName")); //$NON-NLS-1$
	}

}
