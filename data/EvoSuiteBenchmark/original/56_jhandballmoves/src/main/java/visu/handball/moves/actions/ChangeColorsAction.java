/*
 * Created on 26.01.2007
 * Created by Thomas Halm
 * Copyright (C) 2007  Richard Doerfler, Thomas Halm
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
import visu.handball.moves.resources.Resources;
import visu.handball.moves.views.ColorSettingsDialog;

public class ChangeColorsAction extends AbstractAction {

	public ChangeColorsAction() {
		super(Resources.getString("ChangeColorsAction.name"), Main.createImageIcon("images/colors.gif", //$NON-NLS-1$ //$NON-NLS-2$
				"Change colors...")); //$NON-NLS-1$
		putValue(AbstractAction.SHORT_DESCRIPTION,
				Resources.getString("ChangeColorsAction.description")); //$NON-NLS-1$
	}

	public void actionPerformed(ActionEvent e) {
		new ColorSettingsDialog();
	}
}
