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
     *  Find the position and size (in screen coordinates) of tick
     *  marks for a given axis scale.
     *
     * @param quantum Tick mark step size for the axis using this scale.
     * @param aLB Lower bounds of axis using this scale.
     * @param aUB Upper bounds of axis using this scale.
     * @param xA Scaling coefficient for this axis.
     * @param xB Scaling coefficient for this axis.
     * @return An TickMarkData object containing the tick mark positions, lengths,
     *          and data values at each tick mark.
     */
    public TickMarkData calcTickMarks(double quantum, double aLB, double aUB, double xA, double xB);
}
