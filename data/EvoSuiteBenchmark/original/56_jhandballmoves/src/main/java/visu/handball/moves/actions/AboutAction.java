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
import visu.handball.moves.resources.Resources;
import visu.handball.moves.views.AboutDialog;


/**
 * Aktion zum Starten des "Über dieses Programm"-Fenster
 * @author tommy
 *
 */
public class AboutAction extends AbstractAction {

	/**
	 * Konstruktor
	 * 
	 */
	public AboutAction() {
		super(Resources.getString("AboutAction.name"), Main.createImageIcon("images/icon_small.gif", "About")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		putValue(AbstractAction.SHORT_DESCRIPTION, Resources.getString("AboutAction.description")); //$NON-NLS-1$
	}

	public void actionPerformed(ActionEvent e) {
		new AboutDialog().setVisible(true);
	}
}
