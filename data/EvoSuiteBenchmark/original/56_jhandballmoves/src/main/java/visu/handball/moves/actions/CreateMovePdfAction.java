/*
 * Created on 24.10.2006
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

import java.awt.Graphics2D;
import java.io.File;
import java.io.FileOutputStream;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import visu.handball.moves.Main;
import visu.handball.moves.model.HandballModel;
import visu.handball.moves.resources.Resources;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

public class CreateMovePdfAction extends AbstractExportAction {

	public CreateMovePdfAction(HandballModel model) {
		super(Resources.getString("CreateMovePdfAction.name"), Main.createImageIcon("images/pdf_export.gif", //$NON-NLS-1$ //$NON-NLS-2$
				"Pdf"), model); //$NON-NLS-1$
		putValue(AbstractAction.SHORT_DESCRIPTION,
				Resources.getString("CreateMovePdfAction.description")); //$NON-NLS-1$
	}

	@Override
	protected void doExport() {
		// Dialog zur Auswahl Spielzug oder nur Sequenz
		String[] values = { Resources.getString("CreateMovePdfAction.option.actualSequence"), Resources.getString("CreateMovePdfAction.option.complete") }; //$NON-NLS-1$ //$NON-NLS-2$
		Object value = JOptionPane.showInputDialog(Main.getWindow(),
				Resources.getString("CreateMovePdfAction.question.export"), Resources.getString("CreateMovePdfAction.question.title"), //$NON-NLS-1$ //$NON-NLS-2$
				JOptionPane.QUESTION_MESSAGE, null, values, values[0]);

		if (value == null) {
			return;
		}

		try {
			JFileChooser chooser = new JFileChooser();

			chooser.setFileFilter(new FileFilter() {
				@Override
				public boolean accept(File f) {
					if (f.isDirectory() || f.getName().endsWith(".pdf")) { //$NON-NLS-1$
						return true;
					} else {
						return false;
					}
				}

				@Override
				public String getDescription() {
					return Resources.getString("CreateMovePdfAction.pdf.files"); //$NON-NLS-1$
				}
			});

			if (chooser.showOpenDialog(Main.getWindow()) == JFileChooser.APPROVE_OPTION) {
				File choosen = chooser.getSelectedFile();
				if (!choosen.getName().endsWith(".pdf")) { //$NON-NLS-1$
					choosen = new File(choosen.getParentFile(), choosen
							.getName()
							+ ".pdf"); //$NON-NLS-1$
				}

				if (choosen.exists()) {
					int answer = JOptionPane
							.showConfirmDialog(
									Main.getWindow(),
									Resources.getString("CreateMovePdfAction.question.overwrite.message", new String[] {choosen.getName()}), //$NON-NLS-1$
									Resources.getString("CreateMovePdfAction.question.overwrite.title"), JOptionPane.YES_NO_OPTION); //$NON-NLS-1$
					if (answer == JOptionPane.NO_OPTION) {
						return;
					}
				}

				FileOutputStream fout = new FileOutputStream(choosen);
				Document document = new Document();
				PdfWriter writer = PdfWriter.getInstance(document, fout);
				document.open();
				PdfContentByte cb = writer.getDirectContent();

				if (value == values[0]) {
					drawPage(cb, model.getAcutalSequenceNr());
				} else if (value == values[1]) {
					for (int i = 0; i <= model.getHighestSequenceNumber(); i++) {
						drawPage(cb, i);
						if (i < model.getHighestSequenceNumber()) {
							document.newPage();
						}
					}

				}

				document.close();
				fout.close();

			}

		} catch (Exception e) {
			JOptionPane.showMessageDialog(Main.getWindow(),
					Resources.getString("CreateMovePdfAction.error.message") //$NON-NLS-1$
							+ e.getMessage(), Resources.getString("CreateMovePdfAction.error.title"), //$NON-NLS-1$
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

	private void drawPage(PdfContentByte cb, int seq) {
		Graphics2D g2d = cb.createGraphics(PageSize.A4.width(), PageSize.A4
				.height());

		drawHeader(g2d, 30, 30, PageSize.A4.width() - 100);
		drawSequence(g2d, seq);
	}
}
