//
// This file is part of Corina.
// 
// Corina is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
// 
// Corina is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
// 
// You should have received a copy of the GNU General Public License
// along with Corina; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
//
// Copyright 2001 Ken Harris <kbh7@cornell.edu>
//

package corina.editor;

import corina.Sample;
import corina.Element;
import corina.Year;
import corina.Weiserjahre;
import corina.util.StringUtils;
import corina.print.Line;
import corina.print.EmptyLine;
import corina.print.TextLine;
import corina.print.ByLine;
import corina.print.ThinLine;
import corina.print.TabbedLineFactory;
import corina.print.Printer;

import java.io.IOException;
import java.io.FileNotFoundException;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import java.text.DecimalFormat;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.print.PageFormat;

/**
 A Printer for Samples.

 <p>A header is printed, then data, metadata, Weiserjahre, and
 elements sections.  There's also a constructor which lets you print
 only some sections.</p>

 <p>Elements are printed as the browser would show them, with
 various metadata in columns (ID, pith, range, terminal).  Footnotes
 are used to indicate if a sample in the elements list couldn't be
 loaded; different footnotes are used for file-not-found and
 file-couldn't-be-loaded.</p>

 @author Ken Harris &lt;kbh7 <i style="color: gray">at</i> cornell <i style="color: gray">dot</i> edu&gt;
 @version $Id: SamplePrinter.java,v 1.9 2006/02/14 19:37:12 lucasmo Exp $
 */
public class SamplePrinter extends Printer {

	// TO DO:
	// -- javadoc: sample output?
	// -- WeiserjahreLine doesn't use TabbedLineFactory yet

	/**
	 Make a new SamplePrinter which prints all sections of the
	 sample.

	 @param sample the Sample to print
	 */
	public SamplePrinter(Sample sample) {
		this(sample, true, true, true, true);
	}

	/**
	 Make a new SamplePrinter which prints only some sections of the
	 sample.

	 @param sample the Sample to print
	 @param data if true, print the data section
	 @param metadata if true, print the metadata section
	 @param weiserjahre if true, print the Weiserjahre section
	 @param elements if true, print the elements section
	 */
	public SamplePrinter(Sample sample, boolean data, boolean metadata,
			boolean weiserjahre, boolean elements) {
		// save a ref to the sample
		this.s = sample;

		// the print*() methods below each add stuff to |lines|

		// header
		printHeader();

		// data
		if (data) {
			if (s.isSummed())
				printSummedData();
			else
				printRawData();
		}

		// metadata
		if (metadata)
			printMetadata();

		// weiserjahre
		if (weiserjahre)
			if (s.hasWeiserjahre())
				printWeiserjahre();

		// elements
		if (elements)
			if (s.elements != null)
				printElements();
	}

	//
	// PRINTING METHODS -- these construct the lines to print
	//

	private void printHeader() {
		lines.add(new TextLine(s.meta.get("title").toString(),
				Line.SECTION_SIZE));
		lines.add(new ByLine());
		lines.add(new EmptyLine());

		// print radius, avg width, but only for non-indexed samples
		float radius = s.computeRadius() / 1000f;
		float average = radius / s.data.size();
		DecimalFormat df = new DecimalFormat("0.000"); // to 3 places
		if (!s.isIndexed()) {
			// FIXME: i18n!
			// FIXME: all stats?
			lines.add(new TextLine("Radius: " + df.format(radius) + " cm, "
					+ "Average ring width: " + df.format(average) + " cm"));
			lines.add(new EmptyLine());
		}
	}

	private void printRawData() {
		String spec = "> 5% ";
		for (int i = 0; i < 10; i++)
			spec += "9.5% <";
		TabbedLineFactory f = new TabbedLineFactory(spec);

		// header
		lines.add(f.makeLine("\t0\t1\t2\t3\t4\t5\t6\t7\t8\t9"));
		lines.add(new EmptyLine());

		// i really want a pure-data decade model here!

		for (Year y = s.range.getStart(); s.range.contains(y); y = y.add(1)) {
			if (!y.equals(s.range.getStart()) && y.column() != 0)
				continue;

			String data = y.toString();

			// if first row, find first year -- REFACTOR
			Year fake = y;
			while (fake.column() != 0)
				fake = fake.add(-1);

			// loop through years
			for (int i = 0; i < 10; i++) {
				if (!s.range.contains(fake.add(i)))
					data += "\t ";
				else
					data += "\t"
							+ s.data.get(fake.add(i).diff(s.range.getStart()));
			}

			lines.add(f.makeLine(data));
			lines.add(new EmptyLine());
		}
	}

	private void printSummedData() {
		// -- (no header line)

		// i really want a pure-data decade model here!

		// build spec
		String spec = "> 4% ";
		for (int i = 0; i < 10; i++)
			spec += "5.7% <"; // data
		spec += " 5% ";
		for (int i = 0; i < 10; i++)
			spec += "3.4% <"; // count

		// make line factory
		TabbedLineFactory f = new TabbedLineFactory(spec);
		f.setFont(new Font("serif", Font.PLAIN, 9));

		{
			// add header line?
			// lines.add(f.makeLine("Decade \t 0 \t 1 \t 2 \t 3 \t 4 \t 5 \t 6 \t 7 \t 8 \t 9 \t " +
			// "0 \t 1 \t 2 \t 3 \t 4 \t 5 \t 6 \t 7 \t 8 \t 9"));

			// data
			for (Year y = s.range.getStart(); s.range.contains(y); y = y.add(1)) {
				if (!y.equals(s.range.getStart()) && y.column() != 0)
					continue;

				Year decade = y;
				while (decade.column() != 0)
					decade = decade.add(-1);

				String vals = String.valueOf(y);

				// data
				for (int i = 0; i < 10; i++) {
					vals += "\t";
					if (s.range.contains(decade.add(i)))
						vals += ((Number) s.data.get(decade.add(i).diff(
								s.range.getStart()))).intValue();
				}
				// count
				for (int i = 0; i < 10; i++) {
					vals += "\t";
					if (s.range.contains(decade.add(i)))
						vals += ((Number) s.count.get(decade.add(i).diff(
								s.range.getStart()))).intValue();
				}
				lines.add(f.makeLine(vals));
			}
		}

		// extra data for summed files
		lines.add(new TextLine("Number of samples in data set: "
				+ (s.elements == null ? "unknown" : String.valueOf(s.elements
						.size()))));
		lines
				.add(new TextLine("Number of rings in data set: "
						+ s.countRings()));
		lines.add(new TextLine("Length of data set: " + s.range.span()
				+ " years"));
		// TODO: line up these 3 values?
		/* i'd think it might be easier on the eyes just to make a bullet list with units:
		 * 601 intervals with >3 samples
		 * 125 significant intervals (12.8%)
		 */
		// (and when that's done, i18n)
		lines.add(new EmptyLine());
	}

	private void printMetadata() {
		if (s.meta.containsKey("id"))
			lines.add(new TextLine("ID Number " + s.meta.get("id")));
		if (s.meta.containsKey("title"))
			lines.add(new TextLine("Title of sample: " + s.meta.get("title")));
		lines.add(new TextLine(s.isAbsolute() ? "Absolutely dated"
				: "Relatively dated"));
		if (s.meta.containsKey("unmeas_pre"))
			lines.add(new TextLine(s.meta.get("unmeas_pre")
					+ " unmeasured rings at beginning of sample."));
		if (s.meta.containsKey("unmeas_post"))
			lines.add(new TextLine(s.meta.get("unmeas_post")
					+ " unmeasured rings at end of sample."));
		if (s.meta.containsKey("filename"))
			lines.add(new TextLine("File saved as " + s.meta.get("filename")));

		// - comments -- loop
		if (s.meta.containsKey("comments")) {
			String comments[] = StringUtils.splitByLines((String) s.meta
					.get("comments"));
			for (int i = 0; i < comments.length; i++)
				lines.add(new TextLine("Comments: " + comments[i]));
			// this repeats "Comments:" every line -- we don't really want that, do we?
			// no, TODO: put "Comments:" on its own line, then indent each
			// following line using spaces.
		}

		if (s.meta.containsKey("type"))
			lines.add(new TextLine("Type of sample " + s.meta.get("type")));
		if (s.meta.containsKey("species"))
			lines.add(new TextLine("Species: " + s.meta.get("species")));
		// TODO: look up species name (if it's a code)
		if (s.meta.containsKey("format")) { // use a switch?
			if (s.meta.get("format").equals("R"))
				lines.add(new TextLine("Raw format"));
			else if (s.isIndexed())
				lines.add(new TextLine("Indexed format"));
			else
				lines.add(new TextLine("Unknown format"));
		}
		if (s.meta.containsKey("sapwood"))
			lines.add(new TextLine(s.meta.get("sapwood") + " sapwood rings."));
		if (s.meta.containsKey("pith")) {
			String p = (String) s.meta.get("pith");
			if (p.equals("P"))
				lines.add(new TextLine("Pith present and datable"));
			else if (p.equals("*"))
				lines.add(new TextLine("Pith present but undatable"));
			else if (p.equals("N")) // uppercase only?
				lines.add(new TextLine("No pith present"));
			else
				lines.add(new TextLine("Unknown pith"));
		}
		if (s.meta.containsKey("terminal"))
			lines.add(new TextLine("Last ring measured "
					+ s.meta.get("terminal")));
		if (s.meta.containsKey("continuous")) {
			String c = (String) s.meta.get("continuous");
			if (c.equals("C")) // uppercase only?
				lines.add(new TextLine("Last ring measured is continuous"));
			else if (c.equals("R")) // uppercase only?
				lines.add(new TextLine(
						"Last ring measured is partially continuous"));
		}
		if (s.meta.containsKey("quality"))
			lines.add(new TextLine("The quality of the sample is "
					+ s.meta.get("quality")));

		lines.add(new EmptyLine());
	}

	private void printWeiserjahre() {
		lines.add(new TextLine("Weiserjahre data", Line.SECTION_SIZE));

		for (Year y = s.range.getStart(); s.range.contains(y); y = y.add(1)) {
			if (!y.equals(s.range.getStart()) && y.column() != 0)
				continue;
			lines.add(new WeiserjahreLine(y));
		}
		// -- use "x/y", with |x| right-aligned and |y| left-aligned

		lines.add(new EmptyLine());

		// 3 more lines: wj summary
		int sigs = s.countSignificantIntervals();
		int threes = s.count3SampleIntervals();
		lines
				.add(new TextLine("Number of intervals with >3 samples: "
						+ threes));
		float pct = (float) sigs / (float) threes;
		DecimalFormat fmt = new DecimalFormat("0.0%");
		lines.add(new TextLine("Number of significant intervals: " + sigs
				+ " (" + fmt.format(pct) + " of intervals with >3 samples)"));
		lines.add(new EmptyLine());
	}

	// (\u2020 and \u2021 (DAGGER, DOUBLE DAGGER) look the best,
	// but apparently they're not common enough to use for this.
	// so i'll just use * and ** for now.)
	// TODO: can i use them on Macs?
	private static final String footnoteSymbol1 = "*";
	private static final String footnoteSymbol2 = "**";

	private void printElements() {
		lines.add(new TextLine(
				"This data set is composed of the following files:"));

		// fields: (id *filename pith unmeas_pre range unmeas_post terminal)
		// (actually, the range argument should be aligned to the "-", not centered ("^"))
		TabbedLineFactory f = new TabbedLineFactory(
				"> 12% <> 58% ^ 7% ^ 8% ^ 8% ^ 7% <");

		// add table header -- skip "unmeas/pre/post" headers
		// (not enough space, and it's fairly obvious, anyway)
		lines
				.add(f
						.makeLine("ID \t \t Filename \t Pith \t \t Range \t \t Terminal")); // FIXME: i18n me!
		// lines.add(new ThinLine(0f, 1f)); -- see problem with thinline, below

		// write out all elements
		for (int i = 0; i < s.elements.size(); i++) {
			Element e = (Element) s.elements.get(i);

			if (e.details == null) {
				try {
					e.loadMeta();
				} catch (FileNotFoundException fnfe) {
					e.error = fnfe;
					footnote = true;
				} catch (IOException ioe) {
					e.error = ioe;
					footnote2 = true;
				}
			}

			// not loaded?
			if (e.details == null) {
				String mark = ((e.error instanceof FileNotFoundException) ? footnoteSymbol1
						: footnoteSymbol2);
				mark = mark + " ";
				lines.add(f.makeLine("\t " + mark + " \t " + e.filename
						+ " \t \t \t \t "));
			} else {
				String x = "";

				x += (e.details.containsKey("id") ? e.details.get("id")
						.toString() : "");
				x += "\t";
				x += ""; // no footnote
				x += "\t";
				x += (e.details.containsKey("filename") ? e.details.get(
						"filename").toString() : ""); // title?
				x += "\t";
				x += (e.details.containsKey("pith") ? "+"
						+ e.details.get("pith").toString() : "");
				x += "\t";
				x += (e.details.containsKey("unmeas_pre") ? "+"
						+ e.details.get("unmeas_pre").toString() : "");
				x += "\t";
				x += e.getRange().toString();
				x += "\t";
				x += (e.details.containsKey("unmeas_post") ? "+"
						+ e.details.get("unmeas_post").toString() : "");
				x += "\t";
				x += (e.details.containsKey("terminal") ? e.details.get(
						"terminal").toString() : "");

				lines.add(f.makeLine(x));

				// add a horizontal rule every 5th line here?
				// if (i % 5 == 4)
				//    lines.add(new ThinLine(0f, 1f));
				// this might be nice BUT: ThinLine isn't zero-height,
				// nor is this centered vertically between text lines.
			}
		}

		// if one or more files couldn't load, print the correct footnotes for it.
		if (footnote || footnote2)
			lines.add(new EmptyLine());
		if (footnote)
			lines
					.add(new TextLine(
							footnoteSymbol1
									+ " This file wasn't found; it was probably moved, renamed, or deleted."));
		if (footnote2)
			lines
					.add(new TextLine(
							footnoteSymbol2
									+ " This file couldn't be loaded; it might have been corrupted."));
	}

	//
	// old printing layer: REFACTOR to use TabbedLineFactory
	//

	private class WeiserjahreLine implements Line {
		private Year decade;

		public WeiserjahreLine(Year decade) {
			this.decade = decade;
		}

		public void print(Graphics g, PageFormat pf, float y) {
			// baseline
			float baseline = (float) (y + height(g));
			Graphics2D g2 = (Graphics2D) g; // DO I NEED THIS?  why?

			g2.setFont(new Font("serif", Font.PLAIN, 9));

			float col1 = 0.25f * 72; // 3/4"?
			float width = ((float) pf.getImageableWidth() - col1) / 10;

			// decade
			g2.drawString(decade.toString(), (float) pf.getImageableX(),
					baseline);

			// (snap decade back to real decade, now -- BREAKS IMMUTABILITY, if i ever thought i had that.)
			Year fake = decade;
			while (fake.column() != 0)
				fake = fake.add(-1);

			// loop through years
			for (int i = 0; i < 10; i++) {
				// don't draw it, if it ain't there
				if (!s.range.contains(fake.add(i)))
					continue;

				// draw right-aligned "x1" + left-aligned "/x2"
				float position = (float) (pf.getImageableX() + col1 + width
						* (i + 0.5)); // the "/" starts here

				String x1 = ((Number) s.incr.get(fake.add(i).diff(
						s.range.getStart()))).toString();
				String x2 = ((Number) s.decr.get(fake.add(i).diff(
						s.range.getStart()))).toString();

				// separatar char -- (this seems clunky to me)
				String c = (Weiserjahre.isSignificant(s, fake.add(i).diff(
						s.range.getStart())) ? Weiserjahre.SIGNIFICANT
						: Weiserjahre.INSIGNIFICANT);
				int c_width = g.getFontMetrics().stringWidth(c);

				// right-aligned x1
				int w = g.getFontMetrics().stringWidth(x1);
				g2.drawString(x1, (float) (position - w - c_width / 2),
						baseline);

				// centered c
				g2.drawString(c, (float) (position - c_width / 2), baseline);

				// left-aligned x2
				g2.drawString(x2, (float) (position + c_width / 2), baseline);
			}
		}

		public int height(Graphics g) {
			return g.getFontMetrics(new Font("serif", Font.PLAIN, 9))
					.getHeight();
		}
	}

	// true if a file wasn't found where it was expected
	private boolean footnote = false;

	// true if a file couldn't be loaded
	private boolean footnote2 = false;

	// sample to print
	private Sample s;
}
