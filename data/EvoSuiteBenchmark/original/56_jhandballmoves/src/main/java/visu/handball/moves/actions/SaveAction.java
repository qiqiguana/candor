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
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import visu.handball.moves.Main;
import visu.handball.moves.controller.HandballFileFilter;
import visu.handball.moves.model.HandballModel;
import visu.handball.moves.resources.Resources;
import visu.handball.moves.xml.HandballModelWriter;

public class SaveAction extends AbstractAction {

	private HandballModel model;

	public SaveAction(HandballModel model) {
		super(Resources.getString("SaveAction.name"), Main.createImageIcon("images/save.gif", //$NON-NLS-1$ //$NON-NLS-2$
				"Add")); //$NON-NLS-1$
		putValue(AbstractAction.SHORT_DESCRIPTION,
				Resources.getString("SaveAction.description")); //$NON-NLS-1$
		this.model = model;

	}

	public void actionPerformed(ActionEvent e) {
		File saveFile = getSavePath();
		try {
			if (saveFile != null) {
				// wenn Spielzugname leer dann Dateiname
				if (model.getMoveName() == null || model.getMoveName().equals("")) { //$NON-NLS-1$
					model.setMoveName(saveFile.getName());
				}
				
				// Speichern
				if (saveFile.getName().endsWith(
						HandballFileFilter.EXTENSION_SERIALISATION)) {
					// mit Serialisierung speichern
					ObjectOutputStream outStr = new ObjectOutputStream(
							new BufferedOutputStream(new FileOutputStream(
									saveFile)));
					outStr.writeObject(model);
					outStr.close();
				} else {
					// als XML speichern
					HandballModelWriter writer = HandballModelWriter.getInstance(saveFile, model);
					writer.writeToXml();
				}
				//gespeicherten Zustand merken
				model.markAsSaved(model);
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	private File getSavePath() {
		String filename = File.separator + "handballMove"; //$NON-NLS-1$
		JFileChooser fc = new JFileChooser(new File(filename));
		fc.setFileFilter(new HandballFileFilter());
		fc.setAcceptAllFileFilterUsed(false);
		File selFile = null;
		if (fc.showSaveDialog(Main.getWindow()) == JFileChooser.APPROVE_OPTION) {
			selFile = fc.getSelectedFile();

			// Richtige File-Extension setzen
			String ext = ""; //$NON-NLS-1$
			String s = selFile.getName();

			int i = s.lastIndexOf('.');
			if ((i > 0) && (i < s.length() - 1)) {
				ext = s.substring(i).toLowerCase();
			}
			if (!(ext.equals(HandballFileFilter.EXTENSION_SERIALISATION)
					|| ext.equals(HandballFileFilter.EXTENSION_XML))) {
				// standardmäßig wird jetzt immer im XML-Format gespeichert
				selFile = new File(selFile.getAbsolutePath()
						+ HandballFileFilter.EXTENSION_XML);
			}
		}

		if (selFile != null && selFile.exists()) {
			int answer = JOptionPane
					.showConfirmDialog(
							Main.getWindow(),
							Resources.getString("SaveAction.question.overwrite.message", new String[] {selFile.getName()}), //$NON-NLS-1$
							Resources.getString("SaveAction.question.overwrite.title"), JOptionPane.YES_NO_OPTION); //$NON-NLS-1$
			if (answer == JOptionPane.NO_OPTION) {
				return null;
			}
		}
		return selFile;
	}

}
