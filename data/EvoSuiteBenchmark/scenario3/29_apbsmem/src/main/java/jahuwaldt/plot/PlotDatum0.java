package jahuwaldt.plot;

import java.awt.Color;

/**
 *  <p> This class represents a single data point on a plot
 *      and includes all the information required by each point.
 *  </p>
 *
 *  <p>  Modified by:  Joseph A. Huwaldt  </p>
 *
 * @author Joseph A. Huwaldt   Date:  June 1, 2000
 * @version November 20, 2000
 */
public class PlotDatum extends Object implements Cloneable, java.io.Serializable {

    /**
     *  Returns true if this data point has an error bar.
     */
    public boolean hasErrorBar();
}
