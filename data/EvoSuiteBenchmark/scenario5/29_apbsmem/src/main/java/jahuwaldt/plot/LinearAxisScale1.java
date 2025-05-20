package jahuwaldt.plot;

/**
 *  <p> This object provides linear scaling for plot axes.
 *  </p>
 *
 *  <p>  Modified by:  Joseph A. Huwaldt  </p>
 *
 * @author Joseph A. Huwaldt   Date:  September 13, 2000
 * @version January 10, 2001
 */
public class LinearAxisScale extends Object implements PlotAxisScale {

    /**
     *  Method that returns the default upper bounds for
     *  this axis scale.  Returns 1.0.
     */
    public double upperBounds() {
        return 1.0;
    }
}
