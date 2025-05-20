package jahuwaldt.plot;

import java.awt.Color;
import java.util.*;

/**
 *  <p> This class represents a list of runs of data in a plot.
 *      A run is an array or list of PlotDatum objects.  A run
 *      list is an array or list of runs.
 *  </p>
 *
 *  <p>  Modified by:  Joseph A. Huwaldt  </p>
 *
 * @author Joseph A. Huwaldt   Date:  September 13, 2000
 * @version November 20, 2000
 */
public class PlotRunList extends AbstractList implements Cloneable, java.io.Serializable {

    /**
     *  Returns the number of PlotRun objects in this run list.
     *
     * @return The number of plot run objects in this run list.
     */
    public int size();
}
