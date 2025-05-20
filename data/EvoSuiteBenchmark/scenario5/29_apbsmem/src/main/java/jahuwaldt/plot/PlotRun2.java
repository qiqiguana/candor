package jahuwaldt.plot;

import java.awt.Color;
import java.util.*;

/**
 *  <p> This class represents a single run of data in a plot.
 *      A run is an array or list of PlotDatum objects.
 *  </p>
 *
 *  <p>  Modified by:  Joseph A. Huwaldt  </p>
 *
 * @author Joseph A. Huwaldt   Date:  September 13, 2000
 * @version December 12, 2000
 */
public class PlotRun extends AbstractList implements Cloneable, java.io.Serializable {

    /**
     *  Remove the plot data object at the specified position in
     *  this run.  Shifts any subsequent data elements
     *  to the left (subtracts one from their indices).  Returns the
     *  data element that was removed from this run.
     *
     * @param index The index of the plot data element to remove.
     * @return The PlotDatum object previously at the specified position.
     */
    public Object remove(int index) {
        return data.remove(index);
    }
}
