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
// Copyright 2003 Ken Harris <kbh7@cornell.edu>
//

package corina.index;

import java.util.ArrayList;
import java.util.List;

import corina.Sample;
import corina.core.App;
import corina.util.StringUtils;

/**
    Run a set of Indexes on a single Sample.  This runs a set of
    frequently-used indexes:

    <ul>
        <li>Horizontal (polynomial degree 0)
        <li>Linear (polynomial degree 1)
        <li>Polynomial degree 2
        <li>Polynomial degree 3
        <li>Polynomial degree 4
        <li>Polynomial degree 5
        <li>Polynomial degree 6
        <li>Negative exponential
        <li>Floating average (11-year window)
        <li>High-pass filter (configurable, default 1-2-4-2-1)
        <li>Cubic spline (configurable s-value, default 1e-16)
    </ul>

    <h2>Left to do:</h2>
    <ul>
        <li>move run() body into constructor; there's no reason for this to be Runnable
        <li>document corina.index.polydegs
        <li>is a public list really the best interface?
    </ul>

   @author Ken Harris &lt;kbh7 <i style="color: gray">at</i> cornell <i style="color: gray">dot</i> edu&gt;
   @version $Id: IndexSet.java,v 1.4 2005/01/24 03:09:30 aaron Exp $
*/

public class IndexSet implements Runnable {
    /** The indexes. */
    public List indexes = new ArrayList();

    /**
        Make a new IndexSet.

        @param sample the Sample to index
    */
    public IndexSet(Sample sample) {
        // horizontal
        indexes.add(new Horizontal(sample));

        // polynomial fits
        String degreesToUse = App.prefs.getPref("corina.index.polydegs");
        if (degreesToUse == null)
            degreesToUse = "1 2 3 4 5 6";
        int polydegs[] = StringUtils.extractInts(degreesToUse);
        for (int i=0; i<polydegs.length; i++)
            indexes.add(new Polynomial(sample, polydegs[i]));

        // exponential, floating, highpass, and cubicspline
        indexes.add(new Exponential(sample));
        indexes.add(new Floating(sample));
        indexes.add(new HighPass(sample));
        indexes.add(new CubicSpline(sample));
    }

    /**
        Construct a new IndexSet, using a proxy dataset.

        @param sample the Sample to index
        @param proxy the proxy dataset to use for the indexing curve
    */
    public IndexSet(Sample sample, Sample proxy) {
        this(sample);
        for (int i=0; i<indexes.size(); i++)
            ((Index) indexes.get(i)).setProxy(proxy);
    }

    /** Run all the indexes. */
    public void run() {
        // i put this in run(), so it could be threaded, but is it worth it?
        // -- for 100yr sample x 10 indexes, 900mhz athlon: 30-40ms.
        // so no, it's probably not worth it.
        // TODO: put this in the constructor.
        for (int i=0; i<indexes.size(); i++)
            ((Index) indexes.get(i)).run();
    }
}
