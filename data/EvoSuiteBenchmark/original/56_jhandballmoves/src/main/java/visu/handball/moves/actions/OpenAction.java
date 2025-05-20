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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import visu.handball.moves.Main;
import visu.handball.moves.controller.HandballFileFilter;
import visu.handball.moves.model.HandballModel;
import visu.handball.moves.resources.Resources;
import visu.handball.moves.xml.HandballModelReader;

public class OpenAction extends AbstractSupportSaveAction {

	public OpenAction(HandballModel model) {
		super(model, Resources.getString("OpenAction.name"), Main.createImageIcon("images/load.gif", //$NON-NLS-1$ //$NON-NLS-2$
				"LOAD"), Resources.getString("OpenAction.description")); //$NON-NLS-1$ //$NON-NLS-2$
	}

	private File getOpenPath() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new HandballFileFilter());
		File selFile = null;
		if (fc.showOpenDialog(Main.getWindow()) == JFileChooser.APPROVE_OPTION) {
			selFile = fc.getSelectedFile();
		}
		return selFile;
	}

	@Override
	void performAction() {
		File loadFile = getOpenPath();
		try {
			if (loadFile != null) {
				HandballModel loadedModel = null;
				//nur wenn explizit Serialisierungs-Dateiendung per Serialisierung laden
				if (loadFile.getName().endsWith(HandballFileFilter.EXTENSION_SERIALISATION)) {
					ObjectInputStream inStr = new ObjectInputStream(
							new BufferedInputStream(new FileInputStream(
									loadFile)));
					loadedModel = (HandballModel) inStr.readObject();
					inStr.close();
					if (loadedModel.getMoveName() == null) {
						loadedModel.setMoveName(loadFile.getName());
					}
				} else {
					//DEFAULT: XML laden
					HandballModelReader reader = HandballModelReader
					.getInstance(loadFile);
					loadedModel = reader.readFromXml();
				}
				model.initWithLoadedModel(loadedModel);
			}
		} catch (InvalidClassException e1) {
			JOptionPane
					.showMessageDialog(
							Main.getWindow(),
							Resources.getString("OpenAction.error.message"), //$NON-NLS-1$
							Resources.getString("OpenAction.error.title"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}

	}
}
