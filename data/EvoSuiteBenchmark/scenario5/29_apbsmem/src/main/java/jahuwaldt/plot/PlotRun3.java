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
     *  Return an enumeration of all the plot data elements in
     *  this run.
     *
     * @return An interation of all the PlotDatum objects in this run.
     */
    public Iterator iterator() {
        return data.iterator();
    }
}
