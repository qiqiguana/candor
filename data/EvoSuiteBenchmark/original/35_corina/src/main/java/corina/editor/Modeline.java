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

import corina.Year;
import corina.Sample;
import corina.Weiserjahre;
import corina.gui.Tree;
import corina.gui.ElementsPanel;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.ChangeEvent;
import javax.swing.border.EtchedBorder;

/**
   An emacs-style modeline for an Editor's data table.

   <p>Displays the currently selected year, and that year's value,
   count, and weiserjahre up/down values.</p>

   <p>Watching for row-change-events requires implementing a
   ListSelectionListener, while watching for column-change-events
   requires implementing a TableColumnModelListener.  One Modeline
   class can implement them both, though it is incredibly
   non-intuitive.</p>

   @author Ken Harris &lt;kbh7 <i style="color: gray">at</i> cornell <i style="color: gray">dot</i> edu&gt;
   @version $Id: Modeline.java,v 1.5 2004/01/18 17:56:56 aaron Exp $
*/
public class Modeline extends JPanel
                   implements ListSelectionListener, TableColumnModelListener {

/*
  TODO:
  -- click on "year:" lets you type in a new year
  -- (need multiple labels for that?  icon, year, other stuff.  probably, but it's not hard.)

  RENAME:
  -- "modeline" is an emacs term, because emacs has modes.  corina shouldn't really
  (and doesn't) have modes, so "status bar" ("status line"?) is more appropriate.
*/

    // icon -- DISABLED until it's good for something.
    // private Tree icon;

    // label
    private JLabel label;

    // statistics
    private Statistics stats;

    // optional: table
    private JTable table;

    // the sample to get data values from
    private Sample sample;

    /** Update the modeline, when a selection change occurs.  The
	modeline will look something like this:

	<pre>
	1001: 52 [3] 2/1
	</pre>

	which means year 1001 is selected, and has an average value of
	52 for 3 samples; 2 are increasing in size, and 1 is
	decreasing. */
    private void update() {
	// get the point
	int row = table.getSelectedRow();
	int col = table.getSelectedColumn();

	// bail out if not on data
	if (col<1 || col>10) {
	    label.setText(EMPTY);
	    return;
	}

	// get year from (row,col)
	Year y = ((DecadalModel) table.getModel()).getYear(row, col);

	// get index from year
	int i = y.diff(sample.range.getStart());

	// bail out if out of range
	if (i<0 || i>=sample.data.size()) {
	    label.setText(EMPTY);
	    return;
	}

	// get modeline from year, index
	String modeline = y + ": " + sample.data.get(i);
	if (sample.count != null)
	    modeline += " [" + sample.count.get(i) + "]";
	if (sample.hasWeiserjahre())
	    modeline += " " + Weiserjahre.toString(sample, i);

	// set modeline
	label.setText(modeline);
    }

    // if i set it to "", it would have no height, either.  force it
    // to have normal line height by making this a non-empty string.
    private static final String EMPTY = " ";

    // row-change
    public void valueChanged(ListSelectionEvent e) {
	// wait until it stops
	if (e.getValueIsAdjusting())
	    return;

	update();
    }

    public void columnAdded(TableColumnModelEvent e) { }
    public void columnMarginChanged(ChangeEvent e) { }
    public void columnMoved(TableColumnModelEvent e) { }
    public void columnRemoved(TableColumnModelEvent e) { }

    // column-change
    public void columnSelectionChanged(ListSelectionEvent e) {
	// wait until it stops
	if (e.getValueIsAdjusting())
	    return;

	update();
    }

    /** Create a new Modeline, for a given data table and sample.
	@param table data table to watch for selection changes
	@param sample sample to get data values */
    public Modeline(JTable table, Sample sample) {
	// copy refs
	this.sample = sample;
	this.table = table;

	// add myself as a listener
	table.getSelectionModel().addListSelectionListener(this);
	table.getColumnModel().addColumnModelListener(this);
	// BUG: if data changes (how can this happen?), might need to change text.
	// so add myself as a samplelistener, too.

	// label, border
	label = new JLabel();
	label.setBorder(BorderFactory.createEmptyBorder());

	// icon -- DISABLED
	// icon = new Tree(sample);

	// stats, like mean sensitivity
	stats = new Statistics(sample);

	// initial text
	update();

	// pack stuff
	setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
	setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	add(Box.createHorizontalStrut(2));
	// add(icon);
	// add(Box.createHorizontalStrut(4));
	add(label);
	add(Box.createHorizontalGlue());
	add(stats);
	add(Box.createHorizontalStrut(2));
    }

    // here's a modeline for elements panels
    public Modeline(ElementsPanel ep) {
	// what it'll do: "%d samples, %d KB", or "%d of %d samples selected, %d of %d KB"
	// -> it'll need a list selection listener
    }
}
