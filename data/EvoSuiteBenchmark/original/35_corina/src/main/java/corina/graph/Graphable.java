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

package corina.graph;

import corina.Year;

import java.util.List;

/**
   An interface for classes that can be graphed.  To be
   <code>Graphable</code>, an object must have a start
   <code>Year</code>, and an <code>List</code> of
   <code>Number</code>s.<p>

   Graphs must now also be able to suggest their vertical scale, which
   getScale() returns.  A scale of 1.0 means a datum of 100 is 100
   pixels above the baseline on the screen, or about 1"; scale of 0.1
   means that same point would be 10 pixels above the baseline, or
   0.1".  This is useful for manipulations like indexing, where the
   data becomes parts-per-thousand instead of
   hundredths-of-a-millimeter.<p>

   @author Ken Harris &lt;kbh7 <i style="color: gray">at</i> cornell <i style="color: gray">dot</i> edu&gt;
   @version $Id: Graphable.java,v 1.4 2006/04/27 03:33:28 lucasmo Exp $
*/
public interface Graphable {
    /**
       Get the data to graph, as a <code>List</code> of
       <code>Number</code>s.

       @return data to graph
    */
    public List getData();
    // THIS IS WHAT'S HOLDING ME BACK.
    // it's a list because sample holds a list for editing, which forces graphable to take a list,
    // which forces index to use a list, which just sucks everywhere.  i still want to be able
    // to edit a sample and have it updated, so a plain copy won't work -- is this possible?

    /**
       Get the start of the range to graph.

       @return start Year of data
    */
    public Year getStart();

    /**
       Get the default vertical scale; the data points are multiplied
       by this value before being graphed.

       @return default vertical scale factor
    */
    public float getScale();
}
