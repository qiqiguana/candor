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
import corina.SampleListener;
import corina.SampleEvent;
import corina.Weiserjahre;

/**
   Special DecadalModel for Weiserjahre.

   <p>Differences from the stock DecadalModel:</p>

   <ul>
     <li>Weiserjahre is never user-editable, so isCellEditable()
         always returns false

     <li>Also because it's never user-editable, it doesn't need to
         have an extra line if it ends on a -9 year.

     <li>It doesn't need to reference the Editor it is a part of,
         because it is a read-only view (no modified-flag to set)
   </ul>

   @author Ken Harris &lt;kbh7 <i style="color: gray">at</i> cornell <i style="color: gray">dot</i> edu&gt;
   @version $Id: WJTableModel.java,v 1.2 2004/01/18 17:56:56 aaron Exp $ */
public class WJTableModel extends DecadalModel implements SampleListener {
    /**
       Constructor, given a Sample to view.

       @param s the Sample to view
    */
    public WJTableModel(Sample s) {
	this.s = s;

	if (s != null && s.hasWeiserjahre()) // get rid of this check later
	    countRows();

	// we want to see redate-events
	s.addSampleListener(this);
    }

    /**
       Return the row count.  The Weiserjahre is not user-editable, so
       there's no need to ever have an extra (empty) line.

       @return the number of rows
    */
    public int getRowCount() {
	countRows();
	return (row_max - row_min + 1);
    }

    /**
       Return the value of the specified cell.

       @param row the row to query
       @param row the column to query
       @return the value of this cell: the decade title, the
       Weiserjahre value, or the count
    */
    public Object getValueAt(int row, int col) { 
	if (col == 0) {
	    if (row == 0)
		return s.range.getStart();
	    else if (row + row_min == 0) // special case
		return "1";
	    else
		return getYear(row, col+1);
	} if (col == 11) {
	    return getMean(row);
	} else {
	    Year y = getYear(row, col);
	    return (s.range.contains(y) ? Weiserjahre.toString(s, y) : null);
	}
    }

    /**
       Return false: no cell is editable.  Weiserjahre is always
       automatically computed.

       @param row the row to query
       @param row the column to query
       @return false, meaning that no Weiserjahre cell is editable
    */
    public boolean isCellEditable(int row, int col) {
	return false;
    }

    // watch for redate events
    public void sampleRedated(SampleEvent e) {
	fireTableDataChanged();
    }
    public void sampleDataChanged(SampleEvent e) { }
    public void sampleMetadataChanged(SampleEvent e) { }
    public void sampleElementsChanged(SampleEvent e) { }
}
