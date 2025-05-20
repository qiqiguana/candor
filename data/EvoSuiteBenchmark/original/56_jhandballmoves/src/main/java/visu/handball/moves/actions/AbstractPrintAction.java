/*
 * Created on 03.10.2006
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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.Icon;

import visu.handball.moves.model.HandballModel;

public abstract class AbstractPrintAction extends AbstractExportAction {

	public class SequencePagePrintable implements Printable {

		private int sequence;

		public SequencePagePrintable(int sequence) {
			this.sequence = sequence;
		}

		public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
				throws PrinterException {
			Graphics2D g2d = drawHeader(graphics, pageFormat.getImageableX(),
					pageFormat.getImageableY(), pageFormat.getImageableWidth());

			drawSequence(g2d, sequence);

			return Printable.PAGE_EXISTS;
		}

	}

	public AbstractPrintAction(String name, Icon icon, HandballModel model) {
		super(name, icon, model);
	}

	@Override
	protected void doExport() {
		PrinterJob job = PrinterJob.getPrinterJob();
		Book book = new Book();

		// book füllen
		fillBook(book, job.defaultPage());

		job.setPageable(book);

		if (job.printDialog()) {
			try {
				job.print();
			} catch (PrinterException e1) {
				e1.printStackTrace();
			}
		}
	}

	abstract protected void fillBook(Book book, PageFormat format);

}
