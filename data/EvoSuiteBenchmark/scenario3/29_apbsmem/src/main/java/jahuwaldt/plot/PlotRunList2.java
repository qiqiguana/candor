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
     *  Remove the plot run object at the specified position in
     *  this run list.  Shifts any subsequent run elements
     *  to the left (subtracts one from their indices).  Returns the
     *  run element that was removed from this run list.
     *
     * @param index The index of the plot run element to remove.
     * @return The PlotRun object previously at the specified position.
     */
    public Object remove(int index);
}
