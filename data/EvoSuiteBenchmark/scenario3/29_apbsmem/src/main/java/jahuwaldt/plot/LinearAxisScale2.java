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
     *  Method that returns an AxisLimitData object that contains
     *  the preferred axis limits and tick mark spacing for the
     *  specified range of data values for this linear axis scale.
     *
     * @param aLB The lower bounds of the data plotted on this axis.
     * @param aUB The upper bounds of the data plotted on this axis.
     */
    public AxisLimitData findGoodLimits(double aLB, double aUB);
}
